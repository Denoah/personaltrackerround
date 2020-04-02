package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\b\004\n\002\020 \n\002\030\002\n\002\b\007\n\002\020\021\n\002\b\002\n\002\020\002\n\002\b\002\b`\030\000*\f\b\000\020\001 \001*\004\030\0010\0022\0020\003J\033\020\017\032\004\030\0010\0032\n\020\020\032\006\022\002\b\0030\021H&?\006\002\020\022J\031\020\023\032\0020\0242\n\020\020\032\006\022\002\b\0030\021H\026?\006\002\020\025R\022\020\004\032\0028\000X¦\004?\006\006\032\004\b\005\020\006R\030\020\007\032\b\022\004\022\0020\t0\bX¦\004?\006\006\032\004\b\n\020\013R\022\020\f\032\0020\tX¦\004?\006\006\032\004\b\r\020\016?\006\026"}, d2={"Lkotlin/reflect/jvm/internal/calls/Caller;", "M", "Ljava/lang/reflect/Member;", "", "member", "getMember", "()Ljava/lang/reflect/Member;", "parameterTypes", "", "Ljava/lang/reflect/Type;", "getParameterTypes", "()Ljava/util/List;", "returnType", "getReturnType", "()Ljava/lang/reflect/Type;", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "checkArguments", "", "([Ljava/lang/Object;)V", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public abstract interface Caller<M extends Member>
{
  public abstract Object call(Object[] paramArrayOfObject);
  
  public abstract M getMember();
  
  public abstract List<Type> getParameterTypes();
  
  public abstract Type getReturnType();
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 15})
  public static final class DefaultImpls
  {
    public static <M extends Member> void checkArguments(Caller<? extends M> paramCaller, Object[] paramArrayOfObject)
    {
      Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
      if (CallerKt.getArity(paramCaller) == paramArrayOfObject.length) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Callable expects ");
      localStringBuilder.append(CallerKt.getArity(paramCaller));
      localStringBuilder.append(" arguments, but ");
      localStringBuilder.append(paramArrayOfObject.length);
      localStringBuilder.append(" were provided.");
      throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString()));
    }
  }
}
