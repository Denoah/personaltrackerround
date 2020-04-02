package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageViewDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.StarProjectionImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.text.StringsKt;

public final class ReflectionTypes
{
  public static final Companion Companion = new Companion(null);
  private final ClassLookup kClass$delegate;
  private final ClassLookup kMutableProperty0$delegate;
  private final ClassLookup kMutableProperty1$delegate;
  private final ClassLookup kMutableProperty2$delegate;
  private final ClassLookup kProperty$delegate;
  private final ClassLookup kProperty0$delegate;
  private final ClassLookup kProperty1$delegate;
  private final ClassLookup kProperty2$delegate;
  private final Lazy kotlinReflectScope$delegate;
  private final NotFoundClasses notFoundClasses;
  
  public ReflectionTypes(ModuleDescriptor paramModuleDescriptor, NotFoundClasses paramNotFoundClasses)
  {
    this.notFoundClasses = paramNotFoundClasses;
    this.kotlinReflectScope$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0)new Lambda(paramModuleDescriptor)
    {
      public final MemberScope invoke()
      {
        return this.$module.getPackage(ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME()).getMemberScope();
      }
    });
    this.kClass$delegate = new ClassLookup(1);
    this.kProperty$delegate = new ClassLookup(1);
    this.kProperty0$delegate = new ClassLookup(1);
    this.kProperty1$delegate = new ClassLookup(2);
    this.kProperty2$delegate = new ClassLookup(3);
    this.kMutableProperty0$delegate = new ClassLookup(1);
    this.kMutableProperty1$delegate = new ClassLookup(2);
    this.kMutableProperty2$delegate = new ClassLookup(3);
  }
  
  private final ClassDescriptor find(String paramString, int paramInt)
  {
    Name localName = Name.identifier(paramString);
    Intrinsics.checkExpressionValueIsNotNull(localName, "Name.identifier(className)");
    ClassifierDescriptor localClassifierDescriptor = getKotlinReflectScope().getContributedClassifier(localName, (LookupLocation)NoLookupLocation.FROM_REFLECTION);
    paramString = localClassifierDescriptor;
    if (!(localClassifierDescriptor instanceof ClassDescriptor)) {
      paramString = null;
    }
    paramString = (ClassDescriptor)paramString;
    if (paramString == null) {
      paramString = this.notFoundClasses.getClass(new ClassId(ReflectionTypesKt.getKOTLIN_REFLECT_FQ_NAME(), localName), CollectionsKt.listOf(Integer.valueOf(paramInt)));
    }
    return paramString;
  }
  
  private final MemberScope getKotlinReflectScope()
  {
    Lazy localLazy = this.kotlinReflectScope$delegate;
    KProperty localKProperty = $$delegatedProperties[0];
    return (MemberScope)localLazy.getValue();
  }
  
  public final ClassDescriptor getKClass()
  {
    return this.kClass$delegate.getValue(this, $$delegatedProperties[1]);
  }
  
  private static final class ClassLookup
  {
    private final int numberOfTypeParameters;
    
    public ClassLookup(int paramInt)
    {
      this.numberOfTypeParameters = paramInt;
    }
    
    public final ClassDescriptor getValue(ReflectionTypes paramReflectionTypes, KProperty<?> paramKProperty)
    {
      Intrinsics.checkParameterIsNotNull(paramReflectionTypes, "types");
      Intrinsics.checkParameterIsNotNull(paramKProperty, "property");
      return ReflectionTypes.access$find(paramReflectionTypes, StringsKt.capitalize(paramKProperty.getName()), this.numberOfTypeParameters);
    }
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final KotlinType createKPropertyStarType(ModuleDescriptor paramModuleDescriptor)
    {
      Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
      Object localObject1 = KotlinBuiltIns.FQ_NAMES.kProperty;
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "KotlinBuiltIns.FQ_NAMES.kProperty");
      localObject1 = FindClassInModuleKt.findClassAcrossModuleDependencies(paramModuleDescriptor, (ClassId)localObject1);
      if (localObject1 != null)
      {
        paramModuleDescriptor = Annotations.Companion.getEMPTY();
        Object localObject2 = ((ClassDescriptor)localObject1).getTypeConstructor();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "kPropertyClass.typeConstructor");
        localObject2 = ((TypeConstructor)localObject2).getParameters();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "kPropertyClass.typeConstructor.parameters");
        localObject2 = CollectionsKt.single((List)localObject2);
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "kPropertyClass.typeConstructor.parameters.single()");
        return (KotlinType)KotlinTypeFactory.simpleNotNullType(paramModuleDescriptor, (ClassDescriptor)localObject1, CollectionsKt.listOf(new StarProjectionImpl((TypeParameterDescriptor)localObject2)));
      }
      return null;
    }
  }
}
