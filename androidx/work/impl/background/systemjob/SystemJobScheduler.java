package androidx.work.impl.background.systemjob;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import androidx.work.Configuration;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.Scheduler;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.SystemIdInfoDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.IdGenerator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SystemJobScheduler
  implements Scheduler
{
  private static final String TAG = Logger.tagWithPrefix("SystemJobScheduler");
  private final Context mContext;
  private final JobScheduler mJobScheduler;
  private final SystemJobInfoConverter mSystemJobInfoConverter;
  private final WorkManagerImpl mWorkManager;
  
  public SystemJobScheduler(Context paramContext, WorkManagerImpl paramWorkManagerImpl)
  {
    this(paramContext, paramWorkManagerImpl, (JobScheduler)paramContext.getSystemService("jobscheduler"), new SystemJobInfoConverter(paramContext));
  }
  
  public SystemJobScheduler(Context paramContext, WorkManagerImpl paramWorkManagerImpl, JobScheduler paramJobScheduler, SystemJobInfoConverter paramSystemJobInfoConverter)
  {
    this.mContext = paramContext;
    this.mWorkManager = paramWorkManagerImpl;
    this.mJobScheduler = paramJobScheduler;
    this.mSystemJobInfoConverter = paramSystemJobInfoConverter;
  }
  
  public static void cancelAll(Context paramContext)
  {
    JobScheduler localJobScheduler = (JobScheduler)paramContext.getSystemService("jobscheduler");
    if (localJobScheduler != null)
    {
      paramContext = getPendingJobs(paramContext, localJobScheduler);
      if ((paramContext != null) && (!paramContext.isEmpty()))
      {
        paramContext = paramContext.iterator();
        while (paramContext.hasNext()) {
          cancelJobById(localJobScheduler, ((JobInfo)paramContext.next()).getId());
        }
      }
    }
  }
  
  public static void cancelInvalidJobs(Context paramContext)
  {
    JobScheduler localJobScheduler = (JobScheduler)paramContext.getSystemService("jobscheduler");
    if (localJobScheduler != null)
    {
      paramContext = getPendingJobs(paramContext, localJobScheduler);
      if ((paramContext != null) && (!paramContext.isEmpty()))
      {
        Iterator localIterator = paramContext.iterator();
        while (localIterator.hasNext())
        {
          paramContext = (JobInfo)localIterator.next();
          if (getWorkSpecIdFromJobInfo(paramContext) == null) {
            cancelJobById(localJobScheduler, paramContext.getId());
          }
        }
      }
    }
  }
  
  /* Error */
  private static void cancelJobById(JobScheduler paramJobScheduler, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: invokevirtual 102	android/app/job/JobScheduler:cancel	(I)V
    //   5: goto +40 -> 45
    //   8: astore_0
    //   9: invokestatic 106	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   12: getstatic 28	androidx/work/impl/background/systemjob/SystemJobScheduler:TAG	Ljava/lang/String;
    //   15: invokestatic 112	java/util/Locale:getDefault	()Ljava/util/Locale;
    //   18: ldc 114
    //   20: iconst_1
    //   21: anewarray 4	java/lang/Object
    //   24: dup
    //   25: iconst_0
    //   26: iload_1
    //   27: invokestatic 120	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   30: aastore
    //   31: invokestatic 126	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   34: iconst_1
    //   35: anewarray 128	java/lang/Throwable
    //   38: dup
    //   39: iconst_0
    //   40: aload_0
    //   41: aastore
    //   42: invokevirtual 132	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   45: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	46	0	paramJobScheduler	JobScheduler
    //   0	46	1	paramInt	int
    // Exception table:
    //   from	to	target	type
    //   0	5	8	finally
  }
  
  private static List<Integer> getPendingJobIds(Context paramContext, JobScheduler paramJobScheduler, String paramString)
  {
    paramJobScheduler = getPendingJobs(paramContext, paramJobScheduler);
    if (paramJobScheduler == null) {
      return null;
    }
    paramContext = new ArrayList(2);
    Iterator localIterator = paramJobScheduler.iterator();
    while (localIterator.hasNext())
    {
      paramJobScheduler = (JobInfo)localIterator.next();
      if (paramString.equals(getWorkSpecIdFromJobInfo(paramJobScheduler))) {
        paramContext.add(Integer.valueOf(paramJobScheduler.getId()));
      }
    }
    return paramContext;
  }
  
  /* Error */
  private static List<JobInfo> getPendingJobs(Context paramContext, JobScheduler paramJobScheduler)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 151	android/app/job/JobScheduler:getAllPendingJobs	()Ljava/util/List;
    //   4: astore_1
    //   5: goto +25 -> 30
    //   8: astore_1
    //   9: invokestatic 106	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   12: getstatic 28	androidx/work/impl/background/systemjob/SystemJobScheduler:TAG	Ljava/lang/String;
    //   15: ldc -103
    //   17: iconst_1
    //   18: anewarray 128	java/lang/Throwable
    //   21: dup
    //   22: iconst_0
    //   23: aload_1
    //   24: aastore
    //   25: invokevirtual 132	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   28: aconst_null
    //   29: astore_1
    //   30: aload_1
    //   31: ifnonnull +5 -> 36
    //   34: aconst_null
    //   35: areturn
    //   36: new 136	java/util/ArrayList
    //   39: dup
    //   40: aload_1
    //   41: invokeinterface 156 1 0
    //   46: invokespecial 138	java/util/ArrayList:<init>	(I)V
    //   49: astore_2
    //   50: new 158	android/content/ComponentName
    //   53: dup
    //   54: aload_0
    //   55: ldc -96
    //   57: invokespecial 163	android/content/ComponentName:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   60: astore_0
    //   61: aload_1
    //   62: invokeinterface 74 1 0
    //   67: astore_1
    //   68: aload_1
    //   69: invokeinterface 79 1 0
    //   74: ifeq +35 -> 109
    //   77: aload_1
    //   78: invokeinterface 83 1 0
    //   83: checkcast 85	android/app/job/JobInfo
    //   86: astore_3
    //   87: aload_0
    //   88: aload_3
    //   89: invokevirtual 167	android/app/job/JobInfo:getService	()Landroid/content/ComponentName;
    //   92: invokevirtual 168	android/content/ComponentName:equals	(Ljava/lang/Object;)Z
    //   95: ifeq -27 -> 68
    //   98: aload_2
    //   99: aload_3
    //   100: invokeinterface 145 2 0
    //   105: pop
    //   106: goto -38 -> 68
    //   109: aload_2
    //   110: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	111	0	paramContext	Context
    //   0	111	1	paramJobScheduler	JobScheduler
    //   49	61	2	localArrayList	ArrayList
    //   86	14	3	localJobInfo	JobInfo
    // Exception table:
    //   from	to	target	type
    //   0	5	8	finally
  }
  
  private static String getWorkSpecIdFromJobInfo(JobInfo paramJobInfo)
  {
    paramJobInfo = paramJobInfo.getExtras();
    if (paramJobInfo != null) {}
    try
    {
      if (paramJobInfo.containsKey("EXTRA_WORK_SPEC_ID"))
      {
        paramJobInfo = paramJobInfo.getString("EXTRA_WORK_SPEC_ID");
        return paramJobInfo;
      }
    }
    catch (NullPointerException paramJobInfo)
    {
      for (;;) {}
    }
    return null;
  }
  
  public void cancel(String paramString)
  {
    Object localObject = getPendingJobIds(this.mContext, this.mJobScheduler, paramString);
    if ((localObject != null) && (!((List)localObject).isEmpty()))
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        int i = ((Integer)((Iterator)localObject).next()).intValue();
        cancelJobById(this.mJobScheduler, i);
      }
      this.mWorkManager.getWorkDatabase().systemIdInfoDao().removeSystemIdInfo(paramString);
    }
  }
  
  public void schedule(WorkSpec... paramVarArgs)
  {
    WorkDatabase localWorkDatabase = this.mWorkManager.getWorkDatabase();
    IdGenerator localIdGenerator = new IdGenerator(localWorkDatabase);
    int i = paramVarArgs.length;
    int j = 0;
    while (j < i)
    {
      WorkSpec localWorkSpec = paramVarArgs[j];
      localWorkDatabase.beginTransaction();
      try
      {
        Object localObject1 = localWorkDatabase.workSpecDao().getWorkSpec(localWorkSpec.id);
        Object localObject2;
        Object localObject3;
        if (localObject1 == null)
        {
          localObject2 = Logger.get();
          localObject3 = TAG;
          localObject1 = new java/lang/StringBuilder;
          ((StringBuilder)localObject1).<init>();
          ((StringBuilder)localObject1).append("Skipping scheduling ");
          ((StringBuilder)localObject1).append(localWorkSpec.id);
          ((StringBuilder)localObject1).append(" because it's no longer in the DB");
          ((Logger)localObject2).warning((String)localObject3, ((StringBuilder)localObject1).toString(), new Throwable[0]);
          localWorkDatabase.setTransactionSuccessful();
        }
        else if (((WorkSpec)localObject1).state != WorkInfo.State.ENQUEUED)
        {
          localObject3 = Logger.get();
          localObject1 = TAG;
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          ((StringBuilder)localObject2).append("Skipping scheduling ");
          ((StringBuilder)localObject2).append(localWorkSpec.id);
          ((StringBuilder)localObject2).append(" because it is no longer enqueued");
          ((Logger)localObject3).warning((String)localObject1, ((StringBuilder)localObject2).toString(), new Throwable[0]);
          localWorkDatabase.setTransactionSuccessful();
        }
        else
        {
          localObject1 = localWorkDatabase.systemIdInfoDao().getSystemIdInfo(localWorkSpec.id);
          int k;
          if (localObject1 != null) {
            k = ((SystemIdInfo)localObject1).systemId;
          } else {
            k = localIdGenerator.nextJobSchedulerIdWithRange(this.mWorkManager.getConfiguration().getMinJobSchedulerId(), this.mWorkManager.getConfiguration().getMaxJobSchedulerId());
          }
          if (localObject1 == null)
          {
            localObject1 = new androidx/work/impl/model/SystemIdInfo;
            ((SystemIdInfo)localObject1).<init>(localWorkSpec.id, k);
            this.mWorkManager.getWorkDatabase().systemIdInfoDao().insertSystemIdInfo((SystemIdInfo)localObject1);
          }
          scheduleInternal(localWorkSpec, k);
          if (Build.VERSION.SDK_INT == 23)
          {
            localObject1 = getPendingJobIds(this.mContext, this.mJobScheduler, localWorkSpec.id);
            if (localObject1 != null)
            {
              k = ((List)localObject1).indexOf(Integer.valueOf(k));
              if (k >= 0) {
                ((List)localObject1).remove(k);
              }
              if (!((List)localObject1).isEmpty()) {
                k = ((Integer)((List)localObject1).get(0)).intValue();
              } else {
                k = localIdGenerator.nextJobSchedulerIdWithRange(this.mWorkManager.getConfiguration().getMinJobSchedulerId(), this.mWorkManager.getConfiguration().getMaxJobSchedulerId());
              }
              scheduleInternal(localWorkSpec, k);
            }
          }
          localWorkDatabase.setTransactionSuccessful();
        }
        localWorkDatabase.endTransaction();
        j++;
      }
      finally
      {
        localWorkDatabase.endTransaction();
      }
    }
  }
  
  /* Error */
  public void scheduleInternal(WorkSpec paramWorkSpec, int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 59	androidx/work/impl/background/systemjob/SystemJobScheduler:mSystemJobInfoConverter	Landroidx/work/impl/background/systemjob/SystemJobInfoConverter;
    //   4: aload_1
    //   5: iload_2
    //   6: invokevirtual 327	androidx/work/impl/background/systemjob/SystemJobInfoConverter:convert	(Landroidx/work/impl/model/WorkSpec;I)Landroid/app/job/JobInfo;
    //   9: astore_3
    //   10: invokestatic 106	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   13: getstatic 28	androidx/work/impl/background/systemjob/SystemJobScheduler:TAG	Ljava/lang/String;
    //   16: ldc_w 329
    //   19: iconst_2
    //   20: anewarray 4	java/lang/Object
    //   23: dup
    //   24: iconst_0
    //   25: aload_1
    //   26: getfield 228	androidx/work/impl/model/WorkSpec:id	Ljava/lang/String;
    //   29: aastore
    //   30: dup
    //   31: iconst_1
    //   32: iload_2
    //   33: invokestatic 120	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   36: aastore
    //   37: invokestatic 332	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   40: iconst_0
    //   41: anewarray 128	java/lang/Throwable
    //   44: invokevirtual 335	androidx/work/Logger:debug	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   47: aload_0
    //   48: getfield 57	androidx/work/impl/background/systemjob/SystemJobScheduler:mJobScheduler	Landroid/app/job/JobScheduler;
    //   51: aload_3
    //   52: invokevirtual 338	android/app/job/JobScheduler:schedule	(Landroid/app/job/JobInfo;)I
    //   55: pop
    //   56: goto +35 -> 91
    //   59: astore_3
    //   60: invokestatic 106	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   63: getstatic 28	androidx/work/impl/background/systemjob/SystemJobScheduler:TAG	Ljava/lang/String;
    //   66: ldc_w 340
    //   69: iconst_1
    //   70: anewarray 4	java/lang/Object
    //   73: dup
    //   74: iconst_0
    //   75: aload_1
    //   76: aastore
    //   77: invokestatic 332	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   80: iconst_1
    //   81: anewarray 128	java/lang/Throwable
    //   84: dup
    //   85: iconst_0
    //   86: aload_3
    //   87: aastore
    //   88: invokevirtual 132	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   91: return
    //   92: astore_1
    //   93: aload_0
    //   94: getfield 53	androidx/work/impl/background/systemjob/SystemJobScheduler:mContext	Landroid/content/Context;
    //   97: aload_0
    //   98: getfield 57	androidx/work/impl/background/systemjob/SystemJobScheduler:mJobScheduler	Landroid/app/job/JobScheduler;
    //   101: invokestatic 64	androidx/work/impl/background/systemjob/SystemJobScheduler:getPendingJobs	(Landroid/content/Context;Landroid/app/job/JobScheduler;)Ljava/util/List;
    //   104: astore_3
    //   105: aload_3
    //   106: ifnull +13 -> 119
    //   109: aload_3
    //   110: invokeinterface 156 1 0
    //   115: istore_2
    //   116: goto +5 -> 121
    //   119: iconst_0
    //   120: istore_2
    //   121: invokestatic 112	java/util/Locale:getDefault	()Ljava/util/Locale;
    //   124: ldc_w 342
    //   127: iconst_3
    //   128: anewarray 4	java/lang/Object
    //   131: dup
    //   132: iconst_0
    //   133: iload_2
    //   134: invokestatic 120	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   137: aastore
    //   138: dup
    //   139: iconst_1
    //   140: aload_0
    //   141: getfield 55	androidx/work/impl/background/systemjob/SystemJobScheduler:mWorkManager	Landroidx/work/impl/WorkManagerImpl;
    //   144: invokevirtual 198	androidx/work/impl/WorkManagerImpl:getWorkDatabase	()Landroidx/work/impl/WorkDatabase;
    //   147: invokevirtual 223	androidx/work/impl/WorkDatabase:workSpecDao	()Landroidx/work/impl/model/WorkSpecDao;
    //   150: invokeinterface 345 1 0
    //   155: invokeinterface 156 1 0
    //   160: invokestatic 120	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   163: aastore
    //   164: dup
    //   165: iconst_2
    //   166: aload_0
    //   167: getfield 55	androidx/work/impl/background/systemjob/SystemJobScheduler:mWorkManager	Landroidx/work/impl/WorkManagerImpl;
    //   170: invokevirtual 280	androidx/work/impl/WorkManagerImpl:getConfiguration	()Landroidx/work/Configuration;
    //   173: invokevirtual 348	androidx/work/Configuration:getMaxSchedulerLimit	()I
    //   176: invokestatic 120	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   179: aastore
    //   180: invokestatic 126	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   183: astore_3
    //   184: invokestatic 106	androidx/work/Logger:get	()Landroidx/work/Logger;
    //   187: getstatic 28	androidx/work/impl/background/systemjob/SystemJobScheduler:TAG	Ljava/lang/String;
    //   190: aload_3
    //   191: iconst_0
    //   192: anewarray 128	java/lang/Throwable
    //   195: invokevirtual 132	androidx/work/Logger:error	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Throwable;)V
    //   198: new 323	java/lang/IllegalStateException
    //   201: dup
    //   202: aload_3
    //   203: aload_1
    //   204: invokespecial 351	java/lang/IllegalStateException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   207: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	208	0	this	SystemJobScheduler
    //   0	208	1	paramWorkSpec	WorkSpec
    //   0	208	2	paramInt	int
    //   9	43	3	localJobInfo	JobInfo
    //   59	28	3	localObject1	Object
    //   104	99	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   47	56	59	finally
    //   47	56	92	java/lang/IllegalStateException
  }
}
