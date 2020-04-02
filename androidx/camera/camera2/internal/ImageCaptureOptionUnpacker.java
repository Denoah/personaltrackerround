package androidx.camera.camera2.internal;

import android.hardware.camera2.CaptureRequest;
import androidx.camera.camera2.impl.Camera2ImplConfig.Builder;
import androidx.camera.core.impl.CaptureConfig.Builder;
import androidx.camera.core.impl.DeviceProperties;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.core.impl.UseCaseConfig;

final class ImageCaptureOptionUnpacker
  extends Camera2CaptureOptionUnpacker
{
  static final ImageCaptureOptionUnpacker INSTANCE = new ImageCaptureOptionUnpacker();
  private DeviceProperties mDeviceProperties = DeviceProperties.create();
  
  ImageCaptureOptionUnpacker() {}
  
  private void applyPixelHdrPlusChangeForCaptureMode(int paramInt, Camera2ImplConfig.Builder paramBuilder)
  {
    if (("Google".equals(this.mDeviceProperties.manufacturer())) && (("Pixel 2".equals(this.mDeviceProperties.model())) || ("Pixel 3".equals(this.mDeviceProperties.model()))) && (this.mDeviceProperties.sdkVersion() >= 26)) {
      if (paramInt != 0)
      {
        if (paramInt == 1) {
          paramBuilder.setCaptureRequestOption(CaptureRequest.CONTROL_ENABLE_ZSL, Boolean.valueOf(false));
        }
      }
      else {
        paramBuilder.setCaptureRequestOption(CaptureRequest.CONTROL_ENABLE_ZSL, Boolean.valueOf(true));
      }
    }
  }
  
  void setDeviceProperty(DeviceProperties paramDeviceProperties)
  {
    this.mDeviceProperties = paramDeviceProperties;
  }
  
  public void unpack(UseCaseConfig<?> paramUseCaseConfig, CaptureConfig.Builder paramBuilder)
  {
    super.unpack(paramUseCaseConfig, paramBuilder);
    if ((paramUseCaseConfig instanceof ImageCaptureConfig))
    {
      paramUseCaseConfig = (ImageCaptureConfig)paramUseCaseConfig;
      Camera2ImplConfig.Builder localBuilder = new Camera2ImplConfig.Builder();
      if (paramUseCaseConfig.hasCaptureMode()) {
        applyPixelHdrPlusChangeForCaptureMode(paramUseCaseConfig.getCaptureMode(), localBuilder);
      }
      paramBuilder.addImplementationOptions(localBuilder.build());
      return;
    }
    throw new IllegalArgumentException("config is not ImageCaptureConfig");
  }
}
