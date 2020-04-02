package kotlin;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\000\n\002\030\002\n\002\020\005\n\000\n\002\020\b\n\000\n\002\020\t\n\000\n\002\020\n\n\002\b\002\032\025\020\000\032\0020\001*\0020\002H?\b?\001\000?\006\002\020\003\032\025\020\000\032\0020\001*\0020\004H?\b?\001\000?\006\002\020\005\032\025\020\000\032\0020\001*\0020\006H?\b?\001\000?\006\002\020\007\032\025\020\000\032\0020\001*\0020\bH?\b?\001\000?\006\002\020\t?\002\004\n\002\b\031?\006\n"}, d2={"toUByte", "Lkotlin/UByte;", "", "(B)B", "", "(I)B", "", "(J)B", "", "(S)B", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class UByteKt
{
  private static final byte toUByte(byte paramByte)
  {
    return UByte.constructor-impl(paramByte);
  }
  
  private static final byte toUByte(int paramInt)
  {
    return UByte.constructor-impl((byte)paramInt);
  }
  
  private static final byte toUByte(long paramLong)
  {
    return UByte.constructor-impl((byte)(int)paramLong);
  }
  
  private static final byte toUByte(short paramShort)
  {
    return UByte.constructor-impl((byte)paramShort);
  }
}
