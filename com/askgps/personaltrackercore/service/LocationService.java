package com.askgps.personaltrackercore.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import androidx.core.app.NotificationCompat.Builder;
import androidx.core.content.ContextCompat;
import com.askgps.personaltrackercore.BaseMainActivity;
import com.askgps.personaltrackercore.BaseMainActivity.Companion;
import com.askgps.personaltrackercore.LogKt;
import com.askgps.personaltrackercore.R.drawable;
import com.askgps.personaltrackercore.location.GoogleApiHelper;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.Koin;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.registry.ScopeRegistry;
import org.koin.core.scope.Scope;

@Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\005\030\000 \0262\0020\001:\001\026B\005?\006\002\020\002J\b\020\013\032\0020\fH\002J\022\020\r\032\004\030\0010\0162\006\020\017\032\0020\020H\026J\"\020\021\032\0020\0222\b\020\017\032\004\030\0010\0202\006\020\023\032\0020\0222\006\020\024\032\0020\022H\026J\b\020\025\032\0020\fH\004R\016\020\003\032\0020\004X?D?\006\002\n\000R\033\020\005\032\0020\0068BX??\002?\006\f\n\004\b\t\020\n\032\004\b\007\020\b?\006\027"}, d2={"Lcom/askgps/personaltrackercore/service/LocationService;", "Landroid/app/Service;", "()V", "CHANNEL_ID", "", "googleApiHelper", "Lcom/askgps/personaltrackercore/location/GoogleApiHelper;", "getGoogleApiHelper", "()Lcom/askgps/personaltrackercore/location/GoogleApiHelper;", "googleApiHelper$delegate", "Lkotlin/Lazy;", "createNotificationChannel", "", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onStartCommand", "", "flags", "startId", "startUpdateLocationJob", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class LocationService
  extends Service
{
  public static final Companion Companion = new Companion(null);
  private static AlarmManager alarmMgr;
  private static GoogleApiHelper googleApiHelper;
  private static PendingIntent updateLocationIntent;
  private final String CHANNEL_ID = "LocationUpdateService";
  private final Lazy googleApiHelper$delegate;
  
  public LocationService()
  {
    final Qualifier localQualifier = (Qualifier)null;
    final Function0 localFunction0 = (Function0)null;
    this.googleApiHelper$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0)new Lambda(localQualifier)
    {
      public final GoogleApiHelper invoke()
      {
        ComponentCallbacks localComponentCallbacks = this.$this_inject;
        Qualifier localQualifier = localQualifier;
        Function0 localFunction0 = localFunction0;
        return ComponentCallbackExtKt.getKoin(localComponentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(GoogleApiHelper.class), localQualifier, localFunction0);
      }
    });
  }
  
  private final void createNotificationChannel()
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      NotificationChannel localNotificationChannel = new NotificationChannel(this.CHANNEL_ID, (CharSequence)"Location update service", 3);
      NotificationManager localNotificationManager = (NotificationManager)getSystemService(NotificationManager.class);
      if (localNotificationManager == null) {
        Intrinsics.throwNpe();
      }
      localNotificationManager.createNotificationChannel(localNotificationChannel);
    }
  }
  
  private final GoogleApiHelper getGoogleApiHelper()
  {
    Lazy localLazy = this.googleApiHelper$delegate;
    KProperty localKProperty = $$delegatedProperties[0];
    return (GoogleApiHelper)localLazy.getValue();
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    Intrinsics.checkParameterIsNotNull(paramIntent, "intent");
    return null;
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    Long localLong1 = null;
    Long localLong2;
    if (paramIntent != null) {
      localLong2 = Long.valueOf(paramIntent.getLongExtra("sendTelemetryInterval", 0L));
    } else {
      localLong2 = null;
    }
    if (paramIntent != null) {
      localLong1 = Long.valueOf(paramIntent.getLongExtra("locationInterval", 0L));
    }
    createNotificationChannel();
    Context localContext = (Context)this;
    paramIntent = PendingIntent.getService(localContext, 0, new Intent(localContext, LocationUpdateService.class), 0);
    startForeground(101, new NotificationCompat.Builder(localContext, this.CHANNEL_ID).setContentTitle((CharSequence)"Обновление координат").setContentText((CharSequence)"СоцМониторинг").setSmallIcon(R.drawable.notification_icon).setContentIntent(paramIntent).build());
    if ((localLong1 != null) && (localLong2 != null)) {
      getGoogleApiHelper().requestLocationUpdates(localLong1.longValue(), localLong2.longValue());
    }
    startUpdateLocationJob();
    return 1;
  }
  
  protected final void startUpdateLocationJob()
  {
    LogKt.toFile$default("Run LocationUpdateService alarm", null, null, null, 7, null);
    Object localObject = getSystemService("alarm");
    if (localObject != null)
    {
      alarmMgr = (AlarmManager)localObject;
      localObject = (Context)this;
      localObject = PendingIntent.getService((Context)localObject, 0, new Intent((Context)localObject, LocationUpdateService.class), 0);
      Intrinsics.checkExpressionValueIsNotNull(localObject, "PendingIntent.getService(this, 0, intent, 0)");
      Intrinsics.checkExpressionValueIsNotNull(localObject, "Intent(this, LocationUpd…, 0, intent, 0)\n        }");
      updateLocationIntent = (PendingIntent)localObject;
      localObject = alarmMgr;
      if (localObject == null) {
        Intrinsics.throwUninitializedPropertyAccessException("alarmMgr");
      }
      long l1 = System.currentTimeMillis();
      long l2 = BaseMainActivity.Companion.getTELEMETRY_SEND_INTERVAL();
      PendingIntent localPendingIntent = updateLocationIntent;
      if (localPendingIntent == null) {
        Intrinsics.throwUninitializedPropertyAccessException("updateLocationIntent");
      }
      ((AlarmManager)localObject).setRepeating(0, l1, l2, localPendingIntent);
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type android.app.AlarmManager");
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\004\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\036\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\0162\006\020\017\032\0020\016J\016\020\020\032\0020\n2\006\020\013\032\0020\fJ\006\020\021\032\0020\nR\016\020\003\032\0020\004X?.?\006\002\n\000R\016\020\005\032\0020\006X?.?\006\002\n\000R\016\020\007\032\0020\bX?.?\006\002\n\000?\006\022"}, d2={"Lcom/askgps/personaltrackercore/service/LocationService$Companion;", "", "()V", "alarmMgr", "Landroid/app/AlarmManager;", "googleApiHelper", "Lcom/askgps/personaltrackercore/location/GoogleApiHelper;", "updateLocationIntent", "Landroid/app/PendingIntent;", "startService", "", "context", "Landroid/content/Context;", "locationInterval", "", "sendTelemetryInterval", "stopService", "stopUpdateLocationJob", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final void startService(Context paramContext, long paramLong1, long paramLong2)
    {
      Intrinsics.checkParameterIsNotNull(paramContext, "context");
      Intent localIntent = new Intent(paramContext, LocationService.class);
      localIntent.putExtra("locationInterval", paramLong1);
      localIntent.putExtra("sendTelemetryInterval", paramLong2);
      ContextCompat.startForegroundService(paramContext, localIntent);
    }
    
    public final void stopService(Context paramContext)
    {
      Intrinsics.checkParameterIsNotNull(paramContext, "context");
      Object localObject = (Companion)this;
      ((Companion)localObject).stopUpdateLocationJob();
      if (access$getGoogleApiHelper$li((Companion)localObject) != null)
      {
        localObject = LocationService.access$getGoogleApiHelper$cp();
        if (localObject == null) {
          Intrinsics.throwUninitializedPropertyAccessException("googleApiHelper");
        }
        ((GoogleApiHelper)localObject).removeLocationUpdates();
      }
      paramContext.stopService(new Intent(paramContext, LocationService.class));
    }
    
    public final void stopUpdateLocationJob()
    {
      if (access$getAlarmMgr$li((Companion)this) != null)
      {
        AlarmManager localAlarmManager = LocationService.access$getAlarmMgr$cp();
        if (localAlarmManager == null) {
          Intrinsics.throwUninitializedPropertyAccessException("alarmMgr");
        }
        PendingIntent localPendingIntent = LocationService.access$getUpdateLocationIntent$cp();
        if (localPendingIntent == null) {
          Intrinsics.throwUninitializedPropertyAccessException("updateLocationIntent");
        }
        localAlarmManager.cancel(localPendingIntent);
      }
    }
  }
}
