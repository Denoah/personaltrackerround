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
import androidx.camera.core.Preview.Builder;
import androidx.camera.core.UseCase;
import androidx.camera.core.UseCase.EventCallback;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config.Option;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.PreviewConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.extensions.impl.CaptureStageImpl;
import androidx.camera.extensions.impl.PreviewExtenderImpl;
import androidx.camera.extensions.impl.PreviewImageProcessorImpl;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class PreviewExtender
{
  static final Config.Option<ExtensionsManager.EffectMode> OPTION_PREVIEW_EXTENDER_MODE = Config.Option.create("camerax.extensions.previewExtender.mode", ExtensionsManager.EffectMode.class);
  private static final String TAG = "PreviewExtender";
  private Preview.Builder mBuilder;
  private ExtensionsManager.EffectMode mEffectMode;
  private ExtensionCameraIdFilter mExtensionCameraIdFilter;
  private PreviewExtenderImpl mImpl;
  
  public PreviewExtender() {}
  
  static void checkImageCaptureEnabled(ExtensionsManager.EffectMode paramEffectMode, Collection<UseCase> paramCollection)
  {
    if ((paramCollection != null) && (!paramCollection.isEmpty()))
    {
      paramCollection = paramCollection.iterator();
      int i = 0;
      int j = 0;
      while (paramCollection.hasNext())
      {
        ExtensionsManager.EffectMode localEffectMode = (ExtensionsManager.EffectMode)((UseCase)paramCollection.next()).getUseCaseConfig().retrieveOption(ImageCaptureExtender.OPTION_IMAGE_CAPTURE_EXTENDER_MODE, null);
        if (paramEffectMode == localEffectMode) {
          j = 1;
        } else if (localEffectMode != null) {
          i = 1;
        }
      }
      if (i != 0) {
        ExtensionsManager.postExtensionsError(ExtensionsErrorListener.ExtensionsErrorCode.MISMATCHED_EXTENSIONS_ENABLED);
      } else if (j == 0) {
        ExtensionsManager.postExtensionsError(ExtensionsErrorListener.ExtensionsErrorCode.IMAGE_CAPTURE_EXTENSION_REQUIRED);
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
      Log.e("PreviewExtender", "getSupportedResolution interface is not implemented in vendor library.");
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
    int i = 1.$SwitchMap$androidx$camera$extensions$impl$PreviewExtenderImpl$ProcessorType[this.mImpl.getProcessorType().ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        paramCameraSelector = new PreviewExtenderAdapter(this.mImpl, this.mEffectMode, null);
      }
      else
      {
        paramCameraSelector = new AdaptingPreviewProcessor((PreviewImageProcessorImpl)this.mImpl.getProcessor());
        this.mBuilder.setCaptureProcessor(paramCameraSelector);
        paramCameraSelector = new PreviewExtenderAdapter(this.mImpl, this.mEffectMode, paramCameraSelector);
      }
    }
    else
    {
      paramCameraSelector = new AdaptingRequestUpdateProcessor(this.mImpl);
      this.mBuilder.setImageInfoProcessor(paramCameraSelector);
      paramCameraSelector = new PreviewExtenderAdapter(this.mImpl, this.mEffectMode, paramCameraSelector);
    }
    new Camera2ImplConfig.Extender(this.mBuilder).setCameraEventCallback(new CameraEventCallbacks(new CameraEventCallback[] { paramCameraSelector }));
    this.mBuilder.setUseCaseEventCallback(paramCameraSelector);
    this.mBuilder.getMutableConfig().insertOption(OPTION_PREVIEW_EXTENDER_MODE, this.mEffectMode);
    setSupportedResolutions();
  }
  
  void init(Preview.Builder paramBuilder, PreviewExtenderImpl paramPreviewExtenderImpl, ExtensionsManager.EffectMode paramEffectMode)
  {
    this.mBuilder = paramBuilder;
    this.mImpl = paramPreviewExtenderImpl;
    this.mEffectMode = paramEffectMode;
    this.mExtensionCameraIdFilter = new ExtensionCameraIdFilter(paramPreviewExtenderImpl);
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
  
  static abstract interface CloseableProcessor
  {
    public abstract void close();
  }
  
  private static class PreviewExtenderAdapter
    extends CameraEventCallback
    implements UseCase.EventCallback
  {
    volatile boolean mActive = true;
    final PreviewExtender.CloseableProcessor mCloseableProcessor;
    final ExtensionsManager.EffectMode mEffectMode;
    private volatile int mEnabledSessionCount = 0;
    final PreviewExtenderImpl mImpl;
    final Object mLock = new Object();
    private volatile boolean mUnbind = false;
    
    PreviewExtenderAdapter(PreviewExtenderImpl paramPreviewExtenderImpl, ExtensionsManager.EffectMode paramEffectMode, PreviewExtender.CloseableProcessor paramCloseableProcessor)
    {
      this.mImpl = paramPreviewExtenderImpl;
      this.mEffectMode = paramEffectMode;
      this.mCloseableProcessor = paramCloseableProcessor;
    }
    
    private void callDeInit()
    {
      synchronized (this.mLock)
      {
        if (this.mActive)
        {
          if (this.mCloseableProcessor != null) {
            this.mCloseableProcessor.close();
          }
          this.mImpl.onDeInit();
          this.mActive = false;
        }
        return;
      }
    }
    
    public void onBind(String paramString)
    {
      if (this.mActive)
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
      //   1: getfield 36	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mLock	Ljava/lang/Object;
      //   4: astore_1
      //   5: aload_1
      //   6: monitorenter
      //   7: aload_0
      //   8: getfield 31	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mActive	Z
      //   11: ifeq +77 -> 88
      //   14: aload_0
      //   15: getfield 42	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mImpl	Landroidx/camera/extensions/impl/PreviewExtenderImpl;
      //   18: invokeinterface 81 1 0
      //   23: astore_2
      //   24: aload_2
      //   25: ifnull +63 -> 88
      //   28: new 83	androidx/camera/extensions/AdaptingCaptureStage
      //   31: astore_3
      //   32: aload_3
      //   33: aload_2
      //   34: invokespecial 86	androidx/camera/extensions/AdaptingCaptureStage:<init>	(Landroidx/camera/extensions/impl/CaptureStageImpl;)V
      //   37: aload_3
      //   38: invokevirtual 89	androidx/camera/extensions/AdaptingCaptureStage:getCaptureConfig	()Landroidx/camera/core/impl/CaptureConfig;
      //   41: astore_2
      //   42: aload_1
      //   43: monitorexit
      //   44: aload_0
      //   45: getfield 36	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mLock	Ljava/lang/Object;
      //   48: astore_1
      //   49: aload_1
      //   50: monitorenter
      //   51: aload_0
      //   52: aload_0
      //   53: getfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   56: iconst_1
      //   57: isub
      //   58: putfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   61: aload_0
      //   62: getfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   65: ifne +14 -> 79
      //   68: aload_0
      //   69: getfield 40	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mUnbind	Z
      //   72: ifeq +7 -> 79
      //   75: aload_0
      //   76: invokespecial 91	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:callDeInit	()V
      //   79: aload_1
      //   80: monitorexit
      //   81: aload_2
      //   82: areturn
      //   83: astore_2
      //   84: aload_1
      //   85: monitorexit
      //   86: aload_2
      //   87: athrow
      //   88: aload_1
      //   89: monitorexit
      //   90: aload_0
      //   91: getfield 36	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mLock	Ljava/lang/Object;
      //   94: astore_1
      //   95: aload_1
      //   96: monitorenter
      //   97: aload_0
      //   98: aload_0
      //   99: getfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   102: iconst_1
      //   103: isub
      //   104: putfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   107: aload_0
      //   108: getfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   111: ifne +14 -> 125
      //   114: aload_0
      //   115: getfield 40	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mUnbind	Z
      //   118: ifeq +7 -> 125
      //   121: aload_0
      //   122: invokespecial 91	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:callDeInit	()V
      //   125: aload_1
      //   126: monitorexit
      //   127: aconst_null
      //   128: areturn
      //   129: astore_2
      //   130: aload_1
      //   131: monitorexit
      //   132: aload_2
      //   133: athrow
      //   134: astore_2
      //   135: aload_1
      //   136: monitorexit
      //   137: aload_2
      //   138: athrow
      //   139: astore_2
      //   140: aload_0
      //   141: getfield 36	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mLock	Ljava/lang/Object;
      //   144: astore_1
      //   145: aload_1
      //   146: monitorenter
      //   147: aload_0
      //   148: aload_0
      //   149: getfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   152: iconst_1
      //   153: isub
      //   154: putfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   157: aload_0
      //   158: getfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   161: ifne +14 -> 175
      //   164: aload_0
      //   165: getfield 40	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mUnbind	Z
      //   168: ifeq +7 -> 175
      //   171: aload_0
      //   172: invokespecial 91	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:callDeInit	()V
      //   175: aload_1
      //   176: monitorexit
      //   177: aload_2
      //   178: athrow
      //   179: astore_2
      //   180: aload_1
      //   181: monitorexit
      //   182: aload_2
      //   183: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	184	0	this	PreviewExtenderAdapter
      //   23	59	2	localObject2	Object
      //   83	4	2	localObject3	Object
      //   129	4	2	localObject4	Object
      //   134	4	2	localObject5	Object
      //   139	39	2	localObject6	Object
      //   179	4	2	localObject7	Object
      //   31	7	3	localAdaptingCaptureStage	AdaptingCaptureStage
      // Exception table:
      //   from	to	target	type
      //   51	79	83	finally
      //   79	81	83	finally
      //   84	86	83	finally
      //   97	125	129	finally
      //   125	127	129	finally
      //   130	132	129	finally
      //   7	24	134	finally
      //   28	44	134	finally
      //   88	90	134	finally
      //   135	137	134	finally
      //   0	7	139	finally
      //   137	139	139	finally
      //   147	175	179	finally
      //   175	177	179	finally
      //   180	182	179	finally
    }
    
    /* Error */
    public CaptureConfig onEnableSession()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 36	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mLock	Ljava/lang/Object;
      //   4: astore_1
      //   5: aload_1
      //   6: monitorenter
      //   7: aload_0
      //   8: getfield 31	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mActive	Z
      //   11: ifeq +59 -> 70
      //   14: aload_0
      //   15: getfield 42	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mImpl	Landroidx/camera/extensions/impl/PreviewExtenderImpl;
      //   18: invokeinterface 94 1 0
      //   23: astore_2
      //   24: aload_2
      //   25: ifnull +45 -> 70
      //   28: new 83	androidx/camera/extensions/AdaptingCaptureStage
      //   31: astore_3
      //   32: aload_3
      //   33: aload_2
      //   34: invokespecial 86	androidx/camera/extensions/AdaptingCaptureStage:<init>	(Landroidx/camera/extensions/impl/CaptureStageImpl;)V
      //   37: aload_3
      //   38: invokevirtual 89	androidx/camera/extensions/AdaptingCaptureStage:getCaptureConfig	()Landroidx/camera/core/impl/CaptureConfig;
      //   41: astore_2
      //   42: aload_1
      //   43: monitorexit
      //   44: aload_0
      //   45: getfield 36	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mLock	Ljava/lang/Object;
      //   48: astore_1
      //   49: aload_1
      //   50: monitorenter
      //   51: aload_0
      //   52: aload_0
      //   53: getfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   56: iconst_1
      //   57: iadd
      //   58: putfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   61: aload_1
      //   62: monitorexit
      //   63: aload_2
      //   64: areturn
      //   65: astore_2
      //   66: aload_1
      //   67: monitorexit
      //   68: aload_2
      //   69: athrow
      //   70: aload_1
      //   71: monitorexit
      //   72: aload_0
      //   73: getfield 36	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mLock	Ljava/lang/Object;
      //   76: astore_1
      //   77: aload_1
      //   78: monitorenter
      //   79: aload_0
      //   80: aload_0
      //   81: getfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   84: iconst_1
      //   85: iadd
      //   86: putfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   89: aload_1
      //   90: monitorexit
      //   91: aconst_null
      //   92: areturn
      //   93: astore_2
      //   94: aload_1
      //   95: monitorexit
      //   96: aload_2
      //   97: athrow
      //   98: astore_2
      //   99: aload_1
      //   100: monitorexit
      //   101: aload_2
      //   102: athrow
      //   103: astore_2
      //   104: aload_0
      //   105: getfield 36	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mLock	Ljava/lang/Object;
      //   108: astore_1
      //   109: aload_1
      //   110: monitorenter
      //   111: aload_0
      //   112: aload_0
      //   113: getfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   116: iconst_1
      //   117: iadd
      //   118: putfield 38	androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter:mEnabledSessionCount	I
      //   121: aload_1
      //   122: monitorexit
      //   123: aload_2
      //   124: athrow
      //   125: astore_2
      //   126: aload_1
      //   127: monitorexit
      //   128: aload_2
      //   129: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	130	0	this	PreviewExtenderAdapter
      //   23	41	2	localObject2	Object
      //   65	4	2	localObject3	Object
      //   93	4	2	localObject4	Object
      //   98	4	2	localObject5	Object
      //   103	21	2	localObject6	Object
      //   125	4	2	localObject7	Object
      //   31	7	3	localAdaptingCaptureStage	AdaptingCaptureStage
      // Exception table:
      //   from	to	target	type
      //   51	63	65	finally
      //   66	68	65	finally
      //   79	91	93	finally
      //   94	96	93	finally
      //   7	24	98	finally
      //   28	44	98	finally
      //   70	72	98	finally
      //   99	101	98	finally
      //   0	7	103	finally
      //   101	103	103	finally
      //   111	123	125	finally
      //   126	128	125	finally
    }
    
    public CaptureConfig onPresetSession()
    {
      synchronized (this.mLock)
      {
        Object localObject2;
        if (this.mActive)
        {
          localObject2 = new android/os/Handler;
          ((Handler)localObject2).<init>(Looper.getMainLooper());
          localObject3 = new androidx/camera/extensions/PreviewExtender$PreviewExtenderAdapter$1;
          ((1)localObject3).<init>(this);
          ((Handler)localObject2).post((Runnable)localObject3);
        }
        Object localObject3 = this.mImpl.onPresetSession();
        if (localObject3 != null)
        {
          localObject2 = new androidx/camera/extensions/AdaptingCaptureStage;
          ((AdaptingCaptureStage)localObject2).<init>((CaptureStageImpl)localObject3);
          localObject3 = ((AdaptingCaptureStage)localObject2).getCaptureConfig();
          return localObject3;
        }
        return null;
      }
    }
    
    public CaptureConfig onRepeating()
    {
      synchronized (this.mLock)
      {
        if (this.mActive)
        {
          Object localObject2 = this.mImpl.getCaptureStage();
          if (localObject2 != null)
          {
            AdaptingCaptureStage localAdaptingCaptureStage = new androidx/camera/extensions/AdaptingCaptureStage;
            localAdaptingCaptureStage.<init>((CaptureStageImpl)localObject2);
            localObject2 = localAdaptingCaptureStage.getCaptureConfig();
            return localObject2;
          }
        }
        return null;
      }
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
