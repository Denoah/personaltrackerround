package com.google.zxing;

public final class RGBLuminanceSource
  extends LuminanceSource
{
  private final int dataHeight;
  private final int dataWidth;
  private final int left;
  private final byte[] luminances;
  private final int top;
  
  public RGBLuminanceSource(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    super(paramInt1, paramInt2);
    this.dataWidth = paramInt1;
    this.dataHeight = paramInt2;
    this.left = 0;
    this.top = 0;
    this.luminances = new byte[paramInt1 * paramInt2];
    for (int i = 0; i < paramInt2; i++) {
      for (int j = 0; j < paramInt1; j++)
      {
        int k = i * paramInt1 + j;
        int m = paramArrayOfInt[k];
        int n = m >> 16 & 0xFF;
        int i1 = m >> 8 & 0xFF;
        m &= 0xFF;
        if ((n == i1) && (i1 == m)) {
          this.luminances[k] = ((byte)(byte)n);
        } else {
          this.luminances[k] = ((byte)(byte)((n + i1 * 2 + m) / 4));
        }
      }
    }
  }
  
  private RGBLuminanceSource(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    super(paramInt5, paramInt6);
    if ((paramInt5 + paramInt3 <= paramInt1) && (paramInt6 + paramInt4 <= paramInt2))
    {
      this.luminances = paramArrayOfByte;
      this.dataWidth = paramInt1;
      this.dataHeight = paramInt2;
      this.left = paramInt3;
      this.top = paramInt4;
      return;
    }
    throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
  }
  
  public LuminanceSource crop(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return new RGBLuminanceSource(this.luminances, this.dataWidth, this.dataHeight, this.left + paramInt1, this.top + paramInt2, paramInt3, paramInt4);
  }
  
  public byte[] getMatrix()
  {
    int i = getWidth();
    int j = getHeight();
    if ((i == this.dataWidth) && (j == this.dataHeight)) {
      return this.luminances;
    }
    int k = i * j;
    byte[] arrayOfByte1 = new byte[k];
    int m = this.top;
    int n = this.dataWidth;
    int i1 = m * n + this.left;
    m = 0;
    if (i == n)
    {
      System.arraycopy(this.luminances, i1, arrayOfByte1, 0, k);
      return arrayOfByte1;
    }
    byte[] arrayOfByte2 = this.luminances;
    while (m < j)
    {
      System.arraycopy(arrayOfByte2, i1, arrayOfByte1, m * i, i);
      i1 += this.dataWidth;
      m++;
    }
    return arrayOfByte1;
  }
  
  public byte[] getRow(int paramInt, byte[] paramArrayOfByte)
  {
    if ((paramInt >= 0) && (paramInt < getHeight()))
    {
      int i = getWidth();
      byte[] arrayOfByte;
      if (paramArrayOfByte != null)
      {
        arrayOfByte = paramArrayOfByte;
        if (paramArrayOfByte.length >= i) {}
      }
      else
      {
        arrayOfByte = new byte[i];
      }
      int j = this.top;
      int k = this.dataWidth;
      int m = this.left;
      System.arraycopy(this.luminances, (paramInt + j) * k + m, arrayOfByte, 0, i);
      return arrayOfByte;
    }
    paramArrayOfByte = new StringBuilder();
    paramArrayOfByte.append("Requested row is outside the image: ");
    paramArrayOfByte.append(paramInt);
    throw new IllegalArgumentException(paramArrayOfByte.toString());
  }
  
  public boolean isCropSupported()
  {
    return true;
  }
}
