package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;

public final class ObservableDoOnEach<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Action onAfterTerminate;
  final Action onComplete;
  final Consumer<? super Throwable> onError;
  final Consumer<? super T> onNext;
  
  public ObservableDoOnEach(ObservableSource<T> paramObservableSource, Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction1, Action paramAction2)
  {
    super(paramObservableSource);
    this.onNext = paramConsumer;
    this.onError = paramConsumer1;
    this.onComplete = paramAction1;
    this.onAfterTerminate = paramAction2;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new DoOnEachObserver(paramObserver, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
  }
  
  static final class DoOnEachObserver<T>
    implements Observer<T>, Disposable
  {
    boolean done;
    final Observer<? super T> downstream;
    final Action onAfterTerminate;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;
    Disposable upstream;
    
    DoOnEachObserver(Observer<? super T> paramObserver, Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction1, Action paramAction2)
    {
      this.downstream = paramObserver;
      this.onNext = paramConsumer;
      this.onError = paramConsumer1;
      this.onComplete = paramAction1;
      this.onAfterTerminate = paramAction2;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    /* Error */
    public void onComplete()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 56	io/reactivex/internal/operators/observable/ObservableDoOnEach$DoOnEachObserver:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 40	io/reactivex/internal/operators/observable/ObservableDoOnEach$DoOnEachObserver:onComplete	Lio/reactivex/functions/Action;
      //   12: invokeinterface 61 1 0
      //   17: aload_0
      //   18: iconst_1
      //   19: putfield 56	io/reactivex/internal/operators/observable/ObservableDoOnEach$DoOnEachObserver:done	Z
      //   22: aload_0
      //   23: getfield 34	io/reactivex/internal/operators/observable/ObservableDoOnEach$DoOnEachObserver:downstream	Lio/reactivex/Observer;
      //   26: invokeinterface 63 1 0
      //   31: aload_0
      //   32: getfield 42	io/reactivex/internal/operators/observable/ObservableDoOnEach$DoOnEachObserver:onAfterTerminate	Lio/reactivex/functions/Action;
      //   35: invokeinterface 61 1 0
      //   40: goto +12 -> 52
      //   43: astore_1
      //   44: aload_1
      //   45: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   48: aload_1
      //   49: invokestatic 73	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   52: return
      //   53: astore_1
      //   54: aload_1
      //   55: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   58: aload_0
      //   59: aload_1
      //   60: invokevirtual 74	io/reactivex/internal/operators/observable/ObservableDoOnEach$DoOnEachObserver:onError	(Ljava/lang/Throwable;)V
      //   63: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	64	0	this	DoOnEachObserver
      //   43	6	1	localThrowable1	Throwable
      //   53	7	1	localThrowable2	Throwable
      // Exception table:
      //   from	to	target	type
      //   31	40	43	finally
      //   8	17	53	finally
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 56	io/reactivex/internal/operators/observable/ObservableDoOnEach$DoOnEachObserver:done	Z
      //   4: ifeq +8 -> 12
      //   7: aload_1
      //   8: invokestatic 73	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   11: return
      //   12: aload_0
      //   13: iconst_1
      //   14: putfield 56	io/reactivex/internal/operators/observable/ObservableDoOnEach$DoOnEachObserver:done	Z
      //   17: aload_0
      //   18: getfield 38	io/reactivex/internal/operators/observable/ObservableDoOnEach$DoOnEachObserver:onError	Lio/reactivex/functions/Consumer;
      //   21: aload_1
      //   22: invokeinterface 80 2 0
      //   27: goto +28 -> 55
      //   30: astore_2
      //   31: aload_2
      //   32: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   35: new 82	io/reactivex/exceptions/CompositeException
      //   38: dup
      //   39: iconst_2
      //   40: anewarray 84	java/lang/Throwable
      //   43: dup
      //   44: iconst_0
      //   45: aload_1
      //   46: aastore
      //   47: dup
      //   48: iconst_1
      //   49: aload_2
      //   50: aastore
      //   51: invokespecial 87	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   54: astore_1
      //   55: aload_0
      //   56: getfield 34	io/reactivex/internal/operators/observable/ObservableDoOnEach$DoOnEachObserver:downstream	Lio/reactivex/Observer;
      //   59: aload_1
      //   60: invokeinterface 88 2 0
      //   65: aload_0
      //   66: getfield 42	io/reactivex/internal/operators/observable/ObservableDoOnEach$DoOnEachObserver:onAfterTerminate	Lio/reactivex/functions/Action;
      //   69: invokeinterface 61 1 0
      //   74: goto +12 -> 86
      //   77: astore_1
      //   78: aload_1
      //   79: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   82: aload_1
      //   83: invokestatic 73	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   86: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	87	0	this	DoOnEachObserver
      //   0	87	1	paramThrowable	Throwable
      //   30	20	2	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   17	27	30	finally
      //   65	74	77	finally
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      try
      {
        this.onNext.accept(paramT);
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.upstream.dispose();
        onError(paramT);
      }
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
