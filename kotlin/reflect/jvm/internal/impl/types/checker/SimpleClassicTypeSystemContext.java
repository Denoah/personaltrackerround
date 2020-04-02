package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DefinitelyNotNullTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DynamicTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.FlexibleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;

public final class SimpleClassicTypeSystemContext
  implements ClassicTypeSystemContext
{
  public static final SimpleClassicTypeSystemContext INSTANCE = new SimpleClassicTypeSystemContext();
  
  private SimpleClassicTypeSystemContext() {}
  
  public int argumentsCount(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$argumentsCount");
    return ClassicTypeSystemContext.DefaultImpls.argumentsCount(this, paramKotlinTypeMarker);
  }
  
  public TypeArgumentListMarker asArgumentList(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$asArgumentList");
    return ClassicTypeSystemContext.DefaultImpls.asArgumentList(this, paramSimpleTypeMarker);
  }
  
  public CapturedTypeMarker asCapturedType(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$asCapturedType");
    return ClassicTypeSystemContext.DefaultImpls.asCapturedType(this, paramSimpleTypeMarker);
  }
  
  public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$asDefinitelyNotNullType");
    return ClassicTypeSystemContext.DefaultImpls.asDefinitelyNotNullType(this, paramSimpleTypeMarker);
  }
  
  public DynamicTypeMarker asDynamicType(FlexibleTypeMarker paramFlexibleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramFlexibleTypeMarker, "$this$asDynamicType");
    return ClassicTypeSystemContext.DefaultImpls.asDynamicType(this, paramFlexibleTypeMarker);
  }
  
  public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$asFlexibleType");
    return ClassicTypeSystemContext.DefaultImpls.asFlexibleType(this, paramKotlinTypeMarker);
  }
  
  public SimpleTypeMarker asSimpleType(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$asSimpleType");
    return ClassicTypeSystemContext.DefaultImpls.asSimpleType(this, paramKotlinTypeMarker);
  }
  
  public TypeArgumentMarker asTypeArgument(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$asTypeArgument");
    return ClassicTypeSystemContext.DefaultImpls.asTypeArgument(this, paramKotlinTypeMarker);
  }
  
  public SimpleTypeMarker captureFromArguments(SimpleTypeMarker paramSimpleTypeMarker, CaptureStatus paramCaptureStatus)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "type");
    Intrinsics.checkParameterIsNotNull(paramCaptureStatus, "status");
    return ClassicTypeSystemContext.DefaultImpls.captureFromArguments(this, paramSimpleTypeMarker, paramCaptureStatus);
  }
  
  public TypeArgumentMarker get(TypeArgumentListMarker paramTypeArgumentListMarker, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "$this$get");
    return ClassicTypeSystemContext.DefaultImpls.get(this, paramTypeArgumentListMarker, paramInt);
  }
  
  public TypeArgumentMarker getArgument(KotlinTypeMarker paramKotlinTypeMarker, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$getArgument");
    return ClassicTypeSystemContext.DefaultImpls.getArgument(this, paramKotlinTypeMarker, paramInt);
  }
  
  public FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$getClassFqNameUnsafe");
    return ClassicTypeSystemContext.DefaultImpls.getClassFqNameUnsafe(this, paramTypeConstructorMarker);
  }
  
  public TypeParameterMarker getParameter(TypeConstructorMarker paramTypeConstructorMarker, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$getParameter");
    return ClassicTypeSystemContext.DefaultImpls.getParameter(this, paramTypeConstructorMarker, paramInt);
  }
  
  public PrimitiveType getPrimitiveArrayType(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$getPrimitiveArrayType");
    return ClassicTypeSystemContext.DefaultImpls.getPrimitiveArrayType(this, paramTypeConstructorMarker);
  }
  
  public PrimitiveType getPrimitiveType(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$getPrimitiveType");
    return ClassicTypeSystemContext.DefaultImpls.getPrimitiveType(this, paramTypeConstructorMarker);
  }
  
  public KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker paramTypeParameterMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeParameterMarker, "$this$getRepresentativeUpperBound");
    return ClassicTypeSystemContext.DefaultImpls.getRepresentativeUpperBound(this, paramTypeParameterMarker);
  }
  
  public KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$getSubstitutedUnderlyingType");
    return ClassicTypeSystemContext.DefaultImpls.getSubstitutedUnderlyingType(this, paramKotlinTypeMarker);
  }
  
  public KotlinTypeMarker getType(TypeArgumentMarker paramTypeArgumentMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeArgumentMarker, "$this$getType");
    return ClassicTypeSystemContext.DefaultImpls.getType(this, paramTypeArgumentMarker);
  }
  
  public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$getTypeParameterClassifier");
    return ClassicTypeSystemContext.DefaultImpls.getTypeParameterClassifier(this, paramTypeConstructorMarker);
  }
  
  public TypeVariance getVariance(TypeArgumentMarker paramTypeArgumentMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeArgumentMarker, "$this$getVariance");
    return ClassicTypeSystemContext.DefaultImpls.getVariance(this, paramTypeArgumentMarker);
  }
  
  public TypeVariance getVariance(TypeParameterMarker paramTypeParameterMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeParameterMarker, "$this$getVariance");
    return ClassicTypeSystemContext.DefaultImpls.getVariance(this, paramTypeParameterMarker);
  }
  
  public boolean hasAnnotation(KotlinTypeMarker paramKotlinTypeMarker, FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$hasAnnotation");
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    return ClassicTypeSystemContext.DefaultImpls.hasAnnotation(this, paramKotlinTypeMarker, paramFqName);
  }
  
  public boolean identicalArguments(SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker1, "a");
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker2, "b");
    return ClassicTypeSystemContext.DefaultImpls.identicalArguments(this, paramSimpleTypeMarker1, paramSimpleTypeMarker2);
  }
  
  public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "types");
    return ClassicTypeSystemContext.DefaultImpls.intersectTypes(this, paramList);
  }
  
  public boolean isAnyConstructor(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isAnyConstructor");
    return ClassicTypeSystemContext.DefaultImpls.isAnyConstructor(this, paramTypeConstructorMarker);
  }
  
  public boolean isClassTypeConstructor(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isClassTypeConstructor");
    return ClassicTypeSystemContext.DefaultImpls.isClassTypeConstructor(this, paramTypeConstructorMarker);
  }
  
  public boolean isCommonFinalClassConstructor(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isCommonFinalClassConstructor");
    return ClassicTypeSystemContext.DefaultImpls.isCommonFinalClassConstructor(this, paramTypeConstructorMarker);
  }
  
  public boolean isDenotable(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isDenotable");
    return ClassicTypeSystemContext.DefaultImpls.isDenotable(this, paramTypeConstructorMarker);
  }
  
  public boolean isEqualTypeConstructors(TypeConstructorMarker paramTypeConstructorMarker1, TypeConstructorMarker paramTypeConstructorMarker2)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker1, "c1");
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker2, "c2");
    return ClassicTypeSystemContext.DefaultImpls.isEqualTypeConstructors(this, paramTypeConstructorMarker1, paramTypeConstructorMarker2);
  }
  
  public boolean isError(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isError");
    return ClassicTypeSystemContext.DefaultImpls.isError(this, paramKotlinTypeMarker);
  }
  
  public boolean isInlineClass(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isInlineClass");
    return ClassicTypeSystemContext.DefaultImpls.isInlineClass(this, paramTypeConstructorMarker);
  }
  
  public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isIntegerLiteralTypeConstructor");
    return ClassicTypeSystemContext.DefaultImpls.isIntegerLiteralTypeConstructor(this, paramTypeConstructorMarker);
  }
  
  public boolean isIntersection(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isIntersection");
    return ClassicTypeSystemContext.DefaultImpls.isIntersection(this, paramTypeConstructorMarker);
  }
  
  public boolean isMarkedNullable(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isMarkedNullable");
    return ClassicTypeSystemContext.DefaultImpls.isMarkedNullable(this, paramKotlinTypeMarker);
  }
  
  public boolean isMarkedNullable(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isMarkedNullable");
    return ClassicTypeSystemContext.DefaultImpls.isMarkedNullable(this, paramSimpleTypeMarker);
  }
  
  public boolean isNothingConstructor(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isNothingConstructor");
    return ClassicTypeSystemContext.DefaultImpls.isNothingConstructor(this, paramTypeConstructorMarker);
  }
  
  public boolean isNullableType(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isNullableType");
    return ClassicTypeSystemContext.DefaultImpls.isNullableType(this, paramKotlinTypeMarker);
  }
  
  public boolean isPrimitiveType(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isPrimitiveType");
    return ClassicTypeSystemContext.DefaultImpls.isPrimitiveType(this, paramSimpleTypeMarker);
  }
  
  public boolean isSingleClassifierType(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isSingleClassifierType");
    return ClassicTypeSystemContext.DefaultImpls.isSingleClassifierType(this, paramSimpleTypeMarker);
  }
  
  public boolean isStarProjection(TypeArgumentMarker paramTypeArgumentMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeArgumentMarker, "$this$isStarProjection");
    return ClassicTypeSystemContext.DefaultImpls.isStarProjection(this, paramTypeArgumentMarker);
  }
  
  public boolean isStubType(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isStubType");
    return ClassicTypeSystemContext.DefaultImpls.isStubType(this, paramSimpleTypeMarker);
  }
  
  public boolean isUnderKotlinPackage(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isUnderKotlinPackage");
    return ClassicTypeSystemContext.DefaultImpls.isUnderKotlinPackage(this, paramTypeConstructorMarker);
  }
  
  public SimpleTypeMarker lowerBound(FlexibleTypeMarker paramFlexibleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramFlexibleTypeMarker, "$this$lowerBound");
    return ClassicTypeSystemContext.DefaultImpls.lowerBound(this, paramFlexibleTypeMarker);
  }
  
  public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$lowerBoundIfFlexible");
    return ClassicTypeSystemContext.DefaultImpls.lowerBoundIfFlexible(this, paramKotlinTypeMarker);
  }
  
  public KotlinTypeMarker lowerType(CapturedTypeMarker paramCapturedTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramCapturedTypeMarker, "$this$lowerType");
    return ClassicTypeSystemContext.DefaultImpls.lowerType(this, paramCapturedTypeMarker);
  }
  
  public KotlinTypeMarker makeNullable(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$makeNullable");
    return ClassicTypeSystemContext.DefaultImpls.makeNullable(this, paramKotlinTypeMarker);
  }
  
  public AbstractTypeCheckerContext newBaseTypeCheckerContext(boolean paramBoolean)
  {
    return ClassicTypeSystemContext.DefaultImpls.newBaseTypeCheckerContext(this, paramBoolean);
  }
  
  public int parametersCount(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$parametersCount");
    return ClassicTypeSystemContext.DefaultImpls.parametersCount(this, paramTypeConstructorMarker);
  }
  
  public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$possibleIntegerTypes");
    return ClassicTypeSystemContext.DefaultImpls.possibleIntegerTypes(this, paramSimpleTypeMarker);
  }
  
  public int size(TypeArgumentListMarker paramTypeArgumentListMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "$this$size");
    return ClassicTypeSystemContext.DefaultImpls.size(this, paramTypeArgumentListMarker);
  }
  
  public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$supertypes");
    return ClassicTypeSystemContext.DefaultImpls.supertypes(this, paramTypeConstructorMarker);
  }
  
  public TypeConstructorMarker typeConstructor(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$typeConstructor");
    return ClassicTypeSystemContext.DefaultImpls.typeConstructor(this, paramKotlinTypeMarker);
  }
  
  public TypeConstructorMarker typeConstructor(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$typeConstructor");
    return ClassicTypeSystemContext.DefaultImpls.typeConstructor(this, paramSimpleTypeMarker);
  }
  
  public SimpleTypeMarker upperBound(FlexibleTypeMarker paramFlexibleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramFlexibleTypeMarker, "$this$upperBound");
    return ClassicTypeSystemContext.DefaultImpls.upperBound(this, paramFlexibleTypeMarker);
  }
  
  public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$upperBoundIfFlexible");
    return ClassicTypeSystemContext.DefaultImpls.upperBoundIfFlexible(this, paramKotlinTypeMarker);
  }
  
  public SimpleTypeMarker withNullability(SimpleTypeMarker paramSimpleTypeMarker, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$withNullability");
    return ClassicTypeSystemContext.DefaultImpls.withNullability(this, paramSimpleTypeMarker, paramBoolean);
  }
}
