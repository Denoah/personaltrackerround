package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

public final class SignatureEnhancementKt
{
  public static final JavaTypeQualifiers createJavaTypeQualifiers(NullabilityQualifier paramNullabilityQualifier, MutabilityQualifier paramMutabilityQualifier, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramBoolean2) && (paramNullabilityQualifier == NullabilityQualifier.NOT_NULL)) {
      return new JavaTypeQualifiers(paramNullabilityQualifier, paramMutabilityQualifier, true, paramBoolean1);
    }
    return new JavaTypeQualifiers(paramNullabilityQualifier, paramMutabilityQualifier, false, paramBoolean1);
  }
  
  public static final <T> T select(Set<? extends T> paramSet, T paramT1, T paramT2, T paramT3, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "$this$select");
    Intrinsics.checkParameterIsNotNull(paramT1, "low");
    Intrinsics.checkParameterIsNotNull(paramT2, "high");
    if (paramBoolean)
    {
      if (paramSet.contains(paramT1)) {
        paramSet = paramT1;
      } else if (paramSet.contains(paramT2)) {
        paramSet = paramT2;
      } else {
        paramSet = null;
      }
      if ((Intrinsics.areEqual(paramSet, paramT1)) && (Intrinsics.areEqual(paramT3, paramT2))) {
        paramT3 = null;
      } else if (paramT3 == null) {
        paramT3 = paramSet;
      }
      return paramT3;
    }
    paramT1 = paramSet;
    if (paramT3 != null)
    {
      paramT2 = CollectionsKt.toSet((Iterable)SetsKt.plus(paramSet, paramT3));
      paramT1 = paramSet;
      if (paramT2 != null) {
        paramT1 = paramT2;
      }
    }
    return CollectionsKt.singleOrNull((Iterable)paramT1);
  }
  
  public static final NullabilityQualifier select(Set<? extends NullabilityQualifier> paramSet, NullabilityQualifier paramNullabilityQualifier, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramSet, "$this$select");
    if (paramNullabilityQualifier == NullabilityQualifier.FORCE_FLEXIBILITY) {
      paramSet = NullabilityQualifier.FORCE_FLEXIBILITY;
    } else {
      paramSet = (NullabilityQualifier)select(paramSet, NullabilityQualifier.NOT_NULL, NullabilityQualifier.NULLABLE, paramNullabilityQualifier, paramBoolean);
    }
    return paramSet;
  }
}
