package androidx.core.graphics.drawable;

import android.graphics.drawable.Drawable;

public abstract interface WrappedDrawable
{
  public abstract Drawable getWrappedDrawable();
  
  public abstract void setWrappedDrawable(Drawable paramDrawable);
}
