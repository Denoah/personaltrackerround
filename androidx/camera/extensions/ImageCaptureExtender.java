package androidx.camera.extensions;

import android.hardware.camera2.CameraCharacteristics;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.camera.camera2.impl.Camera2ImplConfig.Extender;
import androidx.camera.camera2.impl.CameraEventCallback;
import androidx.camera.camera2.impl.CameraEventCallbacks;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraSelector.Builder;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture.Builder;
import androidx.camera.core.UseCase;
import androidx.camera.core.UseCase.EventCallback;
import androidx.camera.core.impl.CaptureBundle;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.CaptureStage;
import androidx.camera.core.impl.Config.Option;
import androidx.camera.core.impl.ImageCaptureConfig;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.extensions.impl.CaptureStageImpl;
import androidx.camera.extensions.impl.ImageCaptureExtenderImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ImageCaptureExtender
{
  static final Config.Option<ExtensionsManager.EffectMode> OPTION_IMAGE_CAPTURE_EXTENDER_MODE = Config.Option.create("camerax.extensions.imageCaptureExtender.mode", ExtensionsManager.EffectMode.class);
  private static final String TAG = "ImageCaptureExtender";
  private ImageCapture.Builder mBuilder;
  private ExtensionsManager.EffectMode mEffectMode;
  private ExtensionCameraIdFilter mExtensionCameraIdFilter;
  private ImageCaptureExtenderImpl mImpl;
  
  public ImageCaptureExtender() {}
  
  static void checkPreviewEnabled(ExtensionsManager.EffectMode paramEffectMode, Collection<UseCase> paramCollection)
  {
    if ((paramCollection != null) && (!paramCollection.isEmpty()))
    {
      Iterator localIterator = paramCollection.iterator();
      int i = 0;
      int j = 0;
      while (localIterator.hasNext())
      {
        paramCollection = (ExtensionsManager.EffectMode)((UseCase)localIterator.next()).getUseCaseConfig().retrieveOption(PreviewExtender.OPTION_PREVIEW_EXTENDER_MODE, null);
        if (paramEffectMode == paramCollection) {
          j = 1;
        } else if (paramCollection != null) {
          i = 1;
        }
      }
      if (i != 0) {
        ExtensionsManager.postExtensionsError(ExtensionsErrorListener.ExtensionsErrorCode.MISMATCHED_EXTENSIONS_ENABLED);
      } else if (j == 0) {
        ExtensionsManager.postExtensionsError(ExtensionsErrorListener.ExtensionsErrorCode.PREVIEW_EXTENSION_REQUIRED);
      }
    }
  }
  
  private String getCameraWithExtension(CameraSelector paramCameraSelector)
  {
    paramCameraSelector = CameraSelector.Builder.fromSelector(paramCameraSelector);
    paramCameraSelector.appendFilter(this.mExtensionCameraIdFilter);
    return CameraUtil.getCameraIdUnchecked(paramCameraSelector.build());
  }
  
  private void setSupportedResolutions()
  {
    if (ExtensionVersion.getRuntimeVersion().compareTo(Version.VERSION_1_1) < 0) {
      return;
    }
    Object localObject = null;
    try
    {
      List localList = this.mImpl.getSupportedResolutions();
      localObject = localList;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      Log.e("ImageCaptureExtender", "getSupportedResolution interface is not implemented in vendor library.");
    }
    if (localObject != null) {
      this.mBuilder.setSupportedResolutions(localObject);
    }
  }
  
  public void enableExtension(CameraSelector paramCameraSelector)
  {
    paramCameraSelector = getCameraWithExtension(paramCameraSelector);
    if (paramCameraSelector == null) {
      return;
    }
    Object localObject = this.mBuilder.getUseCaseConfig().getCameraSelector(null);
    if (localObject == null) {
      this.mBuilder.setCameraSelector(new CameraSelector.Builder().appendFilter(this.mExtensionCameraIdFilter).build());
    } else {
      this.mBuilder.setCameraSelector(CameraSelector.Builder.fromSelector((CameraSelector)localObject).appendFilter(this.mExtensionCameraIdFilter).build());
    }
    localObject = CameraUtil.getCameraCharacteristics(paramCameraSelector);
    this.mImpl.init(paramCameraSelector, (CameraCharacteristics)localObject);
    paramCameraSelector = this.mImpl.getCaptureProcessor();
    if (paramCameraSelector != null) {
      this.mBuilder.setCaptureProcessor(new AdaptingCaptureProcessor(paramCameraSelector));
    }
    if (this.mImpl.getMaxCaptureStage() > 0) {
      this.mBuilder.setMaxCaptureStages(this.mImpl.getMaxCaptureStage());
    }
    paramCameraSelector = new ImageCaptureAdapter(this.mImpl, this.mEffectMode);
    new Camera2ImplConfig.Extender(this.mBuilder).setCameraEventCallback(new CameraEventCallbacks(new CameraEventCallback[] { paramCameraSelector }));
    this.mBuilder.setUseCaseEventCallback(paramCameraSelector);
    this.mBuilder.setCaptureBundle(paramCameraSelector);
    this.mBuilder.getMutableConfig().insertOption(OPTION_IMAGE_CAPTURE_EXTENDER_MODE, this.mEffectMode);
    setSupportedResolutions();
  }
  
  void init(ImageCapture.Builder paramBuilder, ImageCaptureExtenderImpl paramImageCaptureExtenderImpl, ExtensionsManager.EffectMode paramEffectMode)
  {
    this.mBuilder = paramBuilder;
    this.mImpl = paramImageCaptureExtenderImpl;
    this.mEffectMode = paramEffectMode;
    this.mExtensionCameraIdFilter = new ExtensionCameraIdFilter(paramImageCaptureExtenderImpl);
  }
  
  public boolean isExtensionAvailable(CameraSelector paramCameraSelector)
  {
    boolean bool;
    if (getCameraWithExtension(paramCameraSelector) != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  static class ImageCaptureAdapter
    extends CameraEventCallback
    implements UseCase.EventCallback, CaptureBundle
  {
    private final AtomicBoolean mActive = new AtomicBoolean(true);
    final ExtensionsManager.EffectMode mEffectMode;
    private volatile int mEnabledSessionCount = 0;
    private final ImageCaptureExtenderImpl mImpl;
    private final Object mLock = new Object();
    private volatile boolean mUnbind = false;
    
    ImageCaptureAdapter(ImageCaptureExtenderImpl paramImageCaptureExtenderImpl, ExtensionsManager.EffectMode paramEffectMode)
    {
      this.mImpl = paramImageCaptureExtenderImpl;
      this.mEffectMode = paramEffectMode;
    }
    
    private void callDeInit()
    {
      if (this.mActive.get())
      {
        this.mImpl.onDeInit();
        this.mActive.set(false);
      }
    }
    
    public List<CaptureStage> getCaptureStages()
    {
      if (this.mActive.get())
      {
        Object localObject = this.mImpl.getCaptureStages();
        if ((localObject != null) && (!((List)localObject).isEmpty()))
        {
          ArrayList localArrayList = new ArrayList();
          localObject = ((List)localObject).iterator();
          while (((Iterator)localObject).hasNext()) {
            localArrayList.add(new AdaptingCaptureStage((CaptureStageImpl)((Iterator)localObject).next()));
          }
          return localArrayList;
        }
      }
      return null;
    }
    
    public void onBind(String paramString)
    {
      if (this.mActive.get())
      {
        CameraCharacteristics localCameraCharacteristics = CameraUtil.getCameraCharacteristics(paramString);
        this.mImpl.onInit(paramString, localCameraCharacteristics, CameraX.getContext());
      }
    }
    
    /* Error */
    public CaptureConfig onDisableSession()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 37	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mActive	Ljava/util/concurrent/atomic/AtomicBoolean;
      //   4: invokevirtual 56	java/util/concurrent/atomic/AtomicBoolean:get	()Z
      //   7: ifeq +75 -> 82
      //   10: aload_0
      //   11: getfield 48	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mImpl	Landroidx/camera/extensions/impl/ImageCaptureExtenderImpl;
      //   14: invokeinterface 125 1 0
      //   19: astore_1
      //   20: aload_1
      //   21: ifnull +61 -> 82
      //   24: new 87	androidx/camera/extensions/AdaptingCaptureStage
      //   27: astore_2
      //   28: aload_2
      //   29: aload_1
      //   30: invokespecial 96	androidx/camera/extensions/AdaptingCaptureStage:<init>	(Landroidx/camera/extensions/impl/CaptureStageImpl;)V
      //   33: aload_2
      //   34: invokevirtual 128	androidx/camera/extensions/AdaptingCaptureStage:getCaptureConfig	()Landroidx/camera/core/impl/CaptureConfig;
      //   37: astore_1
      //   38: aload_0
      //   39: getfield 42	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mLock	Ljava/lang/Object;
      //   42: astore_2
      //   43: aload_2
      //   44: monitorenter
      //   45: aload_0
      //   46: aload_0
      //   47: getfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   50: iconst_1
      //   51: isub
      //   52: putfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   55: aload_0
      //   56: getfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   59: ifne +14 -> 73
      //   62: aload_0
      //   63: getfield 46	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mUnbind	Z
      //   66: ifeq +7 -> 73
      //   69: aload_0
      //   70: invokespecial 130	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:callDeInit	()V
      //   73: aload_2
      //   74: monitorexit
      //   75: aload_1
      //   76: areturn
      //   77: astore_1
      //   78: aload_2
      //   79: monitorexit
      //   80: aload_1
      //   81: athrow
      //   82: aload_0
      //   83: getfield 42	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mLock	Ljava/lang/Object;
      //   86: astore_1
      //   87: aload_1
      //   88: monitorenter
      //   89: aload_0
      //   90: aload_0
      //   91: getfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   94: iconst_1
      //   95: isub
      //   96: putfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   99: aload_0
      //   100: getfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   103: ifne +14 -> 117
      //   106: aload_0
      //   107: getfield 46	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mUnbind	Z
      //   110: ifeq +7 -> 117
      //   113: aload_0
      //   114: invokespecial 130	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:callDeInit	()V
      //   117: aload_1
      //   118: monitorexit
      //   119: aconst_null
      //   120: areturn
      //   121: astore_2
      //   122: aload_1
      //   123: monitorexit
      //   124: aload_2
      //   125: athrow
      //   126: astore_1
      //   127: aload_0
      //   128: getfield 42	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mLock	Ljava/lang/Object;
      //   131: astore_2
      //   132: aload_2
      //   133: monitorenter
      //   134: aload_0
      //   135: aload_0
      //   136: getfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   139: iconst_1
      //   140: isub
      //   141: putfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   144: aload_0
      //   145: getfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   148: ifne +14 -> 162
      //   151: aload_0
      //   152: getfield 46	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mUnbind	Z
      //   155: ifeq +7 -> 162
      //   158: aload_0
      //   159: invokespecial 130	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:callDeInit	()V
      //   162: aload_2
      //   163: monitorexit
      //   164: aload_1
      //   165: athrow
      //   166: astore_1
      //   167: aload_2
      //   168: monitorexit
      //   169: aload_1
      //   170: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	171	0	this	ImageCaptureAdapter
      //   19	57	1	localObject1	Object
      //   77	4	1	localObject2	Object
      //   126	39	1	localObject4	Object
      //   166	4	1	localObject5	Object
      //   121	4	2	localObject7	Object
      // Exception table:
      //   from	to	target	type
      //   45	73	77	finally
      //   73	75	77	finally
      //   78	80	77	finally
      //   89	117	121	finally
      //   117	119	121	finally
      //   122	124	121	finally
      //   0	20	126	finally
      //   24	38	126	finally
      //   134	162	166	finally
      //   162	164	166	finally
      //   167	169	166	finally
    }
    
    /* Error */
    public CaptureConfig onEnableSession()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 37	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mActive	Ljava/util/concurrent/atomic/AtomicBoolean;
      //   4: invokevirtual 56	java/util/concurrent/atomic/AtomicBoolean:get	()Z
      //   7: ifeq +57 -> 64
      //   10: aload_0
      //   11: getfield 48	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mImpl	Landroidx/camera/extensions/impl/ImageCaptureExtenderImpl;
      //   14: invokeinterface 133 1 0
      //   19: astore_1
      //   20: aload_1
      //   21: ifnull +43 -> 64
      //   24: new 87	androidx/camera/extensions/AdaptingCaptureStage
      //   27: astore_2
      //   28: aload_2
      //   29: aload_1
      //   30: invokespecial 96	androidx/camera/extensions/AdaptingCaptureStage:<init>	(Landroidx/camera/extensions/impl/CaptureStageImpl;)V
      //   33: aload_2
      //   34: invokevirtual 128	androidx/camera/extensions/AdaptingCaptureStage:getCaptureConfig	()Landroidx/camera/core/impl/CaptureConfig;
      //   37: astore_1
      //   38: aload_0
      //   39: getfield 42	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mLock	Ljava/lang/Object;
      //   42: astore_2
      //   43: aload_2
      //   44: monitorenter
      //   45: aload_0
      //   46: aload_0
      //   47: getfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   50: iconst_1
      //   51: iadd
      //   52: putfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   55: aload_2
      //   56: monitorexit
      //   57: aload_1
      //   58: areturn
      //   59: astore_1
      //   60: aload_2
      //   61: monitorexit
      //   62: aload_1
      //   63: athrow
      //   64: aload_0
      //   65: getfield 42	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mLock	Ljava/lang/Object;
      //   68: astore_1
      //   69: aload_1
      //   70: monitorenter
      //   71: aload_0
      //   72: aload_0
      //   73: getfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   76: iconst_1
      //   77: iadd
      //   78: putfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   81: aload_1
      //   82: monitorexit
      //   83: aconst_null
      //   84: areturn
      //   85: astore_2
      //   86: aload_1
      //   87: monitorexit
      //   88: aload_2
      //   89: athrow
      //   90: astore_1
      //   91: aload_0
      //   92: getfield 42	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mLock	Ljava/lang/Object;
      //   95: astore_2
      //   96: aload_2
      //   97: monitorenter
      //   98: aload_0
      //   99: aload_0
      //   100: getfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   103: iconst_1
      //   104: iadd
      //   105: putfield 44	androidx/camera/extensions/ImageCaptureExtender$ImageCaptureAdapter:mEnabledSessionCount	I
      //   108: aload_2
      //   109: monitorexit
      //   110: aload_1
      //   111: athrow
      //   112: astore_1
      //   113: aload_2
      //   114: monitorexit
      //   115: aload_1
      //   116: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	117	0	this	ImageCaptureAdapter
      //   19	39	1	localObject1	Object
      //   59	4	1	localObject2	Object
      //   90	21	1	localObject4	Object
      //   112	4	1	localObject5	Object
      //   85	4	2	localObject7	Object
      // Exception table:
      //   from	to	target	type
      //   45	57	59	finally
      //   60	62	59	finally
      //   71	83	85	finally
      //   86	88	85	finally
      //   0	20	90	finally
      //   24	38	90	finally
      //   98	110	112	finally
      //   113	115	112	finally
    }
    
    public CaptureConfig onPresetSession()
    {
      if (this.mActive.get())
      {
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {
          public void run()
          {
            ImageCaptureExtender.checkPreviewEnabled(ImageCaptureExtender.ImageCaptureAdapter.this.mEffectMode, CameraX.getActiveUseCases());
          }
        });
        CaptureStageImpl localCaptureStageImpl = this.mImpl.onPresetSession();
        if (localCaptureStageImpl != null) {
          return new AdaptingCaptureStage(localCaptureStageImpl).getCaptureConfig();
        }
      }
      return null;
    }
    
    public void onUnbind()
    {
      synchronized (this.mLock)
      {
        this.mUnbind = true;
        if (this.mEnabledSessionCount == 0) {
          callDeInit();
        }
        return;
      }
    }
  }
}
