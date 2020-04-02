package androidx.camera.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class MultiValueSet<C>
{
  private Set<C> mSet = new HashSet();
  
  public MultiValueSet() {}
  
  public void addAll(List<C> paramList)
  {
    this.mSet.addAll(paramList);
  }
  
  public abstract MultiValueSet<C> clone();
  
  public List<C> getAllItems()
  {
    return Collections.unmodifiableList(new ArrayList(this.mSet));
  }
}
