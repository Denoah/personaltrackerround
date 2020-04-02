package io.reactivex.internal.operators.observable;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

public final class BlockingObservableLatest<T>
  implements Iterable<T>
{
  final ObservableSource<T> source;
  
  public BlockingObservableLatest(ObservableSource<T> paramObservableSource)
  {
    this.source = paramObservableSource;
  }
  
  public Iterator<T> iterator()
  {
    BlockingObservableLatestIterator localBlockingObservableLatestIterator = new BlockingObservableLatestIterator();
    Observable.wrap(this.source).materialize().subscribe(localBlockingObservableLatestIterator);
    return localBlockingObservableLatestIterator;
  }
  
  static final class BlockingObservableLatestIterator<T>
    extends DisposableObserver<Notification<T>>
    implements Iterator<T>
  {
    Notification<T> iteratorNotification;
    final Semaphore notify = new Semaphore(0);
    final AtomicReference<Notification<T>> value = new AtomicReference();
    
    BlockingObservableLatestIterator() {}
    
    public boolean hasNext()
    {
      Notification localNotification = this.iteratorNotification;
      if ((localNotification != null) && (localNotification.isOnError())) {
        throw ExceptionHelper.wrapOrThrow(this.iteratorNotification.getError());
      }
      if (this.iteratorNotification == null) {
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
      if (hasNext())
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
