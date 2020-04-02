package androidx.camera.core;

import android.media.ImageReader;
import androidx.camera.core.impl.ImageReaderProxy;

final class ImageReaderProxys
{
  private ImageReaderProxys() {}
  
  static ImageReaderProxy createIsolatedReader(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return new AndroidImageReaderProxy(ImageReader.newInstance(paramInt1, paramInt2, paramInt3, paramInt4));
  }
}
