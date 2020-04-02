package com.askgps.personaltrackercore.service;

import android.app.Service;
import android.content.ComponentCallbacks;
import android.content.Intent;
import android.os.IBinder;
import com.askgps.personaltrackercore.repository.Repository;
import com.askgps.personaltrackercore.utils.WifiUtils;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;
import org.koin.core.KoinComponent.DefaultImpls;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.registry.ScopeRegistry;
import org.koin.core.scope.Scope;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\004\030\000 \0222\0020\0012\0020\002:\001\022B\005?\006\002\020\003J\024\020\n\032\004\030\0010\0132\b\020\f\032\004\030\0010\rH\026J\"\020\016\032\0020\0172\b\020\f\032\004\030\0010\r2\006\020\020\032\0020\0172\006\020\021\032\0020\017H\026R\033\020\004\032\0020\0058BX??\002?\006\f\n\004\b\b\020\t\032\004\b\006\020\007?\006\023"}, d2={"Lcom/askgps/personaltrackercore/service/LocationUpdateService;", "Landroid/app/Service;", "Lorg/koin/core/KoinComponent;", "()V", "repo", "Lcom/askgps/personaltrackercore/repository/Repository;", "getRepo", "()Lcom/askgps/personaltrackercore/repository/Repository;", "repo$delegate", "Lkotlin/Lazy;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onStartCommand", "", "flags", "startId", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class LocationUpdateService
  extends Service
  implements KoinComponent
{
  public static final Companion Companion = new Companion(null);
  public static final String TAG = "last_loc";
  private final Lazy repo$delegate;
  
  public LocationUpdateService()
  {
    final Qualifier localQualifier = (Qualifier)null;
    final Function0 localFunction0 = (Function0)null;
    this.repo$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0)new Lambda(localQualifier)
    {
      public final Repository invoke()
      {
        ComponentCallbacks localComponentCallbacks = this.$this_inject;
        Qualifier localQualifier = localQualifier;
        Function0 localFunction0 = localFunction0;
        return ComponentCallbackExtKt.getKoin(localComponentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(Repository.class), localQualifier, localFunction0);
      }
    });
  }
  
  private final Repository getRepo()
  {
    Lazy localLazy = this.repo$delegate;
    KProperty localKProperty = $$delegatedProperties[0];
    return (Repository)localLazy.getValue();
  }
  
  public Koin getKoin()
  {
    return KoinComponent.DefaultImpls.getKoin(this);
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    paramIntent = getApplicationContext();
    Intrinsics.checkExpressionValueIsNotNull(paramIntent, "applicationContext");
    new WifiUtils(paramIntent).wifiScan();
    return 1;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000?\006\005"}, d2={"Lcom/askgps/personaltrackercore/service/LocationUpdateService$Companion;", "", "()V", "TAG", "", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
  }
}
