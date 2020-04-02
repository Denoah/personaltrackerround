package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.jvm.internal.impl.builtins.functions.BuiltInFictitiousFunctionClassFactory;
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionClassDescriptor.Kind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorUtilKt;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider.None;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter.NoPlatformDependent;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.utils.CollectionsKt;

public abstract class KotlinBuiltIns
{
  private static final FqName ANNOTATION_PACKAGE_FQ_NAME;
  public static final Name BUILTINS_MODULE_NAME = Name.special("<built-ins module>");
  public static final FqName BUILT_INS_PACKAGE_FQ_NAME;
  public static final Set<FqName> BUILT_INS_PACKAGE_FQ_NAMES;
  public static final Name BUILT_INS_PACKAGE_NAME;
  public static final FqName COLLECTIONS_PACKAGE_FQ_NAME;
  public static final FqNames FQ_NAMES;
  public static final FqName RANGES_PACKAGE_FQ_NAME;
  public static final FqName TEXT_PACKAGE_FQ_NAME;
  private final MemoizedFunctionToNotNull<Name, ClassDescriptor> builtInClassesByName;
  private final NotNullLazyValue<Collection<PackageViewDescriptor>> builtInPackagesImportedByDefault;
  private ModuleDescriptorImpl builtInsModule;
  private final NotNullLazyValue<Primitives> primitives;
  private final StorageManager storageManager;
  
  static
  {
    Object localObject = Name.identifier("kotlin");
    BUILT_INS_PACKAGE_NAME = (Name)localObject;
    localObject = FqName.topLevel((Name)localObject);
    BUILT_INS_PACKAGE_FQ_NAME = (FqName)localObject;
    ANNOTATION_PACKAGE_FQ_NAME = ((FqName)localObject).child(Name.identifier("annotation"));
    COLLECTIONS_PACKAGE_FQ_NAME = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("collections"));
    RANGES_PACKAGE_FQ_NAME = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("ranges"));
    TEXT_PACKAGE_FQ_NAME = BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("text"));
    BUILT_INS_PACKAGE_FQ_NAMES = SetsKt.setOf(new FqName[] { BUILT_INS_PACKAGE_FQ_NAME, COLLECTIONS_PACKAGE_FQ_NAME, RANGES_PACKAGE_FQ_NAME, ANNOTATION_PACKAGE_FQ_NAME, ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier("internal")), DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE });
    FQ_NAMES = new FqNames();
  }
  
  protected KotlinBuiltIns(StorageManager paramStorageManager)
  {
    this.storageManager = paramStorageManager;
    this.builtInPackagesImportedByDefault = paramStorageManager.createLazyValue(new Function0()
    {
      public Collection<PackageViewDescriptor> invoke()
      {
        return Arrays.asList(new PackageViewDescriptor[] { KotlinBuiltIns.this.builtInsModule.getPackage(KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME), KotlinBuiltIns.this.builtInsModule.getPackage(KotlinBuiltIns.COLLECTIONS_PACKAGE_FQ_NAME), KotlinBuiltIns.this.builtInsModule.getPackage(KotlinBuiltIns.RANGES_PACKAGE_FQ_NAME), KotlinBuiltIns.this.builtInsModule.getPackage(KotlinBuiltIns.ANNOTATION_PACKAGE_FQ_NAME) });
      }
    });
    this.primitives = paramStorageManager.createLazyValue(new Function0()
    {
      public KotlinBuiltIns.Primitives invoke()
      {
        EnumMap localEnumMap = new EnumMap(PrimitiveType.class);
        HashMap localHashMap1 = new HashMap();
        HashMap localHashMap2 = new HashMap();
        for (PrimitiveType localPrimitiveType : PrimitiveType.values())
        {
          SimpleType localSimpleType1 = KotlinBuiltIns.this.getBuiltInTypeByClassName(localPrimitiveType.getTypeName().asString());
          SimpleType localSimpleType2 = KotlinBuiltIns.this.getBuiltInTypeByClassName(localPrimitiveType.getArrayTypeName().asString());
          localEnumMap.put(localPrimitiveType, localSimpleType2);
          localHashMap1.put(localSimpleType1, localSimpleType2);
          localHashMap2.put(localSimpleType2, localSimpleType1);
        }
        return new KotlinBuiltIns.Primitives(localEnumMap, localHashMap1, localHashMap2, null);
      }
    });
    this.builtInClassesByName = paramStorageManager.createMemoizedFunction(new Function1()
    {
      public ClassDescriptor invoke(Name paramAnonymousName)
      {
        Object localObject = KotlinBuiltIns.this.getBuiltInsPackageScope().getContributedClassifier(paramAnonymousName, NoLookupLocation.FROM_BUILTINS);
        if (localObject != null)
        {
          if ((localObject instanceof ClassDescriptor)) {
            return (ClassDescriptor)localObject;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Must be a class descriptor ");
          localStringBuilder.append(paramAnonymousName);
          localStringBuilder.append(", but was ");
          localStringBuilder.append(localObject);
          throw new AssertionError(localStringBuilder.toString());
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Built-in class ");
        ((StringBuilder)localObject).append(KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME.child(paramAnonymousName));
        ((StringBuilder)localObject).append(" is not found");
        throw new AssertionError(((StringBuilder)localObject).toString());
      }
    });
  }
  
  private static boolean classFqNameEquals(ClassifierDescriptor paramClassifierDescriptor, FqNameUnsafe paramFqNameUnsafe)
  {
    if (paramClassifierDescriptor == null) {
      $$$reportNull$$$0(99);
    }
    if (paramFqNameUnsafe == null) {
      $$$reportNull$$$0(100);
    }
    boolean bool;
    if ((paramClassifierDescriptor.getName().equals(paramFqNameUnsafe.shortName())) && (paramFqNameUnsafe.equals(DescriptorUtils.getFqName(paramClassifierDescriptor)))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private ClassDescriptor getBuiltInClassByName(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(13);
    }
    paramString = (ClassDescriptor)this.builtInClassesByName.invoke(Name.identifier(paramString));
    if (paramString == null) {
      $$$reportNull$$$0(14);
    }
    return paramString;
  }
  
  private SimpleType getBuiltInTypeByClassName(String paramString)
  {
    if (paramString == null) {
      $$$reportNull$$$0(45);
    }
    paramString = getBuiltInClassByName(paramString).getDefaultType();
    if (paramString == null) {
      $$$reportNull$$$0(46);
    }
    return paramString;
  }
  
  private static KotlinType getElementTypeForUnsignedArray(KotlinType paramKotlinType, ModuleDescriptor paramModuleDescriptor)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(70);
    }
    if (paramModuleDescriptor == null) {
      $$$reportNull$$$0(71);
    }
    paramKotlinType = paramKotlinType.getConstructor().getDeclarationDescriptor();
    if (paramKotlinType == null) {
      return null;
    }
    if (!UnsignedTypes.INSTANCE.isShortNameOfUnsignedArray(paramKotlinType.getName())) {
      return null;
    }
    paramKotlinType = DescriptorUtilsKt.getClassId(paramKotlinType);
    if (paramKotlinType == null) {
      return null;
    }
    paramKotlinType = UnsignedTypes.INSTANCE.getUnsignedClassIdByArrayClassId(paramKotlinType);
    if (paramKotlinType == null) {
      return null;
    }
    paramKotlinType = FindClassInModuleKt.findClassAcrossModuleDependencies(paramModuleDescriptor, paramKotlinType);
    if (paramKotlinType == null) {
      return null;
    }
    return paramKotlinType.getDefaultType();
  }
  
  public static ClassId getFunctionClassId(int paramInt)
  {
    return new ClassId(BUILT_INS_PACKAGE_FQ_NAME, Name.identifier(getFunctionName(paramInt)));
  }
  
  public static String getFunctionName(int paramInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Function");
    ((StringBuilder)localObject).append(paramInt);
    localObject = ((StringBuilder)localObject).toString();
    if (localObject == null) {
      $$$reportNull$$$0(17);
    }
    return localObject;
  }
  
  public static PrimitiveType getPrimitiveArrayType(DeclarationDescriptor paramDeclarationDescriptor)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(77);
    }
    if (FQ_NAMES.primitiveArrayTypeShortNames.contains(paramDeclarationDescriptor.getName())) {
      paramDeclarationDescriptor = (PrimitiveType)FQ_NAMES.arrayClassFqNameToPrimitiveType.get(DescriptorUtils.getFqName(paramDeclarationDescriptor));
    } else {
      paramDeclarationDescriptor = null;
    }
    return paramDeclarationDescriptor;
  }
  
  private ClassDescriptor getPrimitiveClassDescriptor(PrimitiveType paramPrimitiveType)
  {
    if (paramPrimitiveType == null) {
      $$$reportNull$$$0(15);
    }
    return getBuiltInClassByName(paramPrimitiveType.getTypeName().asString());
  }
  
  public static FqName getPrimitiveFqName(PrimitiveType paramPrimitiveType)
  {
    if (paramPrimitiveType == null) {
      $$$reportNull$$$0(152);
    }
    return BUILT_INS_PACKAGE_FQ_NAME.child(paramPrimitiveType.getTypeName());
  }
  
  public static PrimitiveType getPrimitiveType(DeclarationDescriptor paramDeclarationDescriptor)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(76);
    }
    if (FQ_NAMES.primitiveTypeShortNames.contains(paramDeclarationDescriptor.getName())) {
      paramDeclarationDescriptor = (PrimitiveType)FQ_NAMES.fqNameToPrimitiveType.get(DescriptorUtils.getFqName(paramDeclarationDescriptor));
    } else {
      paramDeclarationDescriptor = null;
    }
    return paramDeclarationDescriptor;
  }
  
  public static boolean isAny(ClassDescriptor paramClassDescriptor)
  {
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(104);
    }
    return classFqNameEquals(paramClassDescriptor, FQ_NAMES.any);
  }
  
  public static boolean isAnyOrNullableAny(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(130);
    }
    return isConstructedFromGivenClass(paramKotlinType, FQ_NAMES.any);
  }
  
  public static boolean isArray(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(84);
    }
    return isConstructedFromGivenClass(paramKotlinType, FQ_NAMES.array);
  }
  
  public static boolean isArrayOrPrimitiveArray(ClassDescriptor paramClassDescriptor)
  {
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(85);
    }
    boolean bool;
    if ((!classFqNameEquals(paramClassDescriptor, FQ_NAMES.array)) && (getPrimitiveArrayType(paramClassDescriptor) == null)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isBoolean(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(106);
    }
    return isConstructedFromGivenClassAndNotNullable(paramKotlinType, FQ_NAMES._boolean);
  }
  
  public static boolean isBuiltIn(DeclarationDescriptor paramDeclarationDescriptor)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(8);
    }
    boolean bool = false;
    if (DescriptorUtils.getParentOfType(paramDeclarationDescriptor, BuiltInsPackageFragment.class, false) != null) {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isByte(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(113);
    }
    return isConstructedFromGivenClassAndNotNullable(paramKotlinType, FQ_NAMES._byte);
  }
  
  public static boolean isChar(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(110);
    }
    return isConstructedFromGivenClassAndNotNullable(paramKotlinType, FQ_NAMES._char);
  }
  
  private static boolean isConstructedFromGivenClass(KotlinType paramKotlinType, FqNameUnsafe paramFqNameUnsafe)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(93);
    }
    if (paramFqNameUnsafe == null) {
      $$$reportNull$$$0(94);
    }
    return isTypeConstructorForGivenClass(paramKotlinType.getConstructor(), paramFqNameUnsafe);
  }
  
  private static boolean isConstructedFromGivenClassAndNotNullable(KotlinType paramKotlinType, FqNameUnsafe paramFqNameUnsafe)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(125);
    }
    if (paramFqNameUnsafe == null) {
      $$$reportNull$$$0(126);
    }
    boolean bool;
    if ((isConstructedFromGivenClass(paramKotlinType, paramFqNameUnsafe)) && (!paramKotlinType.isMarkedNullable())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isDefaultBound(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(132);
    }
    return isNullableAny(paramKotlinType);
  }
  
  public static boolean isDeprecated(DeclarationDescriptor paramDeclarationDescriptor)
  {
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(150);
    }
    boolean bool1 = paramDeclarationDescriptor.getOriginal().getAnnotations().hasAnnotation(FQ_NAMES.deprecated);
    boolean bool2 = true;
    if (bool1) {
      return true;
    }
    if ((paramDeclarationDescriptor instanceof PropertyDescriptor))
    {
      Object localObject = (PropertyDescriptor)paramDeclarationDescriptor;
      boolean bool3 = ((PropertyDescriptor)localObject).isVar();
      paramDeclarationDescriptor = ((PropertyDescriptor)localObject).getGetter();
      localObject = ((PropertyDescriptor)localObject).getSetter();
      if ((paramDeclarationDescriptor != null) && (isDeprecated(paramDeclarationDescriptor)))
      {
        bool1 = bool2;
        if (!bool3) {
          break label111;
        }
        if ((localObject != null) && (isDeprecated((DeclarationDescriptor)localObject)))
        {
          bool1 = bool2;
          break label111;
        }
      }
      bool1 = false;
      label111:
      return bool1;
    }
    return false;
  }
  
  public static boolean isDouble(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(119);
    }
    boolean bool;
    if ((isDoubleOrNullableDouble(paramKotlinType)) && (!paramKotlinType.isMarkedNullable())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isDoubleOrNullableDouble(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(124);
    }
    return isConstructedFromGivenClass(paramKotlinType, FQ_NAMES._double);
  }
  
  public static boolean isFloat(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(117);
    }
    boolean bool;
    if ((isFloatOrNullableFloat(paramKotlinType)) && (!paramKotlinType.isMarkedNullable())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isFloatOrNullableFloat(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(118);
    }
    return isConstructedFromGivenClass(paramKotlinType, FQ_NAMES._float);
  }
  
  public static boolean isInt(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(112);
    }
    return isConstructedFromGivenClassAndNotNullable(paramKotlinType, FQ_NAMES._int);
  }
  
  public static boolean isKClass(ClassDescriptor paramClassDescriptor)
  {
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(147);
    }
    return classFqNameEquals(paramClassDescriptor, FQ_NAMES.kClass);
  }
  
  public static boolean isLong(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(114);
    }
    return isConstructedFromGivenClassAndNotNullable(paramKotlinType, FQ_NAMES._long);
  }
  
  private static boolean isNotNullConstructedFromGivenClass(KotlinType paramKotlinType, FqNameUnsafe paramFqNameUnsafe)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(101);
    }
    if (paramFqNameUnsafe == null) {
      $$$reportNull$$$0(102);
    }
    boolean bool;
    if ((!paramKotlinType.isMarkedNullable()) && (isConstructedFromGivenClass(paramKotlinType, paramFqNameUnsafe))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isNothing(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(127);
    }
    boolean bool;
    if ((isNothingOrNullableNothing(paramKotlinType)) && (!TypeUtils.isNullableType(paramKotlinType))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isNothingOrNullableNothing(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(129);
    }
    return isConstructedFromGivenClass(paramKotlinType, FQ_NAMES.nothing);
  }
  
  public static boolean isNullableAny(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(131);
    }
    boolean bool;
    if ((isAnyOrNullableAny(paramKotlinType)) && (paramKotlinType.isMarkedNullable())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isPrimitiveArray(FqNameUnsafe paramFqNameUnsafe)
  {
    if (paramFqNameUnsafe == null) {
      $$$reportNull$$$0(75);
    }
    boolean bool;
    if (FQ_NAMES.arrayClassFqNameToPrimitiveType.get(paramFqNameUnsafe) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isPrimitiveArray(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(87);
    }
    paramKotlinType = paramKotlinType.getConstructor().getDeclarationDescriptor();
    boolean bool;
    if ((paramKotlinType != null) && (getPrimitiveArrayType(paramKotlinType) != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isPrimitiveClass(ClassDescriptor paramClassDescriptor)
  {
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(92);
    }
    boolean bool;
    if (getPrimitiveType(paramClassDescriptor) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isPrimitiveType(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(90);
    }
    boolean bool;
    if ((!paramKotlinType.isMarkedNullable()) && (isPrimitiveTypeOrNullablePrimitiveType(paramKotlinType))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isPrimitiveTypeOrNullablePrimitiveType(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(91);
    }
    paramKotlinType = paramKotlinType.getConstructor().getDeclarationDescriptor();
    boolean bool;
    if (((paramKotlinType instanceof ClassDescriptor)) && (isPrimitiveClass((ClassDescriptor)paramKotlinType))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isShort(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(116);
    }
    return isConstructedFromGivenClassAndNotNullable(paramKotlinType, FQ_NAMES._short);
  }
  
  public static boolean isSpecialClassWithNoSupertypes(ClassDescriptor paramClassDescriptor)
  {
    if (paramClassDescriptor == null) {
      $$$reportNull$$$0(103);
    }
    boolean bool;
    if ((!classFqNameEquals(paramClassDescriptor, FQ_NAMES.any)) && (!classFqNameEquals(paramClassDescriptor, FQ_NAMES.nothing))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isString(KotlinType paramKotlinType)
  {
    boolean bool;
    if ((paramKotlinType != null) && (isNotNullConstructedFromGivenClass(paramKotlinType, FQ_NAMES.string))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isTypeConstructorForGivenClass(TypeConstructor paramTypeConstructor, FqNameUnsafe paramFqNameUnsafe)
  {
    if (paramTypeConstructor == null) {
      $$$reportNull$$$0(97);
    }
    if (paramFqNameUnsafe == null) {
      $$$reportNull$$$0(98);
    }
    paramTypeConstructor = paramTypeConstructor.getDeclarationDescriptor();
    boolean bool;
    if (((paramTypeConstructor instanceof ClassDescriptor)) && (classFqNameEquals(paramTypeConstructor, paramFqNameUnsafe))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isUnderKotlinPackage(DeclarationDescriptor paramDeclarationDescriptor)
  {
    DeclarationDescriptor localDeclarationDescriptor = paramDeclarationDescriptor;
    if (paramDeclarationDescriptor == null) {
      $$$reportNull$$$0(9);
    }
    for (localDeclarationDescriptor = paramDeclarationDescriptor; localDeclarationDescriptor != null; localDeclarationDescriptor = localDeclarationDescriptor.getContainingDeclaration()) {
      if ((localDeclarationDescriptor instanceof PackageFragmentDescriptor)) {
        return ((PackageFragmentDescriptor)localDeclarationDescriptor).getFqName().startsWith(BUILT_INS_PACKAGE_NAME);
      }
    }
    return false;
  }
  
  public static boolean isUnit(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(133);
    }
    return isNotNullConstructedFromGivenClass(paramKotlinType, FQ_NAMES.unit);
  }
  
  protected void createBuiltInsModule(boolean paramBoolean)
  {
    ModuleDescriptorImpl localModuleDescriptorImpl = new ModuleDescriptorImpl(BUILTINS_MODULE_NAME, this.storageManager, this, null);
    this.builtInsModule = localModuleDescriptorImpl;
    localModuleDescriptorImpl.initialize(BuiltInsLoader.Companion.getInstance().createPackageFragmentProvider(this.storageManager, this.builtInsModule, getClassDescriptorFactories(), getPlatformDependentDeclarationFilter(), getAdditionalClassPartsProvider(), paramBoolean));
    localModuleDescriptorImpl = this.builtInsModule;
    localModuleDescriptorImpl.setDependencies(new ModuleDescriptorImpl[] { localModuleDescriptorImpl });
  }
  
  protected AdditionalClassPartsProvider getAdditionalClassPartsProvider()
  {
    AdditionalClassPartsProvider.None localNone = AdditionalClassPartsProvider.None.INSTANCE;
    if (localNone == null) {
      $$$reportNull$$$0(2);
    }
    return localNone;
  }
  
  public ClassDescriptor getAny()
  {
    return getBuiltInClassByName("Any");
  }
  
  public SimpleType getAnyType()
  {
    SimpleType localSimpleType = getAny().getDefaultType();
    if (localSimpleType == null) {
      $$$reportNull$$$0(49);
    }
    return localSimpleType;
  }
  
  public ClassDescriptor getArray()
  {
    return getBuiltInClassByName("Array");
  }
  
  public KotlinType getArrayElementType(KotlinType paramKotlinType)
  {
    if (paramKotlinType == null) {
      $$$reportNull$$$0(66);
    }
    if (isArray(paramKotlinType))
    {
      if (paramKotlinType.getArguments().size() == 1)
      {
        paramKotlinType = ((TypeProjection)paramKotlinType.getArguments().get(0)).getType();
        if (paramKotlinType == null) {
          $$$reportNull$$$0(67);
        }
        return paramKotlinType;
      }
      throw new IllegalStateException();
    }
    Object localObject1 = TypeUtils.makeNotNullable(paramKotlinType);
    Object localObject2 = (KotlinType)((Primitives)this.primitives.invoke()).kotlinArrayTypeToPrimitiveKotlinType.get(localObject1);
    if (localObject2 != null)
    {
      if (localObject2 == null) {
        $$$reportNull$$$0(68);
      }
      return localObject2;
    }
    localObject2 = DescriptorUtils.getContainingModuleOrNull((KotlinType)localObject1);
    if (localObject2 != null)
    {
      localObject1 = getElementTypeForUnsignedArray((KotlinType)localObject1, (ModuleDescriptor)localObject2);
      if (localObject1 != null)
      {
        if (localObject1 == null) {
          $$$reportNull$$$0(69);
        }
        return localObject1;
      }
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("not array: ");
    ((StringBuilder)localObject1).append(paramKotlinType);
    throw new IllegalStateException(((StringBuilder)localObject1).toString());
  }
  
  public SimpleType getArrayType(Variance paramVariance, KotlinType paramKotlinType)
  {
    if (paramVariance == null) {
      $$$reportNull$$$0(78);
    }
    if (paramKotlinType == null) {
      $$$reportNull$$$0(79);
    }
    paramVariance = Collections.singletonList(new TypeProjectionImpl(paramVariance, paramKotlinType));
    paramVariance = KotlinTypeFactory.simpleNotNullType(Annotations.Companion.getEMPTY(), getArray(), paramVariance);
    if (paramVariance == null) {
      $$$reportNull$$$0(80);
    }
    return paramVariance;
  }
  
  public SimpleType getBooleanType()
  {
    SimpleType localSimpleType = getPrimitiveKotlinType(PrimitiveType.BOOLEAN);
    if (localSimpleType == null) {
      $$$reportNull$$$0(62);
    }
    return localSimpleType;
  }
  
  public ClassDescriptor getBuiltInClassByFqName(FqName paramFqName)
  {
    if (paramFqName == null) {
      $$$reportNull$$$0(11);
    }
    paramFqName = DescriptorUtilKt.resolveClassByFqName(this.builtInsModule, paramFqName, NoLookupLocation.FROM_BUILTINS);
    if (paramFqName == null) {
      $$$reportNull$$$0(12);
    }
    return paramFqName;
  }
  
  public ModuleDescriptorImpl getBuiltInsModule()
  {
    ModuleDescriptorImpl localModuleDescriptorImpl = this.builtInsModule;
    if (localModuleDescriptorImpl == null) {
      $$$reportNull$$$0(6);
    }
    return localModuleDescriptorImpl;
  }
  
  public MemberScope getBuiltInsPackageScope()
  {
    MemberScope localMemberScope = this.builtInsModule.getPackage(BUILT_INS_PACKAGE_FQ_NAME).getMemberScope();
    if (localMemberScope == null) {
      $$$reportNull$$$0(10);
    }
    return localMemberScope;
  }
  
  public SimpleType getByteType()
  {
    SimpleType localSimpleType = getPrimitiveKotlinType(PrimitiveType.BYTE);
    if (localSimpleType == null) {
      $$$reportNull$$$0(55);
    }
    return localSimpleType;
  }
  
  public SimpleType getCharType()
  {
    SimpleType localSimpleType = getPrimitiveKotlinType(PrimitiveType.CHAR);
    if (localSimpleType == null) {
      $$$reportNull$$$0(61);
    }
    return localSimpleType;
  }
  
  protected Iterable<ClassDescriptorFactory> getClassDescriptorFactories()
  {
    List localList = Collections.singletonList(new BuiltInFictitiousFunctionClassFactory(this.storageManager, this.builtInsModule));
    if (localList == null) {
      $$$reportNull$$$0(4);
    }
    return localList;
  }
  
  public ClassDescriptor getCollection()
  {
    ClassDescriptor localClassDescriptor = getBuiltInClassByFqName(FQ_NAMES.collection);
    if (localClassDescriptor == null) {
      $$$reportNull$$$0(33);
    }
    return localClassDescriptor;
  }
  
  public ClassDescriptor getComparable()
  {
    return getBuiltInClassByName("Comparable");
  }
  
  public SimpleType getDefaultBound()
  {
    SimpleType localSimpleType = getNullableAnyType();
    if (localSimpleType == null) {
      $$$reportNull$$$0(51);
    }
    return localSimpleType;
  }
  
  public SimpleType getDoubleType()
  {
    SimpleType localSimpleType = getPrimitiveKotlinType(PrimitiveType.DOUBLE);
    if (localSimpleType == null) {
      $$$reportNull$$$0(60);
    }
    return localSimpleType;
  }
  
  public SimpleType getFloatType()
  {
    SimpleType localSimpleType = getPrimitiveKotlinType(PrimitiveType.FLOAT);
    if (localSimpleType == null) {
      $$$reportNull$$$0(59);
    }
    return localSimpleType;
  }
  
  public ClassDescriptor getFunction(int paramInt)
  {
    return getBuiltInClassByName(getFunctionName(paramInt));
  }
  
  public SimpleType getIntType()
  {
    SimpleType localSimpleType = getPrimitiveKotlinType(PrimitiveType.INT);
    if (localSimpleType == null) {
      $$$reportNull$$$0(57);
    }
    return localSimpleType;
  }
  
  public ClassDescriptor getKClass()
  {
    ClassDescriptor localClassDescriptor = getBuiltInClassByFqName(FQ_NAMES.kClass.toSafe());
    if (localClassDescriptor == null) {
      $$$reportNull$$$0(19);
    }
    return localClassDescriptor;
  }
  
  public SimpleType getLongType()
  {
    SimpleType localSimpleType = getPrimitiveKotlinType(PrimitiveType.LONG);
    if (localSimpleType == null) {
      $$$reportNull$$$0(58);
    }
    return localSimpleType;
  }
  
  public ClassDescriptor getNothing()
  {
    return getBuiltInClassByName("Nothing");
  }
  
  public SimpleType getNothingType()
  {
    SimpleType localSimpleType = getNothing().getDefaultType();
    if (localSimpleType == null) {
      $$$reportNull$$$0(47);
    }
    return localSimpleType;
  }
  
  public SimpleType getNullableAnyType()
  {
    SimpleType localSimpleType = getAnyType().makeNullableAsSpecified(true);
    if (localSimpleType == null) {
      $$$reportNull$$$0(50);
    }
    return localSimpleType;
  }
  
  public SimpleType getNullableNothingType()
  {
    SimpleType localSimpleType = getNothingType().makeNullableAsSpecified(true);
    if (localSimpleType == null) {
      $$$reportNull$$$0(48);
    }
    return localSimpleType;
  }
  
  public ClassDescriptor getNumber()
  {
    return getBuiltInClassByName("Number");
  }
  
  public SimpleType getNumberType()
  {
    SimpleType localSimpleType = getNumber().getDefaultType();
    if (localSimpleType == null) {
      $$$reportNull$$$0(54);
    }
    return localSimpleType;
  }
  
  protected PlatformDependentDeclarationFilter getPlatformDependentDeclarationFilter()
  {
    PlatformDependentDeclarationFilter.NoPlatformDependent localNoPlatformDependent = PlatformDependentDeclarationFilter.NoPlatformDependent.INSTANCE;
    if (localNoPlatformDependent == null) {
      $$$reportNull$$$0(3);
    }
    return localNoPlatformDependent;
  }
  
  public SimpleType getPrimitiveArrayKotlinType(PrimitiveType paramPrimitiveType)
  {
    if (paramPrimitiveType == null) {
      $$$reportNull$$$0(72);
    }
    paramPrimitiveType = (SimpleType)((Primitives)this.primitives.invoke()).primitiveTypeToArrayKotlinType.get(paramPrimitiveType);
    if (paramPrimitiveType == null) {
      $$$reportNull$$$0(73);
    }
    return paramPrimitiveType;
  }
  
  public SimpleType getPrimitiveKotlinType(PrimitiveType paramPrimitiveType)
  {
    if (paramPrimitiveType == null) {
      $$$reportNull$$$0(52);
    }
    paramPrimitiveType = getPrimitiveClassDescriptor(paramPrimitiveType).getDefaultType();
    if (paramPrimitiveType == null) {
      $$$reportNull$$$0(53);
    }
    return paramPrimitiveType;
  }
  
  public SimpleType getShortType()
  {
    SimpleType localSimpleType = getPrimitiveKotlinType(PrimitiveType.SHORT);
    if (localSimpleType == null) {
      $$$reportNull$$$0(56);
    }
    return localSimpleType;
  }
  
  protected StorageManager getStorageManager()
  {
    StorageManager localStorageManager = this.storageManager;
    if (localStorageManager == null) {
      $$$reportNull$$$0(5);
    }
    return localStorageManager;
  }
  
  public ClassDescriptor getString()
  {
    return getBuiltInClassByName("String");
  }
  
  public SimpleType getStringType()
  {
    SimpleType localSimpleType = getString().getDefaultType();
    if (localSimpleType == null) {
      $$$reportNull$$$0(64);
    }
    return localSimpleType;
  }
  
  public ClassDescriptor getSuspendFunction(int paramInt)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(FunctionClassDescriptor.Kind.SuspendFunction.getClassNamePrefix());
    ((StringBuilder)localObject).append(paramInt);
    localObject = Name.identifier(((StringBuilder)localObject).toString());
    localObject = getBuiltInClassByFqName(DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE.child((Name)localObject));
    if (localObject == null) {
      $$$reportNull$$$0(18);
    }
    return localObject;
  }
  
  public ClassDescriptor getUnit()
  {
    return getBuiltInClassByName("Unit");
  }
  
  public SimpleType getUnitType()
  {
    SimpleType localSimpleType = getUnit().getDefaultType();
    if (localSimpleType == null) {
      $$$reportNull$$$0(63);
    }
    return localSimpleType;
  }
  
  public void setBuiltInsModule(final ModuleDescriptorImpl paramModuleDescriptorImpl)
  {
    if (paramModuleDescriptorImpl == null) {
      $$$reportNull$$$0(1);
    }
    this.storageManager.compute(new Function0()
    {
      public Void invoke()
      {
        if (KotlinBuiltIns.this.builtInsModule == null)
        {
          KotlinBuiltIns.access$002(KotlinBuiltIns.this, paramModuleDescriptorImpl);
          return null;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Built-ins module is already set: ");
        localStringBuilder.append(KotlinBuiltIns.this.builtInsModule);
        localStringBuilder.append(" (attempting to reset to ");
        localStringBuilder.append(paramModuleDescriptorImpl);
        localStringBuilder.append(")");
        throw new AssertionError(localStringBuilder.toString());
      }
    });
  }
  
  public static class FqNames
  {
    public final FqNameUnsafe _boolean = fqNameUnsafe("Boolean");
    public final FqNameUnsafe _byte = fqNameUnsafe("Byte");
    public final FqNameUnsafe _char = fqNameUnsafe("Char");
    public final FqNameUnsafe _double = fqNameUnsafe("Double");
    public final FqNameUnsafe _enum = fqNameUnsafe("Enum");
    public final FqNameUnsafe _float = fqNameUnsafe("Float");
    public final FqNameUnsafe _int = fqNameUnsafe("Int");
    public final FqNameUnsafe _long = fqNameUnsafe("Long");
    public final FqNameUnsafe _short = fqNameUnsafe("Short");
    public final FqName annotation = fqName("Annotation");
    public final FqName annotationRetention = annotationName("AnnotationRetention");
    public final FqName annotationTarget = annotationName("AnnotationTarget");
    public final FqNameUnsafe any = fqNameUnsafe("Any");
    public final FqNameUnsafe array = fqNameUnsafe("Array");
    public final Map<FqNameUnsafe, PrimitiveType> arrayClassFqNameToPrimitiveType;
    public final FqNameUnsafe charSequence = fqNameUnsafe("CharSequence");
    public final FqNameUnsafe cloneable = fqNameUnsafe("Cloneable");
    public final FqName collection = collectionsFqName("Collection");
    public final FqName comparable = fqName("Comparable");
    public final FqName deprecated = fqName("Deprecated");
    public final FqName deprecationLevel = fqName("DeprecationLevel");
    public final FqName extensionFunctionType = fqName("ExtensionFunctionType");
    public final Map<FqNameUnsafe, PrimitiveType> fqNameToPrimitiveType;
    public final FqNameUnsafe functionSupertype = fqNameUnsafe("Function");
    public final FqNameUnsafe intRange = rangesFqName("IntRange");
    public final FqName iterable = collectionsFqName("Iterable");
    public final FqName iterator = collectionsFqName("Iterator");
    public final FqNameUnsafe kCallable;
    public final FqNameUnsafe kClass;
    public final FqNameUnsafe kDeclarationContainer;
    public final FqNameUnsafe kMutableProperty0;
    public final FqNameUnsafe kMutableProperty1;
    public final FqNameUnsafe kMutableProperty2;
    public final FqNameUnsafe kMutablePropertyFqName;
    public final ClassId kProperty;
    public final FqNameUnsafe kProperty0;
    public final FqNameUnsafe kProperty1;
    public final FqNameUnsafe kProperty2;
    public final FqNameUnsafe kPropertyFqName;
    public final FqName list = collectionsFqName("List");
    public final FqName listIterator = collectionsFqName("ListIterator");
    public final FqNameUnsafe longRange = rangesFqName("LongRange");
    public final FqName map;
    public final FqName mapEntry;
    public final FqName mustBeDocumented = annotationName("MustBeDocumented");
    public final FqName mutableCollection;
    public final FqName mutableIterable;
    public final FqName mutableIterator;
    public final FqName mutableList;
    public final FqName mutableListIterator;
    public final FqName mutableMap;
    public final FqName mutableMapEntry;
    public final FqName mutableSet;
    public final FqNameUnsafe nothing = fqNameUnsafe("Nothing");
    public final FqNameUnsafe number = fqNameUnsafe("Number");
    public final FqName parameterName = fqName("ParameterName");
    public final Set<Name> primitiveArrayTypeShortNames;
    public final Set<Name> primitiveTypeShortNames;
    public final FqName publishedApi = fqName("PublishedApi");
    public final FqName repeatable = annotationName("Repeatable");
    public final FqName replaceWith = fqName("ReplaceWith");
    public final FqName retention = annotationName("Retention");
    public final FqName set = collectionsFqName("Set");
    public final FqNameUnsafe string = fqNameUnsafe("String");
    public final FqName suppress = fqName("Suppress");
    public final FqName target = annotationName("Target");
    public final FqName throwable = fqName("Throwable");
    public final ClassId uByte;
    public final FqName uByteFqName;
    public final ClassId uInt;
    public final FqName uIntFqName;
    public final ClassId uLong;
    public final FqName uLongFqName;
    public final ClassId uShort;
    public final FqName uShortFqName;
    public final FqNameUnsafe unit = fqNameUnsafe("Unit");
    public final FqName unsafeVariance = fqName("UnsafeVariance");
    
    public FqNames()
    {
      Object localObject1 = collectionsFqName("Map");
      this.map = ((FqName)localObject1);
      this.mapEntry = ((FqName)localObject1).child(Name.identifier("Entry"));
      this.mutableIterator = collectionsFqName("MutableIterator");
      this.mutableIterable = collectionsFqName("MutableIterable");
      this.mutableCollection = collectionsFqName("MutableCollection");
      this.mutableList = collectionsFqName("MutableList");
      this.mutableListIterator = collectionsFqName("MutableListIterator");
      this.mutableSet = collectionsFqName("MutableSet");
      localObject1 = collectionsFqName("MutableMap");
      this.mutableMap = ((FqName)localObject1);
      this.mutableMapEntry = ((FqName)localObject1).child(Name.identifier("MutableEntry"));
      this.kClass = reflect("KClass");
      this.kCallable = reflect("KCallable");
      this.kProperty0 = reflect("KProperty0");
      this.kProperty1 = reflect("KProperty1");
      this.kProperty2 = reflect("KProperty2");
      this.kMutableProperty0 = reflect("KMutableProperty0");
      this.kMutableProperty1 = reflect("KMutableProperty1");
      this.kMutableProperty2 = reflect("KMutableProperty2");
      this.kPropertyFqName = reflect("KProperty");
      this.kMutablePropertyFqName = reflect("KMutableProperty");
      this.kProperty = ClassId.topLevel(this.kPropertyFqName.toSafe());
      this.kDeclarationContainer = reflect("KDeclarationContainer");
      this.uByteFqName = fqName("UByte");
      this.uShortFqName = fqName("UShort");
      this.uIntFqName = fqName("UInt");
      this.uLongFqName = fqName("ULong");
      this.uByte = ClassId.topLevel(this.uByteFqName);
      this.uShort = ClassId.topLevel(this.uShortFqName);
      this.uInt = ClassId.topLevel(this.uIntFqName);
      this.uLong = ClassId.topLevel(this.uLongFqName);
      this.primitiveTypeShortNames = CollectionsKt.newHashSetWithExpectedSize(PrimitiveType.values().length);
      this.primitiveArrayTypeShortNames = CollectionsKt.newHashSetWithExpectedSize(PrimitiveType.values().length);
      this.fqNameToPrimitiveType = CollectionsKt.newHashMapWithExpectedSize(PrimitiveType.values().length);
      this.arrayClassFqNameToPrimitiveType = CollectionsKt.newHashMapWithExpectedSize(PrimitiveType.values().length);
      for (Object localObject2 : PrimitiveType.values())
      {
        this.primitiveTypeShortNames.add(localObject2.getTypeName());
        this.primitiveArrayTypeShortNames.add(localObject2.getArrayTypeName());
        this.fqNameToPrimitiveType.put(fqNameUnsafe(localObject2.getTypeName().asString()), localObject2);
        this.arrayClassFqNameToPrimitiveType.put(fqNameUnsafe(localObject2.getArrayTypeName().asString()), localObject2);
      }
    }
    
    private static FqName annotationName(String paramString)
    {
      if (paramString == null) {
        $$$reportNull$$$0(10);
      }
      paramString = KotlinBuiltIns.ANNOTATION_PACKAGE_FQ_NAME.child(Name.identifier(paramString));
      if (paramString == null) {
        $$$reportNull$$$0(11);
      }
      return paramString;
    }
    
    private static FqName collectionsFqName(String paramString)
    {
      if (paramString == null) {
        $$$reportNull$$$0(4);
      }
      paramString = KotlinBuiltIns.COLLECTIONS_PACKAGE_FQ_NAME.child(Name.identifier(paramString));
      if (paramString == null) {
        $$$reportNull$$$0(5);
      }
      return paramString;
    }
    
    private static FqName fqName(String paramString)
    {
      if (paramString == null) {
        $$$reportNull$$$0(2);
      }
      paramString = KotlinBuiltIns.BUILT_INS_PACKAGE_FQ_NAME.child(Name.identifier(paramString));
      if (paramString == null) {
        $$$reportNull$$$0(3);
      }
      return paramString;
    }
    
    private static FqNameUnsafe fqNameUnsafe(String paramString)
    {
      if (paramString == null) {
        $$$reportNull$$$0(0);
      }
      paramString = fqName(paramString).toUnsafe();
      if (paramString == null) {
        $$$reportNull$$$0(1);
      }
      return paramString;
    }
    
    private static FqNameUnsafe rangesFqName(String paramString)
    {
      if (paramString == null) {
        $$$reportNull$$$0(6);
      }
      paramString = KotlinBuiltIns.RANGES_PACKAGE_FQ_NAME.child(Name.identifier(paramString)).toUnsafe();
      if (paramString == null) {
        $$$reportNull$$$0(7);
      }
      return paramString;
    }
    
    private static FqNameUnsafe reflect(String paramString)
    {
      if (paramString == null) {
        $$$reportNull$$$0(8);
      }
      paramString = ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME().child(Name.identifier(paramString)).toUnsafe();
      if (paramString == null) {
        $$$reportNull$$$0(9);
      }
      return paramString;
    }
  }
  
  private static class Primitives
  {
    public final Map<SimpleType, SimpleType> kotlinArrayTypeToPrimitiveKotlinType;
    public final Map<KotlinType, SimpleType> primitiveKotlinTypeToKotlinArrayType;
    public final Map<PrimitiveType, SimpleType> primitiveTypeToArrayKotlinType;
    
    private Primitives(Map<PrimitiveType, SimpleType> paramMap, Map<KotlinType, SimpleType> paramMap1, Map<SimpleType, SimpleType> paramMap2)
    {
      this.primitiveTypeToArrayKotlinType = paramMap;
      this.primitiveKotlinTypeToKotlinArrayType = paramMap1;
      this.kotlinArrayTypeToPrimitiveKotlinType = paramMap2;
    }
  }
}
