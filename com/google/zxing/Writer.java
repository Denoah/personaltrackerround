package com.google.zxing;

import com.google.zxing.common.BitMatrix;
import java.util.Map;

public abstract interface Writer
{
  public abstract BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2)
    throws WriterException;
  
  public abstract BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Map<EncodeHintType, ?> paramMap)
    throws WriterException;
}
