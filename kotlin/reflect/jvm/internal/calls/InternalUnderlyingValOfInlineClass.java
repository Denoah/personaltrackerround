package kotlin.reflect.jvm.internal.calls;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020 \n\002\030\002\n\002\b\n\n\002\020\000\n\002\b\002\n\002\020\021\n\002\b\003\n\002\030\002\n\002\030\002\n\000\b0\030\0002\n\022\006\022\004\030\0010\0020\001:\002\026\027B\035\b\002\022\006\020\003\032\0020\002\022\f\020\004\032\b\022\004\022\0020\0060\005?\006\002\020\007J%\020\020\032\004\030\0010\0212\b\020\022\032\004\030\0010\0212\n\020\023\032\006\022\002\b\0030\024H\004?\006\002\020\025R\023\020\b\032\004\030\0010\0028F?\006\006\032\004\b\t\020\nR\027\020\004\032\b\022\004\022\0020\0060\005?\006\b\n\000\032\004\b\013\020\fR\021\020\r\032\0020\006?\006\b\n\000\032\004\b\016\020\017R\016\020\003\032\0020\002X?\004?\006\002\n\000?\001\002\030\031?\006\032"}, d2={"Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass;", "Lkotlin/reflect/jvm/internal/calls/Caller;", "Ljava/lang/reflect/Method;", "unboxMethod", "parameterTypes", "", "Ljava/lang/reflect/Type;", "(Ljava/lang/reflect/Method;Ljava/util/List;)V", "member", "getMember", "()Ljava/lang/reflect/Method;", "getParameterTypes", "()Ljava/util/List;", "returnType", "getReturnType", "()Ljava/lang/reflect/Type;", "callMethod", "", "instance", "args", "", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", "Bound", "Unbound", "Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass$Unbound;", "Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass$Bound;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
public abstract class InternalUnderlyingValOfInlineClass
  implements Caller<Method>
{
  private final List<Type> parameterTypes;
  private final Type returnType;
  private final Method unboxMethod;
  
  private InternalUnderlyingValOfInlineClass(Method paramMethod, List<? extends Type> paramList)
  {
    this.unboxMethod = paramMethod;
    this.parameterTypes = paramList;
    paramMethod = paramMethod.getReturnType();
    Intrinsics.checkExpressionValueIsNotNull(paramMethod, "unboxMethod.returnType");
    this.returnType = ((Type)paramMethod);
  }
  
  protected final Object callMethod(Object paramObject, Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
    return this.unboxMethod.invoke(paramObject, Arrays.copyOf(paramArrayOfObject, paramArrayOfObject.length));
  }
  
  public void checkArguments(Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
    Caller.DefaultImpls.checkArguments(this, paramArrayOfObject);
  }
  
  public final Method getMember()
  {
    return null;
  }
  
  public final List<Type> getParameterTypes()
  {
    return this.parameterTypes;
  }
  
  public final Type getReturnType()
  {
    return this.returnType;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\000\n\002\b\003\n\002\020\021\n\002\b\002\030\0002\0020\0012\0020\002B\027\022\006\020\003\032\0020\004\022\b\020\005\032\004\030\0010\006?\006\002\020\007J\033\020\b\032\004\030\0010\0062\n\020\t\032\006\022\002\b\0030\nH\026?\006\002\020\013R\020\020\005\032\004\030\0010\006X?\004?\006\002\n\000?\006\f"}, d2={"Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass$Bound;", "Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass;", "Lkotlin/reflect/jvm/internal/calls/BoundCaller;", "unboxMethod", "Ljava/lang/reflect/Method;", "boundReceiver", "", "(Ljava/lang/reflect/Method;Ljava/lang/Object;)V", "call", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class Bound
    extends InternalUnderlyingValOfInlineClass
    implements BoundCaller
  {
    private final Object boundReceiver;
    
    public Bound(Method paramMethod, Object paramObject)
    {
      super(CollectionsKt.emptyList(), null);
      this.boundReceiver = paramObject;
    }
    
    public Object call(Object[] paramArrayOfObject)
    {
      Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
      checkArguments(paramArrayOfObject);
      return callMethod(this.boundReceiver, paramArrayOfObject);
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\020\021\n\002\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\033\020\005\032\004\030\0010\0062\n\020\007\032\006\022\002\b\0030\bH\026?\006\002\020\t?\006\n"}, d2={"Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass$Unbound;", "Lkotlin/reflect/jvm/internal/calls/InternalUnderlyingValOfInlineClass;", "unboxMethod", "Ljava/lang/reflect/Method;", "(Ljava/lang/reflect/Method;)V", "call", "", "args", "", "([Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-reflection"}, k=1, mv={1, 1, 15})
  public static final class Unbound
    extends InternalUnderlyingValOfInlineClass
  {
    public Unbound(Method paramMethod)
    {
      super(CollectionsKt.listOf(paramMethod.getDeclaringClass()), null);
    }
    
    public Object call(Object[] paramArrayOfObject)
    {
      Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "args");
      checkArguments(paramArrayOfObject);
      Object localObject = paramArrayOfObject[0];
      CallerImpl.Companion localCompanion = CallerImpl.Companion;
      if (paramArrayOfObject.length <= 1)
      {
        paramArrayOfObject = new Object[0];
      }
      else
      {
        paramArrayOfObject = ArraysKt.copyOfRange(paramArrayOfObject, 1, paramArrayOfObject.length);
        if (paramArrayOfObject == null) {
          break label52;
        }
      }
      return callMethod(localObject, paramArrayOfObject);
      label52:
      throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }
  }
}
