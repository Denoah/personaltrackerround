package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.Log;
import androidx.camera.core.impl.LensFacingCameraIdFilter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

final class Camera2LensFacingCameraIdFilter
  extends LensFacingCameraIdFilter
{
  private static final String TAG = "Camera2LensFacingCIF";
  private CameraManager mCameraManager;
  
  Camera2LensFacingCameraIdFilter(int paramInt, CameraManager paramCameraManager)
  {
    super(paramInt);
    this.mCameraManager = paramCameraManager;
  }
  
  private Integer cameraXLensFacingToCamera2LensFacing(int paramInt)
  {
    Integer localInteger = Integer.valueOf(-1);
    if (paramInt != 0)
    {
      if (paramInt == 1) {
        localInteger = Integer.valueOf(1);
      }
    }
    else {
      localInteger = Integer.valueOf(0);
    }
    return localInteger;
  }
  
  public Set<String> filter(Set<String> paramSet)
  {
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      paramSet = null;
      try
      {
        Integer localInteger = (Integer)this.mCameraManager.getCameraCharacteristics(str).get(CameraCharacteristics.LENS_FACING);
        paramSet = localInteger;
      }
      catch (CameraAccessException localCameraAccessException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Unable to retrieve info for camera with id ");
        localStringBuilder.append(str);
        localStringBuilder.append(".");
        Log.e("Camera2LensFacingCIF", localStringBuilder.toString(), localCameraAccessException);
      }
      if ((paramSet != null) && (paramSet.equals(cameraXLensFacingToCamera2LensFacing(getLensFacing())))) {
        localLinkedHashSet.add(str);
      }
    }
    return localLinkedHashSet;
  }
}
