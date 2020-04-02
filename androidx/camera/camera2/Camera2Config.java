package androidx.camera.camera2;

import androidx.camera.core.CameraXConfig;
import androidx.camera.core.CameraXConfig.Builder;
import androidx.camera.core.CameraXConfig.Provider;

public final class Camera2Config
{
  private Camera2Config() {}
  
  public static CameraXConfig defaultConfig()
  {
    -..Lambda.ejR8dmSIsD2lu5sM8guFNf47-Yk localEjR8dmSIsD2lu5sM8guFNf47-Yk = _..Lambda.ejR8dmSIsD2lu5sM8guFNf47_Yk.INSTANCE;
    -..Lambda.Zqb088M5OSR69nGBYdSNqZX7Yfw localZqb088M5OSR69nGBYdSNqZX7Yfw = _..Lambda.Zqb088M5OSR69nGBYdSNqZX7Yfw.INSTANCE;
    -..Lambda.Camera2Config.92ESQ0e9eCnVdV8AqGLQ_KzLN1U local92ESQ0e9eCnVdV8AqGLQ_KzLN1U = _..Lambda.Camera2Config.92ESQ0e9eCnVdV8AqGLQ_KzLN1U.INSTANCE;
    return new CameraXConfig.Builder().setCameraFactoryProvider(localEjR8dmSIsD2lu5sM8guFNf47-Yk).setDeviceSurfaceManagerProvider(localZqb088M5OSR69nGBYdSNqZX7Yfw).setUseCaseConfigFactoryProvider(local92ESQ0e9eCnVdV8AqGLQ_KzLN1U).build();
  }
  
  public static final class DefaultProvider
    implements CameraXConfig.Provider
  {
    public DefaultProvider() {}
    
    public CameraXConfig getCameraXConfig()
    {
      return Camera2Config.defaultConfig();
    }
  }
}
