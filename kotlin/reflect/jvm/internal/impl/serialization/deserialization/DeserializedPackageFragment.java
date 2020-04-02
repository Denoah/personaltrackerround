package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PackageFragmentDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;

public abstract class DeserializedPackageFragment
  extends PackageFragmentDescriptorImpl
{
  private final StorageManager storageManager;
  
  public DeserializedPackageFragment(FqName paramFqName, StorageManager paramStorageManager, ModuleDescriptor paramModuleDescriptor)
  {
    super(paramModuleDescriptor, paramFqName);
    this.storageManager = paramStorageManager;
  }
  
  public abstract ClassDataFinder getClassDataFinder();
  
  public boolean hasTopLevelClass(Name paramName)
  {
    Intrinsics.checkParameterIsNotNull(paramName, "name");
    MemberScope localMemberScope = getMemberScope();
    boolean bool;
    if (((localMemberScope instanceof DeserializedMemberScope)) && (((DeserializedMemberScope)localMemberScope).getClassNames$deserialization().contains(paramName))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public abstract void initialize(DeserializationComponents paramDeserializationComponents);
}
