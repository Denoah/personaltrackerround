package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.CompanionObjectMapping;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.FqNames;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.FqNamesUtilKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmPrimitiveType;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.text.StringsKt;

public final class JavaToKotlinClassMap
{
  private static final ClassId FUNCTION_N_CLASS_ID;
  private static final FqName FUNCTION_N_FQ_NAME;
  public static final JavaToKotlinClassMap INSTANCE;
  private static final ClassId K_FUNCTION_CLASS_ID;
  private static final String NUMBERED_FUNCTION_PREFIX;
  private static final String NUMBERED_K_FUNCTION_PREFIX;
  private static final String NUMBERED_K_SUSPEND_FUNCTION_PREFIX;
  private static final String NUMBERED_SUSPEND_FUNCTION_PREFIX;
  private static final HashMap<FqNameUnsafe, ClassId> javaToKotlin;
  private static final HashMap<FqNameUnsafe, ClassId> kotlinToJava;
  private static final List<PlatformMutabilityMapping> mutabilityMappings;
  private static final HashMap<FqNameUnsafe, FqName> mutableToReadOnly;
  private static final HashMap<FqNameUnsafe, FqName> readOnlyToMutable;
  
  static
  {
    JavaToKotlinClassMap localJavaToKotlinClassMap = new JavaToKotlinClassMap();
    INSTANCE = localJavaToKotlinClassMap;
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(FunctionClassDescriptor.Kind.Function.getPackageFqName().toString());
    ((StringBuilder)localObject1).append(".");
    ((StringBuilder)localObject1).append(FunctionClassDescriptor.Kind.Function.getClassNamePrefix());
    NUMBERED_FUNCTION_PREFIX = ((StringBuilder)localObject1).toString();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(FunctionClassDescriptor.Kind.KFunction.getPackageFqName().toString());
    ((StringBuilder)localObject1).append(".");
    ((StringBuilder)localObject1).append(FunctionClassDescriptor.Kind.KFunction.getClassNamePrefix());
    NUMBERED_K_FUNCTION_PREFIX = ((StringBuilder)localObject1).toString();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(FunctionClassDescriptor.Kind.SuspendFunction.getPackageFqName().toString());
    ((StringBuilder)localObject1).append(".");
    ((StringBuilder)localObject1).append(FunctionClassDescriptor.Kind.SuspendFunction.getClassNamePrefix());
    NUMBERED_SUSPEND_FUNCTION_PREFIX = ((StringBuilder)localObject1).toString();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(FunctionClassDescriptor.Kind.KSuspendFunction.getPackageFqName().toString());
    ((StringBuilder)localObject1).append(".");
    ((StringBuilder)localObject1).append(FunctionClassDescriptor.Kind.KSuspendFunction.getClassNamePrefix());
    NUMBERED_K_SUSPEND_FUNCTION_PREFIX = ((StringBuilder)localObject1).toString();
    localObject1 = ClassId.topLevel(new FqName("kotlin.jvm.functions.FunctionN"));
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "ClassId.topLevel(FqName(…vm.functions.FunctionN\"))");
    FUNCTION_N_CLASS_ID = (ClassId)localObject1;
    localObject1 = ((ClassId)localObject1).asSingleFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "FUNCTION_N_CLASS_ID.asSingleFqName()");
    FUNCTION_N_FQ_NAME = (FqName)localObject1;
    localObject1 = ClassId.topLevel(new FqName("kotlin.reflect.KFunction"));
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "ClassId.topLevel(FqName(…tlin.reflect.KFunction\"))");
    K_FUNCTION_CLASS_ID = (ClassId)localObject1;
    javaToKotlin = new HashMap();
    kotlinToJava = new HashMap();
    mutableToReadOnly = new HashMap();
    readOnlyToMutable = new HashMap();
    localObject1 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.iterable);
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "ClassId.topLevel(FQ_NAMES.iterable)");
    Object localObject2 = KotlinBuiltIns.FQ_NAMES.mutableIterable;
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "FQ_NAMES.mutableIterable");
    Object localObject3 = ((ClassId)localObject1).getPackageFqName();
    Object localObject4 = ((ClassId)localObject1).getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject4, "kotlinReadOnly.packageFqName");
    localObject4 = FqNamesUtilKt.tail((FqName)localObject2, (FqName)localObject4);
    int i = 0;
    localObject3 = new ClassId((FqName)localObject3, (FqName)localObject4, false);
    localObject1 = new PlatformMutabilityMapping(access$classId(localJavaToKotlinClassMap, Iterable.class), (ClassId)localObject1, (ClassId)localObject3);
    localObject3 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.iterator);
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "ClassId.topLevel(FQ_NAMES.iterator)");
    Object localObject5 = KotlinBuiltIns.FQ_NAMES.mutableIterator;
    Intrinsics.checkExpressionValueIsNotNull(localObject5, "FQ_NAMES.mutableIterator");
    localObject4 = ((ClassId)localObject3).getPackageFqName();
    localObject2 = ((ClassId)localObject3).getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "kotlinReadOnly.packageFqName");
    localObject4 = new ClassId((FqName)localObject4, FqNamesUtilKt.tail((FqName)localObject5, (FqName)localObject2), false);
    localObject3 = new PlatformMutabilityMapping(access$classId(localJavaToKotlinClassMap, Iterator.class), (ClassId)localObject3, (ClassId)localObject4);
    localObject4 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.collection);
    Intrinsics.checkExpressionValueIsNotNull(localObject4, "ClassId.topLevel(FQ_NAMES.collection)");
    localObject5 = KotlinBuiltIns.FQ_NAMES.mutableCollection;
    Intrinsics.checkExpressionValueIsNotNull(localObject5, "FQ_NAMES.mutableCollection");
    localObject2 = ((ClassId)localObject4).getPackageFqName();
    Object localObject6 = ((ClassId)localObject4).getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject6, "kotlinReadOnly.packageFqName");
    localObject2 = new ClassId((FqName)localObject2, FqNamesUtilKt.tail((FqName)localObject5, (FqName)localObject6), false);
    localObject4 = new PlatformMutabilityMapping(access$classId(localJavaToKotlinClassMap, Collection.class), (ClassId)localObject4, (ClassId)localObject2);
    localObject2 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.list);
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "ClassId.topLevel(FQ_NAMES.list)");
    localObject6 = KotlinBuiltIns.FQ_NAMES.mutableList;
    Intrinsics.checkExpressionValueIsNotNull(localObject6, "FQ_NAMES.mutableList");
    localObject5 = ((ClassId)localObject2).getPackageFqName();
    Object localObject7 = ((ClassId)localObject2).getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject7, "kotlinReadOnly.packageFqName");
    localObject5 = new ClassId((FqName)localObject5, FqNamesUtilKt.tail((FqName)localObject6, (FqName)localObject7), false);
    localObject2 = new PlatformMutabilityMapping(access$classId(localJavaToKotlinClassMap, List.class), (ClassId)localObject2, (ClassId)localObject5);
    localObject5 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.set);
    Intrinsics.checkExpressionValueIsNotNull(localObject5, "ClassId.topLevel(FQ_NAMES.set)");
    localObject7 = KotlinBuiltIns.FQ_NAMES.mutableSet;
    Intrinsics.checkExpressionValueIsNotNull(localObject7, "FQ_NAMES.mutableSet");
    Object localObject8 = ((ClassId)localObject5).getPackageFqName();
    localObject6 = ((ClassId)localObject5).getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject6, "kotlinReadOnly.packageFqName");
    localObject6 = new ClassId((FqName)localObject8, FqNamesUtilKt.tail((FqName)localObject7, (FqName)localObject6), false);
    localObject5 = new PlatformMutabilityMapping(access$classId(localJavaToKotlinClassMap, Set.class), (ClassId)localObject5, (ClassId)localObject6);
    localObject6 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.listIterator);
    Intrinsics.checkExpressionValueIsNotNull(localObject6, "ClassId.topLevel(FQ_NAMES.listIterator)");
    localObject7 = KotlinBuiltIns.FQ_NAMES.mutableListIterator;
    Intrinsics.checkExpressionValueIsNotNull(localObject7, "FQ_NAMES.mutableListIterator");
    localObject8 = ((ClassId)localObject6).getPackageFqName();
    Object localObject9 = ((ClassId)localObject6).getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject9, "kotlinReadOnly.packageFqName");
    localObject7 = new ClassId((FqName)localObject8, FqNamesUtilKt.tail((FqName)localObject7, (FqName)localObject9), false);
    localObject6 = new PlatformMutabilityMapping(access$classId(localJavaToKotlinClassMap, ListIterator.class), (ClassId)localObject6, (ClassId)localObject7);
    localObject7 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.map);
    Intrinsics.checkExpressionValueIsNotNull(localObject7, "ClassId.topLevel(FQ_NAMES.map)");
    FqName localFqName1 = KotlinBuiltIns.FQ_NAMES.mutableMap;
    Intrinsics.checkExpressionValueIsNotNull(localFqName1, "FQ_NAMES.mutableMap");
    localObject8 = ((ClassId)localObject7).getPackageFqName();
    localObject9 = ((ClassId)localObject7).getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject9, "kotlinReadOnly.packageFqName");
    localObject8 = new ClassId((FqName)localObject8, FqNamesUtilKt.tail(localFqName1, (FqName)localObject9), false);
    localObject7 = new PlatformMutabilityMapping(access$classId(localJavaToKotlinClassMap, Map.class), (ClassId)localObject7, (ClassId)localObject8);
    localObject8 = ClassId.topLevel(KotlinBuiltIns.FQ_NAMES.map).createNestedClassId(KotlinBuiltIns.FQ_NAMES.mapEntry.shortName());
    Intrinsics.checkExpressionValueIsNotNull(localObject8, "ClassId.topLevel(FQ_NAME…MES.mapEntry.shortName())");
    localFqName1 = KotlinBuiltIns.FQ_NAMES.mutableMapEntry;
    Intrinsics.checkExpressionValueIsNotNull(localFqName1, "FQ_NAMES.mutableMapEntry");
    localObject9 = ((ClassId)localObject8).getPackageFqName();
    FqName localFqName2 = ((ClassId)localObject8).getPackageFqName();
    Intrinsics.checkExpressionValueIsNotNull(localFqName2, "kotlinReadOnly.packageFqName");
    localObject9 = new ClassId((FqName)localObject9, FqNamesUtilKt.tail(localFqName1, localFqName2), false);
    mutabilityMappings = CollectionsKt.listOf(new PlatformMutabilityMapping[] { localObject1, localObject3, localObject4, localObject2, localObject5, localObject6, localObject7, new PlatformMutabilityMapping(access$classId(localJavaToKotlinClassMap, Map.Entry.class), (ClassId)localObject8, (ClassId)localObject9) });
    localObject1 = KotlinBuiltIns.FQ_NAMES.any;
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "FQ_NAMES.any");
    localJavaToKotlinClassMap.addTopLevel(Object.class, (FqNameUnsafe)localObject1);
    localObject1 = KotlinBuiltIns.FQ_NAMES.string;
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "FQ_NAMES.string");
    localJavaToKotlinClassMap.addTopLevel(String.class, (FqNameUnsafe)localObject1);
    localObject1 = KotlinBuiltIns.FQ_NAMES.charSequence;
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "FQ_NAMES.charSequence");
    localJavaToKotlinClassMap.addTopLevel(CharSequence.class, (FqNameUnsafe)localObject1);
    localObject1 = KotlinBuiltIns.FQ_NAMES.throwable;
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "FQ_NAMES.throwable");
    localJavaToKotlinClassMap.addTopLevel(Throwable.class, (FqName)localObject1);
    localObject1 = KotlinBuiltIns.FQ_NAMES.cloneable;
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "FQ_NAMES.cloneable");
    localJavaToKotlinClassMap.addTopLevel(Cloneable.class, (FqNameUnsafe)localObject1);
    localObject1 = KotlinBuiltIns.FQ_NAMES.number;
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "FQ_NAMES.number");
    localJavaToKotlinClassMap.addTopLevel(Number.class, (FqNameUnsafe)localObject1);
    localObject1 = KotlinBuiltIns.FQ_NAMES.comparable;
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "FQ_NAMES.comparable");
    localJavaToKotlinClassMap.addTopLevel(Comparable.class, (FqName)localObject1);
    localObject1 = KotlinBuiltIns.FQ_NAMES._enum;
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "FQ_NAMES._enum");
    localJavaToKotlinClassMap.addTopLevel(Enum.class, (FqNameUnsafe)localObject1);
    localObject1 = KotlinBuiltIns.FQ_NAMES.annotation;
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "FQ_NAMES.annotation");
    localJavaToKotlinClassMap.addTopLevel(Annotation.class, (FqName)localObject1);
    localObject1 = mutabilityMappings.iterator();
    while (((Iterator)localObject1).hasNext()) {
      localJavaToKotlinClassMap.addMapping((PlatformMutabilityMapping)((Iterator)localObject1).next());
    }
    for (localObject4 : JvmPrimitiveType.values())
    {
      localObject3 = ClassId.topLevel(((JvmPrimitiveType)localObject4).getWrapperFqName());
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "ClassId.topLevel(jvmType.wrapperFqName)");
      localObject4 = ClassId.topLevel(KotlinBuiltIns.getPrimitiveFqName(((JvmPrimitiveType)localObject4).getPrimitiveType()));
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "ClassId.topLevel(KotlinB…e(jvmType.primitiveType))");
      localJavaToKotlinClassMap.add((ClassId)localObject3, (ClassId)localObject4);
    }
    localObject1 = CompanionObjectMapping.INSTANCE.allClassesWithIntrinsicCompanions().iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject4 = (ClassId)((Iterator)localObject1).next();
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("kotlin.jvm.internal.");
      ((StringBuilder)localObject3).append(((ClassId)localObject4).getShortClassName().asString());
      ((StringBuilder)localObject3).append("CompanionObject");
      localObject3 = ClassId.topLevel(new FqName(((StringBuilder)localObject3).toString()));
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "ClassId.topLevel(FqName(…g() + \"CompanionObject\"))");
      localObject4 = ((ClassId)localObject4).createNestedClassId(SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT);
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "classId.createNestedClas…AME_FOR_COMPANION_OBJECT)");
      localJavaToKotlinClassMap.add((ClassId)localObject3, (ClassId)localObject4);
    }
    for (??? = 0; ??? < 23; ???++)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("kotlin.jvm.functions.Function");
      ((StringBuilder)localObject1).append(???);
      localObject3 = ClassId.topLevel(new FqName(((StringBuilder)localObject1).toString()));
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "ClassId.topLevel(FqName(…m.functions.Function$i\"))");
      localObject1 = KotlinBuiltIns.getFunctionClassId(???);
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "KotlinBuiltIns.getFunctionClassId(i)");
      localJavaToKotlinClassMap.add((ClassId)localObject3, (ClassId)localObject1);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(NUMBERED_K_FUNCTION_PREFIX);
      ((StringBuilder)localObject1).append(???);
      localJavaToKotlinClassMap.addKotlinToJava(new FqName(((StringBuilder)localObject1).toString()), K_FUNCTION_CLASS_ID);
    }
    for (??? = i; ??? < 22; ???++)
    {
      localObject3 = FunctionClassDescriptor.Kind.KSuspendFunction;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(((FunctionClassDescriptor.Kind)localObject3).getPackageFqName().toString());
      ((StringBuilder)localObject1).append(".");
      ((StringBuilder)localObject1).append(((FunctionClassDescriptor.Kind)localObject3).getClassNamePrefix());
      localObject3 = ((StringBuilder)localObject1).toString();
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject3);
      ((StringBuilder)localObject1).append(???);
      localJavaToKotlinClassMap.addKotlinToJava(new FqName(((StringBuilder)localObject1).toString()), K_FUNCTION_CLASS_ID);
    }
    localObject1 = KotlinBuiltIns.FQ_NAMES.nothing.toSafe();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "FQ_NAMES.nothing.toSafe()");
    localJavaToKotlinClassMap.addKotlinToJava((FqName)localObject1, localJavaToKotlinClassMap.classId(Void.class));
  }
  
  private JavaToKotlinClassMap() {}
  
  private final void add(ClassId paramClassId1, ClassId paramClassId2)
  {
    addJavaToKotlin(paramClassId1, paramClassId2);
    paramClassId2 = paramClassId2.asSingleFqName();
    Intrinsics.checkExpressionValueIsNotNull(paramClassId2, "kotlinClassId.asSingleFqName()");
    addKotlinToJava(paramClassId2, paramClassId1);
  }
  
  private final void addJavaToKotlin(ClassId paramClassId1, ClassId paramClassId2)
  {
    Map localMap = (Map)javaToKotlin;
    paramClassId1 = paramClassId1.asSingleFqName().toUnsafe();
    Intrinsics.checkExpressionValueIsNotNull(paramClassId1, "javaClassId.asSingleFqName().toUnsafe()");
    localMap.put(paramClassId1, paramClassId2);
  }
  
  private final void addKotlinToJava(FqName paramFqName, ClassId paramClassId)
  {
    Map localMap = (Map)kotlinToJava;
    paramFqName = paramFqName.toUnsafe();
    Intrinsics.checkExpressionValueIsNotNull(paramFqName, "kotlinFqNameUnsafe.toUnsafe()");
    localMap.put(paramFqName, paramClassId);
  }
  
  private final void addMapping(PlatformMutabilityMapping paramPlatformMutabilityMapping)
  {
    Object localObject1 = paramPlatformMutabilityMapping.component1();
    Object localObject2 = paramPlatformMutabilityMapping.component2();
    paramPlatformMutabilityMapping = paramPlatformMutabilityMapping.component3();
    add((ClassId)localObject1, (ClassId)localObject2);
    Object localObject3 = paramPlatformMutabilityMapping.asSingleFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject3, "mutableClassId.asSingleFqName()");
    addKotlinToJava((FqName)localObject3, (ClassId)localObject1);
    localObject1 = ((ClassId)localObject2).asSingleFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "readOnlyClassId.asSingleFqName()");
    localObject2 = paramPlatformMutabilityMapping.asSingleFqName();
    Intrinsics.checkExpressionValueIsNotNull(localObject2, "mutableClassId.asSingleFqName()");
    localObject3 = (Map)mutableToReadOnly;
    paramPlatformMutabilityMapping = paramPlatformMutabilityMapping.asSingleFqName().toUnsafe();
    Intrinsics.checkExpressionValueIsNotNull(paramPlatformMutabilityMapping, "mutableClassId.asSingleFqName().toUnsafe()");
    ((Map)localObject3).put(paramPlatformMutabilityMapping, localObject1);
    paramPlatformMutabilityMapping = (Map)readOnlyToMutable;
    localObject1 = ((FqName)localObject1).toUnsafe();
    Intrinsics.checkExpressionValueIsNotNull(localObject1, "readOnlyFqName.toUnsafe()");
    paramPlatformMutabilityMapping.put(localObject1, localObject2);
  }
  
  private final void addTopLevel(Class<?> paramClass, FqName paramFqName)
  {
    paramClass = classId(paramClass);
    paramFqName = ClassId.topLevel(paramFqName);
    Intrinsics.checkExpressionValueIsNotNull(paramFqName, "ClassId.topLevel(kotlinFqName)");
    add(paramClass, paramFqName);
  }
  
  private final void addTopLevel(Class<?> paramClass, FqNameUnsafe paramFqNameUnsafe)
  {
    paramFqNameUnsafe = paramFqNameUnsafe.toSafe();
    Intrinsics.checkExpressionValueIsNotNull(paramFqNameUnsafe, "kotlinFqName.toSafe()");
    addTopLevel(paramClass, paramFqNameUnsafe);
  }
  
  private final ClassId classId(Class<?> paramClass)
  {
    int i;
    if ((!paramClass.isPrimitive()) && (!paramClass.isArray())) {
      i = 1;
    } else {
      i = 0;
    }
    if ((_Assertions.ENABLED) && (i == 0))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Invalid class: ");
      ((StringBuilder)localObject).append(paramClass);
      throw ((Throwable)new AssertionError(((StringBuilder)localObject).toString()));
    }
    Object localObject = paramClass.getDeclaringClass();
    if (localObject == null)
    {
      paramClass = ClassId.topLevel(new FqName(paramClass.getCanonicalName()));
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "ClassId.topLevel(FqName(clazz.canonicalName))");
    }
    else
    {
      paramClass = classId((Class)localObject).createNestedClassId(Name.identifier(paramClass.getSimpleName()));
      Intrinsics.checkExpressionValueIsNotNull(paramClass, "classId(outer).createNes…tifier(clazz.simpleName))");
    }
    return paramClass;
  }
  
  private final ClassDescriptor convertToOppositeMutability(ClassDescriptor paramClassDescriptor, Map<FqNameUnsafe, FqName> paramMap, String paramString)
  {
    DeclarationDescriptor localDeclarationDescriptor = (DeclarationDescriptor)paramClassDescriptor;
    paramMap = (FqName)paramMap.get(DescriptorUtils.getFqName(localDeclarationDescriptor));
    if (paramMap != null)
    {
      paramClassDescriptor = DescriptorUtilsKt.getBuiltIns(localDeclarationDescriptor).getBuiltInClassByFqName(paramMap);
      Intrinsics.checkExpressionValueIsNotNull(paramClassDescriptor, "descriptor.builtIns.getB…Name(oppositeClassFqName)");
      return paramClassDescriptor;
    }
    paramMap = new StringBuilder();
    paramMap.append("Given class ");
    paramMap.append(paramClassDescriptor);
    paramMap.append(" is not a ");
    paramMap.append(paramString);
    paramMap.append(" collection");
    throw ((Throwable)new IllegalArgumentException(paramMap.toString()));
  }
  
  private final boolean isKotlinFunctionWithBigArity(FqNameUnsafe paramFqNameUnsafe, String paramString)
  {
    paramFqNameUnsafe = paramFqNameUnsafe.asString();
    Intrinsics.checkExpressionValueIsNotNull(paramFqNameUnsafe, "kotlinFqName.asString()");
    paramFqNameUnsafe = StringsKt.substringAfter(paramFqNameUnsafe, paramString, "");
    paramString = (CharSequence)paramFqNameUnsafe;
    int i = paramString.length();
    boolean bool = true;
    if (i > 0) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i != 0) && (!StringsKt.startsWith$default(paramString, '0', false, 2, null)))
    {
      paramFqNameUnsafe = StringsKt.toIntOrNull(paramFqNameUnsafe);
      if ((paramFqNameUnsafe == null) || (paramFqNameUnsafe.intValue() < 23)) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public final ClassDescriptor convertMutableToReadOnly(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "mutable");
    return convertToOppositeMutability(paramClassDescriptor, (Map)mutableToReadOnly, "mutable");
  }
  
  public final ClassDescriptor convertReadOnlyToMutable(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "readOnly");
    return convertToOppositeMutability(paramClassDescriptor, (Map)readOnlyToMutable, "read-only");
  }
  
  public final FqName getFUNCTION_N_FQ_NAME()
  {
    return FUNCTION_N_FQ_NAME;
  }
  
  public final List<PlatformMutabilityMapping> getMutabilityMappings()
  {
    return mutabilityMappings;
  }
  
  public final boolean isMutable(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "mutable");
    return isMutable(DescriptorUtils.getFqName((DeclarationDescriptor)paramClassDescriptor));
  }
  
  public final boolean isMutable(FqNameUnsafe paramFqNameUnsafe)
  {
    Map localMap = (Map)mutableToReadOnly;
    if (localMap != null) {
      return localMap.containsKey(paramFqNameUnsafe);
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
  }
  
  public final boolean isMutable(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
    paramKotlinType = TypeUtils.getClassDescriptor(paramKotlinType);
    boolean bool;
    if ((paramKotlinType != null) && (isMutable(paramKotlinType))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isReadOnly(ClassDescriptor paramClassDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramClassDescriptor, "readOnly");
    return isReadOnly(DescriptorUtils.getFqName((DeclarationDescriptor)paramClassDescriptor));
  }
  
  public final boolean isReadOnly(FqNameUnsafe paramFqNameUnsafe)
  {
    Map localMap = (Map)readOnlyToMutable;
    if (localMap != null) {
      return localMap.containsKey(paramFqNameUnsafe);
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
  }
  
  public final boolean isReadOnly(KotlinType paramKotlinType)
  {
    Intrinsics.checkParameterIsNotNull(paramKotlinType, "type");
    paramKotlinType = TypeUtils.getClassDescriptor(paramKotlinType);
    boolean bool;
    if ((paramKotlinType != null) && (isReadOnly(paramKotlinType))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final ClassDescriptor mapJavaToKotlin(FqName paramFqName, KotlinBuiltIns paramKotlinBuiltIns, Integer paramInteger)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    Intrinsics.checkParameterIsNotNull(paramKotlinBuiltIns, "builtIns");
    if ((paramInteger != null) && (Intrinsics.areEqual(paramFqName, FUNCTION_N_FQ_NAME))) {
      paramFqName = KotlinBuiltIns.getFunctionClassId(paramInteger.intValue());
    } else {
      paramFqName = mapJavaToKotlin(paramFqName);
    }
    if (paramFqName != null) {
      paramFqName = paramKotlinBuiltIns.getBuiltInClassByFqName(paramFqName.asSingleFqName());
    } else {
      paramFqName = null;
    }
    return paramFqName;
  }
  
  public final ClassId mapJavaToKotlin(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    return (ClassId)javaToKotlin.get(paramFqName.toUnsafe());
  }
  
  public final ClassId mapKotlinToJava(FqNameUnsafe paramFqNameUnsafe)
  {
    Intrinsics.checkParameterIsNotNull(paramFqNameUnsafe, "kotlinFqName");
    if (isKotlinFunctionWithBigArity(paramFqNameUnsafe, NUMBERED_FUNCTION_PREFIX)) {
      paramFqNameUnsafe = FUNCTION_N_CLASS_ID;
    } else if (isKotlinFunctionWithBigArity(paramFqNameUnsafe, NUMBERED_SUSPEND_FUNCTION_PREFIX)) {
      paramFqNameUnsafe = FUNCTION_N_CLASS_ID;
    } else if (isKotlinFunctionWithBigArity(paramFqNameUnsafe, NUMBERED_K_FUNCTION_PREFIX)) {
      paramFqNameUnsafe = K_FUNCTION_CLASS_ID;
    } else if (isKotlinFunctionWithBigArity(paramFqNameUnsafe, NUMBERED_K_SUSPEND_FUNCTION_PREFIX)) {
      paramFqNameUnsafe = K_FUNCTION_CLASS_ID;
    } else {
      paramFqNameUnsafe = (ClassId)kotlinToJava.get(paramFqNameUnsafe);
    }
    return paramFqNameUnsafe;
  }
  
  public final Collection<ClassDescriptor> mapPlatformClass(FqName paramFqName, KotlinBuiltIns paramKotlinBuiltIns)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    Intrinsics.checkParameterIsNotNull(paramKotlinBuiltIns, "builtIns");
    paramFqName = mapJavaToKotlin$default(this, paramFqName, paramKotlinBuiltIns, null, 4, null);
    if (paramFqName != null)
    {
      FqName localFqName = (FqName)readOnlyToMutable.get(DescriptorUtilsKt.getFqNameUnsafe((DeclarationDescriptor)paramFqName));
      if (localFqName != null)
      {
        Intrinsics.checkExpressionValueIsNotNull(localFqName, "readOnlyToMutable[kotlin…eturn setOf(kotlinAnalog)");
        paramKotlinBuiltIns = paramKotlinBuiltIns.getBuiltInClassByFqName(localFqName);
        Intrinsics.checkExpressionValueIsNotNull(paramKotlinBuiltIns, "builtIns.getBuiltInClass…otlinMutableAnalogFqName)");
        return (Collection)CollectionsKt.listOf(new ClassDescriptor[] { paramFqName, paramKotlinBuiltIns });
      }
      return (Collection)SetsKt.setOf(paramFqName);
    }
    return (Collection)SetsKt.emptySet();
  }
  
  public static final class PlatformMutabilityMapping
  {
    private final ClassId javaClass;
    private final ClassId kotlinMutable;
    private final ClassId kotlinReadOnly;
    
    public PlatformMutabilityMapping(ClassId paramClassId1, ClassId paramClassId2, ClassId paramClassId3)
    {
      this.javaClass = paramClassId1;
      this.kotlinReadOnly = paramClassId2;
      this.kotlinMutable = paramClassId3;
    }
    
    public final ClassId component1()
    {
      return this.javaClass;
    }
    
    public final ClassId component2()
    {
      return this.kotlinReadOnly;
    }
    
    public final ClassId component3()
    {
      return this.kotlinMutable;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this != paramObject) {
        if ((paramObject instanceof PlatformMutabilityMapping))
        {
          paramObject = (PlatformMutabilityMapping)paramObject;
          if ((Intrinsics.areEqual(this.javaClass, paramObject.javaClass)) && (Intrinsics.areEqual(this.kotlinReadOnly, paramObject.kotlinReadOnly)) && (Intrinsics.areEqual(this.kotlinMutable, paramObject.kotlinMutable))) {}
        }
        else
        {
          return false;
        }
      }
      return true;
    }
    
    public final ClassId getJavaClass()
    {
      return this.javaClass;
    }
    
    public int hashCode()
    {
      ClassId localClassId = this.javaClass;
      int i = 0;
      int j;
      if (localClassId != null) {
        j = localClassId.hashCode();
      } else {
        j = 0;
      }
      localClassId = this.kotlinReadOnly;
      int k;
      if (localClassId != null) {
        k = localClassId.hashCode();
      } else {
        k = 0;
      }
      localClassId = this.kotlinMutable;
      if (localClassId != null) {
        i = localClassId.hashCode();
      }
      return (j * 31 + k) * 31 + i;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("PlatformMutabilityMapping(javaClass=");
      localStringBuilder.append(this.javaClass);
      localStringBuilder.append(", kotlinReadOnly=");
      localStringBuilder.append(this.kotlinReadOnly);
      localStringBuilder.append(", kotlinMutable=");
      localStringBuilder.append(this.kotlinMutable);
      localStringBuilder.append(")");
      return localStringBuilder.toString();
    }
  }
}
