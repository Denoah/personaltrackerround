package com.google.gson.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ISO8601Utils
{
  private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");
  private static final String UTC_ID = "UTC";
  
  public ISO8601Utils() {}
  
  private static boolean checkOffset(String paramString, int paramInt, char paramChar)
  {
    boolean bool;
    if ((paramInt < paramString.length()) && (paramString.charAt(paramInt) == paramChar)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static String format(Date paramDate)
  {
    return format(paramDate, false, TIMEZONE_UTC);
  }
  
  public static String format(Date paramDate, boolean paramBoolean)
  {
    return format(paramDate, paramBoolean, TIMEZONE_UTC);
  }
  
  public static String format(Date paramDate, boolean paramBoolean, TimeZone paramTimeZone)
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(paramTimeZone, Locale.US);
    localGregorianCalendar.setTime(paramDate);
    int i;
    if (paramBoolean) {
      i = 4;
    } else {
      i = 0;
    }
    int j;
    if (paramTimeZone.getRawOffset() == 0) {
      j = 1;
    } else {
      j = 6;
    }
    paramDate = new StringBuilder(19 + i + j);
    padInt(paramDate, localGregorianCalendar.get(1), 4);
    char c1 = '-';
    paramDate.append('-');
    padInt(paramDate, localGregorianCalendar.get(2) + 1, 2);
    paramDate.append('-');
    padInt(paramDate, localGregorianCalendar.get(5), 2);
    paramDate.append('T');
    padInt(paramDate, localGregorianCalendar.get(11), 2);
    paramDate.append(':');
    padInt(paramDate, localGregorianCalendar.get(12), 2);
    paramDate.append(':');
    padInt(paramDate, localGregorianCalendar.get(13), 2);
    if (paramBoolean)
    {
      paramDate.append('.');
      padInt(paramDate, localGregorianCalendar.get(14), 3);
    }
    int k = paramTimeZone.getOffset(localGregorianCalendar.getTimeInMillis());
    if (k != 0)
    {
      j = k / 60000;
      i = Math.abs(j / 60);
      j = Math.abs(j % 60);
      char c2;
      if (k < 0)
      {
        c2 = c1;
      }
      else
      {
        k = 43;
        c2 = k;
      }
      paramDate.append(c2);
      padInt(paramDate, i, 2);
      paramDate.append(':');
      padInt(paramDate, j, 2);
    }
    else
    {
      paramDate.append('Z');
    }
    return paramDate.toString();
  }
  
  private static int indexOfNonDigit(String paramString, int paramInt)
  {
    while (paramInt < paramString.length())
    {
      int i = paramString.charAt(paramInt);
      if ((i >= 48) && (i <= 57)) {
        paramInt++;
      } else {
        return paramInt;
      }
    }
    return paramString.length();
  }
  
  private static void padInt(StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
  {
    String str = Integer.toString(paramInt1);
    for (paramInt1 = paramInt2 - str.length(); paramInt1 > 0; paramInt1--) {
      paramStringBuilder.append('0');
    }
    paramStringBuilder.append(str);
  }
  
  public static Date parse(String paramString, ParsePosition paramParsePosition)
    throws ParseException
  {
    label398:
    Object localObject2;
    try
    {
      int i = paramParsePosition.getIndex();
      int j = i + 4;
      int k = parseInt(paramString, i, j);
      i = j;
      if (checkOffset(paramString, j, '-')) {
        i = j + 1;
      }
      j = i + 2;
      int m = parseInt(paramString, i, j);
      i = j;
      if (checkOffset(paramString, j, '-')) {
        i = j + 1;
      }
      int n = i + 2;
      int i1 = parseInt(paramString, i, n);
      boolean bool = checkOffset(paramString, n, 'T');
      if ((!bool) && (paramString.length() <= n))
      {
        localObject1 = new java/util/GregorianCalendar;
        ((GregorianCalendar)localObject1).<init>(k, m - 1, i1);
        paramParsePosition.setIndex(n);
        return ((Calendar)localObject1).getTime();
      }
      int i2;
      if (bool)
      {
        i = n + 1;
        j = i + 2;
        i2 = parseInt(paramString, i, j);
        i = j;
        if (checkOffset(paramString, j, ':')) {
          i = j + 1;
        }
        j = i + 2;
        i3 = parseInt(paramString, i, j);
        i = j;
        if (checkOffset(paramString, j, ':')) {
          i = j + 1;
        }
        if (paramString.length() > i)
        {
          j = paramString.charAt(i);
          if ((j != 90) && (j != 43) && (j != 45))
          {
            n = i + 2;
            i = parseInt(paramString, i, n);
            j = i;
            if (i > 59)
            {
              j = i;
              if (i < 63) {
                j = 59;
              }
            }
            if (checkOffset(paramString, n, '.'))
            {
              i4 = n + 1;
              n = indexOfNonDigit(paramString, i4 + 1);
              int i5 = Math.min(n, i4 + 3);
              i = parseInt(paramString, i4, i5);
              i4 = i5 - i4;
              if (i4 != 1)
              {
                if (i4 == 2) {
                  i *= 10;
                }
              }
              else {
                i *= 100;
              }
              i4 = i3;
              i3 = i;
              i = i2;
              i2 = i4;
              break label398;
            }
            i = i2;
            int i4 = 0;
            i2 = i3;
            i3 = i4;
            break label398;
          }
        }
        n = i;
        i = i2;
        i2 = i3;
      }
      else
      {
        i = 0;
        i2 = 0;
      }
      int i3 = 0;
      j = 0;
      if (paramString.length() > n)
      {
        char c = paramString.charAt(n);
        if (c == 'Z')
        {
          localObject1 = TIMEZONE_UTC;
          n++;
        }
        else
        {
          if ((c != '+') && (c != '-'))
          {
            localObject1 = new java/lang/IndexOutOfBoundsException;
            localObject2 = new java/lang/StringBuilder;
            ((StringBuilder)localObject2).<init>();
            ((StringBuilder)localObject2).append("Invalid time zone indicator '");
            ((StringBuilder)localObject2).append(c);
            ((StringBuilder)localObject2).append("'");
            ((IndexOutOfBoundsException)localObject1).<init>(((StringBuilder)localObject2).toString());
            throw ((Throwable)localObject1);
          }
          localObject1 = paramString.substring(n);
          if (((String)localObject1).length() < 5)
          {
            localObject2 = new java/lang/StringBuilder;
            ((StringBuilder)localObject2).<init>();
            ((StringBuilder)localObject2).append((String)localObject1);
            ((StringBuilder)localObject2).append("00");
            localObject1 = ((StringBuilder)localObject2).toString();
          }
          n += ((String)localObject1).length();
          if ((!"+0000".equals(localObject1)) && (!"+00:00".equals(localObject1)))
          {
            localObject2 = new java/lang/StringBuilder;
            ((StringBuilder)localObject2).<init>();
            ((StringBuilder)localObject2).append("GMT");
            ((StringBuilder)localObject2).append((String)localObject1);
            localObject2 = ((StringBuilder)localObject2).toString();
            localObject1 = TimeZone.getTimeZone((String)localObject2);
            localObject3 = ((TimeZone)localObject1).getID();
            if ((!((String)localObject3).equals(localObject2)) && (!((String)localObject3).replace(":", "").equals(localObject2)))
            {
              localObject3 = new java/lang/IndexOutOfBoundsException;
              StringBuilder localStringBuilder = new java/lang/StringBuilder;
              localStringBuilder.<init>();
              localStringBuilder.append("Mismatching time zone indicator: ");
              localStringBuilder.append((String)localObject2);
              localStringBuilder.append(" given, resolves to ");
              localStringBuilder.append(((TimeZone)localObject1).getID());
              ((IndexOutOfBoundsException)localObject3).<init>(localStringBuilder.toString());
              throw ((Throwable)localObject3);
            }
          }
          else
          {
            localObject1 = TIMEZONE_UTC;
          }
        }
        localObject2 = new java/util/GregorianCalendar;
        ((GregorianCalendar)localObject2).<init>((TimeZone)localObject1);
        ((Calendar)localObject2).setLenient(false);
        ((Calendar)localObject2).set(1, k);
        ((Calendar)localObject2).set(2, m - 1);
        ((Calendar)localObject2).set(5, i1);
        ((Calendar)localObject2).set(11, i);
        ((Calendar)localObject2).set(12, i2);
        ((Calendar)localObject2).set(13, j);
        ((Calendar)localObject2).set(14, i3);
        paramParsePosition.setIndex(n);
        return ((Calendar)localObject2).getTime();
      }
      Object localObject1 = new java/lang/IllegalArgumentException;
      ((IllegalArgumentException)localObject1).<init>("No time zone indicator");
      throw ((Throwable)localObject1);
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}catch (NumberFormatException localNumberFormatException) {}catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {}
    if (paramString == null)
    {
      paramString = null;
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append('"');
      ((StringBuilder)localObject2).append(paramString);
      ((StringBuilder)localObject2).append('"');
      paramString = ((StringBuilder)localObject2).toString();
    }
    Object localObject3 = localIndexOutOfBoundsException.getMessage();
    if (localObject3 != null)
    {
      localObject2 = localObject3;
      if (!((String)localObject3).isEmpty()) {}
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("(");
      ((StringBuilder)localObject2).append(localIndexOutOfBoundsException.getClass().getName());
      ((StringBuilder)localObject2).append(")");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("Failed to parse date [");
    ((StringBuilder)localObject3).append(paramString);
    ((StringBuilder)localObject3).append("]: ");
    ((StringBuilder)localObject3).append((String)localObject2);
    paramString = new ParseException(((StringBuilder)localObject3).toString(), paramParsePosition.getIndex());
    paramString.initCause(localIndexOutOfBoundsException);
    throw paramString;
  }
  
  private static int parseInt(String paramString, int paramInt1, int paramInt2)
    throws NumberFormatException
  {
    if ((paramInt1 >= 0) && (paramInt2 <= paramString.length()) && (paramInt1 <= paramInt2))
    {
      int i;
      int j;
      StringBuilder localStringBuilder;
      if (paramInt1 < paramInt2)
      {
        i = paramInt1 + 1;
        j = Character.digit(paramString.charAt(paramInt1), 10);
        if (j >= 0)
        {
          j = -j;
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Invalid number: ");
          localStringBuilder.append(paramString.substring(paramInt1, paramInt2));
          throw new NumberFormatException(localStringBuilder.toString());
        }
      }
      else
      {
        j = 0;
        i = paramInt1;
      }
      while (i < paramInt2)
      {
        int k = Character.digit(paramString.charAt(i), 10);
        if (k >= 0)
        {
          j = j * 10 - k;
          i++;
        }
        else
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Invalid number: ");
          localStringBuilder.append(paramString.substring(paramInt1, paramInt2));
          throw new NumberFormatException(localStringBuilder.toString());
        }
      }
      return -j;
    }
    throw new NumberFormatException(paramString);
  }
}
