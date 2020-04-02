package androidx.camera.core;

final class AutoValue_ImageReaderFormatRecommender_FormatCombo
  extends ImageReaderFormatRecommender.FormatCombo
{
  private final int imageAnalysisFormat;
  private final int imageCaptureFormat;
  
  AutoValue_ImageReaderFormatRecommender_FormatCombo(int paramInt1, int paramInt2)
  {
    this.imageCaptureFormat = paramInt1;
    this.imageAnalysisFormat = paramInt2;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof ImageReaderFormatRecommender.FormatCombo))
    {
      paramObject = (ImageReaderFormatRecommender.FormatCombo)paramObject;
      if ((this.imageCaptureFormat != paramObject.imageCaptureFormat()) || (this.imageAnalysisFormat != paramObject.imageAnalysisFormat())) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public int hashCode()
  {
    return (this.imageCaptureFormat ^ 0xF4243) * 1000003 ^ this.imageAnalysisFormat;
  }
  
  int imageAnalysisFormat()
  {
    return this.imageAnalysisFormat;
  }
  
  int imageCaptureFormat()
  {
    return this.imageCaptureFormat;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("FormatCombo{imageCaptureFormat=");
    localStringBuilder.append(this.imageCaptureFormat);
    localStringBuilder.append(", imageAnalysisFormat=");
    localStringBuilder.append(this.imageAnalysisFormat);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
