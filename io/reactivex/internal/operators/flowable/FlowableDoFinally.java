package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Action;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDoFinally<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Action onFinally;
  
  public FlowableDoFinally(Flowable<T> paramFlowable, Action paramAction)
  {
    super(paramFlowable);
    this.onFinally = paramAction;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    if ((paramSubscriber instanceof ConditionalSubscriber)) {
      this.source.subscribe(new DoFinallyConditionalSubscriber((ConditionalSubscriber)paramSubscriber, this.onFinally));
    } else {
      this.source.subscribe(new DoFinallySubscriber(paramSubscriber, this.onFinally));
    }
  }
  
  static final class DoFinallyConditionalSubscriber<T>
    extends BasicIntQueueSubscription<T>
    implements ConditionalSubscriber<T>
  {
    private static final long serialVersionUID = 4109457741734051389L;
    final ConditionalSubscriber<? super T> downstream;
    final Action onFinally;
    QueueSubscription<T> qs;
    boolean syncFused;
    Subscription upstream;
    
    DoFinallyConditionalSubscriber(ConditionalSubscriber<? super T> paramConditionalSubscriber, Action paramAction)
    {
      this.downstream = paramConditionalSubscriber;
      this.onFinally = paramAction;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
      runFinally();
    }
    
    public void clear()
    {
      this.qs.clear();
    }
    
    public boolean isEmpty()
    {
      return this.qs.isEmpty();
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
      runFinally();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
      runFinally();
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        if ((paramSubscription instanceof QueueSubscription)) {
          this.qs = ((QueueSubscription)paramSubscription);
        }
        this.downstream.onSubscribe(this);
      }
    }
    
    public T poll()
      throws Exception
    {
      Object localObject = this.qs.poll();
      if ((localObject == null) && (this.syncFused)) {
        runFinally();
      }
      return localObject;
    }
    
    public void request(long paramLong)
    {
      this.upstream.request(paramLong);
    }
    
    public int requestFusion(int paramInt)
    {
      QueueSubscription localQueueSubscription = this.qs;
      boolean bool = false;
      if ((localQueueSubscription != null) && ((paramInt & 0x4) == 0))
      {
        paramInt = localQueueSubscription.requestFusion(paramInt);
        if (paramInt != 0)
        {
          if (paramInt == 1) {
            bool = true;
          }
          this.syncFused = bool;
        }
        return paramInt;
      }
      return 0;
    }
    
    /* Error */
    void runFinally()
    {
      // Byte code:
      //   0: aload_0
      //   1: iconst_0
      //   2: iconst_1
      //   3: invokevirtual 103	io/reactivex/internal/operators/flowable/FlowableDoFinally$DoFinallyConditionalSubscriber:compareAndSet	(II)Z
      //   6: ifeq +24 -> 30
      //   9: aload_0
      //   10: getfield 35	io/reactivex/internal/operators/flowable/FlowableDoFinally$DoFinallyConditionalSubscriber:onFinally	Lio/reactivex/functions/Action;
      //   13: invokeinterface 108 1 0
      //   18: goto +12 -> 30
      //   21: astore_1
      //   22: aload_1
      //   23: invokestatic 113	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   26: aload_1
      //   27: invokestatic 116	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   30: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	31	0	this	DoFinallyConditionalSubscriber
      //   21	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   9	18	21	finally
    }
    
    public boolean tryOnNext(T paramT)
    {
      return this.downstream.tryOnNext(paramT);
    }
  }
  
  static final class DoFinallySubscriber<T>
    extends BasicIntQueueSubscription<T>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = 4109457741734051389L;
    final Subscriber<? super T> downstream;
    final Action onFinally;
    QueueSubscription<T> qs;
    boolean syncFused;
    Subscription upstream;
    
    DoFinallySubscriber(Subscriber<? super T> paramSubscriber, Action paramAction)
    {
      this.downstream = paramSubscriber;
      this.onFinally = paramAction;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
      runFinally();
    }
    
    public void clear()
    {
      this.qs.clear();
    }
    
    public boolean isEmpty()
    {
      return this.qs.isEmpty();
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
      runFinally();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
      runFinally();
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        if ((paramSubscription instanceof QueueSubscription)) {
          this.qs = ((QueueSubscription)paramSubscription);
        }
        this.downstream.onSubscribe(this);
      }
    }
    
    public T poll()
      throws Exception
    {
      Object localObject = this.qs.poll();
      if ((localObject == null) && (this.syncFused)) {
        runFinally();
      }
      return localObject;
    }
    
    public void request(long paramLong)
    {
      this.upstream.request(paramLong);
    }
    
    public int requestFusion(int paramInt)
    {
      QueueSubscription localQueueSubscription = this.qs;
      boolean bool = false;
      if ((localQueueSubscription != null) && ((paramInt & 0x4) == 0))
      {
        paramInt = localQueueSubscription.requestFusion(paramInt);
        if (paramInt != 0)
        {
          if (paramInt == 1) {
            bool = true;
          }
          this.syncFused = bool;
        }
        return paramInt;
      }
      return 0;
    }
    
    /* Error */
    void runFinally()
    {
      // Byte code:
      //   0: aload_0
      //   1: iconst_0
      //   2: iconst_1
      //   3: invokevirtual 105	io/reactivex/internal/operators/flowable/FlowableDoFinally$DoFinallySubscriber:compareAndSet	(II)Z
      //   6: ifeq +24 -> 30
      //   9: aload_0
      //   10: getfield 35	io/reactivex/internal/operators/flowable/FlowableDoFinally$DoFinallySubscriber:onFinally	Lio/reactivex/functions/Action;
      //   13: invokeinterface 110 1 0
      //   18: goto +12 -> 30
      //   21: astore_1
      //   22: aload_1
      //   23: invokestatic 115	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   26: aload_1
      //   27: invokestatic 118	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   30: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	31	0	this	DoFinallySubscriber
      //   21	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   9	18	21	finally
    }
  }
}
