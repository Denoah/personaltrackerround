package io.reactivex.internal.operators.flowable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableMergeWithCompletable<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final CompletableSource other;
  
  public FlowableMergeWithCompletable(Flowable<T> paramFlowable, CompletableSource paramCompletableSource)
  {
    super(paramFlowable);
    this.other = paramCompletableSource;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    MergeWithSubscriber localMergeWithSubscriber = new MergeWithSubscriber(paramSubscriber);
    paramSubscriber.onSubscribe(localMergeWithSubscriber);
    this.source.subscribe(localMergeWithSubscriber);
    this.other.subscribe(localMergeWithSubscriber.otherObserver);
  }
  
  static final class MergeWithSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -4592979584110982903L;
    final Subscriber<? super T> downstream;
    final AtomicThrowable error;
    volatile boolean mainDone;
    final AtomicReference<Subscription> mainSubscription;
    volatile boolean otherDone;
    final OtherObserver otherObserver;
    final AtomicLong requested;
    
    MergeWithSubscriber(Subscriber<? super T> paramSubscriber)
    {
      this.downstream = paramSubscriber;
      this.mainSubscription = new AtomicReference();
      this.otherObserver = new OtherObserver(this);
      this.error = new AtomicThrowable();
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      SubscriptionHelper.cancel(this.mainSubscription);
      DisposableHelper.dispose(this.otherObserver);
    }
    
    public void onComplete()
    {
      this.mainDone = true;
      if (this.otherDone) {
        HalfSerializer.onComplete(this.downstream, this, this.error);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      SubscriptionHelper.cancel(this.mainSubscription);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
    }
    
    public void onNext(T paramT)
    {
      HalfSerializer.onNext(this.downstream, paramT, this, this.error);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.deferredSetOnce(this.mainSubscription, this.requested, paramSubscription);
    }
    
    void otherComplete()
    {
      this.otherDone = true;
      if (this.mainDone) {
        HalfSerializer.onComplete(this.downstream, this, this.error);
      }
    }
    
    void otherError(Throwable paramThrowable)
    {
      SubscriptionHelper.cancel(this.mainSubscription);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
    }
    
    public void request(long paramLong)
    {
      SubscriptionHelper.deferredRequest(this.mainSubscription, this.requested, paramLong);
    }
    
    static final class OtherObserver
      extends AtomicReference<Disposable>
      implements CompletableObserver
    {
      private static final long serialVersionUID = -2935427570954647017L;
      final FlowableMergeWithCompletable.MergeWithSubscriber<?> parent;
      
      OtherObserver(FlowableMergeWithCompletable.MergeWithSubscriber<?> paramMergeWithSubscriber)
      {
        this.parent = paramMergeWithSubscriber;
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
    }
  }
}
