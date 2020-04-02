package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.List;

public abstract interface JavaArrayAnnotationArgument
  extends JavaAnnotationArgument
{
  public abstract List<JavaAnnotationArgument> getElements();
}
