package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public final class CodaBarReader
  extends OneDReader
{
  static final char[] ALPHABET = "0123456789-$:/.+ABCD".toCharArray();
  private static final String ALPHABET_STRING = "0123456789-$:/.+ABCD";
  static final int[] CHARACTER_ENCODINGS = { 3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14 };
  private static final float MAX_ACCEPTABLE = 2.0F;
  private static final int MIN_CHARACTER_LENGTH = 3;
  private static final float PADDING = 1.5F;
  private static final char[] STARTEND_ENCODING = { 65, 66, 67, 68 };
  private int counterLength = 0;
  private int[] counters = new int[80];
  private final StringBuilder decodeRowResult = new StringBuilder(20);
  
  public CodaBarReader() {}
  
  static boolean arrayContains(char[] paramArrayOfChar, char paramChar)
  {
    if (paramArrayOfChar != null)
    {
      int i = paramArrayOfChar.length;
      for (int j = 0; j < i; j++) {
        if (paramArrayOfChar[j] == paramChar) {
          return true;
        }
      }
    }
    return false;
  }
  
  private void counterAppend(int paramInt)
  {
    int[] arrayOfInt1 = this.counters;
    int i = this.counterLength;
    arrayOfInt1[i] = paramInt;
    paramInt = i + 1;
    this.counterLength = paramInt;
    if (paramInt >= arrayOfInt1.length)
    {
      int[] arrayOfInt2 = new int[paramInt * 2];
      System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, paramInt);
      this.counters = arrayOfInt2;
    }
  }
  
  private int findStartPattern()
    throws NotFoundException
  {
    for (int i = 1; i < this.counterLength; i += 2)
    {
      int j = toNarrowWidePattern(i);
      if ((j != -1) && (arrayContains(STARTEND_ENCODING, ALPHABET[j])))
      {
        int k = 0;
        for (j = i; j < i + 7; j++) {
          k += this.counters[j];
        }
        if ((i == 1) || (this.counters[(i - 1)] >= k / 2)) {
          return i;
        }
      }
    }
    throw NotFoundException.getNotFoundInstance();
  }
  
  private void setCounters(BitArray paramBitArray)
    throws NotFoundException
  {
    int i = 0;
    this.counterLength = 0;
    int j = paramBitArray.getNextUnset(0);
    int k = paramBitArray.getSize();
    if (j < k)
    {
      int m = 1;
      while (j < k)
      {
        if ((paramBitArray.get(j) ^ m))
        {
          i++;
        }
        else
        {
          counterAppend(i);
          m ^= 0x1;
          i = 1;
        }
        j++;
      }
      counterAppend(i);
      return;
    }
    throw NotFoundException.getNotFoundInstance();
  }
  
  private int toNarrowWidePattern(int paramInt)
  {
    int i = paramInt + 7;
    if (i >= this.counterLength) {
      return -1;
    }
    int[] arrayOfInt = this.counters;
    int j = Integer.MAX_VALUE;
    int k = 0;
    int m = paramInt;
    int n = Integer.MAX_VALUE;
    int i2;
    int i4;
    for (int i1 = 0; m < i; i1 = i4)
    {
      i2 = arrayOfInt[m];
      i3 = n;
      if (i2 < n) {
        i3 = i2;
      }
      i4 = i1;
      if (i2 > i1) {
        i4 = i2;
      }
      m += 2;
      n = i3;
    }
    int i5 = (n + i1) / 2;
    m = paramInt + 1;
    i1 = 0;
    n = j;
    while (m < i)
    {
      i2 = arrayOfInt[m];
      i3 = n;
      if (i2 < n) {
        i3 = i2;
      }
      i4 = i1;
      if (i2 > i1) {
        i4 = i2;
      }
      m += 2;
      n = i3;
      i1 = i4;
    }
    n = (n + i1) / 2;
    int i3 = 128;
    m = 0;
    for (i1 = m;; i1 = i4)
    {
      i4 = k;
      if (m >= 7) {
        break;
      }
      if ((m & 0x1) == 0) {
        i2 = i5;
      } else {
        i2 = n;
      }
      i3 >>= 1;
      i4 = i1;
      if (arrayOfInt[(paramInt + m)] > i2) {
        i4 = i1 | i3;
      }
      m++;
    }
    for (;;)
    {
      arrayOfInt = CHARACTER_ENCODINGS;
      if (i4 >= arrayOfInt.length) {
        break;
      }
      if (arrayOfInt[i4] == i1) {
        return i4;
      }
      i4++;
    }
    return -1;
  }
  
  public Result decodeRow(int paramInt, BitArray paramBitArray, Map<DecodeHintType, ?> paramMap)
    throws NotFoundException
  {
    Arrays.fill(this.counters, 0);
    setCounters(paramBitArray);
    int i = findStartPattern();
    this.decodeRowResult.setLength(0);
    int j = i;
    int m;
    do
    {
      k = toNarrowWidePattern(j);
      if (k == -1) {
        break label476;
      }
      this.decodeRowResult.append((char)k);
      m = j + 8;
      if ((this.decodeRowResult.length() > 1) && (arrayContains(STARTEND_ENCODING, ALPHABET[k]))) {
        break;
      }
      j = m;
    } while (m < this.counterLength);
    paramBitArray = this.counters;
    int n = m - 1;
    int i1 = paramBitArray[n];
    int k = -8;
    j = 0;
    while (k < -1)
    {
      j += this.counters[(m + k)];
      k++;
    }
    if ((m < this.counterLength) && (i1 < j / 2)) {
      throw NotFoundException.getNotFoundInstance();
    }
    validatePattern(i);
    for (j = 0; j < this.decodeRowResult.length(); j++)
    {
      paramBitArray = this.decodeRowResult;
      paramBitArray.setCharAt(j, ALPHABET[paramBitArray.charAt(j)]);
    }
    char c = this.decodeRowResult.charAt(0);
    if (arrayContains(STARTEND_ENCODING, c))
    {
      paramBitArray = this.decodeRowResult;
      c = paramBitArray.charAt(paramBitArray.length() - 1);
      if (arrayContains(STARTEND_ENCODING, c))
      {
        if (this.decodeRowResult.length() > 3)
        {
          if ((paramMap == null) || (!paramMap.containsKey(DecodeHintType.RETURN_CODABAR_START_END)))
          {
            paramBitArray = this.decodeRowResult;
            paramBitArray.deleteCharAt(paramBitArray.length() - 1);
            this.decodeRowResult.deleteCharAt(0);
          }
          k = 0;
          j = k;
          while (k < i)
          {
            j += this.counters[k];
            k++;
          }
          float f1 = j;
          while (i < n)
          {
            j += this.counters[i];
            i++;
          }
          float f2 = j;
          paramMap = this.decodeRowResult.toString();
          float f3 = paramInt;
          paramBitArray = new ResultPoint(f1, f3);
          ResultPoint localResultPoint = new ResultPoint(f2, f3);
          BarcodeFormat localBarcodeFormat = BarcodeFormat.CODABAR;
          return new Result(paramMap, null, new ResultPoint[] { paramBitArray, localResultPoint }, localBarcodeFormat);
        }
        throw NotFoundException.getNotFoundInstance();
      }
      throw NotFoundException.getNotFoundInstance();
    }
    throw NotFoundException.getNotFoundInstance();
    label476:
    throw NotFoundException.getNotFoundInstance();
  }
  
  void validatePattern(int paramInt)
    throws NotFoundException
  {
    int[] arrayOfInt1 = new int[4];
    int[] tmp5_4 = arrayOfInt1;
    tmp5_4[0] = 0;
    int[] tmp9_5 = tmp5_4;
    tmp9_5[1] = 0;
    int[] tmp13_9 = tmp9_5;
    tmp13_9[2] = 0;
    int[] tmp17_13 = tmp13_9;
    tmp17_13[3] = 0;
    tmp17_13;
    int[] arrayOfInt2 = new int[4];
    int[] tmp27_26 = arrayOfInt2;
    tmp27_26[0] = 0;
    int[] tmp31_27 = tmp27_26;
    tmp31_27[1] = 0;
    int[] tmp35_31 = tmp31_27;
    tmp35_31[2] = 0;
    int[] tmp39_35 = tmp35_31;
    tmp39_35[3] = 0;
    tmp39_35;
    int i = this.decodeRowResult.length() - 1;
    int j = 0;
    int k = paramInt;
    for (int m = 0;; m++)
    {
      int n = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(m)];
      for (int i1 = 6; i1 >= 0; i1--)
      {
        int i2 = (i1 & 0x1) + (n & 0x1) * 2;
        arrayOfInt1[i2] += this.counters[(k + i1)];
        arrayOfInt2[i2] += 1;
        n >>= 1;
      }
      if (m >= i)
      {
        float[] arrayOfFloat1 = new float[4];
        float[] arrayOfFloat2 = new float[4];
        for (i1 = 0;; i1++)
        {
          m = j;
          k = paramInt;
          if (i1 >= 2) {
            break;
          }
          arrayOfFloat2[i1] = 0.0F;
          m = i1 + 2;
          arrayOfFloat2[m] = ((arrayOfInt1[i1] / arrayOfInt2[i1] + arrayOfInt1[m] / arrayOfInt2[m]) / 2.0F);
          arrayOfFloat1[i1] = arrayOfFloat2[m];
          arrayOfFloat1[m] = ((arrayOfInt1[m] * 2.0F + 1.5F) / arrayOfInt2[m]);
        }
        for (;;)
        {
          i1 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(m)];
          paramInt = 6;
          while (paramInt >= 0)
          {
            n = (paramInt & 0x1) + (i1 & 0x1) * 2;
            float f = this.counters[(k + paramInt)];
            if ((f >= arrayOfFloat2[n]) && (f <= arrayOfFloat1[n]))
            {
              i1 >>= 1;
              paramInt--;
            }
            else
            {
              throw NotFoundException.getNotFoundInstance();
            }
          }
          if (m >= i) {
            return;
          }
          k += 8;
          m++;
        }
      }
      k += 8;
    }
  }
}
