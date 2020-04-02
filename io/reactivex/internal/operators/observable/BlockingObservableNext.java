package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.ObservableSource;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class BlockingObservableNext<T>
  implements Iterable<T>
{
  final ObservableSource<T> source;
  
  public BlockingObservableNext(ObservableSource<T> paramObservableSource)
  {
    this.source = paramObservableSource;
  }
  
  public Iterator<T> iterator()
  {
    NextObserver localNextObserver = new NextObserver();
    return new NextIterator(this.source, localNextObserver);
  }
  
  static final class NextIterator<T>
    implements Iterator<T>
  {
    private Throwable error;
    private boolean hasNext = true;
    private boolean isNextConsumed = true;
    private final ObservableSource<T> items;
    private T next;
    private final BlockingObservableNext.NextObserver<T> observer;
    private boolean started;
    
    NextIterator(ObservableSource<T> paramObservableSource, BlockingObservableNext.NextObserver<T> paramNextObserver)
    {
      this.items = paramObservableSource;
      this.observer = paramNextObserver;
    }
    
    private boolean moveToNext()
    {
      if (!this.started)
      {
        this.started = true;
        this.observer.setWaiting();
        new ObservableMaterialize(this.items).subscribe(this.observer);
      }
      try
      {
        Object localObject = this.observer.takeNext();
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
        localObject = ((Notification)localObject).getError();
        this.error = ((Throwable)localObject);
        throw ExceptionHelper.wrapOrThrow((Throwable)localObject);
      }
      catch (InterruptedException localInterruptedException)
      {
        this.observer.dispose();
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
  
  static final class NextObserver<T>
    extends DisposableObserver<Notification<T>>
  {
    private final BlockingQueue<Notification<T>> buf = new ArrayBlockingQueue(1);
    final AtomicInteger waiting = new AtomicInteger();
    
    NextObserver() {}
    
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
