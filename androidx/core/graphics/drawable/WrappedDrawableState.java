package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;

final class WrappedDrawableState
  extends Drawable.ConstantState
{
  int mChangingConfigurations;
  Drawable.ConstantState mDrawableState;
  ColorStateList mTint = null;
  PorterDuff.Mode mTintMode = WrappedDrawableApi14.DEFAULT_TINT_MODE;
  
  WrappedDrawableState(WrappedDrawableState paramWrappedDrawableState)
  {
    if (paramWrappedDrawableState != null)
    {
      this.mChangingConfigurations = paramWrappedDrawableState.mChangingConfigurations;
      this.mDrawableState = paramWrappedDrawableState.mDrawableState;
      this.mTint = paramWrappedDrawableState.mTint;
      this.mTintMode = paramWrappedDrawableState.mTintMode;
    }
  }
  
  boolean canConstantState()
  {
    boolean bool;
    if (this.mDrawableState != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public int getChangingConfigurations()
  {
    int i = this.mChangingConfigurations;
    Drawable.ConstantState localConstantState = this.mDrawableState;
    int j;
    if (localConstantState != null) {
      j = localConstantState.getChangingConfigurations();
    } else {
      j = 0;
    }
    return i | j;
  }
  
  public Drawable newDrawable()
  {
    return newDrawable(null);
  }
  
  public Drawable newDrawable(Resources paramResources)
  {
    if (Build.VERSION.SDK_INT >= 21) {
      return new WrappedDrawableApi21(this, paramResources);
    }
    return new WrappedDrawableApi14(this, paramResources);
  }
}
