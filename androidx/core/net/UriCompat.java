package androidx.core.net;

import android.net.Uri;

public final class UriCompat
{
  private UriCompat() {}
  
  public static String toSafeString(Uri paramUri)
  {
    String str1 = paramUri.getScheme();
    String str2 = paramUri.getSchemeSpecificPart();
    Object localObject = str2;
    if (str1 != null) {
      if ((!str1.equalsIgnoreCase("tel")) && (!str1.equalsIgnoreCase("sip")) && (!str1.equalsIgnoreCase("sms")) && (!str1.equalsIgnoreCase("smsto")) && (!str1.equalsIgnoreCase("mailto")) && (!str1.equalsIgnoreCase("nfc")))
      {
        if ((!str1.equalsIgnoreCase("http")) && (!str1.equalsIgnoreCase("https")) && (!str1.equalsIgnoreCase("ftp")))
        {
          localObject = str2;
          if (!str1.equalsIgnoreCase("rtsp")) {}
        }
        else
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("//");
          localObject = paramUri.getHost();
          str2 = "";
          if (localObject != null) {
            localObject = paramUri.getHost();
          } else {
            localObject = "";
          }
          localStringBuilder.append((String)localObject);
          localObject = str2;
          if (paramUri.getPort() != -1)
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append(":");
            ((StringBuilder)localObject).append(paramUri.getPort());
            localObject = ((StringBuilder)localObject).toString();
          }
          localStringBuilder.append((String)localObject);
          localStringBuilder.append("/...");
          localObject = localStringBuilder.toString();
        }
      }
      else
      {
        paramUri = new StringBuilder(64);
        paramUri.append(str1);
        paramUri.append(':');
        if (str2 != null) {
          for (int i = 0; i < str2.length(); i++)
          {
            char c = str2.charAt(i);
            if ((c != '-') && (c != '@') && (c != '.')) {
              paramUri.append('x');
            } else {
              paramUri.append(c);
            }
          }
        }
        return paramUri.toString();
      }
    }
    paramUri = new StringBuilder(64);
    if (str1 != null)
    {
      paramUri.append(str1);
      paramUri.append(':');
    }
    if (localObject != null) {
      paramUri.append((String)localObject);
    }
    return paramUri.toString();
  }
}
