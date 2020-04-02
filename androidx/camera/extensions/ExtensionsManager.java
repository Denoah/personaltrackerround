package androidx.camera.extensions;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraSelector.Builder;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCapture.Builder;
import androidx.camera.core.Preview;
import androidx.camera.core.Preview.Builder;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.camera.extensions.impl.InitializerImpl.OnExtensionsInitializedCallback;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import com.google.common.util.concurrent.ListenableFuture;

public final class ExtensionsManager
{
  private static final Handler DEFAULT_HANDLER = new Handler(Looper.getMainLooper());
  private static final Object ERROR_LOCK = new Object();
  private static final String TAG = "ExtensionsManager";
  private static volatile ExtensionsErrorListener sExtensionsErrorListener = null;
  
  private ExtensionsManager() {}
  
  private static boolean checkImageCaptureExtensionCapability(EffectMode paramEffectMode, int paramInt)
  {
    ImageCapture.Builder localBuilder = new ImageCapture.Builder();
    CameraSelector localCameraSelector = new CameraSelector.Builder().requireLensFacing(paramInt).build();
    switch (3.$SwitchMap$androidx$camera$extensions$ExtensionsManager$EffectMode[paramEffectMode.ordinal()])
    {
    default: 
      return false;
    case 6: 
      return true;
    case 5: 
      paramEffectMode = AutoImageCaptureExtender.create(localBuilder);
      break;
    case 4: 
      paramEffectMode = BeautyImageCaptureExtender.create(localBuilder);
      break;
    case 3: 
      paramEffectMode = NightImageCaptureExtender.create(localBuilder);
      break;
    case 2: 
      paramEffectMode = HdrImageCaptureExtender.create(localBuilder);
      break;
    case 1: 
      paramEffectMode = BokehImageCaptureExtender.create(localBuilder);
    }
    return paramEffectMode.isExtensionAvailable(localCameraSelector);
  }
  
  private static boolean checkPreviewExtensionCapability(EffectMode paramEffectMode, int paramInt)
  {
    Preview.Builder localBuilder = new Preview.Builder();
    CameraSelector localCameraSelector = new CameraSelector.Builder().requireLensFacing(paramInt).build();
    switch (3.$SwitchMap$androidx$camera$extensions$ExtensionsManager$EffectMode[paramEffectMode.ordinal()])
    {
    default: 
      return false;
    case 6: 
      return true;
    case 5: 
      paramEffectMode = AutoPreviewExtender.create(localBuilder);
      break;
    case 4: 
      paramEffectMode = BeautyPreviewExtender.create(localBuilder);
      break;
    case 3: 
      paramEffectMode = NightPreviewExtender.create(localBuilder);
      break;
    case 2: 
      paramEffectMode = HdrPreviewExtender.create(localBuilder);
      break;
    case 1: 
      paramEffectMode = BokehPreviewExtender.create(localBuilder);
    }
    return paramEffectMode.isExtensionAvailable(localCameraSelector);
  }
  
  public static ListenableFuture<ExtensionsAvailability> init()
  {
    if (ExtensionVersion.getRuntimeVersion() == null) {
      return Futures.immediateFuture(ExtensionsAvailability.NONE);
    }
    if (ExtensionVersion.getRuntimeVersion().compareTo(Version.VERSION_1_1) < 0) {
      return Futures.immediateFuture(ExtensionsAvailability.LIBRARY_AVAILABLE);
    }
    return CallbackToFutureAdapter.getFuture(_..Lambda.ExtensionsManager.E6KSBVGYYaKzIhB6gmgQw0PfD6o.INSTANCE);
  }
  
  public static boolean isExtensionAvailable(EffectMode paramEffectMode, int paramInt)
  {
    boolean bool1 = checkImageCaptureExtensionCapability(paramEffectMode, paramInt);
    boolean bool2 = checkPreviewExtensionCapability(paramEffectMode, paramInt);
    if (bool1 != bool2)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("ImageCapture and Preview are not available simultaneously for ");
      localStringBuilder.append(paramEffectMode.name());
      Log.e("ExtensionsManager", localStringBuilder.toString());
    }
    if ((bool1) && (bool2)) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    return bool1;
  }
  
  public static boolean isExtensionAvailable(Class<?> paramClass, EffectMode paramEffectMode, int paramInt)
  {
    boolean bool;
    if (paramClass == ImageCapture.class) {
      bool = checkImageCaptureExtensionCapability(paramEffectMode, paramInt);
    } else if (paramClass.equals(Preview.class)) {
      bool = checkPreviewExtensionCapability(paramEffectMode, paramInt);
    } else {
      bool = false;
    }
    return bool;
  }
  
  static void postExtensionsError(ExtensionsErrorListener.ExtensionsErrorCode paramExtensionsErrorCode)
  {
    synchronized (ERROR_LOCK)
    {
      ExtensionsErrorListener localExtensionsErrorListener = sExtensionsErrorListener;
      if (localExtensionsErrorListener != null)
      {
        Handler localHandler = DEFAULT_HANDLER;
        Runnable local2 = new androidx/camera/extensions/ExtensionsManager$2;
        local2.<init>(localExtensionsErrorListener, paramExtensionsErrorCode);
        localHandler.post(local2);
      }
      return;
    }
  }
  
  public static void setExtensionsErrorListener(ExtensionsErrorListener paramExtensionsErrorListener)
  {
    synchronized (ERROR_LOCK)
    {
      sExtensionsErrorListener = paramExtensionsErrorListener;
      return;
    }
  }
  
  public static enum EffectMode
  {
    static
    {
      BOKEH = new EffectMode("BOKEH", 1);
      HDR = new EffectMode("HDR", 2);
      NIGHT = new EffectMode("NIGHT", 3);
      BEAUTY = new EffectMode("BEAUTY", 4);
      EffectMode localEffectMode = new EffectMode("AUTO", 5);
      AUTO = localEffectMode;
      $VALUES = new EffectMode[] { NORMAL, BOKEH, HDR, NIGHT, BEAUTY, localEffectMode };
    }
    
    private EffectMode() {}
  }
  
  public static enum ExtensionsAvailability
  {
    static
    {
      ExtensionsAvailability localExtensionsAvailability = new ExtensionsAvailability("NONE", 3);
      NONE = localExtensionsAvailability;
      $VALUES = new ExtensionsAvailability[] { LIBRARY_AVAILABLE, LIBRARY_UNAVAILABLE_ERROR_LOADING, LIBRARY_UNAVAILABLE_MISSING_IMPLEMENTATION, localExtensionsAvailability };
    }
    
    private ExtensionsAvailability() {}
  }
}
