package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;

public final class NameResolverUtilKt
{
  public static final ClassId getClassId(NameResolver paramNameResolver, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "$this$getClassId");
    paramNameResolver = ClassId.fromString(paramNameResolver.getQualifiedClassName(paramInt), paramNameResolver.isLocalClassName(paramInt));
    Intrinsics.checkExpressionValueIsNotNull(paramNameResolver, "ClassId.fromString(getQu… isLocalClassName(index))");
    return paramNameResolver;
  }
  
  public static final Name getName(NameResolver paramNameResolver, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "$this$getName");
    paramNameResolver = Name.guessByFirstCharacter(paramNameResolver.getString(paramInt));
    Intrinsics.checkExpressionValueIsNotNull(paramNameResolver, "Name.guessByFirstCharacter(getString(index))");
    return paramNameResolver;
  }
}
