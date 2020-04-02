package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.util.Log;
import androidx.camera.core.ZoomState;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.utils.CameraOrientationUtil;
import androidx.core.util.Preconditions;
import androidx.lifecycle.LiveData;

public final class Camera2CameraInfoImpl
  implements CameraInfoInternal
{
  private static final String TAG = "Camera2CameraInfo";
  private final CameraCharacteristics mCameraCharacteristics;
  private final String mCameraId;
  private final TorchControl mTorchControl;
  private final ZoomControl mZoomControl;
  
  Camera2CameraInfoImpl(String paramString, CameraCharacteristics paramCameraCharacteristics, ZoomControl paramZoomControl, TorchControl paramTorchControl)
  {
    Preconditions.checkNotNull(paramCameraCharacteristics, "Camera characteristics map is missing");
    this.mCameraId = ((String)Preconditions.checkNotNull(paramString));
    this.mCameraCharacteristics = paramCameraCharacteristics;
    this.mZoomControl = paramZoomControl;
    this.mTorchControl = paramTorchControl;
    logDeviceInfo();
  }
  
  private void logDeviceInfo()
  {
    logDeviceLevel();
  }
  
  private void logDeviceLevel()
  {
    int i = getSupportedHardwareLevel();
    Object localObject;
    if (i != 0)
    {
      if (i != 1)
      {
        if (i != 2)
        {
          if (i != 3)
          {
            if (i != 4)
            {
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("Unknown value: ");
              ((StringBuilder)localObject).append(i);
              localObject = ((StringBuilder)localObject).toString();
            }
            else
            {
              localObject = "INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL";
            }
          }
          else {
            localObject = "INFO_SUPPORTED_HARDWARE_LEVEL_3";
          }
        }
        else {
          localObject = "INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY";
        }
      }
      else {
        localObject = "INFO_SUPPORTED_HARDWARE_LEVEL_FULL";
      }
    }
    else {
      localObject = "INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Device Level: ");
    localStringBuilder.append((String)localObject);
    Log.i("Camera2CameraInfo", localStringBuilder.toString());
  }
  
  public String getCameraId()
  {
    return this.mCameraId;
  }
  
  public String getImplementationType()
  {
    String str;
    if (getSupportedHardwareLevel() == 2) {
      str = "androidx.camera.camera2.legacy";
    } else {
      str = "androidx.camera.camera2";
    }
    return str;
  }
  
  public Integer getLensFacing()
  {
    Integer localInteger = (Integer)this.mCameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
    Preconditions.checkNotNull(localInteger);
    int i = localInteger.intValue();
    if (i != 0)
    {
      if (i != 1) {
        return null;
      }
      return Integer.valueOf(1);
    }
    return Integer.valueOf(0);
  }
  
  int getSensorOrientation()
  {
    Integer localInteger = (Integer)this.mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
    Preconditions.checkNotNull(localInteger);
    return localInteger.intValue();
  }
  
  public int getSensorRotationDegrees()
  {
    return getSensorRotationDegrees(0);
  }
  
  public int getSensorRotationDegrees(int paramInt)
  {
    int i = getSensorOrientation();
    paramInt = CameraOrientationUtil.surfaceRotationToDegrees(paramInt);
    Integer localInteger = getLensFacing();
    boolean bool = true;
    if ((localInteger == null) || (1 != localInteger.intValue())) {
      bool = false;
    }
    return CameraOrientationUtil.getRelativeImageRotation(paramInt, Integer.valueOf(i).intValue(), bool);
  }
  
  int getSupportedHardwareLevel()
  {
    Integer localInteger = (Integer)this.mCameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
    Preconditions.checkNotNull(localInteger);
    return localInteger.intValue();
  }
  
  public LiveData<Integer> getTorchState()
  {
    return this.mTorchControl.getTorchState();
  }
  
  public LiveData<ZoomState> getZoomState()
  {
    return this.mZoomControl.getZoomState();
  }
  
  public boolean hasFlashUnit()
  {
    Boolean localBoolean = (Boolean)this.mCameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
    Preconditions.checkNotNull(localBoolean);
    return localBoolean.booleanValue();
  }
}
