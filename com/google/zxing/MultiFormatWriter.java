package com.google.zxing;

import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Map;

public final class MultiFormatWriter
  implements Writer
{
  public MultiFormatWriter() {}
  
  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2)
    throws WriterException
  {
    return encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, null);
  }
  
  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Map<EncodeHintType, ?> paramMap)
    throws WriterException
  {
    Object localObject;
    switch (1.$SwitchMap$com$google$zxing$BarcodeFormat[paramBarcodeFormat.ordinal()])
    {
    default: 
      paramString = new StringBuilder();
      paramString.append("No encoder available for format ");
      paramString.append(paramBarcodeFormat);
      throw new IllegalArgumentException(paramString.toString());
    case 11: 
      localObject = new AztecWriter();
      break;
    case 10: 
      localObject = new DataMatrixWriter();
      break;
    case 9: 
      localObject = new CodaBarWriter();
      break;
    case 8: 
      localObject = new PDF417Writer();
      break;
    case 7: 
      localObject = new ITFWriter();
      break;
    case 6: 
      localObject = new Code128Writer();
      break;
    case 5: 
      localObject = new Code39Writer();
      break;
    case 4: 
      localObject = new QRCodeWriter();
      break;
    case 3: 
      localObject = new UPCAWriter();
      break;
    case 2: 
      localObject = new EAN13Writer();
      break;
    case 1: 
      localObject = new EAN8Writer();
    }
    return ((Writer)localObject).encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, paramMap);
  }
}
