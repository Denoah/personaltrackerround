package androidx.camera.extensions;

import androidx.camera.core.impl.CameraIdFilter;
import androidx.camera.extensions.impl.ImageCaptureExtenderImpl;
import androidx.camera.extensions.impl.PreviewExtenderImpl;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class ExtensionCameraIdFilter
  implements CameraIdFilter
{
  private ImageCaptureExtenderImpl mImageCaptureExtenderImpl;
  private PreviewExtenderImpl mPreviewExtenderImpl;
  
  ExtensionCameraIdFilter(ImageCaptureExtenderImpl paramImageCaptureExtenderImpl)
  {
    this.mPreviewExtenderImpl = null;
    this.mImageCaptureExtenderImpl = paramImageCaptureExtenderImpl;
  }
  
  ExtensionCameraIdFilter(PreviewExtenderImpl paramPreviewExtenderImpl)
  {
    this.mPreviewExtenderImpl = paramPreviewExtenderImpl;
    this.mImageCaptureExtenderImpl = null;
  }
  
  ExtensionCameraIdFilter(PreviewExtenderImpl paramPreviewExtenderImpl, ImageCaptureExtenderImpl paramImageCaptureExtenderImpl)
  {
    this.mPreviewExtenderImpl = paramPreviewExtenderImpl;
    this.mImageCaptureExtenderImpl = paramImageCaptureExtenderImpl;
  }
  
  public Set<String> filter(Set<String> paramSet)
  {
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
    {
      paramSet = (String)localIterator.next();
      boolean bool = true;
      Object localObject = this.mPreviewExtenderImpl;
      if (localObject != null) {
        bool = ((PreviewExtenderImpl)localObject).isExtensionAvailable(paramSet, CameraUtil.getCameraCharacteristics(paramSet));
      }
      localObject = this.mImageCaptureExtenderImpl;
      if (localObject != null) {
        bool = ((ImageCaptureExtenderImpl)localObject).isExtensionAvailable(paramSet, CameraUtil.getCameraCharacteristics(paramSet));
      }
      if (bool) {
        localLinkedHashSet.add(paramSet);
      }
    }
    return localLinkedHashSet;
  }
}
