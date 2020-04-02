package androidx.camera.core.impl;

import android.util.Size;
import android.view.Surface;

public abstract interface CaptureProcessor
{
  public abstract void onOutputSurface(Surface paramSurface, int paramInt);
  
  public abstract void onResolutionUpdate(Size paramSize);
  
  public abstract void process(ImageProxyBundle paramImageProxyBundle);
}
