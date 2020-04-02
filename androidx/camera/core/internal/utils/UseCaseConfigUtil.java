package androidx.camera.core.internal.utils;

import android.util.Rational;
import android.util.Size;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.ImageOutputConfig.Builder;
import androidx.camera.core.impl.UseCaseConfig.Builder;
import androidx.camera.core.impl.utils.CameraOrientationUtil;

public final class UseCaseConfigUtil
{
  private UseCaseConfigUtil() {}
  
  public static void updateTargetRotationAndRelatedConfigs(UseCaseConfig.Builder<?, ?, ?> paramBuilder, int paramInt)
  {
    Object localObject = (ImageOutputConfig)paramBuilder.getUseCaseConfig();
    int i = ((ImageOutputConfig)localObject).getTargetRotation(-1);
    if ((i == -1) || (i != paramInt)) {
      ((ImageOutputConfig.Builder)paramBuilder).setTargetRotation(paramInt);
    }
    if ((i != -1) && (paramInt != -1) && (i != paramInt))
    {
      i = CameraOrientationUtil.surfaceRotationToDegrees(i);
      if (Math.abs(CameraOrientationUtil.surfaceRotationToDegrees(paramInt) - i) % 180 == 90)
      {
        Size localSize = ((ImageOutputConfig)localObject).getTargetResolution(null);
        localObject = ((ImageOutputConfig)localObject).getTargetAspectRatioCustom(null);
        if (localSize != null) {
          ((ImageOutputConfig.Builder)paramBuilder).setTargetResolution(new Size(localSize.getHeight(), localSize.getWidth()));
        }
        if (localObject != null) {
          ((ImageOutputConfig.Builder)paramBuilder).setTargetAspectRatioCustom(new Rational(((Rational)localObject).getDenominator(), ((Rational)localObject).getNumerator()));
        }
      }
    }
  }
}
