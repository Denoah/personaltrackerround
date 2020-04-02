package kotlinx.coroutines;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.selects.SelectClause1;

@Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\020\003\n\000\bf\030\000*\006\b\000\020\001 \0012\0020\002J\021\020\007\032\0028\000H¦@?\001\000?\006\002\020\bJ\r\020\t\032\0028\000H'?\006\002\020\nJ\n\020\013\032\004\030\0010\fH'R\030\020\003\032\b\022\004\022\0028\0000\004X¦\004?\006\006\032\004\b\005\020\006?\002\004\n\002\b\031?\006\r"}, d2={"Lkotlinx/coroutines/Deferred;", "T", "Lkotlinx/coroutines/Job;", "onAwait", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnAwait", "()Lkotlinx/coroutines/selects/SelectClause1;", "await", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCompleted", "()Ljava/lang/Object;", "getCompletionExceptionOrNull", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface Deferred<T>
  extends Job
{
  public abstract Object await(Continuation<? super T> paramContinuation);
  
  public abstract T getCompleted();
  
  public abstract Throwable getCompletionExceptionOrNull();
  
  public abstract SelectClause1<T> getOnAwait();
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls
  {
    public static <T, R> R fold(Deferred<? extends T> paramDeferred, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
    {
      Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
      return Job.DefaultImpls.fold((Job)paramDeferred, paramR, paramFunction2);
    }
    
    public static <T, E extends CoroutineContext.Element> E get(Deferred<? extends T> paramDeferred, CoroutineContext.Key<E> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return Job.DefaultImpls.get((Job)paramDeferred, paramKey);
    }
    
    public static <T> CoroutineContext minusKey(Deferred<? extends T> paramDeferred, CoroutineContext.Key<?> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return Job.DefaultImpls.minusKey((Job)paramDeferred, paramKey);
    }
    
    public static <T> CoroutineContext plus(Deferred<? extends T> paramDeferred, CoroutineContext paramCoroutineContext)
    {
      Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
      return Job.DefaultImpls.plus((Job)paramDeferred, paramCoroutineContext);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    public static <T> Job plus(Deferred<? extends T> paramDeferred, Job paramJob)
    {
      Intrinsics.checkParameterIsNotNull(paramJob, "other");
      return Job.DefaultImpls.plus((Job)paramDeferred, paramJob);
    }
  }
}
