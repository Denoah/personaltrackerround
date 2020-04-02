package io.reactivex.internal.queue;

import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class SpscArrayQueue<E>
  extends AtomicReferenceArray<E>
  implements SimplePlainQueue<E>
{
  private static final Integer MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096);
  private static final long serialVersionUID = -1296597691183856449L;
  final AtomicLong consumerIndex = new AtomicLong();
  final int lookAheadStep;
  final int mask = length() - 1;
  final AtomicLong producerIndex = new AtomicLong();
  long producerLookAhead;
  
  public SpscArrayQueue(int paramInt)
  {
    super(Pow2.roundToPowerOfTwo(paramInt));
    this.lookAheadStep = Math.min(paramInt / 4, MAX_LOOK_AHEAD_STEP.intValue());
  }
  
  int calcElementOffset(long paramLong)
  {
    return (int)paramLong & this.mask;
  }
  
  int calcElementOffset(long paramLong, int paramInt)
  {
    return (int)paramLong & paramInt;
  }
  
  public void clear()
  {
    while ((poll() != null) || (!isEmpty())) {}
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (this.producerIndex.get() == this.consumerIndex.get()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  E lvElement(int paramInt)
  {
    return get(paramInt);
  }
  
  public boolean offer(E paramE)
  {
    if (paramE != null)
    {
      int i = this.mask;
      long l1 = this.producerIndex.get();
      int j = calcElementOffset(l1, i);
      if (l1 >= this.producerLookAhead)
      {
        long l2 = this.lookAheadStep + l1;
        if (lvElement(calcElementOffset(l2, i)) == null) {
          this.producerLookAhead = l2;
        } else if (lvElement(j) != null) {
          return false;
        }
      }
      soElement(j, paramE);
      soProducerIndex(l1 + 1L);
      return true;
    }
    throw new NullPointerException("Null is not a valid element");
  }
  
  public boolean offer(E paramE1, E paramE2)
  {
    boolean bool;
    if ((offer(paramE1)) && (offer(paramE2))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public E poll()
  {
    long l = this.consumerIndex.get();
    int i = calcElementOffset(l);
    Object localObject = lvElement(i);
    if (localObject == null) {
      return null;
    }
    soConsumerIndex(l + 1L);
    soElement(i, null);
    return localObject;
  }
  
  void soConsumerIndex(long paramLong)
  {
    this.consumerIndex.lazySet(paramLong);
  }
  
  void soElement(int paramInt, E paramE)
  {
    lazySet(paramInt, paramE);
  }
  
  void soProducerIndex(long paramLong)
  {
    this.producerIndex.lazySet(paramLong);
  }
}
