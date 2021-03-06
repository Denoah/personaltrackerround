package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.ReflectionTypes;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SupertypeLoopChecker;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupTracker;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder;
import kotlin.reflect.jvm.internal.impl.load.java.JavaClassesTracker;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaPropertyInitializerEvaluator;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaResolverCache;
import kotlin.reflect.jvm.internal.impl.load.java.components.SamConversionResolver;
import kotlin.reflect.jvm.internal.impl.load.java.components.SignaturePropagator;
import kotlin.reflect.jvm.internal.impl.load.java.sources.JavaSourceElementFactory;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement;
import kotlin.reflect.jvm.internal.impl.load.kotlin.DeserializedDescriptorResolver;
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.PackagePartProvider;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ErrorReporter;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.checker.NewKotlinTypeChecker;

public final class JavaResolverComponents
{
  private final AnnotationTypeQualifierResolver annotationTypeQualifierResolver;
  private final DeserializedDescriptorResolver deserializedDescriptorResolver;
  private final ErrorReporter errorReporter;
  private final JavaClassFinder finder;
  private final JavaClassesTracker javaClassesTracker;
  private final JavaPropertyInitializerEvaluator javaPropertyInitializerEvaluator;
  private final JavaResolverCache javaResolverCache;
  private final KotlinClassFinder kotlinClassFinder;
  private final NewKotlinTypeChecker kotlinTypeChecker;
  private final LookupTracker lookupTracker;
  private final ModuleDescriptor module;
  private final ModuleClassResolver moduleClassResolver;
  private final PackagePartProvider packagePartProvider;
  private final ReflectionTypes reflectionTypes;
  private final SamConversionResolver samConversionResolver;
  private final JavaResolverSettings settings;
  private final SignatureEnhancement signatureEnhancement;
  private final SignaturePropagator signaturePropagator;
  private final JavaSourceElementFactory sourceElementFactory;
  private final StorageManager storageManager;
  private final SupertypeLoopChecker supertypeLoopChecker;
  
  public JavaResolverComponents(StorageManager paramStorageManager, JavaClassFinder paramJavaClassFinder, KotlinClassFinder paramKotlinClassFinder, DeserializedDescriptorResolver paramDeserializedDescriptorResolver, SignaturePropagator paramSignaturePropagator, ErrorReporter paramErrorReporter, JavaResolverCache paramJavaResolverCache, JavaPropertyInitializerEvaluator paramJavaPropertyInitializerEvaluator, SamConversionResolver paramSamConversionResolver, JavaSourceElementFactory paramJavaSourceElementFactory, ModuleClassResolver paramModuleClassResolver, PackagePartProvider paramPackagePartProvider, SupertypeLoopChecker paramSupertypeLoopChecker, LookupTracker paramLookupTracker, ModuleDescriptor paramModuleDescriptor, ReflectionTypes paramReflectionTypes, AnnotationTypeQualifierResolver paramAnnotationTypeQualifierResolver, SignatureEnhancement paramSignatureEnhancement, JavaClassesTracker paramJavaClassesTracker, JavaResolverSettings paramJavaResolverSettings, NewKotlinTypeChecker paramNewKotlinTypeChecker)
  {
    this.storageManager = paramStorageManager;
    this.finder = paramJavaClassFinder;
    this.kotlinClassFinder = paramKotlinClassFinder;
    this.deserializedDescriptorResolver = paramDeserializedDescriptorResolver;
    this.signaturePropagator = paramSignaturePropagator;
    this.errorReporter = paramErrorReporter;
    this.javaResolverCache = paramJavaResolverCache;
    this.javaPropertyInitializerEvaluator = paramJavaPropertyInitializerEvaluator;
    this.samConversionResolver = paramSamConversionResolver;
    this.sourceElementFactory = paramJavaSourceElementFactory;
    this.moduleClassResolver = paramModuleClassResolver;
    this.packagePartProvider = paramPackagePartProvider;
    this.supertypeLoopChecker = paramSupertypeLoopChecker;
    this.lookupTracker = paramLookupTracker;
    this.module = paramModuleDescriptor;
    this.reflectionTypes = paramReflectionTypes;
    this.annotationTypeQualifierResolver = paramAnnotationTypeQualifierResolver;
    this.signatureEnhancement = paramSignatureEnhancement;
    this.javaClassesTracker = paramJavaClassesTracker;
    this.settings = paramJavaResolverSettings;
    this.kotlinTypeChecker = paramNewKotlinTypeChecker;
  }
  
  public final AnnotationTypeQualifierResolver getAnnotationTypeQualifierResolver()
  {
    return this.annotationTypeQualifierResolver;
  }
  
  public final DeserializedDescriptorResolver getDeserializedDescriptorResolver()
  {
    return this.deserializedDescriptorResolver;
  }
  
  public final ErrorReporter getErrorReporter()
  {
    return this.errorReporter;
  }
  
  public final JavaClassFinder getFinder()
  {
    return this.finder;
  }
  
  public final JavaClassesTracker getJavaClassesTracker()
  {
    return this.javaClassesTracker;
  }
  
  public final JavaPropertyInitializerEvaluator getJavaPropertyInitializerEvaluator()
  {
    return this.javaPropertyInitializerEvaluator;
  }
  
  public final JavaResolverCache getJavaResolverCache()
  {
    return this.javaResolverCache;
  }
  
  public final KotlinClassFinder getKotlinClassFinder()
  {
    return this.kotlinClassFinder;
  }
  
  public final NewKotlinTypeChecker getKotlinTypeChecker()
  {
    return this.kotlinTypeChecker;
  }
  
  public final LookupTracker getLookupTracker()
  {
    return this.lookupTracker;
  }
  
  public final ModuleDescriptor getModule()
  {
    return this.module;
  }
  
  public final ModuleClassResolver getModuleClassResolver()
  {
    return this.moduleClassResolver;
  }
  
  public final PackagePartProvider getPackagePartProvider()
  {
    return this.packagePartProvider;
  }
  
  public final ReflectionTypes getReflectionTypes()
  {
    return this.reflectionTypes;
  }
  
  public final JavaResolverSettings getSettings()
  {
    return this.settings;
  }
  
  public final SignatureEnhancement getSignatureEnhancement()
  {
    return this.signatureEnhancement;
  }
  
  public final SignaturePropagator getSignaturePropagator()
  {
    return this.signaturePropagator;
  }
  
  public final JavaSourceElementFactory getSourceElementFactory()
  {
    return this.sourceElementFactory;
  }
  
  public final StorageManager getStorageManager()
  {
    return this.storageManager;
  }
  
  public final SupertypeLoopChecker getSupertypeLoopChecker()
  {
    return this.supertypeLoopChecker;
  }
  
  public final JavaResolverComponents replace(JavaResolverCache paramJavaResolverCache)
  {
    Intrinsics.checkParameterIsNotNull(paramJavaResolverCache, "javaResolverCache");
    return new JavaResolverComponents(this.storageManager, this.finder, this.kotlinClassFinder, this.deserializedDescriptorResolver, this.signaturePropagator, this.errorReporter, paramJavaResolverCache, this.javaPropertyInitializerEvaluator, this.samConversionResolver, this.sourceElementFactory, this.moduleClassResolver, this.packagePartProvider, this.supertypeLoopChecker, this.lookupTracker, this.module, this.reflectionTypes, this.annotationTypeQualifierResolver, this.signatureEnhancement, this.javaClassesTracker, this.settings, this.kotlinTypeChecker);
  }
}
