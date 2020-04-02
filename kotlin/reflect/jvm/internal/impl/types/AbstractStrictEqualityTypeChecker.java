package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;

public final class AbstractStrictEqualityTypeChecker
{
  public static final AbstractStrictEqualityTypeChecker INSTANCE = new AbstractStrictEqualityTypeChecker();
  
  private AbstractStrictEqualityTypeChecker() {}
  
  private final boolean strictEqualSimpleTypes(TypeSystemContext paramTypeSystemContext, SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2)
  {
    KotlinTypeMarker localKotlinTypeMarker1 = (KotlinTypeMarker)paramSimpleTypeMarker1;
    int i = paramTypeSystemContext.argumentsCount(localKotlinTypeMarker1);
    KotlinTypeMarker localKotlinTypeMarker2 = (KotlinTypeMarker)paramSimpleTypeMarker2;
    if ((i == paramTypeSystemContext.argumentsCount(localKotlinTypeMarker2)) && (paramTypeSystemContext.isMarkedNullable(paramSimpleTypeMarker1) == paramTypeSystemContext.isMarkedNullable(paramSimpleTypeMarker2)))
    {
      if (paramTypeSystemContext.asDefinitelyNotNullType(paramSimpleTypeMarker1) == null) {
        i = 1;
      } else {
        i = 0;
      }
      int j;
      if (paramTypeSystemContext.asDefinitelyNotNullType(paramSimpleTypeMarker2) == null) {
        j = 1;
      } else {
        j = 0;
      }
      if ((i == j) && (paramTypeSystemContext.isEqualTypeConstructors(paramTypeSystemContext.typeConstructor(paramSimpleTypeMarker1), paramTypeSystemContext.typeConstructor(paramSimpleTypeMarker2))))
      {
        if (paramTypeSystemContext.identicalArguments(paramSimpleTypeMarker1, paramSimpleTypeMarker2)) {
          return true;
        }
        j = paramTypeSystemContext.argumentsCount(localKotlinTypeMarker1);
        for (i = 0; i < j; i++)
        {
          paramSimpleTypeMarker2 = paramTypeSystemContext.getArgument(localKotlinTypeMarker1, i);
          paramSimpleTypeMarker1 = paramTypeSystemContext.getArgument(localKotlinTypeMarker2, i);
          if (paramTypeSystemContext.isStarProjection(paramSimpleTypeMarker2) != paramTypeSystemContext.isStarProjection(paramSimpleTypeMarker1)) {
            return false;
          }
          if (!paramTypeSystemContext.isStarProjection(paramSimpleTypeMarker2))
          {
            if (paramTypeSystemContext.getVariance(paramSimpleTypeMarker2) != paramTypeSystemContext.getVariance(paramSimpleTypeMarker1)) {
              return false;
            }
            if (!strictEqualTypesInternal(paramTypeSystemContext, paramTypeSystemContext.getType(paramSimpleTypeMarker2), paramTypeSystemContext.getType(paramSimpleTypeMarker1))) {
              return false;
            }
          }
        }
        return true;
      }
    }
    return false;
  }
  
  private final boolean strictEqualTypesInternal(TypeSystemContext paramTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker1, KotlinTypeMarker paramKotlinTypeMarker2)
  {
    boolean bool = true;
    if (paramKotlinTypeMarker1 == paramKotlinTypeMarker2) {
      return true;
    }
    SimpleTypeMarker localSimpleTypeMarker1 = paramTypeSystemContext.asSimpleType(paramKotlinTypeMarker1);
    SimpleTypeMarker localSimpleTypeMarker2 = paramTypeSystemContext.asSimpleType(paramKotlinTypeMarker2);
    if ((localSimpleTypeMarker1 != null) && (localSimpleTypeMarker2 != null)) {
      return strictEqualSimpleTypes(paramTypeSystemContext, localSimpleTypeMarker1, localSimpleTypeMarker2);
    }
    paramKotlinTypeMarker1 = paramTypeSystemContext.asFlexibleType(paramKotlinTypeMarker1);
    paramKotlinTypeMarker2 = paramTypeSystemContext.asFlexibleType(paramKotlinTypeMarker2);
    if ((paramKotlinTypeMarker1 != null) && (paramKotlinTypeMarker2 != null))
    {
      if ((!strictEqualSimpleTypes(paramTypeSystemContext, paramTypeSystemContext.lowerBound(paramKotlinTypeMarker1), paramTypeSystemContext.lowerBound(paramKotlinTypeMarker2))) || (!strictEqualSimpleTypes(paramTypeSystemContext, paramTypeSystemContext.upperBound(paramKotlinTypeMarker1), paramTypeSystemContext.upperBound(paramKotlinTypeMarker2)))) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public final boolean strictEqualTypes(TypeSystemContext paramTypeSystemContext, KotlinTypeMarker paramKotlinTypeMarker1, KotlinTypeMarker paramKotlinTypeMarker2)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeSystemContext, "context");
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker1, "a");
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker2, "b");
    return strictEqualTypesInternal(paramTypeSystemContext, paramKotlinTypeMarker1, paramKotlinTypeMarker2);
  }
}
