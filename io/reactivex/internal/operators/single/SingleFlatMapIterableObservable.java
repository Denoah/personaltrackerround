package io.reactivex.internal.operators.single;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import java.util.Iterator;

public final class SingleFlatMapIterableObservable<T, R>
  extends Observable<R>
{
  final Function<? super T, ? extends Iterable<? extends R>> mapper;
  final SingleSource<T> source;
  
  public SingleFlatMapIterableObservable(SingleSource<T> paramSingleSource, Function<? super T, ? extends Iterable<? extends R>> paramFunction)
  {
    this.source = paramSingleSource;
    this.mapper = paramFunction;
  }
  
  protected void subscribeActual(Observer<? super R> paramObserver)
  {
    this.source.subscribe(new FlatMapIterableObserver(paramObserver, this.mapper));
  }
  
  static final class FlatMapIterableObserver<T, R>
    extends BasicIntQueueDisposable<R>
    implements SingleObserver<T>
  {
    private static final long serialVersionUID = -8938804753851907758L;
    volatile boolean cancelled;
    final Observer<? super R> downstream;
    volatile Iterator<? extends R> it;
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    boolean outputFused;
    Disposable upstream;
    
    FlatMapIterableObserver(Observer<? super R> paramObserver, Function<? super T, ? extends Iterable<? extends R>> paramFunction)
    {
      this.downstream = paramObserver;
      this.mapper = paramFunction;
    }
    
    public void clear()
    {
      this.it = null;
    }
    
    public void dispose()
    {
      this.cancelled = true;
      this.upstream.dispose();
      this.upstream = DisposableHelper.DISPOSED;
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public boolean isEmpty()
    {
      boolean bool;
      if (this.it == null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.upstream = DisposableHelper.DISPOSED;
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void onSuccess(T paramT)
    {
      Observer localObserver = this.downstream;
      try
      {
        Iterator localIterator = ((Iterable)this.mapper.apply(paramT)).iterator();
        boolean bool = localIterator.hasNext();
        if (!bool)
        {
          localObserver.onComplete();
          return;
        }
        if (this.outputFused)
        {
          this.it = localIterator;
          localObserver.onNext(null);
          localObserver.onComplete();
          return;
        }
        for (;;)
        {
          if (this.cancelled) {
            return;
          }
          try
          {
            paramT = localIterator.next();
            localObserver.onNext(paramT);
            if (this.cancelled) {
              return;
            }
            try
            {
              bool = localIterator.hasNext();
              if (bool) {
                continue;
              }
              localObserver.onComplete();
              return;
            }
            finally {}
            paramT = finally;
          }
          finally
          {
            Exceptions.throwIfFatal(paramT);
            localObserver.onError(paramT);
            return;
          }
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.downstream.onError(paramT);
      }
    }
    
    public R poll()
      throws Exception
    {
      Iterator localIterator = this.it;
      if (localIterator != null)
      {
        Object localObject = ObjectHelper.requireNonNull(localIterator.next(), "The iterator returned a null value");
        if (!localIterator.hasNext()) {
          this.it = null;
        }
        return localObject;
      }
      return null;
    }
    
    public int requestFusion(int paramInt)
    {
      if ((paramInt & 0x2) != 0)
      {
        this.outputFused = true;
        return 2;
      }
      return 0;
    }
  }
}
