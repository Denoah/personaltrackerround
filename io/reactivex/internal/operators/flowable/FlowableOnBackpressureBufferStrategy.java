package io.reactivex.internal.operators.flowable;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Action;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureBufferStrategy<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final long bufferSize;
  final Action onOverflow;
  final BackpressureOverflowStrategy strategy;
  
  public FlowableOnBackpressureBufferStrategy(Flowable<T> paramFlowable, long paramLong, Action paramAction, BackpressureOverflowStrategy paramBackpressureOverflowStrategy)
  {
    super(paramFlowable);
    this.bufferSize = paramLong;
    this.onOverflow = paramAction;
    this.strategy = paramBackpressureOverflowStrategy;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new OnBackpressureBufferStrategySubscriber(paramSubscriber, this.onOverflow, this.strategy, this.bufferSize));
  }
  
  static final class OnBackpressureBufferStrategySubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = 3240706908776709697L;
    final long bufferSize;
    volatile boolean cancelled;
    final Deque<T> deque;
    volatile boolean done;
    final Subscriber<? super T> downstream;
    Throwable error;
    final Action onOverflow;
    final AtomicLong requested;
    final BackpressureOverflowStrategy strategy;
    Subscription upstream;
    
    OnBackpressureBufferStrategySubscriber(Subscriber<? super T> paramSubscriber, Action paramAction, BackpressureOverflowStrategy paramBackpressureOverflowStrategy, long paramLong)
    {
      this.downstream = paramSubscriber;
      this.onOverflow = paramAction;
      this.strategy = paramBackpressureOverflowStrategy;
      this.bufferSize = paramLong;
      this.requested = new AtomicLong();
      this.deque = new ArrayDeque();
    }
    
    public void cancel()
    {
      this.cancelled = true;
      this.upstream.cancel();
      if (getAndIncrement() == 0) {
        clear(this.deque);
      }
    }
    
    void clear(Deque<T> paramDeque)
    {
      try
      {
        paramDeque.clear();
        return;
      }
      finally {}
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      Deque localDeque = this.deque;
      Subscriber localSubscriber = this.downstream;
      int i = 1;
      int j;
      do
      {
        long l1 = this.requested.get();
        long l2 = 0L;
        boolean bool1;
        boolean bool2;
        Object localObject3;
        for (;;)
        {
          bool1 = l2 < l1;
          if (bool1)
          {
            if (this.cancelled)
            {
              clear(localDeque);
              return;
            }
            bool2 = this.done;
            try
            {
              localObject3 = localDeque.poll();
              if (localObject3 == null) {
                j = 1;
              } else {
                j = 0;
              }
              if (bool2)
              {
                Throwable localThrowable = this.error;
                if (localThrowable != null)
                {
                  clear(localDeque);
                  localSubscriber.onError(localThrowable);
                  return;
                }
                if (j != 0)
                {
                  localSubscriber.onComplete();
                  return;
                }
              }
              if (j == 0)
              {
                localSubscriber.onNext(localObject3);
                l2 += 1L;
              }
            }
            finally {}
          }
        }
        if (!bool1)
        {
          if (this.cancelled)
          {
            clear(localDeque);
            return;
          }
          bool2 = this.done;
          try
          {
            boolean bool3 = localDeque.isEmpty();
            if (bool2)
            {
              localObject3 = this.error;
              if (localObject3 != null)
              {
                clear(localDeque);
                localObject1.onError((Throwable)localObject3);
                return;
              }
              if (bool3)
              {
                localObject1.onComplete();
                return;
              }
            }
          }
          finally {}
        }
        if (l2 != 0L) {
          BackpressureHelper.produced(this.requested, l2);
        }
        j = addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    public void onComplete()
    {
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
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 89	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 59	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber:deque	Ljava/util/Deque;
      //   12: astore_2
      //   13: aload_2
      //   14: monitorenter
      //   15: aload_2
      //   16: invokeinterface 130 1 0
      //   21: i2l
      //   22: lstore_3
      //   23: aload_0
      //   24: getfield 49	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber:bufferSize	J
      //   27: lstore 5
      //   29: iconst_0
      //   30: istore 7
      //   32: iconst_1
      //   33: istore 8
      //   35: lload_3
      //   36: lload 5
      //   38: lcmp
      //   39: ifne +73 -> 112
      //   42: getstatic 136	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$1:$SwitchMap$io$reactivex$BackpressureOverflowStrategy	[I
      //   45: aload_0
      //   46: getfield 47	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber:strategy	Lio/reactivex/BackpressureOverflowStrategy;
      //   49: invokevirtual 141	io/reactivex/BackpressureOverflowStrategy:ordinal	()I
      //   52: iaload
      //   53: istore 9
      //   55: iload 9
      //   57: iconst_1
      //   58: if_icmpeq +30 -> 88
      //   61: iload 9
      //   63: iconst_2
      //   64: if_icmpeq +6 -> 70
      //   67: goto +56 -> 123
      //   70: aload_2
      //   71: invokeinterface 93 1 0
      //   76: pop
      //   77: aload_2
      //   78: aload_1
      //   79: invokeinterface 145 2 0
      //   84: pop
      //   85: goto +18 -> 103
      //   88: aload_2
      //   89: invokeinterface 148 1 0
      //   94: pop
      //   95: aload_2
      //   96: aload_1
      //   97: invokeinterface 145 2 0
      //   102: pop
      //   103: iconst_0
      //   104: istore 8
      //   106: iconst_1
      //   107: istore 7
      //   109: goto +14 -> 123
      //   112: aload_2
      //   113: aload_1
      //   114: invokeinterface 145 2 0
      //   119: pop
      //   120: iconst_0
      //   121: istore 8
      //   123: aload_2
      //   124: monitorexit
      //   125: iload 7
      //   127: ifeq +43 -> 170
      //   130: aload_0
      //   131: getfield 45	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber:onOverflow	Lio/reactivex/functions/Action;
      //   134: astore_1
      //   135: aload_1
      //   136: ifnull +66 -> 202
      //   139: aload_1
      //   140: invokeinterface 153 1 0
      //   145: goto +57 -> 202
      //   148: astore_1
      //   149: aload_1
      //   150: invokestatic 158	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   153: aload_0
      //   154: getfield 67	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   157: invokeinterface 69 1 0
      //   162: aload_0
      //   163: aload_1
      //   164: invokevirtual 159	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber:onError	(Ljava/lang/Throwable;)V
      //   167: goto +35 -> 202
      //   170: iload 8
      //   172: ifeq +26 -> 198
      //   175: aload_0
      //   176: getfield 67	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   179: invokeinterface 69 1 0
      //   184: aload_0
      //   185: new 161	io/reactivex/exceptions/MissingBackpressureException
      //   188: dup
      //   189: invokespecial 162	io/reactivex/exceptions/MissingBackpressureException:<init>	()V
      //   192: invokevirtual 159	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber:onError	(Ljava/lang/Throwable;)V
      //   195: goto +7 -> 202
      //   198: aload_0
      //   199: invokevirtual 124	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBufferStrategy$OnBackpressureBufferStrategySubscriber:drain	()V
      //   202: return
      //   203: astore_1
      //   204: aload_2
      //   205: monitorexit
      //   206: aload_1
      //   207: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	208	0	this	OnBackpressureBufferStrategySubscriber
      //   0	208	1	paramT	T
      //   12	193	2	localDeque	Deque
      //   22	14	3	l1	long
      //   27	10	5	l2	long
      //   30	96	7	i	int
      //   33	138	8	j	int
      //   53	12	9	k	int
      // Exception table:
      //   from	to	target	type
      //   139	145	148	finally
      //   15	29	203	finally
      //   42	55	203	finally
      //   70	85	203	finally
      //   88	103	203	finally
      //   112	120	203	finally
      //   123	125	203	finally
      //   204	206	203	finally
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
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
  }
}
