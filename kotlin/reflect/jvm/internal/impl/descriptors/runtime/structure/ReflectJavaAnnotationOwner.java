package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import java.lang.reflect.AnnotatedElement;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.name.FqName;

public abstract interface ReflectJavaAnnotationOwner
  extends JavaAnnotationOwner
{
  public abstract AnnotatedElement getElement();
  
  public static final class DefaultImpls
  {
    public static ReflectJavaAnnotation findAnnotation(ReflectJavaAnnotationOwner paramReflectJavaAnnotationOwner, FqName paramFqName)
    {
      Intrinsics.checkParameterIsNotNull(paramFqName, "fqName");
      paramReflectJavaAnnotationOwner = paramReflectJavaAnnotationOwner.getElement();
      if (paramReflectJavaAnnotationOwner != null)
      {
        paramReflectJavaAnnotationOwner = paramReflectJavaAnnotationOwner.getDeclaredAnnotations();
        if (paramReflectJavaAnnotationOwner != null) {
          return ReflectJavaAnnotationOwnerKt.findAnnotation(paramReflectJavaAnnotationOwner, paramFqName);
        }
      }
      paramReflectJavaAnnotationOwner = null;
      return paramReflectJavaAnnotationOwner;
    }
    
    public static List<ReflectJavaAnnotation> getAnnotations(ReflectJavaAnnotationOwner paramReflectJavaAnnotationOwner)
    {
      paramReflectJavaAnnotationOwner = paramReflectJavaAnnotationOwner.getElement();
      if (paramReflectJavaAnnotationOwner != null)
      {
        paramReflectJavaAnnotationOwner = paramReflectJavaAnnotationOwner.getDeclaredAnnotations();
        if (paramReflectJavaAnnotationOwner != null)
        {
          paramReflectJavaAnnotationOwner = ReflectJavaAnnotationOwnerKt.getAnnotations(paramReflectJavaAnnotationOwner);
          if (paramReflectJavaAnnotationOwner != null) {
            return paramReflectJavaAnnotationOwner;
          }
        }
      }
      paramReflectJavaAnnotationOwner = CollectionsKt.emptyList();
      return paramReflectJavaAnnotationOwner;
    }
    
    public static boolean isDeprecatedInJavaDoc(ReflectJavaAnnotationOwner paramReflectJavaAnnotationOwner)
    {
      return false;
    }
  }
}
