package io.fabric.sdk.android.services.concurrency;

public enum Priority
{
  static
  {
    HIGH = new Priority("HIGH", 2);
    Priority localPriority = new Priority("IMMEDIATE", 3);
    IMMEDIATE = localPriority;
    $VALUES = new Priority[] { LOW, NORMAL, HIGH, localPriority };
  }
  
  private Priority() {}
  
  static <Y> int compareTo(PriorityProvider paramPriorityProvider, Y paramY)
  {
    if ((paramY instanceof PriorityProvider)) {
      paramY = ((PriorityProvider)paramY).getPriority();
    } else {
      paramY = NORMAL;
    }
    return paramY.ordinal() - paramPriorityProvider.getPriority().ordinal();
  }
}
