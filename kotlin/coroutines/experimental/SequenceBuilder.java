package kotlin.coroutines.experimental;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.experimental.intrinsics.IntrinsicsKt;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\000.\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\002\n\002\b\004\n\002\020\034\n\002\b\002\n\002\020(\n\002\b\002\n\002\030\002\n\002\b\002\b'\030\000*\006\b\000\020\001 \0002\0020\002B\007\b\000?\006\002\020\003J\031\020\004\032\0020\0052\006\020\006\032\0028\000H¦@?\001\000?\006\002\020\007J\037\020\b\032\0020\0052\f\020\t\032\b\022\004\022\0028\0000\nH?@?\001\000?\006\002\020\013J\037\020\b\032\0020\0052\f\020\f\032\b\022\004\022\0028\0000\rH¦@?\001\000?\006\002\020\016J\037\020\b\032\0020\0052\f\020\017\032\b\022\004\022\0028\0000\020H?@?\001\000?\006\002\020\021?\002\004\n\002\b\t?\006\022"}, d2={"Lkotlin/coroutines/experimental/SequenceBuilder;", "T", "", "()V", "yield", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "yieldAll", "elements", "", "(Ljava/lang/Iterable;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "iterator", "", "(Ljava/util/Iterator;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "sequence", "Lkotlin/sequences/Sequence;", "(Lkotlin/sequences/Sequence;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "kotlin-stdlib-coroutines"}, k=1, mv={1, 1, 16})
public abstract class SequenceBuilder<T>
{
  public SequenceBuilder() {}
  
  public abstract Object yield(T paramT, Continuation<? super Unit> paramContinuation);
  
  public final Object yieldAll(Iterable<? extends T> paramIterable, Continuation<? super Unit> paramContinuation)
  {
    if (((paramIterable instanceof Collection)) && (((Collection)paramIterable).isEmpty())) {
      return Unit.INSTANCE;
    }
    paramIterable = yieldAll(paramIterable.iterator(), paramContinuation);
    if (paramIterable == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramIterable;
    }
    return Unit.INSTANCE;
  }
  
  public abstract Object yieldAll(Iterator<? extends T> paramIterator, Continuation<? super Unit> paramContinuation);
  
  public final Object yieldAll(Sequence<? extends T> paramSequence, Continuation<? super Unit> paramContinuation)
  {
    paramSequence = yieldAll(paramSequence.iterator(), paramContinuation);
    if (paramSequence == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramSequence;
    }
    return Unit.INSTANCE;
  }
}
