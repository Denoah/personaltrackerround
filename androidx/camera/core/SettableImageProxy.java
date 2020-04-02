package androidx.camera.core;

final class SettableImageProxy
  extends ForwardingImageProxy
{
  private final ImageInfo mImageInfo;
  
  SettableImageProxy(ImageProxy paramImageProxy, ImageInfo paramImageInfo)
  {
    super(paramImageProxy);
    this.mImageInfo = paramImageInfo;
  }
  
  public ImageInfo getImageInfo()
  {
    return this.mImageInfo;
  }
}
