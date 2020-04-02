package androidx.core.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import androidx.core.graphics.TypefaceCompat;
import androidx.core.util.Preconditions;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParserException;

public final class ResourcesCompat
{
  public static final int ID_NULL = 0;
  private static final String TAG = "ResourcesCompat";
  
  private ResourcesCompat() {}
  
  public static int getColor(Resources paramResources, int paramInt, Resources.Theme paramTheme)
    throws Resources.NotFoundException
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramResources.getColor(paramInt, paramTheme);
    }
    return paramResources.getColor(paramInt);
  }
  
  public static ColorStateList getColorStateList(Resources paramResources, int paramInt, Resources.Theme paramTheme)
    throws Resources.NotFoundException
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramResources.getColorStateList(paramInt, paramTheme);
    }
    return paramResources.getColorStateList(paramInt);
  }
  
  public static Drawable getDrawable(Resources paramResources, int paramInt, Resources.Theme paramTheme)
    throws Resources.NotFoundException
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return paramResources.getDrawable(paramInt, paramTheme);
    }
    return paramResources.getDrawable(paramInt);
  }
  
  public static Drawable getDrawableForDensity(Resources paramResources, int paramInt1, int paramInt2, Resources.Theme paramTheme)
    throws Resources.NotFoundException
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return paramResources.getDrawableForDensity(paramInt1, paramInt2, paramTheme);
    }
    if (Build.VERSION.SDK_INT >= 15) {
      return paramResources.getDrawableForDensity(paramInt1, paramInt2);
    }
    return paramResources.getDrawable(paramInt1);
  }
  
  public static float getFloat(Resources paramResources, int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    paramResources.getValue(paramInt, localTypedValue, true);
    if (localTypedValue.type == 4) {
      return localTypedValue.getFloat();
    }
    paramResources = new StringBuilder();
    paramResources.append("Resource ID #0x");
    paramResources.append(Integer.toHexString(paramInt));
    paramResources.append(" type #0x");
    paramResources.append(Integer.toHexString(localTypedValue.type));
    paramResources.append(" is not valid");
    throw new Resources.NotFoundException(paramResources.toString());
  }
  
  public static Typeface getFont(Context paramContext, int paramInt)
    throws Resources.NotFoundException
  {
    if (paramContext.isRestricted()) {
      return null;
    }
    return loadFont(paramContext, paramInt, new TypedValue(), 0, null, null, false);
  }
  
  public static Typeface getFont(Context paramContext, int paramInt1, TypedValue paramTypedValue, int paramInt2, FontCallback paramFontCallback)
    throws Resources.NotFoundException
  {
    if (paramContext.isRestricted()) {
      return null;
    }
    return loadFont(paramContext, paramInt1, paramTypedValue, paramInt2, paramFontCallback, null, true);
  }
  
  public static void getFont(Context paramContext, int paramInt, FontCallback paramFontCallback, Handler paramHandler)
    throws Resources.NotFoundException
  {
    Preconditions.checkNotNull(paramFontCallback);
    if (paramContext.isRestricted())
    {
      paramFontCallback.callbackFailAsync(-4, paramHandler);
      return;
    }
    loadFont(paramContext, paramInt, new TypedValue(), 0, paramFontCallback, paramHandler, false);
  }
  
  private static Typeface loadFont(Context paramContext, int paramInt1, TypedValue paramTypedValue, int paramInt2, FontCallback paramFontCallback, Handler paramHandler, boolean paramBoolean)
  {
    Resources localResources = paramContext.getResources();
    localResources.getValue(paramInt1, paramTypedValue, true);
    paramContext = loadFont(paramContext, localResources, paramTypedValue, paramInt1, paramInt2, paramFontCallback, paramHandler, paramBoolean);
    if ((paramContext == null) && (paramFontCallback == null))
    {
      paramContext = new StringBuilder();
      paramContext.append("Font resource ID #0x");
      paramContext.append(Integer.toHexString(paramInt1));
      paramContext.append(" could not be retrieved.");
      throw new Resources.NotFoundException(paramContext.toString());
    }
    return paramContext;
  }
  
  private static Typeface loadFont(Context paramContext, Resources paramResources, TypedValue paramTypedValue, int paramInt1, int paramInt2, FontCallback paramFontCallback, Handler paramHandler, boolean paramBoolean)
  {
    if (paramTypedValue.string != null)
    {
      paramTypedValue = paramTypedValue.string.toString();
      if (!paramTypedValue.startsWith("res/"))
      {
        if (paramFontCallback != null) {
          paramFontCallback.callbackFailAsync(-3, paramHandler);
        }
        return null;
      }
      Object localObject = TypefaceCompat.findFromCache(paramResources, paramInt1, paramInt2);
      if (localObject != null)
      {
        if (paramFontCallback != null) {
          paramFontCallback.callbackSuccessAsync((Typeface)localObject, paramHandler);
        }
        return localObject;
      }
      try
      {
        if (paramTypedValue.toLowerCase().endsWith(".xml"))
        {
          localObject = FontResourcesParserCompat.parse(paramResources.getXml(paramInt1), paramResources);
          if (localObject == null)
          {
            Log.e("ResourcesCompat", "Failed to find font-family tag");
            if (paramFontCallback != null) {
              paramFontCallback.callbackFailAsync(-3, paramHandler);
            }
            return null;
          }
          return TypefaceCompat.createFromResourcesFamilyXml(paramContext, (FontResourcesParserCompat.FamilyResourceEntry)localObject, paramResources, paramInt1, paramInt2, paramFontCallback, paramHandler, paramBoolean);
        }
        paramContext = TypefaceCompat.createFromResourcesFontFile(paramContext, paramResources, paramInt1, paramTypedValue, paramInt2);
        if (paramFontCallback != null) {
          if (paramContext != null) {
            paramFontCallback.callbackSuccessAsync(paramContext, paramHandler);
          } else {
            paramFontCallback.callbackFailAsync(-3, paramHandler);
          }
        }
        return paramContext;
      }
      catch (IOException paramContext)
      {
        paramResources = new StringBuilder();
        paramResources.append("Failed to read xml resource ");
        paramResources.append(paramTypedValue);
        Log.e("ResourcesCompat", paramResources.toString(), paramContext);
      }
      catch (XmlPullParserException paramResources)
      {
        paramContext = new StringBuilder();
        paramContext.append("Failed to parse xml resource ");
        paramContext.append(paramTypedValue);
        Log.e("ResourcesCompat", paramContext.toString(), paramResources);
      }
      if (paramFontCallback != null) {
        paramFontCallback.callbackFailAsync(-3, paramHandler);
      }
      return null;
    }
    paramContext = new StringBuilder();
    paramContext.append("Resource \"");
    paramContext.append(paramResources.getResourceName(paramInt1));
    paramContext.append("\" (");
    paramContext.append(Integer.toHexString(paramInt1));
    paramContext.append(") is not a Font: ");
    paramContext.append(paramTypedValue);
    throw new Resources.NotFoundException(paramContext.toString());
  }
  
  public static abstract class FontCallback
  {
    public FontCallback() {}
    
    public final void callbackFailAsync(final int paramInt, Handler paramHandler)
    {
      Handler localHandler = paramHandler;
      if (paramHandler == null) {
        localHandler = new Handler(Looper.getMainLooper());
      }
      localHandler.post(new Runnable()
      {
        public void run()
        {
          ResourcesCompat.FontCallback.this.onFontRetrievalFailed(paramInt);
        }
      });
    }
    
    public final void callbackSuccessAsync(final Typeface paramTypeface, Handler paramHandler)
    {
      Handler localHandler = paramHandler;
      if (paramHandler == null) {
        localHandler = new Handler(Looper.getMainLooper());
      }
      localHandler.post(new Runnable()
      {
        public void run()
        {
          ResourcesCompat.FontCallback.this.onFontRetrieved(paramTypeface);
        }
      });
    }
    
    public abstract void onFontRetrievalFailed(int paramInt);
    
    public abstract void onFontRetrieved(Typeface paramTypeface);
  }
  
  public static final class ThemeCompat
  {
    private ThemeCompat() {}
    
    public static void rebase(Resources.Theme paramTheme)
    {
      if (Build.VERSION.SDK_INT >= 29) {
        ImplApi29.rebase(paramTheme);
      } else if (Build.VERSION.SDK_INT >= 23) {
        ImplApi23.rebase(paramTheme);
      }
    }
    
    static class ImplApi23
    {
      private static Method sRebaseMethod;
      private static boolean sRebaseMethodFetched;
      private static final Object sRebaseMethodLock = new Object();
      
      private ImplApi23() {}
      
      static void rebase(Resources.Theme paramTheme)
      {
        synchronized (sRebaseMethodLock)
        {
          boolean bool = sRebaseMethodFetched;
          if (!bool)
          {
            try
            {
              Method localMethod1 = Resources.Theme.class.getDeclaredMethod("rebase", new Class[0]);
              sRebaseMethod = localMethod1;
              localMethod1.setAccessible(true);
            }
            catch (NoSuchMethodException localNoSuchMethodException)
            {
              Log.i("ResourcesCompat", "Failed to retrieve rebase() method", localNoSuchMethodException);
            }
            sRebaseMethodFetched = true;
          }
          Method localMethod2 = sRebaseMethod;
          if (localMethod2 != null)
          {
            try
            {
              sRebaseMethod.invoke(paramTheme, new Object[0]);
            }
            catch (InvocationTargetException paramTheme) {}catch (IllegalAccessException paramTheme) {}
            Log.i("ResourcesCompat", "Failed to invoke rebase() method via reflection", paramTheme);
            sRebaseMethod = null;
          }
          return;
        }
      }
    }
    
    static class ImplApi29
    {
      private ImplApi29() {}
      
      static void rebase(Resources.Theme paramTheme)
      {
        paramTheme.rebase();
      }
    }
  }
}