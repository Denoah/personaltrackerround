package io.reactivex.annotations;

public enum BackpressureKind
{
  static
  {
    FULL = new BackpressureKind("FULL", 1);
    SPECIAL = new BackpressureKind("SPECIAL", 2);
    UNBOUNDED_IN = new BackpressureKind("UNBOUNDED_IN", 3);
    ERROR = new BackpressureKind("ERROR", 4);
    BackpressureKind localBackpressureKind = new BackpressureKind("NONE", 5);
    NONE = localBackpressureKind;
    $VALUES = new BackpressureKind[] { PASS_THROUGH, FULL, SPECIAL, UNBOUNDED_IN, ERROR, localBackpressureKind };
  }
  
  private BackpressureKind() {}
}
