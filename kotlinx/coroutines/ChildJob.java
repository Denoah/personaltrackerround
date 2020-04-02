package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Deprecated(level=DeprecationLevel.ERROR, message="This is internal API and may be removed in the future releases")
@Metadata(bv={1, 0, 3}, d1={"\000\026\n\002\030\002\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\bg\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H'?\006\006"}, d2={"Lkotlinx/coroutines/ChildJob;", "Lkotlinx/coroutines/Job;", "parentCancelled", "", "parentJob", "Lkotlinx/coroutines/ParentJob;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface ChildJob
  extends Job
{
  public abstract void parentCancelled(ParentJob paramParentJob);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls
  {
    public static <R> R fold(ChildJob paramChildJob, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
    {
      Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
      return Job.DefaultImpls.fold((Job)paramChildJob, paramR, paramFunction2);
    }
    
    public static <E extends CoroutineContext.Element> E get(ChildJob paramChildJob, CoroutineContext.Key<E> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return Job.DefaultImpls.get((Job)paramChildJob, paramKey);
    }
    
    public static CoroutineContext minusKey(ChildJob paramChildJob, CoroutineContext.Key<?> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return Job.DefaultImpls.minusKey((Job)paramChildJob, paramKey);
    }
    
    public static CoroutineContext plus(ChildJob paramChildJob, CoroutineContext paramCoroutineContext)
    {
      Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
      return Job.DefaultImpls.plus((Job)paramChildJob, paramCoroutineContext);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    public static Job plus(ChildJob paramChildJob, Job paramJob)
    {
      Intrinsics.checkParameterIsNotNull(paramJob, "other");
      return Job.DefaultImpls.plus((Job)paramChildJob, paramJob);
    }
  }
}
