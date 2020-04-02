package androidx.camera.view.preview.transform;

import android.graphics.Matrix;
import android.view.View;

final class CustomTransform
{
  private CustomTransform() {}
  
  private static float getRotationDegrees(float[] paramArrayOfFloat)
  {
    return (float)Math.toDegrees(-Math.atan2(paramArrayOfFloat[1], paramArrayOfFloat[0]));
  }
  
  static Transformation getTransformation(View paramView, Matrix paramMatrix)
  {
    float[] arrayOfFloat = new float[9];
    paramMatrix.getValues(arrayOfFloat);
    return new Transformation(arrayOfFloat[0], arrayOfFloat[4], paramView.getX() + arrayOfFloat[2], paramView.getY() + arrayOfFloat[5], getRotationDegrees(arrayOfFloat));
  }
}
