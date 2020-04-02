package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.types.model.ArgumentList;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;

public final class AbstractTypeChecker
{
  public static final AbstractTypeChecker INSTANCE = new AbstractTypeChecker();
  public static boolean RUN_SLOW_ASSERTIONS;
  
  private AbstractTypeChecker() {}
  
  private final Boolean checkSubtypeForIntegerLiteralType(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2)
  {
    if ((!paramAbstractTypeCheckerContext.isIntegerLiteralType(paramSimpleTypeMarker1)) && (!paramAbstractTypeCheckerContext.isIntegerLiteralType(paramSimpleTypeMarker2))) {
      return null;
    }
    Lambda local1 = new Lambda(paramAbstractTypeCheckerContext)
    {
      public final boolean invoke(SimpleTypeMarker paramAnonymousSimpleTypeMarker1, SimpleTypeMarker paramAnonymousSimpleTypeMarker2)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousSimpleTypeMarker1, "integerLiteralType");
        Intrinsics.checkParameterIsNotNull(paramAnonymousSimpleTypeMarker2, "type");
        paramAnonymousSimpleTypeMarker1 = (Iterable)this.$this_checkSubtypeForIntegerLiteralType.possibleIntegerTypes(paramAnonymousSimpleTypeMarker1);
        boolean bool1 = paramAnonymousSimpleTypeMarker1 instanceof Collection;
        boolean bool2 = false;
        if ((bool1) && (((Collection)paramAnonymousSimpleTypeMarker1).isEmpty()))
        {
          bool1 = bool2;
        }
        else
        {
          paramAnonymousSimpleTypeMarker1 = paramAnonymousSimpleTypeMarker1.iterator();
          KotlinTypeMarker localKotlinTypeMarker;
          do
          {
            bool1 = bool2;
            if (!paramAnonymousSimpleTypeMarker1.hasNext()) {
              break;
            }
            localKotlinTypeMarker = (KotlinTypeMarker)paramAnonymousSimpleTypeMarker1.next();
          } while (!Intrinsics.areEqual(this.$this_checkSubtypeForIntegerLiteralType.typeConstructor(localKotlinTypeMarker), this.$this_checkSubtypeForIntegerLiteralType.typeConstructor(paramAnonymousSimpleTypeMarker2)));
          bool1 = true;
        }
        return bool1;
      }
    };
    if ((paramAbstractTypeCheckerContext.isIntegerLiteralType(paramSimpleTypeMarker1)) && (paramAbstractTypeCheckerContext.isIntegerLiteralType(paramSimpleTypeMarker2))) {
      return Boolean.valueOf(true);
    }
    if (paramAbstractTypeCheckerContext.isIntegerLiteralType(paramSimpleTypeMarker1))
    {
      if (local1.invoke(paramSimpleTypeMarker1, paramSimpleTypeMarker2)) {
        return Boolean.valueOf(true);
      }
    }
    else if ((paramAbstractTypeCheckerContext.isIntegerLiteralType(paramSimpleTypeMarker2)) && (local1.invoke(paramSimpleTypeMarker2, paramSimpleTypeMarker1))) {
      return Boolean.valueOf(true);
    }
    return null;
  }
  
  private final Boolean checkSubtypeForSpecialCases(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2)
  {
    KotlinTypeMarker localKotlinTypeMarker1 = (KotlinTypeMarker)paramSimpleTypeMarker1;
    boolean bool1 = paramAbstractTypeCheckerContext.isError(localKotlinTypeMarker1);
    boolean bool2 = false;
    Boolean localBoolean = Boolean.valueOf(true);
    if ((!bool1) && (!paramAbstractTypeCheckerContext.isError((KotlinTypeMarker)paramSimpleTypeMarker2)))
    {
      if ((!paramAbstractTypeCheckerContext.isStubType(paramSimpleTypeMarker1)) && (!paramAbstractTypeCheckerContext.isStubType(paramSimpleTypeMarker2)))
      {
        CapturedTypeMarker localCapturedTypeMarker = paramAbstractTypeCheckerContext.asCapturedType(paramSimpleTypeMarker2);
        KotlinTypeMarker localKotlinTypeMarker2;
        if (localCapturedTypeMarker != null) {
          localKotlinTypeMarker2 = paramAbstractTypeCheckerContext.lowerType(localCapturedTypeMarker);
        } else {
          localKotlinTypeMarker2 = null;
        }
        if ((localCapturedTypeMarker != null) && (localKotlinTypeMarker2 != null))
        {
          paramSimpleTypeMarker1 = paramAbstractTypeCheckerContext.getLowerCapturedTypePolicy(paramSimpleTypeMarker1, localCapturedTypeMarker);
          int i = AbstractTypeChecker.WhenMappings.$EnumSwitchMapping$2[paramSimpleTypeMarker1.ordinal()];
          if (i != 1)
          {
            if ((i == 2) && (isSubtypeOf(paramAbstractTypeCheckerContext, localKotlinTypeMarker1, localKotlinTypeMarker2))) {
              return localBoolean;
            }
          }
          else {
            return Boolean.valueOf(isSubtypeOf(paramAbstractTypeCheckerContext, localKotlinTypeMarker1, localKotlinTypeMarker2));
          }
        }
        paramSimpleTypeMarker1 = paramAbstractTypeCheckerContext.typeConstructor(paramSimpleTypeMarker2);
        if (paramAbstractTypeCheckerContext.isIntersection(paramSimpleTypeMarker1))
        {
          bool1 = paramAbstractTypeCheckerContext.isMarkedNullable(paramSimpleTypeMarker2);
          if ((_Assertions.ENABLED) && (!(bool1 ^ true)))
          {
            paramAbstractTypeCheckerContext = new StringBuilder();
            paramAbstractTypeCheckerContext.append("Intersection type should not be marked nullable!: ");
            paramAbstractTypeCheckerContext.append(paramSimpleTypeMarker2);
            throw ((Throwable)new AssertionError(paramAbstractTypeCheckerContext.toString()));
          }
          paramSimpleTypeMarker1 = (Iterable)paramAbstractTypeCheckerContext.supertypes(paramSimpleTypeMarker1);
          if (((paramSimpleTypeMarker1 instanceof Collection)) && (((Collection)paramSimpleTypeMarker1).isEmpty())) {}
          do
          {
            while (!paramSimpleTypeMarker1.hasNext())
            {
              bool2 = true;
              break;
              paramSimpleTypeMarker1 = paramSimpleTypeMarker1.iterator();
            }
            paramSimpleTypeMarker2 = (KotlinTypeMarker)paramSimpleTypeMarker1.next();
          } while (INSTANCE.isSubtypeOf(paramAbstractTypeCheckerContext, localKotlinTypeMarker1, paramSimpleTypeMarker2));
          return Boolean.valueOf(bool2);
        }
        return null;
      }
      return localBoolean;
    }
    if (paramAbstractTypeCheckerContext.isErrorTypeEqualsToAnything()) {
      return localBoolean;
    }
    if ((paramAbstractTypeCheckerContext.isMarkedNullable(paramSimpleTypeMarker1)) && (!paramAbstractTypeCheckerContext.isMarkedNullable(paramSimpleTypeMarker2))) {
      return Boolean.valueOf(false);
    }
    return Boolean.valueOf(AbstractStrictEqualityTypeChecker.INSTANCE.strictEqualTypes((TypeSystemContext)paramAbstractTypeCheckerContext, (KotlinTypeMarker)paramAbstractTypeCheckerContext.withNullability(paramSimpleTypeMarker1, false), (KotlinTypeMarker)paramAbstractTypeCheckerContext.withNullability(paramSimpleTypeMarker2, false)));
  }
  
  private final List<SimpleTypeMarker> collectAllSupertypesWithGivenTypeConstructor(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, SimpleTypeMarker paramSimpleTypeMarker, TypeConstructorMarker paramTypeConstructorMarker)
  {
    Object localObject1 = paramAbstractTypeCheckerContext.fastCorrespondingSupertypes(paramSimpleTypeMarker, paramTypeConstructorMarker);
    if (localObject1 != null) {
      return localObject1;
    }
    if ((!paramAbstractTypeCheckerContext.isClassTypeConstructor(paramTypeConstructorMarker)) && (paramAbstractTypeCheckerContext.isClassType(paramSimpleTypeMarker))) {
      return CollectionsKt.emptyList();
    }
    if (paramAbstractTypeCheckerContext.isCommonFinalClassConstructor(paramTypeConstructorMarker))
    {
      if (paramAbstractTypeCheckerContext.areEqualTypeConstructors(paramAbstractTypeCheckerContext.typeConstructor(paramSimpleTypeMarker), paramTypeConstructorMarker))
      {
        paramAbstractTypeCheckerContext = paramAbstractTypeCheckerContext.captureFromArguments(paramSimpleTypeMarker, CaptureStatus.FOR_SUBTYPING);
        if (paramAbstractTypeCheckerContext != null) {
          paramSimpleTypeMarker = paramAbstractTypeCheckerContext;
        }
        paramAbstractTypeCheckerContext = CollectionsKt.listOf(paramSimpleTypeMarker);
      }
      else
      {
        paramAbstractTypeCheckerContext = CollectionsKt.emptyList();
      }
      return paramAbstractTypeCheckerContext;
    }
    List localList = (List)new SmartList();
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
    while ((((Collection)localArrayDeque).isEmpty() ^ true)) {
      if (localSet.size() <= 1000)
      {
        Object localObject2 = (SimpleTypeMarker)localArrayDeque.pop();
        Intrinsics.checkExpressionValueIsNotNull(localObject2, "current");
        if (localSet.add(localObject2))
        {
          localObject1 = paramAbstractTypeCheckerContext.captureFromArguments((SimpleTypeMarker)localObject2, CaptureStatus.FOR_SUBTYPING);
          if (localObject1 == null) {
            localObject1 = localObject2;
          }
          if (paramAbstractTypeCheckerContext.areEqualTypeConstructors(paramAbstractTypeCheckerContext.typeConstructor((SimpleTypeMarker)localObject1), paramTypeConstructorMarker))
          {
            localList.add(localObject1);
            localObject1 = (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
          }
          else if (paramAbstractTypeCheckerContext.argumentsCount((KotlinTypeMarker)localObject1) == 0)
          {
            localObject1 = (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
          }
          else
          {
            localObject1 = paramAbstractTypeCheckerContext.substitutionSupertypePolicy((SimpleTypeMarker)localObject1);
          }
          if (!(Intrinsics.areEqual(localObject1, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE) ^ true)) {
            localObject1 = null;
          }
          if (localObject1 != null)
          {
            localObject2 = paramAbstractTypeCheckerContext.supertypes(paramAbstractTypeCheckerContext.typeConstructor((SimpleTypeMarker)localObject2)).iterator();
            while (((Iterator)localObject2).hasNext()) {
              localArrayDeque.add(((AbstractTypeCheckerContext.SupertypesPolicy)localObject1).transformType(paramAbstractTypeCheckerContext, (KotlinTypeMarker)((Iterator)localObject2).next()));
            }
          }
        }
      }
      else
      {
        paramAbstractTypeCheckerContext = new StringBuilder();
        paramAbstractTypeCheckerContext.append("Too many supertypes for type: ");
        paramAbstractTypeCheckerContext.append(paramSimpleTypeMarker);
        paramAbstractTypeCheckerContext.append(". Supertypes = ");
        paramAbstractTypeCheckerContext.append(CollectionsKt.joinToString$default((Iterable)localSet, null, null, null, 0, null, null, 63, null));
        throw ((Throwable)new IllegalStateException(paramAbstractTypeCheckerContext.toString().toString()));
      }
    }
    paramAbstractTypeCheckerContext.clear();
    return localList;
  }
  
  private final List<SimpleTypeMarker> collectAndFilter(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, SimpleTypeMarker paramSimpleTypeMarker, TypeConstructorMarker paramTypeConstructorMarker)
  {
    return selectOnlyPureKotlinSupertypes(paramAbstractTypeCheckerContext, collectAllSupertypesWithGivenTypeConstructor(paramAbstractTypeCheckerContext, paramSimpleTypeMarker, paramTypeConstructorMarker));
  }
  
  private final boolean completeIsSubTypeOf(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, KotlinTypeMarker paramKotlinTypeMarker1, KotlinTypeMarker paramKotlinTypeMarker2)
  {
    Boolean localBoolean = checkSubtypeForSpecialCases(paramAbstractTypeCheckerContext, paramAbstractTypeCheckerContext.lowerBoundIfFlexible(paramKotlinTypeMarker1), paramAbstractTypeCheckerContext.upperBoundIfFlexible(paramKotlinTypeMarker2));
    if (localBoolean != null)
    {
      boolean bool = localBoolean.booleanValue();
      paramAbstractTypeCheckerContext.addSubtypeConstraint(paramKotlinTypeMarker1, paramKotlinTypeMarker2);
      return bool;
    }
    localBoolean = paramAbstractTypeCheckerContext.addSubtypeConstraint(paramKotlinTypeMarker1, paramKotlinTypeMarker2);
    if (localBoolean != null) {
      return localBoolean.booleanValue();
    }
    return isSubtypeOfForSingleClassifierType(paramAbstractTypeCheckerContext, paramAbstractTypeCheckerContext.lowerBoundIfFlexible(paramKotlinTypeMarker1), paramAbstractTypeCheckerContext.upperBoundIfFlexible(paramKotlinTypeMarker2));
  }
  
  private final boolean hasNothingSupertype(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, SimpleTypeMarker paramSimpleTypeMarker)
  {
    Object localObject1 = paramAbstractTypeCheckerContext.typeConstructor(paramSimpleTypeMarker);
    if (paramAbstractTypeCheckerContext.isClassTypeConstructor((TypeConstructorMarker)localObject1)) {
      return paramAbstractTypeCheckerContext.isNothingConstructor((TypeConstructorMarker)localObject1);
    }
    boolean bool1 = paramAbstractTypeCheckerContext.isNothingConstructor(paramAbstractTypeCheckerContext.typeConstructor(paramSimpleTypeMarker));
    boolean bool2 = true;
    if (!bool1)
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
      while ((((Collection)localArrayDeque).isEmpty() ^ true)) {
        if (localSet.size() <= 1000)
        {
          Object localObject2 = (SimpleTypeMarker)localArrayDeque.pop();
          Intrinsics.checkExpressionValueIsNotNull(localObject2, "current");
          if (localSet.add(localObject2))
          {
            if (paramAbstractTypeCheckerContext.isClassType((SimpleTypeMarker)localObject2)) {
              localObject1 = (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
            } else {
              localObject1 = (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
            }
            if (!(Intrinsics.areEqual(localObject1, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE) ^ true)) {
              localObject1 = null;
            }
            if (localObject1 != null)
            {
              localObject2 = paramAbstractTypeCheckerContext.supertypes(paramAbstractTypeCheckerContext.typeConstructor((SimpleTypeMarker)localObject2)).iterator();
              while (((Iterator)localObject2).hasNext())
              {
                SimpleTypeMarker localSimpleTypeMarker = ((AbstractTypeCheckerContext.SupertypesPolicy)localObject1).transformType(paramAbstractTypeCheckerContext, (KotlinTypeMarker)((Iterator)localObject2).next());
                if (paramAbstractTypeCheckerContext.isNothingConstructor(paramAbstractTypeCheckerContext.typeConstructor(localSimpleTypeMarker)))
                {
                  paramAbstractTypeCheckerContext.clear();
                  break label339;
                }
                localArrayDeque.add(localSimpleTypeMarker);
              }
            }
          }
        }
        else
        {
          paramAbstractTypeCheckerContext = new StringBuilder();
          paramAbstractTypeCheckerContext.append("Too many supertypes for type: ");
          paramAbstractTypeCheckerContext.append(paramSimpleTypeMarker);
          paramAbstractTypeCheckerContext.append(". Supertypes = ");
          paramAbstractTypeCheckerContext.append(CollectionsKt.joinToString$default((Iterable)localSet, null, null, null, 0, null, null, 63, null));
          throw ((Throwable)new IllegalStateException(paramAbstractTypeCheckerContext.toString().toString()));
        }
      }
      paramAbstractTypeCheckerContext.clear();
      bool2 = false;
    }
    label339:
    return bool2;
  }
  
  private final boolean isCommonDenotableType(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, KotlinTypeMarker paramKotlinTypeMarker)
  {
    boolean bool;
    if ((paramAbstractTypeCheckerContext.isDenotable(paramAbstractTypeCheckerContext.typeConstructor(paramKotlinTypeMarker))) && (!paramAbstractTypeCheckerContext.isDynamic(paramKotlinTypeMarker)) && (!paramAbstractTypeCheckerContext.isDefinitelyNotNullType(paramKotlinTypeMarker)) && (Intrinsics.areEqual(paramAbstractTypeCheckerContext.typeConstructor(paramAbstractTypeCheckerContext.lowerBoundIfFlexible(paramKotlinTypeMarker)), paramAbstractTypeCheckerContext.typeConstructor(paramAbstractTypeCheckerContext.upperBoundIfFlexible(paramKotlinTypeMarker))))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final boolean isSubtypeOfForSingleClassifierType(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, SimpleTypeMarker paramSimpleTypeMarker1, SimpleTypeMarker paramSimpleTypeMarker2)
  {
    if (RUN_SLOW_ASSERTIONS)
    {
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
    if (!AbstractNullabilityChecker.INSTANCE.isPossibleSubtype(paramAbstractTypeCheckerContext, paramSimpleTypeMarker1, paramSimpleTypeMarker2)) {
      return false;
    }
    Object localObject1 = (KotlinTypeMarker)paramSimpleTypeMarker1;
    Object localObject2 = paramAbstractTypeCheckerContext.lowerBoundIfFlexible((KotlinTypeMarker)localObject1);
    Object localObject3 = (KotlinTypeMarker)paramSimpleTypeMarker2;
    localObject2 = checkSubtypeForIntegerLiteralType(paramAbstractTypeCheckerContext, (SimpleTypeMarker)localObject2, paramAbstractTypeCheckerContext.upperBoundIfFlexible((KotlinTypeMarker)localObject3));
    if (localObject2 != null)
    {
      boolean bool = ((Boolean)localObject2).booleanValue();
      paramAbstractTypeCheckerContext.addSubtypeConstraint((KotlinTypeMarker)localObject1, (KotlinTypeMarker)localObject3);
      return bool;
    }
    localObject1 = paramAbstractTypeCheckerContext.typeConstructor(paramSimpleTypeMarker2);
    if ((paramAbstractTypeCheckerContext.isEqualTypeConstructors(paramAbstractTypeCheckerContext.typeConstructor(paramSimpleTypeMarker1), (TypeConstructorMarker)localObject1)) && (paramAbstractTypeCheckerContext.parametersCount((TypeConstructorMarker)localObject1) == 0)) {
      return true;
    }
    if (paramAbstractTypeCheckerContext.isAnyConstructor(paramAbstractTypeCheckerContext.typeConstructor(paramSimpleTypeMarker2))) {
      return true;
    }
    localObject3 = findCorrespondingSupertypes(paramAbstractTypeCheckerContext, paramSimpleTypeMarker1, (TypeConstructorMarker)localObject1);
    int i = ((List)localObject3).size();
    if (i != 0)
    {
      if (i != 1)
      {
        localObject2 = paramAbstractTypeCheckerContext.getSameConstructorPolicy();
        i = AbstractTypeChecker.WhenMappings.$EnumSwitchMapping$0[localObject2.ordinal()];
        if (i != 1)
        {
          if (i != 2)
          {
            Object localObject4;
            if ((i == 3) || (i == 4))
            {
              localObject2 = (Iterable)localObject3;
              if (((localObject2 instanceof Collection)) && (((Collection)localObject2).isEmpty())) {}
              do
              {
                while (!((Iterator)localObject2).hasNext())
                {
                  i = 0;
                  break;
                  localObject2 = ((Iterable)localObject2).iterator();
                }
                localObject4 = (SimpleTypeMarker)((Iterator)localObject2).next();
              } while (!INSTANCE.isSubtypeForSameConstructor(paramAbstractTypeCheckerContext, paramAbstractTypeCheckerContext.asArgumentList((SimpleTypeMarker)localObject4), paramSimpleTypeMarker2));
              i = 1;
              if (i != 0) {
                return true;
              }
            }
            if (paramAbstractTypeCheckerContext.getSameConstructorPolicy() != AbstractTypeCheckerContext.SeveralSupertypesWithSameConstructorPolicy.INTERSECT_ARGUMENTS_AND_CHECK_AGAIN) {
              return false;
            }
            localObject2 = new ArgumentList(paramAbstractTypeCheckerContext.parametersCount((TypeConstructorMarker)localObject1));
            int j = paramAbstractTypeCheckerContext.parametersCount((TypeConstructorMarker)localObject1);
            for (i = 0; i < j; i++)
            {
              localObject1 = (Iterable)localObject3;
              localObject4 = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
              Iterator localIterator = ((Iterable)localObject1).iterator();
              while (localIterator.hasNext())
              {
                SimpleTypeMarker localSimpleTypeMarker = (SimpleTypeMarker)localIterator.next();
                localObject1 = paramAbstractTypeCheckerContext.getArgumentOrNull(localSimpleTypeMarker, i);
                if (localObject1 != null)
                {
                  int k;
                  if (paramAbstractTypeCheckerContext.getVariance((TypeArgumentMarker)localObject1) == TypeVariance.INV) {
                    k = 1;
                  } else {
                    k = 0;
                  }
                  if (k == 0) {
                    localObject1 = null;
                  }
                  if (localObject1 != null)
                  {
                    localObject1 = paramAbstractTypeCheckerContext.getType((TypeArgumentMarker)localObject1);
                    if (localObject1 != null)
                    {
                      ((Collection)localObject4).add(localObject1);
                      continue;
                    }
                  }
                }
                paramAbstractTypeCheckerContext = new StringBuilder();
                paramAbstractTypeCheckerContext.append("Incorrect type: ");
                paramAbstractTypeCheckerContext.append(localSimpleTypeMarker);
                paramAbstractTypeCheckerContext.append(", subType: ");
                paramAbstractTypeCheckerContext.append(paramSimpleTypeMarker1);
                paramAbstractTypeCheckerContext.append(", superType: ");
                paramAbstractTypeCheckerContext.append(paramSimpleTypeMarker2);
                throw ((Throwable)new IllegalStateException(paramAbstractTypeCheckerContext.toString().toString()));
              }
              ((ArgumentList)localObject2).add(paramAbstractTypeCheckerContext.asTypeArgument(paramAbstractTypeCheckerContext.intersectTypes((List)localObject4)));
            }
            return isSubtypeForSameConstructor(paramAbstractTypeCheckerContext, (TypeArgumentListMarker)localObject2, paramSimpleTypeMarker2);
          }
          return isSubtypeForSameConstructor(paramAbstractTypeCheckerContext, paramAbstractTypeCheckerContext.asArgumentList((SimpleTypeMarker)CollectionsKt.first((List)localObject3)), paramSimpleTypeMarker2);
        }
        return false;
      }
      return isSubtypeForSameConstructor(paramAbstractTypeCheckerContext, paramAbstractTypeCheckerContext.asArgumentList((SimpleTypeMarker)CollectionsKt.first((List)localObject3)), paramSimpleTypeMarker2);
    }
    return hasNothingSupertype(paramAbstractTypeCheckerContext, paramSimpleTypeMarker1);
  }
  
  private final List<SimpleTypeMarker> selectOnlyPureKotlinSupertypes(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, List<? extends SimpleTypeMarker> paramList)
  {
    if (paramList.size() < 2) {
      return paramList;
    }
    Object localObject1 = (Iterable)paramList;
    Collection localCollection = (Collection)new ArrayList();
    Iterator localIterator = ((Iterable)localObject1).iterator();
    for (;;)
    {
      boolean bool = localIterator.hasNext();
      int i = 1;
      if (!bool) {
        break;
      }
      Object localObject2 = localIterator.next();
      TypeArgumentListMarker localTypeArgumentListMarker = paramAbstractTypeCheckerContext.asArgumentList((SimpleTypeMarker)localObject2);
      localObject1 = (TypeSystemContext)paramAbstractTypeCheckerContext;
      int j = ((TypeSystemContext)localObject1).size(localTypeArgumentListMarker);
      int m;
      for (int k = 0;; k++)
      {
        m = i;
        if (k >= j) {
          break;
        }
        if (paramAbstractTypeCheckerContext.asFlexibleType(paramAbstractTypeCheckerContext.getType(((TypeSystemContext)localObject1).get(localTypeArgumentListMarker, k))) == null) {
          m = 1;
        } else {
          m = 0;
        }
        if (m == 0)
        {
          m = 0;
          break;
        }
      }
      if (m != 0) {
        localCollection.add(localObject2);
      }
    }
    paramAbstractTypeCheckerContext = (List)localCollection;
    if ((((Collection)paramAbstractTypeCheckerContext).isEmpty() ^ true)) {
      paramList = paramAbstractTypeCheckerContext;
    }
    return paramList;
  }
  
  public final TypeVariance effectiveVariance(TypeVariance paramTypeVariance1, TypeVariance paramTypeVariance2)
  {
    Intrinsics.checkParameterIsNotNull(paramTypeVariance1, "declared");
    Intrinsics.checkParameterIsNotNull(paramTypeVariance2, "useSite");
    if (paramTypeVariance1 == TypeVariance.INV) {
      return paramTypeVariance2;
    }
    if (paramTypeVariance2 == TypeVariance.INV) {
      return paramTypeVariance1;
    }
    if (paramTypeVariance1 == paramTypeVariance2) {
      return paramTypeVariance1;
    }
    return null;
  }
  
  public final boolean equalTypes(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, KotlinTypeMarker paramKotlinTypeMarker1, KotlinTypeMarker paramKotlinTypeMarker2)
  {
    Intrinsics.checkParameterIsNotNull(paramAbstractTypeCheckerContext, "context");
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker1, "a");
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker2, "b");
    boolean bool1 = true;
    boolean bool2 = true;
    if (paramKotlinTypeMarker1 == paramKotlinTypeMarker2) {
      return true;
    }
    if ((INSTANCE.isCommonDenotableType(paramAbstractTypeCheckerContext, paramKotlinTypeMarker1)) && (INSTANCE.isCommonDenotableType(paramAbstractTypeCheckerContext, paramKotlinTypeMarker2)))
    {
      KotlinTypeMarker localKotlinTypeMarker1 = paramAbstractTypeCheckerContext.refineType(paramKotlinTypeMarker1);
      KotlinTypeMarker localKotlinTypeMarker2 = paramAbstractTypeCheckerContext.refineType(paramKotlinTypeMarker2);
      SimpleTypeMarker localSimpleTypeMarker = paramAbstractTypeCheckerContext.lowerBoundIfFlexible(localKotlinTypeMarker1);
      if (!paramAbstractTypeCheckerContext.areEqualTypeConstructors(paramAbstractTypeCheckerContext.typeConstructor(localKotlinTypeMarker1), paramAbstractTypeCheckerContext.typeConstructor(localKotlinTypeMarker2))) {
        return false;
      }
      if (paramAbstractTypeCheckerContext.argumentsCount((KotlinTypeMarker)localSimpleTypeMarker) == 0)
      {
        bool1 = bool2;
        if (!paramAbstractTypeCheckerContext.hasFlexibleNullability(localKotlinTypeMarker1)) {
          if (paramAbstractTypeCheckerContext.hasFlexibleNullability(localKotlinTypeMarker2)) {
            bool1 = bool2;
          } else if (paramAbstractTypeCheckerContext.isMarkedNullable(localSimpleTypeMarker) == paramAbstractTypeCheckerContext.isMarkedNullable(paramAbstractTypeCheckerContext.lowerBoundIfFlexible(localKotlinTypeMarker2))) {
            bool1 = bool2;
          } else {
            bool1 = false;
          }
        }
        return bool1;
      }
    }
    if ((!INSTANCE.isSubtypeOf(paramAbstractTypeCheckerContext, paramKotlinTypeMarker1, paramKotlinTypeMarker2)) || (!INSTANCE.isSubtypeOf(paramAbstractTypeCheckerContext, paramKotlinTypeMarker2, paramKotlinTypeMarker1))) {
      bool1 = false;
    }
    return bool1;
  }
  
  public final List<SimpleTypeMarker> findCorrespondingSupertypes(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, SimpleTypeMarker paramSimpleTypeMarker, TypeConstructorMarker paramTypeConstructorMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramAbstractTypeCheckerContext, "$this$findCorrespondingSupertypes");
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "subType");
    Intrinsics.checkParameterIsNotNull(paramTypeConstructorMarker, "superConstructor");
    if (paramAbstractTypeCheckerContext.isClassType(paramSimpleTypeMarker)) {
      return collectAndFilter(paramAbstractTypeCheckerContext, paramSimpleTypeMarker, paramTypeConstructorMarker);
    }
    if ((!paramAbstractTypeCheckerContext.isClassTypeConstructor(paramTypeConstructorMarker)) && (!paramAbstractTypeCheckerContext.isIntegerLiteralTypeConstructor(paramTypeConstructorMarker))) {
      return collectAllSupertypesWithGivenTypeConstructor(paramAbstractTypeCheckerContext, paramSimpleTypeMarker, paramTypeConstructorMarker);
    }
    SmartList localSmartList = new SmartList();
    paramAbstractTypeCheckerContext.initialize();
    Object localObject1 = paramAbstractTypeCheckerContext.getSupertypesDeque();
    if (localObject1 == null) {
      Intrinsics.throwNpe();
    }
    Object localObject2 = paramAbstractTypeCheckerContext.getSupertypesSet();
    if (localObject2 == null) {
      Intrinsics.throwNpe();
    }
    ((ArrayDeque)localObject1).push(paramSimpleTypeMarker);
    while ((((Collection)localObject1).isEmpty() ^ true)) {
      if (((Set)localObject2).size() <= 1000)
      {
        Object localObject3 = (SimpleTypeMarker)((ArrayDeque)localObject1).pop();
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "current");
        if (((Set)localObject2).add(localObject3))
        {
          if (paramAbstractTypeCheckerContext.isClassType((SimpleTypeMarker)localObject3))
          {
            localSmartList.add(localObject3);
            localObject4 = (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE;
          }
          else
          {
            localObject4 = (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
          }
          if (!(Intrinsics.areEqual(localObject4, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE) ^ true)) {
            localObject4 = null;
          }
          if (localObject4 != null)
          {
            localObject3 = paramAbstractTypeCheckerContext.supertypes(paramAbstractTypeCheckerContext.typeConstructor((SimpleTypeMarker)localObject3)).iterator();
            while (((Iterator)localObject3).hasNext()) {
              ((ArrayDeque)localObject1).add(((AbstractTypeCheckerContext.SupertypesPolicy)localObject4).transformType(paramAbstractTypeCheckerContext, (KotlinTypeMarker)((Iterator)localObject3).next()));
            }
          }
        }
      }
      else
      {
        paramAbstractTypeCheckerContext = new StringBuilder();
        paramAbstractTypeCheckerContext.append("Too many supertypes for type: ");
        paramAbstractTypeCheckerContext.append(paramSimpleTypeMarker);
        paramAbstractTypeCheckerContext.append(". Supertypes = ");
        paramAbstractTypeCheckerContext.append(CollectionsKt.joinToString$default((Iterable)localObject2, null, null, null, 0, null, null, 63, null));
        throw ((Throwable)new IllegalStateException(paramAbstractTypeCheckerContext.toString().toString()));
      }
    }
    paramAbstractTypeCheckerContext.clear();
    Object localObject4 = (Iterable)localSmartList;
    paramSimpleTypeMarker = (Collection)new ArrayList();
    localObject1 = ((Iterable)localObject4).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject4 = (SimpleTypeMarker)((Iterator)localObject1).next();
      localObject2 = INSTANCE;
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "it");
      CollectionsKt.addAll(paramSimpleTypeMarker, (Iterable)((AbstractTypeChecker)localObject2).collectAndFilter(paramAbstractTypeCheckerContext, (SimpleTypeMarker)localObject4, paramTypeConstructorMarker));
    }
    return (List)paramSimpleTypeMarker;
  }
  
  public final boolean isSubtypeForSameConstructor(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, TypeArgumentListMarker paramTypeArgumentListMarker, SimpleTypeMarker paramSimpleTypeMarker)
  {
    Intrinsics.checkParameterIsNotNull(paramAbstractTypeCheckerContext, "$this$isSubtypeForSameConstructor");
    Intrinsics.checkParameterIsNotNull(paramTypeArgumentListMarker, "capturedSubArguments");
    Intrinsics.checkParameterIsNotNull(paramSimpleTypeMarker, "superType");
    TypeConstructorMarker localTypeConstructorMarker = paramAbstractTypeCheckerContext.typeConstructor(paramSimpleTypeMarker);
    int i = paramAbstractTypeCheckerContext.parametersCount(localTypeConstructorMarker);
    int j = 0;
    while (j < i)
    {
      Object localObject1 = paramAbstractTypeCheckerContext.getArgument((KotlinTypeMarker)paramSimpleTypeMarker, j);
      Object localObject2;
      if (!paramAbstractTypeCheckerContext.isStarProjection((TypeArgumentMarker)localObject1))
      {
        KotlinTypeMarker localKotlinTypeMarker = paramAbstractTypeCheckerContext.getType((TypeArgumentMarker)localObject1);
        localObject2 = paramAbstractTypeCheckerContext.get(paramTypeArgumentListMarker, j);
        if (paramAbstractTypeCheckerContext.getVariance((TypeArgumentMarker)localObject2) == TypeVariance.INV) {
          k = 1;
        } else {
          k = 0;
        }
        if ((_Assertions.ENABLED) && (k == 0))
        {
          paramAbstractTypeCheckerContext = new StringBuilder();
          paramAbstractTypeCheckerContext.append("Incorrect sub argument: ");
          paramAbstractTypeCheckerContext.append(localObject2);
          throw ((Throwable)new AssertionError(paramAbstractTypeCheckerContext.toString()));
        }
        localObject2 = paramAbstractTypeCheckerContext.getType((TypeArgumentMarker)localObject2);
        localObject1 = effectiveVariance(paramAbstractTypeCheckerContext.getVariance(paramAbstractTypeCheckerContext.getParameter(localTypeConstructorMarker, j)), paramAbstractTypeCheckerContext.getVariance((TypeArgumentMarker)localObject1));
        if (localObject1 == null) {
          break label362;
        }
        if (AbstractTypeCheckerContext.access$getArgumentsDepth$p(paramAbstractTypeCheckerContext) > 100) {
          break label321;
        }
        AbstractTypeCheckerContext.access$setArgumentsDepth$p(paramAbstractTypeCheckerContext, AbstractTypeCheckerContext.access$getArgumentsDepth$p(paramAbstractTypeCheckerContext) + 1);
        int k = AbstractTypeChecker.WhenMappings.$EnumSwitchMapping$1[localObject1.ordinal()];
        boolean bool;
        if (k != 1)
        {
          if (k != 2)
          {
            if (k == 3) {
              bool = INSTANCE.isSubtypeOf(paramAbstractTypeCheckerContext, localKotlinTypeMarker, (KotlinTypeMarker)localObject2);
            } else {
              throw new NoWhenBranchMatchedException();
            }
          }
          else {
            bool = INSTANCE.isSubtypeOf(paramAbstractTypeCheckerContext, (KotlinTypeMarker)localObject2, localKotlinTypeMarker);
          }
        }
        else {
          bool = INSTANCE.equalTypes(paramAbstractTypeCheckerContext, (KotlinTypeMarker)localObject2, localKotlinTypeMarker);
        }
        AbstractTypeCheckerContext.access$setArgumentsDepth$p(paramAbstractTypeCheckerContext, AbstractTypeCheckerContext.access$getArgumentsDepth$p(paramAbstractTypeCheckerContext) - 1);
        if (!bool) {
          return false;
        }
      }
      j++;
      continue;
      label321:
      paramAbstractTypeCheckerContext = new StringBuilder();
      paramAbstractTypeCheckerContext.append("Arguments depth is too high. Some related argument: ");
      paramAbstractTypeCheckerContext.append(localObject2);
      throw ((Throwable)new IllegalStateException(paramAbstractTypeCheckerContext.toString().toString()));
      label362:
      return paramAbstractTypeCheckerContext.isErrorTypeEqualsToAnything();
    }
    return true;
  }
  
  public final boolean isSubtypeOf(AbstractTypeCheckerContext paramAbstractTypeCheckerContext, KotlinTypeMarker paramKotlinTypeMarker1, KotlinTypeMarker paramKotlinTypeMarker2)
  {
    Intrinsics.checkParameterIsNotNull(paramAbstractTypeCheckerContext, "context");
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker1, "subType");
    Intrinsics.checkParameterIsNotNull(paramKotlinTypeMarker2, "superType");
    if (paramKotlinTypeMarker1 == paramKotlinTypeMarker2) {
      return true;
    }
    return INSTANCE.completeIsSubTypeOf(paramAbstractTypeCheckerContext, paramAbstractTypeCheckerContext.prepareType(paramAbstractTypeCheckerContext.refineType(paramKotlinTypeMarker1)), paramAbstractTypeCheckerContext.prepareType(paramAbstractTypeCheckerContext.refineType(paramKotlinTypeMarker2)));
  }
}
