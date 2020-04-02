package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraDevice.StateCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class CameraDeviceStateCallbacks
{
  private CameraDeviceStateCallbacks() {}
  
  public static CameraDevice.StateCallback createComboCallback(List<CameraDevice.StateCallback> paramList)
  {
    if (paramList.isEmpty()) {
      return createNoOpCallback();
    }
    if (paramList.size() == 1) {
      return (CameraDevice.StateCallback)paramList.get(0);
    }
    return new ComboDeviceStateCallback(paramList);
  }
  
  public static CameraDevice.StateCallback createComboCallback(CameraDevice.StateCallback... paramVarArgs)
  {
    return createComboCallback(Arrays.asList(paramVarArgs));
  }
  
  public static CameraDevice.StateCallback createNoOpCallback()
  {
    return new NoOpDeviceStateCallback();
  }
  
  private static final class ComboDeviceStateCallback
    extends CameraDevice.StateCallback
  {
    private final List<CameraDevice.StateCallback> mCallbacks = new ArrayList();
    
    ComboDeviceStateCallback(List<CameraDevice.StateCallback> paramList)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        paramList = (CameraDevice.StateCallback)localIterator.next();
        if (!(paramList instanceof CameraDeviceStateCallbacks.NoOpDeviceStateCallback)) {
          this.mCallbacks.add(paramList);
        }
      }
    }
    
    public void onClosed(CameraDevice paramCameraDevice)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraDevice.StateCallback)localIterator.next()).onClosed(paramCameraDevice);
      }
    }
    
    public void onDisconnected(CameraDevice paramCameraDevice)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraDevice.StateCallback)localIterator.next()).onDisconnected(paramCameraDevice);
      }
    }
    
    public void onError(CameraDevice paramCameraDevice, int paramInt)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraDevice.StateCallback)localIterator.next()).onError(paramCameraDevice, paramInt);
      }
    }
    
    public void onOpened(CameraDevice paramCameraDevice)
    {
      Iterator localIterator = this.mCallbacks.iterator();
      while (localIterator.hasNext()) {
        ((CameraDevice.StateCallback)localIterator.next()).onOpened(paramCameraDevice);
      }
    }
  }
  
  static final class NoOpDeviceStateCallback
    extends CameraDevice.StateCallback
  {
    NoOpDeviceStateCallback() {}
    
    public void onClosed(CameraDevice paramCameraDevice) {}
    
    public void onDisconnected(CameraDevice paramCameraDevice) {}
    
    public void onError(CameraDevice paramCameraDevice, int paramInt) {}
    
    public void onOpened(CameraDevice paramCameraDevice) {}
  }
}
