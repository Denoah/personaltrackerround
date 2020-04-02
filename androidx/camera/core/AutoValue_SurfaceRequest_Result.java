package androidx.camera.core;

import android.view.Surface;

final class AutoValue_SurfaceRequest_Result
  extends SurfaceRequest.Result
{
  private final int resultCode;
  private final Surface surface;
  
  AutoValue_SurfaceRequest_Result(int paramInt, Surface paramSurface)
  {
    this.resultCode = paramInt;
    if (paramSurface != null)
    {
      this.surface = paramSurface;
      return;
    }
    throw new NullPointerException("Null surface");
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == this) {
      return true;
    }
    if ((paramObject instanceof SurfaceRequest.Result))
    {
      paramObject = (SurfaceRequest.Result)paramObject;
      if ((this.resultCode != paramObject.getResultCode()) || (!this.surface.equals(paramObject.getSurface()))) {
        bool = false;
      }
      return bool;
    }
    return false;
  }
  
  public int getResultCode()
  {
    return this.resultCode;
  }
  
  public Surface getSurface()
  {
    return this.surface;
  }
  
  public int hashCode()
  {
    return (this.resultCode ^ 0xF4243) * 1000003 ^ this.surface.hashCode();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Result{resultCode=");
    localStringBuilder.append(this.resultCode);
    localStringBuilder.append(", surface=");
    localStringBuilder.append(this.surface);
    localStringBuilder.append("}");
    return localStringBuilder.toString();
  }
}
