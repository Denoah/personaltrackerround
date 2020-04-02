package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableOperator;

public final class FlowableLift<R, T>
  extends AbstractFlowableWithUpstream<T, R>
{
  final FlowableOperator<? extends R, ? super T> operator;
  
  public FlowableLift(Flowable<T> paramFlowable, FlowableOperator<? extends R, ? super T> paramFlowableOperator)
  {
    super(paramFlowable);
    this.operator = paramFlowableOperator;
  }
  
  /* Error */
  public void subscribeActual(org.reactivestreams.Subscriber<? super R> paramSubscriber)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 15	io/reactivex/internal/operators/flowable/FlowableLift:operator	Lio/reactivex/FlowableOperator;
    //   4: aload_1
    //   5: invokeinterface 28 2 0
    //   10: astore_1
    //   11: aload_1
    //   12: ifnull +12 -> 24
    //   15: aload_0
    //   16: getfield 32	io/reactivex/internal/operators/flowable/FlowableLift:source	Lio/reactivex/Flowable;
    //   19: aload_1
    //   20: invokevirtual 37	io/reactivex/Flowable:subscribe	(Lorg/reactivestreams/Subscriber;)V
    //   23: return
    //   24: new 22	java/lang/NullPointerException
    //   27: astore_1
    //   28: new 39	java/lang/StringBuilder
    //   31: astore_2
    //   32: aload_2
    //   33: invokespecial 42	java/lang/StringBuilder:<init>	()V
    //   36: aload_2
    //   37: ldc 44
    //   39: invokevirtual 48	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: pop
    //   43: aload_2
    //   44: aload_0
    //   45: getfield 15	io/reactivex/internal/operators/flowable/FlowableLift:operator	Lio/reactivex/FlowableOperator;
    //   48: invokevirtual 51	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   51: pop
    //   52: aload_2
    //   53: ldc 53
    //   55: invokevirtual 48	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: pop
    //   59: aload_1
    //   60: aload_2
    //   61: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   64: invokespecial 60	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   67: aload_1
    //   68: athrow
    //   69: astore_1
    //   70: aload_1
    //   71: invokestatic 66	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   74: aload_1
    //   75: invokestatic 71	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   78: new 22	java/lang/NullPointerException
    //   81: dup
    //   82: ldc 73
    //   84: invokespecial 60	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   87: astore_2
    //   88: aload_2
    //   89: aload_1
    //   90: invokevirtual 77	java/lang/NullPointerException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   93: pop
    //   94: aload_2
    //   95: athrow
    //   96: astore_1
    //   97: aload_1
    //   98: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	99	0	this	FlowableLift
    //   0	99	1	paramSubscriber	org.reactivestreams.Subscriber<? super R>
    //   31	64	2	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	11	69	finally
    //   15	23	69	finally
    //   24	69	69	finally
    //   0	11	96	java/lang/NullPointerException
    //   15	23	96	java/lang/NullPointerException
    //   24	69	96	java/lang/NullPointerException
  }
}
