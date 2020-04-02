package androidx.camera.core.impl.utils.futures;

import androidx.arch.core.util.Function;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

public final class Futures
{
  private static final Function<?, ?> IDENTITY_FUNCTION = new Function()
  {
    public Object apply(Object paramAnonymousObject)
    {
      return paramAnonymousObject;
    }
  };
  
  private Futures() {}
  
  public static <V> void addCallback(ListenableFuture<V> paramListenableFuture, FutureCallback<? super V> paramFutureCallback, Executor paramExecutor)
  {
    Preconditions.checkNotNull(paramFutureCallback);
    paramListenableFuture.addListener(new CallbackListener(paramListenableFuture, paramFutureCallback), paramExecutor);
  }
  
  public static <V> ListenableFuture<List<V>> allAsList(Collection<? extends ListenableFuture<? extends V>> paramCollection)
  {
    return new ListFuture(new ArrayList(paramCollection), true, CameraXExecutors.directExecutor());
  }
  
  public static <V> V getDone(Future<V> paramFuture)
    throws ExecutionException
  {
    boolean bool = paramFuture.isDone();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Future was expected to be done, ");
    localStringBuilder.append(paramFuture);
    Preconditions.checkState(bool, localStringBuilder.toString());
    return getUninterruptibly(paramFuture);
  }
  
  /* Error */
  public static <V> V getUninterruptibly(Future<V> paramFuture)
    throws ExecutionException
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: aload_0
    //   3: invokeinterface 106 1 0
    //   8: astore_2
    //   9: iload_1
    //   10: ifeq +9 -> 19
    //   13: invokestatic 112	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   16: invokevirtual 115	java/lang/Thread:interrupt	()V
    //   19: aload_2
    //   20: areturn
    //   21: astore_0
    //   22: iload_1
    //   23: ifeq +9 -> 32
    //   26: invokestatic 112	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   29: invokevirtual 115	java/lang/Thread:interrupt	()V
    //   32: aload_0
    //   33: athrow
    //   34: astore_2
    //   35: iconst_1
    //   36: istore_1
    //   37: goto -35 -> 2
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	40	0	paramFuture	Future<V>
    //   1	36	1	i	int
    //   8	12	2	localObject	Object
    //   34	1	2	localInterruptedException	InterruptedException
    // Exception table:
    //   from	to	target	type
    //   2	9	21	finally
    //   2	9	34	java/lang/InterruptedException
  }
  
  public static <V> ListenableFuture<V> immediateFailedFuture(Throwable paramThrowable)
  {
    return new ImmediateFuture.ImmediateFailedFuture(paramThrowable);
  }
  
  public static <V> ScheduledFuture<V> immediateFailedScheduledFuture(Throwable paramThrowable)
  {
    return new ImmediateFuture.ImmediateFailedScheduledFuture(paramThrowable);
  }
  
  public static <V> ListenableFuture<V> immediateFuture(V paramV)
  {
    if (paramV == null) {
      return ImmediateFuture.nullFuture();
    }
    return new ImmediateFuture.ImmediateSuccessfulFuture(paramV);
  }
  
  public static <V> ListenableFuture<V> nonCancellationPropagating(ListenableFuture<V> paramListenableFuture)
  {
    Preconditions.checkNotNull(paramListenableFuture);
    if (paramListenableFuture.isDone()) {
      return paramListenableFuture;
    }
    return CallbackToFutureAdapter.getFuture(new _..Lambda.Futures.BFJU90gKHywJ5fHtASrMxI3JslQ(paramListenableFuture));
  }
  
  public static <V> void propagate(ListenableFuture<V> paramListenableFuture, CallbackToFutureAdapter.Completer<V> paramCompleter)
  {
    propagateTransform(paramListenableFuture, IDENTITY_FUNCTION, paramCompleter, CameraXExecutors.directExecutor());
  }
  
  public static <I, O> void propagateTransform(ListenableFuture<I> paramListenableFuture, Function<? super I, ? extends O> paramFunction, CallbackToFutureAdapter.Completer<O> paramCompleter, Executor paramExecutor)
  {
    propagateTransform(true, paramListenableFuture, paramFunction, paramCompleter, paramExecutor);
  }
  
  private static <I, O> void propagateTransform(boolean paramBoolean, ListenableFuture<I> paramListenableFuture, final Function<? super I, ? extends O> paramFunction, CallbackToFutureAdapter.Completer<O> paramCompleter, Executor paramExecutor)
  {
    Preconditions.checkNotNull(paramListenableFuture);
    Preconditions.checkNotNull(paramFunction);
    Preconditions.checkNotNull(paramCompleter);
    Preconditions.checkNotNull(paramExecutor);
    addCallback(paramListenableFuture, new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        this.val$completer.setException(paramAnonymousThrowable);
      }
      
      /* Error */
      public void onSuccess(I paramAnonymousI)
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 20	androidx/camera/core/impl/utils/futures/Futures$3:val$completer	Landroidx/concurrent/futures/CallbackToFutureAdapter$Completer;
        //   4: aload_0
        //   5: getfield 22	androidx/camera/core/impl/utils/futures/Futures$3:val$function	Landroidx/arch/core/util/Function;
        //   8: aload_1
        //   9: invokeinterface 42 2 0
        //   14: invokevirtual 46	androidx/concurrent/futures/CallbackToFutureAdapter$Completer:set	(Ljava/lang/Object;)Z
        //   17: pop
        //   18: goto +13 -> 31
        //   21: astore_1
        //   22: aload_0
        //   23: getfield 20	androidx/camera/core/impl/utils/futures/Futures$3:val$completer	Landroidx/concurrent/futures/CallbackToFutureAdapter$Completer;
        //   26: aload_1
        //   27: invokevirtual 34	androidx/concurrent/futures/CallbackToFutureAdapter$Completer:setException	(Ljava/lang/Throwable;)Z
        //   30: pop
        //   31: return
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	32	0	this	3
        //   0	32	1	paramAnonymousI	I
        // Exception table:
        //   from	to	target	type
        //   0	18	21	finally
      }
    }, paramExecutor);
    if (paramBoolean) {
      paramCompleter.addCancellationListener(new Runnable()
      {
        public void run()
        {
          this.val$input.cancel(true);
        }
      }, CameraXExecutors.directExecutor());
    }
  }
  
  public static <V> ListenableFuture<List<V>> successfulAsList(Collection<? extends ListenableFuture<? extends V>> paramCollection)
  {
    return new ListFuture(new ArrayList(paramCollection), false, CameraXExecutors.directExecutor());
  }
  
  public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> paramListenableFuture, Function<? super I, ? extends O> paramFunction, Executor paramExecutor)
  {
    Preconditions.checkNotNull(paramFunction);
    transformAsync(paramListenableFuture, new AsyncFunction()
    {
      public ListenableFuture<O> apply(I paramAnonymousI)
      {
        return Futures.immediateFuture(this.val$function.apply(paramAnonymousI));
      }
    }, paramExecutor);
  }
  
  public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> paramListenableFuture, AsyncFunction<? super I, ? extends O> paramAsyncFunction, Executor paramExecutor)
  {
    paramAsyncFunction = new ChainingListenableFuture(paramAsyncFunction, paramListenableFuture);
    paramListenableFuture.addListener(paramAsyncFunction, paramExecutor);
    return paramAsyncFunction;
  }
  
  private static final class CallbackListener<V>
    implements Runnable
  {
    final FutureCallback<? super V> mCallback;
    final Future<V> mFuture;
    
    CallbackListener(Future<V> paramFuture, FutureCallback<? super V> paramFutureCallback)
    {
      this.mFuture = paramFuture;
      this.mCallback = paramFutureCallback;
    }
    
    public void run()
    {
      try
      {
        try
        {
          Object localObject = Futures.getDone(this.mFuture);
          this.mCallback.onSuccess(localObject);
          return;
        }
        catch (Error localError) {}catch (RuntimeException localRuntimeException) {}
        this.mCallback.onFailure(localRuntimeException);
        return;
      }
      catch (ExecutionException localExecutionException)
      {
        this.mCallback.onFailure(localExecutionException.getCause());
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(getClass().getSimpleName());
      localStringBuilder.append(",");
      localStringBuilder.append(this.mCallback);
      return localStringBuilder.toString();
    }
  }
}
