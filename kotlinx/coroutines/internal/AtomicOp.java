package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlinx.coroutines.DebugKt;

@Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\b\004\n\002\020\000\n\000\n\002\020\002\n\002\b\n\n\002\020\013\n\002\b\002\n\002\020\t\n\002\b\004\n\002\030\002\b&\030\000*\006\b\000\020\001 \0002\0020\032B\007?\006\004\b\002\020\003J!\020\b\032\0020\0072\006\020\004\032\0028\0002\b\020\006\032\004\030\0010\005H&?\006\004\b\b\020\tJ\031\020\013\032\004\030\0010\0052\b\020\n\032\004\030\0010\005?\006\004\b\013\020\fJ\031\020\r\032\004\030\0010\0052\b\020\004\032\004\030\0010\005?\006\004\b\r\020\fJ\031\020\016\032\004\030\0010\0052\006\020\004\032\0028\000H&?\006\004\b\016\020\fR\032\020\021\032\006\022\002\b\0030\0008V@\026X?\004?\006\006\032\004\b\017\020\020R\023\020\023\032\0020\0228F@\006?\006\006\032\004\b\023\020\024R\026\020\030\032\0020\0258V@\026X?\004?\006\006\032\004\b\026\020\027?\006\031"}, d2={"Lkotlinx/coroutines/internal/AtomicOp;", "T", "<init>", "()V", "affected", "", "failure", "", "complete", "(Ljava/lang/Object;Ljava/lang/Object;)V", "decision", "decide", "(Ljava/lang/Object;)Ljava/lang/Object;", "perform", "prepare", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "atomicOp", "", "isDecided", "()Z", "", "getOpSequence", "()J", "opSequence", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/OpDescriptor;"}, k=1, mv={1, 1, 16})
public abstract class AtomicOp<T>
  extends OpDescriptor
{
  private static final AtomicReferenceFieldUpdater _consensus$FU = AtomicReferenceFieldUpdater.newUpdater(AtomicOp.class, Object.class, "_consensus");
  private volatile Object _consensus = AtomicKt.access$getNO_DECISION$p();
  
  public AtomicOp() {}
  
  public abstract void complete(T paramT, Object paramObject);
  
  public final Object decide(Object paramObject)
  {
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (paramObject != AtomicKt.access$getNO_DECISION$p()) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    Object localObject = this._consensus;
    if (localObject != AtomicKt.access$getNO_DECISION$p()) {
      return localObject;
    }
    if (_consensus$FU.compareAndSet(this, AtomicKt.access$getNO_DECISION$p(), paramObject)) {
      return paramObject;
    }
    return this._consensus;
  }
  
  public AtomicOp<?> getAtomicOp()
  {
    return this;
  }
  
  public long getOpSequence()
  {
    return 0L;
  }
  
  public final boolean isDecided()
  {
    boolean bool;
    if (this._consensus != AtomicKt.access$getNO_DECISION$p()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final Object perform(Object paramObject)
  {
    Object localObject1 = this._consensus;
    Object localObject2 = localObject1;
    if (localObject1 == AtomicKt.access$getNO_DECISION$p()) {
      localObject2 = decide(prepare(paramObject));
    }
    complete(paramObject, localObject2);
    return localObject2;
  }
  
  public abstract Object prepare(T paramT);
}
