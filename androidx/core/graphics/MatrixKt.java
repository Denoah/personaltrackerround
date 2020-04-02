package androidx.core.graphics;

import android.graphics.Matrix;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\026\n\000\n\002\030\002\n\000\n\002\020\007\n\002\b\013\n\002\020\024\n\000\032\"\020\000\032\0020\0012\006\020\002\032\0020\0032\b\b\002\020\004\032\0020\0032\b\b\002\020\005\032\0020\003\032\032\020\006\032\0020\0012\b\b\002\020\007\032\0020\0032\b\b\002\020\b\032\0020\003\032\032\020\t\032\0020\0012\b\b\002\020\n\032\0020\0032\b\b\002\020\013\032\0020\003\032\025\020\f\032\0020\001*\0020\0012\006\020\r\032\0020\001H?\n\032\r\020\016\032\0020\017*\0020\001H?\b?\006\020"}, d2={"rotationMatrix", "Landroid/graphics/Matrix;", "degrees", "", "px", "py", "scaleMatrix", "sx", "sy", "translationMatrix", "tx", "ty", "times", "m", "values", "", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class MatrixKt
{
  public static final Matrix rotationMatrix(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    Matrix localMatrix = new Matrix();
    localMatrix.setRotate(paramFloat1, paramFloat2, paramFloat3);
    return localMatrix;
  }
  
  public static final Matrix scaleMatrix(float paramFloat1, float paramFloat2)
  {
    Matrix localMatrix = new Matrix();
    localMatrix.setScale(paramFloat1, paramFloat2);
    return localMatrix;
  }
  
  public static final Matrix times(Matrix paramMatrix1, Matrix paramMatrix2)
  {
    Intrinsics.checkParameterIsNotNull(paramMatrix1, "$this$times");
    Intrinsics.checkParameterIsNotNull(paramMatrix2, "m");
    paramMatrix1 = new Matrix(paramMatrix1);
    paramMatrix1.preConcat(paramMatrix2);
    return paramMatrix1;
  }
  
  public static final Matrix translationMatrix(float paramFloat1, float paramFloat2)
  {
    Matrix localMatrix = new Matrix();
    localMatrix.setTranslate(paramFloat1, paramFloat2);
    return localMatrix;
  }
  
  public static final float[] values(Matrix paramMatrix)
  {
    Intrinsics.checkParameterIsNotNull(paramMatrix, "$this$values");
    float[] arrayOfFloat = new float[9];
    paramMatrix.getValues(arrayOfFloat);
    return arrayOfFloat;
  }
}
