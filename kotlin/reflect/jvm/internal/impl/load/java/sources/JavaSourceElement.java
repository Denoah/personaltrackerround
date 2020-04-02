package kotlin.reflect.jvm.internal.impl.load.java.sources;

import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement;

public abstract interface JavaSourceElement
  extends SourceElement
{
  public abstract JavaElement getJavaElement();
}
