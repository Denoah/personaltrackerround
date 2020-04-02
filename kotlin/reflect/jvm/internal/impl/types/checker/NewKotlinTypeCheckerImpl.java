package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorImpl;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerValueTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

public final class NewKotlinTypeCheckerImpl
  implements NewKotlinTypeChecker
{
  private final KotlinTypeRefiner kotlinTypeRefiner;
  private final OverridingUtil overridingUtil;
  
  public NewKotlinTypeCheckerImpl(KotlinTypeRefiner paramKotlinTypeRefiner)
  {
    this.kotlinTypeRefiner = paramKotlinTypeRefiner;
    paramKotlinTypeRefiner = OverridingUtil.createWithTypeRefiner(getKotlinTypeRefiner());
    Intrinsics.checkExpressionValueIsNotNull(paramKotlinTypeRefiner, "OverridingUtil.createWit…efiner(kotlinTypeRefiner)");
    this.overridingUtil = paramKotlinTypeRefiner;
  }
  
  public boolean equalTypes(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType1, "a");
    Intrinsics.checkParameterIsNotNull(paramKotlinType2, "b");
    return equalTypes(new ClassicTypeCheckerContext(false, false, getKotlinTypeRefiner(), 2, null), paramKotlinType1.unwrap(), paramKotlinType2.unwrap());
  }
  
  public final boolean equalTypes(ClassicTypeCheckerContext paramClassicTypeCheckerContext, UnwrappedType paramUnwrappedType1, UnwrappedType paramUnwrappedType2)
  {
    Intrinsics.checkParameterIsNotNull(paramClassicTypeCheckerContext, "$this$equalTypes");
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType1, "a");
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType2, "b");
    return AbstractTypeChecker.INSTANCE.equalTypes((AbstractTypeCheckerContext)paramClassicTypeCheckerContext, (KotlinTypeMarker)paramUnwrappedType1, (KotlinTypeMarker)paramUnwrappedType2);
  }
  
  public KotlinTypeRefiner getKotlinTypeRefiner()
  {
    return this.kotlinTypeRefiner;
  }
  
  public OverridingUtil getOverridingUtil()
  {
    return this.overridingUtil;
  }
  
  public boolean isSubtypeOf(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType1, "subtype");
    Intrinsics.checkParameterIsNotNull(paramKotlinType2, "supertype");
    return isSubtypeOf(new ClassicTypeCheckerContext(true, false, getKotlinTypeRefiner(), 2, null), paramKotlinType1.unwrap(), paramKotlinType2.unwrap());
  }
  
  public final boolean isSubtypeOf(ClassicTypeCheckerContext paramClassicTypeCheckerContext, UnwrappedType paramUnwrappedType1, UnwrappedType paramUnwrappedType2)
  {
    Intrinsics.checkParameterIsNotNull(paramClassicTypeCheckerContext, "$this$isSubtypeOf");
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType1, "subType");
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType2, "superType");
    return AbstractTypeChecker.INSTANCE.isSubtypeOf((AbstractTypeCheckerContext)paramClassicTypeCheckerContext, (KotlinTypeMarker)paramUnwrappedType1, (KotlinTypeMarker)paramUnwrappedType2);
  }
  
  public final SimpleType transformToNewType(SimpleType paramSimpleType)
  {
    Object localObject1 = paramSimpleType;
    Intrinsics.checkParameterIsNotNull(localObject1, "type");
    Object localObject2 = paramSimpleType.getConstructor();
    boolean bool = localObject2 instanceof CapturedTypeConstructorImpl;
    int i = 1;
    CapturedTypeConstructorImpl localCapturedTypeConstructorImpl = null;
    Object localObject3 = null;
    int j = 0;
    if (bool)
    {
      localCapturedTypeConstructorImpl = (CapturedTypeConstructorImpl)localObject2;
      localObject4 = localCapturedTypeConstructorImpl.getProjection();
      if (((TypeProjection)localObject4).getProjectionKind() != Variance.IN_VARIANCE) {
        i = 0;
      }
      if (i == 0) {
        localObject4 = null;
      }
      localObject1 = localObject3;
      if (localObject4 != null)
      {
        localObject4 = ((TypeProjection)localObject4).getType();
        localObject1 = localObject3;
        if (localObject4 != null) {
          localObject1 = ((KotlinType)localObject4).unwrap();
        }
      }
      if (localCapturedTypeConstructorImpl.getNewTypeConstructor() == null)
      {
        localObject4 = localCapturedTypeConstructorImpl.getProjection();
        localObject2 = (Iterable)localCapturedTypeConstructorImpl.getSupertypes();
        localObject3 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
        localObject2 = ((Iterable)localObject2).iterator();
        while (((Iterator)localObject2).hasNext()) {
          ((Collection)localObject3).add(((KotlinType)((Iterator)localObject2).next()).unwrap());
        }
        localCapturedTypeConstructorImpl.setNewTypeConstructor(new NewCapturedTypeConstructor((TypeProjection)localObject4, (List)localObject3, null, 4, null));
      }
      localObject4 = CaptureStatus.FOR_SUBTYPING;
      localObject3 = localCapturedTypeConstructorImpl.getNewTypeConstructor();
      if (localObject3 == null) {
        Intrinsics.throwNpe();
      }
      return (SimpleType)new NewCapturedType((CaptureStatus)localObject4, (NewCapturedTypeConstructor)localObject3, (UnwrappedType)localObject1, paramSimpleType.getAnnotations(), paramSimpleType.isMarkedNullable());
    }
    if ((localObject2 instanceof IntegerValueTypeConstructor))
    {
      localObject4 = (Iterable)((IntegerValueTypeConstructor)localObject2).getSupertypes();
      localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject4, 10));
      localObject4 = ((Iterable)localObject4).iterator();
      while (((Iterator)localObject4).hasNext()) {
        ((Collection)localObject1).add(TypeUtils.makeNullableAsSpecified((KotlinType)((Iterator)localObject4).next(), paramSimpleType.isMarkedNullable()));
      }
      localObject1 = new IntersectionTypeConstructor((Collection)localObject1);
      return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(paramSimpleType.getAnnotations(), (TypeConstructor)localObject1, CollectionsKt.emptyList(), false, paramSimpleType.getMemberScope());
    }
    Object localObject4 = localObject1;
    if ((localObject2 instanceof IntersectionTypeConstructor))
    {
      localObject4 = localObject1;
      if (paramSimpleType.isMarkedNullable())
      {
        localObject1 = (IntersectionTypeConstructor)localObject2;
        localObject4 = (Iterable)((IntersectionTypeConstructor)localObject1).getSupertypes();
        paramSimpleType = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject4, 10));
        localObject4 = ((Iterable)localObject4).iterator();
        for (i = j; ((Iterator)localObject4).hasNext(); i = 1) {
          paramSimpleType.add(TypeUtilsKt.makeNullable((KotlinType)((Iterator)localObject4).next()));
        }
        paramSimpleType = (List)paramSimpleType;
        if (i == 0) {
          paramSimpleType = localCapturedTypeConstructorImpl;
        } else {
          paramSimpleType = new IntersectionTypeConstructor((Collection)paramSimpleType);
        }
        if (paramSimpleType != null) {
          localObject1 = paramSimpleType;
        }
        localObject4 = ((IntersectionTypeConstructor)localObject1).createType();
      }
    }
    return localObject4;
  }
  
  public UnwrappedType transformToNewType(UnwrappedType paramUnwrappedType)
  {
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "type");
    Object localObject;
    if ((paramUnwrappedType instanceof SimpleType))
    {
      localObject = (UnwrappedType)transformToNewType((SimpleType)paramUnwrappedType);
    }
    else
    {
      if (!(paramUnwrappedType instanceof FlexibleType)) {
        break label100;
      }
      FlexibleType localFlexibleType = (FlexibleType)paramUnwrappedType;
      localObject = transformToNewType(localFlexibleType.getLowerBound());
      SimpleType localSimpleType = transformToNewType(localFlexibleType.getUpperBound());
      if ((localObject == localFlexibleType.getLowerBound()) && (localSimpleType == localFlexibleType.getUpperBound())) {
        localObject = paramUnwrappedType;
      } else {
        localObject = KotlinTypeFactory.flexibleType((SimpleType)localObject, localSimpleType);
      }
    }
    return TypeWithEnhancementKt.inheritEnhancement((UnwrappedType)localObject, (KotlinType)paramUnwrappedType);
    label100:
    throw new NoWhenBranchMatchedException();
  }
}
