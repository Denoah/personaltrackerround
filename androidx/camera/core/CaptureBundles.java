package androidx.camera.core;

import androidx.camera.core.impl.CaptureBundle;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.CaptureStage.DefaultCaptureStage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class CaptureBundles
{
  private CaptureBundles() {}
  
  static CaptureBundle createCaptureBundle(List<CaptureStage> paramList)
  {
    return new CaptureBundleImpl(paramList);
  }
  
  static CaptureBundle createCaptureBundle(CaptureStage... paramVarArgs)
  {
    return new CaptureBundleImpl(Arrays.asList(paramVarArgs));
  }
  
  static CaptureBundle singleDefaultCaptureBundle()
  {
    return createCaptureBundle(new CaptureStage[] { new CaptureStage.DefaultCaptureStage() });
  }
  
  static final class CaptureBundleImpl
    implements CaptureBundle
  {
    final List<CaptureStage> mCaptureStageList;
    
    CaptureBundleImpl(List<CaptureStage> paramList)
    {
      if ((paramList != null) && (!paramList.isEmpty()))
      {
        this.mCaptureStageList = Collections.unmodifiableList(new ArrayList(paramList));
        return;
      }
      throw new IllegalArgumentException("Cannot set an empty CaptureStage list.");
    }
    
    public List<CaptureStage> getCaptureStages()
    {
      return this.mCaptureStageList;
    }
  }
}
