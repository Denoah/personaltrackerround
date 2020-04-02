package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.observers.SerializedObserver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableBufferTimed<T, U extends Collection<? super T>>
  extends AbstractObservableWithUpstream<T, U>
{
  final Callable<U> bufferSupplier;
  final int maxSize;
  final boolean restartTimerOnMaxSize;
  final Scheduler scheduler;
  final long timeskip;
  final long timespan;
  final TimeUnit unit;
  
  public ObservableBufferTimed(ObservableSource<T> paramObservableSource, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler, Callable<U> paramCallable, int paramInt, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.timespan = paramLong1;
    this.timeskip = paramLong2;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.bufferSupplier = paramCallable;
    this.maxSize = paramInt;
    this.restartTimerOnMaxSize = paramBoolean;
  }
  
  protected void subscribeActual(Observer<? super U> paramObserver)
  {
    if ((this.timespan == this.timeskip) && (this.maxSize == Integer.MAX_VALUE))
    {
      this.source.subscribe(new BufferExactUnboundedObserver(new SerializedObserver(paramObserver), this.bufferSupplier, this.timespan, this.unit, this.scheduler));
      return;
    }
    Scheduler.Worker localWorker = this.scheduler.createWorker();
    if (this.timespan == this.timeskip)
    {
      this.source.subscribe(new BufferExactBoundedObserver(new SerializedObserver(paramObserver), this.bufferSupplier, this.timespan, this.unit, this.maxSize, this.restartTimerOnMaxSize, localWorker));
      return;
    }
    this.source.subscribe(new BufferSkipBoundedObserver(new SerializedObserver(paramObserver), this.bufferSupplier, this.timespan, this.timeskip, this.unit, localWorker));
  }
  
  static final class BufferExactBoundedObserver<T, U extends Collection<? super T>>
    extends QueueDrainObserver<T, U, U>
    implements Runnable, Disposable
  {
    U buffer;
    final Callable<U> bufferSupplier;
    long consumerIndex;
    final int maxSize;
    long producerIndex;
    final boolean restartTimerOnMaxSize;
    Disposable timer;
    final long timespan;
    final TimeUnit unit;
    Disposable upstream;
    final Scheduler.Worker w;
    
    BufferExactBoundedObserver(Observer<? super U> paramObserver, Callable<U> paramCallable, long paramLong, TimeUnit paramTimeUnit, int paramInt, boolean paramBoolean, Scheduler.Worker paramWorker)
    {
      super(new MpscLinkedQueue());
      this.bufferSupplier = paramCallable;
      this.timespan = paramLong;
      this.unit = paramTimeUnit;
      this.maxSize = paramInt;
      this.restartTimerOnMaxSize = paramBoolean;
      this.w = paramWorker;
    }
    
    public void accept(Observer<? super U> paramObserver, U paramU)
    {
      paramObserver.onNext(paramU);
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.upstream.dispose();
        this.w.dispose();
        try
        {
          this.buffer = null;
        }
        finally {}
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      this.w.dispose();
      try
      {
        Collection localCollection = this.buffer;
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
      try
      {
        this.buffer = null;
        this.downstream.onError(paramThrowable);
        this.w.dispose();
        return;
      }
      finally {}
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 85	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:buffer	Ljava/util/Collection;
      //   6: astore_2
      //   7: aload_2
      //   8: ifnonnull +6 -> 14
      //   11: aload_0
      //   12: monitorexit
      //   13: return
      //   14: aload_2
      //   15: aload_1
      //   16: invokeinterface 121 2 0
      //   21: pop
      //   22: aload_2
      //   23: invokeinterface 125 1 0
      //   28: aload_0
      //   29: getfield 51	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:maxSize	I
      //   32: if_icmpge +6 -> 38
      //   35: aload_0
      //   36: monitorexit
      //   37: return
      //   38: aload_0
      //   39: aconst_null
      //   40: putfield 85	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:buffer	Ljava/util/Collection;
      //   43: aload_0
      //   44: aload_0
      //   45: getfield 127	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:producerIndex	J
      //   48: lconst_1
      //   49: ladd
      //   50: putfield 127	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:producerIndex	J
      //   53: aload_0
      //   54: monitorexit
      //   55: aload_0
      //   56: getfield 53	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:restartTimerOnMaxSize	Z
      //   59: ifeq +12 -> 71
      //   62: aload_0
      //   63: getfield 129	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:timer	Lio/reactivex/disposables/Disposable;
      //   66: invokeinterface 80 1 0
      //   71: aload_0
      //   72: aload_2
      //   73: iconst_0
      //   74: aload_0
      //   75: invokevirtual 133	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:fastPathOrderedEmit	(Ljava/lang/Object;ZLio/reactivex/disposables/Disposable;)V
      //   78: aload_0
      //   79: getfield 45	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:bufferSupplier	Ljava/util/concurrent/Callable;
      //   82: invokeinterface 139 1 0
      //   87: ldc -115
      //   89: invokestatic 147	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   92: checkcast 62	java/util/Collection
      //   95: astore_1
      //   96: aload_0
      //   97: monitorenter
      //   98: aload_0
      //   99: aload_1
      //   100: putfield 85	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:buffer	Ljava/util/Collection;
      //   103: aload_0
      //   104: aload_0
      //   105: getfield 149	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:consumerIndex	J
      //   108: lconst_1
      //   109: ladd
      //   110: putfield 149	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:consumerIndex	J
      //   113: aload_0
      //   114: monitorexit
      //   115: aload_0
      //   116: getfield 53	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:restartTimerOnMaxSize	Z
      //   119: ifeq +28 -> 147
      //   122: aload_0
      //   123: getfield 55	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:w	Lio/reactivex/Scheduler$Worker;
      //   126: astore_1
      //   127: aload_0
      //   128: getfield 47	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:timespan	J
      //   131: lstore_3
      //   132: aload_0
      //   133: aload_1
      //   134: aload_0
      //   135: lload_3
      //   136: lload_3
      //   137: aload_0
      //   138: getfield 49	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:unit	Ljava/util/concurrent/TimeUnit;
      //   141: invokevirtual 153	io/reactivex/Scheduler$Worker:schedulePeriodically	(Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;
      //   144: putfield 129	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:timer	Lio/reactivex/disposables/Disposable;
      //   147: return
      //   148: astore_1
      //   149: aload_0
      //   150: monitorexit
      //   151: aload_1
      //   152: athrow
      //   153: astore_1
      //   154: aload_1
      //   155: invokestatic 158	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   158: aload_0
      //   159: getfield 108	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:downstream	Lio/reactivex/Observer;
      //   162: aload_1
      //   163: invokeinterface 118 2 0
      //   168: aload_0
      //   169: invokevirtual 159	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:dispose	()V
      //   172: return
      //   173: astore_1
      //   174: aload_0
      //   175: monitorexit
      //   176: aload_1
      //   177: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	178	0	this	BufferExactBoundedObserver
      //   0	178	1	paramT	T
      //   6	67	2	localCollection	Collection
      //   131	6	3	l	long
      // Exception table:
      //   from	to	target	type
      //   98	115	148	finally
      //   149	151	148	finally
      //   78	96	153	finally
      //   2	7	173	finally
      //   11	13	173	finally
      //   14	37	173	finally
      //   38	55	173	finally
      //   174	176	173	finally
    }
    
    /* Error */
    public void onSubscribe(Disposable paramDisposable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 78	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   4: aload_1
      //   5: invokestatic 168	io/reactivex/internal/disposables/DisposableHelper:validate	(Lio/reactivex/disposables/Disposable;Lio/reactivex/disposables/Disposable;)Z
      //   8: ifeq +95 -> 103
      //   11: aload_0
      //   12: aload_1
      //   13: putfield 78	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   16: aload_0
      //   17: getfield 45	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:bufferSupplier	Ljava/util/concurrent/Callable;
      //   20: invokeinterface 139 1 0
      //   25: ldc -115
      //   27: invokestatic 147	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   30: checkcast 62	java/util/Collection
      //   33: astore_2
      //   34: aload_0
      //   35: aload_2
      //   36: putfield 85	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:buffer	Ljava/util/Collection;
      //   39: aload_0
      //   40: getfield 108	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:downstream	Lio/reactivex/Observer;
      //   43: aload_0
      //   44: invokeinterface 170 2 0
      //   49: aload_0
      //   50: getfield 55	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:w	Lio/reactivex/Scheduler$Worker;
      //   53: astore_1
      //   54: aload_0
      //   55: getfield 47	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:timespan	J
      //   58: lstore_3
      //   59: aload_0
      //   60: aload_1
      //   61: aload_0
      //   62: lload_3
      //   63: lload_3
      //   64: aload_0
      //   65: getfield 49	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:unit	Ljava/util/concurrent/TimeUnit;
      //   68: invokevirtual 153	io/reactivex/Scheduler$Worker:schedulePeriodically	(Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;
      //   71: putfield 129	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:timer	Lio/reactivex/disposables/Disposable;
      //   74: goto +29 -> 103
      //   77: astore_2
      //   78: aload_2
      //   79: invokestatic 158	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   82: aload_1
      //   83: invokeinterface 80 1 0
      //   88: aload_2
      //   89: aload_0
      //   90: getfield 108	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:downstream	Lio/reactivex/Observer;
      //   93: invokestatic 176	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/Observer;)V
      //   96: aload_0
      //   97: getfield 55	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactBoundedObserver:w	Lio/reactivex/Scheduler$Worker;
      //   100: invokevirtual 83	io/reactivex/Scheduler$Worker:dispose	()V
      //   103: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	104	0	this	BufferExactBoundedObserver
      //   0	104	1	paramDisposable	Disposable
      //   33	3	2	localCollection	Collection
      //   77	12	2	localThrowable	Throwable
      //   58	6	3	l	long
      // Exception table:
      //   from	to	target	type
      //   16	34	77	finally
    }
    
    public void run()
    {
      try
      {
        Collection localCollection1 = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null buffer");
        try
        {
          Collection localCollection2 = this.buffer;
          if ((localCollection2 != null) && (this.producerIndex == this.consumerIndex))
          {
            this.buffer = localCollection1;
            fastPathOrderedEmit(localCollection2, false, this);
            return;
          }
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
  }
  
  static final class BufferExactUnboundedObserver<T, U extends Collection<? super T>>
    extends QueueDrainObserver<T, U, U>
    implements Runnable, Disposable
  {
    U buffer;
    final Callable<U> bufferSupplier;
    final Scheduler scheduler;
    final AtomicReference<Disposable> timer = new AtomicReference();
    final long timespan;
    final TimeUnit unit;
    Disposable upstream;
    
    BufferExactUnboundedObserver(Observer<? super U> paramObserver, Callable<U> paramCallable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      super(new MpscLinkedQueue());
      this.bufferSupplier = paramCallable;
      this.timespan = paramLong;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
    }
    
    public void accept(Observer<? super U> paramObserver, U paramU)
    {
      this.downstream.onNext(paramU);
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.timer);
      this.upstream.dispose();
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.timer.get() == DisposableHelper.DISPOSED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      try
      {
        Collection localCollection = this.buffer;
        this.buffer = null;
        if (localCollection != null)
        {
          this.queue.offer(localCollection);
          this.done = true;
          if (enter()) {
            QueueDrainHelper.drainLoop(this.queue, this.downstream, false, null, this);
          }
        }
        DisposableHelper.dispose(this.timer);
        return;
      }
      finally {}
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        this.buffer = null;
        this.downstream.onError(paramThrowable);
        DisposableHelper.dispose(this.timer);
        return;
      }
      finally {}
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
      //   1: getfield 81	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   4: aload_1
      //   5: invokestatic 133	io/reactivex/internal/disposables/DisposableHelper:validate	(Lio/reactivex/disposables/Disposable;Lio/reactivex/disposables/Disposable;)Z
      //   8: ifeq +108 -> 116
      //   11: aload_0
      //   12: aload_1
      //   13: putfield 81	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   16: aload_0
      //   17: getfield 46	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:bufferSupplier	Ljava/util/concurrent/Callable;
      //   20: invokeinterface 138 1 0
      //   25: ldc -116
      //   27: invokestatic 146	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   30: checkcast 59	java/util/Collection
      //   33: astore_1
      //   34: aload_0
      //   35: aload_1
      //   36: putfield 96	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:buffer	Ljava/util/Collection;
      //   39: aload_0
      //   40: getfield 66	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:downstream	Lio/reactivex/Observer;
      //   43: aload_0
      //   44: invokeinterface 148 2 0
      //   49: aload_0
      //   50: getfield 151	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:cancelled	Z
      //   53: ifne +63 -> 116
      //   56: aload_0
      //   57: getfield 52	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:scheduler	Lio/reactivex/Scheduler;
      //   60: astore_1
      //   61: aload_0
      //   62: getfield 48	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:timespan	J
      //   65: lstore_2
      //   66: aload_1
      //   67: aload_0
      //   68: lload_2
      //   69: lload_2
      //   70: aload_0
      //   71: getfield 50	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:unit	Ljava/util/concurrent/TimeUnit;
      //   74: invokevirtual 157	io/reactivex/Scheduler:schedulePeriodicallyDirect	(Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;
      //   77: astore_1
      //   78: aload_0
      //   79: getfield 44	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:timer	Ljava/util/concurrent/atomic/AtomicReference;
      //   82: aconst_null
      //   83: aload_1
      //   84: invokevirtual 161	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   87: ifne +29 -> 116
      //   90: aload_1
      //   91: invokeinterface 83 1 0
      //   96: goto +20 -> 116
      //   99: astore_1
      //   100: aload_1
      //   101: invokestatic 166	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   104: aload_0
      //   105: invokevirtual 167	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:dispose	()V
      //   108: aload_1
      //   109: aload_0
      //   110: getfield 66	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferExactUnboundedObserver:downstream	Lio/reactivex/Observer;
      //   113: invokestatic 173	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/Observer;)V
      //   116: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	117	0	this	BufferExactUnboundedObserver
      //   0	117	1	paramDisposable	Disposable
      //   65	5	2	l	long
      // Exception table:
      //   from	to	target	type
      //   16	34	99	finally
    }
    
    public void run()
    {
      try
      {
        Collection localCollection1 = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null buffer");
        try
        {
          Collection localCollection2 = this.buffer;
          if (localCollection2 != null) {
            this.buffer = localCollection1;
          }
          if (localCollection2 == null)
          {
            DisposableHelper.dispose(this.timer);
            return;
          }
          fastPathEmit(localCollection2, false, this);
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(localThrowable);
      }
    }
  }
  
  static final class BufferSkipBoundedObserver<T, U extends Collection<? super T>>
    extends QueueDrainObserver<T, U, U>
    implements Runnable, Disposable
  {
    final Callable<U> bufferSupplier;
    final List<U> buffers;
    final long timeskip;
    final long timespan;
    final TimeUnit unit;
    Disposable upstream;
    final Scheduler.Worker w;
    
    BufferSkipBoundedObserver(Observer<? super U> paramObserver, Callable<U> paramCallable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker)
    {
      super(new MpscLinkedQueue());
      this.bufferSupplier = paramCallable;
      this.timespan = paramLong1;
      this.timeskip = paramLong2;
      this.unit = paramTimeUnit;
      this.w = paramWorker;
      this.buffers = new LinkedList();
    }
    
    public void accept(Observer<? super U> paramObserver, U paramU)
    {
      paramObserver.onNext(paramU);
    }
    
    void clear()
    {
      try
      {
        this.buffers.clear();
        return;
      }
      finally {}
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        clear();
        this.upstream.dispose();
        this.w.dispose();
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      try
      {
        Object localObject1 = new java/util/ArrayList;
        ((ArrayList)localObject1).<init>(this.buffers);
        this.buffers.clear();
        Iterator localIterator = ((List)localObject1).iterator();
        while (localIterator.hasNext())
        {
          localObject1 = (Collection)localIterator.next();
          this.queue.offer(localObject1);
        }
        this.done = true;
        if (enter()) {
          QueueDrainHelper.drainLoop(this.queue, this.downstream, false, this.w, this);
        }
        return;
      }
      finally {}
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.done = true;
      clear();
      this.downstream.onError(paramThrowable);
      this.w.dispose();
    }
    
    public void onNext(T paramT)
    {
      try
      {
        Iterator localIterator = this.buffers.iterator();
        while (localIterator.hasNext()) {
          ((Collection)localIterator.next()).add(paramT);
        }
        return;
      }
      finally {}
    }
    
    /* Error */
    public void onSubscribe(Disposable paramDisposable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 95	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   4: aload_1
      //   5: invokestatic 163	io/reactivex/internal/disposables/DisposableHelper:validate	(Lio/reactivex/disposables/Disposable;Lio/reactivex/disposables/Disposable;)Z
      //   8: ifeq +123 -> 131
      //   11: aload_0
      //   12: aload_1
      //   13: putfield 95	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   16: aload_0
      //   17: getfield 45	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:bufferSupplier	Ljava/util/concurrent/Callable;
      //   20: invokeinterface 168 1 0
      //   25: ldc -86
      //   27: invokestatic 176	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   30: checkcast 72	java/util/Collection
      //   33: astore_2
      //   34: aload_0
      //   35: getfield 58	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:buffers	Ljava/util/List;
      //   38: aload_2
      //   39: invokeinterface 177 2 0
      //   44: pop
      //   45: aload_0
      //   46: getfield 141	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:downstream	Lio/reactivex/Observer;
      //   49: aload_0
      //   50: invokeinterface 179 2 0
      //   55: aload_0
      //   56: getfield 53	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:w	Lio/reactivex/Scheduler$Worker;
      //   59: astore_1
      //   60: aload_0
      //   61: getfield 49	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:timeskip	J
      //   64: lstore_3
      //   65: aload_1
      //   66: aload_0
      //   67: lload_3
      //   68: lload_3
      //   69: aload_0
      //   70: getfield 51	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:unit	Ljava/util/concurrent/TimeUnit;
      //   73: invokevirtual 183	io/reactivex/Scheduler$Worker:schedulePeriodically	(Ljava/lang/Runnable;JJLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;
      //   76: pop
      //   77: aload_0
      //   78: getfield 53	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:w	Lio/reactivex/Scheduler$Worker;
      //   81: new 17	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBufferEmit
      //   84: dup
      //   85: aload_0
      //   86: aload_2
      //   87: invokespecial 186	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver$RemoveFromBufferEmit:<init>	(Lio/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver;Ljava/util/Collection;)V
      //   90: aload_0
      //   91: getfield 47	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:timespan	J
      //   94: aload_0
      //   95: getfield 51	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:unit	Ljava/util/concurrent/TimeUnit;
      //   98: invokevirtual 190	io/reactivex/Scheduler$Worker:schedule	(Ljava/lang/Runnable;JLjava/util/concurrent/TimeUnit;)Lio/reactivex/disposables/Disposable;
      //   101: pop
      //   102: goto +29 -> 131
      //   105: astore_2
      //   106: aload_2
      //   107: invokestatic 195	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   110: aload_1
      //   111: invokeinterface 97 1 0
      //   116: aload_2
      //   117: aload_0
      //   118: getfield 141	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:downstream	Lio/reactivex/Observer;
      //   121: invokestatic 201	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/Observer;)V
      //   124: aload_0
      //   125: getfield 53	io/reactivex/internal/operators/observable/ObservableBufferTimed$BufferSkipBoundedObserver:w	Lio/reactivex/Scheduler$Worker;
      //   128: invokevirtual 100	io/reactivex/Scheduler$Worker:dispose	()V
      //   131: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	132	0	this	BufferSkipBoundedObserver
      //   0	132	1	paramDisposable	Disposable
      //   33	54	2	localCollection	Collection
      //   105	12	2	localThrowable	Throwable
      //   64	5	3	l	long
      // Exception table:
      //   from	to	target	type
      //   16	34	105	finally
    }
    
    public void run()
    {
      if (this.cancelled) {
        return;
      }
      try
      {
        Collection localCollection = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null buffer");
        try
        {
          if (this.cancelled) {
            return;
          }
          this.buffers.add(localCollection);
          this.w.schedule(new RemoveFromBuffer(localCollection), this.timespan, this.unit);
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(localThrowable);
      }
    }
    
    final class RemoveFromBuffer
      implements Runnable
    {
      private final U b;
      
      RemoveFromBuffer()
      {
        Object localObject;
        this.b = localObject;
      }
      
      public void run()
      {
        synchronized (ObservableBufferTimed.BufferSkipBoundedObserver.this)
        {
          ObservableBufferTimed.BufferSkipBoundedObserver.this.buffers.remove(this.b);
          ??? = ObservableBufferTimed.BufferSkipBoundedObserver.this;
          ???.fastPathOrderedEmit(this.b, false, ???.w);
          return;
        }
      }
    }
    
    final class RemoveFromBufferEmit
      implements Runnable
    {
      private final U buffer;
      
      RemoveFromBufferEmit()
      {
        Object localObject;
        this.buffer = localObject;
      }
      
      public void run()
      {
        synchronized (ObservableBufferTimed.BufferSkipBoundedObserver.this)
        {
          ObservableBufferTimed.BufferSkipBoundedObserver.this.buffers.remove(this.buffer);
          ObservableBufferTimed.BufferSkipBoundedObserver localBufferSkipBoundedObserver2 = ObservableBufferTimed.BufferSkipBoundedObserver.this;
          localBufferSkipBoundedObserver2.fastPathOrderedEmit(this.buffer, false, localBufferSkipBoundedObserver2.w);
          return;
        }
      }
    }
  }
}
