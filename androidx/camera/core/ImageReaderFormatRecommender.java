package androidx.camera.core;

final class ImageReaderFormatRecommender
{
  private ImageReaderFormatRecommender() {}
  
  static FormatCombo chooseCombo()
  {
    return FormatCombo.create(256, 35);
  }
  
  static abstract class FormatCombo
  {
    FormatCombo() {}
    
    static FormatCombo create(int paramInt1, int paramInt2)
    {
      return new AutoValue_ImageReaderFormatRecommender_FormatCombo(paramInt1, paramInt2);
    }
    
    abstract int imageAnalysisFormat();
    
    abstract int imageCaptureFormat();
  }
}
