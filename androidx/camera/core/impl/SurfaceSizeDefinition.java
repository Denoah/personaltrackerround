package androidx.camera.core.impl;

import android.util.Size;

public abstract class SurfaceSizeDefinition
{
  SurfaceSizeDefinition() {}
  
  public static SurfaceSizeDefinition create(Size paramSize1, Size paramSize2, Size paramSize3)
  {
    return new AutoValue_SurfaceSizeDefinition(paramSize1, paramSize2, paramSize3);
  }
  
  public abstract Size getAnalysisSize();
  
  public abstract Size getPreviewSize();
  
  public abstract Size getRecordSize();
}
