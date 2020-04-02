package androidx.camera.camera2.internal;

import android.hardware.camera2.CaptureResult;
import android.util.Log;
import androidx.camera.core.impl.CameraCaptureMetaData.AeState;
import androidx.camera.core.impl.CameraCaptureMetaData.AfMode;
import androidx.camera.core.impl.CameraCaptureMetaData.AfState;
import androidx.camera.core.impl.CameraCaptureMetaData.AwbState;
import androidx.camera.core.impl.CameraCaptureMetaData.FlashState;
import androidx.camera.core.impl.CameraCaptureResult;

public class Camera2CameraCaptureResult
  implements CameraCaptureResult
{
  private static final String TAG = "C2CameraCaptureResult";
  private final CaptureResult mCaptureResult;
  private final Object mTag;
  
  public Camera2CameraCaptureResult(Object paramObject, CaptureResult paramCaptureResult)
  {
    this.mTag = paramObject;
    this.mCaptureResult = paramCaptureResult;
  }
  
  public CameraCaptureMetaData.AeState getAeState()
  {
    Integer localInteger = (Integer)this.mCaptureResult.get(CaptureResult.CONTROL_AE_STATE);
    if (localInteger == null) {
      return CameraCaptureMetaData.AeState.UNKNOWN;
    }
    int i = localInteger.intValue();
    if (i != 0)
    {
      if (i != 1) {
        if (i != 2)
        {
          if (i != 3)
          {
            if (i != 4)
            {
              if (i != 5)
              {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append("Undefined ae state: ");
                localStringBuilder.append(localInteger);
                Log.e("C2CameraCaptureResult", localStringBuilder.toString());
                return CameraCaptureMetaData.AeState.UNKNOWN;
              }
            }
            else {
              return CameraCaptureMetaData.AeState.FLASH_REQUIRED;
            }
          }
          else {
            return CameraCaptureMetaData.AeState.LOCKED;
          }
        }
        else {
          return CameraCaptureMetaData.AeState.CONVERGED;
        }
      }
      return CameraCaptureMetaData.AeState.SEARCHING;
    }
    return CameraCaptureMetaData.AeState.INACTIVE;
  }
  
  public CameraCaptureMetaData.AfMode getAfMode()
  {
    Integer localInteger = (Integer)this.mCaptureResult.get(CaptureResult.CONTROL_AF_MODE);
    if (localInteger == null) {
      return CameraCaptureMetaData.AfMode.UNKNOWN;
    }
    int i = localInteger.intValue();
    if (i != 0) {
      if ((i != 1) && (i != 2))
      {
        if ((i != 3) && (i != 4))
        {
          if (i != 5)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("Undefined af mode: ");
            localStringBuilder.append(localInteger);
            Log.e("C2CameraCaptureResult", localStringBuilder.toString());
            return CameraCaptureMetaData.AfMode.UNKNOWN;
          }
        }
        else {
          return CameraCaptureMetaData.AfMode.ON_CONTINUOUS_AUTO;
        }
      }
      else {
        return CameraCaptureMetaData.AfMode.ON_MANUAL_AUTO;
      }
    }
    return CameraCaptureMetaData.AfMode.OFF;
  }
  
  public CameraCaptureMetaData.AfState getAfState()
  {
    Integer localInteger = (Integer)this.mCaptureResult.get(CaptureResult.CONTROL_AF_STATE);
    if (localInteger == null) {
      return CameraCaptureMetaData.AfState.UNKNOWN;
    }
    switch (localInteger.intValue())
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Undefined af state: ");
      localStringBuilder.append(localInteger);
      Log.e("C2CameraCaptureResult", localStringBuilder.toString());
      return CameraCaptureMetaData.AfState.UNKNOWN;
    case 5: 
      return CameraCaptureMetaData.AfState.LOCKED_NOT_FOCUSED;
    case 4: 
      return CameraCaptureMetaData.AfState.LOCKED_FOCUSED;
    case 2: 
      return CameraCaptureMetaData.AfState.FOCUSED;
    case 1: 
    case 3: 
    case 6: 
      return CameraCaptureMetaData.AfState.SCANNING;
    }
    return CameraCaptureMetaData.AfState.INACTIVE;
  }
  
  public CameraCaptureMetaData.AwbState getAwbState()
  {
    Integer localInteger = (Integer)this.mCaptureResult.get(CaptureResult.CONTROL_AWB_STATE);
    if (localInteger == null) {
      return CameraCaptureMetaData.AwbState.UNKNOWN;
    }
    int i = localInteger.intValue();
    if (i != 0)
    {
      if (i != 1)
      {
        if (i != 2)
        {
          if (i != 3)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("Undefined awb state: ");
            localStringBuilder.append(localInteger);
            Log.e("C2CameraCaptureResult", localStringBuilder.toString());
            return CameraCaptureMetaData.AwbState.UNKNOWN;
          }
          return CameraCaptureMetaData.AwbState.LOCKED;
        }
        return CameraCaptureMetaData.AwbState.CONVERGED;
      }
      return CameraCaptureMetaData.AwbState.METERING;
    }
    return CameraCaptureMetaData.AwbState.INACTIVE;
  }
  
  public CaptureResult getCaptureResult()
  {
    return this.mCaptureResult;
  }
  
  public CameraCaptureMetaData.FlashState getFlashState()
  {
    Integer localInteger = (Integer)this.mCaptureResult.get(CaptureResult.FLASH_STATE);
    if (localInteger == null) {
      return CameraCaptureMetaData.FlashState.UNKNOWN;
    }
    int i = localInteger.intValue();
    if ((i != 0) && (i != 1))
    {
      if (i != 2)
      {
        if ((i != 3) && (i != 4))
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Undefined flash state: ");
          localStringBuilder.append(localInteger);
          Log.e("C2CameraCaptureResult", localStringBuilder.toString());
          return CameraCaptureMetaData.FlashState.UNKNOWN;
        }
        return CameraCaptureMetaData.FlashState.FIRED;
      }
      return CameraCaptureMetaData.FlashState.READY;
    }
    return CameraCaptureMetaData.FlashState.NONE;
  }
  
  public Object getTag()
  {
    return this.mTag;
  }
  
  public long getTimestamp()
  {
    Long localLong = (Long)this.mCaptureResult.get(CaptureResult.SENSOR_TIMESTAMP);
    if (localLong == null) {
      return -1L;
    }
    return localLong.longValue();
  }
}
