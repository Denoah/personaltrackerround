package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;

public final class AbstractNullabilityChecker
{
  public static final AbstractNullabilityChecker INSTANCE = new AbstractNullabilityChecker();
  
  private AbstractNullabilityChecker() {}
  
  private final boolean runIsPossibleSubtype(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2)
  {
    if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS)
    {
      int i;
      if ((!paramAbstractTypeCheckerContext.isSingleClassifierType(paramSimpleTypeMarker1)) && (!paramAbstractTypeCheckerContext.isIntersection(paramAbstractTypeCheckerContext.typeConstructor(paramSimpleTypeMarker1))) && (!paramAbstractTypeCheckerContext.isAllowedTypeVariable((KotlinTypeMarker)paramSimpleTypeMarker1))) {
        i = 0;
      } else {
        i = 1;
      }
      if ((_Assertions.ENABLED) && (i == 0))
      {
        paramAbstractTypeCheckerContext = new StringBuilder();
        paramAbstractTypeCheckerContext.append("Not singleClassifierType and not intersection subType: ");
        paramAbstractTypeCheckerContext.append(paramSimpleTypeMarker1);
        throw ((Throwable)new AssertionError(paramAbstractTypeCheckerContext.toString()));
      }
      if ((!paramAbstractTypeCheckerContext.isSingleClassifierType(paramSimpleTypeMarker2)) && (!paramAbstractTypeCheckerContext.isAllowedTypeVariable((KotlinTypeMarker)paramSimpleTypeMarker2))) {
        i = 0;
      } else {
        i = 1;
      }
      if ((_Assertions.ENABLED) && (i == 0))
      {
        paramAbstractTypeCheckerContext = new StringBuilder();
        paramAbstractTypeCheckerContext.append("Not singleClassifierType superType: ");
        paramAbstractTypeCheckerContext.append(paramSimpleTypeMarker2);
        throw ((Throwable)new AssertionError(paramAbstractTypeCheckerContext.toString()));
      }
    }
    if (paramAbstractTypeCheckerContext.isMarkedNullable(paramSimpleTypeMarker2)) {
      return true;
    }
    if (paramAbstractTypeCheckerContext.isDefinitelyNotNullType((KotlinTypeMarker)paramSimpleTypeMarker1)) {
      return true;
    }
    if (hasNotNullSupertype(paramAbstractTypeCheckerContext, paramSimpleTypeMarker1, (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE)) {
      return true;
    }
    if (paramAbstractTypeCheckerContext.isDefinitelyNotNullType((KotlinTypeMarker)paramSimpleTypeMarker2)) {
      return false;
    }
    if (hasNotNullSupertype(paramAbstractTypeCheckerContext, paramSimpleTypeMarker2, (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.UpperIfFlexible.INSTANCE)) {
      return false;
    }
    if (paramAbstractTypeCheckerContext.isClassType(paramSimpleTypeMarker1)) {
      return false;
    }
    return hasPathByNotMarkedNullableNodes(paramAbstractTypeCheckerContext, paramSimpleTypeMarker1, paramAbstractTypeCheckerContext.typeConstructor(paramSimpleTypeMarker2));
  }
  
  public final boolean hasNotNullSupertype(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, SimpleTypeMarker paramSimpleTypeMarker, AbstractTypeCheckerContext.SupertypesPolicy paramSupertypesPolicy)
  {
    Intrinsics.checkParameterIsNotNull(paramAbstractTypeCheckerContext, "$this$hasNotNullSupertype");
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "type");
    Intrinsics.checkParameterIsNotNull(paramSupertypesPolicy, "supertypesPolicy");
    boolean bool1 = paramAbstractTypeCheckerContext.isClassType(paramSimpleTypeMarker);
    boolean bool2 = false;
    int i;
    if (((bool1) && (!paramAbstractTypeCheckerContext.isMarkedNullable(paramSimpleTypeMarker))) || (paramAbstractTypeCheckerContext.isDefinitelyNotNullType((KotlinTypeMarker)paramSimpleTypeMarker))) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      bool2 = true;
    }
    else
    {
      paramAbstractTypeCheckerContext.initialize();
      ArrayDeque localArrayDeque = paramAbstractTypeCheckerContext.getSupertypesDeque();
      if (localArrayDeque == null) {
        Intrinsics.throwNpe();
      }
      Set localSet = paramAbstractTypeCheckerContext.getSupertypesSet();
      if (localSet == null) {
        Intrinsics.throwNpe();
      }
      localArrayDeque.push(paramSimpleTypeMarker);
      for (;;)
      {
        if ((((Collection)localArrayDeque).isEmpty() ^ true)) {
          if (localSet.size() <= 1000)
          {
            Object localObject = (SimpleTypeMarker)localArrayDeque.pop();
            Intrinsics.checkExpressionValueIsNotNull(localObject, "current");
            if (localSet.add(localObject))
            {
              AbstractTypeCheckerContext.SupertypesPolicy localSupertypesPolicy;
              if (paramAbstractTypeCheckerContext.isMarkedNullable((SimpleTypeMarker)localObject)) {
                localSupertypesPolicy = (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
              } else {
                localSupertypesPolicy = paramSupertypesPolicy;
              }
              if (!(Intrinsics.areEqual(localSupertypesPolicy, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE) ^ true)) {
                localSupertypesPolicy = null;
              }
              if (localSupertypesPolicy != null)
              {
                localObject = paramAbstractTypeCheckerContext.supertypes(paramAbstractTypeCheckerContext.typeConstructor((SimpleTypeMarker)localObject)).iterator();
                for (;;)
                {
                  if (!((Iterator)localObject).hasNext()) {
                    break label322;
                  }
                  SimpleTypeMarker localSimpleTypeMarker = localSupertypesPolicy.transformType(paramAbstractTypeCheckerContext, (KotlinTypeMarker)((Iterator)localObject).next());
                  if (((paramAbstractTypeCheckerContext.isClassType(localSimpleTypeMarker)) && (!paramAbstractTypeCheckerContext.isMarkedNullable(localSimpleTypeMarker))) || (paramAbstractTypeCheckerContext.isDefinitelyNotNullType((KotlinTypeMarker)localSimpleTypeMarker))) {
                    i = 1;
                  } else {
                    i = 0;
                  }
                  if (i != 0)
                  {
                    paramAbstractTypeCheckerContext.clear();
                    break;
                  }
                  localArrayDeque.add(localSimpleTypeMarker);
                }
              }
            }
          }
          else
          {
            label322:
            paramAbstractTypeCheckerContext = new StringBuilder();
            paramAbstractTypeCheckerContext.append("Too many supertypes for type: ");
            paramAbstractTypeCheckerContext.append(paramSimpleTypeMarker);
            paramAbstractTypeCheckerContext.append(". Supertypes = ");
            paramAbstractTypeCheckerContext.append(CollectionsKt.joinToString$default((Iterable)localSet, null, null, null, 0, null, null, 63, null));
            throw ((Throwable)new IllegalStateException(paramAbstractTypeCheckerContext.toString().toString()));
          }
        }
      }
      paramAbstractTypeCheckerContext.clear();
    }
    return bool2;
  }
  
  public final boolean hasPathByNotMarkedNullableNodes(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, SimpleTypeMarker paramSimpleTypeMarker, TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramAbstractTypeCheckerContext, "$this$hasPathByNotMarkedNullableNodes");
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "start");
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "end");
    boolean bool1 = paramAbstractTypeCheckerContext.isNothing((KotlinTypeMarker)paramSimpleTypeMarker);
    boolean bool2 = false;
    int i;
    if ((!bool1) && ((paramAbstractTypeCheckerContext.isMarkedNullable(paramSimpleTypeMarker)) || (!paramAbstractTypeCheckerContext.isEqualTypeConstructors(paramAbstractTypeCheckerContext.typeConstructor(paramSimpleTypeMarker), paramTypeConstructorMarker)))) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0)
    {
      bool2 = true;
    }
    else
    {
      paramAbstractTypeCheckerContext.initialize();
      ArrayDeque localArrayDeque = paramAbstractTypeCheckerContext.getSupertypesDeque();
      if (localArrayDeque == null) {
        Intrinsics.throwNpe();
      }
      Set localSet = paramAbstractTypeCheckerContext.getSupertypesSet();
      if (localSet == null) {
        Intrinsics.throwNpe();
      }
      localArrayDeque.push(paramSimpleTypeMarker);
      for (;;)
      {
        if ((((Collection)localArrayDeque).isEmpty() ^ true)) {
          if (localSet.size() <= 1000)
          {
            Object localObject1 = (SimpleTypeMarker)localArrayDeque.pop();
            Intrinsics.checkExpressionValueIsNotNull(localObject1, "current");
            if (localSet.add(localObject1))
            {
              if (paramAbstractTypeCheckerContext.isMarkedNullable((SimpleTypeMarker)localObject1)) {
                localObject2 = AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
              } else {
                localObject2 = AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
              }
              Object localObject2 = (AbstractTypeCheckerContext.SupertypesPolicy)localObject2;
              if (!(Intrinsics.areEqual(localObject2, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE) ^ true)) {
                localObject2 = null;
              }
              if (localObject2 != null)
              {
                localObject1 = paramAbstractTypeCheckerContext.supertypes(paramAbstractTypeCheckerContext.typeConstructor((SimpleTypeMarker)localObject1)).iterator();
                for (;;)
                {
                  if (!((Iterator)localObject1).hasNext()) {
                    break label344;
                  }
                  SimpleTypeMarker localSimpleTypeMarker = ((AbstractTypeCheckerContext.SupertypesPolicy)localObject2).transformType(paramAbstractTypeCheckerContext, (KotlinTypeMarker)((Iterator)localObject1).next());
                  if ((!paramAbstractTypeCheckerContext.isNothing((KotlinTypeMarker)localSimpleTypeMarker)) && ((paramAbstractTypeCheckerContext.isMarkedNullable(localSimpleTypeMarker)) || (!paramAbstractTypeCheckerContext.isEqualTypeConstructors(paramAbstractTypeCheckerContext.typeConstructor(localSimpleTypeMarker), paramTypeConstructorMarker)))) {
                    i = 0;
                  } else {
                    i = 1;
                  }
                  if (i != 0)
                  {
                    paramAbstractTypeCheckerContext.clear();
                    break;
                  }
                  localArrayDeque.add(localSimpleTypeMarker);
                }
              }
            }
          }
          else
          {
            label344:
            paramAbstractTypeCheckerContext = new StringBuilder();
            paramAbstractTypeCheckerContext.append("Too many supertypes for type: ");
            paramAbstractTypeCheckerContext.append(paramSimpleTypeMarker);
            paramAbstractTypeCheckerContext.append(". Supertypes = ");
            paramAbstractTypeCheckerContext.append(CollectionsKt.joinToString$default((Iterable)localSet, null, null, null, 0, null, null, 63, null));
            throw ((Throwable)new IllegalStateException(paramAbstractTypeCheckerContext.toString().toString()));
          }
        }
      }
      paramAbstractTypeCheckerContext.clear();
    }
    return bool2;
  }
  
  public final boolean isPossibleSubtype(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2)
  {
    Intrinsics.checkParameterIsNotNull(paramAbstractTypeCheckerContext, "context");
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker1, "subType");
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker2, "superType");
    return runIsPossibleSubtype(paramAbstractTypeCheckerContext, paramSimpleTypeMarker1, paramSimpleTypeMarker2);
  }
}
