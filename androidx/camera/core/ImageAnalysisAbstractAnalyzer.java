package androidx.camera.core;

import androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.os.OperationCanceledException;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

abstract class ImageAnalysisAbstractAnalyzer
  implements ImageReaderProxy.OnImageAvailableListener
{
  private final Object mAnalyzerLock = new Object();
  private AtomicBoolean mIsClosed = new AtomicBoolean(false);
  private volatile int mRelativeRotation;
  private ImageAnalysis.Analyzer mSubscribedAnalyzer;
  private Executor mUserExecutor;
  
  ImageAnalysisAbstractAnalyzer() {}
  
  ListenableFuture<Void> analyzeImage(ImageProxy paramImageProxy)
  {
    synchronized (this.mAnalyzerLock)
    {
      Executor localExecutor = this.mUserExecutor;
      ImageAnalysis.Analyzer localAnalyzer = this.mSubscribedAnalyzer;
      if ((localAnalyzer != null) && (localExecutor != null)) {
        paramImageProxy = CallbackToFutureAdapter.getFuture(new _..Lambda.ImageAnalysisAbstractAnalyzer.mR2LN44DwXC209Sm8_KLIHvFCIo(this, localExecutor, paramImageProxy, localAnalyzer));
      } else {
        paramImageProxy = Futures.immediateFailedFuture(new OperationCanceledException("No analyzer or executor currently set."));
      }
      return paramImageProxy;
    }
  }
  
  void close()
  {
    this.mIsClosed.set(true);
  }
  
  boolean isClosed()
  {
    return this.mIsClosed.get();
  }
  
  void open()
  {
    this.mIsClosed.set(false);
  }
  
  void setAnalyzer(Executor paramExecutor, ImageAnalysis.Analyzer paramAnalyzer)
  {
    synchronized (this.mAnalyzerLock)
    {
      this.mSubscribedAnalyzer = paramAnalyzer;
      this.mUserExecutor = paramExecutor;
      return;
    }
  }
  
  void setRelativeRotation(int paramInt)
  {
    this.mRelativeRotation = paramInt;
  }
}
