package androidx.work.impl.utils;

import java.util.concurrent.Executor;

public class SynchronousExecutor
  implements Executor
{
  public SynchronousExecutor() {}
  
  public void execute(Runnable paramRunnable)
  {
    paramRunnable.run();
  }
}
