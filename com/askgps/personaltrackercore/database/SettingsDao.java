package com.askgps.personaltrackercore.database;

import androidx.lifecycle.LiveData;
import com.askgps.personaltrackercore.database.model.Settings;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\002\n\002\020\t\n\002\b\004\n\002\020\013\n\002\b\006\bg\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H'J\016\020\006\032\b\022\004\022\0020\0050\007H'J\020\020\b\032\0020\0032\006\020\t\032\0020\nH'J\020\020\013\032\0020\0032\006\020\f\032\0020\rH'J\020\020\016\032\0020\0032\006\020\f\032\0020\rH'J\020\020\017\032\0020\0032\006\020\f\032\0020\rH'J\020\020\020\032\0020\0032\006\020\021\032\0020\022H'J5\020\023\032\0020\0032\b\020\t\032\004\030\0010\n2\b\020\024\032\004\030\0010\r2\b\020\025\032\004\030\0010\r2\b\020\026\032\004\030\0010\rH\027?\006\002\020\027?\006\030"}, d2={"Lcom/askgps/personaltrackercore/database/SettingsDao;", "", "createSettings", "", "settings", "Lcom/askgps/personaltrackercore/database/model/Settings;", "getSettings", "Landroidx/lifecycle/LiveData;", "setAddress", "address", "", "setLocationInterval", "interval", "", "setRemovalFromHandInterval", "setSendTelemetryInterval", "setWorkIsStart", "workIsStart", "", "updateSettings", "locationInterval", "sendTelemetryInterval", "removalFromHandInterval", "(Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;)V", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public abstract interface SettingsDao
{
  public abstract void createSettings(Settings paramSettings);
  
  public abstract LiveData<Settings> getSettings();
  
  public abstract void setAddress(String paramString);
  
  public abstract void setLocationInterval(long paramLong);
  
  public abstract void setRemovalFromHandInterval(long paramLong);
  
  public abstract void setSendTelemetryInterval(long paramLong);
  
  public abstract void setWorkIsStart(boolean paramBoolean);
  
  public abstract void updateSettings(String paramString, Long paramLong1, Long paramLong2, Long paramLong3);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls
  {
    public static void updateSettings(SettingsDao paramSettingsDao, String paramString, Long paramLong1, Long paramLong2, Long paramLong3)
    {
      if (paramString != null) {
        paramSettingsDao.setAddress(paramString);
      }
      if (paramLong1 != null) {
        paramSettingsDao.setLocationInterval(((Number)paramLong1).longValue());
      }
      if (paramLong2 != null) {
        paramSettingsDao.setSendTelemetryInterval(((Number)paramLong2).longValue());
      }
      if (paramLong3 != null) {
        paramSettingsDao.setRemovalFromHandInterval(((Number)paramLong3).longValue());
      }
    }
  }
}
