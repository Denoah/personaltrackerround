package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import java.util.Collection;
import java.util.concurrent.Callable;

public final class ObservableBufferExactBoundary<T, U extends Collection<? super T>, B>
  extends AbstractObservableWithUpstream<T, U>
{
  final ObservableSource<B> boundary;
  final Callable<U> bufferSupplier;
  
  public ObservableBufferExactBoundary(ObservableSource<T> paramObservableSource, ObservableSource<B> paramObservableSource1, Callable<U> paramCallable)
  {
    super(paramObservableSource);
    this.boundary = paramObservableSource1;
    this.bufferSupplier = paramCallable;
  }
  
  protected void subscribeActual(Observer<? super U> paramObserver)
  {
    this.source.subscribe(new BufferExactBoundaryObserver(new SerializedObserver(paramObserver), this.bufferSupplier, this.boundary));
  }
  
  static final class BufferBoundaryObserver<T, U extends Collection<? super T>, B>
    extends DisposableObserver<B>
  {
    final ObservableBufferExactBoundary.BufferExactBoundaryObserver<T, U, B> parent;
    
    BufferBoundaryObserver(ObservableBufferExactBoundary.BufferExactBoundaryObserver<T, U, B> paramBufferExactBoundaryObserver)
    {
      this.parent = paramBufferExactBoundaryObserver;
    }
    
    public void onComplete()
    {
      this.parent.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.onError(paramThrowable);
    }
    
    public void onNext(B paramB)
    {
      this.parent.next();
    }
  }
  
  static final class BufferExactBoundaryObserver<T, U extends Collection<? super T>, B>
    extends QueueDrainObserver<T, U, U>
    implements Observer<T>, Disposable
  {
    final ObservableSource<B> boundary;
    U buffer;
    final Callable<U> bufferSupplier;
    Disposable other;
    Disposable upstream;
    
    BufferExactBoundaryObserver(Observer<? super U> paramObserver, Callable<U> paramCallable, ObservableSource<B> paramObservableSource)
    {
      super(new MpscLinkedQueue());
      this.bufferSupplier = paramCallable;
      this.boundary = paramObservableSource;
    }
    
    public void accept(Observer<? super U> paramObserver, U paramU)
    {
      this.downstream.onNext(paramU);
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.other.dispose();
        this.upstream.dispose();
        if (enter()) {
          this.queue.clear();
        }
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    void next()
    {
      try
      {
        Collection localCollection1 = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
        try
        {
          Collection localCollection2 = this.buffer;
          if (localCollection2 == null) {
            return;
          }
          this.buffer = localCollection1;
          fastPathEmit(localCollection2, false, this);
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        dispose();
      }
    }
    
    public void onComplete()
    {
      try
      {
        Collection localCollection = this.buffer;
        if (localCollection == null) {
          return;
        }
        this.buffer = null;
        this.queue.offer(localCollection);
        this.done = true;
        if (enter()) {
          QueueDrainHelper.drainLoop(this.queue, this.downstream, false, this, this);
        }
        return;
      }
      finally {}
    }
    
    public void onError(Throwable paramThrowable)
    {
      dispose();
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      try
      {
        Collection localCollection = this.buffer;
        if (localCollection == null) {
          return;
        }
        localCollection.add(paramT);
        return;
      }
      finally {}
    }
    
    /* Error */
    public void onSubscribe(Disposable paramDisposable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 68	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   4: aload_1
      //   5: invokestatic 139	io/reactivex/internal/disposables/DisposableHelper:validate	(Lio/reactivex/disposables/Disposable;Lio/reactivex/disposables/Disposable;)Z
      //   8: ifeq +99 -> 107
      //   11: aload_0
      //   12: aload_1
      //   13: putfield 68	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   16: aload_0
      //   17: getfield 36	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver:bufferSupplier	Ljava/util/concurrent/Callable;
      //   20: invokeinterface 89 1 0
      //   25: ldc 91
      //   27: invokestatic 97	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   30: checkcast 45	java/util/Collection
      //   33: astore_2
      //   34: aload_0
      //   35: aload_2
      //   36: putfield 99	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver:buffer	Ljava/util/Collection;
      //   39: new 141	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferBoundaryObserver
      //   42: dup
      //   43: aload_0
      //   44: invokespecial 144	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferBoundaryObserver:<init>	(Lio/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver;)V
      //   47: astore_1
      //   48: aload_0
      //   49: aload_1
      //   50: putfield 64	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver:other	Lio/reactivex/disposables/Disposable;
      //   53: aload_0
      //   54: getfield 52	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver:downstream	Lio/reactivex/Observer;
      //   57: aload_0
      //   58: invokeinterface 146 2 0
      //   63: aload_0
      //   64: getfield 62	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver:cancelled	Z
      //   67: ifne +40 -> 107
      //   70: aload_0
      //   71: getfield 38	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver:boundary	Lio/reactivex/ObservableSource;
      //   74: aload_1
      //   75: invokeinterface 152 2 0
      //   80: goto +27 -> 107
      //   83: astore_2
      //   84: aload_2
      //   85: invokestatic 109	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   88: aload_0
      //   89: iconst_1
      //   90: putfield 62	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver:cancelled	Z
      //   93: aload_1
      //   94: invokeinterface 66 1 0
      //   99: aload_2
      //   100: aload_0
      //   101: getfield 52	io/reactivex/internal/operators/observable/ObservableBufferExactBoundary$BufferExactBoundaryObserver:downstream	Lio/reactivex/Observer;
      //   104: invokestatic 158	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/Observer;)V
      //   107: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	108	0	this	BufferExactBoundaryObserver
      //   0	108	1	paramDisposable	Disposable
      //   33	3	2	localCollection	Collection
      //   83	17	2	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   16	34	83	finally
    }
  }
}
