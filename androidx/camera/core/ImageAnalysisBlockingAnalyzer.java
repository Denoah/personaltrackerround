package androidx.camera.core;

import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;

final class ImageAnalysisBlockingAnalyzer
  extends ImageAnalysisAbstractAnalyzer
{
  ImageAnalysisBlockingAnalyzer() {}
  
  public void onImageAvailable(final ImageReaderProxy paramImageReaderProxy)
  {
    paramImageReaderProxy = paramImageReaderProxy.acquireNextImage();
    if (paramImageReaderProxy == null) {
      return;
    }
    Futures.addCallback(analyzeImage(paramImageReaderProxy), new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        paramImageReaderProxy.close();
      }
      
      public void onSuccess(Void paramAnonymousVoid) {}
    }, CameraXExecutors.directExecutor());
  }
}
