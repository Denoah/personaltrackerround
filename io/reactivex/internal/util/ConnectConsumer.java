package io.reactivex.internal.util;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public final class ConnectConsumer
  implements Consumer<Disposable>
{
  public Disposable disposable;
  
  public ConnectConsumer() {}
  
  public void accept(Disposable paramDisposable)
    throws Exception
  {
    this.disposable = paramDisposable;
  }
}
