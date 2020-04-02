package androidx.room;

import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Element.DefaultImpls;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.Job.DefaultImpls;

@Metadata(bv={1, 0, 3}, d1={"\0000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\003\b\001\030\000 \0222\0020\001:\001\022B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006J\006\020\017\032\0020\020J\006\020\021\032\0020\020R\032\020\007\032\b\022\004\022\0020\0000\b8VX?\004?\006\006\032\004\b\t\020\nR\016\020\013\032\0020\fX?\004?\006\002\n\000R\024\020\004\032\0020\005X?\004?\006\b\n\000\032\004\b\r\020\016R\016\020\002\032\0020\003X?\004?\006\002\n\000?\006\023"}, d2={"Landroidx/room/TransactionElement;", "Lkotlin/coroutines/CoroutineContext$Element;", "transactionThreadControlJob", "Lkotlinx/coroutines/Job;", "transactionDispatcher", "Lkotlin/coroutines/ContinuationInterceptor;", "(Lkotlinx/coroutines/Job;Lkotlin/coroutines/ContinuationInterceptor;)V", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "referenceCount", "Ljava/util/concurrent/atomic/AtomicInteger;", "getTransactionDispatcher$room_ktx_release", "()Lkotlin/coroutines/ContinuationInterceptor;", "acquire", "", "release", "Key", "room-ktx_release"}, k=1, mv={1, 1, 15})
public final class TransactionElement
  implements CoroutineContext.Element
{
  public static final Key Key = new Key(null);
  private final AtomicInteger referenceCount;
  private final ContinuationInterceptor transactionDispatcher;
  private final Job transactionThreadControlJob;
  
  public TransactionElement(Job paramJob, ContinuationInterceptor paramContinuationInterceptor)
  {
    this.transactionThreadControlJob = paramJob;
    this.transactionDispatcher = paramContinuationInterceptor;
    this.referenceCount = new AtomicInteger(0);
  }
  
  public final void acquire()
  {
    this.referenceCount.incrementAndGet();
  }
  
  public <R> R fold(R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
    return CoroutineContext.Element.DefaultImpls.fold(this, paramR, paramFunction2);
  }
  
  public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    return CoroutineContext.Element.DefaultImpls.get(this, paramKey);
  }
  
  public CoroutineContext.Key<TransactionElement> getKey()
  {
    return (CoroutineContext.Key)Key;
  }
  
  public final ContinuationInterceptor getTransactionDispatcher$room_ktx_release()
  {
    return this.transactionDispatcher;
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key<?> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    return CoroutineContext.Element.DefaultImpls.minusKey(this, paramKey);
  }
  
  public CoroutineContext plus(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return CoroutineContext.Element.DefaultImpls.plus(this, paramCoroutineContext);
  }
  
  public final void release()
  {
    int i = this.referenceCount.decrementAndGet();
    if (i >= 0)
    {
      if (i == 0) {
        Job.DefaultImpls.cancel$default(this.transactionThreadControlJob, null, 1, null);
      }
      return;
    }
    throw ((Throwable)new IllegalStateException("Transaction was never started or was already released."));
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b?\003\030\0002\b\022\004\022\0020\0020\001B\007\b\002?\006\002\020\003?\006\004"}, d2={"Landroidx/room/TransactionElement$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Landroidx/room/TransactionElement;", "()V", "room-ktx_release"}, k=1, mv={1, 1, 15})
  public static final class Key
    implements CoroutineContext.Key<TransactionElement>
  {
    private Key() {}
  }
}
