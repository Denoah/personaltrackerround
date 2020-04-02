package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableMergeWithMaybe<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final MaybeSource<? extends T> other;
  
  public FlowableMergeWithMaybe(Flowable<T> paramFlowable, MaybeSource<? extends T> paramMaybeSource)
  {
    super(paramFlowable);
    this.other = paramMaybeSource;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    MergeWithObserver localMergeWithObserver = new MergeWithObserver(paramSubscriber);
    paramSubscriber.onSubscribe(localMergeWithObserver);
    this.source.subscribe(localMergeWithObserver);
    this.other.subscribe(localMergeWithObserver.otherObserver);
  }
  
  static final class MergeWithObserver<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    static final int OTHER_STATE_CONSUMED_OR_EMPTY = 2;
    static final int OTHER_STATE_HAS_VALUE = 1;
    private static final long serialVersionUID = -4592979584110982903L;
    volatile boolean cancelled;
    int consumed;
    final Subscriber<? super T> downstream;
    long emitted;
    final AtomicThrowable error;
    final int limit;
    volatile boolean mainDone;
    final AtomicReference<Subscription> mainSubscription;
    final OtherObserver<T> otherObserver;
    volatile int otherState;
    final int prefetch;
    volatile SimplePlainQueue<T> queue;
    final AtomicLong requested;
    T singleItem;
    
    MergeWithObserver(Subscriber<? super T> paramSubscriber)
    {
      this.downstream = paramSubscriber;
      this.mainSubscription = new AtomicReference();
      this.otherObserver = new OtherObserver(this);
      this.error = new AtomicThrowable();
      this.requested = new AtomicLong();
      int i = Flowable.bufferSize();
      this.prefetch = i;
      this.limit = (i - (i >> 2));
    }
    
    public void cancel()
    {
      this.cancelled = true;
      SubscriptionHelper.cancel(this.mainSubscription);
      DisposableHelper.dispose(this.otherObserver);
      if (getAndIncrement() == 0)
      {
        this.queue = null;
        this.singleItem = null;
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
      long l1 = this.emitted;
      int i = this.consumed;
      int j = this.limit;
      int k = 1;
      for (;;)
      {
        long l2 = this.requested.get();
        boolean bool1;
        Object localObject;
        boolean bool2;
        int n;
        for (;;)
        {
          bool1 = l1 < l2;
          if (!bool1) {
            break;
          }
          if (this.cancelled)
          {
            this.singleItem = null;
            this.queue = null;
            return;
          }
          if (this.error.get() != null)
          {
            this.singleItem = null;
            this.queue = null;
            localSubscriber.onError(this.error.terminate());
            return;
          }
          int m = this.otherState;
          if (m == 1)
          {
            localObject = this.singleItem;
            this.singleItem = null;
            this.otherState = 2;
            localSubscriber.onNext(localObject);
            l1 += 1L;
          }
          else
          {
            bool2 = this.mainDone;
            localObject = this.queue;
            if (localObject != null) {
              localObject = ((SimplePlainQueue)localObject).poll();
            } else {
              localObject = null;
            }
            if (localObject == null) {
              n = 1;
            } else {
              n = 0;
            }
            if ((bool2) && (n != 0) && (m == 2))
            {
              this.queue = null;
              localSubscriber.onComplete();
              return;
            }
            if (n != 0) {
              break;
            }
            localSubscriber.onNext(localObject);
            l1 += 1L;
            n = i + 1;
            i = n;
            if (n == j)
            {
              ((Subscription)this.mainSubscription.get()).request(j);
              i = 0;
            }
          }
        }
        if (!bool1)
        {
          if (this.cancelled)
          {
            this.singleItem = null;
            this.queue = null;
            return;
          }
          if (this.error.get() != null)
          {
            this.singleItem = null;
            this.queue = null;
            localSubscriber.onError(this.error.terminate());
            return;
          }
          bool2 = this.mainDone;
          localObject = this.queue;
          if ((localObject != null) && (!((SimplePlainQueue)localObject).isEmpty())) {
            n = 0;
          } else {
            n = 1;
          }
          if ((bool2) && (n != 0) && (this.otherState == 2))
          {
            this.queue = null;
            localSubscriber.onComplete();
            return;
          }
        }
        this.emitted = l1;
        this.consumed = i;
        k = addAndGet(-k);
        if (k == 0) {
          return;
        }
      }
    }
    
    SimplePlainQueue<T> getOrCreateQueue()
    {
      SimplePlainQueue localSimplePlainQueue = this.queue;
      Object localObject = localSimplePlainQueue;
      if (localSimplePlainQueue == null)
      {
        localObject = new SpscArrayQueue(Flowable.bufferSize());
        this.queue = ((SimplePlainQueue)localObject);
      }
      return localObject;
    }
    
    public void onComplete()
    {
      this.mainDone = true;
      drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.error.addThrowable(paramThrowable))
      {
        SubscriptionHelper.cancel(this.mainSubscription);
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if (compareAndSet(0, 1))
      {
        long l = this.emitted;
        if (this.requested.get() != l)
        {
          SimplePlainQueue localSimplePlainQueue = this.queue;
          if ((localSimplePlainQueue != null) && (!localSimplePlainQueue.isEmpty()))
          {
            localSimplePlainQueue.offer(paramT);
          }
          else
          {
            this.emitted = (l + 1L);
            this.downstream.onNext(paramT);
            int i = this.consumed + 1;
            if (i == this.limit)
            {
              this.consumed = 0;
              ((Subscription)this.mainSubscription.get()).request(i);
            }
            else
            {
              this.consumed = i;
            }
          }
        }
        else
        {
          getOrCreateQueue().offer(paramT);
        }
        if (decrementAndGet() != 0) {}
      }
      else
      {
        getOrCreateQueue().offer(paramT);
        if (getAndIncrement() != 0) {
          return;
        }
      }
      drainLoop();
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this.mainSubscription, paramSubscription, this.prefetch);
    }
    
    void otherComplete()
    {
      this.otherState = 2;
      drain();
    }
    
    void otherError(Throwable paramThrowable)
    {
      if (this.error.addThrowable(paramThrowable))
      {
        SubscriptionHelper.cancel(this.mainSubscription);
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void otherSuccess(T paramT)
    {
      if (compareAndSet(0, 1))
      {
        long l = this.emitted;
        if (this.requested.get() != l)
        {
          this.emitted = (l + 1L);
          this.downstream.onNext(paramT);
          this.otherState = 2;
        }
        else
        {
          this.singleItem = paramT;
          this.otherState = 1;
          if (decrementAndGet() != 0) {}
        }
      }
      else
      {
        this.singleItem = paramT;
        this.otherState = 1;
        if (getAndIncrement() != 0) {
          return;
        }
      }
      drainLoop();
    }
    
    public void request(long paramLong)
    {
      BackpressureHelper.add(this.requested, paramLong);
      drain();
    }
    
    static final class OtherObserver<T>
      extends AtomicReference<Disposable>
      implements MaybeObserver<T>
    {
      private static final long serialVersionUID = -2935427570954647017L;
      final FlowableMergeWithMaybe.MergeWithObserver<T> parent;
      
      OtherObserver(FlowableMergeWithMaybe.MergeWithObserver<T> paramMergeWithObserver)
      {
        this.parent = paramMergeWithObserver;
      }
      
      public void onComplete()
      {
        this.parent.otherComplete();
      }
      
      public void onError(Throwable paramThrowable)
      {
        this.parent.otherError(paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
      
      public void onSuccess(T paramT)
      {
        this.parent.otherSuccess(paramT);
      }
    }
  }
}
