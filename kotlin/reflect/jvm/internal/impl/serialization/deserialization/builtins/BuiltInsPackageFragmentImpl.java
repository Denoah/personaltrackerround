package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.io.Closeable;
import java.io.InputStream;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsPackageFragment;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.PackageFragment;
import kotlin.reflect.jvm.internal.impl.metadata.builtins.BuiltInsBinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.builtins.BuiltInsBinaryVersion.Companion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializedPackageFragmentImpl;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public final class BuiltInsPackageFragmentImpl
  extends DeserializedPackageFragmentImpl
  implements BuiltInsPackageFragment
{
  public static final Companion Companion = new Companion(null);
  private final boolean isFallback;
  
  private BuiltInsPackageFragmentImpl(FqName paramFqName, StorageManager paramStorageManager, ModuleDescriptor paramModuleDescriptor, ProtoBuf.PackageFragment paramPackageFragment, BuiltInsBinaryVersion paramBuiltInsBinaryVersion, boolean paramBoolean)
  {
    super(paramFqName, paramStorageManager, paramModuleDescriptor, paramPackageFragment, (BinaryVersion)paramBuiltInsBinaryVersion, null);
    this.isFallback = paramBoolean;
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    public final BuiltInsPackageFragmentImpl create(FqName paramFqName, StorageManager paramStorageManager, ModuleDescriptor paramModuleDescriptor, InputStream paramInputStream, boolean paramBoolean)
    {
      Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
      Intrinsics.checkParameterIsNotNull(paramStorageManager, "storageManager");
      Intrinsics.checkParameterIsNotNull(paramModuleDescriptor, "module");
      Intrinsics.checkParameterIsNotNull(paramInputStream, "inputStream");
      paramInputStream = (Closeable)paramInputStream;
      Throwable localThrowable = (Throwable)null;
      try
      {
        Object localObject = (InputStream)paramInputStream;
        BuiltInsBinaryVersion localBuiltInsBinaryVersion = BuiltInsBinaryVersion.Companion.readFrom((InputStream)localObject);
        if (localBuiltInsBinaryVersion == null) {
          Intrinsics.throwUninitializedPropertyAccessException("version");
        }
        if (localBuiltInsBinaryVersion.isCompatible())
        {
          localObject = ProtoBuf.PackageFragment.parseFrom((InputStream)localObject, BuiltInSerializerProtocol.INSTANCE.getExtensionRegistry());
          CloseableKt.closeFinally(paramInputStream, localThrowable);
          Intrinsics.checkExpressionValueIsNotNull(localObject, "proto");
          return new BuiltInsPackageFragmentImpl(paramFqName, paramStorageManager, paramModuleDescriptor, (ProtoBuf.PackageFragment)localObject, localBuiltInsBinaryVersion, paramBoolean, null);
        }
        paramFqName = new java/lang/UnsupportedOperationException;
        paramStorageManager = new java/lang/StringBuilder;
        paramStorageManager.<init>();
        paramStorageManager.append("Kotlin built-in definition format version is not supported: ");
        paramStorageManager.append("expected ");
        paramStorageManager.append(BuiltInsBinaryVersion.INSTANCE);
        paramStorageManager.append(", actual ");
        paramStorageManager.append(localBuiltInsBinaryVersion);
        paramStorageManager.append(". ");
        paramStorageManager.append("Please update Kotlin");
        paramFqName.<init>(paramStorageManager.toString());
        throw ((Throwable)paramFqName);
      }
      finally
      {
        try
        {
          throw paramStorageManager;
        }
        finally
        {
          CloseableKt.closeFinally(paramInputStream, paramStorageManager);
        }
      }
    }
  }
}
