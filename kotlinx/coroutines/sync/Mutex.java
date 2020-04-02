package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.selects.SelectClause2;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\004\bf\030\0002\0020\001J\020\020\t\032\0020\0032\006\020\n\032\0020\001H&J\035\020\013\032\0020\f2\n\b\002\020\n\032\004\030\0010\001H¦@?\001\000?\006\002\020\rJ\024\020\016\032\0020\0032\n\b\002\020\n\032\004\030\0010\001H&J\024\020\017\032\0020\f2\n\b\002\020\n\032\004\030\0010\001H&R\022\020\002\032\0020\003X¦\004?\006\006\032\004\b\002\020\004R \020\005\032\020\022\006\022\004\030\0010\001\022\004\022\0020\0000\006X¦\004?\006\006\032\004\b\007\020\b?\002\004\n\002\b\031?\006\020"}, d2={"Lkotlinx/coroutines/sync/Mutex;", "", "isLocked", "", "()Z", "onLock", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnLock", "()Lkotlinx/coroutines/selects/SelectClause2;", "holdsLock", "owner", "lock", "", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryLock", "unlock", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface Mutex
{
  public abstract SelectClause2<Object, Mutex> getOnLock();
  
  public abstract boolean holdsLock(Object paramObject);
  
  public abstract boolean isLocked();
  
  public abstract Object lock(Object paramObject, Continuation<? super Unit> paramContinuation);
  
  public abstract boolean tryLock(Object paramObject);
  
  public abstract void unlock(Object paramObject);
  
  @Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
  public static final class DefaultImpls {}
}
