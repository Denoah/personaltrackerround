package kotlin.reflect.jvm.internal.impl.load.java.structure;

import kotlin.reflect.jvm.internal.impl.name.Name;

public abstract interface JavaNamedElement
  extends JavaElement
{
  public abstract Name getName();
}
