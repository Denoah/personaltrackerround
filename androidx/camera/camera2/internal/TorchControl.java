package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Log;
import androidx.camera.core.CameraControl.OperationCanceledException;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.concurrent.futures.CallbackToFutureAdapter.Resolver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.common.util.concurrent.ListenableFuture;

final class TorchControl
{
  private static final String TAG = "TorchControl";
  private final Object mActiveLock = new Object();
  private final Camera2CameraControl mCamera2CameraControl;
  private final Camera2CameraControl.CaptureResultListener mCaptureResultListener = new Camera2CameraControl.CaptureResultListener()
  {
    public boolean onCaptureResult(TotalCaptureResult paramAnonymousTotalCaptureResult)
    {
      synchronized (TorchControl.this.mEnableTorchLock)
      {
        if (TorchControl.this.mEnableTorchCompleter != null)
        {
          paramAnonymousTotalCaptureResult = (Integer)paramAnonymousTotalCaptureResult.getRequest().get(CaptureRequest.FLASH_MODE);
          int i;
          if ((paramAnonymousTotalCaptureResult != null) && (paramAnonymousTotalCaptureResult.intValue() == 2)) {
            i = 1;
          } else {
            i = 0;
          }
          if (i == TorchControl.this.mTargetTorchEnabled)
          {
            paramAnonymousTotalCaptureResult = TorchControl.this.mEnableTorchCompleter;
            TorchControl.this.mEnableTorchCompleter = null;
            break label85;
          }
        }
        paramAnonymousTotalCaptureResult = null;
        label85:
        if (paramAnonymousTotalCaptureResult != null) {
          paramAnonymousTotalCaptureResult.set(null);
        }
        return false;
      }
    }
  };
  CallbackToFutureAdapter.Completer<Void> mEnableTorchCompleter;
  final Object mEnableTorchLock = new Object();
  private final boolean mHasFlashUnit;
  private boolean mIsActive;
  boolean mTargetTorchEnabled;
  private final MutableLiveData<Integer> mTorchState;
  
  TorchControl(Camera2CameraControl paramCamera2CameraControl, CameraCharacteristics paramCameraCharacteristics)
  {
    this.mCamera2CameraControl = paramCamera2CameraControl;
    paramCamera2CameraControl = (Boolean)paramCameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
    boolean bool;
    if ((paramCamera2CameraControl != null) && (paramCamera2CameraControl.booleanValue())) {
      bool = true;
    } else {
      bool = false;
    }
    this.mHasFlashUnit = bool;
    this.mTorchState = new MutableLiveData(Integer.valueOf(0));
    this.mCamera2CameraControl.addCaptureResultListener(this.mCaptureResultListener);
  }
  
  private <T> void setLiveDataValue(MutableLiveData<T> paramMutableLiveData, T paramT)
  {
    if (Threads.isMainThread()) {
      paramMutableLiveData.setValue(paramT);
    } else {
      paramMutableLiveData.postValue(paramT);
    }
  }
  
  ListenableFuture<Void> enableTorch(boolean paramBoolean)
  {
    if (!this.mHasFlashUnit)
    {
      Log.d("TorchControl", "Unable to enableTorch due to there is no flash unit.");
      return Futures.immediateFailedFuture(new IllegalStateException("No flash unit"));
    }
    synchronized (this.mActiveLock)
    {
      if (!this.mIsActive)
      {
        localObject2 = new androidx/camera/core/CameraControl$OperationCanceledException;
        ((CameraControl.OperationCanceledException)localObject2).<init>("Camera is not active.");
        localObject2 = Futures.immediateFailedFuture((Throwable)localObject2);
        return localObject2;
      }
      Object localObject2 = new androidx/camera/camera2/internal/_$$Lambda$TorchControl$yfLA642SB9aJ7gO5KSM5aWHWKK4;
      ((_..Lambda.TorchControl.yfLA642SB9aJ7gO5KSM5aWHWKK4)localObject2).<init>(this, paramBoolean);
      localObject2 = CallbackToFutureAdapter.getFuture((CallbackToFutureAdapter.Resolver)localObject2);
      return localObject2;
    }
  }
  
  LiveData<Integer> getTorchState()
  {
    return this.mTorchState;
  }
  
  void setActive(boolean paramBoolean)
  {
    synchronized (this.mActiveLock)
    {
      if (this.mIsActive == paramBoolean) {
        return;
      }
      this.mIsActive = paramBoolean;
      Object localObject2 = this.mEnableTorchLock;
      Object localObject3 = null;
      Object localObject4 = null;
      if (!paramBoolean) {}
      try
      {
        if (this.mEnableTorchCompleter != null)
        {
          localObject4 = this.mEnableTorchCompleter;
          this.mEnableTorchCompleter = null;
        }
        localObject3 = localObject4;
        int i;
        if (this.mTargetTorchEnabled)
        {
          i = 1;
          this.mTargetTorchEnabled = false;
          this.mCamera2CameraControl.enableTorchInternal(false);
        }
        else
        {
          i = 0;
          localObject4 = localObject3;
        }
        if (i != 0) {
          setLiveDataValue(this.mTorchState, Integer.valueOf(0));
        }
        if (localObject4 != null)
        {
          localObject3 = new androidx/camera/core/CameraControl$OperationCanceledException;
          ((CameraControl.OperationCanceledException)localObject3).<init>("Camera is not active.");
          ((CallbackToFutureAdapter.Completer)localObject4).setException((Throwable)localObject3);
        }
        return;
      }
      finally {}
    }
  }
}
