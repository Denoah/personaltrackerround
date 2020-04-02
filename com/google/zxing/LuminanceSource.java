package com.google.zxing;

public abstract class LuminanceSource
{
  private final int height;
  private final int width;
  
  protected LuminanceSource(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
  }
  
  public LuminanceSource crop(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    throw new UnsupportedOperationException("This luminance source does not support cropping.");
  }
  
  public final int getHeight()
  {
    return this.height;
  }
  
  public abstract byte[] getMatrix();
  
  public abstract byte[] getRow(int paramInt, byte[] paramArrayOfByte);
  
  public final int getWidth()
  {
    return this.width;
  }
  
  public LuminanceSource invert()
  {
    return new InvertedLuminanceSource(this);
  }
  
  public boolean isCropSupported()
  {
    return false;
  }
  
  public boolean isRotateSupported()
  {
    return false;
  }
  
  public LuminanceSource rotateCounterClockwise()
  {
    throw new UnsupportedOperationException("This luminance source does not support rotation by 90 degrees.");
  }
  
  public LuminanceSource rotateCounterClockwise45()
  {
    throw new UnsupportedOperationException("This luminance source does not support rotation by 45 degrees.");
  }
  
  public final String toString()
  {
    int i = this.width;
    byte[] arrayOfByte = new byte[i];
    StringBuilder localStringBuilder = new StringBuilder(this.height * (i + 1));
    for (i = 0; i < this.height; i++)
    {
      arrayOfByte = getRow(i, arrayOfByte);
      for (int j = 0; j < this.width; j++)
      {
        int k = arrayOfByte[j] & 0xFF;
        int m;
        if (k < 64)
        {
          k = 35;
          m = k;
        }
        else if (k < 128)
        {
          k = 43;
          m = k;
        }
        else if (k < 192)
        {
          k = 46;
          m = k;
        }
        else
        {
          k = 32;
          m = k;
        }
        localStringBuilder.append(m);
      }
      localStringBuilder.append('\n');
    }
    return localStringBuilder.toString();
  }
}
