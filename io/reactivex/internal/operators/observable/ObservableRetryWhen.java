package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableRetryWhen<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Function<? super Observable<Throwable>, ? extends ObservableSource<?>> handler;
  
  public ObservableRetryWhen(ObservableSource<T> paramObservableSource, Function<? super Observable<Throwable>, ? extends ObservableSource<?>> paramFunction)
  {
    super(paramObservableSource);
    this.handler = paramFunction;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    Object localObject = PublishSubject.create().toSerialized();
    try
    {
      ObservableSource localObservableSource = (ObservableSource)ObjectHelper.requireNonNull(this.handler.apply(localObject), "The handler returned a null ObservableSource");
      localObject = new RepeatWhenObserver(paramObserver, (Subject)localObject, this.source);
      paramObserver.onSubscribe((Disposable)localObject);
      localObservableSource.subscribe(((RepeatWhenObserver)localObject).inner);
      ((RepeatWhenObserver)localObject).subscribeNext();
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramObserver);
    }
  }
  
  static final class RepeatWhenObserver<T>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = 802743776666017014L;
    volatile boolean active;
    final Observer<? super T> downstream;
    final AtomicThrowable error;
    final RepeatWhenObserver<T>.InnerRepeatObserver inner;
    final Subject<Throwable> signaller;
    final ObservableSource<T> source;
    final AtomicReference<Disposable> upstream;
    final AtomicInteger wip;
    
    RepeatWhenObserver(Observer<? super T> paramObserver, Subject<Throwable> paramSubject, ObservableSource<T> paramObservableSource)
    {
      this.downstream = paramObserver;
      this.signaller = paramSubject;
      this.source = paramObservableSource;
      this.wip = new AtomicInteger();
      this.error = new AtomicThrowable();
      this.inner = new InnerRepeatObserver();
      this.upstream = new AtomicReference();
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.upstream);
      DisposableHelper.dispose(this.inner);
    }
    
    void innerComplete()
    {
      DisposableHelper.dispose(this.upstream);
      HalfSerializer.onComplete(this.downstream, this, this.error);
    }
    
    void innerError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.upstream);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
    }
    
    void innerNext()
    {
      subscribeNext();
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)this.upstream.get());
    }
    
    public void onComplete()
    {
      DisposableHelper.dispose(this.inner);
      HalfSerializer.onComplete(this.downstream, this, this.error);
    }
    
    public void onError(Throwable paramThrowable)
    {
      DisposableHelper.replace(this.upstream, null);
      this.active = false;
      this.signaller.onNext(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      HalfSerializer.onNext(this.downstream, paramT, this, this.error);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.replace(this.upstream, paramDisposable);
    }
    
    void subscribeNext()
    {
      if (this.wip.getAndIncrement() == 0) {
        do
        {
          if (isDisposed()) {
            return;
          }
          if (!this.active)
          {
            this.active = true;
            this.source.subscribe(this);
          }
        } while (this.wip.decrementAndGet() != 0);
      }
    }
    
    final class InnerRepeatObserver
      extends AtomicReference<Disposable>
      implements Observer<Object>
    {
      private static final long serialVersionUID = 3254781284376480842L;
      
      InnerRepeatObserver() {}
      
      public void onComplete()
      {
        ObservableRetryWhen.RepeatWhenObserver.this.innerComplete();
      }
      
      public void onError(Throwable paramThrowable)
      {
        ObservableRetryWhen.RepeatWhenObserver.this.innerError(paramThrowable);
      }
      
      public void onNext(Object paramObject)
      {
        ObservableRetryWhen.RepeatWhenObserver.this.innerNext();
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
    }
  }
}
