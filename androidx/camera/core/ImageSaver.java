package androidx.camera.core;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build.VERSION;
import java.util.concurrent.Executor;

final class ImageSaver
  implements Runnable
{
  private static final int COPY_BUFFER_SIZE = 1024;
  private static final int NOT_PENDING = 0;
  private static final int PENDING = 1;
  private static final String TAG = "ImageSaver";
  private static final String TEMP_FILE_PREFIX = "CameraX";
  private static final String TEMP_FILE_SUFFIX = ".tmp";
  final OnImageSavedCallback mCallback;
  private final Executor mExecutor;
  private final ImageProxy mImage;
  private final int mOrientation;
  private final ImageCapture.OutputFileOptions mOutputFileOptions;
  
  ImageSaver(ImageProxy paramImageProxy, ImageCapture.OutputFileOptions paramOutputFileOptions, int paramInt, Executor paramExecutor, OnImageSavedCallback paramOnImageSavedCallback)
  {
    this.mImage = paramImageProxy;
    this.mOutputFileOptions = paramOutputFileOptions;
    this.mOrientation = paramInt;
    this.mCallback = paramOnImageSavedCallback;
    this.mExecutor = paramExecutor;
  }
  
  /* Error */
  private void copyTempFileToOutputStream(java.io.File paramFile, java.io.OutputStream paramOutputStream)
    throws java.io.IOException
  {
    // Byte code:
    //   0: new 62	java/io/FileInputStream
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 65	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   8: astore_1
    //   9: sipush 1024
    //   12: newarray byte
    //   14: astore_3
    //   15: aload_1
    //   16: aload_3
    //   17: invokevirtual 71	java/io/InputStream:read	([B)I
    //   20: istore 4
    //   22: iload 4
    //   24: ifle +14 -> 38
    //   27: aload_2
    //   28: aload_3
    //   29: iconst_0
    //   30: iload 4
    //   32: invokevirtual 77	java/io/OutputStream:write	([BII)V
    //   35: goto -20 -> 15
    //   38: aload_1
    //   39: invokevirtual 80	java/io/InputStream:close	()V
    //   42: return
    //   43: astore_2
    //   44: aload_2
    //   45: athrow
    //   46: astore_3
    //   47: aload_1
    //   48: invokevirtual 80	java/io/InputStream:close	()V
    //   51: goto +9 -> 60
    //   54: astore_1
    //   55: aload_2
    //   56: aload_1
    //   57: invokevirtual 86	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   60: aload_3
    //   61: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	62	0	this	ImageSaver
    //   0	62	1	paramFile	java.io.File
    //   0	62	2	paramOutputStream	java.io.OutputStream
    //   14	15	3	arrayOfByte	byte[]
    //   46	15	3	localObject	Object
    //   20	11	4	i	int
    // Exception table:
    //   from	to	target	type
    //   9	15	43	finally
    //   15	22	43	finally
    //   27	35	43	finally
    //   44	46	46	finally
    //   47	51	54	finally
  }
  
  /* Error */
  private boolean copyTempFileToUri(java.io.File paramFile, Uri paramUri)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 49	androidx/camera/core/ImageSaver:mOutputFileOptions	Landroidx/camera/core/ImageCapture$OutputFileOptions;
    //   4: invokevirtual 95	androidx/camera/core/ImageCapture$OutputFileOptions:getContentResolver	()Landroid/content/ContentResolver;
    //   7: aload_2
    //   8: invokevirtual 101	android/content/ContentResolver:openOutputStream	(Landroid/net/Uri;)Ljava/io/OutputStream;
    //   11: astore_2
    //   12: aload_2
    //   13: ifnonnull +13 -> 26
    //   16: aload_2
    //   17: ifnull +7 -> 24
    //   20: aload_2
    //   21: invokevirtual 102	java/io/OutputStream:close	()V
    //   24: iconst_0
    //   25: ireturn
    //   26: aload_0
    //   27: aload_1
    //   28: aload_2
    //   29: invokespecial 104	androidx/camera/core/ImageSaver:copyTempFileToOutputStream	(Ljava/io/File;Ljava/io/OutputStream;)V
    //   32: aload_2
    //   33: ifnull +7 -> 40
    //   36: aload_2
    //   37: invokevirtual 102	java/io/OutputStream:close	()V
    //   40: iconst_1
    //   41: ireturn
    //   42: astore_3
    //   43: aload_3
    //   44: athrow
    //   45: astore_1
    //   46: aload_2
    //   47: ifnull +16 -> 63
    //   50: aload_2
    //   51: invokevirtual 102	java/io/OutputStream:close	()V
    //   54: goto +9 -> 63
    //   57: astore_2
    //   58: aload_3
    //   59: aload_2
    //   60: invokevirtual 86	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   63: aload_1
    //   64: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	65	0	this	ImageSaver
    //   0	65	1	paramFile	java.io.File
    //   0	65	2	paramUri	Uri
    //   42	17	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   26	32	42	finally
    //   43	45	45	finally
    //   50	54	57	finally
  }
  
  private boolean isSaveToFile()
  {
    boolean bool;
    if (this.mOutputFileOptions.getFile() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean isSaveToMediaStore()
  {
    boolean bool;
    if ((this.mOutputFileOptions.getSaveCollection() != null) && (this.mOutputFileOptions.getContentResolver() != null) && (this.mOutputFileOptions.getContentValues() != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean isSaveToOutputStream()
  {
    boolean bool;
    if (this.mOutputFileOptions.getOutputStream() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void postError(SaveError paramSaveError, String paramString, Throwable paramThrowable)
  {
    this.mExecutor.execute(new _..Lambda.ImageSaver.eAp_cZyzsEk_LVLazzLE_ezQzwo(this, paramSaveError, paramString, paramThrowable));
  }
  
  private void postSuccess(Uri paramUri)
  {
    this.mExecutor.execute(new _..Lambda.ImageSaver.S9mrYGMPcUwPIjUa0oK9HMzbx_o(this, paramUri));
  }
  
  private void setUriPending(Uri paramUri, int paramInt)
  {
    if (Build.VERSION.SDK_INT >= 29)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("is_pending", Integer.valueOf(paramInt));
      this.mOutputFileOptions.getContentResolver().update(paramUri, localContentValues, null, null);
    }
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 188	androidx/camera/core/ImageSaver:isSaveToFile	()Z
    //   4: ifeq +14 -> 18
    //   7: aload_0
    //   8: getfield 49	androidx/camera/core/ImageSaver:mOutputFileOptions	Landroidx/camera/core/ImageCapture$OutputFileOptions;
    //   11: invokevirtual 110	androidx/camera/core/ImageCapture$OutputFileOptions:getFile	()Ljava/io/File;
    //   14: astore_1
    //   15: goto +11 -> 26
    //   18: ldc 28
    //   20: ldc 31
    //   22: invokestatic 194	java/io/File:createTempFile	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   25: astore_1
    //   26: aconst_null
    //   27: astore_2
    //   28: aconst_null
    //   29: astore_3
    //   30: aconst_null
    //   31: astore 4
    //   33: aconst_null
    //   34: astore 5
    //   36: aload_0
    //   37: getfield 47	androidx/camera/core/ImageSaver:mImage	Landroidx/camera/core/ImageProxy;
    //   40: astore 6
    //   42: aload_3
    //   43: astore 7
    //   45: new 196	java/io/FileOutputStream
    //   48: astore 8
    //   50: aload_3
    //   51: astore 7
    //   53: aload 8
    //   55: aload_1
    //   56: invokespecial 197	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   59: aload 8
    //   61: aload_0
    //   62: getfield 47	androidx/camera/core/ImageSaver:mImage	Landroidx/camera/core/ImageProxy;
    //   65: invokestatic 203	androidx/camera/core/ImageUtil:imageToJpegByteArray	(Landroidx/camera/core/ImageProxy;)[B
    //   68: invokevirtual 206	java/io/FileOutputStream:write	([B)V
    //   71: aload_1
    //   72: invokestatic 212	androidx/camera/core/impl/utils/Exif:createFromFile	(Ljava/io/File;)Landroidx/camera/core/impl/utils/Exif;
    //   75: astore 7
    //   77: aload 7
    //   79: invokevirtual 215	androidx/camera/core/impl/utils/Exif:attachTimestamp	()V
    //   82: aload_0
    //   83: getfield 47	androidx/camera/core/ImageSaver:mImage	Landroidx/camera/core/ImageProxy;
    //   86: invokeinterface 221 1 0
    //   91: sipush 256
    //   94: if_icmpne +69 -> 163
    //   97: aload_0
    //   98: getfield 47	androidx/camera/core/ImageSaver:mImage	Landroidx/camera/core/ImageProxy;
    //   101: invokeinterface 225 1 0
    //   106: iconst_0
    //   107: aaload
    //   108: invokeinterface 231 1 0
    //   113: astore 9
    //   115: aload 9
    //   117: invokevirtual 237	java/nio/ByteBuffer:rewind	()Ljava/nio/Buffer;
    //   120: pop
    //   121: aload 9
    //   123: invokevirtual 240	java/nio/ByteBuffer:capacity	()I
    //   126: newarray byte
    //   128: astore_3
    //   129: aload 9
    //   131: aload_3
    //   132: invokevirtual 244	java/nio/ByteBuffer:get	([B)Ljava/nio/ByteBuffer;
    //   135: pop
    //   136: new 246	java/io/ByteArrayInputStream
    //   139: astore 9
    //   141: aload 9
    //   143: aload_3
    //   144: invokespecial 248	java/io/ByteArrayInputStream:<init>	([B)V
    //   147: aload 7
    //   149: aload 9
    //   151: invokestatic 252	androidx/camera/core/impl/utils/Exif:createFromInputStream	(Ljava/io/InputStream;)Landroidx/camera/core/impl/utils/Exif;
    //   154: invokevirtual 255	androidx/camera/core/impl/utils/Exif:getOrientation	()I
    //   157: invokevirtual 259	androidx/camera/core/impl/utils/Exif:setOrientation	(I)V
    //   160: goto +12 -> 172
    //   163: aload 7
    //   165: aload_0
    //   166: getfield 51	androidx/camera/core/ImageSaver:mOrientation	I
    //   169: invokevirtual 262	androidx/camera/core/impl/utils/Exif:rotate	(I)V
    //   172: aload_0
    //   173: getfield 49	androidx/camera/core/ImageSaver:mOutputFileOptions	Landroidx/camera/core/ImageCapture$OutputFileOptions;
    //   176: invokevirtual 266	androidx/camera/core/ImageCapture$OutputFileOptions:getMetadata	()Landroidx/camera/core/ImageCapture$Metadata;
    //   179: astore_3
    //   180: aload_3
    //   181: invokevirtual 271	androidx/camera/core/ImageCapture$Metadata:isReversedHorizontal	()Z
    //   184: ifeq +8 -> 192
    //   187: aload 7
    //   189: invokevirtual 274	androidx/camera/core/impl/utils/Exif:flipHorizontally	()V
    //   192: aload_3
    //   193: invokevirtual 277	androidx/camera/core/ImageCapture$Metadata:isReversedVertical	()Z
    //   196: ifeq +8 -> 204
    //   199: aload 7
    //   201: invokevirtual 280	androidx/camera/core/impl/utils/Exif:flipVertically	()V
    //   204: aload_3
    //   205: invokevirtual 284	androidx/camera/core/ImageCapture$Metadata:getLocation	()Landroid/location/Location;
    //   208: ifnull +18 -> 226
    //   211: aload 7
    //   213: aload_0
    //   214: getfield 49	androidx/camera/core/ImageSaver:mOutputFileOptions	Landroidx/camera/core/ImageCapture$OutputFileOptions;
    //   217: invokevirtual 266	androidx/camera/core/ImageCapture$OutputFileOptions:getMetadata	()Landroidx/camera/core/ImageCapture$Metadata;
    //   220: invokevirtual 284	androidx/camera/core/ImageCapture$Metadata:getLocation	()Landroid/location/Location;
    //   223: invokevirtual 288	androidx/camera/core/impl/utils/Exif:attachLocation	(Landroid/location/Location;)V
    //   226: aload 7
    //   228: invokevirtual 291	androidx/camera/core/impl/utils/Exif:save	()V
    //   231: aload_0
    //   232: invokespecial 293	androidx/camera/core/ImageSaver:isSaveToMediaStore	()Z
    //   235: ifeq +94 -> 329
    //   238: aload_0
    //   239: getfield 49	androidx/camera/core/ImageSaver:mOutputFileOptions	Landroidx/camera/core/ImageCapture$OutputFileOptions;
    //   242: invokevirtual 95	androidx/camera/core/ImageCapture$OutputFileOptions:getContentResolver	()Landroid/content/ContentResolver;
    //   245: aload_0
    //   246: getfield 49	androidx/camera/core/ImageSaver:mOutputFileOptions	Landroidx/camera/core/ImageCapture$OutputFileOptions;
    //   249: invokevirtual 115	androidx/camera/core/ImageCapture$OutputFileOptions:getSaveCollection	()Landroid/net/Uri;
    //   252: aload_0
    //   253: getfield 49	androidx/camera/core/ImageSaver:mOutputFileOptions	Landroidx/camera/core/ImageCapture$OutputFileOptions;
    //   256: invokevirtual 119	androidx/camera/core/ImageCapture$OutputFileOptions:getContentValues	()Landroid/content/ContentValues;
    //   259: invokevirtual 297	android/content/ContentResolver:insert	(Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
    //   262: astore 7
    //   264: aload 7
    //   266: ifnonnull +14 -> 280
    //   269: getstatic 301	androidx/camera/core/ImageSaver$SaveError:FILE_IO_FAILED	Landroidx/camera/core/ImageSaver$SaveError;
    //   272: astore_2
    //   273: ldc_w 303
    //   276: astore_3
    //   277: goto +79 -> 356
    //   280: aload_0
    //   281: aload 7
    //   283: iconst_1
    //   284: invokespecial 305	androidx/camera/core/ImageSaver:setUriPending	(Landroid/net/Uri;I)V
    //   287: aload_0
    //   288: aload_1
    //   289: aload 7
    //   291: invokespecial 307	androidx/camera/core/ImageSaver:copyTempFileToUri	(Ljava/io/File;Landroid/net/Uri;)Z
    //   294: ifne +14 -> 308
    //   297: getstatic 301	androidx/camera/core/ImageSaver$SaveError:FILE_IO_FAILED	Landroidx/camera/core/ImageSaver$SaveError;
    //   300: astore_2
    //   301: ldc_w 309
    //   304: astore_3
    //   305: goto +7 -> 312
    //   308: aconst_null
    //   309: astore_2
    //   310: aload_2
    //   311: astore_3
    //   312: aload_0
    //   313: aload 7
    //   315: iconst_0
    //   316: invokespecial 305	androidx/camera/core/ImageSaver:setUriPending	(Landroid/net/Uri;I)V
    //   319: goto +37 -> 356
    //   322: astore_3
    //   323: aload 7
    //   325: astore_2
    //   326: goto +103 -> 429
    //   329: aload_0
    //   330: invokespecial 311	androidx/camera/core/ImageSaver:isSaveToOutputStream	()Z
    //   333: ifeq +15 -> 348
    //   336: aload_0
    //   337: aload_1
    //   338: aload_0
    //   339: getfield 49	androidx/camera/core/ImageSaver:mOutputFileOptions	Landroidx/camera/core/ImageCapture$OutputFileOptions;
    //   342: invokevirtual 124	androidx/camera/core/ImageCapture$OutputFileOptions:getOutputStream	()Ljava/io/OutputStream;
    //   345: invokespecial 104	androidx/camera/core/ImageSaver:copyTempFileToOutputStream	(Ljava/io/File;Ljava/io/OutputStream;)V
    //   348: aconst_null
    //   349: astore 7
    //   351: aload 7
    //   353: astore_2
    //   354: aload_2
    //   355: astore_3
    //   356: aload 8
    //   358: invokevirtual 312	java/io/FileOutputStream:close	()V
    //   361: aload 6
    //   363: ifnull +18 -> 381
    //   366: aload 7
    //   368: astore 8
    //   370: aload 7
    //   372: astore 9
    //   374: aload 6
    //   376: invokeinterface 313 1 0
    //   381: aload 4
    //   383: astore 8
    //   385: aload 7
    //   387: astore 9
    //   389: aload_2
    //   390: astore 4
    //   392: aload_3
    //   393: astore 6
    //   395: aload_0
    //   396: invokespecial 188	androidx/camera/core/ImageSaver:isSaveToFile	()Z
    //   399: ifne +266 -> 665
    //   402: aload 5
    //   404: astore 8
    //   406: aload_1
    //   407: invokevirtual 316	java/io/File:delete	()Z
    //   410: pop
    //   411: aload 7
    //   413: astore 9
    //   415: aload_2
    //   416: astore 4
    //   418: aload_3
    //   419: astore 6
    //   421: goto +244 -> 665
    //   424: astore_2
    //   425: goto +34 -> 459
    //   428: astore_3
    //   429: aload_3
    //   430: athrow
    //   431: astore 9
    //   433: aload 8
    //   435: invokevirtual 312	java/io/FileOutputStream:close	()V
    //   438: goto +14 -> 452
    //   441: astore 8
    //   443: aload_2
    //   444: astore 7
    //   446: aload_3
    //   447: aload 8
    //   449: invokevirtual 86	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   452: aload_2
    //   453: astore 7
    //   455: aload 9
    //   457: athrow
    //   458: astore_2
    //   459: aload_2
    //   460: athrow
    //   461: astore_3
    //   462: aload 6
    //   464: ifnull +29 -> 493
    //   467: aload 6
    //   469: invokeinterface 313 1 0
    //   474: goto +19 -> 493
    //   477: astore 4
    //   479: aload 7
    //   481: astore 8
    //   483: aload 7
    //   485: astore 9
    //   487: aload_2
    //   488: aload 4
    //   490: invokevirtual 86	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   493: aload 7
    //   495: astore 8
    //   497: aload 7
    //   499: astore 9
    //   501: aload_3
    //   502: athrow
    //   503: astore_2
    //   504: aload 8
    //   506: astore 7
    //   508: goto +20 -> 528
    //   511: astore_2
    //   512: aload 9
    //   514: astore 7
    //   516: goto +110 -> 626
    //   519: astore 7
    //   521: goto +169 -> 690
    //   524: astore_2
    //   525: aconst_null
    //   526: astore 7
    //   528: getstatic 320	androidx/camera/core/ImageSaver$1:$SwitchMap$androidx$camera$core$ImageUtil$CodecFailedException$FailureType	[I
    //   531: aload_2
    //   532: invokevirtual 324	androidx/camera/core/ImageUtil$CodecFailedException:getFailureType	()Landroidx/camera/core/ImageUtil$CodecFailedException$FailureType;
    //   535: invokevirtual 329	androidx/camera/core/ImageUtil$CodecFailedException$FailureType:ordinal	()I
    //   538: iaload
    //   539: istore 10
    //   541: iload 10
    //   543: iconst_1
    //   544: if_icmpeq +33 -> 577
    //   547: iload 10
    //   549: iconst_2
    //   550: if_icmpeq +15 -> 565
    //   553: getstatic 332	androidx/camera/core/ImageSaver$SaveError:UNKNOWN	Landroidx/camera/core/ImageSaver$SaveError;
    //   556: astore_3
    //   557: ldc_w 334
    //   560: astore 8
    //   562: goto +24 -> 586
    //   565: getstatic 337	androidx/camera/core/ImageSaver$SaveError:CROP_FAILED	Landroidx/camera/core/ImageSaver$SaveError;
    //   568: astore_3
    //   569: ldc_w 339
    //   572: astore 8
    //   574: goto +12 -> 586
    //   577: getstatic 342	androidx/camera/core/ImageSaver$SaveError:ENCODE_FAILED	Landroidx/camera/core/ImageSaver$SaveError;
    //   580: astore_3
    //   581: ldc_w 344
    //   584: astore 8
    //   586: aload_3
    //   587: astore 5
    //   589: aload 8
    //   591: astore_3
    //   592: aload_2
    //   593: astore 8
    //   595: aload 7
    //   597: astore 9
    //   599: aload 5
    //   601: astore 4
    //   603: aload_3
    //   604: astore 6
    //   606: aload_0
    //   607: invokespecial 188	androidx/camera/core/ImageSaver:isSaveToFile	()Z
    //   610: ifne +55 -> 665
    //   613: aload_2
    //   614: astore 8
    //   616: aload 5
    //   618: astore_2
    //   619: goto -213 -> 406
    //   622: astore_2
    //   623: aconst_null
    //   624: astore 7
    //   626: getstatic 301	androidx/camera/core/ImageSaver$SaveError:FILE_IO_FAILED	Landroidx/camera/core/ImageSaver$SaveError;
    //   629: astore 5
    //   631: ldc_w 346
    //   634: astore_3
    //   635: aload_2
    //   636: astore 8
    //   638: aload 7
    //   640: astore 9
    //   642: aload 5
    //   644: astore 4
    //   646: aload_3
    //   647: astore 6
    //   649: aload_0
    //   650: invokespecial 188	androidx/camera/core/ImageSaver:isSaveToFile	()Z
    //   653: ifne +12 -> 665
    //   656: aload_2
    //   657: astore 8
    //   659: aload 5
    //   661: astore_2
    //   662: goto -256 -> 406
    //   665: aload 4
    //   667: ifnull +16 -> 683
    //   670: aload_0
    //   671: aload 4
    //   673: aload 6
    //   675: aload 8
    //   677: invokespecial 348	androidx/camera/core/ImageSaver:postError	(Landroidx/camera/core/ImageSaver$SaveError;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   680: goto +9 -> 689
    //   683: aload_0
    //   684: aload 9
    //   686: invokespecial 350	androidx/camera/core/ImageSaver:postSuccess	(Landroid/net/Uri;)V
    //   689: return
    //   690: aload_0
    //   691: invokespecial 188	androidx/camera/core/ImageSaver:isSaveToFile	()Z
    //   694: ifne +8 -> 702
    //   697: aload_1
    //   698: invokevirtual 316	java/io/File:delete	()Z
    //   701: pop
    //   702: aload 7
    //   704: athrow
    //   705: astore 7
    //   707: aload_0
    //   708: getstatic 301	androidx/camera/core/ImageSaver$SaveError:FILE_IO_FAILED	Landroidx/camera/core/ImageSaver$SaveError;
    //   711: ldc_w 352
    //   714: aload 7
    //   716: invokespecial 348	androidx/camera/core/ImageSaver:postError	(Landroidx/camera/core/ImageSaver$SaveError;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   719: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	720	0	this	ImageSaver
    //   14	684	1	localFile	java.io.File
    //   27	389	2	localObject1	Object
    //   424	29	2	localObject2	Object
    //   458	30	2	localObject3	Object
    //   503	1	2	localCodecFailedException1	ImageUtil.CodecFailedException
    //   511	1	2	localIOException1	java.io.IOException
    //   524	90	2	localCodecFailedException2	ImageUtil.CodecFailedException
    //   618	1	2	localObject4	Object
    //   622	35	2	localIOException2	java.io.IOException
    //   661	1	2	localObject5	Object
    //   29	283	3	localObject6	Object
    //   322	1	3	localObject7	Object
    //   355	64	3	localObject8	Object
    //   428	19	3	localObject9	Object
    //   461	41	3	localObject10	Object
    //   556	91	3	localObject11	Object
    //   31	386	4	localObject12	Object
    //   477	12	4	localThrowable1	Throwable
    //   601	71	4	localObject13	Object
    //   34	626	5	localObject14	Object
    //   40	634	6	localObject15	Object
    //   43	472	7	localObject16	Object
    //   519	1	7	localObject17	Object
    //   526	177	7	localObject18	Object
    //   705	10	7	localIOException3	java.io.IOException
    //   48	386	8	localObject19	Object
    //   441	7	8	localThrowable2	Throwable
    //   481	195	8	localObject20	Object
    //   113	301	9	localObject21	Object
    //   431	25	9	localObject22	Object
    //   485	200	9	localObject23	Object
    //   539	12	10	i	int
    // Exception table:
    //   from	to	target	type
    //   269	273	322	finally
    //   280	301	322	finally
    //   312	319	322	finally
    //   356	361	424	finally
    //   59	160	428	finally
    //   163	172	428	finally
    //   172	192	428	finally
    //   192	204	428	finally
    //   204	226	428	finally
    //   226	264	428	finally
    //   329	348	428	finally
    //   429	431	431	finally
    //   433	438	441	finally
    //   45	50	458	finally
    //   53	59	458	finally
    //   446	452	458	finally
    //   455	458	458	finally
    //   459	461	461	finally
    //   467	474	477	finally
    //   374	381	503	androidx/camera/core/ImageUtil$CodecFailedException
    //   487	493	503	androidx/camera/core/ImageUtil$CodecFailedException
    //   501	503	503	androidx/camera/core/ImageUtil$CodecFailedException
    //   374	381	511	java/io/IOException
    //   487	493	511	java/io/IOException
    //   501	503	511	java/io/IOException
    //   36	42	519	finally
    //   374	381	519	finally
    //   487	493	519	finally
    //   501	503	519	finally
    //   528	541	519	finally
    //   553	557	519	finally
    //   565	569	519	finally
    //   577	581	519	finally
    //   626	631	519	finally
    //   36	42	524	androidx/camera/core/ImageUtil$CodecFailedException
    //   36	42	622	java/io/IOException
    //   0	15	705	java/io/IOException
    //   18	26	705	java/io/IOException
  }
  
  public static abstract interface OnImageSavedCallback
  {
    public abstract void onError(ImageSaver.SaveError paramSaveError, String paramString, Throwable paramThrowable);
    
    public abstract void onImageSaved(ImageCapture.OutputFileResults paramOutputFileResults);
  }
  
  public static enum SaveError
  {
    static
    {
      ENCODE_FAILED = new SaveError("ENCODE_FAILED", 1);
      CROP_FAILED = new SaveError("CROP_FAILED", 2);
      SaveError localSaveError = new SaveError("UNKNOWN", 3);
      UNKNOWN = localSaveError;
      $VALUES = new SaveError[] { FILE_IO_FAILED, ENCODE_FAILED, CROP_FAILED, localSaveError };
    }
    
    private SaveError() {}
  }
}
