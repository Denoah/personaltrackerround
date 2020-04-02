package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.subscribers.DefaultSubscriber;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class BlockingFlowableMostRecent<T>
  implements Iterable<T>
{
  final T initialValue;
  final Flowable<T> source;
  
  public BlockingFlowableMostRecent(Flowable<T> paramFlowable, T paramT)
  {
    this.source = paramFlowable;
    this.initialValue = paramT;
  }
  
  public Iterator<T> iterator()
  {
    MostRecentSubscriber localMostRecentSubscriber = new MostRecentSubscriber(this.initialValue);
    this.source.subscribe(localMostRecentSubscriber);
    return localMostRecentSubscriber.getIterable();
  }
  
  static final class MostRecentSubscriber<T>
    extends DefaultSubscriber<T>
  {
    volatile Object value;
    
    MostRecentSubscriber(T paramT)
    {
      this.value = NotificationLite.next(paramT);
    }
    
    public MostRecentSubscriber<T>.Iterator getIterable()
    {
      return new Iterator();
    }
    
    public void onComplete()
    {
      this.value = NotificationLite.complete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.value = NotificationLite.error(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.value = NotificationLite.next(paramT);
    }
    
    final class Iterator
      implements Iterator<T>
    {
      private Object buf;
      
      Iterator() {}
      
      public boolean hasNext()
      {
        Object localObject = BlockingFlowableMostRecent.MostRecentSubscriber.this.value;
        this.buf = localObject;
        return NotificationLite.isComplete(localObject) ^ true;
      }
      
      public T next()
      {
        try
        {
          if (this.buf == null) {
            this.buf = BlockingFlowableMostRecent.MostRecentSubscriber.this.value;
          }
          if (!NotificationLite.isComplete(this.buf))
          {
            if (!NotificationLite.isError(this.buf))
            {
              localObject1 = NotificationLite.getValue(this.buf);
              return localObject1;
            }
            throw ExceptionHelper.wrapOrThrow(NotificationLite.getError(this.buf));
          }
          Object localObject1 = new java/util/NoSuchElementException;
          ((NoSuchElementException)localObject1).<init>();
          throw ((Throwable)localObject1);
        }
        finally
        {
          this.buf = null;
        }
      }
      
      public void remove()
      {
        throw new UnsupportedOperationException("Read only iterator");
      }
    }
  }
}
