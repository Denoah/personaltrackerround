package androidx.camera.core;

import androidx.camera.core.impl.CameraFactory;
import androidx.core.util.Preconditions;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class CameraExecutor
  implements Executor
{
  private static final int DEFAULT_CORE_THREADS = 1;
  private static final int DEFAULT_MAX_THREADS = 1;
  private static final ThreadFactory THREAD_FACTORY = new ThreadFactory()
  {
    private static final String THREAD_NAME_STEM = "CameraX-core_camera_%d";
    private final AtomicInteger mThreadId = new AtomicInteger(0);
    
    public Thread newThread(Runnable paramAnonymousRunnable)
    {
      paramAnonymousRunnable = new Thread(paramAnonymousRunnable);
      paramAnonymousRunnable.setName(String.format(Locale.US, "CameraX-core_camera_%d", new Object[] { Integer.valueOf(this.mThreadId.getAndIncrement()) }));
      return paramAnonymousRunnable;
    }
  };
  private final Object mExecutorLock = new Object();
  private ThreadPoolExecutor mThreadPoolExecutor = createExecutor();
  
  CameraExecutor() {}
  
  private static ThreadPoolExecutor createExecutor()
  {
    return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), THREAD_FACTORY);
  }
  
  void deinit()
  {
    synchronized (this.mExecutorLock)
    {
      if (!this.mThreadPoolExecutor.isShutdown()) {
        this.mThreadPoolExecutor.shutdown();
      }
      return;
    }
  }
  
  public void execute(Runnable paramRunnable)
  {
    Preconditions.checkNotNull(paramRunnable);
    synchronized (this.mExecutorLock)
    {
      boolean bool;
      if (!this.mThreadPoolExecutor.isShutdown()) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkState(bool, "CameraExecutor is deinit");
      this.mThreadPoolExecutor.execute(paramRunnable);
      return;
    }
  }
  
  void init(CameraFactory paramCameraFactory)
  {
    Preconditions.checkNotNull(paramCameraFactory);
    synchronized (this.mExecutorLock)
    {
      if (this.mThreadPoolExecutor.isShutdown()) {
        this.mThreadPoolExecutor = createExecutor();
      }
      ThreadPoolExecutor localThreadPoolExecutor = this.mThreadPoolExecutor;
      int i = 0;
      try
      {
        j = paramCameraFactory.getAvailableCameraIds().size();
      }
      catch (CameraInfoUnavailableException paramCameraFactory)
      {
        paramCameraFactory.printStackTrace();
        j = i;
      }
      int j = Math.max(1, j);
      localThreadPoolExecutor.setMaximumPoolSize(j);
      localThreadPoolExecutor.setCorePoolSize(j);
      return;
    }
  }
}
