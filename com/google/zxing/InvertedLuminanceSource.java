package com.google.zxing;

public final class InvertedLuminanceSource
  extends LuminanceSource
{
  private final LuminanceSource delegate;
  
  public InvertedLuminanceSource(LuminanceSource paramLuminanceSource)
  {
    super(paramLuminanceSource.getWidth(), paramLuminanceSource.getHeight());
    this.delegate = paramLuminanceSource;
  }
  
  public LuminanceSource crop(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return new InvertedLuminanceSource(this.delegate.crop(paramInt1, paramInt2, paramInt3, paramInt4));
  }
  
  public byte[] getMatrix()
  {
    byte[] arrayOfByte1 = this.delegate.getMatrix();
    int i = getWidth() * getHeight();
    byte[] arrayOfByte2 = new byte[i];
    for (int j = 0; j < i; j++) {
      arrayOfByte2[j] = ((byte)(byte)(255 - (arrayOfByte1[j] & 0xFF)));
    }
    return arrayOfByte2;
  }
  
  public byte[] getRow(int paramInt, byte[] paramArrayOfByte)
  {
    paramArrayOfByte = this.delegate.getRow(paramInt, paramArrayOfByte);
    int i = getWidth();
    for (paramInt = 0; paramInt < i; paramInt++) {
      paramArrayOfByte[paramInt] = ((byte)(byte)(255 - (paramArrayOfByte[paramInt] & 0xFF)));
    }
    return paramArrayOfByte;
  }
  
  public LuminanceSource invert()
  {
    return this.delegate;
  }
  
  public boolean isCropSupported()
  {
    return this.delegate.isCropSupported();
  }
  
  public boolean isRotateSupported()
  {
    return this.delegate.isRotateSupported();
  }
  
  public LuminanceSource rotateCounterClockwise()
  {
    return new InvertedLuminanceSource(this.delegate.rotateCounterClockwise());
  }
  
  public LuminanceSource rotateCounterClockwise45()
  {
    return new InvertedLuminanceSource(this.delegate.rotateCounterClockwise45());
  }
}
