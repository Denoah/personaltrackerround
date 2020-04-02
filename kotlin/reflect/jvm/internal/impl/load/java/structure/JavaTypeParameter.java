package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;

public abstract interface JavaTypeParameter
  extends JavaClassifier
{
  public abstract Collection<JavaClassifierType> getUpperBounds();
}
