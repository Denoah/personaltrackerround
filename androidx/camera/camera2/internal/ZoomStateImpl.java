package androidx.camera.camera2.internal;

import androidx.camera.core.ZoomState;
import androidx.core.math.MathUtils;

class ZoomStateImpl
  implements ZoomState
{
  private float mLinearZoom;
  private final float mMaxZoomRatio;
  private final float mMinZoomRatio;
  private float mZoomRatio;
  
  ZoomStateImpl(float paramFloat1, float paramFloat2)
  {
    this.mMaxZoomRatio = paramFloat1;
    this.mMinZoomRatio = paramFloat2;
  }
  
  private float getPercentageByRatio(float paramFloat)
  {
    float f1 = this.mMaxZoomRatio;
    float f2 = this.mMinZoomRatio;
    if (f1 == f2) {
      return 0.0F;
    }
    if (paramFloat == f1) {
      return 1.0F;
    }
    if (paramFloat == f2) {
      return 0.0F;
    }
    paramFloat = 1.0F / paramFloat;
    f1 = 1.0F / f1;
    f2 = 1.0F / f2;
    return (paramFloat - f2) / (f1 - f2);
  }
  
  private float getRatioByPercentage(float paramFloat)
  {
    if (paramFloat == 1.0F) {
      return this.mMaxZoomRatio;
    }
    if (paramFloat == 0.0F) {
      return this.mMinZoomRatio;
    }
    float f1 = this.mMaxZoomRatio;
    double d1 = 1.0F / f1;
    float f2 = this.mMinZoomRatio;
    double d2 = 1.0F / f2;
    return (float)MathUtils.clamp(1.0D / (d2 + (d1 - d2) * paramFloat), f2, f1);
  }
  
  public float getLinearZoom()
  {
    return this.mLinearZoom;
  }
  
  public float getMaxZoomRatio()
  {
    return this.mMaxZoomRatio;
  }
  
  public float getMinZoomRatio()
  {
    return this.mMinZoomRatio;
  }
  
  public float getZoomRatio()
  {
    return this.mZoomRatio;
  }
  
  void setLinearZoom(float paramFloat)
    throws IllegalArgumentException
  {
    if ((paramFloat <= 1.0F) && (paramFloat >= 0.0F))
    {
      this.mLinearZoom = paramFloat;
      this.mZoomRatio = getRatioByPercentage(paramFloat);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Requested linearZoom ");
    localStringBuilder.append(paramFloat);
    localStringBuilder.append(" is not within valid range [0..1]");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  void setZoomRatio(float paramFloat)
    throws IllegalArgumentException
  {
    if ((paramFloat <= this.mMaxZoomRatio) && (paramFloat >= this.mMinZoomRatio))
    {
      this.mZoomRatio = paramFloat;
      this.mLinearZoom = getPercentageByRatio(paramFloat);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Requested zoomRatio ");
    localStringBuilder.append(paramFloat);
    localStringBuilder.append(" is not within valid range [");
    localStringBuilder.append(this.mMinZoomRatio);
    localStringBuilder.append(" , ");
    localStringBuilder.append(this.mMaxZoomRatio);
    localStringBuilder.append("]");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
}
