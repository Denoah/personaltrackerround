package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor.Companion;
import kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.SpecialTypesKt;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;

public final class TypeIntersector
{
  public static final TypeIntersector INSTANCE = new TypeIntersector();
  
  private TypeIntersector() {}
  
  private final Collection<SimpleType> filterTypes(Collection<? extends SimpleType> paramCollection, Function2<? super SimpleType, ? super SimpleType, Boolean> paramFunction2)
  {
    paramCollection = new ArrayList(paramCollection);
    Iterator localIterator1 = paramCollection.iterator();
    Intrinsics.checkExpressionValueIsNotNull(localIterator1, "filteredTypes.iterator()");
    while (localIterator1.hasNext())
    {
      SimpleType localSimpleType = (SimpleType)localIterator1.next();
      Object localObject = (Iterable)paramCollection;
      boolean bool = localObject instanceof Collection;
      int i = 1;
      if ((bool) && (((Collection)localObject).isEmpty())) {}
      do
      {
        Iterator localIterator2;
        while (!localIterator2.hasNext())
        {
          j = 0;
          break;
          localIterator2 = ((Iterable)localObject).iterator();
        }
        localObject = (SimpleType)localIterator2.next();
        if (localObject != localSimpleType)
        {
          Intrinsics.checkExpressionValueIsNotNull(localObject, "lower");
          Intrinsics.checkExpressionValueIsNotNull(localSimpleType, "upper");
          if (((Boolean)paramFunction2.invoke(localObject, localSimpleType)).booleanValue())
          {
            j = 1;
            continue;
          }
        }
        j = 0;
      } while (j == 0);
      int j = i;
      if (j != 0) {
        localIterator1.remove();
      }
    }
    return (Collection)paramCollection;
  }
  
  private final SimpleType intersectTypesWithoutIntersectionType(Set<? extends SimpleType> paramSet)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokeinterface 109 1 0
    //   6: iconst_1
    //   7: if_icmpne +14 -> 21
    //   10: aload_1
    //   11: checkcast 75	java/lang/Iterable
    //   14: invokestatic 115	kotlin/collections/CollectionsKt:single	(Ljava/lang/Iterable;)Ljava/lang/Object;
    //   17: checkcast 73	kotlin/reflect/jvm/internal/impl/types/SimpleType
    //   20: areturn
    //   21: new 21	kotlin/reflect/jvm/internal/impl/types/checker/TypeIntersector$intersectTypesWithoutIntersectionType$errorMessage$1
    //   24: dup
    //   25: aload_1
    //   26: invokespecial 118	kotlin/reflect/jvm/internal/impl/types/checker/TypeIntersector$intersectTypesWithoutIntersectionType$errorMessage$1:<init>	(Ljava/util/Set;)V
    //   29: checkcast 120	kotlin/jvm/functions/Function0
    //   32: astore_2
    //   33: aload_1
    //   34: checkcast 77	java/util/Collection
    //   37: astore_1
    //   38: aload_0
    //   39: aload_1
    //   40: new 23	kotlin/reflect/jvm/internal/impl/types/checker/TypeIntersector$intersectTypesWithoutIntersectionType$filteredEqualTypes$1
    //   43: dup
    //   44: aload_0
    //   45: checkcast 2	kotlin/reflect/jvm/internal/impl/types/checker/TypeIntersector
    //   48: invokespecial 123	kotlin/reflect/jvm/internal/impl/types/checker/TypeIntersector$intersectTypesWithoutIntersectionType$filteredEqualTypes$1:<init>	(Lkotlin/reflect/jvm/internal/impl/types/checker/TypeIntersector;)V
    //   51: checkcast 87	kotlin/jvm/functions/Function2
    //   54: invokespecial 125	kotlin/reflect/jvm/internal/impl/types/checker/TypeIntersector:filterTypes	(Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;
    //   57: astore_3
    //   58: aload_3
    //   59: invokeinterface 80 1 0
    //   64: istore 4
    //   66: getstatic 131	kotlin/_Assertions:ENABLED	Z
    //   69: ifeq +30 -> 99
    //   72: iload 4
    //   74: iconst_1
    //   75: ixor
    //   76: ifeq +6 -> 82
    //   79: goto +20 -> 99
    //   82: new 133	java/lang/AssertionError
    //   85: dup
    //   86: aload_2
    //   87: invokeinterface 135 1 0
    //   92: invokespecial 138	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   95: checkcast 140	java/lang/Throwable
    //   98: athrow
    //   99: getstatic 146	kotlin/reflect/jvm/internal/impl/resolve/constants/IntegerLiteralTypeConstructor:Companion	Lkotlin/reflect/jvm/internal/impl/resolve/constants/IntegerLiteralTypeConstructor$Companion;
    //   102: aload_3
    //   103: invokevirtual 152	kotlin/reflect/jvm/internal/impl/resolve/constants/IntegerLiteralTypeConstructor$Companion:findIntersectionType	(Ljava/util/Collection;)Lkotlin/reflect/jvm/internal/impl/types/SimpleType;
    //   106: astore 5
    //   108: aload 5
    //   110: ifnull +6 -> 116
    //   113: aload 5
    //   115: areturn
    //   116: aload_0
    //   117: aload_3
    //   118: new 25	kotlin/reflect/jvm/internal/impl/types/checker/TypeIntersector$intersectTypesWithoutIntersectionType$filteredSuperAndEqualTypes$1
    //   121: dup
    //   122: getstatic 157	kotlin/reflect/jvm/internal/impl/types/checker/NewKotlinTypeChecker:Companion	Lkotlin/reflect/jvm/internal/impl/types/checker/NewKotlinTypeChecker$Companion;
    //   125: invokevirtual 163	kotlin/reflect/jvm/internal/impl/types/checker/NewKotlinTypeChecker$Companion:getDefault	()Lkotlin/reflect/jvm/internal/impl/types/checker/NewKotlinTypeCheckerImpl;
    //   128: invokespecial 166	kotlin/reflect/jvm/internal/impl/types/checker/TypeIntersector$intersectTypesWithoutIntersectionType$filteredSuperAndEqualTypes$1:<init>	(Lkotlin/reflect/jvm/internal/impl/types/checker/NewKotlinTypeCheckerImpl;)V
    //   131: checkcast 87	kotlin/jvm/functions/Function2
    //   134: invokespecial 125	kotlin/reflect/jvm/internal/impl/types/checker/TypeIntersector:filterTypes	(Ljava/util/Collection;Lkotlin/jvm/functions/Function2;)Ljava/util/Collection;
    //   137: astore_3
    //   138: aload_3
    //   139: invokeinterface 80 1 0
    //   144: istore 4
    //   146: getstatic 131	kotlin/_Assertions:ENABLED	Z
    //   149: ifeq +30 -> 179
    //   152: iconst_1
    //   153: iload 4
    //   155: ixor
    //   156: ifeq +6 -> 162
    //   159: goto +20 -> 179
    //   162: new 133	java/lang/AssertionError
    //   165: dup
    //   166: aload_2
    //   167: invokeinterface 135 1 0
    //   172: invokespecial 138	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   175: checkcast 140	java/lang/Throwable
    //   178: athrow
    //   179: aload_3
    //   180: invokeinterface 167 1 0
    //   185: iconst_2
    //   186: if_icmpge +14 -> 200
    //   189: aload_3
    //   190: checkcast 75	java/lang/Iterable
    //   193: invokestatic 115	kotlin/collections/CollectionsKt:single	(Ljava/lang/Iterable;)Ljava/lang/Object;
    //   196: checkcast 73	kotlin/reflect/jvm/internal/impl/types/SimpleType
    //   199: areturn
    //   200: new 169	kotlin/reflect/jvm/internal/impl/types/IntersectionTypeConstructor
    //   203: dup
    //   204: aload_1
    //   205: invokespecial 170	kotlin/reflect/jvm/internal/impl/types/IntersectionTypeConstructor:<init>	(Ljava/util/Collection;)V
    //   208: invokevirtual 174	kotlin/reflect/jvm/internal/impl/types/IntersectionTypeConstructor:createType	()Lkotlin/reflect/jvm/internal/impl/types/SimpleType;
    //   211: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	212	0	this	TypeIntersector
    //   0	212	1	paramSet	Set<? extends SimpleType>
    //   32	135	2	localFunction0	Function0
    //   57	133	3	localCollection	Collection
    //   64	92	4	bool	boolean
    //   106	8	5	localSimpleType	SimpleType
  }
  
  private final boolean isStrictSupertype(KotlinType paramKotlinType1, KotlinType paramKotlinType2)
  {
    NewKotlinTypeCheckerImpl localNewKotlinTypeCheckerImpl = NewKotlinTypeChecker.Companion.getDefault();
    boolean bool;
    if ((localNewKotlinTypeCheckerImpl.isSubtypeOf(paramKotlinType1, paramKotlinType2)) && (!localNewKotlinTypeCheckerImpl.isSubtypeOf(paramKotlinType2, paramKotlinType1))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final SimpleType intersectTypes$descriptors(List<? extends SimpleType> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "types");
    int i;
    if (paramList.size() > 1) {
      i = 1;
    } else {
      i = 0;
    }
    if ((_Assertions.ENABLED) && (i == 0))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Size should be at least 2, but it is ");
      ((StringBuilder)localObject1).append(paramList.size());
      throw ((Throwable)new AssertionError(((StringBuilder)localObject1).toString()));
    }
    Object localObject2 = new ArrayList();
    Iterator localIterator1 = paramList.iterator();
    while (localIterator1.hasNext())
    {
      localObject3 = (SimpleType)localIterator1.next();
      if ((((SimpleType)localObject3).getConstructor() instanceof IntersectionTypeConstructor))
      {
        paramList = ((SimpleType)localObject3).getConstructor().getSupertypes();
        Intrinsics.checkExpressionValueIsNotNull(paramList, "type.constructor.supertypes");
        paramList = (Iterable)paramList;
        Collection localCollection = (Collection)new ArrayList(CollectionsKt.collectionSizeOrDefault(paramList, 10));
        Iterator localIterator2 = paramList.iterator();
        while (localIterator2.hasNext())
        {
          paramList = (KotlinType)localIterator2.next();
          Intrinsics.checkExpressionValueIsNotNull(paramList, "it");
          localObject1 = FlexibleTypesKt.upperIfFlexible(paramList);
          paramList = (List<? extends SimpleType>)localObject1;
          if (((SimpleType)localObject3).isMarkedNullable()) {
            paramList = ((SimpleType)localObject1).makeNullableAsSpecified(true);
          }
          localCollection.add(paramList);
        }
        ((ArrayList)localObject2).addAll((Collection)localCollection);
      }
      else
      {
        ((ArrayList)localObject2).add(localObject3);
      }
    }
    Object localObject1 = (Iterable)localObject2;
    paramList = ResultNullability.START;
    localObject2 = ((Iterable)localObject1).iterator();
    while (((Iterator)localObject2).hasNext()) {
      paramList = paramList.combine((UnwrappedType)((Iterator)localObject2).next());
    }
    Object localObject3 = (Collection)new LinkedHashSet();
    localIterator1 = ((Iterable)localObject1).iterator();
    while (localIterator1.hasNext())
    {
      localObject2 = (SimpleType)localIterator1.next();
      localObject1 = localObject2;
      if (paramList == ResultNullability.NOT_NULL) {
        localObject1 = SpecialTypesKt.makeSimpleTypeDefinitelyNotNullOrNotNull((SimpleType)localObject2);
      }
      ((Collection)localObject3).add(localObject1);
    }
    return intersectTypesWithoutIntersectionType((Set)localObject3);
  }
  
  private static abstract enum ResultNullability
  {
    static
    {
      START localSTART = new START("START", 0);
      START = localSTART;
      ACCEPT_NULL localACCEPT_NULL = new ACCEPT_NULL("ACCEPT_NULL", 1);
      ACCEPT_NULL = localACCEPT_NULL;
      UNKNOWN localUNKNOWN = new UNKNOWN("UNKNOWN", 2);
      UNKNOWN = localUNKNOWN;
      NOT_NULL localNOT_NULL = new NOT_NULL("NOT_NULL", 3);
      NOT_NULL = localNOT_NULL;
      $VALUES = new ResultNullability[] { localSTART, localACCEPT_NULL, localUNKNOWN, localNOT_NULL };
    }
    
    private ResultNullability() {}
    
    public abstract ResultNullability combine(UnwrappedType paramUnwrappedType);
    
    protected final ResultNullability getResultNullability(UnwrappedType paramUnwrappedType)
    {
      Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "$this$resultNullability");
      if (paramUnwrappedType.isMarkedNullable()) {
        paramUnwrappedType = ACCEPT_NULL;
      } else if (NullabilityChecker.INSTANCE.isSubtypeOfAny(paramUnwrappedType)) {
        paramUnwrappedType = NOT_NULL;
      } else {
        paramUnwrappedType = UNKNOWN;
      }
      return paramUnwrappedType;
    }
    
    static final class ACCEPT_NULL
      extends TypeIntersector.ResultNullability
    {
      ACCEPT_NULL()
      {
        super(i, null);
      }
      
      public TypeIntersector.ResultNullability combine(UnwrappedType paramUnwrappedType)
      {
        Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "nextType");
        return getResultNullability(paramUnwrappedType);
      }
    }
    
    static final class NOT_NULL
      extends TypeIntersector.ResultNullability
    {
      NOT_NULL()
      {
        super(i, null);
      }
      
      public NOT_NULL combine(UnwrappedType paramUnwrappedType)
      {
        Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "nextType");
        return this;
      }
    }
    
    static final class START
      extends TypeIntersector.ResultNullability
    {
      START()
      {
        super(i, null);
      }
      
      public TypeIntersector.ResultNullability combine(UnwrappedType paramUnwrappedType)
      {
        Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "nextType");
        return getResultNullability(paramUnwrappedType);
      }
    }
    
    static final class UNKNOWN
      extends TypeIntersector.ResultNullability
    {
      UNKNOWN()
      {
        super(i, null);
      }
      
      public TypeIntersector.ResultNullability combine(UnwrappedType paramUnwrappedType)
      {
        Intrinsics.checkParameterIsNotNull(paramUnwrappedType, "nextType");
        TypeIntersector.ResultNullability localResultNullability = getResultNullability(paramUnwrappedType);
        paramUnwrappedType = localResultNullability;
        if (localResultNullability == TypeIntersector.ResultNullability.ACCEPT_NULL) {
          paramUnwrappedType = (TypeIntersector.ResultNullability)this;
        }
        return paramUnwrappedType;
      }
    }
  }
}
