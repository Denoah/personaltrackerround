package androidx.camera.core;

import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class ForwardingImageReaderListener
  implements ImageReaderProxy.OnImageAvailableListener
{
  private final List<QueuedImageReaderProxy> mImageReaders;
  
  ForwardingImageReaderListener(List<QueuedImageReaderProxy> paramList)
  {
    this.mImageReaders = Collections.unmodifiableList(new ArrayList(paramList));
  }
  
  public void onImageAvailable(ImageReaderProxy arg1)
  {
    try
    {
      ??? = ???.acquireNextImage();
      if (??? == null) {
        return;
      }
      ReferenceCountedImageProxy localReferenceCountedImageProxy = new androidx/camera/core/ReferenceCountedImageProxy;
      localReferenceCountedImageProxy.<init>(???);
      Iterator localIterator = this.mImageReaders.iterator();
      while (localIterator.hasNext()) {
        synchronized ((QueuedImageReaderProxy)localIterator.next())
        {
          if (!???.isClosed()) {
            ???.enqueueImage(ImageProxyDownsampler.downsample(localReferenceCountedImageProxy.fork(), ???.getWidth(), ???.getHeight(), ImageProxyDownsampler.DownsamplingMethod.AVERAGING));
          }
        }
      }
      localObject.close();
      return;
    }
    finally {}
  }
}
