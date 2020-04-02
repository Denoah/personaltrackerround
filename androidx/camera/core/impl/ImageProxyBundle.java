package androidx.camera.core.impl;

import androidx.camera.core.ImageProxy;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;

public abstract interface ImageProxyBundle
{
  public abstract List<Integer> getCaptureIds();
  
  public abstract ListenableFuture<ImageProxy> getImageProxy(int paramInt);
}
