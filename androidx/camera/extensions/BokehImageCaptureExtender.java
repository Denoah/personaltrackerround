package androidx.camera.extensions;

import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture.Builder;
import androidx.camera.extensions.impl.BokehImageCaptureExtenderImpl;

public class BokehImageCaptureExtender
  extends ImageCaptureExtender
{
  private static final String TAG = "BokehImgCaptureExtender";
  
  private BokehImageCaptureExtender() {}
  
  public static BokehImageCaptureExtender create(ImageCapture.Builder paramBuilder)
  {
    if (ExtensionVersion.isExtensionVersionSupported()) {
      try
      {
        paramBuilder = new VendorBokehImageCaptureExtender(paramBuilder);
        return paramBuilder;
      }
      catch (NoClassDefFoundError paramBuilder)
      {
        Log.d("BokehImgCaptureExtender", "No bokeh image capture extender found. Falling back to default.");
      }
    }
    return new DefaultBokehImageCaptureExtender();
  }
  
  private static class DefaultBokehImageCaptureExtender
    extends BokehImageCaptureExtender
  {
    DefaultBokehImageCaptureExtender()
    {
      super();
    }
    
    public void enableExtension(CameraSelector paramCameraSelector) {}
    
    public boolean isExtensionAvailable(CameraSelector paramCameraSelector)
    {
      return false;
    }
  }
  
  private static class VendorBokehImageCaptureExtender
    extends BokehImageCaptureExtender
  {
    private final BokehImageCaptureExtenderImpl mImpl;
    
    VendorBokehImageCaptureExtender(ImageCapture.Builder paramBuilder)
    {
      super();
      BokehImageCaptureExtenderImpl localBokehImageCaptureExtenderImpl = new BokehImageCaptureExtenderImpl();
      this.mImpl = localBokehImageCaptureExtenderImpl;
      init(paramBuilder, localBokehImageCaptureExtenderImpl, ExtensionsManager.EffectMode.BOKEH);
    }
  }
}
