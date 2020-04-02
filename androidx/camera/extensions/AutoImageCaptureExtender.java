package androidx.camera.extensions;

import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture.Builder;
import androidx.camera.extensions.impl.AutoImageCaptureExtenderImpl;

public class AutoImageCaptureExtender
  extends ImageCaptureExtender
{
  private static final String TAG = "AutoICExtender";
  
  private AutoImageCaptureExtender() {}
  
  public static AutoImageCaptureExtender create(ImageCapture.Builder paramBuilder)
  {
    if (ExtensionVersion.isExtensionVersionSupported()) {
      try
      {
        paramBuilder = new VendorAutoImageCaptureExtender(paramBuilder);
        return paramBuilder;
      }
      catch (NoClassDefFoundError paramBuilder)
      {
        Log.d("AutoICExtender", "No auto image capture extender found. Falling back to default.");
      }
    }
    return new DefaultAutoImageCaptureExtender();
  }
  
  static class DefaultAutoImageCaptureExtender
    extends AutoImageCaptureExtender
  {
    DefaultAutoImageCaptureExtender()
    {
      super();
    }
    
    public void enableExtension(CameraSelector paramCameraSelector) {}
    
    public boolean isExtensionAvailable(CameraSelector paramCameraSelector)
    {
      return false;
    }
  }
  
  static class VendorAutoImageCaptureExtender
    extends AutoImageCaptureExtender
  {
    private final AutoImageCaptureExtenderImpl mImpl;
    
    VendorAutoImageCaptureExtender(ImageCapture.Builder paramBuilder)
    {
      super();
      AutoImageCaptureExtenderImpl localAutoImageCaptureExtenderImpl = new AutoImageCaptureExtenderImpl();
      this.mImpl = localAutoImageCaptureExtenderImpl;
      init(paramBuilder, localAutoImageCaptureExtenderImpl, ExtensionsManager.EffectMode.AUTO);
    }
  }
}
