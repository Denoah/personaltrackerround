package androidx.camera.core.impl;

import android.content.Context;
import androidx.camera.core.CameraInfoUnavailableException;
import java.util.Set;

public abstract interface CameraFactory
{
  public abstract String cameraIdForLensFacing(int paramInt)
    throws CameraInfoUnavailableException;
  
  public abstract Set<String> getAvailableCameraIds()
    throws CameraInfoUnavailableException;
  
  public abstract CameraInternal getCamera(String paramString)
    throws CameraInfoUnavailableException;
  
  public abstract LensFacingCameraIdFilter getLensFacingCameraIdFilter(int paramInt);
  
  public static abstract interface Provider
  {
    public abstract CameraFactory newInstance(Context paramContext);
  }
}
