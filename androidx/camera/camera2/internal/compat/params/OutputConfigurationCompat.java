package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import android.os.Build.VERSION;
import android.util.Size;
import android.view.Surface;
import java.util.List;

public final class OutputConfigurationCompat
{
  public static final int SURFACE_GROUP_ID_NONE = -1;
  private final OutputConfigurationCompatImpl mImpl;
  
  public <T> OutputConfigurationCompat(Size paramSize, Class<T> paramClass)
  {
    paramSize = new OutputConfiguration(paramSize, paramClass);
    if (Build.VERSION.SDK_INT >= 28) {
      this.mImpl = OutputConfigurationCompatApi28Impl.wrap(paramSize);
    } else {
      this.mImpl = OutputConfigurationCompatApi26Impl.wrap(paramSize);
    }
  }
  
  public OutputConfigurationCompat(Surface paramSurface)
  {
    if (Build.VERSION.SDK_INT >= 28) {
      this.mImpl = new OutputConfigurationCompatApi28Impl(paramSurface);
    } else if (Build.VERSION.SDK_INT >= 26) {
      this.mImpl = new OutputConfigurationCompatApi26Impl(paramSurface);
    } else if (Build.VERSION.SDK_INT >= 24) {
      this.mImpl = new OutputConfigurationCompatApi24Impl(paramSurface);
    } else {
      this.mImpl = new OutputConfigurationCompatBaseImpl(paramSurface);
    }
  }
  
  private OutputConfigurationCompat(OutputConfigurationCompatImpl paramOutputConfigurationCompatImpl)
  {
    this.mImpl = paramOutputConfigurationCompatImpl;
  }
  
  public static OutputConfigurationCompat wrap(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    if (Build.VERSION.SDK_INT >= 28) {
      paramObject = OutputConfigurationCompatApi28Impl.wrap((OutputConfiguration)paramObject);
    } else if (Build.VERSION.SDK_INT >= 26) {
      paramObject = OutputConfigurationCompatApi26Impl.wrap((OutputConfiguration)paramObject);
    } else if (Build.VERSION.SDK_INT >= 24) {
      paramObject = OutputConfigurationCompatApi24Impl.wrap((OutputConfiguration)paramObject);
    } else {
      paramObject = null;
    }
    if (paramObject == null) {
      return null;
    }
    return new OutputConfigurationCompat(paramObject);
  }
  
  public void addSurface(Surface paramSurface)
  {
    this.mImpl.addSurface(paramSurface);
  }
  
  public void enableSurfaceSharing()
  {
    this.mImpl.enableSurfaceSharing();
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof OutputConfigurationCompat)) {
      return false;
    }
    return this.mImpl.equals(((OutputConfigurationCompat)paramObject).mImpl);
  }
  
  public int getMaxSharedSurfaceCount()
  {
    return this.mImpl.getMaxSharedSurfaceCount();
  }
  
  public String getPhysicalCameraId()
  {
    return this.mImpl.getPhysicalCameraId();
  }
  
  public Surface getSurface()
  {
    return this.mImpl.getSurface();
  }
  
  public int getSurfaceGroupId()
  {
    return this.mImpl.getSurfaceGroupId();
  }
  
  public List<Surface> getSurfaces()
  {
    return this.mImpl.getSurfaces();
  }
  
  public int hashCode()
  {
    return this.mImpl.hashCode();
  }
  
  public void removeSurface(Surface paramSurface)
  {
    this.mImpl.removeSurface(paramSurface);
  }
  
  public void setPhysicalCameraId(String paramString)
  {
    this.mImpl.setPhysicalCameraId(paramString);
  }
  
  public Object unwrap()
  {
    return this.mImpl.getOutputConfiguration();
  }
  
  static abstract interface OutputConfigurationCompatImpl
  {
    public abstract void addSurface(Surface paramSurface);
    
    public abstract void enableSurfaceSharing();
    
    public abstract int getMaxSharedSurfaceCount();
    
    public abstract Object getOutputConfiguration();
    
    public abstract String getPhysicalCameraId();
    
    public abstract Surface getSurface();
    
    public abstract int getSurfaceGroupId();
    
    public abstract List<Surface> getSurfaces();
    
    public abstract void removeSurface(Surface paramSurface);
    
    public abstract void setPhysicalCameraId(String paramString);
  }
}
