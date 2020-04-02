package androidx.camera.core;

import android.graphics.Rect;
import android.media.Image;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

abstract class ForwardingImageProxy
  implements ImageProxy
{
  protected final ImageProxy mImage;
  private final Set<OnImageCloseListener> mOnImageCloseListeners = new HashSet();
  
  protected ForwardingImageProxy(ImageProxy paramImageProxy)
  {
    this.mImage = paramImageProxy;
  }
  
  void addOnImageCloseListener(OnImageCloseListener paramOnImageCloseListener)
  {
    try
    {
      this.mOnImageCloseListeners.add(paramOnImageCloseListener);
      return;
    }
    finally
    {
      paramOnImageCloseListener = finally;
      throw paramOnImageCloseListener;
    }
  }
  
  public void close()
  {
    this.mImage.close();
    notifyOnImageCloseListeners();
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
      Image localImage = this.mImage.getImage();
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
    try
    {
      ImageInfo localImageInfo = this.mImage.getImageInfo();
      return localImageInfo;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public ImageProxy.PlaneProxy[] getPlanes()
  {
    try
    {
      ImageProxy.PlaneProxy[] arrayOfPlaneProxy = this.mImage.getPlanes();
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
  
  protected void notifyOnImageCloseListeners()
  {
    try
    {
      Object localObject1 = new java/util/HashSet;
      ((HashSet)localObject1).<init>(this.mOnImageCloseListeners);
      localObject1 = ((Set)localObject1).iterator();
      while (((Iterator)localObject1).hasNext()) {
        ((OnImageCloseListener)((Iterator)localObject1).next()).onImageClose(this);
      }
      return;
    }
    finally {}
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
  
  static abstract interface OnImageCloseListener
  {
    public abstract void onImageClose(ImageProxy paramImageProxy);
  }
}
