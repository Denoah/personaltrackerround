package androidx.camera.lifecycle;

import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.UseCase;

abstract interface LifecycleCameraProvider
{
  public abstract boolean hasCamera(CameraSelector paramCameraSelector)
    throws CameraInfoUnavailableException;
  
  public abstract boolean isBound(UseCase paramUseCase);
  
  public abstract void unbind(UseCase... paramVarArgs);
  
  public abstract void unbindAll();
}
