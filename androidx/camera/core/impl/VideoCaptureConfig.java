package androidx.camera.core.impl;

import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.UseCase.EventCallback;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.core.internal.ThreadConfig.Builder;
import androidx.camera.core.internal.UseCaseEventConfig;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;

public final class VideoCaptureConfig
  implements UseCaseConfig<VideoCapture>, ImageOutputConfig, ThreadConfig
{
  static final Config.Option<Integer> OPTION_AUDIO_BIT_RATE;
  static final Config.Option<Integer> OPTION_AUDIO_CHANNEL_COUNT;
  static final Config.Option<Integer> OPTION_AUDIO_MIN_BUFFER_SIZE = Config.Option.create("camerax.core.videoCapture.audioMinBufferSize", Integer.TYPE);
  static final Config.Option<Integer> OPTION_AUDIO_RECORD_SOURCE;
  static final Config.Option<Integer> OPTION_AUDIO_SAMPLE_RATE;
  static final Config.Option<Integer> OPTION_BIT_RATE;
  static final Config.Option<Integer> OPTION_INTRA_FRAME_INTERVAL;
  static final Config.Option<Integer> OPTION_VIDEO_FRAME_RATE = Config.Option.create("camerax.core.videoCapture.recordingFrameRate", Integer.TYPE);
  private final OptionsBundle mConfig;
  
  static
  {
    OPTION_BIT_RATE = Config.Option.create("camerax.core.videoCapture.bitRate", Integer.TYPE);
    OPTION_INTRA_FRAME_INTERVAL = Config.Option.create("camerax.core.videoCapture.intraFrameInterval", Integer.TYPE);
    OPTION_AUDIO_BIT_RATE = Config.Option.create("camerax.core.videoCapture.audioBitRate", Integer.TYPE);
    OPTION_AUDIO_SAMPLE_RATE = Config.Option.create("camerax.core.videoCapture.audioSampleRate", Integer.TYPE);
    OPTION_AUDIO_CHANNEL_COUNT = Config.Option.create("camerax.core.videoCapture.audioChannelCount", Integer.TYPE);
    OPTION_AUDIO_RECORD_SOURCE = Config.Option.create("camerax.core.videoCapture.audioRecordSource", Integer.TYPE);
  }
  
  VideoCaptureConfig(OptionsBundle paramOptionsBundle)
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
  
  public int getAudioBitRate()
  {
    return ((Integer)retrieveOption(OPTION_AUDIO_BIT_RATE)).intValue();
  }
  
  public int getAudioBitRate(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_AUDIO_BIT_RATE, Integer.valueOf(paramInt))).intValue();
  }
  
  public int getAudioChannelCount()
  {
    return ((Integer)retrieveOption(OPTION_AUDIO_CHANNEL_COUNT)).intValue();
  }
  
  public int getAudioChannelCount(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_AUDIO_CHANNEL_COUNT, Integer.valueOf(paramInt))).intValue();
  }
  
  public int getAudioMinBufferSize()
  {
    return ((Integer)retrieveOption(OPTION_AUDIO_MIN_BUFFER_SIZE)).intValue();
  }
  
  public int getAudioMinBufferSize(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_AUDIO_MIN_BUFFER_SIZE, Integer.valueOf(paramInt))).intValue();
  }
  
  public int getAudioRecordSource()
  {
    return ((Integer)retrieveOption(OPTION_AUDIO_RECORD_SOURCE)).intValue();
  }
  
  public int getAudioRecordSource(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_AUDIO_RECORD_SOURCE, Integer.valueOf(paramInt))).intValue();
  }
  
  public int getAudioSampleRate()
  {
    return ((Integer)retrieveOption(OPTION_AUDIO_SAMPLE_RATE)).intValue();
  }
  
  public int getAudioSampleRate(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_AUDIO_SAMPLE_RATE, Integer.valueOf(paramInt))).intValue();
  }
  
  public Executor getBackgroundExecutor()
  {
    return (Executor)retrieveOption(OPTION_BACKGROUND_EXECUTOR);
  }
  
  public Executor getBackgroundExecutor(Executor paramExecutor)
  {
    return (Executor)retrieveOption(OPTION_BACKGROUND_EXECUTOR, paramExecutor);
  }
  
  public int getBitRate()
  {
    return ((Integer)retrieveOption(OPTION_BIT_RATE)).intValue();
  }
  
  public int getBitRate(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_BIT_RATE, Integer.valueOf(paramInt))).intValue();
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
  
  public int getIFrameInterval()
  {
    return ((Integer)retrieveOption(OPTION_INTRA_FRAME_INTERVAL)).intValue();
  }
  
  public int getIFrameInterval(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_INTRA_FRAME_INTERVAL, Integer.valueOf(paramInt))).intValue();
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
  
  public Class<VideoCapture> getTargetClass()
  {
    return (Class)retrieveOption(OPTION_TARGET_CLASS);
  }
  
  public Class<VideoCapture> getTargetClass(Class<VideoCapture> paramClass)
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
  
  public int getVideoFrameRate()
  {
    return ((Integer)retrieveOption(OPTION_VIDEO_FRAME_RATE)).intValue();
  }
  
  public int getVideoFrameRate(int paramInt)
  {
    return ((Integer)retrieveOption(OPTION_VIDEO_FRAME_RATE, Integer.valueOf(paramInt))).intValue();
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
  
  public static final class Builder
    implements UseCaseConfig.Builder<VideoCapture, VideoCaptureConfig, Builder>, ImageOutputConfig.Builder<Builder>, ThreadConfig.Builder<Builder>
  {
    private final MutableOptionsBundle mMutableConfig;
    
    public Builder()
    {
      this(MutableOptionsBundle.create());
    }
    
    private Builder(MutableOptionsBundle paramMutableOptionsBundle)
    {
      this.mMutableConfig = paramMutableOptionsBundle;
      Class localClass = (Class)paramMutableOptionsBundle.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
      if ((localClass != null) && (!localClass.equals(VideoCapture.class)))
      {
        paramMutableOptionsBundle = new StringBuilder();
        paramMutableOptionsBundle.append("Invalid target class configuration for ");
        paramMutableOptionsBundle.append(this);
        paramMutableOptionsBundle.append(": ");
        paramMutableOptionsBundle.append(localClass);
        throw new IllegalArgumentException(paramMutableOptionsBundle.toString());
      }
      setTargetClass(VideoCapture.class);
    }
    
    public static Builder fromConfig(VideoCaptureConfig paramVideoCaptureConfig)
    {
      return new Builder(MutableOptionsBundle.from(paramVideoCaptureConfig));
    }
    
    public VideoCapture build()
    {
      if ((getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, null) != null) && (getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, null) != null)) {
        throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
      }
      return new VideoCapture(getUseCaseConfig());
    }
    
    public MutableConfig getMutableConfig()
    {
      return this.mMutableConfig;
    }
    
    public VideoCaptureConfig getUseCaseConfig()
    {
      return new VideoCaptureConfig(OptionsBundle.from(this.mMutableConfig));
    }
    
    public Builder setAudioBitRate(int paramInt)
    {
      getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_BIT_RATE, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setAudioChannelCount(int paramInt)
    {
      getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_CHANNEL_COUNT, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setAudioMinBufferSize(int paramInt)
    {
      getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_MIN_BUFFER_SIZE, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setAudioRecordSource(int paramInt)
    {
      getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_RECORD_SOURCE, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setAudioSampleRate(int paramInt)
    {
      getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_SAMPLE_RATE, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setBackgroundExecutor(Executor paramExecutor)
    {
      getMutableConfig().insertOption(ThreadConfig.OPTION_BACKGROUND_EXECUTOR, paramExecutor);
      return this;
    }
    
    public Builder setBitRate(int paramInt)
    {
      getMutableConfig().insertOption(VideoCaptureConfig.OPTION_BIT_RATE, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setCameraSelector(CameraSelector paramCameraSelector)
    {
      getMutableConfig().insertOption(UseCaseConfig.OPTION_CAMERA_SELECTOR, paramCameraSelector);
      return this;
    }
    
    public Builder setCaptureOptionUnpacker(CaptureConfig.OptionUnpacker paramOptionUnpacker)
    {
      getMutableConfig().insertOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER, paramOptionUnpacker);
      return this;
    }
    
    public Builder setDefaultCaptureConfig(CaptureConfig paramCaptureConfig)
    {
      getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG, paramCaptureConfig);
      return this;
    }
    
    public Builder setDefaultResolution(Size paramSize)
    {
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION, paramSize);
      return null;
    }
    
    public Builder setDefaultSessionConfig(SessionConfig paramSessionConfig)
    {
      getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG, paramSessionConfig);
      return this;
    }
    
    public Builder setIFrameInterval(int paramInt)
    {
      getMutableConfig().insertOption(VideoCaptureConfig.OPTION_INTRA_FRAME_INTERVAL, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setMaxResolution(Size paramSize)
    {
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_MAX_RESOLUTION, paramSize);
      return this;
    }
    
    public Builder setSessionOptionUnpacker(SessionConfig.OptionUnpacker paramOptionUnpacker)
    {
      getMutableConfig().insertOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER, paramOptionUnpacker);
      return this;
    }
    
    public Builder setSupportedResolutions(List<Pair<Integer, Size[]>> paramList)
    {
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS, paramList);
      return this;
    }
    
    public Builder setSurfaceOccupancyPriority(int paramInt)
    {
      getMutableConfig().insertOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setTargetAspectRatio(int paramInt)
    {
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setTargetAspectRatioCustom(Rational paramRational)
    {
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, paramRational);
      getMutableConfig().removeOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO);
      return this;
    }
    
    public Builder setTargetClass(Class<VideoCapture> paramClass)
    {
      getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_CLASS, paramClass);
      if (getMutableConfig().retrieveOption(TargetConfig.OPTION_TARGET_NAME, null) == null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramClass.getCanonicalName());
        localStringBuilder.append("-");
        localStringBuilder.append(UUID.randomUUID());
        setTargetName(localStringBuilder.toString());
      }
      return this;
    }
    
    public Builder setTargetName(String paramString)
    {
      getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_NAME, paramString);
      return this;
    }
    
    public Builder setTargetResolution(Size paramSize)
    {
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, paramSize);
      if (paramSize != null) {
        getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, new Rational(paramSize.getWidth(), paramSize.getHeight()));
      }
      return this;
    }
    
    public Builder setTargetRotation(int paramInt)
    {
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ROTATION, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setUseCaseEventCallback(UseCase.EventCallback paramEventCallback)
    {
      getMutableConfig().insertOption(UseCaseEventConfig.OPTION_USE_CASE_EVENT_CALLBACK, paramEventCallback);
      return this;
    }
    
    public Builder setVideoFrameRate(int paramInt)
    {
      getMutableConfig().insertOption(VideoCaptureConfig.OPTION_VIDEO_FRAME_RATE, Integer.valueOf(paramInt));
      return this;
    }
  }
}
