package androidx.camera.core.impl.utils.executor;

import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

final class IoExecutor
  implements Executor
{
  private static volatile Executor sExecutor;
  private final ExecutorService mIoService = Executors.newFixedThreadPool(2, new ThreadFactory()
  {
    private static final String THREAD_NAME_STEM = "CameraX-camerax_io_%d";
    private final AtomicInteger mThreadId = new AtomicInteger(0);
    
    public Thread newThread(Runnable paramAnonymousRunnable)
    {
      paramAnonymousRunnable = new Thread(paramAnonymousRunnable);
      paramAnonymousRunnable.setName(String.format(Locale.US, "CameraX-camerax_io_%d", new Object[] { Integer.valueOf(this.mThreadId.getAndIncrement()) }));
      return paramAnonymousRunnable;
    }
  });
  
  IoExecutor() {}
  
  static Executor getInstance()
  {
    if (sExecutor != null) {
      return sExecutor;
    }
    try
    {
      if (sExecutor == null)
      {
        IoExecutor localIoExecutor = new androidx/camera/core/impl/utils/executor/IoExecutor;
        localIoExecutor.<init>();
        sExecutor = localIoExecutor;
      }
      return sExecutor;
    }
    finally {}
  }
  
  public void execute(Runnable paramRunnable)
  {
    this.mIoService.execute(paramRunnable);
  }
}
