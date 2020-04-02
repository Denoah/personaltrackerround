package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\003\n\000\bf\030\0002\0020\001J\b\020\002\032\0020\003H&J\020\020\004\032\0020\0032\006\020\005\032\0020\006H&?\006\007"}, d2={"Lkotlinx/coroutines/CompletableJob;", "Lkotlinx/coroutines/Job;", "complete", "", "completeExceptionally", "exception", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface CompletableJob
  extends Job
{
  public abstract boolean complete();
  
  public abstract boolean completeExceptionally(Throwable paramThrowable);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls
  {
    public static <R> R fold(CompletableJob paramCompletableJob, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
    {
      Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
      return Job.DefaultImpls.fold((Job)paramCompletableJob, paramR, paramFunction2);
    }
    
    public static <E extends CoroutineContext.Element> E get(CompletableJob paramCompletableJob, CoroutineContext.Key<E> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return Job.DefaultImpls.get((Job)paramCompletableJob, paramKey);
    }
    
    public static CoroutineContext minusKey(CompletableJob paramCompletableJob, CoroutineContext.Key<?> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return Job.DefaultImpls.minusKey((Job)paramCompletableJob, paramKey);
    }
    
    public static CoroutineContext plus(CompletableJob paramCompletableJob, CoroutineContext paramCoroutineContext)
    {
      Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
      return Job.DefaultImpls.plus((Job)paramCompletableJob, paramCoroutineContext);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    public static Job plus(CompletableJob paramCompletableJob, Job paramJob)
    {
      Intrinsics.checkParameterIsNotNull(paramJob, "other");
      return Job.DefaultImpls.plus((Job)paramCompletableJob, paramJob);
    }
  }
}
