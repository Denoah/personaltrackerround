package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbstractStrictEqualityTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;

public final class StrictEqualityTypeChecker
{
  public static final StrictEqualityTypeChecker INSTANCE = new StrictEqualityTypeChecker();
  
  private StrictEqualityTypeChecker() {}
  
  public final boolean strictEqualTypes(UnwrappedType paramUnwrappedType1, UnwrappedType paramUnwrappedType2)
  {
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType1, "a");
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType2, "b");
    return AbstractStrictEqualityTypeChecker.INSTANCE.strictEqualTypes((TypeSystemContext)SimpleClassicTypeSystemContext.INSTANCE, (KotlinTypeMarker)paramUnwrappedType1, (KotlinTypeMarker)paramUnwrappedType2);
  }
}
