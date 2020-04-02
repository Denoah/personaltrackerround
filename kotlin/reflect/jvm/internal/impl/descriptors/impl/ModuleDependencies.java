package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.List;
import java.util.Set;

public abstract interface ModuleDependencies
{
  public abstract List<ModuleDescriptorImpl> getAllDependencies();
  
  public abstract List<ModuleDescriptorImpl> getExpectedByDependencies();
  
  public abstract Set<ModuleDescriptorImpl> getModulesWhoseInternalsAreVisible();
}
