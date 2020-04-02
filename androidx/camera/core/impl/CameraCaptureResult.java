package androidx.camera.core.impl;

public abstract interface CameraCaptureResult
{
  public abstract CameraCaptureMetaData.AeState getAeState();
  
  public abstract CameraCaptureMetaData.AfMode getAfMode();
  
  public abstract CameraCaptureMetaData.AfState getAfState();
  
  public abstract CameraCaptureMetaData.AwbState getAwbState();
  
  public abstract CameraCaptureMetaData.FlashState getFlashState();
  
  public abstract Object getTag();
  
  public abstract long getTimestamp();
  
  public static final class EmptyCameraCaptureResult
    implements CameraCaptureResult
  {
    public EmptyCameraCaptureResult() {}
    
    public static CameraCaptureResult create()
    {
      return new EmptyCameraCaptureResult();
    }
    
    public CameraCaptureMetaData.AeState getAeState()
    {
      return CameraCaptureMetaData.AeState.UNKNOWN;
    }
    
    public CameraCaptureMetaData.AfMode getAfMode()
    {
      return CameraCaptureMetaData.AfMode.UNKNOWN;
    }
    
    public CameraCaptureMetaData.AfState getAfState()
    {
      return CameraCaptureMetaData.AfState.UNKNOWN;
    }
    
    public CameraCaptureMetaData.AwbState getAwbState()
    {
      return CameraCaptureMetaData.AwbState.UNKNOWN;
    }
    
    public CameraCaptureMetaData.FlashState getFlashState()
    {
      return CameraCaptureMetaData.FlashState.UNKNOWN;
    }
    
    public Object getTag()
    {
      return null;
    }
    
    public long getTimestamp()
    {
      return -1L;
    }
  }
}
