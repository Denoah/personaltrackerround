package androidx.camera.core.internal;

import androidx.camera.core.ZoomState;

public abstract class ImmutableZoomState
  implements ZoomState
{
  public ImmutableZoomState() {}
  
  public static ZoomState create(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return new AutoValue_ImmutableZoomState(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public static ZoomState create(ZoomState paramZoomState)
  {
    return new AutoValue_ImmutableZoomState(paramZoomState.getZoomRatio(), paramZoomState.getMaxZoomRatio(), paramZoomState.getMinZoomRatio(), paramZoomState.getLinearZoom());
  }
  
  public abstract float getLinearZoom();
  
  public abstract float getMaxZoomRatio();
  
  public abstract float getMinZoomRatio();
  
  public abstract float getZoomRatio();
}
