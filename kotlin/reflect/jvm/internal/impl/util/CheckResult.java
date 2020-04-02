package kotlin.reflect.jvm.internal.impl.util;

public abstract class CheckResult
{
  private final boolean isSuccess;
  
  private CheckResult(boolean paramBoolean)
  {
    this.isSuccess = paramBoolean;
  }
  
  public final boolean isSuccess()
  {
    return this.isSuccess;
  }
  
  public static final class IllegalFunctionName
    extends CheckResult
  {
    public static final IllegalFunctionName INSTANCE = new IllegalFunctionName();
    
    private IllegalFunctionName()
    {
      super(null);
    }
  }
  
  public static final class IllegalSignature
    extends CheckResult
  {
    private final String error;
    
    public IllegalSignature(String paramString)
    {
      super(null);
      this.error = paramString;
    }
  }
  
  public static final class SuccessCheck
    extends CheckResult
  {
    public static final SuccessCheck INSTANCE = new SuccessCheck();
    
    private SuccessCheck()
    {
      super(null);
    }
  }
}
