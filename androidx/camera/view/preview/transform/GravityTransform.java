package androidx.camera.view.preview.transform;

import android.graphics.Point;
import android.view.View;

final class GravityTransform
{
  private GravityTransform() {}
  
  static Point center(View paramView1, View paramView2)
  {
    return new Point((paramView1.getWidth() - paramView2.getWidth()) / 2, (paramView1.getHeight() - paramView2.getHeight()) / 2);
  }
  
  static Point end(View paramView1, View paramView2)
  {
    return new Point(paramView1.getWidth() - paramView2.getWidth(), paramView1.getHeight() - paramView2.getHeight());
  }
  
  static Point start()
  {
    return new Point(0, 0);
  }
}
