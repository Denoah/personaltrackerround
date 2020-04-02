package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ServiceLoader;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public abstract interface BuiltInsLoader
{
  public static final Companion Companion = Companion.$$INSTANCE;
  
  public abstract PackageFragmentProvider createPackageFragmentProvider(StorageManager paramStorageManager, ModuleDescriptor paramModuleDescriptor, Iterable<? extends ClassDescriptorFactory> paramIterable, PlatformDependentDeclarationFilter paramPlatformDependentDeclarationFilter, AdditionalClassPartsProvider paramAdditionalClassPartsProvider, boolean paramBoolean);
  
  public static final class Companion
  {
    private static final Lazy Instance$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0)Instance.2.INSTANCE);
    
    private Companion() {}
    
    public final BuiltInsLoader getInstance()
    {
      Lazy localLazy = Instance$delegate;
      KProperty localKProperty = $$delegatedProperties[0];
      return (BuiltInsLoader)localLazy.getValue();
    }
  }
}
