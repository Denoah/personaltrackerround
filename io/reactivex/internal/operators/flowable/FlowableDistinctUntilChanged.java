package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDistinctUntilChanged<T, K>
  extends AbstractFlowableWithUpstream<T, T>
{
  final BiPredicate<? super K, ? super K> comparer;
  final Function<? super T, K> keySelector;
  
  public FlowableDistinctUntilChanged(Flowable<T> paramFlowable, Function<? super T, K> paramFunction, BiPredicate<? super K, ? super K> paramBiPredicate)
  {
    super(paramFlowable);
    this.keySelector = paramFunction;
    this.comparer = paramBiPredicate;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    if ((paramSubscriber instanceof ConditionalSubscriber))
    {
      paramSubscriber = (ConditionalSubscriber)paramSubscriber;
      this.source.subscribe(new DistinctUntilChangedConditionalSubscriber(paramSubscriber, this.keySelector, this.comparer));
    }
    else
    {
      this.source.subscribe(new DistinctUntilChangedSubscriber(paramSubscriber, this.keySelector, this.comparer));
    }
  }
  
  static final class DistinctUntilChangedConditionalSubscriber<T, K>
    extends BasicFuseableConditionalSubscriber<T, T>
  {
    final BiPredicate<? super K, ? super K> comparer;
    boolean hasValue;
    final Function<? super T, K> keySelector;
    K last;
    
    DistinctUntilChangedConditionalSubscriber(ConditionalSubscriber<? super T> paramConditionalSubscriber, Function<? super T, K> paramFunction, BiPredicate<? super K, ? super K> paramBiPredicate)
    {
      super();
      this.keySelector = paramFunction;
      this.comparer = paramBiPredicate;
    }
    
    public void onNext(T paramT)
    {
      if (!tryOnNext(paramT)) {
        this.upstream.request(1L);
      }
    }
    
    public T poll()
      throws Exception
    {
      for (;;)
      {
        Object localObject1 = this.qs.poll();
        if (localObject1 == null) {
          return null;
        }
        Object localObject2 = this.keySelector.apply(localObject1);
        if (!this.hasValue)
        {
          this.hasValue = true;
          this.last = localObject2;
          return localObject1;
        }
        if (!this.comparer.test(this.last, localObject2))
        {
          this.last = localObject2;
          return localObject1;
        }
        this.last = localObject2;
        if (this.sourceMode != 1) {
          this.upstream.request(1L);
        }
      }
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
    
    public boolean tryOnNext(T paramT)
    {
      if (this.done) {
        return false;
      }
      if (this.sourceMode != 0) {
        return this.downstream.tryOnNext(paramT);
      }
      try
      {
        Object localObject = this.keySelector.apply(paramT);
        if (this.hasValue)
        {
          boolean bool = this.comparer.test(this.last, localObject);
          this.last = localObject;
          if (bool) {
            return false;
          }
        }
        else
        {
          this.hasValue = true;
          this.last = localObject;
        }
        this.downstream.onNext(paramT);
        return true;
      }
      finally
      {
        fail(paramT);
      }
      return true;
    }
  }
  
  static final class DistinctUntilChangedSubscriber<T, K>
    extends BasicFuseableSubscriber<T, T>
    implements ConditionalSubscriber<T>
  {
    final BiPredicate<? super K, ? super K> comparer;
    boolean hasValue;
    final Function<? super T, K> keySelector;
    K last;
    
    DistinctUntilChangedSubscriber(Subscriber<? super T> paramSubscriber, Function<? super T, K> paramFunction, BiPredicate<? super K, ? super K> paramBiPredicate)
    {
      super();
      this.keySelector = paramFunction;
      this.comparer = paramBiPredicate;
    }
    
    public void onNext(T paramT)
    {
      if (!tryOnNext(paramT)) {
        this.upstream.request(1L);
      }
    }
    
    public T poll()
      throws Exception
    {
      for (;;)
      {
        Object localObject1 = this.qs.poll();
        if (localObject1 == null) {
          return null;
        }
        Object localObject2 = this.keySelector.apply(localObject1);
        if (!this.hasValue)
        {
          this.hasValue = true;
          this.last = localObject2;
          return localObject1;
        }
        if (!this.comparer.test(this.last, localObject2))
        {
          this.last = localObject2;
          return localObject1;
        }
        this.last = localObject2;
        if (this.sourceMode != 1) {
          this.upstream.request(1L);
        }
      }
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
    
    public boolean tryOnNext(T paramT)
    {
      if (this.done) {
        return false;
      }
      if (this.sourceMode != 0)
      {
        this.downstream.onNext(paramT);
        return true;
      }
      try
      {
        Object localObject = this.keySelector.apply(paramT);
        if (this.hasValue)
        {
          boolean bool = this.comparer.test(this.last, localObject);
          this.last = localObject;
          if (bool) {
            return false;
          }
        }
        else
        {
          this.hasValue = true;
          this.last = localObject;
        }
        this.downstream.onNext(paramT);
        return true;
      }
      finally
      {
        fail(paramT);
      }
      return true;
    }
  }
}
