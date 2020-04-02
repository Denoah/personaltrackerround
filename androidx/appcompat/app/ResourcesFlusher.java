package androidx.appcompat.app;

import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.LongSparseArray;
import java.lang.reflect.Field;
import java.util.Map;

class ResourcesFlusher
{
  private static final String TAG = "ResourcesFlusher";
  private static Field sDrawableCacheField;
  private static boolean sDrawableCacheFieldFetched;
  private static Field sResourcesImplField;
  private static boolean sResourcesImplFieldFetched;
  private static Class<?> sThemedResourceCacheClazz;
  private static boolean sThemedResourceCacheClazzFetched;
  private static Field sThemedResourceCache_mUnthemedEntriesField;
  private static boolean sThemedResourceCache_mUnthemedEntriesFieldFetched;
  
  private ResourcesFlusher() {}
  
  static void flush(Resources paramResources)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return;
    }
    if (Build.VERSION.SDK_INT >= 24) {
      flushNougats(paramResources);
    } else if (Build.VERSION.SDK_INT >= 23) {
      flushMarshmallows(paramResources);
    } else if (Build.VERSION.SDK_INT >= 21) {
      flushLollipops(paramResources);
    }
  }
  
  private static void flushLollipops(Resources paramResources)
  {
    if (!sDrawableCacheFieldFetched)
    {
      try
      {
        Field localField1 = Resources.class.getDeclaredField("mDrawableCache");
        sDrawableCacheField = localField1;
        localField1.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", localNoSuchFieldException);
      }
      sDrawableCacheFieldFetched = true;
    }
    Field localField2 = sDrawableCacheField;
    if (localField2 != null)
    {
      Object localObject = null;
      try
      {
        paramResources = (Map)localField2.get(paramResources);
      }
      catch (IllegalAccessException paramResources)
      {
        Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", paramResources);
        paramResources = localObject;
      }
      if (paramResources != null) {
        paramResources.clear();
      }
    }
  }
  
  private static void flushMarshmallows(Resources paramResources)
  {
    if (!sDrawableCacheFieldFetched)
    {
      try
      {
        Field localField1 = Resources.class.getDeclaredField("mDrawableCache");
        sDrawableCacheField = localField1;
        localField1.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", localNoSuchFieldException);
      }
      sDrawableCacheFieldFetched = true;
    }
    Object localObject2 = null;
    Field localField2 = sDrawableCacheField;
    Object localObject1 = localObject2;
    if (localField2 != null) {
      try
      {
        localObject1 = localField2.get(paramResources);
      }
      catch (IllegalAccessException paramResources)
      {
        Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", paramResources);
        localObject1 = localObject2;
      }
    }
    if (localObject1 == null) {
      return;
    }
    flushThemedResourcesCache(localObject1);
  }
  
  private static void flushNougats(Resources paramResources)
  {
    if (!sResourcesImplFieldFetched)
    {
      try
      {
        Field localField1 = Resources.class.getDeclaredField("mResourcesImpl");
        sResourcesImplField = localField1;
        localField1.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException1)
      {
        Log.e("ResourcesFlusher", "Could not retrieve Resources#mResourcesImpl field", localNoSuchFieldException1);
      }
      sResourcesImplFieldFetched = true;
    }
    Field localField2 = sResourcesImplField;
    if (localField2 == null) {
      return;
    }
    Object localObject2 = null;
    try
    {
      paramResources = localField2.get(paramResources);
    }
    catch (IllegalAccessException paramResources)
    {
      Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mResourcesImpl", paramResources);
      paramResources = null;
    }
    if (paramResources == null) {
      return;
    }
    if (!sDrawableCacheFieldFetched)
    {
      try
      {
        localField2 = paramResources.getClass().getDeclaredField("mDrawableCache");
        sDrawableCacheField = localField2;
        localField2.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException2)
      {
        Log.e("ResourcesFlusher", "Could not retrieve ResourcesImpl#mDrawableCache field", localNoSuchFieldException2);
      }
      sDrawableCacheFieldFetched = true;
    }
    Field localField3 = sDrawableCacheField;
    Object localObject1 = localObject2;
    if (localField3 != null) {
      try
      {
        localObject1 = localField3.get(paramResources);
      }
      catch (IllegalAccessException paramResources)
      {
        Log.e("ResourcesFlusher", "Could not retrieve value from ResourcesImpl#mDrawableCache", paramResources);
        localObject1 = localObject2;
      }
    }
    if (localObject1 != null) {
      flushThemedResourcesCache(localObject1);
    }
  }
  
  private static void flushThemedResourcesCache(Object paramObject)
  {
    if (!sThemedResourceCacheClazzFetched)
    {
      try
      {
        sThemedResourceCacheClazz = Class.forName("android.content.res.ThemedResourceCache");
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        Log.e("ResourcesFlusher", "Could not find ThemedResourceCache class", localClassNotFoundException);
      }
      sThemedResourceCacheClazzFetched = true;
    }
    Object localObject1 = sThemedResourceCacheClazz;
    if (localObject1 == null) {
      return;
    }
    if (!sThemedResourceCache_mUnthemedEntriesFieldFetched)
    {
      try
      {
        localObject1 = ((Class)localObject1).getDeclaredField("mUnthemedEntries");
        sThemedResourceCache_mUnthemedEntriesField = (Field)localObject1;
        ((Field)localObject1).setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        Log.e("ResourcesFlusher", "Could not retrieve ThemedResourceCache#mUnthemedEntries field", localNoSuchFieldException);
      }
      sThemedResourceCache_mUnthemedEntriesFieldFetched = true;
    }
    Field localField = sThemedResourceCache_mUnthemedEntriesField;
    if (localField == null) {
      return;
    }
    Object localObject2 = null;
    try
    {
      paramObject = (LongSparseArray)localField.get(paramObject);
    }
    catch (IllegalAccessException paramObject)
    {
      Log.e("ResourcesFlusher", "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", paramObject);
      paramObject = localObject2;
    }
    if (paramObject != null) {
      paramObject.clear();
    }
  }
}
