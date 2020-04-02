package kotlinx.coroutines.channels;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(bv={1, 0, 3}, d1={"\000L\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\021\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\006\n\002\020\013\n\002\b\005\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\006\n\002\030\002\n\002\b\006\b\020\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\r\022\006\020\003\032\0020\004?\006\002\020\005J\020\020\033\032\0020\0342\006\020\035\032\0020\004H\002J\025\020\036\032\0020\b2\006\020\037\032\0028\000H\024?\006\002\020 J!\020!\032\0020\b2\006\020\037\032\0028\0002\n\020\"\032\006\022\002\b\0030#H\024?\006\002\020$J\020\020%\032\0020\0342\006\020&\032\0020\022H\024J\n\020'\032\004\030\0010\bH\024J\026\020(\032\004\030\0010\b2\n\020\"\032\006\022\002\b\0030#H\024R\030\020\006\032\n\022\006\022\004\030\0010\b0\007X?\016?\006\004\n\002\020\tR\024\020\n\032\0020\0138TX?\004?\006\006\032\004\b\f\020\rR\021\020\003\032\0020\004?\006\b\n\000\032\004\b\016\020\017R\016\020\020\032\0020\004X?\016?\006\002\n\000R\024\020\021\032\0020\0228DX?\004?\006\006\032\004\b\021\020\023R\024\020\024\032\0020\0228DX?\004?\006\006\032\004\b\024\020\023R\024\020\025\032\0020\0228DX?\004?\006\006\032\004\b\025\020\023R\024\020\026\032\0020\0228DX?\004?\006\006\032\004\b\026\020\023R\022\020\027\032\0060\030j\002`\031X?\004?\006\002\n\000R\016\020\032\032\0020\004X?\016?\006\002\n\000?\006)"}, d2={"Lkotlinx/coroutines/channels/ArrayChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "capacity", "", "(I)V", "buffer", "", "", "[Ljava/lang/Object;", "bufferDebugString", "", "getBufferDebugString", "()Ljava/lang/String;", "getCapacity", "()I", "head", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "size", "ensureCapacity", "", "currentSize", "offerInternal", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "onCancelIdempotent", "wasClosed", "pollInternal", "pollSelectInternal", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public class ArrayChannel<E>
  extends AbstractChannel<E>
{
  private Object[] buffer;
  private final int capacity;
  private int head;
  private final ReentrantLock lock;
  private int size;
  
  public ArrayChannel(int paramInt)
  {
    this.capacity = paramInt;
    int i = 1;
    if (paramInt >= 1) {
      paramInt = i;
    } else {
      paramInt = 0;
    }
    if (paramInt != 0)
    {
      this.lock = new ReentrantLock();
      this.buffer = new Object[Math.min(this.capacity, 8)];
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ArrayChannel capacity must be at least 1, but ");
    localStringBuilder.append(this.capacity);
    localStringBuilder.append(" was specified");
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
  }
  
  private final void ensureCapacity(int paramInt)
  {
    Object[] arrayOfObject1 = this.buffer;
    if (paramInt >= arrayOfObject1.length)
    {
      arrayOfObject1 = new Object[Math.min(arrayOfObject1.length * 2, this.capacity)];
      for (int i = 0; i < paramInt; i++)
      {
        Object[] arrayOfObject2 = this.buffer;
        arrayOfObject1[i] = arrayOfObject2[((this.head + i) % arrayOfObject2.length)];
      }
      this.buffer = arrayOfObject1;
      this.head = 0;
    }
  }
  
  protected String getBufferDebugString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("(buffer:capacity=");
    localStringBuilder.append(this.capacity);
    localStringBuilder.append(",size=");
    localStringBuilder.append(this.size);
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
  
  public final int getCapacity()
  {
    return this.capacity;
  }
  
  protected final boolean isBufferAlwaysEmpty()
  {
    return false;
  }
  
  protected final boolean isBufferAlwaysFull()
  {
    return false;
  }
  
  protected final boolean isBufferEmpty()
  {
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      int i = this.size;
      boolean bool;
      if (i == 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected final boolean isBufferFull()
  {
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      int i = this.size;
      int j = this.capacity;
      boolean bool;
      if (i == j) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected Object offerInternal(E paramE)
  {
    Object localObject1 = (ReceiveOrClosed)null;
    localObject1 = (Lock)this.lock;
    ((Lock)localObject1).lock();
    try
    {
      int i = this.size;
      Object localObject2 = getClosedForSend();
      if (localObject2 != null) {
        return localObject2;
      }
      if (i < this.capacity)
      {
        this.size = (i + 1);
        if (i == 0)
        {
          do
          {
            localObject2 = takeFirstReceiveOrPeekClosed();
            if (localObject2 == null) {
              break;
            }
            if ((localObject2 instanceof Closed))
            {
              this.size = i;
              if (localObject2 == null) {
                Intrinsics.throwNpe();
              }
              return localObject2;
            }
            if (localObject2 == null) {
              Intrinsics.throwNpe();
            }
            localObject3 = ((ReceiveOrClosed)localObject2).tryResumeReceive(paramE, null);
          } while (localObject3 == null);
          if (DebugKt.getASSERTIONS_ENABLED())
          {
            int j;
            if (localObject3 == CancellableContinuationImplKt.RESUME_TOKEN) {
              j = 1;
            } else {
              j = 0;
            }
            if (j == 0)
            {
              paramE = new java/lang/AssertionError;
              paramE.<init>();
              throw ((Throwable)paramE);
            }
          }
          this.size = i;
          Object localObject3 = Unit.INSTANCE;
          ((Lock)localObject1).unlock();
          if (localObject2 == null) {
            Intrinsics.throwNpe();
          }
          ((ReceiveOrClosed)localObject2).completeResumeReceive(paramE);
          if (localObject2 == null) {
            Intrinsics.throwNpe();
          }
          return ((ReceiveOrClosed)localObject2).getOfferResult();
        }
        ensureCapacity(i);
        this.buffer[((this.head + i) % this.buffer.length)] = paramE;
        paramE = AbstractChannelKt.OFFER_SUCCESS;
        return paramE;
      }
      paramE = AbstractChannelKt.OFFER_FAILED;
      return paramE;
    }
    finally
    {
      ((Lock)localObject1).unlock();
    }
  }
  
  protected Object offerSelectInternal(E paramE, SelectInstance<?> paramSelectInstance)
  {
    Intrinsics.checkParameterIsNotNull(paramSelectInstance, "select");
    Object localObject1 = (ReceiveOrClosed)null;
    localObject1 = (Lock)this.lock;
    ((Lock)localObject1).lock();
    try
    {
      int i = this.size;
      Object localObject2 = getClosedForSend();
      if (localObject2 != null) {
        return localObject2;
      }
      if (i < this.capacity)
      {
        this.size = (i + 1);
        if (i == 0)
        {
          do
          {
            AbstractSendChannel.TryOfferDesc localTryOfferDesc = describeTryOffer(paramE);
            localObject2 = paramSelectInstance.performAtomicTrySelect((AtomicDesc)localTryOfferDesc);
            if (localObject2 == null)
            {
              this.size = i;
              paramSelectInstance = (ReceiveOrClosed)localTryOfferDesc.getResult();
              localObject2 = Unit.INSTANCE;
              ((Lock)localObject1).unlock();
              if (paramSelectInstance == null) {
                Intrinsics.throwNpe();
              }
              paramSelectInstance.completeResumeReceive(paramE);
              if (paramSelectInstance == null) {
                Intrinsics.throwNpe();
              }
              return paramSelectInstance.getOfferResult();
            }
            if (localObject2 == AbstractChannelKt.OFFER_FAILED) {
              break;
            }
          } while (localObject2 == AtomicKt.RETRY_ATOMIC);
          if ((localObject2 != SelectKt.getALREADY_SELECTED()) && (!(localObject2 instanceof Closed)))
          {
            paramE = new java/lang/StringBuilder;
            paramE.<init>();
            paramE.append("performAtomicTrySelect(describeTryOffer) returned ");
            paramE.append(localObject2);
            paramE = paramE.toString();
            paramSelectInstance = new java/lang/IllegalStateException;
            paramSelectInstance.<init>(paramE.toString());
            throw ((Throwable)paramSelectInstance);
          }
          this.size = i;
          return localObject2;
        }
        if (!paramSelectInstance.trySelect())
        {
          this.size = i;
          paramE = SelectKt.getALREADY_SELECTED();
          return paramE;
        }
        ensureCapacity(i);
        this.buffer[((this.head + i) % this.buffer.length)] = paramE;
        paramE = AbstractChannelKt.OFFER_SUCCESS;
        return paramE;
      }
      paramE = AbstractChannelKt.OFFER_FAILED;
      return paramE;
    }
    finally
    {
      ((Lock)localObject1).unlock();
    }
  }
  
  protected void onCancelIdempotent(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      Lock localLock = (Lock)this.lock;
      localLock.lock();
      try
      {
        int i = this.size;
        for (int j = 0; j < i; j++)
        {
          this.buffer[this.head] = Integer.valueOf(0);
          this.head = ((this.head + 1) % this.buffer.length);
        }
        this.size = 0;
        Unit localUnit = Unit.INSTANCE;
      }
      finally
      {
        localLock.unlock();
      }
    }
    super.onCancelIdempotent(paramBoolean);
  }
  
  protected Object pollInternal()
  {
    Object localObject1 = (Send)null;
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      int i = this.size;
      if (i == 0)
      {
        localObject2 = getClosedForSend();
        if (localObject2 == null) {
          localObject2 = AbstractChannelKt.POLL_FAILED;
        }
        return localObject2;
      }
      Object localObject4 = this.buffer[this.head];
      this.buffer[this.head] = null;
      this.size = (i - 1);
      Object localObject5 = AbstractChannelKt.POLL_FAILED;
      int j = this.capacity;
      int k = 0;
      int m = 0;
      Object localObject2 = localObject1;
      Object localObject6 = localObject5;
      int n = k;
      if (i == j) {
        for (localObject2 = localObject1;; localObject2 = localObject1)
        {
          localObject1 = takeFirstSendOrPeekClosed();
          localObject6 = localObject5;
          n = k;
          if (localObject1 == null) {
            break;
          }
          if (localObject1 == null) {
            Intrinsics.throwNpe();
          }
          localObject2 = ((Send)localObject1).tryResumeSend(null);
          if (localObject2 != null)
          {
            if (DebugKt.getASSERTIONS_ENABLED())
            {
              n = m;
              if (localObject2 == CancellableContinuationImplKt.RESUME_TOKEN) {
                n = 1;
              }
              if (n == 0)
              {
                localObject2 = new java/lang/AssertionError;
                ((AssertionError)localObject2).<init>();
                throw ((Throwable)localObject2);
              }
            }
            if (localObject1 == null) {
              Intrinsics.throwNpe();
            }
            localObject6 = ((Send)localObject1).getPollResult();
            localObject2 = localObject1;
            n = 1;
            break;
          }
        }
      }
      if ((localObject6 != AbstractChannelKt.POLL_FAILED) && (!(localObject6 instanceof Closed)))
      {
        this.size = i;
        this.buffer[((this.head + i) % this.buffer.length)] = localObject6;
      }
      this.head = ((this.head + 1) % this.buffer.length);
      localObject1 = Unit.INSTANCE;
      localLock.unlock();
      if (n != 0)
      {
        if (localObject2 == null) {
          Intrinsics.throwNpe();
        }
        ((Send)localObject2).completeResumeSend();
      }
      return localObject4;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected Object pollSelectInternal(SelectInstance<?> paramSelectInstance)
  {
    Intrinsics.checkParameterIsNotNull(paramSelectInstance, "select");
    Send localSend = (Send)null;
    Lock localLock = (Lock)this.lock;
    localLock.lock();
    try
    {
      int i = this.size;
      if (i == 0)
      {
        paramSelectInstance = getClosedForSend();
        if (paramSelectInstance == null) {
          paramSelectInstance = AbstractChannelKt.POLL_FAILED;
        }
        return paramSelectInstance;
      }
      Object localObject1 = this.buffer[this.head];
      this.buffer[this.head] = null;
      this.size = (i - 1);
      Object localObject2 = AbstractChannelKt.POLL_FAILED;
      Object localObject3;
      int j;
      if (i == this.capacity)
      {
        do
        {
          AbstractChannel.TryPollDesc localTryPollDesc = describeTryPoll();
          localObject3 = paramSelectInstance.performAtomicTrySelect((AtomicDesc)localTryPollDesc);
          if (localObject3 == null)
          {
            localSend = (Send)localTryPollDesc.getResult();
            if (localSend == null) {
              Intrinsics.throwNpe();
            }
            localObject3 = localSend.getPollResult();
            j = 1;
            break label285;
          }
          if (localObject3 == AbstractChannelKt.POLL_FAILED) {
            break;
          }
        } while (localObject3 == AtomicKt.RETRY_ATOMIC);
        if (localObject3 == SelectKt.getALREADY_SELECTED())
        {
          this.size = i;
          this.buffer[this.head] = localObject1;
          return localObject3;
        }
        if ((localObject3 instanceof Closed))
        {
          localSend = (Send)localObject3;
          j = 1;
        }
        else
        {
          paramSelectInstance = new java/lang/StringBuilder;
          paramSelectInstance.<init>();
          paramSelectInstance.append("performAtomicTrySelect(describeTryOffer) returned ");
          paramSelectInstance.append(localObject3);
          paramSelectInstance = paramSelectInstance.toString();
          localObject3 = new java/lang/IllegalStateException;
          ((IllegalStateException)localObject3).<init>(paramSelectInstance.toString());
          throw ((Throwable)localObject3);
        }
      }
      else
      {
        j = 0;
        localObject3 = localObject2;
      }
      label285:
      if ((localObject3 != AbstractChannelKt.POLL_FAILED) && (!(localObject3 instanceof Closed)))
      {
        this.size = i;
        this.buffer[((this.head + i) % this.buffer.length)] = localObject3;
      }
      else if (!paramSelectInstance.trySelect())
      {
        this.size = i;
        this.buffer[this.head] = localObject1;
        paramSelectInstance = SelectKt.getALREADY_SELECTED();
        return paramSelectInstance;
      }
      this.head = ((this.head + 1) % this.buffer.length);
      paramSelectInstance = Unit.INSTANCE;
      localLock.unlock();
      if (j != 0)
      {
        if (localSend == null) {
          Intrinsics.throwNpe();
        }
        localSend.completeResumeSend();
      }
      return localObject1;
    }
    finally
    {
      localLock.unlock();
    }
  }
}
