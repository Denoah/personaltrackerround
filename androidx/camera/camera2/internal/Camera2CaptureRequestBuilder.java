package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.CaptureRequest.Key;
import android.util.Log;
import android.view.Surface;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.Config.Option;
import androidx.camera.core.impl.DeferrableSurface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Camera2CaptureRequestBuilder
{
  private static final String TAG = "CaptureRequestBuilder";
  
  private Camera2CaptureRequestBuilder() {}
  
  private static void applyImplementationOptionToCaptureBuilder(CaptureRequest.Builder paramBuilder, Config paramConfig)
  {
    Camera2ImplConfig localCamera2ImplConfig = new Camera2ImplConfig(paramConfig);
    paramConfig = localCamera2ImplConfig.getCaptureRequestOptions().iterator();
    while (paramConfig.hasNext())
    {
      Config.Option localOption = (Config.Option)paramConfig.next();
      CaptureRequest.Key localKey = (CaptureRequest.Key)localOption.getToken();
      try
      {
        paramBuilder.set(localKey, localCamera2ImplConfig.retrieveOption(localOption));
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("CaptureRequest.Key is not supported: ");
        localStringBuilder.append(localKey);
        Log.e("CaptureRequestBuilder", localStringBuilder.toString());
      }
    }
  }
  
  public static CaptureRequest build(CaptureConfig paramCaptureConfig, CameraDevice paramCameraDevice, Map<DeferrableSurface, Surface> paramMap)
    throws CameraAccessException
  {
    if (paramCameraDevice == null) {
      return null;
    }
    paramMap = getConfiguredSurfaces(paramCaptureConfig.getSurfaces(), paramMap);
    if (paramMap.isEmpty()) {
      return null;
    }
    paramCameraDevice = paramCameraDevice.createCaptureRequest(paramCaptureConfig.getTemplateType());
    applyImplementationOptionToCaptureBuilder(paramCameraDevice, paramCaptureConfig.getImplementationOptions());
    if (paramCaptureConfig.getImplementationOptions().containsOption(CaptureConfig.OPTION_ROTATION)) {
      paramCameraDevice.set(CaptureRequest.JPEG_ORIENTATION, paramCaptureConfig.getImplementationOptions().retrieveOption(CaptureConfig.OPTION_ROTATION));
    }
    paramMap = paramMap.iterator();
    while (paramMap.hasNext()) {
      paramCameraDevice.addTarget((Surface)paramMap.next());
    }
    paramCameraDevice.setTag(paramCaptureConfig.getTag());
    return paramCameraDevice.build();
  }
  
  public static CaptureRequest buildWithoutTarget(CaptureConfig paramCaptureConfig, CameraDevice paramCameraDevice)
    throws CameraAccessException
  {
    if (paramCameraDevice == null) {
      return null;
    }
    paramCameraDevice = paramCameraDevice.createCaptureRequest(paramCaptureConfig.getTemplateType());
    applyImplementationOptionToCaptureBuilder(paramCameraDevice, paramCaptureConfig.getImplementationOptions());
    return paramCameraDevice.build();
  }
  
  private static List<Surface> getConfiguredSurfaces(List<DeferrableSurface> paramList, Map<DeferrableSurface, Surface> paramMap)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      paramList = (Surface)paramMap.get((DeferrableSurface)localIterator.next());
      if (paramList != null) {
        localArrayList.add(paramList);
      } else {
        throw new IllegalArgumentException("DeferrableSurface not in configuredSurfaceMap");
      }
    }
    return localArrayList;
  }
}
