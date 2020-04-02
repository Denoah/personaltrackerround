package androidx.camera.core.impl;

import android.os.Handler;
import android.view.Surface;
import androidx.camera.core.ImageProxy;
import java.util.concurrent.Executor;

public abstract interface ImageReaderProxy
{
  public abstract ImageProxy acquireLatestImage();
  
  public abstract ImageProxy acquireNextImage();
  
  public abstract void close();
  
  public abstract int getHeight();
  
  public abstract int getImageFormat();
  
  public abstract int getMaxImages();
  
  public abstract Surface getSurface();
  
  public abstract int getWidth();
  
  public abstract void setOnImageAvailableListener(OnImageAvailableListener paramOnImageAvailableListener, Handler paramHandler);
  
  public abstract void setOnImageAvailableListener(OnImageAvailableListener paramOnImageAvailableListener, Executor paramExecutor);
  
  public static abstract interface OnImageAvailableListener
  {
    public abstract void onImageAvailable(ImageReaderProxy paramImageReaderProxy);
  }
}
