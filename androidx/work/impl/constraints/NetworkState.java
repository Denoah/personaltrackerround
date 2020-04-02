package androidx.work.impl.constraints;

public class NetworkState
{
  private boolean mIsConnected;
  private boolean mIsMetered;
  private boolean mIsNotRoaming;
  private boolean mIsValidated;
  
  public NetworkState(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.mIsConnected = paramBoolean1;
    this.mIsValidated = paramBoolean2;
    this.mIsMetered = paramBoolean3;
    this.mIsNotRoaming = paramBoolean4;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof NetworkState)) {
      return false;
    }
    paramObject = (NetworkState)paramObject;
    if ((this.mIsConnected != paramObject.mIsConnected) || (this.mIsValidated != paramObject.mIsValidated) || (this.mIsMetered != paramObject.mIsMetered) || (this.mIsNotRoaming != paramObject.mIsNotRoaming)) {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    if (this.mIsConnected) {
      i = 1;
    } else {
      i = 0;
    }
    int j = i;
    if (this.mIsValidated) {
      j = i + 16;
    }
    int i = j;
    if (this.mIsMetered) {
      i = j + 256;
    }
    j = i;
    if (this.mIsNotRoaming) {
      j = i + 4096;
    }
    return j;
  }
  
  public boolean isConnected()
  {
    return this.mIsConnected;
  }
  
  public boolean isMetered()
  {
    return this.mIsMetered;
  }
  
  public boolean isNotRoaming()
  {
    return this.mIsNotRoaming;
  }
  
  public boolean isValidated()
  {
    return this.mIsValidated;
  }
  
  public String toString()
  {
    return String.format("[ Connected=%b Validated=%b Metered=%b NotRoaming=%b ]", new Object[] { Boolean.valueOf(this.mIsConnected), Boolean.valueOf(this.mIsValidated), Boolean.valueOf(this.mIsMetered), Boolean.valueOf(this.mIsNotRoaming) });
  }
}
