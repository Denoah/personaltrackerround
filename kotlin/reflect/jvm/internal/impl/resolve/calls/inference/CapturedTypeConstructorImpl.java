package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import java.util.Collection;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedTypeConstructor;

public final class CapturedTypeConstructorImpl
  implements CapturedTypeConstructor
{
  private NewCapturedTypeConstructor newTypeConstructor;
  private final TypeProjection projection;
  
  public CapturedTypeConstructorImpl(TypeProjection paramTypeProjection)
  {
    this.projection = paramTypeProjection;
    int i;
    if (getProjection().getProjectionKind() != Variance.INVARIANT) {
      i = 1;
    } else {
      i = 0;
    }
    if ((_Assertions.ENABLED) && (i == 0))
    {
      paramTypeProjection = new StringBuilder();
      paramTypeProjection.append("Only nontrivial projections can be captured, not: ");
      paramTypeProjection.append(getProjection());
      throw ((Throwable)new AssertionError(paramTypeProjection.toString()));
    }
  }
  
  public KotlinBuiltIns getBuiltIns()
  {
    KotlinBuiltIns localKotlinBuiltIns = getProjection().getType().getConstructor().getBuiltIns();
    Intrinsics.checkExpressionValueIsNotNull(localKotlinBuiltIns, "projection.type.constructor.builtIns");
    return localKotlinBuiltIns;
  }
  
  public Void getDeclarationDescriptor()
  {
    return null;
  }
  
  public final NewCapturedTypeConstructor getNewTypeConstructor()
  {
    return this.newTypeConstructor;
  }
  
  public List<TypeParameterDescriptor> getParameters()
  {
    return CollectionsKt.emptyList();
  }
  
  public TypeProjection getProjection()
  {
    return this.projection;
  }
  
  public Collection<KotlinType> getSupertypes()
  {
    KotlinType localKotlinType;
    if (getProjection().getProjectionKind() == Variance.OUT_VARIANCE) {
      localKotlinType = getProjection().getType();
    } else {
      localKotlinType = (KotlinType)getBuiltIns().getNullableAnyType();
    }
    Intrinsics.checkExpressionValueIsNotNull(localKotlinType, "if (projection.projectio… builtIns.nullableAnyType");
    return (Collection)CollectionsKt.listOf(localKotlinType);
  }
  
  public boolean isDenotable()
  {
    return false;
  }
  
  public CapturedTypeConstructorImpl refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    paramKotlinTypeRefiner = getProjection().refine(paramKotlinTypeRefiner);
    Intrinsics.checkExpressionValueIsNotNull(paramKotlinTypeRefiner, "projection.refine(kotlinTypeRefiner)");
    return new CapturedTypeConstructorImpl(paramKotlinTypeRefiner);
  }
  
  public final void setNewTypeConstructor(NewCapturedTypeConstructor paramNewCapturedTypeConstructor)
  {
    this.newTypeConstructor = paramNewCapturedTypeConstructor;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CapturedTypeConstructor(");
    localStringBuilder.append(getProjection());
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
}
