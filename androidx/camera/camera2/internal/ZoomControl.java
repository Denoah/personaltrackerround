package androidx.camera.camera2.internal;

import android.graphics.Rect;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Looper;
import androidx.camera.core.CameraControl.OperationCanceledException;
import androidx.camera.core.ZoomState;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.core.internal.ImmutableZoomState;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.common.util.concurrent.ListenableFuture;

final class ZoomControl
{
  public static final float DEFAULT_ZOOM_RATIO = 1.0F;
  public static final float MIN_ZOOM = 1.0F;
  private static final String TAG = "ZoomControl";
  final Object mActiveLock = new Object();
  private final Camera2CameraControl mCamera2CameraControl;
  private Camera2CameraControl.CaptureResultListener mCaptureResultListener = new Camera2CameraControl.CaptureResultListener()
  {
    public boolean onCaptureResult(TotalCaptureResult paramAnonymousTotalCaptureResult)
    {
      synchronized (ZoomControl.this.mCompleterLock)
      {
        if (ZoomControl.this.mPendingZoomRatioCompleter != null)
        {
          paramAnonymousTotalCaptureResult = paramAnonymousTotalCaptureResult.getRequest();
          if (paramAnonymousTotalCaptureResult == null) {
            paramAnonymousTotalCaptureResult = null;
          } else {
            paramAnonymousTotalCaptureResult = (Rect)paramAnonymousTotalCaptureResult.get(CaptureRequest.SCALER_CROP_REGION);
          }
          if ((ZoomControl.this.mPendingZoomCropRegion != null) && (ZoomControl.this.mPendingZoomCropRegion.equals(paramAnonymousTotalCaptureResult)))
          {
            paramAnonymousTotalCaptureResult = ZoomControl.this.mPendingZoomRatioCompleter;
            ZoomControl.this.mPendingZoomRatioCompleter = null;
            ZoomControl.this.mPendingZoomCropRegion = null;
            break label98;
          }
        }
        paramAnonymousTotalCaptureResult = null;
        label98:
        if (paramAnonymousTotalCaptureResult != null) {
          paramAnonymousTotalCaptureResult.set(null);
        }
        return false;
      }
    }
  };
  final Object mCompleterLock = new Object();
  private final ZoomStateImpl mCurrentZoomState;
  private boolean mIsActive = false;
  Rect mPendingZoomCropRegion = null;
  CallbackToFutureAdapter.Completer<Void> mPendingZoomRatioCompleter;
  private final MutableLiveData<ZoomState> mZoomStateLiveData;
  
  ZoomControl(Camera2CameraControl paramCamera2CameraControl, CameraCharacteristics paramCameraCharacteristics)
  {
    this.mCamera2CameraControl = paramCamera2CameraControl;
    paramCameraCharacteristics = new ZoomStateImpl(getMaxDigitalZoom(paramCameraCharacteristics), 1.0F);
    this.mCurrentZoomState = paramCameraCharacteristics;
    paramCameraCharacteristics.setZoomRatio(1.0F);
    this.mZoomStateLiveData = new MutableLiveData(ImmutableZoomState.create(this.mCurrentZoomState));
    paramCamera2CameraControl.addCaptureResultListener(this.mCaptureResultListener);
  }
  
  static Rect getCropRectByRatio(Rect paramRect, float paramFloat)
  {
    float f1 = paramRect.width() / paramFloat;
    paramFloat = paramRect.height() / paramFloat;
    float f2 = (paramRect.width() - f1) / 2.0F;
    float f3 = (paramRect.height() - paramFloat) / 2.0F;
    return new Rect((int)f2, (int)f3, (int)(f2 + f1), (int)(f3 + paramFloat));
  }
  
  private static float getMaxDigitalZoom(CameraCharacteristics paramCameraCharacteristics)
  {
    paramCameraCharacteristics = (Float)paramCameraCharacteristics.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM);
    if (paramCameraCharacteristics == null) {
      return 1.0F;
    }
    return paramCameraCharacteristics.floatValue();
  }
  
  private ListenableFuture<Void> submitCameraZoomRatio(float paramFloat)
  {
    Rect localRect = getCropRectByRatio(this.mCamera2CameraControl.getSensorRect(), paramFloat);
    this.mCamera2CameraControl.setCropRegion(localRect);
    return CallbackToFutureAdapter.getFuture(new _..Lambda.ZoomControl.Drb_7VorNRO23tcp0rNs8amFoh4(this, localRect));
  }
  
  private void updateLiveData(ZoomState paramZoomState)
  {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      this.mZoomStateLiveData.setValue(paramZoomState);
    } else {
      this.mZoomStateLiveData.postValue(paramZoomState);
    }
  }
  
  LiveData<ZoomState> getZoomState()
  {
    return this.mZoomStateLiveData;
  }
  
  void setActive(boolean paramBoolean)
  {
    synchronized (this.mActiveLock)
    {
      if (this.mIsActive == paramBoolean) {
        return;
      }
      this.mIsActive = paramBoolean;
      if (!paramBoolean) {
        synchronized (this.mCompleterLock)
        {
          CallbackToFutureAdapter.Completer localCompleter;
          if (this.mPendingZoomRatioCompleter != null)
          {
            localCompleter = this.mPendingZoomRatioCompleter;
            this.mPendingZoomRatioCompleter = null;
            this.mPendingZoomCropRegion = null;
          }
          else
          {
            localCompleter = null;
          }
          i = 1;
          this.mCurrentZoomState.setZoomRatio(1.0F);
          updateLiveData(ImmutableZoomState.create(this.mCurrentZoomState));
        }
      }
      int i = 0;
      Object localObject4 = null;
      if (i != 0) {
        this.mCamera2CameraControl.setCropRegion(null);
      }
      if (localObject4 != null) {
        localObject4.setException(new CameraControl.OperationCanceledException("Camera is not active."));
      }
      return;
    }
  }
  
  ListenableFuture<Void> setLinearZoom(float paramFloat)
  {
    synchronized (this.mActiveLock)
    {
      Object localObject2;
      if (!this.mIsActive)
      {
        localObject2 = new androidx/camera/core/CameraControl$OperationCanceledException;
        ((CameraControl.OperationCanceledException)localObject2).<init>("Camera is not active.");
        localObject2 = Futures.immediateFailedFuture((Throwable)localObject2);
        return localObject2;
      }
      try
      {
        this.mCurrentZoomState.setLinearZoom(paramFloat);
        updateLiveData(ImmutableZoomState.create(this.mCurrentZoomState));
        localObject2 = submitCameraZoomRatio(this.mCurrentZoomState.getZoomRatio());
        return localObject2;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        ListenableFuture localListenableFuture = Futures.immediateFailedFuture(localIllegalArgumentException);
        return localListenableFuture;
      }
    }
  }
  
  ListenableFuture<Void> setZoomRatio(float paramFloat)
  {
    synchronized (this.mActiveLock)
    {
      Object localObject2;
      if (!this.mIsActive)
      {
        localObject2 = new androidx/camera/core/CameraControl$OperationCanceledException;
        ((CameraControl.OperationCanceledException)localObject2).<init>("Camera is not active.");
        localObject2 = Futures.immediateFailedFuture((Throwable)localObject2);
        return localObject2;
      }
      try
      {
        this.mCurrentZoomState.setZoomRatio(paramFloat);
        updateLiveData(ImmutableZoomState.create(this.mCurrentZoomState));
        localObject2 = submitCameraZoomRatio(paramFloat);
        return localObject2;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        ListenableFuture localListenableFuture = Futures.immediateFailedFuture(localIllegalArgumentException);
        return localListenableFuture;
      }
    }
  }
}
