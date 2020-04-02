package androidx.camera.core;

abstract class ImmutableImageInfo
  implements ImageInfo
{
  ImmutableImageInfo() {}
  
  public static ImageInfo create(Object paramObject, long paramLong, int paramInt)
  {
    return new AutoValue_ImmutableImageInfo(paramObject, paramLong, paramInt);
  }
  
  public abstract int getRotationDegrees();
  
  public abstract Object getTag();
  
  public abstract long getTimestamp();
}
