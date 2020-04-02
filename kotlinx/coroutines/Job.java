package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Element.DefaultImpls;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.selects.SelectClause0;

@Metadata(bv={1, 0, 3}, d1={"\000^\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\020\003\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\007\bf\030\000 (2\0020\001:\001(J\020\020\017\032\0020\0202\006\020\021\032\0020\022H'J\b\020\023\032\0020\024H\027J\024\020\023\032\0020\0072\n\b\002\020\025\032\004\030\0010\026H'J\032\020\023\032\0020\0242\020\b\002\020\025\032\n\030\0010\027j\004\030\001`\030H&J\f\020\031\032\0060\027j\002`\030H'JE\020\032\032\0020\0332\b\b\002\020\034\032\0020\0072\b\b\002\020\035\032\0020\0072'\020\036\032#\022\025\022\023\030\0010\026?\006\f\b \022\b\b!\022\004\b\b(\025\022\004\022\0020\0240\037j\002`\"H'J1\020\032\032\0020\0332'\020\036\032#\022\025\022\023\030\0010\026?\006\f\b \022\b\b!\022\004\b\b(\025\022\004\022\0020\0240\037j\002`\"H&J\021\020#\032\0020\024H¦@?\001\000?\006\002\020$J\021\020%\032\0020\0002\006\020&\032\0020\000H?\002J\b\020'\032\0020\007H&R\030\020\002\032\b\022\004\022\0020\0000\003X¦\004?\006\006\032\004\b\004\020\005R\022\020\006\032\0020\007X¦\004?\006\006\032\004\b\006\020\bR\022\020\t\032\0020\007X¦\004?\006\006\032\004\b\t\020\bR\022\020\n\032\0020\007X¦\004?\006\006\032\004\b\n\020\bR\022\020\013\032\0020\fX¦\004?\006\006\032\004\b\r\020\016?\002\004\n\002\b\031?\006)"}, d2={"Lkotlinx/coroutines/Job;", "Lkotlin/coroutines/CoroutineContext$Element;", "children", "Lkotlin/sequences/Sequence;", "getChildren", "()Lkotlin/sequences/Sequence;", "isActive", "", "()Z", "isCancelled", "isCompleted", "onJoin", "Lkotlinx/coroutines/selects/SelectClause0;", "getOnJoin", "()Lkotlinx/coroutines/selects/SelectClause0;", "attachChild", "Lkotlinx/coroutines/ChildHandle;", "child", "Lkotlinx/coroutines/ChildJob;", "cancel", "", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "getCancellationException", "invokeOnCompletion", "Lkotlinx/coroutines/DisposableHandle;", "onCancelling", "invokeImmediately", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "join", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "plus", "other", "start", "Key", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface Job
  extends CoroutineContext.Element
{
  public static final Key Key = Key.$$INSTANCE;
  
  public abstract ChildHandle attachChild(ChildJob paramChildJob);
  
  public abstract void cancel(CancellationException paramCancellationException);
  
  public abstract CancellationException getCancellationException();
  
  public abstract Sequence<Job> getChildren();
  
  public abstract SelectClause0 getOnJoin();
  
  public abstract DisposableHandle invokeOnCompletion(Function1<? super Throwable, Unit> paramFunction1);
  
  public abstract DisposableHandle invokeOnCompletion(boolean paramBoolean1, boolean paramBoolean2, Function1<? super Throwable, Unit> paramFunction1);
  
  public abstract boolean isActive();
  
  public abstract boolean isCancelled();
  
  public abstract boolean isCompleted();
  
  public abstract Object join(Continuation<? super Unit> paramContinuation);
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
  public abstract Job plus(Job paramJob);
  
  public abstract boolean start();
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls
  {
    public static <R> R fold(Job paramJob, R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
    {
      Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
      return CoroutineContext.Element.DefaultImpls.fold((CoroutineContext.Element)paramJob, paramR, paramFunction2);
    }
    
    public static <E extends CoroutineContext.Element> E get(Job paramJob, CoroutineContext.Key<E> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return CoroutineContext.Element.DefaultImpls.get((CoroutineContext.Element)paramJob, paramKey);
    }
    
    public static CoroutineContext minusKey(Job paramJob, CoroutineContext.Key<?> paramKey)
    {
      Intrinsics.checkParameterIsNotNull(paramKey, "key");
      return CoroutineContext.Element.DefaultImpls.minusKey((CoroutineContext.Element)paramJob, paramKey);
    }
    
    public static CoroutineContext plus(Job paramJob, CoroutineContext paramCoroutineContext)
    {
      Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
      return CoroutineContext.Element.DefaultImpls.plus((CoroutineContext.Element)paramJob, paramCoroutineContext);
    }
    
    @Deprecated(level=DeprecationLevel.ERROR, message="Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    public static Job plus(Job paramJob1, Job paramJob2)
    {
      Intrinsics.checkParameterIsNotNull(paramJob2, "other");
      return paramJob2;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b?\003\030\0002\b\022\004\022\0020\0020\001B\007\b\002?\006\002\020\003?\006\004"}, d2={"Lkotlinx/coroutines/Job$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlinx/coroutines/Job;", "()V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static final class Key
    implements CoroutineContext.Key<Job>
  {
    static
    {
      CoroutineExceptionHandler.Key localKey = CoroutineExceptionHandler.Key;
    }
    
    private Key() {}
  }
}
