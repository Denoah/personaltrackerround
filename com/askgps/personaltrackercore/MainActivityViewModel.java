package com.askgps.personaltrackercore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.askgps.personaltrackercore.database.model.Settings;
import com.askgps.personaltrackercore.repository.Repository;
import io.reactivex.subjects.PublishSubject;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;
import org.koin.core.KoinComponent.DefaultImpls;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.registry.ScopeRegistry;
import org.koin.core.scope.Scope;

@Metadata(bv={1, 0, 3}, d1={"\000X\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\013\n\002\b\b\n\002\020\002\n\002\b\003\n\002\020\t\n\002\b\004\030\0002\0020\0012\0020\002B\005?\006\002\020\003J\006\020$\032\0020%J3\020&\032\0020%2\b\020'\032\004\030\0010\0162\b\020(\032\004\030\0010)2\b\020*\032\004\030\0010)2\b\020+\032\004\030\0010)?\006\002\020,R\021\020\004\032\0020\0058F?\006\006\032\004\b\006\020\007R\027\020\b\032\b\022\004\022\0020\0050\t?\006\b\n\000\032\004\b\n\020\013R\027\020\f\032\b\022\004\022\0020\0160\r?\006\b\n\000\032\004\b\017\020\020R\033\020\021\032\0020\0228BX??\002?\006\f\n\004\b\025\020\026\032\004\b\023\020\024R\027\020\027\032\b\022\004\022\0020\0300\t?\006\b\n\000\032\004\b\031\020\013R\037\020\032\032\020\022\f\022\n \035*\004\030\0010\0340\0340\033?\006\b\n\000\032\004\b\036\020\037R\027\020 \032\b\022\004\022\0020\0340\r?\006\b\n\000\032\004\b!\020\020R\027\020\"\032\b\022\004\022\0020\0340\t?\006\b\n\000\032\004\b#\020\013?\006-"}, d2={"Lcom/askgps/personaltrackercore/MainActivityViewModel;", "Landroidx/lifecycle/ViewModel;", "Lorg/koin/core/KoinComponent;", "()V", "attemptCountLeft", "", "getAttemptCountLeft", "()I", "authCode", "Landroidx/lifecycle/LiveData;", "getAuthCode", "()Landroidx/lifecycle/LiveData;", "imei", "Landroidx/lifecycle/MutableLiveData;", "", "getImei", "()Landroidx/lifecycle/MutableLiveData;", "repo", "Lcom/askgps/personaltrackercore/repository/Repository;", "getRepo", "()Lcom/askgps/personaltrackercore/repository/Repository;", "repo$delegate", "Lkotlin/Lazy;", "settings", "Lcom/askgps/personaltrackercore/database/model/Settings;", "getSettings", "showAlarm", "Lio/reactivex/subjects/PublishSubject;", "", "kotlin.jvm.PlatformType", "getShowAlarm", "()Lio/reactivex/subjects/PublishSubject;", "showNetworkError", "getShowNetworkError", "successfulIdentification", "getSuccessfulIdentification", "resetAttemptCount", "", "updateSettings", "address", "locationInterval", "", "sendTelemetryInterval", "removalFromHandInterval", "(Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;Ljava/lang/Long;)V", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class MainActivityViewModel
  extends ViewModel
  implements KoinComponent
{
  private final LiveData<Integer> authCode;
  private final MutableLiveData<String> imei;
  private final Lazy repo$delegate;
  private final LiveData<Settings> settings;
  private final PublishSubject<Boolean> showAlarm;
  private final MutableLiveData<Boolean> showNetworkError;
  private final LiveData<Boolean> successfulIdentification;
  
  public MainActivityViewModel()
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
    this.imei = getRepo().getImei();
    this.settings = getRepo().getSettings();
    this.successfulIdentification = ((LiveData)getRepo().getSuccessfulIdentification());
    this.authCode = ((LiveData)getRepo().getAuthCode());
    this.showAlarm = getRepo().getAlarm();
    this.showNetworkError = getRepo().getShowNetworkError();
  }
  
  private final Repository getRepo()
  {
    Lazy localLazy = this.repo$delegate;
    KProperty localKProperty = $$delegatedProperties[0];
    return (Repository)localLazy.getValue();
  }
  
  public final int getAttemptCountLeft()
  {
    return getRepo().getAttemptCountLeft();
  }
  
  public final LiveData<Integer> getAuthCode()
  {
    return this.authCode;
  }
  
  public final MutableLiveData<String> getImei()
  {
    return this.imei;
  }
  
  public Koin getKoin()
  {
    return KoinComponent.DefaultImpls.getKoin(this);
  }
  
  public final LiveData<Settings> getSettings()
  {
    return this.settings;
  }
  
  public final PublishSubject<Boolean> getShowAlarm()
  {
    return this.showAlarm;
  }
  
  public final MutableLiveData<Boolean> getShowNetworkError()
  {
    return this.showNetworkError;
  }
  
  public final LiveData<Boolean> getSuccessfulIdentification()
  {
    return this.successfulIdentification;
  }
  
  public final void resetAttemptCount()
  {
    getRepo().resetAttemptLeft();
  }
  
  public final void updateSettings(final String paramString, final Long paramLong1, final Long paramLong2, final Long paramLong3)
  {
    BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), (CoroutineContext)Dispatchers.getIO(), null, (Function2)new SuspendLambda(paramString, paramLong1)
    {
      int label;
      private CoroutineScope p$;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 1(this.this$0, paramString, paramLong1, paramLong2, paramLong3, paramAnonymousContinuation);
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
          MainActivityViewModel.access$getRepo$p(this.this$0).updateSettings(paramString, paramLong1, paramLong2, paramLong3);
          return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }, 2, null);
  }
}
