package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import android.view.Surface;
import androidx.core.util.Preconditions;

class OutputConfigurationCompatApi28Impl
  extends OutputConfigurationCompatApi26Impl
{
  OutputConfigurationCompatApi28Impl(Surface paramSurface)
  {
    super(new OutputConfiguration(paramSurface));
  }
  
  OutputConfigurationCompatApi28Impl(Object paramObject)
  {
    super(paramObject);
  }
  
  static OutputConfigurationCompatApi28Impl wrap(OutputConfiguration paramOutputConfiguration)
  {
    return new OutputConfigurationCompatApi28Impl(paramOutputConfiguration);
  }
  
  public int getMaxSharedSurfaceCount()
  {
    return ((OutputConfiguration)getOutputConfiguration()).getMaxSharedSurfaceCount();
  }
  
  public Object getOutputConfiguration()
  {
    Preconditions.checkArgument(this.mObject instanceof OutputConfiguration);
    return this.mObject;
  }
  
  public String getPhysicalCameraId()
  {
    return null;
  }
  
  public void removeSurface(Surface paramSurface)
  {
    ((OutputConfiguration)getOutputConfiguration()).removeSurface(paramSurface);
  }
  
  public void setPhysicalCameraId(String paramString)
  {
    ((OutputConfiguration)getOutputConfiguration()).setPhysicalCameraId(paramString);
  }
}
