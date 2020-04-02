package androidx.camera.core;

import android.media.ImageReader;
import android.os.Handler;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CaptureBundle;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

class ProcessingImageReader
  implements ImageReaderProxy
{
  private static final String TAG = "ProcessingImageReader";
  private final List<Integer> mCaptureIdList = new ArrayList();
  CaptureProcessor mCaptureProcessor;
  private FutureCallback<List<ImageProxy>> mCaptureStageReadyCallback = new FutureCallback()
  {
    public void onFailure(Throwable paramAnonymousThrowable) {}
    
    public void onSuccess(List<ImageProxy> paramAnonymousList)
    {
      ProcessingImageReader.this.mCaptureProcessor.process(ProcessingImageReader.this.mSettableImageProxyBundle);
    }
  };
  private boolean mClosed = false;
  Executor mExecutor;
  private ImageReaderProxy.OnImageAvailableListener mImageProcessedListener = new ImageReaderProxy.OnImageAvailableListener()
  {
    public void onImageAvailable(ImageReaderProxy paramAnonymousImageReaderProxy)
    {
      if (ProcessingImageReader.this.mExecutor != null) {
        ProcessingImageReader.this.mExecutor.execute(new Runnable()
        {
          public void run()
          {
            ProcessingImageReader.this.mListener.onImageAvailable(ProcessingImageReader.this);
          }
        });
      } else {
        ProcessingImageReader.this.mListener.onImageAvailable(ProcessingImageReader.this);
      }
      ProcessingImageReader.this.mSettableImageProxyBundle.reset();
      ProcessingImageReader.this.setupSettableImageProxyBundleCallbacks();
    }
  };
  private final ImageReaderProxy mInputImageReader;
  ImageReaderProxy.OnImageAvailableListener mListener;
  private final Object mLock = new Object();
  private final ImageReaderProxy mOutputImageReader;
  SettableImageProxyBundle mSettableImageProxyBundle = null;
  private ImageReaderProxy.OnImageAvailableListener mTransformedListener = new ImageReaderProxy.OnImageAvailableListener()
  {
    public void onImageAvailable(ImageReaderProxy paramAnonymousImageReaderProxy)
    {
      ProcessingImageReader.this.imageIncoming(paramAnonymousImageReaderProxy);
    }
  };
  
  ProcessingImageReader(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Handler paramHandler, CaptureBundle paramCaptureBundle, CaptureProcessor paramCaptureProcessor)
  {
    this.mInputImageReader = new MetadataImageReader(paramInt1, paramInt2, paramInt3, paramInt4, paramHandler);
    this.mOutputImageReader = new AndroidImageReaderProxy(ImageReader.newInstance(paramInt1, paramInt2, paramInt3, paramInt4));
    init(CameraXExecutors.newHandlerExecutor(paramHandler), paramCaptureBundle, paramCaptureProcessor);
  }
  
  ProcessingImageReader(ImageReaderProxy paramImageReaderProxy, Handler paramHandler, CaptureBundle paramCaptureBundle, CaptureProcessor paramCaptureProcessor)
  {
    if (paramImageReaderProxy.getMaxImages() >= paramCaptureBundle.getCaptureStages().size())
    {
      this.mInputImageReader = paramImageReaderProxy;
      this.mOutputImageReader = new AndroidImageReaderProxy(ImageReader.newInstance(paramImageReaderProxy.getWidth(), paramImageReaderProxy.getHeight(), paramImageReaderProxy.getImageFormat(), paramImageReaderProxy.getMaxImages()));
      init(CameraXExecutors.newHandlerExecutor(paramHandler), paramCaptureBundle, paramCaptureProcessor);
      return;
    }
    throw new IllegalArgumentException("MetadataImageReader is smaller than CaptureBundle.");
  }
  
  private void init(Executor paramExecutor, CaptureBundle paramCaptureBundle, CaptureProcessor paramCaptureProcessor)
  {
    this.mExecutor = paramExecutor;
    this.mInputImageReader.setOnImageAvailableListener(this.mTransformedListener, paramExecutor);
    this.mOutputImageReader.setOnImageAvailableListener(this.mImageProcessedListener, paramExecutor);
    this.mCaptureProcessor = paramCaptureProcessor;
    paramCaptureProcessor.onOutputSurface(this.mOutputImageReader.getSurface(), getImageFormat());
    this.mCaptureProcessor.onResolutionUpdate(new Size(this.mInputImageReader.getWidth(), this.mInputImageReader.getHeight()));
    setCaptureBundle(paramCaptureBundle);
  }
  
  public ImageProxy acquireLatestImage()
  {
    synchronized (this.mLock)
    {
      ImageProxy localImageProxy = this.mOutputImageReader.acquireLatestImage();
      return localImageProxy;
    }
  }
  
  public ImageProxy acquireNextImage()
  {
    synchronized (this.mLock)
    {
      ImageProxy localImageProxy = this.mOutputImageReader.acquireNextImage();
      return localImageProxy;
    }
  }
  
  public void close()
  {
    synchronized (this.mLock)
    {
      if (this.mClosed) {
        return;
      }
      this.mInputImageReader.close();
      this.mOutputImageReader.close();
      this.mSettableImageProxyBundle.close();
      this.mClosed = true;
      return;
    }
  }
  
  CameraCaptureCallback getCameraCaptureCallback()
  {
    ImageReaderProxy localImageReaderProxy = this.mInputImageReader;
    if ((localImageReaderProxy instanceof MetadataImageReader)) {
      return ((MetadataImageReader)localImageReaderProxy).getCameraCaptureCallback();
    }
    return null;
  }
  
  public int getHeight()
  {
    synchronized (this.mLock)
    {
      int i = this.mInputImageReader.getHeight();
      return i;
    }
  }
  
  public int getImageFormat()
  {
    synchronized (this.mLock)
    {
      int i = this.mInputImageReader.getImageFormat();
      return i;
    }
  }
  
  public int getMaxImages()
  {
    synchronized (this.mLock)
    {
      int i = this.mInputImageReader.getMaxImages();
      return i;
    }
  }
  
  public Surface getSurface()
  {
    synchronized (this.mLock)
    {
      Surface localSurface = this.mInputImageReader.getSurface();
      return localSurface;
    }
  }
  
  public int getWidth()
  {
    synchronized (this.mLock)
    {
      int i = this.mInputImageReader.getWidth();
      return i;
    }
  }
  
  /* Error */
  void imageIncoming(ImageReaderProxy paramImageReaderProxy)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 48	androidx/camera/core/ProcessingImageReader:mLock	Ljava/lang/Object;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 61	androidx/camera/core/ProcessingImageReader:mClosed	Z
    //   11: ifeq +6 -> 17
    //   14: aload_2
    //   15: monitorexit
    //   16: return
    //   17: aload_1
    //   18: invokeinterface 170 1 0
    //   23: astore_1
    //   24: aload_1
    //   25: ifnull +101 -> 126
    //   28: aload_1
    //   29: invokeinterface 190 1 0
    //   34: invokeinterface 196 1 0
    //   39: checkcast 198	java/lang/Integer
    //   42: astore_3
    //   43: aload_0
    //   44: getfield 68	androidx/camera/core/ProcessingImageReader:mCaptureIdList	Ljava/util/List;
    //   47: aload_3
    //   48: invokeinterface 202 2 0
    //   53: ifne +48 -> 101
    //   56: new 204	java/lang/StringBuilder
    //   59: astore 4
    //   61: aload 4
    //   63: invokespecial 205	java/lang/StringBuilder:<init>	()V
    //   66: aload 4
    //   68: ldc -49
    //   70: invokevirtual 211	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   73: pop
    //   74: aload 4
    //   76: aload_3
    //   77: invokevirtual 214	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   80: pop
    //   81: ldc 18
    //   83: aload 4
    //   85: invokevirtual 218	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   88: invokestatic 224	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   91: pop
    //   92: aload_1
    //   93: invokeinterface 225 1 0
    //   98: goto +28 -> 126
    //   101: aload_0
    //   102: getfield 63	androidx/camera/core/ProcessingImageReader:mSettableImageProxyBundle	Landroidx/camera/core/SettableImageProxyBundle;
    //   105: aload_1
    //   106: invokevirtual 229	androidx/camera/core/SettableImageProxyBundle:addImageProxy	(Landroidx/camera/core/ImageProxy;)V
    //   109: goto +17 -> 126
    //   112: astore_1
    //   113: goto +16 -> 129
    //   116: astore_1
    //   117: ldc 18
    //   119: ldc -25
    //   121: aload_1
    //   122: invokestatic 235	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   125: pop
    //   126: aload_2
    //   127: monitorexit
    //   128: return
    //   129: aload_1
    //   130: athrow
    //   131: astore_1
    //   132: aload_2
    //   133: monitorexit
    //   134: aload_1
    //   135: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	136	0	this	ProcessingImageReader
    //   0	136	1	paramImageReaderProxy	ImageReaderProxy
    //   4	129	2	localObject	Object
    //   42	35	3	localInteger	Integer
    //   59	25	4	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   17	24	112	finally
    //   117	126	112	finally
    //   17	24	116	java/lang/IllegalStateException
    //   7	16	131	finally
    //   28	98	131	finally
    //   101	109	131	finally
    //   126	128	131	finally
    //   129	131	131	finally
    //   132	134	131	finally
  }
  
  public void setCaptureBundle(CaptureBundle paramCaptureBundle)
  {
    synchronized (this.mLock)
    {
      if (paramCaptureBundle.getCaptureStages() != null)
      {
        if (this.mInputImageReader.getMaxImages() >= paramCaptureBundle.getCaptureStages().size())
        {
          this.mCaptureIdList.clear();
          Iterator localIterator = paramCaptureBundle.getCaptureStages().iterator();
          while (localIterator.hasNext())
          {
            paramCaptureBundle = (CaptureStage)localIterator.next();
            if (paramCaptureBundle != null) {
              this.mCaptureIdList.add(Integer.valueOf(paramCaptureBundle.getId()));
            }
          }
        }
        paramCaptureBundle = new java/lang/IllegalArgumentException;
        paramCaptureBundle.<init>("CaptureBundle is lager than InputImageReader.");
        throw paramCaptureBundle;
      }
      paramCaptureBundle = new androidx/camera/core/SettableImageProxyBundle;
      paramCaptureBundle.<init>(this.mCaptureIdList);
      this.mSettableImageProxyBundle = paramCaptureBundle;
      setupSettableImageProxyBundleCallbacks();
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
      this.mInputImageReader.setOnImageAvailableListener(this.mTransformedListener, paramExecutor);
      this.mOutputImageReader.setOnImageAvailableListener(this.mImageProcessedListener, paramExecutor);
      return;
    }
  }
  
  void setupSettableImageProxyBundleCallbacks()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mCaptureIdList.iterator();
    while (localIterator.hasNext())
    {
      Integer localInteger = (Integer)localIterator.next();
      localArrayList.add(this.mSettableImageProxyBundle.getImageProxy(localInteger.intValue()));
    }
    Futures.addCallback(Futures.allAsList(localArrayList), this.mCaptureStageReadyCallback, CameraXExecutors.directExecutor());
  }
}
