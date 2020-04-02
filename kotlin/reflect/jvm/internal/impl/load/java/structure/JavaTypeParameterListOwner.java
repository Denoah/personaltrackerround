package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;

public abstract interface JavaTypeParameterListOwner
  extends JavaElement
{
  public abstract List<JavaTypeParameter> getTypeParameters();
}
