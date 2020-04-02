package androidx.camera.core;

final class AutoValue_ImmutableImageInfo
  extends ImmutableImageInfo
{
  private final int rotationDegrees;
  private final Object tag;
  private final long timestamp;
  
  AutoValue_ImmutableImageInfo(Object paramObject, long paramLong, int paramInt)
  {
    this.tag = paramObject;
    this.timestamp = paramLong;
    this.rotationDegrees = paramInt;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof ImmutableImageInfo))
    {
      paramObject = (ImmutableImageInfo)paramObject;
      Object localObject = this.tag;
      if ((localObject == null ? paramObject.getTag() != null : !localObject.equals(paramObject.getTag())) || (this.timestamp != paramObject.getTimestamp()) || (this.rotationDegrees != paramObject.getRotationDegrees())) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public int getRotationDegrees()
  {
    return this.rotationDegrees;
  }
  
  public Object getTag()
  {
    return this.tag;
  }
  
  public long getTimestamp()
  {
    return this.timestamp;
  }
  
  public int hashCode()
  {
    Object localObject = this.tag;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = localObject.hashCode();
    }
    long l = this.timestamp;
    return ((i ^ 0xF4243) * 1000003 ^ (int)(l ^ l >>> 32)) * 1000003 ^ this.rotationDegrees;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ImmutableImageInfo{tag=");
    localStringBuilder.append(this.tag);
    localStringBuilder.append(", timestamp=");
    localStringBuilder.append(this.timestamp);
    localStringBuilder.append(", rotationDegrees=");
    localStringBuilder.append(this.rotationDegrees);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
