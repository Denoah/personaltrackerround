package androidx.work;

public enum ExistingPeriodicWorkPolicy
{
  static
  {
    ExistingPeriodicWorkPolicy localExistingPeriodicWorkPolicy = new ExistingPeriodicWorkPolicy("KEEP", 1);
    KEEP = localExistingPeriodicWorkPolicy;
    $VALUES = new ExistingPeriodicWorkPolicy[] { REPLACE, localExistingPeriodicWorkPolicy };
  }
  
  private ExistingPeriodicWorkPolicy() {}
}
