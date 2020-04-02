package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.DynamicTypesKt;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;

public final class IntersectionTypeKt
{
  public static final UnwrappedType intersectTypes(List<? extends UnwrappedType> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "types");
    int i = paramList.size();
    if (i != 0)
    {
      if (i != 1)
      {
        Object localObject1 = (Iterable)paramList;
        Collection localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
        Iterator localIterator = ((Iterable)localObject1).iterator();
        i = 0;
        int j = i;
        while (localIterator.hasNext())
        {
          localObject2 = (UnwrappedType)localIterator.next();
          if ((i == 0) && (!KotlinTypeKt.isError((KotlinType)localObject2))) {
            i = 0;
          } else {
            i = 1;
          }
          if ((localObject2 instanceof SimpleType))
          {
            localObject2 = (SimpleType)localObject2;
          }
          else
          {
            if (!(localObject2 instanceof FlexibleType)) {
              break label169;
            }
            if (DynamicTypesKt.isDynamic((KotlinType)localObject2)) {
              return localObject2;
            }
            localObject2 = ((FlexibleType)localObject2).getLowerBound();
            j = 1;
          }
          localCollection.add(localObject2);
          continue;
          label169:
          throw new NoWhenBranchMatchedException();
        }
        Object localObject2 = (List)localCollection;
        if (i != 0)
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("Intersection of error types: ");
          ((StringBuilder)localObject2).append(paramList);
          paramList = ErrorUtils.createErrorType(((StringBuilder)localObject2).toString());
          Intrinsics.checkExpressionValueIsNotNull(paramList, "ErrorUtils.createErrorTy… of error types: $types\")");
          return (UnwrappedType)paramList;
        }
        if (j == 0) {
          return (UnwrappedType)TypeIntersector.INSTANCE.intersectTypes$descriptors((List)localObject2);
        }
        paramList = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
        localObject1 = ((Iterable)localObject1).iterator();
        while (((Iterator)localObject1).hasNext()) {
          paramList.add(FlexibleTypesKt.upperIfFlexible((KotlinType)((Iterator)localObject1).next()));
        }
        paramList = (List)paramList;
        return KotlinTypeFactory.flexibleType(TypeIntersector.INSTANCE.intersectTypes$descriptors((List)localObject2), TypeIntersector.INSTANCE.intersectTypes$descriptors(paramList));
      }
      return (UnwrappedType)CollectionsKt.single(paramList);
    }
    throw ((Throwable)new IllegalStateException("Expected some types".toString()));
  }
}
