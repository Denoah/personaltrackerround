package androidx.work;

import android.app.Notification;

public final class ForegroundInfo
{
  private final int mForegroundServiceType;
  private final Notification mNotification;
  private final int mNotificationId;
  
  public ForegroundInfo(int paramInt, Notification paramNotification)
  {
    this(paramInt, paramNotification, 0);
  }
  
  public ForegroundInfo(int paramInt1, Notification paramNotification, int paramInt2)
  {
    this.mNotificationId = paramInt1;
    this.mNotification = paramNotification;
    this.mForegroundServiceType = paramInt2;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (ForegroundInfo)paramObject;
      if (this.mNotificationId != paramObject.mNotificationId) {
        return false;
      }
      if (this.mForegroundServiceType != paramObject.mForegroundServiceType) {
        return false;
      }
      return this.mNotification.equals(paramObject.mNotification);
    }
    return false;
  }
  
  public int getForegroundServiceType()
  {
    return this.mForegroundServiceType;
  }
  
  public Notification getNotification()
  {
    return this.mNotification;
  }
  
  public int getNotificationId()
  {
    return this.mNotificationId;
  }
  
  public int hashCode()
  {
    return (this.mNotificationId * 31 + this.mForegroundServiceType) * 31 + this.mNotification.hashCode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("ForegroundInfo{");
    localStringBuilder.append("mNotificationId=");
    localStringBuilder.append(this.mNotificationId);
    localStringBuilder.append(", mForegroundServiceType=");
    localStringBuilder.append(this.mForegroundServiceType);
    localStringBuilder.append(", mNotification=");
    localStringBuilder.append(this.mNotification);
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}
