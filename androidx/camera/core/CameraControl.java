package androidx.camera.core;

import com.google.common.util.concurrent.ListenableFuture;

public abstract interface CameraControl
{
  public abstract ListenableFuture<Void> cancelFocusAndMetering();
  
  public abstract ListenableFuture<Void> enableTorch(boolean paramBoolean);
  
  public abstract ListenableFuture<Void> setLinearZoom(float paramFloat);
  
  public abstract ListenableFuture<Void> setZoomRatio(float paramFloat);
  
  public abstract ListenableFuture<FocusMeteringResult> startFocusAndMetering(FocusMeteringAction paramFocusMeteringAction);
  
  public static final class OperationCanceledException
    extends Exception
  {
    public OperationCanceledException(String paramString)
    {
      super();
    }
    
    public OperationCanceledException(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
}
