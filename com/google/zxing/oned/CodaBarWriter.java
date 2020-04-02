package com.google.zxing.oned;

public final class CodaBarWriter
  extends OneDimensionalCodeWriter
{
  private static final char[] ALT_START_END_CHARS = { 84, 78, 42, 69 };
  private static final char[] CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED = { 47, 58, 43, 46 };
  private static final char DEFAULT_GUARD = (char)arrayOfChar[0];
  private static final char[] START_END_CHARS;
  
  static
  {
    char[] arrayOfChar = new char[4];
    arrayOfChar[0] = 65;
    arrayOfChar[1] = 66;
    arrayOfChar[2] = 67;
    arrayOfChar[3] = 68;
    arrayOfChar;
    START_END_CHARS = arrayOfChar;
  }
  
  public CodaBarWriter() {}
  
  public boolean[] encode(String paramString)
  {
    boolean bool4;
    if (paramString.length() < 2)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(DEFAULT_GUARD);
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(DEFAULT_GUARD);
      paramString = ((StringBuilder)localObject).toString();
    }
    else
    {
      char c1 = Character.toUpperCase(paramString.charAt(0));
      char c2 = Character.toUpperCase(paramString.charAt(paramString.length() - 1));
      boolean bool1 = CodaBarReader.arrayContains(START_END_CHARS, c1);
      boolean bool2 = CodaBarReader.arrayContains(START_END_CHARS, c2);
      boolean bool3 = CodaBarReader.arrayContains(ALT_START_END_CHARS, c1);
      bool4 = CodaBarReader.arrayContains(ALT_START_END_CHARS, c2);
      if (bool1)
      {
        if (!bool2)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Invalid start/end guards: ");
          ((StringBuilder)localObject).append(paramString);
          throw new IllegalArgumentException(((StringBuilder)localObject).toString());
        }
      }
      else if (bool3)
      {
        if (!bool4)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Invalid start/end guards: ");
          ((StringBuilder)localObject).append(paramString);
          throw new IllegalArgumentException(((StringBuilder)localObject).toString());
        }
      }
      else
      {
        if ((bool2) || (bool4)) {
          break label644;
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(DEFAULT_GUARD);
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(DEFAULT_GUARD);
        paramString = ((StringBuilder)localObject).toString();
      }
    }
    int i = 20;
    for (int j = 1; j < paramString.length() - 1; j++) {
      if ((!Character.isDigit(paramString.charAt(j))) && (paramString.charAt(j) != '-') && (paramString.charAt(j) != '$'))
      {
        if (CodaBarReader.arrayContains(CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED, paramString.charAt(j)))
        {
          i += 10;
        }
        else
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("Cannot encode : '");
          ((StringBuilder)localObject).append(paramString.charAt(j));
          ((StringBuilder)localObject).append('\'');
          throw new IllegalArgumentException(((StringBuilder)localObject).toString());
        }
      }
      else {
        i += 9;
      }
    }
    Object localObject = new boolean[i + (paramString.length() - 1)];
    int k = 0;
    for (j = k; k < paramString.length(); j = i)
    {
      int m = Character.toUpperCase(paramString.charAt(k));
      if (k != 0)
      {
        i = m;
        if (k != paramString.length() - 1) {}
      }
      else if (m != 42)
      {
        if (m != 69)
        {
          if (m != 78)
          {
            if (m != 84) {
              i = m;
            } else {
              i = 65;
            }
          }
          else {
            i = 66;
          }
        }
        else {
          i = 68;
        }
      }
      else
      {
        i = 67;
      }
      for (m = 0; m < CodaBarReader.ALPHABET.length; m++) {
        if (i == CodaBarReader.ALPHABET[m])
        {
          m = CodaBarReader.CHARACTER_ENCODINGS[m];
          break label537;
        }
      }
      m = 0;
      label537:
      int n = 0;
      i = n;
      bool4 = true;
      while (n < 7)
      {
        localObject[j] = bool4;
        j++;
        if (((m >> 6 - n & 0x1) != 0) && (i != 1))
        {
          i++;
        }
        else
        {
          bool4 ^= true;
          n++;
          i = 0;
        }
      }
      i = j;
      if (k < paramString.length() - 1)
      {
        localObject[j] = 0;
        i = j + 1;
      }
      k++;
    }
    return localObject;
    label644:
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Invalid start/end guards: ");
    ((StringBuilder)localObject).append(paramString);
    throw new IllegalArgumentException(((StringBuilder)localObject).toString());
  }
}
