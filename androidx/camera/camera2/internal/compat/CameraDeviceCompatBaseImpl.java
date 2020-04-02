package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.CameraDevice;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import androidx.camera.camera2.internal.compat.params.OutputConfigurationCompat;
import androidx.camera.camera2.internal.compat.params.SessionConfigurationCompat;
import androidx.camera.core.impl.utils.MainThreadAsyncHandler;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class CameraDeviceCompatBaseImpl
  implements CameraDeviceCompat.CameraDeviceCompatImpl
{
  CameraDeviceCompatBaseImpl() {}
  
  private static void checkPhysicalCameraIdValid(CameraDevice paramCameraDevice, List<OutputConfigurationCompat> paramList)
  {
    paramCameraDevice = paramCameraDevice.getId();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      String str = ((OutputConfigurationCompat)paramList.next()).getPhysicalCameraId();
      if ((str != null) && (!str.isEmpty()))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Camera ");
        localStringBuilder.append(paramCameraDevice);
        localStringBuilder.append(": Camera doesn't support physicalCameraId ");
        localStringBuilder.append(str);
        localStringBuilder.append(". Ignoring.");
        Log.w("CameraDeviceCompat", localStringBuilder.toString());
      }
    }
  }
  
  static void checkPreconditions(CameraDevice paramCameraDevice, SessionConfigurationCompat paramSessionConfigurationCompat)
  {
    Preconditions.checkNotNull(paramCameraDevice);
    Preconditions.checkNotNull(paramSessionConfigurationCompat);
    Preconditions.checkNotNull(paramSessionConfigurationCompat.getStateCallback());
    List localList = paramSessionConfigurationCompat.getOutputConfigurations();
    if (localList != null)
    {
      if (paramSessionConfigurationCompat.getExecutor() != null)
      {
        checkPhysicalCameraIdValid(paramCameraDevice, localList);
        return;
      }
      throw new IllegalArgumentException("Invalid executor");
    }
    throw new IllegalArgumentException("Invalid output configurations");
  }
  
  static List<Surface> unpackSurfaces(List<OutputConfigurationCompat> paramList)
  {
    ArrayList localArrayList = new ArrayList(paramList.size());
    paramList = paramList.iterator();
    while (paramList.hasNext()) {
      localArrayList.add(((OutputConfigurationCompat)paramList.next()).getSurface());
    }
    return localArrayList;
  }
  
  void createBaseCaptureSession(CameraDevice paramCameraDevice, List<Surface> paramList, CameraCaptureSession.StateCallback paramStateCallback, Handler paramHandler)
    throws CameraAccessException
  {
    paramCameraDevice.createCaptureSession(paramList, paramStateCallback, paramHandler);
  }
  
  public void createCaptureSession(CameraDevice paramCameraDevice, SessionConfigurationCompat paramSessionConfigurationCompat)
    throws CameraAccessException
  {
    checkPreconditions(paramCameraDevice, paramSessionConfigurationCompat);
    if (paramSessionConfigurationCompat.getInputConfiguration() == null)
    {
      if (paramSessionConfigurationCompat.getSessionType() != 1)
      {
        CameraCaptureSessionCompat.StateCallbackExecutorWrapper localStateCallbackExecutorWrapper = new CameraCaptureSessionCompat.StateCallbackExecutorWrapper(paramSessionConfigurationCompat.getExecutor(), paramSessionConfigurationCompat.getStateCallback());
        createBaseCaptureSession(paramCameraDevice, unpackSurfaces(paramSessionConfigurationCompat.getOutputConfigurations()), localStateCallbackExecutorWrapper, MainThreadAsyncHandler.getInstance());
        return;
      }
      throw new IllegalArgumentException("High speed capture sessions not supported until API 23");
    }
    throw new IllegalArgumentException("Reprocessing sessions not supported until API 23");
  }
}
