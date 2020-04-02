package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObservableBuffer<T, U extends Collection<? super T>>
  extends AbstractObservableWithUpstream<T, U>
{
  final Callable<U> bufferSupplier;
  final int count;
  final int skip;
  
  public ObservableBuffer(ObservableSource<T> paramObservableSource, int paramInt1, int paramInt2, Callable<U> paramCallable)
  {
    super(paramObservableSource);
    this.count = paramInt1;
    this.skip = paramInt2;
    this.bufferSupplier = paramCallable;
  }
  
  protected void subscribeActual(Observer<? super U> paramObserver)
  {
    int i = this.skip;
    int j = this.count;
    if (i == j)
    {
      paramObserver = new BufferExactObserver(paramObserver, j, this.bufferSupplier);
      if (paramObserver.createBuffer()) {
        this.source.subscribe(paramObserver);
      }
    }
    else
    {
      this.source.subscribe(new BufferSkipObserver(paramObserver, this.count, this.skip, this.bufferSupplier));
    }
  }
  
  static final class BufferExactObserver<T, U extends Collection<? super T>>
    implements Observer<T>, Disposable
  {
    U buffer;
    final Callable<U> bufferSupplier;
    final int count;
    final Observer<? super U> downstream;
    int size;
    Disposable upstream;
    
    BufferExactObserver(Observer<? super U> paramObserver, int paramInt, Callable<U> paramCallable)
    {
      this.downstream = paramObserver;
      this.count = paramInt;
      this.bufferSupplier = paramCallable;
    }
    
    boolean createBuffer()
    {
      try
      {
        Collection localCollection = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "Empty buffer supplied");
        this.buffer = localCollection;
        return true;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.buffer = null;
        Disposable localDisposable = this.upstream;
        if (localDisposable == null)
        {
          EmptyDisposable.error(localThrowable, this.downstream);
        }
        else
        {
          localDisposable.dispose();
          this.downstream.onError(localThrowable);
        }
      }
      return false;
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
      Collection localCollection = this.buffer;
      if (localCollection != null)
      {
        this.buffer = null;
        if (!localCollection.isEmpty()) {
          this.downstream.onNext(localCollection);
        }
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.buffer = null;
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      Collection localCollection = this.buffer;
      if (localCollection != null)
      {
        localCollection.add(paramT);
        int i = this.size + 1;
        this.size = i;
        if (i >= this.count)
        {
          this.downstream.onNext(localCollection);
          this.size = 0;
          createBuffer();
        }
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
  
  static final class BufferSkipObserver<T, U extends Collection<? super T>>
    extends AtomicBoolean
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = -8223395059921494546L;
    final Callable<U> bufferSupplier;
    final ArrayDeque<U> buffers;
    final int count;
    final Observer<? super U> downstream;
    long index;
    final int skip;
    Disposable upstream;
    
    BufferSkipObserver(Observer<? super U> paramObserver, int paramInt1, int paramInt2, Callable<U> paramCallable)
    {
      this.downstream = paramObserver;
      this.count = paramInt1;
      this.skip = paramInt2;
      this.bufferSupplier = paramCallable;
      this.buffers = new ArrayDeque();
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
      while (!this.buffers.isEmpty()) {
        this.downstream.onNext(this.buffers.poll());
      }
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.buffers.clear();
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 84	io/reactivex/internal/operators/observable/ObservableBuffer$BufferSkipObserver:index	J
      //   4: lstore_2
      //   5: aload_0
      //   6: lconst_1
      //   7: lload_2
      //   8: ladd
      //   9: putfield 84	io/reactivex/internal/operators/observable/ObservableBuffer$BufferSkipObserver:index	J
      //   12: lload_2
      //   13: aload_0
      //   14: getfield 42	io/reactivex/internal/operators/observable/ObservableBuffer$BufferSkipObserver:skip	I
      //   17: i2l
      //   18: lrem
      //   19: lconst_0
      //   20: lcmp
      //   21: ifne +63 -> 84
      //   24: aload_0
      //   25: getfield 44	io/reactivex/internal/operators/observable/ObservableBuffer$BufferSkipObserver:bufferSupplier	Ljava/util/concurrent/Callable;
      //   28: invokeinterface 89 1 0
      //   33: ldc 91
      //   35: invokestatic 97	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   38: checkcast 99	java/util/Collection
      //   41: astore 4
      //   43: aload_0
      //   44: getfield 49	io/reactivex/internal/operators/observable/ObservableBuffer$BufferSkipObserver:buffers	Ljava/util/ArrayDeque;
      //   47: aload 4
      //   49: invokevirtual 103	java/util/ArrayDeque:offer	(Ljava/lang/Object;)Z
      //   52: pop
      //   53: goto +31 -> 84
      //   56: astore_1
      //   57: aload_0
      //   58: getfield 49	io/reactivex/internal/operators/observable/ObservableBuffer$BufferSkipObserver:buffers	Ljava/util/ArrayDeque;
      //   61: invokevirtual 80	java/util/ArrayDeque:clear	()V
      //   64: aload_0
      //   65: getfield 55	io/reactivex/internal/operators/observable/ObservableBuffer$BufferSkipObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   68: invokeinterface 57 1 0
      //   73: aload_0
      //   74: getfield 38	io/reactivex/internal/operators/observable/ObservableBuffer$BufferSkipObserver:downstream	Lio/reactivex/Observer;
      //   77: aload_1
      //   78: invokeinterface 82 2 0
      //   83: return
      //   84: aload_0
      //   85: getfield 49	io/reactivex/internal/operators/observable/ObservableBuffer$BufferSkipObserver:buffers	Ljava/util/ArrayDeque;
      //   88: invokevirtual 107	java/util/ArrayDeque:iterator	()Ljava/util/Iterator;
      //   91: astore 5
      //   93: aload 5
      //   95: invokeinterface 112 1 0
      //   100: ifeq +59 -> 159
      //   103: aload 5
      //   105: invokeinterface 115 1 0
      //   110: checkcast 99	java/util/Collection
      //   113: astore 4
      //   115: aload 4
      //   117: aload_1
      //   118: invokeinterface 118 2 0
      //   123: pop
      //   124: aload_0
      //   125: getfield 40	io/reactivex/internal/operators/observable/ObservableBuffer$BufferSkipObserver:count	I
      //   128: aload 4
      //   130: invokeinterface 122 1 0
      //   135: if_icmpgt -42 -> 93
      //   138: aload 5
      //   140: invokeinterface 125 1 0
      //   145: aload_0
      //   146: getfield 38	io/reactivex/internal/operators/observable/ObservableBuffer$BufferSkipObserver:downstream	Lio/reactivex/Observer;
      //   149: aload 4
      //   151: invokeinterface 73 2 0
      //   156: goto -63 -> 93
      //   159: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	160	0	this	BufferSkipObserver
      //   0	160	1	paramT	T
      //   4	9	2	l	long
      //   41	109	4	localCollection	Collection
      //   91	48	5	localIterator	java.util.Iterator
      // Exception table:
      //   from	to	target	type
      //   24	43	56	finally
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
