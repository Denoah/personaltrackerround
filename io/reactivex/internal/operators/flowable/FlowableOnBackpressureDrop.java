package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureDrop<T>
  extends AbstractFlowableWithUpstream<T, T>
  implements Consumer<T>
{
  final Consumer<? super T> onDrop;
  
  public FlowableOnBackpressureDrop(Flowable<T> paramFlowable)
  {
    super(paramFlowable);
    this.onDrop = this;
  }
  
  public FlowableOnBackpressureDrop(Flowable<T> paramFlowable, Consumer<? super T> paramConsumer)
  {
    super(paramFlowable);
    this.onDrop = paramConsumer;
  }
  
  public void accept(T paramT) {}
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new BackpressureDropSubscriber(paramSubscriber, this.onDrop));
  }
  
  static final class BackpressureDropSubscriber<T>
    extends AtomicLong
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -6246093802440953054L;
    boolean done;
    final Subscriber<? super T> downstream;
    final Consumer<? super T> onDrop;
    Subscription upstream;
    
    BackpressureDropSubscriber(Subscriber<? super T> paramSubscriber, Consumer<? super T> paramConsumer)
    {
      this.downstream = paramSubscriber;
      this.onDrop = paramConsumer;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 46	io/reactivex/internal/operators/flowable/FlowableOnBackpressureDrop$BackpressureDropSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: invokevirtual 63	io/reactivex/internal/operators/flowable/FlowableOnBackpressureDrop$BackpressureDropSubscriber:get	()J
      //   12: lconst_0
      //   13: lcmp
      //   14: ifeq +22 -> 36
      //   17: aload_0
      //   18: getfield 33	io/reactivex/internal/operators/flowable/FlowableOnBackpressureDrop$BackpressureDropSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   21: aload_1
      //   22: invokeinterface 65 2 0
      //   27: aload_0
      //   28: lconst_1
      //   29: invokestatic 71	io/reactivex/internal/util/BackpressureHelper:produced	(Ljava/util/concurrent/atomic/AtomicLong;J)J
      //   32: pop2
      //   33: goto +30 -> 63
      //   36: aload_0
      //   37: getfield 35	io/reactivex/internal/operators/flowable/FlowableOnBackpressureDrop$BackpressureDropSubscriber:onDrop	Lio/reactivex/functions/Consumer;
      //   40: aload_1
      //   41: invokeinterface 76 2 0
      //   46: goto +17 -> 63
      //   49: astore_1
      //   50: aload_1
      //   51: invokestatic 81	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   54: aload_0
      //   55: invokevirtual 82	io/reactivex/internal/operators/flowable/FlowableOnBackpressureDrop$BackpressureDropSubscriber:cancel	()V
      //   58: aload_0
      //   59: aload_1
      //   60: invokevirtual 83	io/reactivex/internal/operators/flowable/FlowableOnBackpressureDrop$BackpressureDropSubscriber:onError	(Ljava/lang/Throwable;)V
      //   63: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	64	0	this	BackpressureDropSubscriber
      //   0	64	1	paramT	T
      // Exception table:
      //   from	to	target	type
      //   36	46	49	finally
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        BackpressureHelper.add(this, paramLong);
      }
    }
  }
}
