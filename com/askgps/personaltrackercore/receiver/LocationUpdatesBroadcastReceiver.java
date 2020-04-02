package com.askgps.personaltrackercore.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.askgps.personaltrackercore.LogKt;
import com.askgps.personaltrackercore.utils.WifiUtils;
import com.google.android.gms.location.LocationResult;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;
import org.koin.core.KoinComponent.DefaultImpls;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\030\000 \0162\0020\0012\0020\002:\001\016B\005?\006\002\020\003J\032\020\004\032\0020\0052\006\020\006\032\0020\0072\b\020\b\032\004\030\0010\tH\026J\034\020\n\032\0020\0132\b\020\f\032\004\030\0010\0072\b\020\r\032\004\030\0010\tH\026?\006\017"}, d2={"Lcom/askgps/personaltrackercore/receiver/LocationUpdatesBroadcastReceiver;", "Landroid/content/BroadcastReceiver;", "Lorg/koin/core/KoinComponent;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "peekService", "Landroid/os/IBinder;", "myContext", "service", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class LocationUpdatesBroadcastReceiver
  extends BroadcastReceiver
  implements KoinComponent
{
  public static final String ACTION_PROCESS_UPDATES = "com.askgps.personaltrackercore.action.PROCESS_UPDATES";
  public static final Companion Companion = new Companion(null);
  public static final String TAG = "update_loc";
  
  public LocationUpdatesBroadcastReceiver() {}
  
  public Koin getKoin()
  {
    return KoinComponent.DefaultImpls.getKoin(this);
  }
  
  public void onReceive(final Context paramContext, Intent paramIntent)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "context");
    paramContext = new WifiUtils(paramContext);
    if ((paramIntent != null) && (Intrinsics.areEqual("com.askgps.personaltrackercore.action.PROCESS_UPDATES", paramIntent.getAction()))) {
      BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope((CoroutineContext)Dispatchers.getIO()), (CoroutineContext)Dispatchers.getDefault(), null, (Function2)new SuspendLambda(paramIntent, paramContext)
      {
        int label;
        private CoroutineScope p$;
        
        public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
          paramAnonymousContinuation = new 1(this.$intent, paramContext, paramAnonymousContinuation);
          paramAnonymousContinuation.p$ = ((CoroutineScope)paramAnonymousObject);
          return paramAnonymousContinuation;
        }
        
        public final Object invoke(Object paramAnonymousObject1, Object paramAnonymousObject2)
        {
          return ((1)create(paramAnonymousObject1, (Continuation)paramAnonymousObject2)).invokeSuspend(Unit.INSTANCE);
        }
        
        public final Object invokeSuspend(Object paramAnonymousObject)
        {
          IntrinsicsKt.getCOROUTINE_SUSPENDED();
          if (this.label == 0)
          {
            ResultKt.throwOnFailure(paramAnonymousObject);
            paramAnonymousObject = LocationResult.extractResult(this.$intent);
            if (paramAnonymousObject != null) {
              paramContext.wifiScan(paramAnonymousObject, true);
            }
            return Unit.INSTANCE;
          }
          throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
      }, 2, null);
    }
  }
  
  public IBinder peekService(Context paramContext, Intent paramIntent)
  {
    LogKt.toLog$default(paramIntent, "peekService", null, null, 6, null);
    paramContext = super.peekService(paramContext, paramIntent);
    Intrinsics.checkExpressionValueIsNotNull(paramContext, "super.peekService(myContext, service)");
    return paramContext;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\002\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\004X?T?\006\002\n\000?\006\006"}, d2={"Lcom/askgps/personaltrackercore/receiver/LocationUpdatesBroadcastReceiver$Companion;", "", "()V", "ACTION_PROCESS_UPDATES", "", "TAG", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
  }
}
