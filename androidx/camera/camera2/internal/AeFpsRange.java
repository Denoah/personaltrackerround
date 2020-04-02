package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.util.Range;
import androidx.camera.camera2.impl.Camera2ImplConfig.Builder;

final class AeFpsRange
{
  private Range<Integer> mAeTargetFpsRange = null;
  
  AeFpsRange(CameraCharacteristics paramCameraCharacteristics)
  {
    Integer localInteger = (Integer)paramCameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
    if ((localInteger != null) && (localInteger.intValue() == 2)) {
      this.mAeTargetFpsRange = pickSuitableFpsRange((Range[])paramCameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES));
    }
  }
  
  private static Range<Integer> getCorrectedFpsRange(Range<Integer> paramRange)
  {
    int i = ((Integer)paramRange.getUpper()).intValue();
    int j = ((Integer)paramRange.getLower()).intValue();
    if (((Integer)paramRange.getUpper()).intValue() >= 1000) {
      i = ((Integer)paramRange.getUpper()).intValue() / 1000;
    }
    if (((Integer)paramRange.getLower()).intValue() >= 1000) {
      j = ((Integer)paramRange.getLower()).intValue() / 1000;
    }
    return new Range(Integer.valueOf(j), Integer.valueOf(i));
  }
  
  private static Range<Integer> pickSuitableFpsRange(Range<Integer>[] paramArrayOfRange)
  {
    Range localRange = null;
    Object localObject1 = null;
    Object localObject2 = localRange;
    if (paramArrayOfRange != null) {
      if (paramArrayOfRange.length == 0)
      {
        localObject2 = localRange;
      }
      else
      {
        int i = paramArrayOfRange.length;
        int j = 0;
        for (;;)
        {
          localObject2 = localObject1;
          if (j >= i) {
            break;
          }
          localRange = getCorrectedFpsRange(paramArrayOfRange[j]);
          if (((Integer)localRange.getUpper()).intValue() != 30)
          {
            localObject2 = localObject1;
          }
          else if (localObject1 != null)
          {
            localObject2 = localObject1;
            if (((Integer)localRange.getLower()).intValue() >= ((Integer)localObject1.getLower()).intValue()) {}
          }
          else
          {
            localObject2 = localRange;
          }
          j++;
          localObject1 = localObject2;
        }
      }
    }
    return localObject2;
  }
  
  public void addAeFpsRangeOptions(Camera2ImplConfig.Builder paramBuilder)
  {
    if (this.mAeTargetFpsRange != null) {
      paramBuilder.setCaptureRequestOption(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, this.mAeTargetFpsRange);
    }
  }
}
