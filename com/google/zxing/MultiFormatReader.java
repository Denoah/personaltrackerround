package com.google.zxing;

import com.google.zxing.aztec.AztecReader;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.maxicode.MaxiCodeReader;
import com.google.zxing.oned.MultiFormatOneDReader;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.qrcode.QRCodeReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class MultiFormatReader
  implements Reader
{
  private Map<DecodeHintType, ?> hints;
  private Reader[] readers;
  
  public MultiFormatReader() {}
  
  private Result decodeInternal(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException
  {
    Reader[] arrayOfReader = this.readers;
    if (arrayOfReader != null)
    {
      int i = arrayOfReader.length;
      int j = 0;
      while (j < i)
      {
        Object localObject = arrayOfReader[j];
        try
        {
          localObject = ((Reader)localObject).decode(paramBinaryBitmap, this.hints);
          return localObject;
        }
        catch (ReaderException localReaderException)
        {
          j++;
        }
      }
    }
    throw NotFoundException.getNotFoundInstance();
  }
  
  public Result decode(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException
  {
    setHints(null);
    return decodeInternal(paramBinaryBitmap);
  }
  
  public Result decode(BinaryBitmap paramBinaryBitmap, Map<DecodeHintType, ?> paramMap)
    throws NotFoundException
  {
    setHints(paramMap);
    return decodeInternal(paramBinaryBitmap);
  }
  
  public Result decodeWithState(BinaryBitmap paramBinaryBitmap)
    throws NotFoundException
  {
    if (this.readers == null) {
      setHints(null);
    }
    return decodeInternal(paramBinaryBitmap);
  }
  
  public void reset()
  {
    Reader[] arrayOfReader = this.readers;
    if (arrayOfReader != null)
    {
      int i = arrayOfReader.length;
      for (int j = 0; j < i; j++) {
        arrayOfReader[j].reset();
      }
    }
  }
  
  public void setHints(Map<DecodeHintType, ?> paramMap)
  {
    this.hints = paramMap;
    int i = 1;
    int j;
    if ((paramMap != null) && (paramMap.containsKey(DecodeHintType.TRY_HARDER))) {
      j = 1;
    } else {
      j = 0;
    }
    Collection localCollection;
    if (paramMap == null) {
      localCollection = null;
    } else {
      localCollection = (Collection)paramMap.get(DecodeHintType.POSSIBLE_FORMATS);
    }
    ArrayList localArrayList = new ArrayList();
    if (localCollection != null)
    {
      int k = i;
      if (!localCollection.contains(BarcodeFormat.UPC_A))
      {
        k = i;
        if (!localCollection.contains(BarcodeFormat.UPC_E))
        {
          k = i;
          if (!localCollection.contains(BarcodeFormat.EAN_13))
          {
            k = i;
            if (!localCollection.contains(BarcodeFormat.EAN_8))
            {
              k = i;
              if (!localCollection.contains(BarcodeFormat.CODABAR))
              {
                k = i;
                if (!localCollection.contains(BarcodeFormat.CODE_39))
                {
                  k = i;
                  if (!localCollection.contains(BarcodeFormat.CODE_93))
                  {
                    k = i;
                    if (!localCollection.contains(BarcodeFormat.CODE_128))
                    {
                      k = i;
                      if (!localCollection.contains(BarcodeFormat.ITF))
                      {
                        k = i;
                        if (!localCollection.contains(BarcodeFormat.RSS_14)) {
                          if (localCollection.contains(BarcodeFormat.RSS_EXPANDED)) {
                            k = i;
                          } else {
                            k = 0;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      if ((k != 0) && (j == 0)) {
        localArrayList.add(new MultiFormatOneDReader(paramMap));
      }
      if (localCollection.contains(BarcodeFormat.QR_CODE)) {
        localArrayList.add(new QRCodeReader());
      }
      if (localCollection.contains(BarcodeFormat.DATA_MATRIX)) {
        localArrayList.add(new DataMatrixReader());
      }
      if (localCollection.contains(BarcodeFormat.AZTEC)) {
        localArrayList.add(new AztecReader());
      }
      if (localCollection.contains(BarcodeFormat.PDF_417)) {
        localArrayList.add(new PDF417Reader());
      }
      if (localCollection.contains(BarcodeFormat.MAXICODE)) {
        localArrayList.add(new MaxiCodeReader());
      }
      if ((k != 0) && (j != 0)) {
        localArrayList.add(new MultiFormatOneDReader(paramMap));
      }
    }
    if (localArrayList.isEmpty())
    {
      if (j == 0) {
        localArrayList.add(new MultiFormatOneDReader(paramMap));
      }
      localArrayList.add(new QRCodeReader());
      localArrayList.add(new DataMatrixReader());
      localArrayList.add(new AztecReader());
      localArrayList.add(new PDF417Reader());
      localArrayList.add(new MaxiCodeReader());
      if (j != 0) {
        localArrayList.add(new MultiFormatOneDReader(paramMap));
      }
    }
    this.readers = ((Reader[])localArrayList.toArray(new Reader[localArrayList.size()]));
  }
}
