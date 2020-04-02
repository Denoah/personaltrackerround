package androidx.camera.extensions;

import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview.Builder;
import androidx.camera.extensions.impl.HdrPreviewExtenderImpl;

public class HdrPreviewExtender
  extends PreviewExtender
{
  private static final String TAG = "HdrPreviewExtender";
  
  private HdrPreviewExtender() {}
  
  public static HdrPreviewExtender create(Preview.Builder paramBuilder)
  {
    if (ExtensionVersion.isExtensionVersionSupported()) {
      try
      {
        paramBuilder = new VendorHdrPreviewExtender(paramBuilder);
        return paramBuilder;
      }
      catch (NoClassDefFoundError paramBuilder)
      {
        Log.d("HdrPreviewExtender", "No HDR preview extender found. Falling back to default.");
      }
    }
    return new DefaultHdrPreviewExtender();
  }
  
  static class DefaultHdrPreviewExtender
    extends HdrPreviewExtender
  {
    DefaultHdrPreviewExtender()
    {
      super();
    }
    
    public void enableExtension(CameraSelector paramCameraSelector) {}
    
    public boolean isExtensionAvailable(CameraSelector paramCameraSelector)
    {
      return false;
    }
  }
  
  static class VendorHdrPreviewExtender
    extends HdrPreviewExtender
  {
    private final HdrPreviewExtenderImpl mImpl;
    
    VendorHdrPreviewExtender(Preview.Builder paramBuilder)
    {
      super();
      HdrPreviewExtenderImpl localHdrPreviewExtenderImpl = new HdrPreviewExtenderImpl();
      this.mImpl = localHdrPreviewExtenderImpl;
      init(paramBuilder, localHdrPreviewExtenderImpl, ExtensionsManager.EffectMode.HDR);
    }
  }
}
