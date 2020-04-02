package androidx.core.os;

import android.os.Build.VERSION;

public class BuildCompat
{
  private BuildCompat() {}
  
  @Deprecated
  public static boolean isAtLeastN()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 24) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastNMR1()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 25) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastO()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 26) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastOMR1()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 27) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastP()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 28) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isAtLeastQ()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 29) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isAtLeastR()
  {
    int i = Build.VERSION.CODENAME.length();
    boolean bool = true;
    if ((i != 1) || (Build.VERSION.CODENAME.charAt(0) < 'R') || (Build.VERSION.CODENAME.charAt(0) > 'Z')) {
      bool = false;
    }
    return bool;
  }
}
