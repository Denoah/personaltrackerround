package androidx.camera.core;

import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

final class ImageAnalysisNonBlockingAnalyzer
  extends ImageAnalysisAbstractAnalyzer
{
  private static final String TAG = "NonBlockingCallback";
  final Executor mBackgroundExecutor;
  private ImageProxy mCachedImage;
  private final AtomicReference<CacheAnalyzingImageProxy> mPostedImage;
  private final AtomicLong mPostedImageTimestamp;
  
  ImageAnalysisNonBlockingAnalyzer(Executor paramExecutor)
  {
    this.mBackgroundExecutor = paramExecutor;
    this.mPostedImage = new AtomicReference();
    this.mPostedImageTimestamp = new AtomicLong();
    open();
  }
  
  private void analyze(ImageProxy paramImageProxy)
  {
    try
    {
      if (isClosed())
      {
        paramImageProxy.close();
        return;
      }
      Object localObject = (CacheAnalyzingImageProxy)this.mPostedImage.get();
      if ((localObject != null) && (paramImageProxy.getImageInfo().getTimestamp() <= this.mPostedImageTimestamp.get()))
      {
        paramImageProxy.close();
        return;
      }
      if ((localObject != null) && (!((CacheAnalyzingImageProxy)localObject).isClosed()))
      {
        if (this.mCachedImage != null) {
          this.mCachedImage.close();
        }
        this.mCachedImage = paramImageProxy;
        return;
      }
      localObject = new androidx/camera/core/ImageAnalysisNonBlockingAnalyzer$CacheAnalyzingImageProxy;
      ((CacheAnalyzingImageProxy)localObject).<init>(paramImageProxy, this);
      this.mPostedImage.set(localObject);
      this.mPostedImageTimestamp.set(((CacheAnalyzingImageProxy)localObject).getImageInfo().getTimestamp());
      ListenableFuture localListenableFuture = analyzeImage((ImageProxy)localObject);
      localObject = new androidx/camera/core/ImageAnalysisNonBlockingAnalyzer$1;
      ((1)localObject).<init>(this, paramImageProxy);
      Futures.addCallback(localListenableFuture, (FutureCallback)localObject, CameraXExecutors.directExecutor());
      return;
    }
    finally {}
  }
  
  void analyzeCachedImage()
  {
    try
    {
      if (this.mCachedImage != null)
      {
        ImageProxy localImageProxy = this.mCachedImage;
        this.mCachedImage = null;
        analyze(localImageProxy);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  void close()
  {
    try
    {
      super.close();
      if (this.mCachedImage != null)
      {
        this.mCachedImage.close();
        this.mCachedImage = null;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void onImageAvailable(ImageReaderProxy paramImageReaderProxy)
  {
    paramImageReaderProxy = paramImageReaderProxy.acquireLatestImage();
    if (paramImageReaderProxy == null) {
      return;
    }
    analyze(paramImageReaderProxy);
  }
  
  void open()
  {
    try
    {
      super.open();
      this.mCachedImage = null;
      this.mPostedImageTimestamp.set(-1L);
      this.mPostedImage.set(null);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  static class CacheAnalyzingImageProxy
    extends ForwardingImageProxy
  {
    private boolean mClosed = false;
    WeakReference<ImageAnalysisNonBlockingAnalyzer> mNonBlockingAnalyzerWeakReference;
    
    CacheAnalyzingImageProxy(ImageProxy paramImageProxy, ImageAnalysisNonBlockingAnalyzer paramImageAnalysisNonBlockingAnalyzer)
    {
      super();
      this.mNonBlockingAnalyzerWeakReference = new WeakReference(paramImageAnalysisNonBlockingAnalyzer);
      addOnImageCloseListener(new _..Lambda.ImageAnalysisNonBlockingAnalyzer.CacheAnalyzingImageProxy.Pq3gquMkypA8mh_f3dMKr3oV0M8(this));
    }
    
    boolean isClosed()
    {
      return this.mClosed;
    }
  }
}
