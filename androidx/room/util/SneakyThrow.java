package androidx.room.util;

public class SneakyThrow
{
  private SneakyThrow() {}
  
  public static void reThrow(Exception paramException)
  {
    sneakyThrow(paramException);
  }
  
  private static <E extends Throwable> void sneakyThrow(Throwable paramThrowable)
    throws Throwable
  {
    throw paramThrowable;
  }
}
