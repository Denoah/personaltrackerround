package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableDebounceTimed<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Scheduler scheduler;
  final long timeout;
  final TimeUnit unit;
  
  public FlowableDebounceTimed(Flowable<T> paramFlowable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    super(paramFlowable);
    this.timeout = paramLong;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new DebounceTimedSubscriber(new SerializedSubscriber(paramSubscriber), this.timeout, this.unit, this.scheduler.createWorker()));
  }
  
  static final class DebounceEmitter<T>
    extends AtomicReference<Disposable>
    implements Runnable, Disposable
  {
    private static final long serialVersionUID = 6812032969491025141L;
    final long idx;
    final AtomicBoolean once = new AtomicBoolean();
    final FlowableDebounceTimed.DebounceTimedSubscriber<T> parent;
    final T value;
    
    DebounceEmitter(T paramT, long paramLong, FlowableDebounceTimed.DebounceTimedSubscriber<T> paramDebounceTimedSubscriber)
    {
      this.value = paramT;
      this.idx = paramLong;
      this.parent = paramDebounceTimedSubscriber;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    void emit()
    {
      if (this.once.compareAndSet(false, true)) {
        this.parent.emit(this.idx, this.value, this);
      }
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == DisposableHelper.DISPOSED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void run()
    {
      emit();
    }
    
    public void setResource(Disposable paramDisposable)
    {
      DisposableHelper.replace(this, paramDisposable);
    }
  }
  
  static final class DebounceTimedSubscriber<T>
    extends AtomicLong
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -9102637559663639004L;
    boolean done;
    final Subscriber<? super T> downstream;
    volatile long index;
    final long timeout;
    Disposable timer;
    final TimeUnit unit;
    Subscription upstream;
    final Scheduler.Worker worker;
    
    DebounceTimedSubscriber(Subscriber<? super T> paramSubscriber, long paramLong, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker)
    {
      this.downstream = paramSubscriber;
      this.timeout = paramLong;
      this.unit = paramTimeUnit;
      this.worker = paramWorker;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
      this.worker.dispose();
    }
    
    void emit(long paramLong, T paramT, FlowableDebounceTimed.DebounceEmitter<T> paramDebounceEmitter)
    {
      if (paramLong == this.index) {
        if (get() != 0L)
        {
          this.downstream.onNext(paramT);
          BackpressureHelper.produced(this, 1L);
          paramDebounceEmitter.dispose();
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
      Object localObject = this.timer;
      if (localObject != null) {
        ((Disposable)localObject).dispose();
      }
      localObject = (FlowableDebounceTimed.DebounceEmitter)localObject;
      if (localObject != null) {
        ((FlowableDebounceTimed.DebounceEmitter)localObject).emit();
      }
      this.downstream.onComplete();
      this.worker.dispose();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      Disposable localDisposable = this.timer;
      if (localDisposable != null) {
        localDisposable.dispose();
      }
      this.downstream.onError(paramThrowable);
      this.worker.dispose();
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      long l = this.index + 1L;
      this.index = l;
      Disposable localDisposable = this.timer;
      if (localDisposable != null) {
        localDisposable.dispose();
      }
      paramT = new FlowableDebounceTimed.DebounceEmitter(paramT, l, this);
      this.timer = paramT;
      paramT.setResource(this.worker.schedule(paramT, this.timeout, this.unit));
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
  }
}
