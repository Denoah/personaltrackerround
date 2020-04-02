package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BasicQueueDisposable;

public final class ObservableFromArray<T>
  extends Observable<T>
{
  final T[] array;
  
  public ObservableFromArray(T[] paramArrayOfT)
  {
    this.array = paramArrayOfT;
  }
  
  public void subscribeActual(Observer<? super T> paramObserver)
  {
    FromArrayDisposable localFromArrayDisposable = new FromArrayDisposable(paramObserver, this.array);
    paramObserver.onSubscribe(localFromArrayDisposable);
    if (localFromArrayDisposable.fusionMode) {
      return;
    }
    localFromArrayDisposable.run();
  }
  
  static final class FromArrayDisposable<T>
    extends BasicQueueDisposable<T>
  {
    final T[] array;
    volatile boolean disposed;
    final Observer<? super T> downstream;
    boolean fusionMode;
    int index;
    
    FromArrayDisposable(Observer<? super T> paramObserver, T[] paramArrayOfT)
    {
      this.downstream = paramObserver;
      this.array = paramArrayOfT;
    }
    
    public void clear()
    {
      this.index = this.array.length;
    }
    
    public void dispose()
    {
      this.disposed = true;
    }
    
    public boolean isDisposed()
    {
      return this.disposed;
    }
    
    public boolean isEmpty()
    {
      boolean bool;
      if (this.index == this.array.length) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public T poll()
    {
      int i = this.index;
      Object[] arrayOfObject = this.array;
      if (i != arrayOfObject.length)
      {
        this.index = (i + 1);
        return ObjectHelper.requireNonNull(arrayOfObject[i], "The array element is null");
      }
      return null;
    }
    
    public int requestFusion(int paramInt)
    {
      if ((paramInt & 0x1) != 0)
      {
        this.fusionMode = true;
        return 1;
      }
      return 0;
    }
    
    void run()
    {
      Object localObject = this.array;
      int i = localObject.length;
      for (int j = 0; (j < i) && (!isDisposed()); j++)
      {
        StringBuilder localStringBuilder = localObject[j];
        if (localStringBuilder == null)
        {
          localObject = this.downstream;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("The element at index ");
          localStringBuilder.append(j);
          localStringBuilder.append(" is null");
          ((Observer)localObject).onError(new NullPointerException(localStringBuilder.toString()));
          return;
        }
        this.downstream.onNext(localStringBuilder);
      }
      if (!isDisposed()) {
        this.downstream.onComplete();
      }
    }
  }
}
