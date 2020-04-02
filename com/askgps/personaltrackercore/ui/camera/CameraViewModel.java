package com.askgps.personaltrackercore.ui.camera;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.askgps.personaltrackercore.repository.Repository;
import com.hadilq.liveevent.LiveEvent;
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

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\005\n\002\020\013\n\000\030\0002\0020\0012\0020\002B\005?\006\002\020\003J\016\020\017\032\0020\0202\006\020\021\032\0020\006J\020\020\022\032\004\030\0010\0062\006\020\021\032\0020\006J\016\020\023\032\0020\0202\006\020\024\032\0020\006J\016\020\025\032\0020\0262\006\020\021\032\0020\006R\027\020\004\032\b\022\004\022\0020\0060\005?\006\b\n\000\032\004\b\007\020\bR\033\020\t\032\0020\n8BX??\002?\006\f\n\004\b\r\020\016\032\004\b\013\020\f?\006\027"}, d2={"Lcom/askgps/personaltrackercore/ui/camera/CameraViewModel;", "Landroidx/lifecycle/ViewModel;", "Lorg/koin/core/KoinComponent;", "()V", "idxId", "Lcom/hadilq/liveevent/LiveEvent;", "", "getIdxId", "()Lcom/hadilq/liveevent/LiveEvent;", "repo", "Lcom/askgps/personaltrackercore/repository/Repository;", "getRepo", "()Lcom/askgps/personaltrackercore/repository/Repository;", "repo$delegate", "Lkotlin/Lazy;", "registerUser", "", "path", "searchUser", "startIdentificationAsync", "id", "startIdentificationOnNavigationAsync", "", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class CameraViewModel
  extends ViewModel
  implements KoinComponent
{
  private final LiveEvent<String> idxId;
  private final Lazy repo$delegate;
  
  public CameraViewModel()
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
    this.idxId = getRepo().getIdxId();
  }
  
  private final Repository getRepo()
  {
    Lazy localLazy = this.repo$delegate;
    KProperty localKProperty = $$delegatedProperties[0];
    return (Repository)localLazy.getValue();
  }
  
  public final LiveEvent<String> getIdxId()
  {
    return this.idxId;
  }
  
  public Koin getKoin()
  {
    return KoinComponent.DefaultImpls.getKoin(this);
  }
  
  public final void registerUser(final String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "path");
    BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), (CoroutineContext)Dispatchers.getIO(), null, (Function2)new SuspendLambda(paramString, null)
    {
      int label;
      private CoroutineScope p$;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 1(this.this$0, paramString, paramAnonymousContinuation);
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
          CameraViewModel.access$getRepo$p(this.this$0).registerUser(paramString);
          return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }, 2, null);
  }
  
  public final String searchUser(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "path");
    return getRepo().searchUserInIdentix(paramString);
  }
  
  public final void startIdentificationAsync(final String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "id");
    BuildersKt.launch$default(ViewModelKt.getViewModelScope(this), (CoroutineContext)Dispatchers.getIO(), null, (Function2)new SuspendLambda(paramString, null)
    {
      int label;
      private CoroutineScope p$;
      
      public final Continuation<Unit> create(Object paramAnonymousObject, Continuation<?> paramAnonymousContinuation)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousContinuation, "completion");
        paramAnonymousContinuation = new 1(this.this$0, paramString, paramAnonymousContinuation);
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
          CameraViewModel.access$getRepo$p(this.this$0).startIdentificationAsync(paramString);
          return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
    }, 2, null);
  }
  
  public final boolean startIdentificationOnNavigationAsync(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "path");
    return getRepo().startIdentificationOnNavigationAsync(paramString);
  }
}
