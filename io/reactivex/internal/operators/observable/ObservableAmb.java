package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableAmb<T>
  extends Observable<T>
{
  final ObservableSource<? extends T>[] sources;
  final Iterable<? extends ObservableSource<? extends T>> sourcesIterable;
  
  public ObservableAmb(ObservableSource<? extends T>[] paramArrayOfObservableSource, Iterable<? extends ObservableSource<? extends T>> paramIterable)
  {
    this.sources = paramArrayOfObservableSource;
    this.sourcesIterable = paramIterable;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    Object localObject1 = this.sources;
    int j;
    if (localObject1 == null)
    {
      Object localObject2 = new Observable[8];
      try
      {
        Iterator localIterator = this.sourcesIterable.iterator();
        int i = 0;
        for (;;)
        {
          localObject1 = localObject2;
          j = i;
          if (!localIterator.hasNext()) {
            break;
          }
          ObservableSource localObservableSource = (ObservableSource)localIterator.next();
          if (localObservableSource == null)
          {
            localObject2 = new java/lang/NullPointerException;
            ((NullPointerException)localObject2).<init>("One of the sources is null");
            EmptyDisposable.error((Throwable)localObject2, paramObserver);
            return;
          }
          localObject1 = localObject2;
          if (i == localObject2.length)
          {
            localObject1 = new ObservableSource[(i >> 2) + i];
            System.arraycopy(localObject2, 0, localObject1, 0, i);
          }
          localObject1[i] = localObservableSource;
          i++;
          localObject2 = localObject1;
        }
        j = localObject1.length;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        EmptyDisposable.error(localThrowable, paramObserver);
        return;
      }
    }
    if (j == 0)
    {
      EmptyDisposable.complete(paramObserver);
      return;
    }
    if (j == 1)
    {
      localObject1[0].subscribe(paramObserver);
      return;
    }
    new AmbCoordinator(paramObserver, j).subscribe((ObservableSource[])localObject1);
  }
  
  static final class AmbCoordinator<T>
    implements Disposable
  {
    final Observer<? super T> downstream;
    final ObservableAmb.AmbInnerObserver<T>[] observers;
    final AtomicInteger winner = new AtomicInteger();
    
    AmbCoordinator(Observer<? super T> paramObserver, int paramInt)
    {
      this.downstream = paramObserver;
      this.observers = new ObservableAmb.AmbInnerObserver[paramInt];
    }
    
    public void dispose()
    {
      if (this.winner.get() != -1)
      {
        this.winner.lazySet(-1);
        ObservableAmb.AmbInnerObserver[] arrayOfAmbInnerObserver = this.observers;
        int i = arrayOfAmbInnerObserver.length;
        for (int j = 0; j < i; j++) {
          arrayOfAmbInnerObserver[j].dispose();
        }
      }
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.winner.get() == -1) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void subscribe(ObservableSource<? extends T>[] paramArrayOfObservableSource)
    {
      ObservableAmb.AmbInnerObserver[] arrayOfAmbInnerObserver = this.observers;
      int i = arrayOfAmbInnerObserver.length;
      int j = 0;
      int m;
      for (int k = 0; k < i; k = m)
      {
        m = k + 1;
        arrayOfAmbInnerObserver[k] = new ObservableAmb.AmbInnerObserver(this, m, this.downstream);
      }
      this.winner.lazySet(0);
      this.downstream.onSubscribe(this);
      for (k = j; k < i; k++)
      {
        if (this.winner.get() != 0) {
          return;
        }
        paramArrayOfObservableSource[k].subscribe(arrayOfAmbInnerObserver[k]);
      }
    }
    
    public boolean win(int paramInt)
    {
      int i = this.winner.get();
      boolean bool = true;
      int j = 0;
      if (i == 0)
      {
        if (this.winner.compareAndSet(0, paramInt))
        {
          ObservableAmb.AmbInnerObserver[] arrayOfAmbInnerObserver = this.observers;
          int k = arrayOfAmbInnerObserver.length;
          while (j < k)
          {
            i = j + 1;
            if (i != paramInt) {
              arrayOfAmbInnerObserver[j].dispose();
            }
            j = i;
          }
          return true;
        }
        return false;
      }
      if (i != paramInt) {
        bool = false;
      }
      return bool;
    }
  }
  
  static final class AmbInnerObserver<T>
    extends AtomicReference<Disposable>
    implements Observer<T>
  {
    private static final long serialVersionUID = -1185974347409665484L;
    final Observer<? super T> downstream;
    final int index;
    final ObservableAmb.AmbCoordinator<T> parent;
    boolean won;
    
    AmbInnerObserver(ObservableAmb.AmbCoordinator<T> paramAmbCoordinator, int paramInt, Observer<? super T> paramObserver)
    {
      this.parent = paramAmbCoordinator;
      this.index = paramInt;
      this.downstream = paramObserver;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public void onComplete()
    {
      if (this.won)
      {
        this.downstream.onComplete();
      }
      else if (this.parent.win(this.index))
      {
        this.won = true;
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.won)
      {
        this.downstream.onError(paramThrowable);
      }
      else if (this.parent.win(this.index))
      {
        this.won = true;
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if (this.won)
      {
        this.downstream.onNext(paramT);
      }
      else if (this.parent.win(this.index))
      {
        this.won = true;
        this.downstream.onNext(paramT);
      }
      else
      {
        ((Disposable)get()).dispose();
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
  }
}
