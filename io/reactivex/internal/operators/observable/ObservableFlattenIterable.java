package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableFlattenIterable<T, R>
  extends AbstractObservableWithUpstream<T, R>
{
  final Function<? super T, ? extends Iterable<? extends R>> mapper;
  
  public ObservableFlattenIterable(ObservableSource<T> paramObservableSource, Function<? super T, ? extends Iterable<? extends R>> paramFunction)
  {
    super(paramObservableSource);
    this.mapper = paramFunction;
  }
  
  protected void subscribeActual(Observer<? super R> paramObserver)
  {
    this.source.subscribe(new FlattenIterableObserver(paramObserver, this.mapper));
  }
  
  static final class FlattenIterableObserver<T, R>
    implements Observer<T>, Disposable
  {
    final Observer<? super R> downstream;
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    Disposable upstream;
    
    FlattenIterableObserver(Observer<? super R> paramObserver, Function<? super T, ? extends Iterable<? extends R>> paramFunction)
    {
      this.downstream = paramObserver;
      this.mapper = paramFunction;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      this.upstream = DisposableHelper.DISPOSED;
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      if (this.upstream == DisposableHelper.DISPOSED) {
        return;
      }
      this.upstream = DisposableHelper.DISPOSED;
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.upstream == DisposableHelper.DISPOSED)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.upstream = DisposableHelper.DISPOSED;
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 35	io/reactivex/internal/operators/observable/ObservableFlattenIterable$FlattenIterableObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   4: getstatic 43	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
      //   7: if_acmpne +4 -> 11
      //   10: return
      //   11: aload_0
      //   12: getfield 29	io/reactivex/internal/operators/observable/ObservableFlattenIterable$FlattenIterableObserver:mapper	Lio/reactivex/functions/Function;
      //   15: aload_1
      //   16: invokeinterface 65 2 0
      //   21: checkcast 67	java/lang/Iterable
      //   24: invokeinterface 71 1 0
      //   29: astore_2
      //   30: aload_0
      //   31: getfield 27	io/reactivex/internal/operators/observable/ObservableFlattenIterable$FlattenIterableObserver:downstream	Lio/reactivex/Observer;
      //   34: astore_3
      //   35: aload_2
      //   36: invokeinterface 76 1 0
      //   41: istore 4
      //   43: iload 4
      //   45: ifeq +44 -> 89
      //   48: aload_2
      //   49: invokeinterface 80 1 0
      //   54: ldc 82
      //   56: invokestatic 88	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   59: astore_1
      //   60: aload_3
      //   61: aload_1
      //   62: invokeinterface 90 2 0
      //   67: goto -32 -> 35
      //   70: astore_1
      //   71: aload_1
      //   72: invokestatic 95	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   75: aload_0
      //   76: getfield 35	io/reactivex/internal/operators/observable/ObservableFlattenIterable$FlattenIterableObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   79: invokeinterface 37 1 0
      //   84: aload_0
      //   85: aload_1
      //   86: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableFlattenIterable$FlattenIterableObserver:onError	(Ljava/lang/Throwable;)V
      //   89: return
      //   90: astore_1
      //   91: aload_1
      //   92: invokestatic 95	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   95: aload_0
      //   96: getfield 35	io/reactivex/internal/operators/observable/ObservableFlattenIterable$FlattenIterableObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   99: invokeinterface 37 1 0
      //   104: aload_0
      //   105: aload_1
      //   106: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableFlattenIterable$FlattenIterableObserver:onError	(Ljava/lang/Throwable;)V
      //   109: return
      //   110: astore_1
      //   111: aload_1
      //   112: invokestatic 95	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   115: aload_0
      //   116: getfield 35	io/reactivex/internal/operators/observable/ObservableFlattenIterable$FlattenIterableObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   119: invokeinterface 37 1 0
      //   124: aload_0
      //   125: aload_1
      //   126: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableFlattenIterable$FlattenIterableObserver:onError	(Ljava/lang/Throwable;)V
      //   129: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	130	0	this	FlattenIterableObserver
      //   0	130	1	paramT	T
      //   29	20	2	localIterator	java.util.Iterator
      //   34	27	3	localObserver	Observer
      //   41	3	4	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   48	60	70	finally
      //   35	43	90	finally
      //   11	30	110	finally
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
