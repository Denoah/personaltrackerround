package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoader;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.NotFoundClasses;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProviderImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupTracker;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupTracker.DO_NOTHING;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.serialization.SerializerExtensionProtocol;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationAndConstantLoader;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.AnnotationAndConstantLoaderImpl;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ClassDataFinder;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ContractDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ContractDeserializer.Companion;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationConfiguration.Default;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializedClassDataFinder;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.ErrorReporter;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.FlexibleTypeDeserializer;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.FlexibleTypeDeserializer.ThrowException;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.LocalClassifierTypeSettings;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.LocalClassifierTypeSettings.Default;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public final class BuiltInsLoaderImpl
  implements BuiltInsLoader
{
  private final BuiltInsResourceLoader resourceLoader = new BuiltInsResourceLoader();
  
  public BuiltInsLoaderImpl() {}
  
  public final PackageFragmentProvider createBuiltInPackageFragmentProvider(StorageManager paramStorageManager, ModuleDescriptor paramModuleDescriptor, Set<FqName> paramSet, Iterable<? extends ClassDescriptorFactory> paramIterable, PlatformDependentDeclarationFilter paramPlatformDependentDeclarationFilter, AdditionalClassPartsProvider paramAdditionalClassPartsProvider, boolean paramBoolean, Function1<? super String, ? extends InputStream> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramStorageManager, "storageManager");
    Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
    Intrinsics.checkParameterIsNotNull(paramSet, "packageFqNames");
    Intrinsics.checkParameterIsNotNull(paramIterable, "classDescriptorFactories");
    Intrinsics.checkParameterIsNotNull(paramPlatformDependentDeclarationFilter, "platformDependentDeclarationFilter");
    Intrinsics.checkParameterIsNotNull(paramAdditionalClassPartsProvider, "additionalClassPartsProvider");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "loadResource");
    paramSet = (Iterable)paramSet;
    Object localObject1 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramSet, 10));
    Object localObject2 = paramSet.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (FqName)((Iterator)localObject2).next();
      paramSet = BuiltInSerializerProtocol.INSTANCE.getBuiltInsFilePath((FqName)localObject3);
      localObject4 = (InputStream)paramFunction1.invoke(paramSet);
      if (localObject4 != null)
      {
        ((Collection)localObject1).add(BuiltInsPackageFragmentImpl.Companion.create((FqName)localObject3, paramStorageManager, paramModuleDescriptor, (InputStream)localObject4, paramBoolean));
      }
      else
      {
        paramStorageManager = new StringBuilder();
        paramStorageManager.append("Resource not found in classpath: ");
        paramStorageManager.append(paramSet);
        throw ((Throwable)new IllegalStateException(paramStorageManager.toString()));
      }
    }
    paramSet = (List)localObject1;
    paramFunction1 = new PackageFragmentProviderImpl((Collection)paramSet);
    localObject2 = new NotFoundClasses(paramStorageManager, paramModuleDescriptor);
    localObject1 = (DeserializationConfiguration)DeserializationConfiguration.Default.INSTANCE;
    paramFunction1 = (PackageFragmentProvider)paramFunction1;
    ClassDataFinder localClassDataFinder = (ClassDataFinder)new DeserializedClassDataFinder(paramFunction1);
    Object localObject3 = (AnnotationAndConstantLoader)new AnnotationAndConstantLoaderImpl(paramModuleDescriptor, (NotFoundClasses)localObject2, (SerializerExtensionProtocol)BuiltInSerializerProtocol.INSTANCE);
    Object localObject4 = (LocalClassifierTypeSettings)LocalClassifierTypeSettings.Default.INSTANCE;
    ErrorReporter localErrorReporter = ErrorReporter.DO_NOTHING;
    Intrinsics.checkExpressionValueIsNotNull(localErrorReporter, "ErrorReporter.DO_NOTHING");
    paramStorageManager = new DeserializationComponents(paramStorageManager, paramModuleDescriptor, (DeserializationConfiguration)localObject1, localClassDataFinder, (AnnotationAndConstantLoader)localObject3, paramFunction1, (LocalClassifierTypeSettings)localObject4, localErrorReporter, (LookupTracker)LookupTracker.DO_NOTHING.INSTANCE, (FlexibleTypeDeserializer)FlexibleTypeDeserializer.ThrowException.INSTANCE, paramIterable, (NotFoundClasses)localObject2, ContractDeserializer.Companion.getDEFAULT(), paramAdditionalClassPartsProvider, paramPlatformDependentDeclarationFilter, BuiltInSerializerProtocol.INSTANCE.getExtensionRegistry(), null, 65536, null);
    paramModuleDescriptor = paramSet.iterator();
    while (paramModuleDescriptor.hasNext()) {
      ((BuiltInsPackageFragmentImpl)paramModuleDescriptor.next()).initialize(paramStorageManager);
    }
    return paramFunction1;
  }
  
  public PackageFragmentProvider createPackageFragmentProvider(StorageManager paramStorageManager, ModuleDescriptor paramModuleDescriptor, Iterable<? extends ClassDescriptorFactory> paramIterable, PlatformDependentDeclarationFilter paramPlatformDependentDeclarationFilter, AdditionalClassPartsProvider paramAdditionalClassPartsProvider, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 36
    //   3: invokestatic 42	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc -17
    //   9: invokestatic 42	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_3
    //   13: ldc 48
    //   15: invokestatic 42	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   18: aload 4
    //   20: ldc 50
    //   22: invokestatic 42	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   25: aload 5
    //   27: ldc 52
    //   29: invokestatic 42	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   32: getstatic 245	kotlin/reflect/jvm/internal/impl/builtins/KotlinBuiltIns:BUILT_INS_PACKAGE_FQ_NAMES	Ljava/util/Set;
    //   35: astore 7
    //   37: aload 7
    //   39: ldc -9
    //   41: invokestatic 193	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   44: aload_0
    //   45: aload_1
    //   46: aload_2
    //   47: aload 7
    //   49: aload_3
    //   50: aload 4
    //   52: aload 5
    //   54: iload 6
    //   56: new 8	kotlin/reflect/jvm/internal/impl/serialization/deserialization/builtins/BuiltInsLoaderImpl$createPackageFragmentProvider$1
    //   59: dup
    //   60: aload_0
    //   61: getfield 19	kotlin/reflect/jvm/internal/impl/serialization/deserialization/builtins/BuiltInsLoaderImpl:resourceLoader	Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/builtins/BuiltInsResourceLoader;
    //   64: invokespecial 250	kotlin/reflect/jvm/internal/impl/serialization/deserialization/builtins/BuiltInsLoaderImpl$createPackageFragmentProvider$1:<init>	(Lkotlin/reflect/jvm/internal/impl/serialization/deserialization/builtins/BuiltInsResourceLoader;)V
    //   67: checkcast 96	kotlin/jvm/functions/Function1
    //   70: invokevirtual 34	kotlin/reflect/jvm/internal/impl/serialization/deserialization/builtins/BuiltInsLoaderImpl:createBuiltInPackageFragmentProvider	(Lkotlin/reflect/jvm/internal/impl/storage/StorageManager;Lkotlin/reflect/jvm/internal/impl/descriptors/ModuleDescriptor;Ljava/util/Set;Ljava/lang/Iterable;Lkotlin/reflect/jvm/internal/impl/descriptors/deserialization/PlatformDependentDeclarationFilter;Lkotlin/reflect/jvm/internal/impl/descriptors/deserialization/AdditionalClassPartsProvider;ZLkotlin/jvm/functions/Function1;)Lkotlin/reflect/jvm/internal/impl/descriptors/PackageFragmentProvider;
    //   73: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	74	0	this	BuiltInsLoaderImpl
    //   0	74	1	paramStorageManager	StorageManager
    //   0	74	2	paramModuleDescriptor	ModuleDescriptor
    //   0	74	3	paramIterable	Iterable<? extends ClassDescriptorFactory>
    //   0	74	4	paramPlatformDependentDeclarationFilter	PlatformDependentDeclarationFilter
    //   0	74	5	paramAdditionalClassPartsProvider	AdditionalClassPartsProvider
    //   0	74	6	paramBoolean	boolean
    //   35	13	7	localSet	Set
  }
}
