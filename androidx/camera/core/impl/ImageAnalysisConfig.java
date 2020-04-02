package androidx.camera.core.impl;

import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysis.BackpressureStrategy;
import androidx.camera.core.UseCase.EventCallback;
import androidx.camera.core.internal.ThreadConfig;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public final class ImageAnalysisConfig
  implements UseCaseConfig<ImageAnalysis>, ImageOutputConfig, ThreadConfig
{
  public static final Config.Option<Integer> OPTION_BACKPRESSURE_STRATEGY = Config.Option.create("camerax.core.imageAnalysis.backpressureStrategy", ImageAnalysis.BackpressureStrategy.class);
  public static final Config.Option<Integer> OPTION_IMAGE_QUEUE_DEPTH = Config.Option.create("camerax.core.imageAnalysis.imageQueueDepth", Integer.TYPE);
  private final OptionsBundle mConfig;
  
  public ImageAnalysisConfig(OptionsBundle paramOptionsBundle)
  {
    this.mConfig = paramOptionsBundle;
  }
  
  public boolean containsOption(Config.Option<?> paramOption)
  {
    return this.mConfig.containsOption(paramOption);
  }
  
  public void findOptions(String paramString, Config.OptionMatcher paramOptionMatcher)
  {
    this.mConfig.findOptions(paramString, paramOptionMatcher);
  }
  
  public Executor getBackgroundExecutor()
  {
    return (Executor)retrieveOption(OPTION_BACKGROUND_EXECUTOR);
  }
  
  public Executor getBackgroundExecutor(Executor paramExecutor)
  {
    return (Executor)retrieveOption(OPTION_BACKGROUND_EXECUTOR, paramExecutor);
  }
  
  public int getBackpressureStrategy()
  {
    return ((Integer)retrieveOption(OPTION_BACKPRESSURE_STRATEGY)).intValue();
  }
  
  public int getBackpressureStrategy(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_BACKPRESSURE_STRATEGY, Integer.valueOf(paramInt))).intValue();
  }
  
  public CameraSelector getCameraSelector()
  {
    return (CameraSelector)retrieveOption(OPTION_CAMERA_SELECTOR);
  }
  
  public CameraSelector getCameraSelector(CameraSelector paramCameraSelector)
  {
    return (CameraSelector)retrieveOption(OPTION_CAMERA_SELECTOR, paramCameraSelector);
  }
  
  public CaptureConfig.OptionUnpacker getCaptureOptionUnpacker()
  {
    return (CaptureConfig.OptionUnpacker)retrieveOption(OPTION_CAPTURE_CONFIG_UNPACKER);
  }
  
  public CaptureConfig.OptionUnpacker getCaptureOptionUnpacker(CaptureConfig.OptionUnpacker paramOptionUnpacker)
  {
    return (CaptureConfig.OptionUnpacker)retrieveOption(OPTION_CAPTURE_CONFIG_UNPACKER, paramOptionUnpacker);
  }
  
  public CaptureConfig getDefaultCaptureConfig()
  {
    return (CaptureConfig)retrieveOption(OPTION_DEFAULT_CAPTURE_CONFIG);
  }
  
  public CaptureConfig getDefaultCaptureConfig(CaptureConfig paramCaptureConfig)
  {
    return (CaptureConfig)retrieveOption(OPTION_DEFAULT_CAPTURE_CONFIG, paramCaptureConfig);
  }
  
  public Size getDefaultResolution()
  {
    return (Size)retrieveOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION);
  }
  
  public Size getDefaultResolution(Size paramSize)
  {
    return (Size)retrieveOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION, paramSize);
  }
  
  public SessionConfig getDefaultSessionConfig()
  {
    return (SessionConfig)retrieveOption(OPTION_DEFAULT_SESSION_CONFIG);
  }
  
  public SessionConfig getDefaultSessionConfig(SessionConfig paramSessionConfig)
  {
    return (SessionConfig)retrieveOption(OPTION_DEFAULT_SESSION_CONFIG, paramSessionConfig);
  }
  
  public int getImageQueueDepth()
  {
    return ((Integer)retrieveOption(OPTION_IMAGE_QUEUE_DEPTH)).intValue();
  }
  
  public int getImageQueueDepth(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_IMAGE_QUEUE_DEPTH, Integer.valueOf(paramInt))).intValue();
  }
  
  public Size getMaxResolution()
  {
    return (Size)retrieveOption(OPTION_MAX_RESOLUTION);
  }
  
  public Size getMaxResolution(Size paramSize)
  {
    return (Size)retrieveOption(OPTION_MAX_RESOLUTION, paramSize);
  }
  
  public SessionConfig.OptionUnpacker getSessionOptionUnpacker()
  {
    return (SessionConfig.OptionUnpacker)retrieveOption(OPTION_SESSION_CONFIG_UNPACKER);
  }
  
  public SessionConfig.OptionUnpacker getSessionOptionUnpacker(SessionConfig.OptionUnpacker paramOptionUnpacker)
  {
    return (SessionConfig.OptionUnpacker)retrieveOption(OPTION_SESSION_CONFIG_UNPACKER, paramOptionUnpacker);
  }
  
  public List<Pair<Integer, Size[]>> getSupportedResolutions()
  {
    return (List)retrieveOption(OPTION_SUPPORTED_RESOLUTIONS);
  }
  
  public List<Pair<Integer, Size[]>> getSupportedResolutions(List<Pair<Integer, Size[]>> paramList)
  {
    return (List)retrieveOption(OPTION_SUPPORTED_RESOLUTIONS, paramList);
  }
  
  public int getSurfaceOccupancyPriority()
  {
    return ((Integer)retrieveOption(OPTION_SURFACE_OCCUPANCY_PRIORITY)).intValue();
  }
  
  public int getSurfaceOccupancyPriority(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(paramInt))).intValue();
  }
  
  public int getTargetAspectRatio()
  {
    return ((Integer)retrieveOption(OPTION_TARGET_ASPECT_RATIO)).intValue();
  }
  
  public Rational getTargetAspectRatioCustom()
  {
    return (Rational)retrieveOption(OPTION_TARGET_ASPECT_RATIO_CUSTOM);
  }
  
  public Rational getTargetAspectRatioCustom(Rational paramRational)
  {
    return (Rational)retrieveOption(OPTION_TARGET_ASPECT_RATIO_CUSTOM, paramRational);
  }
  
  public Class<ImageAnalysis> getTargetClass()
  {
    return (Class)retrieveOption(OPTION_TARGET_CLASS);
  }
  
  public Class<ImageAnalysis> getTargetClass(Class<ImageAnalysis> paramClass)
  {
    return (Class)retrieveOption(OPTION_TARGET_CLASS, paramClass);
  }
  
  public String getTargetName()
  {
    return (String)retrieveOption(OPTION_TARGET_NAME);
  }
  
  public String getTargetName(String paramString)
  {
    return (String)retrieveOption(OPTION_TARGET_NAME, paramString);
  }
  
  public Size getTargetResolution()
  {
    return (Size)retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION);
  }
  
  public Size getTargetResolution(Size paramSize)
  {
    return (Size)retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, paramSize);
  }
  
  public int getTargetRotation()
  {
    return ((Integer)retrieveOption(OPTION_TARGET_ROTATION)).intValue();
  }
  
  public int getTargetRotation(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_TARGET_ROTATION, Integer.valueOf(paramInt))).intValue();
  }
  
  public UseCase.EventCallback getUseCaseEventCallback()
  {
    return (UseCase.EventCallback)retrieveOption(OPTION_USE_CASE_EVENT_CALLBACK);
  }
  
  public UseCase.EventCallback getUseCaseEventCallback(UseCase.EventCallback paramEventCallback)
  {
    return (UseCase.EventCallback)retrieveOption(OPTION_USE_CASE_EVENT_CALLBACK, paramEventCallback);
  }
  
  public boolean hasTargetAspectRatio()
  {
    return containsOption(OPTION_TARGET_ASPECT_RATIO);
  }
  
  public Set<Config.Option<?>> listOptions()
  {
    return this.mConfig.listOptions();
  }
  
  public <ValueT> ValueT retrieveOption(Config.Option<ValueT> paramOption)
  {
    return this.mConfig.retrieveOption(paramOption);
  }
  
  public <ValueT> ValueT retrieveOption(Config.Option<ValueT> paramOption, ValueT paramValueT)
  {
    return this.mConfig.retrieveOption(paramOption, paramValueT);
  }
}
