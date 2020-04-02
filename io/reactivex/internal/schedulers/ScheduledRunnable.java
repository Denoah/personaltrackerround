package io.reactivex.internal.schedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class ScheduledRunnable
  extends AtomicReferenceArray<Object>
  implements Runnable, Callable<Object>, Disposable
{
  static final Object ASYNC_DISPOSED = new Object();
  static final Object DONE = new Object();
  static final int FUTURE_INDEX = 1;
  static final Object PARENT_DISPOSED = new Object();
  static final int PARENT_INDEX = 0;
  static final Object SYNC_DISPOSED = new Object();
  static final int THREAD_INDEX = 2;
  private static final long serialVersionUID = -6120223772001106981L;
  final Runnable actual;
  
  public ScheduledRunnable(Runnable paramRunnable, DisposableContainer paramDisposableContainer)
  {
    super(3);
    this.actual = paramRunnable;
    lazySet(0, paramDisposableContainer);
  }
  
  public Object call()
  {
    run();
    return null;
  }
  
  public void dispose()
  {
    Object localObject1;
    boolean bool;
    Object localObject2;
    do
    {
      localObject1 = get(1);
      if ((localObject1 == DONE) || (localObject1 == SYNC_DISPOSED) || (localObject1 == ASYNC_DISPOSED)) {
        break;
      }
      if (get(2) != Thread.currentThread()) {
        bool = true;
      } else {
        bool = false;
      }
      if (bool) {
        localObject2 = ASYNC_DISPOSED;
      } else {
        localObject2 = SYNC_DISPOSED;
      }
    } while (!compareAndSet(1, localObject1, localObject2));
    if (localObject1 != null) {
      ((Future)localObject1).cancel(bool);
    }
    do
    {
      localObject1 = get(0);
      if (localObject1 == DONE) {
        break;
      }
      localObject2 = PARENT_DISPOSED;
      if ((localObject1 == localObject2) || (localObject1 == null)) {
        break;
      }
    } while (!compareAndSet(0, localObject1, localObject2));
    ((DisposableContainer)localObject1).delete(this);
  }
  
  public boolean isDisposed()
  {
    boolean bool = false;
    Object localObject = get(0);
    if ((localObject == PARENT_DISPOSED) || (localObject == DONE)) {
      bool = true;
    }
    return bool;
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: iconst_2
    //   2: invokestatic 71	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   5: invokevirtual 55	io/reactivex/internal/schedulers/ScheduledRunnable:lazySet	(ILjava/lang/Object;)V
    //   8: aload_0
    //   9: getfield 51	io/reactivex/internal/schedulers/ScheduledRunnable:actual	Ljava/lang/Runnable;
    //   12: invokeinterface 90 1 0
    //   17: goto +8 -> 25
    //   20: astore_1
    //   21: aload_1
    //   22: invokestatic 96	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   25: aload_0
    //   26: iconst_2
    //   27: aconst_null
    //   28: invokevirtual 55	io/reactivex/internal/schedulers/ScheduledRunnable:lazySet	(ILjava/lang/Object;)V
    //   31: aload_0
    //   32: iconst_0
    //   33: invokevirtual 65	io/reactivex/internal/schedulers/ScheduledRunnable:get	(I)Ljava/lang/Object;
    //   36: astore_1
    //   37: aload_1
    //   38: getstatic 38	io/reactivex/internal/schedulers/ScheduledRunnable:PARENT_DISPOSED	Ljava/lang/Object;
    //   41: if_acmpeq +30 -> 71
    //   44: aload_0
    //   45: iconst_0
    //   46: aload_1
    //   47: getstatic 44	io/reactivex/internal/schedulers/ScheduledRunnable:DONE	Ljava/lang/Object;
    //   50: invokevirtual 75	io/reactivex/internal/schedulers/ScheduledRunnable:compareAndSet	(ILjava/lang/Object;Ljava/lang/Object;)Z
    //   53: ifeq +18 -> 71
    //   56: aload_1
    //   57: ifnull +14 -> 71
    //   60: aload_1
    //   61: checkcast 83	io/reactivex/internal/disposables/DisposableContainer
    //   64: aload_0
    //   65: invokeinterface 87 2 0
    //   70: pop
    //   71: aload_0
    //   72: iconst_1
    //   73: invokevirtual 65	io/reactivex/internal/schedulers/ScheduledRunnable:get	(I)Ljava/lang/Object;
    //   76: astore_1
    //   77: aload_1
    //   78: getstatic 40	io/reactivex/internal/schedulers/ScheduledRunnable:SYNC_DISPOSED	Ljava/lang/Object;
    //   81: if_acmpeq +22 -> 103
    //   84: aload_1
    //   85: getstatic 42	io/reactivex/internal/schedulers/ScheduledRunnable:ASYNC_DISPOSED	Ljava/lang/Object;
    //   88: if_acmpeq +15 -> 103
    //   91: aload_0
    //   92: iconst_1
    //   93: aload_1
    //   94: getstatic 44	io/reactivex/internal/schedulers/ScheduledRunnable:DONE	Ljava/lang/Object;
    //   97: invokevirtual 75	io/reactivex/internal/schedulers/ScheduledRunnable:compareAndSet	(ILjava/lang/Object;Ljava/lang/Object;)Z
    //   100: ifeq -29 -> 71
    //   103: return
    //   104: astore_1
    //   105: aload_0
    //   106: iconst_2
    //   107: aconst_null
    //   108: invokevirtual 55	io/reactivex/internal/schedulers/ScheduledRunnable:lazySet	(ILjava/lang/Object;)V
    //   111: aload_0
    //   112: iconst_0
    //   113: invokevirtual 65	io/reactivex/internal/schedulers/ScheduledRunnable:get	(I)Ljava/lang/Object;
    //   116: astore_2
    //   117: aload_2
    //   118: getstatic 38	io/reactivex/internal/schedulers/ScheduledRunnable:PARENT_DISPOSED	Ljava/lang/Object;
    //   121: if_acmpeq +30 -> 151
    //   124: aload_0
    //   125: iconst_0
    //   126: aload_2
    //   127: getstatic 44	io/reactivex/internal/schedulers/ScheduledRunnable:DONE	Ljava/lang/Object;
    //   130: invokevirtual 75	io/reactivex/internal/schedulers/ScheduledRunnable:compareAndSet	(ILjava/lang/Object;Ljava/lang/Object;)Z
    //   133: ifeq +18 -> 151
    //   136: aload_2
    //   137: ifnull +14 -> 151
    //   140: aload_2
    //   141: checkcast 83	io/reactivex/internal/disposables/DisposableContainer
    //   144: aload_0
    //   145: invokeinterface 87 2 0
    //   150: pop
    //   151: aload_0
    //   152: iconst_1
    //   153: invokevirtual 65	io/reactivex/internal/schedulers/ScheduledRunnable:get	(I)Ljava/lang/Object;
    //   156: astore_2
    //   157: aload_2
    //   158: getstatic 40	io/reactivex/internal/schedulers/ScheduledRunnable:SYNC_DISPOSED	Ljava/lang/Object;
    //   161: if_acmpeq +25 -> 186
    //   164: aload_2
    //   165: getstatic 42	io/reactivex/internal/schedulers/ScheduledRunnable:ASYNC_DISPOSED	Ljava/lang/Object;
    //   168: if_acmpeq +18 -> 186
    //   171: aload_0
    //   172: iconst_1
    //   173: aload_2
    //   174: getstatic 44	io/reactivex/internal/schedulers/ScheduledRunnable:DONE	Ljava/lang/Object;
    //   177: invokevirtual 75	io/reactivex/internal/schedulers/ScheduledRunnable:compareAndSet	(ILjava/lang/Object;Ljava/lang/Object;)Z
    //   180: ifne +6 -> 186
    //   183: goto -32 -> 151
    //   186: aload_1
    //   187: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	188	0	this	ScheduledRunnable
    //   20	2	1	localThrowable	Throwable
    //   36	58	1	localObject1	Object
    //   104	83	1	localObject2	Object
    //   116	58	2	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   8	17	20	finally
    //   21	25	104	finally
  }
  
  public void setFuture(Future<?> paramFuture)
  {
    Object localObject;
    do
    {
      localObject = get(1);
      if (localObject == DONE) {
        return;
      }
      if (localObject == SYNC_DISPOSED)
      {
        paramFuture.cancel(false);
        return;
      }
      if (localObject == ASYNC_DISPOSED)
      {
        paramFuture.cancel(true);
        return;
      }
    } while (!compareAndSet(1, localObject, paramFuture));
  }
}
