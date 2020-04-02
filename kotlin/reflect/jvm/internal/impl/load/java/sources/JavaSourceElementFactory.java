package kotlin.reflect.jvm.internal.impl.load.java.sources;

import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;

public abstract interface JavaSourceElementFactory
{
  public abstract JavaSourceElement source(JavaElement paramJavaElement);
}
