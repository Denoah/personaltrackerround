package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;

public final class NewCapturedType
  extends SimpleType
  implements CapturedTypeMarker
{
  private final Annotations annotations;
  private final CaptureStatus captureStatus;
  private final NewCapturedTypeConstructor constructor;
  private final boolean isMarkedNullable;
  private final UnwrappedType lowerType;
  
  public NewCapturedType(CaptureStatus paramCaptureStatus, UnwrappedType paramUnwrappedType, TypeProjection paramTypeProjection)
  {
    this(paramCaptureStatus, new NewCapturedTypeConstructor(paramTypeProjection, null, null, 6, null), paramUnwrappedType, null, false, 24, null);
  }
  
  public NewCapturedType(CaptureStatus paramCaptureStatus, NewCapturedTypeConstructor paramNewCapturedTypeConstructor, UnwrappedType paramUnwrappedType, Annotations paramAnnotations, boolean paramBoolean)
  {
    this.captureStatus = paramCaptureStatus;
    this.constructor = paramNewCapturedTypeConstructor;
    this.lowerType = paramUnwrappedType;
    this.annotations = paramAnnotations;
    this.isMarkedNullable = paramBoolean;
  }
  
  public Annotations getAnnotations()
  {
    return this.annotations;
  }
  
  public List<TypeProjection> getArguments()
  {
    return CollectionsKt.emptyList();
  }
  
  public NewCapturedTypeConstructor getConstructor()
  {
    return this.constructor;
  }
  
  public final UnwrappedType getLowerType()
  {
    return this.lowerType;
  }
  
  public MemberScope getMemberScope()
  {
    MemberScope localMemberScope = ErrorUtils.createErrorScope("No member resolution should be done on captured type!", true);
    Intrinsics.checkExpressionValueIsNotNull(localMemberScope, "ErrorUtils.createErrorSc…on captured type!\", true)");
    return localMemberScope;
  }
  
  public boolean isMarkedNullable()
  {
    return this.isMarkedNullable;
  }
  
  public NewCapturedType makeNullableAsSpecified(boolean paramBoolean)
  {
    return new NewCapturedType(this.captureStatus, getConstructor(), this.lowerType, getAnnotations(), paramBoolean);
  }
  
  public NewCapturedType refine(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeRefiner, "kotlinTypeRefiner");
    CaptureStatus localCaptureStatus = this.captureStatus;
    NewCapturedTypeConstructor localNewCapturedTypeConstructor = getConstructor().refine(paramKotlinTypeRefiner);
    UnwrappedType localUnwrappedType = this.lowerType;
    if (localUnwrappedType != null) {
      paramKotlinTypeRefiner = paramKotlinTypeRefiner.refineType((KotlinType)localUnwrappedType).unwrap();
    } else {
      paramKotlinTypeRefiner = null;
    }
    return new NewCapturedType(localCaptureStatus, localNewCapturedTypeConstructor, paramKotlinTypeRefiner, getAnnotations(), isMarkedNullable());
  }
  
  public NewCapturedType replaceAnnotations(Annotations paramAnnotations)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations, "newAnnotations");
    return new NewCapturedType(this.captureStatus, getConstructor(), this.lowerType, paramAnnotations, isMarkedNullable());
  }
}
