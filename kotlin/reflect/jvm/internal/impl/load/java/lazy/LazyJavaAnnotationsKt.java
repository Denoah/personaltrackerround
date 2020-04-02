package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner;

public final class LazyJavaAnnotationsKt
{
  public static final Annotations resolveAnnotations(LazyJavaResolverContext paramLazyJavaResolverContext, JavaAnnotationOwner paramJavaAnnotationOwner)
  {
    Intrinsics.checkParameterIsNotNull(paramLazyJavaResolverContext, "$this$resolveAnnotations");
    Intrinsics.checkParameterIsNotNull(paramJavaAnnotationOwner, "annotationsOwner");
    return (Annotations)new LazyJavaAnnotations(paramLazyJavaResolverContext, paramJavaAnnotationOwner);
  }
}
