package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Objects.ToStringHelper;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

public class Feature
  extends AbstractSafeParcelable
{
  public static final Parcelable.Creator<Feature> CREATOR = new zzb();
  private final String name;
  @Deprecated
  private final int zzk;
  private final long zzl;
  
  public Feature(String paramString, int paramInt, long paramLong)
  {
    this.name = paramString;
    this.zzk = paramInt;
    this.zzl = paramLong;
  }
  
  public Feature(String paramString, long paramLong)
  {
    this.name = paramString;
    this.zzl = paramLong;
    this.zzk = -1;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Feature))
    {
      paramObject = (Feature)paramObject;
      if (((getName() != null) && (getName().equals(paramObject.getName()))) || ((getName() == null) && (paramObject.getName() == null) && (getVersion() == paramObject.getVersion()))) {
        return true;
      }
    }
    return false;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public long getVersion()
  {
    long l1 = this.zzl;
    long l2 = l1;
    if (l1 == -1L) {
      l2 = this.zzk;
    }
    return l2;
  }
  
  public int hashCode()
  {
    return Objects.hashCode(new Object[] { getName(), Long.valueOf(getVersion()) });
  }
  
  public String toString()
  {
    return Objects.toStringHelper(this).add("name", getName()).add("version", Long.valueOf(getVersion())).toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramInt = SafeParcelWriter.beginObjectHeader(paramParcel);
    SafeParcelWriter.writeString(paramParcel, 1, getName(), false);
    SafeParcelWriter.writeInt(paramParcel, 2, this.zzk);
    SafeParcelWriter.writeLong(paramParcel, 3, getVersion());
    SafeParcelWriter.finishObjectHeader(paramParcel, paramInt);
  }
}
