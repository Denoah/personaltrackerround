package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public final class AnnotationsImpl
  implements Annotations
{
  private final List<AnnotationDescriptor> annotations;
  
  public AnnotationsImpl(List<? extends AnnotationDescriptor> paramList)
  {
    this.annotations = paramList;
  }
  
  public AnnotationDescriptor findAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    return Annotations.DefaultImpls.findAnnotation(this, paramFqName);
  }
  
  public boolean hasAnnotation(FqName paramFqName)
  {
    Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
    return Annotations.DefaultImpls.hasAnnotation(this, paramFqName);
  }
  
  public boolean isEmpty()
  {
    return this.annotations.isEmpty();
  }
  
  public Iterator<AnnotationDescriptor> iterator()
  {
    return this.annotations.iterator();
  }
  
  public String toString()
  {
    return this.annotations.toString();
  }
}
