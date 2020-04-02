package androidx.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignal.OnCancelListener;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Builder;
import android.print.PrintAttributes.Margins;
import android.print.PrintAttributes.MediaSize;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentAdapter.LayoutResultCallback;
import android.print.PrintDocumentAdapter.WriteResultCallback;
import android.print.PrintDocumentInfo.Builder;
import android.print.PrintManager;
import android.util.Log;
import java.io.FileNotFoundException;

public final class PrintHelper
{
  public static final int COLOR_MODE_COLOR = 2;
  public static final int COLOR_MODE_MONOCHROME = 1;
  static final boolean IS_MIN_MARGINS_HANDLING_CORRECT;
  private static final String LOG_TAG = "PrintHelper";
  private static final int MAX_PRINT_SIZE = 3500;
  public static final int ORIENTATION_LANDSCAPE = 1;
  public static final int ORIENTATION_PORTRAIT = 2;
  static final boolean PRINT_ACTIVITY_RESPECTS_ORIENTATION;
  public static final int SCALE_MODE_FILL = 2;
  public static final int SCALE_MODE_FIT = 1;
  int mColorMode = 2;
  final Context mContext;
  BitmapFactory.Options mDecodeOptions = null;
  final Object mLock = new Object();
  int mOrientation = 1;
  int mScaleMode = 2;
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    if ((i >= 20) && (Build.VERSION.SDK_INT <= 23)) {
      bool2 = false;
    } else {
      bool2 = true;
    }
    PRINT_ACTIVITY_RESPECTS_ORIENTATION = bool2;
    boolean bool2 = bool1;
    if (Build.VERSION.SDK_INT != 23) {
      bool2 = true;
    }
    IS_MIN_MARGINS_HANDLING_CORRECT = bool2;
  }
  
  public PrintHelper(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  static Bitmap convertBitmapForColorMode(Bitmap paramBitmap, int paramInt)
  {
    if (paramInt != 1) {
      return paramBitmap;
    }
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    ColorMatrix localColorMatrix = new ColorMatrix();
    localColorMatrix.setSaturation(0.0F);
    localPaint.setColorFilter(new ColorMatrixColorFilter(localColorMatrix));
    localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
    localCanvas.setBitmap(null);
    return localBitmap;
  }
  
  private static PrintAttributes.Builder copyAttributes(PrintAttributes paramPrintAttributes)
  {
    PrintAttributes.Builder localBuilder = new PrintAttributes.Builder().setMediaSize(paramPrintAttributes.getMediaSize()).setResolution(paramPrintAttributes.getResolution()).setMinMargins(paramPrintAttributes.getMinMargins());
    if (paramPrintAttributes.getColorMode() != 0) {
      localBuilder.setColorMode(paramPrintAttributes.getColorMode());
    }
    if ((Build.VERSION.SDK_INT >= 23) && (paramPrintAttributes.getDuplexMode() != 0)) {
      localBuilder.setDuplexMode(paramPrintAttributes.getDuplexMode());
    }
    return localBuilder;
  }
  
  static Matrix getMatrix(int paramInt1, int paramInt2, RectF paramRectF, int paramInt3)
  {
    Matrix localMatrix = new Matrix();
    float f1 = paramRectF.width();
    float f2 = paramInt1;
    f1 /= f2;
    if (paramInt3 == 2) {
      f1 = Math.max(f1, paramRectF.height() / paramInt2);
    } else {
      f1 = Math.min(f1, paramRectF.height() / paramInt2);
    }
    localMatrix.postScale(f1, f1);
    localMatrix.postTranslate((paramRectF.width() - f2 * f1) / 2.0F, (paramRectF.height() - paramInt2 * f1) / 2.0F);
    return localMatrix;
  }
  
  static boolean isPortrait(Bitmap paramBitmap)
  {
    boolean bool;
    if (paramBitmap.getWidth() <= paramBitmap.getHeight()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  /* Error */
  private Bitmap loadBitmap(Uri paramUri, BitmapFactory.Options paramOptions)
    throws FileNotFoundException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +87 -> 88
    //   4: aload_0
    //   5: getfield 74	androidx/print/PrintHelper:mContext	Landroid/content/Context;
    //   8: astore_3
    //   9: aload_3
    //   10: ifnull +78 -> 88
    //   13: aconst_null
    //   14: astore 4
    //   16: aload_3
    //   17: invokevirtual 214	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   20: aload_1
    //   21: invokevirtual 220	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   24: astore_3
    //   25: aload_3
    //   26: aconst_null
    //   27: aload_2
    //   28: invokestatic 226	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   31: astore_1
    //   32: aload_3
    //   33: ifnull +20 -> 53
    //   36: aload_3
    //   37: invokevirtual 231	java/io/InputStream:close	()V
    //   40: goto +13 -> 53
    //   43: astore_2
    //   44: ldc 30
    //   46: ldc -23
    //   48: aload_2
    //   49: invokestatic 239	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   52: pop
    //   53: aload_1
    //   54: areturn
    //   55: astore_1
    //   56: aload_3
    //   57: astore_2
    //   58: goto +7 -> 65
    //   61: astore_1
    //   62: aload 4
    //   64: astore_2
    //   65: aload_2
    //   66: ifnull +20 -> 86
    //   69: aload_2
    //   70: invokevirtual 231	java/io/InputStream:close	()V
    //   73: goto +13 -> 86
    //   76: astore_2
    //   77: ldc 30
    //   79: ldc -23
    //   81: aload_2
    //   82: invokestatic 239	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   85: pop
    //   86: aload_1
    //   87: athrow
    //   88: new 241	java/lang/IllegalArgumentException
    //   91: dup
    //   92: ldc -13
    //   94: invokespecial 246	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   97: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	98	0	this	PrintHelper
    //   0	98	1	paramUri	Uri
    //   0	98	2	paramOptions	BitmapFactory.Options
    //   8	49	3	localObject1	Object
    //   14	49	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   36	40	43	java/io/IOException
    //   25	32	55	finally
    //   16	25	61	finally
    //   69	73	76	java/io/IOException
  }
  
  public static boolean systemSupportsPrint()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 19) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int getColorMode()
  {
    return this.mColorMode;
  }
  
  public int getOrientation()
  {
    if ((Build.VERSION.SDK_INT >= 19) && (this.mOrientation == 0)) {
      return 1;
    }
    return this.mOrientation;
  }
  
  public int getScaleMode()
  {
    return this.mScaleMode;
  }
  
  /* Error */
  Bitmap loadConstrainedBitmap(Uri arg1)
    throws FileNotFoundException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +211 -> 212
    //   4: aload_0
    //   5: getfield 74	androidx/print/PrintHelper:mContext	Landroid/content/Context;
    //   8: ifnull +204 -> 212
    //   11: new 255	android/graphics/BitmapFactory$Options
    //   14: dup
    //   15: invokespecial 256	android/graphics/BitmapFactory$Options:<init>	()V
    //   18: astore_2
    //   19: aload_2
    //   20: iconst_1
    //   21: putfield 259	android/graphics/BitmapFactory$Options:inJustDecodeBounds	Z
    //   24: aload_0
    //   25: aload_1
    //   26: aload_2
    //   27: invokespecial 261	androidx/print/PrintHelper:loadBitmap	(Landroid/net/Uri;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   30: pop
    //   31: aload_2
    //   32: getfield 264	android/graphics/BitmapFactory$Options:outWidth	I
    //   35: istore_3
    //   36: aload_2
    //   37: getfield 267	android/graphics/BitmapFactory$Options:outHeight	I
    //   40: istore 4
    //   42: iload_3
    //   43: ifle +167 -> 210
    //   46: iload 4
    //   48: ifgt +6 -> 54
    //   51: goto +159 -> 210
    //   54: iload_3
    //   55: iload 4
    //   57: invokestatic 270	java/lang/Math:max	(II)I
    //   60: istore 5
    //   62: iconst_1
    //   63: istore 6
    //   65: iload 5
    //   67: sipush 3500
    //   70: if_icmple +18 -> 88
    //   73: iload 5
    //   75: iconst_1
    //   76: iushr
    //   77: istore 5
    //   79: iload 6
    //   81: iconst_1
    //   82: ishl
    //   83: istore 6
    //   85: goto -20 -> 65
    //   88: iload 6
    //   90: ifle +120 -> 210
    //   93: iload_3
    //   94: iload 4
    //   96: invokestatic 272	java/lang/Math:min	(II)I
    //   99: iload 6
    //   101: idiv
    //   102: ifgt +6 -> 108
    //   105: goto +105 -> 210
    //   108: aload_0
    //   109: getfield 66	androidx/print/PrintHelper:mLock	Ljava/lang/Object;
    //   112: astore_2
    //   113: aload_2
    //   114: monitorenter
    //   115: new 255	android/graphics/BitmapFactory$Options
    //   118: astore 7
    //   120: aload 7
    //   122: invokespecial 256	android/graphics/BitmapFactory$Options:<init>	()V
    //   125: aload_0
    //   126: aload 7
    //   128: putfield 64	androidx/print/PrintHelper:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
    //   131: aload 7
    //   133: iconst_1
    //   134: putfield 275	android/graphics/BitmapFactory$Options:inMutable	Z
    //   137: aload_0
    //   138: getfield 64	androidx/print/PrintHelper:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
    //   141: iload 6
    //   143: putfield 278	android/graphics/BitmapFactory$Options:inSampleSize	I
    //   146: aload_0
    //   147: getfield 64	androidx/print/PrintHelper:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
    //   150: astore 7
    //   152: aload_2
    //   153: monitorexit
    //   154: aload_0
    //   155: aload_1
    //   156: aload 7
    //   158: invokespecial 261	androidx/print/PrintHelper:loadBitmap	(Landroid/net/Uri;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   161: astore_2
    //   162: aload_0
    //   163: getfield 66	androidx/print/PrintHelper:mLock	Ljava/lang/Object;
    //   166: astore_1
    //   167: aload_1
    //   168: monitorenter
    //   169: aload_0
    //   170: aconst_null
    //   171: putfield 64	androidx/print/PrintHelper:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
    //   174: aload_1
    //   175: monitorexit
    //   176: aload_2
    //   177: areturn
    //   178: astore_2
    //   179: aload_1
    //   180: monitorexit
    //   181: aload_2
    //   182: athrow
    //   183: astore_2
    //   184: aload_0
    //   185: getfield 66	androidx/print/PrintHelper:mLock	Ljava/lang/Object;
    //   188: astore_1
    //   189: aload_1
    //   190: monitorenter
    //   191: aload_0
    //   192: aconst_null
    //   193: putfield 64	androidx/print/PrintHelper:mDecodeOptions	Landroid/graphics/BitmapFactory$Options;
    //   196: aload_1
    //   197: monitorexit
    //   198: aload_2
    //   199: athrow
    //   200: astore_2
    //   201: aload_1
    //   202: monitorexit
    //   203: aload_2
    //   204: athrow
    //   205: astore_1
    //   206: aload_2
    //   207: monitorexit
    //   208: aload_1
    //   209: athrow
    //   210: aconst_null
    //   211: areturn
    //   212: new 241	java/lang/IllegalArgumentException
    //   215: dup
    //   216: ldc_w 280
    //   219: invokespecial 246	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   222: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	223	0	this	PrintHelper
    //   18	159	2	localObject1	Object
    //   178	4	2	localObject2	Object
    //   183	16	2	localObject3	Object
    //   200	7	2	localObject4	Object
    //   35	59	3	i	int
    //   40	55	4	j	int
    //   60	18	5	k	int
    //   63	79	6	m	int
    //   118	39	7	localOptions	BitmapFactory.Options
    // Exception table:
    //   from	to	target	type
    //   169	176	178	finally
    //   179	181	178	finally
    //   154	162	183	finally
    //   191	198	200	finally
    //   201	203	200	finally
    //   115	154	205	finally
    //   206	208	205	finally
  }
  
  public void printBitmap(String paramString, Bitmap paramBitmap)
  {
    printBitmap(paramString, paramBitmap, null);
  }
  
  public void printBitmap(String paramString, Bitmap paramBitmap, OnPrintFinishCallback paramOnPrintFinishCallback)
  {
    if ((Build.VERSION.SDK_INT >= 19) && (paramBitmap != null))
    {
      PrintManager localPrintManager = (PrintManager)this.mContext.getSystemService("print");
      if (isPortrait(paramBitmap)) {
        localObject = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
      } else {
        localObject = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
      }
      Object localObject = new PrintAttributes.Builder().setMediaSize((PrintAttributes.MediaSize)localObject).setColorMode(this.mColorMode).build();
      localPrintManager.print(paramString, new PrintBitmapAdapter(paramString, this.mScaleMode, paramBitmap, paramOnPrintFinishCallback), (PrintAttributes)localObject);
    }
  }
  
  public void printBitmap(String paramString, Uri paramUri)
    throws FileNotFoundException
  {
    printBitmap(paramString, paramUri, null);
  }
  
  public void printBitmap(String paramString, Uri paramUri, OnPrintFinishCallback paramOnPrintFinishCallback)
    throws FileNotFoundException
  {
    if (Build.VERSION.SDK_INT < 19) {
      return;
    }
    paramUri = new PrintUriAdapter(paramString, paramUri, paramOnPrintFinishCallback, this.mScaleMode);
    paramOnPrintFinishCallback = (PrintManager)this.mContext.getSystemService("print");
    PrintAttributes.Builder localBuilder = new PrintAttributes.Builder();
    localBuilder.setColorMode(this.mColorMode);
    int i = this.mOrientation;
    if ((i != 1) && (i != 0))
    {
      if (i == 2) {
        localBuilder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
      }
    }
    else {
      localBuilder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
    }
    paramOnPrintFinishCallback.print(paramString, paramUri, localBuilder.build());
  }
  
  public void setColorMode(int paramInt)
  {
    this.mColorMode = paramInt;
  }
  
  public void setOrientation(int paramInt)
  {
    this.mOrientation = paramInt;
  }
  
  public void setScaleMode(int paramInt)
  {
    this.mScaleMode = paramInt;
  }
  
  void writeBitmap(final PrintAttributes paramPrintAttributes, final int paramInt, final Bitmap paramBitmap, final ParcelFileDescriptor paramParcelFileDescriptor, final CancellationSignal paramCancellationSignal, final PrintDocumentAdapter.WriteResultCallback paramWriteResultCallback)
  {
    final PrintAttributes localPrintAttributes;
    if (IS_MIN_MARGINS_HANDLING_CORRECT) {
      localPrintAttributes = paramPrintAttributes;
    } else {
      localPrintAttributes = copyAttributes(paramPrintAttributes).setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build();
    }
    new AsyncTask()
    {
      /* Error */
      protected Throwable doInBackground(Void... paramAnonymousVarArgs)
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 31	androidx/print/PrintHelper$1:val$cancellationSignal	Landroid/os/CancellationSignal;
        //   4: invokevirtual 62	android/os/CancellationSignal:isCanceled	()Z
        //   7: ifeq +5 -> 12
        //   10: aconst_null
        //   11: areturn
        //   12: new 64	android/print/pdf/PrintedPdfDocument
        //   15: astore_2
        //   16: aload_2
        //   17: aload_0
        //   18: getfield 29	androidx/print/PrintHelper$1:this$0	Landroidx/print/PrintHelper;
        //   21: getfield 68	androidx/print/PrintHelper:mContext	Landroid/content/Context;
        //   24: aload_0
        //   25: getfield 33	androidx/print/PrintHelper$1:val$pdfAttributes	Landroid/print/PrintAttributes;
        //   28: invokespecial 71	android/print/pdf/PrintedPdfDocument:<init>	(Landroid/content/Context;Landroid/print/PrintAttributes;)V
        //   31: aload_0
        //   32: getfield 35	androidx/print/PrintHelper$1:val$bitmap	Landroid/graphics/Bitmap;
        //   35: aload_0
        //   36: getfield 33	androidx/print/PrintHelper$1:val$pdfAttributes	Landroid/print/PrintAttributes;
        //   39: invokevirtual 77	android/print/PrintAttributes:getColorMode	()I
        //   42: invokestatic 81	androidx/print/PrintHelper:convertBitmapForColorMode	(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;
        //   45: astore_3
        //   46: aload_0
        //   47: getfield 31	androidx/print/PrintHelper$1:val$cancellationSignal	Landroid/os/CancellationSignal;
        //   50: invokevirtual 62	android/os/CancellationSignal:isCanceled	()Z
        //   53: istore 4
        //   55: iload 4
        //   57: ifeq +5 -> 62
        //   60: aconst_null
        //   61: areturn
        //   62: aload_2
        //   63: iconst_1
        //   64: invokevirtual 85	android/print/pdf/PrintedPdfDocument:startPage	(I)Landroid/graphics/pdf/PdfDocument$Page;
        //   67: astore 5
        //   69: getstatic 89	androidx/print/PrintHelper:IS_MIN_MARGINS_HANDLING_CORRECT	Z
        //   72: ifeq +22 -> 94
        //   75: new 91	android/graphics/RectF
        //   78: astore_1
        //   79: aload_1
        //   80: aload 5
        //   82: invokevirtual 97	android/graphics/pdf/PdfDocument$Page:getInfo	()Landroid/graphics/pdf/PdfDocument$PageInfo;
        //   85: invokevirtual 103	android/graphics/pdf/PdfDocument$PageInfo:getContentRect	()Landroid/graphics/Rect;
        //   88: invokespecial 106	android/graphics/RectF:<init>	(Landroid/graphics/Rect;)V
        //   91: goto +60 -> 151
        //   94: new 64	android/print/pdf/PrintedPdfDocument
        //   97: astore 6
        //   99: aload 6
        //   101: aload_0
        //   102: getfield 29	androidx/print/PrintHelper$1:this$0	Landroidx/print/PrintHelper;
        //   105: getfield 68	androidx/print/PrintHelper:mContext	Landroid/content/Context;
        //   108: aload_0
        //   109: getfield 37	androidx/print/PrintHelper$1:val$attributes	Landroid/print/PrintAttributes;
        //   112: invokespecial 71	android/print/pdf/PrintedPdfDocument:<init>	(Landroid/content/Context;Landroid/print/PrintAttributes;)V
        //   115: aload 6
        //   117: iconst_1
        //   118: invokevirtual 85	android/print/pdf/PrintedPdfDocument:startPage	(I)Landroid/graphics/pdf/PdfDocument$Page;
        //   121: astore 7
        //   123: new 91	android/graphics/RectF
        //   126: astore_1
        //   127: aload_1
        //   128: aload 7
        //   130: invokevirtual 97	android/graphics/pdf/PdfDocument$Page:getInfo	()Landroid/graphics/pdf/PdfDocument$PageInfo;
        //   133: invokevirtual 103	android/graphics/pdf/PdfDocument$PageInfo:getContentRect	()Landroid/graphics/Rect;
        //   136: invokespecial 106	android/graphics/RectF:<init>	(Landroid/graphics/Rect;)V
        //   139: aload 6
        //   141: aload 7
        //   143: invokevirtual 110	android/print/pdf/PrintedPdfDocument:finishPage	(Landroid/graphics/pdf/PdfDocument$Page;)V
        //   146: aload 6
        //   148: invokevirtual 113	android/print/pdf/PrintedPdfDocument:close	()V
        //   151: aload_3
        //   152: invokevirtual 118	android/graphics/Bitmap:getWidth	()I
        //   155: aload_3
        //   156: invokevirtual 121	android/graphics/Bitmap:getHeight	()I
        //   159: aload_1
        //   160: aload_0
        //   161: getfield 39	androidx/print/PrintHelper$1:val$fittingMode	I
        //   164: invokestatic 125	androidx/print/PrintHelper:getMatrix	(IILandroid/graphics/RectF;I)Landroid/graphics/Matrix;
        //   167: astore 6
        //   169: getstatic 89	androidx/print/PrintHelper:IS_MIN_MARGINS_HANDLING_CORRECT	Z
        //   172: ifeq +6 -> 178
        //   175: goto +27 -> 202
        //   178: aload 6
        //   180: aload_1
        //   181: getfield 129	android/graphics/RectF:left	F
        //   184: aload_1
        //   185: getfield 132	android/graphics/RectF:top	F
        //   188: invokevirtual 138	android/graphics/Matrix:postTranslate	(FF)Z
        //   191: pop
        //   192: aload 5
        //   194: invokevirtual 142	android/graphics/pdf/PdfDocument$Page:getCanvas	()Landroid/graphics/Canvas;
        //   197: aload_1
        //   198: invokevirtual 148	android/graphics/Canvas:clipRect	(Landroid/graphics/RectF;)Z
        //   201: pop
        //   202: aload 5
        //   204: invokevirtual 142	android/graphics/pdf/PdfDocument$Page:getCanvas	()Landroid/graphics/Canvas;
        //   207: aload_3
        //   208: aload 6
        //   210: aconst_null
        //   211: invokevirtual 152	android/graphics/Canvas:drawBitmap	(Landroid/graphics/Bitmap;Landroid/graphics/Matrix;Landroid/graphics/Paint;)V
        //   214: aload_2
        //   215: aload 5
        //   217: invokevirtual 110	android/print/pdf/PrintedPdfDocument:finishPage	(Landroid/graphics/pdf/PdfDocument$Page;)V
        //   220: aload_0
        //   221: getfield 31	androidx/print/PrintHelper$1:val$cancellationSignal	Landroid/os/CancellationSignal;
        //   224: invokevirtual 62	android/os/CancellationSignal:isCanceled	()Z
        //   227: istore 4
        //   229: iload 4
        //   231: ifeq +37 -> 268
        //   234: aload_2
        //   235: invokevirtual 113	android/print/pdf/PrintedPdfDocument:close	()V
        //   238: aload_0
        //   239: getfield 41	androidx/print/PrintHelper$1:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
        //   242: astore_1
        //   243: aload_1
        //   244: ifnull +10 -> 254
        //   247: aload_0
        //   248: getfield 41	androidx/print/PrintHelper$1:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
        //   251: invokevirtual 155	android/os/ParcelFileDescriptor:close	()V
        //   254: aload_3
        //   255: aload_0
        //   256: getfield 35	androidx/print/PrintHelper$1:val$bitmap	Landroid/graphics/Bitmap;
        //   259: if_acmpeq +7 -> 266
        //   262: aload_3
        //   263: invokevirtual 158	android/graphics/Bitmap:recycle	()V
        //   266: aconst_null
        //   267: areturn
        //   268: new 160	java/io/FileOutputStream
        //   271: astore_1
        //   272: aload_1
        //   273: aload_0
        //   274: getfield 41	androidx/print/PrintHelper$1:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
        //   277: invokevirtual 164	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
        //   280: invokespecial 167	java/io/FileOutputStream:<init>	(Ljava/io/FileDescriptor;)V
        //   283: aload_2
        //   284: aload_1
        //   285: invokevirtual 171	android/print/pdf/PrintedPdfDocument:writeTo	(Ljava/io/OutputStream;)V
        //   288: aload_2
        //   289: invokevirtual 113	android/print/pdf/PrintedPdfDocument:close	()V
        //   292: aload_0
        //   293: getfield 41	androidx/print/PrintHelper$1:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
        //   296: astore_1
        //   297: aload_1
        //   298: ifnull +10 -> 308
        //   301: aload_0
        //   302: getfield 41	androidx/print/PrintHelper$1:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
        //   305: invokevirtual 155	android/os/ParcelFileDescriptor:close	()V
        //   308: aload_3
        //   309: aload_0
        //   310: getfield 35	androidx/print/PrintHelper$1:val$bitmap	Landroid/graphics/Bitmap;
        //   313: if_acmpeq +7 -> 320
        //   316: aload_3
        //   317: invokevirtual 158	android/graphics/Bitmap:recycle	()V
        //   320: aconst_null
        //   321: areturn
        //   322: astore_1
        //   323: aload_2
        //   324: invokevirtual 113	android/print/pdf/PrintedPdfDocument:close	()V
        //   327: aload_0
        //   328: getfield 41	androidx/print/PrintHelper$1:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
        //   331: astore_2
        //   332: aload_2
        //   333: ifnull +10 -> 343
        //   336: aload_0
        //   337: getfield 41	androidx/print/PrintHelper$1:val$fileDescriptor	Landroid/os/ParcelFileDescriptor;
        //   340: invokevirtual 155	android/os/ParcelFileDescriptor:close	()V
        //   343: aload_3
        //   344: aload_0
        //   345: getfield 35	androidx/print/PrintHelper$1:val$bitmap	Landroid/graphics/Bitmap;
        //   348: if_acmpeq +7 -> 355
        //   351: aload_3
        //   352: invokevirtual 158	android/graphics/Bitmap:recycle	()V
        //   355: aload_1
        //   356: athrow
        //   357: astore_1
        //   358: aload_1
        //   359: areturn
        //   360: astore_1
        //   361: goto -107 -> 254
        //   364: astore_1
        //   365: goto -57 -> 308
        //   368: astore_2
        //   369: goto -26 -> 343
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	372	0	this	1
        //   0	372	1	paramAnonymousVarArgs	Void[]
        //   15	318	2	localObject1	Object
        //   368	1	2	localIOException	java.io.IOException
        //   45	307	3	localBitmap	Bitmap
        //   53	177	4	bool	boolean
        //   67	149	5	localPage1	android.graphics.pdf.PdfDocument.Page
        //   97	112	6	localObject2	Object
        //   121	21	7	localPage2	android.graphics.pdf.PdfDocument.Page
        // Exception table:
        //   from	to	target	type
        //   62	91	322	finally
        //   94	151	322	finally
        //   151	175	322	finally
        //   178	202	322	finally
        //   202	229	322	finally
        //   268	288	322	finally
        //   0	10	357	finally
        //   12	55	357	finally
        //   234	243	357	finally
        //   247	254	357	finally
        //   254	266	357	finally
        //   288	297	357	finally
        //   301	308	357	finally
        //   308	320	357	finally
        //   323	332	357	finally
        //   336	343	357	finally
        //   343	355	357	finally
        //   355	357	357	finally
        //   247	254	360	java/io/IOException
        //   301	308	364	java/io/IOException
        //   336	343	368	java/io/IOException
      }
      
      protected void onPostExecute(Throwable paramAnonymousThrowable)
      {
        if (paramCancellationSignal.isCanceled())
        {
          paramWriteResultCallback.onWriteCancelled();
        }
        else if (paramAnonymousThrowable == null)
        {
          paramWriteResultCallback.onWriteFinished(new PageRange[] { PageRange.ALL_PAGES });
        }
        else
        {
          Log.e("PrintHelper", "Error writing printed content", paramAnonymousThrowable);
          paramWriteResultCallback.onWriteFailed(null);
        }
      }
    }.execute(new Void[0]);
  }
  
  public static abstract interface OnPrintFinishCallback
  {
    public abstract void onFinish();
  }
  
  private class PrintBitmapAdapter
    extends PrintDocumentAdapter
  {
    private PrintAttributes mAttributes;
    private final Bitmap mBitmap;
    private final PrintHelper.OnPrintFinishCallback mCallback;
    private final int mFittingMode;
    private final String mJobName;
    
    PrintBitmapAdapter(String paramString, int paramInt, Bitmap paramBitmap, PrintHelper.OnPrintFinishCallback paramOnPrintFinishCallback)
    {
      this.mJobName = paramString;
      this.mFittingMode = paramInt;
      this.mBitmap = paramBitmap;
      this.mCallback = paramOnPrintFinishCallback;
    }
    
    public void onFinish()
    {
      PrintHelper.OnPrintFinishCallback localOnPrintFinishCallback = this.mCallback;
      if (localOnPrintFinishCallback != null) {
        localOnPrintFinishCallback.onFinish();
      }
    }
    
    public void onLayout(PrintAttributes paramPrintAttributes1, PrintAttributes paramPrintAttributes2, CancellationSignal paramCancellationSignal, PrintDocumentAdapter.LayoutResultCallback paramLayoutResultCallback, Bundle paramBundle)
    {
      this.mAttributes = paramPrintAttributes2;
      paramLayoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.mJobName).setContentType(1).setPageCount(1).build(), paramPrintAttributes2.equals(paramPrintAttributes1) ^ true);
    }
    
    public void onWrite(PageRange[] paramArrayOfPageRange, ParcelFileDescriptor paramParcelFileDescriptor, CancellationSignal paramCancellationSignal, PrintDocumentAdapter.WriteResultCallback paramWriteResultCallback)
    {
      PrintHelper.this.writeBitmap(this.mAttributes, this.mFittingMode, this.mBitmap, paramParcelFileDescriptor, paramCancellationSignal, paramWriteResultCallback);
    }
  }
  
  private class PrintUriAdapter
    extends PrintDocumentAdapter
  {
    PrintAttributes mAttributes;
    Bitmap mBitmap;
    final PrintHelper.OnPrintFinishCallback mCallback;
    final int mFittingMode;
    final Uri mImageFile;
    final String mJobName;
    AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;
    
    PrintUriAdapter(String paramString, Uri paramUri, PrintHelper.OnPrintFinishCallback paramOnPrintFinishCallback, int paramInt)
    {
      this.mJobName = paramString;
      this.mImageFile = paramUri;
      this.mCallback = paramOnPrintFinishCallback;
      this.mFittingMode = paramInt;
      this.mBitmap = null;
    }
    
    void cancelLoad()
    {
      synchronized (PrintHelper.this.mLock)
      {
        if (PrintHelper.this.mDecodeOptions != null)
        {
          if (Build.VERSION.SDK_INT < 24) {
            PrintHelper.this.mDecodeOptions.requestCancelDecode();
          }
          PrintHelper.this.mDecodeOptions = null;
        }
        return;
      }
    }
    
    public void onFinish()
    {
      super.onFinish();
      cancelLoad();
      Object localObject = this.mLoadBitmap;
      if (localObject != null) {
        ((AsyncTask)localObject).cancel(true);
      }
      localObject = this.mCallback;
      if (localObject != null) {
        ((PrintHelper.OnPrintFinishCallback)localObject).onFinish();
      }
      localObject = this.mBitmap;
      if (localObject != null)
      {
        ((Bitmap)localObject).recycle();
        this.mBitmap = null;
      }
    }
    
    public void onLayout(final PrintAttributes paramPrintAttributes1, final PrintAttributes paramPrintAttributes2, final CancellationSignal paramCancellationSignal, final PrintDocumentAdapter.LayoutResultCallback paramLayoutResultCallback, Bundle paramBundle)
    {
      try
      {
        this.mAttributes = paramPrintAttributes2;
        if (paramCancellationSignal.isCanceled())
        {
          paramLayoutResultCallback.onLayoutCancelled();
          return;
        }
        if (this.mBitmap != null)
        {
          paramLayoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.mJobName).setContentType(1).setPageCount(1).build(), paramPrintAttributes2.equals(paramPrintAttributes1) ^ true);
          return;
        }
        this.mLoadBitmap = new AsyncTask()
        {
          protected Bitmap doInBackground(Uri... paramAnonymousVarArgs)
          {
            try
            {
              paramAnonymousVarArgs = PrintHelper.this.loadConstrainedBitmap(PrintHelper.PrintUriAdapter.this.mImageFile);
              return paramAnonymousVarArgs;
            }
            catch (FileNotFoundException paramAnonymousVarArgs) {}
            return null;
          }
          
          protected void onCancelled(Bitmap paramAnonymousBitmap)
          {
            paramLayoutResultCallback.onLayoutCancelled();
            PrintHelper.PrintUriAdapter.this.mLoadBitmap = null;
          }
          
          protected void onPostExecute(Bitmap paramAnonymousBitmap)
          {
            super.onPostExecute(paramAnonymousBitmap);
            Object localObject = paramAnonymousBitmap;
            if (paramAnonymousBitmap != null) {
              if (PrintHelper.PRINT_ACTIVITY_RESPECTS_ORIENTATION)
              {
                localObject = paramAnonymousBitmap;
                if (PrintHelper.this.mOrientation != 0) {}
              }
              else
              {
                try
                {
                  PrintAttributes.MediaSize localMediaSize = PrintHelper.PrintUriAdapter.this.mAttributes.getMediaSize();
                  localObject = paramAnonymousBitmap;
                  if (localMediaSize != null)
                  {
                    localObject = paramAnonymousBitmap;
                    if (localMediaSize.isPortrait() != PrintHelper.isPortrait(paramAnonymousBitmap))
                    {
                      localObject = new Matrix();
                      ((Matrix)localObject).postRotate(90.0F);
                      localObject = Bitmap.createBitmap(paramAnonymousBitmap, 0, 0, paramAnonymousBitmap.getWidth(), paramAnonymousBitmap.getHeight(), (Matrix)localObject, true);
                    }
                  }
                }
                finally {}
              }
            }
            PrintHelper.PrintUriAdapter.this.mBitmap = ((Bitmap)localObject);
            if (localObject != null)
            {
              paramAnonymousBitmap = new PrintDocumentInfo.Builder(PrintHelper.PrintUriAdapter.this.mJobName).setContentType(1).setPageCount(1).build();
              boolean bool = paramPrintAttributes2.equals(paramPrintAttributes1);
              paramLayoutResultCallback.onLayoutFinished(paramAnonymousBitmap, true ^ bool);
            }
            else
            {
              paramLayoutResultCallback.onLayoutFailed(null);
            }
            PrintHelper.PrintUriAdapter.this.mLoadBitmap = null;
          }
          
          protected void onPreExecute()
          {
            paramCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener()
            {
              public void onCancel()
              {
                PrintHelper.PrintUriAdapter.this.cancelLoad();
                PrintHelper.PrintUriAdapter.1.this.cancel(false);
              }
            });
          }
        }.execute(new Uri[0]);
        return;
      }
      finally {}
    }
    
    public void onWrite(PageRange[] paramArrayOfPageRange, ParcelFileDescriptor paramParcelFileDescriptor, CancellationSignal paramCancellationSignal, PrintDocumentAdapter.WriteResultCallback paramWriteResultCallback)
    {
      PrintHelper.this.writeBitmap(this.mAttributes, this.mFittingMode, this.mBitmap, paramParcelFileDescriptor, paramCancellationSignal, paramWriteResultCallback);
    }
  }
}
