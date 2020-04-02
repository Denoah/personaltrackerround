package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

@Metadata(bv={1, 0, 3}, d1={"kotlinx/coroutines/BuildersKt__BuildersKt", "kotlinx/coroutines/BuildersKt__Builders_commonKt"}, k=4, mv={1, 1, 16})
public final class BuildersKt
{
  public static final <T> Deferred<T> async(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext, CoroutineStart paramCoroutineStart, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> paramFunction2)
  {
    return BuildersKt__Builders_commonKt.async(paramCoroutineScope, paramCoroutineContext, paramCoroutineStart, paramFunction2);
  }
  
  public static final <T> Object invoke(CoroutineDispatcher paramCoroutineDispatcher, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> paramFunction2, Continuation<? super T> paramContinuation)
  {
    return BuildersKt__Builders_commonKt.invoke(paramCoroutineDispatcher, paramFunction2, paramContinuation);
  }
  
  private static final Object invoke$$forInline(CoroutineDispatcher paramCoroutineDispatcher, Function2 paramFunction2, Continuation paramContinuation)
  {
    return BuildersKt__Builders_commonKt.invoke(paramCoroutineDispatcher, paramFunction2, paramContinuation);
  }
  
  public static final Job launch(CoroutineScope paramCoroutineScope, CoroutineContext paramCoroutineContext, CoroutineStart paramCoroutineStart, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    return BuildersKt__Builders_commonKt.launch(paramCoroutineScope, paramCoroutineContext, paramCoroutineStart, paramFunction2);
  }
  
  public static final <T> T runBlocking(CoroutineContext paramCoroutineContext, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> paramFunction2)
    throws InterruptedException
  {
    return BuildersKt__BuildersKt.runBlocking(paramCoroutineContext, paramFunction2);
  }
  
  public static final <T> Object withContext(CoroutineContext paramCoroutineContext, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> paramFunction2, Continuation<? super T> paramContinuation)
  {
    return BuildersKt__Builders_commonKt.withContext(paramCoroutineContext, paramFunction2, paramContinuation);
  }
}
