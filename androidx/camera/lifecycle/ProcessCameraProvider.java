package androidx.camera.lifecycle;

import android.content.Context;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraInfoUnavailableException;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.core.util.Preconditions;
import androidx.lifecycle.LifecycleOwner;
import com.google.common.util.concurrent.ListenableFuture;

public final class ProcessCameraProvider
  implements LifecycleCameraProvider
{
  private static final ProcessCameraProvider sAppInstance = new ProcessCameraProvider();
  
  private ProcessCameraProvider() {}
  
  public static ListenableFuture<ProcessCameraProvider> getInstance(Context paramContext)
  {
    Preconditions.checkNotNull(paramContext);
    return Futures.transform(CameraX.getOrCreateInstance(paramContext), _..Lambda.ProcessCameraProvider.TYjfluwv4_m1lcHTHHt4JLTu5vc.INSTANCE, CameraXExecutors.directExecutor());
  }
  
  public static void initializeInstance(Context paramContext, CameraXConfig paramCameraXConfig)
  {
    Futures.addCallback(CameraX.initialize(paramContext, paramCameraXConfig), new FutureCallback()
    {
      public void onFailure(Throwable paramAnonymousThrowable)
      {
        throw new RuntimeException(paramAnonymousThrowable);
      }
      
      public void onSuccess(Void paramAnonymousVoid) {}
    }, CameraXExecutors.directExecutor());
  }
  
  public Camera bindToLifecycle(LifecycleOwner paramLifecycleOwner, CameraSelector paramCameraSelector, UseCase... paramVarArgs)
  {
    return CameraX.bindToLifecycle(paramLifecycleOwner, paramCameraSelector, paramVarArgs);
  }
  
  public boolean hasCamera(CameraSelector paramCameraSelector)
    throws CameraInfoUnavailableException
  {
    return CameraX.hasCamera(paramCameraSelector);
  }
  
  public boolean isBound(UseCase paramUseCase)
  {
    return CameraX.isBound(paramUseCase);
  }
  
  public ListenableFuture<Void> shutdown()
  {
    return CameraX.shutdown();
  }
  
  public void unbind(UseCase... paramVarArgs)
  {
    CameraX.unbind(paramVarArgs);
  }
  
  public void unbindAll() {}
}
