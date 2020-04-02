package kotlin.reflect.jvm.internal.impl.types.model;

import kotlin.jvm.internal.Intrinsics;

public abstract interface TypeSystemOptimizationContext
{
  public abstract boolean identicalArguments(SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2);
  
  public static final class DefaultImpls
  {
    public static boolean identicalArguments(TypeSystemOptimizationContext paramTypeSystemOptimizationContext, SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2)
    {
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker1, "a");
      Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker2, "b");
      return false;
    }
  }
}
