package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.view.Surface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class CameraBurstCaptureCallback
  extends CameraCaptureSession.CaptureCallback
{
  final Map<CaptureRequest, List<CameraCaptureSession.CaptureCallback>> mCallbackMap = new HashMap();
  
  CameraBurstCaptureCallback() {}
  
  private List<CameraCaptureSession.CaptureCallback> getCallbacks(CaptureRequest paramCaptureRequest)
  {
    paramCaptureRequest = (List)this.mCallbackMap.get(paramCaptureRequest);
    if (paramCaptureRequest == null) {
      paramCaptureRequest = Collections.emptyList();
    }
    return paramCaptureRequest;
  }
  
  void addCamera2Callbacks(CaptureRequest paramCaptureRequest, List<CameraCaptureSession.CaptureCallback> paramList)
  {
    List localList = (List)this.mCallbackMap.get(paramCaptureRequest);
    if (localList != null)
    {
      ArrayList localArrayList = new ArrayList(paramList.size() + localList.size());
      localArrayList.addAll(paramList);
      localArrayList.addAll(localList);
      this.mCallbackMap.put(paramCaptureRequest, localArrayList);
    }
    else
    {
      this.mCallbackMap.put(paramCaptureRequest, paramList);
    }
  }
  
  public void onCaptureBufferLost(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, Surface paramSurface, long paramLong)
  {
    Iterator localIterator = getCallbacks(paramCaptureRequest).iterator();
    while (localIterator.hasNext()) {
      ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureBufferLost(paramCameraCaptureSession, paramCaptureRequest, paramSurface, paramLong);
    }
  }
  
  public void onCaptureCompleted(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, TotalCaptureResult paramTotalCaptureResult)
  {
    Iterator localIterator = getCallbacks(paramCaptureRequest).iterator();
    while (localIterator.hasNext()) {
      ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureCompleted(paramCameraCaptureSession, paramCaptureRequest, paramTotalCaptureResult);
    }
  }
  
  public void onCaptureFailed(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, CaptureFailure paramCaptureFailure)
  {
    Iterator localIterator = getCallbacks(paramCaptureRequest).iterator();
    while (localIterator.hasNext()) {
      ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureFailed(paramCameraCaptureSession, paramCaptureRequest, paramCaptureFailure);
    }
  }
  
  public void onCaptureProgressed(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, CaptureResult paramCaptureResult)
  {
    Iterator localIterator = getCallbacks(paramCaptureRequest).iterator();
    while (localIterator.hasNext()) {
      ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureProgressed(paramCameraCaptureSession, paramCaptureRequest, paramCaptureResult);
    }
  }
  
  public void onCaptureSequenceAborted(CameraCaptureSession paramCameraCaptureSession, int paramInt) {}
  
  public void onCaptureSequenceCompleted(CameraCaptureSession paramCameraCaptureSession, int paramInt, long paramLong) {}
  
  public void onCaptureStarted(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, long paramLong1, long paramLong2)
  {
    Iterator localIterator = getCallbacks(paramCaptureRequest).iterator();
    while (localIterator.hasNext()) {
      ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureStarted(paramCameraCaptureSession, paramCaptureRequest, paramLong1, paramLong2);
    }
  }
}
