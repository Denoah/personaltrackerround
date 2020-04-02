package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDebounce<T, U>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Function<? super T, ? extends Publisher<U>> debounceSelector;
  
  public FlowableDebounce(Flowable<T> paramFlowable, Function<? super T, ? extends Publisher<U>> paramFunction)
  {
    super(paramFlowable);
    this.debounceSelector = paramFunction;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new DebounceSubscriber(new SerializedSubscriber(paramSubscriber), this.debounceSelector));
  }
  
  static final class DebounceSubscriber<T, U>
    extends AtomicLong
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = 6725975399620862591L;
    final Function<? super T, ? extends Publisher<U>> debounceSelector;
    final AtomicReference<Disposable> debouncer = new AtomicReference();
    boolean done;
    final Subscriber<? super T> downstream;
    volatile long index;
    Subscription upstream;
    
    DebounceSubscriber(Subscriber<? super T> paramSubscriber, Function<? super T, ? extends Publisher<U>> paramFunction)
    {
      this.downstream = paramSubscriber;
      this.debounceSelector = paramFunction;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
      DisposableHelper.dispose(this.debouncer);
    }
    
    void emit(long paramLong, T paramT)
    {
      if (paramLong == this.index) {
        if (get() != 0L)
        {
          this.downstream.onNext(paramT);
          BackpressureHelper.produced(this, 1L);
        }
        else
        {
          cancel();
          this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
        }
      }
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      Disposable localDisposable = (Disposable)this.debouncer.get();
      if (!DisposableHelper.isDisposed(localDisposable))
      {
        ((DebounceInnerSubscriber)localDisposable).emit();
        DisposableHelper.dispose(this.debouncer);
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.debouncer);
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      long l = this.index + 1L;
      this.index = l;
      Disposable localDisposable = (Disposable)this.debouncer.get();
      if (localDisposable != null) {
        localDisposable.dispose();
      }
      try
      {
        Publisher localPublisher = (Publisher)ObjectHelper.requireNonNull(this.debounceSelector.apply(paramT), "The publisher supplied is null");
        paramT = new DebounceInnerSubscriber(this, l, paramT);
        if (this.debouncer.compareAndSet(localDisposable, paramT)) {
          localPublisher.subscribe(paramT);
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        cancel();
        this.downstream.onError(paramT);
      }
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
      if (SubscriptionHelper.validate(paramLong)) {
        BackpressureHelper.add(this, paramLong);
      }
    }
    
    static final class DebounceInnerSubscriber<T, U>
      extends DisposableSubscriber<U>
    {
      boolean done;
      final long index;
      final AtomicBoolean once = new AtomicBoolean();
      final FlowableDebounce.DebounceSubscriber<T, U> parent;
      final T value;
      
      DebounceInnerSubscriber(FlowableDebounce.DebounceSubscriber<T, U> paramDebounceSubscriber, long paramLong, T paramT)
      {
        this.parent = paramDebounceSubscriber;
        this.index = paramLong;
        this.value = paramT;
      }
      
      void emit()
      {
        if (this.once.compareAndSet(false, true)) {
          this.parent.emit(this.index, this.value);
        }
      }
      
      public void onComplete()
      {
        if (this.done) {
          return;
        }
        this.done = true;
        emit();
      }
      
      public void onError(Throwable paramThrowable)
      {
        if (this.done)
        {
          RxJavaPlugins.onError(paramThrowable);
          return;
        }
        this.done = true;
        this.parent.onError(paramThrowable);
      }
      
      public void onNext(U paramU)
      {
        if (this.done) {
          return;
        }
        this.done = true;
        cancel();
        emit();
      }
    }
  }
}
