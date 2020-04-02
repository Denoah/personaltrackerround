package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler
  implements ExecutionListener
{
  static final String ACTION_CONSTRAINTS_CHANGED = "ACTION_CONSTRAINTS_CHANGED";
  static final String ACTION_DELAY_MET = "ACTION_DELAY_MET";
  static final String ACTION_EXECUTION_COMPLETED = "ACTION_EXECUTION_COMPLETED";
  static final String ACTION_RESCHEDULE = "ACTION_RESCHEDULE";
  static final String ACTION_SCHEDULE_WORK = "ACTION_SCHEDULE_WORK";
  static final String ACTION_STOP_WORK = "ACTION_STOP_WORK";
  private static final String KEY_NEEDS_RESCHEDULE = "KEY_NEEDS_RESCHEDULE";
  private static final String KEY_WORKSPEC_ID = "KEY_WORKSPEC_ID";
  private static final String TAG = Logger.tagWithPrefix("CommandHandler");
  static final long WORK_PROCESSING_TIME_IN_MS = 600000L;
  private final Context mContext;
  private final Object mLock;
  private final Map<String, ExecutionListener> mPendingDelayMet;
  
  CommandHandler(Context paramContext)
  {
    this.mContext = paramContext;
    this.mPendingDelayMet = new HashMap();
    this.mLock = new Object();
  }
  
  static Intent createConstraintsChangedIntent(Context paramContext)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_CONSTRAINTS_CHANGED");
    return paramContext;
  }
  
  static Intent createDelayMetIntent(Context paramContext, String paramString)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_DELAY_MET");
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    return paramContext;
  }
  
  static Intent createExecutionCompletedIntent(Context paramContext, String paramString, boolean paramBoolean)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_EXECUTION_COMPLETED");
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    paramContext.putExtra("KEY_NEEDS_RESCHEDULE", paramBoolean);
    return paramContext;
  }
  
  static Intent createRescheduleIntent(Context paramContext)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_RESCHEDULE");
    return paramContext;
  }
  
  static Intent createScheduleWorkIntent(Context paramContext, String paramString)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_SCHEDULE_WORK");
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    return paramContext;
  }
  
  static Intent createStopWorkIntent(Context paramContext, String paramString)
  {
    paramContext = new Intent(paramContext, SystemAlarmService.class);
    paramContext.setAction("ACTION_STOP_WORK");
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    return paramContext;
  }
  
  private void handleConstraintsChanged(Intent paramIntent, int paramInt, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    Logger.get().debug(TAG, String.format("Handling constraints changed %s", new Object[] { paramIntent }), new Throwable[0]);
    new ConstraintsCommandHandler(this.mContext, paramInt, paramSystemAlarmDispatcher).handleConstraintsChanged();
  }
  
  private void handleDelayMet(Intent arg1, int paramInt, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    Object localObject = ???.getExtras();
    synchronized (this.mLock)
    {
      localObject = ((Bundle)localObject).getString("KEY_WORKSPEC_ID");
      Logger.get().debug(TAG, String.format("Handing delay met for %s", new Object[] { localObject }), new Throwable[0]);
      if (!this.mPendingDelayMet.containsKey(localObject))
      {
        DelayMetCommandHandler localDelayMetCommandHandler = new androidx/work/impl/background/systemalarm/DelayMetCommandHandler;
        localDelayMetCommandHandler.<init>(this.mContext, paramInt, (String)localObject, paramSystemAlarmDispatcher);
        this.mPendingDelayMet.put(localObject, localDelayMetCommandHandler);
        localDelayMetCommandHandler.handleProcessWork();
      }
      else
      {
        Logger.get().debug(TAG, String.format("WorkSpec %s is already being handled for ACTION_DELAY_MET", new Object[] { localObject }), new Throwable[0]);
      }
      return;
    }
  }
  
  private void handleExecutionCompleted(Intent paramIntent, int paramInt)
  {
    Bundle localBundle = paramIntent.getExtras();
    String str = localBundle.getString("KEY_WORKSPEC_ID");
    boolean bool = localBundle.getBoolean("KEY_NEEDS_RESCHEDULE");
    Logger.get().debug(TAG, String.format("Handling onExecutionCompleted %s, %s", new Object[] { paramIntent, Integer.valueOf(paramInt) }), new Throwable[0]);
    onExecuted(str, bool);
  }
  
  private void handleReschedule(Intent paramIntent, int paramInt, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    Logger.get().debug(TAG, String.format("Handling reschedule %s, %s", new Object[] { paramIntent, Integer.valueOf(paramInt) }), new Throwable[0]);
    paramSystemAlarmDispatcher.getWorkManager().rescheduleEligibleWork();
  }
  
  private void handleScheduleWorkIntent(Intent paramIntent, int paramInt, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    Object localObject1 = paramIntent.getExtras().getString("KEY_WORKSPEC_ID");
    Logger.get().debug(TAG, String.format("Handling schedule work for %s", new Object[] { localObject1 }), new Throwable[0]);
    paramIntent = paramSystemAlarmDispatcher.getWorkManager().getWorkDatabase();
    paramIntent.beginTransaction();
    try
    {
      Object localObject2 = paramIntent.workSpecDao().getWorkSpec((String)localObject1);
      Object localObject3;
      if (localObject2 == null)
      {
        localObject2 = Logger.get();
        localObject3 = TAG;
        paramSystemAlarmDispatcher = new java/lang/StringBuilder;
        paramSystemAlarmDispatcher.<init>();
        paramSystemAlarmDispatcher.append("Skipping scheduling ");
        paramSystemAlarmDispatcher.append((String)localObject1);
        paramSystemAlarmDispatcher.append(" because it's no longer in the DB");
        ((Logger)localObject2).warning((String)localObject3, paramSystemAlarmDispatcher.toString(), new Throwable[0]);
        return;
      }
      if (((WorkSpec)localObject2).state.isFinished())
      {
        localObject3 = Logger.get();
        localObject2 = TAG;
        paramSystemAlarmDispatcher = new java/lang/StringBuilder;
        paramSystemAlarmDispatcher.<init>();
        paramSystemAlarmDispatcher.append("Skipping scheduling ");
        paramSystemAlarmDispatcher.append((String)localObject1);
        paramSystemAlarmDispatcher.append("because it is finished.");
        ((Logger)localObject3).warning((String)localObject2, paramSystemAlarmDispatcher.toString(), new Throwable[0]);
        return;
      }
      long l = ((WorkSpec)localObject2).calculateNextRunTime();
      if (!((WorkSpec)localObject2).hasConstraints())
      {
        Logger.get().debug(TAG, String.format("Setting up Alarms for %s at %s", new Object[] { localObject1, Long.valueOf(l) }), new Throwable[0]);
        Alarms.setAlarm(this.mContext, paramSystemAlarmDispatcher.getWorkManager(), (String)localObject1, l);
      }
      else
      {
        Logger.get().debug(TAG, String.format("Opportunistically setting an alarm for %s at %s", new Object[] { localObject1, Long.valueOf(l) }), new Throwable[0]);
        Alarms.setAlarm(this.mContext, paramSystemAlarmDispatcher.getWorkManager(), (String)localObject1, l);
        localObject2 = createConstraintsChangedIntent(this.mContext);
        localObject1 = new androidx/work/impl/background/systemalarm/SystemAlarmDispatcher$AddRunnable;
        ((SystemAlarmDispatcher.AddRunnable)localObject1).<init>(paramSystemAlarmDispatcher, (Intent)localObject2, paramInt);
        paramSystemAlarmDispatcher.postOnMainThread((Runnable)localObject1);
      }
      paramIntent.setTransactionSuccessful();
      return;
    }
    finally
    {
      paramIntent.endTransaction();
    }
  }
  
  private void handleStopWork(Intent paramIntent, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    paramIntent = paramIntent.getExtras().getString("KEY_WORKSPEC_ID");
    Logger.get().debug(TAG, String.format("Handing stopWork work for %s", new Object[] { paramIntent }), new Throwable[0]);
    paramSystemAlarmDispatcher.getWorkManager().stopWork(paramIntent);
    Alarms.cancelAlarm(this.mContext, paramSystemAlarmDispatcher.getWorkManager(), paramIntent);
    paramSystemAlarmDispatcher.onExecuted(paramIntent, false);
  }
  
  private static boolean hasKeys(Bundle paramBundle, String... paramVarArgs)
  {
    if ((paramBundle != null) && (!paramBundle.isEmpty()))
    {
      int i = paramVarArgs.length;
      for (int j = 0; j < i; j++) {
        if (paramBundle.get(paramVarArgs[j]) == null) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  boolean hasPendingCommands()
  {
    synchronized (this.mLock)
    {
      boolean bool;
      if (!this.mPendingDelayMet.isEmpty()) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  public void onExecuted(String paramString, boolean paramBoolean)
  {
    synchronized (this.mLock)
    {
      ExecutionListener localExecutionListener = (ExecutionListener)this.mPendingDelayMet.remove(paramString);
      if (localExecutionListener != null) {
        localExecutionListener.onExecuted(paramString, paramBoolean);
      }
      return;
    }
  }
  
  void onHandleIntent(Intent paramIntent, int paramInt, SystemAlarmDispatcher paramSystemAlarmDispatcher)
  {
    String str = paramIntent.getAction();
    if ("ACTION_CONSTRAINTS_CHANGED".equals(str)) {
      handleConstraintsChanged(paramIntent, paramInt, paramSystemAlarmDispatcher);
    } else if ("ACTION_RESCHEDULE".equals(str)) {
      handleReschedule(paramIntent, paramInt, paramSystemAlarmDispatcher);
    } else if (!hasKeys(paramIntent.getExtras(), new String[] { "KEY_WORKSPEC_ID" })) {
      Logger.get().error(TAG, String.format("Invalid request for %s, requires %s.", new Object[] { str, "KEY_WORKSPEC_ID" }), new Throwable[0]);
    } else if ("ACTION_SCHEDULE_WORK".equals(str)) {
      handleScheduleWorkIntent(paramIntent, paramInt, paramSystemAlarmDispatcher);
    } else if ("ACTION_DELAY_MET".equals(str)) {
      handleDelayMet(paramIntent, paramInt, paramSystemAlarmDispatcher);
    } else if ("ACTION_STOP_WORK".equals(str)) {
      handleStopWork(paramIntent, paramSystemAlarmDispatcher);
    } else if ("ACTION_EXECUTION_COMPLETED".equals(str)) {
      handleExecutionCompleted(paramIntent, paramInt);
    } else {
      Logger.get().warning(TAG, String.format("Ignoring intent %s", new Object[] { paramIntent }), new Throwable[0]);
    }
  }
}
