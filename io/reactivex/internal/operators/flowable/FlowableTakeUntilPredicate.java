package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableTakeUntilPredicate<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Predicate<? super T> predicate;
  
  public FlowableTakeUntilPredicate(Flowable<T> paramFlowable, Predicate<? super T> paramPredicate)
  {
    super(paramFlowable);
    this.predicate = paramPredicate;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new InnerSubscriber(paramSubscriber, this.predicate));
  }
  
  static final class InnerSubscriber<T>
    implements FlowableSubscriber<T>, Subscription
  {
    boolean done;
    final Subscriber<? super T> downstream;
    final Predicate<? super T> predicate;
    Subscription upstream;
    
    InnerSubscriber(Subscriber<? super T> paramSubscriber, Predicate<? super T> paramPredicate)
    {
      this.downstream = paramSubscriber;
      this.predicate = paramPredicate;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!this.done)
      {
        this.done = true;
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 42	io/reactivex/internal/operators/flowable/FlowableTakeUntilPredicate$InnerSubscriber:done	Z
      //   4: ifne +73 -> 77
      //   7: aload_0
      //   8: getfield 29	io/reactivex/internal/operators/flowable/FlowableTakeUntilPredicate$InnerSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   11: aload_1
      //   12: invokeinterface 57 2 0
      //   17: aload_0
      //   18: getfield 31	io/reactivex/internal/operators/flowable/FlowableTakeUntilPredicate$InnerSubscriber:predicate	Lio/reactivex/functions/Predicate;
      //   21: aload_1
      //   22: invokeinterface 63 2 0
      //   27: istore_2
      //   28: iload_2
      //   29: ifeq +48 -> 77
      //   32: aload_0
      //   33: iconst_1
      //   34: putfield 42	io/reactivex/internal/operators/flowable/FlowableTakeUntilPredicate$InnerSubscriber:done	Z
      //   37: aload_0
      //   38: getfield 37	io/reactivex/internal/operators/flowable/FlowableTakeUntilPredicate$InnerSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   41: invokeinterface 39 1 0
      //   46: aload_0
      //   47: getfield 29	io/reactivex/internal/operators/flowable/FlowableTakeUntilPredicate$InnerSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   50: invokeinterface 46 1 0
      //   55: goto +22 -> 77
      //   58: astore_1
      //   59: aload_1
      //   60: invokestatic 68	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   63: aload_0
      //   64: getfield 37	io/reactivex/internal/operators/flowable/FlowableTakeUntilPredicate$InnerSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   67: invokeinterface 39 1 0
      //   72: aload_0
      //   73: aload_1
      //   74: invokevirtual 69	io/reactivex/internal/operators/flowable/FlowableTakeUntilPredicate$InnerSubscriber:onError	(Ljava/lang/Throwable;)V
      //   77: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	78	0	this	InnerSubscriber
      //   0	78	1	paramT	T
      //   27	2	2	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   17	28	58	finally
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      this.upstream.request(paramLong);
    }
  }
}
