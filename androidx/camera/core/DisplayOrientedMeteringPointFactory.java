package androidx.camera.core;

import android.graphics.PointF;
import android.view.Display;
import androidx.camera.core.impl.CameraInfoInternal;

public final class DisplayOrientedMeteringPointFactory
  extends MeteringPointFactory
{
  private final CameraInfoInternal mCameraInfo;
  private final CameraSelector mCameraSelector;
  private final Display mDisplay;
  private final float mHeight;
  private final float mWidth;
  
  public DisplayOrientedMeteringPointFactory(Display paramDisplay, CameraSelector paramCameraSelector, float paramFloat1, float paramFloat2)
  {
    this.mWidth = paramFloat1;
    this.mHeight = paramFloat2;
    this.mCameraSelector = paramCameraSelector;
    this.mDisplay = paramDisplay;
    try
    {
      this.mCameraInfo = CameraX.getCameraInfo(CameraX.getCameraWithCameraSelector(paramCameraSelector));
      return;
    }
    catch (Exception paramDisplay)
    {
      throw new IllegalArgumentException("Unable to get camera id for the CameraSelector.", paramDisplay);
    }
  }
  
  private Integer getLensFacing()
  {
    return this.mCameraInfo.getLensFacing();
  }
  
  private int getRelativeCameraOrientation(boolean paramBoolean)
  {
    int i;
    try
    {
      i = this.mDisplay.getRotation();
      int j = this.mCameraInfo.getSensorRotationDegrees(i);
      i = j;
      if (paramBoolean) {
        i = (360 - j) % 360;
      }
    }
    catch (Exception localException)
    {
      i = 0;
    }
    return i;
  }
  
  protected PointF convertPoint(float paramFloat1, float paramFloat2)
  {
    float f1 = this.mWidth;
    float f2 = this.mHeight;
    Integer localInteger = getLensFacing();
    boolean bool;
    if ((localInteger != null) && (localInteger.intValue() == 0)) {
      bool = true;
    } else {
      bool = false;
    }
    int i = getRelativeCameraOrientation(bool);
    float f3 = f1;
    float f4 = f2;
    float f5 = paramFloat1;
    float f6 = paramFloat2;
    if (i != 90) {
      if (i == 270)
      {
        f3 = f1;
        f4 = f2;
        f5 = paramFloat1;
        f6 = paramFloat2;
      }
      else
      {
        f4 = f1;
        f3 = f2;
        f6 = paramFloat1;
        f5 = paramFloat2;
      }
    }
    paramFloat1 = f6;
    if (i != 90)
    {
      if (i != 180)
      {
        if (i == 270) {
          f6 = f4 - f6;
        }
      }
      else {
        paramFloat1 = f4 - f6;
      }
    }
    else
    {
      f5 = f3 - f5;
      f6 = paramFloat1;
    }
    paramFloat1 = f6;
    if (bool) {
      paramFloat1 = f4 - f6;
    }
    return new PointF(paramFloat1 / f4, f5 / f3);
  }
}
