package androidx.camera.core.impl;

import android.graphics.Rect;
import androidx.camera.core.CameraControl;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;

public abstract interface CameraControlInternal
  extends CameraControl
{
  public static final CameraControlInternal DEFAULT_EMPTY_INSTANCE = new CameraControlInternal()
  {
    public void cancelAfAeTrigger(boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2) {}
    
    public ListenableFuture<Void> cancelFocusAndMetering()
    {
      return Futures.immediateFuture(null);
    }
    
    public ListenableFuture<Void> enableTorch(boolean paramAnonymousBoolean)
    {
      return Futures.immediateFuture(null);
    }
    
    public int getFlashMode()
    {
      return 2;
    }
    
    public void setCropRegion(Rect paramAnonymousRect) {}
    
    public void setFlashMode(int paramAnonymousInt) {}
    
    public ListenableFuture<Void> setLinearZoom(float paramAnonymousFloat)
    {
      return Futures.immediateFuture(null);
    }
    
    public ListenableFuture<Void> setZoomRatio(float paramAnonymousFloat)
    {
      return Futures.immediateFuture(null);
    }
    
    public ListenableFuture<FocusMeteringResult> startFocusAndMetering(FocusMeteringAction paramAnonymousFocusMeteringAction)
    {
      return Futures.immediateFuture(FocusMeteringResult.emptyInstance());
    }
    
    public void submitCaptureRequests(List<CaptureConfig> paramAnonymousList) {}
    
    public void triggerAePrecapture() {}
    
    public void triggerAf() {}
  };
  
  public abstract void cancelAfAeTrigger(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract int getFlashMode();
  
  public abstract void setCropRegion(Rect paramRect);
  
  public abstract void setFlashMode(int paramInt);
  
  public abstract void submitCaptureRequests(List<CaptureConfig> paramList);
  
  public abstract void triggerAePrecapture();
  
  public abstract void triggerAf();
  
  public static abstract interface ControlUpdateCallback
  {
    public abstract void onCameraControlCaptureRequests(List<CaptureConfig> paramList);
    
    public abstract void onCameraControlUpdateSessionConfig(SessionConfig paramSessionConfig);
  }
}
