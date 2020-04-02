package kotlin;

import kotlin.jvm.functions.Function1;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\000\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\030\002\n\002\b\006\032-\020\000\032\0020\0012\006\020\002\032\0020\0032\022\020\004\032\016\022\004\022\0020\003\022\004\022\0020\0060\005H?\b?\001\000?\006\002\020\007\032\037\020\b\032\0020\0012\n\020\t\032\0020\001\"\0020\006H?\b?\001\000?\006\004\b\n\020\013?\002\004\n\002\b\031?\006\f"}, d2={"ULongArray", "Lkotlin/ULongArray;", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/ULong;", "(ILkotlin/jvm/functions/Function1;)[J", "ulongArrayOf", "elements", "ulongArrayOf-QwZRm1k", "([J)[J", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class ULongArrayKt
{
  private static final long[] ULongArray(int paramInt, Function1<? super Integer, ULong> paramFunction1)
  {
    long[] arrayOfLong = new long[paramInt];
    for (int i = 0; i < paramInt; i++) {
      arrayOfLong[i] = ((ULong)paramFunction1.invoke(Integer.valueOf(i))).unbox-impl();
    }
    return ULongArray.constructor-impl(arrayOfLong);
  }
  
  private static final long[] ulongArrayOf-QwZRm1k(long... paramVarArgs)
  {
    return paramVarArgs;
  }
}
