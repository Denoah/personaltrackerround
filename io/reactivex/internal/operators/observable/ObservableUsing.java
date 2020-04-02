package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObservableUsing<T, D>
  extends Observable<T>
{
  final Consumer<? super D> disposer;
  final boolean eager;
  final Callable<? extends D> resourceSupplier;
  final Function<? super D, ? extends ObservableSource<? extends T>> sourceSupplier;
  
  public ObservableUsing(Callable<? extends D> paramCallable, Function<? super D, ? extends ObservableSource<? extends T>> paramFunction, Consumer<? super D> paramConsumer, boolean paramBoolean)
  {
    this.resourceSupplier = paramCallable;
    this.sourceSupplier = paramFunction;
    this.disposer = paramConsumer;
    this.eager = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    try
    {
      Object localObject = this.resourceSupplier.call();
      try
      {
        ObservableSource localObservableSource = (ObservableSource)ObjectHelper.requireNonNull(this.sourceSupplier.apply(localObject), "The sourceSupplier returned a null ObservableSource");
        localObservableSource.subscribe(new UsingObserver(paramObserver, localObject, this.disposer, this.eager));
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable3);
        try
        {
          this.disposer.accept(localObject);
          EmptyDisposable.error(localThrowable3, paramObserver);
          return;
        }
        finally
        {
          Exceptions.throwIfFatal(localThrowable1);
          EmptyDisposable.error(new CompositeException(new Throwable[] { localThrowable3, localThrowable1 }), paramObserver);
          return;
        }
      }
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable2);
      EmptyDisposable.error(localThrowable2, paramObserver);
    }
  }
  
  static final class UsingObserver<T, D>
    extends AtomicBoolean
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = 5904473792286235046L;
    final Consumer<? super D> disposer;
    final Observer<? super T> downstream;
    final boolean eager;
    final D resource;
    Disposable upstream;
    
    UsingObserver(Observer<? super T> paramObserver, D paramD, Consumer<? super D> paramConsumer, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.resource = paramD;
      this.disposer = paramConsumer;
      this.eager = paramBoolean;
    }
    
    public void dispose()
    {
      disposeAfter();
      this.upstream.dispose();
    }
    
    /* Error */
    void disposeAfter()
    {
      // Byte code:
      //   0: aload_0
      //   1: iconst_0
      //   2: iconst_1
      //   3: invokevirtual 57	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:compareAndSet	(ZZ)Z
      //   6: ifeq +28 -> 34
      //   9: aload_0
      //   10: getfield 40	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:disposer	Lio/reactivex/functions/Consumer;
      //   13: aload_0
      //   14: getfield 38	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:resource	Ljava/lang/Object;
      //   17: invokeinterface 63 2 0
      //   22: goto +12 -> 34
      //   25: astore_1
      //   26: aload_1
      //   27: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   30: aload_1
      //   31: invokestatic 74	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   34: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	35	0	this	UsingObserver
      //   25	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   9	22	25	finally
    }
    
    public boolean isDisposed()
    {
      return get();
    }
    
    /* Error */
    public void onComplete()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 42	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:eager	Z
      //   4: ifeq +65 -> 69
      //   7: aload_0
      //   8: iconst_0
      //   9: iconst_1
      //   10: invokevirtual 57	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:compareAndSet	(ZZ)Z
      //   13: ifeq +35 -> 48
      //   16: aload_0
      //   17: getfield 40	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:disposer	Lio/reactivex/functions/Consumer;
      //   20: aload_0
      //   21: getfield 38	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:resource	Ljava/lang/Object;
      //   24: invokeinterface 63 2 0
      //   29: goto +19 -> 48
      //   32: astore_1
      //   33: aload_1
      //   34: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   37: aload_0
      //   38: getfield 36	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:downstream	Lio/reactivex/Observer;
      //   41: aload_1
      //   42: invokeinterface 81 2 0
      //   47: return
      //   48: aload_0
      //   49: getfield 51	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   52: invokeinterface 53 1 0
      //   57: aload_0
      //   58: getfield 36	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:downstream	Lio/reactivex/Observer;
      //   61: invokeinterface 83 1 0
      //   66: goto +25 -> 91
      //   69: aload_0
      //   70: getfield 36	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:downstream	Lio/reactivex/Observer;
      //   73: invokeinterface 83 1 0
      //   78: aload_0
      //   79: getfield 51	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   82: invokeinterface 53 1 0
      //   87: aload_0
      //   88: invokevirtual 49	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:disposeAfter	()V
      //   91: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	92	0	this	UsingObserver
      //   32	10	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   16	29	32	finally
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 42	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:eager	Z
      //   4: ifeq +79 -> 83
      //   7: aload_1
      //   8: astore_2
      //   9: aload_0
      //   10: iconst_0
      //   11: iconst_1
      //   12: invokevirtual 57	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:compareAndSet	(ZZ)Z
      //   15: ifeq +46 -> 61
      //   18: aload_0
      //   19: getfield 40	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:disposer	Lio/reactivex/functions/Consumer;
      //   22: aload_0
      //   23: getfield 38	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:resource	Ljava/lang/Object;
      //   26: invokeinterface 63 2 0
      //   31: aload_1
      //   32: astore_2
      //   33: goto +28 -> 61
      //   36: astore_2
      //   37: aload_2
      //   38: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   41: new 85	io/reactivex/exceptions/CompositeException
      //   44: dup
      //   45: iconst_2
      //   46: anewarray 87	java/lang/Throwable
      //   49: dup
      //   50: iconst_0
      //   51: aload_1
      //   52: aastore
      //   53: dup
      //   54: iconst_1
      //   55: aload_2
      //   56: aastore
      //   57: invokespecial 90	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   60: astore_2
      //   61: aload_0
      //   62: getfield 51	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   65: invokeinterface 53 1 0
      //   70: aload_0
      //   71: getfield 36	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:downstream	Lio/reactivex/Observer;
      //   74: aload_2
      //   75: invokeinterface 81 2 0
      //   80: goto +26 -> 106
      //   83: aload_0
      //   84: getfield 36	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:downstream	Lio/reactivex/Observer;
      //   87: aload_1
      //   88: invokeinterface 81 2 0
      //   93: aload_0
      //   94: getfield 51	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   97: invokeinterface 53 1 0
      //   102: aload_0
      //   103: invokevirtual 49	io/reactivex/internal/operators/observable/ObservableUsing$UsingObserver:disposeAfter	()V
      //   106: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	107	0	this	UsingObserver
      //   0	107	1	paramThrowable	Throwable
      //   8	25	2	localThrowable1	Throwable
      //   36	20	2	localThrowable2	Throwable
      //   60	15	2	localCompositeException	CompositeException
      // Exception table:
      //   from	to	target	type
      //   18	31	36	finally
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
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
