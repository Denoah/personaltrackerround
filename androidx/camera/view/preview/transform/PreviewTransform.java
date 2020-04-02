package androidx.camera.view.preview.transform;

import android.graphics.Matrix;
import android.util.Size;
import android.view.View;
import androidx.camera.view.PreviewView.ScaleType;

public final class PreviewTransform
{
  private PreviewTransform() {}
  
  private static void applyTransformationToPreview(Transformation paramTransformation, View paramView)
  {
    paramView.setScaleX(paramTransformation.getScaleX());
    paramView.setScaleY(paramTransformation.getScaleY());
    paramView.setX(paramTransformation.getOriginX());
    paramView.setY(paramTransformation.getOriginY());
    paramView.setRotation(paramTransformation.getRotation());
  }
  
  public static void transform(View paramView, Matrix paramMatrix)
  {
    applyTransformationToPreview(CustomTransform.getTransformation(paramView, paramMatrix), paramView);
  }
  
  public static void transform(View paramView1, View paramView2, Size paramSize, PreviewView.ScaleType paramScaleType)
  {
    applyTransformationToPreview(ScaleTypeTransform.getTransformation(paramView1, paramView2, paramSize, paramScaleType), paramView2);
  }
}
