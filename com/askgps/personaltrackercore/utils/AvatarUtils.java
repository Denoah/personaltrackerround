package com.askgps.personaltrackercore.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import com.askgps.personaltrackercore.LogKt;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;
import org.koin.core.KoinComponent.DefaultImpls;

@Metadata(bv={1, 0, 3}, d1={"\000J\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\007\n\002\020\016\n\000\n\002\020\022\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\020\002\n\002\b\003\n\002\020\007\n\002\b\004\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\016\020\017\032\0020\0202\006\020\021\032\0020\022J\026\020\023\032\0020\0242\006\020\025\032\0020\0162\006\020\026\032\0020\020J\016\020\027\032\0020\0222\006\020\030\032\0020\031J\022\020\032\032\0020\0332\b\020\034\032\004\030\0010\016H\002J\030\020\035\032\0020\0332\006\020\034\032\0020\0162\006\020\036\032\0020\037H\002J\030\020 \032\0020\0332\006\020\021\032\0020\0222\006\020\034\032\0020\016H\002J\020\020!\032\0020\0222\006\020\021\032\0020\022H\002J\016\020\"\032\0020\0202\006\020\030\032\0020\016R\016\020\002\032\0020\003X?\004?\006\002\n\000R\024\020\005\032\0020\006X?D?\006\b\n\000\032\004\b\007\020\bR\024\020\t\032\0020\006X?D?\006\b\n\000\032\004\b\n\020\bR\024\020\013\032\0020\006X?D?\006\b\n\000\032\004\b\f\020\bR\016\020\r\032\0020\016X?D?\006\002\n\000?\006#"}, d2={"Lcom/askgps/personaltrackercore/utils/AvatarUtils;", "Lorg/koin/core/KoinComponent;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "maxHeight", "", "getMaxHeight", "()I", "maxWidth", "getMaxWidth", "quality", "getQuality", "tag", "", "compressBitmapToByteArray", "", "bitmap", "Landroid/graphics/Bitmap;", "convertBitmapToFile", "Ljava/io/File;", "fileName", "bitMapData", "loadBitmap", "uri", "Landroid/net/Uri;", "normalizeImage", "", "path", "rotateImage", "angle", "", "saveBitmap", "scaleBitmap", "uploadBitmap", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class AvatarUtils
  implements KoinComponent
{
  private final Context context;
  private final int maxHeight;
  private final int maxWidth;
  private final int quality;
  private final String tag;
  
  public AvatarUtils(Context paramContext)
  {
    this.context = paramContext;
    this.tag = "AvatarUtils";
    this.maxWidth = 600;
    this.maxHeight = 800;
    this.quality = 80;
  }
  
  private final void normalizeImage(String paramString)
  {
    if (paramString == null) {
      return;
    }
    try
    {
      Object localObject = new android/media/ExifInterface;
      ((ExifInterface)localObject).<init>(paramString);
      int i = ((ExifInterface)localObject).getAttributeInt("Orientation", 0);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Image orientation: ");
      ((StringBuilder)localObject).append(i);
      LogKt.toFile$default(((StringBuilder)localObject).toString(), this.tag, null, null, 6, null);
      if (i != 3)
      {
        if (i != 6)
        {
          if (i == 8) {
            rotateImage(paramString, 270.0F);
          }
        }
        else {
          rotateImage(paramString, 90.0F);
        }
      }
      else {
        rotateImage(paramString, 180.0F);
      }
      return;
    }
    catch (IOException paramString)
    {
      for (;;) {}
    }
  }
  
  private final void rotateImage(String paramString, float paramFloat)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Rotating ");
    ((StringBuilder)localObject).append(paramFloat);
    LogKt.toFile$default(((StringBuilder)localObject).toString(), this.tag, null, null, 6, null);
    localObject = BitmapFactory.decodeFile(paramString);
    Matrix localMatrix = new Matrix();
    localMatrix.postRotate(paramFloat);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "source");
    localObject = Bitmap.createBitmap((Bitmap)localObject, 0, 0, ((Bitmap)localObject).getWidth(), ((Bitmap)localObject).getHeight(), localMatrix, true);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "output");
    saveBitmap((Bitmap)localObject, paramString);
  }
  
  /* Error */
  private final void saveBitmap(Bitmap paramBitmap, String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: checkcast 160	java/io/FileOutputStream
    //   4: astore_3
    //   5: aload_3
    //   6: astore 4
    //   8: new 160	java/io/FileOutputStream
    //   11: astore 5
    //   13: aload_3
    //   14: astore 4
    //   16: aload 5
    //   18: aload_2
    //   19: invokespecial 161	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   22: aload_1
    //   23: getstatic 167	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   26: bipush 100
    //   28: aload 5
    //   30: checkcast 169	java/io/OutputStream
    //   33: invokevirtual 173	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   36: pop
    //   37: aload 5
    //   39: invokevirtual 176	java/io/FileOutputStream:close	()V
    //   42: goto +51 -> 93
    //   45: astore_1
    //   46: aload_1
    //   47: invokevirtual 179	java/io/IOException:printStackTrace	()V
    //   50: goto +43 -> 93
    //   53: astore_1
    //   54: aload 5
    //   56: astore 4
    //   58: goto +36 -> 94
    //   61: astore_2
    //   62: aload 5
    //   64: astore_1
    //   65: goto +10 -> 75
    //   68: astore_1
    //   69: goto +25 -> 94
    //   72: astore_2
    //   73: aload_3
    //   74: astore_1
    //   75: aload_1
    //   76: astore 4
    //   78: aload_2
    //   79: invokevirtual 180	java/lang/Exception:printStackTrace	()V
    //   82: aload_1
    //   83: ifnonnull +6 -> 89
    //   86: invokestatic 183	kotlin/jvm/internal/Intrinsics:throwNpe	()V
    //   89: aload_1
    //   90: invokevirtual 176	java/io/FileOutputStream:close	()V
    //   93: return
    //   94: aload 4
    //   96: ifnonnull +6 -> 102
    //   99: invokestatic 183	kotlin/jvm/internal/Intrinsics:throwNpe	()V
    //   102: aload 4
    //   104: invokevirtual 176	java/io/FileOutputStream:close	()V
    //   107: goto +8 -> 115
    //   110: astore_2
    //   111: aload_2
    //   112: invokevirtual 179	java/io/IOException:printStackTrace	()V
    //   115: aload_1
    //   116: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	117	0	this	AvatarUtils
    //   0	117	1	paramBitmap	Bitmap
    //   0	117	2	paramString	String
    //   4	70	3	localFileOutputStream1	FileOutputStream
    //   6	97	4	localObject	Object
    //   11	52	5	localFileOutputStream2	FileOutputStream
    // Exception table:
    //   from	to	target	type
    //   37	42	45	java/io/IOException
    //   86	89	45	java/io/IOException
    //   89	93	45	java/io/IOException
    //   22	37	53	finally
    //   22	37	61	java/lang/Exception
    //   8	13	68	finally
    //   16	22	68	finally
    //   78	82	68	finally
    //   8	13	72	java/lang/Exception
    //   16	22	72	java/lang/Exception
    //   99	102	110	java/io/IOException
    //   102	107	110	java/io/IOException
  }
  
  private final Bitmap scaleBitmap(Bitmap paramBitmap)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("scaling bitmap with size [");
    ((StringBuilder)localObject).append(i);
    ((StringBuilder)localObject).append('x');
    ((StringBuilder)localObject).append(j);
    ((StringBuilder)localObject).append(']');
    LogKt.toFile$default(((StringBuilder)localObject).toString(), this.tag, null, null, 6, null);
    float f;
    if (i > j)
    {
      f = i;
      i = this.maxWidth;
      f /= i;
      localObject = TuplesKt.to(Integer.valueOf(i), Integer.valueOf((int)(j / f)));
    }
    else if (j > i)
    {
      f = j / this.maxHeight;
      localObject = TuplesKt.to(Integer.valueOf((int)(i / f)), Integer.valueOf(this.maxHeight));
    }
    else
    {
      localObject = TuplesKt.to(Integer.valueOf(this.maxWidth), Integer.valueOf(this.maxHeight));
    }
    i = ((Number)((Pair)localObject).component1()).intValue();
    j = ((Number)((Pair)localObject).component2()).intValue();
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("target size [");
    ((StringBuilder)localObject).append(i);
    ((StringBuilder)localObject).append('x');
    ((StringBuilder)localObject).append(j);
    ((StringBuilder)localObject).append(']');
    LogKt.toFile$default(((StringBuilder)localObject).toString(), this.tag, null, null, 6, null);
    paramBitmap = Bitmap.createScaledBitmap(paramBitmap, i, j, false);
    Intrinsics.checkExpressionValueIsNotNull(paramBitmap, "Bitmap.createScaledBitma…dth, targetHeight, false)");
    return paramBitmap;
  }
  
  public final byte[] compressBitmapToByteArray(Bitmap paramBitmap)
  {
    Intrinsics.checkParameterIsNotNull(paramBitmap, "bitmap");
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, this.quality, (OutputStream)localByteArrayOutputStream);
    paramBitmap = localByteArrayOutputStream.toByteArray();
    Intrinsics.checkExpressionValueIsNotNull(paramBitmap, "outputStream.toByteArray()");
    return paramBitmap;
  }
  
  public final File convertBitmapToFile(String paramString, byte[] paramArrayOfByte)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "fileName");
    Intrinsics.checkParameterIsNotNull(paramArrayOfByte, "bitMapData");
    File localFile = new File(paramString);
    if (localFile.exists()) {
      localFile.delete();
    }
    localFile.createNewFile();
    paramString = (FileOutputStream)null;
    try
    {
      FileOutputStream localFileOutputStream = new java/io/FileOutputStream;
      localFileOutputStream.<init>(localFile);
      paramString = localFileOutputStream;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
    if (paramString != null) {
      try
      {
        paramString.write(paramArrayOfByte);
      }
      catch (IOException paramString)
      {
        break label102;
      }
    }
    if (paramString != null) {
      paramString.flush();
    }
    if (paramString != null)
    {
      paramString.close();
      return localFile;
      label102:
      paramString.printStackTrace();
    }
    return localFile;
  }
  
  public Koin getKoin()
  {
    return KoinComponent.DefaultImpls.getKoin(this);
  }
  
  public final int getMaxHeight()
  {
    return this.maxHeight;
  }
  
  public final int getMaxWidth()
  {
    return this.maxWidth;
  }
  
  public final int getQuality()
  {
    return this.quality;
  }
  
  public final Bitmap loadBitmap(Uri paramUri)
  {
    Intrinsics.checkParameterIsNotNull(paramUri, "uri");
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
    paramUri = BitmapFactory.decodeFile(paramUri.getEncodedPath(), localOptions);
    Intrinsics.checkExpressionValueIsNotNull(paramUri, "bitmap");
    return paramUri;
  }
  
  public final byte[] uploadBitmap(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "uri");
    normalizeImage(paramString);
    paramString = Uri.parse(paramString);
    Intrinsics.checkExpressionValueIsNotNull(paramString, "Uri.parse(uri)");
    byte[] arrayOfByte = compressBitmapToByteArray(scaleBitmap(loadBitmap(paramString)));
    paramString = new StringBuilder();
    paramString.append("Upload image size: ");
    paramString.append(arrayOfByte.length);
    LogKt.toFile$default(paramString.toString(), this.tag, null, null, 6, null);
    return arrayOfByte;
  }
}
