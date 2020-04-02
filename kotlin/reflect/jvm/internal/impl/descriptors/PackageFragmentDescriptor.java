package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;

public abstract interface PackageFragmentDescriptor
  extends ClassOrPackageFragmentDescriptor
{
  public abstract ModuleDescriptor getContainingDeclaration();
  
  public abstract FqName getFqName();
  
  public abstract MemberScope getMemberScope();
}
