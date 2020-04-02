package com.askgps.personaltrackercore.database.model;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\016\n\002\b\t\n\002\020\013\n\002\b\004\b?\b\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006J\t\020\013\032\0020\003H?\003J\t\020\f\032\0020\005H?\003J\035\020\r\032\0020\0002\b\b\002\020\002\032\0020\0032\b\b\002\020\004\032\0020\005H?\001J\023\020\016\032\0020\0172\b\020\020\032\004\030\0010\001H?\003J\t\020\021\032\0020\003H?\001J\t\020\022\032\0020\005H?\001R\026\020\002\032\0020\0038\006X?\004?\006\b\n\000\032\004\b\007\020\bR\026\020\004\032\0020\0058\006X?\004?\006\b\n\000\032\004\b\t\020\n?\006\023"}, d2={"Lcom/askgps/personaltrackercore/database/model/AuthResponse;", "", "code", "", "message", "", "(ILjava/lang/String;)V", "getCode", "()I", "getMessage", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class AuthResponse
{
  @SerializedName("code")
  private final int code;
  @SerializedName("message")
  private final String message;
  
  public AuthResponse(int paramInt, String paramString)
  {
    this.code = paramInt;
    this.message = paramString;
  }
  
  public final int component1()
  {
    return this.code;
  }
  
  public final String component2()
  {
    return this.message;
  }
  
  public final AuthResponse copy(int paramInt, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "message");
    return new AuthResponse(paramInt, paramString);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof AuthResponse))
      {
        paramObject = (AuthResponse)paramObject;
        if ((this.code == paramObject.code) && (Intrinsics.areEqual(this.message, paramObject.message))) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final int getCode()
  {
    return this.code;
  }
  
  public final String getMessage()
  {
    return this.message;
  }
  
  public int hashCode()
  {
    int i = this.code;
    String str = this.message;
    int j;
    if (str != null) {
      j = str.hashCode();
    } else {
      j = 0;
    }
    return i * 31 + j;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("AuthResponse(code=");
    localStringBuilder.append(this.code);
    localStringBuilder.append(", message=");
    localStringBuilder.append(this.message);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
