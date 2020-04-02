package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;

public final class BuiltinMethodsWithSpecialGenericSignature
{
  private static final List<String> ERASED_COLLECTION_PARAMETER_NAMES;
  private static final List<NameAndSignature> ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;
  private static final List<String> ERASED_COLLECTION_PARAMETER_SIGNATURES;
  private static final Set<Name> ERASED_VALUE_PARAMETERS_SHORT_NAMES;
  private static final Set<String> ERASED_VALUE_PARAMETERS_SIGNATURES;
  private static final Map<NameAndSignature, TypeSafeBarrierDescription> GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP;
  public static final BuiltinMethodsWithSpecialGenericSignature INSTANCE = new BuiltinMethodsWithSpecialGenericSignature();
  private static final Map<String, TypeSafeBarrierDescription> SIGNATURE_TO_DEFAULT_VALUES_MAP;
  
  static
  {
    Object localObject1 = (Iterable)SetsKt.setOf(new String[] { "containsAll", "removeAll", "retainAll" });
    Object localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
    localObject1 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject3 = (String)((Iterator)localObject1).next();
      localObject4 = JvmPrimitiveType.BOOLEAN.getDesc();
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "JvmPrimitiveType.BOOLEAN.desc");
      ((Collection)localObject2).add(SpecialBuiltinMembers.access$method("java/util/Collection", (String)localObject3, "Ljava/util/Collection;", (String)localObject4));
    }
    localObject2 = (List)localObject2;
    ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES = (List)localObject2;
    localObject1 = (Iterable)localObject2;
    localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
    localObject1 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject1).hasNext()) {
      ((Collection)localObject2).add(((NameAndSignature)((Iterator)localObject1).next()).getSignature());
    }
    ERASED_COLLECTION_PARAMETER_SIGNATURES = (List)localObject2;
    localObject1 = (Iterable)ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES;
    localObject2 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
    localObject1 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject1).hasNext()) {
      ((Collection)localObject2).add(((NameAndSignature)((Iterator)localObject1).next()).getName().asString());
    }
    ERASED_COLLECTION_PARAMETER_NAMES = (List)localObject2;
    localObject2 = SignatureBuildingComponents.INSTANCE;
    Object localObject3 = ((SignatureBuildingComponents)localObject2).javaUtil("Collection");
    localObject1 = JvmPrimitiveType.BOOLEAN.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "JvmPrimitiveType.BOOLEAN.desc");
    localObject1 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject3, "contains", "Ljava/lang/Object;", (String)localObject1), TypeSafeBarrierDescription.FALSE);
    localObject3 = ((SignatureBuildingComponents)localObject2).javaUtil("Collection");
    Object localObject4 = JvmPrimitiveType.BOOLEAN.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject4, "JvmPrimitiveType.BOOLEAN.desc");
    localObject3 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject3, "remove", "Ljava/lang/Object;", (String)localObject4), TypeSafeBarrierDescription.FALSE);
    localObject4 = ((SignatureBuildingComponents)localObject2).javaUtil("Map");
    Object localObject5 = JvmPrimitiveType.BOOLEAN.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject5, "JvmPrimitiveType.BOOLEAN.desc");
    localObject4 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject4, "containsKey", "Ljava/lang/Object;", (String)localObject5), TypeSafeBarrierDescription.FALSE);
    Object localObject6 = ((SignatureBuildingComponents)localObject2).javaUtil("Map");
    localObject5 = JvmPrimitiveType.BOOLEAN.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject5, "JvmPrimitiveType.BOOLEAN.desc");
    localObject5 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject6, "containsValue", "Ljava/lang/Object;", (String)localObject5), TypeSafeBarrierDescription.FALSE);
    Object localObject7 = ((SignatureBuildingComponents)localObject2).javaUtil("Map");
    localObject6 = JvmPrimitiveType.BOOLEAN.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject6, "JvmPrimitiveType.BOOLEAN.desc");
    Pair localPair1 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject7, "remove", "Ljava/lang/Object;Ljava/lang/Object;", (String)localObject6), TypeSafeBarrierDescription.FALSE);
    localObject7 = TuplesKt.to(SpecialBuiltinMembers.access$method(((SignatureBuildingComponents)localObject2).javaUtil("Map"), "getOrDefault", "Ljava/lang/Object;Ljava/lang/Object;", "Ljava/lang/Object;"), TypeSafeBarrierDescription.MAP_GET_OR_DEFAULT);
    localObject6 = TuplesKt.to(SpecialBuiltinMembers.access$method(((SignatureBuildingComponents)localObject2).javaUtil("Map"), "get", "Ljava/lang/Object;", "Ljava/lang/Object;"), TypeSafeBarrierDescription.NULL);
    Pair localPair2 = TuplesKt.to(SpecialBuiltinMembers.access$method(((SignatureBuildingComponents)localObject2).javaUtil("Map"), "remove", "Ljava/lang/Object;", "Ljava/lang/Object;"), TypeSafeBarrierDescription.NULL);
    Object localObject8 = ((SignatureBuildingComponents)localObject2).javaUtil("List");
    String str = JvmPrimitiveType.INT.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(str, "JvmPrimitiveType.INT.desc");
    localObject8 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject8, "indexOf", "Ljava/lang/Object;", str), TypeSafeBarrierDescription.INDEX);
    str = ((SignatureBuildingComponents)localObject2).javaUtil("List");
    localObject2 = JvmPrimitiveType.INT.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "JvmPrimitiveType.INT.desc");
    localObject1 = MapsKt.mapOf(new Pair[] { localObject1, localObject3, localObject4, localObject5, localPair1, localObject7, localObject6, localPair2, localObject8, TuplesKt.to(SpecialBuiltinMembers.access$method(str, "lastIndexOf", "Ljava/lang/Object;", (String)localObject2), TypeSafeBarrierDescription.INDEX) });
    GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP = (Map)localObject1;
    localObject2 = (Map)new LinkedHashMap(MapsKt.mapCapacity(((Map)localObject1).size()));
    localObject3 = ((Iterable)((Map)localObject1).entrySet()).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject1 = (Map.Entry)((Iterator)localObject3).next();
      ((Map)localObject2).put(((NameAndSignature)((Map.Entry)localObject1).getKey()).getSignature(), ((Map.Entry)localObject1).getValue());
    }
    SIGNATURE_TO_DEFAULT_VALUES_MAP = (Map)localObject2;
    localObject2 = (Iterable)SetsKt.plus(GENERIC_PARAMETERS_METHODS_TO_DEFAULT_VALUES_MAP.keySet(), (Iterable)ERASED_COLLECTION_PARAMETER_NAME_AND_SIGNATURES);
    localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject3 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject3).hasNext()) {
      ((Collection)localObject1).add(((NameAndSignature)((Iterator)localObject3).next()).getName());
    }
    ERASED_VALUE_PARAMETERS_SHORT_NAMES = CollectionsKt.toSet((Iterable)localObject1);
    localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject2 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject2).hasNext()) {
      ((Collection)localObject1).add(((NameAndSignature)((Iterator)localObject2).next()).getSignature());
    }
    ERASED_VALUE_PARAMETERS_SIGNATURES = CollectionsKt.toSet((Iterable)localObject1);
  }
  
  private BuiltinMethodsWithSpecialGenericSignature() {}
  
  private final boolean getHasErasedValueParametersInJava(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    return CollectionsKt.contains((Iterable)ERASED_VALUE_PARAMETERS_SIGNATURES, MethodSignatureMappingKt.computeJvmSignature((CallableDescriptor)paramCallableMemberDescriptor));
  }
  
  @JvmStatic
  public static final FunctionDescriptor getOverriddenBuiltinFunctionWithErasedValueParametersInJava(FunctionDescriptor paramFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramFunctionDescriptor, "functionDescriptor");
    BuiltinMethodsWithSpecialGenericSignature localBuiltinMethodsWithSpecialGenericSignature = INSTANCE;
    Name localName = paramFunctionDescriptor.getName();
    Intrinsics.checkExpressionValueIsNotNull(localName, "functionDescriptor.name");
    if (!localBuiltinMethodsWithSpecialGenericSignature.getSameAsBuiltinMethodWithErasedValueParameters(localName)) {
      return null;
    }
    return (FunctionDescriptor)DescriptorUtilsKt.firstOverridden$default((CallableMemberDescriptor)paramFunctionDescriptor, false, (Function1)getOverriddenBuiltinFunctionWithErasedValueParametersInJava.1.INSTANCE, 1, null);
  }
  
  @JvmStatic
  public static final SpecialSignatureInfo getSpecialSignatureInfo(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "$this$getSpecialSignatureInfo");
    if (!ERASED_VALUE_PARAMETERS_SHORT_NAMES.contains(paramCallableMemberDescriptor.getName())) {
      return null;
    }
    paramCallableMemberDescriptor = DescriptorUtilsKt.firstOverridden$default(paramCallableMemberDescriptor, false, (Function1)getSpecialSignatureInfo.builtinSignature.1.INSTANCE, 1, null);
    if (paramCallableMemberDescriptor != null)
    {
      paramCallableMemberDescriptor = MethodSignatureMappingKt.computeJvmSignature((CallableDescriptor)paramCallableMemberDescriptor);
      if (paramCallableMemberDescriptor != null)
      {
        if (ERASED_COLLECTION_PARAMETER_SIGNATURES.contains(paramCallableMemberDescriptor)) {
          return SpecialSignatureInfo.ONE_COLLECTION_PARAMETER;
        }
        if ((TypeSafeBarrierDescription)MapsKt.getValue(SIGNATURE_TO_DEFAULT_VALUES_MAP, paramCallableMemberDescriptor) == TypeSafeBarrierDescription.NULL) {
          paramCallableMemberDescriptor = SpecialSignatureInfo.OBJECT_PARAMETER_GENERIC;
        } else {
          paramCallableMemberDescriptor = SpecialSignatureInfo.OBJECT_PARAMETER_NON_GENERIC;
        }
        return paramCallableMemberDescriptor;
      }
    }
    return null;
  }
  
  public final boolean getSameAsBuiltinMethodWithErasedValueParameters(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "$this$sameAsBuiltinMethodWithErasedValueParameters");
    return ERASED_VALUE_PARAMETERS_SHORT_NAMES.contains(paramName);
  }
  
  public static enum SpecialSignatureInfo
  {
    private final boolean isObjectReplacedWithTypeParameter;
    private final String valueParametersSignature;
    
    static
    {
      SpecialSignatureInfo localSpecialSignatureInfo1 = new SpecialSignatureInfo("ONE_COLLECTION_PARAMETER", 0, "Ljava/util/Collection<+Ljava/lang/Object;>;", false);
      ONE_COLLECTION_PARAMETER = localSpecialSignatureInfo1;
      SpecialSignatureInfo localSpecialSignatureInfo2 = new SpecialSignatureInfo("OBJECT_PARAMETER_NON_GENERIC", 1, null, true);
      OBJECT_PARAMETER_NON_GENERIC = localSpecialSignatureInfo2;
      SpecialSignatureInfo localSpecialSignatureInfo3 = new SpecialSignatureInfo("OBJECT_PARAMETER_GENERIC", 2, "Ljava/lang/Object;", true);
      OBJECT_PARAMETER_GENERIC = localSpecialSignatureInfo3;
      $VALUES = new SpecialSignatureInfo[] { localSpecialSignatureInfo1, localSpecialSignatureInfo2, localSpecialSignatureInfo3 };
    }
    
    private SpecialSignatureInfo(String paramString, boolean paramBoolean)
    {
      this.valueParametersSignature = paramString;
      this.isObjectReplacedWithTypeParameter = paramBoolean;
    }
  }
  
  public static enum TypeSafeBarrierDescription
  {
    private final Object defaultValue;
    
    static
    {
      TypeSafeBarrierDescription localTypeSafeBarrierDescription1 = new TypeSafeBarrierDescription("NULL", 0, null);
      NULL = localTypeSafeBarrierDescription1;
      TypeSafeBarrierDescription localTypeSafeBarrierDescription2 = new TypeSafeBarrierDescription("INDEX", 1, Integer.valueOf(-1));
      INDEX = localTypeSafeBarrierDescription2;
      TypeSafeBarrierDescription localTypeSafeBarrierDescription3 = new TypeSafeBarrierDescription("FALSE", 2, Boolean.valueOf(false));
      FALSE = localTypeSafeBarrierDescription3;
      MAP_GET_OR_DEFAULT localMAP_GET_OR_DEFAULT = new MAP_GET_OR_DEFAULT("MAP_GET_OR_DEFAULT", 3);
      MAP_GET_OR_DEFAULT = localMAP_GET_OR_DEFAULT;
      $VALUES = new TypeSafeBarrierDescription[] { localTypeSafeBarrierDescription1, localTypeSafeBarrierDescription2, localTypeSafeBarrierDescription3, localMAP_GET_OR_DEFAULT };
    }
    
    private TypeSafeBarrierDescription(Object paramObject)
    {
      this.defaultValue = paramObject;
    }
    
    static final class MAP_GET_OR_DEFAULT
      extends BuiltinMethodsWithSpecialGenericSignature.TypeSafeBarrierDescription
    {
      MAP_GET_OR_DEFAULT()
      {
        super(i, null, null);
      }
    }
  }
}
