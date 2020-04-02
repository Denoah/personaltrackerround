package androidx.camera.camera2.internal;

import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraManager.AvailabilityCallback;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.Preview;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraControlInternal.ControlUpdateCallback;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CameraInternal.State;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureConfig.Builder;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.DeferrableSurface.SurfaceClosedException;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.LiveDataObservable;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.Observable.Observer;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.SessionConfig.Builder;
import androidx.camera.core.impl.SessionConfig.ErrorListener;
import androidx.camera.core.impl.SessionConfig.ValidatingBuilder;
import androidx.camera.core.impl.UseCaseAttachState;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

final class Camera2CameraImpl
  implements CameraInternal
{
  private static final int ERROR_NONE = 0;
  private static final String TAG = "Camera";
  private final Observable<Integer> mAvailableCamerasObservable;
  private final CameraAvailability mCameraAvailability;
  private final Camera2CameraControl mCameraControlInternal;
  SessionConfig mCameraControlSessionConfig = SessionConfig.defaultEmptySessionConfig();
  CameraDevice mCameraDevice;
  int mCameraDeviceError = 0;
  final CameraInfoInternal mCameraInfoInternal;
  private final CameraManagerCompat mCameraManager;
  CaptureSession mCaptureSession;
  private CaptureSession.Builder mCaptureSessionBuilder = new CaptureSession.Builder();
  final Set<CaptureSession> mConfiguringForClose = new HashSet();
  private final Executor mExecutor;
  private final LiveDataObservable<CameraInternal.State> mObservableState = new LiveDataObservable();
  final AtomicInteger mReleaseRequestCount = new AtomicInteger(0);
  final Map<CaptureSession, ListenableFuture<Void>> mReleasedCaptureSessions = new LinkedHashMap();
  volatile InternalState mState = InternalState.INITIALIZED;
  private final StateCallback mStateCallback = new StateCallback();
  private final UseCaseAttachState mUseCaseAttachState;
  ListenableFuture<Void> mUserReleaseFuture;
  CallbackToFutureAdapter.Completer<Void> mUserReleaseNotifier;
  
  Camera2CameraImpl(CameraManagerCompat paramCameraManagerCompat, String paramString, Observable<Integer> paramObservable, Handler paramHandler)
  {
    this.mCameraManager = paramCameraManagerCompat;
    this.mAvailableCamerasObservable = paramObservable;
    paramCameraManagerCompat = CameraXExecutors.newHandlerExecutor(paramHandler);
    this.mExecutor = paramCameraManagerCompat;
    this.mUseCaseAttachState = new UseCaseAttachState(paramString);
    this.mObservableState.postValue(CameraInternal.State.CLOSED);
    try
    {
      paramObservable = this.mCameraManager.unwrap().getCameraCharacteristics(paramString);
      paramHandler = new androidx/camera/camera2/internal/Camera2CameraControl;
      Object localObject = this.mExecutor;
      ControlUpdateListenerInternal localControlUpdateListenerInternal = new androidx/camera/camera2/internal/Camera2CameraImpl$ControlUpdateListenerInternal;
      localControlUpdateListenerInternal.<init>(this);
      paramHandler.<init>(paramObservable, paramCameraManagerCompat, (Executor)localObject, localControlUpdateListenerInternal);
      this.mCameraControlInternal = paramHandler;
      localObject = new androidx/camera/camera2/internal/Camera2CameraInfoImpl;
      ((Camera2CameraInfoImpl)localObject).<init>(paramString, paramObservable, paramHandler.getZoomControl(), this.mCameraControlInternal.getTorchControl());
      this.mCameraInfoInternal = ((CameraInfoInternal)localObject);
      paramObservable = (Camera2CameraInfoImpl)localObject;
      this.mCaptureSessionBuilder.setSupportedHardwareLevel(paramObservable.getSupportedHardwareLevel());
      this.mCaptureSessionBuilder.setExecutor(this.mExecutor);
      this.mCaptureSessionBuilder.setScheduledExecutorService(paramCameraManagerCompat);
      this.mCaptureSession = this.mCaptureSessionBuilder.build();
      paramCameraManagerCompat = new CameraAvailability(paramString);
      this.mCameraAvailability = paramCameraManagerCompat;
      this.mAvailableCamerasObservable.addObserver(this.mExecutor, paramCameraManagerCompat);
      this.mCameraManager.registerAvailabilityCallback(this.mExecutor, this.mCameraAvailability);
      return;
    }
    catch (CameraAccessException paramCameraManagerCompat)
    {
      throw new IllegalStateException("Cannot access camera", paramCameraManagerCompat);
    }
  }
  
  private boolean checkAndAttachRepeatingSurface(CaptureConfig.Builder paramBuilder)
  {
    if (!paramBuilder.getSurfaces().isEmpty())
    {
      Log.w("Camera", "The capture config builder already has surface inside.");
      return false;
    }
    Iterator localIterator = this.mUseCaseAttachState.getActiveAndOnlineUseCases().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = ((UseCase)localIterator.next()).getSessionConfig(this.mCameraInfoInternal.getCameraId()).getRepeatingCaptureConfig().getSurfaces();
      if (!((List)localObject).isEmpty())
      {
        localObject = ((List)localObject).iterator();
        while (((Iterator)localObject).hasNext()) {
          paramBuilder.addSurface((DeferrableSurface)((Iterator)localObject).next());
        }
      }
    }
    if (paramBuilder.getSurfaces().isEmpty())
    {
      Log.w("Camera", "Unable to find a repeating surface to attach to CaptureConfig");
      return false;
    }
    return true;
  }
  
  private void clearCameraControlPreviewAspectRatio(Collection<UseCase> paramCollection)
  {
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext()) {
      if (((UseCase)paramCollection.next() instanceof Preview)) {
        this.mCameraControlInternal.setPreviewAspectRatio(null);
      }
    }
  }
  
  private void closeInternal()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Closing camera: ");
    localStringBuilder.append(this.mCameraInfoInternal.getCameraId());
    Log.d("Camera", localStringBuilder.toString());
    int i = 4.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[this.mState.ordinal()];
    boolean bool = false;
    if (i != 3)
    {
      if ((i != 4) && (i != 5))
      {
        if (i != 6)
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("close() ignored due to being in state: ");
          localStringBuilder.append(this.mState);
          Log.d("Camera", localStringBuilder.toString());
        }
        else
        {
          if (this.mCameraDevice == null) {
            bool = true;
          }
          Preconditions.checkState(bool);
          setState(InternalState.INITIALIZED);
        }
      }
      else {
        setState(InternalState.CLOSING);
      }
    }
    else
    {
      setState(InternalState.CLOSING);
      closeCamera(false);
    }
  }
  
  private void configAndClose(boolean paramBoolean)
  {
    final CaptureSession localCaptureSession = this.mCaptureSessionBuilder.build();
    this.mConfiguringForClose.add(localCaptureSession);
    resetCaptureSession(paramBoolean);
    Object localObject = new SurfaceTexture(0);
    ((SurfaceTexture)localObject).setDefaultBufferSize(640, 480);
    Surface localSurface = new Surface((SurfaceTexture)localObject);
    localObject = new _..Lambda.Camera2CameraImpl.uk0LTTpHa2F5WWH3KTX1TkFW3ps(localSurface, (SurfaceTexture)localObject);
    SessionConfig.Builder localBuilder = new SessionConfig.Builder();
    localBuilder.addNonRepeatingSurface(new ImmediateSurface(localSurface));
    localBuilder.setTemplateType(1);
    Log.d("Camera", "Start configAndClose.");
    Futures.addCallback(localCaptureSession.open(localBuilder.build(), this.mCameraDevice), new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Unable to configure camera ");
        localStringBuilder.append(Camera2CameraImpl.this.mCameraInfoInternal.getCameraId());
        localStringBuilder.append(" due to ");
        localStringBuilder.append(paramAnonymousThrowable.getMessage());
        Log.d("Camera", localStringBuilder.toString());
        Camera2CameraImpl.this.releaseDummySession(localCaptureSession, this.val$closeAndCleanupRunner);
      }
      
      public void onSuccess(Void paramAnonymousVoid)
      {
        Camera2CameraImpl.this.closeStaleCaptureSessions(localCaptureSession);
        Camera2CameraImpl.this.releaseDummySession(localCaptureSession, this.val$closeAndCleanupRunner);
      }
    }, this.mExecutor);
  }
  
  private CameraDevice.StateCallback createDeviceStateCallback()
  {
    ArrayList localArrayList = new ArrayList(this.mUseCaseAttachState.getOnlineBuilder().build().getDeviceStateCallbacks());
    localArrayList.add(this.mStateCallback);
    return CameraDeviceStateCallbacks.createComboCallback(localArrayList);
  }
  
  static String getErrorMessage(int paramInt)
  {
    if (paramInt != 0)
    {
      if (paramInt != 1)
      {
        if (paramInt != 2)
        {
          if (paramInt != 3)
          {
            if (paramInt != 4)
            {
              if (paramInt != 5) {
                return "UNKNOWN ERROR";
              }
              return "ERROR_CAMERA_SERVICE";
            }
            return "ERROR_CAMERA_DEVICE";
          }
          return "ERROR_CAMERA_DISABLED";
        }
        return "ERROR_MAX_CAMERAS_IN_USE";
      }
      return "ERROR_CAMERA_IN_USE";
    }
    return "ERROR_NONE";
  }
  
  private ListenableFuture<Void> getOrCreateUserReleaseFuture()
  {
    if (this.mUserReleaseFuture == null) {
      if (this.mState != InternalState.RELEASED) {
        this.mUserReleaseFuture = CallbackToFutureAdapter.getFuture(new _..Lambda.Camera2CameraImpl.fdO_RSwTpczwJ8hciYGsVqCv1gs(this));
      } else {
        this.mUserReleaseFuture = Futures.immediateFuture(null);
      }
    }
    return this.mUserReleaseFuture;
  }
  
  private void notifyStateOfflineToUseCases(List<UseCase> paramList)
  {
    CameraXExecutors.mainThreadExecutor().execute(new _..Lambda.Camera2CameraImpl.12f6LAfz84Ochaa_VfAClFjUcj0(this, paramList));
  }
  
  private void notifyStateOnlineToUseCases(List<UseCase> paramList)
  {
    CameraXExecutors.mainThreadExecutor().execute(new _..Lambda.Camera2CameraImpl.1GBcDQ7vAmdwUiKUN55462xnj_8(this, paramList));
  }
  
  private void openInternal()
  {
    int i = 4.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[this.mState.ordinal()];
    boolean bool = true;
    if (i != 1)
    {
      if (i != 2)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("open() ignored due to being in state: ");
        localStringBuilder.append(this.mState);
        Log.d("Camera", localStringBuilder.toString());
      }
      else
      {
        setState(InternalState.REOPENING);
        if ((!isSessionCloseComplete()) && (this.mCameraDeviceError == 0))
        {
          if (this.mCameraDevice == null) {
            bool = false;
          }
          Preconditions.checkState(bool, "Camera Device should be open if session close is not complete");
          setState(InternalState.OPENED);
          openCaptureSession();
        }
      }
    }
    else {
      openCameraDevice();
    }
  }
  
  private ListenableFuture<Void> releaseInternal()
  {
    ListenableFuture localListenableFuture = getOrCreateUserReleaseFuture();
    int i = 4.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[this.mState.ordinal()];
    boolean bool = true;
    switch (i)
    {
    default: 
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("release() ignored due to being in state: ");
      localStringBuilder.append(this.mState);
      Log.d("Camera", localStringBuilder.toString());
      break;
    case 3: 
      setState(InternalState.RELEASING);
      closeCamera(true);
      break;
    case 2: 
    case 4: 
    case 5: 
    case 7: 
      setState(InternalState.RELEASING);
      break;
    case 1: 
    case 6: 
      if (this.mCameraDevice != null) {
        bool = false;
      }
      Preconditions.checkState(bool);
      setState(InternalState.RELEASING);
      Preconditions.checkState(isSessionCloseComplete());
      finishClose();
    }
    return localListenableFuture;
  }
  
  private void tryAddOnlineUseCases(Collection<UseCase> paramCollection)
  {
    ArrayList localArrayList = new ArrayList();
    String str = this.mCameraInfoInternal.getCameraId();
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      paramCollection = (UseCase)localIterator.next();
      if (!this.mUseCaseAttachState.isUseCaseOnline(paramCollection))
      {
        localArrayList.add(paramCollection);
        this.mUseCaseAttachState.setUseCaseOnline(paramCollection);
      }
    }
    if (localArrayList.isEmpty()) {
      return;
    }
    paramCollection = new StringBuilder();
    paramCollection.append("Use cases [");
    paramCollection.append(TextUtils.join(", ", localArrayList));
    paramCollection.append("] now ONLINE for camera ");
    paramCollection.append(str);
    Log.d("Camera", paramCollection.toString());
    notifyStateOnlineToUseCases(localArrayList);
    updateCaptureSessionConfig();
    resetCaptureSession(false);
    if (this.mState == InternalState.OPENED) {
      openCaptureSession();
    } else {
      openInternal();
    }
    updateCameraControlPreviewAspectRatio(localArrayList);
  }
  
  private void tryRemoveOnlineUseCases(Collection<UseCase> paramCollection)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      paramCollection = (UseCase)localIterator.next();
      if (this.mUseCaseAttachState.isUseCaseOnline(paramCollection))
      {
        this.mUseCaseAttachState.setUseCaseOffline(paramCollection);
        localArrayList.add(paramCollection);
      }
    }
    if (localArrayList.isEmpty()) {
      return;
    }
    paramCollection = new StringBuilder();
    paramCollection.append("Use cases [");
    paramCollection.append(TextUtils.join(", ", localArrayList));
    paramCollection.append("] now OFFLINE for camera ");
    paramCollection.append(this.mCameraInfoInternal.getCameraId());
    Log.d("Camera", paramCollection.toString());
    clearCameraControlPreviewAspectRatio(localArrayList);
    notifyStateOfflineToUseCases(localArrayList);
    if (this.mUseCaseAttachState.getOnlineUseCases().isEmpty())
    {
      this.mCameraControlInternal.setActive(false);
      resetCaptureSession(false);
      closeInternal();
    }
    else
    {
      updateCaptureSessionConfig();
      resetCaptureSession(false);
      if (this.mState == InternalState.OPENED) {
        openCaptureSession();
      }
    }
  }
  
  private void updateCameraControlPreviewAspectRatio(Collection<UseCase> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      paramCollection = (UseCase)localIterator.next();
      if ((paramCollection instanceof Preview))
      {
        paramCollection = paramCollection.getAttachedSurfaceResolution(this.mCameraInfoInternal.getCameraId());
        paramCollection = new Rational(paramCollection.getWidth(), paramCollection.getHeight());
        this.mCameraControlInternal.setPreviewAspectRatio(paramCollection);
      }
    }
  }
  
  public void addOnlineUseCase(Collection<UseCase> paramCollection)
  {
    if (!paramCollection.isEmpty())
    {
      this.mCameraControlInternal.setActive(true);
      this.mExecutor.execute(new _..Lambda.Camera2CameraImpl.Df5M1T9h5eRGRFHHyDoZKAxYbL4(this, paramCollection));
    }
  }
  
  public void close()
  {
    this.mExecutor.execute(new _..Lambda.Camera2CameraImpl.UC0M7m80_HJTLdlzE6dfb6TvRHY(this));
  }
  
  void closeCamera(boolean paramBoolean)
  {
    InternalState localInternalState = this.mState;
    Object localObject = InternalState.CLOSING;
    int i = 0;
    boolean bool;
    if ((localInternalState != localObject) && (this.mState != InternalState.RELEASING) && ((this.mState != InternalState.REOPENING) || (this.mCameraDeviceError == 0))) {
      bool = false;
    } else {
      bool = true;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("closeCamera should only be called in a CLOSING, RELEASING or REOPENING (with error) state. Current state: ");
    ((StringBuilder)localObject).append(this.mState);
    ((StringBuilder)localObject).append(" (error: ");
    ((StringBuilder)localObject).append(getErrorMessage(this.mCameraDeviceError));
    ((StringBuilder)localObject).append(")");
    Preconditions.checkState(bool, ((StringBuilder)localObject).toString());
    if (((Camera2CameraInfoImpl)getCameraInfoInternal()).getSupportedHardwareLevel() == 2) {
      i = 1;
    }
    if ((Build.VERSION.SDK_INT > 23) && (Build.VERSION.SDK_INT < 29) && (i != 0) && (this.mCameraDeviceError == 0)) {
      configAndClose(paramBoolean);
    } else {
      resetCaptureSession(paramBoolean);
    }
    this.mCaptureSession.cancelIssuedCaptureRequests();
  }
  
  void closeStaleCaptureSessions(CaptureSession paramCaptureSession)
  {
    if (Build.VERSION.SDK_INT < 23)
    {
      Object localObject1 = this.mReleasedCaptureSessions.keySet();
      int i = 0;
      localObject1 = (CaptureSession[])((Set)localObject1).toArray(new CaptureSession[0]);
      int j = localObject1.length;
      while (i < j)
      {
        Object localObject2 = localObject1[i];
        if (paramCaptureSession == localObject2) {
          break;
        }
        localObject2.forceClose();
        i++;
      }
    }
  }
  
  UseCase findUseCaseForSurface(DeferrableSurface paramDeferrableSurface)
  {
    Iterator localIterator = this.mUseCaseAttachState.getOnlineUseCases().iterator();
    while (localIterator.hasNext())
    {
      UseCase localUseCase = (UseCase)localIterator.next();
      if (localUseCase.getSessionConfig(this.mCameraInfoInternal.getCameraId()).getSurfaces().contains(paramDeferrableSurface)) {
        return localUseCase;
      }
    }
    return null;
  }
  
  void finishClose()
  {
    boolean bool;
    if ((this.mState != InternalState.RELEASING) && (this.mState != InternalState.CLOSING)) {
      bool = false;
    } else {
      bool = true;
    }
    Preconditions.checkState(bool);
    Preconditions.checkState(this.mReleasedCaptureSessions.isEmpty());
    this.mCameraDevice = null;
    if (this.mState == InternalState.CLOSING)
    {
      setState(InternalState.INITIALIZED);
    }
    else
    {
      setState(InternalState.RELEASED);
      this.mAvailableCamerasObservable.removeObserver(this.mCameraAvailability);
      this.mCameraManager.unregisterAvailabilityCallback(this.mCameraAvailability);
      CallbackToFutureAdapter.Completer localCompleter = this.mUserReleaseNotifier;
      if (localCompleter != null)
      {
        localCompleter.set(null);
        this.mUserReleaseNotifier = null;
      }
    }
  }
  
  public CameraControl getCameraControl()
  {
    return getCameraControlInternal();
  }
  
  public CameraControlInternal getCameraControlInternal()
  {
    return this.mCameraControlInternal;
  }
  
  public CameraInfo getCameraInfo()
  {
    return getCameraInfoInternal();
  }
  
  public CameraInfoInternal getCameraInfoInternal()
  {
    return this.mCameraInfoInternal;
  }
  
  public Observable<CameraInternal.State> getCameraState()
  {
    return this.mObservableState;
  }
  
  boolean isSessionCloseComplete()
  {
    boolean bool;
    if ((this.mReleasedCaptureSessions.isEmpty()) && (this.mConfiguringForClose.isEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  boolean isUseCaseOnline(UseCase paramUseCase)
  {
    try
    {
      _..Lambda.Camera2CameraImpl.Hxs_WLzBhhzqtn9x_JwDP3Uu34o localHxs_WLzBhhzqtn9x_JwDP3Uu34o = new androidx/camera/camera2/internal/_$$Lambda$Camera2CameraImpl$Hxs_WLzBhhzqtn9x_JwDP3Uu34o;
      localHxs_WLzBhhzqtn9x_JwDP3Uu34o.<init>(this, paramUseCase);
      boolean bool = ((Boolean)CallbackToFutureAdapter.getFuture(localHxs_WLzBhhzqtn9x_JwDP3Uu34o).get()).booleanValue();
      return bool;
    }
    catch (ExecutionException paramUseCase) {}catch (InterruptedException paramUseCase) {}
    throw new RuntimeException("Unable to check if use case is online.", paramUseCase);
  }
  
  public void onUseCaseActive(UseCase paramUseCase)
  {
    Preconditions.checkNotNull(paramUseCase);
    this.mExecutor.execute(new _..Lambda.Camera2CameraImpl.TMsHTDAH1CH4sDiZkII1XxPFppI(this, paramUseCase));
  }
  
  public void onUseCaseInactive(UseCase paramUseCase)
  {
    Preconditions.checkNotNull(paramUseCase);
    this.mExecutor.execute(new _..Lambda.Camera2CameraImpl.7Txc67S146YaHtfnM8aykQqncpw(this, paramUseCase));
  }
  
  public void onUseCaseReset(UseCase paramUseCase)
  {
    Preconditions.checkNotNull(paramUseCase);
    this.mExecutor.execute(new _..Lambda.Camera2CameraImpl.A1amYMQE9w_YK1K0_m9xJmwJAeE(this, paramUseCase));
  }
  
  public void onUseCaseUpdated(UseCase paramUseCase)
  {
    Preconditions.checkNotNull(paramUseCase);
    this.mExecutor.execute(new _..Lambda.Camera2CameraImpl.SEVETBHT_ZqPnYRa2CLtP3I8vao(this, paramUseCase));
  }
  
  public void open()
  {
    this.mExecutor.execute(new _..Lambda.Camera2CameraImpl.tFCuzMJVSd_6gQqkOS71yo2JPHA(this));
  }
  
  void openCameraDevice()
  {
    if (!this.mCameraAvailability.isCameraAvailable())
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("No cameras available. Waiting for available camera before opening camera: ");
      localStringBuilder.append(this.mCameraInfoInternal.getCameraId());
      Log.d("Camera", localStringBuilder.toString());
      setState(InternalState.PENDING_OPEN);
      return;
    }
    setState(InternalState.OPENING);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Opening camera: ");
    localStringBuilder.append(this.mCameraInfoInternal.getCameraId());
    Log.d("Camera", localStringBuilder.toString());
    try
    {
      this.mCameraManager.openCamera(this.mCameraInfoInternal.getCameraId(), this.mExecutor, createDeviceStateCallback());
    }
    catch (CameraAccessException localCameraAccessException)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unable to open camera ");
      localStringBuilder.append(this.mCameraInfoInternal.getCameraId());
      localStringBuilder.append(" due to ");
      localStringBuilder.append(localCameraAccessException.getMessage());
      Log.d("Camera", localStringBuilder.toString());
    }
  }
  
  void openCaptureSession()
  {
    boolean bool;
    if (this.mState == InternalState.OPENED) {
      bool = true;
    } else {
      bool = false;
    }
    Preconditions.checkState(bool);
    SessionConfig.ValidatingBuilder localValidatingBuilder = this.mUseCaseAttachState.getOnlineBuilder();
    if (!localValidatingBuilder.isValid())
    {
      Log.d("Camera", "Unable to create capture session due to conflicting configurations");
      return;
    }
    final CaptureSession localCaptureSession = this.mCaptureSession;
    Futures.addCallback(localCaptureSession.open(localValidatingBuilder.build(), this.mCameraDevice), new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        if ((paramAnonymousThrowable instanceof CameraAccessException))
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Unable to configure camera ");
          localStringBuilder.append(Camera2CameraImpl.this.mCameraInfoInternal.getCameraId());
          localStringBuilder.append(" due to ");
          localStringBuilder.append(paramAnonymousThrowable.getMessage());
          Log.d("Camera", localStringBuilder.toString());
        }
        else if ((paramAnonymousThrowable instanceof CancellationException))
        {
          paramAnonymousThrowable = new StringBuilder();
          paramAnonymousThrowable.append("Unable to configure camera ");
          paramAnonymousThrowable.append(Camera2CameraImpl.this.mCameraInfoInternal.getCameraId());
          paramAnonymousThrowable.append(" cancelled");
          Log.d("Camera", paramAnonymousThrowable.toString());
        }
        else if ((paramAnonymousThrowable instanceof DeferrableSurface.SurfaceClosedException))
        {
          paramAnonymousThrowable = Camera2CameraImpl.this.findUseCaseForSurface(((DeferrableSurface.SurfaceClosedException)paramAnonymousThrowable).getDeferrableSurface());
          if (paramAnonymousThrowable != null) {
            Camera2CameraImpl.this.postSurfaceClosedError(paramAnonymousThrowable);
          }
        }
        else
        {
          if (!(paramAnonymousThrowable instanceof TimeoutException)) {
            break label221;
          }
          paramAnonymousThrowable = new StringBuilder();
          paramAnonymousThrowable.append("Unable to configure camera ");
          paramAnonymousThrowable.append(Camera2CameraImpl.this.mCameraInfoInternal.getCameraId());
          paramAnonymousThrowable.append(", timeout!");
          Log.e("Camera", paramAnonymousThrowable.toString());
        }
        return;
        label221:
        throw new RuntimeException(paramAnonymousThrowable);
      }
      
      public void onSuccess(Void paramAnonymousVoid)
      {
        Camera2CameraImpl.this.closeStaleCaptureSessions(localCaptureSession);
      }
    }, this.mExecutor);
  }
  
  void postSurfaceClosedError(UseCase paramUseCase)
  {
    ScheduledExecutorService localScheduledExecutorService = CameraXExecutors.mainThreadExecutor();
    paramUseCase = paramUseCase.getSessionConfig(this.mCameraInfoInternal.getCameraId());
    Object localObject = paramUseCase.getErrorListeners();
    if (!((List)localObject).isEmpty())
    {
      localObject = (SessionConfig.ErrorListener)((List)localObject).get(0);
      Log.d("Camera", "Posting surface closed", new Throwable());
      localScheduledExecutorService.execute(new _..Lambda.Camera2CameraImpl.3_Ci3Ca1SfnAk2LfH6SIwQMfLYo((SessionConfig.ErrorListener)localObject, paramUseCase));
    }
  }
  
  public ListenableFuture<Void> release()
  {
    return CallbackToFutureAdapter.getFuture(new _..Lambda.Camera2CameraImpl.5CchKeuwJTmxSf_8XdoUol5CxQU(this));
  }
  
  void releaseDummySession(CaptureSession paramCaptureSession, Runnable paramRunnable)
  {
    this.mConfiguringForClose.remove(paramCaptureSession);
    releaseSession(paramCaptureSession, false).addListener(paramRunnable, CameraXExecutors.directExecutor());
  }
  
  ListenableFuture<Void> releaseSession(final CaptureSession paramCaptureSession, boolean paramBoolean)
  {
    paramCaptureSession.close();
    ListenableFuture localListenableFuture = paramCaptureSession.release(paramBoolean);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("releasing session in state ");
    localStringBuilder.append(this.mState.name());
    Log.d("Camera", localStringBuilder.toString());
    this.mReleasedCaptureSessions.put(paramCaptureSession, localListenableFuture);
    Futures.addCallback(localListenableFuture, new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable) {}
      
      public void onSuccess(Void paramAnonymousVoid)
      {
        Camera2CameraImpl.this.mReleasedCaptureSessions.remove(paramCaptureSession);
        int i = Camera2CameraImpl.4.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[Camera2CameraImpl.this.mState.ordinal()];
        if ((i == 2) || (i != 5 ? i == 7 : Camera2CameraImpl.this.mCameraDeviceError != 0)) {
          if ((Camera2CameraImpl.this.isSessionCloseComplete()) && (Camera2CameraImpl.this.mCameraDevice != null))
          {
            Camera2CameraImpl.this.mCameraDevice.close();
            Camera2CameraImpl.this.mCameraDevice = null;
          }
        }
      }
    }, CameraXExecutors.directExecutor());
    return localListenableFuture;
  }
  
  public void removeOnlineUseCase(Collection<UseCase> paramCollection)
  {
    if (!paramCollection.isEmpty()) {
      this.mExecutor.execute(new _..Lambda.Camera2CameraImpl.5wc8VkOCNW87m5eLEZfzgWUl_nY(this, paramCollection));
    }
  }
  
  void resetCaptureSession(boolean paramBoolean)
  {
    boolean bool;
    if (this.mCaptureSession != null) {
      bool = true;
    } else {
      bool = false;
    }
    Preconditions.checkState(bool);
    Log.d("Camera", "Resetting Capture Session");
    CaptureSession localCaptureSession1 = this.mCaptureSession;
    SessionConfig localSessionConfig = localCaptureSession1.getSessionConfig();
    List localList = localCaptureSession1.getCaptureConfigs();
    CaptureSession localCaptureSession2 = this.mCaptureSessionBuilder.build();
    this.mCaptureSession = localCaptureSession2;
    localCaptureSession2.setSessionConfig(localSessionConfig);
    this.mCaptureSession.issueCaptureRequests(localList);
    releaseSession(localCaptureSession1, paramBoolean);
  }
  
  void setState(InternalState paramInternalState)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Transitioning camera internal state: ");
    localStringBuilder.append(this.mState);
    localStringBuilder.append(" --> ");
    localStringBuilder.append(paramInternalState);
    Log.d("Camera", localStringBuilder.toString());
    this.mState = paramInternalState;
    switch (4.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[paramInternalState.ordinal()])
    {
    default: 
      break;
    case 8: 
      this.mObservableState.postValue(CameraInternal.State.RELEASED);
      break;
    case 7: 
      this.mObservableState.postValue(CameraInternal.State.RELEASING);
      break;
    case 6: 
      this.mObservableState.postValue(CameraInternal.State.PENDING_OPEN);
      break;
    case 4: 
    case 5: 
      this.mObservableState.postValue(CameraInternal.State.OPENING);
      break;
    case 3: 
      this.mObservableState.postValue(CameraInternal.State.OPEN);
      break;
    case 2: 
      this.mObservableState.postValue(CameraInternal.State.CLOSING);
      break;
    case 1: 
      this.mObservableState.postValue(CameraInternal.State.CLOSED);
    }
  }
  
  void submitCaptureRequests(List<CaptureConfig> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      paramList = (CaptureConfig)localIterator.next();
      CaptureConfig.Builder localBuilder = CaptureConfig.Builder.from(paramList);
      if ((!paramList.getSurfaces().isEmpty()) || (!paramList.isUseRepeatingSurface()) || (checkAndAttachRepeatingSurface(localBuilder))) {
        localArrayList.add(localBuilder.build());
      }
    }
    paramList = new StringBuilder();
    paramList.append("issue capture request for camera ");
    paramList.append(this.mCameraInfoInternal.getCameraId());
    Log.d("Camera", paramList.toString());
    this.mCaptureSession.issueCaptureRequests(localArrayList);
  }
  
  public String toString()
  {
    return String.format(Locale.US, "Camera@%x[id=%s]", new Object[] { Integer.valueOf(hashCode()), this.mCameraInfoInternal.getCameraId() });
  }
  
  void updateCaptureSessionConfig()
  {
    Object localObject = this.mUseCaseAttachState.getActiveAndOnlineBuilder();
    if (((SessionConfig.ValidatingBuilder)localObject).isValid())
    {
      ((SessionConfig.ValidatingBuilder)localObject).add(this.mCameraControlSessionConfig);
      localObject = ((SessionConfig.ValidatingBuilder)localObject).build();
      this.mCaptureSession.setSessionConfig((SessionConfig)localObject);
    }
  }
  
  void updateDefaultRequestBuilderToCameraControl(CameraDevice paramCameraDevice)
  {
    try
    {
      paramCameraDevice = paramCameraDevice.createCaptureRequest(this.mCameraControlInternal.getDefaultTemplate());
      this.mCameraControlInternal.setDefaultRequestBuilder(paramCameraDevice);
    }
    catch (CameraAccessException paramCameraDevice)
    {
      Log.e("Camera", "fail to create capture request.", paramCameraDevice);
    }
  }
  
  final class CameraAvailability
    extends CameraManager.AvailabilityCallback
    implements Observable.Observer<Integer>
  {
    private boolean mCameraAvailable = true;
    private final String mCameraId;
    private int mNumAvailableCameras = 0;
    
    CameraAvailability(String paramString)
    {
      this.mCameraId = paramString;
    }
    
    boolean isCameraAvailable()
    {
      boolean bool;
      if ((this.mCameraAvailable) && (this.mNumAvailableCameras > 0)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onCameraAvailable(String paramString)
    {
      if (!this.mCameraId.equals(paramString)) {
        return;
      }
      this.mCameraAvailable = true;
      if (Camera2CameraImpl.this.mState == Camera2CameraImpl.InternalState.PENDING_OPEN) {
        Camera2CameraImpl.this.openCameraDevice();
      }
    }
    
    public void onCameraUnavailable(String paramString)
    {
      if (!this.mCameraId.equals(paramString)) {
        return;
      }
      this.mCameraAvailable = false;
    }
    
    public void onError(Throwable paramThrowable) {}
    
    public void onNewData(Integer paramInteger)
    {
      Preconditions.checkNotNull(paramInteger);
      if (paramInteger.intValue() != this.mNumAvailableCameras)
      {
        this.mNumAvailableCameras = paramInteger.intValue();
        if (Camera2CameraImpl.this.mState == Camera2CameraImpl.InternalState.PENDING_OPEN) {
          Camera2CameraImpl.this.openCameraDevice();
        }
      }
    }
  }
  
  final class ControlUpdateListenerInternal
    implements CameraControlInternal.ControlUpdateCallback
  {
    ControlUpdateListenerInternal() {}
    
    public void onCameraControlCaptureRequests(List<CaptureConfig> paramList)
    {
      Camera2CameraImpl.this.submitCaptureRequests((List)Preconditions.checkNotNull(paramList));
    }
    
    public void onCameraControlUpdateSessionConfig(SessionConfig paramSessionConfig)
    {
      Camera2CameraImpl.this.mCameraControlSessionConfig = ((SessionConfig)Preconditions.checkNotNull(paramSessionConfig));
      Camera2CameraImpl.this.updateCaptureSessionConfig();
    }
  }
  
  static enum InternalState
  {
    static
    {
      OPENING = new InternalState("OPENING", 2);
      OPENED = new InternalState("OPENED", 3);
      CLOSING = new InternalState("CLOSING", 4);
      REOPENING = new InternalState("REOPENING", 5);
      RELEASING = new InternalState("RELEASING", 6);
      InternalState localInternalState = new InternalState("RELEASED", 7);
      RELEASED = localInternalState;
      $VALUES = new InternalState[] { INITIALIZED, PENDING_OPEN, OPENING, OPENED, CLOSING, REOPENING, RELEASING, localInternalState };
    }
    
    private InternalState() {}
  }
  
  final class StateCallback
    extends CameraDevice.StateCallback
  {
    StateCallback() {}
    
    private void handleErrorOnOpen(CameraDevice paramCameraDevice, int paramInt)
    {
      boolean bool;
      if ((Camera2CameraImpl.this.mState != Camera2CameraImpl.InternalState.OPENING) && (Camera2CameraImpl.this.mState != Camera2CameraImpl.InternalState.OPENED) && (Camera2CameraImpl.this.mState != Camera2CameraImpl.InternalState.REOPENING)) {
        bool = false;
      } else {
        bool = true;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Attempt to handle open error from non open state: ");
      localStringBuilder.append(Camera2CameraImpl.this.mState);
      Preconditions.checkState(bool, localStringBuilder.toString());
      if ((paramInt != 1) && (paramInt != 2) && (paramInt != 4))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Error observed on open (or opening) camera device ");
        localStringBuilder.append(paramCameraDevice.getId());
        localStringBuilder.append(": ");
        localStringBuilder.append(Camera2CameraImpl.getErrorMessage(paramInt));
        Log.e("Camera", localStringBuilder.toString());
        Camera2CameraImpl.this.setState(Camera2CameraImpl.InternalState.CLOSING);
        Camera2CameraImpl.this.closeCamera(false);
      }
      else
      {
        reopenCameraAfterError();
      }
    }
    
    private void reopenCameraAfterError()
    {
      boolean bool;
      if (Camera2CameraImpl.this.mCameraDeviceError != 0) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkState(bool, "Can only reopen camera device after error if the camera device is actually in an error state.");
      Camera2CameraImpl.this.setState(Camera2CameraImpl.InternalState.REOPENING);
      Camera2CameraImpl.this.closeCamera(false);
    }
    
    public void onClosed(CameraDevice paramCameraDevice)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("CameraDevice.onClosed(): ");
      localStringBuilder.append(paramCameraDevice.getId());
      Log.d("Camera", localStringBuilder.toString());
      boolean bool;
      if (Camera2CameraImpl.this.mCameraDevice == null) {
        bool = true;
      } else {
        bool = false;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unexpected onClose callback on camera device: ");
      localStringBuilder.append(paramCameraDevice);
      Preconditions.checkState(bool, localStringBuilder.toString());
      int i = Camera2CameraImpl.4.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[Camera2CameraImpl.this.mState.ordinal()];
      if (i != 2) {
        if (i != 5)
        {
          if (i != 7)
          {
            paramCameraDevice = new StringBuilder();
            paramCameraDevice.append("Camera closed while in state: ");
            paramCameraDevice.append(Camera2CameraImpl.this.mState);
            throw new IllegalStateException(paramCameraDevice.toString());
          }
        }
        else
        {
          Camera2CameraImpl.this.openCameraDevice();
          return;
        }
      }
      Preconditions.checkState(Camera2CameraImpl.this.isSessionCloseComplete());
      Camera2CameraImpl.this.finishClose();
    }
    
    public void onDisconnected(CameraDevice paramCameraDevice)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("CameraDevice.onDisconnected(): ");
      ((StringBuilder)localObject).append(paramCameraDevice.getId());
      Log.d("Camera", ((StringBuilder)localObject).toString());
      localObject = Camera2CameraImpl.this.mReleasedCaptureSessions.keySet().iterator();
      while (((Iterator)localObject).hasNext()) {
        ((CaptureSession)((Iterator)localObject).next()).forceClose();
      }
      Camera2CameraImpl.this.mCaptureSession.forceClose();
      onError(paramCameraDevice, 1);
    }
    
    public void onError(CameraDevice paramCameraDevice, int paramInt)
    {
      Camera2CameraImpl.this.mCameraDevice = paramCameraDevice;
      Camera2CameraImpl.this.mCameraDeviceError = paramInt;
      int i = Camera2CameraImpl.4.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[Camera2CameraImpl.this.mState.ordinal()];
      if (i != 2) {
        if ((i != 3) && (i != 4) && (i != 5))
        {
          if (i != 7)
          {
            paramCameraDevice = new StringBuilder();
            paramCameraDevice.append("onError() should not be possible from state: ");
            paramCameraDevice.append(Camera2CameraImpl.this.mState);
            throw new IllegalStateException(paramCameraDevice.toString());
          }
        }
        else
        {
          handleErrorOnOpen(paramCameraDevice, paramInt);
          return;
        }
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("CameraDevice.onError(): ");
      localStringBuilder.append(paramCameraDevice.getId());
      localStringBuilder.append(" with error: ");
      localStringBuilder.append(Camera2CameraImpl.getErrorMessage(paramInt));
      Log.e("Camera", localStringBuilder.toString());
      Camera2CameraImpl.this.closeCamera(false);
    }
    
    public void onOpened(CameraDevice paramCameraDevice)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("CameraDevice.onOpened(): ");
      localStringBuilder.append(paramCameraDevice.getId());
      Log.d("Camera", localStringBuilder.toString());
      Camera2CameraImpl.this.mCameraDevice = paramCameraDevice;
      Camera2CameraImpl.this.updateDefaultRequestBuilderToCameraControl(paramCameraDevice);
      Camera2CameraImpl.this.mCameraDeviceError = 0;
      int i = Camera2CameraImpl.4.$SwitchMap$androidx$camera$camera2$internal$Camera2CameraImpl$InternalState[Camera2CameraImpl.this.mState.ordinal()];
      if ((i != 2) && (i != 7))
      {
        if ((i != 4) && (i != 5))
        {
          paramCameraDevice = new StringBuilder();
          paramCameraDevice.append("onOpened() should not be possible from state: ");
          paramCameraDevice.append(Camera2CameraImpl.this.mState);
          throw new IllegalStateException(paramCameraDevice.toString());
        }
        Camera2CameraImpl.this.setState(Camera2CameraImpl.InternalState.OPENED);
        Camera2CameraImpl.this.openCaptureSession();
      }
      else
      {
        Preconditions.checkState(Camera2CameraImpl.this.isSessionCloseComplete());
        Camera2CameraImpl.this.mCameraDevice.close();
        Camera2CameraImpl.this.mCameraDevice = null;
      }
    }
  }
}
