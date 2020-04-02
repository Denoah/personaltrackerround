package kotlin.reflect.jvm.internal.impl.load.java.structure;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.name.ClassId;

public abstract interface JavaAnnotation
  extends JavaElement
{
  public abstract Collection<JavaAnnotationArgument> getArguments();
  
  public abstract ClassId getClassId();
  
  public abstract boolean isIdeExternalAnnotation();
  
  public abstract JavaClass resolve();
  
  public static final class DefaultImpls
  {
    public static boolean isIdeExternalAnnotation(JavaAnnotation paramJavaAnnotation)
    {
      return false;
    }
  }
}
