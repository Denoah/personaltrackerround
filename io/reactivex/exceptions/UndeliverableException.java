package io.reactivex.exceptions;

public final class UndeliverableException
  extends IllegalStateException
{
  private static final long serialVersionUID = 1644750035281290266L;
  
  public UndeliverableException(Throwable paramThrowable)
  {
    super(localStringBuilder.toString(), paramThrowable);
  }
}
