package androidx.camera.view;

import android.graphics.SurfaceTexture;
import android.util.Size;

final class FixedSizeSurfaceTexture
  extends SurfaceTexture
{
  FixedSizeSurfaceTexture(int paramInt, Size paramSize)
  {
    super(paramInt);
    super.setDefaultBufferSize(paramSize.getWidth(), paramSize.getHeight());
  }
  
  FixedSizeSurfaceTexture(int paramInt, boolean paramBoolean, Size paramSize)
  {
    super(paramInt, paramBoolean);
    super.setDefaultBufferSize(paramSize.getWidth(), paramSize.getHeight());
  }
  
  FixedSizeSurfaceTexture(boolean paramBoolean, Size paramSize)
  {
    super(paramBoolean);
    super.setDefaultBufferSize(paramSize.getWidth(), paramSize.getHeight());
  }
  
  public void setDefaultBufferSize(int paramInt1, int paramInt2) {}
}
