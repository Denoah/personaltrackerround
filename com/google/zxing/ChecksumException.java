package com.google.zxing;

public final class ChecksumException
  extends ReaderException
{
  private static final ChecksumException instance = new ChecksumException();
  
  private ChecksumException() {}
  
  private ChecksumException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
  
  public static ChecksumException getChecksumInstance()
  {
    if (isStackTrace) {
      return new ChecksumException();
    }
    return instance;
  }
  
  public static ChecksumException getChecksumInstance(Throwable paramThrowable)
  {
    if (isStackTrace) {
      return new ChecksumException(paramThrowable);
    }
    return instance;
  }
}
