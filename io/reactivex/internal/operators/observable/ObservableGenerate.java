package io.reactivex.internal.operators.observable;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

public final class ObservableGenerate<T, S>
  extends Observable<T>
{
  final Consumer<? super S> disposeState;
  final BiFunction<S, Emitter<T>, S> generator;
  final Callable<S> stateSupplier;
  
  public ObservableGenerate(Callable<S> paramCallable, BiFunction<S, Emitter<T>, S> paramBiFunction, Consumer<? super S> paramConsumer)
  {
    this.stateSupplier = paramCallable;
    this.generator = paramBiFunction;
    this.disposeState = paramConsumer;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    try
    {
      Object localObject = this.stateSupplier.call();
      localObject = new GeneratorDisposable(paramObserver, this.generator, this.disposeState, localObject);
      paramObserver.onSubscribe((Disposable)localObject);
      ((GeneratorDisposable)localObject).run();
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramObserver);
    }
  }
  
  static final class GeneratorDisposable<T, S>
    implements Emitter<T>, Disposable
  {
    volatile boolean cancelled;
    final Consumer<? super S> disposeState;
    final Observer<? super T> downstream;
    final BiFunction<S, ? super Emitter<T>, S> generator;
    boolean hasNext;
    S state;
    boolean terminate;
    
    GeneratorDisposable(Observer<? super T> paramObserver, BiFunction<S, ? super Emitter<T>, S> paramBiFunction, Consumer<? super S> paramConsumer, S paramS)
    {
      this.downstream = paramObserver;
      this.generator = paramBiFunction;
      this.disposeState = paramConsumer;
      this.state = paramS;
    }
    
    /* Error */
    private void dispose(S paramS)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 39	io/reactivex/internal/operators/observable/ObservableGenerate$GeneratorDisposable:disposeState	Lio/reactivex/functions/Consumer;
      //   4: aload_1
      //   5: invokeinterface 51 2 0
      //   10: goto +12 -> 22
      //   13: astore_1
      //   14: aload_1
      //   15: invokestatic 57	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   18: aload_1
      //   19: invokestatic 62	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   22: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	23	0	this	GeneratorDisposable
      //   0	23	1	paramS	S
      // Exception table:
      //   from	to	target	type
      //   0	10	13	finally
    }
    
    public void dispose()
    {
      this.cancelled = true;
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      if (!this.terminate)
      {
        this.terminate = true;
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.terminate)
      {
        RxJavaPlugins.onError(paramThrowable);
      }
      else
      {
        Object localObject = paramThrowable;
        if (paramThrowable == null) {
          localObject = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        }
        this.terminate = true;
        this.downstream.onError((Throwable)localObject);
      }
    }
    
    public void onNext(T paramT)
    {
      if (!this.terminate) {
        if (this.hasNext)
        {
          onError(new IllegalStateException("onNext already called in this generate turn"));
        }
        else if (paramT == null)
        {
          onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
        }
        else
        {
          this.hasNext = true;
          this.downstream.onNext(paramT);
        }
      }
    }
    
    public void run()
    {
      Object localObject1 = this.state;
      if (this.cancelled)
      {
        this.state = null;
        dispose(localObject1);
        return;
      }
      BiFunction localBiFunction = this.generator;
      for (;;)
      {
        if (this.cancelled)
        {
          this.state = null;
          dispose(localObject1);
          return;
        }
        this.hasNext = false;
        try
        {
          Object localObject2 = localBiFunction.apply(localObject1, this);
          localObject1 = localObject2;
          if (this.terminate)
          {
            this.cancelled = true;
            this.state = null;
            dispose(localObject2);
            return;
          }
        }
        finally
        {
          Exceptions.throwIfFatal(localThrowable);
          this.state = null;
          this.cancelled = true;
          onError(localThrowable);
          dispose(localObject1);
        }
      }
    }
  }
}
