package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableBufferBoundarySupplier<T, U extends Collection<? super T>, B>
  extends AbstractObservableWithUpstream<T, U>
{
  final Callable<? extends ObservableSource<B>> boundarySupplier;
  final Callable<U> bufferSupplier;
  
  public ObservableBufferBoundarySupplier(ObservableSource<T> paramObservableSource, Callable<? extends ObservableSource<B>> paramCallable, Callable<U> paramCallable1)
  {
    super(paramObservableSource);
    this.boundarySupplier = paramCallable;
    this.bufferSupplier = paramCallable1;
  }
  
  protected void subscribeActual(Observer<? super U> paramObserver)
  {
    this.source.subscribe(new BufferBoundarySupplierObserver(new SerializedObserver(paramObserver), this.bufferSupplier, this.boundarySupplier));
  }
  
  static final class BufferBoundaryObserver<T, U extends Collection<? super T>, B>
    extends DisposableObserver<B>
  {
    boolean once;
    final ObservableBufferBoundarySupplier.BufferBoundarySupplierObserver<T, U, B> parent;
    
    BufferBoundaryObserver(ObservableBufferBoundarySupplier.BufferBoundarySupplierObserver<T, U, B> paramBufferBoundarySupplierObserver)
    {
      this.parent = paramBufferBoundarySupplierObserver;
    }
    
    public void onComplete()
    {
      if (this.once) {
        return;
      }
      this.once = true;
      this.parent.next();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.once)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.once = true;
      this.parent.onError(paramThrowable);
    }
    
    public void onNext(B paramB)
    {
      if (this.once) {
        return;
      }
      this.once = true;
      dispose();
      this.parent.next();
    }
  }
  
  static final class BufferBoundarySupplierObserver<T, U extends Collection<? super T>, B>
    extends QueueDrainObserver<T, U, U>
    implements Observer<T>, Disposable
  {
    final Callable<? extends ObservableSource<B>> boundarySupplier;
    U buffer;
    final Callable<U> bufferSupplier;
    final AtomicReference<Disposable> other = new AtomicReference();
    Disposable upstream;
    
    BufferBoundarySupplierObserver(Observer<? super U> paramObserver, Callable<U> paramCallable, Callable<? extends ObservableSource<B>> paramCallable1)
    {
      super(new MpscLinkedQueue());
      this.bufferSupplier = paramCallable;
      this.boundarySupplier = paramCallable1;
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
        this.upstream.dispose();
        disposeOther();
        if (enter()) {
          this.queue.clear();
        }
      }
    }
    
    void disposeOther()
    {
      DisposableHelper.dispose(this.other);
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
          ObservableSource localObservableSource = (ObservableSource)ObjectHelper.requireNonNull(this.boundarySupplier.call(), "The boundary ObservableSource supplied is null");
          ObservableBufferBoundarySupplier.BufferBoundaryObserver localBufferBoundaryObserver = new ObservableBufferBoundarySupplier.BufferBoundaryObserver(this);
          if (DisposableHelper.replace(this.other, localBufferBoundaryObserver)) {
            try
            {
              Collection localCollection2 = this.buffer;
              if (localCollection2 == null) {
                return;
              }
              this.buffer = localCollection1;
              localObservableSource.subscribe(localBufferBoundaryObserver);
              fastPathEmit(localCollection2, false, this);
            }
            finally {}
          }
          return;
        }
        finally
        {
          Exceptions.throwIfFatal(localThrowable1);
          this.cancelled = true;
          this.upstream.dispose();
          this.downstream.onError(localThrowable1);
          return;
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable2);
        dispose();
        this.downstream.onError(localThrowable2);
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
      //   1: getfield 70	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   4: aload_1
      //   5: invokestatic 166	io/reactivex/internal/disposables/DisposableHelper:validate	(Lio/reactivex/disposables/Disposable;Lio/reactivex/disposables/Disposable;)Z
      //   8: ifeq +138 -> 146
      //   11: aload_0
      //   12: aload_1
      //   13: putfield 70	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   16: aload_0
      //   17: getfield 58	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver:downstream	Lio/reactivex/Observer;
      //   20: astore_2
      //   21: aload_0
      //   22: getfield 42	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver:bufferSupplier	Ljava/util/concurrent/Callable;
      //   25: invokeinterface 101 1 0
      //   30: ldc 103
      //   32: invokestatic 109	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   35: checkcast 51	java/util/Collection
      //   38: astore_3
      //   39: aload_0
      //   40: aload_3
      //   41: putfield 124	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver:buffer	Ljava/util/Collection;
      //   44: aload_0
      //   45: getfield 44	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver:boundarySupplier	Ljava/util/concurrent/Callable;
      //   48: invokeinterface 101 1 0
      //   53: ldc 111
      //   55: invokestatic 109	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   58: checkcast 113	io/reactivex/ObservableSource
      //   61: astore_3
      //   62: new 115	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundaryObserver
      //   65: dup
      //   66: aload_0
      //   67: invokespecial 118	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundaryObserver:<init>	(Lio/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver;)V
      //   70: astore_1
      //   71: aload_0
      //   72: getfield 40	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver:other	Ljava/util/concurrent/atomic/AtomicReference;
      //   75: aload_1
      //   76: invokevirtual 169	java/util/concurrent/atomic/AtomicReference:set	(Ljava/lang/Object;)V
      //   79: aload_2
      //   80: aload_0
      //   81: invokeinterface 171 2 0
      //   86: aload_0
      //   87: getfield 68	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver:cancelled	Z
      //   90: ifne +56 -> 146
      //   93: aload_3
      //   94: aload_1
      //   95: invokeinterface 128 2 0
      //   100: goto +46 -> 146
      //   103: astore_3
      //   104: aload_3
      //   105: invokestatic 138	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   108: aload_0
      //   109: iconst_1
      //   110: putfield 68	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver:cancelled	Z
      //   113: aload_1
      //   114: invokeinterface 72 1 0
      //   119: aload_3
      //   120: aload_2
      //   121: invokestatic 177	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/Observer;)V
      //   124: return
      //   125: astore_3
      //   126: aload_3
      //   127: invokestatic 138	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   130: aload_0
      //   131: iconst_1
      //   132: putfield 68	io/reactivex/internal/operators/observable/ObservableBufferBoundarySupplier$BufferBoundarySupplierObserver:cancelled	Z
      //   135: aload_1
      //   136: invokeinterface 72 1 0
      //   141: aload_3
      //   142: aload_2
      //   143: invokestatic 177	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/Observer;)V
      //   146: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	147	0	this	BufferBoundarySupplierObserver
      //   0	147	1	paramDisposable	Disposable
      //   20	123	2	localObserver	Observer
      //   38	56	3	localObject	Object
      //   103	17	3	localThrowable1	Throwable
      //   125	17	3	localThrowable2	Throwable
      // Exception table:
      //   from	to	target	type
      //   44	62	103	finally
      //   21	39	125	finally
    }
  }
}
