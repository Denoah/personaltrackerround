package kotlin.reflect.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.jvm.internal.KClassImpl;

@Metadata(bv={1, 0, 3}, d1={"\000\016\n\000\n\002\020\016\n\002\030\002\n\002\b\003\"\031\020\000\032\0020\001*\006\022\002\b\0030\0028F?\006\006\032\004\b\003\020\004?\006\005"}, d2={"jvmName", "", "Lkotlin/reflect/KClass;", "getJvmName", "(Lkotlin/reflect/KClass;)Ljava/lang/String;", "kotlin-reflection"}, k=2, mv={1, 1, 15})
public final class KClassesJvm
{
  public static final String getJvmName(KClass<?> paramKClass)
  {
    Intrinsics.checkParameterIsNotNull(paramKClass, "$this$jvmName");
    paramKClass = ((KClassImpl)paramKClass).getJClass().getName();
    Intrinsics.checkExpressionValueIsNotNull(paramKClass, "(this as KClassImpl).jClass.name");
    return paramKClass;
  }
}
