package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(bv={1, 0, 3}, d1={"\000 \n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\002\b\003\n\002\020\002\n\002\b\003\n\002\020\013\n\000\bf\030\0002\0020\001J\021\020\006\032\0020\007H¦@?\001\000?\006\002\020\bJ\b\020\t\032\0020\007H&J\b\020\n\032\0020\013H&R\022\020\002\032\0020\003X¦\004?\006\006\032\004\b\004\020\005?\002\004\n\002\b\031?\006\f"}, d2={"Lkotlinx/coroutines/sync/Semaphore;", "", "availablePermits", "", "getAvailablePermits", "()I", "acquire", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "release", "tryAcquire", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface Semaphore
{
  public abstract Object acquire(Continuation<? super Unit> paramContinuation);
  
  public abstract int getAvailablePermits();
  
  public abstract void release();
  
  public abstract boolean tryAcquire();
}
