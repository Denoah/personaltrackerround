package androidx.camera.view.preview.transform;

import android.view.Display;
import android.view.View;

final class RotationTransform
{
  private RotationTransform() {}
  
  static float getRotationDegrees(View paramView)
  {
    paramView = paramView.getDisplay();
    if (paramView == null) {
      return 0.0F;
    }
    return SurfaceRotation.rotationDegreesFromSurfaceRotation(paramView.getRotation());
  }
}
