package androidx.camera.core;

import android.util.Size;
import java.nio.ByteBuffer;

final class ImageProxyDownsampler
{
  private ImageProxyDownsampler() {}
  
  private static ImageProxy.PlaneProxy createPlaneProxy(final int paramInt1, final int paramInt2, byte[] paramArrayOfByte)
  {
    new ImageProxy.PlaneProxy()
    {
      final ByteBuffer mBuffer = ByteBuffer.wrap(this.val$data);
      
      public ByteBuffer getBuffer()
      {
        return this.mBuffer;
      }
      
      public int getPixelStride()
      {
        return paramInt2;
      }
      
      public int getRowStride()
      {
        return paramInt1;
      }
    };
  }
  
  static ForwardingImageProxy downsample(ImageProxy paramImageProxy, int paramInt1, int paramInt2, DownsamplingMethod paramDownsamplingMethod)
  {
    if (paramImageProxy.getFormat() == 35)
    {
      if ((paramImageProxy.getWidth() >= paramInt1) && (paramImageProxy.getHeight() >= paramInt2))
      {
        if ((paramImageProxy.getWidth() == paramInt1) && (paramImageProxy.getHeight() == paramInt2)) {
          return new ForwardingImageProxyImpl(paramImageProxy, paramImageProxy.getPlanes(), paramInt1, paramInt2);
        }
        int[] arrayOfInt1 = new int[3];
        int i = paramImageProxy.getWidth();
        int j = 0;
        arrayOfInt1[0] = i;
        arrayOfInt1[1] = (paramImageProxy.getWidth() / 2);
        arrayOfInt1[2] = (paramImageProxy.getWidth() / 2);
        int[] arrayOfInt2 = new int[3];
        arrayOfInt2[0] = paramImageProxy.getHeight();
        arrayOfInt2[1] = (paramImageProxy.getHeight() / 2);
        arrayOfInt2[2] = (paramImageProxy.getHeight() / 2);
        int[] arrayOfInt3 = new int[3];
        arrayOfInt3[0] = paramInt1;
        i = paramInt1 / 2;
        arrayOfInt3[1] = i;
        arrayOfInt3[2] = i;
        int[] arrayOfInt4 = new int[3];
        arrayOfInt4[0] = paramInt2;
        i = paramInt2 / 2;
        arrayOfInt4[1] = i;
        arrayOfInt4[2] = i;
        ImageProxy.PlaneProxy[] arrayOfPlaneProxy = new ImageProxy.PlaneProxy[3];
        while (j < 3)
        {
          ImageProxy.PlaneProxy localPlaneProxy = paramImageProxy.getPlanes()[j];
          ByteBuffer localByteBuffer = localPlaneProxy.getBuffer();
          byte[] arrayOfByte = new byte[arrayOfInt3[j] * arrayOfInt4[j]];
          i = 2.$SwitchMap$androidx$camera$core$ImageProxyDownsampler$DownsamplingMethod[paramDownsamplingMethod.ordinal()];
          if (i != 1)
          {
            if (i == 2) {
              resizeAveraging(localByteBuffer, arrayOfInt1[j], localPlaneProxy.getPixelStride(), localPlaneProxy.getRowStride(), arrayOfInt2[j], arrayOfByte, arrayOfInt3[j], arrayOfInt4[j]);
            }
          }
          else {
            resizeNearestNeighbor(localByteBuffer, arrayOfInt1[j], localPlaneProxy.getPixelStride(), localPlaneProxy.getRowStride(), arrayOfInt2[j], arrayOfByte, arrayOfInt3[j], arrayOfInt4[j]);
          }
          arrayOfPlaneProxy[j] = createPlaneProxy(arrayOfInt3[j], 1, arrayOfByte);
          j++;
        }
        return new ForwardingImageProxyImpl(paramImageProxy, arrayOfPlaneProxy, paramInt1, paramInt2);
      }
      paramDownsamplingMethod = new StringBuilder();
      paramDownsamplingMethod.append("Downsampled dimension ");
      paramDownsamplingMethod.append(new Size(paramInt1, paramInt2));
      paramDownsamplingMethod.append(" is not <= original dimension ");
      paramDownsamplingMethod.append(new Size(paramImageProxy.getWidth(), paramImageProxy.getHeight()));
      paramDownsamplingMethod.append(".");
      throw new IllegalArgumentException(paramDownsamplingMethod.toString());
    }
    throw new UnsupportedOperationException("Only YUV_420_888 format is currently supported.");
  }
  
  private static void resizeAveraging(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte, int paramInt5, int paramInt6)
  {
    float f1 = paramInt1 / paramInt5;
    float f2 = paramInt4 / paramInt6;
    byte[] arrayOfByte1 = new byte[paramInt3];
    byte[] arrayOfByte2 = new byte[paramInt3];
    int[] arrayOfInt = new int[paramInt5];
    for (paramInt1 = 0; paramInt1 < paramInt5; paramInt1++) {
      arrayOfInt[paramInt1] = ((int)(paramInt1 * f1) * paramInt2);
    }
    try
    {
      paramByteBuffer.rewind();
      for (paramInt1 = 0; paramInt1 < paramInt6; paramInt1++)
      {
        int i = (int)(paramInt1 * f2);
        int j = paramInt4 - 1;
        int k = Math.min(i, j);
        j = Math.min(i + 1, j);
        paramByteBuffer.position(k * paramInt3);
        paramByteBuffer.get(arrayOfByte1, 0, Math.min(paramInt3, paramByteBuffer.remaining()));
        paramByteBuffer.position(j * paramInt3);
        paramByteBuffer.get(arrayOfByte2, 0, Math.min(paramInt3, paramByteBuffer.remaining()));
        for (k = 0; k < paramInt5; k++) {
          paramArrayOfByte[(paramInt1 * paramInt5 + k)] = ((byte)(byte)(((arrayOfByte1[arrayOfInt[k]] & 0xFF) + (arrayOfByte1[(arrayOfInt[k] + paramInt2)] & 0xFF) + (arrayOfByte2[arrayOfInt[k]] & 0xFF) + (arrayOfByte2[(arrayOfInt[k] + paramInt2)] & 0xFF)) / 4 & 0xFF));
        }
      }
      return;
    }
    finally {}
  }
  
  private static void resizeNearestNeighbor(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte, int paramInt5, int paramInt6)
  {
    float f1 = paramInt1 / paramInt5;
    float f2 = paramInt4 / paramInt6;
    byte[] arrayOfByte = new byte[paramInt3];
    int[] arrayOfInt = new int[paramInt5];
    for (paramInt1 = 0; paramInt1 < paramInt5; paramInt1++) {
      arrayOfInt[paramInt1] = ((int)(paramInt1 * f1) * paramInt2);
    }
    try
    {
      paramByteBuffer.rewind();
      for (paramInt1 = 0; paramInt1 < paramInt6; paramInt1++)
      {
        paramByteBuffer.position(Math.min((int)(paramInt1 * f2), paramInt4 - 1) * paramInt3);
        paramByteBuffer.get(arrayOfByte, 0, Math.min(paramInt3, paramByteBuffer.remaining()));
        for (paramInt2 = 0; paramInt2 < paramInt5; paramInt2++) {
          paramArrayOfByte[(paramInt1 * paramInt5 + paramInt2)] = ((byte)arrayOfByte[arrayOfInt[paramInt2]]);
        }
      }
      return;
    }
    finally {}
  }
  
  static enum DownsamplingMethod
  {
    static
    {
      DownsamplingMethod localDownsamplingMethod = new DownsamplingMethod("AVERAGING", 1);
      AVERAGING = localDownsamplingMethod;
      $VALUES = new DownsamplingMethod[] { NEAREST_NEIGHBOR, localDownsamplingMethod };
    }
    
    private DownsamplingMethod() {}
  }
  
  private static final class ForwardingImageProxyImpl
    extends ForwardingImageProxy
  {
    private final int mDownsampledHeight;
    private final ImageProxy.PlaneProxy[] mDownsampledPlanes;
    private final int mDownsampledWidth;
    
    ForwardingImageProxyImpl(ImageProxy paramImageProxy, ImageProxy.PlaneProxy[] paramArrayOfPlaneProxy, int paramInt1, int paramInt2)
    {
      super();
      this.mDownsampledPlanes = paramArrayOfPlaneProxy;
      this.mDownsampledWidth = paramInt1;
      this.mDownsampledHeight = paramInt2;
    }
    
    public int getHeight()
    {
      try
      {
        int i = this.mDownsampledHeight;
        return i;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public ImageProxy.PlaneProxy[] getPlanes()
    {
      try
      {
        ImageProxy.PlaneProxy[] arrayOfPlaneProxy = this.mDownsampledPlanes;
        return arrayOfPlaneProxy;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public int getWidth()
    {
      try
      {
        int i = this.mDownsampledWidth;
        return i;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
  }
}
