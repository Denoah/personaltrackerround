package androidx.camera.core.impl;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExtendableBuilder;
import androidx.camera.core.UseCase;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.TargetConfig.Builder;
import androidx.camera.core.internal.UseCaseEventConfig;
import androidx.camera.core.internal.UseCaseEventConfig.Builder;

public abstract interface UseCaseConfig<T extends UseCase>
  extends TargetConfig<T>, Config, UseCaseEventConfig
{
  public static final Config.Option<CameraSelector> OPTION_CAMERA_SELECTOR = Config.Option.create("camerax.core.useCase.cameraSelector", CameraSelector.class);
  public static final Config.Option<CaptureConfig.OptionUnpacker> OPTION_CAPTURE_CONFIG_UNPACKER;
  public static final Config.Option<CaptureConfig> OPTION_DEFAULT_CAPTURE_CONFIG;
  public static final Config.Option<SessionConfig> OPTION_DEFAULT_SESSION_CONFIG = Config.Option.create("camerax.core.useCase.defaultSessionConfig", SessionConfig.class);
  public static final Config.Option<SessionConfig.OptionUnpacker> OPTION_SESSION_CONFIG_UNPACKER;
  public static final Config.Option<Integer> OPTION_SURFACE_OCCUPANCY_PRIORITY;
  
  static
  {
    OPTION_DEFAULT_CAPTURE_CONFIG = Config.Option.create("camerax.core.useCase.defaultCaptureConfig", CaptureConfig.class);
    OPTION_SESSION_CONFIG_UNPACKER = Config.Option.create("camerax.core.useCase.sessionConfigUnpacker", SessionConfig.OptionUnpacker.class);
    OPTION_CAPTURE_CONFIG_UNPACKER = Config.Option.create("camerax.core.useCase.captureConfigUnpacker", CaptureConfig.OptionUnpacker.class);
    OPTION_SURFACE_OCCUPANCY_PRIORITY = Config.Option.create("camerax.core.useCase.surfaceOccupancyPriority", Integer.TYPE);
  }
  
  public abstract CameraSelector getCameraSelector();
  
  public abstract CameraSelector getCameraSelector(CameraSelector paramCameraSelector);
  
  public abstract CaptureConfig.OptionUnpacker getCaptureOptionUnpacker();
  
  public abstract CaptureConfig.OptionUnpacker getCaptureOptionUnpacker(CaptureConfig.OptionUnpacker paramOptionUnpacker);
  
  public abstract CaptureConfig getDefaultCaptureConfig();
  
  public abstract CaptureConfig getDefaultCaptureConfig(CaptureConfig paramCaptureConfig);
  
  public abstract SessionConfig getDefaultSessionConfig();
  
  public abstract SessionConfig getDefaultSessionConfig(SessionConfig paramSessionConfig);
  
  public abstract SessionConfig.OptionUnpacker getSessionOptionUnpacker();
  
  public abstract SessionConfig.OptionUnpacker getSessionOptionUnpacker(SessionConfig.OptionUnpacker paramOptionUnpacker);
  
  public abstract int getSurfaceOccupancyPriority();
  
  public abstract int getSurfaceOccupancyPriority(int paramInt);
  
  public static abstract interface Builder<T extends UseCase, C extends UseCaseConfig<T>, B>
    extends TargetConfig.Builder<T, B>, ExtendableBuilder<T>, UseCaseEventConfig.Builder<B>
  {
    public abstract C getUseCaseConfig();
    
    public abstract B setCameraSelector(CameraSelector paramCameraSelector);
    
    public abstract B setCaptureOptionUnpacker(CaptureConfig.OptionUnpacker paramOptionUnpacker);
    
    public abstract B setDefaultCaptureConfig(CaptureConfig paramCaptureConfig);
    
    public abstract B setDefaultSessionConfig(SessionConfig paramSessionConfig);
    
    public abstract B setSessionOptionUnpacker(SessionConfig.OptionUnpacker paramOptionUnpacker);
    
    public abstract B setSurfaceOccupancyPriority(int paramInt);
  }
}
