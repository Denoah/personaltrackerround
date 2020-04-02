package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;

public abstract interface PackageViewDescriptor
  extends DeclarationDescriptor
{
  public abstract FqName getFqName();
  
  public abstract List<PackageFragmentDescriptor> getFragments();
  
  public abstract MemberScope getMemberScope();
  
  public abstract ModuleDescriptor getModule();
  
  public abstract boolean isEmpty();
  
  public static final class DefaultImpls
  {
    public static boolean isEmpty(PackageViewDescriptor paramPackageViewDescriptor)
    {
      return paramPackageViewDescriptor.getFragments().isEmpty();
    }
  }
}
