package com.askgps.personaltrackercore.database;

import com.askgps.personaltrackercore.database.model.StepSensor;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\002\bg\030\0002\0020\001J\n\020\002\032\004\030\0010\003H'J\020\020\004\032\0020\0052\006\020\006\032\0020\003H'?\006\007"}, d2={"Lcom/askgps/personaltrackercore/database/StepSensorDao;", "", "getStepInfo", "Lcom/askgps/personaltrackercore/database/model/StepSensor;", "updateRow", "", "item", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public abstract interface StepSensorDao
{
  public abstract StepSensor getStepInfo();
  
  public abstract void updateRow(StepSensor paramStepSensor);
}
