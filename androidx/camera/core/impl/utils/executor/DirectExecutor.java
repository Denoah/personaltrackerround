package androidx.camera.core.impl.utils.executor;

import java.util.concurrent.Executor;

final class DirectExecutor
  implements Executor
{
  private static volatile DirectExecutor sDirectExecutor;
  
  DirectExecutor() {}
  
  static Executor getInstance()
  {
    if (sDirectExecutor != null) {
      return sDirectExecutor;
    }
    try
    {
      if (sDirectExecutor == null)
      {
        DirectExecutor localDirectExecutor = new androidx/camera/core/impl/utils/executor/DirectExecutor;
        localDirectExecutor.<init>();
        sDirectExecutor = localDirectExecutor;
      }
      return sDirectExecutor;
    }
    finally {}
  }
  
  public void execute(Runnable paramRunnable)
  {
    paramRunnable.run();
  }
}
