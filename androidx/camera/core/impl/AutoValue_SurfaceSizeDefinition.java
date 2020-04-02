package androidx.camera.core.impl;

import android.util.Size;

final class AutoValue_SurfaceSizeDefinition
  extends SurfaceSizeDefinition
{
  private final Size analysisSize;
  private final Size previewSize;
  private final Size recordSize;
  
  AutoValue_SurfaceSizeDefinition(Size paramSize1, Size paramSize2, Size paramSize3)
  {
    if (paramSize1 != null)
    {
      this.analysisSize = paramSize1;
      if (paramSize2 != null)
      {
        this.previewSize = paramSize2;
        if (paramSize3 != null)
        {
          this.recordSize = paramSize3;
          return;
        }
        throw new NullPointerException("Null recordSize");
      }
      throw new NullPointerException("Null previewSize");
    }
    throw new NullPointerException("Null analysisSize");
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof SurfaceSizeDefinition))
    {
      paramObject = (SurfaceSizeDefinition)paramObject;
      if ((!this.analysisSize.equals(paramObject.getAnalysisSize())) || (!this.previewSize.equals(paramObject.getPreviewSize())) || (!this.recordSize.equals(paramObject.getRecordSize()))) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public Size getAnalysisSize()
  {
    return this.analysisSize;
  }
  
  public Size getPreviewSize()
  {
    return this.previewSize;
  }
  
  public Size getRecordSize()
  {
    return this.recordSize;
  }
  
  public int hashCode()
  {
    return ((this.analysisSize.hashCode() ^ 0xF4243) * 1000003 ^ this.previewSize.hashCode()) * 1000003 ^ this.recordSize.hashCode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SurfaceSizeDefinition{analysisSize=");
    localStringBuilder.append(this.analysisSize);
    localStringBuilder.append(", previewSize=");
    localStringBuilder.append(this.previewSize);
    localStringBuilder.append(", recordSize=");
    localStringBuilder.append(this.recordSize);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
