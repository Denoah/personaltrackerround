package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.observers.BasicIntQueueDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public final class ObservableObserveOn<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final int bufferSize;
  final boolean delayError;
  final Scheduler scheduler;
  
  public ObservableObserveOn(ObservableSource<T> paramObservableSource, Scheduler paramScheduler, boolean paramBoolean, int paramInt)
  {
    super(paramObservableSource);
    this.scheduler = paramScheduler;
    this.delayError = paramBoolean;
    this.bufferSize = paramInt;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    Object localObject = this.scheduler;
    if ((localObject instanceof TrampolineScheduler))
    {
      this.source.subscribe(paramObserver);
    }
    else
    {
      localObject = ((Scheduler)localObject).createWorker();
      this.source.subscribe(new ObserveOnObserver(paramObserver, (Scheduler.Worker)localObject, this.delayError, this.bufferSize));
    }
  }
  
  static final class ObserveOnObserver<T>
    extends BasicIntQueueDisposable<T>
    implements Observer<T>, Runnable
  {
    private static final long serialVersionUID = 6576896619930983584L;
    final int bufferSize;
    final boolean delayError;
    volatile boolean disposed;
    volatile boolean done;
    final Observer<? super T> downstream;
    Throwable error;
    boolean outputFused;
    SimpleQueue<T> queue;
    int sourceMode;
    Disposable upstream;
    final Scheduler.Worker worker;
    
    ObserveOnObserver(Observer<? super T> paramObserver, Scheduler.Worker paramWorker, boolean paramBoolean, int paramInt)
    {
      this.downstream = paramObserver;
      this.worker = paramWorker;
      this.delayError = paramBoolean;
      this.bufferSize = paramInt;
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Observer<? super T> paramObserver)
    {
      if (this.disposed)
      {
        this.queue.clear();
        return true;
      }
      if (paramBoolean1)
      {
        Throwable localThrowable = this.error;
        if (this.delayError)
        {
          if (paramBoolean2)
          {
            this.disposed = true;
            if (localThrowable != null) {
              paramObserver.onError(localThrowable);
            } else {
              paramObserver.onComplete();
            }
            this.worker.dispose();
            return true;
          }
        }
        else
        {
          if (localThrowable != null)
          {
            this.disposed = true;
            this.queue.clear();
            paramObserver.onError(localThrowable);
            this.worker.dispose();
            return true;
          }
          if (paramBoolean2)
          {
            this.disposed = true;
            paramObserver.onComplete();
            this.worker.dispose();
            return true;
          }
        }
      }
      return false;
    }
    
    public void clear()
    {
      this.queue.clear();
    }
    
    public void dispose()
    {
      if (!this.disposed)
      {
        this.disposed = true;
        this.upstream.dispose();
        this.worker.dispose();
        if (getAndIncrement() == 0) {
          this.queue.clear();
        }
      }
    }
    
    void drainFused()
    {
      int i = 1;
      int j;
      do
      {
        if (this.disposed) {
          return;
        }
        boolean bool = this.done;
        Throwable localThrowable = this.error;
        if ((!this.delayError) && (bool) && (localThrowable != null))
        {
          this.disposed = true;
          this.downstream.onError(this.error);
          this.worker.dispose();
          return;
        }
        this.downstream.onNext(null);
        if (bool)
        {
          this.disposed = true;
          localThrowable = this.error;
          if (localThrowable != null) {
            this.downstream.onError(localThrowable);
          } else {
            this.downstream.onComplete();
          }
          this.worker.dispose();
          return;
        }
        j = addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    /* Error */
    void drainNormal()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 58	io/reactivex/internal/operators/observable/ObservableObserveOn$ObserveOnObserver:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   4: astore_1
      //   5: aload_0
      //   6: getfield 43	io/reactivex/internal/operators/observable/ObservableObserveOn$ObserveOnObserver:downstream	Lio/reactivex/Observer;
      //   9: astore_2
      //   10: iconst_1
      //   11: istore_3
      //   12: aload_0
      //   13: aload_0
      //   14: getfield 90	io/reactivex/internal/operators/observable/ObservableObserveOn$ObserveOnObserver:done	Z
      //   17: aload_1
      //   18: invokeinterface 103 1 0
      //   23: aload_2
      //   24: invokevirtual 105	io/reactivex/internal/operators/observable/ObservableObserveOn$ObserveOnObserver:checkTerminated	(ZZLio/reactivex/Observer;)Z
      //   27: ifeq +4 -> 31
      //   30: return
      //   31: aload_0
      //   32: getfield 90	io/reactivex/internal/operators/observable/ObservableObserveOn$ObserveOnObserver:done	Z
      //   35: istore 4
      //   37: aload_1
      //   38: invokeinterface 109 1 0
      //   43: astore 5
      //   45: aload 5
      //   47: ifnonnull +9 -> 56
      //   50: iconst_1
      //   51: istore 6
      //   53: goto +6 -> 59
      //   56: iconst_0
      //   57: istore 6
      //   59: aload_0
      //   60: iload 4
      //   62: iload 6
      //   64: aload_2
      //   65: invokevirtual 105	io/reactivex/internal/operators/observable/ObservableObserveOn$ObserveOnObserver:checkTerminated	(ZZLio/reactivex/Observer;)Z
      //   68: ifeq +4 -> 72
      //   71: return
      //   72: iload 6
      //   74: ifeq +20 -> 94
      //   77: aload_0
      //   78: iload_3
      //   79: ineg
      //   80: invokevirtual 98	io/reactivex/internal/operators/observable/ObservableObserveOn$ObserveOnObserver:addAndGet	(I)I
      //   83: istore 7
      //   85: iload 7
      //   87: istore_3
      //   88: iload 7
      //   90: ifne -78 -> 12
      //   93: return
      //   94: aload_2
      //   95: aload 5
      //   97: invokeinterface 94 2 0
      //   102: goto -71 -> 31
      //   105: astore 5
      //   107: aload 5
      //   109: invokestatic 114	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   112: aload_0
      //   113: iconst_1
      //   114: putfield 56	io/reactivex/internal/operators/observable/ObservableObserveOn$ObserveOnObserver:disposed	Z
      //   117: aload_0
      //   118: getfield 80	io/reactivex/internal/operators/observable/ObservableObserveOn$ObserveOnObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   121: invokeinterface 83 1 0
      //   126: aload_1
      //   127: invokeinterface 63 1 0
      //   132: aload_2
      //   133: aload 5
      //   135: invokeinterface 69 2 0
      //   140: aload_0
      //   141: getfield 45	io/reactivex/internal/operators/observable/ObservableObserveOn$ObserveOnObserver:worker	Lio/reactivex/Scheduler$Worker;
      //   144: invokevirtual 77	io/reactivex/Scheduler$Worker:dispose	()V
      //   147: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	148	0	this	ObserveOnObserver
      //   4	123	1	localSimpleQueue	SimpleQueue
      //   9	124	2	localObserver	Observer
      //   11	77	3	i	int
      //   35	26	4	bool1	boolean
      //   43	53	5	localObject	Object
      //   105	29	5	localThrowable	Throwable
      //   51	22	6	bool2	boolean
      //   83	6	7	j	int
      // Exception table:
      //   from	to	target	type
      //   37	45	105	finally
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    public boolean isEmpty()
    {
      return this.queue.isEmpty();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      schedule();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.error = paramThrowable;
      this.done = true;
      schedule();
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      if (this.sourceMode != 2) {
        this.queue.offer(paramT);
      }
      schedule();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        if ((paramDisposable instanceof QueueDisposable))
        {
          paramDisposable = (QueueDisposable)paramDisposable;
          int i = paramDisposable.requestFusion(7);
          if (i == 1)
          {
            this.sourceMode = i;
            this.queue = paramDisposable;
            this.done = true;
            this.downstream.onSubscribe(this);
            schedule();
            return;
          }
          if (i == 2)
          {
            this.sourceMode = i;
            this.queue = paramDisposable;
            this.downstream.onSubscribe(this);
            return;
          }
        }
        this.queue = new SpscLinkedArrayQueue(this.bufferSize);
        this.downstream.onSubscribe(this);
      }
    }
    
    public T poll()
      throws Exception
    {
      return this.queue.poll();
    }
    
    public int requestFusion(int paramInt)
    {
      if ((paramInt & 0x2) != 0)
      {
        this.outputFused = true;
        return 2;
      }
      return 0;
    }
    
    public void run()
    {
      if (this.outputFused) {
        drainFused();
      } else {
        drainNormal();
      }
    }
    
    void schedule()
    {
      if (getAndIncrement() == 0) {
        this.worker.schedule(this);
      }
    }
  }
}
