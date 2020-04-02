package androidx.camera.core.impl.utils.futures;

import com.google.common.util.concurrent.ListenableFuture;

@FunctionalInterface
public abstract interface AsyncFunction<I, O>
{
  public abstract ListenableFuture<O> apply(I paramI)
    throws Exception;
}
