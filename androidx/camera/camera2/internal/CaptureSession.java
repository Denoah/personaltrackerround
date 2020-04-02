package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Surface;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.impl.CameraEventCallbacks;
import androidx.camera.camera2.impl.CameraEventCallbacks.ComboCameraEventCallback;
import androidx.camera.camera2.internal.compat.CameraCaptureSessionCompat;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureConfig.Builder;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.Config.Option;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.DeferrableSurfaces;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.utils.futures.AsyncFunction;
import androidx.camera.core.impl.utils.futures.FutureChain;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.concurrent.futures.CallbackToFutureAdapter.Resolver;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

final class CaptureSession
{
  private static final String TAG = "CaptureSession";
  private static final long TIMEOUT_GET_SURFACE_IN_MS = 5000L;
  CameraCaptureSession mCameraCaptureSession;
  volatile Config mCameraEventOnRepeatingOptions;
  private final CameraCaptureSession.CaptureCallback mCaptureCallback = new CameraCaptureSession.CaptureCallback()
  {
    public void onCaptureCompleted(CameraCaptureSession paramAnonymousCameraCaptureSession, CaptureRequest paramAnonymousCaptureRequest, TotalCaptureResult paramAnonymousTotalCaptureResult) {}
  };
  private final List<CaptureConfig> mCaptureConfigs = new ArrayList();
  private final StateCallback mCaptureSessionStateCallback = new StateCallback();
  List<DeferrableSurface> mConfiguredDeferrableSurfaces = Collections.emptyList();
  private Map<DeferrableSurface, Surface> mConfiguredSurfaceMap = new HashMap();
  private final Executor mExecutor;
  private final boolean mIsLegacyDevice;
  CallbackToFutureAdapter.Completer<Void> mOpenCaptureSessionCompleter;
  ListenableFuture<Void> mOpenFuture;
  CallbackToFutureAdapter.Completer<Void> mReleaseCompleter;
  ListenableFuture<Void> mReleaseFuture;
  private final ScheduledExecutorService mScheduleExecutor;
  volatile SessionConfig mSessionConfig;
  State mState = State.UNINITIALIZED;
  final Object mStateLock = new Object();
  
  CaptureSession(Executor paramExecutor, ScheduledExecutorService paramScheduledExecutorService, boolean paramBoolean)
  {
    this.mExecutor = paramExecutor;
    this.mScheduleExecutor = paramScheduledExecutorService;
    this.mIsLegacyDevice = paramBoolean;
  }
  
  private CameraCaptureSession.CaptureCallback createCamera2CaptureCallback(List<CameraCaptureCallback> paramList, CameraCaptureSession.CaptureCallback... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList(paramList.size() + paramVarArgs.length);
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localArrayList.add(CaptureCallbackConverter.toCaptureCallback((CameraCaptureCallback)paramList.next()));
    }
    Collections.addAll(localArrayList, paramVarArgs);
    return Camera2CaptureCallbacks.createComboCallback(localArrayList);
  }
  
  private Executor getExecutor()
  {
    return this.mExecutor;
  }
  
  private static Config mergeOptions(List<CaptureConfig> paramList)
  {
    MutableOptionsBundle localMutableOptionsBundle = MutableOptionsBundle.create();
    Iterator localIterator1 = paramList.iterator();
    while (localIterator1.hasNext())
    {
      Config localConfig = ((CaptureConfig)localIterator1.next()).getImplementationOptions();
      Iterator localIterator2 = localConfig.listOptions().iterator();
      while (localIterator2.hasNext())
      {
        Config.Option localOption = (Config.Option)localIterator2.next();
        Object localObject = localConfig.retrieveOption(localOption, null);
        if (localMutableOptionsBundle.containsOption(localOption))
        {
          paramList = localMutableOptionsBundle.retrieveOption(localOption, null);
          if (!Objects.equals(paramList, localObject))
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("Detect conflicting option ");
            localStringBuilder.append(localOption.getId());
            localStringBuilder.append(" : ");
            localStringBuilder.append(localObject);
            localStringBuilder.append(" != ");
            localStringBuilder.append(paramList);
            Log.d("CaptureSession", localStringBuilder.toString());
          }
        }
        else
        {
          localMutableOptionsBundle.insertOption(localOption, localObject);
        }
      }
    }
    return localMutableOptionsBundle;
  }
  
  private ListenableFuture<Void> openCaptureSession(List<Surface> paramList, SessionConfig paramSessionConfig, CameraDevice paramCameraDevice)
  {
    synchronized (this.mStateLock)
    {
      int i = 3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()];
      if ((i != 1) && (i != 2)) {
        if (i != 3)
        {
          if (i != 5)
          {
            paramSessionConfig = new java/util/concurrent/CancellationException;
            paramList = new java/lang/StringBuilder;
            paramList.<init>();
            paramList.append("openCaptureSession() not execute in state: ");
            paramList.append(this.mState);
            paramSessionConfig.<init>(paramList.toString());
            paramList = Futures.immediateFailedFuture(paramSessionConfig);
            return paramList;
          }
        }
        else
        {
          _..Lambda.CaptureSession.bwwGuGuBhJx_fgB4Br9Wswwme0U localBwwGuGuBhJx_fgB4Br9Wswwme0U = new androidx/camera/camera2/internal/_$$Lambda$CaptureSession$bwwGuGuBhJx_fgB4Br9Wswwme0U;
          localBwwGuGuBhJx_fgB4Br9Wswwme0U.<init>(this, paramList, paramSessionConfig, paramCameraDevice);
          paramList = CallbackToFutureAdapter.getFuture(localBwwGuGuBhJx_fgB4Br9Wswwme0U);
          return paramList;
        }
      }
      paramSessionConfig = new java/lang/IllegalStateException;
      paramList = new java/lang/StringBuilder;
      paramList.<init>();
      paramList.append("openCaptureSession() should not be possible in state: ");
      paramList.append(this.mState);
      paramSessionConfig.<init>(paramList.toString());
      paramList = Futures.immediateFailedFuture(paramSessionConfig);
      return paramList;
    }
  }
  
  void cancelIssuedCaptureRequests()
  {
    if (!this.mCaptureConfigs.isEmpty())
    {
      Iterator localIterator1 = this.mCaptureConfigs.iterator();
      while (localIterator1.hasNext())
      {
        Iterator localIterator2 = ((CaptureConfig)localIterator1.next()).getCameraCaptureCallbacks().iterator();
        while (localIterator2.hasNext()) {
          ((CameraCaptureCallback)localIterator2.next()).onCaptureCancelled();
        }
      }
      this.mCaptureConfigs.clear();
    }
  }
  
  void clearConfiguredSurfaces()
  {
    DeferrableSurfaces.decrementAll(this.mConfiguredDeferrableSurfaces);
    this.mConfiguredDeferrableSurfaces.clear();
  }
  
  void close()
  {
    synchronized (this.mStateLock)
    {
      int i = 3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()];
      if (i != 1)
      {
        if (i != 2)
        {
          if (i != 3)
          {
            if (i != 4)
            {
              if (i != 5) {
                break label168;
              }
              if (this.mSessionConfig != null)
              {
                Object localObject2 = new androidx/camera/camera2/impl/Camera2ImplConfig;
                ((Camera2ImplConfig)localObject2).<init>(this.mSessionConfig.getImplementationOptions());
                localObject2 = ((Camera2ImplConfig)localObject2).getCameraEventCallback(CameraEventCallbacks.createEmptyCallback()).createComboCallback().onDisableSession();
                boolean bool = ((List)localObject2).isEmpty();
                if (!bool) {
                  try
                  {
                    issueCaptureRequests(setupConfiguredSurface((List)localObject2));
                  }
                  catch (IllegalStateException localIllegalStateException1)
                  {
                    Log.e("CaptureSession", "Unable to issue the request before close the capture session", localIllegalStateException1);
                  }
                }
              }
            }
            this.mState = State.CLOSED;
            this.mSessionConfig = null;
            this.mCameraEventOnRepeatingOptions = null;
            closeConfiguredDeferrableSurfaces();
            break label168;
          }
          if (this.mOpenFuture != null) {
            this.mOpenFuture.cancel(true);
          }
        }
        this.mState = State.RELEASED;
        label168:
        return;
      }
      IllegalStateException localIllegalStateException2 = new java/lang/IllegalStateException;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("close() should not be possible in state: ");
      localStringBuilder.append(this.mState);
      localIllegalStateException2.<init>(localStringBuilder.toString());
      throw localIllegalStateException2;
    }
  }
  
  void closeConfiguredDeferrableSurfaces()
  {
    if ((this.mIsLegacyDevice) || (Build.VERSION.SDK_INT <= 23))
    {
      Iterator localIterator = this.mConfiguredDeferrableSurfaces.iterator();
      while (localIterator.hasNext()) {
        ((DeferrableSurface)localIterator.next()).close();
      }
    }
  }
  
  void forceClose()
  {
    this.mCaptureSessionStateCallback.onClosed(this.mCameraCaptureSession);
  }
  
  List<CaptureConfig> getCaptureConfigs()
  {
    synchronized (this.mStateLock)
    {
      List localList = Collections.unmodifiableList(this.mCaptureConfigs);
      return localList;
    }
  }
  
  SessionConfig getSessionConfig()
  {
    synchronized (this.mStateLock)
    {
      SessionConfig localSessionConfig = this.mSessionConfig;
      return localSessionConfig;
    }
  }
  
  State getState()
  {
    synchronized (this.mStateLock)
    {
      State localState = this.mState;
      return localState;
    }
  }
  
  void issueBurstCaptureRequest(List<CaptureConfig> paramList)
  {
    if (paramList.isEmpty()) {
      return;
    }
    try
    {
      CameraBurstCaptureCallback localCameraBurstCaptureCallback = new androidx/camera/camera2/internal/CameraBurstCaptureCallback;
      localCameraBurstCaptureCallback.<init>();
      ArrayList localArrayList = new java/util/ArrayList;
      localArrayList.<init>();
      Log.d("CaptureSession", "Issuing capture request.");
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        Object localObject1 = (CaptureConfig)paramList.next();
        if (((CaptureConfig)localObject1).getSurfaces().isEmpty())
        {
          Log.d("CaptureSession", "Skipping issuing empty capture request.");
        }
        else
        {
          int i = 1;
          Object localObject2 = ((CaptureConfig)localObject1).getSurfaces().iterator();
          Object localObject3;
          do
          {
            j = i;
            if (!((Iterator)localObject2).hasNext()) {
              break;
            }
            localObject3 = (DeferrableSurface)((Iterator)localObject2).next();
          } while (this.mConfiguredSurfaceMap.containsKey(localObject3));
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          ((StringBuilder)localObject2).append("Skipping capture request with invalid surface: ");
          ((StringBuilder)localObject2).append(localObject3);
          Log.d("CaptureSession", ((StringBuilder)localObject2).toString());
          int j = 0;
          if (j != 0)
          {
            localObject3 = CaptureConfig.Builder.from((CaptureConfig)localObject1);
            if (this.mSessionConfig != null) {
              ((CaptureConfig.Builder)localObject3).addImplementationOptions(this.mSessionConfig.getRepeatingCaptureConfig().getImplementationOptions());
            }
            if (this.mCameraEventOnRepeatingOptions != null) {
              ((CaptureConfig.Builder)localObject3).addImplementationOptions(this.mCameraEventOnRepeatingOptions);
            }
            ((CaptureConfig.Builder)localObject3).addImplementationOptions(((CaptureConfig)localObject1).getImplementationOptions());
            localObject3 = Camera2CaptureRequestBuilder.build(((CaptureConfig.Builder)localObject3).build(), this.mCameraCaptureSession.getDevice(), this.mConfiguredSurfaceMap);
            if (localObject3 == null)
            {
              Log.d("CaptureSession", "Skipping issuing request without surface.");
              return;
            }
            localObject2 = new java/util/ArrayList;
            ((ArrayList)localObject2).<init>();
            localObject1 = ((CaptureConfig)localObject1).getCameraCaptureCallbacks().iterator();
            while (((Iterator)localObject1).hasNext()) {
              CaptureCallbackConverter.toCaptureCallback((CameraCaptureCallback)((Iterator)localObject1).next(), (List)localObject2);
            }
            localCameraBurstCaptureCallback.addCamera2Callbacks((CaptureRequest)localObject3, (List)localObject2);
            localArrayList.add(localObject3);
          }
        }
      }
      if (!localArrayList.isEmpty()) {
        CameraCaptureSessionCompat.captureBurstRequests(this.mCameraCaptureSession, localArrayList, this.mExecutor, localCameraBurstCaptureCallback);
      } else {
        Log.d("CaptureSession", "Skipping issuing burst request due to no valid request elements");
      }
    }
    catch (CameraAccessException localCameraAccessException)
    {
      paramList = new StringBuilder();
      paramList.append("Unable to access camera: ");
      paramList.append(localCameraAccessException.getMessage());
      Log.e("CaptureSession", paramList.toString());
      Thread.dumpStack();
    }
  }
  
  void issueCaptureRequests(List<CaptureConfig> paramList)
  {
    synchronized (this.mStateLock)
    {
      switch (3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()])
      {
      default: 
        break;
      case 6: 
      case 7: 
      case 8: 
        paramList = new java/lang/IllegalStateException;
        paramList.<init>("Cannot issue capture request on a closed/released session.");
        throw paramList;
      case 5: 
        this.mCaptureConfigs.addAll(paramList);
        issuePendingCaptureRequest();
        break;
      case 2: 
      case 3: 
      case 4: 
        this.mCaptureConfigs.addAll(paramList);
        break;
      case 1: 
        paramList = new java/lang/IllegalStateException;
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("issueCaptureRequests() should not be possible in state: ");
        localStringBuilder.append(this.mState);
        paramList.<init>(localStringBuilder.toString());
        throw paramList;
      }
      return;
    }
  }
  
  void issuePendingCaptureRequest()
  {
    if (this.mCaptureConfigs.isEmpty()) {
      return;
    }
    try
    {
      issueBurstCaptureRequest(this.mCaptureConfigs);
      return;
    }
    finally
    {
      this.mCaptureConfigs.clear();
    }
  }
  
  void issueRepeatingCaptureRequests()
  {
    if (this.mSessionConfig == null)
    {
      Log.d("CaptureSession", "Skipping issueRepeatingCaptureRequests for no configuration case.");
      return;
    }
    Object localObject1 = this.mSessionConfig.getRepeatingCaptureConfig();
    try
    {
      Log.d("CaptureSession", "Issuing request for session.");
      CaptureConfig.Builder localBuilder = CaptureConfig.Builder.from((CaptureConfig)localObject1);
      localObject2 = new androidx/camera/camera2/impl/Camera2ImplConfig;
      ((Camera2ImplConfig)localObject2).<init>(this.mSessionConfig.getImplementationOptions());
      this.mCameraEventOnRepeatingOptions = mergeOptions(((Camera2ImplConfig)localObject2).getCameraEventCallback(CameraEventCallbacks.createEmptyCallback()).createComboCallback().onRepeating());
      if (this.mCameraEventOnRepeatingOptions != null) {
        localBuilder.addImplementationOptions(this.mCameraEventOnRepeatingOptions);
      }
      localObject2 = Camera2CaptureRequestBuilder.build(localBuilder.build(), this.mCameraCaptureSession.getDevice(), this.mConfiguredSurfaceMap);
      if (localObject2 == null)
      {
        Log.d("CaptureSession", "Skipping issuing empty request for session.");
        return;
      }
      localObject1 = createCamera2CaptureCallback(((CaptureConfig)localObject1).getCameraCaptureCallbacks(), new CameraCaptureSession.CaptureCallback[] { this.mCaptureCallback });
      CameraCaptureSessionCompat.setSingleRepeatingRequest(this.mCameraCaptureSession, (CaptureRequest)localObject2, this.mExecutor, (CameraCaptureSession.CaptureCallback)localObject1);
    }
    catch (CameraAccessException localCameraAccessException)
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Unable to access camera: ");
      ((StringBuilder)localObject2).append(localCameraAccessException.getMessage());
      Log.e("CaptureSession", ((StringBuilder)localObject2).toString());
      Thread.dumpStack();
    }
  }
  
  ListenableFuture<Void> open(SessionConfig paramSessionConfig, CameraDevice paramCameraDevice)
  {
    synchronized (this.mStateLock)
    {
      if (3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()] != 2)
      {
        paramSessionConfig = new java/lang/StringBuilder;
        paramSessionConfig.<init>();
        paramSessionConfig.append("Open not allowed in state: ");
        paramSessionConfig.append(this.mState);
        Log.e("CaptureSession", paramSessionConfig.toString());
        paramSessionConfig = new StringBuilder();
        paramSessionConfig.append("open() should not allow the state: ");
        paramSessionConfig.append(this.mState);
        return Futures.immediateFailedFuture(new IllegalStateException(paramSessionConfig.toString()));
      }
      this.mState = State.GET_SURFACE;
      Object localObject2 = paramSessionConfig.getSurfaces();
      Object localObject3 = new java/util/ArrayList;
      ((ArrayList)localObject3).<init>((Collection)localObject2);
      this.mConfiguredDeferrableSurfaces = ((List)localObject3);
      localObject2 = FutureChain.from(DeferrableSurfaces.surfaceListWithTimeout((Collection)localObject3, false, 5000L, this.mExecutor, this.mScheduleExecutor));
      localObject3 = new androidx/camera/camera2/internal/_$$Lambda$CaptureSession$2IbSQd39wMeo2dJgmFG1rvePLoM;
      ((_..Lambda.CaptureSession.2IbSQd39wMeo2dJgmFG1rvePLoM)localObject3).<init>(this, paramSessionConfig, paramCameraDevice);
      paramCameraDevice = ((FutureChain)localObject2).transformAsync((AsyncFunction)localObject3, this.mExecutor);
      this.mOpenFuture = paramCameraDevice;
      paramSessionConfig = new androidx/camera/camera2/internal/_$$Lambda$CaptureSession$sXRVbG2D_a097UMmXwOO3AjgT5k;
      paramSessionConfig.<init>(this);
      paramCameraDevice.addListener(paramSessionConfig, this.mExecutor);
      paramSessionConfig = Futures.nonCancellationPropagating(this.mOpenFuture);
      return paramSessionConfig;
    }
  }
  
  ListenableFuture<Void> release(boolean paramBoolean)
  {
    synchronized (this.mStateLock)
    {
      switch (3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()])
      {
      default: 
        break;
      case 5: 
      case 6: 
        CameraCaptureSession localCameraCaptureSession = this.mCameraCaptureSession;
        if (localCameraCaptureSession != null)
        {
          if (paramBoolean) {
            try
            {
              this.mCameraCaptureSession.abortCaptures();
            }
            catch (CameraAccessException localCameraAccessException)
            {
              Log.e("CaptureSession", "Unable to abort captures.", localCameraAccessException);
            }
          }
          this.mCameraCaptureSession.close();
        }
      case 4: 
        this.mState = State.RELEASING;
      case 7: 
        if (this.mReleaseFuture == null)
        {
          localObject2 = new androidx/camera/camera2/internal/CaptureSession$2;
          ((2)localObject2).<init>(this);
          this.mReleaseFuture = CallbackToFutureAdapter.getFuture((CallbackToFutureAdapter.Resolver)localObject2);
        }
        localObject2 = this.mReleaseFuture;
        return localObject2;
      case 3: 
        if (this.mOpenFuture != null) {
          this.mOpenFuture.cancel(true);
        }
      case 2: 
        this.mState = State.RELEASED;
        break;
      }
      Object localObject2 = new java/lang/IllegalStateException;
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("release() should not be possible in state: ");
      localStringBuilder.append(this.mState);
      ((IllegalStateException)localObject2).<init>(localStringBuilder.toString());
      throw ((Throwable)localObject2);
      return Futures.immediateFuture(null);
    }
  }
  
  void setSessionConfig(SessionConfig paramSessionConfig)
  {
    synchronized (this.mStateLock)
    {
      switch (3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[this.mState.ordinal()])
      {
      default: 
        break;
      case 6: 
      case 7: 
      case 8: 
        paramSessionConfig = new java/lang/IllegalStateException;
        paramSessionConfig.<init>("Session configuration cannot be set on a closed/released session.");
        throw paramSessionConfig;
      case 5: 
        this.mSessionConfig = paramSessionConfig;
        if (!this.mConfiguredSurfaceMap.keySet().containsAll(paramSessionConfig.getSurfaces()))
        {
          Log.e("CaptureSession", "Does not have the proper configured lists");
          return;
        }
        Log.d("CaptureSession", "Attempting to submit CaptureRequest after setting");
        issueRepeatingCaptureRequests();
        break;
      case 2: 
      case 3: 
      case 4: 
        this.mSessionConfig = paramSessionConfig;
        break;
      case 1: 
        IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
        paramSessionConfig = new java/lang/StringBuilder;
        paramSessionConfig.<init>();
        paramSessionConfig.append("setSessionConfig() should not be possible in state: ");
        paramSessionConfig.append(this.mState);
        localIllegalStateException.<init>(paramSessionConfig.toString());
        throw localIllegalStateException;
      }
      return;
    }
  }
  
  List<CaptureConfig> setupConfiguredSurface(List<CaptureConfig> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      CaptureConfig.Builder localBuilder = CaptureConfig.Builder.from((CaptureConfig)paramList.next());
      localBuilder.setTemplateType(1);
      Iterator localIterator = this.mSessionConfig.getRepeatingCaptureConfig().getSurfaces().iterator();
      while (localIterator.hasNext()) {
        localBuilder.addSurface((DeferrableSurface)localIterator.next());
      }
      localArrayList.add(localBuilder.build());
    }
    return localArrayList;
  }
  
  static final class Builder
  {
    private Executor mExecutor;
    private ScheduledExecutorService mScheduledExecutorService;
    private int mSupportedHardwareLevel = -1;
    
    Builder() {}
    
    CaptureSession build()
    {
      Executor localExecutor = this.mExecutor;
      if (localExecutor != null)
      {
        ScheduledExecutorService localScheduledExecutorService = this.mScheduledExecutorService;
        if (localScheduledExecutorService != null)
        {
          boolean bool;
          if (this.mSupportedHardwareLevel == 2) {
            bool = true;
          } else {
            bool = false;
          }
          return new CaptureSession(localExecutor, localScheduledExecutorService, bool);
        }
        throw new IllegalStateException("Missing ScheduledExecutorService. ScheduledExecutorService must be set with setScheduledExecutorService()");
      }
      throw new IllegalStateException("Missing camera executor. Executor must be set with setExecutor()");
    }
    
    void setExecutor(Executor paramExecutor)
    {
      this.mExecutor = ((Executor)Preconditions.checkNotNull(paramExecutor));
    }
    
    void setScheduledExecutorService(ScheduledExecutorService paramScheduledExecutorService)
    {
      this.mScheduledExecutorService = ((ScheduledExecutorService)Preconditions.checkNotNull(paramScheduledExecutorService));
    }
    
    void setSupportedHardwareLevel(int paramInt)
    {
      this.mSupportedHardwareLevel = paramInt;
    }
  }
  
  static enum State
  {
    static
    {
      INITIALIZED = new State("INITIALIZED", 1);
      GET_SURFACE = new State("GET_SURFACE", 2);
      OPENING = new State("OPENING", 3);
      OPENED = new State("OPENED", 4);
      CLOSED = new State("CLOSED", 5);
      RELEASING = new State("RELEASING", 6);
      State localState = new State("RELEASED", 7);
      RELEASED = localState;
      $VALUES = new State[] { UNINITIALIZED, INITIALIZED, GET_SURFACE, OPENING, OPENED, CLOSED, RELEASING, localState };
    }
    
    private State() {}
  }
  
  final class StateCallback
    extends CameraCaptureSession.StateCallback
  {
    StateCallback() {}
    
    public void onClosed(CameraCaptureSession arg1)
    {
      synchronized (CaptureSession.this.mStateLock)
      {
        if (CaptureSession.this.mState != CaptureSession.State.UNINITIALIZED)
        {
          if (CaptureSession.this.mState == CaptureSession.State.RELEASED) {
            return;
          }
          Log.d("CaptureSession", "CameraCaptureSession.onClosed()");
          CaptureSession.this.closeConfiguredDeferrableSurfaces();
          CaptureSession.this.mState = CaptureSession.State.RELEASED;
          CaptureSession.this.mCameraCaptureSession = null;
          CaptureSession.this.clearConfiguredSurfaces();
          if (CaptureSession.this.mReleaseCompleter != null)
          {
            CaptureSession.this.mReleaseCompleter.set(null);
            CaptureSession.this.mReleaseCompleter = null;
          }
          return;
        }
        IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("onClosed() should not be possible in state: ");
        localStringBuilder.append(CaptureSession.this.mState);
        localIllegalStateException.<init>(localStringBuilder.toString());
        throw localIllegalStateException;
      }
    }
    
    public void onConfigureFailed(CameraCaptureSession paramCameraCaptureSession)
    {
      synchronized (CaptureSession.this.mStateLock)
      {
        Preconditions.checkNotNull(CaptureSession.this.mOpenCaptureSessionCompleter, "OpenCaptureSession completer should not null");
        CallbackToFutureAdapter.Completer localCompleter = CaptureSession.this.mOpenCaptureSessionCompleter;
        Object localObject2 = new java/util/concurrent/CancellationException;
        ((CancellationException)localObject2).<init>("onConfigureFailed");
        localCompleter.setException((Throwable)localObject2);
        CaptureSession.this.mOpenCaptureSessionCompleter = null;
        switch (CaptureSession.3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[CaptureSession.this.mState.ordinal()])
        {
        default: 
          break;
        case 7: 
          CaptureSession.this.mState = CaptureSession.State.RELEASING;
          paramCameraCaptureSession.close();
          break;
        case 4: 
        case 6: 
          CaptureSession.this.mState = CaptureSession.State.RELEASED;
          CaptureSession.this.mCameraCaptureSession = null;
          break;
        case 1: 
        case 2: 
        case 3: 
        case 5: 
        case 8: 
          localObject2 = new java/lang/IllegalStateException;
          paramCameraCaptureSession = new java/lang/StringBuilder;
          paramCameraCaptureSession.<init>();
          paramCameraCaptureSession.append("onConfiguredFailed() should not be possible in state: ");
          paramCameraCaptureSession.append(CaptureSession.this.mState);
          ((IllegalStateException)localObject2).<init>(paramCameraCaptureSession.toString());
          throw ((Throwable)localObject2);
        }
        paramCameraCaptureSession = new java/lang/StringBuilder;
        paramCameraCaptureSession.<init>();
        paramCameraCaptureSession.append("CameraCaptureSession.onConfiguredFailed() ");
        paramCameraCaptureSession.append(CaptureSession.this.mState);
        Log.e("CaptureSession", paramCameraCaptureSession.toString());
        return;
      }
    }
    
    public void onConfigured(CameraCaptureSession paramCameraCaptureSession)
    {
      synchronized (CaptureSession.this.mStateLock)
      {
        Preconditions.checkNotNull(CaptureSession.this.mOpenCaptureSessionCompleter, "OpenCaptureSession completer should not null");
        CaptureSession.this.mOpenCaptureSessionCompleter.set(null);
        CaptureSession.this.mOpenCaptureSessionCompleter = null;
        Object localObject2;
        switch (CaptureSession.3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[CaptureSession.this.mState.ordinal()])
        {
        default: 
          break;
        case 7: 
          paramCameraCaptureSession.close();
          break;
        case 6: 
          CaptureSession.this.mCameraCaptureSession = paramCameraCaptureSession;
          break;
        case 4: 
          CaptureSession.this.mState = CaptureSession.State.OPENED;
          CaptureSession.this.mCameraCaptureSession = paramCameraCaptureSession;
          if (CaptureSession.this.mSessionConfig != null)
          {
            paramCameraCaptureSession = CaptureSession.this.mSessionConfig.getImplementationOptions();
            localObject2 = new androidx/camera/camera2/impl/Camera2ImplConfig;
            ((Camera2ImplConfig)localObject2).<init>(paramCameraCaptureSession);
            paramCameraCaptureSession = ((Camera2ImplConfig)localObject2).getCameraEventCallback(CameraEventCallbacks.createEmptyCallback()).createComboCallback().onEnableSession();
            if (!paramCameraCaptureSession.isEmpty()) {
              CaptureSession.this.issueBurstCaptureRequest(CaptureSession.this.setupConfiguredSurface(paramCameraCaptureSession));
            }
          }
          Log.d("CaptureSession", "Attempting to send capture request onConfigured");
          CaptureSession.this.issueRepeatingCaptureRequests();
          CaptureSession.this.issuePendingCaptureRequest();
          break;
        case 1: 
        case 2: 
        case 3: 
        case 5: 
        case 8: 
          paramCameraCaptureSession = new java/lang/IllegalStateException;
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          ((StringBuilder)localObject2).append("onConfigured() should not be possible in state: ");
          ((StringBuilder)localObject2).append(CaptureSession.this.mState);
          paramCameraCaptureSession.<init>(((StringBuilder)localObject2).toString());
          throw paramCameraCaptureSession;
        }
        paramCameraCaptureSession = new java/lang/StringBuilder;
        paramCameraCaptureSession.<init>();
        paramCameraCaptureSession.append("CameraCaptureSession.onConfigured() mState=");
        paramCameraCaptureSession.append(CaptureSession.this.mState);
        Log.d("CaptureSession", paramCameraCaptureSession.toString());
        return;
      }
    }
    
    public void onReady(CameraCaptureSession arg1)
    {
      synchronized (CaptureSession.this.mStateLock)
      {
        if (CaptureSession.3.$SwitchMap$androidx$camera$camera2$internal$CaptureSession$State[CaptureSession.this.mState.ordinal()] != 1)
        {
          localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          localStringBuilder.append("CameraCaptureSession.onReady() ");
          localStringBuilder.append(CaptureSession.this.mState);
          Log.d("CaptureSession", localStringBuilder.toString());
          return;
        }
        IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>();
        localStringBuilder.append("onReady() should not be possible in state: ");
        localStringBuilder.append(CaptureSession.this.mState);
        localIllegalStateException.<init>(localStringBuilder.toString());
        throw localIllegalStateException;
      }
    }
  }
}
