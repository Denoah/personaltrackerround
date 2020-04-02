package com.askgps.personaltrackercore.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.askgps.personaltrackercore.LogKt;
import com.askgps.personaltrackercore.repository.Repository;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
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

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\030\0002\0020\0012\0020\002B\005?\006\002\020\003J\030\020\n\032\0020\0132\006\020\f\032\0020\r2\006\020\016\032\0020\017H\026R\033\020\004\032\0020\0058BX??\002?\006\f\n\004\b\b\020\t\032\004\b\006\020\007?\006\020"}, d2={"Lcom/askgps/personaltrackercore/receiver/PowerManagerReceiver;", "Landroid/content/BroadcastReceiver;", "Lorg/koin/core/KoinComponent;", "()V", "repo", "Lcom/askgps/personaltrackercore/repository/Repository;", "getRepo", "()Lcom/askgps/personaltrackercore/repository/Repository;", "repo$delegate", "Lkotlin/Lazy;", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class PowerManagerReceiver
  extends BroadcastReceiver
  implements KoinComponent
{
  private final Lazy repo$delegate;
  
  public PowerManagerReceiver()
  {
    final Qualifier localQualifier = (Qualifier)null;
    final Function0 localFunction0 = (Function0)null;
    this.repo$delegate = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0)new Lambda(localQualifier)
    {
      public final Repository invoke()
      {
        Koin localKoin = this.$this_inject.getKoin();
        Qualifier localQualifier = localQualifier;
        Function0 localFunction0 = localFunction0;
        return localKoin.get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(Repository.class), localQualifier, localFunction0);
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
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    Intrinsics.checkParameterIsNotNull(paramIntent, "intent");
    LogKt.toLog$default(paramIntent.getAction(), "PowerManagerReceiver intent", null, null, 6, null);
    paramContext = paramIntent.getAction();
    if (paramContext != null)
    {
      int i = paramContext.hashCode();
      if (i != 798292259)
      {
        if ((i == 1947666138) && (paramContext.equals("android.intent.action.ACTION_SHUTDOWN"))) {
          getRepo().processingPowerManagerMessage(false);
        }
      }
      else if (paramContext.equals("android.intent.action.BOOT_COMPLETED")) {
        getRepo().processingPowerManagerMessage(true);
      }
    }
  }
}
