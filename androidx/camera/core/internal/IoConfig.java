package androidx.camera.core.internal;

import androidx.camera.core.impl.Config.Option;
import java.util.concurrent.Executor;

public abstract interface IoConfig
{
  public static final Config.Option<Executor> OPTION_IO_EXECUTOR = Config.Option.create("camerax.core.io.ioExecutor", Executor.class);
  
  public abstract Executor getIoExecutor();
  
  public abstract Executor getIoExecutor(Executor paramExecutor);
  
  public static abstract interface Builder<B>
  {
    public abstract B setIoExecutor(Executor paramExecutor);
  }
}
