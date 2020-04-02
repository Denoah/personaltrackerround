package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public abstract interface JavaAnnotationOwner
  extends JavaElement
{
  public abstract JavaAnnotation findAnnotation(FqName paramFqName);
  
  public abstract Collection<JavaAnnotation> getAnnotations();
  
  public abstract boolean isDeprecatedInJavaDoc();
}
