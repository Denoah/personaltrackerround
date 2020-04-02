package androidx.camera.core;

import android.graphics.PointF;
import android.util.Rational;

public abstract class MeteringPointFactory
{
  private Rational mSurfaceAspectRatio;
  
  public MeteringPointFactory()
  {
    this(null);
  }
  
  public MeteringPointFactory(Rational paramRational)
  {
    this.mSurfaceAspectRatio = paramRational;
  }
  
  public static float getDefaultPointSize()
  {
    return 0.15F;
  }
  
  protected abstract PointF convertPoint(float paramFloat1, float paramFloat2);
  
  public final MeteringPoint createPoint(float paramFloat1, float paramFloat2)
  {
    return createPoint(paramFloat1, paramFloat2, getDefaultPointSize());
  }
  
  public final MeteringPoint createPoint(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    PointF localPointF = convertPoint(paramFloat1, paramFloat2);
    return new MeteringPoint(localPointF.x, localPointF.y, paramFloat3, this.mSurfaceAspectRatio);
  }
}
