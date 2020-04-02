package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\020\001\n\002\b\005\n\002\020 \n\002\030\002\n\002\b\006\n\002\020\000\n\000\n\002\020\021\n\002\b\002\b?\002\030\0002\n\022\006\022\004\030\0010\0020\001B\007\b\002?\006\002\020\003J\033\020\017\032\004\030\0010\0202\n\020\021\032\006\022\002\b\0030\022H\026?\006\002\020\023R\026\020\004\032\004\030\0010\0028VX?\004?\006\006\032\004\b\005\020\006R\032\020\007\032\b\022\004\022\0020\t0\b8VX?\004?\006\006\032\004\b\n\020\013R\024\020\f\032\0020\t8VX?\004?\006\006\032\004\b\r\020\016?\006\024"}, d2={"Lkotlin/reflect/jvm/internal/calls/ThrowingCaller;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "", "()V", "member", "getMember", "()Ljava/lang/Void;", "parameterTypes", "", "Ljava/lang/reflect/Type;", "getParameterTypes", "()Ljava/util/List;", "returnType", "getReturnType", "()Ljava/lang/reflect/Type;", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public final class ThrowingCaller
  implements Caller
{
  public static final ThrowingCaller INSTANCE = new ThrowingCaller();
  
  private ThrowingCaller() {}
  
  public Object call(Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
    throw ((Throwable)new UnsupportedOperationException("call/callBy are not supported for this declaration."));
  }
  
  public Void getMember()
  {
    return null;
  }
  
  public List<Type> getParameterTypes()
  {
    return CollectionsKt.emptyList();
  }
  
  public Type getReturnType()
  {
    Class localClass = Void.TYPE;
    Intrinsics.checkExpressionValueIsNotNull(localClass, "Void.TYPE");
    return (Type)localClass;
  }
}
