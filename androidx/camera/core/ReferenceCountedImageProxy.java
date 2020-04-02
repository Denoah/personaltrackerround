package androidx.camera.core;

final class ReferenceCountedImageProxy
  extends ForwardingImageProxy
{
  private int mReferenceCount = 1;
  
  ReferenceCountedImageProxy(ImageProxy paramImageProxy)
  {
    super(paramImageProxy);
  }
  
  public void close()
  {
    try
    {
      if (this.mReferenceCount > 0)
      {
        int i = this.mReferenceCount - 1;
        this.mReferenceCount = i;
        if (i <= 0) {
          super.close();
        }
      }
      return;
    }
    finally {}
  }
  
  ImageProxy fork()
  {
    try
    {
      int i = this.mReferenceCount;
      if (i <= 0) {
        return null;
      }
      this.mReferenceCount += 1;
      SingleCloseImageProxy localSingleCloseImageProxy = new SingleCloseImageProxy(this);
      return localSingleCloseImageProxy;
    }
    finally {}
  }
  
  int getReferenceCount()
  {
    try
    {
      int i = this.mReferenceCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}
