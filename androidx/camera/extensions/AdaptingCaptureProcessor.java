package androidx.camera.extensions;

import android.hardware.camera2.TotalCaptureResult;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import androidx.camera.camera2.impl.Camera2CameraCaptureResultConverter;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraCaptureResults;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.ImageProxyBundle;
import androidx.camera.extensions.impl.CaptureProcessorImpl;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class AdaptingCaptureProcessor
  implements CaptureProcessor
{
  private final CaptureProcessorImpl mImpl;
  
  AdaptingCaptureProcessor(CaptureProcessorImpl paramCaptureProcessorImpl)
  {
    this.mImpl = paramCaptureProcessorImpl;
  }
  
  public void onOutputSurface(Surface paramSurface, int paramInt)
  {
    this.mImpl.onOutputSurface(paramSurface, paramInt);
    this.mImpl.onImageFormatUpdate(paramInt);
  }
  
  public void onResolutionUpdate(Size paramSize)
  {
    this.mImpl.onResolutionUpdate(paramSize);
  }
  
  public void process(ImageProxyBundle paramImageProxyBundle)
  {
    Object localObject1 = paramImageProxyBundle.getCaptureIds();
    HashMap localHashMap = new HashMap();
    localObject1 = ((List)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      Integer localInteger = (Integer)((Iterator)localObject1).next();
      Object localObject2 = paramImageProxyBundle.getImageProxy(localInteger.intValue());
      try
      {
        localObject2 = (ImageProxy)((ListenableFuture)localObject2).get(5L, TimeUnit.SECONDS);
        if (((ImageProxy)localObject2).getImage() == null) {
          return;
        }
        Object localObject3 = CameraCaptureResults.retrieveCameraCaptureResult(((ImageProxy)localObject2).getImageInfo());
        if (localObject3 == null) {
          return;
        }
        localObject3 = Camera2CameraCaptureResultConverter.getCaptureResult((CameraCaptureResult)localObject3);
        if (localObject3 == null) {
          return;
        }
        localObject3 = (TotalCaptureResult)localObject3;
        if (localObject3 == null) {
          return;
        }
        Pair localPair = new android/util/Pair;
        localPair.<init>(((ImageProxy)localObject2).getImage(), localObject3);
        localHashMap.put(localInteger, localPair);
      }
      catch (TimeoutException|ExecutionException|InterruptedException paramImageProxyBundle)
      {
        return;
      }
    }
    this.mImpl.process(localHashMap);
  }
}
