package androidx.camera.core;

final class SingleCloseImageProxy
  extends ForwardingImageProxy
{
  private boolean mClosed = false;
  
  SingleCloseImageProxy(ImageProxy paramImageProxy)
  {
    super(paramImageProxy);
  }
  
  public void close()
  {
    try
    {
      if (!this.mClosed)
      {
        this.mClosed = true;
        super.close();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}
