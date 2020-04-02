package androidx.camera.camera2.internal;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.VideoCapture.Defaults;
import androidx.camera.core.impl.CaptureConfig.Builder;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.SessionConfig.Builder;
import androidx.camera.core.impl.VideoCaptureConfig;
import androidx.camera.core.impl.VideoCaptureConfig.Builder;

public final class VideoCaptureConfigProvider
  implements ConfigProvider<VideoCaptureConfig>
{
  private static final String TAG = "VideoCaptureProvider";
  private final WindowManager mWindowManager;
  
  public VideoCaptureConfigProvider(Context paramContext)
  {
    this.mWindowManager = ((WindowManager)paramContext.getSystemService("window"));
  }
  
  public VideoCaptureConfig getConfig(CameraInfo paramCameraInfo)
  {
    VideoCaptureConfig.Builder localBuilder = VideoCaptureConfig.Builder.fromConfig(VideoCapture.DEFAULT_CONFIG.getConfig(paramCameraInfo));
    Object localObject = new SessionConfig.Builder();
    int i = 1;
    ((SessionConfig.Builder)localObject).setTemplateType(1);
    localBuilder.setDefaultSessionConfig(((SessionConfig.Builder)localObject).build());
    localBuilder.setSessionOptionUnpacker(Camera2SessionOptionUnpacker.INSTANCE);
    localObject = new CaptureConfig.Builder();
    ((CaptureConfig.Builder)localObject).setTemplateType(1);
    localBuilder.setDefaultCaptureConfig(((CaptureConfig.Builder)localObject).build());
    localBuilder.setCaptureOptionUnpacker(Camera2CaptureOptionUnpacker.INSTANCE);
    int j = this.mWindowManager.getDefaultDisplay().getRotation();
    localBuilder.setTargetRotation(j);
    if (paramCameraInfo != null)
    {
      int k = paramCameraInfo.getSensorRotationDegrees(j);
      j = i;
      if (k != 90) {
        if (k == 270) {
          j = i;
        } else {
          j = 0;
        }
      }
      if (j != 0) {
        paramCameraInfo = ImageOutputConfig.DEFAULT_ASPECT_RATIO_PORTRAIT;
      } else {
        paramCameraInfo = ImageOutputConfig.DEFAULT_ASPECT_RATIO_LANDSCAPE;
      }
      localBuilder.setTargetAspectRatioCustom(paramCameraInfo);
    }
    return localBuilder.getUseCaseConfig();
  }
}
