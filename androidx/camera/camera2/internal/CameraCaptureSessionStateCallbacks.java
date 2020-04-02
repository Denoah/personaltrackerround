package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.view.Surface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class CameraCaptureSessionStateCallbacks
{
  private CameraCaptureSessionStateCallbacks() {}
  
  public static CameraCaptureSession.StateCallback createComboCallback(List<CameraCaptureSession.StateCallback> paramList)
  {
    if (paramList.isEmpty()) {
      return createNoOpCallback();
    }
    if (paramList.size() == 1) {
      return (CameraCaptureSession.StateCallback)paramList.get(0);
    }
    return new ComboSessionStateCallback(paramList);
  }
  
  public static CameraCaptureSession.StateCallback createComboCallback(CameraCaptureSession.StateCallback... paramVarArgs)
  {
    return createComboCallback(Arrays.asList(paramVarArgs));
  }
  
  public static CameraCaptureSession.StateCallback createNoOpCallback()
  {
    return new NoOpSessionStateCallback();
  }
  
  static final class ComboSessionStateCallback
    extends CameraCaptureSession.StateCallback
  {
    private final List<CameraCaptureSession.StateCallback> mCallbacks = new ArrayList();
    
    ComboSessionStateCallback(List<CameraCaptureSession.StateCallback> paramList)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        paramList = (CameraCaptureSession.StateCallback)localIterator.next();
        if (!(paramList instanceof CameraCaptureSessionStateCallbacks.NoOpSessionStateCallback)) {
          this.mCallbacks.add(paramList);
        }
      }
    }
    
    public void onActive(CameraCaptureSession paramCameraCaptureSession)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.StateCallback)localIterator.next()).onActive(paramCameraCaptureSession);
      }
    }
    
    public void onCaptureQueueEmpty(CameraCaptureSession paramCameraCaptureSession)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.StateCallback)localIterator.next()).onCaptureQueueEmpty(paramCameraCaptureSession);
      }
    }
    
    public void onClosed(CameraCaptureSession paramCameraCaptureSession)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.StateCallback)localIterator.next()).onClosed(paramCameraCaptureSession);
      }
    }
    
    public void onConfigureFailed(CameraCaptureSession paramCameraCaptureSession)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.StateCallback)localIterator.next()).onConfigureFailed(paramCameraCaptureSession);
      }
    }
    
    public void onConfigured(CameraCaptureSession paramCameraCaptureSession)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.StateCallback)localIterator.next()).onConfigured(paramCameraCaptureSession);
      }
    }
    
    public void onReady(CameraCaptureSession paramCameraCaptureSession)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.StateCallback)localIterator.next()).onReady(paramCameraCaptureSession);
      }
    }
    
    public void onSurfacePrepared(CameraCaptureSession paramCameraCaptureSession, Surface paramSurface)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureSession.StateCallback)localIterator.next()).onSurfacePrepared(paramCameraCaptureSession, paramSurface);
      }
    }
  }
  
  static final class NoOpSessionStateCallback
    extends CameraCaptureSession.StateCallback
  {
    NoOpSessionStateCallback() {}
    
    public void onActive(CameraCaptureSession paramCameraCaptureSession) {}
    
    public void onCaptureQueueEmpty(CameraCaptureSession paramCameraCaptureSession) {}
    
    public void onClosed(CameraCaptureSession paramCameraCaptureSession) {}
    
    public void onConfigureFailed(CameraCaptureSession paramCameraCaptureSession) {}
    
    public void onConfigured(CameraCaptureSession paramCameraCaptureSession) {}
    
    public void onReady(CameraCaptureSession paramCameraCaptureSession) {}
    
    public void onSurfacePrepared(CameraCaptureSession paramCameraCaptureSession, Surface paramSurface) {}
  }
}
