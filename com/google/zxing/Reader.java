package com.google.zxing;

import java.util.Map;

public abstract interface Reader
{
  public abstract Result decode(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException, ChecksumException, FormatException;
  
  public abstract Result decode(BinaryBitmap paramBinaryBitmap, Map<DecodeHintType, ?> paramMap)
    throws NotFoundException, ChecksumException, FormatException;
  
  public abstract void reset();
}
