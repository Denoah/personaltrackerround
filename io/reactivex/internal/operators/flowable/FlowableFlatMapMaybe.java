package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
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

public final class FlowableFlatMapMaybe<T, R>
  extends AbstractFlowableWithUpstream<T, R>
{
  final boolean delayErrors;
  final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
  final int maxConcurrency;
  
  public FlowableFlatMapMaybe(Flowable<T> paramFlowable, Function<? super T, ? extends MaybeSource<? extends R>> paramFunction, boolean paramBoolean, int paramInt)
  {
    super(paramFlowable);
    this.mapper = paramFunction;
    this.delayErrors = paramBoolean;
    this.maxConcurrency = paramInt;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    this.source.subscribe(new FlatMapMaybeSubscriber(paramSubscriber, this.mapper, this.delayErrors, this.maxConcurrency));
  }
  
  static final class FlatMapMaybeSubscriber<T, R>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = 8600231336733376951L;
    final AtomicInteger active;
    volatile boolean cancelled;
    final boolean delayErrors;
    final Subscriber<? super R> downstream;
    final AtomicThrowable errors;
    final Function<? super T, ? extends MaybeSource<? extends R>> mapper;
    final int maxConcurrency;
    final AtomicReference<SpscLinkedArrayQueue<R>> queue;
    final AtomicLong requested;
    final CompositeDisposable set;
    Subscription upstream;
    
    FlatMapMaybeSubscriber(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends MaybeSource<? extends R>> paramFunction, boolean paramBoolean, int paramInt)
    {
      this.downstream = paramSubscriber;
      this.mapper = paramFunction;
      this.delayErrors = paramBoolean;
      this.maxConcurrency = paramInt;
      this.requested = new AtomicLong();
      this.set = new CompositeDisposable();
      this.errors = new AtomicThrowable();
      this.active = new AtomicInteger(1);
      this.queue = new AtomicReference();
    }
    
    public void cancel()
    {
      this.cancelled = true;
      this.upstream.cancel();
      this.set.dispose();
    }
    
    void clear()
    {
      SpscLinkedArrayQueue localSpscLinkedArrayQueue = (SpscLinkedArrayQueue)this.queue.get();
      if (localSpscLinkedArrayQueue != null) {
        localSpscLinkedArrayQueue.clear();
      }
    }
    
    void drain()
    {
      if (getAndIncrement() == 0) {
        drainLoop();
      }
    }
    
    void drainLoop()
    {
      Subscriber localSubscriber = this.downstream;
      AtomicInteger localAtomicInteger = this.active;
      AtomicReference localAtomicReference = this.queue;
      int i = 1;
      int k;
      do
      {
        long l1 = this.requested.get();
        boolean bool;
        int j;
        Object localObject;
        int m;
        for (long l2 = 0L;; l2 += 1L)
        {
          bool = l2 < l1;
          j = 0;
          if (!bool) {
            break;
          }
          if (this.cancelled)
          {
            clear();
            return;
          }
          if ((!this.delayErrors) && ((Throwable)this.errors.get() != null))
          {
            localObject = this.errors.terminate();
            clear();
            localSubscriber.onError((Throwable)localObject);
            return;
          }
          if (localAtomicInteger.get() == 0) {
            k = 1;
          } else {
            k = 0;
          }
          localObject = (SpscLinkedArrayQueue)localAtomicReference.get();
          if (localObject != null) {
            localObject = ((SpscLinkedArrayQueue)localObject).poll();
          } else {
            localObject = null;
          }
          if (localObject == null) {
            m = 1;
          } else {
            m = 0;
          }
          if ((k != 0) && (m != 0))
          {
            localObject = this.errors.terminate();
            if (localObject != null) {
              localSubscriber.onError((Throwable)localObject);
            } else {
              localSubscriber.onComplete();
            }
            return;
          }
          if (m != 0) {
            break;
          }
          localSubscriber.onNext(localObject);
        }
        if (!bool)
        {
          if (this.cancelled)
          {
            clear();
            return;
          }
          if ((!this.delayErrors) && ((Throwable)this.errors.get() != null))
          {
            localObject = this.errors.terminate();
            clear();
            localSubscriber.onError((Throwable)localObject);
            return;
          }
          if (localAtomicInteger.get() == 0) {
            k = 1;
          } else {
            k = 0;
          }
          localObject = (SpscLinkedArrayQueue)localAtomicReference.get();
          if (localObject != null)
          {
            m = j;
            if (!((SpscLinkedArrayQueue)localObject).isEmpty()) {}
          }
          else
          {
            m = 1;
          }
          if ((k != 0) && (m != 0))
          {
            localObject = this.errors.terminate();
            if (localObject != null) {
              localSubscriber.onError((Throwable)localObject);
            } else {
              localSubscriber.onComplete();
            }
            return;
          }
        }
        if (l2 != 0L)
        {
          BackpressureHelper.produced(this.requested, l2);
          if (this.maxConcurrency != Integer.MAX_VALUE) {
            this.upstream.request(l2);
          }
        }
        k = addAndGet(-i);
        i = k;
      } while (k != 0);
    }
    
    SpscLinkedArrayQueue<R> getOrCreateQueue()
    {
      SpscLinkedArrayQueue localSpscLinkedArrayQueue;
      do
      {
        localSpscLinkedArrayQueue = (SpscLinkedArrayQueue)this.queue.get();
        if (localSpscLinkedArrayQueue != null) {
          return localSpscLinkedArrayQueue;
        }
        localSpscLinkedArrayQueue = new SpscLinkedArrayQueue(Flowable.bufferSize());
      } while (!this.queue.compareAndSet(null, localSpscLinkedArrayQueue));
      return localSpscLinkedArrayQueue;
    }
    
    void innerComplete(FlatMapMaybeSubscriber<T, R>.InnerObserver paramFlatMapMaybeSubscriber)
    {
      this.set.delete(paramFlatMapMaybeSubscriber);
      if (get() == 0)
      {
        int i = 1;
        if (compareAndSet(0, 1))
        {
          if (this.active.decrementAndGet() != 0) {
            i = 0;
          }
          paramFlatMapMaybeSubscriber = (SpscLinkedArrayQueue)this.queue.get();
          if ((i != 0) && ((paramFlatMapMaybeSubscriber == null) || (paramFlatMapMaybeSubscriber.isEmpty())))
          {
            paramFlatMapMaybeSubscriber = this.errors.terminate();
            if (paramFlatMapMaybeSubscriber != null) {
              this.downstream.onError(paramFlatMapMaybeSubscriber);
            } else {
              this.downstream.onComplete();
            }
            return;
          }
          if (this.maxConcurrency != Integer.MAX_VALUE) {
            this.upstream.request(1L);
          }
          if (decrementAndGet() == 0) {
            return;
          }
          drainLoop();
          return;
        }
      }
      this.active.decrementAndGet();
      if (this.maxConcurrency != Integer.MAX_VALUE) {
        this.upstream.request(1L);
      }
      drain();
    }
    
    void innerError(FlatMapMaybeSubscriber<T, R>.InnerObserver paramFlatMapMaybeSubscriber, Throwable paramThrowable)
    {
      this.set.delete(paramFlatMapMaybeSubscriber);
      if (this.errors.addThrowable(paramThrowable))
      {
        if (!this.delayErrors)
        {
          this.upstream.cancel();
          this.set.dispose();
        }
        else if (this.maxConcurrency != Integer.MAX_VALUE)
        {
          this.upstream.request(1L);
        }
        this.active.decrementAndGet();
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void innerSuccess(FlatMapMaybeSubscriber<T, R>.InnerObserver arg1, R paramR)
    {
      this.set.delete(???);
      if (get() == 0)
      {
        int i = 1;
        if (compareAndSet(0, 1))
        {
          if (this.active.decrementAndGet() != 0) {
            i = 0;
          }
          if (this.requested.get() != 0L)
          {
            this.downstream.onNext(paramR);
            ??? = (SpscLinkedArrayQueue)this.queue.get();
            if ((i != 0) && ((??? == null) || (???.isEmpty())))
            {
              ??? = this.errors.terminate();
              if (??? != null) {
                this.downstream.onError(???);
              } else {
                this.downstream.onComplete();
              }
              return;
            }
            BackpressureHelper.produced(this.requested, 1L);
            if (this.maxConcurrency == Integer.MAX_VALUE) {
              break label171;
            }
            this.upstream.request(1L);
          }
          synchronized (getOrCreateQueue())
          {
            ???.offer(paramR);
            label171:
            if (decrementAndGet() == 0) {
              return;
            }
          }
        }
      }
      synchronized (getOrCreateQueue())
      {
        ???.offer(paramR);
        this.active.decrementAndGet();
        if (getAndIncrement() != 0) {
          return;
        }
        drainLoop();
        return;
      }
    }
    
    public void onComplete()
    {
      this.active.decrementAndGet();
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.active.decrementAndGet();
      if (this.errors.addThrowable(paramThrowable))
      {
        if (!this.delayErrors) {
          this.set.dispose();
        }
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      try
      {
        paramT = (MaybeSource)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null MaybeSource");
        this.active.getAndIncrement();
        InnerObserver localInnerObserver = new InnerObserver();
        if ((!this.cancelled) && (this.set.add(localInnerObserver))) {
          paramT.subscribe(localInnerObserver);
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.cancel();
        onError(paramT);
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        int i = this.maxConcurrency;
        if (i == Integer.MAX_VALUE) {
          paramSubscription.request(Long.MAX_VALUE);
        } else {
          paramSubscription.request(i);
        }
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
    
    final class InnerObserver
      extends AtomicReference<Disposable>
      implements MaybeObserver<R>, Disposable
    {
      private static final long serialVersionUID = -502562646270949838L;
      
      InnerObserver() {}
      
      public void dispose()
      {
        DisposableHelper.dispose(this);
      }
      
      public boolean isDisposed()
      {
        return DisposableHelper.isDisposed((Disposable)get());
      }
      
      public void onComplete()
      {
        FlowableFlatMapMaybe.FlatMapMaybeSubscriber.this.innerComplete(this);
      }
      
      public void onError(Throwable paramThrowable)
      {
        FlowableFlatMapMaybe.FlatMapMaybeSubscriber.this.innerError(this, paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
      
      public void onSuccess(R paramR)
      {
        FlowableFlatMapMaybe.FlatMapMaybeSubscriber.this.innerSuccess(this, paramR);
      }
    }
  }
}
