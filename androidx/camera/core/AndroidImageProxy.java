package androidx.camera.core;

import android.graphics.Rect;
import android.media.Image;
import android.media.Image.Plane;
import java.nio.ByteBuffer;

final class AndroidImageProxy
  implements ImageProxy
{
  private final Image mImage;
  private final ImageInfo mImageInfo;
  private final PlaneProxy[] mPlanes;
  
  AndroidImageProxy(Image paramImage)
  {
    this.mImage = paramImage;
    Image.Plane[] arrayOfPlane = paramImage.getPlanes();
    if (arrayOfPlane != null)
    {
      this.mPlanes = new PlaneProxy[arrayOfPlane.length];
      for (int i = 0; i < arrayOfPlane.length; i++) {
        this.mPlanes[i] = new PlaneProxy(arrayOfPlane[i]);
      }
    }
    this.mPlanes = new PlaneProxy[0];
    this.mImageInfo = ImmutableImageInfo.create(null, paramImage.getTimestamp(), 0);
  }
  
  public void close()
  {
    try
    {
      this.mImage.close();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public Rect getCropRect()
  {
    try
    {
      Rect localRect = this.mImage.getCropRect();
      return localRect;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public int getFormat()
  {
    try
    {
      int i = this.mImage.getFormat();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public int getHeight()
  {
    try
    {
      int i = this.mImage.getHeight();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public Image getImage()
  {
    try
    {
      Image localImage = this.mImage;
      return localImage;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public ImageInfo getImageInfo()
  {
    return this.mImageInfo;
  }
  
  public ImageProxy.PlaneProxy[] getPlanes()
  {
    try
    {
      PlaneProxy[] arrayOfPlaneProxy = this.mPlanes;
      return arrayOfPlaneProxy;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public int getWidth()
  {
    try
    {
      int i = this.mImage.getWidth();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void setCropRect(Rect paramRect)
  {
    try
    {
      this.mImage.setCropRect(paramRect);
      return;
    }
    finally
    {
      paramRect = finally;
      throw paramRect;
    }
  }
  
  private static final class PlaneProxy
    implements ImageProxy.PlaneProxy
  {
    private final Image.Plane mPlane;
    
    PlaneProxy(Image.Plane paramPlane)
    {
      this.mPlane = paramPlane;
    }
    
    public ByteBuffer getBuffer()
    {
      try
      {
        ByteBuffer localByteBuffer = this.mPlane.getBuffer();
        return localByteBuffer;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public int getPixelStride()
    {
      try
      {
        int i = this.mPlane.getPixelStride();
        return i;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public int getRowStride()
    {
      try
      {
        int i = this.mPlane.getRowStride();
        return i;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
  }
}
