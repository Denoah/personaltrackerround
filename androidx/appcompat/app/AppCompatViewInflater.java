package androidx.appcompat.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.R.styleable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.appcompat.widget.TintContextWrapper;
import androidx.collection.ArrayMap;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class AppCompatViewInflater
{
  private static final String LOG_TAG = "AppCompatViewInflater";
  private static final String[] sClassPrefixList = { "android.widget.", "android.view.", "android.webkit." };
  private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
  private static final Class<?>[] sConstructorSignature = { Context.class, AttributeSet.class };
  private static final int[] sOnClickAttrs = { 16843375 };
  private final Object[] mConstructorArgs = new Object[2];
  
  public AppCompatViewInflater() {}
  
  private void checkOnClickListener(View paramView, AttributeSet paramAttributeSet)
  {
    Object localObject = paramView.getContext();
    if (((localObject instanceof ContextWrapper)) && ((Build.VERSION.SDK_INT < 15) || (ViewCompat.hasOnClickListeners(paramView))))
    {
      localObject = ((Context)localObject).obtainStyledAttributes(paramAttributeSet, sOnClickAttrs);
      paramAttributeSet = ((TypedArray)localObject).getString(0);
      if (paramAttributeSet != null) {
        paramView.setOnClickListener(new DeclaredOnClickListener(paramView, paramAttributeSet));
      }
      ((TypedArray)localObject).recycle();
    }
  }
  
  private View createViewByPrefix(Context paramContext, String paramString1, String paramString2)
    throws ClassNotFoundException, InflateException
  {
    Constructor localConstructor = (Constructor)sConstructorMap.get(paramString1);
    Object localObject = localConstructor;
    if ((localConstructor != null) || (paramString2 != null)) {}
    try
    {
      localObject = new java/lang/StringBuilder;
      ((StringBuilder)localObject).<init>();
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append(paramString1);
      paramString2 = ((StringBuilder)localObject).toString();
      break label62;
      paramString2 = paramString1;
      label62:
      localObject = Class.forName(paramString2, false, paramContext.getClassLoader()).asSubclass(View.class).getConstructor(sConstructorSignature);
      sConstructorMap.put(paramString1, localObject);
      ((Constructor)localObject).setAccessible(true);
      paramContext = (View)((Constructor)localObject).newInstance(this.mConstructorArgs);
      return paramContext;
    }
    catch (Exception paramContext) {}
    return null;
  }
  
  /* Error */
  private View createViewFromTag(Context paramContext, String paramString, AttributeSet paramAttributeSet)
  {
    // Byte code:
    //   0: aload_2
    //   1: astore 4
    //   3: aload_2
    //   4: ldc -97
    //   6: invokevirtual 163	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   9: ifeq +14 -> 23
    //   12: aload_3
    //   13: aconst_null
    //   14: ldc -91
    //   16: invokeinterface 169 3 0
    //   21: astore 4
    //   23: aload_0
    //   24: getfield 57	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   27: iconst_0
    //   28: aload_1
    //   29: aastore
    //   30: aload_0
    //   31: getfield 57	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   34: iconst_1
    //   35: aload_3
    //   36: aastore
    //   37: iconst_m1
    //   38: aload 4
    //   40: bipush 46
    //   42: invokevirtual 173	java/lang/String:indexOf	(I)I
    //   45: if_icmpne +69 -> 114
    //   48: iconst_0
    //   49: istore 5
    //   51: iload 5
    //   53: getstatic 46	androidx/appcompat/app/AppCompatViewInflater:sClassPrefixList	[Ljava/lang/String;
    //   56: arraylength
    //   57: if_icmpge +42 -> 99
    //   60: aload_0
    //   61: aload_1
    //   62: aload 4
    //   64: getstatic 46	androidx/appcompat/app/AppCompatViewInflater:sClassPrefixList	[Ljava/lang/String;
    //   67: iload 5
    //   69: aaload
    //   70: invokespecial 175	androidx/appcompat/app/AppCompatViewInflater:createViewByPrefix	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Landroid/view/View;
    //   73: astore_2
    //   74: aload_2
    //   75: ifnull +18 -> 93
    //   78: aload_0
    //   79: getfield 57	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   82: astore_1
    //   83: aload_1
    //   84: iconst_0
    //   85: aconst_null
    //   86: aastore
    //   87: aload_1
    //   88: iconst_1
    //   89: aconst_null
    //   90: aastore
    //   91: aload_2
    //   92: areturn
    //   93: iinc 5 1
    //   96: goto -45 -> 51
    //   99: aload_0
    //   100: getfield 57	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   103: astore_1
    //   104: aload_1
    //   105: iconst_0
    //   106: aconst_null
    //   107: aastore
    //   108: aload_1
    //   109: iconst_1
    //   110: aconst_null
    //   111: aastore
    //   112: aconst_null
    //   113: areturn
    //   114: aload_0
    //   115: aload_1
    //   116: aload 4
    //   118: aconst_null
    //   119: invokespecial 175	androidx/appcompat/app/AppCompatViewInflater:createViewByPrefix	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Landroid/view/View;
    //   122: astore_1
    //   123: aload_0
    //   124: getfield 57	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   127: astore_2
    //   128: aload_2
    //   129: iconst_0
    //   130: aconst_null
    //   131: aastore
    //   132: aload_2
    //   133: iconst_1
    //   134: aconst_null
    //   135: aastore
    //   136: aload_1
    //   137: areturn
    //   138: astore_2
    //   139: aload_0
    //   140: getfield 57	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   143: astore_1
    //   144: aload_1
    //   145: iconst_0
    //   146: aconst_null
    //   147: aastore
    //   148: aload_1
    //   149: iconst_1
    //   150: aconst_null
    //   151: aastore
    //   152: aload_2
    //   153: athrow
    //   154: astore_1
    //   155: aload_0
    //   156: getfield 57	androidx/appcompat/app/AppCompatViewInflater:mConstructorArgs	[Ljava/lang/Object;
    //   159: astore_1
    //   160: aload_1
    //   161: iconst_0
    //   162: aconst_null
    //   163: aastore
    //   164: aload_1
    //   165: iconst_1
    //   166: aconst_null
    //   167: aastore
    //   168: aconst_null
    //   169: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	170	0	this	AppCompatViewInflater
    //   0	170	1	paramContext	Context
    //   0	170	2	paramString	String
    //   0	170	3	paramAttributeSet	AttributeSet
    //   1	116	4	str	String
    //   49	45	5	i	int
    // Exception table:
    //   from	to	target	type
    //   23	48	138	finally
    //   51	74	138	finally
    //   114	123	138	finally
    //   23	48	154	java/lang/Exception
    //   51	74	154	java/lang/Exception
    //   114	123	154	java/lang/Exception
  }
  
  private static Context themifyContext(Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramAttributeSet = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.View, 0, 0);
    int i;
    if (paramBoolean1) {
      i = paramAttributeSet.getResourceId(R.styleable.View_android_theme, 0);
    } else {
      i = 0;
    }
    int j = i;
    if (paramBoolean2)
    {
      j = i;
      if (i == 0)
      {
        i = paramAttributeSet.getResourceId(R.styleable.View_theme, 0);
        j = i;
        if (i != 0)
        {
          Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
          j = i;
        }
      }
    }
    paramAttributeSet.recycle();
    paramAttributeSet = paramContext;
    if (j != 0) {
      if ((paramContext instanceof ContextThemeWrapper))
      {
        paramAttributeSet = paramContext;
        if (((ContextThemeWrapper)paramContext).getThemeResId() == j) {}
      }
      else
      {
        paramAttributeSet = new ContextThemeWrapper(paramContext, j);
      }
    }
    return paramAttributeSet;
  }
  
  private void verifyNotNull(View paramView, String paramString)
  {
    if (paramView != null) {
      return;
    }
    paramView = new StringBuilder();
    paramView.append(getClass().getName());
    paramView.append(" asked to inflate view for <");
    paramView.append(paramString);
    paramView.append(">, but returned null");
    throw new IllegalStateException(paramView.toString());
  }
  
  protected AppCompatAutoCompleteTextView createAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatAutoCompleteTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatButton createButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatButton(paramContext, paramAttributeSet);
  }
  
  protected AppCompatCheckBox createCheckBox(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatCheckBox(paramContext, paramAttributeSet);
  }
  
  protected AppCompatCheckedTextView createCheckedTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatCheckedTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatEditText createEditText(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatEditText(paramContext, paramAttributeSet);
  }
  
  protected AppCompatImageButton createImageButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatImageButton(paramContext, paramAttributeSet);
  }
  
  protected AppCompatImageView createImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatImageView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatMultiAutoCompleteTextView createMultiAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatMultiAutoCompleteTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatRadioButton createRadioButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatRadioButton(paramContext, paramAttributeSet);
  }
  
  protected AppCompatRatingBar createRatingBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatRatingBar(paramContext, paramAttributeSet);
  }
  
  protected AppCompatSeekBar createSeekBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatSeekBar(paramContext, paramAttributeSet);
  }
  
  protected AppCompatSpinner createSpinner(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatSpinner(paramContext, paramAttributeSet);
  }
  
  protected AppCompatTextView createTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatToggleButton createToggleButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    return new AppCompatToggleButton(paramContext, paramAttributeSet);
  }
  
  protected View createView(Context paramContext, String paramString, AttributeSet paramAttributeSet)
  {
    return null;
  }
  
  final View createView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    if ((paramBoolean1) && (paramView != null)) {
      localObject = paramView.getContext();
    } else {
      localObject = paramContext;
    }
    if (!paramBoolean2)
    {
      paramView = (View)localObject;
      if (!paramBoolean3) {}
    }
    else
    {
      paramView = themifyContext((Context)localObject, paramAttributeSet, paramBoolean2, paramBoolean3);
    }
    Object localObject = paramView;
    if (paramBoolean4) {
      localObject = TintContextWrapper.wrap(paramView);
    }
    int i = -1;
    switch (paramString.hashCode())
    {
    default: 
      break;
    case 2001146706: 
      if (paramString.equals("Button")) {
        i = 2;
      }
      break;
    case 1666676343: 
      if (paramString.equals("EditText")) {
        i = 3;
      }
      break;
    case 1601505219: 
      if (paramString.equals("CheckBox")) {
        i = 6;
      }
      break;
    case 1413872058: 
      if (paramString.equals("AutoCompleteTextView")) {
        i = 9;
      }
      break;
    case 1125864064: 
      if (paramString.equals("ImageView")) {
        i = 1;
      }
      break;
    case 799298502: 
      if (paramString.equals("ToggleButton")) {
        i = 13;
      }
      break;
    case 776382189: 
      if (paramString.equals("RadioButton")) {
        i = 7;
      }
      break;
    case -339785223: 
      if (paramString.equals("Spinner")) {
        i = 4;
      }
      break;
    case -658531749: 
      if (paramString.equals("SeekBar")) {
        i = 12;
      }
      break;
    case -937446323: 
      if (paramString.equals("ImageButton")) {
        i = 5;
      }
      break;
    case -938935918: 
      if (paramString.equals("TextView")) {
        i = 0;
      }
      break;
    case -1346021293: 
      if (paramString.equals("MultiAutoCompleteTextView")) {
        i = 10;
      }
      break;
    case -1455429095: 
      if (paramString.equals("CheckedTextView")) {
        i = 8;
      }
      break;
    case -1946472170: 
      if (paramString.equals("RatingBar")) {
        i = 11;
      }
      break;
    }
    switch (i)
    {
    default: 
      paramView = createView((Context)localObject, paramString, paramAttributeSet);
      break;
    case 13: 
      paramView = createToggleButton((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 12: 
      paramView = createSeekBar((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 11: 
      paramView = createRatingBar((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 10: 
      paramView = createMultiAutoCompleteTextView((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 9: 
      paramView = createAutoCompleteTextView((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 8: 
      paramView = createCheckedTextView((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 7: 
      paramView = createRadioButton((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 6: 
      paramView = createCheckBox((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 5: 
      paramView = createImageButton((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 4: 
      paramView = createSpinner((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 3: 
      paramView = createEditText((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 2: 
      paramView = createButton((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 1: 
      paramView = createImageView((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
      break;
    case 0: 
      paramView = createTextView((Context)localObject, paramAttributeSet);
      verifyNotNull(paramView, paramString);
    }
    View localView = paramView;
    if (paramView == null)
    {
      localView = paramView;
      if (paramContext != localObject) {
        localView = createViewFromTag((Context)localObject, paramString, paramAttributeSet);
      }
    }
    if (localView != null) {
      checkOnClickListener(localView, paramAttributeSet);
    }
    return localView;
  }
  
  private static class DeclaredOnClickListener
    implements View.OnClickListener
  {
    private final View mHostView;
    private final String mMethodName;
    private Context mResolvedContext;
    private Method mResolvedMethod;
    
    public DeclaredOnClickListener(View paramView, String paramString)
    {
      this.mHostView = paramView;
      this.mMethodName = paramString;
    }
    
    private void resolveMethod(Context paramContext, String paramString)
    {
      while (paramContext != null)
      {
        try
        {
          if (!paramContext.isRestricted())
          {
            paramString = paramContext.getClass().getMethod(this.mMethodName, new Class[] { View.class });
            if (paramString != null)
            {
              this.mResolvedMethod = paramString;
              this.mResolvedContext = paramContext;
              return;
            }
          }
        }
        catch (NoSuchMethodException paramString)
        {
          int i;
          for (;;) {}
        }
        if ((paramContext instanceof ContextWrapper)) {
          paramContext = ((ContextWrapper)paramContext).getBaseContext();
        } else {
          paramContext = null;
        }
      }
      i = this.mHostView.getId();
      if (i == -1)
      {
        paramContext = "";
      }
      else
      {
        paramContext = new StringBuilder();
        paramContext.append(" with id '");
        paramContext.append(this.mHostView.getContext().getResources().getResourceEntryName(i));
        paramContext.append("'");
        paramContext = paramContext.toString();
      }
      paramString = new StringBuilder();
      paramString.append("Could not find method ");
      paramString.append(this.mMethodName);
      paramString.append("(View) in a parent or ancestor Context for android:onClick attribute defined on view ");
      paramString.append(this.mHostView.getClass());
      paramString.append(paramContext);
      throw new IllegalStateException(paramString.toString());
    }
    
    public void onClick(View paramView)
    {
      if (this.mResolvedMethod == null) {
        resolveMethod(this.mHostView.getContext(), this.mMethodName);
      }
      try
      {
        this.mResolvedMethod.invoke(this.mResolvedContext, new Object[] { paramView });
        return;
      }
      catch (InvocationTargetException paramView)
      {
        throw new IllegalStateException("Could not execute method for android:onClick", paramView);
      }
      catch (IllegalAccessException paramView)
      {
        throw new IllegalStateException("Could not execute non-public method for android:onClick", paramView);
      }
    }
  }
}
