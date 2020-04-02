package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleZipArray<T, R>
  extends Single<R>
{
  final SingleSource<? extends T>[] sources;
  final Function<? super Object[], ? extends R> zipper;
  
  public SingleZipArray(SingleSource<? extends T>[] paramArrayOfSingleSource, Function<? super Object[], ? extends R> paramFunction)
  {
    this.sources = paramArrayOfSingleSource;
    this.zipper = paramFunction;
  }
  
  protected void subscribeActual(SingleObserver<? super R> paramSingleObserver)
  {
    SingleSource[] arrayOfSingleSource = this.sources;
    int i = arrayOfSingleSource.length;
    int j = 0;
    if (i == 1)
    {
      arrayOfSingleSource[0].subscribe(new SingleMap.MapSingleObserver(paramSingleObserver, new SingletonArrayFunc()));
      return;
    }
    ZipCoordinator localZipCoordinator = new ZipCoordinator(paramSingleObserver, i, this.zipper);
    paramSingleObserver.onSubscribe(localZipCoordinator);
    while (j < i)
    {
      if (localZipCoordinator.isDisposed()) {
        return;
      }
      paramSingleObserver = arrayOfSingleSource[j];
      if (paramSingleObserver == null)
      {
        localZipCoordinator.innerError(new NullPointerException("One of the sources is null"), j);
        return;
      }
      paramSingleObserver.subscribe(localZipCoordinator.observers[j]);
      j++;
    }
  }
  
  final class SingletonArrayFunc
    implements Function<T, R>
  {
    SingletonArrayFunc() {}
    
    public R apply(T paramT)
      throws Exception
    {
      return ObjectHelper.requireNonNull(SingleZipArray.this.zipper.apply(new Object[] { paramT }), "The zipper returned a null value");
    }
  }
  
  static final class ZipCoordinator<T, R>
    extends AtomicInteger
    implements Disposable
  {
    private static final long serialVersionUID = -5556924161382950569L;
    final SingleObserver<? super R> downstream;
    final SingleZipArray.ZipSingleObserver<T>[] observers;
    final Object[] values;
    final Function<? super Object[], ? extends R> zipper;
    
    ZipCoordinator(SingleObserver<? super R> paramSingleObserver, int paramInt, Function<? super Object[], ? extends R> paramFunction)
    {
      super();
      this.downstream = paramSingleObserver;
      this.zipper = paramFunction;
      paramSingleObserver = new SingleZipArray.ZipSingleObserver[paramInt];
      for (int i = 0; i < paramInt; i++) {
        paramSingleObserver[i] = new SingleZipArray.ZipSingleObserver(this, i);
      }
      this.observers = paramSingleObserver;
      this.values = new Object[paramInt];
    }
    
    public void dispose()
    {
      int i = 0;
      if (getAndSet(0) > 0)
      {
        SingleZipArray.ZipSingleObserver[] arrayOfZipSingleObserver = this.observers;
        int j = arrayOfZipSingleObserver.length;
        while (i < j)
        {
          arrayOfZipSingleObserver[i].dispose();
          i++;
        }
      }
    }
    
    void disposeExcept(int paramInt)
    {
      SingleZipArray.ZipSingleObserver[] arrayOfZipSingleObserver = this.observers;
      int i = arrayOfZipSingleObserver.length;
      int k;
      for (int j = 0;; j++)
      {
        k = paramInt;
        if (j >= paramInt) {
          break;
        }
        arrayOfZipSingleObserver[j].dispose();
      }
      for (;;)
      {
        k++;
        if (k >= i) {
          break;
        }
        arrayOfZipSingleObserver[k].dispose();
      }
    }
    
    void innerError(Throwable paramThrowable, int paramInt)
    {
      if (getAndSet(0) > 0)
      {
        disposeExcept(paramInt);
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    /* Error */
    void innerSuccess(T paramT, int paramInt)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 45	io/reactivex/internal/operators/single/SingleZipArray$ZipCoordinator:values	[Ljava/lang/Object;
      //   4: iload_2
      //   5: aload_1
      //   6: aastore
      //   7: aload_0
      //   8: invokevirtual 76	io/reactivex/internal/operators/single/SingleZipArray$ZipCoordinator:decrementAndGet	()I
      //   11: ifne +50 -> 61
      //   14: aload_0
      //   15: getfield 34	io/reactivex/internal/operators/single/SingleZipArray$ZipCoordinator:zipper	Lio/reactivex/functions/Function;
      //   18: aload_0
      //   19: getfield 45	io/reactivex/internal/operators/single/SingleZipArray$ZipCoordinator:values	[Ljava/lang/Object;
      //   22: invokeinterface 82 2 0
      //   27: ldc 84
      //   29: invokestatic 90	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   32: astore_1
      //   33: aload_0
      //   34: getfield 32	io/reactivex/internal/operators/single/SingleZipArray$ZipCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   37: aload_1
      //   38: invokeinterface 94 2 0
      //   43: goto +18 -> 61
      //   46: astore_1
      //   47: aload_1
      //   48: invokestatic 99	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   51: aload_0
      //   52: getfield 32	io/reactivex/internal/operators/single/SingleZipArray$ZipCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   55: aload_1
      //   56: invokeinterface 67 2 0
      //   61: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	62	0	this	ZipCoordinator
      //   0	62	1	paramT	T
      //   0	62	2	paramInt	int
      // Exception table:
      //   from	to	target	type
      //   14	33	46	finally
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() <= 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  static final class ZipSingleObserver<T>
    extends AtomicReference<Disposable>
    implements SingleObserver<T>
  {
    private static final long serialVersionUID = 3323743579927613702L;
    final int index;
    final SingleZipArray.ZipCoordinator<T, ?> parent;
    
    ZipSingleObserver(SingleZipArray.ZipCoordinator<T, ?> paramZipCoordinator, int paramInt)
    {
      this.parent = paramZipCoordinator;
      this.index = paramInt;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.innerError(paramThrowable, this.index);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
    
    public void onSuccess(T paramT)
    {
      this.parent.innerSuccess(paramT, this.index);
    }
  }
}
