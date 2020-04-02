package androidx.camera.core;

public abstract interface ZoomState
{
  public abstract float getLinearZoom();
  
  public abstract float getMaxZoomRatio();
  
  public abstract float getMinZoomRatio();
  
  public abstract float getZoomRatio();
}
