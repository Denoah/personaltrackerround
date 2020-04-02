package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class CompletableConcat
  extends Completable
{
  final int prefetch;
  final Publisher<? extends CompletableSource> sources;
  
  public CompletableConcat(Publisher<? extends CompletableSource> paramPublisher, int paramInt)
  {
    this.sources = paramPublisher;
    this.prefetch = paramInt;
  }
  
  public void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.sources.subscribe(new CompletableConcatSubscriber(paramCompletableObserver, this.prefetch));
  }
  
  static final class CompletableConcatSubscriber
    extends AtomicInteger
    implements FlowableSubscriber<CompletableSource>, Disposable
  {
    private static final long serialVersionUID = 9032184911934499404L;
    volatile boolean active;
    int consumed;
    volatile boolean done;
    final CompletableObserver downstream;
    final ConcatInnerObserver inner;
    final int limit;
    final AtomicBoolean once;
    final int prefetch;
    SimpleQueue<CompletableSource> queue;
    int sourceFused;
    Subscription upstream;
    
    CompletableConcatSubscriber(CompletableObserver paramCompletableObserver, int paramInt)
    {
      this.downstream = paramCompletableObserver;
      this.prefetch = paramInt;
      this.inner = new ConcatInnerObserver(this);
      this.once = new AtomicBoolean();
      this.limit = (paramInt - (paramInt >> 2));
    }
    
    public void dispose()
    {
      this.upstream.cancel();
      DisposableHelper.dispose(this.inner);
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 78	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: invokevirtual 82	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:isDisposed	()Z
      //   12: ifeq +4 -> 16
      //   15: return
      //   16: aload_0
      //   17: getfield 84	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:active	Z
      //   20: ifne +99 -> 119
      //   23: aload_0
      //   24: getfield 86	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:done	Z
      //   27: istore_1
      //   28: aload_0
      //   29: getfield 88	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   32: invokeinterface 94 1 0
      //   37: checkcast 96	io/reactivex/CompletableSource
      //   40: astore_2
      //   41: aload_2
      //   42: ifnonnull +8 -> 50
      //   45: iconst_1
      //   46: istore_3
      //   47: goto +5 -> 52
      //   50: iconst_0
      //   51: istore_3
      //   52: iload_1
      //   53: ifeq +29 -> 82
      //   56: iload_3
      //   57: ifeq +25 -> 82
      //   60: aload_0
      //   61: getfield 57	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:once	Ljava/util/concurrent/atomic/AtomicBoolean;
      //   64: iconst_0
      //   65: iconst_1
      //   66: invokevirtual 100	java/util/concurrent/atomic/AtomicBoolean:compareAndSet	(ZZ)Z
      //   69: ifeq +12 -> 81
      //   72: aload_0
      //   73: getfield 45	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:downstream	Lio/reactivex/CompletableObserver;
      //   76: invokeinterface 105 1 0
      //   81: return
      //   82: iload_3
      //   83: ifne +36 -> 119
      //   86: aload_0
      //   87: iconst_1
      //   88: putfield 84	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:active	Z
      //   91: aload_2
      //   92: aload_0
      //   93: getfield 52	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:inner	Lio/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber$ConcatInnerObserver;
      //   96: invokeinterface 109 2 0
      //   101: aload_0
      //   102: invokevirtual 112	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:request	()V
      //   105: goto +14 -> 119
      //   108: astore_2
      //   109: aload_2
      //   110: invokestatic 118	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   113: aload_0
      //   114: aload_2
      //   115: invokevirtual 121	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:innerError	(Ljava/lang/Throwable;)V
      //   118: return
      //   119: aload_0
      //   120: invokevirtual 124	io/reactivex/internal/operators/completable/CompletableConcat$CompletableConcatSubscriber:decrementAndGet	()I
      //   123: ifne -115 -> 8
      //   126: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	127	0	this	CompletableConcatSubscriber
      //   27	26	1	bool	boolean
      //   40	52	2	localCompletableSource	CompletableSource
      //   108	7	2	localThrowable	Throwable
      //   46	37	3	i	int
      // Exception table:
      //   from	to	target	type
      //   28	41	108	finally
    }
    
    void innerComplete()
    {
      this.active = false;
      drain();
    }
    
    void innerError(Throwable paramThrowable)
    {
      if (this.once.compareAndSet(false, true))
      {
        this.upstream.cancel();
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)this.inner.get());
    }
    
    public void onComplete()
    {
      this.done = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.once.compareAndSet(false, true))
      {
        DisposableHelper.dispose(this.inner);
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(CompletableSource paramCompletableSource)
    {
      if ((this.sourceFused == 0) && (!this.queue.offer(paramCompletableSource)))
      {
        onError(new MissingBackpressureException());
        return;
      }
      drain();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        int i = this.prefetch;
        long l;
        if (i == Integer.MAX_VALUE) {
          l = Long.MAX_VALUE;
        } else {
          l = i;
        }
        if ((paramSubscription instanceof QueueSubscription))
        {
          QueueSubscription localQueueSubscription = (QueueSubscription)paramSubscription;
          i = localQueueSubscription.requestFusion(3);
          if (i == 1)
          {
            this.sourceFused = i;
            this.queue = localQueueSubscription;
            this.done = true;
            this.downstream.onSubscribe(this);
            drain();
            return;
          }
          if (i == 2)
          {
            this.sourceFused = i;
            this.queue = localQueueSubscription;
            this.downstream.onSubscribe(this);
            paramSubscription.request(l);
            return;
          }
        }
        if (this.prefetch == Integer.MAX_VALUE) {
          this.queue = new SpscLinkedArrayQueue(Flowable.bufferSize());
        } else {
          this.queue = new SpscArrayQueue(this.prefetch);
        }
        this.downstream.onSubscribe(this);
        paramSubscription.request(l);
      }
    }
    
    void request()
    {
      if (this.sourceFused != 1)
      {
        int i = this.consumed + 1;
        if (i == this.limit)
        {
          this.consumed = 0;
          this.upstream.request(i);
        }
        else
        {
          this.consumed = i;
        }
      }
    }
    
    static final class ConcatInnerObserver
      extends AtomicReference<Disposable>
      implements CompletableObserver
    {
      private static final long serialVersionUID = -5454794857847146511L;
      final CompletableConcat.CompletableConcatSubscriber parent;
      
      ConcatInnerObserver(CompletableConcat.CompletableConcatSubscriber paramCompletableConcatSubscriber)
      {
        this.parent = paramCompletableConcatSubscriber;
      }
      
      public void onComplete()
      {
        this.parent.innerComplete();
      }
      
      public void onError(Throwable paramThrowable)
      {
        this.parent.innerError(paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.replace(this, paramDisposable);
      }
    }
  }
}
