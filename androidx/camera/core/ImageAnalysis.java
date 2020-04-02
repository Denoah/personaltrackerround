package androidx.camera.core;

import android.util.Log;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureConfig.OptionUnpacker;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageAnalysisConfig;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.ImageOutputConfig.Builder;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.SessionConfig.Builder;
import androidx.camera.core.impl.SessionConfig.ErrorListener;
import androidx.camera.core.impl.SessionConfig.OptionUnpacker;
import androidx.camera.core.impl.SessionConfig.SessionError;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfig.Builder;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.core.internal.ThreadConfig.Builder;
import androidx.camera.core.internal.utils.UseCaseConfigUtil;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;

public final class ImageAnalysis
  extends UseCase
{
  public static final Defaults DEFAULT_CONFIG = new Defaults();
  private static final int NON_BLOCKING_IMAGE_DEPTH = 4;
  public static final int STRATEGY_BLOCK_PRODUCER = 1;
  public static final int STRATEGY_KEEP_ONLY_LATEST = 0;
  private static final String TAG = "ImageAnalysis";
  private final Object mAnalysisLock = new Object();
  private DeferrableSurface mDeferrableSurface;
  final ImageAnalysisAbstractAnalyzer mImageAnalysisAbstractAnalyzer;
  private Analyzer mSubscribedAnalyzer;
  
  ImageAnalysis(ImageAnalysisConfig paramImageAnalysisConfig)
  {
    super(paramImageAnalysisConfig);
    ImageAnalysisConfig localImageAnalysisConfig = (ImageAnalysisConfig)getUseCaseConfig();
    setImageFormat(ImageReaderFormatRecommender.chooseCombo().imageAnalysisFormat());
    if (localImageAnalysisConfig.getBackpressureStrategy() == 1) {
      this.mImageAnalysisAbstractAnalyzer = new ImageAnalysisBlockingAnalyzer();
    } else {
      this.mImageAnalysisAbstractAnalyzer = new ImageAnalysisNonBlockingAnalyzer(paramImageAnalysisConfig.getBackgroundExecutor(CameraXExecutors.highPriorityExecutor()));
    }
  }
  
  private void tryUpdateRelativeRotation()
  {
    ImageOutputConfig localImageOutputConfig = (ImageOutputConfig)getUseCaseConfig();
    CameraInfoInternal localCameraInfoInternal = getBoundCamera().getCameraInfoInternal();
    this.mImageAnalysisAbstractAnalyzer.setRelativeRotation(localCameraInfoInternal.getSensorRotationDegrees(localImageOutputConfig.getTargetRotation(0)));
  }
  
  public void clear()
  {
    clearPipeline();
    super.clear();
  }
  
  public void clearAnalyzer()
  {
    synchronized (this.mAnalysisLock)
    {
      this.mImageAnalysisAbstractAnalyzer.setAnalyzer(null, null);
      if (this.mSubscribedAnalyzer != null) {
        notifyInactive();
      }
      this.mSubscribedAnalyzer = null;
      return;
    }
  }
  
  void clearPipeline()
  {
    Threads.checkMainThread();
    this.mImageAnalysisAbstractAnalyzer.close();
    DeferrableSurface localDeferrableSurface = this.mDeferrableSurface;
    if (localDeferrableSurface != null)
    {
      localDeferrableSurface.close();
      this.mDeferrableSurface = null;
    }
  }
  
  SessionConfig.Builder createPipeline(final String paramString, final ImageAnalysisConfig paramImageAnalysisConfig, final Size paramSize)
  {
    Threads.checkMainThread();
    Object localObject1 = (Executor)Preconditions.checkNotNull(paramImageAnalysisConfig.getBackgroundExecutor(CameraXExecutors.highPriorityExecutor()));
    int i;
    if (paramImageAnalysisConfig.getBackpressureStrategy() == 1) {
      i = paramImageAnalysisConfig.getImageQueueDepth();
    } else {
      i = 4;
    }
    ImageReaderProxy localImageReaderProxy = ImageReaderProxys.createIsolatedReader(paramSize.getWidth(), paramSize.getHeight(), getImageFormat(), i);
    tryUpdateRelativeRotation();
    this.mImageAnalysisAbstractAnalyzer.open();
    localImageReaderProxy.setOnImageAvailableListener(this.mImageAnalysisAbstractAnalyzer, (Executor)localObject1);
    localObject1 = SessionConfig.Builder.createFrom(paramImageAnalysisConfig);
    Object localObject2 = this.mDeferrableSurface;
    if (localObject2 != null) {
      ((DeferrableSurface)localObject2).close();
    }
    localObject2 = new ImmediateSurface(localImageReaderProxy.getSurface());
    this.mDeferrableSurface = ((DeferrableSurface)localObject2);
    localObject2 = ((DeferrableSurface)localObject2).getTerminationFuture();
    localImageReaderProxy.getClass();
    ((ListenableFuture)localObject2).addListener(new _..Lambda.NNk8NDCoxfY3Ku9v43wIWJ59jao(localImageReaderProxy), CameraXExecutors.mainThreadExecutor());
    ((SessionConfig.Builder)localObject1).addSurface(this.mDeferrableSurface);
    ((SessionConfig.Builder)localObject1).addErrorListener(new SessionConfig.ErrorListener()
    {
      public void onError(SessionConfig paramAnonymousSessionConfig, SessionConfig.SessionError paramAnonymousSessionError)
      {
        ImageAnalysis.this.clearPipeline();
        if (ImageAnalysis.this.isCurrentlyBoundCamera(paramString))
        {
          paramAnonymousSessionConfig = ImageAnalysis.this.createPipeline(paramString, paramImageAnalysisConfig, paramSize);
          ImageAnalysis.this.attachToCamera(paramString, paramAnonymousSessionConfig.build());
          ImageAnalysis.this.notifyReset();
        }
      }
    });
    return localObject1;
  }
  
  public int getBackpressureStrategy()
  {
    return ((ImageAnalysisConfig)getUseCaseConfig()).getBackpressureStrategy();
  }
  
  protected UseCaseConfig.Builder<?, ?, ?> getDefaultBuilder(CameraInfo paramCameraInfo)
  {
    paramCameraInfo = (ImageAnalysisConfig)CameraX.getDefaultUseCaseConfig(ImageAnalysisConfig.class, paramCameraInfo);
    if (paramCameraInfo != null) {
      return Builder.fromConfig(paramCameraInfo);
    }
    return null;
  }
  
  public int getImageQueueDepth()
  {
    return ((ImageAnalysisConfig)getUseCaseConfig()).getImageQueueDepth();
  }
  
  public int getTargetRotation()
  {
    return ((ImageAnalysisConfig)getUseCaseConfig()).getTargetRotation();
  }
  
  protected Map<String, Size> onSuggestedResolutionUpdated(Map<String, Size> paramMap)
  {
    ImageAnalysisConfig localImageAnalysisConfig = (ImageAnalysisConfig)getUseCaseConfig();
    String str = getBoundCameraId();
    Size localSize = (Size)paramMap.get(str);
    if (localSize != null)
    {
      attachToCamera(str, createPipeline(str, localImageAnalysisConfig, localSize).build());
      return paramMap;
    }
    paramMap = new StringBuilder();
    paramMap.append("Suggested resolution map missing resolution for camera ");
    paramMap.append(str);
    throw new IllegalArgumentException(paramMap.toString());
  }
  
  public void setAnalyzer(Executor paramExecutor, Analyzer paramAnalyzer)
  {
    synchronized (this.mAnalysisLock)
    {
      this.mImageAnalysisAbstractAnalyzer.setAnalyzer(paramExecutor, paramAnalyzer);
      if (this.mSubscribedAnalyzer == null) {
        notifyActive();
      }
      this.mSubscribedAnalyzer = paramAnalyzer;
      return;
    }
  }
  
  public void setTargetRotation(int paramInt)
  {
    ImageAnalysisConfig localImageAnalysisConfig = (ImageAnalysisConfig)getUseCaseConfig();
    Builder localBuilder = Builder.fromConfig(localImageAnalysisConfig);
    int i = localImageAnalysisConfig.getTargetRotation(-1);
    if ((i == -1) || (i != paramInt))
    {
      UseCaseConfigUtil.updateTargetRotationAndRelatedConfigs(localBuilder, paramInt);
      updateUseCaseConfig(localBuilder.getUseCaseConfig());
      try
      {
        tryUpdateRelativeRotation();
      }
      catch (Exception localException)
      {
        Log.w("ImageAnalysis", "Unable to get camera id for the use case.");
      }
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ImageAnalysis:");
    localStringBuilder.append(getName());
    return localStringBuilder.toString();
  }
  
  public static abstract interface Analyzer
  {
    public abstract void analyze(ImageProxy paramImageProxy);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface BackpressureStrategy {}
  
  public static final class Builder
    implements ImageOutputConfig.Builder<Builder>, ThreadConfig.Builder<Builder>, UseCaseConfig.Builder<ImageAnalysis, ImageAnalysisConfig, Builder>
  {
    private final MutableOptionsBundle mMutableConfig;
    
    public Builder()
    {
      this(MutableOptionsBundle.create());
    }
    
    private Builder(MutableOptionsBundle paramMutableOptionsBundle)
    {
      this.mMutableConfig = paramMutableOptionsBundle;
      paramMutableOptionsBundle = (Class)paramMutableOptionsBundle.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
      if ((paramMutableOptionsBundle != null) && (!paramMutableOptionsBundle.equals(ImageAnalysis.class)))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Invalid target class configuration for ");
        localStringBuilder.append(this);
        localStringBuilder.append(": ");
        localStringBuilder.append(paramMutableOptionsBundle);
        throw new IllegalArgumentException(localStringBuilder.toString());
      }
      setTargetClass(ImageAnalysis.class);
    }
    
    public static Builder fromConfig(ImageAnalysisConfig paramImageAnalysisConfig)
    {
      return new Builder(MutableOptionsBundle.from(paramImageAnalysisConfig));
    }
    
    public ImageAnalysis build()
    {
      if ((getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, null) != null) && (getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, null) != null)) {
        throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
      }
      return new ImageAnalysis(getUseCaseConfig());
    }
    
    public MutableConfig getMutableConfig()
    {
      return this.mMutableConfig;
    }
    
    public ImageAnalysisConfig getUseCaseConfig()
    {
      return new ImageAnalysisConfig(OptionsBundle.from(this.mMutableConfig));
    }
    
    public Builder setBackgroundExecutor(Executor paramExecutor)
    {
      getMutableConfig().insertOption(ThreadConfig.OPTION_BACKGROUND_EXECUTOR, paramExecutor);
      return this;
    }
    
    public Builder setBackpressureStrategy(int paramInt)
    {
      getMutableConfig().insertOption(ImageAnalysisConfig.OPTION_BACKPRESSURE_STRATEGY, Integer.valueOf(paramInt));
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
      return this;
    }
    
    public Builder setDefaultSessionConfig(SessionConfig paramSessionConfig)
    {
      getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG, paramSessionConfig);
      return this;
    }
    
    public Builder setImageQueueDepth(int paramInt)
    {
      getMutableConfig().insertOption(ImageAnalysisConfig.OPTION_IMAGE_QUEUE_DEPTH, Integer.valueOf(paramInt));
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
    
    public Builder setTargetClass(Class<ImageAnalysis> paramClass)
    {
      getMutableConfig().insertOption(UseCaseConfig.OPTION_TARGET_CLASS, paramClass);
      if (getMutableConfig().retrieveOption(UseCaseConfig.OPTION_TARGET_NAME, null) == null)
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
      getMutableConfig().insertOption(UseCaseConfig.OPTION_TARGET_NAME, paramString);
      return this;
    }
    
    public Builder setTargetResolution(Size paramSize)
    {
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, paramSize);
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, new Rational(paramSize.getWidth(), paramSize.getHeight()));
      return this;
    }
    
    public Builder setTargetRotation(int paramInt)
    {
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ROTATION, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setUseCaseEventCallback(UseCase.EventCallback paramEventCallback)
    {
      getMutableConfig().insertOption(UseCaseConfig.OPTION_USE_CASE_EVENT_CALLBACK, paramEventCallback);
      return this;
    }
  }
  
  public static final class Defaults
    implements ConfigProvider<ImageAnalysisConfig>
  {
    private static final int DEFAULT_BACKPRESSURE_STRATEGY = 0;
    private static final ImageAnalysisConfig DEFAULT_CONFIG = new ImageAnalysis.Builder().setBackpressureStrategy(0).setImageQueueDepth(6).setDefaultResolution(DEFAULT_TARGET_RESOLUTION).setMaxResolution(DEFAULT_MAX_RESOLUTION).setSurfaceOccupancyPriority(1).getUseCaseConfig();
    private static final int DEFAULT_IMAGE_QUEUE_DEPTH = 6;
    private static final Size DEFAULT_MAX_RESOLUTION;
    private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 1;
    private static final Size DEFAULT_TARGET_RESOLUTION = new Size(640, 480);
    
    static
    {
      DEFAULT_MAX_RESOLUTION = new Size(1920, 1080);
    }
    
    public Defaults() {}
    
    public ImageAnalysisConfig getConfig(CameraInfo paramCameraInfo)
    {
      return DEFAULT_CONFIG;
    }
  }
}
