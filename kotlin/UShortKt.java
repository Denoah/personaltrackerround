package kotlin;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\000\n\002\030\002\n\002\020\005\n\000\n\002\020\b\n\000\n\002\020\t\n\000\n\002\020\n\n\002\b\002\032\025\020\000\032\0020\001*\0020\002H?\b?\001\000?\006\002\020\003\032\025\020\000\032\0020\001*\0020\004H?\b?\001\000?\006\002\020\005\032\025\020\000\032\0020\001*\0020\006H?\b?\001\000?\006\002\020\007\032\025\020\000\032\0020\001*\0020\bH?\b?\001\000?\006\002\020\t?\002\004\n\002\b\031?\006\n"}, d2={"toUShort", "Lkotlin/UShort;", "", "(B)S", "", "(I)S", "", "(J)S", "", "(S)S", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class UShortKt
{
  private static final short toUShort(byte paramByte)
  {
    return UShort.constructor-impl((short)paramByte);
  }
  
  private static final short toUShort(int paramInt)
  {
    return UShort.constructor-impl((short)paramInt);
  }
  
  private static final short toUShort(long paramLong)
  {
    return UShort.constructor-impl((short)(int)paramLong);
  }
  
  private static final short toUShort(short paramShort)
  {
    return UShort.constructor-impl(paramShort);
  }
}
