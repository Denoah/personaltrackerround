package androidx.camera.view;

import android.content.Context;
import android.graphics.Point;
import android.util.Pair;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

final class ScaleTypeTransform
{
  private ScaleTypeTransform() {}
  
  static Pair<Float, Float> getFillScaleWithBufferAspectRatio(View paramView1, View paramView2, Size paramSize)
  {
    if ((paramView1.getWidth() != 0) && (paramView1.getHeight() != 0) && (paramView2.getWidth() != 0) && (paramView2.getHeight() != 0) && (paramSize.getWidth() != 0) && (paramSize.getHeight() != 0))
    {
      int i = getRotationDegrees(paramView2);
      int j;
      int k;
      if (isNaturalPortrait(paramView2.getContext(), i))
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
  
  static Point getOriginOfCenteredView(View paramView1, View paramView2)
  {
    int i = (paramView2.getWidth() - paramView1.getWidth()) / 2;
    int j = (paramView2.getHeight() - paramView1.getHeight()) / 2;
    return new Point(-i, -j);
  }
  
  static int getRotationDegrees(View paramView)
  {
    paramView = (WindowManager)paramView.getContext().getSystemService("window");
    if (paramView == null) {
      return 0;
    }
    return SurfaceRotation.rotationDegreesFromSurfaceRotation(paramView.getDefaultDisplay().getRotation());
  }
  
  private static boolean isNaturalPortrait(Context paramContext, int paramInt)
  {
    paramContext = (WindowManager)paramContext.getSystemService("window");
    boolean bool1 = true;
    if (paramContext == null) {
      return true;
    }
    paramContext = paramContext.getDefaultDisplay();
    Point localPoint = new Point();
    paramContext.getRealSize(localPoint);
    int i = localPoint.x;
    int j = localPoint.y;
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
