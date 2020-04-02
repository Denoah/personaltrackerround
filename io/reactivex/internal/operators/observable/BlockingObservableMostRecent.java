package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.DefaultObserver;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class BlockingObservableMostRecent<T>
  implements Iterable<T>
{
  final T initialValue;
  final ObservableSource<T> source;
  
  public BlockingObservableMostRecent(ObservableSource<T> paramObservableSource, T paramT)
  {
    this.source = paramObservableSource;
    this.initialValue = paramT;
  }
  
  public Iterator<T> iterator()
  {
    MostRecentObserver localMostRecentObserver = new MostRecentObserver(this.initialValue);
    this.source.subscribe(localMostRecentObserver);
    return localMostRecentObserver.getIterable();
  }
  
  static final class MostRecentObserver<T>
    extends DefaultObserver<T>
  {
    volatile Object value;
    
    MostRecentObserver(T paramT)
    {
      this.value = NotificationLite.next(paramT);
    }
    
    public MostRecentObserver<T>.Iterator getIterable()
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
        Object localObject = BlockingObservableMostRecent.MostRecentObserver.this.value;
        this.buf = localObject;
        return NotificationLite.isComplete(localObject) ^ true;
      }
      
      public T next()
      {
        try
        {
          if (this.buf == null) {
            this.buf = BlockingObservableMostRecent.MostRecentObserver.this.value;
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
