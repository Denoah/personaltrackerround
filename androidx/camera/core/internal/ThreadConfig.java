package androidx.camera.core.internal;

import androidx.camera.core.impl.Config.Option;
import java.util.concurrent.Executor;

public abstract interface ThreadConfig
{
  public static final Config.Option<Executor> OPTION_BACKGROUND_EXECUTOR = Config.Option.create("camerax.core.thread.backgroundExecutor", Executor.class);
  
  public abstract Executor getBackgroundExecutor();
  
  public abstract Executor getBackgroundExecutor(Executor paramExecutor);
  
  public static abstract interface Builder<B>
  {
    public abstract B setBackgroundExecutor(Executor paramExecutor);
  }
}
