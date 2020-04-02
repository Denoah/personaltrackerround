package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.collections.CollectionsKt;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider.None;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter.NoPlatformDependent;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupTracker;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaPackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.JvmProtoBufUtil;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationAndConstantLoader;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDataFinder;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ContractDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ErrorReporter;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.FlexibleTypeDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.LocalClassifierTypeSettings;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.LocalClassifierTypeSettings.Default;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker;

public final class DeserializationComponentsForJava
{
  private final DeserializationComponents components;
  
  public DeserializationComponentsForJava(StorageManager paramStorageManager, ModuleDescriptor paramModuleDescriptor, DeserializationConfiguration paramDeserializationConfiguration, JavaClassDataFinder paramJavaClassDataFinder, BinaryClassAnnotationAndConstantLoaderImpl paramBinaryClassAnnotationAndConstantLoaderImpl, LazyJavaPackageFragmentProvider paramLazyJavaPackageFragmentProvider, NotFoundClasses paramNotFoundClasses, ErrorReporter paramErrorReporter, LookupTracker paramLookupTracker, ContractDeserializer paramContractDeserializer, NewKotlinTypeChecker paramNewKotlinTypeChecker)
  {
    Object localObject1 = paramModuleDescriptor.getBuiltIns();
    Object localObject2 = localObject1;
    if (!(localObject1 instanceof JvmBuiltIns)) {
      localObject2 = null;
    }
    localObject1 = (JvmBuiltIns)localObject2;
    localObject2 = (ClassDataFinder)paramJavaClassDataFinder;
    paramBinaryClassAnnotationAndConstantLoaderImpl = (AnnotationAndConstantLoader)paramBinaryClassAnnotationAndConstantLoaderImpl;
    PackageFragmentProvider localPackageFragmentProvider = (PackageFragmentProvider)paramLazyJavaPackageFragmentProvider;
    LocalClassifierTypeSettings localLocalClassifierTypeSettings = (LocalClassifierTypeSettings)LocalClassifierTypeSettings.Default.INSTANCE;
    FlexibleTypeDeserializer localFlexibleTypeDeserializer = (FlexibleTypeDeserializer)JavaFlexibleTypeDeserializer.INSTANCE;
    paramLazyJavaPackageFragmentProvider = (Iterable)CollectionsKt.emptyList();
    if (localObject1 != null)
    {
      paramJavaClassDataFinder = ((JvmBuiltIns)localObject1).getSettings();
      if (paramJavaClassDataFinder != null) {}
    }
    else
    {
      paramJavaClassDataFinder = AdditionalClassPartsProvider.None.INSTANCE;
    }
    AdditionalClassPartsProvider localAdditionalClassPartsProvider = (AdditionalClassPartsProvider)paramJavaClassDataFinder;
    if (localObject1 != null)
    {
      paramJavaClassDataFinder = ((JvmBuiltIns)localObject1).getSettings();
      if (paramJavaClassDataFinder != null) {}
    }
    else
    {
      paramJavaClassDataFinder = PlatformDependentDeclarationFilter.NoPlatformDependent.INSTANCE;
    }
    this.components = new DeserializationComponents(paramStorageManager, paramModuleDescriptor, paramDeserializationConfiguration, (ClassDataFinder)localObject2, paramBinaryClassAnnotationAndConstantLoaderImpl, localPackageFragmentProvider, localLocalClassifierTypeSettings, paramErrorReporter, paramLookupTracker, localFlexibleTypeDeserializer, paramLazyJavaPackageFragmentProvider, paramNotFoundClasses, paramContractDeserializer, localAdditionalClassPartsProvider, (PlatformDependentDeclarationFilter)paramJavaClassDataFinder, JvmProtoBufUtil.INSTANCE.getEXTENSION_REGISTRY(), paramNewKotlinTypeChecker);
  }
  
  public final DeserializationComponents getComponents()
  {
    return this.components;
  }
}
