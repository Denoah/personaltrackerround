package androidx.camera.core.impl.utils.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

final class HighPriorityExecutor
  implements Executor
{
  private static volatile Executor sExecutor;
  private final ExecutorService mHighPriorityService = Executors.newSingleThreadExecutor(new ThreadFactory()
  {
    private static final String THREAD_NAME = "CameraX-camerax_high_priority";
    
    public Thread newThread(Runnable paramAnonymousRunnable)
    {
      paramAnonymousRunnable = new Thread(paramAnonymousRunnable);
      paramAnonymousRunnable.setPriority(10);
      paramAnonymousRunnable.setName("CameraX-camerax_high_priority");
      return paramAnonymousRunnable;
    }
  });
  
  HighPriorityExecutor() {}
  
  static Executor getInstance()
  {
    if (sExecutor != null) {
      return sExecutor;
    }
    try
    {
      if (sExecutor == null)
      {
        HighPriorityExecutor localHighPriorityExecutor = new androidx/camera/core/impl/utils/executor/HighPriorityExecutor;
        localHighPriorityExecutor.<init>();
        sExecutor = localHighPriorityExecutor;
      }
      return sExecutor;
    }
    finally {}
  }
  
  public void execute(Runnable paramRunnable)
  {
    this.mHighPriorityService.execute(paramRunnable);
  }
}
