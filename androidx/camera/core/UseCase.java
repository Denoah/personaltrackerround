package androidx.camera.core;

import android.util.Size;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.Config.Option;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfig.Builder;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public abstract class UseCase
{
  private final Map<String, CameraControlInternal> mAttachedCameraControlMap = new HashMap();
  private final Map<String, SessionConfig> mAttachedCameraIdToSessionConfigMap = new HashMap();
  private final Map<String, Size> mAttachedSurfaceResolutionMap = new HashMap();
  private CameraInternal mBoundCamera;
  private final Object mBoundCameraLock = new Object();
  private int mImageFormat = 34;
  private State mState = State.INACTIVE;
  private final Set<StateChangeCallback> mStateChangeCallbacks = new HashSet();
  private UseCaseConfig<?> mUseCaseConfig;
  
  protected UseCase(UseCaseConfig<?> paramUseCaseConfig)
  {
    updateUseCaseConfig(paramUseCaseConfig);
  }
  
  public void addStateChangeCallback(StateChangeCallback paramStateChangeCallback)
  {
    this.mStateChangeCallbacks.add(paramStateChangeCallback);
  }
  
  protected UseCaseConfig<?> applyDefaults(UseCaseConfig<?> paramUseCaseConfig, UseCaseConfig.Builder<?, ?, ?> paramBuilder)
  {
    if (paramBuilder == null) {
      return paramUseCaseConfig;
    }
    MutableConfig localMutableConfig = paramBuilder.getMutableConfig();
    if ((paramUseCaseConfig.containsOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO)) && (localMutableConfig.containsOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM))) {
      localMutableConfig.removeOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO_CUSTOM);
    }
    Iterator localIterator = paramUseCaseConfig.listOptions().iterator();
    while (localIterator.hasNext())
    {
      Config.Option localOption = (Config.Option)localIterator.next();
      localMutableConfig.insertOption(localOption, paramUseCaseConfig.retrieveOption(localOption));
    }
    return paramBuilder.getUseCaseConfig();
  }
  
  public final void attachCameraControl(String paramString, CameraControlInternal paramCameraControlInternal)
  {
    this.mAttachedCameraControlMap.put(paramString, paramCameraControlInternal);
    onCameraControlReady(paramString);
  }
  
  protected void attachToCamera(String paramString, SessionConfig paramSessionConfig)
  {
    this.mAttachedCameraIdToSessionConfigMap.put(paramString, paramSessionConfig);
  }
  
  public void clear()
  {
    ??? = this.mUseCaseConfig.getUseCaseEventCallback(null);
    if (??? != null) {
      ((EventCallback)???).onUnbind();
    }
    synchronized (this.mBoundCameraLock)
    {
      this.mBoundCamera = null;
      this.mStateChangeCallbacks.clear();
      return;
    }
  }
  
  final void detachCameraControl(String paramString)
  {
    this.mAttachedCameraControlMap.remove(paramString);
  }
  
  public Set<String> getAttachedCameraIds()
  {
    return this.mAttachedCameraIdToSessionConfigMap.keySet();
  }
  
  public Size getAttachedSurfaceResolution(String paramString)
  {
    return (Size)this.mAttachedSurfaceResolutionMap.get(paramString);
  }
  
  public CameraInternal getBoundCamera()
  {
    synchronized (this.mBoundCameraLock)
    {
      CameraInternal localCameraInternal = this.mBoundCamera;
      return localCameraInternal;
    }
  }
  
  protected String getBoundCameraId()
  {
    CameraInternal localCameraInternal = getBoundCamera();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("No camera bound to use case: ");
    localStringBuilder.append(this);
    return ((CameraInternal)Preconditions.checkNotNull(localCameraInternal, localStringBuilder.toString())).getCameraInfoInternal().getCameraId();
  }
  
  protected CameraControlInternal getCameraControl(String paramString)
  {
    CameraControlInternal localCameraControlInternal = (CameraControlInternal)this.mAttachedCameraControlMap.get(paramString);
    paramString = localCameraControlInternal;
    if (localCameraControlInternal == null) {
      paramString = CameraControlInternal.DEFAULT_EMPTY_INSTANCE;
    }
    return paramString;
  }
  
  protected UseCaseConfig.Builder<?, ?, ?> getDefaultBuilder(CameraInfo paramCameraInfo)
  {
    return null;
  }
  
  public int getImageFormat()
  {
    return this.mImageFormat;
  }
  
  public String getName()
  {
    UseCaseConfig localUseCaseConfig = this.mUseCaseConfig;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("<UnknownUseCase-");
    localStringBuilder.append(hashCode());
    localStringBuilder.append(">");
    return localUseCaseConfig.getTargetName(localStringBuilder.toString());
  }
  
  public SessionConfig getSessionConfig(String paramString)
  {
    Object localObject = (SessionConfig)this.mAttachedCameraIdToSessionConfigMap.get(paramString);
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Invalid camera: ");
    ((StringBuilder)localObject).append(paramString);
    throw new IllegalArgumentException(((StringBuilder)localObject).toString());
  }
  
  public UseCaseConfig<?> getUseCaseConfig()
  {
    return this.mUseCaseConfig;
  }
  
  protected boolean isCurrentlyBoundCamera(String paramString)
  {
    if (getBoundCamera() == null) {
      return false;
    }
    return Objects.equals(paramString, getBoundCameraId());
  }
  
  protected final void notifyActive()
  {
    this.mState = State.ACTIVE;
    notifyState();
  }
  
  protected final void notifyInactive()
  {
    this.mState = State.INACTIVE;
    notifyState();
  }
  
  protected final void notifyReset()
  {
    Iterator localIterator = this.mStateChangeCallbacks.iterator();
    while (localIterator.hasNext()) {
      ((StateChangeCallback)localIterator.next()).onUseCaseReset(this);
    }
  }
  
  protected final void notifyState()
  {
    int i = 1.$SwitchMap$androidx$camera$core$UseCase$State[this.mState.ordinal()];
    Iterator localIterator;
    if (i != 1)
    {
      if (i == 2)
      {
        localIterator = this.mStateChangeCallbacks.iterator();
        while (localIterator.hasNext()) {
          ((StateChangeCallback)localIterator.next()).onUseCaseActive(this);
        }
      }
    }
    else
    {
      localIterator = this.mStateChangeCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((StateChangeCallback)localIterator.next()).onUseCaseInactive(this);
      }
    }
  }
  
  protected final void notifyUpdated()
  {
    Iterator localIterator = this.mStateChangeCallbacks.iterator();
    while (localIterator.hasNext()) {
      ((StateChangeCallback)localIterator.next()).onUseCaseUpdated(this);
    }
  }
  
  protected void onBind(CameraInternal paramCameraInternal)
  {
    synchronized (this.mBoundCameraLock)
    {
      this.mBoundCamera = paramCameraInternal;
      updateUseCaseConfig(this.mUseCaseConfig);
      ??? = this.mUseCaseConfig.getUseCaseEventCallback(null);
      if (??? != null) {
        ((EventCallback)???).onBind(paramCameraInternal.getCameraInfoInternal().getCameraId());
      }
      return;
    }
  }
  
  protected void onCameraControlReady(String paramString) {}
  
  public void onStateOffline(String paramString) {}
  
  public void onStateOnline(String paramString) {}
  
  protected abstract Map<String, Size> onSuggestedResolutionUpdated(Map<String, Size> paramMap);
  
  public void removeStateChangeCallback(StateChangeCallback paramStateChangeCallback)
  {
    this.mStateChangeCallbacks.remove(paramStateChangeCallback);
  }
  
  protected void setImageFormat(int paramInt)
  {
    this.mImageFormat = paramInt;
  }
  
  public void updateSuggestedResolution(Map<String, Size> paramMap)
  {
    paramMap = onSuggestedResolutionUpdated(paramMap).entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      this.mAttachedSurfaceResolutionMap.put(localEntry.getKey(), localEntry.getValue());
    }
  }
  
  protected final void updateUseCaseConfig(UseCaseConfig<?> paramUseCaseConfig)
  {
    CameraInfo localCameraInfo;
    if (getBoundCamera() == null) {
      localCameraInfo = null;
    } else {
      localCameraInfo = getBoundCamera().getCameraInfo();
    }
    this.mUseCaseConfig = applyDefaults(paramUseCaseConfig, getDefaultBuilder(localCameraInfo));
  }
  
  public static abstract interface EventCallback
  {
    public abstract void onBind(String paramString);
    
    public abstract void onUnbind();
  }
  
  static enum State
  {
    static
    {
      State localState = new State("INACTIVE", 1);
      INACTIVE = localState;
      $VALUES = new State[] { ACTIVE, localState };
    }
    
    private State() {}
  }
  
  public static abstract interface StateChangeCallback
  {
    public abstract void onUseCaseActive(UseCase paramUseCase);
    
    public abstract void onUseCaseInactive(UseCase paramUseCase);
    
    public abstract void onUseCaseReset(UseCase paramUseCase);
    
    public abstract void onUseCaseUpdated(UseCase paramUseCase);
  }
}
