package io.reactivex.parallel;

import io.reactivex.functions.BiFunction;

public enum ParallelFailureHandling
  implements BiFunction<Long, Throwable, ParallelFailureHandling>
{
  static
  {
    ERROR = new ParallelFailureHandling("ERROR", 1);
    SKIP = new ParallelFailureHandling("SKIP", 2);
    ParallelFailureHandling localParallelFailureHandling = new ParallelFailureHandling("RETRY", 3);
    RETRY = localParallelFailureHandling;
    $VALUES = new ParallelFailureHandling[] { STOP, ERROR, SKIP, localParallelFailureHandling };
  }
  
  private ParallelFailureHandling() {}
  
  public ParallelFailureHandling apply(Long paramLong, Throwable paramThrowable)
  {
    return this;
  }
}
