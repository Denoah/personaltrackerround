package androidx.camera.core.impl;

import android.os.SystemClock;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.concurrent.futures.CallbackToFutureAdapter.Resolver;
import androidx.core.util.Preconditions;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public final class LiveDataObservable<T>
  implements Observable<T>
{
  final MutableLiveData<Result<T>> mLiveData = new MutableLiveData();
  private final Map<Observable.Observer<T>, LiveDataObserverAdapter<T>> mObservers = new HashMap();
  
  public LiveDataObservable() {}
  
  public void addObserver(Executor paramExecutor, Observable.Observer<T> paramObserver)
  {
    synchronized (this.mObservers)
    {
      LiveDataObserverAdapter localLiveDataObserverAdapter1 = (LiveDataObserverAdapter)this.mObservers.get(paramObserver);
      if (localLiveDataObserverAdapter1 != null) {
        localLiveDataObserverAdapter1.disable();
      }
      LiveDataObserverAdapter localLiveDataObserverAdapter2 = new androidx/camera/core/impl/LiveDataObservable$LiveDataObserverAdapter;
      localLiveDataObserverAdapter2.<init>(paramExecutor, paramObserver);
      this.mObservers.put(paramObserver, localLiveDataObserverAdapter2);
      paramExecutor = CameraXExecutors.mainThreadExecutor();
      paramObserver = new androidx/camera/core/impl/LiveDataObservable$2;
      paramObserver.<init>(this, localLiveDataObserverAdapter1, localLiveDataObserverAdapter2);
      paramExecutor.execute(paramObserver);
      return;
    }
  }
  
  public ListenableFuture<T> fetchData()
  {
    CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver()
    {
      public Object attachCompleter(final CallbackToFutureAdapter.Completer<T> paramAnonymousCompleter)
      {
        CameraXExecutors.mainThreadExecutor().execute(new Runnable()
        {
          public void run()
          {
            LiveDataObservable.Result localResult = (LiveDataObservable.Result)LiveDataObservable.this.mLiveData.getValue();
            if (localResult == null)
            {
              paramAnonymousCompleter.setException(new IllegalStateException("Observable has not yet been initialized with a value."));
            }
            else if (localResult.completedSuccessfully())
            {
              paramAnonymousCompleter.set(localResult.getValue());
            }
            else
            {
              Preconditions.checkNotNull(localResult.getError());
              paramAnonymousCompleter.setException(localResult.getError());
            }
          }
        });
        paramAnonymousCompleter = new StringBuilder();
        paramAnonymousCompleter.append(LiveDataObservable.this);
        paramAnonymousCompleter.append(" [fetch@");
        paramAnonymousCompleter.append(SystemClock.uptimeMillis());
        paramAnonymousCompleter.append("]");
        return paramAnonymousCompleter.toString();
      }
    });
  }
  
  public LiveData<Result<T>> getLiveData()
  {
    return this.mLiveData;
  }
  
  public void postError(Throwable paramThrowable)
  {
    this.mLiveData.postValue(Result.fromError(paramThrowable));
  }
  
  public void postValue(T paramT)
  {
    this.mLiveData.postValue(Result.fromValue(paramT));
  }
  
  public void removeObserver(Observable.Observer<T> paramObserver)
  {
    synchronized (this.mObservers)
    {
      LiveDataObserverAdapter localLiveDataObserverAdapter = (LiveDataObserverAdapter)this.mObservers.remove(paramObserver);
      if (localLiveDataObserverAdapter != null)
      {
        localLiveDataObserverAdapter.disable();
        paramObserver = CameraXExecutors.mainThreadExecutor();
        Runnable local3 = new androidx/camera/core/impl/LiveDataObservable$3;
        local3.<init>(this, localLiveDataObserverAdapter);
        paramObserver.execute(local3);
      }
      return;
    }
  }
  
  private static final class LiveDataObserverAdapter<T>
    implements Observer<LiveDataObservable.Result<T>>
  {
    final AtomicBoolean mActive = new AtomicBoolean(true);
    final Executor mExecutor;
    final Observable.Observer<T> mObserver;
    
    LiveDataObserverAdapter(Executor paramExecutor, Observable.Observer<T> paramObserver)
    {
      this.mExecutor = paramExecutor;
      this.mObserver = paramObserver;
    }
    
    void disable()
    {
      this.mActive.set(false);
    }
    
    public void onChanged(final LiveDataObservable.Result<T> paramResult)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          if (!LiveDataObservable.LiveDataObserverAdapter.this.mActive.get()) {
            return;
          }
          if (paramResult.completedSuccessfully())
          {
            LiveDataObservable.LiveDataObserverAdapter.this.mObserver.onNewData(paramResult.getValue());
          }
          else
          {
            Preconditions.checkNotNull(paramResult.getError());
            LiveDataObservable.LiveDataObserverAdapter.this.mObserver.onError(paramResult.getError());
          }
        }
      });
    }
  }
  
  public static final class Result<T>
  {
    private Throwable mError;
    private T mValue;
    
    private Result(T paramT, Throwable paramThrowable)
    {
      this.mValue = paramT;
      this.mError = paramThrowable;
    }
    
    static <T> Result<T> fromError(Throwable paramThrowable)
    {
      return new Result(null, (Throwable)Preconditions.checkNotNull(paramThrowable));
    }
    
    static <T> Result<T> fromValue(T paramT)
    {
      return new Result(paramT, null);
    }
    
    public boolean completedSuccessfully()
    {
      boolean bool;
      if (this.mError == null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public Throwable getError()
    {
      return this.mError;
    }
    
    public T getValue()
    {
      if (completedSuccessfully()) {
        return this.mValue;
      }
      throw new IllegalStateException("Result contains an error. Does not contain a value.");
    }
  }
}
