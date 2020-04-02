package androidx.camera.core;

import android.media.ImageReader;
import android.os.Handler;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.Surface;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.CameraCaptureResultImageInfo;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

class MetadataImageReader
  implements ImageReaderProxy, ForwardingImageProxy.OnImageCloseListener
{
  private static final String TAG = "MetadataImageReader";
  private final List<ImageProxy> mAcquiredImageProxies = new ArrayList();
  private CameraCaptureCallback mCameraCaptureCallback = new CameraCaptureCallback()
  {
    public void onCaptureCompleted(CameraCaptureResult paramAnonymousCameraCaptureResult)
    {
      super.onCaptureCompleted(paramAnonymousCameraCaptureResult);
      MetadataImageReader.this.resultIncoming(paramAnonymousCameraCaptureResult);
    }
  };
  private boolean mClosed = false;
  private Executor mExecutor;
  private int mImageProxiesIndex;
  private final ImageReaderProxy mImageReaderProxy;
  ImageReaderProxy.OnImageAvailableListener mListener;
  private final Object mLock = new Object();
  private List<ImageProxy> mMatchedImageProxies;
  private final LongSparseArray<ImageInfo> mPendingImageInfos = new LongSparseArray();
  private final LongSparseArray<ImageProxy> mPendingImages = new LongSparseArray();
  private ImageReaderProxy.OnImageAvailableListener mTransformedListener = new ImageReaderProxy.OnImageAvailableListener()
  {
    public void onImageAvailable(ImageReaderProxy paramAnonymousImageReaderProxy)
    {
      MetadataImageReader.this.imageIncoming(paramAnonymousImageReaderProxy);
    }
  };
  
  MetadataImageReader(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Handler paramHandler)
  {
    this.mImageReaderProxy = new AndroidImageReaderProxy(ImageReader.newInstance(paramInt1, paramInt2, paramInt3, paramInt4));
    init(CameraXExecutors.newHandlerExecutor(paramHandler));
  }
  
  MetadataImageReader(ImageReaderProxy paramImageReaderProxy, Handler paramHandler)
  {
    this.mImageReaderProxy = paramImageReaderProxy;
    init(CameraXExecutors.newHandlerExecutor(paramHandler));
  }
  
  private void dequeImageProxy(ImageProxy paramImageProxy)
  {
    synchronized (this.mLock)
    {
      int i = this.mMatchedImageProxies.indexOf(paramImageProxy);
      if (i >= 0)
      {
        this.mMatchedImageProxies.remove(i);
        if (i <= this.mImageProxiesIndex) {
          this.mImageProxiesIndex -= 1;
        }
      }
      this.mAcquiredImageProxies.remove(paramImageProxy);
      return;
    }
  }
  
  private void enqueueImageProxy(SettableImageProxy paramSettableImageProxy)
  {
    synchronized (this.mLock)
    {
      if (this.mMatchedImageProxies.size() < getMaxImages())
      {
        paramSettableImageProxy.addOnImageCloseListener(this);
        this.mMatchedImageProxies.add(paramSettableImageProxy);
        if (this.mListener != null) {
          if (this.mExecutor != null)
          {
            Executor localExecutor = this.mExecutor;
            paramSettableImageProxy = new androidx/camera/core/MetadataImageReader$3;
            paramSettableImageProxy.<init>(this);
            localExecutor.execute(paramSettableImageProxy);
          }
          else
          {
            this.mListener.onImageAvailable(this);
          }
        }
      }
      else
      {
        Log.d("TAG", "Maximum image number reached.");
        paramSettableImageProxy.close();
      }
      return;
    }
  }
  
  private void init(Executor paramExecutor)
  {
    this.mExecutor = paramExecutor;
    this.mImageReaderProxy.setOnImageAvailableListener(this.mTransformedListener, paramExecutor);
    this.mImageProxiesIndex = 0;
    this.mMatchedImageProxies = new ArrayList(getMaxImages());
  }
  
  private void matchImages()
  {
    synchronized (this.mLock)
    {
      for (int i = this.mPendingImageInfos.size() - 1; i >= 0; i--)
      {
        ImageInfo localImageInfo = (ImageInfo)this.mPendingImageInfos.valueAt(i);
        long l = localImageInfo.getTimestamp();
        ImageProxy localImageProxy = (ImageProxy)this.mPendingImages.get(l);
        if (localImageProxy != null)
        {
          this.mPendingImages.remove(l);
          this.mPendingImageInfos.removeAt(i);
          SettableImageProxy localSettableImageProxy = new androidx/camera/core/SettableImageProxy;
          localSettableImageProxy.<init>(localImageProxy, localImageInfo);
          enqueueImageProxy(localSettableImageProxy);
        }
      }
      removeStaleData();
      return;
    }
  }
  
  private void removeStaleData()
  {
    synchronized (this.mLock)
    {
      if ((this.mPendingImages.size() != 0) && (this.mPendingImageInfos.size() != 0))
      {
        Object localObject2 = this.mPendingImages;
        boolean bool = false;
        Long localLong = Long.valueOf(((LongSparseArray)localObject2).keyAt(0));
        localObject2 = Long.valueOf(this.mPendingImageInfos.keyAt(0));
        if (!((Long)localObject2).equals(localLong)) {
          bool = true;
        }
        Preconditions.checkArgument(bool);
        if (((Long)localObject2).longValue() > localLong.longValue()) {
          for (i = this.mPendingImages.size() - 1; i >= 0; i--) {
            if (this.mPendingImages.keyAt(i) < ((Long)localObject2).longValue())
            {
              ((ImageProxy)this.mPendingImages.valueAt(i)).close();
              this.mPendingImages.removeAt(i);
            }
          }
        }
        for (int i = this.mPendingImageInfos.size() - 1; i >= 0; i--) {
          if (this.mPendingImageInfos.keyAt(i) < localLong.longValue()) {
            this.mPendingImageInfos.removeAt(i);
          }
        }
        return;
      }
      return;
    }
  }
  
  public ImageProxy acquireLatestImage()
  {
    synchronized (this.mLock)
    {
      if (this.mMatchedImageProxies.isEmpty()) {
        return null;
      }
      if (this.mImageProxiesIndex < this.mMatchedImageProxies.size())
      {
        localObject2 = new java/util/ArrayList;
        ((ArrayList)localObject2).<init>();
        for (int i = 0; i < this.mMatchedImageProxies.size() - 1; i++) {
          if (!this.mAcquiredImageProxies.contains(this.mMatchedImageProxies.get(i))) {
            ((List)localObject2).add(this.mMatchedImageProxies.get(i));
          }
        }
        localObject2 = ((List)localObject2).iterator();
        while (((Iterator)localObject2).hasNext()) {
          ((ImageProxy)((Iterator)localObject2).next()).close();
        }
        i = this.mMatchedImageProxies.size() - 1;
        this.mImageProxiesIndex = i;
        localObject2 = this.mMatchedImageProxies;
        this.mImageProxiesIndex = (i + 1);
        localObject2 = (ImageProxy)((List)localObject2).get(i);
        this.mAcquiredImageProxies.add(localObject2);
        return localObject2;
      }
      Object localObject2 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject2).<init>("Maximum image number reached.");
      throw ((Throwable)localObject2);
    }
  }
  
  public ImageProxy acquireNextImage()
  {
    synchronized (this.mLock)
    {
      if (this.mMatchedImageProxies.isEmpty()) {
        return null;
      }
      if (this.mImageProxiesIndex < this.mMatchedImageProxies.size())
      {
        localObject2 = this.mMatchedImageProxies;
        int i = this.mImageProxiesIndex;
        this.mImageProxiesIndex = (i + 1);
        localObject2 = (ImageProxy)((List)localObject2).get(i);
        this.mAcquiredImageProxies.add(localObject2);
        return localObject2;
      }
      Object localObject2 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject2).<init>("Maximum image number reached.");
      throw ((Throwable)localObject2);
    }
  }
  
  public void close()
  {
    synchronized (this.mLock)
    {
      if (this.mClosed) {
        return;
      }
      Object localObject2 = new java/util/ArrayList;
      ((ArrayList)localObject2).<init>(this.mMatchedImageProxies);
      localObject2 = ((List)localObject2).iterator();
      while (((Iterator)localObject2).hasNext()) {
        ((ImageProxy)((Iterator)localObject2).next()).close();
      }
      this.mMatchedImageProxies.clear();
      this.mImageReaderProxy.close();
      this.mClosed = true;
      return;
    }
  }
  
  CameraCaptureCallback getCameraCaptureCallback()
  {
    return this.mCameraCaptureCallback;
  }
  
  public int getHeight()
  {
    synchronized (this.mLock)
    {
      int i = this.mImageReaderProxy.getHeight();
      return i;
    }
  }
  
  public int getImageFormat()
  {
    synchronized (this.mLock)
    {
      int i = this.mImageReaderProxy.getImageFormat();
      return i;
    }
  }
  
  public int getMaxImages()
  {
    synchronized (this.mLock)
    {
      int i = this.mImageReaderProxy.getMaxImages();
      return i;
    }
  }
  
  public Surface getSurface()
  {
    synchronized (this.mLock)
    {
      Surface localSurface = this.mImageReaderProxy.getSurface();
      return localSurface;
    }
  }
  
  public int getWidth()
  {
    synchronized (this.mLock)
    {
      int i = this.mImageReaderProxy.getWidth();
      return i;
    }
  }
  
  /* Error */
  void imageIncoming(ImageReaderProxy paramImageReaderProxy)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 49	androidx/camera/core/MetadataImageReader:mLock	Ljava/lang/Object;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 59	androidx/camera/core/MetadataImageReader:mClosed	Z
    //   11: ifeq +6 -> 17
    //   14: aload_2
    //   15: monitorexit
    //   16: return
    //   17: iconst_0
    //   18: istore_3
    //   19: aconst_null
    //   20: astore 4
    //   22: aload_1
    //   23: invokeinterface 279 1 0
    //   28: astore 5
    //   30: iload_3
    //   31: istore 6
    //   33: aload 5
    //   35: astore 4
    //   37: aload 5
    //   39: ifnull +60 -> 99
    //   42: iload_3
    //   43: iconst_1
    //   44: iadd
    //   45: istore 6
    //   47: aload_0
    //   48: getfield 66	androidx/camera/core/MetadataImageReader:mPendingImages	Landroid/util/LongSparseArray;
    //   51: aload 5
    //   53: invokeinterface 283 1 0
    //   58: invokeinterface 180 1 0
    //   63: aload 5
    //   65: invokevirtual 287	android/util/LongSparseArray:put	(JLjava/lang/Object;)V
    //   68: aload_0
    //   69: invokespecial 289	androidx/camera/core/MetadataImageReader:matchImages	()V
    //   72: aload 5
    //   74: astore 4
    //   76: goto +23 -> 99
    //   79: astore_1
    //   80: goto +41 -> 121
    //   83: astore 5
    //   85: ldc 18
    //   87: ldc_w 291
    //   90: aload 5
    //   92: invokestatic 294	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   95: pop
    //   96: iload_3
    //   97: istore 6
    //   99: aload 4
    //   101: ifnull +17 -> 118
    //   104: iload 6
    //   106: istore_3
    //   107: iload 6
    //   109: aload_1
    //   110: invokeinterface 269 1 0
    //   115: if_icmplt -96 -> 19
    //   118: aload_2
    //   119: monitorexit
    //   120: return
    //   121: aload_1
    //   122: athrow
    //   123: astore_1
    //   124: aload_2
    //   125: monitorexit
    //   126: aload_1
    //   127: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	128	0	this	MetadataImageReader
    //   0	128	1	paramImageReaderProxy	ImageReaderProxy
    //   4	121	2	localObject1	Object
    //   18	89	3	i	int
    //   20	80	4	localObject2	Object
    //   28	45	5	localImageProxy	ImageProxy
    //   83	8	5	localIllegalStateException	IllegalStateException
    //   31	85	6	j	int
    // Exception table:
    //   from	to	target	type
    //   22	30	79	finally
    //   85	96	79	finally
    //   22	30	83	java/lang/IllegalStateException
    //   7	16	123	finally
    //   47	72	123	finally
    //   107	118	123	finally
    //   118	120	123	finally
    //   121	123	123	finally
    //   124	126	123	finally
  }
  
  public void onImageClose(ImageProxy paramImageProxy)
  {
    synchronized (this.mLock)
    {
      dequeImageProxy(paramImageProxy);
      return;
    }
  }
  
  void resultIncoming(CameraCaptureResult paramCameraCaptureResult)
  {
    synchronized (this.mLock)
    {
      if (this.mClosed) {
        return;
      }
      LongSparseArray localLongSparseArray = this.mPendingImageInfos;
      long l = paramCameraCaptureResult.getTimestamp();
      CameraCaptureResultImageInfo localCameraCaptureResultImageInfo = new androidx/camera/core/internal/CameraCaptureResultImageInfo;
      localCameraCaptureResultImageInfo.<init>(paramCameraCaptureResult);
      localLongSparseArray.put(l, localCameraCaptureResultImageInfo);
      matchImages();
      return;
    }
  }
  
  public void setOnImageAvailableListener(ImageReaderProxy.OnImageAvailableListener paramOnImageAvailableListener, Handler paramHandler)
  {
    setOnImageAvailableListener(paramOnImageAvailableListener, CameraXExecutors.newHandlerExecutor(paramHandler));
  }
  
  public void setOnImageAvailableListener(ImageReaderProxy.OnImageAvailableListener paramOnImageAvailableListener, Executor paramExecutor)
  {
    synchronized (this.mLock)
    {
      this.mListener = paramOnImageAvailableListener;
      this.mExecutor = paramExecutor;
      this.mImageReaderProxy.setOnImageAvailableListener(this.mTransformedListener, paramExecutor);
      return;
    }
  }
}
