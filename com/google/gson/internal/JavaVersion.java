package com.google.gson.internal;

public final class JavaVersion
{
  private static final int majorJavaVersion = ;
  
  private JavaVersion() {}
  
  private static int determineMajorJavaVersion()
  {
    return getMajorJavaVersion(System.getProperty("java.version"));
  }
  
  private static int extractBeginningInt(String paramString)
  {
    try
    {
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      for (int i = 0; i < paramString.length(); i++)
      {
        char c = paramString.charAt(i);
        if (!Character.isDigit(c)) {
          break;
        }
        localStringBuilder.append(c);
      }
      i = Integer.parseInt(localStringBuilder.toString());
      return i;
    }
    catch (NumberFormatException paramString) {}
    return -1;
  }
  
  public static int getMajorJavaVersion()
  {
    return majorJavaVersion;
  }
  
  static int getMajorJavaVersion(String paramString)
  {
    int i = parseDotted(paramString);
    int j = i;
    if (i == -1) {
      j = extractBeginningInt(paramString);
    }
    if (j == -1) {
      return 6;
    }
    return j;
  }
  
  public static boolean isJava9OrLater()
  {
    boolean bool;
    if (majorJavaVersion >= 9) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static int parseDotted(String paramString)
  {
    try
    {
      paramString = paramString.split("[._]");
      int i = Integer.parseInt(paramString[0]);
      if ((i == 1) && (paramString.length > 1))
      {
        i = Integer.parseInt(paramString[1]);
        return i;
      }
      return i;
    }
    catch (NumberFormatException paramString) {}
    return -1;
  }
}
