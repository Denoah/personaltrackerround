package io.fabric.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import io.fabric.sdk.android.services.common.DataCollectionArbiter;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fabric
{
  static final String ANSWERS_KIT_IDENTIFIER = "com.crashlytics.sdk.android:answers";
  static final String CRASHLYTICS_KIT_IDENTIFIER = "com.crashlytics.sdk.android:crashlytics";
  static final boolean DEFAULT_DEBUGGABLE = false;
  static final Logger DEFAULT_LOGGER = new DefaultLogger();
  static final String ROOT_DIR = ".Fabric";
  public static final String TAG = "Fabric";
  static volatile Fabric singleton;
  private WeakReference<Activity> activity;
  private ActivityLifecycleManager activityLifecycleManager;
  private final Context context;
  final boolean debuggable;
  private final ExecutorService executorService;
  private final IdManager idManager;
  private final InitializationCallback<Fabric> initializationCallback;
  private AtomicBoolean initialized;
  private final InitializationCallback<?> kitInitializationCallback;
  private final Map<Class<? extends Kit>, Kit> kits;
  final Logger logger;
  private final Handler mainHandler;
  
  Fabric(Context paramContext, Map<Class<? extends Kit>, Kit> paramMap, PriorityThreadPoolExecutor paramPriorityThreadPoolExecutor, Handler paramHandler, Logger paramLogger, boolean paramBoolean, InitializationCallback paramInitializationCallback, IdManager paramIdManager, Activity paramActivity)
  {
    this.context = paramContext;
    this.kits = paramMap;
    this.executorService = paramPriorityThreadPoolExecutor;
    this.mainHandler = paramHandler;
    this.logger = paramLogger;
    this.debuggable = paramBoolean;
    this.initializationCallback = paramInitializationCallback;
    this.initialized = new AtomicBoolean(false);
    this.kitInitializationCallback = createKitInitializationCallback(paramMap.size());
    this.idManager = paramIdManager;
    setCurrentActivity(paramActivity);
  }
  
  private static void addToKitMap(Map<Class<? extends Kit>, Kit> paramMap, Collection<? extends Kit> paramCollection)
  {
    paramCollection = paramCollection.iterator();
    while (paramCollection.hasNext())
    {
      Kit localKit = (Kit)paramCollection.next();
      paramMap.put(localKit.getClass(), localKit);
      if ((localKit instanceof KitGroup)) {
        addToKitMap(paramMap, ((KitGroup)localKit).getKits());
      }
    }
  }
  
  private static Activity extractActivity(Context paramContext)
  {
    if ((paramContext instanceof Activity)) {
      return (Activity)paramContext;
    }
    return null;
  }
  
  public static <T extends Kit> T getKit(Class<T> paramClass)
  {
    return (Kit)singleton().kits.get(paramClass);
  }
  
  private static Map<Class<? extends Kit>, Kit> getKitMap(Collection<? extends Kit> paramCollection)
  {
    HashMap localHashMap = new HashMap(paramCollection.size());
    addToKitMap(localHashMap, paramCollection);
    return localHashMap;
  }
  
  public static Logger getLogger()
  {
    if (singleton == null) {
      return DEFAULT_LOGGER;
    }
    return singleton.logger;
  }
  
  private void init()
  {
    ActivityLifecycleManager localActivityLifecycleManager = new ActivityLifecycleManager(this.context);
    this.activityLifecycleManager = localActivityLifecycleManager;
    localActivityLifecycleManager.registerCallbacks(new ActivityLifecycleManager.Callbacks()
    {
      public void onActivityCreated(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
      {
        Fabric.this.setCurrentActivity(paramAnonymousActivity);
      }
      
      public void onActivityResumed(Activity paramAnonymousActivity)
      {
        Fabric.this.setCurrentActivity(paramAnonymousActivity);
      }
      
      public void onActivityStarted(Activity paramAnonymousActivity)
      {
        Fabric.this.setCurrentActivity(paramAnonymousActivity);
      }
    });
    initializeKits(this.context);
  }
  
  public static boolean isDebuggable()
  {
    if (singleton == null) {
      return false;
    }
    return singleton.debuggable;
  }
  
  public static boolean isInitialized()
  {
    boolean bool;
    if ((singleton != null) && (singleton.initialized.get())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private static void setFabric(Fabric paramFabric)
  {
    singleton = paramFabric;
    paramFabric.init();
  }
  
  static Fabric singleton()
  {
    if (singleton != null) {
      return singleton;
    }
    throw new IllegalStateException("Must Initialize Fabric before using singleton()");
  }
  
  public static Fabric with(Context paramContext, Kit... paramVarArgs)
  {
    if (singleton == null) {
      try
      {
        if (singleton == null)
        {
          Builder localBuilder = new io/fabric/sdk/android/Fabric$Builder;
          localBuilder.<init>(paramContext);
          setFabric(localBuilder.kits(paramVarArgs).build());
        }
      }
      finally {}
    }
    return singleton;
  }
  
  public static Fabric with(Fabric paramFabric)
  {
    if (singleton == null) {
      try
      {
        if (singleton == null) {
          setFabric(paramFabric);
        }
      }
      finally {}
    }
    return singleton;
  }
  
  void addAnnotatedDependencies(Map<Class<? extends Kit>, Kit> paramMap, Kit paramKit)
  {
    Object localObject = paramKit.dependsOnAnnotation;
    if (localObject != null)
    {
      Class[] arrayOfClass = ((DependsOn)localObject).value();
      int i = arrayOfClass.length;
      int j = 0;
      while (j < i)
      {
        Class localClass = arrayOfClass[j];
        if (localClass.isInterface())
        {
          localObject = paramMap.values().iterator();
          while (((Iterator)localObject).hasNext())
          {
            Kit localKit = (Kit)((Iterator)localObject).next();
            if (localClass.isAssignableFrom(localKit.getClass())) {
              paramKit.initializationTask.addDependency(localKit.initializationTask);
            }
          }
        }
        if ((Kit)paramMap.get(localClass) != null)
        {
          paramKit.initializationTask.addDependency(((Kit)paramMap.get(localClass)).initializationTask);
          j++;
        }
        else
        {
          throw new UnmetDependencyException("Referenced Kit was null, does the kit exist?");
        }
      }
    }
  }
  
  InitializationCallback<?> createKitInitializationCallback(final int paramInt)
  {
    new InitializationCallback()
    {
      final CountDownLatch kitInitializedLatch = new CountDownLatch(paramInt);
      
      public void failure(Exception paramAnonymousException)
      {
        Fabric.this.initializationCallback.failure(paramAnonymousException);
      }
      
      public void success(Object paramAnonymousObject)
      {
        this.kitInitializedLatch.countDown();
        if (this.kitInitializedLatch.getCount() == 0L)
        {
          Fabric.this.initialized.set(true);
          Fabric.this.initializationCallback.success(Fabric.this);
        }
      }
    };
  }
  
  public ActivityLifecycleManager getActivityLifecycleManager()
  {
    return this.activityLifecycleManager;
  }
  
  public String getAppIdentifier()
  {
    return this.idManager.getAppIdentifier();
  }
  
  public String getAppInstallIdentifier()
  {
    return this.idManager.getAppInstallIdentifier();
  }
  
  public Activity getCurrentActivity()
  {
    WeakReference localWeakReference = this.activity;
    if (localWeakReference != null) {
      return (Activity)localWeakReference.get();
    }
    return null;
  }
  
  public ExecutorService getExecutorService()
  {
    return this.executorService;
  }
  
  public String getIdentifier()
  {
    return "io.fabric.sdk.android:fabric";
  }
  
  public Collection<Kit> getKits()
  {
    return this.kits.values();
  }
  
  Future<Map<String, KitInfo>> getKitsFinderFuture(Context paramContext)
  {
    paramContext = new FabricKitsFinder(paramContext.getPackageCodePath());
    return getExecutorService().submit(paramContext);
  }
  
  public Handler getMainHandler()
  {
    return this.mainHandler;
  }
  
  public String getVersion()
  {
    return "1.4.8.32";
  }
  
  void initializeKits(Context paramContext)
  {
    Object localObject1 = getKitsFinderFuture(paramContext);
    Object localObject2 = getKits();
    localObject1 = new Onboarding((Future)localObject1, (Collection)localObject2);
    localObject2 = new ArrayList((Collection)localObject2);
    Collections.sort((List)localObject2);
    ((Onboarding)localObject1).injectParameters(paramContext, this, InitializationCallback.EMPTY, this.idManager);
    Object localObject3 = ((List)localObject2).iterator();
    while (((Iterator)localObject3).hasNext()) {
      ((Kit)((Iterator)localObject3).next()).injectParameters(paramContext, this, this.kitInitializationCallback, this.idManager);
    }
    ((Onboarding)localObject1).initialize();
    if (getLogger().isLoggable("Fabric", 3))
    {
      paramContext = new StringBuilder("Initializing ");
      paramContext.append(getIdentifier());
      paramContext.append(" [Version: ");
      paramContext.append(getVersion());
      paramContext.append("], with the following kits:\n");
    }
    else
    {
      paramContext = null;
    }
    localObject2 = ((List)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (Kit)((Iterator)localObject2).next();
      ((Kit)localObject3).initializationTask.addDependency(((Onboarding)localObject1).initializationTask);
      addAnnotatedDependencies(this.kits, (Kit)localObject3);
      ((Kit)localObject3).initialize();
      if (paramContext != null)
      {
        paramContext.append(((Kit)localObject3).getIdentifier());
        paramContext.append(" [Version: ");
        paramContext.append(((Kit)localObject3).getVersion());
        paramContext.append("]\n");
      }
    }
    if (paramContext != null) {
      getLogger().d("Fabric", paramContext.toString());
    }
  }
  
  public Fabric setCurrentActivity(Activity paramActivity)
  {
    this.activity = new WeakReference(paramActivity);
    return this;
  }
  
  public static class Builder
  {
    private String appIdentifier;
    private String appInstallIdentifier;
    private final Context context;
    private boolean debuggable;
    private Handler handler;
    private InitializationCallback<Fabric> initializationCallback;
    private Kit[] kits;
    private Logger logger;
    private PriorityThreadPoolExecutor threadPoolExecutor;
    
    public Builder(Context paramContext)
    {
      if (paramContext != null)
      {
        this.context = paramContext;
        return;
      }
      throw new IllegalArgumentException("Context must not be null.");
    }
    
    public Builder appIdentifier(String paramString)
    {
      if (paramString != null)
      {
        if (this.appIdentifier == null)
        {
          this.appIdentifier = paramString;
          return this;
        }
        throw new IllegalStateException("appIdentifier already set.");
      }
      throw new IllegalArgumentException("appIdentifier must not be null.");
    }
    
    public Builder appInstallIdentifier(String paramString)
    {
      if (paramString != null)
      {
        if (this.appInstallIdentifier == null)
        {
          this.appInstallIdentifier = paramString;
          return this;
        }
        throw new IllegalStateException("appInstallIdentifier already set.");
      }
      throw new IllegalArgumentException("appInstallIdentifier must not be null.");
    }
    
    public Fabric build()
    {
      if (this.threadPoolExecutor == null) {
        this.threadPoolExecutor = PriorityThreadPoolExecutor.create();
      }
      if (this.handler == null) {
        this.handler = new Handler(Looper.getMainLooper());
      }
      if (this.logger == null) {
        if (this.debuggable) {
          this.logger = new DefaultLogger(3);
        } else {
          this.logger = new DefaultLogger();
        }
      }
      if (this.appIdentifier == null) {
        this.appIdentifier = this.context.getPackageName();
      }
      if (this.initializationCallback == null) {
        this.initializationCallback = InitializationCallback.EMPTY;
      }
      Object localObject = this.kits;
      if (localObject == null) {
        localObject = new HashMap();
      } else {
        localObject = Fabric.getKitMap(Arrays.asList((Object[])localObject));
      }
      Context localContext = this.context.getApplicationContext();
      IdManager localIdManager = new IdManager(localContext, this.appIdentifier, this.appInstallIdentifier, ((Map)localObject).values());
      return new Fabric(localContext, (Map)localObject, this.threadPoolExecutor, this.handler, this.logger, this.debuggable, this.initializationCallback, localIdManager, Fabric.extractActivity(this.context));
    }
    
    public Builder debuggable(boolean paramBoolean)
    {
      this.debuggable = paramBoolean;
      return this;
    }
    
    @Deprecated
    public Builder executorService(ExecutorService paramExecutorService)
    {
      return this;
    }
    
    @Deprecated
    public Builder handler(Handler paramHandler)
    {
      return this;
    }
    
    public Builder initializationCallback(InitializationCallback<Fabric> paramInitializationCallback)
    {
      if (paramInitializationCallback != null)
      {
        if (this.initializationCallback == null)
        {
          this.initializationCallback = paramInitializationCallback;
          return this;
        }
        throw new IllegalStateException("initializationCallback already set.");
      }
      throw new IllegalArgumentException("initializationCallback must not be null.");
    }
    
    public Builder kits(Kit... paramVarArgs)
    {
      if (this.kits == null)
      {
        Object localObject = paramVarArgs;
        if (!DataCollectionArbiter.getInstance(this.context).isDataCollectionEnabled())
        {
          localObject = new ArrayList();
          int i = paramVarArgs.length;
          int j = 0;
          int m;
          for (int k = j; j < i; k = m)
          {
            Kit localKit = paramVarArgs[j];
            String str = localKit.getIdentifier();
            m = -1;
            int n = str.hashCode();
            if (n != 607220212)
            {
              if ((n == 1830452504) && (str.equals("com.crashlytics.sdk.android:crashlytics"))) {
                m = 0;
              }
            }
            else if (str.equals("com.crashlytics.sdk.android:answers")) {
              m = 1;
            }
            if ((m != 0) && (m != 1))
            {
              m = k;
              if (k == 0)
              {
                Fabric.getLogger().w("Fabric", "Fabric will not initialize any kits when Firebase automatic data collection is disabled; to use Third-party kits with automatic data collection disabled, initialize these kits via non-Fabric means.");
                m = 1;
              }
            }
            else
            {
              ((List)localObject).add(localKit);
              m = k;
            }
            j++;
          }
          localObject = (Kit[])((List)localObject).toArray(new Kit[0]);
        }
        this.kits = ((Kit[])localObject);
        return this;
      }
      throw new IllegalStateException("Kits already set.");
    }
    
    public Builder logger(Logger paramLogger)
    {
      if (paramLogger != null)
      {
        if (this.logger == null)
        {
          this.logger = paramLogger;
          return this;
        }
        throw new IllegalStateException("Logger already set.");
      }
      throw new IllegalArgumentException("Logger must not be null.");
    }
    
    public Builder threadPoolExecutor(PriorityThreadPoolExecutor paramPriorityThreadPoolExecutor)
    {
      if (paramPriorityThreadPoolExecutor != null)
      {
        if (this.threadPoolExecutor == null)
        {
          this.threadPoolExecutor = paramPriorityThreadPoolExecutor;
          return this;
        }
        throw new IllegalStateException("PriorityThreadPoolExecutor already set.");
      }
      throw new IllegalArgumentException("PriorityThreadPoolExecutor must not be null.");
    }
  }
}
