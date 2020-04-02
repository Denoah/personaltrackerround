package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCaptureSession.StateCallback;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build.VERSION;
import android.view.Surface;
import java.util.List;
import java.util.concurrent.Executor;

public final class CameraCaptureSessionCompat
{
  private static final CameraCaptureSessionCompatImpl IMPL = ;
  
  private CameraCaptureSessionCompat() {}
  
  public static int captureBurstRequests(CameraCaptureSession paramCameraCaptureSession, List<CaptureRequest> paramList, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    return IMPL.captureBurstRequests(paramCameraCaptureSession, paramList, paramExecutor, paramCaptureCallback);
  }
  
  public static int captureSingleRequest(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    return IMPL.captureSingleRequest(paramCameraCaptureSession, paramCaptureRequest, paramExecutor, paramCaptureCallback);
  }
  
  private static CameraCaptureSessionCompatImpl chooseImplementation()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return new CameraCaptureSessionCompatApi28Impl();
    }
    return new CameraCaptureSessionCompatBaseImpl();
  }
  
  public static int setRepeatingBurstRequests(CameraCaptureSession paramCameraCaptureSession, List<CaptureRequest> paramList, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    return IMPL.setRepeatingBurstRequests(paramCameraCaptureSession, paramList, paramExecutor, paramCaptureCallback);
  }
  
  public static int setSingleRepeatingRequest(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    throws CameraAccessException
  {
    return IMPL.setSingleRepeatingRequest(paramCameraCaptureSession, paramCaptureRequest, paramExecutor, paramCaptureCallback);
  }
  
  static abstract interface CameraCaptureSessionCompatImpl
  {
    public abstract int captureBurstRequests(CameraCaptureSession paramCameraCaptureSession, List<CaptureRequest> paramList, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
      throws CameraAccessException;
    
    public abstract int captureSingleRequest(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
      throws CameraAccessException;
    
    public abstract int setRepeatingBurstRequests(CameraCaptureSession paramCameraCaptureSession, List<CaptureRequest> paramList, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
      throws CameraAccessException;
    
    public abstract int setSingleRepeatingRequest(CameraCaptureSession paramCameraCaptureSession, CaptureRequest paramCaptureRequest, Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
      throws CameraAccessException;
  }
  
  static final class CaptureCallbackExecutorWrapper
    extends CameraCaptureSession.CaptureCallback
  {
    private final Executor mExecutor;
    final CameraCaptureSession.CaptureCallback mWrappedCallback;
    
    CaptureCallbackExecutorWrapper(Executor paramExecutor, CameraCaptureSession.CaptureCallback paramCaptureCallback)
    {
      this.mExecutor = paramExecutor;
      this.mWrappedCallback = paramCaptureCallback;
    }
    
    public void onCaptureBufferLost(final CameraCaptureSession paramCameraCaptureSession, final CaptureRequest paramCaptureRequest, final Surface paramSurface, final long paramLong)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.CaptureCallbackExecutorWrapper.this.mWrappedCallback.onCaptureBufferLost(paramCameraCaptureSession, paramCaptureRequest, paramSurface, paramLong);
        }
      });
    }
    
    public void onCaptureCompleted(final CameraCaptureSession paramCameraCaptureSession, final CaptureRequest paramCaptureRequest, final TotalCaptureResult paramTotalCaptureResult)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.CaptureCallbackExecutorWrapper.this.mWrappedCallback.onCaptureCompleted(paramCameraCaptureSession, paramCaptureRequest, paramTotalCaptureResult);
        }
      });
    }
    
    public void onCaptureFailed(final CameraCaptureSession paramCameraCaptureSession, final CaptureRequest paramCaptureRequest, final CaptureFailure paramCaptureFailure)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.CaptureCallbackExecutorWrapper.this.mWrappedCallback.onCaptureFailed(paramCameraCaptureSession, paramCaptureRequest, paramCaptureFailure);
        }
      });
    }
    
    public void onCaptureProgressed(final CameraCaptureSession paramCameraCaptureSession, final CaptureRequest paramCaptureRequest, final CaptureResult paramCaptureResult)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.CaptureCallbackExecutorWrapper.this.mWrappedCallback.onCaptureProgressed(paramCameraCaptureSession, paramCaptureRequest, paramCaptureResult);
        }
      });
    }
    
    public void onCaptureSequenceAborted(final CameraCaptureSession paramCameraCaptureSession, final int paramInt)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.CaptureCallbackExecutorWrapper.this.mWrappedCallback.onCaptureSequenceAborted(paramCameraCaptureSession, paramInt);
        }
      });
    }
    
    public void onCaptureSequenceCompleted(final CameraCaptureSession paramCameraCaptureSession, final int paramInt, final long paramLong)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.CaptureCallbackExecutorWrapper.this.mWrappedCallback.onCaptureSequenceCompleted(paramCameraCaptureSession, paramInt, paramLong);
        }
      });
    }
    
    public void onCaptureStarted(final CameraCaptureSession paramCameraCaptureSession, final CaptureRequest paramCaptureRequest, final long paramLong1, long paramLong2)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.CaptureCallbackExecutorWrapper.this.mWrappedCallback.onCaptureStarted(paramCameraCaptureSession, paramCaptureRequest, paramLong1, this.val$frameNumber);
        }
      });
    }
  }
  
  static final class StateCallbackExecutorWrapper
    extends CameraCaptureSession.StateCallback
  {
    private final Executor mExecutor;
    final CameraCaptureSession.StateCallback mWrappedCallback;
    
    StateCallbackExecutorWrapper(Executor paramExecutor, CameraCaptureSession.StateCallback paramStateCallback)
    {
      this.mExecutor = paramExecutor;
      this.mWrappedCallback = paramStateCallback;
    }
    
    public void onActive(final CameraCaptureSession paramCameraCaptureSession)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.StateCallbackExecutorWrapper.this.mWrappedCallback.onActive(paramCameraCaptureSession);
        }
      });
    }
    
    public void onCaptureQueueEmpty(final CameraCaptureSession paramCameraCaptureSession)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.StateCallbackExecutorWrapper.this.mWrappedCallback.onCaptureQueueEmpty(paramCameraCaptureSession);
        }
      });
    }
    
    public void onClosed(final CameraCaptureSession paramCameraCaptureSession)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.StateCallbackExecutorWrapper.this.mWrappedCallback.onClosed(paramCameraCaptureSession);
        }
      });
    }
    
    public void onConfigureFailed(final CameraCaptureSession paramCameraCaptureSession)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.StateCallbackExecutorWrapper.this.mWrappedCallback.onConfigureFailed(paramCameraCaptureSession);
        }
      });
    }
    
    public void onConfigured(final CameraCaptureSession paramCameraCaptureSession)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.StateCallbackExecutorWrapper.this.mWrappedCallback.onConfigured(paramCameraCaptureSession);
        }
      });
    }
    
    public void onReady(final CameraCaptureSession paramCameraCaptureSession)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.StateCallbackExecutorWrapper.this.mWrappedCallback.onReady(paramCameraCaptureSession);
        }
      });
    }
    
    public void onSurfacePrepared(final CameraCaptureSession paramCameraCaptureSession, final Surface paramSurface)
    {
      this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          CameraCaptureSessionCompat.StateCallbackExecutorWrapper.this.mWrappedCallback.onSurfacePrepared(paramCameraCaptureSession, paramSurface);
        }
      });
    }
  }
}
