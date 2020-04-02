package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

public class AnnotatedImpl
  implements Annotated
{
  private final Annotations annotations;
  
  public AnnotatedImpl(Annotations paramAnnotations)
  {
    this.annotations = paramAnnotations;
  }
  
  public Annotations getAnnotations()
  {
    Annotations localAnnotations = this.annotations;
    if (localAnnotations == null) {
      $$$reportNull$$$0(1);
    }
    return localAnnotations;
  }
}
