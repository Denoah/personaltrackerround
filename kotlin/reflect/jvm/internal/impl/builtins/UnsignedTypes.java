package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;

public final class UnsignedTypes
{
  public static final UnsignedTypes INSTANCE = new UnsignedTypes();
  private static final HashMap<ClassId, ClassId> arrayClassIdToUnsignedClassId;
  private static final Set<Name> arrayClassesShortNames;
  private static final HashMap<ClassId, ClassId> unsignedClassIdToArrayClassId;
  private static final Set<Name> unsignedTypeNames;
  
  static
  {
    Object localObject1 = UnsignedType.values();
    Object localObject2 = (Collection)new ArrayList(localObject1.length);
    int i = localObject1.length;
    int j = 0;
    for (int k = 0; k < i; k++) {
      ((Collection)localObject2).add(localObject1[k].getTypeName());
    }
    unsignedTypeNames = CollectionsKt.toSet((Iterable)localObject2);
    arrayClassIdToUnsignedClassId = new HashMap();
    unsignedClassIdToArrayClassId = new HashMap();
    localObject2 = UnsignedType.values();
    localObject1 = (Collection)new LinkedHashSet();
    i = localObject2.length;
    for (k = 0; k < i; k++) {
      ((Collection)localObject1).add(localObject2[k].getArrayClassId().getShortClassName());
    }
    arrayClassesShortNames = (Set)localObject1;
    localObject1 = UnsignedType.values();
    i = localObject1.length;
    for (k = j; k < i; k++)
    {
      localObject2 = localObject1[k];
      ((Map)arrayClassIdToUnsignedClassId).put(((UnsignedType)localObject2).getArrayClassId(), ((UnsignedType)localObject2).getClassId());
      ((Map)unsignedClassIdToArrayClassId).put(((UnsignedType)localObject2).getClassId(), ((UnsignedType)localObject2).getArrayClassId());
    }
  }
  
  private UnsignedTypes() {}
  
  public final ClassId getUnsignedClassIdByArrayClassId(ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramClassId, "arrayClassId");
    return (ClassId)arrayClassIdToUnsignedClassId.get(paramClassId);
  }
  
  public final boolean isShortNameOfUnsignedArray(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    return arrayClassesShortNames.contains(paramName);
  }
  
  public final boolean isUnsignedClass(DeclarationDescriptor paramDeclarationDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptor, "descriptor");
    DeclarationDescriptor localDeclarationDescriptor = paramDeclarationDescriptor.getContainingDeclaration();
    boolean bool;
    if (((localDeclarationDescriptor instanceof PackageFragmentDescriptor)) && (Intrinsics.areEqual(((PackageFragmentDescriptor)localDeclarationDescriptor).getFqName(), KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME)) && (unsignedTypeNames.contains(paramDeclarationDescriptor.getName()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isUnsignedType(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
    if (TypeUtils.noExpectedType(paramKotlinType)) {
      return false;
    }
    paramKotlinType = paramKotlinType.getConstructor().getDeclarationDescriptor();
    if (paramKotlinType != null)
    {
      Intrinsics.checkExpressionValueIsNotNull(paramKotlinType, "type.constructor.declara…escriptor ?: return false");
      return isUnsignedClass((DeclarationDescriptor)paramKotlinType);
    }
    return false;
  }
}
