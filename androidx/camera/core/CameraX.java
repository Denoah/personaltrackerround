package androidx.camera.core;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import android.util.Size;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.CameraFactory;
import androidx.camera.core.impl.CameraIdFilter;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CameraRepository;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.UseCaseGroup;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.FutureCallback;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.concurrent.futures.CallbackToFutureAdapter.Resolver;
import androidx.core.util.Preconditions;
import androidx.lifecycle.LifecycleOwner;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class CameraX
{
  private static final String TAG = "CameraX";
  private static final long WAIT_INITIALIZED_TIMEOUT = 3L;
  private static ListenableFuture<Void> sInitializeFuture = Futures.immediateFailedFuture(new IllegalStateException("CameraX is not initialized."));
  static final Object sInitializeLock = new Object();
  static CameraX sInstance = null;
  private static ListenableFuture<Void> sShutdownFuture = Futures.immediateFuture(null);
  private static boolean sTargetInitialized = false;
  private final Executor mCameraExecutor;
  private CameraFactory mCameraFactory;
  final CameraRepository mCameraRepository = new CameraRepository();
  private Context mContext;
  private UseCaseConfigFactory mDefaultConfigFactory;
  private InternalInitState mInitState = InternalInitState.UNINITIALIZED;
  private final Object mInitializeLock = new Object();
  private ListenableFuture<Void> mShutdownInternalFuture = Futures.immediateFuture(null);
  private CameraDeviceSurfaceManager mSurfaceManager;
  private final UseCaseGroupRepository mUseCaseGroupRepository = new UseCaseGroupRepository();
  
  CameraX(Executor paramExecutor)
  {
    Preconditions.checkNotNull(paramExecutor);
    this.mCameraExecutor = paramExecutor;
  }
  
  private static void attach(String paramString, UseCase paramUseCase)
  {
    CameraInternal localCameraInternal = checkInitialized().getCameraRepository().getCamera(paramString);
    paramUseCase.addStateChangeCallback(localCameraInternal);
    paramUseCase.attachCameraControl(paramString, localCameraInternal.getCameraControlInternal());
  }
  
  public static Camera bindToLifecycle(LifecycleOwner paramLifecycleOwner, CameraSelector paramCameraSelector, UseCase... paramVarArgs)
  {
    Threads.checkMainThread();
    Object localObject1 = checkInitialized();
    UseCaseGroupLifecycleController localUseCaseGroupLifecycleController = ((CameraX)localObject1).getOrCreateUseCaseGroup(paramLifecycleOwner);
    UseCaseGroup localUseCaseGroup1 = localUseCaseGroupLifecycleController.getUseCaseGroup();
    Collection localCollection = ((CameraX)localObject1).mUseCaseGroupRepository.getUseCaseGroups();
    int i = paramVarArgs.length;
    int j = 0;
    for (int k = 0; k < i; k++)
    {
      localObject2 = paramVarArgs[k];
      Iterator localIterator = localCollection.iterator();
      while (localIterator.hasNext())
      {
        UseCaseGroup localUseCaseGroup2 = ((UseCaseGroupLifecycleController)localIterator.next()).getUseCaseGroup();
        if ((localUseCaseGroup2.contains((UseCase)localObject2)) && (localUseCaseGroup2 != localUseCaseGroup1)) {
          throw new IllegalStateException(String.format("Use case %s already bound to a different lifecycle.", new Object[] { localObject2 }));
        }
      }
    }
    paramCameraSelector = CameraSelector.Builder.fromSelector(paramCameraSelector);
    i = paramVarArgs.length;
    for (k = 0; k < i; k++)
    {
      localObject2 = paramVarArgs[k].getUseCaseConfig().getCameraSelector(null);
      if (localObject2 != null)
      {
        localObject2 = ((CameraSelector)localObject2).getCameraFilterSet().iterator();
        while (((Iterator)localObject2).hasNext()) {
          paramCameraSelector.appendFilter((CameraIdFilter)((Iterator)localObject2).next());
        }
      }
    }
    Object localObject2 = getCameraWithCameraSelector(paramCameraSelector.build());
    paramCameraSelector = ((CameraX)localObject1).getCameraRepository().getCamera((String)localObject2);
    i = paramVarArgs.length;
    for (k = 0; k < i; k++) {
      paramVarArgs[k].onBind(paramCameraSelector);
    }
    calculateSuggestedResolutions(paramLifecycleOwner, (String)localObject2, paramVarArgs);
    i = paramVarArgs.length;
    for (k = j; k < i; k++)
    {
      paramLifecycleOwner = paramVarArgs[k];
      localUseCaseGroup1.addUseCase(paramLifecycleOwner);
      localObject1 = paramLifecycleOwner.getAttachedCameraIds().iterator();
      while (((Iterator)localObject1).hasNext()) {
        attach((String)((Iterator)localObject1).next(), paramLifecycleOwner);
      }
    }
    localUseCaseGroupLifecycleController.notifyState();
    return paramCameraSelector;
  }
  
  private static void calculateSuggestedResolutions(LifecycleOwner paramLifecycleOwner, String paramString, UseCase... paramVarArgs)
  {
    paramLifecycleOwner = checkInitialized().getOrCreateUseCaseGroup(paramLifecycleOwner).getUseCaseGroup();
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    Iterator localIterator = paramLifecycleOwner.getUseCases().iterator();
    Object localObject1;
    Object localObject2;
    Object localObject3;
    while (localIterator.hasNext())
    {
      localObject1 = (UseCase)localIterator.next();
      localObject2 = ((UseCase)localObject1).getAttachedCameraIds().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        String str = (String)((Iterator)localObject2).next();
        localObject3 = (List)localHashMap1.get(str);
        paramLifecycleOwner = (LifecycleOwner)localObject3;
        if (localObject3 == null)
        {
          paramLifecycleOwner = new ArrayList();
          localHashMap1.put(str, paramLifecycleOwner);
        }
        paramLifecycleOwner.add(localObject1);
      }
    }
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++)
    {
      localObject1 = paramVarArgs[j];
      localObject3 = (List)localHashMap2.get(paramString);
      paramLifecycleOwner = (LifecycleOwner)localObject3;
      if (localObject3 == null)
      {
        paramLifecycleOwner = new ArrayList();
        localHashMap2.put(paramString, paramLifecycleOwner);
      }
      paramLifecycleOwner.add(localObject1);
    }
    paramString = localHashMap2.keySet().iterator();
    while (paramString.hasNext())
    {
      localObject1 = (String)paramString.next();
      localObject3 = getSurfaceManager().getSuggestedResolutions((String)localObject1, (List)localHashMap1.get(localObject1), (List)localHashMap2.get(localObject1));
      localIterator = ((List)localHashMap2.get(localObject1)).iterator();
      while (localIterator.hasNext())
      {
        localObject2 = (UseCase)localIterator.next();
        paramVarArgs = (Size)((Map)localObject3).get(localObject2);
        paramLifecycleOwner = new HashMap();
        paramLifecycleOwner.put(localObject1, paramVarArgs);
        ((UseCase)localObject2).updateSuggestedResolution(paramLifecycleOwner);
      }
    }
  }
  
  private static CameraX checkInitialized()
  {
    CameraX localCameraX = waitInitialized();
    Preconditions.checkState(localCameraX.isInitializedInternal(), "Must call CameraX.initialize() first");
    return localCameraX;
  }
  
  private static void detach(String paramString, List<UseCase> paramList)
  {
    CameraInternal localCameraInternal = checkInitialized().getCameraRepository().getCamera(paramString);
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      UseCase localUseCase = (UseCase)localIterator.next();
      localUseCase.removeStateChangeCallback(localCameraInternal);
      localUseCase.detachCameraControl(paramString);
    }
    localCameraInternal.removeOnlineUseCase(paramList);
  }
  
  public static Collection<UseCase> getActiveUseCases()
  {
    Object localObject = checkInitialized().mUseCaseGroupRepository.getUseCaseGroups().iterator();
    while (((Iterator)localObject).hasNext())
    {
      UseCaseGroupLifecycleController localUseCaseGroupLifecycleController = (UseCaseGroupLifecycleController)((Iterator)localObject).next();
      if (localUseCaseGroupLifecycleController.getUseCaseGroup().isActive()) {
        return localUseCaseGroupLifecycleController.getUseCaseGroup().getUseCases();
      }
    }
    localObject = null;
    return localObject;
  }
  
  private CameraDeviceSurfaceManager getCameraDeviceSurfaceManager()
  {
    CameraDeviceSurfaceManager localCameraDeviceSurfaceManager = this.mSurfaceManager;
    if (localCameraDeviceSurfaceManager != null) {
      return localCameraDeviceSurfaceManager;
    }
    throw new IllegalStateException("CameraX not initialized yet.");
  }
  
  public static CameraFactory getCameraFactory()
  {
    CameraFactory localCameraFactory = checkInitialized().mCameraFactory;
    if (localCameraFactory != null) {
      return localCameraFactory;
    }
    throw new IllegalStateException("CameraX not initialized yet.");
  }
  
  public static CameraInfoInternal getCameraInfo(String paramString)
  {
    return checkInitialized().getCameraRepository().getCamera(paramString).getCameraInfoInternal();
  }
  
  private CameraRepository getCameraRepository()
  {
    return this.mCameraRepository;
  }
  
  public static String getCameraWithCameraSelector(CameraSelector paramCameraSelector)
  {
    checkInitialized();
    try
    {
      paramCameraSelector = paramCameraSelector.select(getCameraFactory().getAvailableCameraIds());
      return paramCameraSelector;
    }
    catch (CameraInfoUnavailableException paramCameraSelector) {}
    return null;
  }
  
  public static String getCameraWithLensFacing(int paramInt)
    throws CameraInfoUnavailableException
  {
    checkInitialized();
    return getCameraFactory().cameraIdForLensFacing(paramInt);
  }
  
  public static Context getContext()
  {
    return checkInitialized().mContext;
  }
  
  private UseCaseConfigFactory getDefaultConfigFactory()
  {
    UseCaseConfigFactory localUseCaseConfigFactory = this.mDefaultConfigFactory;
    if (localUseCaseConfigFactory != null) {
      return localUseCaseConfigFactory;
    }
    throw new IllegalStateException("CameraX not initialized yet.");
  }
  
  public static int getDefaultLensFacing()
    throws CameraInfoUnavailableException
  {
    checkInitialized();
    Iterator localIterator = Arrays.asList(new Integer[] { Integer.valueOf(1), Integer.valueOf(0) }).iterator();
    while (localIterator.hasNext())
    {
      localInteger = (Integer)localIterator.next();
      if (getCameraFactory().cameraIdForLensFacing(localInteger.intValue()) != null) {
        break label70;
      }
    }
    Integer localInteger = null;
    label70:
    if (localInteger != null) {
      return localInteger.intValue();
    }
    throw new IllegalStateException("Unable to get default lens facing.");
  }
  
  public static <C extends UseCaseConfig<?>> C getDefaultUseCaseConfig(Class<C> paramClass, CameraInfo paramCameraInfo)
  {
    return checkInitialized().getDefaultConfigFactory().getConfig(paramClass, paramCameraInfo);
  }
  
  private static ListenableFuture<CameraX> getInstance()
  {
    synchronized (sInitializeLock)
    {
      ListenableFuture localListenableFuture = getInstanceLocked();
      return localListenableFuture;
    }
  }
  
  private static ListenableFuture<CameraX> getInstanceLocked()
  {
    if (!sTargetInitialized) {
      return Futures.immediateFailedFuture(new IllegalStateException("Must call CameraX.initialize() first"));
    }
    CameraX localCameraX = sInstance;
    return Futures.transform(sInitializeFuture, new _..Lambda.CameraX.VEE9dh7_HJjJWwjbSbma_1Kbo5Y(localCameraX), CameraXExecutors.directExecutor());
  }
  
  public static ListenableFuture<CameraX> getOrCreateInstance(Context paramContext)
  {
    Preconditions.checkNotNull(paramContext, "Context must not be null.");
    synchronized (sInitializeLock)
    {
      Object localObject2 = getInstanceLocked();
      boolean bool = ((ListenableFuture)localObject2).isDone();
      Object localObject3 = null;
      Object localObject4 = localObject2;
      if (bool) {
        try
        {
          ((ListenableFuture)localObject2).get();
          localObject4 = localObject2;
        }
        catch (ExecutionException localExecutionException)
        {
          shutdownLocked();
          Object localObject5 = null;
        }
        catch (InterruptedException localInterruptedException)
        {
          paramContext = new java/lang/RuntimeException;
          paramContext.<init>("Unexpected thread interrupt. Should not be possible since future is already complete.", localInterruptedException);
          throw paramContext;
        }
      }
      localObject2 = localInterruptedException;
      if (localInterruptedException == null)
      {
        Application localApplication = (Application)paramContext.getApplicationContext();
        if ((localApplication instanceof CameraXConfig.Provider))
        {
          paramContext = (CameraXConfig.Provider)localApplication;
        }
        else
        {
          try
          {
            paramContext = (CameraXConfig.Provider)Class.forName(localApplication.getResources().getString(R.string.androidx_camera_default_config_provider)).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
          }
          catch (IllegalAccessException paramContext) {}catch (NoSuchMethodException paramContext) {}catch (InvocationTargetException paramContext) {}catch (InstantiationException paramContext) {}catch (ClassNotFoundException paramContext) {}catch (Resources.NotFoundException paramContext) {}
          Log.e("CameraX", "Failed to retrieve default CameraXConfig.Provider from resources", paramContext);
          paramContext = localObject3;
        }
        if (paramContext != null)
        {
          initializeLocked(localApplication, paramContext.getCameraXConfig());
          localObject2 = getInstanceLocked();
        }
        else
        {
          paramContext = new java/lang/IllegalStateException;
          paramContext.<init>("CameraX is not initialized properly. Either the CameraXConfig.Provider interface must be implemented by your Application class or a CameraXConfig must be explicitly provided for initialization.");
          throw paramContext;
        }
      }
      return localObject2;
    }
  }
  
  private UseCaseGroupLifecycleController getOrCreateUseCaseGroup(LifecycleOwner paramLifecycleOwner)
  {
    this.mUseCaseGroupRepository.getOrCreateUseCaseGroup(paramLifecycleOwner, new UseCaseGroupRepository.UseCaseGroupSetup()
    {
      public void setup(UseCaseGroup paramAnonymousUseCaseGroup)
      {
        paramAnonymousUseCaseGroup.setListener(CameraX.this.mCameraRepository);
      }
    });
  }
  
  public static CameraDeviceSurfaceManager getSurfaceManager()
  {
    return checkInitialized().getCameraDeviceSurfaceManager();
  }
  
  public static boolean hasCamera(CameraSelector paramCameraSelector)
    throws CameraInfoUnavailableException
  {
    checkInitialized();
    try
    {
      paramCameraSelector.select(getCameraFactory().getAvailableCameraIds());
      return true;
    }
    catch (IllegalArgumentException paramCameraSelector) {}
    return false;
  }
  
  private ListenableFuture<Void> initInternal(Context paramContext, CameraXConfig paramCameraXConfig)
  {
    synchronized (this.mInitializeLock)
    {
      boolean bool;
      if (this.mInitState == InternalInitState.UNINITIALIZED) {
        bool = true;
      } else {
        bool = false;
      }
      Preconditions.checkState(bool, "CameraX.initInternal() should only be called once per instance");
      this.mInitState = InternalInitState.INITIALIZING;
      _..Lambda.CameraX.4E6qa025Kj0hVMOg15hnO_5sf0U local4E6qa025Kj0hVMOg15hnO_5sf0U = new androidx/camera/core/_$$Lambda$CameraX$4E6qa025Kj0hVMOg15hnO_5sf0U;
      local4E6qa025Kj0hVMOg15hnO_5sf0U.<init>(this, paramContext, paramCameraXConfig);
      paramContext = CallbackToFutureAdapter.getFuture(local4E6qa025Kj0hVMOg15hnO_5sf0U);
      return paramContext;
    }
  }
  
  public static ListenableFuture<Void> initialize(Context paramContext, CameraXConfig paramCameraXConfig)
  {
    synchronized (sInitializeLock)
    {
      paramContext = initializeLocked(paramContext, paramCameraXConfig);
      return paramContext;
    }
  }
  
  private static ListenableFuture<Void> initializeLocked(Context paramContext, CameraXConfig paramCameraXConfig)
  {
    Preconditions.checkNotNull(paramContext);
    Preconditions.checkNotNull(paramCameraXConfig);
    Preconditions.checkState(sTargetInitialized ^ true, "Must call CameraX.shutdown() first.");
    sTargetInitialized = true;
    Executor localExecutor = paramCameraXConfig.getCameraExecutor(null);
    Object localObject = localExecutor;
    if (localExecutor == null) {
      localObject = new CameraExecutor();
    }
    localObject = new CameraX((Executor)localObject);
    sInstance = (CameraX)localObject;
    paramContext = CallbackToFutureAdapter.getFuture(new _..Lambda.CameraX.FR7eKDFrmm030DhYj6aYstpPnQI((CameraX)localObject, paramContext, paramCameraXConfig));
    sInitializeFuture = paramContext;
    return paramContext;
  }
  
  public static boolean isBound(UseCase paramUseCase)
  {
    Iterator localIterator = checkInitialized().mUseCaseGroupRepository.getUseCaseGroups().iterator();
    while (localIterator.hasNext()) {
      if (((UseCaseGroupLifecycleController)localIterator.next()).getUseCaseGroup().contains(paramUseCase)) {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isInitialized()
  {
    synchronized (sInitializeLock)
    {
      boolean bool;
      if ((sInstance != null) && (sInstance.isInitializedInternal())) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  private boolean isInitializedInternal()
  {
    synchronized (this.mInitializeLock)
    {
      boolean bool;
      if (this.mInitState == InternalInitState.INITIALIZED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
  }
  
  public static ListenableFuture<Void> shutdown()
  {
    synchronized (sInitializeLock)
    {
      ListenableFuture localListenableFuture = shutdownLocked();
      return localListenableFuture;
    }
  }
  
  private ListenableFuture<Void> shutdownInternal()
  {
    synchronized (this.mInitializeLock)
    {
      int i = 3.$SwitchMap$androidx$camera$core$CameraX$InternalInitState[this.mInitState.ordinal()];
      if (i != 1)
      {
        if (i != 2)
        {
          if (i == 3)
          {
            this.mInitState = InternalInitState.SHUTDOWN;
            localObject2 = new androidx/camera/core/_$$Lambda$CameraX$kZGZ3U2NTHv0y_Uj9y_NdcLq1kc;
            ((_..Lambda.CameraX.kZGZ3U2NTHv0y_Uj9y_NdcLq1kc)localObject2).<init>(this);
            this.mShutdownInternalFuture = CallbackToFutureAdapter.getFuture((CallbackToFutureAdapter.Resolver)localObject2);
          }
          localObject2 = this.mShutdownInternalFuture;
          return localObject2;
        }
        localObject2 = new java/lang/IllegalStateException;
        ((IllegalStateException)localObject2).<init>("CameraX could not be shutdown when it is initializing.");
        throw ((Throwable)localObject2);
      }
      this.mInitState = InternalInitState.SHUTDOWN;
      Object localObject2 = Futures.immediateFuture(null);
      return localObject2;
    }
  }
  
  private static ListenableFuture<Void> shutdownLocked()
  {
    if (!sTargetInitialized) {
      return sShutdownFuture;
    }
    sTargetInitialized = false;
    Object localObject = sInstance;
    sInstance = null;
    localObject = CallbackToFutureAdapter.getFuture(new _..Lambda.CameraX.41c4Yl7XOftWrniEb_aDSAzTLpg((CameraX)localObject));
    sShutdownFuture = (ListenableFuture)localObject;
    return localObject;
  }
  
  public static void unbind(UseCase... paramVarArgs)
  {
    Threads.checkMainThread();
    Collection localCollection = checkInitialized().mUseCaseGroupRepository.getUseCaseGroups();
    HashMap localHashMap = new HashMap();
    int i = paramVarArgs.length;
    int j = 0;
    Object localObject1;
    for (int k = 0; k < i; k++)
    {
      UseCase localUseCase = paramVarArgs[k];
      Iterator localIterator1 = localCollection.iterator();
      while (localIterator1.hasNext()) {
        if (((UseCaseGroupLifecycleController)localIterator1.next()).getUseCaseGroup().removeUseCase(localUseCase))
        {
          Iterator localIterator2 = localUseCase.getAttachedCameraIds().iterator();
          while (localIterator2.hasNext())
          {
            String str = (String)localIterator2.next();
            localObject1 = (List)localHashMap.get(str);
            localObject2 = localObject1;
            if (localObject1 == null)
            {
              localObject2 = new ArrayList();
              localHashMap.put(str, localObject2);
            }
            ((List)localObject2).add(localUseCase);
          }
        }
      }
    }
    Object localObject2 = localHashMap.keySet().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (String)((Iterator)localObject2).next();
      detach((String)localObject1, (List)localHashMap.get(localObject1));
    }
    i = paramVarArgs.length;
    for (k = j; k < i; k++) {
      paramVarArgs[k].clear();
    }
  }
  
  public static void unbindAll()
  {
    Threads.checkMainThread();
    Object localObject = checkInitialized().mUseCaseGroupRepository.getUseCaseGroups();
    ArrayList localArrayList = new ArrayList();
    localObject = ((Collection)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      localArrayList.addAll(((UseCaseGroupLifecycleController)((Iterator)localObject).next()).getUseCaseGroup().getUseCases());
    }
    unbind((UseCase[])localArrayList.toArray(new UseCase[0]));
  }
  
  private static CameraX waitInitialized()
  {
    Object localObject = getInstance();
    try
    {
      localObject = (CameraX)((ListenableFuture)localObject).get(3L, TimeUnit.SECONDS);
      return localObject;
    }
    catch (InterruptedException localInterruptedException)
    {
      throw new IllegalStateException(localInterruptedException);
    }
    catch (TimeoutException localTimeoutException)
    {
      throw new IllegalStateException(localTimeoutException);
    }
    catch (ExecutionException localExecutionException)
    {
      throw new IllegalStateException(localExecutionException);
    }
  }
  
  private static enum InternalInitState
  {
    static
    {
      INITIALIZING = new InternalInitState("INITIALIZING", 1);
      INITIALIZED = new InternalInitState("INITIALIZED", 2);
      InternalInitState localInternalInitState = new InternalInitState("SHUTDOWN", 3);
      SHUTDOWN = localInternalInitState;
      $VALUES = new InternalInitState[] { UNINITIALIZED, INITIALIZING, INITIALIZED, localInternalInitState };
    }
    
    private InternalInitState() {}
  }
}
