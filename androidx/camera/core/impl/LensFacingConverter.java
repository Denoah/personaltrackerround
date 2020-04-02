package androidx.camera.core.impl;

public class LensFacingConverter
{
  private LensFacingConverter() {}
  
  public static String nameOf(int paramInt)
  {
    if (paramInt != 0)
    {
      if (paramInt == 1) {
        return "BACK";
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unknown lens facing ");
      localStringBuilder.append(paramInt);
      throw new IllegalArgumentException(localStringBuilder.toString());
    }
    return "FRONT";
  }
  
  public static int valueOf(String paramString)
  {
    if (paramString != null)
    {
      int i = -1;
      int j = paramString.hashCode();
      if (j != 2030823)
      {
        if ((j == 67167753) && (paramString.equals("FRONT"))) {
          i = 0;
        }
      }
      else if (paramString.equals("BACK")) {
        i = 1;
      }
      if (i != 0)
      {
        if (i == 1) {
          return 1;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Unknown len facing name ");
        localStringBuilder.append(paramString);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      return 0;
    }
    throw new NullPointerException("name cannot be null");
  }
  
  public static Integer[] values()
  {
    return new Integer[] { Integer.valueOf(0), Integer.valueOf(1) };
  }
}
