package androidx.camera.extensions;

import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview.Builder;
import androidx.camera.extensions.impl.AutoPreviewExtenderImpl;

public class AutoPreviewExtender
  extends PreviewExtender
{
  private static final String TAG = "AutoPreviewExtender";
  
  private AutoPreviewExtender() {}
  
  public static AutoPreviewExtender create(Preview.Builder paramBuilder)
  {
    if (ExtensionVersion.isExtensionVersionSupported()) {
      try
      {
        paramBuilder = new VendorAutoPreviewExtender(paramBuilder);
        return paramBuilder;
      }
      catch (NoClassDefFoundError paramBuilder)
      {
        Log.d("AutoPreviewExtender", "No auto preview extender found. Falling back to default.");
      }
    }
    return new DefaultAutoPreviewExtender();
  }
  
  static class DefaultAutoPreviewExtender
    extends AutoPreviewExtender
  {
    DefaultAutoPreviewExtender()
    {
      super();
    }
    
    public void enableExtension(CameraSelector paramCameraSelector) {}
    
    public boolean isExtensionAvailable(CameraSelector paramCameraSelector)
    {
      return false;
    }
  }
  
  static class VendorAutoPreviewExtender
    extends AutoPreviewExtender
  {
    private final AutoPreviewExtenderImpl mImpl;
    
    VendorAutoPreviewExtender(Preview.Builder paramBuilder)
    {
      super();
      AutoPreviewExtenderImpl localAutoPreviewExtenderImpl = new AutoPreviewExtenderImpl();
      this.mImpl = localAutoPreviewExtenderImpl;
      init(paramBuilder, localAutoPreviewExtenderImpl, ExtensionsManager.EffectMode.AUTO);
    }
  }
}
