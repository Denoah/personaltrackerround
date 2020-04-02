package androidx.camera.extensions;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.Log;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.impl.CameraFactory;
import androidx.camera.core.impl.LensFacingCameraIdFilter;
import java.util.Set;

class CameraUtil
{
  private static final String TAG = "CameraUtil";
  
  private CameraUtil() {}
  
  static CameraCharacteristics getCameraCharacteristics(String paramString)
  {
    Object localObject = (CameraManager)CameraX.getContext().getSystemService("camera");
    try
    {
      localObject = ((CameraManager)localObject).getCameraCharacteristics(paramString);
      return localObject;
    }
    catch (CameraAccessException localCameraAccessException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unable to retrieve info for camera with id ");
      localStringBuilder.append(paramString);
      localStringBuilder.append(".");
      throw new IllegalArgumentException(localStringBuilder.toString(), localCameraAccessException);
    }
  }
  
  static Set<String> getCameraIdSetWithLensFacing(Integer paramInteger)
    throws CameraInfoUnavailableException
  {
    Set localSet = CameraX.getCameraFactory().getAvailableCameraIds();
    return CameraX.getCameraFactory().getLensFacingCameraIdFilter(paramInteger.intValue()).filter(localSet);
  }
  
  static String getCameraIdUnchecked(CameraSelector paramCameraSelector)
  {
    try
    {
      paramCameraSelector = CameraX.getCameraWithCameraSelector(paramCameraSelector);
      return paramCameraSelector;
    }
    catch (IllegalArgumentException paramCameraSelector)
    {
      Log.w("CameraUtil", "Unable to get camera id for the camera selector.");
    }
    return null;
  }
}
