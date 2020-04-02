package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;

public final class TypeCapabilitiesKt
{
  public static final CustomTypeVariable getCustomTypeVariable(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$getCustomTypeVariable");
    paramKotlinType = paramKotlinType.unwrap();
    boolean bool = paramKotlinType instanceof CustomTypeVariable;
    Object localObject = null;
    if (!bool) {
      paramKotlinType = null;
    }
    CustomTypeVariable localCustomTypeVariable = (CustomTypeVariable)paramKotlinType;
    paramKotlinType = localObject;
    if (localCustomTypeVariable != null)
    {
      paramKotlinType = localObject;
      if (localCustomTypeVariable.isTypeVariable()) {
        paramKotlinType = localCustomTypeVariable;
      }
    }
    return paramKotlinType;
  }
  
  public static final KotlinType getSubtypeRepresentative(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$getSubtypeRepresentative");
    Object localObject1 = paramKotlinType.unwrap();
    Object localObject2 = localObject1;
    if (!(localObject1 instanceof SubtypingRepresentatives)) {
      localObject2 = null;
    }
    localObject1 = (SubtypingRepresentatives)localObject2;
    localObject2 = paramKotlinType;
    if (localObject1 != null)
    {
      localObject1 = ((SubtypingRepresentatives)localObject1).getSubTypeRepresentative();
      localObject2 = paramKotlinType;
      if (localObject1 != null) {
        localObject2 = localObject1;
      }
    }
    return localObject2;
  }
  
  public static final KotlinType getSupertypeRepresentative(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$getSupertypeRepresentative");
    Object localObject1 = paramKotlinType.unwrap();
    Object localObject2 = localObject1;
    if (!(localObject1 instanceof SubtypingRepresentatives)) {
      localObject2 = null;
    }
    localObject1 = (SubtypingRepresentatives)localObject2;
    localObject2 = paramKotlinType;
    if (localObject1 != null)
    {
      localObject1 = ((SubtypingRepresentatives)localObject1).getSuperTypeRepresentative();
      localObject2 = paramKotlinType;
      if (localObject1 != null) {
        localObject2 = localObject1;
      }
    }
    return localObject2;
  }
  
  public static final boolean isCustomTypeVariable(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "$this$isCustomTypeVariable");
    UnwrappedType localUnwrappedType = paramKotlinType.unwrap();
    paramKotlinType = localUnwrappedType;
    if (!(localUnwrappedType instanceof CustomTypeVariable)) {
      paramKotlinType = null;
    }
    paramKotlinType = (CustomTypeVariable)paramKotlinType;
    boolean bool;
    if (paramKotlinType != null) {
      bool = paramKotlinType.isTypeVariable();
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean sameTypeConstructors(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType1, "first");
    Intrinsics.checkParameterIsNotNull(paramKotlinType2, "second");
    Object localObject1 = paramKotlinType1.unwrap();
    boolean bool1 = localObject1 instanceof SubtypingRepresentatives;
    Object localObject2 = null;
    if (!bool1) {
      localObject1 = null;
    }
    localObject1 = (SubtypingRepresentatives)localObject1;
    boolean bool2 = false;
    if (localObject1 != null) {
      bool1 = ((SubtypingRepresentatives)localObject1).sameTypeConstructor(paramKotlinType2);
    } else {
      bool1 = false;
    }
    if (!bool1)
    {
      paramKotlinType2 = paramKotlinType2.unwrap();
      if (!(paramKotlinType2 instanceof SubtypingRepresentatives)) {
        paramKotlinType2 = localObject2;
      }
      paramKotlinType2 = (SubtypingRepresentatives)paramKotlinType2;
      if (paramKotlinType2 != null) {
        bool1 = paramKotlinType2.sameTypeConstructor(paramKotlinType1);
      } else {
        bool1 = false;
      }
      if (!bool1) {}
    }
    else
    {
      bool2 = true;
    }
    return bool2;
  }
}
