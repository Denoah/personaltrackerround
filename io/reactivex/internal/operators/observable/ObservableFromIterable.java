package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;
import java.util.Iterator;

public final class ObservableFromIterable<T>
  extends Observable<T>
{
  final Iterable<? extends T> source;
  
  public ObservableFromIterable(Iterable<? extends T> paramIterable)
  {
    this.source = paramIterable;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    try
    {
      Object localObject = this.source.iterator();
      try
      {
        boolean bool = ((Iterator)localObject).hasNext();
        if (!bool)
        {
          EmptyDisposable.complete(paramObserver);
          return;
        }
        localObject = new FromIterableDisposable(paramObserver, (Iterator)localObject);
        paramObserver.onSubscribe((Disposable)localObject);
        if (!((FromIterableDisposable)localObject).fusionMode) {
          ((FromIterableDisposable)localObject).run();
        }
        return;
      }
      finally {}
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable2);
      EmptyDisposable.error(localThrowable2, paramObserver);
    }
  }
  
  static final class FromIterableDisposable<T>
    extends BasicQueueDisposable<T>
  {
    boolean checkNext;
    volatile boolean disposed;
    boolean done;
    final Observer<? super T> downstream;
    boolean fusionMode;
    final Iterator<? extends T> it;
    
    FromIterableDisposable(Observer<? super T> paramObserver, Iterator<? extends T> paramIterator)
    {
      this.downstream = paramObserver;
      this.it = paramIterator;
    }
    
    public void clear()
    {
      this.done = true;
    }
    
    public void dispose()
    {
      this.disposed = true;
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    public boolean isEmpty()
    {
      return this.done;
    }
    
    public T poll()
    {
      if (this.done) {
        return null;
      }
      if (this.checkNext)
      {
        if (!this.it.hasNext())
        {
          this.done = true;
          return null;
        }
      }
      else {
        this.checkNext = true;
      }
      return ObjectHelper.requireNonNull(this.it.next(), "The iterator returned a null value");
    }
    
    public int requestFusion(int paramInt)
    {
      if ((paramInt & 0x1) != 0)
      {
        this.fusionMode = true;
        return 1;
      }
      return 0;
    }
    
    void run()
    {
      for (;;)
      {
        if (isDisposed()) {
          return;
        }
        try
        {
          Object localObject = ObjectHelper.requireNonNull(this.it.next(), "The iterator returned a null value");
          this.downstream.onNext(localObject);
          if (isDisposed()) {
            return;
          }
          try
          {
            boolean bool = this.it.hasNext();
            if (bool) {
              continue;
            }
            if (!isDisposed()) {
              this.downstream.onComplete();
            }
            return;
          }
          finally {}
          return;
        }
        finally
        {
          Exceptions.throwIfFatal(localThrowable2);
          this.downstream.onError(localThrowable2);
        }
      }
    }
  }
}
