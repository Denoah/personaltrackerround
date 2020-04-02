package com.askgps.personaltrackercore.ui.sos;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import com.askgps.personaltrackercore.LogKt;
import com.askgps.personaltrackercore.repository.Repository;
import java.util.concurrent.CancellationException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
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
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.Job.DefaultImpls;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;
import org.koin.core.KoinComponent.DefaultImpls;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.registry.ScopeRegistry;
import org.koin.core.scope.Scope;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\030\002\n\002\020\b\n\002\b\005\n\002\030\002\n\000\n\002\020\002\n\002\b\004\030\000 \0272\0020\0012\0020\002:\001\027B\005?\006\002\020\003J\006\020\023\032\0020\024J\b\020\025\032\0020\024H\002J\006\020\026\032\0020\024R\033\020\004\032\0020\0058BX??\002?\006\f\n\004\b\b\020\t\032\004\b\006\020\007R\037\020\n\032\020\022\f\022\n \r*\004\030\0010\f0\f0\013?\006\b\n\000\032\004\b\016\020\017R\016\020\020\032\0020\fX?\016?\006\002\n\000R\020\020\021\032\004\030\0010\022X?\016?\006\002\n\000?\006\030"}, d2={"Lcom/askgps/personaltrackercore/ui/sos/SosViewModel;", "Landroidx/lifecycle/ViewModel;", "Lorg/koin/core/KoinComponent;", "()V", "repo", "Lcom/askgps/personaltrackercore/repository/Repository;", "getRepo", "()Lcom/askgps/personaltrackercore/repository/Repository;", "repo$delegate", "Lkotlin/Lazy;", "timer", "Landroidx/lifecycle/MutableLiveData;", "", "kotlin.jvm.PlatformType", "getTimer", "()Landroidx/lifecycle/MutableLiveData;", "timerCounter", "timerJob", "Lkotlinx/coroutines/Job;", "cancel", "", "sendSos", "startTimer", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class SosViewModel
  extends ViewModel
  implements KoinComponent
{
  public static final Companion Companion = new Companion(null);
  public static final long DELAY_SEC = 1000L;
  public static final int START_TIMER = 5;
  private final Lazy repo$delegate;
  private final MutableLiveData<Integer> timer = new MutableLiveData(Integer.valueOf(5));
  private int timerCounter = 5;
  private Job timerJob;
  
  public SosViewModel()
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
  
  private final void sendSos()
  {
    getRepo().processingSosMessage();
  }
  
  public final void cancel()
  {
    Job localJob = this.timerJob;
    if (localJob != null) {
      Job.DefaultImpls.cancel$default(localJob, null, 1, null);
    }
  }
  
  public Koin getKoin()
  {
    return KoinComponent.DefaultImpls.getKoin(this);
  }
  
  public final MutableLiveData<Integer> getTimer()
  {
    return this.timer;
  }
  
  public final void startTimer()
  {
    Object localObject = this.timerJob;
    if ((localObject != null) && (((Job)localObject).isActive() == true)) {
      return;
    }
    try
    {
      localObject = ViewModelKt.getViewModelScope(this);
      SuspendLambda local1 = new com/askgps/personaltrackercore/ui/sos/SosViewModel$startTimer$1;
      local1.<init>(this, null);
      this.timerJob = BuildersKt.launch$default((CoroutineScope)localObject, null, null, (Function2)local1, 3, null);
    }
    catch (CancellationException localCancellationException)
    {
      LogKt.toLog$default("cancel timer", null, null, null, 7, null);
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\020\b\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\006X?T?\006\002\n\000?\006\007"}, d2={"Lcom/askgps/personaltrackercore/ui/sos/SosViewModel$Companion;", "", "()V", "DELAY_SEC", "", "START_TIMER", "", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
  }
}
