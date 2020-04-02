package androidx.camera.view;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.SurfaceTexture;
import android.view.TextureView;
import androidx.camera.core.MeteringPointFactory;

public class TextureViewMeteringPointFactory
  extends MeteringPointFactory
{
  private final TextureView mTextureView;
  
  public TextureViewMeteringPointFactory(TextureView paramTextureView)
  {
    this.mTextureView = paramTextureView;
  }
  
  private Matrix glMatrixToGraphicsMatrix(float[] paramArrayOfFloat)
  {
    float f1 = paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[4];
    float f3 = paramArrayOfFloat[12];
    float f4 = paramArrayOfFloat[1];
    float f5 = paramArrayOfFloat[5];
    float f6 = paramArrayOfFloat[13];
    float f7 = paramArrayOfFloat[3];
    float f8 = paramArrayOfFloat[7];
    float f9 = paramArrayOfFloat[15];
    paramArrayOfFloat = new Matrix();
    paramArrayOfFloat.setValues(new float[] { f1, f2, f3, f4, f5, f6, f7, f8, f9 });
    return paramArrayOfFloat;
  }
  
  protected PointF convertPoint(float paramFloat1, float paramFloat2)
  {
    Object localObject1 = new Matrix();
    this.mTextureView.getTransform((Matrix)localObject1);
    Object localObject2 = new Matrix();
    ((Matrix)localObject1).invert((Matrix)localObject2);
    localObject1 = new float[2];
    localObject1[0] = paramFloat1;
    localObject1[1] = paramFloat2;
    ((Matrix)localObject2).mapPoints((float[])localObject1);
    localObject2 = new float[16];
    this.mTextureView.getSurfaceTexture().getTransformMatrix((float[])localObject2);
    localObject2 = glMatrixToGraphicsMatrix((float[])localObject2);
    float[] arrayOfFloat = new float[2];
    localObject1[0] /= this.mTextureView.getWidth();
    arrayOfFloat[1] = ((this.mTextureView.getHeight() - localObject1[1]) / this.mTextureView.getHeight());
    ((Matrix)localObject2).mapPoints(arrayOfFloat);
    return new PointF(arrayOfFloat[0], arrayOfFloat[1]);
  }
}
