package io.fabric.sdk.android.services.common;

class AdvertisingInfo
{
  public final String advertisingId;
  public final boolean limitAdTrackingEnabled;
  
  AdvertisingInfo(String paramString, boolean paramBoolean)
  {
    this.advertisingId = paramString;
    this.limitAdTrackingEnabled = paramBoolean;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      Object localObject = (AdvertisingInfo)paramObject;
      if (this.limitAdTrackingEnabled != ((AdvertisingInfo)localObject).limitAdTrackingEnabled) {
        return false;
      }
      paramObject = this.advertisingId;
      localObject = ((AdvertisingInfo)localObject).advertisingId;
      return paramObject != null ? paramObject.equals(localObject) : localObject == null;
    }
    return false;
  }
  
  public int hashCode()
  {
    String str = this.advertisingId;
    int i;
    if (str != null) {
      i = str.hashCode();
    } else {
      i = 0;
    }
    return i * 31 + this.limitAdTrackingEnabled;
  }
}
