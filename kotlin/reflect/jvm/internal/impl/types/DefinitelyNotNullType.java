package kotlin.reflect.jvm.internal.impl.types;

import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.NullabilityChecker;
import kotlin.reflect.jvm.internal.impl.types.model.DefinitelyNotNullTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class DefinitelyNotNullType
  extends DelegatingSimpleType
  implements CustomTypeVariable, DefinitelyNotNullTypeMarker
{
  public static final Companion Companion = new Companion(null);
  private final SimpleType original;
  
  private DefinitelyNotNullType(SimpleType paramSimpleType)
  {
    this.original = paramSimpleType;
  }
  
  protected SimpleType getDelegate()
  {
    return this.original;
  }
  
  public final SimpleType getOriginal()
  {
    return this.original;
  }
  
  public boolean isMarkedNullable()
  {
    return false;
  }
  
  public boolean isTypeVariable()
  {
    boolean bool;
    if ((!(getDelegate().getConstructor() instanceof NewTypeVariableConstructor)) && (!(getDelegate().getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public SimpleType makeNullableAsSpecified(boolean paramBoolean)
  {
    SimpleType localSimpleType;
    if (paramBoolean) {
      localSimpleType = getDelegate().makeNullableAsSpecified(paramBoolean);
    } else {
      localSimpleType = (SimpleType)this;
    }
    return localSimpleType;
  }
  
  public DefinitelyNotNullType replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    return new DefinitelyNotNullType(getDelegate().replaceAnnotations(paramAnnotations));
  }
  
  public DefinitelyNotNullType replaceDelegate(SimpleType paramSimpleType)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType, "delegate");
    return new DefinitelyNotNullType(paramSimpleType);
  }
  
  public KotlinType substitutionResult(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "replacement");
    return (KotlinType)SpecialTypesKt.makeDefinitelyNotNullOrNotNull(paramKotlinType.unwrap());
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getDelegate());
    localStringBuilder.append("!!");
    return localStringBuilder.toString();
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    private final boolean makesSenseToBeDefinitelyNotNull(UnwrappedType paramUnwrappedType)
    {
      boolean bool;
      if ((TypeUtilsKt.canHaveUndefinedNullability(paramUnwrappedType)) && (!NullabilityChecker.INSTANCE.isSubtypeOfAny(paramUnwrappedType))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public final DefinitelyNotNullType makeDefinitelyNotNull$descriptors(UnwrappedType paramUnwrappedType)
    {
      Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "type");
      boolean bool = paramUnwrappedType instanceof DefinitelyNotNullType;
      Object localObject = null;
      if (bool)
      {
        localObject = (DefinitelyNotNullType)paramUnwrappedType;
      }
      else if (((Companion)this).makesSenseToBeDefinitelyNotNull(paramUnwrappedType))
      {
        if ((paramUnwrappedType instanceof FlexibleType))
        {
          localObject = (FlexibleType)paramUnwrappedType;
          bool = Intrinsics.areEqual(((FlexibleType)localObject).getLowerBound().getConstructor(), ((FlexibleType)localObject).getUpperBound().getConstructor());
          if ((_Assertions.ENABLED) && (!bool))
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("DefinitelyNotNullType for flexible type (");
            ((StringBuilder)localObject).append(paramUnwrappedType);
            ((StringBuilder)localObject).append(") can be created only from type variable with the same constructor for bounds");
            throw ((Throwable)new AssertionError(((StringBuilder)localObject).toString()));
          }
        }
        localObject = new DefinitelyNotNullType(FlexibleTypesKt.lowerIfFlexible((KotlinType)paramUnwrappedType), null);
      }
      return localObject;
    }
  }
}
