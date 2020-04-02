package com.askgps.personaltrackercore;

import android.app.Application;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.CameraXConfig.Provider;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\b&\030\0002\0020\0012\0020\002B\005?\006\002\020\003J\b\020\004\032\0020\005H\026?\006\006"}, d2={"Lcom/askgps/personaltrackercore/BaseApplication;", "Landroid/app/Application;", "Landroidx/camera/core/CameraXConfig$Provider;", "()V", "getCameraXConfig", "Landroidx/camera/core/CameraXConfig;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public abstract class BaseApplication
  extends Application
  implements CameraXConfig.Provider
{
  public BaseApplication() {}
  
  public CameraXConfig getCameraXConfig()
  {
    CameraXConfig localCameraXConfig = Camera2Config.defaultConfig();
    Intrinsics.checkExpressionValueIsNotNull(localCameraXConfig, "Camera2Config.defaultConfig()");
    return localCameraXConfig;
  }
}
