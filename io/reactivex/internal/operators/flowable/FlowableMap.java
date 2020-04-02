package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableMap<T, U>
  extends AbstractFlowableWithUpstream<T, U>
{
  final Function<? super T, ? extends U> mapper;
  
  public FlowableMap(Flowable<T> paramFlowable, Function<? super T, ? extends U> paramFunction)
  {
    super(paramFlowable);
    this.mapper = paramFunction;
  }
  
  protected void subscribeActual(Subscriber<? super U> paramSubscriber)
  {
    if ((paramSubscriber instanceof ConditionalSubscriber)) {
      this.source.subscribe(new MapConditionalSubscriber((ConditionalSubscriber)paramSubscriber, this.mapper));
    } else {
      this.source.subscribe(new MapSubscriber(paramSubscriber, this.mapper));
    }
  }
  
  static final class MapConditionalSubscriber<T, U>
    extends BasicFuseableConditionalSubscriber<T, U>
  {
    final Function<? super T, ? extends U> mapper;
    
    MapConditionalSubscriber(ConditionalSubscriber<? super U> paramConditionalSubscriber, Function<? super T, ? extends U> paramFunction)
    {
      super();
      this.mapper = paramFunction;
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (this.sourceMode != 0)
      {
        this.downstream.onNext(null);
        return;
      }
      try
      {
        paramT = ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper function returned a null value.");
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        fail(paramT);
      }
    }
    
    public U poll()
      throws Exception
    {
      Object localObject = this.qs.poll();
      if (localObject != null) {
        localObject = ObjectHelper.requireNonNull(this.mapper.apply(localObject), "The mapper function returned a null value.");
      } else {
        localObject = null;
      }
      return localObject;
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
      try
      {
        paramT = ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper function returned a null value.");
        return this.downstream.tryOnNext(paramT);
      }
      finally
      {
        fail(paramT);
      }
      return true;
    }
  }
  
  static final class MapSubscriber<T, U>
    extends BasicFuseableSubscriber<T, U>
  {
    final Function<? super T, ? extends U> mapper;
    
    MapSubscriber(Subscriber<? super U> paramSubscriber, Function<? super T, ? extends U> paramFunction)
    {
      super();
      this.mapper = paramFunction;
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (this.sourceMode != 0)
      {
        this.downstream.onNext(null);
        return;
      }
      try
      {
        paramT = ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper function returned a null value.");
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        fail(paramT);
      }
    }
    
    public U poll()
      throws Exception
    {
      Object localObject = this.qs.poll();
      if (localObject != null) {
        localObject = ObjectHelper.requireNonNull(this.mapper.apply(localObject), "The mapper function returned a null value.");
      } else {
        localObject = null;
      }
      return localObject;
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
  }
}
