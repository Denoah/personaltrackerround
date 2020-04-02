package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract interface ModuleDescriptor
  extends DeclarationDescriptor
{
  public abstract KotlinBuiltIns getBuiltIns();
  
  public abstract <T> T getCapability(Capability<T> paramCapability);
  
  public abstract List<ModuleDescriptor> getExpectedByModules();
  
  public abstract PackageViewDescriptor getPackage(FqName paramFqName);
  
  public abstract Collection<FqName> getSubPackagesOf(FqName paramFqName, Function1<? super Name, Boolean> paramFunction1);
  
  public abstract boolean shouldSeeInternalsOf(ModuleDescriptor paramModuleDescriptor);
  
  public static final class Capability<T>
  {
    private final String name;
    
    public Capability(String paramString)
    {
      this.name = paramString;
    }
    
    public String toString()
    {
      return this.name;
    }
  }
  
  public static final class DefaultImpls
  {
    public static <R, D> R accept(ModuleDescriptor paramModuleDescriptor, DeclarationDescriptorVisitor<R, D> paramDeclarationDescriptorVisitor, D paramD)
    {
      Intrinsics.checkParameterIsNotNull(paramDeclarationDescriptorVisitor, "visitor");
      return paramDeclarationDescriptorVisitor.visitModuleDeclaration(paramModuleDescriptor, paramD);
    }
    
    public static DeclarationDescriptor getContainingDeclaration(ModuleDescriptor paramModuleDescriptor)
    {
      return null;
    }
  }
}
