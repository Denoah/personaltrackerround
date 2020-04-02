package com.askgps.personaltrackercore.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.askgps.personaltrackercore.LogKt;
import com.askgps.personaltrackercore.database.DatabaseHelper;
import com.askgps.personaltrackercore.pedometer.Pedometer;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Reflection;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;
import org.koin.core.KoinComponent.DefaultImpls;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.registry.ScopeRegistry;
import org.koin.core.scope.Scope;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\002\b\006\030\0002\0020\0012\0020\002B\r\022\006\020\003\032\0020\004?\006\002\020\005J\b\020\f\032\0020\rH\026J\b\020\016\032\0020\rH\026J\b\020\017\032\0020\rH\026J\b\020\020\032\0020\rH\026J\b\020\021\032\0020\rH\026J\b\020\022\032\0020\rH\026R\024\020\003\032\0020\004X?\004?\006\b\n\000\032\004\b\006\020\007R\020\020\b\032\004\030\0010\tX?\016?\006\002\n\000R\016\020\n\032\0020\013X?\004?\006\002\n\000?\006\023"}, d2={"Lcom/askgps/personaltrackercore/utils/PedometerUtils;", "Lcom/askgps/personaltrackercore/utils/Utils;", "Lorg/koin/core/KoinComponent;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "handlerThread", "Landroid/os/HandlerThread;", "pedometer", "Lcom/askgps/personaltrackercore/pedometer/Pedometer;", "onCreate", "", "onDestroy", "onPause", "onResume", "registerSensorListener", "unregisterSensorListener", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class PedometerUtils
  extends Utils
  implements KoinComponent
{
  private final Context context;
  private HandlerThread handlerThread;
  private final Pedometer pedometer;
  
  public PedometerUtils(Context paramContext)
  {
    this.context = paramContext;
    paramContext = (Qualifier)null;
    Function0 localFunction0 = (Function0)null;
    this.pedometer = new Pedometer((DatabaseHelper)getKoin().get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(DatabaseHelper.class), paramContext, localFunction0));
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
      Sensor localSensor = localSensorManager.getDefaultSensor(19);
      localObject = new HandlerThread("PedometerThread");
      this.handlerThread = ((HandlerThread)localObject);
      if (localObject != null) {
        ((HandlerThread)localObject).start();
      }
      SensorEventListener localSensorEventListener = (SensorEventListener)this.pedometer;
      localObject = this.handlerThread;
      if (localObject != null) {
        localObject = ((HandlerThread)localObject).getLooper();
      } else {
        localObject = null;
      }
      localSensorManager.registerListener(localSensorEventListener, localSensor, 3, 1000000, new Handler((Looper)localObject));
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
      localObject = (SensorManager)localObject;
      Sensor localSensor = ((SensorManager)localObject).getDefaultSensor(19);
      ((SensorManager)localObject).unregisterListener((SensorEventListener)this.pedometer, localSensor);
      localObject = this.handlerThread;
      if (localObject != null) {
        ((HandlerThread)localObject).quitSafely();
      }
      setListenerIsStart(false);
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type android.hardware.SensorManager");
  }
}
