package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableDoOnEach<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Action onAfterTerminate;
  final Action onComplete;
  final Consumer<? super Throwable> onError;
  final Consumer<? super T> onNext;
  
  public FlowableDoOnEach(Flowable<T> paramFlowable, Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction1, Action paramAction2)
  {
    super(paramFlowable);
    this.onNext = paramConsumer;
    this.onError = paramConsumer1;
    this.onComplete = paramAction1;
    this.onAfterTerminate = paramAction2;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    if ((paramSubscriber instanceof ConditionalSubscriber)) {
      this.source.subscribe(new DoOnEachConditionalSubscriber((ConditionalSubscriber)paramSubscriber, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
    } else {
      this.source.subscribe(new DoOnEachSubscriber(paramSubscriber, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
    }
  }
  
  static final class DoOnEachConditionalSubscriber<T>
    extends BasicFuseableConditionalSubscriber<T, T>
  {
    final Action onAfterTerminate;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;
    
    DoOnEachConditionalSubscriber(ConditionalSubscriber<? super T> paramConditionalSubscriber, Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction1, Action paramAction2)
    {
      super();
      this.onNext = paramConsumer;
      this.onError = paramConsumer1;
      this.onComplete = paramAction1;
      this.onAfterTerminate = paramAction2;
    }
    
    /* Error */
    public void onComplete()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 37	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 27	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:onComplete	Lio/reactivex/functions/Action;
      //   12: invokeinterface 42 1 0
      //   17: aload_0
      //   18: iconst_1
      //   19: putfield 37	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:done	Z
      //   22: aload_0
      //   23: getfield 46	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:downstream	Lio/reactivex/internal/fuseable/ConditionalSubscriber;
      //   26: invokeinterface 50 1 0
      //   31: aload_0
      //   32: getfield 29	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:onAfterTerminate	Lio/reactivex/functions/Action;
      //   35: invokeinterface 42 1 0
      //   40: goto +12 -> 52
      //   43: astore_1
      //   44: aload_1
      //   45: invokestatic 56	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   48: aload_1
      //   49: invokestatic 60	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   52: return
      //   53: astore_1
      //   54: aload_0
      //   55: aload_1
      //   56: invokevirtual 63	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:fail	(Ljava/lang/Throwable;)V
      //   59: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	60	0	this	DoOnEachConditionalSubscriber
      //   43	6	1	localThrowable1	Throwable
      //   53	3	1	localThrowable2	Throwable
      // Exception table:
      //   from	to	target	type
      //   31	40	43	finally
      //   8	17	53	finally
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 37	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:done	Z
      //   4: ifeq +8 -> 12
      //   7: aload_1
      //   8: invokestatic 60	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   11: return
      //   12: iconst_1
      //   13: istore_2
      //   14: aload_0
      //   15: iconst_1
      //   16: putfield 37	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:done	Z
      //   19: aload_0
      //   20: getfield 25	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:onError	Lio/reactivex/functions/Consumer;
      //   23: aload_1
      //   24: invokeinterface 69 2 0
      //   29: goto +38 -> 67
      //   32: astore_3
      //   33: aload_3
      //   34: invokestatic 56	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   37: aload_0
      //   38: getfield 46	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:downstream	Lio/reactivex/internal/fuseable/ConditionalSubscriber;
      //   41: new 71	io/reactivex/exceptions/CompositeException
      //   44: dup
      //   45: iconst_2
      //   46: anewarray 73	java/lang/Throwable
      //   49: dup
      //   50: iconst_0
      //   51: aload_1
      //   52: aastore
      //   53: dup
      //   54: iconst_1
      //   55: aload_3
      //   56: aastore
      //   57: invokespecial 76	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   60: invokeinterface 77 2 0
      //   65: iconst_0
      //   66: istore_2
      //   67: iload_2
      //   68: ifeq +13 -> 81
      //   71: aload_0
      //   72: getfield 46	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:downstream	Lio/reactivex/internal/fuseable/ConditionalSubscriber;
      //   75: aload_1
      //   76: invokeinterface 77 2 0
      //   81: aload_0
      //   82: getfield 29	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:onAfterTerminate	Lio/reactivex/functions/Action;
      //   85: invokeinterface 42 1 0
      //   90: goto +12 -> 102
      //   93: astore_1
      //   94: aload_1
      //   95: invokestatic 56	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   98: aload_1
      //   99: invokestatic 60	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   102: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	103	0	this	DoOnEachConditionalSubscriber
      //   0	103	1	paramThrowable	Throwable
      //   13	55	2	i	int
      //   32	24	3	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   19	29	32	finally
      //   81	90	93	finally
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
        this.onNext.accept(paramT);
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        fail(paramT);
      }
    }
    
    /* Error */
    public T poll()
      throws java.lang.Exception
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 92	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:qs	Lio/reactivex/internal/fuseable/QueueSubscription;
      //   4: invokeinterface 96 1 0
      //   9: astore_1
      //   10: aload_1
      //   11: ifnull +80 -> 91
      //   14: aload_0
      //   15: getfield 23	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:onNext	Lio/reactivex/functions/Consumer;
      //   18: aload_1
      //   19: invokeinterface 69 2 0
      //   24: aload_0
      //   25: getfield 29	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:onAfterTerminate	Lio/reactivex/functions/Action;
      //   28: invokeinterface 42 1 0
      //   33: goto +84 -> 117
      //   36: astore_1
      //   37: aload_1
      //   38: invokestatic 56	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   41: aload_0
      //   42: getfield 25	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:onError	Lio/reactivex/functions/Consumer;
      //   45: aload_1
      //   46: invokeinterface 69 2 0
      //   51: aload_1
      //   52: invokestatic 102	io/reactivex/internal/util/ExceptionHelper:throwIfThrowable	(Ljava/lang/Throwable;)Ljava/lang/Exception;
      //   55: athrow
      //   56: astore_2
      //   57: new 71	io/reactivex/exceptions/CompositeException
      //   60: astore_3
      //   61: aload_3
      //   62: iconst_2
      //   63: anewarray 73	java/lang/Throwable
      //   66: dup
      //   67: iconst_0
      //   68: aload_1
      //   69: aastore
      //   70: dup
      //   71: iconst_1
      //   72: aload_2
      //   73: aastore
      //   74: invokespecial 76	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   77: aload_3
      //   78: athrow
      //   79: astore_1
      //   80: aload_0
      //   81: getfield 29	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:onAfterTerminate	Lio/reactivex/functions/Action;
      //   84: invokeinterface 42 1 0
      //   89: aload_1
      //   90: athrow
      //   91: aload_0
      //   92: getfield 81	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:sourceMode	I
      //   95: iconst_1
      //   96: if_icmpne +21 -> 117
      //   99: aload_0
      //   100: getfield 27	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:onComplete	Lio/reactivex/functions/Action;
      //   103: invokeinterface 42 1 0
      //   108: aload_0
      //   109: getfield 29	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:onAfterTerminate	Lio/reactivex/functions/Action;
      //   112: invokeinterface 42 1 0
      //   117: aload_1
      //   118: areturn
      //   119: astore_1
      //   120: aload_1
      //   121: invokestatic 56	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   124: aload_0
      //   125: getfield 25	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachConditionalSubscriber:onError	Lio/reactivex/functions/Consumer;
      //   128: aload_1
      //   129: invokeinterface 69 2 0
      //   134: aload_1
      //   135: invokestatic 102	io/reactivex/internal/util/ExceptionHelper:throwIfThrowable	(Ljava/lang/Throwable;)Ljava/lang/Exception;
      //   138: athrow
      //   139: astore_2
      //   140: new 71	io/reactivex/exceptions/CompositeException
      //   143: dup
      //   144: iconst_2
      //   145: anewarray 73	java/lang/Throwable
      //   148: dup
      //   149: iconst_0
      //   150: aload_1
      //   151: aastore
      //   152: dup
      //   153: iconst_1
      //   154: aload_2
      //   155: aastore
      //   156: invokespecial 76	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   159: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	160	0	this	DoOnEachConditionalSubscriber
      //   9	10	1	localObject1	Object
      //   36	33	1	localThrowable1	Throwable
      //   79	39	1	?	T
      //   119	32	1	localThrowable2	Throwable
      //   56	17	2	localObject2	Object
      //   139	16	2	localObject3	Object
      //   60	18	3	localCompositeException	io.reactivex.exceptions.CompositeException
      // Exception table:
      //   from	to	target	type
      //   14	24	36	finally
      //   41	51	56	finally
      //   37	41	79	finally
      //   51	56	79	finally
      //   57	79	79	finally
      //   0	10	119	finally
      //   124	134	139	finally
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
        this.onNext.accept(paramT);
        return this.downstream.tryOnNext(paramT);
      }
      finally
      {
        fail(paramT);
      }
      return false;
    }
  }
  
  static final class DoOnEachSubscriber<T>
    extends BasicFuseableSubscriber<T, T>
  {
    final Action onAfterTerminate;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;
    
    DoOnEachSubscriber(Subscriber<? super T> paramSubscriber, Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction1, Action paramAction2)
    {
      super();
      this.onNext = paramConsumer;
      this.onError = paramConsumer1;
      this.onComplete = paramAction1;
      this.onAfterTerminate = paramAction2;
    }
    
    /* Error */
    public void onComplete()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 37	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 27	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:onComplete	Lio/reactivex/functions/Action;
      //   12: invokeinterface 42 1 0
      //   17: aload_0
      //   18: iconst_1
      //   19: putfield 37	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:done	Z
      //   22: aload_0
      //   23: getfield 46	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   26: invokeinterface 50 1 0
      //   31: aload_0
      //   32: getfield 29	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:onAfterTerminate	Lio/reactivex/functions/Action;
      //   35: invokeinterface 42 1 0
      //   40: goto +12 -> 52
      //   43: astore_1
      //   44: aload_1
      //   45: invokestatic 56	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   48: aload_1
      //   49: invokestatic 60	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   52: return
      //   53: astore_1
      //   54: aload_0
      //   55: aload_1
      //   56: invokevirtual 63	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:fail	(Ljava/lang/Throwable;)V
      //   59: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	60	0	this	DoOnEachSubscriber
      //   43	6	1	localThrowable1	Throwable
      //   53	3	1	localThrowable2	Throwable
      // Exception table:
      //   from	to	target	type
      //   31	40	43	finally
      //   8	17	53	finally
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 37	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:done	Z
      //   4: ifeq +8 -> 12
      //   7: aload_1
      //   8: invokestatic 60	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   11: return
      //   12: iconst_1
      //   13: istore_2
      //   14: aload_0
      //   15: iconst_1
      //   16: putfield 37	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:done	Z
      //   19: aload_0
      //   20: getfield 25	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:onError	Lio/reactivex/functions/Consumer;
      //   23: aload_1
      //   24: invokeinterface 69 2 0
      //   29: goto +38 -> 67
      //   32: astore_3
      //   33: aload_3
      //   34: invokestatic 56	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   37: aload_0
      //   38: getfield 46	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   41: new 71	io/reactivex/exceptions/CompositeException
      //   44: dup
      //   45: iconst_2
      //   46: anewarray 73	java/lang/Throwable
      //   49: dup
      //   50: iconst_0
      //   51: aload_1
      //   52: aastore
      //   53: dup
      //   54: iconst_1
      //   55: aload_3
      //   56: aastore
      //   57: invokespecial 76	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   60: invokeinterface 77 2 0
      //   65: iconst_0
      //   66: istore_2
      //   67: iload_2
      //   68: ifeq +13 -> 81
      //   71: aload_0
      //   72: getfield 46	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   75: aload_1
      //   76: invokeinterface 77 2 0
      //   81: aload_0
      //   82: getfield 29	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:onAfterTerminate	Lio/reactivex/functions/Action;
      //   85: invokeinterface 42 1 0
      //   90: goto +12 -> 102
      //   93: astore_1
      //   94: aload_1
      //   95: invokestatic 56	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   98: aload_1
      //   99: invokestatic 60	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   102: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	103	0	this	DoOnEachSubscriber
      //   0	103	1	paramThrowable	Throwable
      //   13	55	2	i	int
      //   32	24	3	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   19	29	32	finally
      //   81	90	93	finally
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
        this.onNext.accept(paramT);
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        fail(paramT);
      }
    }
    
    /* Error */
    public T poll()
      throws java.lang.Exception
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 92	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:qs	Lio/reactivex/internal/fuseable/QueueSubscription;
      //   4: invokeinterface 96 1 0
      //   9: astore_1
      //   10: aload_1
      //   11: ifnull +80 -> 91
      //   14: aload_0
      //   15: getfield 23	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:onNext	Lio/reactivex/functions/Consumer;
      //   18: aload_1
      //   19: invokeinterface 69 2 0
      //   24: aload_0
      //   25: getfield 29	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:onAfterTerminate	Lio/reactivex/functions/Action;
      //   28: invokeinterface 42 1 0
      //   33: goto +84 -> 117
      //   36: astore_2
      //   37: aload_2
      //   38: invokestatic 56	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   41: aload_0
      //   42: getfield 25	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:onError	Lio/reactivex/functions/Consumer;
      //   45: aload_2
      //   46: invokeinterface 69 2 0
      //   51: aload_2
      //   52: invokestatic 102	io/reactivex/internal/util/ExceptionHelper:throwIfThrowable	(Ljava/lang/Throwable;)Ljava/lang/Exception;
      //   55: athrow
      //   56: astore_1
      //   57: new 71	io/reactivex/exceptions/CompositeException
      //   60: astore_3
      //   61: aload_3
      //   62: iconst_2
      //   63: anewarray 73	java/lang/Throwable
      //   66: dup
      //   67: iconst_0
      //   68: aload_2
      //   69: aastore
      //   70: dup
      //   71: iconst_1
      //   72: aload_1
      //   73: aastore
      //   74: invokespecial 76	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   77: aload_3
      //   78: athrow
      //   79: astore_1
      //   80: aload_0
      //   81: getfield 29	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:onAfterTerminate	Lio/reactivex/functions/Action;
      //   84: invokeinterface 42 1 0
      //   89: aload_1
      //   90: athrow
      //   91: aload_0
      //   92: getfield 81	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:sourceMode	I
      //   95: iconst_1
      //   96: if_icmpne +21 -> 117
      //   99: aload_0
      //   100: getfield 27	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:onComplete	Lio/reactivex/functions/Action;
      //   103: invokeinterface 42 1 0
      //   108: aload_0
      //   109: getfield 29	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:onAfterTerminate	Lio/reactivex/functions/Action;
      //   112: invokeinterface 42 1 0
      //   117: aload_1
      //   118: areturn
      //   119: astore_3
      //   120: aload_3
      //   121: invokestatic 56	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   124: aload_0
      //   125: getfield 25	io/reactivex/internal/operators/flowable/FlowableDoOnEach$DoOnEachSubscriber:onError	Lio/reactivex/functions/Consumer;
      //   128: aload_3
      //   129: invokeinterface 69 2 0
      //   134: aload_3
      //   135: invokestatic 102	io/reactivex/internal/util/ExceptionHelper:throwIfThrowable	(Ljava/lang/Throwable;)Ljava/lang/Exception;
      //   138: athrow
      //   139: astore_1
      //   140: new 71	io/reactivex/exceptions/CompositeException
      //   143: dup
      //   144: iconst_2
      //   145: anewarray 73	java/lang/Throwable
      //   148: dup
      //   149: iconst_0
      //   150: aload_3
      //   151: aastore
      //   152: dup
      //   153: iconst_1
      //   154: aload_1
      //   155: aastore
      //   156: invokespecial 76	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   159: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	160	0	this	DoOnEachSubscriber
      //   9	10	1	localObject1	Object
      //   56	17	1	localObject2	Object
      //   79	39	1	?	T
      //   139	16	1	localObject3	Object
      //   36	33	2	localThrowable1	Throwable
      //   60	18	3	localCompositeException	io.reactivex.exceptions.CompositeException
      //   119	32	3	localThrowable2	Throwable
      // Exception table:
      //   from	to	target	type
      //   14	24	36	finally
      //   41	51	56	finally
      //   37	41	79	finally
      //   51	56	79	finally
      //   57	79	79	finally
      //   0	10	119	finally
      //   124	134	139	finally
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
  }
}
