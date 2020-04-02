package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class ObservableCollectSingle<T, U>
  extends Single<U>
  implements FuseToObservable<U>
{
  final BiConsumer<? super U, ? super T> collector;
  final Callable<? extends U> initialSupplier;
  final ObservableSource<T> source;
  
  public ObservableCollectSingle(ObservableSource<T> paramObservableSource, Callable<? extends U> paramCallable, BiConsumer<? super U, ? super T> paramBiConsumer)
  {
    this.source = paramObservableSource;
    this.initialSupplier = paramCallable;
    this.collector = paramBiConsumer;
  }
  
  public Observable<U> fuseToObservable()
  {
    return RxJavaPlugins.onAssembly(new ObservableCollect(this.source, this.initialSupplier, this.collector));
  }
  
  protected void subscribeActual(SingleObserver<? super U> paramSingleObserver)
  {
    try
    {
      Object localObject = ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initialSupplier returned a null value");
      this.source.subscribe(new CollectObserver(paramSingleObserver, localObject, this.collector));
      return;
    }
    finally
    {
      EmptyDisposable.error(localThrowable, paramSingleObserver);
    }
  }
  
  static final class CollectObserver<T, U>
    implements Observer<T>, Disposable
  {
    final BiConsumer<? super U, ? super T> collector;
    boolean done;
    final SingleObserver<? super U> downstream;
    final U u;
    Disposable upstream;
    
    CollectObserver(SingleObserver<? super U> paramSingleObserver, U paramU, BiConsumer<? super U, ? super T> paramBiConsumer)
    {
      this.downstream = paramSingleObserver;
      this.collector = paramBiConsumer;
      this.u = paramU;
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
      if (this.done) {
        return;
      }
      this.done = true;
      this.downstream.onSuccess(this.u);
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
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 51	io/reactivex/internal/operators/observable/ObservableCollectSingle$CollectObserver:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 34	io/reactivex/internal/operators/observable/ObservableCollectSingle$CollectObserver:collector	Lio/reactivex/functions/BiConsumer;
      //   12: aload_0
      //   13: getfield 36	io/reactivex/internal/operators/observable/ObservableCollectSingle$CollectObserver:u	Ljava/lang/Object;
      //   16: aload_1
      //   17: invokeinterface 71 3 0
      //   22: goto +18 -> 40
      //   25: astore_1
      //   26: aload_0
      //   27: getfield 42	io/reactivex/internal/operators/observable/ObservableCollectSingle$CollectObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   30: invokeinterface 44 1 0
      //   35: aload_0
      //   36: aload_1
      //   37: invokevirtual 72	io/reactivex/internal/operators/observable/ObservableCollectSingle$CollectObserver:onError	(Ljava/lang/Throwable;)V
      //   40: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	41	0	this	CollectObserver
      //   0	41	1	paramT	T
      // Exception table:
      //   from	to	target	type
      //   8	22	25	finally
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
