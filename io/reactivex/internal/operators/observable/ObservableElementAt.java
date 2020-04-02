package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;

public final class ObservableElementAt<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final T defaultValue;
  final boolean errorOnFewer;
  final long index;
  
  public ObservableElementAt(ObservableSource<T> paramObservableSource, long paramLong, T paramT, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.index = paramLong;
    this.defaultValue = paramT;
    this.errorOnFewer = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new ElementAtObserver(paramObserver, this.index, this.defaultValue, this.errorOnFewer));
  }
  
  static final class ElementAtObserver<T>
    implements Observer<T>, Disposable
  {
    long count;
    final T defaultValue;
    boolean done;
    final Observer<? super T> downstream;
    final boolean errorOnFewer;
    final long index;
    Disposable upstream;
    
    ElementAtObserver(Observer<? super T> paramObserver, long paramLong, T paramT, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.index = paramLong;
      this.defaultValue = paramT;
      this.errorOnFewer = paramBoolean;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        Object localObject = this.defaultValue;
        if ((localObject == null) && (this.errorOnFewer))
        {
          this.downstream.onError(new NoSuchElementException());
        }
        else
        {
          if (localObject != null) {
            this.downstream.onNext(localObject);
          }
          this.downstream.onComplete();
        }
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      long l = this.count;
      if (l == this.index)
      {
        this.done = true;
        this.upstream.dispose();
        this.downstream.onNext(paramT);
        this.downstream.onComplete();
        return;
      }
      this.count = (l + 1L);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
  }
}
