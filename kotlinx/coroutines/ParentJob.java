package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Deprecated(level=DeprecationLevel.ERROR, message="This is internal API and may be removed in the future releases")
@Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\bg\030\0002\0020\001J\f\020\002\032\0060\003j\002`\004H'?\006\005"}, d2={"Lkotlinx/coroutines/ParentJob;", "Lkotlinx/coroutines/Job;", "getChildJobCancellationCause", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface ParentJob
  extends Job
{
  public abstract CancellationException getChildJobCancellationCause();
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls
  {
    public static <R> R fold(ParentJob paramParentJob, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
    {
      Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
      return Job.DefaultImpls.fold((Job)paramParentJob, paramR, paramFunction2);
    }
    
    public static <E extends CoroutineContext.Element> E get(ParentJob paramParentJob, CoroutineContext.Key<E> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return Job.DefaultImpls.get((Job)paramParentJob, paramKey);
    }
    
    public static CoroutineContext minusKey(ParentJob paramParentJob, CoroutineContext.Key<?> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return Job.DefaultImpls.minusKey((Job)paramParentJob, paramKey);
    }
    
    public static CoroutineContext plus(ParentJob paramParentJob, CoroutineContext paramCoroutineContext)
    {
      Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
      return Job.DefaultImpls.plus((Job)paramParentJob, paramCoroutineContext);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    public static Job plus(ParentJob paramParentJob, Job paramJob)
    {
      Intrinsics.checkParameterIsNotNull(paramJob, "other");
      return Job.DefaultImpls.plus((Job)paramParentJob, paramJob);
    }
  }
}
