package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class BlockingObservableIterable<T>
  implements Iterable<T>
{
  final int bufferSize;
  final ObservableSource<? extends T> source;
  
  public BlockingObservableIterable(ObservableSource<? extends T> paramObservableSource, int paramInt)
  {
    this.source = paramObservableSource;
    this.bufferSize = paramInt;
  }
  
  public Iterator<T> iterator()
  {
    BlockingObservableIterator localBlockingObservableIterator = new BlockingObservableIterator(this.bufferSize);
    this.source.subscribe(localBlockingObservableIterator);
    return localBlockingObservableIterator;
  }
  
  static final class BlockingObservableIterator<T>
    extends AtomicReference<Disposable>
    implements Observer<T>, Iterator<T>, Disposable
  {
    private static final long serialVersionUID = 6695226475494099826L;
    final Condition condition;
    volatile boolean done;
    Throwable error;
    final Lock lock;
    final SpscLinkedArrayQueue<T> queue;
    
    BlockingObservableIterator(int paramInt)
    {
      this.queue = new SpscLinkedArrayQueue(paramInt);
      ReentrantLock localReentrantLock = new ReentrantLock();
      this.lock = localReentrantLock;
      this.condition = localReentrantLock.newCondition();
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    /* Error */
    public boolean hasNext()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 66	io/reactivex/internal/operators/observable/BlockingObservableIterable$BlockingObservableIterator:done	Z
      //   4: istore_1
      //   5: aload_0
      //   6: getfield 40	io/reactivex/internal/operators/observable/BlockingObservableIterable$BlockingObservableIterator:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   9: invokevirtual 69	io/reactivex/internal/queue/SpscLinkedArrayQueue:isEmpty	()Z
      //   12: istore_2
      //   13: iload_1
      //   14: ifeq +23 -> 37
      //   17: aload_0
      //   18: getfield 71	io/reactivex/internal/operators/observable/BlockingObservableIterable$BlockingObservableIterator:error	Ljava/lang/Throwable;
      //   21: astore_3
      //   22: aload_3
      //   23: ifnonnull +9 -> 32
      //   26: iload_2
      //   27: ifeq +10 -> 37
      //   30: iconst_0
      //   31: ireturn
      //   32: aload_3
      //   33: invokestatic 77	io/reactivex/internal/util/ExceptionHelper:wrapOrThrow	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
      //   36: athrow
      //   37: iload_2
      //   38: ifeq +83 -> 121
      //   41: invokestatic 82	io/reactivex/internal/util/BlockingHelper:verifyNonBlocking	()V
      //   44: aload_0
      //   45: getfield 45	io/reactivex/internal/operators/observable/BlockingObservableIterable$BlockingObservableIterator:lock	Ljava/util/concurrent/locks/Lock;
      //   48: invokeinterface 84 1 0
      //   53: aload_0
      //   54: getfield 66	io/reactivex/internal/operators/observable/BlockingObservableIterable$BlockingObservableIterator:done	Z
      //   57: ifne +25 -> 82
      //   60: aload_0
      //   61: getfield 40	io/reactivex/internal/operators/observable/BlockingObservableIterable$BlockingObservableIterator:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   64: invokevirtual 69	io/reactivex/internal/queue/SpscLinkedArrayQueue:isEmpty	()Z
      //   67: ifeq +15 -> 82
      //   70: aload_0
      //   71: getfield 53	io/reactivex/internal/operators/observable/BlockingObservableIterable$BlockingObservableIterator:condition	Ljava/util/concurrent/locks/Condition;
      //   74: invokeinterface 89 1 0
      //   79: goto -26 -> 53
      //   82: aload_0
      //   83: getfield 45	io/reactivex/internal/operators/observable/BlockingObservableIterable$BlockingObservableIterator:lock	Ljava/util/concurrent/locks/Lock;
      //   86: invokeinterface 92 1 0
      //   91: goto -91 -> 0
      //   94: astore_3
      //   95: aload_0
      //   96: getfield 45	io/reactivex/internal/operators/observable/BlockingObservableIterable$BlockingObservableIterator:lock	Ljava/util/concurrent/locks/Lock;
      //   99: invokeinterface 92 1 0
      //   104: aload_3
      //   105: athrow
      //   106: astore_3
      //   107: aload_0
      //   108: invokestatic 60	io/reactivex/internal/disposables/DisposableHelper:dispose	(Ljava/util/concurrent/atomic/AtomicReference;)Z
      //   111: pop
      //   112: aload_0
      //   113: invokevirtual 95	io/reactivex/internal/operators/observable/BlockingObservableIterable$BlockingObservableIterator:signalConsumer	()V
      //   116: aload_3
      //   117: invokestatic 77	io/reactivex/internal/util/ExceptionHelper:wrapOrThrow	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
      //   120: athrow
      //   121: iconst_1
      //   122: ireturn
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	123	0	this	BlockingObservableIterator
      //   4	10	1	bool1	boolean
      //   12	26	2	bool2	boolean
      //   21	12	3	localThrowable	Throwable
      //   94	11	3	localObject	Object
      //   106	11	3	localInterruptedException	InterruptedException
      // Exception table:
      //   from	to	target	type
      //   53	79	94	finally
      //   41	53	106	java/lang/InterruptedException
      //   82	91	106	java/lang/InterruptedException
      //   95	106	106	java/lang/InterruptedException
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public T next()
    {
      if (hasNext()) {
        return this.queue.poll();
      }
      throw new NoSuchElementException();
    }
    
    public void onComplete()
    {
      this.done = true;
      signalConsumer();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      signalConsumer();
    }
    
    public void onNext(T paramT)
    {
      this.queue.offer(paramT);
      signalConsumer();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException("remove");
    }
    
    void signalConsumer()
    {
      this.lock.lock();
      try
      {
        this.condition.signalAll();
        return;
      }
      finally
      {
        this.lock.unlock();
      }
    }
  }
}
