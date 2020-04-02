package androidx.camera.core;

import android.util.Rational;

public class MeteringPoint
{
  private float mNormalizedX;
  private float mNormalizedY;
  private float mSize;
  private Rational mSurfaceAspectRatio;
  
  MeteringPoint(float paramFloat1, float paramFloat2, float paramFloat3, Rational paramRational)
  {
    this.mNormalizedX = paramFloat1;
    this.mNormalizedY = paramFloat2;
    this.mSize = paramFloat3;
    this.mSurfaceAspectRatio = paramRational;
  }
  
  public float getSize()
  {
    return this.mSize;
  }
  
  public Rational getSurfaceAspectRatio()
  {
    return this.mSurfaceAspectRatio;
  }
  
  public float getX()
  {
    return this.mNormalizedX;
  }
  
  public float getY()
  {
    return this.mNormalizedY;
  }
}
