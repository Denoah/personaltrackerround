package com.google.zxing;

public final class PlanarYUVLuminanceSource
  extends LuminanceSource
{
  private static final int THUMBNAIL_SCALE_FACTOR = 2;
  private final int dataHeight;
  private final int dataWidth;
  private final int left;
  private final int top;
  private final byte[] yuvData;
  
  public PlanarYUVLuminanceSource(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean)
  {
    super(paramInt5, paramInt6);
    if ((paramInt3 + paramInt5 <= paramInt1) && (paramInt4 + paramInt6 <= paramInt2))
    {
      this.yuvData = paramArrayOfByte;
      this.dataWidth = paramInt1;
      this.dataHeight = paramInt2;
      this.left = paramInt3;
      this.top = paramInt4;
      if (paramBoolean) {
        reverseHorizontal(paramInt5, paramInt6);
      }
      return;
    }
    throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
  }
  
  private void reverseHorizontal(int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = this.yuvData;
    int i = this.top * this.dataWidth + this.left;
    int j = 0;
    while (j < paramInt2)
    {
      int k = paramInt1 / 2;
      int m = i + paramInt1 - 1;
      int n = i;
      while (n < k + i)
      {
        int i1 = arrayOfByte[n];
        arrayOfByte[n] = ((byte)arrayOfByte[m]);
        arrayOfByte[m] = ((byte)i1);
        n++;
        m--;
      }
      j++;
      i += this.dataWidth;
    }
  }
  
  public LuminanceSource crop(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return new PlanarYUVLuminanceSource(this.yuvData, this.dataWidth, this.dataHeight, this.left + paramInt1, this.top + paramInt2, paramInt3, paramInt4, false);
  }
  
  public byte[] getMatrix()
  {
    int i = getWidth();
    int j = getHeight();
    if ((i == this.dataWidth) && (j == this.dataHeight)) {
      return this.yuvData;
    }
    int k = i * j;
    byte[] arrayOfByte1 = new byte[k];
    int m = this.top;
    int n = this.dataWidth;
    int i1 = m * n + this.left;
    m = 0;
    if (i == n)
    {
      System.arraycopy(this.yuvData, i1, arrayOfByte1, 0, k);
      return arrayOfByte1;
    }
    byte[] arrayOfByte2 = this.yuvData;
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
      System.arraycopy(this.yuvData, (paramInt + j) * k + m, arrayOfByte, 0, i);
      return arrayOfByte;
    }
    paramArrayOfByte = new StringBuilder();
    paramArrayOfByte.append("Requested row is outside the image: ");
    paramArrayOfByte.append(paramInt);
    throw new IllegalArgumentException(paramArrayOfByte.toString());
  }
  
  public int getThumbnailHeight()
  {
    return getHeight() / 2;
  }
  
  public int getThumbnailWidth()
  {
    return getWidth() / 2;
  }
  
  public boolean isCropSupported()
  {
    return true;
  }
  
  public int[] renderThumbnail()
  {
    int i = getWidth() / 2;
    int j = getHeight() / 2;
    int[] arrayOfInt = new int[i * j];
    byte[] arrayOfByte = this.yuvData;
    int k = this.top * this.dataWidth + this.left;
    for (int m = 0; m < j; m++)
    {
      for (int n = 0; n < i; n++) {
        arrayOfInt[(m * i + n)] = ((arrayOfByte[(n * 2 + k)] & 0xFF) * 65793 | 0xFF000000);
      }
      k += this.dataWidth * 2;
    }
    return arrayOfInt;
  }
}
