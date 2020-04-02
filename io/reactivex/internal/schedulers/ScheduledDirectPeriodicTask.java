package io.reactivex.internal.schedulers;

public final class ScheduledDirectPeriodicTask
  extends AbstractDirectTask
  implements Runnable
{
  private static final long serialVersionUID = 1811839108042568751L;
  
  public ScheduledDirectPeriodicTask(Runnable paramRunnable)
  {
    super(paramRunnable);
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 27	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   4: putfield 31	io/reactivex/internal/schedulers/ScheduledDirectPeriodicTask:runner	Ljava/lang/Thread;
    //   7: aload_0
    //   8: getfield 35	io/reactivex/internal/schedulers/ScheduledDirectPeriodicTask:runnable	Ljava/lang/Runnable;
    //   11: invokeinterface 37 1 0
    //   16: aload_0
    //   17: aconst_null
    //   18: putfield 31	io/reactivex/internal/schedulers/ScheduledDirectPeriodicTask:runner	Ljava/lang/Thread;
    //   21: goto +20 -> 41
    //   24: astore_1
    //   25: aload_0
    //   26: aconst_null
    //   27: putfield 31	io/reactivex/internal/schedulers/ScheduledDirectPeriodicTask:runner	Ljava/lang/Thread;
    //   30: aload_0
    //   31: getstatic 41	io/reactivex/internal/schedulers/ScheduledDirectPeriodicTask:FINISHED	Ljava/util/concurrent/FutureTask;
    //   34: invokevirtual 45	io/reactivex/internal/schedulers/ScheduledDirectPeriodicTask:lazySet	(Ljava/lang/Object;)V
    //   37: aload_1
    //   38: invokestatic 51	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   41: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	42	0	this	ScheduledDirectPeriodicTask
    //   24	14	1	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   7	21	24	finally
  }
}
