package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class UPCAWriter
  implements Writer
{
  private final EAN13Writer subWriter = new EAN13Writer();
  
  public UPCAWriter() {}
  
  private static String preencode(String paramString)
  {
    int i = paramString.length();
    if (i == 11)
    {
      i = 0;
      int j = 0;
      while (i < 11)
      {
        int k = paramString.charAt(i);
        int m;
        if (i % 2 == 0) {
          m = 3;
        } else {
          m = 1;
        }
        j += (k - 48) * m;
        i++;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append((1000 - j) % 10);
      paramString = localStringBuilder.toString();
    }
    else
    {
      if (i != 12) {
        break label133;
      }
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('0');
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
    label133:
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Requested contents should be 11 or 12 digits long, but got ");
    localStringBuilder.append(paramString.length());
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2)
    throws WriterException
  {
    return encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, null);
  }
  
  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Map<EncodeHintType, ?> paramMap)
    throws WriterException
  {
    if (paramBarcodeFormat == BarcodeFormat.UPC_A) {
      return this.subWriter.encode(preencode(paramString), BarcodeFormat.EAN_13, paramInt1, paramInt2, paramMap);
    }
    paramString = new StringBuilder();
    paramString.append("Can only encode UPC-A, but got ");
    paramString.append(paramBarcodeFormat);
    throw new IllegalArgumentException(paramString.toString());
  }
}
