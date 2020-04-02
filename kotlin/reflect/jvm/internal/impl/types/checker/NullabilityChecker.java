package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbstractNullabilityChecker;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext.SupertypesPolicy;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;

public final class NullabilityChecker
{
  public static final NullabilityChecker INSTANCE = new NullabilityChecker();
  
  private NullabilityChecker() {}
  
  public final boolean isSubtypeOfAny(UnwrappedType paramUnwrappedType)
  {
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "type");
    return AbstractNullabilityChecker.INSTANCE.hasNotNullSupertype(SimpleClassicTypeSystemContext.INSTANCE.newBaseTypeCheckerContext(false), (SimpleTypeMarker)FlexibleTypesKt.lowerIfFlexible((KotlinType)paramUnwrappedType), (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE);
  }
}
