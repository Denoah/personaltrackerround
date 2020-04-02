package io.reactivex.internal.operators.parallel;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.parallel.ParallelFlowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class ParallelReduceFull<T>
  extends Flowable<T>
{
  final BiFunction<T, T, T> reducer;
  final ParallelFlowable<? extends T> source;
  
  public ParallelReduceFull(ParallelFlowable<? extends T> paramParallelFlowable, BiFunction<T, T, T> paramBiFunction)
  {
    this.source = paramParallelFlowable;
    this.reducer = paramBiFunction;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    ParallelReduceFullMainSubscriber localParallelReduceFullMainSubscriber = new ParallelReduceFullMainSubscriber(paramSubscriber, this.source.parallelism(), this.reducer);
    paramSubscriber.onSubscribe(localParallelReduceFullMainSubscriber);
    this.source.subscribe(localParallelReduceFullMainSubscriber.subscribers);
  }
  
  static final class ParallelReduceFullInnerSubscriber<T>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -7954444275102466525L;
    boolean done;
    final ParallelReduceFull.ParallelReduceFullMainSubscriber<T> parent;
    final BiFunction<T, T, T> reducer;
    T value;
    
    ParallelReduceFullInnerSubscriber(ParallelReduceFull.ParallelReduceFullMainSubscriber<T> paramParallelReduceFullMainSubscriber, BiFunction<T, T, T> paramBiFunction)
    {
      this.parent = paramParallelReduceFullMainSubscriber;
      this.reducer = paramBiFunction;
    }
    
    void cancel()
    {
      SubscriptionHelper.cancel(this);
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        this.parent.innerComplete(this.value);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.parent.innerError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 46	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullInnerSubscriber:done	Z
      //   4: ifne +67 -> 71
      //   7: aload_0
      //   8: getfield 48	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullInnerSubscriber:value	Ljava/lang/Object;
      //   11: astore_2
      //   12: aload_2
      //   13: ifnonnull +11 -> 24
      //   16: aload_0
      //   17: aload_1
      //   18: putfield 48	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullInnerSubscriber:value	Ljava/lang/Object;
      //   21: goto +50 -> 71
      //   24: aload_0
      //   25: getfield 34	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullInnerSubscriber:reducer	Lio/reactivex/functions/BiFunction;
      //   28: aload_2
      //   29: aload_1
      //   30: invokeinterface 70 3 0
      //   35: ldc 72
      //   37: invokestatic 78	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   40: astore_1
      //   41: aload_0
      //   42: aload_1
      //   43: putfield 48	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullInnerSubscriber:value	Ljava/lang/Object;
      //   46: goto +25 -> 71
      //   49: astore_1
      //   50: aload_1
      //   51: invokestatic 83	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   54: aload_0
      //   55: invokevirtual 87	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullInnerSubscriber:get	()Ljava/lang/Object;
      //   58: checkcast 89	org/reactivestreams/Subscription
      //   61: invokeinterface 91 1 0
      //   66: aload_0
      //   67: aload_1
      //   68: invokevirtual 92	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullInnerSubscriber:onError	(Ljava/lang/Throwable;)V
      //   71: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	72	0	this	ParallelReduceFullInnerSubscriber
      //   0	72	1	paramT	T
      //   11	18	2	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   24	41	49	finally
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this, paramSubscription, Long.MAX_VALUE);
    }
  }
  
  static final class ParallelReduceFullMainSubscriber<T>
    extends DeferredScalarSubscription<T>
  {
    private static final long serialVersionUID = -5370107872170712765L;
    final AtomicReference<ParallelReduceFull.SlotPair<T>> current = new AtomicReference();
    final AtomicReference<Throwable> error = new AtomicReference();
    final BiFunction<T, T, T> reducer;
    final AtomicInteger remaining = new AtomicInteger();
    final ParallelReduceFull.ParallelReduceFullInnerSubscriber<T>[] subscribers;
    
    ParallelReduceFullMainSubscriber(Subscriber<? super T> paramSubscriber, int paramInt, BiFunction<T, T, T> paramBiFunction)
    {
      super();
      paramSubscriber = new ParallelReduceFull.ParallelReduceFullInnerSubscriber[paramInt];
      for (int i = 0; i < paramInt; i++) {
        paramSubscriber[i] = new ParallelReduceFull.ParallelReduceFullInnerSubscriber(this, paramBiFunction);
      }
      this.subscribers = paramSubscriber;
      this.reducer = paramBiFunction;
      this.remaining.lazySet(paramInt);
    }
    
    ParallelReduceFull.SlotPair<T> addValue(T paramT)
    {
      ParallelReduceFull.SlotPair localSlotPair2;
      int i;
      for (;;)
      {
        ParallelReduceFull.SlotPair localSlotPair1 = (ParallelReduceFull.SlotPair)this.current.get();
        localSlotPair2 = localSlotPair1;
        if (localSlotPair1 == null)
        {
          localSlotPair1 = new ParallelReduceFull.SlotPair();
          localSlotPair2 = localSlotPair1;
          if (!this.current.compareAndSet(null, localSlotPair1)) {}
        }
        else
        {
          i = localSlotPair2.tryAcquireSlot();
          if (i >= 0) {
            break;
          }
          this.current.compareAndSet(localSlotPair2, null);
        }
      }
      if (i == 0) {
        localSlotPair2.first = paramT;
      } else {
        localSlotPair2.second = paramT;
      }
      if (localSlotPair2.releaseSlot())
      {
        this.current.compareAndSet(localSlotPair2, null);
        return localSlotPair2;
      }
      return null;
    }
    
    public void cancel()
    {
      ParallelReduceFull.ParallelReduceFullInnerSubscriber[] arrayOfParallelReduceFullInnerSubscriber = this.subscribers;
      int i = arrayOfParallelReduceFullInnerSubscriber.length;
      for (int j = 0; j < i; j++) {
        arrayOfParallelReduceFullInnerSubscriber[j].cancel();
      }
    }
    
    /* Error */
    void innerComplete(T paramT)
    {
      // Byte code:
      //   0: aload_1
      //   1: ifnull +50 -> 51
      //   4: aload_0
      //   5: aload_1
      //   6: invokevirtual 96	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullMainSubscriber:addValue	(Ljava/lang/Object;)Lio/reactivex/internal/operators/parallel/ParallelReduceFull$SlotPair;
      //   9: astore_1
      //   10: aload_1
      //   11: ifnull +40 -> 51
      //   14: aload_0
      //   15: getfield 53	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullMainSubscriber:reducer	Lio/reactivex/functions/BiFunction;
      //   18: aload_1
      //   19: getfield 81	io/reactivex/internal/operators/parallel/ParallelReduceFull$SlotPair:first	Ljava/lang/Object;
      //   22: aload_1
      //   23: getfield 84	io/reactivex/internal/operators/parallel/ParallelReduceFull$SlotPair:second	Ljava/lang/Object;
      //   26: invokeinterface 102 3 0
      //   31: ldc 104
      //   33: invokestatic 110	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   36: astore_1
      //   37: goto -33 -> 4
      //   40: astore_1
      //   41: aload_1
      //   42: invokestatic 116	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   45: aload_0
      //   46: aload_1
      //   47: invokevirtual 119	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullMainSubscriber:innerError	(Ljava/lang/Throwable;)V
      //   50: return
      //   51: aload_0
      //   52: getfield 42	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullMainSubscriber:remaining	Ljava/util/concurrent/atomic/AtomicInteger;
      //   55: invokevirtual 122	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
      //   58: ifne +46 -> 104
      //   61: aload_0
      //   62: getfield 37	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullMainSubscriber:current	Ljava/util/concurrent/atomic/AtomicReference;
      //   65: invokevirtual 66	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   68: checkcast 68	io/reactivex/internal/operators/parallel/ParallelReduceFull$SlotPair
      //   71: astore_1
      //   72: aload_0
      //   73: getfield 37	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullMainSubscriber:current	Ljava/util/concurrent/atomic/AtomicReference;
      //   76: aconst_null
      //   77: invokevirtual 124	java/util/concurrent/atomic/AtomicReference:lazySet	(Ljava/lang/Object;)V
      //   80: aload_1
      //   81: ifnull +14 -> 95
      //   84: aload_0
      //   85: aload_1
      //   86: getfield 81	io/reactivex/internal/operators/parallel/ParallelReduceFull$SlotPair:first	Ljava/lang/Object;
      //   89: invokevirtual 127	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullMainSubscriber:complete	(Ljava/lang/Object;)V
      //   92: goto +12 -> 104
      //   95: aload_0
      //   96: getfield 131	io/reactivex/internal/operators/parallel/ParallelReduceFull$ParallelReduceFullMainSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   99: invokeinterface 136 1 0
      //   104: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	105	0	this	ParallelReduceFullMainSubscriber
      //   0	105	1	paramT	T
      // Exception table:
      //   from	to	target	type
      //   14	37	40	finally
    }
    
    void innerError(Throwable paramThrowable)
    {
      if (this.error.compareAndSet(null, paramThrowable))
      {
        cancel();
        this.downstream.onError(paramThrowable);
      }
      else if (paramThrowable != this.error.get())
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
  }
  
  static final class SlotPair<T>
    extends AtomicInteger
  {
    private static final long serialVersionUID = 473971317683868662L;
    T first;
    final AtomicInteger releaseIndex = new AtomicInteger();
    T second;
    
    SlotPair() {}
    
    boolean releaseSlot()
    {
      boolean bool;
      if (this.releaseIndex.incrementAndGet() == 2) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    int tryAcquireSlot()
    {
      int i;
      do
      {
        i = get();
        if (i >= 2) {
          return -1;
        }
      } while (!compareAndSet(i, i + 1));
      return i;
    }
  }
}
