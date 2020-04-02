package androidx.camera.camera2.internal;

import android.content.Context;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build.VERSION;
import android.util.Pair;
import android.util.Rational;
import android.util.Size;
import android.view.Display;
import android.view.WindowManager;
import androidx.camera.core.CameraX;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.SurfaceCombination;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.SurfaceConfig.ConfigSize;
import androidx.camera.core.impl.SurfaceConfig.ConfigType;
import androidx.camera.core.impl.SurfaceSizeDefinition;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class SupportedSurfaceCombination
{
  private static final int ALIGN16 = 16;
  private static final Rational ASPECT_RATIO_16_9 = new Rational(16, 9);
  private static final Rational ASPECT_RATIO_3_4;
  private static final Rational ASPECT_RATIO_4_3;
  private static final Rational ASPECT_RATIO_9_16 = new Rational(9, 16);
  private static final Size DEFAULT_SIZE;
  private static final Size MAX_PREVIEW_SIZE = new Size(1920, 1080);
  private static final Size QUALITY_1080P_SIZE;
  private static final Size QUALITY_2160P_SIZE;
  private static final Size QUALITY_480P_SIZE;
  private static final Size QUALITY_720P_SIZE;
  private static final Size ZERO_SIZE;
  private CamcorderProfileHelper mCamcorderProfileHelper;
  private String mCameraId;
  private CameraCharacteristics mCharacteristics;
  private int mHardwareLevel = 2;
  private boolean mIsBurstCaptureSupported = false;
  private boolean mIsRawSupported = false;
  private final Map<Integer, Size> mMaxSizeCache = new HashMap();
  private final List<SurfaceCombination> mSurfaceCombinations = new ArrayList();
  private SurfaceSizeDefinition mSurfaceSizeDefinition;
  
  static
  {
    DEFAULT_SIZE = new Size(640, 480);
    ZERO_SIZE = new Size(0, 0);
    QUALITY_2160P_SIZE = new Size(3840, 2160);
    QUALITY_1080P_SIZE = new Size(1920, 1080);
    QUALITY_720P_SIZE = new Size(1280, 720);
    QUALITY_480P_SIZE = new Size(720, 480);
    ASPECT_RATIO_4_3 = new Rational(4, 3);
    ASPECT_RATIO_3_4 = new Rational(3, 4);
  }
  
  private SupportedSurfaceCombination() {}
  
  SupportedSurfaceCombination(Context paramContext, String paramString, CamcorderProfileHelper paramCamcorderProfileHelper)
  {
    this.mCameraId = paramString;
    this.mCamcorderProfileHelper = paramCamcorderProfileHelper;
    init(paramContext);
  }
  
  private void checkCustomization() {}
  
  private Size fetchMaxSize(int paramInt)
  {
    Size localSize = (Size)this.mMaxSizeCache.get(Integer.valueOf(paramInt));
    if (localSize != null) {
      return localSize;
    }
    localSize = getMaxOutputSizeByFormat(paramInt);
    this.mMaxSizeCache.put(Integer.valueOf(paramInt), localSize);
    return localSize;
  }
  
  private void generateSupportedCombinationList(CameraManager paramCameraManager)
    throws CameraAccessException
  {
    paramCameraManager = paramCameraManager.getCameraCharacteristics(this.mCameraId);
    this.mCharacteristics = paramCameraManager;
    paramCameraManager = (Integer)paramCameraManager.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
    if (paramCameraManager != null) {
      this.mHardwareLevel = paramCameraManager.intValue();
    }
    this.mSurfaceCombinations.addAll(getLegacySupportedCombinationList());
    int i = this.mHardwareLevel;
    if ((i == 0) || (i == 1) || (i == 3)) {
      this.mSurfaceCombinations.addAll(getLimitedSupportedCombinationList());
    }
    i = this.mHardwareLevel;
    if ((i == 1) || (i == 3)) {
      this.mSurfaceCombinations.addAll(getFullSupportedCombinationList());
    }
    paramCameraManager = (int[])this.mCharacteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES);
    if (paramCameraManager != null)
    {
      int j = paramCameraManager.length;
      for (i = 0; i < j; i++)
      {
        int k = paramCameraManager[i];
        if (k == 3) {
          this.mIsRawSupported = true;
        } else if (k == 6) {
          this.mIsBurstCaptureSupported = true;
        }
      }
    }
    if (this.mIsRawSupported) {
      this.mSurfaceCombinations.addAll(getRAWSupportedCombinationList());
    }
    if ((this.mIsBurstCaptureSupported) && (this.mHardwareLevel == 0)) {
      this.mSurfaceCombinations.addAll(getBurstSupportedCombinationList());
    }
    if (this.mHardwareLevel == 3) {
      this.mSurfaceCombinations.addAll(getLevel3SupportedCombinationList());
    }
  }
  
  private void generateSurfaceSizeDefinition(WindowManager paramWindowManager)
  {
    this.mSurfaceSizeDefinition = SurfaceSizeDefinition.create(new Size(640, 480), getPreviewSize(paramWindowManager), getRecordSize());
  }
  
  private Size[] getAllOutputSizesByFormat(int paramInt)
  {
    return getAllOutputSizesByFormat(paramInt, null);
  }
  
  private Size[] getAllOutputSizesByFormat(int paramInt, UseCase paramUseCase)
  {
    Object localObject1 = null;
    if (paramUseCase != null) {
      localObject2 = ((ImageOutputConfig)paramUseCase.getUseCaseConfig()).getSupportedResolutions(null);
    } else {
      localObject2 = null;
    }
    paramUseCase = localObject1;
    if (localObject2 != null)
    {
      localObject2 = ((List)localObject2).iterator();
      do
      {
        paramUseCase = localObject1;
        if (!((Iterator)localObject2).hasNext()) {
          break;
        }
        paramUseCase = (Pair)((Iterator)localObject2).next();
      } while (((Integer)paramUseCase.first).intValue() != paramInt);
      paramUseCase = (Size[])paramUseCase.second;
    }
    Object localObject2 = paramUseCase;
    if (paramUseCase == null)
    {
      paramUseCase = this.mCharacteristics;
      if (paramUseCase != null)
      {
        paramUseCase = (StreamConfigurationMap)paramUseCase.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (paramUseCase != null)
        {
          if ((Build.VERSION.SDK_INT < 23) && (paramInt == 34)) {
            localObject2 = paramUseCase.getOutputSizes(SurfaceTexture.class);
          } else {
            localObject2 = paramUseCase.getOutputSizes(paramInt);
          }
        }
        else
        {
          paramUseCase = new StringBuilder();
          paramUseCase.append("Can not get supported output size for the format: ");
          paramUseCase.append(paramInt);
          throw new IllegalArgumentException(paramUseCase.toString());
        }
      }
      else
      {
        throw new IllegalStateException("CameraCharacteristics is null.");
      }
    }
    if (localObject2 != null)
    {
      Arrays.sort((Object[])localObject2, new CompareSizesByArea(true));
      return localObject2;
    }
    paramUseCase = new StringBuilder();
    paramUseCase.append("Can not get supported output size for the format: ");
    paramUseCase.append(paramInt);
    throw new IllegalArgumentException(paramUseCase.toString());
  }
  
  private List<List<Size>> getAllPossibleSizeArrangements(List<List<Size>> paramList)
  {
    Object localObject = paramList.iterator();
    int i = 1;
    while (((Iterator)localObject).hasNext()) {
      i *= ((List)((Iterator)localObject).next()).size();
    }
    if (i != 0)
    {
      localObject = new ArrayList();
      for (int j = 0; j < i; j++) {
        ((List)localObject).add(new ArrayList());
      }
      j = i / ((List)paramList.get(0)).size();
      int k = i;
      int m = 0;
      while (m < paramList.size())
      {
        List localList = (List)paramList.get(m);
        for (int n = 0; n < i; n++) {
          ((List)((List)localObject).get(n)).add(localList.get(n % k / j));
        }
        n = j;
        if (m < paramList.size() - 1)
        {
          n = j / ((List)paramList.get(m + 1)).size();
          k = j;
        }
        m++;
        j = n;
      }
      return localObject;
    }
    throw new IllegalArgumentException("Failed to find supported resolutions.");
  }
  
  private int getArea(Size paramSize)
  {
    return paramSize.getWidth() * paramSize.getHeight();
  }
  
  private Size getPreviewSize(WindowManager paramWindowManager)
  {
    Point localPoint = new Point();
    paramWindowManager.getDefaultDisplay().getRealSize(localPoint);
    if (localPoint.x > localPoint.y) {
      paramWindowManager = new Size(localPoint.x, localPoint.y);
    } else {
      paramWindowManager = new Size(localPoint.y, localPoint.x);
    }
    return (Size)Collections.min(Arrays.asList(new Size[] { new Size(paramWindowManager.getWidth(), paramWindowManager.getHeight()), MAX_PREVIEW_SIZE }), new CompareSizesByArea());
  }
  
  private Size getRecordSize()
  {
    Size localSize = QUALITY_480P_SIZE;
    if (this.mCamcorderProfileHelper.hasProfile(Integer.parseInt(this.mCameraId), 8)) {
      localSize = QUALITY_2160P_SIZE;
    } else if (this.mCamcorderProfileHelper.hasProfile(Integer.parseInt(this.mCameraId), 6)) {
      localSize = QUALITY_1080P_SIZE;
    } else if (this.mCamcorderProfileHelper.hasProfile(Integer.parseInt(this.mCameraId), 5)) {
      localSize = QUALITY_720P_SIZE;
    } else if (this.mCamcorderProfileHelper.hasProfile(Integer.parseInt(this.mCameraId), 4)) {
      localSize = QUALITY_480P_SIZE;
    }
    return localSize;
  }
  
  private List<Integer> getUseCasesPriorityOrder(List<UseCase> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject = new ArrayList();
    Iterator localIterator = paramList.iterator();
    int i;
    while (localIterator.hasNext())
    {
      i = ((UseCase)localIterator.next()).getUseCaseConfig().getSurfaceOccupancyPriority(0);
      if (!((List)localObject).contains(Integer.valueOf(i))) {
        ((List)localObject).add(Integer.valueOf(i));
      }
    }
    Collections.sort((List)localObject);
    Collections.reverse((List)localObject);
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      i = ((Integer)((Iterator)localObject).next()).intValue();
      localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        UseCase localUseCase = (UseCase)localIterator.next();
        if (i == localUseCase.getUseCaseConfig().getSurfaceOccupancyPriority(0)) {
          localArrayList.add(Integer.valueOf(paramList.indexOf(localUseCase)));
        }
      }
    }
    return localArrayList;
  }
  
  static boolean hasMatchingAspectRatio(Size paramSize, Rational paramRational)
  {
    boolean bool;
    if (paramRational == null) {
      bool = false;
    } else if (paramRational.equals(new Rational(paramSize.getWidth(), paramSize.getHeight()))) {
      bool = true;
    } else {
      bool = isPossibleMod16FromAspectRatio(paramSize, paramRational);
    }
    return bool;
  }
  
  private void init(Context paramContext)
  {
    Object localObject = (CameraManager)paramContext.getSystemService("camera");
    paramContext = (WindowManager)paramContext.getSystemService("window");
    try
    {
      generateSupportedCombinationList((CameraManager)localObject);
      generateSurfaceSizeDefinition(paramContext);
      checkCustomization();
      return;
    }
    catch (CameraAccessException paramContext)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Generate supported combination list and size definition fail - CameraId:");
      ((StringBuilder)localObject).append(this.mCameraId);
      throw new IllegalArgumentException(((StringBuilder)localObject).toString(), paramContext);
    }
  }
  
  private static boolean isPossibleMod16FromAspectRatio(Size paramSize, Rational paramRational)
  {
    int i = paramSize.getWidth();
    int j = paramSize.getHeight();
    paramSize = new Rational(paramRational.getDenominator(), paramRational.getNumerator());
    int k = i % 16;
    boolean bool = false;
    if ((k == 0) && (j % 16 == 0))
    {
      if ((ratioIntersectsMod16Segment(Math.max(0, j - 16), i, paramRational)) || (ratioIntersectsMod16Segment(Math.max(0, i - 16), j, paramSize))) {
        bool = true;
      }
      return bool;
    }
    if (k == 0) {
      return ratioIntersectsMod16Segment(j, i, paramRational);
    }
    if (j % 16 == 0) {
      return ratioIntersectsMod16Segment(i, j, paramSize);
    }
    return false;
  }
  
  private boolean isRotationNeeded(int paramInt)
  {
    paramInt = CameraX.getCameraInfo(this.mCameraId).getSensorRotationDegrees(paramInt);
    boolean bool;
    if ((paramInt != 90) && (paramInt != 270)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  private static boolean ratioIntersectsMod16Segment(int paramInt1, int paramInt2, Rational paramRational)
  {
    boolean bool1 = true;
    boolean bool2;
    if (paramInt2 % 16 == 0) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    Preconditions.checkArgument(bool2);
    double d = paramInt1 * paramRational.getNumerator() / paramRational.getDenominator();
    if ((d > Math.max(0, paramInt2 - 16)) && (d < paramInt2 + 16)) {
      bool2 = bool1;
    } else {
      bool2 = false;
    }
    return bool2;
  }
  
  private void removeSupportedSizesByTargetSize(List<Size> paramList, Size paramSize)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      int i = -1;
      ArrayList localArrayList = new ArrayList();
      int k;
      for (int j = 0; j < paramList.size(); j = k)
      {
        Size localSize = (Size)paramList.get(j);
        if ((localSize.getWidth() < paramSize.getWidth()) || (localSize.getHeight() < paramSize.getHeight())) {
          break;
        }
        if (i >= 0) {
          localArrayList.add(paramList.get(i));
        }
        k = j + 1;
        i = j;
      }
      paramList.removeAll(localArrayList);
    }
  }
  
  private void removeSupportedSizesByTargetSizeAndAspectRatio(List<Size> paramList, Size paramSize)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      HashMap localHashMap = new HashMap();
      ArrayList localArrayList = new ArrayList();
      Object localObject1 = null;
      int i = 0;
      while (i < paramList.size())
      {
        Size localSize = (Size)paramList.get(i);
        Object localObject2 = localObject1;
        if (localSize.getWidth() >= paramSize.getWidth())
        {
          localObject2 = localObject1;
          if (localSize.getHeight() >= paramSize.getHeight())
          {
            Rational localRational = new Rational(localSize.getWidth(), localSize.getHeight());
            if (localObject1 != null)
            {
              localObject2 = localObject1;
              if (hasMatchingAspectRatio(localSize, (Rational)localObject1)) {}
            }
            else
            {
              localObject2 = localRational;
            }
            localObject1 = (Size)localHashMap.get(localObject2);
            if (localObject1 != null) {
              localArrayList.add(localObject1);
            }
            localHashMap.put(localObject2, localSize);
          }
        }
        i++;
        localObject1 = localObject2;
      }
      paramList.removeAll(localArrayList);
    }
  }
  
  private Rational rotateAspectRatioByRotation(Rational paramRational, int paramInt)
  {
    Rational localRational = paramRational;
    if (paramRational != null)
    {
      localRational = paramRational;
      if (isRotationNeeded(paramInt)) {
        localRational = new Rational(paramRational.getDenominator(), paramRational.getNumerator());
      }
    }
    return localRational;
  }
  
  boolean checkSupported(List<SurfaceConfig> paramList)
  {
    Iterator localIterator = this.mSurfaceCombinations.iterator();
    boolean bool1 = false;
    while (localIterator.hasNext())
    {
      boolean bool2 = ((SurfaceCombination)localIterator.next()).isSupported(paramList);
      bool1 = bool2;
      if (bool2) {
        bool1 = bool2;
      }
    }
    return bool1;
  }
  
  List<SurfaceCombination> getBurstSupportedCombinationList()
  {
    ArrayList localArrayList = new ArrayList();
    SurfaceCombination localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    return localArrayList;
  }
  
  String getCameraId()
  {
    return this.mCameraId;
  }
  
  Rational getCorrectedAspectRatio(int paramInt)
  {
    Object localObject;
    if ((this.mHardwareLevel == 2) && (Build.VERSION.SDK_INT == 21))
    {
      localObject = fetchMaxSize(256);
      localObject = rotateAspectRatioByRotation(new Rational(((Size)localObject).getWidth(), ((Size)localObject).getHeight()), paramInt);
    }
    else
    {
      localObject = null;
    }
    return localObject;
  }
  
  List<SurfaceCombination> getFullSupportedCombinationList()
  {
    ArrayList localArrayList = new ArrayList();
    SurfaceCombination localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.ANALYSIS));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.ANALYSIS));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    return localArrayList;
  }
  
  List<SurfaceCombination> getLegacySupportedCombinationList()
  {
    ArrayList localArrayList = new ArrayList();
    SurfaceCombination localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    return localArrayList;
  }
  
  List<SurfaceCombination> getLevel3SupportedCombinationList()
  {
    ArrayList localArrayList = new ArrayList();
    SurfaceCombination localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.ANALYSIS));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.MAXIMUM));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.ANALYSIS));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    return localArrayList;
  }
  
  List<SurfaceCombination> getLimitedSupportedCombinationList()
  {
    ArrayList localArrayList = new ArrayList();
    SurfaceCombination localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.RECORD));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.RECORD));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.RECORD));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.RECORD));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.RECORD));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.RECORD));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.RECORD));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    return localArrayList;
  }
  
  Size getMaxOutputSizeByFormat(int paramInt)
  {
    return (Size)Collections.max(Arrays.asList(getAllOutputSizesByFormat(paramInt)), new CompareSizesByArea());
  }
  
  List<SurfaceCombination> getRAWSupportedCombinationList()
  {
    ArrayList localArrayList = new ArrayList();
    SurfaceCombination localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.PRIV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    localSurfaceCombination = new SurfaceCombination();
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.YUV, SurfaceConfig.ConfigSize.PREVIEW));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.JPEG, SurfaceConfig.ConfigSize.MAXIMUM));
    localSurfaceCombination.addSurfaceConfig(SurfaceConfig.create(SurfaceConfig.ConfigType.RAW, SurfaceConfig.ConfigSize.MAXIMUM));
    localArrayList.add(localSurfaceCombination);
    return localArrayList;
  }
  
  Map<UseCase, Size> getSuggestedResolutions(List<UseCase> paramList1, List<UseCase> paramList2)
  {
    HashMap localHashMap = new HashMap();
    List localList = getUseCasesPriorityOrder(paramList2);
    Object localObject1 = new ArrayList();
    Object localObject2 = localList.iterator();
    while (((Iterator)localObject2).hasNext()) {
      ((List)localObject1).add(getSupportedOutputSizes((UseCase)paramList2.get(((Integer)((Iterator)localObject2).next()).intValue())));
    }
    Iterator localIterator = getAllPossibleSizeArrangements((List)localObject1).iterator();
    while (localIterator.hasNext())
    {
      localObject2 = (List)localIterator.next();
      localObject1 = new ArrayList();
      Object localObject3;
      if (paramList1 != null)
      {
        localObject3 = paramList1.iterator();
        while (((Iterator)localObject3).hasNext())
        {
          UseCase localUseCase = (UseCase)((Iterator)localObject3).next();
          Size localSize = localUseCase.getAttachedSurfaceResolution(localUseCase.getBoundCamera().getCameraInfoInternal().getCameraId());
          ((List)localObject1).add(transformSurfaceConfig(localUseCase.getImageFormat(), localSize));
        }
      }
      for (int i = 0; i < ((List)localObject2).size(); i++)
      {
        localObject3 = (Size)((List)localObject2).get(i);
        ((List)localObject1).add(transformSurfaceConfig(((UseCase)paramList2.get(((Integer)localList.get(i)).intValue())).getImageFormat(), (Size)localObject3));
      }
      if (checkSupported((List)localObject1))
      {
        paramList1 = paramList2.iterator();
        while (paramList1.hasNext())
        {
          localObject1 = (UseCase)paramList1.next();
          localHashMap.put(localObject1, ((List)localObject2).get(localList.indexOf(Integer.valueOf(paramList2.indexOf(localObject1)))));
        }
      }
    }
    return localHashMap;
  }
  
  List<Size> getSupportedOutputSizes(UseCase paramUseCase)
  {
    int i = paramUseCase.getImageFormat();
    Object localObject1 = getAllOutputSizesByFormat(i, paramUseCase);
    Object localObject2 = new ArrayList();
    ImageOutputConfig localImageOutputConfig = (ImageOutputConfig)paramUseCase.getUseCaseConfig();
    Object localObject3 = localImageOutputConfig.getMaxResolution(getMaxOutputSizeByFormat(i));
    int j = localImageOutputConfig.getTargetRotation(0);
    Arrays.sort((Object[])localObject1, new CompareSizesByArea(true));
    paramUseCase = localImageOutputConfig.getTargetResolution(ZERO_SIZE);
    Object localObject4 = paramUseCase;
    if (isRotationNeeded(j)) {
      localObject4 = new Size(paramUseCase.getHeight(), paramUseCase.getWidth());
    }
    Object localObject5 = DEFAULT_SIZE;
    paramUseCase = (UseCase)localObject5;
    if (!((Size)localObject4).equals(ZERO_SIZE))
    {
      paramUseCase = (UseCase)localObject5;
      if (getArea((Size)localObject4) < getArea(DEFAULT_SIZE)) {
        paramUseCase = (UseCase)localObject4;
      }
    }
    int k = localObject1.length;
    for (int m = 0; m < k; m++)
    {
      localObject5 = localObject1[m];
      if ((getArea((Size)localObject5) <= getArea((Size)localObject3)) && (getArea((Size)localObject5) >= getArea(paramUseCase))) {
        ((List)localObject2).add(localObject5);
      }
    }
    if (!((List)localObject2).isEmpty())
    {
      localObject1 = new ArrayList();
      localObject5 = new ArrayList();
      boolean bool = localImageOutputConfig.hasTargetAspectRatio();
      paramUseCase = null;
      if (bool)
      {
        bool = isRotationNeeded(0);
        m = localImageOutputConfig.getTargetAspectRatio();
        if (m != 0)
        {
          if (m != 1) {
            break label338;
          }
          if (bool) {
            paramUseCase = ASPECT_RATIO_16_9;
          } else {
            paramUseCase = ASPECT_RATIO_9_16;
          }
        }
        for (;;)
        {
          break;
          if (bool) {
            paramUseCase = ASPECT_RATIO_4_3;
          } else {
            paramUseCase = ASPECT_RATIO_3_4;
          }
        }
      }
      else
      {
        paramUseCase = rotateAspectRatioByRotation(localImageOutputConfig.getTargetAspectRatioCustom(null), j);
      }
      label338:
      localObject3 = ((List)localObject2).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject2 = (Size)((Iterator)localObject3).next();
        if ((paramUseCase != null) && (!hasMatchingAspectRatio((Size)localObject2, paramUseCase)))
        {
          if (!((List)localObject5).contains(localObject2)) {
            ((List)localObject5).add(localObject2);
          }
        }
        else if (!((List)localObject1).contains(localObject2)) {
          ((List)localObject1).add(localObject2);
        }
      }
      if (paramUseCase != null) {
        Collections.sort((List)localObject5, new CompareSizesByDistanceToTargetRatio(Float.valueOf(paramUseCase.floatValue())));
      }
      paramUseCase = (UseCase)localObject4;
      if (((Size)localObject4).equals(ZERO_SIZE)) {
        paramUseCase = localImageOutputConfig.getDefaultResolution(ZERO_SIZE);
      }
      if (!paramUseCase.equals(ZERO_SIZE))
      {
        removeSupportedSizesByTargetSize((List)localObject1, paramUseCase);
        removeSupportedSizesByTargetSizeAndAspectRatio((List)localObject5, paramUseCase);
      }
      paramUseCase = new ArrayList();
      paramUseCase.addAll((Collection)localObject1);
      paramUseCase.addAll((Collection)localObject5);
      return paramUseCase;
    }
    paramUseCase = new StringBuilder();
    paramUseCase.append("Can not get supported output size under supported maximum for the format: ");
    paramUseCase.append(i);
    throw new IllegalArgumentException(paramUseCase.toString());
  }
  
  SurfaceSizeDefinition getSurfaceSizeDefinition()
  {
    return this.mSurfaceSizeDefinition;
  }
  
  boolean isBurstCaptureSupported()
  {
    return this.mIsBurstCaptureSupported;
  }
  
  boolean isRawSupported()
  {
    return this.mIsRawSupported;
  }
  
  boolean requiresCorrectedAspectRatio()
  {
    boolean bool;
    if ((this.mHardwareLevel == 2) && (Build.VERSION.SDK_INT == 21)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  SurfaceConfig transformSurfaceConfig(int paramInt, Size paramSize)
  {
    SurfaceConfig.ConfigSize localConfigSize = SurfaceConfig.ConfigSize.NOT_SUPPORT;
    if (getAllOutputSizesByFormat(paramInt) != null)
    {
      SurfaceConfig.ConfigType localConfigType;
      if (paramInt == 35) {
        localConfigType = SurfaceConfig.ConfigType.YUV;
      } else if (paramInt == 256) {
        localConfigType = SurfaceConfig.ConfigType.JPEG;
      } else if (paramInt == 32) {
        localConfigType = SurfaceConfig.ConfigType.RAW;
      } else {
        localConfigType = SurfaceConfig.ConfigType.PRIV;
      }
      Size localSize = fetchMaxSize(paramInt);
      if (paramSize.getWidth() * paramSize.getHeight() <= this.mSurfaceSizeDefinition.getAnalysisSize().getWidth() * this.mSurfaceSizeDefinition.getAnalysisSize().getHeight()) {
        localConfigSize = SurfaceConfig.ConfigSize.ANALYSIS;
      } else if (paramSize.getWidth() * paramSize.getHeight() <= this.mSurfaceSizeDefinition.getPreviewSize().getWidth() * this.mSurfaceSizeDefinition.getPreviewSize().getHeight()) {
        localConfigSize = SurfaceConfig.ConfigSize.PREVIEW;
      } else if (paramSize.getWidth() * paramSize.getHeight() <= this.mSurfaceSizeDefinition.getRecordSize().getWidth() * this.mSurfaceSizeDefinition.getRecordSize().getHeight()) {
        localConfigSize = SurfaceConfig.ConfigSize.RECORD;
      } else if (paramSize.getWidth() * paramSize.getHeight() <= localSize.getWidth() * localSize.getHeight()) {
        localConfigSize = SurfaceConfig.ConfigSize.MAXIMUM;
      }
      return SurfaceConfig.create(localConfigType, localConfigSize);
    }
    paramSize = new StringBuilder();
    paramSize.append("Can not get supported output size for the format: ");
    paramSize.append(paramInt);
    throw new IllegalArgumentException(paramSize.toString());
  }
  
  static final class CompareSizesByArea
    implements Comparator<Size>
  {
    private boolean mReverse = false;
    
    CompareSizesByArea() {}
    
    CompareSizesByArea(boolean paramBoolean)
    {
      this.mReverse = paramBoolean;
    }
    
    public int compare(Size paramSize1, Size paramSize2)
    {
      int i = Long.signum(paramSize1.getWidth() * paramSize1.getHeight() - paramSize2.getWidth() * paramSize2.getHeight());
      int j = i;
      if (this.mReverse) {
        j = i * -1;
      }
      return j;
    }
  }
  
  static final class CompareSizesByDistanceToTargetRatio
    implements Comparator<Size>
  {
    private Float mTargetRatio;
    
    CompareSizesByDistanceToTargetRatio(Float paramFloat)
    {
      this.mTargetRatio = paramFloat;
    }
    
    public int compare(Size paramSize1, Size paramSize2)
    {
      if (SupportedSurfaceCombination.hasMatchingAspectRatio(paramSize1, new Rational(paramSize2.getWidth(), paramSize2.getHeight()))) {
        return 0;
      }
      float f1 = paramSize1.getWidth() * 1.0F / paramSize1.getHeight();
      float f2 = paramSize2.getWidth() * 1.0F / paramSize2.getHeight();
      f1 = Math.abs(Float.valueOf(f1).floatValue() - this.mTargetRatio.floatValue());
      f2 = Math.abs(Float.valueOf(f2).floatValue() - this.mTargetRatio.floatValue());
      return (int)Math.signum(Float.valueOf(f1).floatValue() - Float.valueOf(f2).floatValue());
    }
  }
}
