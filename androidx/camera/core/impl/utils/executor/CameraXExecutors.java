package androidx.camera.core.impl.utils.executor;

import android.os.Handler;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

public final class CameraXExecutors
{
  private CameraXExecutors() {}
  
  public static Executor directExecutor()
  {
    return DirectExecutor.getInstance();
  }
  
  public static Executor highPriorityExecutor()
  {
    return HighPriorityExecutor.getInstance();
  }
  
  public static Executor ioExecutor()
  {
    return IoExecutor.getInstance();
  }
  
  public static boolean isSequentialExecutor(Executor paramExecutor)
  {
    return paramExecutor instanceof SequentialExecutor;
  }
  
  public static ScheduledExecutorService mainThreadExecutor()
  {
    return MainThreadExecutor.getInstance();
  }
  
  public static ScheduledExecutorService myLooperExecutor()
  {
    return HandlerScheduledExecutorService.currentThreadExecutor();
  }
  
  public static ScheduledExecutorService newHandlerExecutor(Handler paramHandler)
  {
    return new HandlerScheduledExecutorService(paramHandler);
  }
  
  public static Executor newSequentialExecutor(Executor paramExecutor)
  {
    return new SequentialExecutor(paramExecutor);
  }
}
