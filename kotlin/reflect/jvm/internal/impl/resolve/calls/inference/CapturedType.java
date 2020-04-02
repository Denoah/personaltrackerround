package kotlin.reflect.jvm.internal.impl.resolve.calls.inference;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SubtypingRepresentatives;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class CapturedType
  extends SimpleType
  implements SubtypingRepresentatives, CapturedTypeMarker
{
  private final Annotations annotations;
  private final CapturedTypeConstructor constructor;
  private final boolean isMarkedNullable;
  private final TypeProjection typeProjection;
  
  public CapturedType(TypeProjection paramTypeProjection, CapturedTypeConstructor paramCapturedTypeConstructor, boolean paramBoolean, Annotations paramAnnotations)
  {
    this.typeProjection = paramTypeProjection;
    this.constructor = paramCapturedTypeConstructor;
    this.isMarkedNullable = paramBoolean;
    this.annotations = paramAnnotations;
  }
  
  private final KotlinType representative(Variance paramVariance, KotlinType paramKotlinType)
  {
    if (this.typeProjection.getProjectionKind() == paramVariance) {
      paramKotlinType = this.typeProjection.getType();
    }
    Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "if (typeProjection.proje…jection.type else default");
    return paramKotlinType;
  }
  
  public Annotations getAnnotations()
  {
    return this.annotations;
  }
  
  public List<TypeProjection> getArguments()
  {
    return CollectionsKt.emptyList();
  }
  
  public CapturedTypeConstructor getConstructor()
  {
    return this.constructor;
  }
  
  public MemberScope getMemberScope()
  {
    MemberScope localMemberScope = ErrorUtils.createErrorScope("No member resolution should be done on captured type, it used only during constraint system resolution", true);
    Intrinsics.checkExpressionValueIsNotNull(localMemberScope, "ErrorUtils.createErrorSc…solution\", true\n        )");
    return localMemberScope;
  }
  
  public KotlinType getSubTypeRepresentative()
  {
    Variance localVariance = Variance.OUT_VARIANCE;
    SimpleType localSimpleType = TypeUtilsKt.getBuiltIns(this).getNullableAnyType();
    Intrinsics.checkExpressionValueIsNotNull(localSimpleType, "builtIns.nullableAnyType");
    return representative(localVariance, (KotlinType)localSimpleType);
  }
  
  public KotlinType getSuperTypeRepresentative()
  {
    Variance localVariance = Variance.IN_VARIANCE;
    SimpleType localSimpleType = TypeUtilsKt.getBuiltIns(this).getNothingType();
    Intrinsics.checkExpressionValueIsNotNull(localSimpleType, "builtIns.nothingType");
    return representative(localVariance, (KotlinType)localSimpleType);
  }
  
  public boolean isMarkedNullable()
  {
    return this.isMarkedNullable;
  }
  
  public CapturedType makeNullableAsSpecified(boolean paramBoolean)
  {
    if (paramBoolean == isMarkedNullable()) {
      return this;
    }
    return new CapturedType(this.typeProjection, getConstructor(), paramBoolean, getAnnotations());
  }
  
  public CapturedType refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    paramKotlinTypeRefiner = this.typeProjection.refine(paramKotlinTypeRefiner);
    Intrinsics.checkExpressionValueIsNotNull(paramKotlinTypeRefiner, "typeProjection.refine(kotlinTypeRefiner)");
    return new CapturedType(paramKotlinTypeRefiner, getConstructor(), isMarkedNullable(), getAnnotations());
  }
  
  public CapturedType replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    return new CapturedType(this.typeProjection, getConstructor(), isMarkedNullable(), paramAnnotations);
  }
  
  public boolean sameTypeConstructor(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
    boolean bool;
    if (getConstructor() == paramKotlinType.getConstructor()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Captured(");
    localStringBuilder.append(this.typeProjection);
    localStringBuilder.append(')');
    String str;
    if (isMarkedNullable()) {
      str = "?";
    } else {
      str = "";
    }
    localStringBuilder.append(str);
    return localStringBuilder.toString();
  }
}
