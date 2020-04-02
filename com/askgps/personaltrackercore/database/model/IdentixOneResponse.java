package com.askgps.personaltrackercore.database.model;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\002\n\002\020\b\n\002\b\024\n\002\020\013\n\002\b\004\b?\b\030\0002\0020\001B5\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\006\022\006\020\007\032\0020\006\022\006\020\b\032\0020\003\022\006\020\t\032\0020\003?\006\002\020\nJ\t\020\023\032\0020\003H?\003J\t\020\024\032\0020\003H?\003J\t\020\025\032\0020\006H?\003J\t\020\026\032\0020\006H?\003J\t\020\027\032\0020\003H?\003J\t\020\030\032\0020\003H?\003JE\020\031\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\0032\b\b\002\020\005\032\0020\0062\b\b\002\020\007\032\0020\0062\b\b\002\020\b\032\0020\0032\b\b\002\020\t\032\0020\003H?\001J\023\020\032\032\0020\0332\b\020\034\032\004\030\0010\001H?\003J\t\020\035\032\0020\006H?\001J\t\020\036\032\0020\003H?\001R\026\020\005\032\0020\0068\006X?\004?\006\b\n\000\032\004\b\013\020\fR\026\020\002\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\r\020\016R\026\020\004\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\017\020\016R\026\020\t\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\020\020\016R\026\020\b\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\021\020\016R\026\020\007\032\0020\0068\006X?\004?\006\b\n\000\032\004\b\022\020\f?\006\037"}, d2={"Lcom/askgps/personaltrackercore/database/model/IdentixOneResponse;", "", "conf", "", "idxId", "age", "", "sex", "mood", "liveness", "(Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;Ljava/lang/String;)V", "getAge", "()I", "getConf", "()Ljava/lang/String;", "getIdxId", "getLiveness", "getMood", "getSex", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class IdentixOneResponse
{
  @SerializedName("age")
  private final int age;
  @SerializedName("conf")
  private final String conf;
  @SerializedName("idxid")
  private final String idxId;
  @SerializedName("liveness")
  private final String liveness;
  @SerializedName("mood")
  private final String mood;
  @SerializedName("sex")
  private final int sex;
  
  public IdentixOneResponse(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4)
  {
    this.conf = paramString1;
    this.idxId = paramString2;
    this.age = paramInt1;
    this.sex = paramInt2;
    this.mood = paramString3;
    this.liveness = paramString4;
  }
  
  public final String component1()
  {
    return this.conf;
  }
  
  public final String component2()
  {
    return this.idxId;
  }
  
  public final int component3()
  {
    return this.age;
  }
  
  public final int component4()
  {
    return this.sex;
  }
  
  public final String component5()
  {
    return this.mood;
  }
  
  public final String component6()
  {
    return this.liveness;
  }
  
  public final IdentixOneResponse copy(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "conf");
    Intrinsics.checkParameterIsNotNull(paramString2, "idxId");
    Intrinsics.checkParameterIsNotNull(paramString3, "mood");
    Intrinsics.checkParameterIsNotNull(paramString4, "liveness");
    return new IdentixOneResponse(paramString1, paramString2, paramInt1, paramInt2, paramString3, paramString4);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof IdentixOneResponse))
      {
        paramObject = (IdentixOneResponse)paramObject;
        if ((Intrinsics.areEqual(this.conf, paramObject.conf)) && (Intrinsics.areEqual(this.idxId, paramObject.idxId)) && (this.age == paramObject.age) && (this.sex == paramObject.sex) && (Intrinsics.areEqual(this.mood, paramObject.mood)) && (Intrinsics.areEqual(this.liveness, paramObject.liveness))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final int getAge()
  {
    return this.age;
  }
  
  public final String getConf()
  {
    return this.conf;
  }
  
  public final String getIdxId()
  {
    return this.idxId;
  }
  
  public final String getLiveness()
  {
    return this.liveness;
  }
  
  public final String getMood()
  {
    return this.mood;
  }
  
  public final int getSex()
  {
    return this.sex;
  }
  
  public int hashCode()
  {
    String str = this.conf;
    int i = 0;
    int j;
    if (str != null) {
      j = str.hashCode();
    } else {
      j = 0;
    }
    str = this.idxId;
    int k;
    if (str != null) {
      k = str.hashCode();
    } else {
      k = 0;
    }
    int m = this.age;
    int n = this.sex;
    str = this.mood;
    int i1;
    if (str != null) {
      i1 = str.hashCode();
    } else {
      i1 = 0;
    }
    str = this.liveness;
    if (str != null) {
      i = str.hashCode();
    }
    return ((((j * 31 + k) * 31 + m) * 31 + n) * 31 + i1) * 31 + i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("IdentixOneResponse(conf=");
    localStringBuilder.append(this.conf);
    localStringBuilder.append(", idxId=");
    localStringBuilder.append(this.idxId);
    localStringBuilder.append(", age=");
    localStringBuilder.append(this.age);
    localStringBuilder.append(", sex=");
    localStringBuilder.append(this.sex);
    localStringBuilder.append(", mood=");
    localStringBuilder.append(this.mood);
    localStringBuilder.append(", liveness=");
    localStringBuilder.append(this.liveness);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
