package androidx.core.util;

import java.io.PrintWriter;

public final class TimeUtils
{
  public static final int HUNDRED_DAY_FIELD_LEN = 19;
  private static final int SECONDS_PER_DAY = 86400;
  private static final int SECONDS_PER_HOUR = 3600;
  private static final int SECONDS_PER_MINUTE = 60;
  private static char[] sFormatStr = new char[24];
  private static final Object sFormatSync = new Object();
  
  private TimeUtils() {}
  
  private static int accumField(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    if ((paramInt1 <= 99) && ((!paramBoolean) || (paramInt3 < 3)))
    {
      if ((paramInt1 <= 9) && ((!paramBoolean) || (paramInt3 < 2)))
      {
        if ((!paramBoolean) && (paramInt1 <= 0)) {
          return 0;
        }
        return paramInt2 + 1;
      }
      return paramInt2 + 2;
    }
    return paramInt2 + 3;
  }
  
  public static void formatDuration(long paramLong1, long paramLong2, PrintWriter paramPrintWriter)
  {
    if (paramLong1 == 0L)
    {
      paramPrintWriter.print("--");
      return;
    }
    formatDuration(paramLong1 - paramLong2, paramPrintWriter, 0);
  }
  
  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter)
  {
    formatDuration(paramLong, paramPrintWriter, 0);
  }
  
  public static void formatDuration(long paramLong, PrintWriter paramPrintWriter, int paramInt)
  {
    synchronized (sFormatSync)
    {
      paramInt = formatDurationLocked(paramLong, paramInt);
      String str = new java/lang/String;
      str.<init>(sFormatStr, 0, paramInt);
      paramPrintWriter.print(str);
      return;
    }
  }
  
  public static void formatDuration(long paramLong, StringBuilder paramStringBuilder)
  {
    synchronized (sFormatSync)
    {
      int i = formatDurationLocked(paramLong, 0);
      paramStringBuilder.append(sFormatStr, 0, i);
      return;
    }
  }
  
  private static int formatDurationLocked(long paramLong, int paramInt)
  {
    if (sFormatStr.length < paramInt) {
      sFormatStr = new char[paramInt];
    }
    char[] arrayOfChar = sFormatStr;
    boolean bool1 = paramLong < 0L;
    if (!bool1)
    {
      while (paramInt - 1 > 0) {
        arrayOfChar[0] = ((char)32);
      }
      arrayOfChar[0] = ((char)48);
      return 1;
    }
    if (bool1)
    {
      j = 43;
    }
    else
    {
      j = 45;
      paramLong = -paramLong;
    }
    int k = (int)(paramLong % 1000L);
    int i = (int)Math.floor(paramLong / 1000L);
    if (i > 86400)
    {
      m = i / 86400;
      i -= 86400 * m;
    }
    else
    {
      m = 0;
    }
    if (i > 3600)
    {
      n = i / 3600;
      i -= n * 3600;
    }
    else
    {
      n = 0;
    }
    int i1;
    int i2;
    if (i > 60)
    {
      i1 = i / 60;
      i2 = i - i1 * 60;
    }
    else
    {
      i1 = 0;
      i2 = i;
    }
    boolean bool2;
    if (paramInt != 0)
    {
      i = accumField(m, 1, false, 0);
      if (i > 0) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      i += accumField(n, 1, bool2, 2);
      if (i > 0) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      i += accumField(i1, 1, bool2, 2);
      if (i > 0) {
        bool2 = true;
      } else {
        bool2 = false;
      }
      int i3 = i + accumField(i2, 1, bool2, 2);
      if (i3 > 0) {
        i = 3;
      } else {
        i = 0;
      }
      i3 += accumField(k, 2, true, i) + 1;
      i = 0;
      for (;;)
      {
        i4 = i;
        if (i3 >= paramInt) {
          break;
        }
        arrayOfChar[i] = ((char)32);
        i++;
        i3++;
      }
    }
    int i4 = 0;
    arrayOfChar[i4] = ((char)j);
    int j = i4 + 1;
    if (paramInt != 0) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    int m = printField(arrayOfChar, m, 'd', j, false, 0);
    if (m != j) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    if (paramInt != 0) {
      i = 2;
    } else {
      i = 0;
    }
    int n = printField(arrayOfChar, n, 'h', m, bool2, i);
    if (n != j) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    if (paramInt != 0) {
      i = 2;
    } else {
      i = 0;
    }
    n = printField(arrayOfChar, i1, 'm', n, bool2, i);
    if (n != j) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    if (paramInt != 0) {
      i = 2;
    } else {
      i = 0;
    }
    i = printField(arrayOfChar, i2, 's', n, bool2, i);
    if ((paramInt != 0) && (i != j)) {
      paramInt = 3;
    } else {
      paramInt = 0;
    }
    paramInt = printField(arrayOfChar, k, 'm', i, true, paramInt);
    arrayOfChar[paramInt] = ((char)115);
    return paramInt + 1;
  }
  
  private static int printField(char[] paramArrayOfChar, int paramInt1, char paramChar, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    int i;
    if (!paramBoolean)
    {
      i = paramInt2;
      if (paramInt1 <= 0) {}
    }
    else
    {
      int j;
      if (((paramBoolean) && (paramInt3 >= 3)) || (paramInt1 > 99))
      {
        j = paramInt1 / 100;
        paramArrayOfChar[paramInt2] = ((char)(char)(j + 48));
        i = paramInt2 + 1;
        paramInt1 -= j * 100;
      }
      else
      {
        i = paramInt2;
      }
      if (((!paramBoolean) || (paramInt3 < 2)) && (paramInt1 <= 9))
      {
        j = i;
        paramInt3 = paramInt1;
        if (paramInt2 == i) {}
      }
      else
      {
        paramInt2 = paramInt1 / 10;
        paramArrayOfChar[i] = ((char)(char)(paramInt2 + 48));
        j = i + 1;
        paramInt3 = paramInt1 - paramInt2 * 10;
      }
      paramArrayOfChar[j] = ((char)(char)(paramInt3 + 48));
      paramInt1 = j + 1;
      paramArrayOfChar[paramInt1] = ((char)paramChar);
      i = paramInt1 + 1;
    }
    return i;
  }
}
