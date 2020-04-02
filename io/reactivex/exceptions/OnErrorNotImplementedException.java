package io.reactivex.exceptions;

public final class OnErrorNotImplementedException
  extends RuntimeException
{
  private static final long serialVersionUID = -6298857009889503852L;
  
  public OnErrorNotImplementedException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  public OnErrorNotImplementedException(Throwable paramThrowable)
  {
    this(localStringBuilder.toString(), paramThrowable);
  }
}
