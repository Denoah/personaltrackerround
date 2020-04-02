package kotlin.reflect.jvm.internal.impl.load.java.structure;

import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract interface JavaValueParameter
  extends JavaAnnotationOwner
{
  public abstract Name getName();
  
  public abstract JavaType getType();
  
  public abstract boolean isVararg();
}
