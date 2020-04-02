package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import android.view.Surface;
import androidx.core.util.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class OutputConfigurationCompatApi24Impl
  extends OutputConfigurationCompatBaseImpl
{
  OutputConfigurationCompatApi24Impl(Surface paramSurface)
  {
    this(new OutputConfigurationParamsApi24(new OutputConfiguration(paramSurface)));
  }
  
  OutputConfigurationCompatApi24Impl(Object paramObject)
  {
    super(paramObject);
  }
  
  static OutputConfigurationCompatApi24Impl wrap(OutputConfiguration paramOutputConfiguration)
  {
    return new OutputConfigurationCompatApi24Impl(new OutputConfigurationParamsApi24(paramOutputConfiguration));
  }
  
  public void enableSurfaceSharing()
  {
    ((OutputConfigurationParamsApi24)this.mObject).mIsShared = true;
  }
  
  public Object getOutputConfiguration()
  {
    Preconditions.checkArgument(this.mObject instanceof OutputConfigurationParamsApi24);
    return ((OutputConfigurationParamsApi24)this.mObject).mOutputConfiguration;
  }
  
  public String getPhysicalCameraId()
  {
    return ((OutputConfigurationParamsApi24)this.mObject).mPhysicalCameraId;
  }
  
  public Surface getSurface()
  {
    return ((OutputConfiguration)getOutputConfiguration()).getSurface();
  }
  
  public int getSurfaceGroupId()
  {
    return ((OutputConfiguration)getOutputConfiguration()).getSurfaceGroupId();
  }
  
  public List<Surface> getSurfaces()
  {
    return Collections.singletonList(getSurface());
  }
  
  boolean isSurfaceSharingEnabled()
  {
    return ((OutputConfigurationParamsApi24)this.mObject).mIsShared;
  }
  
  public void setPhysicalCameraId(String paramString)
  {
    ((OutputConfigurationParamsApi24)this.mObject).mPhysicalCameraId = paramString;
  }
  
  private static final class OutputConfigurationParamsApi24
  {
    boolean mIsShared;
    final OutputConfiguration mOutputConfiguration;
    String mPhysicalCameraId;
    
    OutputConfigurationParamsApi24(OutputConfiguration paramOutputConfiguration)
    {
      this.mOutputConfiguration = paramOutputConfiguration;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof OutputConfigurationParamsApi24;
      boolean bool2 = false;
      if (!bool1) {
        return false;
      }
      paramObject = (OutputConfigurationParamsApi24)paramObject;
      bool1 = bool2;
      if (Objects.equals(this.mOutputConfiguration, paramObject.mOutputConfiguration))
      {
        bool1 = bool2;
        if (this.mIsShared == paramObject.mIsShared)
        {
          bool1 = bool2;
          if (Objects.equals(this.mPhysicalCameraId, paramObject.mPhysicalCameraId)) {
            bool1 = true;
          }
        }
      }
      return bool1;
    }
    
    public int hashCode()
    {
      int i = this.mOutputConfiguration.hashCode() ^ 0x1F;
      int j = this.mIsShared ^ (i << 5) - i;
      String str = this.mPhysicalCameraId;
      if (str == null) {
        i = 0;
      } else {
        i = str.hashCode();
      }
      return i ^ (j << 5) - j;
    }
  }
}
