package androidx.camera.camera2.internal.compat.params;

import android.os.Build.VERSION;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import androidx.core.util.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class OutputConfigurationCompatBaseImpl
  implements OutputConfigurationCompat.OutputConfigurationCompatImpl
{
  static final String TAG = "OutputConfigCompat";
  final Object mObject;
  
  OutputConfigurationCompatBaseImpl(Surface paramSurface)
  {
    this.mObject = new OutputConfigurationParamsApi21(paramSurface);
  }
  
  OutputConfigurationCompatBaseImpl(Object paramObject)
  {
    this.mObject = paramObject;
  }
  
  public void addSurface(Surface paramSurface)
  {
    Preconditions.checkNotNull(paramSurface, "Surface must not be null");
    if (getSurface() != paramSurface)
    {
      if (!isSurfaceSharingEnabled()) {
        throw new IllegalStateException("Cannot have 2 surfaces for a non-sharing configuration");
      }
      throw new IllegalArgumentException("Exceeds maximum number of surfaces");
    }
    throw new IllegalStateException("Surface is already added!");
  }
  
  public void enableSurfaceSharing()
  {
    ((OutputConfigurationParamsApi21)this.mObject).mIsShared = true;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof OutputConfigurationCompatBaseImpl)) {
      return false;
    }
    return Objects.equals(this.mObject, ((OutputConfigurationCompatBaseImpl)paramObject).mObject);
  }
  
  public int getMaxSharedSurfaceCount()
  {
    return 1;
  }
  
  public Object getOutputConfiguration()
  {
    return null;
  }
  
  public String getPhysicalCameraId()
  {
    return ((OutputConfigurationParamsApi21)this.mObject).mPhysicalCameraId;
  }
  
  public Surface getSurface()
  {
    List localList = ((OutputConfigurationParamsApi21)this.mObject).mSurfaces;
    if (localList.size() == 0) {
      return null;
    }
    return (Surface)localList.get(0);
  }
  
  public int getSurfaceGroupId()
  {
    return -1;
  }
  
  public List<Surface> getSurfaces()
  {
    return ((OutputConfigurationParamsApi21)this.mObject).mSurfaces;
  }
  
  public int hashCode()
  {
    return this.mObject.hashCode();
  }
  
  boolean isSurfaceSharingEnabled()
  {
    return ((OutputConfigurationParamsApi21)this.mObject).mIsShared;
  }
  
  public void removeSurface(Surface paramSurface)
  {
    if (getSurface() == paramSurface) {
      throw new IllegalArgumentException("Cannot remove surface associated with this output configuration");
    }
    throw new IllegalArgumentException("Surface is not part of this output configuration");
  }
  
  public void setPhysicalCameraId(String paramString)
  {
    ((OutputConfigurationParamsApi21)this.mObject).mPhysicalCameraId = paramString;
  }
  
  private static final class OutputConfigurationParamsApi21
  {
    private static final String DETECT_SURFACE_TYPE_METHOD = "detectSurfaceType";
    private static final String GET_GENERATION_ID_METHOD = "getGenerationId";
    private static final String GET_SURFACE_SIZE_METHOD = "getSurfaceSize";
    private static final String LEGACY_CAMERA_DEVICE_CLASS = "android.hardware.camera2.legacy.LegacyCameraDevice";
    static final int MAX_SURFACES_COUNT = 1;
    final int mConfiguredFormat;
    final int mConfiguredGenerationId;
    final Size mConfiguredSize;
    boolean mIsShared = false;
    String mPhysicalCameraId;
    final List<Surface> mSurfaces;
    
    OutputConfigurationParamsApi21(Surface paramSurface)
    {
      Preconditions.checkNotNull(paramSurface, "Surface must not be null");
      this.mSurfaces = Collections.singletonList(paramSurface);
      this.mConfiguredSize = getSurfaceSize(paramSurface);
      this.mConfiguredFormat = getSurfaceFormat(paramSurface);
      this.mConfiguredGenerationId = getSurfaceGenerationId(paramSurface);
    }
    
    private static int getSurfaceFormat(Surface paramSurface)
    {
      try
      {
        Method localMethod = Class.forName("android.hardware.camera2.legacy.LegacyCameraDevice").getDeclaredMethod("detectSurfaceType", new Class[] { Surface.class });
        if (Build.VERSION.SDK_INT < 22) {
          localMethod.setAccessible(true);
        }
        int i = ((Integer)localMethod.invoke(null, new Object[] { paramSurface })).intValue();
        return i;
      }
      catch (InvocationTargetException paramSurface) {}catch (IllegalAccessException paramSurface) {}catch (NoSuchMethodException paramSurface) {}catch (ClassNotFoundException paramSurface) {}
      Log.e("OutputConfigCompat", "Unable to retrieve surface format.", paramSurface);
      return 0;
    }
    
    private static int getSurfaceGenerationId(Surface paramSurface)
    {
      try
      {
        int i = ((Integer)Surface.class.getDeclaredMethod("getGenerationId", new Class[0]).invoke(paramSurface, new Object[0])).intValue();
        return i;
      }
      catch (InvocationTargetException paramSurface) {}catch (IllegalAccessException paramSurface) {}catch (NoSuchMethodException paramSurface) {}
      Log.e("OutputConfigCompat", "Unable to retrieve surface generation id.", paramSurface);
      return -1;
    }
    
    private static Size getSurfaceSize(Surface paramSurface)
    {
      try
      {
        Method localMethod = Class.forName("android.hardware.camera2.legacy.LegacyCameraDevice").getDeclaredMethod("getSurfaceSize", new Class[] { Surface.class });
        localMethod.setAccessible(true);
        paramSurface = (Size)localMethod.invoke(null, new Object[] { paramSurface });
        return paramSurface;
      }
      catch (InvocationTargetException paramSurface) {}catch (IllegalAccessException paramSurface) {}catch (NoSuchMethodException paramSurface) {}catch (ClassNotFoundException paramSurface) {}
      Log.e("OutputConfigCompat", "Unable to retrieve surface size.", paramSurface);
      return null;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof OutputConfigurationParamsApi21)) {
        return false;
      }
      paramObject = (OutputConfigurationParamsApi21)paramObject;
      if ((this.mConfiguredSize.equals(paramObject.mConfiguredSize)) && (this.mConfiguredFormat == paramObject.mConfiguredFormat) && (this.mConfiguredGenerationId == paramObject.mConfiguredGenerationId) && (this.mIsShared == paramObject.mIsShared) && (Objects.equals(this.mPhysicalCameraId, paramObject.mPhysicalCameraId)))
      {
        int i = Math.min(this.mSurfaces.size(), paramObject.mSurfaces.size());
        for (int j = 0; j < i; j++) {
          if (this.mSurfaces.get(j) != paramObject.mSurfaces.get(j)) {
            return false;
          }
        }
        return true;
      }
      return false;
    }
    
    public int hashCode()
    {
      int i = this.mSurfaces.hashCode() ^ 0x1F;
      i = this.mConfiguredGenerationId ^ (i << 5) - i;
      i = this.mConfiguredSize.hashCode() ^ (i << 5) - i;
      i = this.mConfiguredFormat ^ (i << 5) - i;
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
