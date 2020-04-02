package androidx.camera.core.impl;

import androidx.camera.core.ImageInfo;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;
import java.util.List;

public final class SingleImageProxyBundle
  implements ImageProxyBundle
{
  private final int mCaptureId;
  private final ImageProxy mImageProxy;
  
  public SingleImageProxyBundle(ImageProxy paramImageProxy)
  {
    Object localObject = paramImageProxy.getImageInfo();
    if (localObject != null)
    {
      localObject = ((ImageInfo)localObject).getTag();
      if (localObject != null)
      {
        if ((localObject instanceof Integer))
        {
          this.mCaptureId = ((Integer)localObject).intValue();
          this.mImageProxy = paramImageProxy;
          return;
        }
        throw new IllegalArgumentException("ImageProxy has tag that isn't an integer");
      }
      throw new IllegalArgumentException("ImageProxy has no associated tag");
    }
    throw new IllegalArgumentException("ImageProxy has no associated ImageInfo");
  }
  
  SingleImageProxyBundle(ImageProxy paramImageProxy, int paramInt)
  {
    this.mCaptureId = paramInt;
    this.mImageProxy = paramImageProxy;
  }
  
  public void close()
  {
    this.mImageProxy.close();
  }
  
  public List<Integer> getCaptureIds()
  {
    return Collections.singletonList(Integer.valueOf(this.mCaptureId));
  }
  
  public ListenableFuture<ImageProxy> getImageProxy(int paramInt)
  {
    if (paramInt != this.mCaptureId) {
      return Futures.immediateFailedFuture(new IllegalArgumentException("Capture id does not exist in the bundle"));
    }
    return Futures.immediateFuture(this.mImageProxy);
  }
}
