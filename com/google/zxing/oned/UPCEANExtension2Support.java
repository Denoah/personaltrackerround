package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.EnumMap;
import java.util.Map;

final class UPCEANExtension2Support
{
  private final int[] decodeMiddleCounters = new int[4];
  private final StringBuilder decodeRowStringBuffer = new StringBuilder();
  
  UPCEANExtension2Support() {}
  
  private static Map<ResultMetadataType, Object> parseExtensionString(String paramString)
  {
    if (paramString.length() != 2) {
      return null;
    }
    EnumMap localEnumMap = new EnumMap(ResultMetadataType.class);
    localEnumMap.put(ResultMetadataType.ISSUE_NUMBER, Integer.valueOf(paramString));
    return localEnumMap;
  }
  
  int decodeMiddle(BitArray paramBitArray, int[] paramArrayOfInt, StringBuilder paramStringBuilder)
    throws NotFoundException
  {
    int[] arrayOfInt = this.decodeMiddleCounters;
    arrayOfInt[0] = 0;
    arrayOfInt[1] = 0;
    arrayOfInt[2] = 0;
    arrayOfInt[3] = 0;
    int i = paramBitArray.getSize();
    int j = paramArrayOfInt[1];
    int k = 0;
    int m = k;
    while ((k < 2) && (j < i))
    {
      int n = UPCEANReader.decodeDigit(paramBitArray, arrayOfInt, j, UPCEANReader.L_AND_G_PATTERNS);
      paramStringBuilder.append((char)(n % 10 + 48));
      int i1 = arrayOfInt.length;
      for (int i2 = 0; i2 < i1; i2++) {
        j += arrayOfInt[i2];
      }
      i1 = m;
      if (n >= 10) {
        i1 = m | 1 << 1 - k;
      }
      i2 = j;
      if (k != 1) {
        i2 = paramBitArray.getNextUnset(paramBitArray.getNextSet(j));
      }
      k++;
      m = i1;
      j = i2;
    }
    if (paramStringBuilder.length() == 2)
    {
      if (Integer.parseInt(paramStringBuilder.toString()) % 4 == m) {
        return j;
      }
      throw NotFoundException.getNotFoundInstance();
    }
    throw NotFoundException.getNotFoundInstance();
  }
  
  Result decodeRow(int paramInt, BitArray paramBitArray, int[] paramArrayOfInt)
    throws NotFoundException
  {
    Object localObject = this.decodeRowStringBuffer;
    ((StringBuilder)localObject).setLength(0);
    int i = decodeMiddle(paramBitArray, paramArrayOfInt, (StringBuilder)localObject);
    localObject = ((StringBuilder)localObject).toString();
    paramBitArray = parseExtensionString((String)localObject);
    float f1 = (paramArrayOfInt[0] + paramArrayOfInt[1]) / 2.0F;
    float f2 = paramInt;
    paramArrayOfInt = new ResultPoint(f1, f2);
    ResultPoint localResultPoint = new ResultPoint(i, f2);
    BarcodeFormat localBarcodeFormat = BarcodeFormat.UPC_EAN_EXTENSION;
    paramArrayOfInt = new Result((String)localObject, null, new ResultPoint[] { paramArrayOfInt, localResultPoint }, localBarcodeFormat);
    if (paramBitArray != null) {
      paramArrayOfInt.putAllMetadata(paramBitArray);
    }
    return paramArrayOfInt;
  }
}
