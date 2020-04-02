package androidx.camera.core;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener;
import androidx.camera.core.impl.SingleImageProxyBundle;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;

final class ProcessingSurface
  extends DeferrableSurface
{
  private static final int MAX_IMAGES = 2;
  private static final String TAG = "ProcessingSurfaceTextur";
  private final CameraCaptureCallback mCameraCaptureCallback;
  final CaptureProcessor mCaptureProcessor;
  final CaptureStage mCaptureStage;
  private final Handler mImageReaderHandler;
  final MetadataImageReader mInputImageReader;
  final Surface mInputSurface;
  final Object mLock = new Object();
  private final DeferrableSurface mOutputDeferrableSurface;
  boolean mReleased = false;
  private final Size mResolution;
  private final ImageReaderProxy.OnImageAvailableListener mTransformedListener = new ImageReaderProxy.OnImageAvailableListener()
  {
    public void onImageAvailable(ImageReaderProxy paramAnonymousImageReaderProxy)
    {
      synchronized (ProcessingSurface.this.mLock)
      {
        ProcessingSurface.this.imageIncoming(paramAnonymousImageReaderProxy);
        return;
      }
    }
  };
  
  ProcessingSurface(int paramInt1, int paramInt2, int paramInt3, Handler paramHandler, CaptureStage paramCaptureStage, CaptureProcessor paramCaptureProcessor, DeferrableSurface paramDeferrableSurface)
  {
    this.mResolution = new Size(paramInt1, paramInt2);
    if (paramHandler != null)
    {
      this.mImageReaderHandler = paramHandler;
    }
    else
    {
      paramHandler = Looper.myLooper();
      if (paramHandler == null) {
        break label209;
      }
      this.mImageReaderHandler = new Handler(paramHandler);
    }
    paramHandler = new MetadataImageReader(paramInt1, paramInt2, paramInt3, 2, this.mImageReaderHandler);
    this.mInputImageReader = paramHandler;
    paramHandler.setOnImageAvailableListener(this.mTransformedListener, this.mImageReaderHandler);
    this.mInputSurface = this.mInputImageReader.getSurface();
    this.mCameraCaptureCallback = this.mInputImageReader.getCameraCaptureCallback();
    this.mCaptureProcessor = paramCaptureProcessor;
    paramCaptureProcessor.onResolutionUpdate(this.mResolution);
    this.mCaptureStage = paramCaptureStage;
    this.mOutputDeferrableSurface = paramDeferrableSurface;
    Futures.addCallback(paramDeferrableSurface.getSurface(), new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        Log.e("ProcessingSurfaceTextur", "Failed to extract Listenable<Surface>.", paramAnonymousThrowable);
      }
      
      public void onSuccess(Surface paramAnonymousSurface)
      {
        synchronized (ProcessingSurface.this.mLock)
        {
          ProcessingSurface.this.mCaptureProcessor.onOutputSurface(paramAnonymousSurface, 1);
          return;
        }
      }
    }, CameraXExecutors.directExecutor());
    getTerminationFuture().addListener(new _..Lambda.ProcessingSurface.fleJ7Fv2BvhRan9diypF10B_VWk(this), CameraXExecutors.directExecutor());
    return;
    label209:
    throw new IllegalStateException("Creating a ProcessingSurface requires a non-null Handler, or be created  on a thread with a Looper.");
  }
  
  private void release()
  {
    synchronized (this.mLock)
    {
      if (this.mReleased) {
        return;
      }
      this.mInputImageReader.close();
      this.mInputSurface.release();
      this.mOutputDeferrableSurface.close();
      this.mReleased = true;
      return;
    }
  }
  
  CameraCaptureCallback getCameraCaptureCallback()
  {
    synchronized (this.mLock)
    {
      if (!this.mReleased)
      {
        localObject2 = this.mCameraCaptureCallback;
        return localObject2;
      }
      Object localObject2 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject2).<init>("ProcessingSurface already released!");
      throw ((Throwable)localObject2);
    }
  }
  
  void imageIncoming(ImageReaderProxy paramImageReaderProxy)
  {
    if (this.mReleased) {
      return;
    }
    Object localObject = null;
    try
    {
      paramImageReaderProxy = paramImageReaderProxy.acquireNextImage();
    }
    catch (IllegalStateException paramImageReaderProxy)
    {
      Log.e("ProcessingSurfaceTextur", "Failed to acquire next image.", paramImageReaderProxy);
      paramImageReaderProxy = (ImageReaderProxy)localObject;
    }
    if (paramImageReaderProxy == null) {
      return;
    }
    localObject = paramImageReaderProxy.getImageInfo();
    if (localObject == null)
    {
      paramImageReaderProxy.close();
      return;
    }
    localObject = ((ImageInfo)localObject).getTag();
    if (localObject == null)
    {
      paramImageReaderProxy.close();
      return;
    }
    if (!(localObject instanceof Integer))
    {
      paramImageReaderProxy.close();
      return;
    }
    Integer localInteger = (Integer)localObject;
    if (this.mCaptureStage.getId() != localInteger.intValue())
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("ImageProxyBundle does not contain this id: ");
      ((StringBuilder)localObject).append(localInteger);
      Log.w("ProcessingSurfaceTextur", ((StringBuilder)localObject).toString());
      paramImageReaderProxy.close();
    }
    else
    {
      paramImageReaderProxy = new SingleImageProxyBundle(paramImageReaderProxy);
      this.mCaptureProcessor.process(paramImageReaderProxy);
      paramImageReaderProxy.close();
    }
  }
  
  public ListenableFuture<Surface> provideSurface()
  {
    return Futures.immediateFuture(this.mInputSurface);
  }
}
