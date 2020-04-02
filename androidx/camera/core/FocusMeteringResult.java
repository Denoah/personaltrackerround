package androidx.camera.core;

public final class FocusMeteringResult
{
  private boolean mIsFocusSuccessful;
  
  private FocusMeteringResult(boolean paramBoolean)
  {
    this.mIsFocusSuccessful = paramBoolean;
  }
  
  public static FocusMeteringResult create(boolean paramBoolean)
  {
    return new FocusMeteringResult(paramBoolean);
  }
  
  public static FocusMeteringResult emptyInstance()
  {
    return new FocusMeteringResult(false);
  }
  
  public boolean isFocusSuccessful()
  {
    return this.mIsFocusSuccessful;
  }
}
