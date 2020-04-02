package io.fabric.sdk.android.services.common;

public abstract class Crash
{
  private static final String UNKNOWN_EXCEPTION = "<unknown>";
  private final String exceptionName;
  private final String sessionId;
  
  public Crash(String paramString)
  {
    this(paramString, "<unknown>");
  }
  
  public Crash(String paramString1, String paramString2)
  {
    this.sessionId = paramString1;
    this.exceptionName = paramString2;
  }
  
  public String getExceptionName()
  {
    return this.exceptionName;
  }
  
  public String getSessionId()
  {
    return this.sessionId;
  }
  
  public static class FatalException
    extends Crash
  {
    public FatalException(String paramString)
    {
      super();
    }
    
    public FatalException(String paramString1, String paramString2)
    {
      super(paramString2);
    }
  }
  
  public static class LoggedException
    extends Crash
  {
    public LoggedException(String paramString)
    {
      super();
    }
    
    public LoggedException(String paramString1, String paramString2)
    {
      super(paramString2);
    }
  }
}
