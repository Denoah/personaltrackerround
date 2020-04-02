package androidx.camera.camera2.internal.compat.params;

import android.hardware.camera2.params.OutputConfiguration;
import android.util.Log;
import android.view.Surface;
import androidx.core.util.Preconditions;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

class OutputConfigurationCompatApi26Impl
  extends OutputConfigurationCompatApi24Impl
{
  private static final String MAX_SHARED_SURFACES_COUNT_FIELD = "MAX_SURFACES_COUNT";
  private static final String SURFACES_FIELD = "mSurfaces";
  
  OutputConfigurationCompatApi26Impl(Surface paramSurface)
  {
    this(new OutputConfigurationParamsApi26(new OutputConfiguration(paramSurface)));
  }
  
  OutputConfigurationCompatApi26Impl(Object paramObject)
  {
    super(paramObject);
  }
  
  private static int getMaxSharedSurfaceCountApi26()
    throws NoSuchFieldException, IllegalAccessException
  {
    Field localField = OutputConfiguration.class.getDeclaredField("MAX_SURFACES_COUNT");
    localField.setAccessible(true);
    return localField.getInt(null);
  }
  
  private static List<Surface> getMutableSurfaceListApi26(OutputConfiguration paramOutputConfiguration)
    throws NoSuchFieldException, IllegalAccessException
  {
    Field localField = OutputConfiguration.class.getDeclaredField("mSurfaces");
    localField.setAccessible(true);
    return (List)localField.get(paramOutputConfiguration);
  }
  
  static OutputConfigurationCompatApi26Impl wrap(OutputConfiguration paramOutputConfiguration)
  {
    return new OutputConfigurationCompatApi26Impl(new OutputConfigurationParamsApi26(paramOutputConfiguration));
  }
  
  public void addSurface(Surface paramSurface)
  {
    ((OutputConfiguration)getOutputConfiguration()).addSurface(paramSurface);
  }
  
  public void enableSurfaceSharing()
  {
    ((OutputConfiguration)getOutputConfiguration()).enableSurfaceSharing();
  }
  
  public int getMaxSharedSurfaceCount()
  {
    try
    {
      int i = getMaxSharedSurfaceCountApi26();
      return i;
    }
    catch (IllegalAccessException localIllegalAccessException) {}catch (NoSuchFieldException localNoSuchFieldException) {}
    Log.e("OutputConfigCompat", "Unable to retrieve max shared surface count.", localNoSuchFieldException);
    return super.getMaxSharedSurfaceCount();
  }
  
  public Object getOutputConfiguration()
  {
    Preconditions.checkArgument(this.mObject instanceof OutputConfigurationParamsApi26);
    return ((OutputConfigurationParamsApi26)this.mObject).mOutputConfiguration;
  }
  
  public String getPhysicalCameraId()
  {
    return ((OutputConfigurationParamsApi26)this.mObject).mPhysicalCameraId;
  }
  
  public List<Surface> getSurfaces()
  {
    return ((OutputConfiguration)getOutputConfiguration()).getSurfaces();
  }
  
  final boolean isSurfaceSharingEnabled()
  {
    throw new AssertionError("isSurfaceSharingEnabled() should not be called on API >= 26");
  }
  
  public void removeSurface(Surface paramSurface)
  {
    if (getSurface() != paramSurface)
    {
      try
      {
        if (getMutableSurfaceListApi26((OutputConfiguration)getOutputConfiguration()).remove(paramSurface)) {
          break label56;
        }
        paramSurface = new java/lang/IllegalArgumentException;
        paramSurface.<init>("Surface is not part of this output configuration");
        throw paramSurface;
      }
      catch (NoSuchFieldException paramSurface) {}catch (IllegalAccessException paramSurface) {}
      Log.e("OutputConfigCompat", "Unable to remove surface from this output configuration.", paramSurface);
      label56:
      return;
    }
    throw new IllegalArgumentException("Cannot remove surface associated with this output configuration");
  }
  
  public void setPhysicalCameraId(String paramString)
  {
    ((OutputConfigurationParamsApi26)this.mObject).mPhysicalCameraId = paramString;
  }
  
  private static final class OutputConfigurationParamsApi26
  {
    final OutputConfiguration mOutputConfiguration;
    String mPhysicalCameraId;
    
    OutputConfigurationParamsApi26(OutputConfiguration paramOutputConfiguration)
    {
      this.mOutputConfiguration = paramOutputConfiguration;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof OutputConfigurationParamsApi26;
      boolean bool2 = false;
      if (!bool1) {
        return false;
      }
      paramObject = (OutputConfigurationParamsApi26)paramObject;
      bool1 = bool2;
      if (Objects.equals(this.mOutputConfiguration, paramObject.mOutputConfiguration))
      {
        bool1 = bool2;
        if (Objects.equals(this.mPhysicalCameraId, paramObject.mPhysicalCameraId)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    
    public int hashCode()
    {
      int i = this.mOutputConfiguration.hashCode() ^ 0x1F;
      String str = this.mPhysicalCameraId;
      int j;
      if (str == null) {
        j = 0;
      } else {
        j = str.hashCode();
      }
      return j ^ (i << 5) - i;
    }
  }
}
