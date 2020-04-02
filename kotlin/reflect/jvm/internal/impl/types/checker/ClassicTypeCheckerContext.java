package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext.SupertypesPolicy.DoCustomTransform;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructorSubstitution.Companion;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitution;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
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

public class ClassicTypeCheckerContext
  extends AbstractTypeCheckerContext
  implements ClassicTypeSystemContext
{
  public static final Companion Companion = new Companion(null);
  private final boolean allowedTypeVariable;
  private final boolean errorTypeEqualsToAnything;
  private final KotlinTypeRefiner kotlinTypeRefiner;
  
  public ClassicTypeCheckerContext(boolean paramBoolean1, boolean paramBoolean2, KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    this.errorTypeEqualsToAnything = paramBoolean1;
    this.allowedTypeVariable = paramBoolean2;
    this.kotlinTypeRefiner = paramKotlinTypeRefiner;
  }
  
  public boolean areEqualTypeConstructors(TypeConstructor paramTypeConstructor1, TypeConstructor paramTypeConstructor2)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructor1, "a");
    Intrinsics.checkParameterIsNotNull(paramTypeConstructor2, "b");
    boolean bool;
    if ((paramTypeConstructor1 instanceof IntegerLiteralTypeConstructor)) {
      bool = ((IntegerLiteralTypeConstructor)paramTypeConstructor1).checkConstructor(paramTypeConstructor2);
    } else if ((paramTypeConstructor2 instanceof IntegerLiteralTypeConstructor)) {
      bool = ((IntegerLiteralTypeConstructor)paramTypeConstructor2).checkConstructor(paramTypeConstructor1);
    } else {
      bool = Intrinsics.areEqual(paramTypeConstructor1, paramTypeConstructor2);
    }
    return bool;
  }
  
  public boolean areEqualTypeConstructors(TypeConstructorMarker paramTypeConstructorMarker1, TypeConstructorMarker paramTypeConstructorMarker2)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker1, "a");
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker2, "b");
    if ((paramTypeConstructorMarker1 instanceof TypeConstructor))
    {
      if ((paramTypeConstructorMarker2 instanceof TypeConstructor)) {
        return areEqualTypeConstructors((TypeConstructor)paramTypeConstructorMarker1, (TypeConstructor)paramTypeConstructorMarker2);
      }
      throw ((Throwable)new IllegalArgumentException(ClassicTypeCheckerContextKt.access$errorMessage(paramTypeConstructorMarker2).toString()));
    }
    throw ((Throwable)new IllegalArgumentException(ClassicTypeCheckerContextKt.access$errorMessage(paramTypeConstructorMarker1).toString()));
  }
  
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
  
  public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker paramSimpleTypeMarker, TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$fastCorrespondingSupertypes");
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "constructor");
    return ClassicTypeSystemContext.DefaultImpls.fastCorrespondingSupertypes(this, paramSimpleTypeMarker, paramTypeConstructorMarker);
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
  
  public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker paramSimpleTypeMarker, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$getArgumentOrNull");
    return ClassicTypeSystemContext.DefaultImpls.getArgumentOrNull(this, paramSimpleTypeMarker, paramInt);
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
  
  public boolean hasFlexibleNullability(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$hasFlexibleNullability");
    return ClassicTypeSystemContext.DefaultImpls.hasFlexibleNullability(this, paramKotlinTypeMarker);
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
  
  public boolean isAllowedTypeVariable(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isAllowedTypeVariable");
    boolean bool;
    if (((paramKotlinTypeMarker instanceof UnwrappedType)) && (this.allowedTypeVariable) && ((((UnwrappedType)paramKotlinTypeMarker).getConstructor() instanceof NewTypeVariableConstructor))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isAnyConstructor(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isAnyConstructor");
    return ClassicTypeSystemContext.DefaultImpls.isAnyConstructor(this, paramTypeConstructorMarker);
  }
  
  public boolean isClassType(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isClassType");
    return ClassicTypeSystemContext.DefaultImpls.isClassType(this, paramSimpleTypeMarker);
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
  
  public boolean isDefinitelyNotNullType(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isDefinitelyNotNullType");
    return ClassicTypeSystemContext.DefaultImpls.isDefinitelyNotNullType(this, paramKotlinTypeMarker);
  }
  
  public boolean isDenotable(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isDenotable");
    return ClassicTypeSystemContext.DefaultImpls.isDenotable(this, paramTypeConstructorMarker);
  }
  
  public boolean isDynamic(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isDynamic");
    return ClassicTypeSystemContext.DefaultImpls.isDynamic(this, paramKotlinTypeMarker);
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
  
  public boolean isErrorTypeEqualsToAnything()
  {
    return this.errorTypeEqualsToAnything;
  }
  
  public boolean isInlineClass(TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "$this$isInlineClass");
    return ClassicTypeSystemContext.DefaultImpls.isInlineClass(this, paramTypeConstructorMarker);
  }
  
  public boolean isIntegerLiteralType(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "$this$isIntegerLiteralType");
    return ClassicTypeSystemContext.DefaultImpls.isIntegerLiteralType(this, paramSimpleTypeMarker);
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
  
  public boolean isNothing(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "$this$isNothing");
    return ClassicTypeSystemContext.DefaultImpls.isNothing(this, paramKotlinTypeMarker);
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
  
  public KotlinTypeMarker prepareType(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "type");
    if ((paramKotlinTypeMarker instanceof KotlinType)) {
      return (KotlinTypeMarker)NewKotlinTypeChecker.Companion.getDefault().transformToNewType(((KotlinType)paramKotlinTypeMarker).unwrap());
    }
    throw ((Throwable)new IllegalArgumentException(ClassicTypeCheckerContextKt.access$errorMessage(paramKotlinTypeMarker).toString()));
  }
  
  public KotlinTypeMarker refineType(KotlinTypeMarker paramKotlinTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker, "type");
    if ((paramKotlinTypeMarker instanceof KotlinType)) {
      return (KotlinTypeMarker)this.kotlinTypeRefiner.refineType((KotlinType)paramKotlinTypeMarker);
    }
    throw ((Throwable)new IllegalArgumentException(ClassicTypeCheckerContextKt.access$errorMessage(paramKotlinTypeMarker).toString()));
  }
  
  public int size(TypeArgumentListMarker paramTypeArgumentListMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "$this$size");
    return ClassicTypeSystemContext.DefaultImpls.size(this, paramTypeArgumentListMarker);
  }
  
  public AbstractTypeCheckerContext.SupertypesPolicy.DoCustomTransform substitutionSupertypePolicy(SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "type");
    return Companion.classicSubstitutionSupertypePolicy(this, paramSimpleTypeMarker);
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
  
  public static final class Companion
  {
    private Companion() {}
    
    public final AbstractTypeCheckerContext.SupertypesPolicy.DoCustomTransform classicSubstitutionSupertypePolicy(ClassicTypeSystemContext paramClassicTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker)
    {
      Intrinsics.checkParameterIsNotNull(paramClassicTypeSystemContext, "$this$classicSubstitutionSupertypePolicy");
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "type");
      if ((paramSimpleTypeMarker instanceof SimpleType)) {
        (AbstractTypeCheckerContext.SupertypesPolicy.DoCustomTransform)new AbstractTypeCheckerContext.SupertypesPolicy.DoCustomTransform()
        {
          public SimpleTypeMarker transformType(AbstractTypeCheckerContext paramAnonymousAbstractTypeCheckerContext, KotlinTypeMarker paramAnonymousKotlinTypeMarker)
          {
            Intrinsics.checkParameterIsNotNull(paramAnonymousAbstractTypeCheckerContext, "context");
            Intrinsics.checkParameterIsNotNull(paramAnonymousKotlinTypeMarker, "type");
            paramAnonymousAbstractTypeCheckerContext = this.$this_classicSubstitutionSupertypePolicy;
            TypeSubstitutor localTypeSubstitutor = this.$substitutor;
            paramAnonymousKotlinTypeMarker = paramAnonymousAbstractTypeCheckerContext.lowerBoundIfFlexible(paramAnonymousKotlinTypeMarker);
            if (paramAnonymousKotlinTypeMarker != null)
            {
              paramAnonymousKotlinTypeMarker = localTypeSubstitutor.safeSubstitute((KotlinType)paramAnonymousKotlinTypeMarker, Variance.INVARIANT);
              Intrinsics.checkExpressionValueIsNotNull(paramAnonymousKotlinTypeMarker, "substitutor.safeSubstitu…ANT\n                    )");
              paramAnonymousAbstractTypeCheckerContext = paramAnonymousAbstractTypeCheckerContext.asSimpleType((KotlinTypeMarker)paramAnonymousKotlinTypeMarker);
              if (paramAnonymousAbstractTypeCheckerContext == null) {
                Intrinsics.throwNpe();
              }
              return paramAnonymousAbstractTypeCheckerContext;
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.KotlinType");
          }
        };
      }
      throw ((Throwable)new IllegalArgumentException(ClassicTypeCheckerContextKt.access$errorMessage(paramSimpleTypeMarker).toString()));
    }
  }
}
