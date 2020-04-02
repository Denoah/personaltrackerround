package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableScanSeed<T, R>
  extends AbstractFlowableWithUpstream<T, R>
{
  final BiFunction<R, ? super T, R> accumulator;
  final Callable<R> seedSupplier;
  
  public FlowableScanSeed(Flowable<T> paramFlowable, Callable<R> paramCallable, BiFunction<R, ? super T, R> paramBiFunction)
  {
    super(paramFlowable);
    this.accumulator = paramBiFunction;
    this.seedSupplier = paramCallable;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    try
    {
      Object localObject = ObjectHelper.requireNonNull(this.seedSupplier.call(), "The seed supplied is null");
      this.source.subscribe(new ScanSeedSubscriber(paramSubscriber, this.accumulator, localObject, bufferSize()));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptySubscription.error(localThrowable, paramSubscriber);
    }
  }
  
  static final class ScanSeedSubscriber<T, R>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -1776795561228106469L;
    final BiFunction<R, ? super T, R> accumulator;
    volatile boolean cancelled;
    int consumed;
    volatile boolean done;
    final Subscriber<? super R> downstream;
    Throwable error;
    final int limit;
    final int prefetch;
    final SimplePlainQueue<R> queue;
    final AtomicLong requested;
    Subscription upstream;
    R value;
    
    ScanSeedSubscriber(Subscriber<? super R> paramSubscriber, BiFunction<R, ? super T, R> paramBiFunction, R paramR, int paramInt)
    {
      this.downstream = paramSubscriber;
      this.accumulator = paramBiFunction;
      this.value = paramR;
      this.prefetch = paramInt;
      this.limit = (paramInt - (paramInt >> 2));
      paramSubscriber = new SpscArrayQueue(paramInt);
      this.queue = paramSubscriber;
      paramSubscriber.offer(paramR);
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      this.cancelled = true;
      this.upstream.cancel();
      if (getAndIncrement() == 0) {
        this.queue.clear();
      }
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      Subscriber localSubscriber = this.downstream;
      SimplePlainQueue localSimplePlainQueue = this.queue;
      int i = this.limit;
      int j = this.consumed;
      int k = 1;
      int m;
      do
      {
        long l1 = this.requested.get();
        long l2 = 0L;
        boolean bool1;
        Object localObject;
        for (;;)
        {
          bool1 = l2 < l1;
          if (!bool1) {
            break;
          }
          if (this.cancelled)
          {
            localSimplePlainQueue.clear();
            return;
          }
          boolean bool2 = this.done;
          if (bool2)
          {
            localObject = this.error;
            if (localObject != null)
            {
              localSimplePlainQueue.clear();
              localSubscriber.onError((Throwable)localObject);
              return;
            }
          }
          localObject = localSimplePlainQueue.poll();
          if (localObject == null) {
            m = 1;
          } else {
            m = 0;
          }
          if ((bool2) && (m != 0))
          {
            localSubscriber.onComplete();
            return;
          }
          if (m != 0) {
            break;
          }
          localSubscriber.onNext(localObject);
          long l3 = l2 + 1L;
          m = j + 1;
          j = m;
          l2 = l3;
          if (m == i)
          {
            this.upstream.request(i);
            j = 0;
            l2 = l3;
          }
        }
        if ((!bool1) && (this.done))
        {
          localObject = this.error;
          if (localObject != null)
          {
            localSimplePlainQueue.clear();
            localSubscriber.onError((Throwable)localObject);
            return;
          }
          if (localSimplePlainQueue.isEmpty())
          {
            localSubscriber.onComplete();
            return;
          }
        }
        if (l2 != 0L) {
          BackpressureHelper.produced(this.requested, l2);
        }
        this.consumed = j;
        m = addAndGet(-k);
        k = m;
      } while (m != 0);
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.error = paramThrowable;
      this.done = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      Object localObject = this.value;
      try
      {
        paramT = ObjectHelper.requireNonNull(this.accumulator.apply(localObject, paramT), "The accumulator returned a null value");
        this.value = paramT;
        this.queue.offer(paramT);
        drain();
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.cancel();
        onError(paramT);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(this.prefetch - 1);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
  }
}
