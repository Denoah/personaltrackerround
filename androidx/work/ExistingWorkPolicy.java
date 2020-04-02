package androidx.work;

public enum ExistingWorkPolicy
{
  static
  {
    KEEP = new ExistingWorkPolicy("KEEP", 1);
    ExistingWorkPolicy localExistingWorkPolicy = new ExistingWorkPolicy("APPEND", 2);
    APPEND = localExistingWorkPolicy;
    $VALUES = new ExistingWorkPolicy[] { REPLACE, KEEP, localExistingWorkPolicy };
  }
  
  private ExistingWorkPolicy() {}
}
