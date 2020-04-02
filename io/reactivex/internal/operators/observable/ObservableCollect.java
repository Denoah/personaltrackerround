package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class ObservableCollect<T, U>
  extends AbstractObservableWithUpstream<T, U>
{
  final BiConsumer<? super U, ? super T> collector;
  final Callable<? extends U> initialSupplier;
  
  public ObservableCollect(ObservableSource<T> paramObservableSource, Callable<? extends U> paramCallable, BiConsumer<? super U, ? super T> paramBiConsumer)
  {
    super(paramObservableSource);
    this.initialSupplier = paramCallable;
    this.collector = paramBiConsumer;
  }
  
  protected void subscribeActual(Observer<? super U> paramObserver)
  {
    try
    {
      Object localObject = ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initialSupplier returned a null value");
      this.source.subscribe(new CollectObserver(paramObserver, localObject, this.collector));
      return;
    }
    finally
    {
      EmptyDisposable.error(localThrowable, paramObserver);
    }
  }
  
  static final class CollectObserver<T, U>
    implements Observer<T>, Disposable
  {
    final BiConsumer<? super U, ? super T> collector;
    boolean done;
    final Observer<? super U> downstream;
    final U u;
    Disposable upstream;
    
    CollectObserver(Observer<? super U> paramObserver, U paramU, BiConsumer<? super U, ? super T> paramBiConsumer)
    {
      this.downstream = paramObserver;
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
      this.downstream.onNext(this.u);
      this.downstream.onComplete();
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
      //   1: getfield 51	io/reactivex/internal/operators/observable/ObservableCollect$CollectObserver:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 34	io/reactivex/internal/operators/observable/ObservableCollect$CollectObserver:collector	Lio/reactivex/functions/BiConsumer;
      //   12: aload_0
      //   13: getfield 36	io/reactivex/internal/operators/observable/ObservableCollect$CollectObserver:u	Ljava/lang/Object;
      //   16: aload_1
      //   17: invokeinterface 70 3 0
      //   22: goto +18 -> 40
      //   25: astore_1
      //   26: aload_0
      //   27: getfield 42	io/reactivex/internal/operators/observable/ObservableCollect$CollectObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   30: invokeinterface 44 1 0
      //   35: aload_0
      //   36: aload_1
      //   37: invokevirtual 71	io/reactivex/internal/operators/observable/ObservableCollect$CollectObserver:onError	(Ljava/lang/Throwable;)V
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
