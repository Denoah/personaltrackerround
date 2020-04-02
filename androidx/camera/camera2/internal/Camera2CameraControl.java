package androidx.camera.camera2.internal;

import android.graphics.Rect;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Rational;
import androidx.camera.camera2.impl.Camera2ImplConfig.Builder;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.impl.CameraControlInternal;
import androidx.camera.core.impl.CameraControlInternal.ControlUpdateCallback;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.SessionConfig.Builder;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

final class Camera2CameraControl
  implements CameraControlInternal
{
  private final AeFpsRange mAeFpsRange;
  private final CameraCharacteristics mCameraCharacteristics;
  private final CameraControlInternal.ControlUpdateCallback mControlUpdateCallback;
  private Rect mCropRect = null;
  final Executor mExecutor;
  private volatile int mFlashMode = 2;
  private final FocusMeteringControl mFocusMeteringControl;
  private volatile boolean mIsTorchOn = false;
  volatile Rational mPreviewAspectRatio = null;
  final CameraControlSessionCallback mSessionCallback;
  private final SessionConfig.Builder mSessionConfigBuilder = new SessionConfig.Builder();
  private final TorchControl mTorchControl;
  private final ZoomControl mZoomControl;
  
  Camera2CameraControl(CameraCharacteristics paramCameraCharacteristics, ScheduledExecutorService paramScheduledExecutorService, Executor paramExecutor, CameraControlInternal.ControlUpdateCallback paramControlUpdateCallback)
  {
    this.mCameraCharacteristics = paramCameraCharacteristics;
    this.mControlUpdateCallback = paramControlUpdateCallback;
    this.mExecutor = paramExecutor;
    this.mSessionCallback = new CameraControlSessionCallback(this.mExecutor);
    this.mSessionConfigBuilder.setTemplateType(getDefaultTemplate());
    this.mSessionConfigBuilder.addRepeatingCameraCaptureCallback(CaptureCallbackContainer.create(this.mSessionCallback));
    this.mFocusMeteringControl = new FocusMeteringControl(this, paramScheduledExecutorService, this.mExecutor);
    this.mZoomControl = new ZoomControl(this, this.mCameraCharacteristics);
    this.mTorchControl = new TorchControl(this, this.mCameraCharacteristics);
    this.mAeFpsRange = new AeFpsRange(this.mCameraCharacteristics);
    this.mExecutor.execute(new _..Lambda.o3ojAeQw5uAR3XMBxVrGNZPoNY0(this));
  }
  
  private int getSupportedAeMode(int paramInt)
  {
    int[] arrayOfInt = (int[])this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES);
    if (arrayOfInt == null) {
      return 0;
    }
    if (isModeInList(paramInt, arrayOfInt)) {
      return paramInt;
    }
    if (isModeInList(1, arrayOfInt)) {
      return 1;
    }
    return 0;
  }
  
  private int getSupportedAwbMode(int paramInt)
  {
    int[] arrayOfInt = (int[])this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES);
    if (arrayOfInt == null) {
      return 0;
    }
    if (isModeInList(paramInt, arrayOfInt)) {
      return paramInt;
    }
    if (isModeInList(1, arrayOfInt)) {
      return 1;
    }
    return 0;
  }
  
  private boolean isModeInList(int paramInt, int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++) {
      if (paramInt == paramArrayOfInt[j]) {
        return true;
      }
    }
    return false;
  }
  
  void addCaptureResultListener(CaptureResultListener paramCaptureResultListener)
  {
    this.mSessionCallback.addListener(paramCaptureResultListener);
  }
  
  public void cancelAfAeTrigger(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mExecutor.execute(new _..Lambda.Camera2CameraControl.Jjzeg8tXscfy6ODM1OG_vXIvOO4(this, paramBoolean1, paramBoolean2));
  }
  
  public ListenableFuture<Void> cancelFocusAndMetering()
  {
    return this.mFocusMeteringControl.cancelFocusAndMetering();
  }
  
  public ListenableFuture<Void> enableTorch(boolean paramBoolean)
  {
    return this.mTorchControl.enableTorch(paramBoolean);
  }
  
  void enableTorchInternal(boolean paramBoolean)
  {
    this.mExecutor.execute(new _..Lambda.Camera2CameraControl._uR8xQejdgJo8dwAPBtWHbuq1xU(this, paramBoolean));
  }
  
  Rect getCropSensorRegion()
  {
    Rect localRect1 = this.mCropRect;
    Rect localRect2 = localRect1;
    if (localRect1 == null) {
      localRect2 = getSensorRect();
    }
    return localRect2;
  }
  
  int getDefaultTemplate()
  {
    return 1;
  }
  
  public int getFlashMode()
  {
    return this.mFlashMode;
  }
  
  int getMaxAeRegionCount()
  {
    Integer localInteger = (Integer)this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AE);
    int i;
    if (localInteger == null) {
      i = 0;
    } else {
      i = localInteger.intValue();
    }
    return i;
  }
  
  int getMaxAfRegionCount()
  {
    Integer localInteger = (Integer)this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AF);
    int i;
    if (localInteger == null) {
      i = 0;
    } else {
      i = localInteger.intValue();
    }
    return i;
  }
  
  int getMaxAwbRegionCount()
  {
    Integer localInteger = (Integer)this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_MAX_REGIONS_AWB);
    int i;
    if (localInteger == null) {
      i = 0;
    } else {
      i = localInteger.intValue();
    }
    return i;
  }
  
  Rect getSensorRect()
  {
    return (Rect)Preconditions.checkNotNull(this.mCameraCharacteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE));
  }
  
  Config getSessionOptions()
  {
    Camera2ImplConfig.Builder localBuilder = new Camera2ImplConfig.Builder();
    localBuilder.setCaptureRequestOption(CaptureRequest.CONTROL_MODE, Integer.valueOf(1));
    this.mFocusMeteringControl.addFocusMeteringOptions(localBuilder);
    this.mAeFpsRange.addAeFpsRangeOptions(localBuilder);
    boolean bool = this.mIsTorchOn;
    int i = 2;
    if (bool)
    {
      localBuilder.setCaptureRequestOption(CaptureRequest.FLASH_MODE, Integer.valueOf(2));
    }
    else
    {
      int j = this.mFlashMode;
      if (j == 0) {
        break label86;
      }
      if (j == 1) {
        break label84;
      }
    }
    i = 1;
    break label86;
    label84:
    i = 3;
    label86:
    localBuilder.setCaptureRequestOption(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(getSupportedAeMode(i)));
    localBuilder.setCaptureRequestOption(CaptureRequest.CONTROL_AWB_MODE, Integer.valueOf(getSupportedAwbMode(1)));
    if (this.mCropRect != null) {
      localBuilder.setCaptureRequestOption(CaptureRequest.SCALER_CROP_REGION, this.mCropRect);
    }
    return localBuilder.build();
  }
  
  int getSupportedAfMode(int paramInt)
  {
    int[] arrayOfInt = (int[])this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
    if (arrayOfInt == null) {
      return 0;
    }
    if (isModeInList(paramInt, arrayOfInt)) {
      return paramInt;
    }
    if (isModeInList(4, arrayOfInt)) {
      return 4;
    }
    if (isModeInList(1, arrayOfInt)) {
      return 1;
    }
    return 0;
  }
  
  public TorchControl getTorchControl()
  {
    return this.mTorchControl;
  }
  
  public ZoomControl getZoomControl()
  {
    return this.mZoomControl;
  }
  
  void removeCaptureResultListener(CaptureResultListener paramCaptureResultListener)
  {
    this.mSessionCallback.removeListener(paramCaptureResultListener);
  }
  
  void setActive(boolean paramBoolean)
  {
    this.mFocusMeteringControl.setActive(paramBoolean);
    this.mZoomControl.setActive(paramBoolean);
    this.mTorchControl.setActive(paramBoolean);
  }
  
  public void setCropRegion(Rect paramRect)
  {
    this.mExecutor.execute(new _..Lambda.Camera2CameraControl.n_55emHrx23eyFO4AOfALgxleaM(this, paramRect));
  }
  
  void setCropRegionInternal(Rect paramRect)
  {
    this.mCropRect = paramRect;
    updateSessionConfig();
  }
  
  public void setDefaultRequestBuilder(CaptureRequest.Builder paramBuilder)
  {
    this.mFocusMeteringControl.setDefaultRequestBuilder(paramBuilder);
  }
  
  public void setFlashMode(int paramInt)
  {
    this.mFlashMode = paramInt;
    this.mExecutor.execute(new _..Lambda.o3ojAeQw5uAR3XMBxVrGNZPoNY0(this));
  }
  
  public ListenableFuture<Void> setLinearZoom(float paramFloat)
  {
    return this.mZoomControl.setLinearZoom(paramFloat);
  }
  
  public void setPreviewAspectRatio(Rational paramRational)
  {
    this.mPreviewAspectRatio = paramRational;
  }
  
  public ListenableFuture<Void> setZoomRatio(float paramFloat)
  {
    return this.mZoomControl.setZoomRatio(paramFloat);
  }
  
  public ListenableFuture<FocusMeteringResult> startFocusAndMetering(FocusMeteringAction paramFocusMeteringAction)
  {
    return this.mFocusMeteringControl.startFocusAndMetering(paramFocusMeteringAction, this.mPreviewAspectRatio);
  }
  
  public void submitCaptureRequests(List<CaptureConfig> paramList)
  {
    this.mExecutor.execute(new _..Lambda.Camera2CameraControl.GERqx0LkpfTy59lJbP8w9DjVI18(this, paramList));
  }
  
  void submitCaptureRequestsInternal(List<CaptureConfig> paramList)
  {
    this.mControlUpdateCallback.onCameraControlCaptureRequests(paramList);
  }
  
  public void triggerAePrecapture()
  {
    Executor localExecutor = this.mExecutor;
    FocusMeteringControl localFocusMeteringControl = this.mFocusMeteringControl;
    localFocusMeteringControl.getClass();
    localExecutor.execute(new _..Lambda.CYPdi4_uPx6_TvmhN2hVOTmRnZY(localFocusMeteringControl));
  }
  
  public void triggerAf()
  {
    Executor localExecutor = this.mExecutor;
    FocusMeteringControl localFocusMeteringControl = this.mFocusMeteringControl;
    localFocusMeteringControl.getClass();
    localExecutor.execute(new _..Lambda.jMLZkVYtb17_sGVEMgJeaqSwRqM(localFocusMeteringControl));
  }
  
  void updateSessionConfig()
  {
    this.mSessionConfigBuilder.setImplementationOptions(getSessionOptions());
    this.mControlUpdateCallback.onCameraControlUpdateSessionConfig(this.mSessionConfigBuilder.build());
  }
  
  static final class CameraControlSessionCallback
    extends CameraCaptureSession.CaptureCallback
  {
    private final Executor mExecutor;
    final Set<Camera2CameraControl.CaptureResultListener> mResultListeners = new HashSet();
    
    CameraControlSessionCallback(Executor paramExecutor)
    {
      this.mExecutor = paramExecutor;
    }
    
    void addListener(Camera2CameraControl.CaptureResultListener paramCaptureResultListener)
    {
      this.mResultListeners.add(paramCaptureResultListener);
    }
    
    public void onCaptureCompleted(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, TotalCaptureResult paramTotalCaptureResult)
    {
      this.mExecutor.execute(new _..Lambda.Camera2CameraControl.CameraControlSessionCallback.D22_IA_eQmzTWvkNwlgaZNWM8E8(this, paramTotalCaptureResult));
    }
    
    void removeListener(Camera2CameraControl.CaptureResultListener paramCaptureResultListener)
    {
      this.mResultListeners.remove(paramCaptureResultListener);
    }
  }
  
  static abstract interface CaptureResultListener
  {
    public abstract boolean onCaptureResult(TotalCaptureResult paramTotalCaptureResult);
  }
}
