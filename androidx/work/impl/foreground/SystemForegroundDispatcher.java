package androidx.work.impl.foreground;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.work.ForegroundInfo;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Processor;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class SystemForegroundDispatcher
  implements WorkConstraintsCallback, ExecutionListener
{
  private static final String ACTION_CANCEL_WORK = "ACTION_CANCEL_WORK";
  private static final String ACTION_NOTIFY = "ACTION_NOTIFY";
  private static final String ACTION_START_FOREGROUND = "ACTION_START_FOREGROUND";
  private static final String KEY_FOREGROUND_SERVICE_TYPE = "KEY_FOREGROUND_SERVICE_TYPE";
  private static final String KEY_NOTIFICATION = "KEY_NOTIFICATION";
  private static final String KEY_NOTIFICATION_ID = "KEY_NOTIFICATION_ID";
  private static final String KEY_WORKSPEC_ID = "KEY_WORKSPEC_ID";
  static final String TAG = Logger.tagWithPrefix("SystemFgDispatcher");
  private Callback mCallback;
  final WorkConstraintsTracker mConstraintsTracker;
  private Context mContext;
  String mCurrentForegroundWorkSpecId;
  final Map<String, ForegroundInfo> mForegroundInfoById;
  ForegroundInfo mLastForegroundInfo;
  final Object mLock;
  private final TaskExecutor mTaskExecutor;
  final Set<WorkSpec> mTrackedWorkSpecs;
  private WorkManagerImpl mWorkManagerImpl;
  final Map<String, WorkSpec> mWorkSpecById;
  
  SystemForegroundDispatcher(Context paramContext)
  {
    this.mContext = paramContext;
    this.mLock = new Object();
    paramContext = WorkManagerImpl.getInstance(this.mContext);
    this.mWorkManagerImpl = paramContext;
    this.mTaskExecutor = paramContext.getWorkTaskExecutor();
    this.mCurrentForegroundWorkSpecId = null;
    this.mLastForegroundInfo = null;
    this.mForegroundInfoById = new LinkedHashMap();
    this.mTrackedWorkSpecs = new HashSet();
    this.mWorkSpecById = new HashMap();
    this.mConstraintsTracker = new WorkConstraintsTracker(this.mContext, this.mTaskExecutor, this);
    this.mWorkManagerImpl.getProcessor().addExecutionListener(this);
  }
  
  SystemForegroundDispatcher(Context paramContext, WorkManagerImpl paramWorkManagerImpl, WorkConstraintsTracker paramWorkConstraintsTracker)
  {
    this.mContext = paramContext;
    this.mLock = new Object();
    this.mWorkManagerImpl = paramWorkManagerImpl;
    this.mTaskExecutor = paramWorkManagerImpl.getWorkTaskExecutor();
    this.mCurrentForegroundWorkSpecId = null;
    this.mForegroundInfoById = new LinkedHashMap();
    this.mTrackedWorkSpecs = new HashSet();
    this.mWorkSpecById = new HashMap();
    this.mConstraintsTracker = paramWorkConstraintsTracker;
    this.mWorkManagerImpl.getProcessor().addExecutionListener(this);
  }
  
  public static Intent createCancelWorkIntent(Context paramContext, String paramString)
  {
    paramContext = new Intent(paramContext, SystemForegroundService.class);
    paramContext.setAction("ACTION_CANCEL_WORK");
    paramContext.setData(Uri.parse(String.format("workspec://%s", new Object[] { paramString })));
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    return paramContext;
  }
  
  public static Intent createNotifyIntent(Context paramContext, String paramString, ForegroundInfo paramForegroundInfo)
  {
    paramContext = new Intent(paramContext, SystemForegroundService.class);
    paramContext.setAction("ACTION_NOTIFY");
    paramContext.putExtra("KEY_NOTIFICATION_ID", paramForegroundInfo.getNotificationId());
    paramContext.putExtra("KEY_FOREGROUND_SERVICE_TYPE", paramForegroundInfo.getForegroundServiceType());
    paramContext.putExtra("KEY_NOTIFICATION", paramForegroundInfo.getNotification());
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    return paramContext;
  }
  
  public static Intent createStartForegroundIntent(Context paramContext, String paramString, ForegroundInfo paramForegroundInfo)
  {
    paramContext = new Intent(paramContext, SystemForegroundService.class);
    paramContext.setAction("ACTION_START_FOREGROUND");
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    paramContext.putExtra("KEY_NOTIFICATION_ID", paramForegroundInfo.getNotificationId());
    paramContext.putExtra("KEY_FOREGROUND_SERVICE_TYPE", paramForegroundInfo.getForegroundServiceType());
    paramContext.putExtra("KEY_NOTIFICATION", paramForegroundInfo.getNotification());
    paramContext.putExtra("KEY_WORKSPEC_ID", paramString);
    return paramContext;
  }
  
  private void handleCancelWork(Intent paramIntent)
  {
    Logger.get().info(TAG, String.format("Stopping foreground work for %s", new Object[] { paramIntent }), new Throwable[0]);
    paramIntent = paramIntent.getStringExtra("KEY_WORKSPEC_ID");
    if ((paramIntent != null) && (!TextUtils.isEmpty(paramIntent))) {
      this.mWorkManagerImpl.cancelWorkById(UUID.fromString(paramIntent));
    }
  }
  
  private void handleNotify(Intent paramIntent)
  {
    int i = 0;
    int j = paramIntent.getIntExtra("KEY_NOTIFICATION_ID", 0);
    int k = paramIntent.getIntExtra("KEY_FOREGROUND_SERVICE_TYPE", 0);
    String str = paramIntent.getStringExtra("KEY_WORKSPEC_ID");
    Notification localNotification = (Notification)paramIntent.getParcelableExtra("KEY_NOTIFICATION");
    Logger.get().debug(TAG, String.format("Notifying with (id: %s, workSpecId: %s, notificationType: %s)", new Object[] { Integer.valueOf(j), str, Integer.valueOf(k) }), new Throwable[0]);
    if ((localNotification != null) && (this.mCallback != null))
    {
      paramIntent = new ForegroundInfo(j, localNotification, k);
      this.mForegroundInfoById.put(str, paramIntent);
      if (TextUtils.isEmpty(this.mCurrentForegroundWorkSpecId))
      {
        this.mCurrentForegroundWorkSpecId = str;
        this.mCallback.startForeground(j, k, localNotification);
      }
      else
      {
        this.mCallback.notify(j, localNotification);
        if ((k != 0) && (Build.VERSION.SDK_INT >= 29))
        {
          paramIntent = this.mForegroundInfoById.entrySet().iterator();
          while (paramIntent.hasNext()) {
            i |= ((ForegroundInfo)((Map.Entry)paramIntent.next()).getValue()).getForegroundServiceType();
          }
          paramIntent = (ForegroundInfo)this.mForegroundInfoById.get(this.mCurrentForegroundWorkSpecId);
          if (paramIntent != null) {
            this.mCallback.startForeground(paramIntent.getNotificationId(), i, paramIntent.getNotification());
          }
        }
      }
    }
  }
  
  private void handleStartForeground(final Intent paramIntent)
  {
    Logger.get().info(TAG, String.format("Started foreground service %s", new Object[] { paramIntent }), new Throwable[0]);
    final String str = paramIntent.getStringExtra("KEY_WORKSPEC_ID");
    paramIntent = this.mWorkManagerImpl.getWorkDatabase();
    this.mTaskExecutor.executeOnBackgroundThread(new Runnable()
    {
      public void run()
      {
        WorkSpec localWorkSpec = paramIntent.workSpecDao().getWorkSpec(str);
        if ((localWorkSpec != null) && (localWorkSpec.hasConstraints())) {
          synchronized (SystemForegroundDispatcher.this.mLock)
          {
            SystemForegroundDispatcher.this.mWorkSpecById.put(str, localWorkSpec);
            SystemForegroundDispatcher.this.mTrackedWorkSpecs.add(localWorkSpec);
            SystemForegroundDispatcher.this.mConstraintsTracker.replace(SystemForegroundDispatcher.this.mTrackedWorkSpecs);
          }
        }
      }
    });
  }
  
  WorkManagerImpl getWorkManager()
  {
    return this.mWorkManagerImpl;
  }
  
  void handleStop()
  {
    Logger.get().info(TAG, "Stopping foreground service", new Throwable[0]);
    Callback localCallback = this.mCallback;
    if (localCallback != null)
    {
      ForegroundInfo localForegroundInfo = this.mLastForegroundInfo;
      if (localForegroundInfo != null)
      {
        localCallback.cancelNotification(localForegroundInfo.getNotificationId());
        this.mLastForegroundInfo = null;
      }
      this.mCallback.stop();
    }
  }
  
  public void onAllConstraintsMet(List<String> paramList) {}
  
  public void onAllConstraintsNotMet(List<String> paramList)
  {
    if (!paramList.isEmpty())
    {
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        String str = (String)paramList.next();
        Logger.get().debug(TAG, String.format("Constraints unmet for WorkSpec %s", new Object[] { str }), new Throwable[0]);
        this.mWorkManagerImpl.stopForegroundWork(str);
      }
    }
  }
  
  void onDestroy()
  {
    this.mCallback = null;
    this.mConstraintsTracker.reset();
    this.mWorkManagerImpl.getProcessor().removeExecutionListener(this);
  }
  
  public void onExecuted(String paramString, boolean paramBoolean)
  {
    synchronized (this.mLock)
    {
      WorkSpec localWorkSpec = (WorkSpec)this.mWorkSpecById.remove(paramString);
      if (localWorkSpec != null) {
        paramBoolean = this.mTrackedWorkSpecs.remove(localWorkSpec);
      } else {
        paramBoolean = false;
      }
      if (paramBoolean) {
        this.mConstraintsTracker.replace(this.mTrackedWorkSpecs);
      }
      this.mLastForegroundInfo = ((ForegroundInfo)this.mForegroundInfoById.remove(paramString));
      if (paramString.equals(this.mCurrentForegroundWorkSpecId))
      {
        if (this.mForegroundInfoById.size() > 0)
        {
          ??? = this.mForegroundInfoById.entrySet().iterator();
          for (paramString = (Map.Entry)((Iterator)???).next(); ((Iterator)???).hasNext(); paramString = (Map.Entry)((Iterator)???).next()) {}
          this.mCurrentForegroundWorkSpecId = ((String)paramString.getKey());
          if (this.mCallback != null)
          {
            paramString = (ForegroundInfo)paramString.getValue();
            this.mCallback.startForeground(paramString.getNotificationId(), paramString.getForegroundServiceType(), paramString.getNotification());
            this.mCallback.cancelNotification(paramString.getNotificationId());
          }
        }
      }
      else
      {
        paramString = this.mLastForegroundInfo;
        if (paramString != null)
        {
          ??? = this.mCallback;
          if (??? != null) {
            ((Callback)???).cancelNotification(paramString.getNotificationId());
          }
        }
      }
      return;
    }
  }
  
  void onStartCommand(Intent paramIntent)
  {
    String str = paramIntent.getAction();
    if ("ACTION_START_FOREGROUND".equals(str))
    {
      handleStartForeground(paramIntent);
      handleNotify(paramIntent);
    }
    else if ("ACTION_NOTIFY".equals(str))
    {
      handleNotify(paramIntent);
    }
    else if ("ACTION_CANCEL_WORK".equals(str))
    {
      handleCancelWork(paramIntent);
    }
  }
  
  void setCallback(Callback paramCallback)
  {
    if (this.mCallback != null)
    {
      Logger.get().error(TAG, "A callback already exists.", new Throwable[0]);
      return;
    }
    this.mCallback = paramCallback;
  }
  
  static abstract interface Callback
  {
    public abstract void cancelNotification(int paramInt);
    
    public abstract void notify(int paramInt, Notification paramNotification);
    
    public abstract void startForeground(int paramInt1, int paramInt2, Notification paramNotification);
    
    public abstract void stop();
  }
}
