package androidx.camera.core.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class CameraCaptureCallbacks
{
  private CameraCaptureCallbacks() {}
  
  static CameraCaptureCallback createComboCallback(List<CameraCaptureCallback> paramList)
  {
    if (paramList.isEmpty()) {
      return createNoOpCallback();
    }
    if (paramList.size() == 1) {
      return (CameraCaptureCallback)paramList.get(0);
    }
    return new ComboCameraCaptureCallback(paramList);
  }
  
  public static CameraCaptureCallback createComboCallback(CameraCaptureCallback... paramVarArgs)
  {
    return createComboCallback(Arrays.asList(paramVarArgs));
  }
  
  public static CameraCaptureCallback createNoOpCallback()
  {
    return new NoOpCameraCaptureCallback();
  }
  
  public static final class ComboCameraCaptureCallback
    extends CameraCaptureCallback
  {
    private final List<CameraCaptureCallback> mCallbacks = new ArrayList();
    
    ComboCameraCaptureCallback(List<CameraCaptureCallback> paramList)
    {
      paramList = paramList.iterator();
      while (paramList.hasNext())
      {
        CameraCaptureCallback localCameraCaptureCallback = (CameraCaptureCallback)paramList.next();
        if (!(localCameraCaptureCallback instanceof CameraCaptureCallbacks.NoOpCameraCaptureCallback)) {
          this.mCallbacks.add(localCameraCaptureCallback);
        }
      }
    }
    
    public List<CameraCaptureCallback> getCallbacks()
    {
      return this.mCallbacks;
    }
    
    public void onCaptureCancelled()
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureCallback)localIterator.next()).onCaptureCancelled();
      }
    }
    
    public void onCaptureCompleted(CameraCaptureResult paramCameraCaptureResult)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureCallback)localIterator.next()).onCaptureCompleted(paramCameraCaptureResult);
      }
    }
    
    public void onCaptureFailed(CameraCaptureFailure paramCameraCaptureFailure)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraCaptureCallback)localIterator.next()).onCaptureFailed(paramCameraCaptureFailure);
      }
    }
  }
  
  static final class NoOpCameraCaptureCallback
    extends CameraCaptureCallback
  {
    NoOpCameraCaptureCallback() {}
    
    public void onCaptureCompleted(CameraCaptureResult paramCameraCaptureResult) {}
    
    public void onCaptureFailed(CameraCaptureFailure paramCameraCaptureFailure) {}
  }
}
