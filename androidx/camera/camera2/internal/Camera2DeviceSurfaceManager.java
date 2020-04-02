package androidx.camera.camera2.internal;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Rational;
import android.util.Size;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.SurfaceSizeDefinition;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Camera2DeviceSurfaceManager
  implements CameraDeviceSurfaceManager
{
  private static final Size MAXIMUM_PREVIEW_SIZE = new Size(1920, 1080);
  private static final String TAG = "Camera2DeviceSurfaceManager";
  private final CamcorderProfileHelper mCamcorderProfileHelper;
  private final Map<String, SupportedSurfaceCombination> mCameraSupportedSurfaceCombinationMap = new HashMap();
  
  public Camera2DeviceSurfaceManager(Context paramContext)
  {
    this(paramContext, _..Lambda.U7YGfX89lmJkjkmxjZTvW1_ZUo0.INSTANCE);
  }
  
  Camera2DeviceSurfaceManager(Context paramContext, CamcorderProfileHelper paramCamcorderProfileHelper)
  {
    Preconditions.checkNotNull(paramCamcorderProfileHelper);
    this.mCamcorderProfileHelper = paramCamcorderProfileHelper;
    init(paramContext);
  }
  
  private void init(Context paramContext)
  {
    Preconditions.checkNotNull(paramContext);
    Object localObject = (CameraManager)Preconditions.checkNotNull((CameraManager)paramContext.getSystemService("camera"));
    try
    {
      for (String str : ((CameraManager)localObject).getCameraIdList())
      {
        localObject = this.mCameraSupportedSurfaceCombinationMap;
        SupportedSurfaceCombination localSupportedSurfaceCombination = new androidx/camera/camera2/internal/SupportedSurfaceCombination;
        localSupportedSurfaceCombination.<init>(paramContext, str, this.mCamcorderProfileHelper);
        ((Map)localObject).put(str, localSupportedSurfaceCombination);
      }
      return;
    }
    catch (CameraAccessException paramContext)
    {
      throw new IllegalArgumentException("Fail to get camera id list", paramContext);
    }
  }
  
  public boolean checkSupported(String paramString, List<SurfaceConfig> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      paramString = (SupportedSurfaceCombination)this.mCameraSupportedSurfaceCombinationMap.get(paramString);
      boolean bool = false;
      if (paramString != null) {
        bool = paramString.checkSupported(paramList);
      }
      return bool;
    }
    return true;
  }
  
  public Rational getCorrectedAspectRatio(String paramString, int paramInt)
  {
    Object localObject = (SupportedSurfaceCombination)this.mCameraSupportedSurfaceCombinationMap.get(paramString);
    if (localObject != null) {
      return ((SupportedSurfaceCombination)localObject).getCorrectedAspectRatio(paramInt);
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Fail to find supported surface info - CameraId:");
    ((StringBuilder)localObject).append(paramString);
    throw new IllegalArgumentException(((StringBuilder)localObject).toString());
  }
  
  public Size getMaxOutputSize(String paramString, int paramInt)
  {
    Object localObject = (SupportedSurfaceCombination)this.mCameraSupportedSurfaceCombinationMap.get(paramString);
    if (localObject != null) {
      return ((SupportedSurfaceCombination)localObject).getMaxOutputSizeByFormat(paramInt);
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Fail to find supported surface info - CameraId:");
    ((StringBuilder)localObject).append(paramString);
    throw new IllegalArgumentException(((StringBuilder)localObject).toString());
  }
  
  public Size getPreviewSize()
  {
    Object localObject = MAXIMUM_PREVIEW_SIZE;
    if (!this.mCameraSupportedSurfaceCombinationMap.isEmpty())
    {
      localObject = (String)this.mCameraSupportedSurfaceCombinationMap.keySet().toArray()[0];
      localObject = ((SupportedSurfaceCombination)this.mCameraSupportedSurfaceCombinationMap.get(localObject)).getSurfaceSizeDefinition().getPreviewSize();
    }
    return localObject;
  }
  
  public Map<UseCase, Size> getSuggestedResolutions(String paramString, List<UseCase> paramList1, List<UseCase> paramList2)
  {
    Preconditions.checkNotNull(paramList2, "No new use cases to be bound.");
    Preconditions.checkArgument(paramList2.isEmpty() ^ true, "No new use cases to be bound.");
    UseCaseSurfaceOccupancyManager.checkUseCaseLimitNotExceeded(paramList1, paramList2);
    ArrayList localArrayList = new ArrayList();
    if (paramList1 != null)
    {
      localObject = paramList1.iterator();
      while (((Iterator)localObject).hasNext())
      {
        UseCase localUseCase = (UseCase)((Iterator)localObject).next();
        Size localSize = localUseCase.getAttachedSurfaceResolution(((CameraInternal)Preconditions.checkNotNull(localUseCase.getBoundCamera())).getCameraInfoInternal().getCameraId());
        localArrayList.add(transformSurfaceConfig(paramString, localUseCase.getImageFormat(), localSize));
      }
    }
    Object localObject = paramList2.iterator();
    while (((Iterator)localObject).hasNext()) {
      localArrayList.add(transformSurfaceConfig(paramString, ((UseCase)((Iterator)localObject).next()).getImageFormat(), new Size(640, 480)));
    }
    localObject = (SupportedSurfaceCombination)this.mCameraSupportedSurfaceCombinationMap.get(paramString);
    if ((localObject != null) && (((SupportedSurfaceCombination)localObject).checkSupported(localArrayList))) {
      return ((SupportedSurfaceCombination)localObject).getSuggestedResolutions(paramList1, paramList2);
    }
    paramList1 = new StringBuilder();
    paramList1.append("No supported surface combination is found for camera device - Id : ");
    paramList1.append(paramString);
    paramList1.append(".  May be attempting to bind too many use cases.");
    throw new IllegalArgumentException(paramList1.toString());
  }
  
  public boolean requiresCorrectedAspectRatio(String paramString)
  {
    Object localObject = (SupportedSurfaceCombination)this.mCameraSupportedSurfaceCombinationMap.get(paramString);
    if (localObject != null) {
      return ((SupportedSurfaceCombination)localObject).requiresCorrectedAspectRatio();
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Fail to find supported surface info - CameraId:");
    ((StringBuilder)localObject).append(paramString);
    throw new IllegalArgumentException(((StringBuilder)localObject).toString());
  }
  
  public SurfaceConfig transformSurfaceConfig(String paramString, int paramInt, Size paramSize)
  {
    paramString = (SupportedSurfaceCombination)this.mCameraSupportedSurfaceCombinationMap.get(paramString);
    if (paramString != null) {
      paramString = paramString.transformSurfaceConfig(paramInt, paramSize);
    } else {
      paramString = null;
    }
    return paramString;
  }
}
