package okhttp3.internal;

import java.net.IDN;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okio.Buffer;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\000\n\002\020\013\n\000\n\002\020\016\n\000\n\002\020\b\n\002\b\002\n\002\020\022\n\002\b\002\n\002\030\002\n\002\b\004\0320\020\000\032\0020\0012\006\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\0052\006\020\007\032\0020\b2\006\020\t\032\0020\005H\002\032\"\020\n\032\004\030\0010\0132\006\020\002\032\0020\0032\006\020\004\032\0020\0052\006\020\006\032\0020\005H\002\032\020\020\f\032\0020\0032\006\020\007\032\0020\bH\002\032\f\020\r\032\0020\001*\0020\003H\002\032\f\020\016\032\004\030\0010\003*\0020\003?\006\017"}, d2={"decodeIpv4Suffix", "", "input", "", "pos", "", "limit", "address", "", "addressOffset", "decodeIpv6", "Ljava/net/InetAddress;", "inet6AddressToAscii", "containsInvalidHostnameAsciiCodes", "toCanonicalHost", "okhttp"}, k=2, mv={1, 1, 16})
public final class HostnamesKt
{
  private static final boolean containsInvalidHostnameAsciiCodes(String paramString)
  {
    int i = paramString.length();
    int j = 0;
    while (j < i)
    {
      char c = paramString.charAt(j);
      if ((c > '\037') && (c < ''))
      {
        if (StringsKt.indexOf$default((CharSequence)" #%/:?@[\\]", c, 0, false, 6, null) != -1) {
          return true;
        }
        j++;
      }
      else
      {
        return true;
      }
    }
    return false;
  }
  
  private static final boolean decodeIpv4Suffix(String paramString, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
  {
    int i = paramInt3;
    int j = paramInt1;
    boolean bool;
    for (;;)
    {
      bool = false;
      if (j >= paramInt2) {
        break;
      }
      if (i == paramArrayOfByte.length) {
        return false;
      }
      paramInt1 = j;
      if (i != paramInt3)
      {
        if (paramString.charAt(j) != '.') {
          return false;
        }
        paramInt1 = j + 1;
      }
      j = paramInt1;
      int k = 0;
      while (j < paramInt2)
      {
        int m = paramString.charAt(j);
        if ((m < 48) || (m > 57)) {
          break;
        }
        if ((k == 0) && (paramInt1 != j)) {
          return false;
        }
        k = k * 10 + m - 48;
        if (k > 255) {
          return false;
        }
        j++;
      }
      if (j - paramInt1 == 0) {
        return false;
      }
      paramArrayOfByte[i] = ((byte)(byte)k);
      i++;
    }
    if (i == paramInt3 + 4) {
      bool = true;
    }
    return bool;
  }
  
  private static final InetAddress decodeIpv6(String paramString, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[16];
    int i = -1;
    int j = i;
    int k = 0;
    int m = paramInt1;
    int n;
    for (;;)
    {
      paramInt1 = k;
      n = i;
      if (m >= paramInt2) {
        break label308;
      }
      if (k == 16) {
        return null;
      }
      n = m + 2;
      if ((n <= paramInt2) && (StringsKt.startsWith$default(paramString, "::", m, false, 4, null)))
      {
        if (i != -1) {
          return null;
        }
        paramInt1 = k + 2;
        if (n == paramInt2)
        {
          n = paramInt1;
          break label308;
        }
        k = n;
        i = paramInt1;
        m = paramInt1;
        paramInt1 = k;
      }
      else
      {
        paramInt1 = m;
        if (k != 0) {
          if (StringsKt.startsWith$default(paramString, ":", m, false, 4, null))
          {
            paramInt1 = m + 1;
          }
          else
          {
            if (StringsKt.startsWith$default(paramString, ".", m, false, 4, null))
            {
              if (!decodeIpv4Suffix(paramString, j, paramInt2, arrayOfByte, k - 2)) {
                return null;
              }
              paramInt1 = k + 2;
              n = i;
              break label308;
            }
            return null;
          }
        }
        m = k;
      }
      k = paramInt1;
      j = 0;
      while (k < paramInt2)
      {
        n = Util.parseHexDigit(paramString.charAt(k));
        if (n == -1) {
          break;
        }
        j = (j << 4) + n;
        k++;
      }
      n = k - paramInt1;
      if ((n == 0) || (n > 4)) {
        break;
      }
      int i1 = m + 1;
      arrayOfByte[m] = ((byte)(byte)(j >>> 8 & 0xFF));
      n = i1 + 1;
      arrayOfByte[i1] = ((byte)(byte)(j & 0xFF));
      m = k;
      k = n;
      j = paramInt1;
    }
    return null;
    label308:
    if (paramInt1 != 16)
    {
      if (n == -1) {
        return null;
      }
      paramInt2 = paramInt1 - n;
      System.arraycopy(arrayOfByte, n, arrayOfByte, 16 - paramInt2, paramInt2);
      Arrays.fill(arrayOfByte, n, 16 - paramInt1 + n, (byte)0);
    }
    return InetAddress.getByAddress(arrayOfByte);
  }
  
  private static final String inet6AddressToAscii(byte[] paramArrayOfByte)
  {
    int i = 0;
    int j = -1;
    int k = 0;
    int n;
    int i3;
    for (int m = k; k < paramArrayOfByte.length; m = i3)
    {
      for (n = k; (n < 16) && (paramArrayOfByte[n] == 0) && (paramArrayOfByte[(n + 1)] == 0); n += 2) {}
      int i1 = n - k;
      int i2 = j;
      i3 = m;
      if (i1 > m)
      {
        i2 = j;
        i3 = m;
        if (i1 >= 4)
        {
          i3 = i1;
          i2 = k;
        }
      }
      k = n + 2;
      j = i2;
    }
    Buffer localBuffer = new Buffer();
    k = i;
    while (k < paramArrayOfByte.length) {
      if (k == j)
      {
        localBuffer.writeByte(58);
        n = k + m;
        k = n;
        if (n == 16)
        {
          localBuffer.writeByte(58);
          k = n;
        }
      }
      else
      {
        if (k > 0) {
          localBuffer.writeByte(58);
        }
        localBuffer.writeHexadecimalUnsignedLong(Util.and(paramArrayOfByte[k], 255) << 8 | Util.and(paramArrayOfByte[(k + 1)], 255));
        k += 2;
      }
    }
    return localBuffer.readUtf8();
  }
  
  public static final String toCanonicalHost(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "$this$toCanonicalHost");
    Object localObject1 = (CharSequence)paramString;
    CharSequence localCharSequence = (CharSequence)":";
    int i = 0;
    Object localObject2 = null;
    if (StringsKt.contains$default((CharSequence)localObject1, localCharSequence, false, 2, null))
    {
      if ((StringsKt.startsWith$default(paramString, "[", false, 2, null)) && (StringsKt.endsWith$default(paramString, "]", false, 2, null))) {
        localObject2 = decodeIpv6(paramString, 1, paramString.length() - 1);
      } else {
        localObject2 = decodeIpv6(paramString, 0, paramString.length());
      }
      if (localObject2 != null)
      {
        localObject1 = ((InetAddress)localObject2).getAddress();
        if (localObject1.length == 16)
        {
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "address");
          return inet6AddressToAscii((byte[])localObject1);
        }
        if (localObject1.length == 4) {
          return ((InetAddress)localObject2).getHostAddress();
        }
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Invalid IPv6 address: '");
        ((StringBuilder)localObject2).append(paramString);
        ((StringBuilder)localObject2).append('\'');
        throw ((Throwable)new AssertionError(((StringBuilder)localObject2).toString()));
      }
      return null;
    }
    try
    {
      localObject1 = IDN.toASCII(paramString);
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "IDN.toASCII(host)");
      paramString = Locale.US;
      Intrinsics.checkExpressionValueIsNotNull(paramString, "Locale.US");
      if (localObject1 != null)
      {
        paramString = ((String)localObject1).toLowerCase(paramString);
        Intrinsics.checkExpressionValueIsNotNull(paramString, "(this as java.lang.String).toLowerCase(locale)");
        if (((CharSequence)paramString).length() == 0) {
          i = 1;
        }
        if (i != 0) {
          return null;
        }
        if (containsInvalidHostnameAsciiCodes(paramString)) {
          paramString = (String)localObject2;
        }
        return paramString;
      }
      paramString = new kotlin/TypeCastException;
      paramString.<init>("null cannot be cast to non-null type java.lang.String");
      throw paramString;
    }
    catch (IllegalArgumentException paramString) {}
    return null;
  }
}
