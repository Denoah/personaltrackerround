package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.List;
import java.util.Set;

public final class ModuleDependenciesImpl
  implements ModuleDependencies
{
  private final List<ModuleDescriptorImpl> allDependencies;
  private final List<ModuleDescriptorImpl> expectedByDependencies;
  private final Set<ModuleDescriptorImpl> modulesWhoseInternalsAreVisible;
  
  public ModuleDependenciesImpl(List<ModuleDescriptorImpl> paramList1, Set<ModuleDescriptorImpl> paramSet, List<ModuleDescriptorImpl> paramList2)
  {
    this.allDependencies = paramList1;
    this.modulesWhoseInternalsAreVisible = paramSet;
    this.expectedByDependencies = paramList2;
  }
  
  public List<ModuleDescriptorImpl> getAllDependencies()
  {
    return this.allDependencies;
  }
  
  public List<ModuleDescriptorImpl> getExpectedByDependencies()
  {
    return this.expectedByDependencies;
  }
  
  public Set<ModuleDescriptorImpl> getModulesWhoseInternalsAreVisible()
  {
    return this.modulesWhoseInternalsAreVisible;
  }
}
