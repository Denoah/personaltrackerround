package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet.Companion;

public final class OverridingUtilsKt
{
  public static final <D extends CallableDescriptor> void retainMostSpecificInEachOverridableGroup(Collection<D> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$retainMostSpecificInEachOverridableGroup");
    Collection localCollection = selectMostSpecificInEachOverridableGroup(paramCollection, (Function1)retainMostSpecificInEachOverridableGroup.newResult.1.INSTANCE);
    if (paramCollection.size() == localCollection.size()) {
      return;
    }
    paramCollection.retainAll(localCollection);
  }
  
  public static final <H> Collection<H> selectMostSpecificInEachOverridableGroup(Collection<? extends H> paramCollection, Function1<? super H, ? extends CallableDescriptor> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "$this$selectMostSpecificInEachOverridableGroup");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "descriptorByHandle");
    if (paramCollection.size() <= 1) {
      return paramCollection;
    }
    paramCollection = new LinkedList(paramCollection);
    SmartSet localSmartSet = SmartSet.Companion.create();
    for (;;)
    {
      Object localObject1 = (Collection)paramCollection;
      if (!(((Collection)localObject1).isEmpty() ^ true)) {
        break;
      }
      Object localObject2 = CollectionsKt.first((List)paramCollection);
      Object localObject3 = SmartSet.Companion.create();
      Object localObject4 = OverridingUtil.extractMembersOverridableInBothWays(localObject2, (Collection)localObject1, paramFunction1, (Function1)new Lambda((SmartSet)localObject3)
      {
        public final void invoke(H paramAnonymousH)
        {
          SmartSet localSmartSet = this.$conflictedHandles;
          Intrinsics.checkExpressionValueIsNotNull(paramAnonymousH, "it");
          localSmartSet.add(paramAnonymousH);
        }
      });
      Intrinsics.checkExpressionValueIsNotNull(localObject4, "OverridingUtil.extractMe…nflictedHandles.add(it) }");
      if ((((Collection)localObject4).size() == 1) && (((SmartSet)localObject3).isEmpty()))
      {
        localObject3 = CollectionsKt.single((Iterable)localObject4);
        Intrinsics.checkExpressionValueIsNotNull(localObject3, "overridableGroup.single()");
        localSmartSet.add(localObject3);
      }
      else
      {
        localObject1 = OverridingUtil.selectMostSpecificMember((Collection)localObject4, paramFunction1);
        Intrinsics.checkExpressionValueIsNotNull(localObject1, "OverridingUtil.selectMos…roup, descriptorByHandle)");
        localObject2 = (CallableDescriptor)paramFunction1.invoke(localObject1);
        Iterator localIterator = ((Iterable)localObject4).iterator();
        while (localIterator.hasNext())
        {
          localObject4 = localIterator.next();
          Intrinsics.checkExpressionValueIsNotNull(localObject4, "it");
          if (!OverridingUtil.isMoreSpecific((CallableDescriptor)localObject2, (CallableDescriptor)paramFunction1.invoke(localObject4))) {
            ((Collection)localObject3).add(localObject4);
          }
        }
        localObject3 = (Collection)localObject3;
        if ((((Collection)localObject3).isEmpty() ^ true)) {
          localSmartSet.addAll((Collection)localObject3);
        }
        localSmartSet.add(localObject1);
      }
    }
    return (Collection)localSmartSet;
  }
}
