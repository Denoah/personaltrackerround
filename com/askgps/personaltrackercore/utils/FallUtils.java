package com.askgps.personaltrackercore.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.askgps.personaltrackercore.LogKt;
import com.askgps.personaltrackercore.repository.Repository;
import java.util.LinkedList;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;
import org.koin.core.KoinComponent.DefaultImpls;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.registry.ScopeRegistry;
import org.koin.core.scope.Scope;

@Metadata(bv={1, 0, 3}, d1={"\000M\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\002\n\002\020\013\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\020\007\n\000\n\002\030\002\n\002\b\005\n\002\b\005\n\002\020\002\n\002\b\f*\001\031\030\000 )2\0020\0012\0020\002:\001)B\r\022\006\020\003\032\0020\004?\006\002\020\005J\020\020\035\032\0020\0362\006\020\037\032\0020\021H\002J\b\020 \032\0020\036H\002J\020\020!\032\0020\0362\006\020\"\032\0020\021H\002J\b\020#\032\0020\036H\026J\b\020$\032\0020\036H\026J\b\020%\032\0020\036H\026J\b\020&\032\0020\036H\026J\b\020'\032\0020\036H\026J\b\020(\032\0020\036H\026R\016\020\006\032\0020\007X?\016?\006\002\n\000R\016\020\b\032\0020\007X?\016?\006\002\n\000R\016\020\t\032\0020\nX?\016?\006\002\n\000R\024\020\003\032\0020\004X?\004?\006\b\n\000\032\004\b\013\020\fR\020\020\r\032\004\030\0010\016X?\016?\006\002\n\000R\024\020\017\032\b\022\004\022\0020\0210\020X?\004?\006\002\n\000R\033\020\022\032\0020\0238BX??\002?\006\f\n\004\b\026\020\027\032\004\b\024\020\025R\020\020\030\032\0020\031X?\004?\006\004\n\002\020\032R\022\020\033\032\004\030\0010\007X?\016?\006\004\n\002\020\034?\006*"}, d2={"Lcom/askgps/personaltrackercore/utils/FallUtils;", "Lcom/askgps/personaltrackercore/utils/Utils;", "Lorg/koin/core/KoinComponent;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "checkFallBeginTime", "", "checkFallEndTime", "checkFallIsStart", "", "getContext", "()Landroid/content/Context;", "handlerThread", "Landroid/os/HandlerThread;", "lastValueList", "Ljava/util/LinkedList;", "", "repo", "Lcom/askgps/personaltrackercore/repository/Repository;", "getRepo", "()Lcom/askgps/personaltrackercore/repository/Repository;", "repo$delegate", "Lkotlin/Lazy;", "sensorEventListener", "com/askgps/personaltrackercore/utils/FallUtils$sensorEventListener$1", "Lcom/askgps/personaltrackercore/utils/FallUtils$sensorEventListener$1;", "stayStartTime", "Ljava/lang/Long;", "addToLastItemList", "", "item", "initFields", "moveDetect", "newValue", "onCreate", "onDestroy", "onPause", "onResume", "registerSensorListener", "unregisterSensorListener", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class FallUtils
  extends Utils
  implements KoinComponent
{
  public static final Companion Companion = new Companion(null);
  private static final int LAST_ITEM_LIST_SIZE = 5;
  public static final int MIN_FALL_TIME = 400000000;
  private static final float MOVE_EPS = 0.5F;
  public static final int SENSOR_DELAY = 200000;
  private static final int STAY_THRESHOLD = 60000;
  public static final float THRESHOLD = 4.0F;
  private long checkFallBeginTime;
  private long checkFallEndTime;
  private boolean checkFallIsStart;
  private final Context context;
  private HandlerThread handlerThread;
  private final LinkedList<Float> lastValueList;
  private final Lazy repo$delegate;
  private final sensorEventListener.1 sensorEventListener;
  private Long stayStartTime;
  
  public FallUtils(final Context paramContext)
  {
    this.context = paramContext;
    paramContext = (Qualifier)null;
    final Function0 localFunction0 = (Function0)null;
    this.repo$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0)new Lambda(paramContext)
    {
      public final Repository invoke()
      {
        Koin localKoin = this.$this_inject.getKoin();
        Qualifier localQualifier = paramContext;
        Function0 localFunction0 = localFunction0;
        return localKoin.get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(Repository.class), localQualifier, localFunction0);
      }
    });
    this.lastValueList = new LinkedList();
    this.sensorEventListener = new SensorEventListener()
    {
      public void onAccuracyChanged(Sensor paramAnonymousSensor, int paramAnonymousInt)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousSensor, "sensor");
      }
      
      public void onSensorChanged(SensorEvent paramAnonymousSensorEvent)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousSensorEvent, "event");
        float f = (float)Math.sqrt(paramAnonymousSensorEvent.values[0] * paramAnonymousSensorEvent.values[0] + paramAnonymousSensorEvent.values[1] * paramAnonymousSensorEvent.values[1] + paramAnonymousSensorEvent.values[2] * paramAnonymousSensorEvent.values[2]);
        FallUtils.access$moveDetect(this.this$0, f);
        FallUtils.access$addToLastItemList(this.this$0, f);
        if ((f <= 4.0F) && (!FallUtils.access$getCheckFallIsStart$p(this.this$0)))
        {
          FallUtils.access$setCheckFallBeginTime$p(this.this$0, paramAnonymousSensorEvent.timestamp);
          FallUtils.access$setCheckFallIsStart$p(this.this$0, true);
          LogKt.toLog$default(Long.valueOf(FallUtils.access$getCheckFallBeginTime$p(this.this$0)), "start fall time", null, null, 6, null);
        }
        else if ((f > 4.0F) && (FallUtils.access$getCheckFallIsStart$p(this.this$0)))
        {
          FallUtils.access$setCheckFallEndTime$p(this.this$0, paramAnonymousSensorEvent.timestamp);
          LogKt.toLog$default(Long.valueOf(FallUtils.access$getCheckFallEndTime$p(this.this$0)), "end fall time", null, null, 6, null);
          FallUtils.access$setCheckFallIsStart$p(this.this$0, false);
          long l = FallUtils.access$getCheckFallEndTime$p(this.this$0) - FallUtils.access$getCheckFallBeginTime$p(this.this$0);
          LogKt.toLog$default(Long.valueOf(l), "fallTime", null, null, 6, null);
          if (l >= 400000000) {
            FallUtils.access$getRepo$p(this.this$0).processingFallMessage();
          }
          FallUtils.access$setCheckFallBeginTime$p(this.this$0, 0L);
          FallUtils.access$setCheckFallEndTime$p(this.this$0, 0L);
        }
      }
    };
  }
  
  private final void addToLastItemList(float paramFloat)
  {
    if (this.lastValueList.size() == 5) {
      this.lastValueList.remove();
    }
    this.lastValueList.add(Float.valueOf(paramFloat));
  }
  
  private final Repository getRepo()
  {
    Lazy localLazy = this.repo$delegate;
    KProperty localKProperty = $$delegatedProperties[0];
    return (Repository)localLazy.getValue();
  }
  
  private final void initFields() {}
  
  private final void moveDetect(float paramFloat)
  {
    if (Math.abs(paramFloat - CollectionsKt.averageOfFloat((Iterable)this.lastValueList)) >= 0.5F)
    {
      this.stayStartTime = ((Long)null);
      if (!getRepo().isMove()) {
        getRepo().setMove(true);
      }
    }
    else
    {
      if (!getRepo().isMove()) {
        return;
      }
      if (this.stayStartTime == null)
      {
        this.stayStartTime = Long.valueOf(System.currentTimeMillis());
        return;
      }
      long l = System.currentTimeMillis();
      Long localLong = this.stayStartTime;
      if (localLong == null) {
        Intrinsics.throwNpe();
      }
      if (l - localLong.longValue() >= 60000) {
        getRepo().setMove(false);
      }
    }
  }
  
  public Context getContext()
  {
    return this.context;
  }
  
  public Koin getKoin()
  {
    return KoinComponent.DefaultImpls.getKoin(this);
  }
  
  public void onCreate() {}
  
  public void onDestroy() {}
  
  public void onPause() {}
  
  public void onResume() {}
  
  public void registerSensorListener()
  {
    if (getListenerIsStart())
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Utils ");
      ((StringBuilder)localObject).append(getClass().getSimpleName());
      ((StringBuilder)localObject).append(" already started");
      LogKt.toFile$default(((StringBuilder)localObject).toString(), null, null, null, 7, null);
      return;
    }
    Object localObject = getContext().getSystemService("sensor");
    if (localObject != null)
    {
      SensorManager localSensorManager = (SensorManager)localObject;
      Sensor localSensor = localSensorManager.getDefaultSensor(1);
      localObject = new HandlerThread("FallThread");
      this.handlerThread = ((HandlerThread)localObject);
      if (localObject != null) {
        ((HandlerThread)localObject).start();
      }
      initFields();
      SensorEventListener localSensorEventListener = (SensorEventListener)this.sensorEventListener;
      localObject = this.handlerThread;
      if (localObject != null) {
        localObject = ((HandlerThread)localObject).getLooper();
      } else {
        localObject = null;
      }
      localSensorManager.registerListener(localSensorEventListener, localSensor, 200000, 1000000, new Handler((Looper)localObject));
      setListenerIsStart(true);
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type android.hardware.SensorManager");
  }
  
  public void unregisterSensorListener()
  {
    Object localObject = getContext().getSystemService("sensor");
    if (localObject != null)
    {
      SensorManager localSensorManager = (SensorManager)localObject;
      localObject = localSensorManager.getDefaultSensor(1);
      localSensorManager.unregisterListener((SensorEventListener)this.sensorEventListener, (Sensor)localObject);
      localObject = this.handlerThread;
      if (localObject != null) {
        ((HandlerThread)localObject).quitSafely();
      }
      setListenerIsStart(false);
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type android.hardware.SensorManager");
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\007\n\002\b\004\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\004X?T?\006\002\n\000R\016\020\006\032\0020\007X?T?\006\002\n\000R\016\020\b\032\0020\004X?T?\006\002\n\000R\016\020\t\032\0020\004X?T?\006\002\n\000R\016\020\n\032\0020\007X?T?\006\002\n\000?\006\013"}, d2={"Lcom/askgps/personaltrackercore/utils/FallUtils$Companion;", "", "()V", "LAST_ITEM_LIST_SIZE", "", "MIN_FALL_TIME", "MOVE_EPS", "", "SENSOR_DELAY", "STAY_THRESHOLD", "THRESHOLD", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
  }
}
