package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.Typeface.CustomFallbackBuilder;
import android.graphics.fonts.Font;
import android.graphics.fonts.Font.Builder;
import android.graphics.fonts.FontFamily.Builder;
import android.graphics.fonts.FontStyle;
import androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import androidx.core.provider.FontsContractCompat.FontInfo;
import java.io.IOException;
import java.io.InputStream;

public class TypefaceCompatApi29Impl
  extends TypefaceCompatBaseImpl
{
  public TypefaceCompatApi29Impl() {}
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt)
  {
    FontResourcesParserCompat.FontFileResourceEntry[] arrayOfFontFileResourceEntry = paramFontFamilyFilesResourceEntry.getEntries();
    int i = arrayOfFontFileResourceEntry.length;
    int j = 0;
    paramContext = null;
    int k = 0;
    for (;;)
    {
      int m = 1;
      if (k >= i) {
        break;
      }
      paramFontFamilyFilesResourceEntry = arrayOfFontFileResourceEntry[k];
      try
      {
        Object localObject = new android/graphics/fonts/Font$Builder;
        ((Font.Builder)localObject).<init>(paramResources, paramFontFamilyFilesResourceEntry.getResourceId());
        localObject = ((Font.Builder)localObject).setWeight(paramFontFamilyFilesResourceEntry.getWeight());
        if (!paramFontFamilyFilesResourceEntry.isItalic()) {
          m = 0;
        }
        localObject = ((Font.Builder)localObject).setSlant(m).setTtcIndex(paramFontFamilyFilesResourceEntry.getTtcIndex()).setFontVariationSettings(paramFontFamilyFilesResourceEntry.getVariationSettings()).build();
        if (paramContext == null)
        {
          paramFontFamilyFilesResourceEntry = new android/graphics/fonts/FontFamily$Builder;
          paramFontFamilyFilesResourceEntry.<init>((Font)localObject);
          paramContext = paramFontFamilyFilesResourceEntry;
        }
        else
        {
          paramContext.addFont((Font)localObject);
        }
        k++;
      }
      catch (IOException paramFontFamilyFilesResourceEntry)
      {
        for (;;) {}
      }
    }
    if (paramContext == null) {
      return null;
    }
    if ((paramInt & 0x1) != 0) {
      k = 700;
    } else {
      k = 400;
    }
    m = j;
    if ((paramInt & 0x2) != 0) {
      m = 1;
    }
    paramFontFamilyFilesResourceEntry = new FontStyle(k, m);
    return new Typeface.CustomFallbackBuilder(paramContext.build()).setStyle(paramFontFamilyFilesResourceEntry).build();
  }
  
  /* Error */
  public Typeface createFromFontInfo(Context paramContext, android.os.CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 99	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   4: astore 5
    //   6: aload_3
    //   7: arraylength
    //   8: istore 6
    //   10: iconst_0
    //   11: istore 7
    //   13: aconst_null
    //   14: astore_1
    //   15: iconst_0
    //   16: istore 8
    //   18: iconst_1
    //   19: istore 9
    //   21: iload 8
    //   23: iload 6
    //   25: if_icmpge +198 -> 223
    //   28: aload_3
    //   29: iload 8
    //   31: aaload
    //   32: astore 10
    //   34: aload_1
    //   35: astore 11
    //   37: aload 5
    //   39: aload 10
    //   41: invokevirtual 105	androidx/core/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   44: ldc 107
    //   46: aload_2
    //   47: invokevirtual 113	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   50: astore 12
    //   52: aload 12
    //   54: ifnonnull +25 -> 79
    //   57: aload_1
    //   58: astore 11
    //   60: aload 12
    //   62: ifnull +152 -> 214
    //   65: aload_1
    //   66: astore 11
    //   68: aload 12
    //   70: invokevirtual 118	android/os/ParcelFileDescriptor:close	()V
    //   73: aload_1
    //   74: astore 11
    //   76: goto +138 -> 214
    //   79: new 21	android/graphics/fonts/Font$Builder
    //   82: astore 11
    //   84: aload 11
    //   86: aload 12
    //   88: invokespecial 121	android/graphics/fonts/Font$Builder:<init>	(Landroid/os/ParcelFileDescriptor;)V
    //   91: aload 11
    //   93: aload 10
    //   95: invokevirtual 122	androidx/core/provider/FontsContractCompat$FontInfo:getWeight	()I
    //   98: invokevirtual 37	android/graphics/fonts/Font$Builder:setWeight	(I)Landroid/graphics/fonts/Font$Builder;
    //   101: astore 11
    //   103: aload 10
    //   105: invokevirtual 123	androidx/core/provider/FontsContractCompat$FontInfo:isItalic	()Z
    //   108: ifeq +6 -> 114
    //   111: goto +6 -> 117
    //   114: iconst_0
    //   115: istore 9
    //   117: aload 11
    //   119: iload 9
    //   121: invokevirtual 44	android/graphics/fonts/Font$Builder:setSlant	(I)Landroid/graphics/fonts/Font$Builder;
    //   124: aload 10
    //   126: invokevirtual 124	androidx/core/provider/FontsContractCompat$FontInfo:getTtcIndex	()I
    //   129: invokevirtual 50	android/graphics/fonts/Font$Builder:setTtcIndex	(I)Landroid/graphics/fonts/Font$Builder;
    //   132: invokevirtual 62	android/graphics/fonts/Font$Builder:build	()Landroid/graphics/fonts/Font;
    //   135: astore 11
    //   137: aload_1
    //   138: ifnonnull +20 -> 158
    //   141: new 64	android/graphics/fonts/FontFamily$Builder
    //   144: dup
    //   145: aload 11
    //   147: invokespecial 67	android/graphics/fonts/FontFamily$Builder:<init>	(Landroid/graphics/fonts/Font;)V
    //   150: astore 11
    //   152: aload 11
    //   154: astore_1
    //   155: goto +10 -> 165
    //   158: aload_1
    //   159: aload 11
    //   161: invokevirtual 71	android/graphics/fonts/FontFamily$Builder:addFont	(Landroid/graphics/fonts/Font;)Landroid/graphics/fonts/FontFamily$Builder;
    //   164: pop
    //   165: aload_1
    //   166: astore 11
    //   168: aload 12
    //   170: ifnull +44 -> 214
    //   173: goto -108 -> 65
    //   176: astore 13
    //   178: aload 13
    //   180: athrow
    //   181: astore 10
    //   183: aload 12
    //   185: ifnull +23 -> 208
    //   188: aload 12
    //   190: invokevirtual 118	android/os/ParcelFileDescriptor:close	()V
    //   193: goto +15 -> 208
    //   196: astore 12
    //   198: aload_1
    //   199: astore 11
    //   201: aload 13
    //   203: aload 12
    //   205: invokevirtual 130	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   208: aload_1
    //   209: astore 11
    //   211: aload 10
    //   213: athrow
    //   214: iinc 8 1
    //   217: aload 11
    //   219: astore_1
    //   220: goto -202 -> 18
    //   223: aload_1
    //   224: ifnonnull +5 -> 229
    //   227: aconst_null
    //   228: areturn
    //   229: iload 4
    //   231: iconst_1
    //   232: iand
    //   233: ifeq +11 -> 244
    //   236: sipush 700
    //   239: istore 8
    //   241: goto +8 -> 249
    //   244: sipush 400
    //   247: istore 8
    //   249: iload 7
    //   251: istore 9
    //   253: iload 4
    //   255: iconst_2
    //   256: iand
    //   257: ifeq +6 -> 263
    //   260: iconst_1
    //   261: istore 9
    //   263: new 73	android/graphics/fonts/FontStyle
    //   266: dup
    //   267: iload 8
    //   269: iload 9
    //   271: invokespecial 76	android/graphics/fonts/FontStyle:<init>	(II)V
    //   274: astore_2
    //   275: new 78	android/graphics/Typeface$CustomFallbackBuilder
    //   278: dup
    //   279: aload_1
    //   280: invokevirtual 81	android/graphics/fonts/FontFamily$Builder:build	()Landroid/graphics/fonts/FontFamily;
    //   283: invokespecial 84	android/graphics/Typeface$CustomFallbackBuilder:<init>	(Landroid/graphics/fonts/FontFamily;)V
    //   286: aload_2
    //   287: invokevirtual 88	android/graphics/Typeface$CustomFallbackBuilder:setStyle	(Landroid/graphics/fonts/FontStyle;)Landroid/graphics/Typeface$CustomFallbackBuilder;
    //   290: invokevirtual 91	android/graphics/Typeface$CustomFallbackBuilder:build	()Landroid/graphics/Typeface;
    //   293: areturn
    //   294: astore_1
    //   295: goto -81 -> 214
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	298	0	this	TypefaceCompatApi29Impl
    //   0	298	1	paramContext	Context
    //   0	298	2	paramCancellationSignal	android.os.CancellationSignal
    //   0	298	3	paramArrayOfFontInfo	FontsContractCompat.FontInfo[]
    //   0	298	4	paramInt	int
    //   4	34	5	localContentResolver	android.content.ContentResolver
    //   8	18	6	i	int
    //   11	239	7	j	int
    //   16	252	8	k	int
    //   19	251	9	m	int
    //   32	93	10	localFontInfo	FontsContractCompat.FontInfo
    //   181	31	10	localObject1	Object
    //   35	183	11	localObject2	Object
    //   50	139	12	localParcelFileDescriptor	android.os.ParcelFileDescriptor
    //   196	8	12	localThrowable	Throwable
    //   176	26	13	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   79	111	176	finally
    //   117	137	176	finally
    //   141	152	176	finally
    //   158	165	176	finally
    //   178	181	181	finally
    //   188	193	196	finally
    //   37	52	294	java/io/IOException
    //   68	73	294	java/io/IOException
    //   201	208	294	java/io/IOException
    //   211	214	294	java/io/IOException
  }
  
  protected Typeface createFromInputStream(Context paramContext, InputStream paramInputStream)
  {
    throw new RuntimeException("Do not use this function in API 29 or later.");
  }
  
  public Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2)
  {
    try
    {
      paramString = new android/graphics/fonts/FontFamily$Builder;
      paramContext = new android/graphics/fonts/Font$Builder;
      paramContext.<init>(paramResources, paramInt1);
      paramString.<init>(paramContext.build());
      paramContext = paramString.build();
      if ((paramInt2 & 0x1) != 0) {
        paramInt1 = 700;
      } else {
        paramInt1 = 400;
      }
      if ((paramInt2 & 0x2) != 0) {
        paramInt2 = 1;
      } else {
        paramInt2 = 0;
      }
      paramResources = new FontStyle(paramInt1, paramInt2);
      return new Typeface.CustomFallbackBuilder(paramContext).setStyle(paramResources).build();
    }
    catch (IOException paramContext) {}
    return null;
  }
  
  protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    throw new RuntimeException("Do not use this function in API 29 or later.");
  }
}
