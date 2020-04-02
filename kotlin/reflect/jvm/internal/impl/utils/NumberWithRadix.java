package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.Intrinsics;

public final class NumberWithRadix
{
  private final String number;
  private final int radix;
  
  public NumberWithRadix(String paramString, int paramInt)
  {
    this.number = paramString;
    this.radix = paramInt;
  }
  
  public final String component1()
  {
    return this.number;
  }
  
  public final int component2()
  {
    return this.radix;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject)
    {
      if ((paramObject instanceof NumberWithRadix))
      {
        paramObject = (NumberWithRadix)paramObject;
        if (Intrinsics.areEqual(this.number, paramObject.number))
        {
          int i;
          if (this.radix == paramObject.radix) {
            i = 1;
          } else {
            i = 0;
          }
          if (i != 0) {
            break label58;
          }
        }
      }
      return false;
    }
    label58:
    return true;
  }
  
  public int hashCode()
  {
    String str = this.number;
    int i;
    if (str != null) {
      i = str.hashCode();
    } else {
      i = 0;
    }
    return i * 31 + this.radix;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("NumberWithRadix(number=");
    localStringBuilder.append(this.number);
    localStringBuilder.append(", radix=");
    localStringBuilder.append(this.radix);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
