package androidx.work.impl.background.systemalarm;

import android.content.Context;
import androidx.work.Logger;
import androidx.work.impl.Scheduler;
import androidx.work.impl.model.WorkSpec;

public class SystemAlarmScheduler
  implements Scheduler
{
  private static final String TAG = Logger.tagWithPrefix("SystemAlarmScheduler");
  private final Context mContext;
  
  public SystemAlarmScheduler(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
  }
  
  private void scheduleWorkSpec(WorkSpec paramWorkSpec)
  {
    Logger.get().debug(TAG, String.format("Scheduling work with workSpecId %s", new Object[] { paramWorkSpec.id }), new Throwable[0]);
    paramWorkSpec = CommandHandler.createScheduleWorkIntent(this.mContext, paramWorkSpec.id);
    this.mContext.startService(paramWorkSpec);
  }
  
  public void cancel(String paramString)
  {
    paramString = CommandHandler.createStopWorkIntent(this.mContext, paramString);
    this.mContext.startService(paramString);
  }
  
  public void schedule(WorkSpec... paramVarArgs)
  {
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++) {
      scheduleWorkSpec(paramVarArgs[j]);
    }
  }
}
