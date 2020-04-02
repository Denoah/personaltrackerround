package androidx.camera.core.impl.utils;

public final class CameraOrientationUtil
{
  private static final boolean DEBUG = false;
  private static final String TAG = "CameraOrientationUtil";
  
  private CameraOrientationUtil() {}
  
  public static int getRelativeImageRotation(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramInt1 = (paramInt2 - paramInt1 + 360) % 360;
    } else {
      paramInt1 = (paramInt2 + paramInt1) % 360;
    }
    return paramInt1;
  }
  
  public static int surfaceRotationToDegrees(int paramInt)
  {
    if (paramInt != 0)
    {
      if (paramInt != 1)
      {
        if (paramInt != 2)
        {
          if (paramInt == 3)
          {
            paramInt = 270;
          }
          else
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("Unsupported surface rotation: ");
            localStringBuilder.append(paramInt);
            throw new IllegalArgumentException(localStringBuilder.toString());
          }
        }
        else {
          paramInt = 180;
        }
      }
      else {
        paramInt = 90;
      }
    }
    else {
      paramInt = 0;
    }
    return paramInt;
  }
}
