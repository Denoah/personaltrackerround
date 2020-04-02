package androidx.camera.extensions;

import android.util.Log;
import androidx.camera.extensions.impl.ExtensionVersionImpl;

abstract class ExtensionVersion
{
  private static final String TAG = "ExtenderVersion";
  private static volatile ExtensionVersion sExtensionVersion;
  
  ExtensionVersion() {}
  
  private static ExtensionVersion getInstance()
  {
    if (sExtensionVersion != null) {
      return sExtensionVersion;
    }
    try
    {
      Object localObject1 = sExtensionVersion;
      if (localObject1 == null) {
        try
        {
          localObject1 = new androidx/camera/extensions/ExtensionVersion$VendorExtenderVersioning;
          ((VendorExtenderVersioning)localObject1).<init>();
          sExtensionVersion = (ExtensionVersion)localObject1;
        }
        catch (NoClassDefFoundError localNoClassDefFoundError)
        {
          Log.d("ExtenderVersion", "No versioning extender found. Falling back to default.");
          DefaultExtenderVersioning localDefaultExtenderVersioning = new androidx/camera/extensions/ExtensionVersion$DefaultExtenderVersioning;
          localDefaultExtenderVersioning.<init>();
          sExtensionVersion = localDefaultExtenderVersioning;
        }
      }
      return sExtensionVersion;
    }
    finally {}
  }
  
  static Version getRuntimeVersion()
  {
    return getInstance().getVersionObject();
  }
  
  static boolean isExtensionVersionSupported()
  {
    boolean bool;
    if (getInstance().getVersionObject() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  abstract Version getVersionObject();
  
  private static class DefaultExtenderVersioning
    extends ExtensionVersion
  {
    DefaultExtenderVersioning() {}
    
    Version getVersionObject()
    {
      return null;
    }
  }
  
  private static class VendorExtenderVersioning
    extends ExtensionVersion
  {
    private ExtensionVersionImpl mImpl;
    private Version mRuntimeVersion;
    
    VendorExtenderVersioning()
    {
      Object localObject = new ExtensionVersionImpl();
      this.mImpl = ((ExtensionVersionImpl)localObject);
      localObject = Version.parse(((ExtensionVersionImpl)localObject).checkApiVersion(VersionName.getCurrentVersion().toVersionString()));
      if ((localObject != null) && (VersionName.getCurrentVersion().getVersion().getMajor() == ((Version)localObject).getMajor())) {
        this.mRuntimeVersion = ((Version)localObject);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Selected vendor runtime: ");
      ((StringBuilder)localObject).append(this.mRuntimeVersion);
      Log.d("ExtenderVersion", ((StringBuilder)localObject).toString());
    }
    
    Version getVersionObject()
    {
      return this.mRuntimeVersion;
    }
  }
}
