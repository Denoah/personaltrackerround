package androidx.camera.view.preview.transform;

import android.graphics.Point;
import android.util.Pair;
import android.util.Size;
import android.view.Display;
import android.view.View;

final class ScaleTransform
{
  private ScaleTransform() {}
  
  static Pair<Float, Float> fill(View paramView1, View paramView2, Size paramSize)
  {
    if ((paramView1.getWidth() != 0) && (paramView1.getHeight() != 0) && (paramView2.getWidth() != 0) && (paramView2.getHeight() != 0) && (paramSize.getWidth() != 0) && (paramSize.getHeight() != 0))
    {
      int i = (int)RotationTransform.getRotationDegrees(paramView2);
      int j;
      int k;
      if (isNaturalPortrait(paramView2, i))
      {
        j = paramSize.getHeight();
        k = paramSize.getWidth();
      }
      else
      {
        j = paramSize.getWidth();
        k = paramSize.getHeight();
      }
      float f1 = j / paramView2.getWidth();
      float f2 = k / paramView2.getHeight();
      int m;
      int n;
      if (i != 0)
      {
        m = j;
        n = k;
        if (i != 180) {}
      }
      else
      {
        n = j;
        m = k;
      }
      float f3 = Math.max(paramView1.getWidth() / n, paramView1.getHeight() / m);
      return new Pair(Float.valueOf(f1 * f3), Float.valueOf(f2 * f3));
    }
    return new Pair(Float.valueOf(1.0F), Float.valueOf(1.0F));
  }
  
  static Pair<Float, Float> fit(View paramView1, View paramView2, Size paramSize)
  {
    paramView1 = Float.valueOf(1.0F);
    return new Pair(paramView1, paramView1);
  }
  
  private static boolean isNaturalPortrait(View paramView, int paramInt)
  {
    Display localDisplay = paramView.getDisplay();
    boolean bool1 = true;
    if (localDisplay == null) {
      return true;
    }
    paramView = new Point();
    localDisplay.getRealSize(paramView);
    int i = paramView.x;
    int j = paramView.y;
    boolean bool2;
    if ((paramInt == 0) || (paramInt == 180))
    {
      bool2 = bool1;
      if (i < j) {}
    }
    else if (((paramInt == 90) || (paramInt == 270)) && (i >= j))
    {
      bool2 = bool1;
    }
    else
    {
      bool2 = false;
    }
    return bool2;
  }
}
