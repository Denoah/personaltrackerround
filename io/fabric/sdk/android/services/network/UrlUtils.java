package io.fabric.sdk.android.services.network;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.TreeMap;

public final class UrlUtils
{
  public static final String UTF8 = "UTF8";
  
  private UrlUtils() {}
  
  public static TreeMap<String, String> getQueryParams(String paramString, boolean paramBoolean)
  {
    TreeMap localTreeMap = new TreeMap();
    if (paramString == null) {
      return localTreeMap;
    }
    paramString = paramString.split("&");
    int i = paramString.length;
    for (int j = 0; j < i; j++)
    {
      String[] arrayOfString = paramString[j].split("=");
      if (arrayOfString.length == 2)
      {
        if (paramBoolean) {
          localTreeMap.put(urlDecode(arrayOfString[0]), urlDecode(arrayOfString[1]));
        } else {
          localTreeMap.put(arrayOfString[0], arrayOfString[1]);
        }
      }
      else if (!TextUtils.isEmpty(arrayOfString[0])) {
        if (paramBoolean) {
          localTreeMap.put(urlDecode(arrayOfString[0]), "");
        } else {
          localTreeMap.put(arrayOfString[0], "");
        }
      }
    }
    return localTreeMap;
  }
  
  public static TreeMap<String, String> getQueryParams(URI paramURI, boolean paramBoolean)
  {
    return getQueryParams(paramURI.getRawQuery(), paramBoolean);
  }
  
  public static String percentEncode(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    paramString = urlEncode(paramString);
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      char c = paramString.charAt(j);
      if (c == '*')
      {
        localStringBuilder.append("%2A");
      }
      else if (c == '+')
      {
        localStringBuilder.append("%20");
      }
      else
      {
        if (c == '%')
        {
          int k = j + 2;
          if ((k < i) && (paramString.charAt(j + 1) == '7') && (paramString.charAt(k) == 'E'))
          {
            localStringBuilder.append('~');
            j = k;
            continue;
          }
        }
        localStringBuilder.append(c);
      }
    }
    return localStringBuilder.toString();
  }
  
  public static String urlDecode(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    try
    {
      paramString = URLDecoder.decode(paramString, "UTF8");
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new RuntimeException(paramString.getMessage(), paramString);
    }
  }
  
  public static String urlEncode(String paramString)
  {
    if (paramString == null) {
      return "";
    }
    try
    {
      paramString = URLEncoder.encode(paramString, "UTF8");
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new RuntimeException(paramString.getMessage(), paramString);
    }
  }
}
