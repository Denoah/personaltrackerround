package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;

@Metadata(bv={1, 0, 3}, d1={"\000B\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\005\n\002\020\003\n\000\n\002\020\002\n\000\n\002\020\000\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\n\n\002\030\002\n\002\b\003\bf\030\000*\006\b\000\020\001 \0002\b\022\004\022\002H\0010\002J\024\020\b\032\0020\0042\n\b\002\020\t\032\004\030\0010\nH&J\020\020\013\032\0020\f2\006\020\r\032\0020\016H'J\b\020\017\032\0020\fH'J1\020\020\032\0020\f2'\020\021\032#\022\025\022\023\030\0010\n?\006\f\b\023\022\b\b\024\022\004\b\b(\t\022\004\022\0020\f0\022j\002`\025H&J8\020\026\032\0020\f2\006\020\027\032\0028\0002!\020\030\032\035\022\023\022\0210\n?\006\f\b\023\022\b\b\024\022\004\b\b(\t\022\004\022\0020\f0\022H'?\006\002\020\031J#\020\032\032\004\030\0010\0162\006\020\027\032\0028\0002\n\b\002\020\033\032\004\030\0010\016H'?\006\002\020\034J\022\020\035\032\004\030\0010\0162\006\020\036\032\0020\nH'J\031\020\037\032\0020\f*\0020 2\006\020\027\032\0028\000H'?\006\002\020!J\024\020\"\032\0020\f*\0020 2\006\020\036\032\0020\nH'R\022\020\003\032\0020\004X¦\004?\006\006\032\004\b\003\020\005R\022\020\006\032\0020\004X¦\004?\006\006\032\004\b\006\020\005R\022\020\007\032\0020\004X¦\004?\006\006\032\004\b\007\020\005?\006#"}, d2={"Lkotlinx/coroutines/CancellableContinuation;", "T", "Lkotlin/coroutines/Continuation;", "isActive", "", "()Z", "isCancelled", "isCompleted", "cancel", "cause", "", "completeResume", "", "token", "", "initCancellability", "invokeOnCancellation", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "resume", "value", "onCancellation", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "tryResume", "idempotent", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "tryResumeWithException", "exception", "resumeUndispatched", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Object;)V", "resumeUndispatchedWithException", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface CancellableContinuation<T>
  extends Continuation<T>
{
  public abstract boolean cancel(Throwable paramThrowable);
  
  public abstract void completeResume(Object paramObject);
  
  public abstract void invokeOnCancellation(Function1<? super Throwable, Unit> paramFunction1);
  
  public abstract boolean isActive();
  
  public abstract boolean isCancelled();
  
  public abstract boolean isCompleted();
  
  public abstract void resume(T paramT, Function1<? super Throwable, Unit> paramFunction1);
  
  public abstract void resumeUndispatched(CoroutineDispatcher paramCoroutineDispatcher, T paramT);
  
  public abstract void resumeUndispatchedWithException(CoroutineDispatcher paramCoroutineDispatcher, Throwable paramThrowable);
  
  public abstract Object tryResume(T paramT, Object paramObject);
  
  public abstract Object tryResumeWithException(Throwable paramThrowable);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls {}
}
