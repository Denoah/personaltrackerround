package kotlin.reflect.jvm.internal.impl.utils;

public class WrappedValues
{
  private static final Object NULL_VALUE = new Object()
  {
    public String toString()
    {
      return "NULL_VALUE";
    }
  };
  public static volatile boolean throwWrappedProcessCanceledException = false;
  
  public static <V> Object escapeNull(V paramV)
  {
    if (paramV == null)
    {
      paramV = NULL_VALUE;
      if (paramV == null) {
        $$$reportNull$$$0(1);
      }
      return paramV;
    }
    if (paramV == null) {
      $$$reportNull$$$0(2);
    }
    return paramV;
  }
  
  public static Object escapeThrowable(Throwable paramThrowable)
  {
    if (paramThrowable == null) {
      $$$reportNull$$$0(3);
    }
    return new ThrowableWrapper(paramThrowable, null);
  }
  
  public static <V> V unescapeExceptionOrNull(Object paramObject)
  {
    if (paramObject == null) {
      $$$reportNull$$$0(4);
    }
    return unescapeNull(unescapeThrowable(paramObject));
  }
  
  public static <V> V unescapeNull(Object paramObject)
  {
    if (paramObject == null) {
      $$$reportNull$$$0(0);
    }
    Object localObject = paramObject;
    if (paramObject == NULL_VALUE) {
      localObject = null;
    }
    return localObject;
  }
  
  public static <V> V unescapeThrowable(Object paramObject)
  {
    if ((paramObject instanceof ThrowableWrapper))
    {
      paramObject = ((ThrowableWrapper)paramObject).getThrowable();
      if ((throwWrappedProcessCanceledException) && (ExceptionUtilsKt.isProcessCanceledException(paramObject))) {
        throw new WrappedProcessCanceledException(paramObject);
      }
      throw ExceptionUtilsKt.rethrow(paramObject);
    }
    return paramObject;
  }
  
  private static final class ThrowableWrapper
  {
    private final Throwable throwable;
    
    private ThrowableWrapper(Throwable paramThrowable)
    {
      this.throwable = paramThrowable;
    }
    
    public Throwable getThrowable()
    {
      Throwable localThrowable = this.throwable;
      if (localThrowable == null) {
        $$$reportNull$$$0(1);
      }
      return localThrowable;
    }
    
    public String toString()
    {
      return this.throwable.toString();
    }
  }
  
  public static class WrappedProcessCanceledException
    extends RuntimeException
  {
    public WrappedProcessCanceledException(Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
}
