package com.google.zxing;

public final class FormatException
  extends ReaderException
{
  private static final FormatException instance = new FormatException();
  
  private FormatException() {}
  
  private FormatException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
  
  public static FormatException getFormatInstance()
  {
    if (isStackTrace) {
      return new FormatException();
    }
    return instance;
  }
  
  public static FormatException getFormatInstance(Throwable paramThrowable)
  {
    if (isStackTrace) {
      return new FormatException(paramThrowable);
    }
    return instance;
  }
}
