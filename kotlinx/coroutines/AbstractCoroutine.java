package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000d\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\013\n\002\020\002\n\000\n\002\020\000\n\000\n\002\020\016\n\002\b\002\n\002\020\003\n\002\b\021\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b'\030\000*\006\b\000\020\001 \0002\0020\0022\0020\0032\b\022\004\022\002H\0010\0042\0020\005B\027\022\006\020\006\032\0020\007\022\b\b\002\020\b\032\0020\t?\006\002\020\nJ\022\020\024\032\0020\0252\b\020\026\032\004\030\0010\027H\024J\b\020\030\032\0020\031H\024J\025\020\032\032\0020\0252\006\020\033\032\0020\034H\000?\006\002\b\035J\r\020\036\032\0020\025H\000?\006\002\b\037J\r\020 \032\0020\031H\020?\006\002\b!J\030\020\"\032\0020\0252\006\020#\032\0020\0342\006\020$\032\0020\tH\024J\025\020%\032\0020\0252\006\020&\032\0028\000H\024?\006\002\020'J\022\020(\032\0020\0252\b\020\026\032\004\030\0010\027H\004J\b\020)\032\0020\025H\024J\r\020*\032\0020\025H\000?\006\002\b+J\034\020,\032\0020\0252\f\020-\032\b\022\004\022\0028\0000.?\001\000?\006\002\020'JM\020/\032\0020\025\"\004\b\001\02002\006\020/\032\002012\006\0202\032\002H02'\0203\032#\b\001\022\004\022\002H0\022\n\022\b\022\004\022\0028\0000\004\022\006\022\004\030\0010\02704?\006\002\b5?\001\000?\006\002\0206J4\020/\032\0020\0252\006\020/\032\002012\034\0203\032\030\b\001\022\n\022\b\022\004\022\0028\0000\004\022\006\022\004\030\0010\02707?\001\000?\006\002\0208R\027\020\013\032\0020\007?\006\016\n\000\022\004\b\f\020\r\032\004\b\016\020\017R\024\020\020\032\0020\0078VX?\004?\006\006\032\004\b\021\020\017R\024\020\022\032\0020\t8VX?\004?\006\006\032\004\b\022\020\023R\020\020\006\032\0020\0078\004X?\004?\006\002\n\000?\002\004\n\002\b\031?\0069"}, d2={"Lkotlinx/coroutines/AbstractCoroutine;", "T", "Lkotlinx/coroutines/JobSupport;", "Lkotlinx/coroutines/Job;", "Lkotlin/coroutines/Continuation;", "Lkotlinx/coroutines/CoroutineScope;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "active", "", "(Lkotlin/coroutines/CoroutineContext;Z)V", "context", "context$annotations", "()V", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "coroutineContext", "getCoroutineContext", "isActive", "()Z", "afterResume", "", "state", "", "cancellationExceptionMessage", "", "handleOnCompletionException", "exception", "", "handleOnCompletionException$kotlinx_coroutines_core", "initParentJob", "initParentJob$kotlinx_coroutines_core", "nameString", "nameString$kotlinx_coroutines_core", "onCancelled", "cause", "handled", "onCompleted", "value", "(Ljava/lang/Object;)V", "onCompletionInternal", "onStart", "onStartInternal", "onStartInternal$kotlinx_coroutines_core", "resumeWith", "result", "Lkotlin/Result;", "start", "R", "Lkotlinx/coroutines/CoroutineStart;", "receiver", "block", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineStart;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/Function1;", "(Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function1;)V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class AbstractCoroutine<T>
  extends JobSupport
  implements Job, Continuation<T>, CoroutineScope
{
  private final CoroutineContext context;
  protected final CoroutineContext parentContext;
  
  public AbstractCoroutine(CoroutineContext paramCoroutineContext, boolean paramBoolean)
  {
    super(paramBoolean);
    this.parentContext = paramCoroutineContext;
    this.context = paramCoroutineContext.plus((CoroutineContext)this);
  }
  
  protected void afterResume(Object paramObject)
  {
    afterCompletion(paramObject);
  }
  
  protected String cancellationExceptionMessage()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(DebugStringsKt.getClassSimpleName(this));
    localStringBuilder.append(" was cancelled");
    return localStringBuilder.toString();
  }
  
  public final CoroutineContext getContext()
  {
    return this.context;
  }
  
  public CoroutineContext getCoroutineContext()
  {
    return this.context;
  }
  
  public final void handleOnCompletionException$kotlinx_coroutines_core(Throwable paramThrowable)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "exception");
    CoroutineExceptionHandlerKt.handleCoroutineException(this.context, paramThrowable);
  }
  
  public final void initParentJob$kotlinx_coroutines_core()
  {
    initParentJobInternal$kotlinx_coroutines_core((Job)this.parentContext.get((CoroutineContext.Key)Job.Key));
  }
  
  public boolean isActive()
  {
    return super.isActive();
  }
  
  public String nameString$kotlinx_coroutines_core()
  {
    String str = CoroutineContextKt.getCoroutineName(this.context);
    if (str != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append('"');
      localStringBuilder.append(str);
      localStringBuilder.append("\":");
      localStringBuilder.append(super.nameString$kotlinx_coroutines_core());
      return localStringBuilder.toString();
    }
    return super.nameString$kotlinx_coroutines_core();
  }
  
  protected void onCancelled(Throwable paramThrowable, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramThrowable, "cause");
  }
  
  protected void onCompleted(T paramT) {}
  
  protected final void onCompletionInternal(Object paramObject)
  {
    if ((paramObject instanceof CompletedExceptionally))
    {
      paramObject = (CompletedExceptionally)paramObject;
      onCancelled(paramObject.cause, paramObject.getHandled());
    }
    else
    {
      onCompleted(paramObject);
    }
  }
  
  protected void onStart() {}
  
  public final void onStartInternal$kotlinx_coroutines_core()
  {
    onStart();
  }
  
  public final void resumeWith(Object paramObject)
  {
    paramObject = makeCompletingOnce$kotlinx_coroutines_core(CompletedExceptionallyKt.toState(paramObject));
    if (paramObject == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
      return;
    }
    afterResume(paramObject);
  }
  
  public final <R> void start(CoroutineStart paramCoroutineStart, R paramR, Function2<? super R, ? super Continuation<? super T>, ? extends Object> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineStart, "start");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "block");
    initParentJob$kotlinx_coroutines_core();
    paramCoroutineStart.invoke(paramFunction2, paramR, (Continuation)this);
  }
  
  public final void start(CoroutineStart paramCoroutineStart, Function1<? super Continuation<? super T>, ? extends Object> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineStart, "start");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "block");
    initParentJob$kotlinx_coroutines_core();
    paramCoroutineStart.invoke(paramFunction1, (Continuation)this);
  }
}
