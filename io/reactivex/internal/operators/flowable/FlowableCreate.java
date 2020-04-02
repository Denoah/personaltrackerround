package io.reactivex.internal.operators.flowable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Cancellable;
import io.reactivex.internal.disposables.CancellableDisposable;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableCreate<T>
  extends Flowable<T>
{
  final BackpressureStrategy backpressure;
  final FlowableOnSubscribe<T> source;
  
  public FlowableCreate(FlowableOnSubscribe<T> paramFlowableOnSubscribe, BackpressureStrategy paramBackpressureStrategy)
  {
    this.source = paramFlowableOnSubscribe;
    this.backpressure = paramBackpressureStrategy;
  }
  
  /* Error */
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    // Byte code:
    //   0: getstatic 54	io/reactivex/internal/operators/flowable/FlowableCreate$1:$SwitchMap$io$reactivex$BackpressureStrategy	[I
    //   3: aload_0
    //   4: getfield 45	io/reactivex/internal/operators/flowable/FlowableCreate:backpressure	Lio/reactivex/BackpressureStrategy;
    //   7: invokevirtual 60	io/reactivex/BackpressureStrategy:ordinal	()I
    //   10: iaload
    //   11: istore_2
    //   12: iload_2
    //   13: iconst_1
    //   14: if_icmpeq +69 -> 83
    //   17: iload_2
    //   18: iconst_2
    //   19: if_icmpeq +52 -> 71
    //   22: iload_2
    //   23: iconst_3
    //   24: if_icmpeq +35 -> 59
    //   27: iload_2
    //   28: iconst_4
    //   29: if_icmpeq +18 -> 47
    //   32: new 12	io/reactivex/internal/operators/flowable/FlowableCreate$BufferAsyncEmitter
    //   35: dup
    //   36: aload_1
    //   37: invokestatic 63	io/reactivex/internal/operators/flowable/FlowableCreate:bufferSize	()I
    //   40: invokespecial 66	io/reactivex/internal/operators/flowable/FlowableCreate$BufferAsyncEmitter:<init>	(Lorg/reactivestreams/Subscriber;I)V
    //   43: astore_3
    //   44: goto +48 -> 92
    //   47: new 21	io/reactivex/internal/operators/flowable/FlowableCreate$LatestAsyncEmitter
    //   50: dup
    //   51: aload_1
    //   52: invokespecial 68	io/reactivex/internal/operators/flowable/FlowableCreate$LatestAsyncEmitter:<init>	(Lorg/reactivestreams/Subscriber;)V
    //   55: astore_3
    //   56: goto +36 -> 92
    //   59: new 15	io/reactivex/internal/operators/flowable/FlowableCreate$DropAsyncEmitter
    //   62: dup
    //   63: aload_1
    //   64: invokespecial 69	io/reactivex/internal/operators/flowable/FlowableCreate$DropAsyncEmitter:<init>	(Lorg/reactivestreams/Subscriber;)V
    //   67: astore_3
    //   68: goto +24 -> 92
    //   71: new 18	io/reactivex/internal/operators/flowable/FlowableCreate$ErrorAsyncEmitter
    //   74: dup
    //   75: aload_1
    //   76: invokespecial 70	io/reactivex/internal/operators/flowable/FlowableCreate$ErrorAsyncEmitter:<init>	(Lorg/reactivestreams/Subscriber;)V
    //   79: astore_3
    //   80: goto +12 -> 92
    //   83: new 24	io/reactivex/internal/operators/flowable/FlowableCreate$MissingEmitter
    //   86: dup
    //   87: aload_1
    //   88: invokespecial 71	io/reactivex/internal/operators/flowable/FlowableCreate$MissingEmitter:<init>	(Lorg/reactivestreams/Subscriber;)V
    //   91: astore_3
    //   92: aload_1
    //   93: aload_3
    //   94: invokeinterface 77 2 0
    //   99: aload_0
    //   100: getfield 43	io/reactivex/internal/operators/flowable/FlowableCreate:source	Lio/reactivex/FlowableOnSubscribe;
    //   103: aload_3
    //   104: invokeinterface 83 2 0
    //   109: goto +13 -> 122
    //   112: astore_1
    //   113: aload_1
    //   114: invokestatic 89	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   117: aload_3
    //   118: aload_1
    //   119: invokevirtual 92	io/reactivex/internal/operators/flowable/FlowableCreate$BaseEmitter:onError	(Ljava/lang/Throwable;)V
    //   122: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	123	0	this	FlowableCreate
    //   0	123	1	paramSubscriber	Subscriber<? super T>
    //   11	19	2	i	int
    //   43	75	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   99	109	112	finally
  }
  
  static abstract class BaseEmitter<T>
    extends AtomicLong
    implements FlowableEmitter<T>, Subscription
  {
    private static final long serialVersionUID = 7326289992464377023L;
    final Subscriber<? super T> downstream;
    final SequentialDisposable serial;
    
    BaseEmitter(Subscriber<? super T> paramSubscriber)
    {
      this.downstream = paramSubscriber;
      this.serial = new SequentialDisposable();
    }
    
    public final void cancel()
    {
      this.serial.dispose();
      onUnsubscribed();
    }
    
    protected void complete()
    {
      if (isCancelled()) {
        return;
      }
      try
      {
        this.downstream.onComplete();
        return;
      }
      finally
      {
        this.serial.dispose();
      }
    }
    
    protected boolean error(Throwable paramThrowable)
    {
      Object localObject = paramThrowable;
      if (paramThrowable == null) {
        localObject = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
      }
      if (isCancelled()) {
        return false;
      }
      try
      {
        this.downstream.onError((Throwable)localObject);
        return true;
      }
      finally
      {
        this.serial.dispose();
      }
    }
    
    public final boolean isCancelled()
    {
      return this.serial.isDisposed();
    }
    
    public void onComplete()
    {
      complete();
    }
    
    public final void onError(Throwable paramThrowable)
    {
      if (!tryOnError(paramThrowable)) {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void onRequested() {}
    
    void onUnsubscribed() {}
    
    public final void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        BackpressureHelper.add(this, paramLong);
        onRequested();
      }
    }
    
    public final long requested()
    {
      return get();
    }
    
    public final FlowableEmitter<T> serialize()
    {
      return new FlowableCreate.SerializedEmitter(this);
    }
    
    public final void setCancellable(Cancellable paramCancellable)
    {
      setDisposable(new CancellableDisposable(paramCancellable));
    }
    
    public final void setDisposable(Disposable paramDisposable)
    {
      this.serial.update(paramDisposable);
    }
    
    public String toString()
    {
      return String.format("%s{%s}", new Object[] { getClass().getSimpleName(), super.toString() });
    }
    
    public boolean tryOnError(Throwable paramThrowable)
    {
      return error(paramThrowable);
    }
  }
  
  static final class BufferAsyncEmitter<T>
    extends FlowableCreate.BaseEmitter<T>
  {
    private static final long serialVersionUID = 2427151001689639875L;
    volatile boolean done;
    Throwable error;
    final SpscLinkedArrayQueue<T> queue;
    final AtomicInteger wip;
    
    BufferAsyncEmitter(Subscriber<? super T> paramSubscriber, int paramInt)
    {
      super();
      this.queue = new SpscLinkedArrayQueue(paramInt);
      this.wip = new AtomicInteger();
    }
    
    void drain()
    {
      if (this.wip.getAndIncrement() != 0) {
        return;
      }
      Subscriber localSubscriber = this.downstream;
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = this.queue;
      int i = 1;
      int j;
      do
      {
        long l1 = get();
        boolean bool1;
        boolean bool2;
        Object localObject;
        for (long l2 = 0L;; l2 += 1L)
        {
          bool1 = l2 < l1;
          if (!bool1) {
            break;
          }
          if (isCancelled())
          {
            localSpscLinkedArrayQueue.clear();
            return;
          }
          bool2 = this.done;
          localObject = localSpscLinkedArrayQueue.poll();
          if (localObject == null) {
            j = 1;
          } else {
            j = 0;
          }
          if ((bool2) && (j != 0))
          {
            localObject = this.error;
            if (localObject != null) {
              error((Throwable)localObject);
            } else {
              complete();
            }
            return;
          }
          if (j != 0) {
            break;
          }
          localSubscriber.onNext(localObject);
        }
        if (!bool1)
        {
          if (isCancelled())
          {
            localSpscLinkedArrayQueue.clear();
            return;
          }
          boolean bool3 = this.done;
          bool2 = localSpscLinkedArrayQueue.isEmpty();
          if ((bool3) && (bool2))
          {
            localObject = this.error;
            if (localObject != null) {
              error((Throwable)localObject);
            } else {
              complete();
            }
            return;
          }
        }
        if (l2 != 0L) {
          BackpressureHelper.produced(this, l2);
        }
        j = this.wip.addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    public void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      if ((!this.done) && (!isCancelled()))
      {
        if (paramT == null)
        {
          onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
          return;
        }
        this.queue.offer(paramT);
        drain();
      }
    }
    
    void onRequested()
    {
      drain();
    }
    
    void onUnsubscribed()
    {
      if (this.wip.getAndIncrement() == 0) {
        this.queue.clear();
      }
    }
    
    public boolean tryOnError(Throwable paramThrowable)
    {
      if ((!this.done) && (!isCancelled()))
      {
        Object localObject = paramThrowable;
        if (paramThrowable == null) {
          localObject = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        this.error = ((Throwable)localObject);
        this.done = true;
        drain();
        return true;
      }
      return false;
    }
  }
  
  static final class DropAsyncEmitter<T>
    extends FlowableCreate.NoOverflowBaseAsyncEmitter<T>
  {
    private static final long serialVersionUID = 8360058422307496563L;
    
    DropAsyncEmitter(Subscriber<? super T> paramSubscriber)
    {
      super();
    }
    
    void onOverflow() {}
  }
  
  static final class ErrorAsyncEmitter<T>
    extends FlowableCreate.NoOverflowBaseAsyncEmitter<T>
  {
    private static final long serialVersionUID = 338953216916120960L;
    
    ErrorAsyncEmitter(Subscriber<? super T> paramSubscriber)
    {
      super();
    }
    
    void onOverflow()
    {
      onError(new MissingBackpressureException("create: could not emit value due to lack of requests"));
    }
  }
  
  static final class LatestAsyncEmitter<T>
    extends FlowableCreate.BaseEmitter<T>
  {
    private static final long serialVersionUID = 4023437720691792495L;
    volatile boolean done;
    Throwable error;
    final AtomicReference<T> queue = new AtomicReference();
    final AtomicInteger wip = new AtomicInteger();
    
    LatestAsyncEmitter(Subscriber<? super T> paramSubscriber)
    {
      super();
    }
    
    void drain()
    {
      if (this.wip.getAndIncrement() != 0) {
        return;
      }
      Object localObject1 = this.downstream;
      AtomicReference localAtomicReference = this.queue;
      int i = 1;
      int k;
      do
      {
        long l1 = get();
        boolean bool1;
        int j;
        boolean bool2;
        for (long l2 = 0L;; l2 += 1L)
        {
          bool1 = l2 < l1;
          j = 0;
          if (!bool1) {
            break;
          }
          if (isCancelled())
          {
            localAtomicReference.lazySet(null);
            return;
          }
          bool2 = this.done;
          Object localObject2 = localAtomicReference.getAndSet(null);
          if (localObject2 == null) {
            k = 1;
          } else {
            k = 0;
          }
          if ((bool2) && (k != 0))
          {
            localObject1 = this.error;
            if (localObject1 != null) {
              error((Throwable)localObject1);
            } else {
              complete();
            }
            return;
          }
          if (k != 0) {
            break;
          }
          ((Subscriber)localObject1).onNext(localObject2);
        }
        if (!bool1)
        {
          if (isCancelled())
          {
            localAtomicReference.lazySet(null);
            return;
          }
          bool2 = this.done;
          k = j;
          if (localAtomicReference.get() == null) {
            k = 1;
          }
          if ((bool2) && (k != 0))
          {
            localObject1 = this.error;
            if (localObject1 != null) {
              error((Throwable)localObject1);
            } else {
              complete();
            }
            return;
          }
        }
        if (l2 != 0L) {
          BackpressureHelper.produced(this, l2);
        }
        k = this.wip.addAndGet(-i);
        i = k;
      } while (k != 0);
    }
    
    public void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public void onNext(T paramT)
    {
      if ((!this.done) && (!isCancelled()))
      {
        if (paramT == null)
        {
          onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
          return;
        }
        this.queue.set(paramT);
        drain();
      }
    }
    
    void onRequested()
    {
      drain();
    }
    
    void onUnsubscribed()
    {
      if (this.wip.getAndIncrement() == 0) {
        this.queue.lazySet(null);
      }
    }
    
    public boolean tryOnError(Throwable paramThrowable)
    {
      if ((!this.done) && (!isCancelled()))
      {
        if (paramThrowable == null) {
          onError(new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources."));
        }
        this.error = paramThrowable;
        this.done = true;
        drain();
        return true;
      }
      return false;
    }
  }
  
  static final class MissingEmitter<T>
    extends FlowableCreate.BaseEmitter<T>
  {
    private static final long serialVersionUID = 3776720187248809713L;
    
    MissingEmitter(Subscriber<? super T> paramSubscriber)
    {
      super();
    }
    
    public void onNext(T paramT)
    {
      if (isCancelled()) {
        return;
      }
      if (paramT != null)
      {
        this.downstream.onNext(paramT);
        long l;
        do
        {
          l = get();
        } while ((l != 0L) && (!compareAndSet(l, l - 1L)));
        return;
      }
      onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
    }
  }
  
  static abstract class NoOverflowBaseAsyncEmitter<T>
    extends FlowableCreate.BaseEmitter<T>
  {
    private static final long serialVersionUID = 4127754106204442833L;
    
    NoOverflowBaseAsyncEmitter(Subscriber<? super T> paramSubscriber)
    {
      super();
    }
    
    public final void onNext(T paramT)
    {
      if (isCancelled()) {
        return;
      }
      if (paramT == null)
      {
        onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
        return;
      }
      if (get() != 0L)
      {
        this.downstream.onNext(paramT);
        BackpressureHelper.produced(this, 1L);
      }
      else
      {
        onOverflow();
      }
    }
    
    abstract void onOverflow();
  }
  
  static final class SerializedEmitter<T>
    extends AtomicInteger
    implements FlowableEmitter<T>
  {
    private static final long serialVersionUID = 4883307006032401862L;
    volatile boolean done;
    final FlowableCreate.BaseEmitter<T> emitter;
    final AtomicThrowable error;
    final SimplePlainQueue<T> queue;
    
    SerializedEmitter(FlowableCreate.BaseEmitter<T> paramBaseEmitter)
    {
      this.emitter = paramBaseEmitter;
      this.error = new AtomicThrowable();
      this.queue = new SpscLinkedArrayQueue(16);
    }
    
    void drain()
    {
      if (getAndIncrement() == 0) {
        drainLoop();
      }
    }
    
    void drainLoop()
    {
      FlowableCreate.BaseEmitter localBaseEmitter = this.emitter;
      SimplePlainQueue localSimplePlainQueue = this.queue;
      AtomicThrowable localAtomicThrowable = this.error;
      int i = 1;
      for (;;)
      {
        if (localBaseEmitter.isCancelled())
        {
          localSimplePlainQueue.clear();
          return;
        }
        if (localAtomicThrowable.get() != null)
        {
          localSimplePlainQueue.clear();
          localBaseEmitter.onError(localAtomicThrowable.terminate());
          return;
        }
        boolean bool = this.done;
        Object localObject = localSimplePlainQueue.poll();
        int j;
        if (localObject == null) {
          j = 1;
        } else {
          j = 0;
        }
        if ((bool) && (j != 0))
        {
          localBaseEmitter.onComplete();
          return;
        }
        if (j != 0)
        {
          j = addAndGet(-i);
          i = j;
          if (j != 0) {}
        }
        else
        {
          localBaseEmitter.onNext(localObject);
        }
      }
    }
    
    public boolean isCancelled()
    {
      return this.emitter.isCancelled();
    }
    
    public void onComplete()
    {
      if ((!this.emitter.isCancelled()) && (!this.done))
      {
        this.done = true;
        drain();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!tryOnError(paramThrowable)) {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if ((!this.emitter.isCancelled()) && (!this.done))
      {
        if (paramT == null)
        {
          onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
          return;
        }
        if ((get() == 0) && (compareAndSet(0, 1)))
        {
          this.emitter.onNext(paramT);
          if (decrementAndGet() != 0) {
            break label95;
          }
        }
        synchronized (this.queue)
        {
          ???.offer(paramT);
          if (getAndIncrement() != 0) {
            return;
          }
          label95:
          drainLoop();
          return;
        }
      }
    }
    
    public long requested()
    {
      return this.emitter.requested();
    }
    
    public FlowableEmitter<T> serialize()
    {
      return this;
    }
    
    public void setCancellable(Cancellable paramCancellable)
    {
      this.emitter.setCancellable(paramCancellable);
    }
    
    public void setDisposable(Disposable paramDisposable)
    {
      this.emitter.setDisposable(paramDisposable);
    }
    
    public String toString()
    {
      return this.emitter.toString();
    }
    
    public boolean tryOnError(Throwable paramThrowable)
    {
      if ((!this.emitter.isCancelled()) && (!this.done))
      {
        Object localObject = paramThrowable;
        if (paramThrowable == null) {
          localObject = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        if (this.error.addThrowable((Throwable)localObject))
        {
          this.done = true;
          drain();
          return true;
        }
      }
      return false;
    }
  }
}
