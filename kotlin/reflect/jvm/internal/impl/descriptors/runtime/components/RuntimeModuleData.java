package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns.Kind;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsPackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.CompositePackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaResolverCache;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaPackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ModuleClassResolver;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.SingleModuleClassResolver;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializationComponentsForJava;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializedDescriptorResolver;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JavaDescriptorResolver;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration.Default;
import kotlin.reflect.jvm.internal.impl.storage.LockBasedStorageManager;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker.Companion;

public final class RuntimeModuleData
{
  public static final Companion Companion = new Companion(null);
  private final DeserializationComponents deserialization;
  private final PackagePartScopeCache packagePartScopeCache;
  
  private RuntimeModuleData(DeserializationComponents paramDeserializationComponents, PackagePartScopeCache paramPackagePartScopeCache)
  {
    this.deserialization = paramDeserializationComponents;
    this.packagePartScopeCache = paramPackagePartScopeCache;
  }
  
  public final DeserializationComponents getDeserialization()
  {
    return this.deserialization;
  }
  
  public final ModuleDescriptor getModule()
  {
    return this.deserialization.getModuleDescriptor();
  }
  
  public final PackagePartScopeCache getPackagePartScopeCache()
  {
    return this.packagePartScopeCache;
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final RuntimeModuleData create(ClassLoader paramClassLoader)
    {
      Intrinsics.checkParameterIsNotNull(paramClassLoader, "classLoader");
      Object localObject1 = (StorageManager)new LockBasedStorageManager("RuntimeModuleData");
      JvmBuiltIns localJvmBuiltIns = new JvmBuiltIns((StorageManager)localObject1, JvmBuiltIns.Kind.FROM_DEPENDENCIES);
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("<runtime module for ");
      ((StringBuilder)localObject2).append(paramClassLoader);
      ((StringBuilder)localObject2).append('>');
      localObject2 = Name.special(((StringBuilder)localObject2).toString());
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "Name.special(\"<runtime module for $classLoader>\")");
      ModuleDescriptorImpl localModuleDescriptorImpl = new ModuleDescriptorImpl((Name)localObject2, (StorageManager)localObject1, (KotlinBuiltIns)localJvmBuiltIns, null, null, null, 56, null);
      localJvmBuiltIns.setBuiltInsModule(localModuleDescriptorImpl);
      ModuleDescriptor localModuleDescriptor = (ModuleDescriptor)localModuleDescriptorImpl;
      localJvmBuiltIns.initialize(localModuleDescriptor, true);
      ReflectKotlinClassFinder localReflectKotlinClassFinder = new ReflectKotlinClassFinder(paramClassLoader);
      localObject2 = new DeserializedDescriptorResolver();
      Object localObject3 = new SingleModuleClassResolver();
      NotFoundClasses localNotFoundClasses = new NotFoundClasses((StorageManager)localObject1, localModuleDescriptor);
      Object localObject4 = (KotlinClassFinder)localReflectKotlinClassFinder;
      Object localObject5 = RuntimeModuleDataKt.makeLazyJavaPackageFragmentFromClassLoaderProvider$default(paramClassLoader, localModuleDescriptor, (StorageManager)localObject1, localNotFoundClasses, (KotlinClassFinder)localObject4, (DeserializedDescriptorResolver)localObject2, (ModuleClassResolver)localObject3, null, 128, null);
      paramClassLoader = RuntimeModuleDataKt.makeDeserializationComponentsForJava(localModuleDescriptor, (StorageManager)localObject1, localNotFoundClasses, (LazyJavaPackageFragmentProvider)localObject5, (KotlinClassFinder)localObject4, (DeserializedDescriptorResolver)localObject2);
      ((DeserializedDescriptorResolver)localObject2).setComponents(paramClassLoader);
      localObject4 = JavaResolverCache.EMPTY;
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "JavaResolverCache.EMPTY");
      localObject5 = new JavaDescriptorResolver((LazyJavaPackageFragmentProvider)localObject5, (JavaResolverCache)localObject4);
      ((SingleModuleClassResolver)localObject3).setResolver((JavaDescriptorResolver)localObject5);
      localObject3 = Unit.class.getClassLoader();
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "stdlibClassLoader");
      localObject1 = new JvmBuiltInsPackageFragmentProvider((StorageManager)localObject1, (KotlinClassFinder)new ReflectKotlinClassFinder((ClassLoader)localObject3), localModuleDescriptor, localNotFoundClasses, (AdditionalClassPartsProvider)localJvmBuiltIns.getSettings(), (PlatformDependentDeclarationFilter)localJvmBuiltIns.getSettings(), (DeserializationConfiguration)DeserializationConfiguration.Default.INSTANCE, (NewKotlinTypeChecker)NewKotlinTypeChecker.Companion.getDefault());
      localModuleDescriptorImpl.setDependencies(new ModuleDescriptorImpl[] { localModuleDescriptorImpl });
      localModuleDescriptorImpl.initialize((PackageFragmentProvider)new CompositePackageFragmentProvider(CollectionsKt.listOf(new PackageFragmentProvider[] { (PackageFragmentProvider)((JavaDescriptorResolver)localObject5).getPackageFragmentProvider(), (PackageFragmentProvider)localObject1 })));
      return new RuntimeModuleData(paramClassLoader.getComponents(), new PackagePartScopeCache((DeserializedDescriptorResolver)localObject2, localReflectKotlinClassFinder), null);
    }
  }
}
