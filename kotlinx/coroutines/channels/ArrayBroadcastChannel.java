package kotlinx.coroutines.channels;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.ConcurrentKt;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectInstance<*>;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(bv={1, 0, 3}, d1={"\000?\001\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\003\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\005\n\002\020\t\n\002\b\006\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020\021\n\002\b\002\n\002\020\016\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\020\n\002\020!\n\002\030\002\n\002\b\007\n\002\030\002\n\002\030\002\b\000\030\000*\004\b\000\020\0012\b\022\004\022\0028\0000K2\b\022\004\022\0028\0000L:\001IB\017\022\006\020\003\032\0020\002?\006\004\b\004\020\005J\031\020\t\032\0020\b2\b\020\007\032\004\030\0010\006H\027?\006\004\b\t\020\nJ\037\020\t\032\0020\r2\016\020\007\032\n\030\0010\013j\004\030\001`\fH\026?\006\004\b\t\020\016J\031\020\017\032\0020\b2\b\020\007\032\004\030\0010\006H\002?\006\004\b\017\020\nJ\017\020\020\032\0020\rH\002?\006\004\b\020\020\021J\031\020\022\032\0020\b2\b\020\007\032\004\030\0010\006H\026?\006\004\b\022\020\nJ\017\020\024\032\0020\023H\002?\006\004\b\024\020\025J\027\020\027\032\0028\0002\006\020\026\032\0020\023H\002?\006\004\b\027\020\030J\027\020\033\032\0020\0322\006\020\031\032\0028\000H\024?\006\004\b\033\020\034J#\020\037\032\0020\0322\006\020\031\032\0028\0002\n\020\036\032\006\022\002\b\0030\035H\024?\006\004\b\037\020 J\025\020\"\032\b\022\004\022\0028\0000!H\026?\006\004\b\"\020#J4\020'\032\0020\r2\020\b\002\020%\032\n\022\004\022\0028\000\030\0010$2\020\b\002\020&\032\n\022\004\022\0028\000\030\0010$H?\020?\006\004\b'\020(R\036\020*\032\n\022\006\022\004\030\0010\0320)8\002@\002X?\004?\006\006\n\004\b*\020+R\026\020/\032\0020,8T@\024X?\004?\006\006\032\004\b-\020.R\032\0202\032\00600j\002`18\002@\002X?\004?\006\006\n\004\b2\0203R\031\020\003\032\0020\0028\006@\006?\006\f\n\004\b\003\0204\032\004\b5\0206R$\020;\032\0020\0232\006\0207\032\0020\0238B@BX?\016?\006\f\032\004\b8\020\025\"\004\b9\020:R\026\020<\032\0020\b8T@\024X?\004?\006\006\032\004\b<\020=R\026\020>\032\0020\b8T@\024X?\004?\006\006\032\004\b>\020=R$\020A\032\0020\0022\006\0207\032\0020\0028B@BX?\016?\006\f\032\004\b?\0206\"\004\b@\020\005R2\020D\032\036\022\n\022\b\022\004\022\0028\0000$0Bj\016\022\n\022\b\022\004\022\0028\0000$`C8\002@\002X?\004?\006\006\n\004\bD\020ER$\020H\032\0020\0232\006\0207\032\0020\0238B@BX?\016?\006\f\032\004\bF\020\025\"\004\bG\020:?\006J"}, d2={"Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "E", "", "capacity", "<init>", "(I)V", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "", "(Ljava/util/concurrent/CancellationException;)V", "cancelInternal", "checkSubOffers", "()V", "close", "", "computeMinHead", "()J", "index", "elementAt", "(J)Ljava/lang/Object;", "element", "", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "addSub", "removeSub", "updateHead", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;)V", "", "buffer", "[Ljava/lang/Object;", "", "getBufferDebugString", "()Ljava/lang/String;", "bufferDebugString", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "bufferLock", "Ljava/util/concurrent/locks/ReentrantLock;", "I", "getCapacity", "()I", "value", "getHead", "setHead", "(J)V", "head", "isBufferAlwaysFull", "()Z", "isBufferFull", "getSize", "setSize", "size", "", "Lkotlinx/coroutines/internal/SubscribersList;", "subscribers", "Ljava/util/List;", "getTail", "setTail", "tail", "Subscriber", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/BroadcastChannel;"}, k=1, mv={1, 1, 16})
public final class ArrayBroadcastChannel<E>
  extends AbstractSendChannel<E>
  implements BroadcastChannel<E>
{
  private volatile long _head;
  private volatile int _size;
  private volatile long _tail;
  private final Object[] buffer;
  private final ReentrantLock bufferLock;
  private final int capacity;
  private final List<Subscriber<E>> subscribers;
  
  public ArrayBroadcastChannel(int paramInt)
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
      this.bufferLock = new ReentrantLock();
      this.buffer = new Object[this.capacity];
      this._head = 0L;
      this._tail = 0L;
      this._size = 0;
      this.subscribers = ConcurrentKt.subscriberList();
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ArrayBroadcastChannel capacity must be at least 1, but ");
    localStringBuilder.append(this.capacity);
    localStringBuilder.append(" was specified");
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
  }
  
  private final boolean cancelInternal(Throwable paramThrowable)
  {
    boolean bool = close(paramThrowable);
    Iterator localIterator = this.subscribers.iterator();
    while (localIterator.hasNext()) {
      ((Subscriber)localIterator.next()).cancelInternal$kotlinx_coroutines_core(paramThrowable);
    }
    return bool;
  }
  
  private final void checkSubOffers()
  {
    Iterator localIterator = this.subscribers.iterator();
    int i = 0;
    for (int j = 0; localIterator.hasNext(); j = 1) {
      if (((Subscriber)localIterator.next()).checkOffer()) {
        i = 1;
      }
    }
    if ((i != 0) || (j == 0)) {
      updateHead$default(this, null, null, 3, null);
    }
  }
  
  private final long computeMinHead()
  {
    Iterator localIterator = this.subscribers.iterator();
    for (long l = Long.MAX_VALUE; localIterator.hasNext(); l = RangesKt.coerceAtMost(l, ((Subscriber)localIterator.next()).getSubHead())) {}
    return l;
  }
  
  private final E elementAt(long paramLong)
  {
    return this.buffer[((int)(paramLong % this.capacity))];
  }
  
  private final long getHead()
  {
    return this._head;
  }
  
  private final int getSize()
  {
    return this._size;
  }
  
  private final long getTail()
  {
    return this._tail;
  }
  
  private final void setHead(long paramLong)
  {
    this._head = paramLong;
  }
  
  private final void setSize(int paramInt)
  {
    this._size = paramInt;
  }
  
  private final void setTail(long paramLong)
  {
    this._tail = paramLong;
  }
  
  private final void updateHead(Subscriber<E> paramSubscriber1, Subscriber<E> paramSubscriber2)
  {
    Object localObject;
    for (;;)
    {
      localObject = (Send)null;
      localObject = (Lock)this.bufferLock;
      ((Lock)localObject).lock();
      if (paramSubscriber1 != null) {
        try
        {
          paramSubscriber1.setSubHead(getTail());
          boolean bool = this.subscribers.isEmpty();
          this.subscribers.add(paramSubscriber1);
          if (!bool)
          {
            ((Lock)localObject).unlock();
            return;
          }
        }
        finally
        {
          break label433;
        }
      }
      if (paramSubscriber2 != null)
      {
        this.subscribers.remove(paramSubscriber2);
        l1 = getHead();
        l2 = paramSubscriber2.getSubHead();
        if (l1 != l2)
        {
          ((Lock)localObject).unlock();
          return;
        }
      }
      long l1 = computeMinHead();
      long l3 = getTail();
      long l2 = getHead();
      long l4 = RangesKt.coerceAtMost(l1, l3);
      if (l4 <= l2)
      {
        ((Lock)localObject).unlock();
        return;
      }
      int i = getSize();
      int j;
      int k;
      int m;
      do
      {
        if (l2 >= l4) {
          break;
        }
        this.buffer[((int)(l2 % this.capacity))] = null;
        j = this.capacity;
        k = 0;
        if (i >= j) {
          j = 1;
        } else {
          j = 0;
        }
        l1 = l2 + 1L;
        setHead(l1);
        m = i - 1;
        setSize(m);
        l2 = l1;
        i = m;
      } while (j == 0);
      do
      {
        paramSubscriber1 = takeFirstSendOrPeekClosed();
        l2 = l1;
        i = m;
        if (paramSubscriber1 == null) {
          break;
        }
        if ((paramSubscriber1 instanceof Closed))
        {
          l2 = l1;
          i = m;
          break;
        }
        if (paramSubscriber1 == null) {
          Intrinsics.throwNpe();
        }
        paramSubscriber2 = paramSubscriber1.tryResumeSend(null);
      } while (paramSubscriber2 == null);
      if (DebugKt.getASSERTIONS_ENABLED())
      {
        i = k;
        if (paramSubscriber2 == CancellableContinuationImplKt.RESUME_TOKEN) {
          i = 1;
        }
        if (i == 0)
        {
          paramSubscriber1 = new java/lang/AssertionError;
          paramSubscriber1.<init>();
          throw ((Throwable)paramSubscriber1);
        }
      }
      paramSubscriber2 = this.buffer;
      i = (int)(l3 % this.capacity);
      if (paramSubscriber1 == null) {
        break;
      }
      paramSubscriber2[i] = paramSubscriber1.getPollResult();
      setSize(m + 1);
      setTail(l3 + 1L);
      paramSubscriber2 = Unit.INSTANCE;
      ((Lock)localObject).unlock();
      if (paramSubscriber1 == null) {
        Intrinsics.throwNpe();
      }
      paramSubscriber1.completeResumeSend();
      checkSubOffers();
      paramSubscriber1 = null;
      paramSubscriber2 = paramSubscriber1;
    }
    paramSubscriber1 = new kotlin/TypeCastException;
    paramSubscriber1.<init>("null cannot be cast to non-null type kotlinx.coroutines.channels.Send");
    throw paramSubscriber1;
    ((Lock)localObject).unlock();
    return;
    label433:
    ((Lock)localObject).unlock();
    throw paramSubscriber1;
  }
  
  public void cancel(CancellationException paramCancellationException)
  {
    cancelInternal((Throwable)paramCancellationException);
  }
  
  public boolean close(Throwable paramThrowable)
  {
    if (!super.close(paramThrowable)) {
      return false;
    }
    checkSubOffers();
    return true;
  }
  
  protected String getBufferDebugString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("(buffer:capacity=");
    localStringBuilder.append(this.buffer.length);
    localStringBuilder.append(",size=");
    localStringBuilder.append(getSize());
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
  
  public final int getCapacity()
  {
    return this.capacity;
  }
  
  protected boolean isBufferAlwaysFull()
  {
    return false;
  }
  
  protected boolean isBufferFull()
  {
    boolean bool;
    if (getSize() >= this.capacity) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected Object offerInternal(E paramE)
  {
    Lock localLock = (Lock)this.bufferLock;
    localLock.lock();
    try
    {
      Closed localClosed = getClosedForSend();
      if (localClosed != null) {
        return localClosed;
      }
      int i = getSize();
      if (i >= this.capacity)
      {
        paramE = AbstractChannelKt.OFFER_FAILED;
        return paramE;
      }
      long l = getTail();
      this.buffer[((int)(l % this.capacity))] = paramE;
      setSize(i + 1);
      setTail(l + 1L);
      paramE = Unit.INSTANCE;
      localLock.unlock();
      checkSubOffers();
      return AbstractChannelKt.OFFER_SUCCESS;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  protected Object offerSelectInternal(E paramE, SelectInstance<?> paramSelectInstance)
  {
    Intrinsics.checkParameterIsNotNull(paramSelectInstance, "select");
    Lock localLock = (Lock)this.bufferLock;
    localLock.lock();
    try
    {
      Closed localClosed = getClosedForSend();
      if (localClosed != null) {
        return localClosed;
      }
      int i = getSize();
      if (i >= this.capacity)
      {
        paramE = AbstractChannelKt.OFFER_FAILED;
        return paramE;
      }
      if (!paramSelectInstance.trySelect())
      {
        paramE = SelectKt.getALREADY_SELECTED();
        return paramE;
      }
      long l = getTail();
      this.buffer[((int)(l % this.capacity))] = paramE;
      setSize(i + 1);
      setTail(l + 1L);
      paramE = Unit.INSTANCE;
      localLock.unlock();
      checkSubOffers();
      return AbstractChannelKt.OFFER_SUCCESS;
    }
    finally
    {
      localLock.unlock();
    }
  }
  
  public ReceiveChannel<E> openSubscription()
  {
    Subscriber localSubscriber = new Subscriber(this);
    updateHead$default(this, localSubscriber, null, 2, null);
    return (ReceiveChannel)localSubscriber;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000J\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\004\n\002\020\002\n\002\b\002\n\002\020\000\n\002\b\003\n\002\030\002\n\002\b\b\n\002\020\t\n\002\b\006\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\b\002\030\000*\004\b\001\020\0012\b\022\004\022\0028\0010'2\b\022\004\022\0028\0010(B\025\022\f\020\003\032\b\022\004\022\0028\0010\002?\006\004\b\004\020\005J\r\020\007\032\0020\006?\006\004\b\007\020\bJ\017\020\t\032\0020\006H\002?\006\004\b\t\020\bJ\027\020\f\032\0020\0132\006\020\n\032\0020\006H\024?\006\004\b\f\020\rJ\021\020\017\032\004\030\0010\016H\002?\006\004\b\017\020\020J\021\020\021\032\004\030\0010\016H\024?\006\004\b\021\020\020J\035\020\024\032\004\030\0010\0162\n\020\023\032\006\022\002\b\0030\022H\024?\006\004\b\024\020\025R\034\020\003\032\b\022\004\022\0028\0010\0028\002@\002X?\004?\006\006\n\004\b\003\020\026R\026\020\027\032\0020\0068T@\024X?\004?\006\006\032\004\b\027\020\bR\026\020\030\032\0020\0068T@\024X?\004?\006\006\032\004\b\030\020\bR\026\020\031\032\0020\0068T@\024X?\004?\006\006\032\004\b\031\020\bR\026\020\032\032\0020\0068T@\024X?\004?\006\006\032\004\b\032\020\bR$\020!\032\0020\0332\006\020\034\032\0020\0338F@FX?\016?\006\f\032\004\b\035\020\036\"\004\b\037\020 R\032\020$\032\0060\"j\002`#8\002@\002X?\004?\006\006\n\004\b$\020%?\006&"}, d2={"Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "E", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "broadcastChannel", "<init>", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel;)V", "", "checkOffer", "()Z", "needsToCheckOfferWithoutLock", "wasClosed", "", "onCancelIdempotent", "(Z)V", "", "peekUnderLock", "()Ljava/lang/Object;", "pollInternal", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "pollSelectInternal", "(Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "isBufferAlwaysEmpty", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "", "value", "getSubHead", "()J", "setSubHead", "(J)V", "subHead", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "subLock", "Ljava/util/concurrent/locks/ReentrantLock;", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/AbstractChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;"}, k=1, mv={1, 1, 16})
  private static final class Subscriber<E>
    extends AbstractChannel<E>
    implements ReceiveChannel<E>
  {
    private volatile long _subHead;
    private final ArrayBroadcastChannel<E> broadcastChannel;
    private final ReentrantLock subLock;
    
    public Subscriber(ArrayBroadcastChannel<E> paramArrayBroadcastChannel)
    {
      this.broadcastChannel = paramArrayBroadcastChannel;
      this.subLock = new ReentrantLock();
      this._subHead = 0L;
    }
    
    private final boolean needsToCheckOfferWithoutLock()
    {
      if (getClosedForReceive() != null) {
        return false;
      }
      return (!isBufferEmpty()) || (this.broadcastChannel.getClosedForReceive() != null);
    }
    
    private final Object peekUnderLock()
    {
      long l = getSubHead();
      Object localObject = this.broadcastChannel.getClosedForReceive();
      if (l >= ArrayBroadcastChannel.access$getTail$p(this.broadcastChannel))
      {
        if (localObject == null) {
          localObject = getClosedForReceive();
        }
        if (localObject == null) {
          localObject = AbstractChannelKt.POLL_FAILED;
        }
        return localObject;
      }
      localObject = ArrayBroadcastChannel.access$elementAt(this.broadcastChannel, l);
      Closed localClosed = getClosedForReceive();
      if (localClosed != null) {
        return localClosed;
      }
      return localObject;
    }
    
    public final boolean checkOffer()
    {
      Closed localClosed = (Closed)null;
      boolean bool = false;
      for (;;)
      {
        Object localObject1 = localClosed;
        if (needsToCheckOfferWithoutLock()) {
          if (!this.subLock.tryLock()) {
            localObject1 = localClosed;
          } else {
            try
            {
              Object localObject3 = peekUnderLock();
              localObject1 = AbstractChannelKt.POLL_FAILED;
              if (localObject3 == localObject1) {}
              ReceiveOrClosed localReceiveOrClosed;
              do
              {
                this.subLock.unlock();
                break;
                if ((localObject3 instanceof Closed)) {
                  localObject1 = (Closed)localObject3;
                }
                for (;;)
                {
                  this.subLock.unlock();
                  break label214;
                  localReceiveOrClosed = takeFirstReceiveOrPeekClosed();
                  localObject1 = localClosed;
                  if (localReceiveOrClosed != null)
                  {
                    if (!(localReceiveOrClosed instanceof Closed)) {
                      break;
                    }
                    localObject1 = localClosed;
                  }
                }
                localObject1 = localReceiveOrClosed.tryResumeReceive(localObject3, null);
              } while (localObject1 == null);
              if (DebugKt.getASSERTIONS_ENABLED())
              {
                int i;
                if (localObject1 == CancellableContinuationImplKt.RESUME_TOKEN) {
                  i = 1;
                } else {
                  i = 0;
                }
                if (i == 0)
                {
                  localObject1 = new java/lang/AssertionError;
                  ((AssertionError)localObject1).<init>();
                  throw ((Throwable)localObject1);
                }
              }
              setSubHead(getSubHead() + 1L);
              this.subLock.unlock();
              if (localReceiveOrClosed == null) {
                Intrinsics.throwNpe();
              }
              localReceiveOrClosed.completeResumeReceive(localObject3);
              bool = true;
            }
            finally
            {
              this.subLock.unlock();
            }
          }
        }
      }
      label214:
      if (localObject2 != null) {
        close(localObject2.closeCause);
      }
      return bool;
    }
    
    public final long getSubHead()
    {
      return this._subHead;
    }
    
    protected boolean isBufferAlwaysEmpty()
    {
      return false;
    }
    
    protected boolean isBufferAlwaysFull()
    {
      throw ((Throwable)new IllegalStateException("Should not be used".toString()));
    }
    
    protected boolean isBufferEmpty()
    {
      boolean bool;
      if (getSubHead() >= ArrayBroadcastChannel.access$getTail$p(this.broadcastChannel)) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    protected boolean isBufferFull()
    {
      throw ((Throwable)new IllegalStateException("Should not be used".toString()));
    }
    
    protected void onCancelIdempotent(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, this, 1, null);
        Lock localLock = (Lock)this.subLock;
        localLock.lock();
        try
        {
          setSubHead(ArrayBroadcastChannel.access$getTail$p(this.broadcastChannel));
          Unit localUnit = Unit.INSTANCE;
          localLock.unlock();
        }
        finally
        {
          localLock.unlock();
        }
      }
    }
    
    protected Object pollInternal()
    {
      Object localObject1 = (Lock)this.subLock;
      ((Lock)localObject1).lock();
      try
      {
        Object localObject2 = peekUnderLock();
        boolean bool = localObject2 instanceof Closed;
        int i = 1;
        int j;
        if ((bool) || (localObject2 == AbstractChannelKt.POLL_FAILED))
        {
          j = 0;
        }
        else
        {
          setSubHead(getSubHead() + 1L);
          j = 1;
        }
        ((Lock)localObject1).unlock();
        if (!(localObject2 instanceof Closed)) {
          localObject1 = null;
        } else {
          localObject1 = localObject2;
        }
        localObject1 = (Closed)localObject1;
        if (localObject1 != null) {
          close(((Closed)localObject1).closeCause);
        }
        if (checkOffer()) {
          j = i;
        }
        if (j != 0) {
          ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3, null);
        }
        return localObject2;
      }
      finally
      {
        ((Lock)localObject1).unlock();
      }
    }
    
    protected Object pollSelectInternal(SelectInstance<?> paramSelectInstance)
    {
      Intrinsics.checkParameterIsNotNull(paramSelectInstance, "select");
      Lock localLock = (Lock)this.subLock;
      localLock.lock();
      try
      {
        Object localObject = peekUnderLock();
        boolean bool = localObject instanceof Closed;
        int i = 1;
        int j = 0;
        if (bool)
        {
          paramSelectInstance = (SelectInstance<?>)localObject;
        }
        else if (localObject == AbstractChannelKt.POLL_FAILED)
        {
          paramSelectInstance = (SelectInstance<?>)localObject;
        }
        else if (!paramSelectInstance.trySelect())
        {
          paramSelectInstance = SelectKt.getALREADY_SELECTED();
        }
        else
        {
          setSubHead(getSubHead() + 1L);
          j = 1;
          paramSelectInstance = (SelectInstance<?>)localObject;
        }
        localLock.unlock();
        if (!(paramSelectInstance instanceof Closed)) {
          localObject = null;
        } else {
          localObject = paramSelectInstance;
        }
        localObject = (Closed)localObject;
        if (localObject != null) {
          close(((Closed)localObject).closeCause);
        }
        if (checkOffer()) {
          j = i;
        }
        if (j != 0) {
          ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, null, null, 3, null);
        }
        return paramSelectInstance;
      }
      finally
      {
        localLock.unlock();
      }
    }
    
    public final void setSubHead(long paramLong)
    {
      this._subHead = paramLong;
    }
  }
}
