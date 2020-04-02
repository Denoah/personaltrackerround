package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import java.lang.annotation.Annotation;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceFile;

public final class ReflectAnnotationSource
  implements SourceElement
{
  private final Annotation annotation;
  
  public ReflectAnnotationSource(Annotation paramAnnotation)
  {
    this.annotation = paramAnnotation;
  }
  
  public final Annotation getAnnotation()
  {
    return this.annotation;
  }
  
  public SourceFile getContainingFile()
  {
    SourceFile localSourceFile = SourceFile.NO_SOURCE_FILE;
    Intrinsics.checkExpressionValueIsNotNull(localSourceFile, "SourceFile.NO_SOURCE_FILE");
    return localSourceFile;
  }
}
