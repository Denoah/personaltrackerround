package androidx.camera.core.impl;

public final class CameraCaptureMetaData
{
  private CameraCaptureMetaData() {}
  
  public static enum AeState
  {
    static
    {
      INACTIVE = new AeState("INACTIVE", 1);
      SEARCHING = new AeState("SEARCHING", 2);
      FLASH_REQUIRED = new AeState("FLASH_REQUIRED", 3);
      CONVERGED = new AeState("CONVERGED", 4);
      AeState localAeState = new AeState("LOCKED", 5);
      LOCKED = localAeState;
      $VALUES = new AeState[] { UNKNOWN, INACTIVE, SEARCHING, FLASH_REQUIRED, CONVERGED, localAeState };
    }
    
    private AeState() {}
  }
  
  public static enum AfMode
  {
    static
    {
      OFF = new AfMode("OFF", 1);
      ON_MANUAL_AUTO = new AfMode("ON_MANUAL_AUTO", 2);
      AfMode localAfMode = new AfMode("ON_CONTINUOUS_AUTO", 3);
      ON_CONTINUOUS_AUTO = localAfMode;
      $VALUES = new AfMode[] { UNKNOWN, OFF, ON_MANUAL_AUTO, localAfMode };
    }
    
    private AfMode() {}
  }
  
  public static enum AfState
  {
    static
    {
      INACTIVE = new AfState("INACTIVE", 1);
      SCANNING = new AfState("SCANNING", 2);
      FOCUSED = new AfState("FOCUSED", 3);
      LOCKED_FOCUSED = new AfState("LOCKED_FOCUSED", 4);
      AfState localAfState = new AfState("LOCKED_NOT_FOCUSED", 5);
      LOCKED_NOT_FOCUSED = localAfState;
      $VALUES = new AfState[] { UNKNOWN, INACTIVE, SCANNING, FOCUSED, LOCKED_FOCUSED, localAfState };
    }
    
    private AfState() {}
  }
  
  public static enum AwbState
  {
    static
    {
      INACTIVE = new AwbState("INACTIVE", 1);
      METERING = new AwbState("METERING", 2);
      CONVERGED = new AwbState("CONVERGED", 3);
      AwbState localAwbState = new AwbState("LOCKED", 4);
      LOCKED = localAwbState;
      $VALUES = new AwbState[] { UNKNOWN, INACTIVE, METERING, CONVERGED, localAwbState };
    }
    
    private AwbState() {}
  }
  
  public static enum FlashState
  {
    static
    {
      NONE = new FlashState("NONE", 1);
      READY = new FlashState("READY", 2);
      FlashState localFlashState = new FlashState("FIRED", 3);
      FIRED = localFlashState;
      $VALUES = new FlashState[] { UNKNOWN, NONE, READY, localFlashState };
    }
    
    private FlashState() {}
  }
}
