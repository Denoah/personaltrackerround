package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Notification;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;

public final class BlockingFlowableNext<T>
  implements Iterable<T>
{
  final Publisher<? extends T> source;
  
  public BlockingFlowableNext(Publisher<? extends T> paramPublisher)
  {
    this.source = paramPublisher;
  }
  
  public Iterator<T> iterator()
  {
    NextSubscriber localNextSubscriber = new NextSubscriber();
    return new NextIterator(this.source, localNextSubscriber);
  }
  
  static final class NextIterator<T>
    implements Iterator<T>
  {
    private Throwable error;
    private boolean hasNext = true;
    private boolean isNextConsumed = true;
    private final Publisher<? extends T> items;
    private T next;
    private boolean started;
    private final BlockingFlowableNext.NextSubscriber<T> subscriber;
    
    NextIterator(Publisher<? extends T> paramPublisher, BlockingFlowableNext.NextSubscriber<T> paramNextSubscriber)
    {
      this.items = paramPublisher;
      this.subscriber = paramNextSubscriber;
    }
    
    private boolean moveToNext()
    {
      try
      {
        if (!this.started)
        {
          this.started = true;
          this.subscriber.setWaiting();
          Flowable.fromPublisher(this.items).materialize().subscribe(this.subscriber);
        }
        Object localObject = this.subscriber.takeNext();
        if (((Notification)localObject).isOnNext())
        {
          this.isNextConsumed = false;
          this.next = ((Notification)localObject).getValue();
          return true;
        }
        this.hasNext = false;
        if (((Notification)localObject).isOnComplete()) {
          return false;
        }
        if (((Notification)localObject).isOnError())
        {
          localObject = ((Notification)localObject).getError();
          this.error = ((Throwable)localObject);
          throw ExceptionHelper.wrapOrThrow((Throwable)localObject);
        }
        localObject = new java/lang/IllegalStateException;
        ((IllegalStateException)localObject).<init>("Should not reach here");
        throw ((Throwable)localObject);
      }
      catch (InterruptedException localInterruptedException)
      {
        this.subscriber.dispose();
        this.error = localInterruptedException;
        throw ExceptionHelper.wrapOrThrow(localInterruptedException);
      }
    }
    
    public boolean hasNext()
    {
      Throwable localThrowable = this.error;
      if (localThrowable == null)
      {
        boolean bool1 = this.hasNext;
        boolean bool2 = false;
        if (!bool1) {
          return false;
        }
        if ((!this.isNextConsumed) || (moveToNext())) {
          bool2 = true;
        }
        return bool2;
      }
      throw ExceptionHelper.wrapOrThrow(localThrowable);
    }
    
    public T next()
    {
      Throwable localThrowable = this.error;
      if (localThrowable == null)
      {
        if (hasNext())
        {
          this.isNextConsumed = true;
          return this.next;
        }
        throw new NoSuchElementException("No more elements");
      }
      throw ExceptionHelper.wrapOrThrow(localThrowable);
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException("Read only iterator");
    }
  }
  
  static final class NextSubscriber<T>
    extends DisposableSubscriber<Notification<T>>
  {
    private final BlockingQueue<Notification<T>> buf = new ArrayBlockingQueue(1);
    final AtomicInteger waiting = new AtomicInteger();
    
    NextSubscriber() {}
    
    public void onComplete() {}
    
    public void onError(Throwable paramThrowable)
    {
      RxJavaPlugins.onError(paramThrowable);
    }
    
    public void onNext(Notification<T> paramNotification)
    {
      Notification<T> localNotification = paramNotification;
      if (this.waiting.getAndSet(0) != 1)
      {
        if (paramNotification.isOnNext()) {}
      }
      else {
        for (localNotification = paramNotification; !this.buf.offer(localNotification); localNotification = paramNotification)
        {
          label23:
          paramNotification = (Notification)this.buf.poll();
          if ((paramNotification == null) || (paramNotification.isOnNext())) {
            break label23;
          }
        }
      }
    }
    
    void setWaiting()
    {
      this.waiting.set(1);
    }
    
    public Notification<T> takeNext()
      throws InterruptedException
    {
      setWaiting();
      BlockingHelper.verifyNonBlocking();
      return (Notification)this.buf.take();
    }
  }
}
