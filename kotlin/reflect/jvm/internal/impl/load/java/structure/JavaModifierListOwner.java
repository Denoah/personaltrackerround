package kotlin.reflect.jvm.internal.impl.load.java.structure;

import kotlin.reflect.jvm.internal.impl.descriptors.Visibility;

public abstract interface JavaModifierListOwner
  extends JavaElement
{
  public abstract Visibility getVisibility();
  
  public abstract boolean isAbstract();
  
  public abstract boolean isFinal();
  
  public abstract boolean isStatic();
}
