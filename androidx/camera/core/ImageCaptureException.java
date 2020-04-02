package androidx.camera.core;

public class ImageCaptureException
  extends Exception
{
  private final int mImageCaptureError;
  
  public ImageCaptureException(int paramInt, String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
    this.mImageCaptureError = paramInt;
  }
  
  public int getImageCaptureError()
  {
    return this.mImageCaptureError;
  }
}
