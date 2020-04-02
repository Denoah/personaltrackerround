package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class SingleZipIterable<T, R>
  extends Single<R>
{
  final Iterable<? extends SingleSource<? extends T>> sources;
  final Function<? super Object[], ? extends R> zipper;
  
  public SingleZipIterable(Iterable<? extends SingleSource<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction)
  {
    this.sources = paramIterable;
    this.zipper = paramFunction;
  }
  
  protected void subscribeActual(SingleObserver<? super R> paramSingleObserver)
  {
    Object localObject1 = new SingleSource[8];
    try
    {
      Iterator localIterator = this.sources.iterator();
      int i = 0;
      int j = 0;
      while (localIterator.hasNext())
      {
        SingleSource localSingleSource = (SingleSource)localIterator.next();
        if (localSingleSource == null)
        {
          localObject1 = new java/lang/NullPointerException;
          ((NullPointerException)localObject1).<init>("One of the sources is null");
          EmptyDisposable.error((Throwable)localObject1, paramSingleObserver);
          return;
        }
        localObject2 = localObject1;
        if (j == localObject1.length) {
          localObject2 = (SingleSource[])Arrays.copyOf((Object[])localObject1, (j >> 2) + j);
        }
        localObject2[j] = localSingleSource;
        j++;
        localObject1 = localObject2;
      }
      if (j == 0)
      {
        EmptyDisposable.error(new NoSuchElementException(), paramSingleObserver);
        return;
      }
      if (j == 1)
      {
        localObject1[0].subscribe(new SingleMap.MapSingleObserver(paramSingleObserver, new SingletonArrayFunc()));
        return;
      }
      Object localObject2 = new SingleZipArray.ZipCoordinator(paramSingleObserver, j, this.zipper);
      paramSingleObserver.onSubscribe((Disposable)localObject2);
      while (i < j)
      {
        if (((SingleZipArray.ZipCoordinator)localObject2).isDisposed()) {
          return;
        }
        localObject1[i].subscribe(localObject2.observers[i]);
        i++;
      }
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramSingleObserver);
    }
  }
  
  final class SingletonArrayFunc
    implements Function<T, R>
  {
    SingletonArrayFunc() {}
    
    public R apply(T paramT)
      throws Exception
    {
      return ObjectHelper.requireNonNull(SingleZipIterable.this.zipper.apply(new Object[] { paramT }), "The zipper returned a null value");
    }
  }
}
