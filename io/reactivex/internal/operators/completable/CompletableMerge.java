package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class CompletableMerge
  extends Completable
{
  final boolean delayErrors;
  final int maxConcurrency;
  final Publisher<? extends CompletableSource> source;
  
  public CompletableMerge(Publisher<? extends CompletableSource> paramPublisher, int paramInt, boolean paramBoolean)
  {
    this.source = paramPublisher;
    this.maxConcurrency = paramInt;
    this.delayErrors = paramBoolean;
  }
  
  public void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    paramCompletableObserver = new CompletableMergeSubscriber(paramCompletableObserver, this.maxConcurrency, this.delayErrors);
    this.source.subscribe(paramCompletableObserver);
  }
  
  static final class CompletableMergeSubscriber
    extends AtomicInteger
    implements FlowableSubscriber<CompletableSource>, Disposable
  {
    private static final long serialVersionUID = -2108443387387077490L;
    final boolean delayErrors;
    final CompletableObserver downstream;
    final AtomicThrowable error;
    final int maxConcurrency;
    final CompositeDisposable set;
    Subscription upstream;
    
    CompletableMergeSubscriber(CompletableObserver paramCompletableObserver, int paramInt, boolean paramBoolean)
    {
      this.downstream = paramCompletableObserver;
      this.maxConcurrency = paramInt;
      this.delayErrors = paramBoolean;
      this.set = new CompositeDisposable();
      this.error = new AtomicThrowable();
      lazySet(1);
    }
    
    public void dispose()
    {
      this.upstream.cancel();
      this.set.dispose();
    }
    
    void innerComplete(MergeInnerObserver paramMergeInnerObserver)
    {
      this.set.delete(paramMergeInnerObserver);
      if (decrementAndGet() == 0)
      {
        paramMergeInnerObserver = (Throwable)this.error.get();
        if (paramMergeInnerObserver != null) {
          this.downstream.onError(paramMergeInnerObserver);
        } else {
          this.downstream.onComplete();
        }
      }
      else if (this.maxConcurrency != Integer.MAX_VALUE)
      {
        this.upstream.request(1L);
      }
    }
    
    void innerError(MergeInnerObserver paramMergeInnerObserver, Throwable paramThrowable)
    {
      this.set.delete(paramMergeInnerObserver);
      if (!this.delayErrors)
      {
        this.upstream.cancel();
        this.set.dispose();
        if (this.error.addThrowable(paramThrowable))
        {
          if (getAndSet(0) > 0) {
            this.downstream.onError(this.error.terminate());
          }
        }
        else {
          RxJavaPlugins.onError(paramThrowable);
        }
      }
      else if (this.error.addThrowable(paramThrowable))
      {
        if (decrementAndGet() == 0) {
          this.downstream.onError(this.error.terminate());
        } else if (this.maxConcurrency != Integer.MAX_VALUE) {
          this.upstream.request(1L);
        }
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public boolean isDisposed()
    {
      return this.set.isDisposed();
    }
    
    public void onComplete()
    {
      if (decrementAndGet() == 0) {
        if ((Throwable)this.error.get() != null) {
          this.downstream.onError(this.error.terminate());
        } else {
          this.downstream.onComplete();
        }
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!this.delayErrors)
      {
        this.set.dispose();
        if (this.error.addThrowable(paramThrowable))
        {
          if (getAndSet(0) > 0) {
            this.downstream.onError(this.error.terminate());
          }
        }
        else {
          RxJavaPlugins.onError(paramThrowable);
        }
      }
      else if (this.error.addThrowable(paramThrowable))
      {
        if (decrementAndGet() == 0) {
          this.downstream.onError(this.error.terminate());
        }
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(CompletableSource paramCompletableSource)
    {
      getAndIncrement();
      MergeInnerObserver localMergeInnerObserver = new MergeInnerObserver();
      this.set.add(localMergeInnerObserver);
      paramCompletableSource.subscribe(localMergeInnerObserver);
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
    
    final class MergeInnerObserver
      extends AtomicReference<Disposable>
      implements CompletableObserver, Disposable
    {
      private static final long serialVersionUID = 251330541679988317L;
      
      MergeInnerObserver() {}
      
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
        CompletableMerge.CompletableMergeSubscriber.this.innerComplete(this);
      }
      
      public void onError(Throwable paramThrowable)
      {
        CompletableMerge.CompletableMergeSubscriber.this.innerError(this, paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
    }
  }
}
