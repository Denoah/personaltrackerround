package androidx.camera.core.impl;

import android.content.Context;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.UseCase;
import java.util.List;
import java.util.Map;

public abstract interface CameraDeviceSurfaceManager
{
  public abstract boolean checkSupported(String paramString, List<SurfaceConfig> paramList);
  
  public abstract Rational getCorrectedAspectRatio(String paramString, int paramInt);
  
  public abstract Size getMaxOutputSize(String paramString, int paramInt);
  
  public abstract Size getPreviewSize();
  
  public abstract Map<UseCase, Size> getSuggestedResolutions(String paramString, List<UseCase> paramList1, List<UseCase> paramList2);
  
  public abstract boolean requiresCorrectedAspectRatio(String paramString);
  
  public abstract SurfaceConfig transformSurfaceConfig(String paramString, int paramInt, Size paramSize);
  
  public static abstract interface Provider
  {
    public abstract CameraDeviceSurfaceManager newInstance(Context paramContext);
  }
}
