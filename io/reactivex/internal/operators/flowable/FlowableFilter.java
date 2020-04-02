package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFilter<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Predicate<? super T> predicate;
  
  public FlowableFilter(Flowable<T> paramFlowable, Predicate<? super T> paramPredicate)
  {
    super(paramFlowable);
    this.predicate = paramPredicate;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    if ((paramSubscriber instanceof ConditionalSubscriber)) {
      this.source.subscribe(new FilterConditionalSubscriber((ConditionalSubscriber)paramSubscriber, this.predicate));
    } else {
      this.source.subscribe(new FilterSubscriber(paramSubscriber, this.predicate));
    }
  }
  
  static final class FilterConditionalSubscriber<T>
    extends BasicFuseableConditionalSubscriber<T, T>
  {
    final Predicate<? super T> filter;
    
    FilterConditionalSubscriber(ConditionalSubscriber<? super T> paramConditionalSubscriber, Predicate<? super T> paramPredicate)
    {
      super();
      this.filter = paramPredicate;
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
      QueueSubscription localQueueSubscription = this.qs;
      Predicate localPredicate = this.filter;
      for (;;)
      {
        Object localObject = localQueueSubscription.poll();
        if (localObject == null) {
          return null;
        }
        if (localPredicate.test(localObject)) {
          return localObject;
        }
        if (this.sourceMode == 2) {
          localQueueSubscription.request(1L);
        }
      }
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
    
    public boolean tryOnNext(T paramT)
    {
      boolean bool1 = this.done;
      boolean bool2 = false;
      if (bool1) {
        return false;
      }
      if (this.sourceMode != 0) {
        return this.downstream.tryOnNext(null);
      }
      try
      {
        boolean bool3 = this.filter.test(paramT);
        bool1 = bool2;
        if (bool3)
        {
          bool1 = bool2;
          if (this.downstream.tryOnNext(paramT)) {
            bool1 = true;
          }
        }
        return bool1;
      }
      finally
      {
        fail(paramT);
      }
      return true;
    }
  }
  
  static final class FilterSubscriber<T>
    extends BasicFuseableSubscriber<T, T>
    implements ConditionalSubscriber<T>
  {
    final Predicate<? super T> filter;
    
    FilterSubscriber(Subscriber<? super T> paramSubscriber, Predicate<? super T> paramPredicate)
    {
      super();
      this.filter = paramPredicate;
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
      QueueSubscription localQueueSubscription = this.qs;
      Predicate localPredicate = this.filter;
      for (;;)
      {
        Object localObject = localQueueSubscription.poll();
        if (localObject == null) {
          return null;
        }
        if (localPredicate.test(localObject)) {
          return localObject;
        }
        if (this.sourceMode == 2) {
          localQueueSubscription.request(1L);
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
        this.downstream.onNext(null);
        return true;
      }
      try
      {
        boolean bool = this.filter.test(paramT);
        if (bool) {
          this.downstream.onNext(paramT);
        }
        return bool;
      }
      finally
      {
        fail(paramT);
      }
      return true;
    }
  }
}
