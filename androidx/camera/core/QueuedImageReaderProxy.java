package androidx.camera.core;

import android.os.Handler;
import android.view.Surface;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

final class QueuedImageReaderProxy
  implements ImageReaderProxy, ForwardingImageProxy.OnImageCloseListener
{
  private final Set<ImageProxy> mAcquiredImages = new HashSet();
  private boolean mClosed;
  private int mCurrentPosition;
  private final int mFormat;
  private final int mHeight;
  private final List<ImageProxy> mImages;
  private final int mMaxImages;
  private Executor mOnImageAvailableExecutor;
  private ImageReaderProxy.OnImageAvailableListener mOnImageAvailableListener;
  private final Set<OnReaderCloseListener> mOnReaderCloseListeners = new HashSet();
  private final Surface mSurface;
  private final int mWidth;
  
  QueuedImageReaderProxy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Surface paramSurface)
  {
    this.mWidth = paramInt1;
    this.mHeight = paramInt2;
    this.mFormat = paramInt3;
    this.mMaxImages = paramInt4;
    this.mSurface = paramSurface;
    this.mImages = new ArrayList(paramInt4);
    this.mCurrentPosition = 0;
    this.mClosed = false;
  }
  
  private void notifyOnReaderCloseListeners()
  {
    try
    {
      Iterator localIterator = this.mOnReaderCloseListeners.iterator();
      while (localIterator.hasNext()) {
        ((OnReaderCloseListener)localIterator.next()).onReaderClose(this);
      }
      return;
    }
    finally {}
  }
  
  private void throwExceptionIfClosed()
  {
    try
    {
      boolean bool = this.mClosed;
      if (!bool) {
        return;
      }
      IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
      localIllegalStateException.<init>("This reader is already closed.");
      throw localIllegalStateException;
    }
    finally {}
  }
  
  public ImageProxy acquireLatestImage()
  {
    try
    {
      throwExceptionIfClosed();
      boolean bool = this.mImages.isEmpty();
      if (bool) {
        return null;
      }
      if (this.mCurrentPosition < this.mImages.size())
      {
        localObject1 = new java/util/ArrayList;
        ((ArrayList)localObject1).<init>();
        for (int i = 0; i < this.mImages.size() - 1; i++) {
          if (!this.mAcquiredImages.contains(this.mImages.get(i))) {
            ((List)localObject1).add(this.mImages.get(i));
          }
        }
        localObject1 = ((List)localObject1).iterator();
        while (((Iterator)localObject1).hasNext()) {
          ((ImageProxy)((Iterator)localObject1).next()).close();
        }
        i = this.mImages.size() - 1;
        this.mCurrentPosition = i;
        localObject1 = this.mImages;
        this.mCurrentPosition = (i + 1);
        localObject1 = (ImageProxy)((List)localObject1).get(i);
        this.mAcquiredImages.add(localObject1);
        return localObject1;
      }
      Object localObject1 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject1).<init>("Max images have already been acquired without close.");
      throw ((Throwable)localObject1);
    }
    finally {}
  }
  
  public ImageProxy acquireNextImage()
  {
    try
    {
      throwExceptionIfClosed();
      boolean bool = this.mImages.isEmpty();
      if (bool) {
        return null;
      }
      if (this.mCurrentPosition < this.mImages.size())
      {
        localObject1 = this.mImages;
        int i = this.mCurrentPosition;
        this.mCurrentPosition = (i + 1);
        localObject1 = (ImageProxy)((List)localObject1).get(i);
        this.mAcquiredImages.add(localObject1);
        return localObject1;
      }
      Object localObject1 = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject1).<init>("Max images have already been acquired without close.");
      throw ((Throwable)localObject1);
    }
    finally {}
  }
  
  void addOnReaderCloseListener(OnReaderCloseListener paramOnReaderCloseListener)
  {
    try
    {
      this.mOnReaderCloseListeners.add(paramOnReaderCloseListener);
      return;
    }
    finally
    {
      paramOnReaderCloseListener = finally;
      throw paramOnReaderCloseListener;
    }
  }
  
  public void close()
  {
    try
    {
      if (!this.mClosed)
      {
        this.mOnImageAvailableExecutor = null;
        this.mOnImageAvailableListener = null;
        Object localObject1 = new java/util/ArrayList;
        ((ArrayList)localObject1).<init>(this.mImages);
        localObject1 = ((List)localObject1).iterator();
        while (((Iterator)localObject1).hasNext()) {
          ((ImageProxy)((Iterator)localObject1).next()).close();
        }
        this.mImages.clear();
        this.mClosed = true;
        notifyOnReaderCloseListeners();
      }
      return;
    }
    finally {}
  }
  
  void enqueueImage(ForwardingImageProxy paramForwardingImageProxy)
  {
    try
    {
      throwExceptionIfClosed();
      if (this.mImages.size() < this.mMaxImages)
      {
        this.mImages.add(paramForwardingImageProxy);
        paramForwardingImageProxy.addOnImageCloseListener(this);
        if ((this.mOnImageAvailableListener != null) && (this.mOnImageAvailableExecutor != null))
        {
          ImageReaderProxy.OnImageAvailableListener localOnImageAvailableListener = this.mOnImageAvailableListener;
          paramForwardingImageProxy = this.mOnImageAvailableExecutor;
          Runnable local1 = new androidx/camera/core/QueuedImageReaderProxy$1;
          local1.<init>(this, localOnImageAvailableListener);
          paramForwardingImageProxy.execute(local1);
        }
      }
      else
      {
        paramForwardingImageProxy.close();
      }
      return;
    }
    finally {}
  }
  
  int getCurrentImages()
  {
    try
    {
      throwExceptionIfClosed();
      int i = this.mImages.size();
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
    throwExceptionIfClosed();
    return this.mHeight;
  }
  
  public int getImageFormat()
  {
    throwExceptionIfClosed();
    return this.mFormat;
  }
  
  public int getMaxImages()
  {
    throwExceptionIfClosed();
    return this.mMaxImages;
  }
  
  public Surface getSurface()
  {
    try
    {
      throwExceptionIfClosed();
      Surface localSurface = this.mSurface;
      return localSurface;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public int getWidth()
  {
    throwExceptionIfClosed();
    return this.mWidth;
  }
  
  boolean isClosed()
  {
    try
    {
      boolean bool = this.mClosed;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void onImageClose(ImageProxy paramImageProxy)
  {
    try
    {
      int i = this.mImages.indexOf(paramImageProxy);
      if (i >= 0)
      {
        this.mImages.remove(i);
        if (i <= this.mCurrentPosition) {
          this.mCurrentPosition -= 1;
        }
      }
      this.mAcquiredImages.remove(paramImageProxy);
      return;
    }
    finally {}
  }
  
  public void setOnImageAvailableListener(ImageReaderProxy.OnImageAvailableListener paramOnImageAvailableListener, Handler paramHandler)
  {
    if (paramHandler == null) {
      paramHandler = null;
    }
    try
    {
      paramHandler = CameraXExecutors.newHandlerExecutor(paramHandler);
      setOnImageAvailableListener(paramOnImageAvailableListener, paramHandler);
      return;
    }
    finally {}
  }
  
  public void setOnImageAvailableListener(ImageReaderProxy.OnImageAvailableListener paramOnImageAvailableListener, Executor paramExecutor)
  {
    try
    {
      throwExceptionIfClosed();
      this.mOnImageAvailableListener = paramOnImageAvailableListener;
      this.mOnImageAvailableExecutor = paramExecutor;
      return;
    }
    finally
    {
      paramOnImageAvailableListener = finally;
      throw paramOnImageAvailableListener;
    }
  }
  
  static abstract interface OnReaderCloseListener
  {
    public abstract void onReaderClose(ImageReaderProxy paramImageReaderProxy);
  }
}
