package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Publisher;

public final class FlowableScalarXMap
{
  private FlowableScalarXMap()
  {
    throw new IllegalStateException("No instances!");
  }
  
  public static <T, U> Flowable<U> scalarXMap(T paramT, Function<? super T, ? extends Publisher<? extends U>> paramFunction)
  {
    return RxJavaPlugins.onAssembly(new ScalarXMapFlowable(paramT, paramFunction));
  }
  
  /* Error */
  public static <T, R> boolean tryScalarXMapSubscribe(Publisher<T> paramPublisher, org.reactivestreams.Subscriber<? super R> paramSubscriber, Function<? super T, ? extends Publisher<? extends R>> paramFunction)
  {
    // Byte code:
    //   0: aload_0
    //   1: instanceof 36
    //   4: ifeq +129 -> 133
    //   7: aload_0
    //   8: checkcast 36	java/util/concurrent/Callable
    //   11: invokeinterface 40 1 0
    //   16: astore_0
    //   17: aload_0
    //   18: ifnonnull +9 -> 27
    //   21: aload_1
    //   22: invokestatic 46	io/reactivex/internal/subscriptions/EmptySubscription:complete	(Lorg/reactivestreams/Subscriber;)V
    //   25: iconst_1
    //   26: ireturn
    //   27: aload_2
    //   28: aload_0
    //   29: invokeinterface 52 2 0
    //   34: ldc 54
    //   36: invokestatic 60	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   39: checkcast 62	org/reactivestreams/Publisher
    //   42: astore_0
    //   43: aload_0
    //   44: instanceof 36
    //   47: ifeq +53 -> 100
    //   50: aload_0
    //   51: checkcast 36	java/util/concurrent/Callable
    //   54: invokeinterface 40 1 0
    //   59: astore_0
    //   60: aload_0
    //   61: ifnonnull +9 -> 70
    //   64: aload_1
    //   65: invokestatic 46	io/reactivex/internal/subscriptions/EmptySubscription:complete	(Lorg/reactivestreams/Subscriber;)V
    //   68: iconst_1
    //   69: ireturn
    //   70: aload_1
    //   71: new 64	io/reactivex/internal/subscriptions/ScalarSubscription
    //   74: dup
    //   75: aload_1
    //   76: aload_0
    //   77: invokespecial 67	io/reactivex/internal/subscriptions/ScalarSubscription:<init>	(Lorg/reactivestreams/Subscriber;Ljava/lang/Object;)V
    //   80: invokeinterface 73 2 0
    //   85: goto +22 -> 107
    //   88: astore_0
    //   89: aload_0
    //   90: invokestatic 79	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   93: aload_0
    //   94: aload_1
    //   95: invokestatic 83	io/reactivex/internal/subscriptions/EmptySubscription:error	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;)V
    //   98: iconst_1
    //   99: ireturn
    //   100: aload_0
    //   101: aload_1
    //   102: invokeinterface 86 2 0
    //   107: iconst_1
    //   108: ireturn
    //   109: astore_0
    //   110: aload_0
    //   111: invokestatic 79	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   114: aload_0
    //   115: aload_1
    //   116: invokestatic 83	io/reactivex/internal/subscriptions/EmptySubscription:error	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;)V
    //   119: iconst_1
    //   120: ireturn
    //   121: astore_0
    //   122: aload_0
    //   123: invokestatic 79	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   126: aload_0
    //   127: aload_1
    //   128: invokestatic 83	io/reactivex/internal/subscriptions/EmptySubscription:error	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;)V
    //   131: iconst_1
    //   132: ireturn
    //   133: iconst_0
    //   134: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	135	0	paramPublisher	Publisher<T>
    //   0	135	1	paramSubscriber	org.reactivestreams.Subscriber<? super R>
    //   0	135	2	paramFunction	Function<? super T, ? extends Publisher<? extends R>>
    // Exception table:
    //   from	to	target	type
    //   50	60	88	finally
    //   27	43	109	finally
    //   7	17	121	finally
  }
  
  static final class ScalarXMapFlowable<T, R>
    extends Flowable<R>
  {
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final T value;
    
    ScalarXMapFlowable(T paramT, Function<? super T, ? extends Publisher<? extends R>> paramFunction)
    {
      this.value = paramT;
      this.mapper = paramFunction;
    }
    
    /* Error */
    public void subscribeActual(org.reactivestreams.Subscriber<? super R> paramSubscriber)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 23	io/reactivex/internal/operators/flowable/FlowableScalarXMap$ScalarXMapFlowable:mapper	Lio/reactivex/functions/Function;
      //   4: aload_0
      //   5: getfield 21	io/reactivex/internal/operators/flowable/FlowableScalarXMap$ScalarXMapFlowable:value	Ljava/lang/Object;
      //   8: invokeinterface 34 2 0
      //   13: ldc 36
      //   15: invokestatic 42	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   18: checkcast 44	org/reactivestreams/Publisher
      //   21: astore_2
      //   22: aload_2
      //   23: instanceof 46
      //   26: ifeq +51 -> 77
      //   29: aload_2
      //   30: checkcast 46	java/util/concurrent/Callable
      //   33: invokeinterface 50 1 0
      //   38: astore_2
      //   39: aload_2
      //   40: ifnonnull +8 -> 48
      //   43: aload_1
      //   44: invokestatic 55	io/reactivex/internal/subscriptions/EmptySubscription:complete	(Lorg/reactivestreams/Subscriber;)V
      //   47: return
      //   48: aload_1
      //   49: new 57	io/reactivex/internal/subscriptions/ScalarSubscription
      //   52: dup
      //   53: aload_1
      //   54: aload_2
      //   55: invokespecial 60	io/reactivex/internal/subscriptions/ScalarSubscription:<init>	(Lorg/reactivestreams/Subscriber;Ljava/lang/Object;)V
      //   58: invokeinterface 66 2 0
      //   63: goto +21 -> 84
      //   66: astore_2
      //   67: aload_2
      //   68: invokestatic 72	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   71: aload_2
      //   72: aload_1
      //   73: invokestatic 76	io/reactivex/internal/subscriptions/EmptySubscription:error	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;)V
      //   76: return
      //   77: aload_2
      //   78: aload_1
      //   79: invokeinterface 79 2 0
      //   84: return
      //   85: astore_2
      //   86: aload_2
      //   87: aload_1
      //   88: invokestatic 76	io/reactivex/internal/subscriptions/EmptySubscription:error	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;)V
      //   91: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	92	0	this	ScalarXMapFlowable
      //   0	92	1	paramSubscriber	org.reactivestreams.Subscriber<? super R>
      //   21	34	2	localObject	Object
      //   66	12	2	localThrowable1	Throwable
      //   85	2	2	localThrowable2	Throwable
      // Exception table:
      //   from	to	target	type
      //   29	39	66	finally
      //   0	22	85	finally
    }
  }
}
