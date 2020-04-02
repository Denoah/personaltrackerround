package androidx.camera.camera2.internal;

import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CameraInternal.State;
import androidx.camera.core.impl.LiveDataObservable;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.Observable.Observer;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executor;

final class CameraAvailabilityRegistry
{
  private static final boolean DEBUG = false;
  private static final String TAG = "AvailabilityRegistry";
  private final LiveDataObservable<Integer> mAvailableCameras;
  private final Map<CameraInternal, CameraInternal.State> mCameraStates = new HashMap();
  private StringBuilder mDebugString = null;
  private final Executor mExecutor;
  private final Object mLock = new Object();
  final int mMaxAllowedOpenedCameras;
  
  CameraAvailabilityRegistry(int paramInt, Executor paramExecutor)
  {
    this.mMaxAllowedOpenedCameras = paramInt;
    this.mExecutor = ((Executor)Preconditions.checkNotNull(paramExecutor));
    paramExecutor = new LiveDataObservable();
    this.mAvailableCameras = paramExecutor;
    paramExecutor.postValue(Integer.valueOf(paramInt));
  }
  
  private int recalculateAvailableCameras()
  {
    Iterator localIterator = this.mCameraStates.entrySet().iterator();
    for (int i = 0; localIterator.hasNext(); i++)
    {
      label17:
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if ((localEntry.getValue() == CameraInternal.State.CLOSED) || (localEntry.getValue() == CameraInternal.State.OPENING) || (localEntry.getValue() == CameraInternal.State.PENDING_OPEN)) {
        break label17;
      }
    }
    return Math.max(this.mMaxAllowedOpenedCameras - i, 0);
  }
  
  Observable<Integer> getAvailableCameraCount()
  {
    return this.mAvailableCameras;
  }
  
  void registerCamera(CameraInternal paramCameraInternal)
  {
    synchronized (this.mLock)
    {
      if (!this.mCameraStates.containsKey(paramCameraInternal))
      {
        this.mCameraStates.put(paramCameraInternal, null);
        Observable localObservable = paramCameraInternal.getCameraState();
        Executor localExecutor = this.mExecutor;
        Observable.Observer local1 = new androidx/camera/camera2/internal/CameraAvailabilityRegistry$1;
        local1.<init>(this, paramCameraInternal);
        localObservable.addObserver(localExecutor, local1);
      }
      return;
    }
  }
  
  void unregisterCamera(CameraInternal paramCameraInternal, Observable.Observer<CameraInternal.State> paramObserver)
  {
    synchronized (this.mLock)
    {
      paramCameraInternal.getCameraState().removeObserver(paramObserver);
      if (this.mCameraStates.remove(paramCameraInternal) == null) {
        return;
      }
      int i = recalculateAvailableCameras();
      this.mAvailableCameras.postValue(Integer.valueOf(i));
      return;
    }
  }
  
  void updateState(CameraInternal paramCameraInternal, CameraInternal.State paramState)
  {
    synchronized (this.mLock)
    {
      if ((this.mCameraStates.containsKey(paramCameraInternal)) && (this.mCameraStates.put(paramCameraInternal, paramState) != paramState))
      {
        int i = recalculateAvailableCameras();
        this.mAvailableCameras.postValue(Integer.valueOf(i));
        return;
      }
      return;
    }
  }
}
