package androidx.camera.core;

final class CameraClosedException
  extends RuntimeException
{
  CameraClosedException(String paramString)
  {
    super(paramString);
  }
  
  CameraClosedException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
}
