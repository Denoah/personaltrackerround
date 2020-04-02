package androidx.camera.core;

public abstract interface ImageInfo
{
  public abstract int getRotationDegrees();
  
  public abstract Object getTag();
  
  public abstract long getTimestamp();
}
