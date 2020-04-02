package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SingleAmb<T>
  extends Single<T>
{
  private final SingleSource<? extends T>[] sources;
  private final Iterable<? extends SingleSource<? extends T>> sourcesIterable;
  
  public SingleAmb(SingleSource<? extends T>[] paramArrayOfSingleSource, Iterable<? extends SingleSource<? extends T>> paramIterable)
  {
    this.sources = paramArrayOfSingleSource;
    this.sourcesIterable = paramIterable;
  }
  
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    Object localObject1 = this.sources;
    Iterator localIterator;
    int j;
    if (localObject1 == null)
    {
      Object localObject2 = new SingleSource[8];
      try
      {
        localIterator = this.sourcesIterable.iterator();
        i = 0;
        for (;;)
        {
          localObject1 = localObject2;
          j = i;
          if (!localIterator.hasNext()) {
            break;
          }
          localObject3 = (SingleSource)localIterator.next();
          if (localObject3 == null)
          {
            localObject2 = new java/lang/NullPointerException;
            ((NullPointerException)localObject2).<init>("One of the sources is null");
            EmptyDisposable.error((Throwable)localObject2, paramSingleObserver);
            return;
          }
          localObject1 = localObject2;
          if (i == localObject2.length)
          {
            localObject1 = new SingleSource[(i >> 2) + i];
            System.arraycopy(localObject2, 0, localObject1, 0, i);
          }
          localObject1[i] = localObject3;
          i++;
          localObject2 = localObject1;
        }
        j = localObject1.length;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        EmptyDisposable.error(localThrowable, paramSingleObserver);
        return;
      }
    }
    AtomicBoolean localAtomicBoolean = new AtomicBoolean();
    Object localObject3 = new CompositeDisposable();
    paramSingleObserver.onSubscribe((Disposable)localObject3);
    for (int i = 0; i < j; i++)
    {
      localIterator = localObject1[i];
      if (((CompositeDisposable)localObject3).isDisposed()) {
        return;
      }
      if (localIterator == null)
      {
        ((CompositeDisposable)localObject3).dispose();
        localObject1 = new NullPointerException("One of the sources is null");
        if (localAtomicBoolean.compareAndSet(false, true)) {
          paramSingleObserver.onError((Throwable)localObject1);
        } else {
          RxJavaPlugins.onError((Throwable)localObject1);
        }
        return;
      }
      localIterator.subscribe(new AmbSingleObserver(paramSingleObserver, (CompositeDisposable)localObject3, localAtomicBoolean));
    }
  }
  
  static final class AmbSingleObserver<T>
    implements SingleObserver<T>
  {
    final SingleObserver<? super T> downstream;
    final CompositeDisposable set;
    Disposable upstream;
    final AtomicBoolean winner;
    
    AmbSingleObserver(SingleObserver<? super T> paramSingleObserver, CompositeDisposable paramCompositeDisposable, AtomicBoolean paramAtomicBoolean)
    {
      this.downstream = paramSingleObserver;
      this.set = paramCompositeDisposable;
      this.winner = paramAtomicBoolean;
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.winner.compareAndSet(false, true))
      {
        this.set.delete(this.upstream);
        this.set.dispose();
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.upstream = paramDisposable;
      this.set.add(paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      if (this.winner.compareAndSet(false, true))
      {
        this.set.delete(this.upstream);
        this.set.dispose();
        this.downstream.onSuccess(paramT);
      }
    }
  }
}
