package io.reactivex.exceptions;

import io.reactivex.internal.util.ExceptionHelper;

public final class Exceptions
{
  private Exceptions()
  {
    throw new IllegalStateException("No instances!");
  }
  
  public static RuntimeException propagate(Throwable paramThrowable)
  {
    throw ExceptionHelper.wrapOrThrow(paramThrowable);
  }
  
  public static void throwIfFatal(Throwable paramThrowable)
  {
    if (!(paramThrowable instanceof VirtualMachineError))
    {
      if (!(paramThrowable instanceof ThreadDeath))
      {
        if (!(paramThrowable instanceof LinkageError)) {
          return;
        }
        throw ((LinkageError)paramThrowable);
      }
      throw ((ThreadDeath)paramThrowable);
    }
    throw ((VirtualMachineError)paramThrowable);
  }
}
