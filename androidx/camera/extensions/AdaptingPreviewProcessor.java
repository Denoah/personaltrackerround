package androidx.camera.extensions;

import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import androidx.camera.camera2.impl.Camera2CameraCaptureResultConverter;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.CameraCaptureResults;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.ImageProxyBundle;
import androidx.camera.extensions.impl.PreviewImageProcessorImpl;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.concurrent.ExecutionException;

final class AdaptingPreviewProcessor
  implements CaptureProcessor, PreviewExtender.CloseableProcessor
{
  private static final String TAG = "AdaptingPreviewProcesso";
  private BlockingCloseAccessCounter mAccessCounter = new BlockingCloseAccessCounter();
  private final PreviewImageProcessorImpl mImpl;
  
  AdaptingPreviewProcessor(PreviewImageProcessorImpl paramPreviewImageProcessorImpl)
  {
    this.mImpl = paramPreviewImageProcessorImpl;
  }
  
  public void close()
  {
    this.mAccessCounter.destroyAndWaitForZeroAccess();
  }
  
  public void onOutputSurface(Surface paramSurface, int paramInt)
  {
    if (!this.mAccessCounter.tryIncrement()) {
      return;
    }
    try
    {
      this.mImpl.onOutputSurface(paramSurface, paramInt);
      this.mImpl.onImageFormatUpdate(paramInt);
      return;
    }
    finally
    {
      this.mAccessCounter.decrement();
    }
  }
  
  public void onResolutionUpdate(Size paramSize)
  {
    if (!this.mAccessCounter.tryIncrement()) {
      return;
    }
    try
    {
      this.mImpl.onResolutionUpdate(paramSize);
      return;
    }
    finally
    {
      this.mAccessCounter.decrement();
    }
  }
  
  public void process(ImageProxyBundle paramImageProxyBundle)
  {
    Object localObject1 = paramImageProxyBundle.getCaptureIds();
    int i = ((List)localObject1).size();
    boolean bool = true;
    if (i != 1) {
      bool = false;
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Processing preview bundle must be 1, but found ");
    ((StringBuilder)localObject2).append(((List)localObject1).size());
    Preconditions.checkArgument(bool, ((StringBuilder)localObject2).toString());
    paramImageProxyBundle = paramImageProxyBundle.getImageProxy(((Integer)((List)localObject1).get(0)).intValue());
    Preconditions.checkArgument(paramImageProxyBundle.isDone());
    try
    {
      paramImageProxyBundle = (ImageProxy)paramImageProxyBundle.get();
      localObject2 = paramImageProxyBundle.getImage();
      localObject1 = Camera2CameraCaptureResultConverter.getCaptureResult(CameraCaptureResults.retrieveCameraCaptureResult(paramImageProxyBundle.getImageInfo()));
      paramImageProxyBundle = null;
      if ((localObject1 instanceof TotalCaptureResult)) {
        paramImageProxyBundle = (TotalCaptureResult)localObject1;
      }
      if (localObject2 == null) {
        return;
      }
      if (!this.mAccessCounter.tryIncrement()) {
        return;
      }
      try
      {
        this.mImpl.process((Image)localObject2, paramImageProxyBundle);
        return;
      }
      finally
      {
        this.mAccessCounter.decrement();
      }
      return;
    }
    catch (ExecutionException|InterruptedException paramImageProxyBundle)
    {
      Log.e("AdaptingPreviewProcesso", "Unable to retrieve ImageProxy from bundle");
    }
  }
}
