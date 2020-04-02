package androidx.camera.extensions;

import android.hardware.camera2.CaptureRequest.Key;
import android.util.Pair;
import androidx.camera.camera2.impl.Camera2ImplConfig.Builder;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureConfig.Builder;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.extensions.impl.CaptureStageImpl;
import java.util.Iterator;
import java.util.List;

final class AdaptingCaptureStage
  implements CaptureStage
{
  private final CaptureConfig mCaptureRequestConfiguration;
  private final int mId;
  
  AdaptingCaptureStage(CaptureStageImpl paramCaptureStageImpl)
  {
    this.mId = paramCaptureStageImpl.getId();
    Camera2ImplConfig.Builder localBuilder = new Camera2ImplConfig.Builder();
    Iterator localIterator = paramCaptureStageImpl.getParameters().iterator();
    while (localIterator.hasNext())
    {
      paramCaptureStageImpl = (Pair)localIterator.next();
      localBuilder.setCaptureRequestOption((CaptureRequest.Key)paramCaptureStageImpl.first, paramCaptureStageImpl.second);
    }
    paramCaptureStageImpl = new CaptureConfig.Builder();
    paramCaptureStageImpl.addImplementationOptions(localBuilder.build());
    paramCaptureStageImpl.setTag(Integer.valueOf(this.mId));
    this.mCaptureRequestConfiguration = paramCaptureStageImpl.build();
  }
  
  public CaptureConfig getCaptureConfig()
  {
    return this.mCaptureRequestConfiguration;
  }
  
  public int getId()
  {
    return this.mId;
  }
}
