package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableUsing<T, D>
  extends Flowable<T>
{
  final Consumer<? super D> disposer;
  final boolean eager;
  final Callable<? extends D> resourceSupplier;
  final Function<? super D, ? extends Publisher<? extends T>> sourceSupplier;
  
  public FlowableUsing(Callable<? extends D> paramCallable, Function<? super D, ? extends Publisher<? extends T>> paramFunction, Consumer<? super D> paramConsumer, boolean paramBoolean)
  {
    this.resourceSupplier = paramCallable;
    this.sourceSupplier = paramFunction;
    this.disposer = paramConsumer;
    this.eager = paramBoolean;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    try
    {
      Object localObject = this.resourceSupplier.call();
      try
      {
        Publisher localPublisher = (Publisher)ObjectHelper.requireNonNull(this.sourceSupplier.apply(localObject), "The sourceSupplier returned a null Publisher");
        localPublisher.subscribe(new UsingSubscriber(paramSubscriber, localObject, this.disposer, this.eager));
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable3);
        try
        {
          this.disposer.accept(localObject);
          EmptySubscription.error(localThrowable3, paramSubscriber);
          return;
        }
        finally
        {
          Exceptions.throwIfFatal(localThrowable1);
          EmptySubscription.error(new CompositeException(new Throwable[] { localThrowable3, localThrowable1 }), paramSubscriber);
          return;
        }
      }
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable2);
      EmptySubscription.error(localThrowable2, paramSubscriber);
    }
  }
  
  static final class UsingSubscriber<T, D>
    extends AtomicBoolean
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = 5904473792286235046L;
    final Consumer<? super D> disposer;
    final Subscriber<? super T> downstream;
    final boolean eager;
    final D resource;
    Subscription upstream;
    
    UsingSubscriber(Subscriber<? super T> paramSubscriber, D paramD, Consumer<? super D> paramConsumer, boolean paramBoolean)
    {
      this.downstream = paramSubscriber;
      this.resource = paramD;
      this.disposer = paramConsumer;
      this.eager = paramBoolean;
    }
    
    public void cancel()
    {
      disposeAfter();
      this.upstream.cancel();
    }
    
    /* Error */
    void disposeAfter()
    {
      // Byte code:
      //   0: aload_0
      //   1: iconst_0
      //   2: iconst_1
      //   3: invokevirtual 57	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:compareAndSet	(ZZ)Z
      //   6: ifeq +28 -> 34
      //   9: aload_0
      //   10: getfield 40	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:disposer	Lio/reactivex/functions/Consumer;
      //   13: aload_0
      //   14: getfield 38	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:resource	Ljava/lang/Object;
      //   17: invokeinterface 63 2 0
      //   22: goto +12 -> 34
      //   25: astore_1
      //   26: aload_1
      //   27: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   30: aload_1
      //   31: invokestatic 74	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   34: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	35	0	this	UsingSubscriber
      //   25	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   9	22	25	finally
    }
    
    /* Error */
    public void onComplete()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 42	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:eager	Z
      //   4: ifeq +65 -> 69
      //   7: aload_0
      //   8: iconst_0
      //   9: iconst_1
      //   10: invokevirtual 57	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:compareAndSet	(ZZ)Z
      //   13: ifeq +35 -> 48
      //   16: aload_0
      //   17: getfield 40	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:disposer	Lio/reactivex/functions/Consumer;
      //   20: aload_0
      //   21: getfield 38	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:resource	Ljava/lang/Object;
      //   24: invokeinterface 63 2 0
      //   29: goto +19 -> 48
      //   32: astore_1
      //   33: aload_1
      //   34: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   37: aload_0
      //   38: getfield 36	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   41: aload_1
      //   42: invokeinterface 78 2 0
      //   47: return
      //   48: aload_0
      //   49: getfield 51	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   52: invokeinterface 53 1 0
      //   57: aload_0
      //   58: getfield 36	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   61: invokeinterface 80 1 0
      //   66: goto +25 -> 91
      //   69: aload_0
      //   70: getfield 36	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   73: invokeinterface 80 1 0
      //   78: aload_0
      //   79: getfield 51	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   82: invokeinterface 53 1 0
      //   87: aload_0
      //   88: invokevirtual 49	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:disposeAfter	()V
      //   91: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	92	0	this	UsingSubscriber
      //   32	10	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   16	29	32	finally
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 42	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:eager	Z
      //   4: ifeq +96 -> 100
      //   7: aconst_null
      //   8: astore_2
      //   9: aload_2
      //   10: astore_3
      //   11: aload_0
      //   12: iconst_0
      //   13: iconst_1
      //   14: invokevirtual 57	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:compareAndSet	(ZZ)Z
      //   17: ifeq +26 -> 43
      //   20: aload_0
      //   21: getfield 40	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:disposer	Lio/reactivex/functions/Consumer;
      //   24: aload_0
      //   25: getfield 38	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:resource	Ljava/lang/Object;
      //   28: invokeinterface 63 2 0
      //   33: aload_2
      //   34: astore_3
      //   35: goto +8 -> 43
      //   38: astore_3
      //   39: aload_3
      //   40: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   43: aload_0
      //   44: getfield 51	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   47: invokeinterface 53 1 0
      //   52: aload_3
      //   53: ifnull +34 -> 87
      //   56: aload_0
      //   57: getfield 36	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   60: new 82	io/reactivex/exceptions/CompositeException
      //   63: dup
      //   64: iconst_2
      //   65: anewarray 84	java/lang/Throwable
      //   68: dup
      //   69: iconst_0
      //   70: aload_1
      //   71: aastore
      //   72: dup
      //   73: iconst_1
      //   74: aload_3
      //   75: aastore
      //   76: invokespecial 87	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   79: invokeinterface 78 2 0
      //   84: goto +39 -> 123
      //   87: aload_0
      //   88: getfield 36	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   91: aload_1
      //   92: invokeinterface 78 2 0
      //   97: goto +26 -> 123
      //   100: aload_0
      //   101: getfield 36	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   104: aload_1
      //   105: invokeinterface 78 2 0
      //   110: aload_0
      //   111: getfield 51	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   114: invokeinterface 53 1 0
      //   119: aload_0
      //   120: invokevirtual 49	io/reactivex/internal/operators/flowable/FlowableUsing$UsingSubscriber:disposeAfter	()V
      //   123: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	124	0	this	UsingSubscriber
      //   0	124	1	paramThrowable	Throwable
      //   8	26	2	localObject1	Object
      //   10	25	3	localObject2	Object
      //   38	37	3	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   20	33	38	finally
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
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      this.upstream.request(paramLong);
    }
  }
}
