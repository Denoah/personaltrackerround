package io.reactivex;

public enum BackpressureStrategy
{
  static
  {
    ERROR = new BackpressureStrategy("ERROR", 1);
    BUFFER = new BackpressureStrategy("BUFFER", 2);
    DROP = new BackpressureStrategy("DROP", 3);
    BackpressureStrategy localBackpressureStrategy = new BackpressureStrategy("LATEST", 4);
    LATEST = localBackpressureStrategy;
    $VALUES = new BackpressureStrategy[] { MISSING, ERROR, BUFFER, DROP, localBackpressureStrategy };
  }
  
  private BackpressureStrategy() {}
}
