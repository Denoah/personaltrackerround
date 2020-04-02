package io.reactivex.internal.util;

public enum ErrorMode
{
  static
  {
    BOUNDARY = new ErrorMode("BOUNDARY", 1);
    ErrorMode localErrorMode = new ErrorMode("END", 2);
    END = localErrorMode;
    $VALUES = new ErrorMode[] { IMMEDIATE, BOUNDARY, localErrorMode };
  }
  
  private ErrorMode() {}
}
