package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWithLatestFromMany<T, R>
  extends AbstractFlowableWithUpstream<T, R>
{
  final Function<? super Object[], R> combiner;
  final Publisher<?>[] otherArray;
  final Iterable<? extends Publisher<?>> otherIterable;
  
  public FlowableWithLatestFromMany(Flowable<T> paramFlowable, Iterable<? extends Publisher<?>> paramIterable, Function<? super Object[], R> paramFunction)
  {
    super(paramFlowable);
    this.otherArray = null;
    this.otherIterable = paramIterable;
    this.combiner = paramFunction;
  }
  
  public FlowableWithLatestFromMany(Flowable<T> paramFlowable, Publisher<?>[] paramArrayOfPublisher, Function<? super Object[], R> paramFunction)
  {
    super(paramFlowable);
    this.otherArray = paramArrayOfPublisher;
    this.otherIterable = null;
    this.combiner = paramFunction;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    Object localObject1 = this.otherArray;
    int j;
    if (localObject1 == null)
    {
      Object localObject2 = new Publisher[8];
      try
      {
        Iterator localIterator = this.otherIterable.iterator();
        int i = 0;
        for (;;)
        {
          localObject1 = localObject2;
          j = i;
          if (!localIterator.hasNext()) {
            break;
          }
          Publisher localPublisher = (Publisher)localIterator.next();
          localObject1 = localObject2;
          if (i == localObject2.length) {
            localObject1 = (Publisher[])Arrays.copyOf((Object[])localObject2, (i >> 1) + i);
          }
          localObject1[i] = localPublisher;
          i++;
          localObject2 = localObject1;
        }
        j = localObject1.length;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        EmptySubscription.error(localThrowable, paramSubscriber);
        return;
      }
    }
    if (j == 0)
    {
      new FlowableMap(this.source, new SingletonArrayFunc()).subscribeActual(paramSubscriber);
      return;
    }
    WithLatestFromSubscriber localWithLatestFromSubscriber = new WithLatestFromSubscriber(paramSubscriber, this.combiner, j);
    paramSubscriber.onSubscribe(localWithLatestFromSubscriber);
    localWithLatestFromSubscriber.subscribe((Publisher[])localObject1, j);
    this.source.subscribe(localWithLatestFromSubscriber);
  }
  
  final class SingletonArrayFunc
    implements Function<T, R>
  {
    SingletonArrayFunc() {}
    
    public R apply(T paramT)
      throws Exception
    {
      return ObjectHelper.requireNonNull(FlowableWithLatestFromMany.this.combiner.apply(new Object[] { paramT }), "The combiner returned a null value");
    }
  }
  
  static final class WithLatestFromSubscriber<T, R>
    extends AtomicInteger
    implements ConditionalSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = 1577321883966341961L;
    final Function<? super Object[], R> combiner;
    volatile boolean done;
    final Subscriber<? super R> downstream;
    final AtomicThrowable error;
    final AtomicLong requested;
    final FlowableWithLatestFromMany.WithLatestInnerSubscriber[] subscribers;
    final AtomicReference<Subscription> upstream;
    final AtomicReferenceArray<Object> values;
    
    WithLatestFromSubscriber(Subscriber<? super R> paramSubscriber, Function<? super Object[], R> paramFunction, int paramInt)
    {
      this.downstream = paramSubscriber;
      this.combiner = paramFunction;
      paramSubscriber = new FlowableWithLatestFromMany.WithLatestInnerSubscriber[paramInt];
      for (int i = 0; i < paramInt; i++) {
        paramSubscriber[i] = new FlowableWithLatestFromMany.WithLatestInnerSubscriber(this, i);
      }
      this.subscribers = paramSubscriber;
      this.values = new AtomicReferenceArray(paramInt);
      this.upstream = new AtomicReference();
      this.requested = new AtomicLong();
      this.error = new AtomicThrowable();
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this.upstream);
      FlowableWithLatestFromMany.WithLatestInnerSubscriber[] arrayOfWithLatestInnerSubscriber = this.subscribers;
      int i = arrayOfWithLatestInnerSubscriber.length;
      for (int j = 0; j < i; j++) {
        arrayOfWithLatestInnerSubscriber[j].dispose();
      }
    }
    
    void cancelAllBut(int paramInt)
    {
      FlowableWithLatestFromMany.WithLatestInnerSubscriber[] arrayOfWithLatestInnerSubscriber = this.subscribers;
      for (int i = 0; i < arrayOfWithLatestInnerSubscriber.length; i++) {
        if (i != paramInt) {
          arrayOfWithLatestInnerSubscriber[i].dispose();
        }
      }
    }
    
    void innerComplete(int paramInt, boolean paramBoolean)
    {
      if (!paramBoolean)
      {
        this.done = true;
        SubscriptionHelper.cancel(this.upstream);
        cancelAllBut(paramInt);
        HalfSerializer.onComplete(this.downstream, this, this.error);
      }
    }
    
    void innerError(int paramInt, Throwable paramThrowable)
    {
      this.done = true;
      SubscriptionHelper.cancel(this.upstream);
      cancelAllBut(paramInt);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
    }
    
    void innerNext(int paramInt, Object paramObject)
    {
      this.values.set(paramInt, paramObject);
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        cancelAllBut(-1);
        HalfSerializer.onComplete(this.downstream, this, this.error);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      cancelAllBut(-1);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
    }
    
    public void onNext(T paramT)
    {
      if ((!tryOnNext(paramT)) && (!this.done)) {
        ((Subscription)this.upstream.get()).request(1L);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.deferredSetOnce(this.upstream, this.requested, paramSubscription);
    }
    
    public void request(long paramLong)
    {
      SubscriptionHelper.deferredRequest(this.upstream, this.requested, paramLong);
    }
    
    void subscribe(Publisher<?>[] paramArrayOfPublisher, int paramInt)
    {
      FlowableWithLatestFromMany.WithLatestInnerSubscriber[] arrayOfWithLatestInnerSubscriber = this.subscribers;
      AtomicReference localAtomicReference = this.upstream;
      for (int i = 0; i < paramInt; i++)
      {
        if (localAtomicReference.get() == SubscriptionHelper.CANCELLED) {
          return;
        }
        paramArrayOfPublisher[i].subscribe(arrayOfWithLatestInnerSubscriber[i]);
      }
    }
    
    public boolean tryOnNext(T paramT)
    {
      if (this.done) {
        return false;
      }
      AtomicReferenceArray localAtomicReferenceArray = this.values;
      int i = localAtomicReferenceArray.length();
      Object[] arrayOfObject = new Object[i + 1];
      arrayOfObject[0] = paramT;
      int j = 0;
      while (j < i)
      {
        paramT = localAtomicReferenceArray.get(j);
        if (paramT == null) {
          return false;
        }
        j++;
        arrayOfObject[j] = paramT;
      }
      try
      {
        paramT = ObjectHelper.requireNonNull(this.combiner.apply(arrayOfObject), "The combiner returned a null value");
        HalfSerializer.onNext(this.downstream, paramT, this, this.error);
        return true;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        cancel();
        onError(paramT);
      }
      return false;
    }
  }
  
  static final class WithLatestInnerSubscriber
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<Object>
  {
    private static final long serialVersionUID = 3256684027868224024L;
    boolean hasValue;
    final int index;
    final FlowableWithLatestFromMany.WithLatestFromSubscriber<?, ?> parent;
    
    WithLatestInnerSubscriber(FlowableWithLatestFromMany.WithLatestFromSubscriber<?, ?> paramWithLatestFromSubscriber, int paramInt)
    {
      this.parent = paramWithLatestFromSubscriber;
      this.index = paramInt;
    }
    
    void dispose()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public void onComplete()
    {
      this.parent.innerComplete(this.index, this.hasValue);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.innerError(this.index, paramThrowable);
    }
    
    public void onNext(Object paramObject)
    {
      if (!this.hasValue) {
        this.hasValue = true;
      }
      this.parent.innerNext(this.index, paramObject);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this, paramSubscription, Long.MAX_VALUE);
    }
  }
}
