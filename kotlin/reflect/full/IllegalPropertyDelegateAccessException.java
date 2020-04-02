package kotlin.reflect.full;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\007\030\0002\0060\001j\002`\002B\r\022\006\020\003\032\0020\004?\006\002\020\005?\006\006"}, d2={"Lkotlin/reflect/full/IllegalPropertyDelegateAccessException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "cause", "Ljava/lang/IllegalAccessException;", "(Ljava/lang/IllegalAccessException;)V", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public final class IllegalPropertyDelegateAccessException
  extends Exception
{
  public IllegalPropertyDelegateAccessException(IllegalAccessException paramIllegalAccessException)
  {
    super("Cannot obtain the delegate of a non-accessible property. Use \"isAccessible = true\" to make the property accessible", (Throwable)paramIllegalAccessException);
  }
}
