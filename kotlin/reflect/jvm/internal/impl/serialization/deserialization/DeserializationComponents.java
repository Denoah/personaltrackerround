package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupTracker;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.protobuf.ExtensionRegistryLite;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker;

public final class DeserializationComponents
{
  private final AdditionalClassPartsProvider additionalClassPartsProvider;
  private final AnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>> annotationAndConstantLoader;
  private final ClassDataFinder classDataFinder;
  private final ClassDeserializer classDeserializer;
  private final DeserializationConfiguration configuration;
  private final ContractDeserializer contractDeserializer;
  private final ErrorReporter errorReporter;
  private final ExtensionRegistryLite extensionRegistryLite;
  private final Iterable<ClassDescriptorFactory> fictitiousClassDescriptorFactories;
  private final FlexibleTypeDeserializer flexibleTypeDeserializer;
  private final NewKotlinTypeChecker kotlinTypeChecker;
  private final LocalClassifierTypeSettings localClassifierTypeSettings;
  private final LookupTracker lookupTracker;
  private final ModuleDescriptor moduleDescriptor;
  private final NotFoundClasses notFoundClasses;
  private final PackageFragmentProvider packageFragmentProvider;
  private final PlatformDependentDeclarationFilter platformDependentDeclarationFilter;
  private final StorageManager storageManager;
  
  public DeserializationComponents(StorageManager paramStorageManager, ModuleDescriptor paramModuleDescriptor, DeserializationConfiguration paramDeserializationConfiguration, ClassDataFinder paramClassDataFinder, AnnotationAndConstantLoader<? extends AnnotationDescriptor, ? extends ConstantValue<?>> paramAnnotationAndConstantLoader, PackageFragmentProvider paramPackageFragmentProvider, LocalClassifierTypeSettings paramLocalClassifierTypeSettings, ErrorReporter paramErrorReporter, LookupTracker paramLookupTracker, FlexibleTypeDeserializer paramFlexibleTypeDeserializer, Iterable<? extends ClassDescriptorFactory> paramIterable, NotFoundClasses paramNotFoundClasses, ContractDeserializer paramContractDeserializer, AdditionalClassPartsProvider paramAdditionalClassPartsProvider, PlatformDependentDeclarationFilter paramPlatformDependentDeclarationFilter, ExtensionRegistryLite paramExtensionRegistryLite, NewKotlinTypeChecker paramNewKotlinTypeChecker)
  {
    this.storageManager = paramStorageManager;
    this.moduleDescriptor = paramModuleDescriptor;
    this.configuration = paramDeserializationConfiguration;
    this.classDataFinder = paramClassDataFinder;
    this.annotationAndConstantLoader = paramAnnotationAndConstantLoader;
    this.packageFragmentProvider = paramPackageFragmentProvider;
    this.localClassifierTypeSettings = paramLocalClassifierTypeSettings;
    this.errorReporter = paramErrorReporter;
    this.lookupTracker = paramLookupTracker;
    this.flexibleTypeDeserializer = paramFlexibleTypeDeserializer;
    this.fictitiousClassDescriptorFactories = paramIterable;
    this.notFoundClasses = paramNotFoundClasses;
    this.contractDeserializer = paramContractDeserializer;
    this.additionalClassPartsProvider = paramAdditionalClassPartsProvider;
    this.platformDependentDeclarationFilter = paramPlatformDependentDeclarationFilter;
    this.extensionRegistryLite = paramExtensionRegistryLite;
    this.kotlinTypeChecker = paramNewKotlinTypeChecker;
    this.classDeserializer = new ClassDeserializer(this);
  }
  
  public final DeserializationContext createContext(PackageFragmentDescriptor paramPackageFragmentDescriptor, NameResolver paramNameResolver, TypeTable paramTypeTable, VersionRequirementTable paramVersionRequirementTable, BinaryVersion paramBinaryVersion, DeserializedContainerSource paramDeserializedContainerSource)
  {
    Intrinsics.checkParameterIsNotNull(paramPackageFragmentDescriptor, "descriptor");
    Intrinsics.checkParameterIsNotNull(paramNameResolver, "nameResolver");
    Intrinsics.checkParameterIsNotNull(paramTypeTable, "typeTable");
    Intrinsics.checkParameterIsNotNull(paramVersionRequirementTable, "versionRequirementTable");
    Intrinsics.checkParameterIsNotNull(paramBinaryVersion, "metadataVersion");
    return new DeserializationContext(this, paramNameResolver, (DeclarationDescriptor)paramPackageFragmentDescriptor, paramTypeTable, paramVersionRequirementTable, paramBinaryVersion, paramDeserializedContainerSource, null, CollectionsKt.emptyList());
  }
  
  public final ClassDescriptor deserializeClass(ClassId paramClassId)
  {
    Intrinsics.checkParameterIsNotNull(paramClassId, "classId");
    return ClassDeserializer.deserializeClass$default(this.classDeserializer, paramClassId, null, 2, null);
  }
  
  public final AdditionalClassPartsProvider getAdditionalClassPartsProvider()
  {
    return this.additionalClassPartsProvider;
  }
  
  public final AnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>> getAnnotationAndConstantLoader()
  {
    return this.annotationAndConstantLoader;
  }
  
  public final ClassDataFinder getClassDataFinder()
  {
    return this.classDataFinder;
  }
  
  public final ClassDeserializer getClassDeserializer()
  {
    return this.classDeserializer;
  }
  
  public final DeserializationConfiguration getConfiguration()
  {
    return this.configuration;
  }
  
  public final ContractDeserializer getContractDeserializer()
  {
    return this.contractDeserializer;
  }
  
  public final ErrorReporter getErrorReporter()
  {
    return this.errorReporter;
  }
  
  public final ExtensionRegistryLite getExtensionRegistryLite()
  {
    return this.extensionRegistryLite;
  }
  
  public final Iterable<ClassDescriptorFactory> getFictitiousClassDescriptorFactories()
  {
    return this.fictitiousClassDescriptorFactories;
  }
  
  public final FlexibleTypeDeserializer getFlexibleTypeDeserializer()
  {
    return this.flexibleTypeDeserializer;
  }
  
  public final NewKotlinTypeChecker getKotlinTypeChecker()
  {
    return this.kotlinTypeChecker;
  }
  
  public final LocalClassifierTypeSettings getLocalClassifierTypeSettings()
  {
    return this.localClassifierTypeSettings;
  }
  
  public final LookupTracker getLookupTracker()
  {
    return this.lookupTracker;
  }
  
  public final ModuleDescriptor getModuleDescriptor()
  {
    return this.moduleDescriptor;
  }
  
  public final NotFoundClasses getNotFoundClasses()
  {
    return this.notFoundClasses;
  }
  
  public final PackageFragmentProvider getPackageFragmentProvider()
  {
    return this.packageFragmentProvider;
  }
  
  public final PlatformDependentDeclarationFilter getPlatformDependentDeclarationFilter()
  {
    return this.platformDependentDeclarationFilter;
  }
  
  public final StorageManager getStorageManager()
  {
    return this.storageManager;
  }
}
