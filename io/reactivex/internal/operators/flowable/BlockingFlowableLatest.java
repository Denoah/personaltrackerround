package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Notification;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;

public final class BlockingFlowableLatest<T>
  implements Iterable<T>
{
  final Publisher<? extends T> source;
  
  public BlockingFlowableLatest(Publisher<? extends T> paramPublisher)
  {
    this.source = paramPublisher;
  }
  
  public Iterator<T> iterator()
  {
    LatestSubscriberIterator localLatestSubscriberIterator = new LatestSubscriberIterator();
    Flowable.fromPublisher(this.source).materialize().subscribe(localLatestSubscriberIterator);
    return localLatestSubscriberIterator;
  }
  
  static final class LatestSubscriberIterator<T>
    extends DisposableSubscriber<Notification<T>>
    implements Iterator<T>
  {
    Notification<T> iteratorNotification;
    final Semaphore notify = new Semaphore(0);
    final AtomicReference<Notification<T>> value = new AtomicReference();
    
    LatestSubscriberIterator() {}
    
    public boolean hasNext()
    {
      Notification localNotification = this.iteratorNotification;
      if ((localNotification != null) && (localNotification.isOnError())) {
        throw ExceptionHelper.wrapOrThrow(this.iteratorNotification.getError());
      }
      localNotification = this.iteratorNotification;
      if (((localNotification == null) || (localNotification.isOnNext())) && (this.iteratorNotification == null)) {
        try
        {
          BlockingHelper.verifyNonBlocking();
          this.notify.acquire();
          localNotification = (Notification)this.value.getAndSet(null);
          this.iteratorNotification = localNotification;
          if (localNotification.isOnError()) {
            throw ExceptionHelper.wrapOrThrow(localNotification.getError());
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          dispose();
          this.iteratorNotification = Notification.createOnError(localInterruptedException);
          throw ExceptionHelper.wrapOrThrow(localInterruptedException);
        }
      }
      return this.iteratorNotification.isOnNext();
    }
    
    public T next()
    {
      if ((hasNext()) && (this.iteratorNotification.isOnNext()))
      {
        Object localObject = this.iteratorNotification.getValue();
        this.iteratorNotification = null;
        return localObject;
      }
      throw new NoSuchElementException();
    }
    
    public void onComplete() {}
    
    public void onError(Throwable paramThrowable)
    {
      RxJavaPlugins.onError(paramThrowable);
    }
    
    public void onNext(Notification<T> paramNotification)
    {
      int i;
      if (this.value.getAndSet(paramNotification) == null) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0) {
        this.notify.release();
      }
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException("Read-only iterator.");
    }
  }
}
