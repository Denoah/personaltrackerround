package androidx.camera.core;

import android.graphics.PointF;
import android.util.Rational;
import android.util.Size;
import java.util.Iterator;
import java.util.Set;

public class SurfaceOrientedMeteringPointFactory
  extends MeteringPointFactory
{
  private final float mHeight;
  private final float mWidth;
  
  public SurfaceOrientedMeteringPointFactory(float paramFloat1, float paramFloat2)
  {
    this.mWidth = paramFloat1;
    this.mHeight = paramFloat2;
  }
  
  public SurfaceOrientedMeteringPointFactory(float paramFloat1, float paramFloat2, UseCase paramUseCase)
  {
    super(getUseCaseAspectRatio(paramUseCase));
    this.mWidth = paramFloat1;
    this.mHeight = paramFloat2;
  }
  
  private static Rational getUseCaseAspectRatio(UseCase paramUseCase)
  {
    Object localObject1 = null;
    if (paramUseCase == null) {
      return null;
    }
    Object localObject2 = paramUseCase.getAttachedCameraIds();
    if (!((Set)localObject2).isEmpty())
    {
      localObject2 = ((Set)localObject2).iterator();
      if (((Iterator)localObject2).hasNext())
      {
        paramUseCase = paramUseCase.getAttachedSurfaceResolution((String)((Iterator)localObject2).next());
        localObject1 = new Rational(paramUseCase.getWidth(), paramUseCase.getHeight());
      }
      return localObject1;
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("UseCase ");
    ((StringBuilder)localObject1).append(paramUseCase);
    ((StringBuilder)localObject1).append(" is not bound.");
    throw new IllegalStateException(((StringBuilder)localObject1).toString());
  }
  
  protected PointF convertPoint(float paramFloat1, float paramFloat2)
  {
    return new PointF(paramFloat1 / this.mWidth, paramFloat2 / this.mHeight);
  }
}
