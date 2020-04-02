package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;

public final class BuiltinMethodsWithDifferentJvmName
{
  public static final BuiltinMethodsWithDifferentJvmName INSTANCE = new BuiltinMethodsWithDifferentJvmName();
  private static final Map<Name, List<Name>> JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP;
  private static final Map<NameAndSignature, Name> NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP;
  private static final List<Name> ORIGINAL_SHORT_NAMES;
  private static final NameAndSignature REMOVE_AT_NAME_AND_SIGNATURE;
  private static final Map<String, Name> SIGNATURE_TO_JVM_REPRESENTATION_NAME;
  
  static
  {
    Object localObject1 = JvmPrimitiveType.INT.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "JvmPrimitiveType.INT.desc");
    REMOVE_AT_NAME_AND_SIGNATURE = SpecialBuiltinMembers.access$method("java/util/List", "removeAt", (String)localObject1, "Ljava/lang/Object;");
    Object localObject2 = SignatureBuildingComponents.INSTANCE;
    localObject1 = ((SignatureBuildingComponents)localObject2).javaLang("Number");
    Object localObject3 = JvmPrimitiveType.BYTE.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "JvmPrimitiveType.BYTE.desc");
    localObject1 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject1, "toByte", "", (String)localObject3), Name.identifier("byteValue"));
    Object localObject4 = ((SignatureBuildingComponents)localObject2).javaLang("Number");
    localObject3 = JvmPrimitiveType.SHORT.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "JvmPrimitiveType.SHORT.desc");
    localObject3 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject4, "toShort", "", (String)localObject3), Name.identifier("shortValue"));
    Object localObject5 = ((SignatureBuildingComponents)localObject2).javaLang("Number");
    localObject4 = JvmPrimitiveType.INT.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject4, "JvmPrimitiveType.INT.desc");
    localObject4 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject5, "toInt", "", (String)localObject4), Name.identifier("intValue"));
    localObject5 = ((SignatureBuildingComponents)localObject2).javaLang("Number");
    Object localObject6 = JvmPrimitiveType.LONG.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject6, "JvmPrimitiveType.LONG.desc");
    localObject5 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject5, "toLong", "", (String)localObject6), Name.identifier("longValue"));
    Object localObject7 = ((SignatureBuildingComponents)localObject2).javaLang("Number");
    localObject6 = JvmPrimitiveType.FLOAT.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject6, "JvmPrimitiveType.FLOAT.desc");
    localObject6 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject7, "toFloat", "", (String)localObject6), Name.identifier("floatValue"));
    Object localObject8 = ((SignatureBuildingComponents)localObject2).javaLang("Number");
    localObject7 = JvmPrimitiveType.DOUBLE.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject7, "JvmPrimitiveType.DOUBLE.desc");
    localObject7 = TuplesKt.to(SpecialBuiltinMembers.access$method((String)localObject8, "toDouble", "", (String)localObject7), Name.identifier("doubleValue"));
    localObject8 = TuplesKt.to(REMOVE_AT_NAME_AND_SIGNATURE, Name.identifier("remove"));
    String str1 = ((SignatureBuildingComponents)localObject2).javaLang("CharSequence");
    String str2 = JvmPrimitiveType.INT.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(str2, "JvmPrimitiveType.INT.desc");
    localObject2 = JvmPrimitiveType.CHAR.getDesc();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "JvmPrimitiveType.CHAR.desc");
    localObject2 = MapsKt.mapOf(new Pair[] { localObject1, localObject3, localObject4, localObject5, localObject6, localObject7, localObject8, TuplesKt.to(SpecialBuiltinMembers.access$method(str1, "get", str2, (String)localObject2), Name.identifier("charAt")) });
    NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP = (Map)localObject2;
    localObject1 = (Map)new LinkedHashMap(MapsKt.mapCapacity(((Map)localObject2).size()));
    localObject2 = ((Iterable)((Map)localObject2).entrySet()).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (Map.Entry)((Iterator)localObject2).next();
      ((Map)localObject1).put(((NameAndSignature)((Map.Entry)localObject3).getKey()).getSignature(), ((Map.Entry)localObject3).getValue());
    }
    SIGNATURE_TO_JVM_REPRESENTATION_NAME = (Map)localObject1;
    localObject2 = (Iterable)NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP.keySet();
    localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject2 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject2).hasNext()) {
      ((Collection)localObject1).add(((NameAndSignature)((Iterator)localObject2).next()).getName());
    }
    ORIGINAL_SHORT_NAMES = (List)localObject1;
    localObject2 = (Iterable)NAME_AND_SIGNATURE_TO_JVM_REPRESENTATION_NAME_MAP.entrySet();
    localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject3 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject3).hasNext())
    {
      localObject2 = (Map.Entry)((Iterator)localObject3).next();
      ((Collection)localObject1).add(new Pair(((NameAndSignature)((Map.Entry)localObject2).getKey()).getName(), ((Map.Entry)localObject2).getValue()));
    }
    localObject1 = (Iterable)localObject1;
    localObject3 = (Map)new LinkedHashMap();
    localObject6 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject6).hasNext())
    {
      localObject4 = (Pair)((Iterator)localObject6).next();
      localObject5 = (Name)((Pair)localObject4).getSecond();
      localObject2 = ((Map)localObject3).get(localObject5);
      localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = new ArrayList();
        ((Map)localObject3).put(localObject5, localObject1);
      }
      ((List)localObject1).add((Name)((Pair)localObject4).getFirst());
    }
    JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP = (Map)localObject3;
  }
  
  private BuiltinMethodsWithDifferentJvmName() {}
  
  public final List<Name> getBuiltinFunctionNamesByJvmName(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    paramName = (List)JVM_SHORT_NAME_TO_BUILTIN_SHORT_NAMES_MAP.get(paramName);
    if (paramName == null) {
      paramName = CollectionsKt.emptyList();
    }
    return paramName;
  }
  
  public final Name getJvmName(SimpleFunctionDescriptor paramSimpleFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleFunctionDescriptor, "functionDescriptor");
    Map localMap = SIGNATURE_TO_JVM_REPRESENTATION_NAME;
    paramSimpleFunctionDescriptor = MethodSignatureMappingKt.computeJvmSignature((CallableDescriptor)paramSimpleFunctionDescriptor);
    if (paramSimpleFunctionDescriptor != null) {
      return (Name)localMap.get(paramSimpleFunctionDescriptor);
    }
    return null;
  }
  
  public final List<Name> getORIGINAL_SHORT_NAMES()
  {
    return ORIGINAL_SHORT_NAMES;
  }
  
  public final boolean getSameAsRenamedInJvmBuiltin(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "$this$sameAsRenamedInJvmBuiltin");
    return ORIGINAL_SHORT_NAMES.contains(paramName);
  }
  
  public final boolean isBuiltinFunctionWithDifferentNameInJvm(SimpleFunctionDescriptor paramSimpleFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleFunctionDescriptor, "functionDescriptor");
    boolean bool1 = KotlinBuiltIns.isBuiltIn((DeclarationDescriptor)paramSimpleFunctionDescriptor);
    boolean bool2 = true;
    if ((!bool1) || (DescriptorUtilsKt.firstOverridden$default((CallableMemberDescriptor)paramSimpleFunctionDescriptor, false, (Function1)new Lambda(paramSimpleFunctionDescriptor)
    {
      public final boolean invoke(CallableMemberDescriptor paramAnonymousCallableMemberDescriptor)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousCallableMemberDescriptor, "it");
        paramAnonymousCallableMemberDescriptor = BuiltinMethodsWithDifferentJvmName.access$getSIGNATURE_TO_JVM_REPRESENTATION_NAME$p(BuiltinMethodsWithDifferentJvmName.INSTANCE);
        String str = MethodSignatureMappingKt.computeJvmSignature((CallableDescriptor)this.$functionDescriptor);
        if (paramAnonymousCallableMemberDescriptor != null) {
          return paramAnonymousCallableMemberDescriptor.containsKey(str);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
      }
    }, 1, null) == null)) {
      bool2 = false;
    }
    return bool2;
  }
  
  public final boolean isRemoveAtByIndex(SimpleFunctionDescriptor paramSimpleFunctionDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramSimpleFunctionDescriptor, "$this$isRemoveAtByIndex");
    boolean bool;
    if ((Intrinsics.areEqual(paramSimpleFunctionDescriptor.getName().asString(), "removeAt")) && (Intrinsics.areEqual(MethodSignatureMappingKt.computeJvmSignature((CallableDescriptor)paramSimpleFunctionDescriptor), REMOVE_AT_NAME_AND_SIGNATURE.getSignature()))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}
