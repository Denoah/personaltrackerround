package androidx.camera.view.preview.transform;

import android.graphics.Point;
import android.util.Pair;
import android.util.Size;
import android.view.View;
import androidx.camera.view.PreviewView.ScaleType;

final class ScaleTypeTransform
{
  private ScaleTypeTransform() {}
  
  private static Point getGravityOrigin(View paramView1, View paramView2, PreviewView.ScaleType paramScaleType)
  {
    switch (1.$SwitchMap$androidx$camera$view$PreviewView$ScaleType[paramScaleType.ordinal()])
    {
    default: 
      paramView1 = new StringBuilder();
      paramView1.append("Unknown scale type ");
      paramView1.append(paramScaleType);
      throw new IllegalArgumentException(paramView1.toString());
    case 3: 
    case 6: 
      return GravityTransform.end(paramView1, paramView2);
    case 2: 
    case 5: 
      return GravityTransform.center(paramView1, paramView2);
    }
    return GravityTransform.start();
  }
  
  private static Pair<Float, Float> getScaleXY(View paramView1, View paramView2, Size paramSize, PreviewView.ScaleType paramScaleType)
  {
    switch (1.$SwitchMap$androidx$camera$view$PreviewView$ScaleType[paramScaleType.ordinal()])
    {
    default: 
      paramView1 = new StringBuilder();
      paramView1.append("Unknown scale type ");
      paramView1.append(paramScaleType);
      throw new IllegalArgumentException(paramView1.toString());
    case 4: 
    case 5: 
    case 6: 
      return ScaleTransform.fit(paramView1, paramView2, paramSize);
    }
    return ScaleTransform.fill(paramView1, paramView2, paramSize);
  }
  
  static Transformation getTransformation(View paramView1, View paramView2, Size paramSize, PreviewView.ScaleType paramScaleType)
  {
    paramSize = getScaleXY(paramView1, paramView2, paramSize, paramScaleType);
    paramView1 = getGravityOrigin(paramView1, paramView2, paramScaleType);
    float f = -RotationTransform.getRotationDegrees(paramView2);
    return new Transformation(((Float)paramSize.first).floatValue(), ((Float)paramSize.second).floatValue(), paramView1.x, paramView1.y, f);
  }
}
