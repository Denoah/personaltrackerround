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
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.SupervisorKt;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;
import org.koin.core.KoinComponent.DefaultImpls;

@Metadata(bv={1, 0, 3}, d1={"\000o\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\013\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\002\n\002\b\007\n\002\030\002\n\002\b\005\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\006\n\002\030\002\n\000\n\002\020 \n\002\020\b\n\002\b\006\n\002\020\007\n\002\b\005\n\002\030\002\n\000\n\002\020\t\n\002\b\004*\001\030\030\000 92\0020\0012\b\022\004\022\0020\0030\0022\0020\004:\0019B\r\022\006\020\005\032\0020\006?\006\002\020\007J\020\020,\032\0020\n2\006\020-\032\0020.H\004J\b\020/\032\0020\nH\026J\b\0200\032\0020\nH\026J\b\0201\032\0020\nH\026J\b\0202\032\0020\nH\026J\026\0203\032\b\022\004\022\0020\003042\006\0205\032\00206H\026J\b\0207\032\0020\nH\026J\b\0208\032\0020\nH\026R&\020\b\032\016\022\004\022\0020\003\022\004\022\0020\n0\tX?.?\006\016\n\000\032\004\b\013\020\f\"\004\b\r\020\016R\024\020\005\032\0020\006X?\004?\006\b\n\000\032\004\b\017\020\020R\032\020\021\032\0020\022X?.?\006\016\n\000\032\004\b\023\020\024\"\004\b\025\020\026R\020\020\027\032\0020\030X?\004?\006\004\n\002\020\031R\020\020\032\032\004\030\0010\033X?\016?\006\002\n\000R\016\020\034\032\0020\035X?\004?\006\002\n\000R\032\020\036\032\0020\003X?\016?\006\016\n\000\032\004\b\037\020 \"\004\b!\020\"R\016\020#\032\0020$X?\004?\006\002\n\000R \020%\032\b\022\004\022\0020'0&X?\016?\006\016\n\000\032\004\b(\020)\"\004\b*\020+?\006:"}, d2={"Lcom/askgps/personaltrackercore/utils/RemovalFromHandUtils;", "Lcom/askgps/personaltrackercore/utils/Utils;", "Lcom/askgps/personaltrackercore/utils/Periodic;", "", "Lorg/koin/core/KoinComponent;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "callback", "Lkotlin/Function1;", "", "getCallback", "()Lkotlin/jvm/functions/Function1;", "setCallback", "(Lkotlin/jvm/functions/Function1;)V", "getContext", "()Landroid/content/Context;", "defaultSensor", "Landroid/hardware/Sensor;", "getDefaultSensor", "()Landroid/hardware/Sensor;", "setDefaultSensor", "(Landroid/hardware/Sensor;)V", "eventListener", "com/askgps/personaltrackercore/utils/RemovalFromHandUtils$eventListener$1", "Lcom/askgps/personaltrackercore/utils/RemovalFromHandUtils$eventListener$1;", "handlerThread", "Landroid/os/HandlerThread;", "job", "Lkotlinx/coroutines/CompletableJob;", "result", "getResult", "()Z", "setResult", "(Z)V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "sensorTypes", "", "", "getSensorTypes", "()Ljava/util/List;", "setSensorTypes", "(Ljava/util/List;)V", "calcHeartRate", "value", "", "onCreate", "onDestroy", "onPause", "onResume", "periodicResultAsync", "Lkotlinx/coroutines/Deferred;", "period", "", "registerSensorListener", "unregisterSensorListener", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class RemovalFromHandUtils
  extends Utils
  implements Periodic<Boolean>, KoinComponent
{
  public static final Companion Companion = new Companion(null);
  public static final int SENSOR_PERIOD = 1000000;
  protected Function1<? super Boolean, Unit> callback;
  private final Context context;
  public Sensor defaultSensor;
  private final eventListener.1 eventListener;
  private HandlerThread handlerThread;
  private final CompletableJob job;
  private volatile boolean result;
  private final CoroutineScope scope;
  private List<Integer> sensorTypes;
  
  public RemovalFromHandUtils(Context paramContext)
  {
    this.context = paramContext;
    this.job = SupervisorKt.SupervisorJob$default(null, 1, null);
    this.scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault().plus((CoroutineContext)this.job));
    this.sensorTypes = CollectionsKt.listOf(new Integer[] { Integer.valueOf(8), Integer.valueOf(21) });
    this.eventListener = new SensorEventListener()
    {
      public void onAccuracyChanged(Sensor paramAnonymousSensor, int paramAnonymousInt)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousSensor, "sensor");
      }
      
      public void onSensorChanged(SensorEvent paramAnonymousSensorEvent)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousSensorEvent, "event");
        this.this$0.calcHeartRate(paramAnonymousSensorEvent.values[0]);
      }
    };
  }
  
  protected final void calcHeartRate(float paramFloat)
  {
    Sensor localSensor = this.defaultSensor;
    if (localSensor == null) {
      Intrinsics.throwUninitializedPropertyAccessException("defaultSensor");
    }
    int i = localSensor.getType();
    boolean bool1 = true;
    boolean bool2 = true;
    if (i != 8)
    {
      if (i == 21)
      {
        if (paramFloat != 0.0F) {
          bool2 = false;
        }
        this.result = bool2;
      }
    }
    else
    {
      if (paramFloat > '?') {
        return;
      }
      bool2 = this.result;
      if (paramFloat >= '?') {
        bool1 = false;
      }
      this.result = (bool2 | bool1);
    }
  }
  
  protected final Function1<Boolean, Unit> getCallback()
  {
    Function1 localFunction1 = this.callback;
    if (localFunction1 == null) {
      Intrinsics.throwUninitializedPropertyAccessException("callback");
    }
    return localFunction1;
  }
  
  public Context getContext()
  {
    return this.context;
  }
  
  public final Sensor getDefaultSensor()
  {
    Sensor localSensor = this.defaultSensor;
    if (localSensor == null) {
      Intrinsics.throwUninitializedPropertyAccessException("defaultSensor");
    }
    return localSensor;
  }
  
  public Koin getKoin()
  {
    return KoinComponent.DefaultImpls.getKoin(this);
  }
  
  public final boolean getResult()
  {
    return this.result;
  }
  
  public final List<Integer> getSensorTypes()
  {
    return this.sensorTypes;
  }
  
  public void onCreate() {}
  
  public void onDestroy() {}
  
  public void onPause() {}
  
  public void onResume() {}
  
  public Deferred<Boolean> periodicResultAsync(final long paramLong)
  {
    BuildersKt.async$default(this.scope, null, null, (Function2)new SuspendLambda(paramLong, null)
    {
      Object L$0;
      int label;
      private CoroutineScope p$;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 1(this.this$0, paramLong, paramAnonymousContinuation);
        paramAnonymousContinuation.p$ = ((CoroutineScope)paramAnonymousObject);
        return paramAnonymousContinuation;
      }
      
      public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return ((1)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
      }
      
      public final Object invokeSuspend(Object paramAnonymousObject)
      {
        Object localObject = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i != 0)
        {
          if (i == 1)
          {
            localObject = (CoroutineScope)this.L$0;
            ResultKt.throwOnFailure(paramAnonymousObject);
          }
          else
          {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
          }
        }
        else
        {
          ResultKt.throwOnFailure(paramAnonymousObject);
          paramAnonymousObject = this.p$;
          this.this$0.registerSensorListener();
          long l = paramLong;
          this.L$0 = paramAnonymousObject;
          this.label = 1;
          if (DelayKt.delay(l, this) == localObject) {
            return localObject;
          }
        }
        this.this$0.unregisterSensorListener();
        LogKt.toLog$default(Boxing.boxBoolean(this.this$0.getResult()), "removal from hand", null, null, 6, null);
        return Boxing.boxBoolean(this.this$0.getResult());
      }
    }, 3, null);
  }
  
  public void registerSensorListener()
  {
    if (getListenerIsStart())
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Utils ");
      ((StringBuilder)localObject1).append(getClass().getSimpleName());
      ((StringBuilder)localObject1).append(" already started");
      LogKt.toFile$default(((StringBuilder)localObject1).toString(), null, null, null, 7, null);
      return;
    }
    this.result = false;
    Object localObject1 = getContext().getSystemService("sensor");
    if (localObject1 != null)
    {
      SensorManager localSensorManager = (SensorManager)localObject1;
      localObject1 = (RemovalFromHandUtils)this;
      Object localObject2;
      if (((RemovalFromHandUtils)localObject1).defaultSensor == null)
      {
        localObject2 = this.sensorTypes.iterator();
        while (((Iterator)localObject2).hasNext())
        {
          int i = ((Number)((Iterator)localObject2).next()).intValue();
          if (!localSensorManager.getSensorList(i).isEmpty())
          {
            localObject2 = localSensorManager.getDefaultSensor(i);
            Intrinsics.checkExpressionValueIsNotNull(localObject2, "sensorManager.getDefaultSensor(sensorType)");
            this.defaultSensor = ((Sensor)localObject2);
          }
        }
      }
      if (((RemovalFromHandUtils)localObject1).defaultSensor != null)
      {
        localObject1 = new HandlerThread("RemovalThread");
        this.handlerThread = ((HandlerThread)localObject1);
        if (localObject1 != null) {
          ((HandlerThread)localObject1).start();
        }
        SensorEventListener localSensorEventListener = (SensorEventListener)this.eventListener;
        localObject2 = this.defaultSensor;
        if (localObject2 == null) {
          Intrinsics.throwUninitializedPropertyAccessException("defaultSensor");
        }
        localObject1 = this.handlerThread;
        if (localObject1 != null) {
          localObject1 = ((HandlerThread)localObject1).getLooper();
        } else {
          localObject1 = null;
        }
        localSensorManager.registerListener(localSensorEventListener, (Sensor)localObject2, 1000000, new Handler((Looper)localObject1));
        setListenerIsStart(true);
      }
      else
      {
        LogKt.toFile$default("Error. defaultSensor is not initialized", null, "registerSensorListener", null, 5, null);
      }
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type android.hardware.SensorManager");
  }
  
  protected final void setCallback(Function1<? super Boolean, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "<set-?>");
    this.callback = paramFunction1;
  }
  
  public final void setDefaultSensor(Sensor paramSensor)
  {
    Intrinsics.checkParameterIsNotNull(paramSensor, "<set-?>");
    this.defaultSensor = paramSensor;
  }
  
  public final void setResult(boolean paramBoolean)
  {
    this.result = paramBoolean;
  }
  
  public final void setSensorTypes(List<Integer> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "<set-?>");
    this.sensorTypes = paramList;
  }
  
  public void unregisterSensorListener()
  {
    if (((RemovalFromHandUtils)this).defaultSensor != null)
    {
      Object localObject = getContext().getSystemService("sensor");
      if (localObject != null)
      {
        SensorManager localSensorManager = (SensorManager)localObject;
        SensorEventListener localSensorEventListener = (SensorEventListener)this.eventListener;
        localObject = this.defaultSensor;
        if (localObject == null) {
          Intrinsics.throwUninitializedPropertyAccessException("defaultSensor");
        }
        localSensorManager.unregisterListener(localSensorEventListener, (Sensor)localObject);
        localObject = this.handlerThread;
        if (localObject != null) {
          ((HandlerThread)localObject).quitSafely();
        }
      }
      else
      {
        throw new TypeCastException("null cannot be cast to non-null type android.hardware.SensorManager");
      }
    }
    setListenerIsStart(false);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000?\006\005"}, d2={"Lcom/askgps/personaltrackercore/utils/RemovalFromHandUtils$Companion;", "", "()V", "SENSOR_PERIOD", "", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
  }
}
