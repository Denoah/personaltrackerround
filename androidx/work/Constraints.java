package androidx.work;

import android.net.Uri;
import android.os.Build.VERSION;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public final class Constraints
{
  public static final Constraints NONE = new Builder().build();
  private ContentUriTriggers mContentUriTriggers = new ContentUriTriggers();
  private NetworkType mRequiredNetworkType = NetworkType.NOT_REQUIRED;
  private boolean mRequiresBatteryNotLow;
  private boolean mRequiresCharging;
  private boolean mRequiresDeviceIdle;
  private boolean mRequiresStorageNotLow;
  private long mTriggerContentUpdateDelay = -1L;
  private long mTriggerMaxContentDelay = -1L;
  
  public Constraints() {}
  
  Constraints(Builder paramBuilder)
  {
    this.mRequiresCharging = paramBuilder.mRequiresCharging;
    boolean bool;
    if ((Build.VERSION.SDK_INT >= 23) && (paramBuilder.mRequiresDeviceIdle)) {
      bool = true;
    } else {
      bool = false;
    }
    this.mRequiresDeviceIdle = bool;
    this.mRequiredNetworkType = paramBuilder.mRequiredNetworkType;
    this.mRequiresBatteryNotLow = paramBuilder.mRequiresBatteryNotLow;
    this.mRequiresStorageNotLow = paramBuilder.mRequiresStorageNotLow;
    if (Build.VERSION.SDK_INT >= 24)
    {
      this.mContentUriTriggers = paramBuilder.mContentUriTriggers;
      this.mTriggerContentUpdateDelay = paramBuilder.mTriggerContentUpdateDelay;
      this.mTriggerMaxContentDelay = paramBuilder.mTriggerContentMaxDelay;
    }
  }
  
  public Constraints(Constraints paramConstraints)
  {
    this.mRequiresCharging = paramConstraints.mRequiresCharging;
    this.mRequiresDeviceIdle = paramConstraints.mRequiresDeviceIdle;
    this.mRequiredNetworkType = paramConstraints.mRequiredNetworkType;
    this.mRequiresBatteryNotLow = paramConstraints.mRequiresBatteryNotLow;
    this.mRequiresStorageNotLow = paramConstraints.mRequiresStorageNotLow;
    this.mContentUriTriggers = paramConstraints.mContentUriTriggers;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (Constraints)paramObject;
      if (this.mRequiresCharging != paramObject.mRequiresCharging) {
        return false;
      }
      if (this.mRequiresDeviceIdle != paramObject.mRequiresDeviceIdle) {
        return false;
      }
      if (this.mRequiresBatteryNotLow != paramObject.mRequiresBatteryNotLow) {
        return false;
      }
      if (this.mRequiresStorageNotLow != paramObject.mRequiresStorageNotLow) {
        return false;
      }
      if (this.mTriggerContentUpdateDelay != paramObject.mTriggerContentUpdateDelay) {
        return false;
      }
      if (this.mTriggerMaxContentDelay != paramObject.mTriggerMaxContentDelay) {
        return false;
      }
      if (this.mRequiredNetworkType != paramObject.mRequiredNetworkType) {
        return false;
      }
      return this.mContentUriTriggers.equals(paramObject.mContentUriTriggers);
    }
    return false;
  }
  
  public ContentUriTriggers getContentUriTriggers()
  {
    return this.mContentUriTriggers;
  }
  
  public NetworkType getRequiredNetworkType()
  {
    return this.mRequiredNetworkType;
  }
  
  public long getTriggerContentUpdateDelay()
  {
    return this.mTriggerContentUpdateDelay;
  }
  
  public long getTriggerMaxContentDelay()
  {
    return this.mTriggerMaxContentDelay;
  }
  
  public boolean hasContentUriTriggers()
  {
    boolean bool;
    if (this.mContentUriTriggers.size() > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    int i = this.mRequiredNetworkType.hashCode();
    int j = this.mRequiresCharging;
    int k = this.mRequiresDeviceIdle;
    int m = this.mRequiresBatteryNotLow;
    int n = this.mRequiresStorageNotLow;
    long l = this.mTriggerContentUpdateDelay;
    int i1 = (int)(l ^ l >>> 32);
    l = this.mTriggerMaxContentDelay;
    return ((((((i * 31 + j) * 31 + k) * 31 + m) * 31 + n) * 31 + i1) * 31 + (int)(l ^ l >>> 32)) * 31 + this.mContentUriTriggers.hashCode();
  }
  
  public boolean requiresBatteryNotLow()
  {
    return this.mRequiresBatteryNotLow;
  }
  
  public boolean requiresCharging()
  {
    return this.mRequiresCharging;
  }
  
  public boolean requiresDeviceIdle()
  {
    return this.mRequiresDeviceIdle;
  }
  
  public boolean requiresStorageNotLow()
  {
    return this.mRequiresStorageNotLow;
  }
  
  public void setContentUriTriggers(ContentUriTriggers paramContentUriTriggers)
  {
    this.mContentUriTriggers = paramContentUriTriggers;
  }
  
  public void setRequiredNetworkType(NetworkType paramNetworkType)
  {
    this.mRequiredNetworkType = paramNetworkType;
  }
  
  public void setRequiresBatteryNotLow(boolean paramBoolean)
  {
    this.mRequiresBatteryNotLow = paramBoolean;
  }
  
  public void setRequiresCharging(boolean paramBoolean)
  {
    this.mRequiresCharging = paramBoolean;
  }
  
  public void setRequiresDeviceIdle(boolean paramBoolean)
  {
    this.mRequiresDeviceIdle = paramBoolean;
  }
  
  public void setRequiresStorageNotLow(boolean paramBoolean)
  {
    this.mRequiresStorageNotLow = paramBoolean;
  }
  
  public void setTriggerContentUpdateDelay(long paramLong)
  {
    this.mTriggerContentUpdateDelay = paramLong;
  }
  
  public void setTriggerMaxContentDelay(long paramLong)
  {
    this.mTriggerMaxContentDelay = paramLong;
  }
  
  public static final class Builder
  {
    ContentUriTriggers mContentUriTriggers = new ContentUriTriggers();
    NetworkType mRequiredNetworkType = NetworkType.NOT_REQUIRED;
    boolean mRequiresBatteryNotLow = false;
    boolean mRequiresCharging = false;
    boolean mRequiresDeviceIdle = false;
    boolean mRequiresStorageNotLow = false;
    long mTriggerContentMaxDelay = -1L;
    long mTriggerContentUpdateDelay = -1L;
    
    public Builder() {}
    
    public Builder addContentUriTrigger(Uri paramUri, boolean paramBoolean)
    {
      this.mContentUriTriggers.add(paramUri, paramBoolean);
      return this;
    }
    
    public Constraints build()
    {
      return new Constraints(this);
    }
    
    public Builder setRequiredNetworkType(NetworkType paramNetworkType)
    {
      this.mRequiredNetworkType = paramNetworkType;
      return this;
    }
    
    public Builder setRequiresBatteryNotLow(boolean paramBoolean)
    {
      this.mRequiresBatteryNotLow = paramBoolean;
      return this;
    }
    
    public Builder setRequiresCharging(boolean paramBoolean)
    {
      this.mRequiresCharging = paramBoolean;
      return this;
    }
    
    public Builder setRequiresDeviceIdle(boolean paramBoolean)
    {
      this.mRequiresDeviceIdle = paramBoolean;
      return this;
    }
    
    public Builder setRequiresStorageNotLow(boolean paramBoolean)
    {
      this.mRequiresStorageNotLow = paramBoolean;
      return this;
    }
    
    public Builder setTriggerContentMaxDelay(long paramLong, TimeUnit paramTimeUnit)
    {
      this.mTriggerContentMaxDelay = paramTimeUnit.toMillis(paramLong);
      return this;
    }
    
    public Builder setTriggerContentMaxDelay(Duration paramDuration)
    {
      this.mTriggerContentMaxDelay = paramDuration.toMillis();
      return this;
    }
    
    public Builder setTriggerContentUpdateDelay(long paramLong, TimeUnit paramTimeUnit)
    {
      this.mTriggerContentUpdateDelay = paramTimeUnit.toMillis(paramLong);
      return this;
    }
    
    public Builder setTriggerContentUpdateDelay(Duration paramDuration)
    {
      this.mTriggerContentUpdateDelay = paramDuration.toMillis();
      return this;
    }
  }
}
