package androidx.core.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.util.Log;
import androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class TypefaceCompatApi26Impl
  extends TypefaceCompatApi21Impl
{
  private static final String ABORT_CREATION_METHOD = "abortCreation";
  private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
  private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
  private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
  private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
  private static final String FREEZE_METHOD = "freeze";
  private static final int RESOLVE_BY_FONT_TABLE = -1;
  private static final String TAG = "TypefaceCompatApi26Impl";
  protected final Method mAbortCreation;
  protected final Method mAddFontFromAssetManager;
  protected final Method mAddFontFromBuffer;
  protected final Method mCreateFromFamiliesWithDefault;
  protected final Class<?> mFontFamily;
  protected final Constructor<?> mFontFamilyCtor;
  protected final Method mFreeze;
  
  public TypefaceCompatApi26Impl()
  {
    Object localObject1 = null;
    try
    {
      localObject2 = obtainFontFamily();
      Constructor localConstructor = obtainFontFamilyCtor((Class)localObject2);
      localObject4 = obtainAddFontFromAssetManagerMethod((Class)localObject2);
      localObject5 = obtainAddFontFromBufferMethod((Class)localObject2);
      localObject6 = obtainFreezeMethod((Class)localObject2);
      localObject7 = obtainAbortCreationMethod((Class)localObject2);
      localMethod = obtainCreateFromFamiliesWithDefaultMethod((Class)localObject2);
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}catch (ClassNotFoundException localClassNotFoundException) {}
    Object localObject4 = new StringBuilder();
    ((StringBuilder)localObject4).append("Unable to collect necessary methods for class ");
    ((StringBuilder)localObject4).append(localClassNotFoundException.getClass().getName());
    Log.e("TypefaceCompatApi26Impl", ((StringBuilder)localObject4).toString(), localClassNotFoundException);
    Method localMethod = null;
    Object localObject3 = localMethod;
    localObject4 = localObject3;
    Object localObject5 = localObject4;
    Object localObject2 = localObject5;
    Object localObject7 = localObject2;
    Object localObject6 = localObject2;
    localObject2 = localObject1;
    this.mFontFamily = ((Class)localObject2);
    this.mFontFamilyCtor = localObject3;
    this.mAddFontFromAssetManager = ((Method)localObject4);
    this.mAddFontFromBuffer = ((Method)localObject5);
    this.mFreeze = ((Method)localObject6);
    this.mAbortCreation = ((Method)localObject7);
    this.mCreateFromFamiliesWithDefault = localMethod;
  }
  
  private void abortCreation(Object paramObject)
  {
    try
    {
      this.mAbortCreation.invoke(paramObject, new Object[0]);
      return;
    }
    catch (IllegalAccessException|InvocationTargetException paramObject)
    {
      for (;;) {}
    }
  }
  
  private boolean addFontFromAssetManager(Context paramContext, Object paramObject, String paramString, int paramInt1, int paramInt2, int paramInt3, FontVariationAxis[] paramArrayOfFontVariationAxis)
  {
    try
    {
      boolean bool = ((Boolean)this.mAddFontFromAssetManager.invoke(paramObject, new Object[] { paramContext.getAssets(), paramString, Integer.valueOf(0), Boolean.valueOf(false), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), paramArrayOfFontVariationAxis })).booleanValue();
      return bool;
    }
    catch (IllegalAccessException|InvocationTargetException paramContext) {}
    return false;
  }
  
  private boolean addFontFromBuffer(Object paramObject, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      boolean bool = ((Boolean)this.mAddFontFromBuffer.invoke(paramObject, new Object[] { paramByteBuffer, Integer.valueOf(paramInt1), null, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) })).booleanValue();
      return bool;
    }
    catch (IllegalAccessException|InvocationTargetException paramObject) {}
    return false;
  }
  
  private boolean freeze(Object paramObject)
  {
    try
    {
      boolean bool = ((Boolean)this.mFreeze.invoke(paramObject, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalAccessException|InvocationTargetException paramObject) {}
    return false;
  }
  
  private boolean isFontFamilyPrivateAPIAvailable()
  {
    if (this.mAddFontFromAssetManager == null) {
      Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods. Fallback to legacy implementation.");
    }
    boolean bool;
    if (this.mAddFontFromAssetManager != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private Object newFamily()
  {
    try
    {
      Object localObject = this.mFontFamilyCtor.newInstance(new Object[0]);
      return localObject;
    }
    catch (IllegalAccessException|InstantiationException|InvocationTargetException localIllegalAccessException) {}
    return null;
  }
  
  protected Typeface createFromFamiliesWithDefault(Object paramObject)
  {
    try
    {
      Object localObject = Array.newInstance(this.mFontFamily, 1);
      Array.set(localObject, 0, paramObject);
      paramObject = (Typeface)this.mCreateFromFamiliesWithDefault.invoke(null, new Object[] { localObject, Integer.valueOf(-1), Integer.valueOf(-1) });
      return paramObject;
    }
    catch (IllegalAccessException|InvocationTargetException paramObject) {}
    return null;
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt)
  {
    if (!isFontFamilyPrivateAPIAvailable()) {
      return super.createFromFontFamilyFilesResourceEntry(paramContext, paramFontFamilyFilesResourceEntry, paramResources, paramInt);
    }
    paramResources = newFamily();
    if (paramResources == null) {
      return null;
    }
    for (Object localObject : paramFontFamilyFilesResourceEntry.getEntries()) {
      if (!addFontFromAssetManager(paramContext, paramResources, localObject.getFileName(), localObject.getTtcIndex(), localObject.getWeight(), localObject.isItalic(), FontVariationAxis.fromFontVariationSettings(localObject.getVariationSettings())))
      {
        abortCreation(paramResources);
        return null;
      }
    }
    if (!freeze(paramResources)) {
      return null;
    }
    return createFromFamiliesWithDefault(paramResources);
  }
  
  /* Error */
  public Typeface createFromFontInfo(Context paramContext, android.os.CancellationSignal paramCancellationSignal, androidx.core.provider.FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    // Byte code:
    //   0: aload_3
    //   1: arraylength
    //   2: iconst_1
    //   3: if_icmpge +5 -> 8
    //   6: aconst_null
    //   7: areturn
    //   8: aload_0
    //   9: invokespecial 185	androidx/core/graphics/TypefaceCompatApi26Impl:isFontFamilyPrivateAPIAvailable	()Z
    //   12: ifne +109 -> 121
    //   15: aload_0
    //   16: aload_3
    //   17: iload 4
    //   19: invokevirtual 235	androidx/core/graphics/TypefaceCompatApi26Impl:findBestInfo	([Landroidx/core/provider/FontsContractCompat$FontInfo;I)Landroidx/core/provider/FontsContractCompat$FontInfo;
    //   22: astore_3
    //   23: aload_1
    //   24: invokevirtual 239	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   27: astore_1
    //   28: aload_1
    //   29: aload_3
    //   30: invokevirtual 245	androidx/core/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   33: ldc -9
    //   35: aload_2
    //   36: invokevirtual 253	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;Landroid/os/CancellationSignal;)Landroid/os/ParcelFileDescriptor;
    //   39: astore_1
    //   40: aload_1
    //   41: ifnonnull +13 -> 54
    //   44: aload_1
    //   45: ifnull +7 -> 52
    //   48: aload_1
    //   49: invokevirtual 258	android/os/ParcelFileDescriptor:close	()V
    //   52: aconst_null
    //   53: areturn
    //   54: new 260	android/graphics/Typeface$Builder
    //   57: astore_2
    //   58: aload_2
    //   59: aload_1
    //   60: invokevirtual 264	android/os/ParcelFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   63: invokespecial 267	android/graphics/Typeface$Builder:<init>	(Ljava/io/FileDescriptor;)V
    //   66: aload_2
    //   67: aload_3
    //   68: invokevirtual 268	androidx/core/provider/FontsContractCompat$FontInfo:getWeight	()I
    //   71: invokevirtual 272	android/graphics/Typeface$Builder:setWeight	(I)Landroid/graphics/Typeface$Builder;
    //   74: aload_3
    //   75: invokevirtual 273	androidx/core/provider/FontsContractCompat$FontInfo:isItalic	()Z
    //   78: invokevirtual 277	android/graphics/Typeface$Builder:setItalic	(Z)Landroid/graphics/Typeface$Builder;
    //   81: invokevirtual 281	android/graphics/Typeface$Builder:build	()Landroid/graphics/Typeface;
    //   84: astore_2
    //   85: aload_1
    //   86: ifnull +7 -> 93
    //   89: aload_1
    //   90: invokevirtual 258	android/os/ParcelFileDescriptor:close	()V
    //   93: aload_2
    //   94: areturn
    //   95: astore_3
    //   96: aload_3
    //   97: athrow
    //   98: astore_2
    //   99: aload_1
    //   100: ifnull +16 -> 116
    //   103: aload_1
    //   104: invokevirtual 258	android/os/ParcelFileDescriptor:close	()V
    //   107: goto +9 -> 116
    //   110: astore_1
    //   111: aload_3
    //   112: aload_1
    //   113: invokevirtual 287	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   116: aload_2
    //   117: athrow
    //   118: astore_1
    //   119: aconst_null
    //   120: areturn
    //   121: aload_1
    //   122: aload_3
    //   123: aload_2
    //   124: invokestatic 293	androidx/core/provider/FontsContractCompat:prepareFontData	(Landroid/content/Context;[Landroidx/core/provider/FontsContractCompat$FontInfo;Landroid/os/CancellationSignal;)Ljava/util/Map;
    //   127: astore 5
    //   129: aload_0
    //   130: invokespecial 189	androidx/core/graphics/TypefaceCompatApi26Impl:newFamily	()Ljava/lang/Object;
    //   133: astore 6
    //   135: aload 6
    //   137: ifnonnull +5 -> 142
    //   140: aconst_null
    //   141: areturn
    //   142: aload_3
    //   143: arraylength
    //   144: istore 7
    //   146: iconst_0
    //   147: istore 8
    //   149: iconst_0
    //   150: istore 9
    //   152: iload 9
    //   154: iload 7
    //   156: if_icmpge +69 -> 225
    //   159: aload_3
    //   160: iload 9
    //   162: aaload
    //   163: astore_1
    //   164: aload 5
    //   166: aload_1
    //   167: invokevirtual 245	androidx/core/provider/FontsContractCompat$FontInfo:getUri	()Landroid/net/Uri;
    //   170: invokeinterface 299 2 0
    //   175: checkcast 301	java/nio/ByteBuffer
    //   178: astore_2
    //   179: aload_2
    //   180: ifnonnull +6 -> 186
    //   183: goto +36 -> 219
    //   186: aload_0
    //   187: aload 6
    //   189: aload_2
    //   190: aload_1
    //   191: invokevirtual 302	androidx/core/provider/FontsContractCompat$FontInfo:getTtcIndex	()I
    //   194: aload_1
    //   195: invokevirtual 268	androidx/core/provider/FontsContractCompat$FontInfo:getWeight	()I
    //   198: aload_1
    //   199: invokevirtual 273	androidx/core/provider/FontsContractCompat$FontInfo:isItalic	()Z
    //   202: invokespecial 304	androidx/core/graphics/TypefaceCompatApi26Impl:addFontFromBuffer	(Ljava/lang/Object;Ljava/nio/ByteBuffer;III)Z
    //   205: ifne +11 -> 216
    //   208: aload_0
    //   209: aload 6
    //   211: invokespecial 223	androidx/core/graphics/TypefaceCompatApi26Impl:abortCreation	(Ljava/lang/Object;)V
    //   214: aconst_null
    //   215: areturn
    //   216: iconst_1
    //   217: istore 8
    //   219: iinc 9 1
    //   222: goto -70 -> 152
    //   225: iload 8
    //   227: ifne +11 -> 238
    //   230: aload_0
    //   231: aload 6
    //   233: invokespecial 223	androidx/core/graphics/TypefaceCompatApi26Impl:abortCreation	(Ljava/lang/Object;)V
    //   236: aconst_null
    //   237: areturn
    //   238: aload_0
    //   239: aload 6
    //   241: invokespecial 225	androidx/core/graphics/TypefaceCompatApi26Impl:freeze	(Ljava/lang/Object;)Z
    //   244: ifne +5 -> 249
    //   247: aconst_null
    //   248: areturn
    //   249: aload_0
    //   250: aload 6
    //   252: invokevirtual 227	androidx/core/graphics/TypefaceCompatApi26Impl:createFromFamiliesWithDefault	(Ljava/lang/Object;)Landroid/graphics/Typeface;
    //   255: astore_1
    //   256: aload_1
    //   257: ifnonnull +5 -> 262
    //   260: aconst_null
    //   261: areturn
    //   262: aload_1
    //   263: iload 4
    //   265: invokestatic 308	android/graphics/Typeface:create	(Landroid/graphics/Typeface;I)Landroid/graphics/Typeface;
    //   268: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	269	0	this	TypefaceCompatApi26Impl
    //   0	269	1	paramContext	Context
    //   0	269	2	paramCancellationSignal	android.os.CancellationSignal
    //   0	269	3	paramArrayOfFontInfo	androidx.core.provider.FontsContractCompat.FontInfo[]
    //   0	269	4	paramInt	int
    //   127	38	5	localMap	java.util.Map
    //   133	118	6	localObject	Object
    //   144	13	7	i	int
    //   147	79	8	j	int
    //   150	70	9	k	int
    // Exception table:
    //   from	to	target	type
    //   54	85	95	finally
    //   96	98	98	finally
    //   103	107	110	finally
    //   28	40	118	java/io/IOException
    //   48	52	118	java/io/IOException
    //   89	93	118	java/io/IOException
    //   111	116	118	java/io/IOException
    //   116	118	118	java/io/IOException
  }
  
  public Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2)
  {
    if (!isFontFamilyPrivateAPIAvailable()) {
      return super.createFromResourcesFontFile(paramContext, paramResources, paramInt1, paramString, paramInt2);
    }
    paramResources = newFamily();
    if (paramResources == null) {
      return null;
    }
    if (!addFontFromAssetManager(paramContext, paramResources, paramString, 0, -1, -1, null))
    {
      abortCreation(paramResources);
      return null;
    }
    if (!freeze(paramResources)) {
      return null;
    }
    return createFromFamiliesWithDefault(paramResources);
  }
  
  protected Method obtainAbortCreationMethod(Class<?> paramClass)
    throws NoSuchMethodException
  {
    return paramClass.getMethod("abortCreation", new Class[0]);
  }
  
  protected Method obtainAddFontFromAssetManagerMethod(Class<?> paramClass)
    throws NoSuchMethodException
  {
    return paramClass.getMethod("addFontFromAssetManager", new Class[] { AssetManager.class, String.class, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, [Landroid.graphics.fonts.FontVariationAxis.class });
  }
  
  protected Method obtainAddFontFromBufferMethod(Class<?> paramClass)
    throws NoSuchMethodException
  {
    return paramClass.getMethod("addFontFromBuffer", new Class[] { ByteBuffer.class, Integer.TYPE, [Landroid.graphics.fonts.FontVariationAxis.class, Integer.TYPE, Integer.TYPE });
  }
  
  protected Method obtainCreateFromFamiliesWithDefaultMethod(Class<?> paramClass)
    throws NoSuchMethodException
  {
    paramClass = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", new Class[] { Array.newInstance(paramClass, 1).getClass(), Integer.TYPE, Integer.TYPE });
    paramClass.setAccessible(true);
    return paramClass;
  }
  
  protected Class<?> obtainFontFamily()
    throws ClassNotFoundException
  {
    return Class.forName("android.graphics.FontFamily");
  }
  
  protected Constructor<?> obtainFontFamilyCtor(Class<?> paramClass)
    throws NoSuchMethodException
  {
    return paramClass.getConstructor(new Class[0]);
  }
  
  protected Method obtainFreezeMethod(Class<?> paramClass)
    throws NoSuchMethodException
  {
    return paramClass.getMethod("freeze", new Class[0]);
  }
}
