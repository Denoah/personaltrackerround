package androidx.camera.core.impl.utils.futures;

import androidx.arch.core.util.Function;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.concurrent.futures.CallbackToFutureAdapter.Resolver;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureChain<V>
  implements ListenableFuture<V>
{
  CallbackToFutureAdapter.Completer<V> mCompleter;
  private final ListenableFuture<V> mDelegate;
  
  FutureChain()
  {
    this.mDelegate = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver()
    {
      public Object attachCompleter(CallbackToFutureAdapter.Completer<V> paramAnonymousCompleter)
      {
        boolean bool;
        if (FutureChain.this.mCompleter == null) {
          bool = true;
        } else {
          bool = false;
        }
        Preconditions.checkState(bool, "The result can only set once!");
        FutureChain.this.mCompleter = paramAnonymousCompleter;
        paramAnonymousCompleter = new StringBuilder();
        paramAnonymousCompleter.append("FutureChain[");
        paramAnonymousCompleter.append(FutureChain.this);
        paramAnonymousCompleter.append("]");
        return paramAnonymousCompleter.toString();
      }
    });
  }
  
  FutureChain(ListenableFuture<V> paramListenableFuture)
  {
    this.mDelegate = ((ListenableFuture)Preconditions.checkNotNull(paramListenableFuture));
  }
  
  public static <V> FutureChain<V> from(ListenableFuture<V> paramListenableFuture)
  {
    if ((paramListenableFuture instanceof FutureChain)) {
      paramListenableFuture = (FutureChain)paramListenableFuture;
    } else {
      paramListenableFuture = new FutureChain(paramListenableFuture);
    }
    return paramListenableFuture;
  }
  
  public final void addCallback(FutureCallback<? super V> paramFutureCallback, Executor paramExecutor)
  {
    Futures.addCallback(this, paramFutureCallback, paramExecutor);
  }
  
  public void addListener(Runnable paramRunnable, Executor paramExecutor)
  {
    this.mDelegate.addListener(paramRunnable, paramExecutor);
  }
  
  public boolean cancel(boolean paramBoolean)
  {
    return this.mDelegate.cancel(paramBoolean);
  }
  
  public V get()
    throws InterruptedException, ExecutionException
  {
    return this.mDelegate.get();
  }
  
  public V get(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    return this.mDelegate.get(paramLong, paramTimeUnit);
  }
  
  public boolean isCancelled()
  {
    return this.mDelegate.isCancelled();
  }
  
  public boolean isDone()
  {
    return this.mDelegate.isDone();
  }
  
  boolean set(V paramV)
  {
    CallbackToFutureAdapter.Completer localCompleter = this.mCompleter;
    if (localCompleter != null) {
      return localCompleter.set(paramV);
    }
    return false;
  }
  
  boolean setException(Throwable paramThrowable)
  {
    CallbackToFutureAdapter.Completer localCompleter = this.mCompleter;
    if (localCompleter != null) {
      return localCompleter.setException(paramThrowable);
    }
    return false;
  }
  
  public final <T> FutureChain<T> transform(Function<? super V, T> paramFunction, Executor paramExecutor)
  {
    return (FutureChain)Futures.transform(this, paramFunction, paramExecutor);
  }
  
  public final <T> FutureChain<T> transformAsync(AsyncFunction<? super V, T> paramAsyncFunction, Executor paramExecutor)
  {
    return (FutureChain)Futures.transformAsync(this, paramAsyncFunction, paramExecutor);
  }
}
