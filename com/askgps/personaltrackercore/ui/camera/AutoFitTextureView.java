package com.askgps.personaltrackercore.ui.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import android.view.View.MeasureSpec;
import java.util.HashMap;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\004\n\002\020\002\n\002\b\006\030\0002\0020\001B%\b\007\022\006\020\002\032\0020\003\022\n\b\002\020\004\032\004\030\0010\005\022\b\b\002\020\006\032\0020\007?\006\002\020\bJ\030\020\013\032\0020\f2\006\020\r\032\0020\0072\006\020\016\032\0020\007H\024J\026\020\017\032\0020\f2\006\020\020\032\0020\0072\006\020\021\032\0020\007R\016\020\t\032\0020\007X?\016?\006\002\n\000R\016\020\n\032\0020\007X?\016?\006\002\n\000?\006\022"}, d2={"Lcom/askgps/personaltrackercore/ui/camera/AutoFitTextureView;", "Landroid/view/TextureView;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "ratioHeight", "ratioWidth", "onMeasure", "", "widthMeasureSpec", "heightMeasureSpec", "setAspectRatio", "width", "height", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class AutoFitTextureView
  extends TextureView
{
  private HashMap _$_findViewCache;
  private int ratioHeight;
  private int ratioWidth;
  
  public AutoFitTextureView(Context paramContext)
  {
    this(paramContext, null, 0, 6, null);
  }
  
  public AutoFitTextureView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0, 4, null);
  }
  
  public AutoFitTextureView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void _$_clearFindViewByIdCache()
  {
    HashMap localHashMap = this._$_findViewCache;
    if (localHashMap != null) {
      localHashMap.clear();
    }
  }
  
  public View _$_findCachedViewById(int paramInt)
  {
    if (this._$_findViewCache == null) {
      this._$_findViewCache = new HashMap();
    }
    View localView1 = (View)this._$_findViewCache.get(Integer.valueOf(paramInt));
    View localView2 = localView1;
    if (localView1 == null)
    {
      localView2 = findViewById(paramInt);
      this._$_findViewCache.put(Integer.valueOf(paramInt), localView2);
    }
    return localView2;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    paramInt1 = View.MeasureSpec.getSize(paramInt1);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    int i = this.ratioWidth;
    if (i != 0)
    {
      int j = this.ratioHeight;
      if (j != 0)
      {
        if (paramInt1 < paramInt2 * i / j)
        {
          setMeasuredDimension(paramInt1, j * paramInt1 / i);
          return;
        }
        setMeasuredDimension(i * paramInt2 / j, paramInt2);
        return;
      }
    }
    setMeasuredDimension(paramInt1, paramInt2);
  }
  
  public final void setAspectRatio(int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= 0) && (paramInt2 >= 0))
    {
      this.ratioWidth = paramInt1;
      this.ratioHeight = paramInt2;
      requestLayout();
      return;
    }
    throw ((Throwable)new IllegalArgumentException("Size cannot be negative."));
  }
}
