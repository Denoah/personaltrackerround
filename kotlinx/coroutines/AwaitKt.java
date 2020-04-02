package kotlinx.coroutines;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\000\n\002\020 \n\002\b\002\n\002\020\021\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\036\n\002\b\002\032=\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\0022\036\020\003\032\020\022\f\b\001\022\b\022\004\022\002H\0020\0050\004\"\b\022\004\022\002H\0020\005H?@?\001\000?\006\002\020\006\032%\020\007\032\0020\b2\022\020\t\032\n\022\006\b\001\022\0020\n0\004\"\0020\nH?@?\001\000?\006\002\020\013\032-\020\000\032\b\022\004\022\002H\0020\001\"\004\b\000\020\002*\016\022\n\022\b\022\004\022\002H\0020\0050\fH?@?\001\000?\006\002\020\r\032\033\020\007\032\0020\b*\b\022\004\022\0020\n0\fH?@?\001\000?\006\002\020\r?\002\004\n\002\b\031?\006\016"}, d2={"awaitAll", "", "T", "deferreds", "", "Lkotlinx/coroutines/Deferred;", "([Lkotlinx/coroutines/Deferred;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "joinAll", "", "jobs", "Lkotlinx/coroutines/Job;", "([Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "(Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=2, mv={1, 1, 16})
public final class AwaitKt
{
  public static final <T> Object awaitAll(Collection<? extends Deferred<? extends T>> paramCollection, Continuation<? super List<? extends T>> paramContinuation)
  {
    if ((paramContinuation instanceof awaitAll.2))
    {
      localObject1 = (awaitAll.2)paramContinuation;
      if ((((awaitAll.2)localObject1).label & 0x80000000) != 0)
      {
        ((awaitAll.2)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super List<? extends T>>)localObject1;
        break label47;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      int label;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return AwaitKt.awaitAll(null, this);
      }
    };
    label47:
    Object localObject1 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int i = paramContinuation.label;
    if (i != 0)
    {
      if (i == 1)
      {
        paramCollection = (Collection)paramContinuation.L$0;
        ResultKt.throwOnFailure(localObject1);
        paramCollection = (Collection<? extends Deferred<? extends T>>)localObject1;
      }
      else
      {
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }
    else
    {
      ResultKt.throwOnFailure(localObject1);
      if (paramCollection.isEmpty())
      {
        paramCollection = CollectionsKt.emptyList();
        break label177;
      }
      localObject1 = paramCollection.toArray(new Deferred[0]);
      if (localObject1 == null) {
        break label179;
      }
      localObject1 = new AwaitAll((Deferred[])localObject1);
      paramContinuation.L$0 = paramCollection;
      paramContinuation.label = 1;
      paramContinuation = ((AwaitAll)localObject1).await(paramContinuation);
      paramCollection = paramContinuation;
      if (paramContinuation == localObject2) {
        return localObject2;
      }
    }
    paramCollection = (List)paramCollection;
    label177:
    return paramCollection;
    label179:
    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
  }
  
  public static final <T> Object awaitAll(Deferred<? extends T>[] paramArrayOfDeferred, Continuation<? super List<? extends T>> paramContinuation)
  {
    if ((paramContinuation instanceof awaitAll.1))
    {
      localObject1 = (awaitAll.1)paramContinuation;
      if ((((awaitAll.1)localObject1).label & 0x80000000) != 0)
      {
        ((awaitAll.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super List<? extends T>>)localObject1;
        break label47;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      int label;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return AwaitKt.awaitAll(null, this);
      }
    };
    label47:
    Object localObject1 = paramContinuation.result;
    Object localObject2 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int i = paramContinuation.label;
    if (i != 0)
    {
      if (i == 1)
      {
        paramArrayOfDeferred = (Deferred[])paramContinuation.L$0;
        ResultKt.throwOnFailure(localObject1);
        paramArrayOfDeferred = (Deferred<? extends T>[])localObject1;
      }
      else
      {
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }
    else
    {
      ResultKt.throwOnFailure(localObject1);
      if (paramArrayOfDeferred.length == 0) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        return CollectionsKt.emptyList();
      }
      localObject1 = new AwaitAll(paramArrayOfDeferred);
      paramContinuation.L$0 = paramArrayOfDeferred;
      paramContinuation.label = 1;
      paramContinuation = ((AwaitAll)localObject1).await(paramContinuation);
      paramArrayOfDeferred = paramContinuation;
      if (paramContinuation == localObject2) {
        return localObject2;
      }
    }
    paramArrayOfDeferred = (List)paramArrayOfDeferred;
    return paramArrayOfDeferred;
  }
  
  public static final Object joinAll(Collection<? extends Job> paramCollection, Continuation<? super Unit> paramContinuation)
  {
    Object localObject1;
    if ((paramContinuation instanceof joinAll.3))
    {
      localObject1 = (joinAll.3)paramContinuation;
      if ((((joinAll.3)localObject1).label & 0x80000000) != 0)
      {
        ((joinAll.3)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super Unit>)localObject1;
        break label47;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      Object L$0;
      Object L$1;
      Object L$2;
      Object L$3;
      Object L$4;
      int label;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return AwaitKt.joinAll(null, this);
      }
    };
    label47:
    Object localObject2 = paramContinuation.result;
    Object localObject3 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int i = paramContinuation.label;
    Iterator localIterator;
    if (i != 0)
    {
      if (i == 1)
      {
        paramCollection = (Job)paramContinuation.L$4;
        paramCollection = paramContinuation.L$3;
        localIterator = (Iterator)paramContinuation.L$2;
        paramCollection = (Iterable)paramContinuation.L$1;
        localObject1 = (Collection)paramContinuation.L$0;
        ResultKt.throwOnFailure(localObject2);
      }
      else
      {
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }
    else
    {
      ResultKt.throwOnFailure(localObject2);
      localObject2 = (Iterable)paramCollection;
      localIterator = ((Iterable)localObject2).iterator();
      localObject1 = paramCollection;
      paramCollection = (Collection<? extends Job>)localObject2;
    }
    while (localIterator.hasNext())
    {
      Object localObject4 = localIterator.next();
      localObject2 = (Job)localObject4;
      paramContinuation.L$0 = localObject1;
      paramContinuation.L$1 = paramCollection;
      paramContinuation.L$2 = localIterator;
      paramContinuation.L$3 = localObject4;
      paramContinuation.L$4 = localObject2;
      paramContinuation.label = 1;
      if (((Job)localObject2).join(paramContinuation) == localObject3) {
        return localObject3;
      }
    }
    return Unit.INSTANCE;
  }
  
  public static final Object joinAll(Job[] paramArrayOfJob, Continuation<? super Unit> paramContinuation)
  {
    Object localObject1;
    if ((paramContinuation instanceof joinAll.1))
    {
      localObject1 = (joinAll.1)paramContinuation;
      if ((((joinAll.1)localObject1).label & 0x80000000) != 0)
      {
        ((joinAll.1)localObject1).label += Integer.MIN_VALUE;
        paramContinuation = (Continuation<? super Unit>)localObject1;
        break label47;
      }
    }
    paramContinuation = new ContinuationImpl(paramContinuation)
    {
      int I$0;
      int I$1;
      Object L$0;
      Object L$1;
      Object L$2;
      Object L$3;
      Object L$4;
      int label;
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        this.result = paramAnonymousObject;
        this.label |= 0x80000000;
        return AwaitKt.joinAll(null, this);
      }
    };
    label47:
    Object localObject2 = paramContinuation.result;
    Object localObject3 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
    int i = paramContinuation.label;
    int j;
    Job[] arrayOfJob;
    Object localObject4;
    if (i != 0)
    {
      if (i == 1)
      {
        paramArrayOfJob = (Job)paramContinuation.L$4;
        paramArrayOfJob = (Job)paramContinuation.L$3;
        i = paramContinuation.I$1;
        j = paramContinuation.I$0;
        localObject1 = (Job[])paramContinuation.L$2;
        arrayOfJob = (Job[])paramContinuation.L$1;
        paramArrayOfJob = (Job[])paramContinuation.L$0;
        ResultKt.throwOnFailure(localObject2);
        localObject4 = paramContinuation;
      }
      else
      {
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }
    else
    {
      ResultKt.throwOnFailure(localObject2);
      j = paramArrayOfJob.length;
      localObject1 = paramArrayOfJob;
      paramArrayOfJob = (Job[])localObject1;
      i = 0;
      localObject2 = paramContinuation;
      localObject4 = paramArrayOfJob;
      paramContinuation = (Continuation<? super Unit>)localObject3;
      arrayOfJob = paramArrayOfJob;
      paramArrayOfJob = (Job[])localObject4;
    }
    while (i < j)
    {
      Object localObject5 = localObject1[i];
      ((joinAll.1)localObject2).L$0 = paramArrayOfJob;
      ((joinAll.1)localObject2).L$1 = arrayOfJob;
      ((joinAll.1)localObject2).L$2 = localObject1;
      ((joinAll.1)localObject2).I$0 = j;
      ((joinAll.1)localObject2).I$1 = i;
      ((joinAll.1)localObject2).L$3 = localObject5;
      ((joinAll.1)localObject2).L$4 = localObject5;
      ((joinAll.1)localObject2).label = 1;
      localObject4 = localObject2;
      localObject3 = paramContinuation;
      if (localObject5.join((Continuation)localObject2) == paramContinuation) {
        return paramContinuation;
      }
      i++;
      localObject2 = localObject4;
      paramContinuation = (Continuation<? super Unit>)localObject3;
    }
    return Unit.INSTANCE;
  }
}
