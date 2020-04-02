package androidx.work.impl.utils.futures;

import java.util.concurrent.Executor;

 enum DirectExecutor
  implements Executor
{
  static
  {
    DirectExecutor localDirectExecutor = new DirectExecutor("INSTANCE", 0);
    INSTANCE = localDirectExecutor;
    $VALUES = new DirectExecutor[] { localDirectExecutor };
  }
  
  private DirectExecutor() {}
  
  public void execute(Runnable paramRunnable)
  {
    paramRunnable.run();
  }
  
  public String toString()
  {
    return "DirectExecutor";
  }
}
