package kotlin.reflect;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\b\002\n\002\020\000\n\002\030\002\n\002\b\004\032+\020\000\032\002H\001\"\b\b\000\020\001*\0020\002*\b\022\004\022\002H\0010\0032\b\020\004\032\004\030\0010\002H\007?\006\002\020\005\032-\020\006\032\004\030\001H\001\"\b\b\000\020\001*\0020\002*\b\022\004\022\002H\0010\0032\b\020\004\032\004\030\0010\002H\007?\006\002\020\005?\006\007"}, d2={"cast", "T", "", "Lkotlin/reflect/KClass;", "value", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)Ljava/lang/Object;", "safeCast", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class KClasses
{
  public static final <T> T cast(KClass<T> paramKClass, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$cast");
    if (paramKClass.isInstance(paramObject))
    {
      if (paramObject != null) {
        return paramObject;
      }
      throw new TypeCastException("null cannot be cast to non-null type T");
    }
    paramObject = new StringBuilder();
    paramObject.append("Value cannot be cast to ");
    paramObject.append(paramKClass.getQualifiedName());
    throw ((Throwable)new ClassCastException(paramObject.toString()));
  }
  
  public static final <T> T safeCast(KClass<T> paramKClass, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$safeCast");
    if (paramKClass.isInstance(paramObject))
    {
      if (paramObject == null) {
        throw new TypeCastException("null cannot be cast to non-null type T");
      }
    }
    else {
      paramObject = null;
    }
    return paramObject;
  }
}
