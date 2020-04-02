package androidx.camera.extensions;

import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview.Builder;
import androidx.camera.extensions.impl.BeautyPreviewExtenderImpl;

public class BeautyPreviewExtender
  extends PreviewExtender
{
  private static final String TAG = "BeautyPreviewExtender";
  
  private BeautyPreviewExtender() {}
  
  public static BeautyPreviewExtender create(Preview.Builder paramBuilder)
  {
    if (ExtensionVersion.isExtensionVersionSupported()) {
      try
      {
        paramBuilder = new VendorBeautyPreviewExtender(paramBuilder);
        return paramBuilder;
      }
      catch (NoClassDefFoundError paramBuilder)
      {
        Log.d("BeautyPreviewExtender", "No beauty preview extender found. Falling back to default.");
      }
    }
    return new DefaultBeautyPreviewExtender();
  }
  
  static class DefaultBeautyPreviewExtender
    extends BeautyPreviewExtender
  {
    DefaultBeautyPreviewExtender()
    {
      super();
    }
    
    public void enableExtension(CameraSelector paramCameraSelector) {}
    
    public boolean isExtensionAvailable(CameraSelector paramCameraSelector)
    {
      return false;
    }
  }
  
  static class VendorBeautyPreviewExtender
    extends BeautyPreviewExtender
  {
    private final BeautyPreviewExtenderImpl mImpl;
    
    VendorBeautyPreviewExtender(Preview.Builder paramBuilder)
    {
      super();
      BeautyPreviewExtenderImpl localBeautyPreviewExtenderImpl = new BeautyPreviewExtenderImpl();
      this.mImpl = localBeautyPreviewExtenderImpl;
      init(paramBuilder, localBeautyPreviewExtenderImpl, ExtensionsManager.EffectMode.BEAUTY);
    }
  }
}
