package androidx.core.graphics;

import android.graphics.Typeface;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.Log;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class TypefaceCompatApi21Impl
  extends TypefaceCompatBaseImpl
{
  private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
  private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
  private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
  private static final String TAG = "TypefaceCompatApi21Impl";
  private static Method sAddFontWeightStyle;
  private static Method sCreateFromFamiliesWithDefault;
  private static Class<?> sFontFamily;
  private static Constructor<?> sFontFamilyCtor;
  private static boolean sHasInitBeenCalled = false;
  
  TypefaceCompatApi21Impl() {}
  
  private static boolean addFontWeightStyle(Object paramObject, String paramString, int paramInt, boolean paramBoolean)
  {
    
    try
    {
      paramBoolean = ((Boolean)sAddFontWeightStyle.invoke(paramObject, new Object[] { paramString, Integer.valueOf(paramInt), Boolean.valueOf(paramBoolean) })).booleanValue();
      return paramBoolean;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    throw new RuntimeException(paramObject);
  }
  
  private static Typeface createFromFamiliesWithDefault(Object paramObject)
  {
    
    try
    {
      Object localObject = Array.newInstance(sFontFamily, 1);
      Array.set(localObject, 0, paramObject);
      paramObject = (Typeface)sCreateFromFamiliesWithDefault.invoke(null, new Object[] { localObject });
      return paramObject;
    }
    catch (InvocationTargetException paramObject) {}catch (IllegalAccessException paramObject) {}
    throw new RuntimeException(paramObject);
  }
  
  private File getFile(ParcelFileDescriptor paramParcelFileDescriptor)
  {
    try
    {
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      localStringBuilder.<init>();
      localStringBuilder.append("/proc/self/fd/");
      localStringBuilder.append(paramParcelFileDescriptor.getFd());
      paramParcelFileDescriptor = Os.readlink(localStringBuilder.toString());
      if (OsConstants.S_ISREG(Os.stat(paramParcelFileDescriptor).st_mode))
      {
        paramParcelFileDescriptor = new File(paramParcelFileDescriptor);
        return paramParcelFileDescriptor;
      }
    }
    catch (ErrnoException paramParcelFileDescriptor)
    {
      for (;;) {}
    }
    return null;
  }
  
  private static void init()
  {
    if (sHasInitBeenCalled) {
      return;
    }
    sHasInitBeenCalled = true;
    Object localObject1 = null;
    try
    {
      localObject2 = Class.forName("android.graphics.FontFamily");
      Constructor localConstructor = ((Class)localObject2).getConstructor(new Class[0]);
      localObject4 = ((Class)localObject2).getMethod("addFontWeightStyle", new Class[] { String.class, Integer.TYPE, Boolean.TYPE });
      localMethod = Typeface.class.getMethod("createFromFamiliesWithDefault", new Class[] { Array.newInstance((Class)localObject2, 1).getClass() });
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}catch (ClassNotFoundException localClassNotFoundException) {}
    Log.e("TypefaceCompatApi21Impl", localClassNotFoundException.getClass().getName(), localClassNotFoundException);
    Method localMethod = null;
    Object localObject3 = localMethod;
    Object localObject4 = localObject3;
    Object localObject2 = localObject3;
    localObject3 = localObject1;
    sFontFamilyCtor = localObject3;
    sFontFamily = (Class)localObject2;
    sAddFontWeightStyle = (Method)localObject4;
    sCreateFromFamiliesWithDefault = localMethod;
  }
  
  private static Object newFamily()
  {
    
    try
    {
      Object localObject = sFontFamilyCtor.newInstance(new Object[0]);
      return localObject;
    }
    catch (InvocationTargetException localInvocationTargetException) {}catch (InstantiationException localInstantiationException) {}catch (IllegalAccessException localIllegalAccessException) {}
    throw new RuntimeException(localIllegalAccessException);
  }
  
  /* Error */
  public Typeface createFromFontFamilyFilesResourceEntry(android.content.Context paramContext, androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, android.content.res.Resources paramResources, int paramInt)
  {
    // Byte code:
    //   0: invokestatic 197	androidx/core/graphics/TypefaceCompatApi21Impl:newFamily	()Ljava/lang/Object;
    //   3: astore 5
    //   5: aload_2
    //   6: invokevirtual 203	androidx/core/content/res/FontResourcesParserCompat$FontFamilyFilesResourceEntry:getEntries	()[Landroidx/core/content/res/FontResourcesParserCompat$FontFileResourceEntry;
    //   9: astore 6
    //   11: aload 6
    //   13: arraylength
    //   14: istore 7
    //   16: iconst_0
    //   17: istore 4
    //   19: iload 4
    //   21: iload 7
    //   23: if_icmpge +105 -> 128
    //   26: aload 6
    //   28: iload 4
    //   30: aaload
    //   31: astore 8
    //   33: aload_1
    //   34: invokestatic 209	androidx/core/graphics/TypefaceCompatUtil:getTempFile	(Landroid/content/Context;)Ljava/io/File;
    //   37: astore_2
    //   38: aload_2
    //   39: ifnonnull +5 -> 44
    //   42: aconst_null
    //   43: areturn
    //   44: aload_2
    //   45: aload_3
    //   46: aload 8
    //   48: invokevirtual 214	androidx/core/content/res/FontResourcesParserCompat$FontFileResourceEntry:getResourceId	()I
    //   51: invokestatic 218	androidx/core/graphics/TypefaceCompatUtil:copyToFile	(Ljava/io/File;Landroid/content/res/Resources;I)Z
    //   54: istore 9
    //   56: iload 9
    //   58: ifne +10 -> 68
    //   61: aload_2
    //   62: invokevirtual 221	java/io/File:delete	()Z
    //   65: pop
    //   66: aconst_null
    //   67: areturn
    //   68: aload 5
    //   70: aload_2
    //   71: invokevirtual 224	java/io/File:getPath	()Ljava/lang/String;
    //   74: aload 8
    //   76: invokevirtual 227	androidx/core/content/res/FontResourcesParserCompat$FontFileResourceEntry:getWeight	()I
    //   79: aload 8
    //   81: invokevirtual 230	androidx/core/content/res/FontResourcesParserCompat$FontFileResourceEntry:isItalic	()Z
    //   84: invokestatic 232	androidx/core/graphics/TypefaceCompatApi21Impl:addFontWeightStyle	(Ljava/lang/Object;Ljava/lang/String;IZ)Z
    //   87: istore 9
    //   89: iload 9
    //   91: ifne +10 -> 101
    //   94: aload_2
    //   95: invokevirtual 221	java/io/File:delete	()Z
    //   98: pop
    //   99: aconst_null
    //   100: areturn
    //   101: aload_2
    //   102: invokevirtual 221	java/io/File:delete	()Z
    //   105: pop
    //   106: iinc 4 1
    //   109: goto -90 -> 19
    //   112: astore_1
    //   113: aload_2
    //   114: invokevirtual 221	java/io/File:delete	()Z
    //   117: pop
    //   118: aload_1
    //   119: athrow
    //   120: astore_1
    //   121: aload_2
    //   122: invokevirtual 221	java/io/File:delete	()Z
    //   125: pop
    //   126: aconst_null
    //   127: areturn
    //   128: aload 5
    //   130: invokestatic 234	androidx/core/graphics/TypefaceCompatApi21Impl:createFromFamiliesWithDefault	(Ljava/lang/Object;)Landroid/graphics/Typeface;
    //   133: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	134	0	this	TypefaceCompatApi21Impl
    //   0	134	1	paramContext	android.content.Context
    //   0	134	2	paramFontFamilyFilesResourceEntry	androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry
    //   0	134	3	paramResources	android.content.res.Resources
    //   0	134	4	paramInt	int
    //   3	126	5	localObject	Object
    //   9	18	6	arrayOfFontFileResourceEntry	androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry[]
    //   14	10	7	i	int
    //   31	49	8	localFontFileResourceEntry	androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry
    //   54	36	9	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   44	56	112	finally
    //   68	89	112	finally
    //   44	56	120	java/lang/RuntimeException
    //   68	89	120	java/lang/RuntimeException
  }
  
  /* Error */
  public Typeface createFromFontInfo(android.content.Context paramContext, android.os.CancellationSignal paramCancellationSignal, androidx.core.provider.FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    // Byte code:
    //   0: aload_3
    //   1: arraylength
    //   2: iconst_1
    //   3: if_icmpge +5 -> 8
    //   6: aconst_null
    //   7: areturn
    //   8: aload_0
    //   9: aload_3
    //   10: iload 4
    //   12: invokevirtual 242	androidx/core/graphics/TypefaceCompatApi21Impl:findBestInfo	([Landroidx/core/provider/FontsContractCompat$FontInfo;I)Landroidx/core/provider/FontsContractCompat$FontInfo;
    //   15: astore_3
    //   16: aload_1
    //   17: invokevirtual 248	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   20: astore 5
    //   22: aload 5
    //   24: aload_3
    //   25: invokevirtual 254	androidx/core/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   28: ldc_w 256
    //   31: aload_2
    //   32: invokevirtual 262	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   35: astore_2
    //   36: aload_2
    //   37: ifnonnull +13 -> 50
    //   40: aload_2
    //   41: ifnull +7 -> 48
    //   44: aload_2
    //   45: invokevirtual 265	android/os/ParcelFileDescriptor:close	()V
    //   48: aconst_null
    //   49: areturn
    //   50: aload_0
    //   51: aload_2
    //   52: invokespecial 267	androidx/core/graphics/TypefaceCompatApi21Impl:getFile	(Landroid/os/ParcelFileDescriptor;)Ljava/io/File;
    //   55: astore_3
    //   56: aload_3
    //   57: ifnull +28 -> 85
    //   60: aload_3
    //   61: invokevirtual 270	java/io/File:canRead	()Z
    //   64: ifne +6 -> 70
    //   67: goto +18 -> 85
    //   70: aload_3
    //   71: invokestatic 274	android/graphics/Typeface:createFromFile	(Ljava/io/File;)Landroid/graphics/Typeface;
    //   74: astore_1
    //   75: aload_2
    //   76: ifnull +7 -> 83
    //   79: aload_2
    //   80: invokevirtual 265	android/os/ParcelFileDescriptor:close	()V
    //   83: aload_1
    //   84: areturn
    //   85: new 276	java/io/FileInputStream
    //   88: astore_3
    //   89: aload_3
    //   90: aload_2
    //   91: invokevirtual 280	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   94: invokespecial 283	java/io/FileInputStream:<init>	(Ljava/io/FileDescriptor;)V
    //   97: aload_0
    //   98: aload_1
    //   99: aload_3
    //   100: invokespecial 287	androidx/core/graphics/TypefaceCompatBaseImpl:createFromInputStream	(Landroid/content/Context;Ljava/io/InputStream;)Landroid/graphics/Typeface;
    //   103: astore_1
    //   104: aload_3
    //   105: invokevirtual 288	java/io/FileInputStream:close	()V
    //   108: aload_2
    //   109: ifnull +7 -> 116
    //   112: aload_2
    //   113: invokevirtual 265	android/os/ParcelFileDescriptor:close	()V
    //   116: aload_1
    //   117: areturn
    //   118: astore 5
    //   120: aload 5
    //   122: athrow
    //   123: astore_1
    //   124: aload_3
    //   125: invokevirtual 288	java/io/FileInputStream:close	()V
    //   128: goto +10 -> 138
    //   131: astore_3
    //   132: aload 5
    //   134: aload_3
    //   135: invokevirtual 293	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   138: aload_1
    //   139: athrow
    //   140: astore_3
    //   141: aload_3
    //   142: athrow
    //   143: astore_1
    //   144: aload_2
    //   145: ifnull +16 -> 161
    //   148: aload_2
    //   149: invokevirtual 265	android/os/ParcelFileDescriptor:close	()V
    //   152: goto +9 -> 161
    //   155: astore_2
    //   156: aload_3
    //   157: aload_2
    //   158: invokevirtual 293	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   161: aload_1
    //   162: athrow
    //   163: astore_1
    //   164: aconst_null
    //   165: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	166	0	this	TypefaceCompatApi21Impl
    //   0	166	1	paramContext	android.content.Context
    //   0	166	2	paramCancellationSignal	android.os.CancellationSignal
    //   0	166	3	paramArrayOfFontInfo	androidx.core.provider.FontsContractCompat.FontInfo[]
    //   0	166	4	paramInt	int
    //   20	3	5	localContentResolver	android.content.ContentResolver
    //   118	15	5	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   97	104	118	finally
    //   120	123	123	finally
    //   124	128	131	finally
    //   50	56	140	finally
    //   60	67	140	finally
    //   70	75	140	finally
    //   85	97	140	finally
    //   104	108	140	finally
    //   132	138	140	finally
    //   138	140	140	finally
    //   141	143	143	finally
    //   148	152	155	finally
    //   22	36	163	java/io/IOException
    //   44	48	163	java/io/IOException
    //   79	83	163	java/io/IOException
    //   112	116	163	java/io/IOException
    //   156	161	163	java/io/IOException
    //   161	163	163	java/io/IOException
  }
}
