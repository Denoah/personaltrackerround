package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public abstract class UPCEANReader
  extends OneDReader
{
  static final int[][] L_AND_G_PATTERNS;
  static final int[][] L_PATTERNS;
  private static final float MAX_AVG_VARIANCE = 0.48F;
  private static final float MAX_INDIVIDUAL_VARIANCE = 0.7F;
  static final int[] MIDDLE_PATTERN;
  static final int[] START_END_PATTERN = { 1, 1, 1 };
  private final StringBuilder decodeRowStringBuffer = new StringBuilder(20);
  private final EANManufacturerOrgSupport eanManSupport = new EANManufacturerOrgSupport();
  private final UPCEANExtensionSupport extensionReader = new UPCEANExtensionSupport();
  
  static
  {
    MIDDLE_PATTERN = new int[] { 1, 1, 1, 1, 1 };
    int i = 10;
    Object localObject1 = new int[10][];
    localObject1[0] = { 3, 2, 1, 1 };
    localObject1[1] = { 2, 2, 2, 1 };
    localObject1[2] = { 2, 1, 2, 2 };
    localObject1[3] = { 1, 4, 1, 1 };
    localObject1[4] = { 1, 1, 3, 2 };
    localObject1[5] = { 1, 2, 3, 1 };
    localObject1[6] = { 1, 1, 1, 4 };
    localObject1[7] = { 1, 3, 1, 2 };
    localObject1[8] = { 1, 2, 1, 3 };
    localObject1[9] = { 3, 1, 1, 2 };
    L_PATTERNS = (int[][])localObject1;
    Object localObject2 = new int[20][];
    L_AND_G_PATTERNS = (int[][])localObject2;
    System.arraycopy(localObject1, 0, localObject2, 0, 10);
    while (i < 20)
    {
      localObject2 = L_PATTERNS[(i - 10)];
      localObject1 = new int[localObject2.length];
      for (int j = 0; j < localObject2.length; j++) {
        localObject1[j] = localObject2[(localObject2.length - j - 1)];
      }
      L_AND_G_PATTERNS[i] = localObject1;
      i++;
    }
  }
  
  protected UPCEANReader() {}
  
  static boolean checkStandardUPCEANChecksum(CharSequence paramCharSequence)
    throws FormatException
  {
    int i = paramCharSequence.length();
    boolean bool = false;
    if (i == 0) {
      return false;
    }
    int j = i - 2;
    int k = 0;
    while (j >= 0)
    {
      int m = paramCharSequence.charAt(j) - '0';
      if ((m >= 0) && (m <= 9))
      {
        k += m;
        j -= 2;
      }
      else
      {
        throw FormatException.getFormatInstance();
      }
    }
    j = k * 3;
    k = i - 1;
    while (k >= 0)
    {
      i = paramCharSequence.charAt(k) - '0';
      if ((i >= 0) && (i <= 9))
      {
        j += i;
        k -= 2;
      }
      else
      {
        throw FormatException.getFormatInstance();
      }
    }
    if (j % 10 == 0) {
      bool = true;
    }
    return bool;
  }
  
  static int decodeDigit(BitArray paramBitArray, int[] paramArrayOfInt, int paramInt, int[][] paramArrayOfInt1)
    throws NotFoundException
  {
    recordPattern(paramBitArray, paramInt, paramArrayOfInt);
    int i = paramArrayOfInt1.length;
    float f1 = 0.48F;
    int j = -1;
    paramInt = 0;
    while (paramInt < i)
    {
      float f2 = patternMatchVariance(paramArrayOfInt, paramArrayOfInt1[paramInt], 0.7F);
      float f3 = f1;
      if (f2 < f1)
      {
        j = paramInt;
        f3 = f2;
      }
      paramInt++;
      f1 = f3;
    }
    if (j >= 0) {
      return j;
    }
    throw NotFoundException.getNotFoundInstance();
  }
  
  static int[] findGuardPattern(BitArray paramBitArray, int paramInt, boolean paramBoolean, int[] paramArrayOfInt)
    throws NotFoundException
  {
    return findGuardPattern(paramBitArray, paramInt, paramBoolean, paramArrayOfInt, new int[paramArrayOfInt.length]);
  }
  
  private static int[] findGuardPattern(BitArray paramBitArray, int paramInt, boolean paramBoolean, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    throws NotFoundException
  {
    int i = paramArrayOfInt1.length;
    int j = paramBitArray.getSize();
    if (paramBoolean) {
      paramInt = paramBitArray.getNextUnset(paramInt);
    } else {
      paramInt = paramBitArray.getNextSet(paramInt);
    }
    int k = 0;
    int m = paramInt;
    int n = paramInt;
    paramInt = k;
    while (n < j)
    {
      if ((paramBitArray.get(n) ^ paramBoolean))
      {
        paramArrayOfInt2[paramInt] += 1;
      }
      else
      {
        k = i - 1;
        if (paramInt == k)
        {
          if (patternMatchVariance(paramArrayOfInt2, paramArrayOfInt1, 0.7F) < 0.48F) {
            return new int[] { m, n };
          }
          m += paramArrayOfInt2[0] + paramArrayOfInt2[1];
          int i1 = i - 2;
          System.arraycopy(paramArrayOfInt2, 2, paramArrayOfInt2, 0, i1);
          paramArrayOfInt2[i1] = 0;
          paramArrayOfInt2[k] = 0;
          paramInt--;
        }
        else
        {
          paramInt++;
        }
        paramArrayOfInt2[paramInt] = 1;
        paramBoolean ^= true;
      }
      n++;
    }
    throw NotFoundException.getNotFoundInstance();
  }
  
  static int[] findStartGuardPattern(BitArray paramBitArray)
    throws NotFoundException
  {
    int[] arrayOfInt1 = new int[START_END_PATTERN.length];
    int[] arrayOfInt2 = null;
    int i = 0;
    int j = i;
    while (i == 0)
    {
      Arrays.fill(arrayOfInt1, 0, START_END_PATTERN.length, 0);
      arrayOfInt2 = findGuardPattern(paramBitArray, j, false, START_END_PATTERN, arrayOfInt1);
      int m = arrayOfInt2[0];
      int k = arrayOfInt2[1];
      int n = m - (k - m);
      if (n >= 0) {
        i = paramBitArray.isRange(n, m, false);
      }
    }
    return arrayOfInt2;
  }
  
  boolean checkChecksum(String paramString)
    throws FormatException
  {
    return checkStandardUPCEANChecksum(paramString);
  }
  
  int[] decodeEnd(BitArray paramBitArray, int paramInt)
    throws NotFoundException
  {
    return findGuardPattern(paramBitArray, paramInt, false, START_END_PATTERN);
  }
  
  protected abstract int decodeMiddle(BitArray paramBitArray, int[] paramArrayOfInt, StringBuilder paramStringBuilder)
    throws NotFoundException;
  
  public Result decodeRow(int paramInt, BitArray paramBitArray, Map<DecodeHintType, ?> paramMap)
    throws NotFoundException, ChecksumException, FormatException
  {
    return decodeRow(paramInt, paramBitArray, findStartGuardPattern(paramBitArray), paramMap);
  }
  
  public Result decodeRow(int paramInt, BitArray paramBitArray, int[] paramArrayOfInt, Map<DecodeHintType, ?> paramMap)
    throws NotFoundException, ChecksumException, FormatException
  {
    Object localObject1 = null;
    Object localObject2;
    if (paramMap == null) {
      localObject2 = null;
    } else {
      localObject2 = (ResultPointCallback)paramMap.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
    }
    int i = 1;
    if (localObject2 != null) {
      ((ResultPointCallback)localObject2).foundPossibleResultPoint(new ResultPoint((paramArrayOfInt[0] + paramArrayOfInt[1]) / 2.0F, paramInt));
    }
    Object localObject3 = this.decodeRowStringBuffer;
    ((StringBuilder)localObject3).setLength(0);
    int j = decodeMiddle(paramBitArray, paramArrayOfInt, (StringBuilder)localObject3);
    if (localObject2 != null) {
      ((ResultPointCallback)localObject2).foundPossibleResultPoint(new ResultPoint(j, paramInt));
    }
    int[] arrayOfInt = decodeEnd(paramBitArray, j);
    if (localObject2 != null) {
      ((ResultPointCallback)localObject2).foundPossibleResultPoint(new ResultPoint((arrayOfInt[0] + arrayOfInt[1]) / 2.0F, paramInt));
    }
    j = arrayOfInt[1];
    int k = j - arrayOfInt[0] + j;
    if ((k < paramBitArray.getSize()) && (paramBitArray.isRange(j, k, false)))
    {
      localObject2 = ((StringBuilder)localObject3).toString();
      if (((String)localObject2).length() >= 8)
      {
        if (checkChecksum((String)localObject2))
        {
          float f1 = (paramArrayOfInt[1] + paramArrayOfInt[0]) / 2.0F;
          float f2 = (arrayOfInt[1] + arrayOfInt[0]) / 2.0F;
          localObject3 = getBarcodeFormat();
          float f3 = paramInt;
          paramArrayOfInt = new Result((String)localObject2, null, new ResultPoint[] { new ResultPoint(f1, f3), new ResultPoint(f2, f3) }, (BarcodeFormat)localObject3);
          try
          {
            paramBitArray = this.extensionReader.decodeRow(paramInt, paramBitArray, arrayOfInt[1]);
            paramArrayOfInt.putMetadata(ResultMetadataType.UPC_EAN_EXTENSION, paramBitArray.getText());
            paramArrayOfInt.putAllMetadata(paramBitArray.getResultMetadata());
            paramArrayOfInt.addResultPoints(paramBitArray.getResultPoints());
            paramInt = paramBitArray.getText().length();
          }
          catch (ReaderException paramBitArray)
          {
            paramInt = 0;
          }
          if (paramMap == null) {
            paramBitArray = localObject1;
          } else {
            paramBitArray = (int[])paramMap.get(DecodeHintType.ALLOWED_EAN_EXTENSIONS);
          }
          if (paramBitArray != null)
          {
            k = paramBitArray.length;
            for (j = 0; j < k; j++) {
              if (paramInt == paramBitArray[j])
              {
                paramInt = i;
                break label417;
              }
            }
            paramInt = 0;
            label417:
            if (paramInt == 0) {
              throw NotFoundException.getNotFoundInstance();
            }
          }
          if ((localObject3 == BarcodeFormat.EAN_13) || (localObject3 == BarcodeFormat.UPC_A))
          {
            paramBitArray = this.eanManSupport.lookupCountryIdentifier((String)localObject2);
            if (paramBitArray != null) {
              paramArrayOfInt.putMetadata(ResultMetadataType.POSSIBLE_COUNTRY, paramBitArray);
            }
          }
          return paramArrayOfInt;
        }
        throw ChecksumException.getChecksumInstance();
      }
      throw FormatException.getFormatInstance();
    }
    throw NotFoundException.getNotFoundInstance();
  }
  
  abstract BarcodeFormat getBarcodeFormat();
}
