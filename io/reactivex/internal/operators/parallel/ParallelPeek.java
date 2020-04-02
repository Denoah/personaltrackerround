package io.reactivex.internal.operators.parallel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.LongConsumer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.parallel.ParallelFlowable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelPeek<T>
  extends ParallelFlowable<T>
{
  final Consumer<? super T> onAfterNext;
  final Action onAfterTerminated;
  final Action onCancel;
  final Action onComplete;
  final Consumer<? super Throwable> onError;
  final Consumer<? super T> onNext;
  final LongConsumer onRequest;
  final Consumer<? super Subscription> onSubscribe;
  final ParallelFlowable<T> source;
  
  public ParallelPeek(ParallelFlowable<T> paramParallelFlowable, Consumer<? super T> paramConsumer1, Consumer<? super T> paramConsumer2, Consumer<? super Throwable> paramConsumer, Action paramAction1, Action paramAction2, Consumer<? super Subscription> paramConsumer3, LongConsumer paramLongConsumer, Action paramAction3)
  {
    this.source = paramParallelFlowable;
    this.onNext = ((Consumer)ObjectHelper.requireNonNull(paramConsumer1, "onNext is null"));
    this.onAfterNext = ((Consumer)ObjectHelper.requireNonNull(paramConsumer2, "onAfterNext is null"));
    this.onError = ((Consumer)ObjectHelper.requireNonNull(paramConsumer, "onError is null"));
    this.onComplete = ((Action)ObjectHelper.requireNonNull(paramAction1, "onComplete is null"));
    this.onAfterTerminated = ((Action)ObjectHelper.requireNonNull(paramAction2, "onAfterTerminated is null"));
    this.onSubscribe = ((Consumer)ObjectHelper.requireNonNull(paramConsumer3, "onSubscribe is null"));
    this.onRequest = ((LongConsumer)ObjectHelper.requireNonNull(paramLongConsumer, "onRequest is null"));
    this.onCancel = ((Action)ObjectHelper.requireNonNull(paramAction3, "onCancel is null"));
  }
  
  public int parallelism()
  {
    return this.source.parallelism();
  }
  
  public void subscribe(Subscriber<? super T>[] paramArrayOfSubscriber)
  {
    if (!validate(paramArrayOfSubscriber)) {
      return;
    }
    int i = paramArrayOfSubscriber.length;
    Subscriber[] arrayOfSubscriber = new Subscriber[i];
    for (int j = 0; j < i; j++) {
      arrayOfSubscriber[j] = new ParallelPeekSubscriber(paramArrayOfSubscriber[j], this);
    }
    this.source.subscribe(arrayOfSubscriber);
  }
  
  static final class ParallelPeekSubscriber<T>
    implements FlowableSubscriber<T>, Subscription
  {
    boolean done;
    final Subscriber<? super T> downstream;
    final ParallelPeek<T> parent;
    Subscription upstream;
    
    ParallelPeekSubscriber(Subscriber<? super T> paramSubscriber, ParallelPeek<T> paramParallelPeek)
    {
      this.downstream = paramSubscriber;
      this.parent = paramParallelPeek;
    }
    
    /* Error */
    public void cancel()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 31	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:parent	Lio/reactivex/internal/operators/parallel/ParallelPeek;
      //   4: getfield 39	io/reactivex/internal/operators/parallel/ParallelPeek:onCancel	Lio/reactivex/functions/Action;
      //   7: invokeinterface 44 1 0
      //   12: goto +12 -> 24
      //   15: astore_1
      //   16: aload_1
      //   17: invokestatic 50	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   20: aload_1
      //   21: invokestatic 55	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   24: aload_0
      //   25: getfield 57	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   28: invokeinterface 59 1 0
      //   33: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	34	0	this	ParallelPeekSubscriber
      //   15	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	12	15	finally
    }
    
    /* Error */
    public void onComplete()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 62	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:done	Z
      //   4: ifne +71 -> 75
      //   7: aload_0
      //   8: iconst_1
      //   9: putfield 62	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:done	Z
      //   12: aload_0
      //   13: getfield 31	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:parent	Lio/reactivex/internal/operators/parallel/ParallelPeek;
      //   16: getfield 64	io/reactivex/internal/operators/parallel/ParallelPeek:onComplete	Lio/reactivex/functions/Action;
      //   19: invokeinterface 44 1 0
      //   24: aload_0
      //   25: getfield 29	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   28: invokeinterface 68 1 0
      //   33: aload_0
      //   34: getfield 31	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:parent	Lio/reactivex/internal/operators/parallel/ParallelPeek;
      //   37: getfield 71	io/reactivex/internal/operators/parallel/ParallelPeek:onAfterTerminated	Lio/reactivex/functions/Action;
      //   40: invokeinterface 44 1 0
      //   45: goto +30 -> 75
      //   48: astore_1
      //   49: aload_1
      //   50: invokestatic 50	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   53: aload_1
      //   54: invokestatic 55	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   57: goto +18 -> 75
      //   60: astore_1
      //   61: aload_1
      //   62: invokestatic 50	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   65: aload_0
      //   66: getfield 29	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   69: aload_1
      //   70: invokeinterface 72 2 0
      //   75: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	76	0	this	ParallelPeekSubscriber
      //   48	6	1	localThrowable1	Throwable
      //   60	10	1	localThrowable2	Throwable
      // Exception table:
      //   from	to	target	type
      //   33	45	48	finally
      //   12	24	60	finally
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 62	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:done	Z
      //   4: ifeq +8 -> 12
      //   7: aload_1
      //   8: invokestatic 55	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   11: return
      //   12: aload_0
      //   13: iconst_1
      //   14: putfield 62	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:done	Z
      //   17: aload_0
      //   18: getfield 31	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:parent	Lio/reactivex/internal/operators/parallel/ParallelPeek;
      //   21: getfield 75	io/reactivex/internal/operators/parallel/ParallelPeek:onError	Lio/reactivex/functions/Consumer;
      //   24: aload_1
      //   25: invokeinterface 81 2 0
      //   30: goto +28 -> 58
      //   33: astore_2
      //   34: aload_2
      //   35: invokestatic 50	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   38: new 83	io/reactivex/exceptions/CompositeException
      //   41: dup
      //   42: iconst_2
      //   43: anewarray 85	java/lang/Throwable
      //   46: dup
      //   47: iconst_0
      //   48: aload_1
      //   49: aastore
      //   50: dup
      //   51: iconst_1
      //   52: aload_2
      //   53: aastore
      //   54: invokespecial 88	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   57: astore_1
      //   58: aload_0
      //   59: getfield 29	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   62: aload_1
      //   63: invokeinterface 72 2 0
      //   68: aload_0
      //   69: getfield 31	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:parent	Lio/reactivex/internal/operators/parallel/ParallelPeek;
      //   72: getfield 71	io/reactivex/internal/operators/parallel/ParallelPeek:onAfterTerminated	Lio/reactivex/functions/Action;
      //   75: invokeinterface 44 1 0
      //   80: goto +12 -> 92
      //   83: astore_1
      //   84: aload_1
      //   85: invokestatic 50	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   88: aload_1
      //   89: invokestatic 55	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   92: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	93	0	this	ParallelPeekSubscriber
      //   0	93	1	paramThrowable	Throwable
      //   33	20	2	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   17	30	33	finally
      //   68	80	83	finally
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 62	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:done	Z
      //   4: ifne +65 -> 69
      //   7: aload_0
      //   8: getfield 31	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:parent	Lio/reactivex/internal/operators/parallel/ParallelPeek;
      //   11: getfield 91	io/reactivex/internal/operators/parallel/ParallelPeek:onNext	Lio/reactivex/functions/Consumer;
      //   14: aload_1
      //   15: invokeinterface 81 2 0
      //   20: aload_0
      //   21: getfield 29	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   24: aload_1
      //   25: invokeinterface 93 2 0
      //   30: aload_0
      //   31: getfield 31	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:parent	Lio/reactivex/internal/operators/parallel/ParallelPeek;
      //   34: getfield 96	io/reactivex/internal/operators/parallel/ParallelPeek:onAfterNext	Lio/reactivex/functions/Consumer;
      //   37: aload_1
      //   38: invokeinterface 81 2 0
      //   43: goto +26 -> 69
      //   46: astore_1
      //   47: aload_1
      //   48: invokestatic 50	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   51: aload_0
      //   52: aload_1
      //   53: invokevirtual 97	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:onError	(Ljava/lang/Throwable;)V
      //   56: goto +13 -> 69
      //   59: astore_1
      //   60: aload_1
      //   61: invokestatic 50	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   64: aload_0
      //   65: aload_1
      //   66: invokevirtual 97	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:onError	(Ljava/lang/Throwable;)V
      //   69: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	70	0	this	ParallelPeekSubscriber
      //   0	70	1	paramT	T
      // Exception table:
      //   from	to	target	type
      //   30	43	46	finally
      //   7	20	59	finally
    }
    
    /* Error */
    public void onSubscribe(Subscription paramSubscription)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 57	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   4: aload_1
      //   5: invokestatic 106	io/reactivex/internal/subscriptions/SubscriptionHelper:validate	(Lorg/reactivestreams/Subscription;Lorg/reactivestreams/Subscription;)Z
      //   8: ifeq +62 -> 70
      //   11: aload_0
      //   12: aload_1
      //   13: putfield 57	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   16: aload_0
      //   17: getfield 31	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:parent	Lio/reactivex/internal/operators/parallel/ParallelPeek;
      //   20: getfield 108	io/reactivex/internal/operators/parallel/ParallelPeek:onSubscribe	Lio/reactivex/functions/Consumer;
      //   23: aload_1
      //   24: invokeinterface 81 2 0
      //   29: aload_0
      //   30: getfield 29	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   33: aload_0
      //   34: invokeinterface 110 2 0
      //   39: goto +31 -> 70
      //   42: astore_2
      //   43: aload_2
      //   44: invokestatic 50	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   47: aload_1
      //   48: invokeinterface 59 1 0
      //   53: aload_0
      //   54: getfield 29	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   57: getstatic 116	io/reactivex/internal/subscriptions/EmptySubscription:INSTANCE	Lio/reactivex/internal/subscriptions/EmptySubscription;
      //   60: invokeinterface 110 2 0
      //   65: aload_0
      //   66: aload_2
      //   67: invokevirtual 97	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:onError	(Ljava/lang/Throwable;)V
      //   70: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	71	0	this	ParallelPeekSubscriber
      //   0	71	1	paramSubscription	Subscription
      //   42	25	2	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   16	29	42	finally
    }
    
    /* Error */
    public void request(long paramLong)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 31	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:parent	Lio/reactivex/internal/operators/parallel/ParallelPeek;
      //   4: getfield 122	io/reactivex/internal/operators/parallel/ParallelPeek:onRequest	Lio/reactivex/functions/LongConsumer;
      //   7: lload_1
      //   8: invokeinterface 126 3 0
      //   13: goto +12 -> 25
      //   16: astore_3
      //   17: aload_3
      //   18: invokestatic 50	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   21: aload_3
      //   22: invokestatic 55	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   25: aload_0
      //   26: getfield 57	io/reactivex/internal/operators/parallel/ParallelPeek$ParallelPeekSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   29: lload_1
      //   30: invokeinterface 128 3 0
      //   35: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	36	0	this	ParallelPeekSubscriber
      //   0	36	1	paramLong	long
      //   16	6	3	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   0	13	16	finally
    }
  }
}
