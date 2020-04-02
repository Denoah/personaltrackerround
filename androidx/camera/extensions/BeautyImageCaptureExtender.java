package androidx.camera.extensions;

import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture.Builder;
import androidx.camera.extensions.impl.BeautyImageCaptureExtenderImpl;

public class BeautyImageCaptureExtender
  extends ImageCaptureExtender
{
  private static final String TAG = "BeautyICExtender";
  
  private BeautyImageCaptureExtender() {}
  
  public static BeautyImageCaptureExtender create(ImageCapture.Builder paramBuilder)
  {
    if (ExtensionVersion.isExtensionVersionSupported()) {
      try
      {
        paramBuilder = new VendorBeautyImageCaptureExtender(paramBuilder);
        return paramBuilder;
      }
      catch (NoClassDefFoundError paramBuilder)
      {
        Log.d("BeautyICExtender", "No beauty image capture extender found. Falling back to default.");
      }
    }
    return new DefaultBeautyImageCaptureExtender();
  }
  
  static class DefaultBeautyImageCaptureExtender
    extends BeautyImageCaptureExtender
  {
    DefaultBeautyImageCaptureExtender()
    {
      super();
    }
    
    public void enableExtension(CameraSelector paramCameraSelector) {}
    
    public boolean isExtensionAvailable(CameraSelector paramCameraSelector)
    {
      return false;
    }
  }
  
  static class VendorBeautyImageCaptureExtender
    extends BeautyImageCaptureExtender
  {
    private final BeautyImageCaptureExtenderImpl mImpl;
    
    VendorBeautyImageCaptureExtender(ImageCapture.Builder paramBuilder)
    {
      super();
      BeautyImageCaptureExtenderImpl localBeautyImageCaptureExtenderImpl = new BeautyImageCaptureExtenderImpl();
      this.mImpl = localBeautyImageCaptureExtenderImpl;
      init(paramBuilder, localBeautyImageCaptureExtenderImpl, ExtensionsManager.EffectMode.BEAUTY);
    }
  }
}
