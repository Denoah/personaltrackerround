package com.google.gson.stream;

import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.bind.JsonTreeReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class JsonReader
  implements Closeable
{
  private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
  private static final char[] NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();
  private static final int NUMBER_CHAR_DECIMAL = 3;
  private static final int NUMBER_CHAR_DIGIT = 2;
  private static final int NUMBER_CHAR_EXP_DIGIT = 7;
  private static final int NUMBER_CHAR_EXP_E = 5;
  private static final int NUMBER_CHAR_EXP_SIGN = 6;
  private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
  private static final int NUMBER_CHAR_NONE = 0;
  private static final int NUMBER_CHAR_SIGN = 1;
  private static final int PEEKED_BEGIN_ARRAY = 3;
  private static final int PEEKED_BEGIN_OBJECT = 1;
  private static final int PEEKED_BUFFERED = 11;
  private static final int PEEKED_DOUBLE_QUOTED = 9;
  private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
  private static final int PEEKED_END_ARRAY = 4;
  private static final int PEEKED_END_OBJECT = 2;
  private static final int PEEKED_EOF = 17;
  private static final int PEEKED_FALSE = 6;
  private static final int PEEKED_LONG = 15;
  private static final int PEEKED_NONE = 0;
  private static final int PEEKED_NULL = 7;
  private static final int PEEKED_NUMBER = 16;
  private static final int PEEKED_SINGLE_QUOTED = 8;
  private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
  private static final int PEEKED_TRUE = 5;
  private static final int PEEKED_UNQUOTED = 10;
  private static final int PEEKED_UNQUOTED_NAME = 14;
  private final char[] buffer = new char['?'];
  private final Reader in;
  private boolean lenient = false;
  private int limit = 0;
  private int lineNumber = 0;
  private int lineStart = 0;
  private int[] pathIndices;
  private String[] pathNames;
  int peeked = 0;
  private long peekedLong;
  private int peekedNumberLength;
  private String peekedString;
  private int pos = 0;
  private int[] stack;
  private int stackSize;
  
  static
  {
    JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess()
    {
      public void promoteNameToValue(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if ((paramAnonymousJsonReader instanceof JsonTreeReader))
        {
          ((JsonTreeReader)paramAnonymousJsonReader).promoteNameToValue();
          return;
        }
        int i = paramAnonymousJsonReader.peeked;
        int j = i;
        if (i == 0) {
          j = paramAnonymousJsonReader.doPeek();
        }
        if (j == 13)
        {
          paramAnonymousJsonReader.peeked = 9;
        }
        else if (j == 12)
        {
          paramAnonymousJsonReader.peeked = 8;
        }
        else
        {
          if (j != 14) {
            break label74;
          }
          paramAnonymousJsonReader.peeked = 10;
        }
        return;
        label74:
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Expected a name but was ");
        localStringBuilder.append(paramAnonymousJsonReader.peek());
        localStringBuilder.append(paramAnonymousJsonReader.locationString());
        throw new IllegalStateException(localStringBuilder.toString());
      }
    };
  }
  
  public JsonReader(Reader paramReader)
  {
    int[] arrayOfInt = new int[32];
    this.stack = arrayOfInt;
    this.stackSize = 0;
    this.stackSize = (0 + 1);
    arrayOfInt[0] = 6;
    this.pathNames = new String[32];
    this.pathIndices = new int[32];
    if (paramReader != null)
    {
      this.in = paramReader;
      return;
    }
    throw new NullPointerException("in == null");
  }
  
  private void checkLenient()
    throws IOException
  {
    if (this.lenient) {
      return;
    }
    throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
  }
  
  private void consumeNonExecutePrefix()
    throws IOException
  {
    nextNonWhitespace(true);
    int i = this.pos - 1;
    this.pos = i;
    char[] arrayOfChar = NON_EXECUTE_PREFIX;
    if ((i + arrayOfChar.length > this.limit) && (!fillBuffer(arrayOfChar.length))) {
      return;
    }
    for (i = 0;; i++)
    {
      arrayOfChar = NON_EXECUTE_PREFIX;
      if (i >= arrayOfChar.length) {
        break;
      }
      if (this.buffer[(this.pos + i)] != arrayOfChar[i]) {
        return;
      }
    }
    this.pos += arrayOfChar.length;
  }
  
  private boolean fillBuffer(int paramInt)
    throws IOException
  {
    char[] arrayOfChar = this.buffer;
    int i = this.lineStart;
    int j = this.pos;
    this.lineStart = (i - j);
    i = this.limit;
    if (i != j)
    {
      i -= j;
      this.limit = i;
      System.arraycopy(arrayOfChar, j, arrayOfChar, 0, i);
    }
    else
    {
      this.limit = 0;
    }
    this.pos = 0;
    do
    {
      Reader localReader = this.in;
      j = this.limit;
      j = localReader.read(arrayOfChar, j, arrayOfChar.length - j);
      if (j == -1) {
        break;
      }
      i = this.limit + j;
      this.limit = i;
      j = paramInt;
      if (this.lineNumber == 0)
      {
        int k = this.lineStart;
        j = paramInt;
        if (k == 0)
        {
          j = paramInt;
          if (i > 0)
          {
            j = paramInt;
            if (arrayOfChar[0] == 65279)
            {
              this.pos += 1;
              this.lineStart = (k + 1);
              j = paramInt + 1;
            }
          }
        }
      }
      paramInt = j;
    } while (this.limit < j);
    return true;
    return false;
  }
  
  private boolean isLiteral(char paramChar)
    throws IOException
  {
    if ((paramChar != '\t') && (paramChar != '\n') && (paramChar != '\f') && (paramChar != '\r') && (paramChar != ' '))
    {
      if (paramChar != '#')
      {
        if (paramChar == ',') {
          break label110;
        }
        if ((paramChar != '/') && (paramChar != '='))
        {
          if ((paramChar == '{') || (paramChar == '}') || (paramChar == ':')) {
            break label110;
          }
          if (paramChar == ';') {}
        }
      }
      switch (paramChar)
      {
      default: 
        return true;
      case '\\': 
        checkLenient();
      }
    }
    label110:
    return false;
  }
  
  private int nextNonWhitespace(boolean paramBoolean)
    throws IOException
  {
    Object localObject = this.buffer;
    int i = this.pos;
    int j = this.limit;
    for (;;)
    {
      int k = i;
      int m = j;
      if (i == j)
      {
        this.pos = i;
        if (!fillBuffer(1))
        {
          if (!paramBoolean) {
            return -1;
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("End of input");
          ((StringBuilder)localObject).append(locationString());
          throw new EOFException(((StringBuilder)localObject).toString());
        }
        k = this.pos;
        m = this.limit;
      }
      i = k + 1;
      j = localObject[k];
      if (j == 10)
      {
        this.lineNumber += 1;
        this.lineStart = i;
      }
      else if ((j != 32) && (j != 13) && (j != 9))
      {
        if (j == 47)
        {
          this.pos = i;
          if (i == m)
          {
            this.pos = (i - 1);
            boolean bool = fillBuffer(2);
            this.pos += 1;
            if (!bool) {
              return j;
            }
          }
          checkLenient();
          m = this.pos;
          i = localObject[m];
          if (i != 42)
          {
            if (i != 47) {
              return j;
            }
            this.pos = (m + 1);
            skipToEndOfLine();
            i = this.pos;
            j = this.limit;
            continue;
          }
          this.pos = (m + 1);
          if (skipTo("*/"))
          {
            i = this.pos + 2;
            j = this.limit;
            continue;
          }
          throw syntaxError("Unterminated comment");
        }
        if (j == 35)
        {
          this.pos = i;
          checkLenient();
          skipToEndOfLine();
          i = this.pos;
          j = this.limit;
          continue;
        }
        this.pos = i;
        return j;
      }
      j = m;
    }
  }
  
  private String nextQuotedValue(char paramChar)
    throws IOException
  {
    char[] arrayOfChar = this.buffer;
    Object localObject2;
    for (Object localObject1 = null;; localObject1 = localObject2)
    {
      char c1 = this.pos;
      int i = this.limit;
      char c2 = c1;
      char c3;
      for (;;)
      {
        c3 = c2;
        if (c3 >= i) {
          break label210;
        }
        c2 = c3 + '\001';
        c3 = arrayOfChar[c3];
        if (c3 == paramChar)
        {
          this.pos = c2;
          paramChar = c2 - c1 - 1;
          if (localObject1 == null) {
            return new String(arrayOfChar, c1, paramChar);
          }
          localObject1.append(arrayOfChar, c1, paramChar);
          return localObject1.toString();
        }
        if (c3 == '\\')
        {
          this.pos = c2;
          c2 = c2 - c1 - 1;
          localObject2 = localObject1;
          if (localObject1 == null) {
            localObject2 = new StringBuilder(Math.max((c2 + '\001') * 2, 16));
          }
          ((StringBuilder)localObject2).append(arrayOfChar, c1, c2);
          ((StringBuilder)localObject2).append(readEscapeCharacter());
          c1 = this.pos;
          i = this.limit;
          localObject1 = localObject2;
          break;
        }
        if (c3 == '\n')
        {
          this.lineNumber += 1;
          this.lineStart = c2;
        }
      }
      label210:
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = new StringBuilder(Math.max((c3 - c1) * 2, 16));
      }
      ((StringBuilder)localObject2).append(arrayOfChar, c1, c3 - c1);
      this.pos = c3;
      if (!fillBuffer(1)) {
        break;
      }
    }
    throw syntaxError("Unterminated string");
  }
  
  private String nextUnquotedValue()
    throws IOException
  {
    int i = 0;
    Object localObject1 = null;
    label165:
    label187:
    Object localObject2;
    label193:
    do
    {
      j = 0;
      do
      {
        for (;;)
        {
          int k = this.pos;
          if (k + j >= this.limit) {
            break label165;
          }
          k = this.buffer[(k + j)];
          if ((k == 9) || (k == 10) || (k == 12) || (k == 13) || (k == 32)) {
            break label187;
          }
          if (k == 35) {
            break;
          }
          if (k == 44) {
            break label187;
          }
          if ((k == 47) || (k == 61)) {
            break;
          }
          if ((k == 123) || (k == 125) || (k == 58)) {
            break label187;
          }
          if (k == 59) {
            break;
          }
          switch (k)
          {
          default: 
            j++;
          }
        }
        checkLenient();
        break;
        if (j >= this.buffer.length) {
          break label193;
        }
      } while (fillBuffer(j + 1));
      localObject2 = localObject1;
      break;
      localObject2 = localObject1;
      if (localObject1 == null) {
        localObject2 = new StringBuilder(Math.max(j, 16));
      }
      ((StringBuilder)localObject2).append(this.buffer, this.pos, j);
      this.pos += j;
      localObject1 = localObject2;
    } while (fillBuffer(1));
    int j = i;
    if (localObject2 == null)
    {
      localObject2 = new String(this.buffer, this.pos, j);
    }
    else
    {
      ((StringBuilder)localObject2).append(this.buffer, this.pos, j);
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    this.pos += j;
    return localObject2;
  }
  
  private int peekKeyword()
    throws IOException
  {
    int i = this.buffer[this.pos];
    String str1;
    String str2;
    if ((i != 116) && (i != 84))
    {
      if ((i != 102) && (i != 70))
      {
        if ((i != 110) && (i != 78)) {
          return 0;
        }
        i = 7;
        str1 = "null";
        str2 = "NULL";
      }
      else
      {
        i = 6;
        str1 = "false";
        str2 = "FALSE";
      }
    }
    else
    {
      i = 5;
      str1 = "true";
      str2 = "TRUE";
    }
    int j = str1.length();
    for (int k = 1; k < j; k++)
    {
      if ((this.pos + k >= this.limit) && (!fillBuffer(k + 1))) {
        return 0;
      }
      int m = this.buffer[(this.pos + k)];
      if ((m != str1.charAt(k)) && (m != str2.charAt(k))) {
        return 0;
      }
    }
    if (((this.pos + j < this.limit) || (fillBuffer(j + 1))) && (isLiteral(this.buffer[(this.pos + j)]))) {
      return 0;
    }
    this.pos += j;
    this.peeked = i;
    return i;
  }
  
  private int peekNumber()
    throws IOException
  {
    char[] arrayOfChar = this.buffer;
    int i = this.pos;
    int j = this.limit;
    int m = 1;
    int n = 0;
    int i1 = n;
    int i2 = i1;
    long l1 = 0L;
    for (;;)
    {
      int i3 = i;
      int i4 = j;
      if (i + n == j)
      {
        if (n == arrayOfChar.length) {
          return 0;
        }
        if (fillBuffer(n + 1))
        {
          i3 = this.pos;
          i4 = this.limit;
        }
      }
      else
      {
        char c = arrayOfChar[(i3 + n)];
        j = 3;
        if (c == '+') {
          break label496;
        }
        if ((c == 'E') || (c == 'e')) {
          break label473;
        }
        if (c == '-') {
          break label441;
        }
        if (c == '.') {
          break label427;
        }
        if ((c >= '0') && (c <= '9'))
        {
          long l2;
          if ((i1 != 1) && (i1 != 0))
          {
            boolean bool;
            if (i1 == 2)
            {
              if (l1 == 0L) {
                return 0;
              }
              l2 = 10L * l1 - (c - '0');
              bool = l1 < -922337203685477580L;
              if ((!bool) && ((bool) || (l2 >= l1))) {
                bool = false;
              } else {
                bool = true;
              }
              i = m & bool;
              bool = i1;
            }
            else
            {
              if (i1 == 3)
              {
                i1 = 4;
                break label298;
              }
              if (i1 != 5)
              {
                bool = i1;
                i = m;
                l2 = l1;
                if (i1 != 6) {}
              }
              else
              {
                i1 = 7;
                break label298;
              }
            }
          }
          else
          {
            l2 = -(c - '0');
            k = 2;
            i = m;
          }
          l1 = l2;
          m = i;
          i1 = k;
          label298:
          break label508;
        }
        if (isLiteral(c)) {
          break label425;
        }
      }
      if ((i1 == 2) && (m != 0) && ((l1 != Long.MIN_VALUE) || (i2 != 0)) && ((l1 != 0L) || (i2 == 0)))
      {
        if (i2 == 0) {
          l1 = -l1;
        }
        this.peekedLong = l1;
        this.pos += n;
        this.peeked = 15;
        return 15;
      }
      if ((i1 != 2) && (i1 != 4) && (i1 != 7)) {
        return 0;
      }
      this.peekedNumberLength = n;
      this.peeked = 16;
      return 16;
      label425:
      return 0;
      label427:
      if (i1 == 2)
      {
        i1 = k;
      }
      else
      {
        return 0;
        label441:
        k = 6;
        if (i1 == 0)
        {
          i1 = 1;
          i2 = i1;
        }
        else if (i1 == 5)
        {
          i1 = k;
        }
        else
        {
          return 0;
          label473:
          if ((i1 != 2) && (i1 != 4)) {
            return 0;
          }
          i1 = 5;
          break label508;
          label496:
          k = 6;
          if (i1 != 5) {
            break;
          }
          i1 = k;
        }
      }
      label508:
      n++;
      i = i3;
      int k = i4;
    }
    return 0;
  }
  
  private void push(int paramInt)
  {
    int i = this.stackSize;
    int[] arrayOfInt = this.stack;
    if (i == arrayOfInt.length)
    {
      i *= 2;
      this.stack = Arrays.copyOf(arrayOfInt, i);
      this.pathIndices = Arrays.copyOf(this.pathIndices, i);
      this.pathNames = ((String[])Arrays.copyOf(this.pathNames, i));
    }
    arrayOfInt = this.stack;
    i = this.stackSize;
    this.stackSize = (i + 1);
    arrayOfInt[i] = paramInt;
  }
  
  private char readEscapeCharacter()
    throws IOException
  {
    if ((this.pos == this.limit) && (!fillBuffer(1))) {
      throw syntaxError("Unterminated escape sequence");
    }
    Object localObject = this.buffer;
    int i = this.pos;
    int j = i + 1;
    this.pos = j;
    char c = localObject[i];
    if (c != '\n')
    {
      if ((c != '"') && (c != '\'') && (c != '/') && (c != '\\'))
      {
        if (c != 'b')
        {
          if (c != 'f')
          {
            if (c != 'n')
            {
              if (c != 'r')
              {
                if (c != 't')
                {
                  if (c == 'u')
                  {
                    if ((j + 4 > this.limit) && (!fillBuffer(4))) {
                      throw syntaxError("Unterminated escape sequence");
                    }
                    int k = 0;
                    j = this.pos;
                    i = j;
                    int m;
                    for (c = k;; c = m)
                    {
                      k = i;
                      if (k >= j + 4) {
                        break label325;
                      }
                      i = this.buffer[k];
                      m = (char)(c << '\004');
                      if ((i >= 48) && (i <= 57))
                      {
                        i -= 48;
                        m = (char)(m + i);
                      }
                      else
                      {
                        if ((i >= 97) && (i <= 102)) {
                          i -= 97;
                        }
                        for (;;)
                        {
                          i += 10;
                          break;
                          if ((i < 65) || (i > 70)) {
                            break label276;
                          }
                          i -= 65;
                        }
                      }
                      i = k + 1;
                    }
                    label276:
                    localObject = new StringBuilder();
                    ((StringBuilder)localObject).append("\\u");
                    ((StringBuilder)localObject).append(new String(this.buffer, this.pos, 4));
                    throw new NumberFormatException(((StringBuilder)localObject).toString());
                    label325:
                    this.pos += 4;
                    return c;
                  }
                  throw syntaxError("Invalid escape sequence");
                }
                return '\t';
              }
              return '\r';
            }
            return '\n';
          }
          return '\f';
        }
        return '\b';
      }
    }
    else
    {
      this.lineNumber += 1;
      this.lineStart = j;
    }
    return c;
  }
  
  private void skipQuotedValue(char paramChar)
    throws IOException
  {
    char[] arrayOfChar = this.buffer;
    do
    {
      char c1 = this.pos;
      char c2 = this.limit;
      while (c1 < c2)
      {
        char c3 = c1 + '\001';
        c1 = arrayOfChar[c1];
        if (c1 == paramChar)
        {
          this.pos = c3;
          return;
        }
        if (c1 == '\\')
        {
          this.pos = c3;
          readEscapeCharacter();
          c1 = this.pos;
          c2 = this.limit;
        }
        else
        {
          if (c1 == '\n')
          {
            this.lineNumber += 1;
            this.lineStart = c3;
          }
          c1 = c3;
        }
      }
      this.pos = c1;
    } while (fillBuffer(1));
    throw syntaxError("Unterminated string");
  }
  
  private boolean skipTo(String paramString)
    throws IOException
  {
    int i = paramString.length();
    int j = this.pos;
    int k = this.limit;
    int m = 0;
    if ((j + i > k) && (!fillBuffer(i))) {
      return false;
    }
    char[] arrayOfChar = this.buffer;
    k = this.pos;
    if (arrayOfChar[k] == '\n')
    {
      this.lineNumber += 1;
      this.lineStart = (k + 1);
    }
    for (;;)
    {
      if (m >= i) {
        break label129;
      }
      if (this.buffer[(this.pos + m)] != paramString.charAt(m))
      {
        this.pos += 1;
        break;
      }
      m++;
    }
    label129:
    return true;
  }
  
  private void skipToEndOfLine()
    throws IOException
  {
    int i;
    do
    {
      if ((this.pos >= this.limit) && (!fillBuffer(1))) {
        break;
      }
      char[] arrayOfChar = this.buffer;
      i = this.pos;
      int j = i + 1;
      this.pos = j;
      i = arrayOfChar[i];
      if (i == 10)
      {
        this.lineNumber += 1;
        this.lineStart = j;
        break;
      }
    } while (i != 13);
  }
  
  private void skipUnquotedValue()
    throws IOException
  {
    label142:
    label153:
    do
    {
      int j;
      for (int i = 0;; i++)
      {
        j = this.pos;
        if (j + i >= this.limit) {
          break label153;
        }
        j = this.buffer[(j + i)];
        if ((j == 9) || (j == 10) || (j == 12) || (j == 13) || (j == 32)) {
          break label142;
        }
        if (j == 35) {
          break;
        }
        if (j == 44) {
          break label142;
        }
        if ((j == 47) || (j == 61)) {
          break;
        }
        if ((j == 123) || (j == 125) || (j == 58)) {
          break label142;
        }
        if (j == 59) {
          break;
        }
        switch (j)
        {
        }
      }
      checkLenient();
      this.pos += i;
      return;
      this.pos = (j + i);
    } while (fillBuffer(1));
  }
  
  private IOException syntaxError(String paramString)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(locationString());
    throw new MalformedJsonException(localStringBuilder.toString());
  }
  
  public void beginArray()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    if (j == 3)
    {
      push(1);
      this.pathIndices[(this.stackSize - 1)] = 0;
      this.peeked = 0;
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected BEGIN_ARRAY but was ");
    localStringBuilder.append(peek());
    localStringBuilder.append(locationString());
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public void beginObject()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    if (j == 1)
    {
      push(3);
      this.peeked = 0;
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected BEGIN_OBJECT but was ");
    localStringBuilder.append(peek());
    localStringBuilder.append(locationString());
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public void close()
    throws IOException
  {
    this.peeked = 0;
    this.stack[0] = 8;
    this.stackSize = 1;
    this.in.close();
  }
  
  int doPeek()
    throws IOException
  {
    Object localObject = this.stack;
    int i = this.stackSize;
    int j = localObject[(i - 1)];
    if (j == 1)
    {
      localObject[(i - 1)] = 2;
    }
    else if (j == 2)
    {
      i = nextNonWhitespace(true);
      if (i != 44)
      {
        if (i != 59)
        {
          if (i == 93)
          {
            this.peeked = 4;
            return 4;
          }
          throw syntaxError("Unterminated array");
        }
        checkLenient();
      }
    }
    else
    {
      if ((j == 3) || (j == 5)) {
        break label482;
      }
      if (j == 4)
      {
        localObject[(i - 1)] = 5;
        i = nextNonWhitespace(true);
        if (i != 58) {
          if (i == 61)
          {
            checkLenient();
            if ((this.pos < this.limit) || (fillBuffer(1)))
            {
              localObject = this.buffer;
              i = this.pos;
              if (localObject[i] == '>') {
                this.pos = (i + 1);
              }
            }
          }
          else
          {
            throw syntaxError("Expected ':'");
          }
        }
      }
      else if (j == 6)
      {
        if (this.lenient) {
          consumeNonExecutePrefix();
        }
        this.stack[(this.stackSize - 1)] = 7;
      }
      else if (j == 7)
      {
        if (nextNonWhitespace(false) == -1)
        {
          this.peeked = 17;
          return 17;
        }
        checkLenient();
        this.pos -= 1;
      }
      else
      {
        if (j == 8) {
          break label471;
        }
      }
    }
    i = nextNonWhitespace(true);
    if (i != 34)
    {
      if (i != 39)
      {
        if ((i != 44) && (i != 59)) {
          if (i != 91)
          {
            if (i != 93)
            {
              if (i != 123)
              {
                this.pos -= 1;
                j = peekKeyword();
                if (j != 0) {
                  return j;
                }
                j = peekNumber();
                if (j != 0) {
                  return j;
                }
                if (isLiteral(this.buffer[this.pos]))
                {
                  checkLenient();
                  this.peeked = 10;
                  return 10;
                }
                throw syntaxError("Expected value");
              }
              this.peeked = 1;
              return 1;
            }
            if (j == 1)
            {
              this.peeked = 4;
              return 4;
            }
          }
          else
          {
            this.peeked = 3;
            return 3;
          }
        }
        if ((j != 1) && (j != 2)) {
          throw syntaxError("Unexpected value");
        }
        checkLenient();
        this.pos -= 1;
        this.peeked = 7;
        return 7;
      }
      checkLenient();
      this.peeked = 8;
      return 8;
    }
    this.peeked = 9;
    return 9;
    label471:
    throw new IllegalStateException("JsonReader is closed");
    label482:
    this.stack[(this.stackSize - 1)] = 4;
    if (j == 5)
    {
      i = nextNonWhitespace(true);
      if (i != 44)
      {
        if (i != 59)
        {
          if (i == 125)
          {
            this.peeked = 2;
            return 2;
          }
          throw syntaxError("Unterminated object");
        }
        checkLenient();
      }
    }
    i = nextNonWhitespace(true);
    if (i != 34)
    {
      if (i != 39)
      {
        if (i != 125)
        {
          checkLenient();
          this.pos -= 1;
          if (isLiteral((char)i))
          {
            this.peeked = 14;
            return 14;
          }
          throw syntaxError("Expected name");
        }
        if (j != 5)
        {
          this.peeked = 2;
          return 2;
        }
        throw syntaxError("Expected name");
      }
      checkLenient();
      this.peeked = 12;
      return 12;
    }
    this.peeked = 13;
    return 13;
  }
  
  public void endArray()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    if (j == 4)
    {
      j = this.stackSize - 1;
      this.stackSize = j;
      localObject = this.pathIndices;
      j--;
      localObject[j] += 1;
      this.peeked = 0;
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected END_ARRAY but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public void endObject()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    if (j == 2)
    {
      j = this.stackSize - 1;
      this.stackSize = j;
      this.pathNames[j] = null;
      localObject = this.pathIndices;
      j--;
      localObject[j] += 1;
      this.peeked = 0;
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected END_OBJECT but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public String getPath()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('$');
    int i = this.stackSize;
    for (int j = 0; j < i; j++)
    {
      int k = this.stack[j];
      if ((k != 1) && (k != 2))
      {
        if ((k == 3) || (k == 4) || (k == 5))
        {
          localStringBuilder.append('.');
          String[] arrayOfString = this.pathNames;
          if (arrayOfString[j] != null) {
            localStringBuilder.append(arrayOfString[j]);
          }
        }
      }
      else
      {
        localStringBuilder.append('[');
        localStringBuilder.append(this.pathIndices[j]);
        localStringBuilder.append(']');
      }
    }
    return localStringBuilder.toString();
  }
  
  public boolean hasNext()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    boolean bool;
    if ((j != 2) && (j != 4)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isLenient()
  {
    return this.lenient;
  }
  
  String locationString()
  {
    int i = this.lineNumber;
    int j = this.pos;
    int k = this.lineStart;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" at line ");
    localStringBuilder.append(i + 1);
    localStringBuilder.append(" column ");
    localStringBuilder.append(j - k + 1);
    localStringBuilder.append(" path ");
    localStringBuilder.append(getPath());
    return localStringBuilder.toString();
  }
  
  public boolean nextBoolean()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    if (j == 5)
    {
      this.peeked = 0;
      localObject = this.pathIndices;
      j = this.stackSize - 1;
      localObject[j] += 1;
      return true;
    }
    if (j == 6)
    {
      this.peeked = 0;
      localObject = this.pathIndices;
      j = this.stackSize - 1;
      localObject[j] += 1;
      return false;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected a boolean but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public double nextDouble()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    if (j == 15)
    {
      this.peeked = 0;
      localObject = this.pathIndices;
      j = this.stackSize - 1;
      localObject[j] += 1;
      return this.peekedLong;
    }
    if (j == 16)
    {
      this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
      this.pos += this.peekedNumberLength;
    }
    else if ((j != 8) && (j != 9))
    {
      if (j == 10)
      {
        this.peekedString = nextUnquotedValue();
      }
      else if (j != 11)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Expected a double but was ");
        ((StringBuilder)localObject).append(peek());
        ((StringBuilder)localObject).append(locationString());
        throw new IllegalStateException(((StringBuilder)localObject).toString());
      }
    }
    else
    {
      int k;
      if (j == 8)
      {
        j = 39;
        k = j;
      }
      else
      {
        j = 34;
        k = j;
      }
      this.peekedString = nextQuotedValue(k);
    }
    this.peeked = 11;
    double d = Double.parseDouble(this.peekedString);
    if ((!this.lenient) && ((Double.isNaN(d)) || (Double.isInfinite(d))))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("JSON forbids NaN and infinities: ");
      ((StringBuilder)localObject).append(d);
      ((StringBuilder)localObject).append(locationString());
      throw new MalformedJsonException(((StringBuilder)localObject).toString());
    }
    this.peekedString = null;
    this.peeked = 0;
    Object localObject = this.pathIndices;
    j = this.stackSize - 1;
    localObject[j] += 1;
    return d;
  }
  
  public int nextInt()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    if (j == 15)
    {
      long l = this.peekedLong;
      j = (int)l;
      if (l == j)
      {
        this.peeked = 0;
        localObject = this.pathIndices;
        i = this.stackSize - 1;
        localObject[i] += 1;
        return j;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Expected an int but was ");
      ((StringBuilder)localObject).append(this.peekedLong);
      ((StringBuilder)localObject).append(locationString());
      throw new NumberFormatException(((StringBuilder)localObject).toString());
    }
    if (j == 16)
    {
      this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
      this.pos += this.peekedNumberLength;
    }
    else
    {
      if ((j != 8) && (j != 9) && (j != 10))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Expected an int but was ");
        ((StringBuilder)localObject).append(peek());
        ((StringBuilder)localObject).append(locationString());
        throw new IllegalStateException(((StringBuilder)localObject).toString());
      }
      if (j == 10)
      {
        this.peekedString = nextUnquotedValue();
      }
      else
      {
        int k;
        if (j == 8)
        {
          j = 39;
          k = j;
        }
        else
        {
          j = 34;
          k = j;
        }
        this.peekedString = nextQuotedValue(k);
      }
    }
    try
    {
      i = Integer.parseInt(this.peekedString);
      this.peeked = 0;
      localObject = this.pathIndices;
      j = this.stackSize - 1;
      localObject[j] += 1;
      return i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      double d;
      for (;;) {}
    }
    this.peeked = 11;
    d = Double.parseDouble(this.peekedString);
    j = (int)d;
    if (j == d)
    {
      this.peekedString = null;
      this.peeked = 0;
      localObject = this.pathIndices;
      i = this.stackSize - 1;
      localObject[i] += 1;
      return j;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected an int but was ");
    ((StringBuilder)localObject).append(this.peekedString);
    ((StringBuilder)localObject).append(locationString());
    throw new NumberFormatException(((StringBuilder)localObject).toString());
  }
  
  public long nextLong()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    if (j == 15)
    {
      this.peeked = 0;
      localObject = this.pathIndices;
      j = this.stackSize - 1;
      localObject[j] += 1;
      return this.peekedLong;
    }
    if (j == 16)
    {
      this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
      this.pos += this.peekedNumberLength;
    }
    else
    {
      if ((j != 8) && (j != 9) && (j != 10))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Expected a long but was ");
        ((StringBuilder)localObject).append(peek());
        ((StringBuilder)localObject).append(locationString());
        throw new IllegalStateException(((StringBuilder)localObject).toString());
      }
      if (j == 10)
      {
        this.peekedString = nextUnquotedValue();
      }
      else
      {
        int k;
        if (j == 8)
        {
          j = 39;
          k = j;
        }
        else
        {
          j = 34;
          k = j;
        }
        this.peekedString = nextQuotedValue(k);
      }
    }
    try
    {
      l = Long.parseLong(this.peekedString);
      this.peeked = 0;
      localObject = this.pathIndices;
      j = this.stackSize - 1;
      localObject[j] += 1;
      return l;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      long l;
      double d;
      for (;;) {}
    }
    this.peeked = 11;
    d = Double.parseDouble(this.peekedString);
    l = d;
    if (l == d)
    {
      this.peekedString = null;
      this.peeked = 0;
      localObject = this.pathIndices;
      j = this.stackSize - 1;
      localObject[j] += 1;
      return l;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected a long but was ");
    ((StringBuilder)localObject).append(this.peekedString);
    ((StringBuilder)localObject).append(locationString());
    throw new NumberFormatException(((StringBuilder)localObject).toString());
  }
  
  public String nextName()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    if (j == 14)
    {
      localObject = nextUnquotedValue();
    }
    else if (j == 12)
    {
      localObject = nextQuotedValue('\'');
    }
    else
    {
      if (j != 13) {
        break label78;
      }
      localObject = nextQuotedValue('"');
    }
    this.peeked = 0;
    this.pathNames[(this.stackSize - 1)] = localObject;
    return localObject;
    label78:
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected a name but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public void nextNull()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    if (j == 7)
    {
      this.peeked = 0;
      localObject = this.pathIndices;
      j = this.stackSize - 1;
      localObject[j] += 1;
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected null but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public String nextString()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    if (j == 10)
    {
      localObject = nextUnquotedValue();
    }
    else if (j == 8)
    {
      localObject = nextQuotedValue('\'');
    }
    else if (j == 9)
    {
      localObject = nextQuotedValue('"');
    }
    else if (j == 11)
    {
      localObject = this.peekedString;
      this.peekedString = null;
    }
    else if (j == 15)
    {
      localObject = Long.toString(this.peekedLong);
    }
    else
    {
      if (j != 16) {
        break label167;
      }
      localObject = new String(this.buffer, this.pos, this.peekedNumberLength);
      this.pos += this.peekedNumberLength;
    }
    this.peeked = 0;
    int[] arrayOfInt = this.pathIndices;
    j = this.stackSize - 1;
    arrayOfInt[j] += 1;
    return localObject;
    label167:
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Expected a string but was ");
    ((StringBuilder)localObject).append(peek());
    ((StringBuilder)localObject).append(locationString());
    throw new IllegalStateException(((StringBuilder)localObject).toString());
  }
  
  public JsonToken peek()
    throws IOException
  {
    int i = this.peeked;
    int j = i;
    if (i == 0) {
      j = doPeek();
    }
    switch (j)
    {
    default: 
      throw new AssertionError();
    case 17: 
      return JsonToken.END_DOCUMENT;
    case 15: 
    case 16: 
      return JsonToken.NUMBER;
    case 12: 
    case 13: 
    case 14: 
      return JsonToken.NAME;
    case 8: 
    case 9: 
    case 10: 
    case 11: 
      return JsonToken.STRING;
    case 7: 
      return JsonToken.NULL;
    case 5: 
    case 6: 
      return JsonToken.BOOLEAN;
    case 4: 
      return JsonToken.END_ARRAY;
    case 3: 
      return JsonToken.BEGIN_ARRAY;
    case 2: 
      return JsonToken.END_OBJECT;
    }
    return JsonToken.BEGIN_OBJECT;
  }
  
  public final void setLenient(boolean paramBoolean)
  {
    this.lenient = paramBoolean;
  }
  
  public void skipValue()
    throws IOException
  {
    int i = 0;
    label187:
    do
    {
      j = this.peeked;
      int k = j;
      if (j == 0) {
        k = doPeek();
      }
      if (k == 3) {
        push(1);
      }
      for (;;)
      {
        j = i + 1;
        break label187;
        if (k != 1) {
          break;
        }
        push(3);
      }
      if (k == 4) {}
      for (this.stackSize -= 1;; this.stackSize -= 1)
      {
        j = i - 1;
        break label187;
        if (k != 2) {
          break;
        }
      }
      if ((k != 14) && (k != 10))
      {
        if ((k != 8) && (k != 12))
        {
          if ((k != 9) && (k != 13))
          {
            j = i;
            if (k == 16)
            {
              this.pos += this.peekedNumberLength;
              j = i;
            }
          }
          else
          {
            skipQuotedValue('"');
            j = i;
          }
        }
        else
        {
          skipQuotedValue('\'');
          j = i;
        }
      }
      else
      {
        skipUnquotedValue();
        j = i;
      }
      this.peeked = 0;
      i = j;
    } while (j != 0);
    int[] arrayOfInt = this.pathIndices;
    int j = this.stackSize;
    i = j - 1;
    arrayOfInt[i] += 1;
    this.pathNames[(j - 1)] = "null";
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append(locationString());
    return localStringBuilder.toString();
  }
}
