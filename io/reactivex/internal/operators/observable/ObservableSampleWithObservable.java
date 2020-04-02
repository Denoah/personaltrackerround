package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableSampleWithObservable<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final boolean emitLast;
  final ObservableSource<?> other;
  
  public ObservableSampleWithObservable(ObservableSource<T> paramObservableSource, ObservableSource<?> paramObservableSource1, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.other = paramObservableSource1;
    this.emitLast = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    paramObserver = new SerializedObserver(paramObserver);
    if (this.emitLast) {
      this.source.subscribe(new SampleMainEmitLast(paramObserver, this.other));
    } else {
      this.source.subscribe(new SampleMainNoLast(paramObserver, this.other));
    }
  }
  
  static final class SampleMainEmitLast<T>
    extends ObservableSampleWithObservable.SampleMainObserver<T>
  {
    private static final long serialVersionUID = -3029755663834015785L;
    volatile boolean done;
    final AtomicInteger wip = new AtomicInteger();
    
    SampleMainEmitLast(Observer<? super T> paramObserver, ObservableSource<?> paramObservableSource)
    {
      super(paramObservableSource);
    }
    
    void completion()
    {
      this.done = true;
      if (this.wip.getAndIncrement() == 0)
      {
        emit();
        this.downstream.onComplete();
      }
    }
    
    void run()
    {
      if (this.wip.getAndIncrement() == 0) {
        do
        {
          boolean bool = this.done;
          emit();
          if (bool)
          {
            this.downstream.onComplete();
            return;
          }
        } while (this.wip.decrementAndGet() != 0);
      }
    }
  }
  
  static final class SampleMainNoLast<T>
    extends ObservableSampleWithObservable.SampleMainObserver<T>
  {
    private static final long serialVersionUID = -3029755663834015785L;
    
    SampleMainNoLast(Observer<? super T> paramObserver, ObservableSource<?> paramObservableSource)
    {
      super(paramObservableSource);
    }
    
    void completion()
    {
      this.downstream.onComplete();
    }
    
    void run()
    {
      emit();
    }
  }
  
  static abstract class SampleMainObserver<T>
    extends AtomicReference<T>
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = -3517602651313910099L;
    final Observer<? super T> downstream;
    final AtomicReference<Disposable> other = new AtomicReference();
    final ObservableSource<?> sampler;
    Disposable upstream;
    
    SampleMainObserver(Observer<? super T> paramObserver, ObservableSource<?> paramObservableSource)
    {
      this.downstream = paramObserver;
      this.sampler = paramObservableSource;
    }
    
    public void complete()
    {
      this.upstream.dispose();
      completion();
    }
    
    abstract void completion();
    
    public void dispose()
    {
      DisposableHelper.dispose(this.other);
      this.upstream.dispose();
    }
    
    void emit()
    {
      Object localObject = getAndSet(null);
      if (localObject != null) {
        this.downstream.onNext(localObject);
      }
    }
    
    public void error(Throwable paramThrowable)
    {
      this.upstream.dispose();
      this.downstream.onError(paramThrowable);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.other.get() == DisposableHelper.DISPOSED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      DisposableHelper.dispose(this.other);
      completion();
    }
    
    public void onError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.other);
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      lazySet(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
        if (this.other.get() == null) {
          this.sampler.subscribe(new ObservableSampleWithObservable.SamplerObserver(this));
        }
      }
    }
    
    abstract void run();
    
    boolean setOther(Disposable paramDisposable)
    {
      return DisposableHelper.setOnce(this.other, paramDisposable);
    }
  }
  
  static final class SamplerObserver<T>
    implements Observer<Object>
  {
    final ObservableSampleWithObservable.SampleMainObserver<T> parent;
    
    SamplerObserver(ObservableSampleWithObservable.SampleMainObserver<T> paramSampleMainObserver)
    {
      this.parent = paramSampleMainObserver;
    }
    
    public void onComplete()
    {
      this.parent.complete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.error(paramThrowable);
    }
    
    public void onNext(Object paramObject)
    {
      this.parent.run();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.parent.setOther(paramDisposable);
    }
  }
}
