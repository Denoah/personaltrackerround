package androidx.core.view;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowInsets.Builder;
import androidx.core.graphics.Insets;
import androidx.core.util.ObjectsCompat;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Objects;

public class WindowInsetsCompat
{
  private static final String TAG = "WindowInsetsCompat";
  private final Object mInsets;
  private Insets mMandatorySystemGestureInsets;
  private Insets mStableInsets;
  private Insets mSystemGestureInsets;
  private Insets mSystemWindowInsets;
  private Insets mTappableElementInsets;
  
  public WindowInsetsCompat(WindowInsetsCompat paramWindowInsetsCompat)
  {
    int i = Build.VERSION.SDK_INT;
    Object localObject = null;
    if (i >= 20)
    {
      if (paramWindowInsetsCompat == null) {
        paramWindowInsetsCompat = localObject;
      } else {
        paramWindowInsetsCompat = new WindowInsets((WindowInsets)paramWindowInsetsCompat.mInsets);
      }
      this.mInsets = paramWindowInsetsCompat;
    }
    else
    {
      this.mInsets = null;
    }
  }
  
  WindowInsetsCompat(Object paramObject)
  {
    this.mInsets = paramObject;
  }
  
  public static WindowInsetsCompat toWindowInsetsCompat(WindowInsets paramWindowInsets)
  {
    return new WindowInsetsCompat(Objects.requireNonNull(paramWindowInsets));
  }
  
  public WindowInsetsCompat consumeDisplayCutout()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return new WindowInsetsCompat(((WindowInsets)this.mInsets).consumeDisplayCutout());
    }
    return this;
  }
  
  public WindowInsetsCompat consumeStableInsets()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return new WindowInsetsCompat(((WindowInsets)this.mInsets).consumeStableInsets());
    }
    return null;
  }
  
  public WindowInsetsCompat consumeSystemWindowInsets()
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return new WindowInsetsCompat(((WindowInsets)this.mInsets).consumeSystemWindowInsets());
    }
    return null;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof WindowInsetsCompat)) {
      return false;
    }
    paramObject = (WindowInsetsCompat)paramObject;
    return ObjectsCompat.equals(this.mInsets, paramObject.mInsets);
  }
  
  public DisplayCutoutCompat getDisplayCutout()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return DisplayCutoutCompat.wrap(((WindowInsets)this.mInsets).getDisplayCutout());
    }
    return null;
  }
  
  public Insets getMandatorySystemGestureInsets()
  {
    if (this.mMandatorySystemGestureInsets == null) {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mMandatorySystemGestureInsets = Insets.toCompatInsets(((WindowInsets)this.mInsets).getMandatorySystemGestureInsets());
      } else {
        this.mMandatorySystemGestureInsets = getSystemWindowInsets();
      }
    }
    return this.mMandatorySystemGestureInsets;
  }
  
  public int getStableInsetBottom()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ((WindowInsets)this.mInsets).getStableInsetBottom();
    }
    return 0;
  }
  
  public int getStableInsetLeft()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ((WindowInsets)this.mInsets).getStableInsetLeft();
    }
    return 0;
  }
  
  public int getStableInsetRight()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ((WindowInsets)this.mInsets).getStableInsetRight();
    }
    return 0;
  }
  
  public int getStableInsetTop()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ((WindowInsets)this.mInsets).getStableInsetTop();
    }
    return 0;
  }
  
  public Insets getStableInsets()
  {
    if (this.mStableInsets == null) {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mStableInsets = Insets.toCompatInsets(((WindowInsets)this.mInsets).getStableInsets());
      } else {
        this.mStableInsets = Insets.of(getStableInsetLeft(), getStableInsetTop(), getStableInsetRight(), getStableInsetBottom());
      }
    }
    return this.mStableInsets;
  }
  
  public Insets getSystemGestureInsets()
  {
    if (this.mSystemGestureInsets == null) {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mSystemGestureInsets = Insets.toCompatInsets(((WindowInsets)this.mInsets).getSystemGestureInsets());
      } else {
        this.mSystemGestureInsets = getSystemWindowInsets();
      }
    }
    return this.mSystemGestureInsets;
  }
  
  public int getSystemWindowInsetBottom()
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return ((WindowInsets)this.mInsets).getSystemWindowInsetBottom();
    }
    return 0;
  }
  
  public int getSystemWindowInsetLeft()
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return ((WindowInsets)this.mInsets).getSystemWindowInsetLeft();
    }
    return 0;
  }
  
  public int getSystemWindowInsetRight()
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return ((WindowInsets)this.mInsets).getSystemWindowInsetRight();
    }
    return 0;
  }
  
  public int getSystemWindowInsetTop()
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return ((WindowInsets)this.mInsets).getSystemWindowInsetTop();
    }
    return 0;
  }
  
  public Insets getSystemWindowInsets()
  {
    if (this.mSystemWindowInsets == null) {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mSystemWindowInsets = Insets.toCompatInsets(((WindowInsets)this.mInsets).getSystemWindowInsets());
      } else {
        this.mSystemWindowInsets = Insets.of(getSystemWindowInsetLeft(), getSystemWindowInsetTop(), getSystemWindowInsetRight(), getSystemWindowInsetBottom());
      }
    }
    return this.mSystemWindowInsets;
  }
  
  public Insets getTappableElementInsets()
  {
    if (this.mTappableElementInsets == null) {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mTappableElementInsets = Insets.toCompatInsets(((WindowInsets)this.mInsets).getTappableElementInsets());
      } else {
        this.mTappableElementInsets = getSystemWindowInsets();
      }
    }
    return this.mTappableElementInsets;
  }
  
  public boolean hasInsets()
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return ((WindowInsets)this.mInsets).hasInsets();
    }
    return false;
  }
  
  public boolean hasStableInsets()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ((WindowInsets)this.mInsets).hasStableInsets();
    }
    return false;
  }
  
  public boolean hasSystemWindowInsets()
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return ((WindowInsets)this.mInsets).hasSystemWindowInsets();
    }
    return false;
  }
  
  public int hashCode()
  {
    Object localObject = this.mInsets;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = localObject.hashCode();
    }
    return i;
  }
  
  public boolean isConsumed()
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return ((WindowInsets)this.mInsets).isConsumed();
    }
    return false;
  }
  
  public boolean isRound()
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return ((WindowInsets)this.mInsets).isRound();
    }
    return false;
  }
  
  @Deprecated
  public WindowInsetsCompat replaceSystemWindowInsets(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return new WindowInsetsCompat(((WindowInsets)this.mInsets).replaceSystemWindowInsets(paramInt1, paramInt2, paramInt3, paramInt4));
    }
    return null;
  }
  
  @Deprecated
  public WindowInsetsCompat replaceSystemWindowInsets(Rect paramRect)
  {
    if (Build.VERSION.SDK_INT >= 20) {
      return replaceSystemWindowInsets(paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
    }
    return null;
  }
  
  public WindowInsets toWindowInsets()
  {
    return (WindowInsets)this.mInsets;
  }
  
  public static final class Builder
  {
    private final WindowInsetsCompat.BuilderImpl mImpl;
    
    public Builder()
    {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mImpl = new WindowInsetsCompat.BuilderImpl29();
      } else if (Build.VERSION.SDK_INT >= 20) {
        this.mImpl = new WindowInsetsCompat.BuilderImpl20();
      } else {
        this.mImpl = new WindowInsetsCompat.BuilderImpl();
      }
    }
    
    public Builder(WindowInsetsCompat paramWindowInsetsCompat)
    {
      if (Build.VERSION.SDK_INT >= 29) {
        this.mImpl = new WindowInsetsCompat.BuilderImpl29(paramWindowInsetsCompat);
      } else if (Build.VERSION.SDK_INT >= 20) {
        this.mImpl = new WindowInsetsCompat.BuilderImpl20(paramWindowInsetsCompat);
      } else {
        this.mImpl = new WindowInsetsCompat.BuilderImpl(paramWindowInsetsCompat);
      }
    }
    
    public WindowInsetsCompat build()
    {
      return this.mImpl.build();
    }
    
    public Builder setDisplayCutout(DisplayCutoutCompat paramDisplayCutoutCompat)
    {
      this.mImpl.setDisplayCutout(paramDisplayCutoutCompat);
      return this;
    }
    
    public Builder setMandatorySystemGestureInsets(Insets paramInsets)
    {
      this.mImpl.setMandatorySystemGestureInsets(paramInsets);
      return this;
    }
    
    public Builder setStableInsets(Insets paramInsets)
    {
      this.mImpl.setStableInsets(paramInsets);
      return this;
    }
    
    public Builder setSystemGestureInsets(Insets paramInsets)
    {
      this.mImpl.setSystemGestureInsets(paramInsets);
      return this;
    }
    
    public Builder setSystemWindowInsets(Insets paramInsets)
    {
      this.mImpl.setSystemWindowInsets(paramInsets);
      return this;
    }
    
    public Builder setTappableElementInsets(Insets paramInsets)
    {
      this.mImpl.setTappableElementInsets(paramInsets);
      return this;
    }
  }
  
  private static class BuilderImpl
  {
    private WindowInsetsCompat mInsets;
    
    BuilderImpl()
    {
      this.mInsets = new WindowInsetsCompat(null);
    }
    
    BuilderImpl(WindowInsetsCompat paramWindowInsetsCompat)
    {
      this.mInsets = paramWindowInsetsCompat;
    }
    
    public WindowInsetsCompat build()
    {
      return this.mInsets;
    }
    
    public void setDisplayCutout(DisplayCutoutCompat paramDisplayCutoutCompat) {}
    
    public void setMandatorySystemGestureInsets(Insets paramInsets) {}
    
    public void setStableInsets(Insets paramInsets) {}
    
    public void setSystemGestureInsets(Insets paramInsets) {}
    
    public void setSystemWindowInsets(Insets paramInsets) {}
    
    public void setTappableElementInsets(Insets paramInsets) {}
  }
  
  private static class BuilderImpl20
    extends WindowInsetsCompat.BuilderImpl
  {
    private static Constructor<WindowInsets> sConstructor;
    private static boolean sConstructorFetched = false;
    private static Field sConsumedField;
    private static boolean sConsumedFieldFetched = false;
    private WindowInsets mInsets;
    
    BuilderImpl20()
    {
      this.mInsets = createWindowInsetsInstance();
    }
    
    BuilderImpl20(WindowInsetsCompat paramWindowInsetsCompat)
    {
      this.mInsets = paramWindowInsetsCompat.toWindowInsets();
    }
    
    private static WindowInsets createWindowInsetsInstance()
    {
      if (!sConsumedFieldFetched)
      {
        try
        {
          sConsumedField = WindowInsets.class.getDeclaredField("CONSUMED");
        }
        catch (ReflectiveOperationException localReflectiveOperationException1)
        {
          Log.i("WindowInsetsCompat", "Could not retrieve WindowInsets.CONSUMED field", localReflectiveOperationException1);
        }
        sConsumedFieldFetched = true;
      }
      Object localObject1 = sConsumedField;
      if (localObject1 != null) {
        try
        {
          localObject1 = (WindowInsets)((Field)localObject1).get(null);
          if (localObject1 != null)
          {
            localObject1 = new WindowInsets((WindowInsets)localObject1);
            return localObject1;
          }
        }
        catch (ReflectiveOperationException localReflectiveOperationException2)
        {
          Log.i("WindowInsetsCompat", "Could not get value from WindowInsets.CONSUMED field", localReflectiveOperationException2);
        }
      }
      if (!sConstructorFetched)
      {
        try
        {
          sConstructor = WindowInsets.class.getConstructor(new Class[] { Rect.class });
        }
        catch (ReflectiveOperationException localReflectiveOperationException3)
        {
          Log.i("WindowInsetsCompat", "Could not retrieve WindowInsets(Rect) constructor", localReflectiveOperationException3);
        }
        sConstructorFetched = true;
      }
      Constructor localConstructor = sConstructor;
      if (localConstructor != null) {
        try
        {
          Object localObject2 = new android/graphics/Rect;
          ((Rect)localObject2).<init>();
          localObject2 = (WindowInsets)localConstructor.newInstance(new Object[] { localObject2 });
          return localObject2;
        }
        catch (ReflectiveOperationException localReflectiveOperationException4)
        {
          Log.i("WindowInsetsCompat", "Could not invoke WindowInsets(Rect) constructor", localReflectiveOperationException4);
        }
      }
      return null;
    }
    
    public WindowInsetsCompat build()
    {
      return WindowInsetsCompat.toWindowInsetsCompat(this.mInsets);
    }
    
    public void setSystemWindowInsets(Insets paramInsets)
    {
      WindowInsets localWindowInsets = this.mInsets;
      if (localWindowInsets != null) {
        this.mInsets = localWindowInsets.replaceSystemWindowInsets(paramInsets.left, paramInsets.top, paramInsets.right, paramInsets.bottom);
      }
    }
  }
  
  private static class BuilderImpl29
    extends WindowInsetsCompat.BuilderImpl
  {
    private final WindowInsets.Builder mPlatBuilder;
    
    BuilderImpl29()
    {
      this.mPlatBuilder = new WindowInsets.Builder();
    }
    
    BuilderImpl29(WindowInsetsCompat paramWindowInsetsCompat)
    {
      this.mPlatBuilder = new WindowInsets.Builder(paramWindowInsetsCompat.toWindowInsets());
    }
    
    public WindowInsetsCompat build()
    {
      return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatBuilder.build());
    }
    
    public void setDisplayCutout(DisplayCutoutCompat paramDisplayCutoutCompat)
    {
      WindowInsets.Builder localBuilder = this.mPlatBuilder;
      if (paramDisplayCutoutCompat != null) {
        paramDisplayCutoutCompat = paramDisplayCutoutCompat.unwrap();
      } else {
        paramDisplayCutoutCompat = null;
      }
      localBuilder.setDisplayCutout(paramDisplayCutoutCompat);
    }
    
    public void setMandatorySystemGestureInsets(Insets paramInsets)
    {
      this.mPlatBuilder.setMandatorySystemGestureInsets(paramInsets.toPlatformInsets());
    }
    
    public void setStableInsets(Insets paramInsets)
    {
      this.mPlatBuilder.setStableInsets(paramInsets.toPlatformInsets());
    }
    
    public void setSystemGestureInsets(Insets paramInsets)
    {
      this.mPlatBuilder.setSystemGestureInsets(paramInsets.toPlatformInsets());
    }
    
    public void setSystemWindowInsets(Insets paramInsets)
    {
      this.mPlatBuilder.setSystemWindowInsets(paramInsets.toPlatformInsets());
    }
    
    public void setTappableElementInsets(Insets paramInsets)
    {
      this.mPlatBuilder.setTappableElementInsets(paramInsets.toPlatformInsets());
    }
  }
}
