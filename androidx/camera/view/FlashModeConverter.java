package androidx.camera.view;

final class FlashModeConverter
{
  private FlashModeConverter() {}
  
  public static String nameOf(int paramInt)
  {
    if (paramInt != 0)
    {
      if (paramInt != 1)
      {
        if (paramInt == 2) {
          return "OFF";
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Unknown flash mode ");
        localStringBuilder.append(paramInt);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      return "ON";
    }
    return "AUTO";
  }
  
  public static int valueOf(String paramString)
  {
    if (paramString != null)
    {
      int i = -1;
      int j = paramString.hashCode();
      if (j != 2527)
      {
        if (j != 78159)
        {
          if ((j == 2020783) && (paramString.equals("AUTO"))) {
            i = 0;
          }
        }
        else if (paramString.equals("OFF")) {
          i = 2;
        }
      }
      else if (paramString.equals("ON")) {
        i = 1;
      }
      if (i != 0)
      {
        if (i != 1)
        {
          if (i == 2) {
            return 2;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Unknown flash mode name ");
          localStringBuilder.append(paramString);
          throw new IllegalArgumentException(localStringBuilder.toString());
        }
        return 1;
      }
      return 0;
    }
    throw new NullPointerException("name cannot be null");
  }
}
