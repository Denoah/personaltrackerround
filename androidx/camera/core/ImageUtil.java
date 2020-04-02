package androidx.camera.core;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

final class ImageUtil
{
  private static final String TAG = "ImageUtil";
  
  private ImageUtil() {}
  
  public static Rect computeCropRectFromAspectRatio(Size paramSize, Rational paramRational)
  {
    if (!isAspectRatioValid(paramRational))
    {
      Log.w("ImageUtil", "Invalid view ratio.");
      return null;
    }
    int i = paramSize.getWidth();
    int j = paramSize.getHeight();
    float f1 = i;
    float f2 = j;
    float f3 = f1 / f2;
    int k = paramRational.getNumerator();
    int m = paramRational.getDenominator();
    float f4 = paramRational.floatValue();
    int n = 0;
    if (f4 > f3)
    {
      m = Math.round(f1 / k * m);
      k = (j - m) / 2;
      j = m;
    }
    else
    {
      k = Math.round(f2 / m * k);
      n = (i - k) / 2;
      i = k;
      k = 0;
    }
    return new Rect(n, k, i + n, j + k);
  }
  
  public static byte[] cropByteArray(byte[] paramArrayOfByte, Rect paramRect)
    throws ImageUtil.CodecFailedException
  {
    if (paramRect == null) {
      return paramArrayOfByte;
    }
    try
    {
      paramArrayOfByte = BitmapRegionDecoder.newInstance(paramArrayOfByte, 0, paramArrayOfByte.length, false);
      BitmapFactory.Options localOptions = new android/graphics/BitmapFactory$Options;
      localOptions.<init>();
      paramRect = paramArrayOfByte.decodeRegion(paramRect, localOptions);
      paramArrayOfByte.recycle();
      if (paramRect != null)
      {
        paramArrayOfByte = new ByteArrayOutputStream();
        if (paramRect.compress(Bitmap.CompressFormat.JPEG, 100, paramArrayOfByte))
        {
          paramRect.recycle();
          return paramArrayOfByte.toByteArray();
        }
        throw new CodecFailedException("Encode bitmap failed.", ImageUtil.CodecFailedException.FailureType.ENCODE_FAILED);
      }
      throw new CodecFailedException("Decode byte array failed.", ImageUtil.CodecFailedException.FailureType.DECODE_FAILED);
    }
    catch (IOException paramArrayOfByte)
    {
      throw new CodecFailedException("Decode byte array failed.", ImageUtil.CodecFailedException.FailureType.DECODE_FAILED);
    }
    catch (IllegalArgumentException paramArrayOfByte)
    {
      paramRect = new StringBuilder();
      paramRect.append("Decode byte array failed with illegal argument.");
      paramRect.append(paramArrayOfByte);
      throw new CodecFailedException(paramRect.toString(), ImageUtil.CodecFailedException.FailureType.DECODE_FAILED);
    }
  }
  
  public static byte[] imageToJpegByteArray(ImageProxy paramImageProxy)
    throws ImageUtil.CodecFailedException
  {
    if (paramImageProxy.getFormat() == 256)
    {
      paramImageProxy = jpegImageToJpegByteArray(paramImageProxy);
    }
    else if (paramImageProxy.getFormat() == 35)
    {
      paramImageProxy = yuvImageToJpegByteArray(paramImageProxy);
    }
    else
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unrecognized image format: ");
      localStringBuilder.append(paramImageProxy.getFormat());
      Log.w("ImageUtil", localStringBuilder.toString());
      paramImageProxy = null;
    }
    return paramImageProxy;
  }
  
  private static Rational inverseRational(Rational paramRational)
  {
    if (paramRational == null) {
      return paramRational;
    }
    return new Rational(paramRational.getDenominator(), paramRational.getNumerator());
  }
  
  public static boolean isAspectRatioValid(Rational paramRational)
  {
    boolean bool;
    if ((paramRational != null) && (paramRational.floatValue() > 0.0F) && (!paramRational.isNaN())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isAspectRatioValid(Size paramSize, Rational paramRational)
  {
    boolean bool;
    if ((paramRational != null) && (paramRational.floatValue() > 0.0F) && (isCropAspectRatioHasEffect(paramSize, paramRational)) && (!paramRational.isNaN())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static boolean isCropAspectRatioHasEffect(Size paramSize, Rational paramRational)
  {
    int i = paramSize.getWidth();
    int j = paramSize.getHeight();
    int k = paramRational.getNumerator();
    int m = paramRational.getDenominator();
    float f1 = i;
    float f2 = k;
    f1 /= f2;
    float f3 = m;
    boolean bool;
    if ((j == Math.round(f1 * f3)) && (i == Math.round(j / f3 * f2))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static byte[] jpegImageToJpegByteArray(ImageProxy paramImageProxy)
    throws ImageUtil.CodecFailedException
  {
    Object localObject = paramImageProxy.getPlanes()[0].getBuffer();
    byte[] arrayOfByte = new byte[((ByteBuffer)localObject).capacity()];
    ((ByteBuffer)localObject).get(arrayOfByte);
    localObject = arrayOfByte;
    if (shouldCropImage(paramImageProxy)) {
      localObject = cropByteArray(arrayOfByte, paramImageProxy.getCropRect());
    }
    return localObject;
  }
  
  private static byte[] nv21ToJpeg(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Rect paramRect)
    throws ImageUtil.CodecFailedException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    YuvImage localYuvImage = new YuvImage(paramArrayOfByte, 17, paramInt1, paramInt2, null);
    paramArrayOfByte = paramRect;
    if (paramRect == null) {
      paramArrayOfByte = new Rect(0, 0, paramInt1, paramInt2);
    }
    if (localYuvImage.compressToJpeg(paramArrayOfByte, 100, localByteArrayOutputStream)) {
      return localByteArrayOutputStream.toByteArray();
    }
    throw new CodecFailedException("YuvImage failed to encode jpeg.", ImageUtil.CodecFailedException.FailureType.ENCODE_FAILED);
  }
  
  public static Rational rotate(Rational paramRational, int paramInt)
  {
    if ((paramInt != 90) && (paramInt != 270)) {
      return paramRational;
    }
    return inverseRational(paramRational);
  }
  
  private static boolean shouldCropImage(ImageProxy paramImageProxy)
  {
    Size localSize = new Size(paramImageProxy.getWidth(), paramImageProxy.getHeight());
    return new Size(paramImageProxy.getCropRect().width(), paramImageProxy.getCropRect().height()).equals(localSize) ^ true;
  }
  
  private static byte[] yuvImageToJpegByteArray(ImageProxy paramImageProxy)
    throws ImageUtil.CodecFailedException
  {
    byte[] arrayOfByte = yuv_420_888toNv21(paramImageProxy);
    int i = paramImageProxy.getWidth();
    int j = paramImageProxy.getHeight();
    if (shouldCropImage(paramImageProxy)) {
      paramImageProxy = paramImageProxy.getCropRect();
    } else {
      paramImageProxy = null;
    }
    return nv21ToJpeg(arrayOfByte, i, j, paramImageProxy);
  }
  
  private static byte[] yuv_420_888toNv21(ImageProxy paramImageProxy)
  {
    ImageProxy.PlaneProxy localPlaneProxy1 = paramImageProxy.getPlanes()[0];
    ImageProxy.PlaneProxy localPlaneProxy2 = paramImageProxy.getPlanes()[1];
    Object localObject = paramImageProxy.getPlanes()[2];
    ByteBuffer localByteBuffer1 = localPlaneProxy1.getBuffer();
    ByteBuffer localByteBuffer2 = localPlaneProxy2.getBuffer();
    ByteBuffer localByteBuffer3 = ((ImageProxy.PlaneProxy)localObject).getBuffer();
    localByteBuffer1.rewind();
    localByteBuffer2.rewind();
    localByteBuffer3.rewind();
    int i = localByteBuffer1.remaining();
    byte[] arrayOfByte = new byte[paramImageProxy.getWidth() * paramImageProxy.getHeight() / 2 + i];
    int j = 0;
    int k = j;
    while (j < paramImageProxy.getHeight())
    {
      localByteBuffer1.get(arrayOfByte, k, paramImageProxy.getWidth());
      k += paramImageProxy.getWidth();
      localByteBuffer1.position(Math.min(i, localByteBuffer1.position() - paramImageProxy.getWidth() + localPlaneProxy1.getRowStride()));
      j++;
    }
    int m = paramImageProxy.getHeight() / 2;
    int n = paramImageProxy.getWidth() / 2;
    int i1 = ((ImageProxy.PlaneProxy)localObject).getRowStride();
    int i2 = localPlaneProxy2.getRowStride();
    int i3 = ((ImageProxy.PlaneProxy)localObject).getPixelStride();
    int i4 = localPlaneProxy2.getPixelStride();
    localObject = new byte[i1];
    paramImageProxy = new byte[i2];
    j = 0;
    int i5 = k;
    while (j < m)
    {
      localByteBuffer3.get((byte[])localObject, 0, Math.min(i1, localByteBuffer3.remaining()));
      localByteBuffer2.get(paramImageProxy, 0, Math.min(i2, localByteBuffer2.remaining()));
      int i6 = 0;
      k = i6;
      i = k;
      while (i6 < n)
      {
        int i7 = i5 + 1;
        arrayOfByte[i5] = ((byte)localObject[k]);
        i5 = i7 + 1;
        arrayOfByte[i7] = ((byte)paramImageProxy[i]);
        k += i3;
        i += i4;
        i6++;
      }
      j++;
    }
    return arrayOfByte;
  }
  
  public static final class CodecFailedException
    extends Exception
  {
    private FailureType mFailureType;
    
    CodecFailedException(String paramString)
    {
      super();
      this.mFailureType = FailureType.UNKNOWN;
    }
    
    CodecFailedException(String paramString, FailureType paramFailureType)
    {
      super();
      this.mFailureType = paramFailureType;
    }
    
    public FailureType getFailureType()
    {
      return this.mFailureType;
    }
    
    static enum FailureType
    {
      static
      {
        DECODE_FAILED = new FailureType("DECODE_FAILED", 1);
        FailureType localFailureType = new FailureType("UNKNOWN", 2);
        UNKNOWN = localFailureType;
        $VALUES = new FailureType[] { ENCODE_FAILED, DECODE_FAILED, localFailureType };
      }
      
      private FailureType() {}
    }
  }
}
