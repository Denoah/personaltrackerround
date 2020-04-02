package com.askgps.personaltrackercore.database.model;

import .r8.backportedMethods.utility.Long.1.hashCode;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\t\n\002\b\022\n\002\020\013\n\002\b\003\n\002\020\016\n\000\b?\b\030\0002\0020\001B/\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\003\022\006\020\007\032\0020\003\022\b\b\002\020\b\032\0020\003?\006\002\020\tJ\t\020\021\032\0020\003H?\003J\t\020\022\032\0020\005H?\003J\t\020\023\032\0020\003H?\003J\t\020\024\032\0020\003H?\003J\t\020\025\032\0020\003H?\003J;\020\026\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\0052\b\b\002\020\006\032\0020\0032\b\b\002\020\007\032\0020\0032\b\b\002\020\b\032\0020\003H?\001J\023\020\027\032\0020\0302\b\020\031\032\004\030\0010\001H?\003J\t\020\032\032\0020\003H?\001J\t\020\033\032\0020\034H?\001R\026\020\b\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\n\020\013R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\f\020\013R\021\020\007\032\0020\003?\006\b\n\000\032\004\b\r\020\013R\021\020\006\032\0020\003?\006\b\n\000\032\004\b\016\020\013R\021\020\004\032\0020\005?\006\b\n\000\032\004\b\017\020\020?\006\035"}, d2={"Lcom/askgps/personaltrackercore/database/model/StepSensor;", "", "lastSensorValue", "", "stepsTimeStamp", "", "stepsAtStartDay", "stepCount", "id", "(IJIII)V", "getId", "()I", "getLastSensorValue", "getStepCount", "getStepsAtStartDay", "getStepsTimeStamp", "()J", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class StepSensor
{
  private final int id;
  private final int lastSensorValue;
  private final int stepCount;
  private final int stepsAtStartDay;
  private final long stepsTimeStamp;
  
  public StepSensor(int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4)
  {
    this.lastSensorValue = paramInt1;
    this.stepsTimeStamp = paramLong;
    this.stepsAtStartDay = paramInt2;
    this.stepCount = paramInt3;
    this.id = paramInt4;
  }
  
  public final int component1()
  {
    return this.lastSensorValue;
  }
  
  public final long component2()
  {
    return this.stepsTimeStamp;
  }
  
  public final int component3()
  {
    return this.stepsAtStartDay;
  }
  
  public final int component4()
  {
    return this.stepCount;
  }
  
  public final int component5()
  {
    return this.id;
  }
  
  public final StepSensor copy(int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4)
  {
    return new StepSensor(paramInt1, paramLong, paramInt2, paramInt3, paramInt4);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof StepSensor))
      {
        paramObject = (StepSensor)paramObject;
        if ((this.lastSensorValue == paramObject.lastSensorValue) && (this.stepsTimeStamp == paramObject.stepsTimeStamp) && (this.stepsAtStartDay == paramObject.stepsAtStartDay) && (this.stepCount == paramObject.stepCount) && (this.id == paramObject.id)) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final int getId()
  {
    return this.id;
  }
  
  public final int getLastSensorValue()
  {
    return this.lastSensorValue;
  }
  
  public final int getStepCount()
  {
    return this.stepCount;
  }
  
  public final int getStepsAtStartDay()
  {
    return this.stepsAtStartDay;
  }
  
  public final long getStepsTimeStamp()
  {
    return this.stepsTimeStamp;
  }
  
  public int hashCode()
  {
    return (((this.lastSensorValue * 31 + .r8.backportedMethods.utility.Long.1.hashCode.hashCode(this.stepsTimeStamp)) * 31 + this.stepsAtStartDay) * 31 + this.stepCount) * 31 + this.id;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("StepSensor(lastSensorValue=");
    localStringBuilder.append(this.lastSensorValue);
    localStringBuilder.append(", stepsTimeStamp=");
    localStringBuilder.append(this.stepsTimeStamp);
    localStringBuilder.append(", stepsAtStartDay=");
    localStringBuilder.append(this.stepsAtStartDay);
    localStringBuilder.append(", stepCount=");
    localStringBuilder.append(this.stepCount);
    localStringBuilder.append(", id=");
    localStringBuilder.append(this.id);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
