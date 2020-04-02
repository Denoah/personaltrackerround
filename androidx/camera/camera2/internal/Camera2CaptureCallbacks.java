package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.view.Surface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class Camera2CaptureCallbacks
{
  private Camera2CaptureCallbacks() {}
  
  static CameraCaptureSession.CaptureCallback createComboCallback(List<CameraCaptureSession.CaptureCallback> paramList)
  {
    return new ComboSessionCaptureCallback(paramList);
  }
  
  public static CameraCaptureSession.CaptureCallback createComboCallback(CameraCaptureSession.CaptureCallback... paramVarArgs)
  {
    return createComboCallback(Arrays.asList(paramVarArgs));
  }
  
  public static CameraCaptureSession.CaptureCallback createNoOpCallback()
  {
    return new NoOpSessionCaptureCallback();
  }
  
  private static final class ComboSessionCaptureCallback
    extends CameraCaptureSession.CaptureCallback
  {
    private final List<CameraCaptureSession.CaptureCallback> mCallbacks = new ArrayList();
    
    ComboSessionCaptureCallback(List<CameraCaptureSession.CaptureCallback> paramList)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        paramList = (CameraCaptureSession.CaptureCallback)localIterator.next();
        if (!(paramList instanceof Camera2CaptureCallbacks.NoOpSessionCaptureCallback)) {
          this.mCallbacks.add(paramList);
        }
      }
    }
    
    public void onCaptureBufferLost(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, Surface paramSurface, long paramLong)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureBufferLost(paramCameraCaptureSession, paramCaptureRequest, paramSurface, paramLong);
      }
    }
    
    public void onCaptureCompleted(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, TotalCaptureResult paramTotalCaptureResult)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureCompleted(paramCameraCaptureSession, paramCaptureRequest, paramTotalCaptureResult);
      }
    }
    
    public void onCaptureFailed(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, CaptureFailure paramCaptureFailure)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureFailed(paramCameraCaptureSession, paramCaptureRequest, paramCaptureFailure);
      }
    }
    
    public void onCaptureProgressed(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, CaptureResult paramCaptureResult)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureProgressed(paramCameraCaptureSession, paramCaptureRequest, paramCaptureResult);
      }
    }
    
    public void onCaptureSequenceAborted(CameraCaptureSession paramCameraCaptureSession, int paramInt)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureSequenceAborted(paramCameraCaptureSession, paramInt);
      }
    }
    
    public void onCaptureSequenceCompleted(CameraCaptureSession paramCameraCaptureSession, int paramInt, long paramLong)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureSequenceCompleted(paramCameraCaptureSession, paramInt, paramLong);
      }
    }
    
    public void onCaptureStarted(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, long paramLong1, long paramLong2)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.CaptureCallback)localIterator.next()).onCaptureStarted(paramCameraCaptureSession, paramCaptureRequest, paramLong1, paramLong2);
      }
    }
  }
  
  static final class NoOpSessionCaptureCallback
    extends CameraCaptureSession.CaptureCallback
  {
    NoOpSessionCaptureCallback() {}
    
    public void onCaptureBufferLost(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, Surface paramSurface, long paramLong) {}
    
    public void onCaptureCompleted(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, TotalCaptureResult paramTotalCaptureResult) {}
    
    public void onCaptureFailed(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, CaptureFailure paramCaptureFailure) {}
    
    public void onCaptureProgressed(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, CaptureResult paramCaptureResult) {}
    
    public void onCaptureSequenceAborted(CameraCaptureSession paramCameraCaptureSession, int paramInt) {}
    
    public void onCaptureSequenceCompleted(CameraCaptureSession paramCameraCaptureSession, int paramInt, long paramLong) {}
    
    public void onCaptureStarted(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, long paramLong1, long paramLong2) {}
  }
}
