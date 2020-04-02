package androidx.camera.extensions;

import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview.Builder;
import androidx.camera.extensions.impl.NightPreviewExtenderImpl;

public class NightPreviewExtender
  extends PreviewExtender
{
  private static final String TAG = "NightPreviewExtender";
  
  private NightPreviewExtender() {}
  
  public static NightPreviewExtender create(Preview.Builder paramBuilder)
  {
    if (ExtensionVersion.isExtensionVersionSupported()) {
      try
      {
        paramBuilder = new VendorNightPreviewExtender(paramBuilder);
        return paramBuilder;
      }
      catch (NoClassDefFoundError paramBuilder)
      {
        Log.d("NightPreviewExtender", "No night preview extender found. Falling back to default.");
      }
    }
    return new DefaultNightPreviewExtender();
  }
  
  static class DefaultNightPreviewExtender
    extends NightPreviewExtender
  {
    DefaultNightPreviewExtender()
    {
      super();
    }
    
    public void enableExtension(CameraSelector paramCameraSelector) {}
    
    public boolean isExtensionAvailable(CameraSelector paramCameraSelector)
    {
      return false;
    }
  }
  
  static class VendorNightPreviewExtender
    extends NightPreviewExtender
  {
    private final NightPreviewExtenderImpl mImpl;
    
    VendorNightPreviewExtender(Preview.Builder paramBuilder)
    {
      super();
      NightPreviewExtenderImpl localNightPreviewExtenderImpl = new NightPreviewExtenderImpl();
      this.mImpl = localNightPreviewExtenderImpl;
      init(paramBuilder, localNightPreviewExtenderImpl, ExtensionsManager.EffectMode.NIGHT);
    }
  }
}
