package androidx.work.impl.background.systemjob;

import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobInfo.TriggerContentUri;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ContentUriTriggers;
import androidx.work.ContentUriTriggers.Trigger;
import androidx.work.Logger;
import androidx.work.NetworkType;
import androidx.work.impl.model.WorkSpec;
import java.util.Iterator;
import java.util.Set;

class SystemJobInfoConverter
{
  static final String EXTRA_IS_PERIODIC = "EXTRA_IS_PERIODIC";
  static final String EXTRA_WORK_SPEC_ID = "EXTRA_WORK_SPEC_ID";
  private static final String TAG = Logger.tagWithPrefix("SystemJobInfoConverter");
  private final ComponentName mWorkServiceComponent;
  
  SystemJobInfoConverter(Context paramContext)
  {
    this.mWorkServiceComponent = new ComponentName(paramContext.getApplicationContext(), SystemJobService.class);
  }
  
  private static JobInfo.TriggerContentUri convertContentUriTrigger(ContentUriTriggers.Trigger paramTrigger)
  {
    int i = paramTrigger.shouldTriggerForDescendants();
    return new JobInfo.TriggerContentUri(paramTrigger.getUri(), i);
  }
  
  static int convertNetworkType(NetworkType paramNetworkType)
  {
    int i = 1.$SwitchMap$androidx$work$NetworkType[paramNetworkType.ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 3)
        {
          if (i != 4)
          {
            if ((i == 5) && (Build.VERSION.SDK_INT >= 26)) {
              return 4;
            }
          }
          else if (Build.VERSION.SDK_INT >= 24) {
            return 3;
          }
          Logger.get().debug(TAG, String.format("API version too low. Cannot convert network type value %s", new Object[] { paramNetworkType }), new Throwable[0]);
          return 1;
        }
        return 2;
      }
      return 1;
    }
    return 0;
  }
  
  JobInfo convert(WorkSpec paramWorkSpec, int paramInt)
  {
    Constraints localConstraints = paramWorkSpec.constraints;
    int i = convertNetworkType(localConstraints.getRequiredNetworkType());
    Object localObject = new PersistableBundle();
    ((PersistableBundle)localObject).putString("EXTRA_WORK_SPEC_ID", paramWorkSpec.id);
    ((PersistableBundle)localObject).putBoolean("EXTRA_IS_PERIODIC", paramWorkSpec.isPeriodic());
    localObject = new JobInfo.Builder(paramInt, this.mWorkServiceComponent).setRequiredNetworkType(i).setRequiresCharging(localConstraints.requiresCharging()).setRequiresDeviceIdle(localConstraints.requiresDeviceIdle()).setExtras((PersistableBundle)localObject);
    if (!localConstraints.requiresDeviceIdle())
    {
      if (paramWorkSpec.backoffPolicy == BackoffPolicy.LINEAR) {
        paramInt = 0;
      } else {
        paramInt = 1;
      }
      ((JobInfo.Builder)localObject).setBackoffCriteria(paramWorkSpec.backoffDelayDuration, paramInt);
    }
    long l = Math.max(paramWorkSpec.calculateNextRunTime() - System.currentTimeMillis(), 0L);
    if (Build.VERSION.SDK_INT <= 28) {
      ((JobInfo.Builder)localObject).setMinimumLatency(l);
    } else if (l > 0L) {
      ((JobInfo.Builder)localObject).setMinimumLatency(l);
    } else {
      ((JobInfo.Builder)localObject).setImportantWhileForeground(true);
    }
    if ((Build.VERSION.SDK_INT >= 24) && (localConstraints.hasContentUriTriggers()))
    {
      paramWorkSpec = localConstraints.getContentUriTriggers().getTriggers().iterator();
      while (paramWorkSpec.hasNext()) {
        ((JobInfo.Builder)localObject).addTriggerContentUri(convertContentUriTrigger((ContentUriTriggers.Trigger)paramWorkSpec.next()));
      }
      ((JobInfo.Builder)localObject).setTriggerContentUpdateDelay(localConstraints.getTriggerContentUpdateDelay());
      ((JobInfo.Builder)localObject).setTriggerContentMaxDelay(localConstraints.getTriggerMaxContentDelay());
    }
    ((JobInfo.Builder)localObject).setPersisted(false);
    if (Build.VERSION.SDK_INT >= 26)
    {
      ((JobInfo.Builder)localObject).setRequiresBatteryNotLow(localConstraints.requiresBatteryNotLow());
      ((JobInfo.Builder)localObject).setRequiresStorageNotLow(localConstraints.requiresStorageNotLow());
    }
    return ((JobInfo.Builder)localObject).build();
  }
}
