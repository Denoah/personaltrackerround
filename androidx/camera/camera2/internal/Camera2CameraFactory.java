package androidx.camera.camera2.internal;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.impl.CameraFactory;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.LensFacingCameraIdFilter;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class Camera2CameraFactory
  implements CameraFactory
{
  private static final int DEFAULT_ALLOWED_CONCURRENT_OPEN_CAMERAS = 1;
  private static final Handler sHandler = new Handler(sHandlerThread.getLooper());
  private static final HandlerThread sHandlerThread;
  private final CameraAvailabilityRegistry mAvailabilityRegistry = new CameraAvailabilityRegistry(1, CameraXExecutors.newHandlerExecutor(sHandler));
  private final CameraManagerCompat mCameraManager;
  
  static
  {
    HandlerThread localHandlerThread = new HandlerThread("CameraX-");
    sHandlerThread = localHandlerThread;
    localHandlerThread.start();
  }
  
  public Camera2CameraFactory(Context paramContext)
  {
    this.mCameraManager = CameraManagerCompat.from(paramContext);
  }
  
  public String cameraIdForLensFacing(int paramInt)
    throws CameraInfoUnavailableException
  {
    Set localSet = getLensFacingCameraIdFilter(paramInt).filter(getAvailableCameraIds());
    if (!localSet.isEmpty()) {
      return (String)localSet.iterator().next();
    }
    return null;
  }
  
  public Set<String> getAvailableCameraIds()
    throws CameraInfoUnavailableException
  {
    try
    {
      List localList = Arrays.asList(this.mCameraManager.unwrap().getCameraIdList());
      return new LinkedHashSet(localList);
    }
    catch (CameraAccessException localCameraAccessException)
    {
      throw new CameraInfoUnavailableException("Unable to retrieve list of cameras on device.", localCameraAccessException);
    }
  }
  
  public CameraInternal getCamera(String paramString)
    throws CameraInfoUnavailableException
  {
    if (getAvailableCameraIds().contains(paramString))
    {
      paramString = new Camera2CameraImpl(this.mCameraManager, paramString, this.mAvailabilityRegistry.getAvailableCameraCount(), sHandler);
      this.mAvailabilityRegistry.registerCamera(paramString);
      return paramString;
    }
    throw new IllegalArgumentException("The given camera id is not on the available camera id list.");
  }
  
  public LensFacingCameraIdFilter getLensFacingCameraIdFilter(int paramInt)
  {
    return new Camera2LensFacingCameraIdFilter(paramInt, this.mCameraManager.unwrap());
  }
}
