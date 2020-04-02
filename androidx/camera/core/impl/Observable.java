package androidx.camera.core.impl;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;

public abstract interface Observable<T>
{
  public abstract void addObserver(Executor paramExecutor, Observer<T> paramObserver);
  
  public abstract ListenableFuture<T> fetchData();
  
  public abstract void removeObserver(Observer<T> paramObserver);
  
  public static abstract interface Observer<T>
  {
    public abstract void onError(Throwable paramThrowable);
    
    public abstract void onNewData(T paramT);
  }
}
