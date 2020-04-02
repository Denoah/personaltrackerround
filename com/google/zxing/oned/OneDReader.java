package com.google.zxing.oned;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public abstract class OneDReader
  implements Reader
{
  public OneDReader() {}
  
  /* Error */
  private Result doDecode(BinaryBitmap paramBinaryBitmap, Map<DecodeHintType, ?> paramMap)
    throws NotFoundException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 23	com/google/zxing/BinaryBitmap:getWidth	()I
    //   4: istore_3
    //   5: aload_1
    //   6: invokevirtual 26	com/google/zxing/BinaryBitmap:getHeight	()I
    //   9: istore 4
    //   11: new 28	com/google/zxing/common/BitArray
    //   14: dup
    //   15: iload_3
    //   16: invokespecial 31	com/google/zxing/common/BitArray:<init>	(I)V
    //   19: astore 5
    //   21: iconst_1
    //   22: istore 6
    //   24: aload_2
    //   25: ifnull +21 -> 46
    //   28: aload_2
    //   29: getstatic 37	com/google/zxing/DecodeHintType:TRY_HARDER	Lcom/google/zxing/DecodeHintType;
    //   32: invokeinterface 43 2 0
    //   37: ifeq +9 -> 46
    //   40: iconst_1
    //   41: istore 7
    //   43: goto +6 -> 49
    //   46: iconst_0
    //   47: istore 7
    //   49: iload 7
    //   51: ifeq +10 -> 61
    //   54: bipush 8
    //   56: istore 8
    //   58: goto +6 -> 64
    //   61: iconst_5
    //   62: istore 8
    //   64: iconst_1
    //   65: iload 4
    //   67: iload 8
    //   69: ishr
    //   70: invokestatic 49	java/lang/Math:max	(II)I
    //   73: istore 9
    //   75: iload 7
    //   77: ifeq +10 -> 87
    //   80: iload 4
    //   82: istore 10
    //   84: goto +7 -> 91
    //   87: bipush 15
    //   89: istore 10
    //   91: iconst_0
    //   92: istore 11
    //   94: iload 6
    //   96: istore 7
    //   98: iload_3
    //   99: istore 8
    //   101: iload 11
    //   103: iload 10
    //   105: if_icmpge +329 -> 434
    //   108: iload 11
    //   110: iconst_1
    //   111: iadd
    //   112: istore 12
    //   114: iload 12
    //   116: iconst_2
    //   117: idiv
    //   118: istore 6
    //   120: iload 11
    //   122: iconst_1
    //   123: iand
    //   124: ifne +9 -> 133
    //   127: iload 7
    //   129: istore_3
    //   130: goto +5 -> 135
    //   133: iconst_0
    //   134: istore_3
    //   135: iload_3
    //   136: ifeq +9 -> 145
    //   139: iload 6
    //   141: istore_3
    //   142: goto +7 -> 149
    //   145: iload 6
    //   147: ineg
    //   148: istore_3
    //   149: iload_3
    //   150: iload 9
    //   152: imul
    //   153: iload 4
    //   155: iconst_1
    //   156: ishr
    //   157: iadd
    //   158: istore 13
    //   160: iload 13
    //   162: iflt +272 -> 434
    //   165: iload 13
    //   167: iload 4
    //   169: if_icmpge +265 -> 434
    //   172: aload_1
    //   173: iload 13
    //   175: aload 5
    //   177: invokevirtual 53	com/google/zxing/BinaryBitmap:getBlackRow	(ILcom/google/zxing/common/BitArray;)Lcom/google/zxing/common/BitArray;
    //   180: astore 14
    //   182: iconst_0
    //   183: istore 11
    //   185: aload_2
    //   186: astore 15
    //   188: iload 8
    //   190: istore_3
    //   191: aload 14
    //   193: astore 5
    //   195: iload 7
    //   197: istore 6
    //   199: iload 11
    //   201: iconst_2
    //   202: if_icmpge +215 -> 417
    //   205: aload_2
    //   206: astore 5
    //   208: iload 11
    //   210: iload 7
    //   212: if_icmpne +60 -> 272
    //   215: aload 14
    //   217: invokevirtual 56	com/google/zxing/common/BitArray:reverse	()V
    //   220: aload_2
    //   221: astore 5
    //   223: aload_2
    //   224: ifnull +48 -> 272
    //   227: aload_2
    //   228: astore 5
    //   230: aload_2
    //   231: getstatic 59	com/google/zxing/DecodeHintType:NEED_RESULT_POINT_CALLBACK	Lcom/google/zxing/DecodeHintType;
    //   234: invokeinterface 43 2 0
    //   239: ifeq +33 -> 272
    //   242: new 61	java/util/EnumMap
    //   245: dup
    //   246: ldc 33
    //   248: invokespecial 64	java/util/EnumMap:<init>	(Ljava/lang/Class;)V
    //   251: astore 5
    //   253: aload 5
    //   255: aload_2
    //   256: invokeinterface 68 2 0
    //   261: aload 5
    //   263: getstatic 59	com/google/zxing/DecodeHintType:NEED_RESULT_POINT_CALLBACK	Lcom/google/zxing/DecodeHintType;
    //   266: invokeinterface 72 2 0
    //   271: pop
    //   272: aload_0
    //   273: iload 13
    //   275: aload 14
    //   277: aload 5
    //   279: invokevirtual 76	com/google/zxing/oned/OneDReader:decodeRow	(ILcom/google/zxing/common/BitArray;Ljava/util/Map;)Lcom/google/zxing/Result;
    //   282: astore_2
    //   283: iload 11
    //   285: iload 7
    //   287: if_icmpne +118 -> 405
    //   290: getstatic 82	com/google/zxing/ResultMetadataType:ORIENTATION	Lcom/google/zxing/ResultMetadataType;
    //   293: astore 15
    //   295: aload_2
    //   296: aload 15
    //   298: sipush 180
    //   301: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   304: invokevirtual 94	com/google/zxing/Result:putMetadata	(Lcom/google/zxing/ResultMetadataType;Ljava/lang/Object;)V
    //   307: aload_2
    //   308: invokevirtual 98	com/google/zxing/Result:getResultPoints	()[Lcom/google/zxing/ResultPoint;
    //   311: astore 15
    //   313: aload 15
    //   315: ifnull +90 -> 405
    //   318: new 100	com/google/zxing/ResultPoint
    //   321: astore 16
    //   323: iload 8
    //   325: i2f
    //   326: fstore 17
    //   328: aload 15
    //   330: iconst_0
    //   331: aaload
    //   332: invokevirtual 104	com/google/zxing/ResultPoint:getX	()F
    //   335: fstore 18
    //   337: aload 16
    //   339: fload 17
    //   341: fload 18
    //   343: fsub
    //   344: fconst_1
    //   345: fsub
    //   346: aload 15
    //   348: iconst_0
    //   349: aaload
    //   350: invokevirtual 107	com/google/zxing/ResultPoint:getY	()F
    //   353: invokespecial 110	com/google/zxing/ResultPoint:<init>	(FF)V
    //   356: aload 15
    //   358: iconst_0
    //   359: aload 16
    //   361: aastore
    //   362: iconst_1
    //   363: istore 7
    //   365: aload 15
    //   367: iconst_1
    //   368: new 100	com/google/zxing/ResultPoint
    //   371: dup
    //   372: fload 17
    //   374: aload 15
    //   376: iconst_1
    //   377: aaload
    //   378: invokevirtual 104	com/google/zxing/ResultPoint:getX	()F
    //   381: fsub
    //   382: fconst_1
    //   383: fsub
    //   384: aload 15
    //   386: iconst_1
    //   387: aaload
    //   388: invokevirtual 107	com/google/zxing/ResultPoint:getY	()F
    //   391: invokespecial 110	com/google/zxing/ResultPoint:<init>	(FF)V
    //   394: aastore
    //   395: goto +10 -> 405
    //   398: astore_2
    //   399: iconst_1
    //   400: istore 7
    //   402: goto +6 -> 408
    //   405: aload_2
    //   406: areturn
    //   407: astore_2
    //   408: iinc 11 1
    //   411: aload 5
    //   413: astore_2
    //   414: goto -229 -> 185
    //   417: iload 12
    //   419: istore 11
    //   421: iload 6
    //   423: istore 7
    //   425: iload_3
    //   426: istore 8
    //   428: aload 15
    //   430: astore_2
    //   431: goto -330 -> 101
    //   434: invokestatic 114	com/google/zxing/NotFoundException:getNotFoundInstance	()Lcom/google/zxing/NotFoundException;
    //   437: athrow
    //   438: astore 15
    //   440: aload_2
    //   441: astore 15
    //   443: iload 8
    //   445: istore_3
    //   446: iload 7
    //   448: istore 6
    //   450: goto -33 -> 417
    //   453: astore_2
    //   454: goto -55 -> 399
    //   457: astore_2
    //   458: goto -59 -> 399
    //   461: astore_2
    //   462: goto -54 -> 408
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	465	0	this	OneDReader
    //   0	465	1	paramBinaryBitmap	BinaryBitmap
    //   0	465	2	paramMap	Map<DecodeHintType, ?>
    //   4	442	3	i	int
    //   9	161	4	j	int
    //   19	393	5	localObject1	Object
    //   22	427	6	k	int
    //   41	406	7	m	int
    //   56	388	8	n	int
    //   73	80	9	i1	int
    //   82	24	10	i2	int
    //   92	328	11	i3	int
    //   112	306	12	i4	int
    //   158	116	13	i5	int
    //   180	96	14	localBitArray	BitArray
    //   186	243	15	localObject2	Object
    //   438	1	15	localNotFoundException	NotFoundException
    //   441	1	15	localMap	Map<DecodeHintType, ?>
    //   321	39	16	localResultPoint	ResultPoint
    //   326	47	17	f1	float
    //   335	7	18	f2	float
    // Exception table:
    //   from	to	target	type
    //   295	313	398	com/google/zxing/ReaderException
    //   318	323	398	com/google/zxing/ReaderException
    //   272	283	407	com/google/zxing/ReaderException
    //   290	295	407	com/google/zxing/ReaderException
    //   172	182	438	com/google/zxing/NotFoundException
    //   328	337	453	com/google/zxing/ReaderException
    //   337	356	457	com/google/zxing/ReaderException
    //   365	395	461	com/google/zxing/ReaderException
  }
  
  protected static float patternMatchVariance(int[] paramArrayOfInt1, int[] paramArrayOfInt2, float paramFloat)
  {
    int i = paramArrayOfInt1.length;
    int j = 0;
    int k = 0;
    int m = k;
    int n = m;
    while (k < i)
    {
      m += paramArrayOfInt1[k];
      n += paramArrayOfInt2[k];
      k++;
    }
    if (m < n) {
      return Float.POSITIVE_INFINITY;
    }
    float f1 = m;
    float f2 = f1 / n;
    float f3 = 0.0F;
    for (m = j; m < i; m++)
    {
      n = paramArrayOfInt1[m];
      float f4 = paramArrayOfInt2[m] * f2;
      float f5 = n;
      if (f5 > f4) {
        f4 = f5 - f4;
      } else {
        f4 -= f5;
      }
      if (f4 > paramFloat * f2) {
        return Float.POSITIVE_INFINITY;
      }
      f3 += f4;
    }
    return f3 / f1;
  }
  
  protected static void recordPattern(BitArray paramBitArray, int paramInt, int[] paramArrayOfInt)
    throws NotFoundException
  {
    int i = paramArrayOfInt.length;
    int j = 0;
    Arrays.fill(paramArrayOfInt, 0, i, 0);
    int k = paramBitArray.getSize();
    if (paramInt < k)
    {
      boolean bool = paramBitArray.get(paramInt) ^ true;
      int m = paramInt;
      paramInt = j;
      for (;;)
      {
        j = paramInt;
        if (m >= k) {
          break;
        }
        if ((paramBitArray.get(m) ^ bool))
        {
          paramArrayOfInt[paramInt] += 1;
        }
        else
        {
          paramInt++;
          if (paramInt == i)
          {
            j = paramInt;
            break;
          }
          paramArrayOfInt[paramInt] = 1;
          bool ^= true;
        }
        m++;
      }
      if ((j != i) && ((j != i - 1) || (m != k))) {
        throw NotFoundException.getNotFoundInstance();
      }
      return;
    }
    throw NotFoundException.getNotFoundInstance();
  }
  
  protected static void recordPatternInReverse(BitArray paramBitArray, int paramInt, int[] paramArrayOfInt)
    throws NotFoundException
  {
    int i = paramArrayOfInt.length;
    boolean bool = paramBitArray.get(paramInt);
    while ((paramInt > 0) && (i >= 0))
    {
      int j = paramInt - 1;
      paramInt = j;
      if (paramBitArray.get(j) != bool)
      {
        i--;
        bool ^= true;
        paramInt = j;
      }
    }
    if (i < 0)
    {
      recordPattern(paramBitArray, paramInt + 1, paramArrayOfInt);
      return;
    }
    throw NotFoundException.getNotFoundInstance();
  }
  
  public Result decode(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException, FormatException
  {
    return decode(paramBinaryBitmap, null);
  }
  
  public Result decode(BinaryBitmap paramBinaryBitmap, Map<DecodeHintType, ?> paramMap)
    throws NotFoundException, FormatException
  {
    try
    {
      Result localResult = doDecode(paramBinaryBitmap, paramMap);
      return localResult;
    }
    catch (NotFoundException localNotFoundException)
    {
      int i = 0;
      int j;
      if ((paramMap != null) && (paramMap.containsKey(DecodeHintType.TRY_HARDER))) {
        j = 1;
      } else {
        j = 0;
      }
      Object localObject;
      if ((j != 0) && (paramBinaryBitmap.isRotateSupported()))
      {
        paramBinaryBitmap = paramBinaryBitmap.rotateCounterClockwise();
        paramMap = doDecode(paramBinaryBitmap, paramMap);
        localObject = paramMap.getResultMetadata();
        int k = 270;
        j = k;
        if (localObject != null)
        {
          j = k;
          if (((Map)localObject).containsKey(ResultMetadataType.ORIENTATION)) {
            j = (((Integer)((Map)localObject).get(ResultMetadataType.ORIENTATION)).intValue() + 270) % 360;
          }
        }
        paramMap.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(j));
        localObject = paramMap.getResultPoints();
        if (localObject != null)
        {
          k = paramBinaryBitmap.getHeight();
          for (j = i; j < localObject.length; j++) {
            localObject[j] = new ResultPoint(k - localObject[j].getY() - 1.0F, localObject[j].getX());
          }
        }
        return paramMap;
      }
      throw ((Throwable)localObject);
    }
  }
  
  public abstract Result decodeRow(int paramInt, BitArray paramBitArray, Map<DecodeHintType, ?> paramMap)
    throws NotFoundException, ChecksumException, FormatException;
  
  public void reset() {}
}
