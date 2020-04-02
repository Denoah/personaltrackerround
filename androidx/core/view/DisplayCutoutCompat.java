package androidx.core.view;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.DisplayCutout;
import java.util.List;

public final class DisplayCutoutCompat
{
  private final Object mDisplayCutout;
  
  public DisplayCutoutCompat(Rect paramRect, List<Rect> paramList)
  {
    this(paramRect);
  }
  
  private DisplayCutoutCompat(Object paramObject)
  {
    this.mDisplayCutout = paramObject;
  }
  
  static DisplayCutoutCompat wrap(Object paramObject)
  {
    if (paramObject == null) {
      paramObject = null;
    } else {
      paramObject = new DisplayCutoutCompat(paramObject);
    }
    return paramObject;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      Object localObject = (DisplayCutoutCompat)paramObject;
      paramObject = this.mDisplayCutout;
      localObject = ((DisplayCutoutCompat)localObject).mDisplayCutout;
      if (paramObject == null)
      {
        if (localObject != null) {
          bool = false;
        }
      }
      else {
        bool = paramObject.equals(localObject);
      }
      return bool;
    }
    return false;
  }
  
  public List<Rect> getBoundingRects()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return ((DisplayCutout)this.mDisplayCutout).getBoundingRects();
    }
    return null;
  }
  
  public int getSafeInsetBottom()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return ((DisplayCutout)this.mDisplayCutout).getSafeInsetBottom();
    }
    return 0;
  }
  
  public int getSafeInsetLeft()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return ((DisplayCutout)this.mDisplayCutout).getSafeInsetLeft();
    }
    return 0;
  }
  
  public int getSafeInsetRight()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return ((DisplayCutout)this.mDisplayCutout).getSafeInsetRight();
    }
    return 0;
  }
  
  public int getSafeInsetTop()
  {
    if (Build.VERSION.SDK_INT >= 28) {
      return ((DisplayCutout)this.mDisplayCutout).getSafeInsetTop();
    }
    return 0;
  }
  
  public int hashCode()
  {
    Object localObject = this.mDisplayCutout;
    int i;
    if (localObject == null) {
      i = 0;
    } else {
      i = localObject.hashCode();
    }
    return i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DisplayCutoutCompat{");
    localStringBuilder.append(this.mDisplayCutout);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
  
  DisplayCutout unwrap()
  {
    return (DisplayCutout)this.mDisplayCutout;
  }
}
