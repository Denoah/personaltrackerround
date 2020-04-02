package androidx.work.impl.model;

public class Preference
{
  public String mKey;
  public Long mValue;
  
  public Preference(String paramString, long paramLong)
  {
    this.mKey = paramString;
    this.mValue = Long.valueOf(paramLong);
  }
  
  public Preference(String paramString, boolean paramBoolean)
  {
    this(paramString, l);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof Preference)) {
      return false;
    }
    Object localObject = (Preference)paramObject;
    if (!this.mKey.equals(((Preference)localObject).mKey)) {
      return false;
    }
    paramObject = this.mValue;
    localObject = ((Preference)localObject).mValue;
    if (paramObject != null) {
      bool = paramObject.equals(localObject);
    } else if (localObject != null) {
      bool = false;
    }
    return bool;
  }
  
  public int hashCode()
  {
    int i = this.mKey.hashCode();
    Long localLong = this.mValue;
    int j;
    if (localLong != null) {
      j = localLong.hashCode();
    } else {
      j = 0;
    }
    return i * 31 + j;
  }
}
