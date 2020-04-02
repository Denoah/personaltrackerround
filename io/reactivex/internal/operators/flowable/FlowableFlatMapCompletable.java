package io.reactivex.internal.operators.flowable;

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
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableFlatMapCompletable<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final boolean delayErrors;
  final Function<? super T, ? extends CompletableSource> mapper;
  final int maxConcurrency;
  
  public FlowableFlatMapCompletable(Flowable<T> paramFlowable, Function<? super T, ? extends CompletableSource> paramFunction, boolean paramBoolean, int paramInt)
  {
    super(paramFlowable);
    this.mapper = paramFunction;
    this.delayErrors = paramBoolean;
    this.maxConcurrency = paramInt;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new FlatMapCompletableMainSubscriber(paramSubscriber, this.mapper, this.delayErrors, this.maxConcurrency));
  }
  
  static final class FlatMapCompletableMainSubscriber<T>
    extends BasicIntQueueSubscription<T>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = 8443155186132538303L;
    volatile boolean cancelled;
    final boolean delayErrors;
    final Subscriber<? super T> downstream;
    final AtomicThrowable errors;
    final Function<? super T, ? extends CompletableSource> mapper;
    final int maxConcurrency;
    final CompositeDisposable set;
    Subscription upstream;
    
    FlatMapCompletableMainSubscriber(Subscriber<? super T> paramSubscriber, Function<? super T, ? extends CompletableSource> paramFunction, boolean paramBoolean, int paramInt)
    {
      this.downstream = paramSubscriber;
      this.mapper = paramFunction;
      this.delayErrors = paramBoolean;
      this.errors = new AtomicThrowable();
      this.set = new CompositeDisposable();
      this.maxConcurrency = paramInt;
      lazySet(1);
    }
    
    public void cancel()
    {
      this.cancelled = true;
      this.upstream.cancel();
      this.set.dispose();
    }
    
    public void clear() {}
    
    void innerComplete(FlatMapCompletableMainSubscriber<T>.InnerConsumer paramFlatMapCompletableMainSubscriber)
    {
      this.set.delete(paramFlatMapCompletableMainSubscriber);
      onComplete();
    }
    
    void innerError(FlatMapCompletableMainSubscriber<T>.InnerConsumer paramFlatMapCompletableMainSubscriber, Throwable paramThrowable)
    {
      this.set.delete(paramFlatMapCompletableMainSubscriber);
      onError(paramThrowable);
    }
    
    public boolean isEmpty()
    {
      return true;
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
          cancel();
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
        paramT = (CompletableSource)ObjectHelper.requireNonNull(this.mapper.apply(paramT), "The mapper returned a null CompletableSource");
        getAndIncrement();
        InnerConsumer localInnerConsumer = new InnerConsumer();
        if ((!this.cancelled) && (this.set.add(localInnerConsumer))) {
          paramT.subscribe(localInnerConsumer);
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
    
    public T poll()
      throws Exception
    {
      return null;
    }
    
    public void request(long paramLong) {}
    
    public int requestFusion(int paramInt)
    {
      return paramInt & 0x2;
    }
    
    final class InnerConsumer
      extends AtomicReference<Disposable>
      implements CompletableObserver, Disposable
    {
      private static final long serialVersionUID = 8606673141535671828L;
      
      InnerConsumer() {}
      
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
        FlowableFlatMapCompletable.FlatMapCompletableMainSubscriber.this.innerComplete(this);
      }
      
      public void onError(Throwable paramThrowable)
      {
        FlowableFlatMapCompletable.FlatMapCompletableMainSubscriber.this.innerError(this, paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
    }
  }
}
