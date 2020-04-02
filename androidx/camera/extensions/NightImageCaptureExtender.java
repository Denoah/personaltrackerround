package androidx.camera.extensions;

import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture.Builder;
import androidx.camera.extensions.impl.NightImageCaptureExtenderImpl;

public class NightImageCaptureExtender
  extends ImageCaptureExtender
{
  private static final String TAG = "NightICExtender";
  
  private NightImageCaptureExtender() {}
  
  public static NightImageCaptureExtender create(ImageCapture.Builder paramBuilder)
  {
    if (ExtensionVersion.isExtensionVersionSupported()) {
      try
      {
        paramBuilder = new VendorNightImageCaptureExtender(paramBuilder);
        return paramBuilder;
      }
      catch (NoClassDefFoundError paramBuilder)
      {
        Log.d("NightICExtender", "No night image capture extender found. Falling back to default.");
      }
    }
    return new DefaultNightImageCaptureExtender();
  }
  
  static class DefaultNightImageCaptureExtender
    extends NightImageCaptureExtender
  {
    DefaultNightImageCaptureExtender()
    {
      super();
    }
    
    public void enableExtension(CameraSelector paramCameraSelector) {}
    
    public boolean isExtensionAvailable(CameraSelector paramCameraSelector)
    {
      return false;
    }
  }
  
  static class VendorNightImageCaptureExtender
    extends NightImageCaptureExtender
  {
    private final NightImageCaptureExtenderImpl mImpl;
    
    VendorNightImageCaptureExtender(ImageCapture.Builder paramBuilder)
    {
      super();
      NightImageCaptureExtenderImpl localNightImageCaptureExtenderImpl = new NightImageCaptureExtenderImpl();
      this.mImpl = localNightImageCaptureExtenderImpl;
      init(paramBuilder, localNightImageCaptureExtenderImpl, ExtensionsManager.EffectMode.NIGHT);
    }
  }
}
