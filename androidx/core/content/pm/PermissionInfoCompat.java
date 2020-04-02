package androidx.core.content.pm;

import android.content.pm.PermissionInfo;
import android.os.Build.VERSION;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PermissionInfoCompat
{
  private PermissionInfoCompat() {}
  
  public static int getProtection(PermissionInfo paramPermissionInfo)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return paramPermissionInfo.getProtection();
    }
    return paramPermissionInfo.protectionLevel & 0xF;
  }
  
  public static int getProtectionFlags(PermissionInfo paramPermissionInfo)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return paramPermissionInfo.getProtectionFlags();
    }
    return paramPermissionInfo.protectionLevel & 0xFFFFFFF0;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Protection {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ProtectionFlags {}
}
