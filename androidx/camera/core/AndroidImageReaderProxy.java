package androidx.camera.core;

import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.os.Handler;
import android.view.Surface;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener;
import androidx.camera.core.impl.utils.MainThreadAsyncHandler;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import java.util.concurrent.Executor;

final class AndroidImageReaderProxy
  implements ImageReaderProxy
{
  private final ImageReader mImageReader;
  
  AndroidImageReaderProxy(ImageReader paramImageReader)
  {
    this.mImageReader = paramImageReader;
  }
  
  private boolean isImageReaderContextNotInitializedException(RuntimeException paramRuntimeException)
  {
    return "ImageReaderContext is not initialized".equals(paramRuntimeException.getMessage());
  }
  
  /* Error */
  public ImageProxy acquireLatestImage()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 19	androidx/camera/core/AndroidImageReaderProxy:mImageReader	Landroid/media/ImageReader;
    //   6: invokevirtual 43	android/media/ImageReader:acquireLatestImage	()Landroid/media/Image;
    //   9: astore_1
    //   10: goto +20 -> 30
    //   13: astore_1
    //   14: goto +39 -> 53
    //   17: astore_1
    //   18: aload_0
    //   19: aload_1
    //   20: invokespecial 45	androidx/camera/core/AndroidImageReaderProxy:isImageReaderContextNotInitializedException	(Ljava/lang/RuntimeException;)Z
    //   23: istore_2
    //   24: iload_2
    //   25: ifeq +26 -> 51
    //   28: aconst_null
    //   29: astore_1
    //   30: aload_1
    //   31: ifnonnull +7 -> 38
    //   34: aload_0
    //   35: monitorexit
    //   36: aconst_null
    //   37: areturn
    //   38: new 47	androidx/camera/core/AndroidImageProxy
    //   41: dup
    //   42: aload_1
    //   43: invokespecial 50	androidx/camera/core/AndroidImageProxy:<init>	(Landroid/media/Image;)V
    //   46: astore_1
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_1
    //   50: areturn
    //   51: aload_1
    //   52: athrow
    //   53: aload_0
    //   54: monitorexit
    //   55: aload_1
    //   56: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	57	0	this	AndroidImageReaderProxy
    //   9	1	1	localImage	android.media.Image
    //   13	1	1	localObject1	Object
    //   17	3	1	localRuntimeException	RuntimeException
    //   29	27	1	localObject2	Object
    //   23	2	2	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	10	13	finally
    //   18	24	13	finally
    //   38	47	13	finally
    //   51	53	13	finally
    //   2	10	17	java/lang/RuntimeException
  }
  
  /* Error */
  public ImageProxy acquireNextImage()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 19	androidx/camera/core/AndroidImageReaderProxy:mImageReader	Landroid/media/ImageReader;
    //   6: invokevirtual 53	android/media/ImageReader:acquireNextImage	()Landroid/media/Image;
    //   9: astore_1
    //   10: goto +20 -> 30
    //   13: astore_1
    //   14: goto +39 -> 53
    //   17: astore_1
    //   18: aload_0
    //   19: aload_1
    //   20: invokespecial 45	androidx/camera/core/AndroidImageReaderProxy:isImageReaderContextNotInitializedException	(Ljava/lang/RuntimeException;)Z
    //   23: istore_2
    //   24: iload_2
    //   25: ifeq +26 -> 51
    //   28: aconst_null
    //   29: astore_1
    //   30: aload_1
    //   31: ifnonnull +7 -> 38
    //   34: aload_0
    //   35: monitorexit
    //   36: aconst_null
    //   37: areturn
    //   38: new 47	androidx/camera/core/AndroidImageProxy
    //   41: dup
    //   42: aload_1
    //   43: invokespecial 50	androidx/camera/core/AndroidImageProxy:<init>	(Landroid/media/Image;)V
    //   46: astore_1
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_1
    //   50: areturn
    //   51: aload_1
    //   52: athrow
    //   53: aload_0
    //   54: monitorexit
    //   55: aload_1
    //   56: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	57	0	this	AndroidImageReaderProxy
    //   9	1	1	localImage	android.media.Image
    //   13	1	1	localObject1	Object
    //   17	3	1	localRuntimeException	RuntimeException
    //   29	27	1	localObject2	Object
    //   23	2	2	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	10	13	finally
    //   18	24	13	finally
    //   38	47	13	finally
    //   51	53	13	finally
    //   2	10	17	java/lang/RuntimeException
  }
  
  public void close()
  {
    try
    {
      this.mImageReader.close();
      return;
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
      int i = this.mImageReader.getHeight();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public int getImageFormat()
  {
    try
    {
      int i = this.mImageReader.getImageFormat();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public int getMaxImages()
  {
    try
    {
      int i = this.mImageReader.getMaxImages();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public Surface getSurface()
  {
    try
    {
      Surface localSurface = this.mImageReader.getSurface();
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
    try
    {
      int i = this.mImageReader.getWidth();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
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
      ImageReader.OnImageAvailableListener local1 = new androidx/camera/core/AndroidImageReaderProxy$1;
      local1.<init>(this, paramExecutor, paramOnImageAvailableListener);
      this.mImageReader.setOnImageAvailableListener(local1, MainThreadAsyncHandler.getInstance());
      return;
    }
    finally
    {
      paramOnImageAvailableListener = finally;
      throw paramOnImageAvailableListener;
    }
  }
}
