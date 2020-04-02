package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraCaptureCallbacks.ComboCameraCaptureCallback;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class CaptureCallbackConverter
{
  private CaptureCallbackConverter() {}
  
  static CameraCaptureSession.CaptureCallback toCaptureCallback(CameraCaptureCallback paramCameraCaptureCallback)
  {
    if (paramCameraCaptureCallback == null) {
      return null;
    }
    ArrayList localArrayList = new ArrayList();
    toCaptureCallback(paramCameraCaptureCallback, localArrayList);
    if (localArrayList.size() == 1) {
      paramCameraCaptureCallback = (CameraCaptureSession.CaptureCallback)localArrayList.get(0);
    } else {
      paramCameraCaptureCallback = Camera2CaptureCallbacks.createComboCallback(localArrayList);
    }
    return paramCameraCaptureCallback;
  }
  
  static void toCaptureCallback(CameraCaptureCallback paramCameraCaptureCallback, List<CameraCaptureSession.CaptureCallback> paramList)
  {
    if ((paramCameraCaptureCallback instanceof CameraCaptureCallbacks.ComboCameraCaptureCallback))
    {
      paramCameraCaptureCallback = ((CameraCaptureCallbacks.ComboCameraCaptureCallback)paramCameraCaptureCallback).getCallbacks().iterator();
      while (paramCameraCaptureCallback.hasNext()) {
        toCaptureCallback((CameraCaptureCallback)paramCameraCaptureCallback.next(), paramList);
      }
    }
    if ((paramCameraCaptureCallback instanceof CaptureCallbackContainer)) {
      paramList.add(((CaptureCallbackContainer)paramCameraCaptureCallback).getCaptureCallback());
    } else {
      paramList.add(new CaptureCallbackAdapter(paramCameraCaptureCallback));
    }
  }
}
