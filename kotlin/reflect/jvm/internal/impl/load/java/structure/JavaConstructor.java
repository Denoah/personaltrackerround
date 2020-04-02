package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;

public abstract interface JavaConstructor
  extends JavaMember, JavaTypeParameterListOwner
{
  public abstract List<JavaValueParameter> getValueParameters();
}
