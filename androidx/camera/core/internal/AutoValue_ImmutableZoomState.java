package androidx.camera.core.internal;

final class AutoValue_ImmutableZoomState
  extends ImmutableZoomState
{
  private final float linearZoom;
  private final float maxZoomRatio;
  private final float minZoomRatio;
  private final float zoomRatio;
  
  AutoValue_ImmutableZoomState(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.zoomRatio = paramFloat1;
    this.maxZoomRatio = paramFloat2;
    this.minZoomRatio = paramFloat3;
    this.linearZoom = paramFloat4;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof ImmutableZoomState))
    {
      paramObject = (ImmutableZoomState)paramObject;
      if ((Float.floatToIntBits(this.zoomRatio) != Float.floatToIntBits(paramObject.getZoomRatio())) || (Float.floatToIntBits(this.maxZoomRatio) != Float.floatToIntBits(paramObject.getMaxZoomRatio())) || (Float.floatToIntBits(this.minZoomRatio) != Float.floatToIntBits(paramObject.getMinZoomRatio())) || (Float.floatToIntBits(this.linearZoom) != Float.floatToIntBits(paramObject.getLinearZoom()))) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public float getLinearZoom()
  {
    return this.linearZoom;
  }
  
  public float getMaxZoomRatio()
  {
    return this.maxZoomRatio;
  }
  
  public float getMinZoomRatio()
  {
    return this.minZoomRatio;
  }
  
  public float getZoomRatio()
  {
    return this.zoomRatio;
  }
  
  public int hashCode()
  {
    return (((Float.floatToIntBits(this.zoomRatio) ^ 0xF4243) * 1000003 ^ Float.floatToIntBits(this.maxZoomRatio)) * 1000003 ^ Float.floatToIntBits(this.minZoomRatio)) * 1000003 ^ Float.floatToIntBits(this.linearZoom);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ImmutableZoomState{zoomRatio=");
    localStringBuilder.append(this.zoomRatio);
    localStringBuilder.append(", maxZoomRatio=");
    localStringBuilder.append(this.maxZoomRatio);
    localStringBuilder.append(", minZoomRatio=");
    localStringBuilder.append(this.minZoomRatio);
    localStringBuilder.append(", linearZoom=");
    localStringBuilder.append(this.linearZoom);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
