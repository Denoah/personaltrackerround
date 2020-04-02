package androidx.camera.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;

final class TransformableSurfaceView
  extends SurfaceView
{
  private RectF mOverriddenLayoutRect;
  
  TransformableSurfaceView(Context paramContext)
  {
    super(paramContext);
  }
  
  TransformableSurfaceView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  TransformableSurfaceView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  TransformableSurfaceView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  private boolean hasRotation(Matrix paramMatrix)
  {
    float[] arrayOfFloat = new float[9];
    paramMatrix.getValues(arrayOfFloat);
    boolean bool = true;
    if (Math.round(-Math.atan2(arrayOfFloat[1], arrayOfFloat[0]) * 57.29577951308232D) == 0L) {
      bool = false;
    }
    return bool;
  }
  
  private void overrideLayout(RectF paramRectF)
  {
    this.mOverriddenLayoutRect = paramRectF;
    setX(paramRectF.left);
    setY(paramRectF.top);
    requestLayout();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    RectF localRectF = this.mOverriddenLayoutRect;
    if (localRectF == null) {
      super.onMeasure(paramInt1, paramInt2);
    } else {
      setMeasuredDimension((int)localRectF.width(), (int)this.mOverriddenLayoutRect.height());
    }
  }
  
  void setTransform(Matrix paramMatrix)
  {
    if (!hasRotation(paramMatrix))
    {
      RectF localRectF = new RectF(getLeft(), getTop(), getRight(), getBottom());
      paramMatrix.mapRect(localRectF);
      overrideLayout(localRectF);
      return;
    }
    throw new IllegalArgumentException("TransformableSurfaceView does not support rotation transformations.");
  }
}
