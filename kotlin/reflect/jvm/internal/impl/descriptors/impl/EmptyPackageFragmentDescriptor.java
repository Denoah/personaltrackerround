package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope.Empty;

public final class EmptyPackageFragmentDescriptor
  extends PackageFragmentDescriptorImpl
{
  public EmptyPackageFragmentDescriptor(ModuleDescriptor paramModuleDescriptor, FqName paramFqName)
  {
    super(paramModuleDescriptor, paramFqName);
  }
  
  public MemberScope.Empty getMemberScope()
  {
    return MemberScope.Empty.INSTANCE;
  }
}
