package androidx.camera.core;

import androidx.lifecycle.LiveData;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract interface CameraInfo
{
  public static final String IMPLEMENTATION_TYPE_CAMERA2 = "androidx.camera.camera2";
  public static final String IMPLEMENTATION_TYPE_CAMERA2_LEGACY = "androidx.camera.camera2.legacy";
  public static final String IMPLEMENTATION_TYPE_FAKE = "androidx.camera.fake";
  public static final String IMPLEMENTATION_TYPE_UNKNOWN = "<unknown>";
  
  public abstract String getImplementationType();
  
  public abstract int getSensorRotationDegrees();
  
  public abstract int getSensorRotationDegrees(int paramInt);
  
  public abstract LiveData<Integer> getTorchState();
  
  public abstract LiveData<ZoomState> getZoomState();
  
  public abstract boolean hasFlashUnit();
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ImplementationType {}
}
