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
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.FqNames;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;

public final class BuiltinSpecialProperties
{
  private static final Map<Name, List<Name>> GETTER_JVM_NAME_TO_PROPERTIES_SHORT_NAME_MAP;
  public static final BuiltinSpecialProperties INSTANCE = new BuiltinSpecialProperties();
  private static final Map<FqName, Name> PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP;
  private static final Set<FqName> SPECIAL_FQ_NAMES;
  private static final Set<Name> SPECIAL_SHORT_NAMES;
  
  static
  {
    Object localObject1 = KotlinBuiltIns.FQ_NAMES._enum;
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "BUILTIN_NAMES._enum");
    localObject1 = TuplesKt.to(SpecialBuiltinMembers.access$childSafe((FqNameUnsafe)localObject1, "name"), Name.identifier("name"));
    Object localObject2 = KotlinBuiltIns.FQ_NAMES._enum;
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "BUILTIN_NAMES._enum");
    localObject2 = TuplesKt.to(SpecialBuiltinMembers.access$childSafe((FqNameUnsafe)localObject2, "ordinal"), Name.identifier("ordinal"));
    Object localObject3 = KotlinBuiltIns.FQ_NAMES.collection;
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "BUILTIN_NAMES.collection");
    localObject3 = TuplesKt.to(SpecialBuiltinMembers.access$child((FqName)localObject3, "size"), Name.identifier("size"));
    Object localObject4 = KotlinBuiltIns.FQ_NAMES.map;
    Intrinsics.checkExpressionValueIsNotNull(localObject4, "BUILTIN_NAMES.map");
    localObject4 = TuplesKt.to(SpecialBuiltinMembers.access$child((FqName)localObject4, "size"), Name.identifier("size"));
    Object localObject5 = KotlinBuiltIns.FQ_NAMES.charSequence;
    Intrinsics.checkExpressionValueIsNotNull(localObject5, "BUILTIN_NAMES.charSequence");
    localObject5 = TuplesKt.to(SpecialBuiltinMembers.access$childSafe((FqNameUnsafe)localObject5, "length"), Name.identifier("length"));
    Object localObject6 = KotlinBuiltIns.FQ_NAMES.map;
    Intrinsics.checkExpressionValueIsNotNull(localObject6, "BUILTIN_NAMES.map");
    localObject6 = TuplesKt.to(SpecialBuiltinMembers.access$child((FqName)localObject6, "keys"), Name.identifier("keySet"));
    FqName localFqName = KotlinBuiltIns.FQ_NAMES.map;
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "BUILTIN_NAMES.map");
    Pair localPair = TuplesKt.to(SpecialBuiltinMembers.access$child(localFqName, "values"), Name.identifier("values"));
    localFqName = KotlinBuiltIns.FQ_NAMES.map;
    Intrinsics.checkExpressionValueIsNotNull(localFqName, "BUILTIN_NAMES.map");
    localObject1 = MapsKt.mapOf(new Pair[] { localObject1, localObject2, localObject3, localObject4, localObject5, localObject6, localPair, TuplesKt.to(SpecialBuiltinMembers.access$child(localFqName, "entries"), Name.identifier("entrySet")) });
    PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP = (Map)localObject1;
    localObject2 = (Iterable)((Map)localObject1).entrySet();
    localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject2 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (Map.Entry)((Iterator)localObject2).next();
      ((Collection)localObject1).add(new Pair(((FqName)((Map.Entry)localObject3).getKey()).shortName(), ((Map.Entry)localObject3).getValue()));
    }
    localObject1 = (Iterable)localObject1;
    localObject3 = (Map)new LinkedHashMap();
    localObject4 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject4).hasNext())
    {
      localObject5 = (Pair)((Iterator)localObject4).next();
      localObject6 = (Name)((Pair)localObject5).getSecond();
      localObject2 = ((Map)localObject3).get(localObject6);
      localObject1 = localObject2;
      if (localObject2 == null)
      {
        localObject1 = new ArrayList();
        ((Map)localObject3).put(localObject6, localObject1);
      }
      ((List)localObject1).add((Name)((Pair)localObject5).getFirst());
    }
    GETTER_JVM_NAME_TO_PROPERTIES_SHORT_NAME_MAP = (Map)localObject3;
    localObject1 = PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP.keySet();
    SPECIAL_FQ_NAMES = (Set)localObject1;
    localObject2 = (Iterable)localObject1;
    localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject2, 10));
    localObject2 = ((Iterable)localObject2).iterator();
    while (((Iterator)localObject2).hasNext()) {
      ((Collection)localObject1).add(((FqName)((Iterator)localObject2).next()).shortName());
    }
    SPECIAL_SHORT_NAMES = CollectionsKt.toSet((Iterable)localObject1);
  }
  
  private BuiltinSpecialProperties() {}
  
  private final boolean hasBuiltinSpecialPropertyFqNameImpl(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Object localObject1 = (Iterable)SPECIAL_FQ_NAMES;
    Object localObject2 = (DeclarationDescriptor)paramCallableMemberDescriptor;
    boolean bool1 = CollectionsKt.contains((Iterable)localObject1, DescriptorUtilsKt.fqNameOrNull((DeclarationDescriptor)localObject2));
    boolean bool2 = true;
    if ((bool1) && (paramCallableMemberDescriptor.getValueParameters().isEmpty())) {
      return true;
    }
    if (!KotlinBuiltIns.isBuiltIn((DeclarationDescriptor)localObject2)) {
      return false;
    }
    paramCallableMemberDescriptor = paramCallableMemberDescriptor.getOverriddenDescriptors();
    Intrinsics.checkExpressionValueIsNotNull(paramCallableMemberDescriptor, "overriddenDescriptors");
    paramCallableMemberDescriptor = (Iterable)paramCallableMemberDescriptor;
    if (((paramCallableMemberDescriptor instanceof Collection)) && (((Collection)paramCallableMemberDescriptor).isEmpty())) {}
    do
    {
      while (!((Iterator)localObject1).hasNext())
      {
        bool2 = false;
        break;
        localObject1 = paramCallableMemberDescriptor.iterator();
      }
      localObject2 = (CallableMemberDescriptor)((Iterator)localObject1).next();
      paramCallableMemberDescriptor = INSTANCE;
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "it");
    } while (!paramCallableMemberDescriptor.hasBuiltinSpecialPropertyFqName((CallableMemberDescriptor)localObject2));
    return bool2;
  }
  
  public final String getBuiltinSpecialPropertyGetterName(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "$this$getBuiltinSpecialPropertyGetterName");
    boolean bool = KotlinBuiltIns.isBuiltIn((DeclarationDescriptor)paramCallableMemberDescriptor);
    if ((_Assertions.ENABLED) && (!bool))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("This method is defined only for builtin members, but ");
      localStringBuilder.append(paramCallableMemberDescriptor);
      localStringBuilder.append(" found");
      throw ((Throwable)new AssertionError(localStringBuilder.toString()));
    }
    paramCallableMemberDescriptor = DescriptorUtilsKt.getPropertyIfAccessor(paramCallableMemberDescriptor);
    Object localObject = (Function1)getBuiltinSpecialPropertyGetterName.descriptor.1.INSTANCE;
    StringBuilder localStringBuilder = null;
    localObject = DescriptorUtilsKt.firstOverridden$default(paramCallableMemberDescriptor, false, (Function1)localObject, 1, null);
    paramCallableMemberDescriptor = localStringBuilder;
    if (localObject != null)
    {
      localObject = (Name)PROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP.get(DescriptorUtilsKt.getFqNameSafe((DeclarationDescriptor)localObject));
      paramCallableMemberDescriptor = localStringBuilder;
      if (localObject != null) {
        paramCallableMemberDescriptor = ((Name)localObject).asString();
      }
    }
    return paramCallableMemberDescriptor;
  }
  
  public final List<Name> getPropertyNameCandidatesBySpecialGetterName(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name1");
    paramName = (List)GETTER_JVM_NAME_TO_PROPERTIES_SHORT_NAME_MAP.get(paramName);
    if (paramName == null) {
      paramName = CollectionsKt.emptyList();
    }
    return paramName;
  }
  
  public final Set<Name> getSPECIAL_SHORT_NAMES$descriptors_jvm()
  {
    return SPECIAL_SHORT_NAMES;
  }
  
  public final boolean hasBuiltinSpecialPropertyFqName(CallableMemberDescriptor paramCallableMemberDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramCallableMemberDescriptor, "callableMemberDescriptor");
    if (!SPECIAL_SHORT_NAMES.contains(paramCallableMemberDescriptor.getName())) {
      return false;
    }
    return hasBuiltinSpecialPropertyFqNameImpl(paramCallableMemberDescriptor);
  }
}
