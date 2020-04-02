package androidx.work.impl.workers;

import android.content.Context;
import androidx.work.ListenableWorker;
import androidx.work.ListenableWorker.Result;
import androidx.work.Logger;
import androidx.work.WorkerParameters;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.concurrent.Executor;

public class ConstraintTrackingWorker
  extends ListenableWorker
  implements WorkConstraintsCallback
{
  public static final String ARGUMENT_CLASS_NAME = "androidx.work.impl.workers.ConstraintTrackingWorker.ARGUMENT_CLASS_NAME";
  private static final String TAG = Logger.tagWithPrefix("ConstraintTrkngWrkr");
  volatile boolean mAreConstraintsUnmet;
  private ListenableWorker mDelegate;
  SettableFuture<ListenableWorker.Result> mFuture;
  final Object mLock;
  private WorkerParameters mWorkerParameters;
  
  public ConstraintTrackingWorker(Context paramContext, WorkerParameters paramWorkerParameters)
  {
    super(paramContext, paramWorkerParameters);
    this.mWorkerParameters = paramWorkerParameters;
    this.mLock = new Object();
    this.mAreConstraintsUnmet = false;
    this.mFuture = SettableFuture.create();
  }
  
  public ListenableWorker getDelegate()
  {
    return this.mDelegate;
  }
  
  public TaskExecutor getTaskExecutor()
  {
    return WorkManagerImpl.getInstance(getApplicationContext()).getWorkTaskExecutor();
  }
  
  public WorkDatabase getWorkDatabase()
  {
    return WorkManagerImpl.getInstance(getApplicationContext()).getWorkDatabase();
  }
  
  public void onAllConstraintsMet(List<String> paramList) {}
  
  public void onAllConstraintsNotMet(List<String> arg1)
  {
    Logger.get().debug(TAG, String.format("Constraints changed for %s", new Object[] { ??? }), new Throwable[0]);
    synchronized (this.mLock)
    {
      this.mAreConstraintsUnmet = true;
      return;
    }
  }
  
  public void onStopped()
  {
    super.onStopped();
    ListenableWorker localListenableWorker = this.mDelegate;
    if (localListenableWorker != null) {
      localListenableWorker.stop();
    }
  }
  
  void setFutureFailed()
  {
    this.mFuture.set(ListenableWorker.Result.failure());
  }
  
  void setFutureRetry()
  {
    this.mFuture.set(ListenableWorker.Result.retry());
  }
  
  /* Error */
  void setupAndRunConstraintTrackingWork()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 133	androidx/work/impl/workers/ConstraintTrackingWorker:getInputData	()Landroidx/work/Data;
    //   4: ldc 14
    //   6: invokevirtual 138	androidx/work/Data:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   9: astore_1
    //   10: aload_1
    //   11: invokestatic 144	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   14: ifeq +23 -> 37
    //   17: invokestatic 93	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   20: getstatic 38	androidx/work/impl/workers/ConstraintTrackingWorker:TAG	Ljava/lang/String;
    //   23: ldc -110
    //   25: iconst_0
    //   26: anewarray 103	java/lang/Throwable
    //   29: invokevirtual 149	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   32: aload_0
    //   33: invokevirtual 151	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureFailed	()V
    //   36: return
    //   37: aload_0
    //   38: invokevirtual 155	androidx/work/impl/workers/ConstraintTrackingWorker:getWorkerFactory	()Landroidx/work/WorkerFactory;
    //   41: aload_0
    //   42: invokevirtual 71	androidx/work/impl/workers/ConstraintTrackingWorker:getApplicationContext	()Landroid/content/Context;
    //   45: aload_1
    //   46: aload_0
    //   47: getfield 45	androidx/work/impl/workers/ConstraintTrackingWorker:mWorkerParameters	Landroidx/work/WorkerParameters;
    //   50: invokevirtual 161	androidx/work/WorkerFactory:createWorkerWithDefaultFallback	(Landroid/content/Context;Ljava/lang/String;Landroidx/work/WorkerParameters;)Landroidx/work/ListenableWorker;
    //   53: astore_2
    //   54: aload_0
    //   55: aload_2
    //   56: putfield 65	androidx/work/impl/workers/ConstraintTrackingWorker:mDelegate	Landroidx/work/ListenableWorker;
    //   59: aload_2
    //   60: ifnonnull +23 -> 83
    //   63: invokestatic 93	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   66: getstatic 38	androidx/work/impl/workers/ConstraintTrackingWorker:TAG	Ljava/lang/String;
    //   69: ldc -110
    //   71: iconst_0
    //   72: anewarray 103	java/lang/Throwable
    //   75: invokevirtual 107	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   78: aload_0
    //   79: invokevirtual 151	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureFailed	()V
    //   82: return
    //   83: aload_0
    //   84: invokevirtual 162	androidx/work/impl/workers/ConstraintTrackingWorker:getWorkDatabase	()Landroidx/work/impl/WorkDatabase;
    //   87: invokevirtual 168	androidx/work/impl/WorkDatabase:workSpecDao	()Landroidx/work/impl/model/WorkSpecDao;
    //   90: aload_0
    //   91: invokevirtual 172	androidx/work/impl/workers/ConstraintTrackingWorker:getId	()Ljava/util/UUID;
    //   94: invokevirtual 178	java/util/UUID:toString	()Ljava/lang/String;
    //   97: invokeinterface 184 2 0
    //   102: astore_2
    //   103: aload_2
    //   104: ifnonnull +8 -> 112
    //   107: aload_0
    //   108: invokevirtual 151	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureFailed	()V
    //   111: return
    //   112: new 186	androidx/work/impl/constraints/WorkConstraintsTracker
    //   115: dup
    //   116: aload_0
    //   117: invokevirtual 71	androidx/work/impl/workers/ConstraintTrackingWorker:getApplicationContext	()Landroid/content/Context;
    //   120: aload_0
    //   121: invokevirtual 188	androidx/work/impl/workers/ConstraintTrackingWorker:getTaskExecutor	()Landroidx/work/impl/utils/taskexecutor/TaskExecutor;
    //   124: aload_0
    //   125: invokespecial 191	androidx/work/impl/constraints/WorkConstraintsTracker:<init>	(Landroid/content/Context;Landroidx/work/impl/utils/taskexecutor/TaskExecutor;Landroidx/work/impl/constraints/WorkConstraintsCallback;)V
    //   128: astore_3
    //   129: aload_3
    //   130: aload_2
    //   131: invokestatic 197	java/util/Collections:singletonList	(Ljava/lang/Object;)Ljava/util/List;
    //   134: invokevirtual 201	androidx/work/impl/constraints/WorkConstraintsTracker:replace	(Ljava/lang/Iterable;)V
    //   137: aload_3
    //   138: aload_0
    //   139: invokevirtual 172	androidx/work/impl/workers/ConstraintTrackingWorker:getId	()Ljava/util/UUID;
    //   142: invokevirtual 178	java/util/UUID:toString	()Ljava/lang/String;
    //   145: invokevirtual 205	androidx/work/impl/constraints/WorkConstraintsTracker:areAllConstraintsMet	(Ljava/lang/String;)Z
    //   148: ifeq +142 -> 290
    //   151: invokestatic 93	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   154: getstatic 38	androidx/work/impl/workers/ConstraintTrackingWorker:TAG	Ljava/lang/String;
    //   157: ldc -49
    //   159: iconst_1
    //   160: anewarray 47	java/lang/Object
    //   163: dup
    //   164: iconst_0
    //   165: aload_1
    //   166: aastore
    //   167: invokestatic 101	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   170: iconst_0
    //   171: anewarray 103	java/lang/Throwable
    //   174: invokevirtual 107	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   177: aload_0
    //   178: getfield 65	androidx/work/impl/workers/ConstraintTrackingWorker:mDelegate	Landroidx/work/ListenableWorker;
    //   181: invokevirtual 211	androidx/work/ListenableWorker:startWork	()Lcom/google/common/util/concurrent/ListenableFuture;
    //   184: astore_3
    //   185: new 10	androidx/work/impl/workers/ConstraintTrackingWorker$2
    //   188: astore_2
    //   189: aload_2
    //   190: aload_0
    //   191: aload_3
    //   192: invokespecial 214	androidx/work/impl/workers/ConstraintTrackingWorker$2:<init>	(Landroidx/work/impl/workers/ConstraintTrackingWorker;Lcom/google/common/util/concurrent/ListenableFuture;)V
    //   195: aload_3
    //   196: aload_2
    //   197: aload_0
    //   198: invokevirtual 218	androidx/work/impl/workers/ConstraintTrackingWorker:getBackgroundExecutor	()Ljava/util/concurrent/Executor;
    //   201: invokeinterface 224 3 0
    //   206: goto +114 -> 320
    //   209: astore_2
    //   210: invokestatic 93	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   213: getstatic 38	androidx/work/impl/workers/ConstraintTrackingWorker:TAG	Ljava/lang/String;
    //   216: ldc -30
    //   218: iconst_1
    //   219: anewarray 47	java/lang/Object
    //   222: dup
    //   223: iconst_0
    //   224: aload_1
    //   225: aastore
    //   226: invokestatic 101	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   229: iconst_1
    //   230: anewarray 103	java/lang/Throwable
    //   233: dup
    //   234: iconst_0
    //   235: aload_2
    //   236: aastore
    //   237: invokevirtual 107	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   240: aload_0
    //   241: getfield 51	androidx/work/impl/workers/ConstraintTrackingWorker:mLock	Ljava/lang/Object;
    //   244: astore_2
    //   245: aload_2
    //   246: monitorenter
    //   247: aload_0
    //   248: getfield 53	androidx/work/impl/workers/ConstraintTrackingWorker:mAreConstraintsUnmet	Z
    //   251: ifeq +25 -> 276
    //   254: invokestatic 93	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   257: getstatic 38	androidx/work/impl/workers/ConstraintTrackingWorker:TAG	Ljava/lang/String;
    //   260: ldc -28
    //   262: iconst_0
    //   263: anewarray 103	java/lang/Throwable
    //   266: invokevirtual 107	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   269: aload_0
    //   270: invokevirtual 230	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureRetry	()V
    //   273: goto +7 -> 280
    //   276: aload_0
    //   277: invokevirtual 151	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureFailed	()V
    //   280: aload_2
    //   281: monitorexit
    //   282: goto +38 -> 320
    //   285: astore_1
    //   286: aload_2
    //   287: monitorexit
    //   288: aload_1
    //   289: athrow
    //   290: invokestatic 93	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   293: getstatic 38	androidx/work/impl/workers/ConstraintTrackingWorker:TAG	Ljava/lang/String;
    //   296: ldc -24
    //   298: iconst_1
    //   299: anewarray 47	java/lang/Object
    //   302: dup
    //   303: iconst_0
    //   304: aload_1
    //   305: aastore
    //   306: invokestatic 101	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   309: iconst_0
    //   310: anewarray 103	java/lang/Throwable
    //   313: invokevirtual 107	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   316: aload_0
    //   317: invokevirtual 230	androidx/work/impl/workers/ConstraintTrackingWorker:setFutureRetry	()V
    //   320: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	321	0	this	ConstraintTrackingWorker
    //   9	216	1	str	String
    //   285	20	1	localObject1	Object
    //   53	144	2	localObject2	Object
    //   209	27	2	localObject3	Object
    //   128	68	3	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   177	206	209	finally
    //   247	273	285	finally
    //   276	280	285	finally
    //   280	282	285	finally
    //   286	288	285	finally
  }
  
  public ListenableFuture<ListenableWorker.Result> startWork()
  {
    getBackgroundExecutor().execute(new Runnable()
    {
      public void run()
      {
        ConstraintTrackingWorker.this.setupAndRunConstraintTrackingWork();
      }
    });
    return this.mFuture;
  }
}
