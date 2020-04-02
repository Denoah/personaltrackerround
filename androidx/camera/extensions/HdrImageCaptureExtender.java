package androidx.camera.extensions;

import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture.Builder;
import androidx.camera.extensions.impl.HdrImageCaptureExtenderImpl;

public class HdrImageCaptureExtender
  extends ImageCaptureExtender
{
  private static final String TAG = "HdrImageCaptureExtender";
  
  private HdrImageCaptureExtender() {}
  
  public static HdrImageCaptureExtender create(ImageCapture.Builder paramBuilder)
  {
    if (ExtensionVersion.isExtensionVersionSupported()) {
      try
      {
        paramBuilder = new VendorHdrImageCaptureExtender(paramBuilder);
        return paramBuilder;
      }
      catch (NoClassDefFoundError paramBuilder)
      {
        Log.d("HdrImageCaptureExtender", "No HDR image capture extender found. Falling back to default.");
      }
    }
    return new DefaultHdrImageCaptureExtender();
  }
  
  static class DefaultHdrImageCaptureExtender
    extends HdrImageCaptureExtender
  {
    DefaultHdrImageCaptureExtender()
    {
      super();
    }
    
    public void enableExtension(CameraSelector paramCameraSelector) {}
    
    public boolean isExtensionAvailable(CameraSelector paramCameraSelector)
    {
      return false;
    }
  }
  
  static class VendorHdrImageCaptureExtender
    extends HdrImageCaptureExtender
  {
    private final HdrImageCaptureExtenderImpl mImpl;
    
    VendorHdrImageCaptureExtender(ImageCapture.Builder paramBuilder)
    {
      super();
      HdrImageCaptureExtenderImpl localHdrImageCaptureExtenderImpl = new HdrImageCaptureExtenderImpl();
      this.mImpl = localHdrImageCaptureExtenderImpl;
      init(paramBuilder, localHdrImageCaptureExtenderImpl, ExtensionsManager.EffectMode.HDR);
    }
  }
}
