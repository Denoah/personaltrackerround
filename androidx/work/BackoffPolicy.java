package androidx.work;

public enum BackoffPolicy
{
  static
  {
    BackoffPolicy localBackoffPolicy = new BackoffPolicy("LINEAR", 1);
    LINEAR = localBackoffPolicy;
    $VALUES = new BackoffPolicy[] { EXPONENTIAL, localBackoffPolicy };
  }
  
  private BackoffPolicy() {}
}
