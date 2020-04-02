package io.reactivex.internal.queue;

import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class SpscLinkedArrayQueue<T>
  implements SimplePlainQueue<T>
{
  private static final Object HAS_NEXT = new Object();
  static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
  AtomicReferenceArray<Object> consumerBuffer;
  final AtomicLong consumerIndex = new AtomicLong();
  final int consumerMask;
  AtomicReferenceArray<Object> producerBuffer;
  final AtomicLong producerIndex = new AtomicLong();
  long producerLookAhead;
  int producerLookAheadStep;
  final int producerMask;
  
  public SpscLinkedArrayQueue(int paramInt)
  {
    int i = Pow2.roundToPowerOfTwo(Math.max(8, paramInt));
    paramInt = i - 1;
    AtomicReferenceArray localAtomicReferenceArray = new AtomicReferenceArray(i + 1);
    this.producerBuffer = localAtomicReferenceArray;
    this.producerMask = paramInt;
    adjustLookAheadStep(i);
    this.consumerBuffer = localAtomicReferenceArray;
    this.consumerMask = paramInt;
    this.producerLookAhead = (paramInt - 1);
    soProducerIndex(0L);
  }
  
  private void adjustLookAheadStep(int paramInt)
  {
    this.producerLookAheadStep = Math.min(paramInt / 4, MAX_LOOK_AHEAD_STEP);
  }
  
  private static int calcDirectOffset(int paramInt)
  {
    return paramInt;
  }
  
  private static int calcWrappedOffset(long paramLong, int paramInt)
  {
    return calcDirectOffset((int)paramLong & paramInt);
  }
  
  private long lpConsumerIndex()
  {
    return this.consumerIndex.get();
  }
  
  private long lpProducerIndex()
  {
    return this.producerIndex.get();
  }
  
  private long lvConsumerIndex()
  {
    return this.consumerIndex.get();
  }
  
  private static <E> Object lvElement(AtomicReferenceArray<Object> paramAtomicReferenceArray, int paramInt)
  {
    return paramAtomicReferenceArray.get(paramInt);
  }
  
  private AtomicReferenceArray<Object> lvNextBufferAndUnlink(AtomicReferenceArray<Object> paramAtomicReferenceArray, int paramInt)
  {
    paramInt = calcDirectOffset(paramInt);
    AtomicReferenceArray localAtomicReferenceArray = (AtomicReferenceArray)lvElement(paramAtomicReferenceArray, paramInt);
    soElement(paramAtomicReferenceArray, paramInt, null);
    return localAtomicReferenceArray;
  }
  
  private long lvProducerIndex()
  {
    return this.producerIndex.get();
  }
  
  private T newBufferPeek(AtomicReferenceArray<Object> paramAtomicReferenceArray, long paramLong, int paramInt)
  {
    this.consumerBuffer = paramAtomicReferenceArray;
    return lvElement(paramAtomicReferenceArray, calcWrappedOffset(paramLong, paramInt));
  }
  
  private T newBufferPoll(AtomicReferenceArray<Object> paramAtomicReferenceArray, long paramLong, int paramInt)
  {
    this.consumerBuffer = paramAtomicReferenceArray;
    paramInt = calcWrappedOffset(paramLong, paramInt);
    Object localObject = lvElement(paramAtomicReferenceArray, paramInt);
    if (localObject != null)
    {
      soElement(paramAtomicReferenceArray, paramInt, null);
      soConsumerIndex(paramLong + 1L);
    }
    return localObject;
  }
  
  private void resize(AtomicReferenceArray<Object> paramAtomicReferenceArray, long paramLong1, int paramInt, T paramT, long paramLong2)
  {
    AtomicReferenceArray localAtomicReferenceArray = new AtomicReferenceArray(paramAtomicReferenceArray.length());
    this.producerBuffer = localAtomicReferenceArray;
    this.producerLookAhead = (paramLong2 + paramLong1 - 1L);
    soElement(localAtomicReferenceArray, paramInt, paramT);
    soNext(paramAtomicReferenceArray, localAtomicReferenceArray);
    soElement(paramAtomicReferenceArray, paramInt, HAS_NEXT);
    soProducerIndex(paramLong1 + 1L);
  }
  
  private void soConsumerIndex(long paramLong)
  {
    this.consumerIndex.lazySet(paramLong);
  }
  
  private static void soElement(AtomicReferenceArray<Object> paramAtomicReferenceArray, int paramInt, Object paramObject)
  {
    paramAtomicReferenceArray.lazySet(paramInt, paramObject);
  }
  
  private void soNext(AtomicReferenceArray<Object> paramAtomicReferenceArray1, AtomicReferenceArray<Object> paramAtomicReferenceArray2)
  {
    soElement(paramAtomicReferenceArray1, calcDirectOffset(paramAtomicReferenceArray1.length() - 1), paramAtomicReferenceArray2);
  }
  
  private void soProducerIndex(long paramLong)
  {
    this.producerIndex.lazySet(paramLong);
  }
  
  private boolean writeToQueue(AtomicReferenceArray<Object> paramAtomicReferenceArray, T paramT, long paramLong, int paramInt)
  {
    soElement(paramAtomicReferenceArray, paramInt, paramT);
    soProducerIndex(paramLong + 1L);
    return true;
  }
  
  public void clear()
  {
    while ((poll() != null) || (!isEmpty())) {}
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (lvProducerIndex() == lvConsumerIndex()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean offer(T paramT)
  {
    if (paramT != null)
    {
      AtomicReferenceArray localAtomicReferenceArray = this.producerBuffer;
      long l1 = lpProducerIndex();
      int i = this.producerMask;
      int j = calcWrappedOffset(l1, i);
      if (l1 < this.producerLookAhead) {
        return writeToQueue(localAtomicReferenceArray, paramT, l1, j);
      }
      long l2 = this.producerLookAheadStep + l1;
      if (lvElement(localAtomicReferenceArray, calcWrappedOffset(l2, i)) == null)
      {
        this.producerLookAhead = (l2 - 1L);
        return writeToQueue(localAtomicReferenceArray, paramT, l1, j);
      }
      if (lvElement(localAtomicReferenceArray, calcWrappedOffset(1L + l1, i)) == null) {
        return writeToQueue(localAtomicReferenceArray, paramT, l1, j);
      }
      resize(localAtomicReferenceArray, l1, j, paramT, i);
      return true;
    }
    throw new NullPointerException("Null is not a valid element");
  }
  
  public boolean offer(T paramT1, T paramT2)
  {
    AtomicReferenceArray localAtomicReferenceArray1 = this.producerBuffer;
    long l1 = lvProducerIndex();
    int i = this.producerMask;
    long l2 = 2L + l1;
    if (lvElement(localAtomicReferenceArray1, calcWrappedOffset(l2, i)) == null)
    {
      i = calcWrappedOffset(l1, i);
      soElement(localAtomicReferenceArray1, i + 1, paramT2);
      soElement(localAtomicReferenceArray1, i, paramT1);
      soProducerIndex(l2);
    }
    else
    {
      AtomicReferenceArray localAtomicReferenceArray2 = new AtomicReferenceArray(localAtomicReferenceArray1.length());
      this.producerBuffer = localAtomicReferenceArray2;
      i = calcWrappedOffset(l1, i);
      soElement(localAtomicReferenceArray2, i + 1, paramT2);
      soElement(localAtomicReferenceArray2, i, paramT1);
      soNext(localAtomicReferenceArray1, localAtomicReferenceArray2);
      soElement(localAtomicReferenceArray1, i, HAS_NEXT);
      soProducerIndex(l2);
    }
    return true;
  }
  
  public T peek()
  {
    AtomicReferenceArray localAtomicReferenceArray = this.consumerBuffer;
    long l = lpConsumerIndex();
    int i = this.consumerMask;
    Object localObject = lvElement(localAtomicReferenceArray, calcWrappedOffset(l, i));
    if (localObject == HAS_NEXT) {
      return newBufferPeek(lvNextBufferAndUnlink(localAtomicReferenceArray, i + 1), l, i);
    }
    return localObject;
  }
  
  public T poll()
  {
    AtomicReferenceArray localAtomicReferenceArray = this.consumerBuffer;
    long l = lpConsumerIndex();
    int i = this.consumerMask;
    int j = calcWrappedOffset(l, i);
    Object localObject = lvElement(localAtomicReferenceArray, j);
    int k;
    if (localObject == HAS_NEXT) {
      k = 1;
    } else {
      k = 0;
    }
    if ((localObject != null) && (k == 0))
    {
      soElement(localAtomicReferenceArray, j, null);
      soConsumerIndex(l + 1L);
      return localObject;
    }
    if (k != 0) {
      return newBufferPoll(lvNextBufferAndUnlink(localAtomicReferenceArray, i + 1), l, i);
    }
    return null;
  }
  
  public int size()
  {
    long l3;
    for (long l1 = lvConsumerIndex();; l1 = l3)
    {
      long l2 = lvProducerIndex();
      l3 = lvConsumerIndex();
      if (l1 == l3) {
        return (int)(l2 - l3);
      }
    }
  }
}
