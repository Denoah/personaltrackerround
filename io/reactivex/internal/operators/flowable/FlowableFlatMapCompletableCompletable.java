package io.reactivex.internal.operators.flowable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

public final class FlowableFlatMapCompletableCompletable<T>
  extends Completable
  implements FuseToFlowable<T>
{
  final boolean delayErrors;
  final Function<? super T, ? extends CompletableSource> mapper;
  final int maxConcurrency;
  final Flowable<T> source;
  
  public FlowableFlatMapCompletableCompletable(Flowable<T> paramFlowable, Function<? super T, ? extends CompletableSource> paramFunction, boolean paramBoolean, int paramInt)
  {
    this.source = paramFlowable;
    this.mapper = paramFunction;
    this.delayErrors = paramBoolean;
    this.maxConcurrency = paramInt;
  }
  
  public Flowable<T> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableFlatMapCompletable(this.source, this.mapper, this.delayErrors, this.maxConcurrency));
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    this.source.subscribe(new FlatMapCompletableMainSubscriber(paramCompletableObserver, this.mapper, this.delayErrors, this.maxConcurrency));
  }
  
  static final class FlatMapCompletableMainSubscriber<T>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Disposable
  {
    private static final long serialVersionUID = 8443155186132538303L;
    final boolean delayErrors;
    volatile boolean disposed;
    final CompletableObserver downstream;
    final AtomicThrowable errors;
    final Function<? super T, ? extends CompletableSource> mapper;
    final int maxConcurrency;
    final CompositeDisposable set;
    Subscription upstream;
    
    FlatMapCompletableMainSubscriber(CompletableObserver paramCompletableObserver, Function<? super T, ? extends CompletableSource> paramFunction, boolean paramBoolean, int paramInt)
    {
      this.downstream = paramCompletableObserver;
      this.mapper = paramFunction;
      this.delayErrors = paramBoolean;
      this.errors = new AtomicThrowable();
      this.set = new CompositeDisposable();
      this.maxConcurrency = paramInt;
      lazySet(1);
    }
    
    public void dispose()
    {
      this.disposed = true;
      this.upstream.cancel();
      this.set.dispose();
    }
    
    void innerComplete(FlatMapCompletableMainSubscriber<T>.InnerObserver paramFlatMapCompletableMainSubscriber)
    {
      this.set.delete(paramFlatMapCompletableMainSubscriber);
      onComplete();
    }
    
    void innerError(FlatMapCompletableMainSubscriber<T>.InnerObserver paramFlatMapCompletableMainSubscriber, Throwable paramThrowable)
    {
      this.set.delete(paramFlatMapCompletableMainSubscriber);
      onError(paramThrowable);
    }
    
    public boolean isDisposed()
    {
      return this.set.isDisposed();
    }
    
    public void onComplete()
    {
      if (decrementAndGet() == 0)
      {
        Throwable localThrowable = this.errors.terminate();
        if (localThrowable != null) {
          this.downstream.onError(localThrowable);
        } else {
          this.downstream.onComplete();
        }
      }
      else if (this.maxConcurrency != Integer.MAX_VALUE)
      {
        this.upstream.request(1L);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.errors.addThrowable(paramThrowable))
      {
        if (this.delayErrors)
        {
          if (decrementAndGet() == 0)
          {
            paramThrowable = this.errors.terminate();
            this.downstream.onError(paramThrowable);
          }
          else if (this.maxConcurrency != Integer.MAX_VALUE)
          {
            this.upstream.request(1L);
          }
        }
        else
        {
          dispose();
          if (getAndSet(0) > 0)
          {
            paramThrowable = this.errors.terminate();
            this.downstream.onError(paramThrowable);
          }
        }
      }
      else {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      try
      {
        CompletableSource localCompletableSource = (CompletableSource)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null CompletableSource");
        getAndIncrement();
        paramT = new InnerObserver();
        if ((!this.disposed) && (this.set.add(paramT))) {
          localCompletableSource.subscribe(paramT);
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
    
    final class InnerObserver
      extends AtomicReference<Disposable>
      implements CompletableObserver, Disposable
    {
      private static final long serialVersionUID = 8606673141535671828L;
      
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
        FlowableFlatMapCompletableCompletable.FlatMapCompletableMainSubscriber.this.innerComplete(this);
      }
      
      public void onError(Throwable paramThrowable)
      {
        FlowableFlatMapCompletableCompletable.FlatMapCompletableMainSubscriber.this.innerError(this, paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
    }
  }
}
