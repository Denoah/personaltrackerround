package io.reactivex.internal.operators.flowable;

import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableGenerate<T, S>
  extends Flowable<T>
{
  final Consumer<? super S> disposeState;
  final BiFunction<S, Emitter<T>, S> generator;
  final Callable<S> stateSupplier;
  
  public FlowableGenerate(Callable<S> paramCallable, BiFunction<S, Emitter<T>, S> paramBiFunction, Consumer<? super S> paramConsumer)
  {
    this.stateSupplier = paramCallable;
    this.generator = paramBiFunction;
    this.disposeState = paramConsumer;
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    try
    {
      Object localObject = this.stateSupplier.call();
      paramSubscriber.onSubscribe(new GeneratorSubscription(paramSubscriber, this.generator, this.disposeState, localObject));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptySubscription.error(localThrowable, paramSubscriber);
    }
  }
  
  static final class GeneratorSubscription<T, S>
    extends AtomicLong
    implements Emitter<T>, Subscription
  {
    private static final long serialVersionUID = 7565982551505011832L;
    volatile boolean cancelled;
    final Consumer<? super S> disposeState;
    final Subscriber<? super T> downstream;
    final BiFunction<S, ? super Emitter<T>, S> generator;
    boolean hasNext;
    S state;
    boolean terminate;
    
    GeneratorSubscription(Subscriber<? super T> paramSubscriber, BiFunction<S, ? super Emitter<T>, S> paramBiFunction, Consumer<? super S> paramConsumer, S paramS)
    {
      this.downstream = paramSubscriber;
      this.generator = paramBiFunction;
      this.disposeState = paramConsumer;
      this.state = paramS;
    }
    
    /* Error */
    private void dispose(S paramS)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 43	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:disposeState	Lio/reactivex/functions/Consumer;
      //   4: aload_1
      //   5: invokeinterface 55 2 0
      //   10: goto +12 -> 22
      //   13: astore_1
      //   14: aload_1
      //   15: invokestatic 61	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   18: aload_1
      //   19: invokestatic 66	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   22: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	23	0	this	GeneratorSubscription
      //   0	23	1	paramS	S
      // Exception table:
      //   from	to	target	type
      //   0	10	13	finally
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        if (BackpressureHelper.add(this, 1L) == 0L)
        {
          Object localObject = this.state;
          this.state = null;
          dispose(localObject);
        }
      }
    }
    
    public void onComplete()
    {
      if (!this.terminate)
      {
        this.terminate = true;
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.terminate)
      {
        RxJavaPlugins.onError(paramThrowable);
      }
      else
      {
        Object localObject = paramThrowable;
        if (paramThrowable == null) {
          localObject = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        this.terminate = true;
        this.downstream.onError((Throwable)localObject);
      }
    }
    
    public void onNext(T paramT)
    {
      if (!this.terminate) {
        if (this.hasNext)
        {
          onError(new IllegalStateException("onNext already called in this generate turn"));
        }
        else if (paramT == null)
        {
          onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
        }
        else
        {
          this.hasNext = true;
          this.downstream.onNext(paramT);
        }
      }
    }
    
    /* Error */
    public void request(long paramLong)
    {
      // Byte code:
      //   0: lload_1
      //   1: invokestatic 115	io/reactivex/internal/subscriptions/SubscriptionHelper:validate	(J)Z
      //   4: ifne +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: lload_1
      //   10: invokestatic 76	io/reactivex/internal/util/BackpressureHelper:add	(Ljava/util/concurrent/atomic/AtomicLong;J)J
      //   13: lconst_0
      //   14: lcmp
      //   15: ifeq +4 -> 19
      //   18: return
      //   19: aload_0
      //   20: getfield 45	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:state	Ljava/lang/Object;
      //   23: astore_3
      //   24: aload_0
      //   25: getfield 41	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:generator	Lio/reactivex/functions/BiFunction;
      //   28: astore 4
      //   30: lconst_0
      //   31: lstore 5
      //   33: aload_3
      //   34: astore 7
      //   36: lload 5
      //   38: lload_1
      //   39: lcmp
      //   40: ifeq +97 -> 137
      //   43: aload_0
      //   44: getfield 70	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:cancelled	Z
      //   47: ifeq +15 -> 62
      //   50: aload_0
      //   51: aconst_null
      //   52: putfield 45	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:state	Ljava/lang/Object;
      //   55: aload_0
      //   56: aload 7
      //   58: invokespecial 78	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:dispose	(Ljava/lang/Object;)V
      //   61: return
      //   62: aload_0
      //   63: iconst_0
      //   64: putfield 96	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:hasNext	Z
      //   67: aload 4
      //   69: aload 7
      //   71: aload_0
      //   72: invokeinterface 121 3 0
      //   77: astore_3
      //   78: aload_0
      //   79: getfield 81	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:terminate	Z
      //   82: ifeq +19 -> 101
      //   85: aload_0
      //   86: iconst_1
      //   87: putfield 70	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:cancelled	Z
      //   90: aload_0
      //   91: aconst_null
      //   92: putfield 45	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:state	Ljava/lang/Object;
      //   95: aload_0
      //   96: aload_3
      //   97: invokespecial 78	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:dispose	(Ljava/lang/Object;)V
      //   100: return
      //   101: lload 5
      //   103: lconst_1
      //   104: ladd
      //   105: lstore 5
      //   107: goto -74 -> 33
      //   110: astore_3
      //   111: aload_3
      //   112: invokestatic 61	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   115: aload_0
      //   116: iconst_1
      //   117: putfield 70	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:cancelled	Z
      //   120: aload_0
      //   121: aconst_null
      //   122: putfield 45	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:state	Ljava/lang/Object;
      //   125: aload_0
      //   126: aload_3
      //   127: invokevirtual 102	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:onError	(Ljava/lang/Throwable;)V
      //   130: aload_0
      //   131: aload 7
      //   133: invokespecial 78	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:dispose	(Ljava/lang/Object;)V
      //   136: return
      //   137: aload_0
      //   138: invokevirtual 125	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:get	()J
      //   141: lstore 8
      //   143: aload 7
      //   145: astore_3
      //   146: lload 8
      //   148: lstore_1
      //   149: lload 5
      //   151: lload 8
      //   153: lcmp
      //   154: ifne -121 -> 33
      //   157: aload_0
      //   158: aload 7
      //   160: putfield 45	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:state	Ljava/lang/Object;
      //   163: aload_0
      //   164: lload 5
      //   166: lneg
      //   167: invokevirtual 129	io/reactivex/internal/operators/flowable/FlowableGenerate$GeneratorSubscription:addAndGet	(J)J
      //   170: lstore 5
      //   172: aload 7
      //   174: astore_3
      //   175: lload 5
      //   177: lstore_1
      //   178: lload 5
      //   180: lconst_0
      //   181: lcmp
      //   182: ifne -152 -> 30
      //   185: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	186	0	this	GeneratorSubscription
      //   0	186	1	paramLong	long
      //   23	74	3	localObject1	Object
      //   110	17	3	localThrowable	Throwable
      //   145	30	3	localObject2	Object
      //   28	40	4	localBiFunction	BiFunction
      //   31	148	5	l1	long
      //   34	139	7	localObject3	Object
      //   141	11	8	l2	long
      // Exception table:
      //   from	to	target	type
      //   67	78	110	finally
    }
  }
}
