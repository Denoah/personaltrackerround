package androidx.camera.extensions;

import android.hardware.camera2.TotalCaptureResult;
import androidx.camera.camera2.impl.Camera2CameraCaptureResultConverter;
import androidx.camera.core.ImageInfo;
import androidx.camera.core.impl.CameraCaptureResults;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.ImageInfoProcessor;
import androidx.camera.extensions.impl.PreviewExtenderImpl;
import androidx.camera.extensions.impl.PreviewExtenderImpl.ProcessorType;
import androidx.camera.extensions.impl.RequestUpdateProcessorImpl;
import androidx.core.util.Preconditions;

final class AdaptingRequestUpdateProcessor
  implements ImageInfoProcessor, PreviewExtender.CloseableProcessor
{
  private BlockingCloseAccessCounter mAccessCounter = new BlockingCloseAccessCounter();
  private final PreviewExtenderImpl mPreviewExtenderImpl;
  private final RequestUpdateProcessorImpl mProcessorImpl;
  
  AdaptingRequestUpdateProcessor(PreviewExtenderImpl paramPreviewExtenderImpl)
  {
    boolean bool;
    if (paramPreviewExtenderImpl.getProcessorType() == PreviewExtenderImpl.ProcessorType.PROCESSOR_TYPE_REQUEST_UPDATE_ONLY) {
      bool = true;
    } else {
      bool = false;
    }
    Preconditions.checkArgument(bool, "AdaptingRequestUpdateProcess can only adapt extender with PROCESSOR_TYPE_REQUEST_UPDATE_ONLY ProcessorType.");
    this.mPreviewExtenderImpl = paramPreviewExtenderImpl;
    this.mProcessorImpl = ((RequestUpdateProcessorImpl)paramPreviewExtenderImpl.getProcessor());
  }
  
  public void close()
  {
    this.mAccessCounter.destroyAndWaitForZeroAccess();
  }
  
  public CaptureStage getCaptureStage()
  {
    if (!this.mAccessCounter.tryIncrement()) {
      return null;
    }
    try
    {
      AdaptingCaptureStage localAdaptingCaptureStage = new AdaptingCaptureStage(this.mPreviewExtenderImpl.getCaptureStage());
      return localAdaptingCaptureStage;
    }
    finally
    {
      this.mAccessCounter.decrement();
    }
  }
  
  public boolean process(ImageInfo paramImageInfo)
  {
    boolean bool1 = this.mAccessCounter.tryIncrement();
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    try
    {
      paramImageInfo = Camera2CameraCaptureResultConverter.getCaptureResult(CameraCaptureResults.retrieveCameraCaptureResult(paramImageInfo));
      bool1 = bool2;
      if ((paramImageInfo instanceof TotalCaptureResult))
      {
        paramImageInfo = this.mProcessorImpl.process((TotalCaptureResult)paramImageInfo);
        bool1 = bool2;
        if (paramImageInfo != null) {
          bool1 = true;
        }
      }
      return bool1;
    }
    finally
    {
      this.mAccessCounter.decrement();
    }
  }
}
