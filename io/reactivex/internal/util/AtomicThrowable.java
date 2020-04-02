package io.reactivex.internal.util;

import java.util.concurrent.atomic.AtomicReference;

public final class AtomicThrowable
  extends AtomicReference<Throwable>
{
  private static final long serialVersionUID = 3949248817947090603L;
  
  public AtomicThrowable() {}
  
  public boolean addThrowable(Throwable paramThrowable)
  {
    return ExceptionHelper.addThrowable(this, paramThrowable);
  }
  
  public boolean isTerminated()
  {
    boolean bool;
    if (get() == ExceptionHelper.TERMINATED) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public Throwable terminate()
  {
    return ExceptionHelper.terminate(this);
  }
}
