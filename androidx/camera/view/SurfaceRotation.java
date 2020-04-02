package androidx.camera.view;

final class SurfaceRotation
{
  private SurfaceRotation() {}
  
  static int rotationDegreesFromSurfaceRotation(int paramInt)
  {
    if (paramInt != 0)
    {
      if (paramInt != 1)
      {
        if (paramInt != 2)
        {
          if (paramInt == 3) {
            return 270;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Unsupported surface rotation constant: ");
          localStringBuilder.append(paramInt);
          throw new UnsupportedOperationException(localStringBuilder.toString());
        }
        return 180;
      }
      return 90;
    }
    return 0;
  }
}
