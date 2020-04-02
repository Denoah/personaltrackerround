package androidx.camera.core.impl;

public final class CameraCaptureFailure
{
  private final Reason mReason;
  
  public CameraCaptureFailure(Reason paramReason)
  {
    this.mReason = paramReason;
  }
  
  public Reason getReason()
  {
    return this.mReason;
  }
  
  public static enum Reason
  {
    static
    {
      Reason localReason = new Reason("ERROR", 0);
      ERROR = localReason;
      $VALUES = new Reason[] { localReason };
    }
    
    private Reason() {}
  }
}
