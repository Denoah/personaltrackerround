package androidx.core.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import java.lang.reflect.Field;

public final class LayoutInflaterCompat
{
  private static final String TAG = "LayoutInflaterCompatHC";
  private static boolean sCheckedField;
  private static Field sLayoutInflaterFactory2Field;
  
  private LayoutInflaterCompat() {}
  
  private static void forceSetFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2)
  {
    if (!sCheckedField)
    {
      try
      {
        Field localField = LayoutInflater.class.getDeclaredField("mFactory2");
        sLayoutInflaterFactory2Field = localField;
        localField.setAccessible(true);
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("forceSetFactory2 Could not find field 'mFactory2' on class ");
        localStringBuilder.append(LayoutInflater.class.getName());
        localStringBuilder.append("; inflation may have unexpected results.");
        Log.e("LayoutInflaterCompatHC", localStringBuilder.toString(), localNoSuchFieldException);
      }
      sCheckedField = true;
    }
    Object localObject = sLayoutInflaterFactory2Field;
    if (localObject != null) {
      try
      {
        ((Field)localObject).set(paramLayoutInflater, paramFactory2);
      }
      catch (IllegalAccessException paramFactory2)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("forceSetFactory2 could not set the Factory2 on LayoutInflater ");
        ((StringBuilder)localObject).append(paramLayoutInflater);
        ((StringBuilder)localObject).append("; inflation may have unexpected results.");
        Log.e("LayoutInflaterCompatHC", ((StringBuilder)localObject).toString(), paramFactory2);
      }
    }
  }
  
  @Deprecated
  public static LayoutInflaterFactory getFactory(LayoutInflater paramLayoutInflater)
  {
    paramLayoutInflater = paramLayoutInflater.getFactory();
    if ((paramLayoutInflater instanceof Factory2Wrapper)) {
      return ((Factory2Wrapper)paramLayoutInflater).mDelegateFactory;
    }
    return null;
  }
  
  @Deprecated
  public static void setFactory(LayoutInflater paramLayoutInflater, LayoutInflaterFactory paramLayoutInflaterFactory)
  {
    int i = Build.VERSION.SDK_INT;
    Object localObject1 = null;
    Object localObject2 = null;
    if (i >= 21)
    {
      if (paramLayoutInflaterFactory != null) {
        localObject2 = new Factory2Wrapper(paramLayoutInflaterFactory);
      }
      paramLayoutInflater.setFactory2((LayoutInflater.Factory2)localObject2);
    }
    else
    {
      localObject2 = localObject1;
      if (paramLayoutInflaterFactory != null) {
        localObject2 = new Factory2Wrapper(paramLayoutInflaterFactory);
      }
      paramLayoutInflater.setFactory2((LayoutInflater.Factory2)localObject2);
      paramLayoutInflaterFactory = paramLayoutInflater.getFactory();
      if ((paramLayoutInflaterFactory instanceof LayoutInflater.Factory2)) {
        forceSetFactory2(paramLayoutInflater, (LayoutInflater.Factory2)paramLayoutInflaterFactory);
      } else {
        forceSetFactory2(paramLayoutInflater, (LayoutInflater.Factory2)localObject2);
      }
    }
  }
  
  public static void setFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2)
  {
    paramLayoutInflater.setFactory2(paramFactory2);
    if (Build.VERSION.SDK_INT < 21)
    {
      LayoutInflater.Factory localFactory = paramLayoutInflater.getFactory();
      if ((localFactory instanceof LayoutInflater.Factory2)) {
        forceSetFactory2(paramLayoutInflater, (LayoutInflater.Factory2)localFactory);
      } else {
        forceSetFactory2(paramLayoutInflater, paramFactory2);
      }
    }
  }
  
  static class Factory2Wrapper
    implements LayoutInflater.Factory2
  {
    final LayoutInflaterFactory mDelegateFactory;
    
    Factory2Wrapper(LayoutInflaterFactory paramLayoutInflaterFactory)
    {
      this.mDelegateFactory = paramLayoutInflaterFactory;
    }
    
    public View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
    {
      return this.mDelegateFactory.onCreateView(paramView, paramString, paramContext, paramAttributeSet);
    }
    
    public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet)
    {
      return this.mDelegateFactory.onCreateView(null, paramString, paramContext, paramAttributeSet);
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(getClass().getName());
      localStringBuilder.append("{");
      localStringBuilder.append(this.mDelegateFactory);
      localStringBuilder.append("}");
      return localStringBuilder.toString();
    }
  }
}
