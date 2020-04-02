package androidx.camera.core;

import android.graphics.Rect;
import android.media.Image;
import java.nio.ByteBuffer;

public abstract interface ImageProxy
  extends AutoCloseable
{
  public abstract void close();
  
  public abstract Rect getCropRect();
  
  public abstract int getFormat();
  
  public abstract int getHeight();
  
  public abstract Image getImage();
  
  public abstract ImageInfo getImageInfo();
  
  public abstract PlaneProxy[] getPlanes();
  
  public abstract int getWidth();
  
  public abstract void setCropRect(Rect paramRect);
  
  public static abstract interface PlaneProxy
  {
    public abstract ByteBuffer getBuffer();
    
    public abstract int getPixelStride();
    
    public abstract int getRowStride();
  }
}
