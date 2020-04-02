package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import kotlin.jvm.internal.Intrinsics;

public final class AnnotationsKt
{
  public static final Annotations composeAnnotations(Annotations paramAnnotations1, Annotations paramAnnotations2)
  {
    Intrinsics.checkParameterIsNotNull(paramAnnotations1, "first");
    Intrinsics.checkParameterIsNotNull(paramAnnotations2, "second");
    if (paramAnnotations1.isEmpty()) {
      paramAnnotations1 = paramAnnotations2;
    } else if (!paramAnnotations2.isEmpty()) {
      paramAnnotations1 = (Annotations)new CompositeAnnotations(new Annotations[] { paramAnnotations1, paramAnnotations2 });
    }
    return paramAnnotations1;
  }
}
