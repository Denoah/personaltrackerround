package io.reactivex;

public enum BackpressureOverflowStrategy
{
  static
  {
    DROP_OLDEST = new BackpressureOverflowStrategy("DROP_OLDEST", 1);
    BackpressureOverflowStrategy localBackpressureOverflowStrategy = new BackpressureOverflowStrategy("DROP_LATEST", 2);
    DROP_LATEST = localBackpressureOverflowStrategy;
    $VALUES = new BackpressureOverflowStrategy[] { ERROR, DROP_OLDEST, localBackpressureOverflowStrategy };
  }
  
  private BackpressureOverflowStrategy() {}
}
