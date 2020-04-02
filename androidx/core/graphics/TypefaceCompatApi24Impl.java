package androidx.core.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.collection.SimpleArrayMap;
import androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry;
import androidx.core.content.res.FontResourcesParserCompat.FontFileResourceEntry;
import androidx.core.provider.FontsContractCompat.FontInfo;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

class TypefaceCompatApi24Impl
  extends TypefaceCompatBaseImpl
{
  private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
  private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
  private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
  private static final String TAG = "TypefaceCompatApi24Impl";
  private static final Method sAddFontWeightStyle;
  private static final Method sCreateFromFamiliesWithDefault;
  private static final Class<?> sFontFamily;
  private static final Constructor<?> sFontFamilyCtor;
  
  static
  {
    Object localObject1 = null;
    try
    {
      Class localClass = Class.forName("android.graphics.FontFamily");
      localObject3 = localClass.getConstructor(new Class[0]);
      localObject4 = localClass.getMethod("addFontWeightStyle", new Class[] { ByteBuffer.class, Integer.TYPE, List.class, Integer.TYPE, Boolean.TYPE });
      localObject5 = Typeface.class.getMethod("createFromFamiliesWithDefault", new Class[] { Array.newInstance(localClass, 1).getClass() });
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}catch (ClassNotFoundException localClassNotFoundException) {}
    Log.e("TypefaceCompatApi24Impl", localClassNotFoundException.getClass().getName(), localClassNotFoundException);
    Object localObject3 = null;
    Object localObject2 = localObject3;
    Object localObject4 = localObject2;
    Object localObject5 = localObject2;
    localObject2 = localObject3;
    localObject3 = localObject1;
    sFontFamilyCtor = (Constructor)localObject3;
    sFontFamily = localObject2;
    sAddFontWeightStyle = (Method)localObject4;
    sCreateFromFamiliesWithDefault = (Method)localObject5;
  }
  
  TypefaceCompatApi24Impl() {}
  
  private static boolean addFontWeightStyle(Object paramObject, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    try
    {
      paramBoolean = ((Boolean)sAddFontWeightStyle.invoke(paramObject, new Object[] { paramByteBuffer, Integer.valueOf(paramInt1), null, Integer.valueOf(paramInt2), Boolean.valueOf(paramBoolean) })).booleanValue();
      return paramBoolean;
    }
    catch (IllegalAccessException|InvocationTargetException paramObject) {}
    return false;
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
    catch (IllegalAccessException|InvocationTargetException paramObject) {}
    return null;
  }
  
  public static boolean isUsable()
  {
    if (sAddFontWeightStyle == null) {
      Log.w("TypefaceCompatApi24Impl", "Unable to collect necessary private methods.Fallback to legacy implementation.");
    }
    boolean bool;
    if (sAddFontWeightStyle != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static Object newFamily()
  {
    try
    {
      Object localObject = sFontFamilyCtor.newInstance(new Object[0]);
      return localObject;
    }
    catch (IllegalAccessException|InstantiationException|InvocationTargetException localIllegalAccessException) {}
    return null;
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt)
  {
    Object localObject1 = newFamily();
    if (localObject1 == null) {
      return null;
    }
    for (Object localObject2 : paramFontFamilyFilesResourceEntry.getEntries())
    {
      ByteBuffer localByteBuffer = TypefaceCompatUtil.copyToDirectBuffer(paramContext, paramResources, localObject2.getResourceId());
      if (localByteBuffer == null) {
        return null;
      }
      if (!addFontWeightStyle(localObject1, localByteBuffer, localObject2.getTtcIndex(), localObject2.getWeight(), localObject2.isItalic())) {
        return null;
      }
    }
    return createFromFamiliesWithDefault(localObject1);
  }
  
  public Typeface createFromFontInfo(Context paramContext, CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt)
  {
    Object localObject = newFamily();
    if (localObject == null) {
      return null;
    }
    SimpleArrayMap localSimpleArrayMap = new SimpleArrayMap();
    int i = paramArrayOfFontInfo.length;
    for (int j = 0; j < i; j++)
    {
      FontsContractCompat.FontInfo localFontInfo = paramArrayOfFontInfo[j];
      Uri localUri = localFontInfo.getUri();
      ByteBuffer localByteBuffer1 = (ByteBuffer)localSimpleArrayMap.get(localUri);
      ByteBuffer localByteBuffer2 = localByteBuffer1;
      if (localByteBuffer1 == null)
      {
        localByteBuffer2 = TypefaceCompatUtil.mmap(paramContext, paramCancellationSignal, localUri);
        localSimpleArrayMap.put(localUri, localByteBuffer2);
      }
      if (localByteBuffer2 == null) {
        return null;
      }
      if (!addFontWeightStyle(localObject, localByteBuffer2, localFontInfo.getTtcIndex(), localFontInfo.getWeight(), localFontInfo.isItalic())) {
        return null;
      }
    }
    paramContext = createFromFamiliesWithDefault(localObject);
    if (paramContext == null) {
      return null;
    }
    return Typeface.create(paramContext, paramInt);
  }
}
