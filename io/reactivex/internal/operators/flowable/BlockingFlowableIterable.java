package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.reactivestreams.Subscription;

public final class BlockingFlowableIterable<T>
  implements Iterable<T>
{
  final int bufferSize;
  final Flowable<T> source;
  
  public BlockingFlowableIterable(Flowable<T> paramFlowable, int paramInt)
  {
    this.source = paramFlowable;
    this.bufferSize = paramInt;
  }
  
  public Iterator<T> iterator()
  {
    BlockingFlowableIterator localBlockingFlowableIterator = new BlockingFlowableIterator(this.bufferSize);
    this.source.subscribe(localBlockingFlowableIterator);
    return localBlockingFlowableIterator;
  }
  
  static final class BlockingFlowableIterator<T>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<T>, Iterator<T>, Runnable, Disposable
  {
    private static final long serialVersionUID = 6695226475494099826L;
    final long batchSize;
    final Condition condition;
    volatile boolean done;
    Throwable error;
    final long limit;
    final Lock lock;
    long produced;
    final SpscArrayQueue<T> queue;
    
    BlockingFlowableIterator(int paramInt)
    {
      this.queue = new SpscArrayQueue(paramInt);
      this.batchSize = paramInt;
      this.limit = (paramInt - (paramInt >> 2));
      ReentrantLock localReentrantLock = new ReentrantLock();
      this.lock = localReentrantLock;
      this.condition = localReentrantLock.newCondition();
    }
    
    public void dispose()
    {
      SubscriptionHelper.cancel(this);
    }
    
    /* Error */
    public boolean hasNext()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 76	io/reactivex/internal/operators/flowable/BlockingFlowableIterable$BlockingFlowableIterator:done	Z
      //   4: istore_1
      //   5: aload_0
      //   6: getfield 45	io/reactivex/internal/operators/flowable/BlockingFlowableIterable$BlockingFlowableIterator:queue	Lio/reactivex/internal/queue/SpscArrayQueue;
      //   9: invokevirtual 79	io/reactivex/internal/queue/SpscArrayQueue:isEmpty	()Z
      //   12: istore_2
      //   13: iload_1
      //   14: ifeq +23 -> 37
      //   17: aload_0
      //   18: getfield 81	io/reactivex/internal/operators/flowable/BlockingFlowableIterable$BlockingFlowableIterator:error	Ljava/lang/Throwable;
      //   21: astore_3
      //   22: aload_3
      //   23: ifnonnull +9 -> 32
      //   26: iload_2
      //   27: ifeq +10 -> 37
      //   30: iconst_0
      //   31: ireturn
      //   32: aload_3
      //   33: invokestatic 87	io/reactivex/internal/util/ExceptionHelper:wrapOrThrow	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
      //   36: athrow
      //   37: iload_2
      //   38: ifeq +81 -> 119
      //   41: invokestatic 92	io/reactivex/internal/util/BlockingHelper:verifyNonBlocking	()V
      //   44: aload_0
      //   45: getfield 54	io/reactivex/internal/operators/flowable/BlockingFlowableIterable$BlockingFlowableIterator:lock	Ljava/util/concurrent/locks/Lock;
      //   48: invokeinterface 94 1 0
      //   53: aload_0
      //   54: getfield 76	io/reactivex/internal/operators/flowable/BlockingFlowableIterable$BlockingFlowableIterator:done	Z
      //   57: ifne +25 -> 82
      //   60: aload_0
      //   61: getfield 45	io/reactivex/internal/operators/flowable/BlockingFlowableIterable$BlockingFlowableIterator:queue	Lio/reactivex/internal/queue/SpscArrayQueue;
      //   64: invokevirtual 79	io/reactivex/internal/queue/SpscArrayQueue:isEmpty	()Z
      //   67: ifeq +15 -> 82
      //   70: aload_0
      //   71: getfield 62	io/reactivex/internal/operators/flowable/BlockingFlowableIterable$BlockingFlowableIterator:condition	Ljava/util/concurrent/locks/Condition;
      //   74: invokeinterface 99 1 0
      //   79: goto -26 -> 53
      //   82: aload_0
      //   83: getfield 54	io/reactivex/internal/operators/flowable/BlockingFlowableIterable$BlockingFlowableIterator:lock	Ljava/util/concurrent/locks/Lock;
      //   86: invokeinterface 102 1 0
      //   91: goto -91 -> 0
      //   94: astore_3
      //   95: goto +13 -> 108
      //   98: astore_3
      //   99: aload_0
      //   100: invokevirtual 105	io/reactivex/internal/operators/flowable/BlockingFlowableIterable$BlockingFlowableIterator:run	()V
      //   103: aload_3
      //   104: invokestatic 87	io/reactivex/internal/util/ExceptionHelper:wrapOrThrow	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
      //   107: athrow
      //   108: aload_0
      //   109: getfield 54	io/reactivex/internal/operators/flowable/BlockingFlowableIterable$BlockingFlowableIterator:lock	Ljava/util/concurrent/locks/Lock;
      //   112: invokeinterface 102 1 0
      //   117: aload_3
      //   118: athrow
      //   119: iconst_1
      //   120: ireturn
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	121	0	this	BlockingFlowableIterator
      //   4	10	1	bool1	boolean
      //   12	26	2	bool2	boolean
      //   21	12	3	localThrowable	Throwable
      //   94	1	3	localObject	Object
      //   98	20	3	localInterruptedException	InterruptedException
      // Exception table:
      //   from	to	target	type
      //   53	79	94	finally
      //   99	108	94	finally
      //   53	79	98	java/lang/InterruptedException
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == SubscriptionHelper.CANCELLED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public T next()
    {
      if (hasNext())
      {
        Object localObject = this.queue.poll();
        long l = this.produced + 1L;
        if (l == this.limit)
        {
          this.produced = 0L;
          ((Subscription)get()).request(l);
        }
        else
        {
          this.produced = l;
        }
        return localObject;
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
      if (!this.queue.offer(paramT))
      {
        SubscriptionHelper.cancel(this);
        onError(new MissingBackpressureException("Queue full?!"));
      }
      else
      {
        signalConsumer();
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      SubscriptionHelper.setOnce(this, paramSubscription, this.batchSize);
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException("remove");
    }
    
    public void run()
    {
      SubscriptionHelper.cancel(this);
      signalConsumer();
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
