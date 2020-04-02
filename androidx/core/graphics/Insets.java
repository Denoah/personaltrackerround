package androidx.core.graphics;

import android.graphics.Rect;

public final class Insets
{
  public static final Insets NONE = new Insets(0, 0, 0, 0);
  public final int bottom;
  public final int left;
  public final int right;
  public final int top;
  
  private Insets(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.left = paramInt1;
    this.top = paramInt2;
    this.right = paramInt3;
    this.bottom = paramInt4;
  }
  
  public static Insets of(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt1 == 0) && (paramInt2 == 0) && (paramInt3 == 0) && (paramInt4 == 0)) {
      return NONE;
    }
    return new Insets(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static Insets of(Rect paramRect)
  {
    return of(paramRect.left, paramRect.top, paramRect.right, paramRect.bottom);
  }
  
  public static Insets toCompatInsets(android.graphics.Insets paramInsets)
  {
    return of(paramInsets.left, paramInsets.top, paramInsets.right, paramInsets.bottom);
  }
  
  @Deprecated
  public static Insets wrap(android.graphics.Insets paramInsets)
  {
    return toCompatInsets(paramInsets);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      paramObject = (Insets)paramObject;
      if (this.bottom != paramObject.bottom) {
        return false;
      }
      if (this.left != paramObject.left) {
        return false;
      }
      if (this.right != paramObject.right) {
        return false;
      }
      return this.top == paramObject.top;
    }
    return false;
  }
  
  public int hashCode()
  {
    return ((this.left * 31 + this.top) * 31 + this.right) * 31 + this.bottom;
  }
  
  public android.graphics.Insets toPlatformInsets()
  {
    return android.graphics.Insets.of(this.left, this.top, this.right, this.bottom);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Insets{left=");
    localStringBuilder.append(this.left);
    localStringBuilder.append(", top=");
    localStringBuilder.append(this.top);
    localStringBuilder.append(", right=");
    localStringBuilder.append(this.right);
    localStringBuilder.append(", bottom=");
    localStringBuilder.append(this.bottom);
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}
