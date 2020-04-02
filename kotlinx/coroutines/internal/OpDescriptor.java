package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugStringsKt;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\004\n\002\020\016\n\000\b&\030\0002\0020\001B\005?\006\002\020\002J\016\020\007\032\0020\b2\006\020\t\032\0020\000J\024\020\n\032\004\030\0010\0012\b\020\013\032\004\030\0010\001H&J\b\020\f\032\0020\rH\026R\030\020\003\032\b\022\002\b\003\030\0010\004X¦\004?\006\006\032\004\b\005\020\006?\006\016"}, d2={"Lkotlinx/coroutines/internal/OpDescriptor;", "", "()V", "atomicOp", "Lkotlinx/coroutines/internal/AtomicOp;", "getAtomicOp", "()Lkotlinx/coroutines/internal/AtomicOp;", "isEarlierThan", "", "that", "perform", "affected", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class OpDescriptor
{
  public OpDescriptor() {}
  
  public abstract AtomicOp<?> getAtomicOp();
  
  public final boolean isEarlierThan(OpDescriptor paramOpDescriptor)
  {
    Intrinsics.checkParameterIsNotNull(paramOpDescriptor, "that");
    AtomicOp localAtomicOp = getAtomicOp();
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (localAtomicOp != null)
    {
      paramOpDescriptor = paramOpDescriptor.getAtomicOp();
      bool2 = bool1;
      if (paramOpDescriptor != null)
      {
        bool2 = bool1;
        if (localAtomicOp.getOpSequence() < paramOpDescriptor.getOpSequence()) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public abstract Object perform(Object paramObject);
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(DebugStringsKt.getClassSimpleName(this));
    localStringBuilder.append('@');
    localStringBuilder.append(DebugStringsKt.getHexAddress(this));
    return localStringBuilder.toString();
  }
}
