package androidx.camera.core.impl;

import androidx.camera.core.CameraX;
import java.util.Set;

public class LensFacingCameraIdFilter
  implements CameraIdFilter
{
  private int mLensFacing;
  
  public LensFacingCameraIdFilter(int paramInt)
  {
    this.mLensFacing = paramInt;
  }
  
  public Set<String> filter(Set<String> paramSet)
  {
    return CameraX.getCameraFactory().getLensFacingCameraIdFilter(this.mLensFacing).filter(paramSet);
  }
  
  public int getLensFacing()
  {
    return this.mLensFacing;
  }
}
