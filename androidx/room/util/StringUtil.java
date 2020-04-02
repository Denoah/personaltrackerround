package androidx.room.util;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtil
{
  public static final String[] EMPTY_STRING_ARRAY = new String[0];
  
  private StringUtil() {}
  
  public static void appendPlaceholders(StringBuilder paramStringBuilder, int paramInt)
  {
    for (int i = 0; i < paramInt; i++)
    {
      paramStringBuilder.append("?");
      if (i < paramInt - 1) {
        paramStringBuilder.append(",");
      }
    }
  }
  
  public static String joinIntoString(List<Integer> paramList)
  {
    if (paramList == null) {
      return null;
    }
    int i = paramList.size();
    if (i == 0) {
      return "";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    for (int j = 0; j < i; j++)
    {
      localStringBuilder.append(Integer.toString(((Integer)paramList.get(j)).intValue()));
      if (j < i - 1) {
        localStringBuilder.append(",");
      }
    }
    return localStringBuilder.toString();
  }
  
  public static StringBuilder newStringBuilder()
  {
    return new StringBuilder();
  }
  
  public static List<Integer> splitToIntList(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    paramString = new StringTokenizer(paramString, ",");
    while (paramString.hasMoreElements())
    {
      String str = paramString.nextToken();
      try
      {
        localArrayList.add(Integer.valueOf(Integer.parseInt(str)));
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Log.e("ROOM", "Malformed integer list", localNumberFormatException);
      }
    }
    return localArrayList;
  }
}
