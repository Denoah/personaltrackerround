package androidx.camera.core;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureConfig.OptionUnpacker;
import androidx.camera.core.impl.CaptureProcessor;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.CaptureStage.DefaultCaptureStage;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageInfoProcessor;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.ImageOutputConfig.Builder;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.SessionConfig.Builder;
import androidx.camera.core.impl.SessionConfig.ErrorListener;
import androidx.camera.core.impl.SessionConfig.OptionUnpacker;
import androidx.camera.core.impl.SessionConfig.SessionError;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfig.Builder;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.CameraCaptureResultImageInfo;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.ThreadConfig.Builder;
import androidx.core.util.Preconditions;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;

public final class Preview
  extends UseCase
{
  public static final Defaults DEFAULT_CONFIG = new Defaults();
  private static final String TAG = "Preview";
  private Size mLatestResolution;
  Executor mPreviewSurfaceProviderExecutor;
  private Handler mProcessingPreviewHandler;
  private HandlerThread mProcessingPreviewThread;
  private DeferrableSurface mSessionDeferrableSurface;
  SurfaceProvider mSurfaceProvider;
  
  Preview(PreviewConfig paramPreviewConfig)
  {
    super(paramPreviewConfig);
  }
  
  private void updateConfigAndOutput(String paramString, PreviewConfig paramPreviewConfig, Size paramSize)
  {
    Preconditions.checkState(isPreviewSurfaceProviderSet());
    attachToCamera(paramString, createPipeline(paramString, paramPreviewConfig, paramSize).build());
  }
  
  protected UseCaseConfig<?> applyDefaults(UseCaseConfig<?> paramUseCaseConfig, UseCaseConfig.Builder<?, ?, ?> paramBuilder)
  {
    paramBuilder = (PreviewConfig)super.applyDefaults(paramUseCaseConfig, paramBuilder);
    Object localObject = getBoundCamera();
    paramUseCaseConfig = paramBuilder;
    if (localObject != null)
    {
      paramUseCaseConfig = paramBuilder;
      if (CameraX.getSurfaceManager().requiresCorrectedAspectRatio(((CameraInternal)localObject).getCameraInfoInternal().getCameraId()))
      {
        localObject = CameraX.getSurfaceManager().getCorrectedAspectRatio(((CameraInternal)localObject).getCameraInfoInternal().getCameraId(), paramBuilder.getTargetRotation(0));
        paramUseCaseConfig = paramBuilder;
        if (localObject != null)
        {
          paramUseCaseConfig = Builder.fromConfig(paramBuilder);
          paramUseCaseConfig.setTargetAspectRatioCustom((Rational)localObject);
          paramUseCaseConfig = paramUseCaseConfig.getUseCaseConfig();
        }
      }
    }
    return paramUseCaseConfig;
  }
  
  public void clear()
  {
    notifyInactive();
    DeferrableSurface localDeferrableSurface = this.mSessionDeferrableSurface;
    if (localDeferrableSurface != null) {
      localDeferrableSurface.close();
    }
    super.clear();
  }
  
  SessionConfig.Builder createPipeline(final String paramString, final PreviewConfig paramPreviewConfig, final Size paramSize)
  {
    Threads.checkMainThread();
    Preconditions.checkState(isPreviewSurfaceProviderSet());
    SessionConfig.Builder localBuilder = SessionConfig.Builder.createFrom(paramPreviewConfig);
    CaptureProcessor localCaptureProcessor = paramPreviewConfig.getCaptureProcessor(null);
    Object localObject1 = new SurfaceRequest(paramSize);
    this.mPreviewSurfaceProviderExecutor.execute(new _..Lambda.Preview.TapRxcAo4pDHsGfM4Thz6iica6w(this, (SurfaceRequest)localObject1));
    Object localObject2;
    if (localCaptureProcessor != null)
    {
      localObject2 = new CaptureStage.DefaultCaptureStage();
      if (this.mProcessingPreviewHandler == null)
      {
        HandlerThread localHandlerThread = new HandlerThread("ProcessingSurfaceTexture");
        this.mProcessingPreviewThread = localHandlerThread;
        localHandlerThread.start();
        this.mProcessingPreviewHandler = new Handler(this.mProcessingPreviewThread.getLooper());
      }
      localObject1 = new ProcessingSurface(paramSize.getWidth(), paramSize.getHeight(), 35, this.mProcessingPreviewHandler, (CaptureStage)localObject2, localCaptureProcessor, ((SurfaceRequest)localObject1).getDeferrableSurface());
      localBuilder.addCameraCaptureCallback(((ProcessingSurface)localObject1).getCameraCaptureCallback());
      this.mSessionDeferrableSurface = ((DeferrableSurface)localObject1);
      localBuilder.setTag(Integer.valueOf(((CaptureStage)localObject2).getId()));
    }
    else
    {
      localObject2 = paramPreviewConfig.getImageInfoProcessor(null);
      if (localObject2 != null) {
        localBuilder.addCameraCaptureCallback(new CameraCaptureCallback()
        {
          public void onCaptureCompleted(CameraCaptureResult paramAnonymousCameraCaptureResult)
          {
            super.onCaptureCompleted(paramAnonymousCameraCaptureResult);
            if (this.val$processor.process(new CameraCaptureResultImageInfo(paramAnonymousCameraCaptureResult))) {
              Preview.this.notifyUpdated();
            }
          }
        });
      }
      this.mSessionDeferrableSurface = ((SurfaceRequest)localObject1).getDeferrableSurface();
    }
    localBuilder.addSurface(this.mSessionDeferrableSurface);
    localBuilder.addErrorListener(new SessionConfig.ErrorListener()
    {
      public void onError(SessionConfig paramAnonymousSessionConfig, SessionConfig.SessionError paramAnonymousSessionError)
      {
        if (Preview.this.isCurrentlyBoundCamera(paramString))
        {
          paramAnonymousSessionConfig = Preview.this.createPipeline(paramString, paramPreviewConfig, paramSize);
          Preview.this.attachToCamera(paramString, paramAnonymousSessionConfig.build());
          Preview.this.notifyReset();
        }
      }
    });
    return localBuilder;
  }
  
  protected UseCaseConfig.Builder<?, ?, ?> getDefaultBuilder(CameraInfo paramCameraInfo)
  {
    paramCameraInfo = (PreviewConfig)CameraX.getDefaultUseCaseConfig(PreviewConfig.class, paramCameraInfo);
    if (paramCameraInfo != null) {
      return Builder.fromConfig(paramCameraInfo);
    }
    return null;
  }
  
  public int getTargetRotation()
  {
    return ((PreviewConfig)getUseCaseConfig()).getTargetRotation();
  }
  
  boolean isPreviewSurfaceProviderSet()
  {
    boolean bool;
    if ((this.mSurfaceProvider != null) && (this.mPreviewSurfaceProviderExecutor != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected Map<String, Size> onSuggestedResolutionUpdated(Map<String, Size> paramMap)
  {
    String str = getBoundCameraId();
    Size localSize = (Size)paramMap.get(str);
    if (localSize != null)
    {
      this.mLatestResolution = localSize;
      if (isPreviewSurfaceProviderSet()) {
        updateConfigAndOutput(str, (PreviewConfig)getUseCaseConfig(), localSize);
      }
      return paramMap;
    }
    paramMap = new StringBuilder();
    paramMap.append("Suggested resolution map missing resolution for camera ");
    paramMap.append(str);
    throw new IllegalArgumentException(paramMap.toString());
  }
  
  public void setSurfaceProvider(SurfaceProvider paramSurfaceProvider)
  {
    setSurfaceProvider(CameraXExecutors.mainThreadExecutor(), paramSurfaceProvider);
  }
  
  public void setSurfaceProvider(Executor paramExecutor, SurfaceProvider paramSurfaceProvider)
  {
    
    if (paramSurfaceProvider == null)
    {
      this.mSurfaceProvider = null;
      notifyInactive();
    }
    else
    {
      this.mSurfaceProvider = paramSurfaceProvider;
      this.mPreviewSurfaceProviderExecutor = paramExecutor;
      notifyActive();
      if (this.mLatestResolution != null) {
        updateConfigAndOutput(getBoundCameraId(), (PreviewConfig)getUseCaseConfig(), this.mLatestResolution);
      }
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Preview:");
    localStringBuilder.append(getName());
    return localStringBuilder.toString();
  }
  
  public static final class Builder
    implements UseCaseConfig.Builder<Preview, PreviewConfig, Builder>, ImageOutputConfig.Builder<Builder>, ThreadConfig.Builder<Builder>
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
      if ((localClass != null) && (!localClass.equals(Preview.class)))
      {
        paramMutableOptionsBundle = new StringBuilder();
        paramMutableOptionsBundle.append("Invalid target class configuration for ");
        paramMutableOptionsBundle.append(this);
        paramMutableOptionsBundle.append(": ");
        paramMutableOptionsBundle.append(localClass);
        throw new IllegalArgumentException(paramMutableOptionsBundle.toString());
      }
      setTargetClass(Preview.class);
    }
    
    public static Builder fromConfig(PreviewConfig paramPreviewConfig)
    {
      return new Builder(MutableOptionsBundle.from(paramPreviewConfig));
    }
    
    public Preview build()
    {
      if ((getMutableConfig().retrieveOption(PreviewConfig.OPTION_TARGET_ASPECT_RATIO, null) != null) && (getMutableConfig().retrieveOption(PreviewConfig.OPTION_TARGET_RESOLUTION, null) != null)) {
        throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
      }
      return new Preview(getUseCaseConfig());
    }
    
    public MutableConfig getMutableConfig()
    {
      return this.mMutableConfig;
    }
    
    public PreviewConfig getUseCaseConfig()
    {
      return new PreviewConfig(OptionsBundle.from(this.mMutableConfig));
    }
    
    public Builder setBackgroundExecutor(Executor paramExecutor)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_BACKGROUND_EXECUTOR, paramExecutor);
      return this;
    }
    
    public Builder setCameraSelector(CameraSelector paramCameraSelector)
    {
      getMutableConfig().insertOption(UseCaseConfig.OPTION_CAMERA_SELECTOR, paramCameraSelector);
      return this;
    }
    
    public Builder setCaptureOptionUnpacker(CaptureConfig.OptionUnpacker paramOptionUnpacker)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_CAPTURE_CONFIG_UNPACKER, paramOptionUnpacker);
      return this;
    }
    
    public Builder setCaptureProcessor(CaptureProcessor paramCaptureProcessor)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_PREVIEW_CAPTURE_PROCESSOR, paramCaptureProcessor);
      return this;
    }
    
    public Builder setDefaultCaptureConfig(CaptureConfig paramCaptureConfig)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_DEFAULT_CAPTURE_CONFIG, paramCaptureConfig);
      return this;
    }
    
    public Builder setDefaultResolution(Size paramSize)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_DEFAULT_RESOLUTION, paramSize);
      return this;
    }
    
    public Builder setDefaultSessionConfig(SessionConfig paramSessionConfig)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_DEFAULT_SESSION_CONFIG, paramSessionConfig);
      return this;
    }
    
    public Builder setImageInfoProcessor(ImageInfoProcessor paramImageInfoProcessor)
    {
      getMutableConfig().insertOption(PreviewConfig.IMAGE_INFO_PROCESSOR, paramImageInfoProcessor);
      return this;
    }
    
    public Builder setMaxResolution(Size paramSize)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_MAX_RESOLUTION, paramSize);
      return this;
    }
    
    public Builder setSessionOptionUnpacker(SessionConfig.OptionUnpacker paramOptionUnpacker)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_SESSION_CONFIG_UNPACKER, paramOptionUnpacker);
      return this;
    }
    
    public Builder setSupportedResolutions(List<Pair<Integer, Size[]>> paramList)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_SUPPORTED_RESOLUTIONS, paramList);
      return this;
    }
    
    public Builder setSurfaceOccupancyPriority(int paramInt)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setTargetAspectRatio(int paramInt)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_ASPECT_RATIO, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setTargetAspectRatioCustom(Rational paramRational)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, paramRational);
      getMutableConfig().removeOption(PreviewConfig.OPTION_TARGET_ASPECT_RATIO);
      return this;
    }
    
    public Builder setTargetClass(Class<Preview> paramClass)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_CLASS, paramClass);
      if (getMutableConfig().retrieveOption(PreviewConfig.OPTION_TARGET_NAME, null) == null)
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
      getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_NAME, paramString);
      return this;
    }
    
    public Builder setTargetResolution(Size paramSize)
    {
      getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, paramSize);
      if (paramSize != null) {
        getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM, new Rational(paramSize.getWidth(), paramSize.getHeight()));
      }
      return this;
    }
    
    public Builder setTargetRotation(int paramInt)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_TARGET_ROTATION, Integer.valueOf(paramInt));
      return this;
    }
    
    public Builder setUseCaseEventCallback(UseCase.EventCallback paramEventCallback)
    {
      getMutableConfig().insertOption(PreviewConfig.OPTION_USE_CASE_EVENT_CALLBACK, paramEventCallback);
      return this;
    }
  }
  
  public static final class Defaults
    implements ConfigProvider<PreviewConfig>
  {
    private static final PreviewConfig DEFAULT_CONFIG = new Preview.Builder().setMaxResolution(DEFAULT_MAX_RESOLUTION).setSurfaceOccupancyPriority(2).getUseCaseConfig();
    private static final Size DEFAULT_MAX_RESOLUTION = CameraX.getSurfaceManager().getPreviewSize();
    private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 2;
    
    public Defaults() {}
    
    public PreviewConfig getConfig(CameraInfo paramCameraInfo)
    {
      return DEFAULT_CONFIG;
    }
  }
  
  public static abstract interface SurfaceProvider
  {
    public abstract void onSurfaceRequested(SurfaceRequest paramSurfaceRequest);
  }
}
