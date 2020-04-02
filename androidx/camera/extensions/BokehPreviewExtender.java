package androidx.camera.extensions;

import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview.Builder;
import androidx.camera.extensions.impl.BokehPreviewExtenderImpl;

public class BokehPreviewExtender
  extends PreviewExtender
{
  private static final String TAG = "BokehPreviewExtender";
  
  private BokehPreviewExtender() {}
  
  public static BokehPreviewExtender create(Preview.Builder paramBuilder)
  {
    if (ExtensionVersion.isExtensionVersionSupported()) {
      try
      {
        paramBuilder = new VendorBokehPreviewExtender(paramBuilder);
        return paramBuilder;
      }
      catch (NoClassDefFoundError paramBuilder)
      {
        Log.d("BokehPreviewExtender", "No bokeh preview extender found. Falling back to default.");
      }
    }
    return new DefaultBokehPreviewExtender();
  }
  
  private static class DefaultBokehPreviewExtender
    extends BokehPreviewExtender
  {
    DefaultBokehPreviewExtender()
    {
      super();
    }
    
    public void enableExtension(CameraSelector paramCameraSelector) {}
    
    public boolean isExtensionAvailable(CameraSelector paramCameraSelector)
    {
      return false;
    }
  }
  
  private static class VendorBokehPreviewExtender
    extends BokehPreviewExtender
  {
    private final BokehPreviewExtenderImpl mImpl;
    
    VendorBokehPreviewExtender(Preview.Builder paramBuilder)
    {
      super();
      BokehPreviewExtenderImpl localBokehPreviewExtenderImpl = new BokehPreviewExtenderImpl();
      this.mImpl = localBokehPreviewExtenderImpl;
      init(paramBuilder, localBokehPreviewExtenderImpl, ExtensionsManager.EffectMode.BOKEH);
    }
  }
}
