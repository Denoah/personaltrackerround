package androidx.camera.core.impl;

public abstract interface CaptureStage
{
  public abstract CaptureConfig getCaptureConfig();
  
  public abstract int getId();
  
  public static final class DefaultCaptureStage
    implements CaptureStage
  {
    private final CaptureConfig mCaptureConfig = new CaptureConfig.Builder().build();
    
    public DefaultCaptureStage() {}
    
    public CaptureConfig getCaptureConfig()
    {
      return this.mCaptureConfig;
    }
    
    public int getId()
    {
      return 0;
    }
  }
}
