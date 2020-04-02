package com.google.zxing.oned;

import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;

final class UPCEANExtensionSupport
{
  private static final int[] EXTENSION_START_PATTERN = { 1, 1, 2 };
  private final UPCEANExtension5Support fiveSupport = new UPCEANExtension5Support();
  private final UPCEANExtension2Support twoSupport = new UPCEANExtension2Support();
  
  UPCEANExtensionSupport() {}
  
  Result decodeRow(int paramInt1, BitArray paramBitArray, int paramInt2)
    throws NotFoundException
  {
    int[] arrayOfInt = UPCEANReader.findGuardPattern(paramBitArray, paramInt2, false, EXTENSION_START_PATTERN);
    try
    {
      Result localResult = this.fiveSupport.decodeRow(paramInt1, paramBitArray, arrayOfInt);
      return localResult;
    }
    catch (ReaderException localReaderException) {}
    return this.twoSupport.decodeRow(paramInt1, paramBitArray, arrayOfInt);
  }
}
