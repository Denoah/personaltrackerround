package com.askgps.personaltrackercore.database.model;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\035\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\002\b?\b\030\0002\0020\001BE\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\003\022\006\020\006\032\0020\003\022\006\020\007\032\0020\003\022\006\020\b\032\0020\003\022\006\020\t\032\0020\003\022\006\020\n\032\0020\003?\006\002\020\013J\t\020\027\032\0020\003H?\003J\t\020\030\032\0020\003H?\003J\t\020\031\032\0020\003H?\003J\t\020\032\032\0020\003H?\003J\t\020\033\032\0020\003H?\003J\t\020\034\032\0020\003H?\003J\t\020\035\032\0020\003H?\003J\t\020\036\032\0020\003H?\003JY\020\037\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\0032\b\b\002\020\005\032\0020\0032\b\b\002\020\006\032\0020\0032\b\b\002\020\007\032\0020\0032\b\b\002\020\b\032\0020\0032\b\b\002\020\t\032\0020\0032\b\b\002\020\n\032\0020\003H?\001J\023\020 \032\0020!2\b\020\"\032\004\030\0010\001H?\003J\t\020#\032\0020$H?\001J\t\020%\032\0020\003H?\001R\026\020\007\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\f\020\rR\036\020\006\032\0020\0038\006@\006X?\016?\006\016\n\000\032\004\b\016\020\r\"\004\b\017\020\020R\026\020\n\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\021\020\rR\026\020\005\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\022\020\rR\026\020\002\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\023\020\rR\026\020\004\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\024\020\rR\026\020\t\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\025\020\rR\026\020\b\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\026\020\r?\006&"}, d2={"Lcom/askgps/personaltrackercore/database/model/Device;", "", "fio", "", "iD_Data", "deviceId", "code", "address", "phone", "liveness", "comments", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAddress", "()Ljava/lang/String;", "getCode", "setCode", "(Ljava/lang/String;)V", "getComments", "getDeviceId", "getFio", "getID_Data", "getLiveness", "getPhone", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "", "toString", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class Device
{
  @SerializedName("address")
  private final String address;
  @SerializedName("code")
  private String code;
  @SerializedName("comments")
  private final String comments;
  @SerializedName("deviceId")
  private final String deviceId;
  @SerializedName("fio")
  private final String fio;
  @SerializedName("iD_Data")
  private final String iD_Data;
  @SerializedName("doctorName")
  private final String liveness;
  @SerializedName("phone")
  private final String phone;
  
  public Device(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    this.fio = paramString1;
    this.iD_Data = paramString2;
    this.deviceId = paramString3;
    this.code = paramString4;
    this.address = paramString5;
    this.phone = paramString6;
    this.liveness = paramString7;
    this.comments = paramString8;
  }
  
  public final String component1()
  {
    return this.fio;
  }
  
  public final String component2()
  {
    return this.iD_Data;
  }
  
  public final String component3()
  {
    return this.deviceId;
  }
  
  public final String component4()
  {
    return this.code;
  }
  
  public final String component5()
  {
    return this.address;
  }
  
  public final String component6()
  {
    return this.phone;
  }
  
  public final String component7()
  {
    return this.liveness;
  }
  
  public final String component8()
  {
    return this.comments;
  }
  
  public final Device copy(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "fio");
    Intrinsics.checkParameterIsNotNull(paramString2, "iD_Data");
    Intrinsics.checkParameterIsNotNull(paramString3, "deviceId");
    Intrinsics.checkParameterIsNotNull(paramString4, "code");
    Intrinsics.checkParameterIsNotNull(paramString5, "address");
    Intrinsics.checkParameterIsNotNull(paramString6, "phone");
    Intrinsics.checkParameterIsNotNull(paramString7, "liveness");
    Intrinsics.checkParameterIsNotNull(paramString8, "comments");
    return new Device(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof Device))
      {
        paramObject = (Device)paramObject;
        if ((Intrinsics.areEqual(this.fio, paramObject.fio)) && (Intrinsics.areEqual(this.iD_Data, paramObject.iD_Data)) && (Intrinsics.areEqual(this.deviceId, paramObject.deviceId)) && (Intrinsics.areEqual(this.code, paramObject.code)) && (Intrinsics.areEqual(this.address, paramObject.address)) && (Intrinsics.areEqual(this.phone, paramObject.phone)) && (Intrinsics.areEqual(this.liveness, paramObject.liveness)) && (Intrinsics.areEqual(this.comments, paramObject.comments))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final String getAddress()
  {
    return this.address;
  }
  
  public final String getCode()
  {
    return this.code;
  }
  
  public final String getComments()
  {
    return this.comments;
  }
  
  public final String getDeviceId()
  {
    return this.deviceId;
  }
  
  public final String getFio()
  {
    return this.fio;
  }
  
  public final String getID_Data()
  {
    return this.iD_Data;
  }
  
  public final String getLiveness()
  {
    return this.liveness;
  }
  
  public final String getPhone()
  {
    return this.phone;
  }
  
  public int hashCode()
  {
    String str = this.fio;
    int i = 0;
    int j;
    if (str != null) {
      j = str.hashCode();
    } else {
      j = 0;
    }
    str = this.iD_Data;
    int k;
    if (str != null) {
      k = str.hashCode();
    } else {
      k = 0;
    }
    str = this.deviceId;
    int m;
    if (str != null) {
      m = str.hashCode();
    } else {
      m = 0;
    }
    str = this.code;
    int n;
    if (str != null) {
      n = str.hashCode();
    } else {
      n = 0;
    }
    str = this.address;
    int i1;
    if (str != null) {
      i1 = str.hashCode();
    } else {
      i1 = 0;
    }
    str = this.phone;
    int i2;
    if (str != null) {
      i2 = str.hashCode();
    } else {
      i2 = 0;
    }
    str = this.liveness;
    int i3;
    if (str != null) {
      i3 = str.hashCode();
    } else {
      i3 = 0;
    }
    str = this.comments;
    if (str != null) {
      i = str.hashCode();
    }
    return ((((((j * 31 + k) * 31 + m) * 31 + n) * 31 + i1) * 31 + i2) * 31 + i3) * 31 + i;
  }
  
  public final void setCode(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
    this.code = paramString;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Device(fio=");
    localStringBuilder.append(this.fio);
    localStringBuilder.append(", iD_Data=");
    localStringBuilder.append(this.iD_Data);
    localStringBuilder.append(", deviceId=");
    localStringBuilder.append(this.deviceId);
    localStringBuilder.append(", code=");
    localStringBuilder.append(this.code);
    localStringBuilder.append(", address=");
    localStringBuilder.append(this.address);
    localStringBuilder.append(", phone=");
    localStringBuilder.append(this.phone);
    localStringBuilder.append(", liveness=");
    localStringBuilder.append(this.liveness);
    localStringBuilder.append(", comments=");
    localStringBuilder.append(this.comments);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
