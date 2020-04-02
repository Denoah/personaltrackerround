package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

public final class SpecialTypesKt
{
  public static final AbbreviatedType getAbbreviatedType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$getAbbreviatedType");
    UnwrappedType localUnwrappedType = paramKotlinType.unwrap();
    paramKotlinType = localUnwrappedType;
    if (!(localUnwrappedType instanceof AbbreviatedType)) {
      paramKotlinType = null;
    }
    return (AbbreviatedType)paramKotlinType;
  }
  
  public static final SimpleType getAbbreviation(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$getAbbreviation");
    paramKotlinType = getAbbreviatedType(paramKotlinType);
    if (paramKotlinType != null) {
      paramKotlinType = paramKotlinType.getAbbreviation();
    } else {
      paramKotlinType = null;
    }
    return paramKotlinType;
  }
  
  public static final boolean isDefinitelyNotNullType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isDefinitelyNotNullType");
    return paramKotlinType.unwrap() instanceof DefinitelyNotNullType;
  }
  
  private static final IntersectionTypeConstructor makeDefinitelyNotNullOrNotNull(IntersectionTypeConstructor paramIntersectionTypeConstructor)
  {
    paramIntersectionTypeConstructor = (Iterable)paramIntersectionTypeConstructor.getSupertypes();
    Collection localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramIntersectionTypeConstructor, 10));
    Iterator localIterator = paramIntersectionTypeConstructor.iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      KotlinType localKotlinType = (KotlinType)localIterator.next();
      paramIntersectionTypeConstructor = localKotlinType;
      if (TypeUtils.isNullableType(localKotlinType))
      {
        i = 1;
        paramIntersectionTypeConstructor = (KotlinType)makeDefinitelyNotNullOrNotNull(localKotlinType.unwrap());
      }
      localCollection.add(paramIntersectionTypeConstructor);
    }
    paramIntersectionTypeConstructor = (List)localCollection;
    if (i == 0) {
      paramIntersectionTypeConstructor = null;
    } else {
      paramIntersectionTypeConstructor = new IntersectionTypeConstructor((Collection)paramIntersectionTypeConstructor);
    }
    return paramIntersectionTypeConstructor;
  }
  
  public static final UnwrappedType makeDefinitelyNotNullOrNotNull(UnwrappedType paramUnwrappedType)
  {
    Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "$this$makeDefinitelyNotNullOrNotNull");
    Object localObject = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$descriptors(paramUnwrappedType);
    if (localObject != null) {
      localObject = (UnwrappedType)localObject;
    } else {
      localObject = (UnwrappedType)makeIntersectionTypeDefinitelyNotNullOrNotNull((KotlinType)paramUnwrappedType);
    }
    if (localObject == null) {
      localObject = paramUnwrappedType.makeNullableAsSpecified(false);
    }
    return localObject;
  }
  
  private static final SimpleType makeIntersectionTypeDefinitelyNotNullOrNotNull(KotlinType paramKotlinType)
  {
    TypeConstructor localTypeConstructor = paramKotlinType.getConstructor();
    paramKotlinType = localTypeConstructor;
    if (!(localTypeConstructor instanceof IntersectionTypeConstructor)) {
      paramKotlinType = null;
    }
    paramKotlinType = (IntersectionTypeConstructor)paramKotlinType;
    if (paramKotlinType != null)
    {
      paramKotlinType = makeDefinitelyNotNullOrNotNull(paramKotlinType);
      if (paramKotlinType != null) {
        return paramKotlinType.createType();
      }
    }
    return null;
  }
  
  public static final SimpleType makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleType paramSimpleType)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType, "$this$makeSimpleTypeDefinitelyNotNullOrNotNull");
    Object localObject = DefinitelyNotNullType.Companion.makeDefinitelyNotNull$descriptors((UnwrappedType)paramSimpleType);
    if (localObject != null) {
      localObject = (SimpleType)localObject;
    } else {
      localObject = makeIntersectionTypeDefinitelyNotNullOrNotNull((KotlinType)paramSimpleType);
    }
    if (localObject == null) {
      localObject = paramSimpleType.makeNullableAsSpecified(false);
    }
    return localObject;
  }
  
  public static final SimpleType withAbbreviation(SimpleType paramSimpleType1, SimpleType paramSimpleType2)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleType1, "$this$withAbbreviation");
    Intrinsics.checkParameterIsNotNull(paramSimpleType2, "abbreviatedType");
    if (KotlinTypeKt.isError((KotlinType)paramSimpleType1)) {
      return paramSimpleType1;
    }
    return (SimpleType)new AbbreviatedType(paramSimpleType1, paramSimpleType2);
  }
}
