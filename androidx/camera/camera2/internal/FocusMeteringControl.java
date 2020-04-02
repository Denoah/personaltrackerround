package androidx.camera.camera2.internal;

import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.params.MeteringRectangle;
import android.os.Build.VERSION;
import android.util.Rational;
import androidx.camera.camera2.impl.Camera2ImplConfig.Builder;
import androidx.camera.core.CameraControl.OperationCanceledException;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.FocusMeteringResult;
import androidx.camera.core.MeteringPoint;
import androidx.camera.core.impl.CaptureConfig.Builder;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class FocusMeteringControl
{
  private static final String TAG = "FocusMeteringControl";
  private MeteringRectangle[] mAeRects = new MeteringRectangle[0];
  private MeteringRectangle[] mAfRects = new MeteringRectangle[0];
  private ScheduledFuture<?> mAutoCancelHandle;
  private MeteringRectangle[] mAwbRects = new MeteringRectangle[0];
  private final Camera2CameraControl mCameraControl;
  Integer mCurrentAfState = Integer.valueOf(0);
  private FocusMeteringAction mCurrentFocusMeteringAction;
  MeteringRectangle[] mDefaultAeRects = new MeteringRectangle[0];
  MeteringRectangle[] mDefaultAfRects = new MeteringRectangle[0];
  MeteringRectangle[] mDefaultAwbRects = new MeteringRectangle[0];
  final Executor mExecutor;
  long mFocusTimeoutCounter = 0L;
  private volatile boolean mIsActive = false;
  boolean mIsAutoFocusCompleted = false;
  boolean mIsFocusSuccessful = false;
  private boolean mIsInAfAutoMode = false;
  CallbackToFutureAdapter.Completer<FocusMeteringResult> mRunningActionCompleter = null;
  CallbackToFutureAdapter.Completer<Void> mRunningCancelCompleter = null;
  private final ScheduledExecutorService mScheduler;
  private Camera2CameraControl.CaptureResultListener mSessionListenerForCancel = null;
  private Camera2CameraControl.CaptureResultListener mSessionListenerForFocus = null;
  
  FocusMeteringControl(Camera2CameraControl paramCamera2CameraControl, ScheduledExecutorService paramScheduledExecutorService, Executor paramExecutor)
  {
    this.mCameraControl = paramCamera2CameraControl;
    this.mExecutor = paramExecutor;
    this.mScheduler = paramScheduledExecutorService;
  }
  
  private void completeActionFuture(boolean paramBoolean)
  {
    CallbackToFutureAdapter.Completer localCompleter = this.mRunningActionCompleter;
    if (localCompleter != null)
    {
      localCompleter.set(FocusMeteringResult.create(paramBoolean));
      this.mRunningActionCompleter = null;
    }
  }
  
  private void completeCancelFuture()
  {
    CallbackToFutureAdapter.Completer localCompleter = this.mRunningCancelCompleter;
    if (localCompleter != null)
    {
      localCompleter.set(null);
      this.mRunningCancelCompleter = null;
    }
  }
  
  private void disableAutoCancel()
  {
    ScheduledFuture localScheduledFuture = this.mAutoCancelHandle;
    if (localScheduledFuture != null)
    {
      localScheduledFuture.cancel(true);
      this.mAutoCancelHandle = null;
    }
  }
  
  private void executeMeteringAction(MeteringRectangle[] paramArrayOfMeteringRectangle1, MeteringRectangle[] paramArrayOfMeteringRectangle2, MeteringRectangle[] paramArrayOfMeteringRectangle3, FocusMeteringAction paramFocusMeteringAction)
  {
    this.mCameraControl.removeCaptureResultListener(this.mSessionListenerForFocus);
    disableAutoCancel();
    this.mAfRects = paramArrayOfMeteringRectangle1;
    this.mAeRects = paramArrayOfMeteringRectangle2;
    this.mAwbRects = paramArrayOfMeteringRectangle3;
    if (shouldTriggerAF())
    {
      this.mIsInAfAutoMode = true;
      this.mIsAutoFocusCompleted = false;
      this.mIsFocusSuccessful = false;
      this.mCameraControl.updateSessionConfig();
      triggerAf();
    }
    else
    {
      this.mIsInAfAutoMode = false;
      this.mIsAutoFocusCompleted = true;
      this.mIsFocusSuccessful = false;
      this.mCameraControl.updateSessionConfig();
    }
    this.mCurrentAfState = Integer.valueOf(0);
    paramArrayOfMeteringRectangle1 = new _..Lambda.FocusMeteringControl.myFOvxwuOccCZDq_wHBp6yswlIg(this, isAfModeSupported(), paramArrayOfMeteringRectangle1, paramArrayOfMeteringRectangle2, paramArrayOfMeteringRectangle3);
    this.mSessionListenerForFocus = paramArrayOfMeteringRectangle1;
    this.mCameraControl.addCaptureResultListener(paramArrayOfMeteringRectangle1);
    if (paramFocusMeteringAction.isAutoCancelEnabled())
    {
      long l = this.mFocusTimeoutCounter + 1L;
      this.mFocusTimeoutCounter = l;
      paramArrayOfMeteringRectangle1 = new _..Lambda.FocusMeteringControl.d6WwVXBeWXIsWhvPS_3v3isXXpE(this, l);
      this.mAutoCancelHandle = this.mScheduler.schedule(paramArrayOfMeteringRectangle1, paramFocusMeteringAction.getAutoCancelDurationInMillis(), TimeUnit.MILLISECONDS);
    }
  }
  
  private void failActionFuture(String paramString)
  {
    this.mCameraControl.removeCaptureResultListener(this.mSessionListenerForFocus);
    CallbackToFutureAdapter.Completer localCompleter = this.mRunningActionCompleter;
    if (localCompleter != null)
    {
      localCompleter.setException(new CameraControl.OperationCanceledException(paramString));
      this.mRunningActionCompleter = null;
    }
  }
  
  private void failCancelFuture(String paramString)
  {
    this.mCameraControl.removeCaptureResultListener(this.mSessionListenerForCancel);
    CallbackToFutureAdapter.Completer localCompleter = this.mRunningCancelCompleter;
    if (localCompleter != null)
    {
      localCompleter.setException(new CameraControl.OperationCanceledException(paramString));
      this.mRunningCancelCompleter = null;
    }
  }
  
  private int getDefaultTemplate()
  {
    return 1;
  }
  
  private PointF getFovAdjustedPoint(MeteringPoint paramMeteringPoint, Rational paramRational1, Rational paramRational2)
  {
    if (paramMeteringPoint.getSurfaceAspectRatio() != null) {
      paramRational2 = paramMeteringPoint.getSurfaceAspectRatio();
    }
    paramMeteringPoint = new PointF(paramMeteringPoint.getX(), paramMeteringPoint.getY());
    if (!paramRational2.equals(paramRational1))
    {
      float f;
      if (paramRational2.compareTo(paramRational1) > 0)
      {
        f = (float)(paramRational2.doubleValue() / paramRational1.doubleValue());
        paramMeteringPoint.y = (((float)((f - 1.0D) / 2.0D) + paramMeteringPoint.y) * (1.0F / f));
      }
      else
      {
        f = (float)(paramRational1.doubleValue() / paramRational2.doubleValue());
        paramMeteringPoint.x = (((float)((f - 1.0D) / 2.0D) + paramMeteringPoint.x) * (1.0F / f));
      }
    }
    return paramMeteringPoint;
  }
  
  private MeteringRectangle getMeteringRect(MeteringPoint paramMeteringPoint, PointF paramPointF, Rect paramRect)
  {
    int i = (int)(paramRect.left + paramPointF.x * paramRect.width());
    int j = (int)(paramRect.top + paramPointF.y * paramRect.height());
    int k = (int)(paramMeteringPoint.getSize() * paramRect.width());
    int m = (int)(paramMeteringPoint.getSize() * paramRect.height());
    k /= 2;
    m /= 2;
    paramMeteringPoint = new Rect(i - k, j - m, i + k, j + m);
    paramMeteringPoint.left = rangeLimit(paramMeteringPoint.left, paramRect.right, paramRect.left);
    paramMeteringPoint.right = rangeLimit(paramMeteringPoint.right, paramRect.right, paramRect.left);
    paramMeteringPoint.top = rangeLimit(paramMeteringPoint.top, paramRect.bottom, paramRect.top);
    paramMeteringPoint.bottom = rangeLimit(paramMeteringPoint.bottom, paramRect.bottom, paramRect.top);
    return new MeteringRectangle(paramMeteringPoint, 1000);
  }
  
  private static int getRegionCount(MeteringRectangle[] paramArrayOfMeteringRectangle)
  {
    if (paramArrayOfMeteringRectangle == null) {
      return 0;
    }
    return paramArrayOfMeteringRectangle.length;
  }
  
  private static boolean hasEqualRegions(MeteringRectangle[] paramArrayOfMeteringRectangle1, MeteringRectangle[] paramArrayOfMeteringRectangle2)
  {
    if ((getRegionCount(paramArrayOfMeteringRectangle1) == 0) && (getRegionCount(paramArrayOfMeteringRectangle2) == 0)) {
      return true;
    }
    if (getRegionCount(paramArrayOfMeteringRectangle1) != getRegionCount(paramArrayOfMeteringRectangle2)) {
      return false;
    }
    if ((paramArrayOfMeteringRectangle1 != null) && (paramArrayOfMeteringRectangle2 != null)) {
      for (int i = 0; i < paramArrayOfMeteringRectangle1.length; i++) {
        if (!paramArrayOfMeteringRectangle1[i].equals(paramArrayOfMeteringRectangle2[i])) {
          return false;
        }
      }
    }
    return true;
  }
  
  private boolean isAfModeSupported()
  {
    Camera2CameraControl localCamera2CameraControl = this.mCameraControl;
    boolean bool = true;
    if (localCamera2CameraControl.getSupportedAfMode(1) != 1) {
      bool = false;
    }
    return bool;
  }
  
  private int rangeLimit(int paramInt1, int paramInt2, int paramInt3)
  {
    return Math.min(Math.max(paramInt1, paramInt3), paramInt2);
  }
  
  private boolean shouldTriggerAF()
  {
    boolean bool;
    if (this.mAfRects.length > 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  void addFocusMeteringOptions(Camera2ImplConfig.Builder paramBuilder)
  {
    int i;
    if (this.mIsInAfAutoMode) {
      i = 1;
    } else {
      i = 4;
    }
    paramBuilder.setCaptureRequestOption(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(this.mCameraControl.getSupportedAfMode(i)));
    if (this.mAfRects.length != 0) {
      paramBuilder.setCaptureRequestOption(CaptureRequest.CONTROL_AF_REGIONS, this.mAfRects);
    }
    if (this.mAeRects.length != 0) {
      paramBuilder.setCaptureRequestOption(CaptureRequest.CONTROL_AE_REGIONS, this.mAeRects);
    }
    if (this.mAwbRects.length != 0) {
      paramBuilder.setCaptureRequestOption(CaptureRequest.CONTROL_AWB_REGIONS, this.mAwbRects);
    }
  }
  
  void cancelAfAeTrigger(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!this.mIsActive) {
      return;
    }
    CaptureConfig.Builder localBuilder = new CaptureConfig.Builder();
    localBuilder.setUseRepeatingSurface(true);
    localBuilder.setTemplateType(getDefaultTemplate());
    Camera2ImplConfig.Builder localBuilder1 = new Camera2ImplConfig.Builder();
    if (paramBoolean1) {
      localBuilder1.setCaptureRequestOption(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(2));
    }
    if ((Build.VERSION.SDK_INT >= 23) && (paramBoolean2)) {
      localBuilder1.setCaptureRequestOption(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, Integer.valueOf(2));
    }
    localBuilder.addImplementationOptions(localBuilder1.build());
    this.mCameraControl.submitCaptureRequestsInternal(Collections.singletonList(localBuilder.build()));
  }
  
  ListenableFuture<Void> cancelFocusAndMetering()
  {
    return CallbackToFutureAdapter.getFuture(new _..Lambda.FocusMeteringControl.FJquylG_HqkA7eofMN5jopP8hJg(this));
  }
  
  void cancelFocusAndMeteringInternal(CallbackToFutureAdapter.Completer<Void> paramCompleter)
  {
    failCancelFuture("Cancelled by another cancelFocusAndMetering()");
    failActionFuture("Cancelled by cancelFocusAndMetering()");
    this.mRunningCancelCompleter = paramCompleter;
    disableAutoCancel();
    if (this.mRunningCancelCompleter != null)
    {
      paramCompleter = new _..Lambda.FocusMeteringControl.vgaNwg87Tv3HiROj1CRAp20PA_Q(this, this.mCameraControl.getSupportedAfMode(4));
      this.mSessionListenerForCancel = paramCompleter;
      this.mCameraControl.addCaptureResultListener(paramCompleter);
    }
    if (shouldTriggerAF()) {
      cancelAfAeTrigger(true, false);
    }
    this.mAfRects = new MeteringRectangle[0];
    this.mAeRects = new MeteringRectangle[0];
    this.mAwbRects = new MeteringRectangle[0];
    this.mIsInAfAutoMode = false;
    this.mCameraControl.updateSessionConfig();
    this.mCurrentFocusMeteringAction = null;
  }
  
  void cancelFocusAndMeteringWithoutAsyncResult()
  {
    cancelFocusAndMeteringInternal(null);
  }
  
  void setActive(boolean paramBoolean)
  {
    if (paramBoolean == this.mIsActive) {
      return;
    }
    this.mIsActive = paramBoolean;
    if (!this.mIsActive) {
      this.mExecutor.execute(new _..Lambda.FocusMeteringControl.Fo24HuX4QgnBob7n82jQvBqN3XU(this));
    }
  }
  
  void setDefaultRequestBuilder(CaptureRequest.Builder paramBuilder)
  {
    this.mDefaultAfRects = ((MeteringRectangle[])paramBuilder.get(CaptureRequest.CONTROL_AF_REGIONS));
    this.mDefaultAeRects = ((MeteringRectangle[])paramBuilder.get(CaptureRequest.CONTROL_AE_REGIONS));
    this.mDefaultAwbRects = ((MeteringRectangle[])paramBuilder.get(CaptureRequest.CONTROL_AWB_REGIONS));
  }
  
  ListenableFuture<FocusMeteringResult> startFocusAndMetering(FocusMeteringAction paramFocusMeteringAction, Rational paramRational)
  {
    return CallbackToFutureAdapter.getFuture(new _..Lambda.FocusMeteringControl.Gz_st8wNW3AE79rbLhzPR3lH1sM(this, paramFocusMeteringAction, paramRational));
  }
  
  void startFocusAndMeteringInternal(CallbackToFutureAdapter.Completer<FocusMeteringResult> paramCompleter, FocusMeteringAction paramFocusMeteringAction, Rational paramRational)
  {
    if (!this.mIsActive)
    {
      paramCompleter.setException(new CameraControl.OperationCanceledException("Camera is not active."));
      return;
    }
    if ((paramFocusMeteringAction.getMeteringPointsAf().isEmpty()) && (paramFocusMeteringAction.getMeteringPointsAe().isEmpty()) && (paramFocusMeteringAction.getMeteringPointsAwb().isEmpty()))
    {
      paramCompleter.setException(new IllegalArgumentException("No AF/AE/AWB MeteringPoints are added."));
      return;
    }
    int i = Math.min(paramFocusMeteringAction.getMeteringPointsAf().size(), this.mCameraControl.getMaxAfRegionCount());
    int j = Math.min(paramFocusMeteringAction.getMeteringPointsAe().size(), this.mCameraControl.getMaxAeRegionCount());
    int k = Math.min(paramFocusMeteringAction.getMeteringPointsAwb().size(), this.mCameraControl.getMaxAwbRegionCount());
    if (i + j + k <= 0)
    {
      paramCompleter.setException(new IllegalArgumentException("None of the specified AF/AE/AWB MeteringPoints is supported on this camera."));
      return;
    }
    Object localObject1 = new ArrayList();
    Object localObject2 = new ArrayList();
    Object localObject3 = new ArrayList();
    if (i > 0) {
      ((List)localObject1).addAll(paramFocusMeteringAction.getMeteringPointsAf().subList(0, i));
    }
    if (j > 0) {
      ((List)localObject2).addAll(paramFocusMeteringAction.getMeteringPointsAe().subList(0, j));
    }
    if (k > 0) {
      ((List)localObject3).addAll(paramFocusMeteringAction.getMeteringPointsAwb().subList(0, k));
    }
    failActionFuture("Cancelled by another startFocusAndMetering()");
    failCancelFuture("Cancelled by another startFocusAndMetering()");
    if (this.mCurrentFocusMeteringAction != null) {
      cancelFocusAndMeteringWithoutAsyncResult();
    }
    disableAutoCancel();
    this.mCurrentFocusMeteringAction = paramFocusMeteringAction;
    this.mRunningActionCompleter = paramCompleter;
    Rect localRect = this.mCameraControl.getCropSensorRegion();
    Rational localRational = new Rational(localRect.width(), localRect.height());
    paramCompleter = paramRational;
    if (paramRational == null) {
      paramCompleter = localRational;
    }
    ArrayList localArrayList1 = new ArrayList();
    paramRational = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    localObject1 = ((List)localObject1).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      MeteringPoint localMeteringPoint = (MeteringPoint)((Iterator)localObject1).next();
      localArrayList1.add(getMeteringRect(localMeteringPoint, getFovAdjustedPoint(localMeteringPoint, localRational, paramCompleter), localRect));
    }
    localObject2 = ((List)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (MeteringPoint)((Iterator)localObject2).next();
      paramRational.add(getMeteringRect((MeteringPoint)localObject1, getFovAdjustedPoint((MeteringPoint)localObject1, localRational, paramCompleter), localRect));
    }
    localObject2 = ((List)localObject3).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (MeteringPoint)((Iterator)localObject2).next();
      localArrayList2.add(getMeteringRect((MeteringPoint)localObject3, getFovAdjustedPoint((MeteringPoint)localObject3, localRational, paramCompleter), localRect));
    }
    executeMeteringAction((MeteringRectangle[])localArrayList1.toArray(new MeteringRectangle[0]), (MeteringRectangle[])paramRational.toArray(new MeteringRectangle[0]), (MeteringRectangle[])localArrayList2.toArray(new MeteringRectangle[0]), paramFocusMeteringAction);
  }
  
  void triggerAePrecapture()
  {
    if (!this.mIsActive) {
      return;
    }
    CaptureConfig.Builder localBuilder = new CaptureConfig.Builder();
    localBuilder.setTemplateType(getDefaultTemplate());
    localBuilder.setUseRepeatingSurface(true);
    Camera2ImplConfig.Builder localBuilder1 = new Camera2ImplConfig.Builder();
    localBuilder1.setCaptureRequestOption(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, Integer.valueOf(1));
    localBuilder.addImplementationOptions(localBuilder1.build());
    this.mCameraControl.submitCaptureRequestsInternal(Collections.singletonList(localBuilder.build()));
  }
  
  void triggerAf()
  {
    if (!this.mIsActive) {
      return;
    }
    CaptureConfig.Builder localBuilder = new CaptureConfig.Builder();
    localBuilder.setTemplateType(getDefaultTemplate());
    localBuilder.setUseRepeatingSurface(true);
    Camera2ImplConfig.Builder localBuilder1 = new Camera2ImplConfig.Builder();
    localBuilder1.setCaptureRequestOption(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(1));
    localBuilder.addImplementationOptions(localBuilder1.build());
    this.mCameraControl.submitCaptureRequestsInternal(Collections.singletonList(localBuilder.build()));
  }
}
