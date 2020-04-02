package androidx.camera.core.impl;

import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.UseCase.EventCallback;
import androidx.camera.core.internal.IoConfig;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public final class ImageCaptureConfig
  implements UseCaseConfig<ImageCapture>, ImageOutputConfig, IoConfig
{
  public static final Config.Option<Integer> OPTION_BUFFER_FORMAT = Config.Option.create("camerax.core.imageCapture.bufferFormat", Integer.class);
  public static final Config.Option<CaptureBundle> OPTION_CAPTURE_BUNDLE;
  public static final Config.Option<CaptureProcessor> OPTION_CAPTURE_PROCESSOR;
  public static final Config.Option<Integer> OPTION_FLASH_MODE;
  public static final Config.Option<Integer> OPTION_IMAGE_CAPTURE_MODE = Config.Option.create("camerax.core.imageCapture.captureMode", Integer.TYPE);
  public static final Config.Option<Integer> OPTION_MAX_CAPTURE_STAGES = Config.Option.create("camerax.core.imageCapture.maxCaptureStages", Integer.class);
  private final OptionsBundle mConfig;
  
  static
  {
    OPTION_FLASH_MODE = Config.Option.create("camerax.core.imageCapture.flashMode", Integer.TYPE);
    OPTION_CAPTURE_BUNDLE = Config.Option.create("camerax.core.imageCapture.captureBundle", CaptureBundle.class);
    OPTION_CAPTURE_PROCESSOR = Config.Option.create("camerax.core.imageCapture.captureProcessor", CaptureProcessor.class);
  }
  
  public ImageCaptureConfig(OptionsBundle paramOptionsBundle)
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
  
  public Integer getBufferFormat()
  {
    return (Integer)retrieveOption(OPTION_BUFFER_FORMAT);
  }
  
  public Integer getBufferFormat(Integer paramInteger)
  {
    return (Integer)retrieveOption(OPTION_BUFFER_FORMAT, paramInteger);
  }
  
  public CameraSelector getCameraSelector()
  {
    return (CameraSelector)retrieveOption(OPTION_CAMERA_SELECTOR);
  }
  
  public CameraSelector getCameraSelector(CameraSelector paramCameraSelector)
  {
    return (CameraSelector)retrieveOption(OPTION_CAMERA_SELECTOR, paramCameraSelector);
  }
  
  public CaptureBundle getCaptureBundle()
  {
    return (CaptureBundle)retrieveOption(OPTION_CAPTURE_BUNDLE);
  }
  
  public CaptureBundle getCaptureBundle(CaptureBundle paramCaptureBundle)
  {
    return (CaptureBundle)retrieveOption(OPTION_CAPTURE_BUNDLE, paramCaptureBundle);
  }
  
  public int getCaptureMode()
  {
    return ((Integer)retrieveOption(OPTION_IMAGE_CAPTURE_MODE)).intValue();
  }
  
  public CaptureConfig.OptionUnpacker getCaptureOptionUnpacker()
  {
    return (CaptureConfig.OptionUnpacker)retrieveOption(OPTION_CAPTURE_CONFIG_UNPACKER);
  }
  
  public CaptureConfig.OptionUnpacker getCaptureOptionUnpacker(CaptureConfig.OptionUnpacker paramOptionUnpacker)
  {
    return (CaptureConfig.OptionUnpacker)retrieveOption(OPTION_CAPTURE_CONFIG_UNPACKER, paramOptionUnpacker);
  }
  
  public CaptureProcessor getCaptureProcessor()
  {
    return (CaptureProcessor)retrieveOption(OPTION_CAPTURE_PROCESSOR);
  }
  
  public CaptureProcessor getCaptureProcessor(CaptureProcessor paramCaptureProcessor)
  {
    return (CaptureProcessor)retrieveOption(OPTION_CAPTURE_PROCESSOR, paramCaptureProcessor);
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
    return (Size)retrieveOption(OPTION_DEFAULT_RESOLUTION);
  }
  
  public Size getDefaultResolution(Size paramSize)
  {
    return (Size)retrieveOption(OPTION_DEFAULT_RESOLUTION, paramSize);
  }
  
  public SessionConfig getDefaultSessionConfig()
  {
    return (SessionConfig)retrieveOption(OPTION_DEFAULT_SESSION_CONFIG);
  }
  
  public SessionConfig getDefaultSessionConfig(SessionConfig paramSessionConfig)
  {
    return (SessionConfig)retrieveOption(OPTION_DEFAULT_SESSION_CONFIG, paramSessionConfig);
  }
  
  public int getFlashMode()
  {
    return ((Integer)retrieveOption(OPTION_FLASH_MODE)).intValue();
  }
  
  public Executor getIoExecutor()
  {
    return (Executor)retrieveOption(OPTION_IO_EXECUTOR);
  }
  
  public Executor getIoExecutor(Executor paramExecutor)
  {
    return (Executor)retrieveOption(OPTION_IO_EXECUTOR, paramExecutor);
  }
  
  public int getMaxCaptureStages()
  {
    return ((Integer)retrieveOption(OPTION_MAX_CAPTURE_STAGES)).intValue();
  }
  
  public int getMaxCaptureStages(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_MAX_CAPTURE_STAGES, Integer.valueOf(paramInt))).intValue();
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
  
  public Class<ImageCapture> getTargetClass()
  {
    return (Class)retrieveOption(OPTION_TARGET_CLASS);
  }
  
  public Class<ImageCapture> getTargetClass(Class<ImageCapture> paramClass)
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
  
  public boolean hasCaptureMode()
  {
    return containsOption(OPTION_IMAGE_CAPTURE_MODE);
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
