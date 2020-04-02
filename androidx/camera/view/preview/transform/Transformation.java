package androidx.camera.view.preview.transform;

final class Transformation
{
  private final float mOriginX;
  private final float mOriginY;
  private final float mRotation;
  private final float mScaleX;
  private final float mScaleY;
  
  Transformation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    this.mScaleX = paramFloat1;
    this.mScaleY = paramFloat2;
    this.mOriginX = paramFloat3;
    this.mOriginY = paramFloat4;
    this.mRotation = paramFloat5;
  }
  
  float getOriginX()
  {
    return this.mOriginX;
  }
  
  float getOriginY()
  {
    return this.mOriginY;
  }
  
  float getRotation()
  {
    return this.mRotation;
  }
  
  float getScaleX()
  {
    return this.mScaleX;
  }
  
  float getScaleY()
  {
    return this.mScaleY;
  }
}
