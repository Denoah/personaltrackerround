package kotlinx.coroutines.channels;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.YieldKt;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.AddLastDesc;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.CondAddOp;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.RemoveFirstDesc;
import kotlinx.coroutines.internal.LockFreeLinkedList_commonKt;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(bv={1, 0, 3}, d1={"\000?\001\n\002\030\002\n\002\b\003\n\002\020\003\n\000\n\002\020\013\n\002\b\002\n\002\020\b\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\t\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\t\n\002\020\016\n\002\b\022\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\f\b \030\000*\004\b\000\020\0012\b\022\004\022\0028\00005:\005cdefgB\007?\006\004\b\002\020\003J\031\020\007\032\0020\0062\b\020\005\032\004\030\0010\004H\026?\006\004\b\007\020\bJ\017\020\n\032\0020\tH\002?\006\004\b\n\020\013J#\020\017\032\016\022\002\b\0030\rj\006\022\002\b\003`\0162\006\020\f\032\0028\000H\004?\006\004\b\017\020\020J#\020\021\032\016\022\002\b\0030\rj\006\022\002\b\003`\0162\006\020\f\032\0028\000H\004?\006\004\b\021\020\020J\035\020\023\032\b\022\004\022\0028\0000\0222\006\020\f\032\0028\000H\004?\006\004\b\023\020\024J\031\020\030\032\004\030\0010\0272\006\020\026\032\0020\025H\002?\006\004\b\030\020\031J\033\020\035\032\0020\0342\n\020\033\032\006\022\002\b\0030\032H\002?\006\004\b\035\020\036J\033\020\037\032\0020\0042\n\020\033\032\006\022\002\b\0030\032H\002?\006\004\b\037\020 J)\020$\032\0020\0342\030\020#\032\024\022\006\022\004\030\0010\004\022\004\022\0020\0340!j\002`\"H\026?\006\004\b$\020%J\031\020&\032\0020\0342\b\020\005\032\004\030\0010\004H\002?\006\004\b&\020'J\025\020(\032\0020\0062\006\020\f\032\0028\000?\006\004\b(\020)J\027\020*\032\0020\0272\006\020\f\032\0028\000H\024?\006\004\b*\020+J#\020.\032\0020\0272\006\020\f\032\0028\0002\n\020-\032\006\022\002\b\0030,H\024?\006\004\b.\020/J\027\0201\032\0020\0342\006\020\033\032\00200H\024?\006\004\b1\0202JX\0208\032\0020\034\"\004\b\001\02032\f\020-\032\b\022\004\022\0028\0010,2\006\020\f\032\0028\0002(\0207\032$\b\001\022\n\022\b\022\004\022\0028\00005\022\n\022\b\022\004\022\0028\00106\022\006\022\004\030\0010\02704H\002?\001\000?\006\004\b8\0209J\033\020\026\032\0020\0342\006\020\f\032\0028\000H?@?\001\000?\006\004\b\026\020:J\035\020<\032\b\022\002\b\003\030\0010;2\006\020\f\032\0028\000H\004?\006\004\b<\020=J\033\020?\032\0020\0342\006\020\f\032\0028\000H?@?\001\000?\006\004\b>\020:J\033\020@\032\0020\0342\006\020\f\032\0028\000H?@?\001\000?\006\004\b@\020:J\027\020A\032\n\022\004\022\0028\000\030\0010;H\024?\006\004\bA\020BJ\021\020C\032\004\030\0010\025H\004?\006\004\bC\020DJ\017\020F\032\0020EH\026?\006\004\bF\020GJ#\020H\032\0020\034*\006\022\002\b\003062\n\020\033\032\006\022\002\b\0030\032H\002?\006\004\bH\020IR\026\020K\032\0020E8T@\024X?\004?\006\006\032\004\bJ\020GR\034\020N\032\b\022\002\b\003\030\0010\0328D@\004X?\004?\006\006\032\004\bL\020MR\034\020P\032\b\022\002\b\003\030\0010\0328D@\004X?\004?\006\006\032\004\bO\020MR\026\020S\032\0020\0068B@\002X?\004?\006\006\032\004\bQ\020RR\026\020T\032\0020\0068$@$X¤\004?\006\006\032\004\bT\020RR\026\020U\032\0020\0068$@$X¤\004?\006\006\032\004\bU\020RR\023\020V\032\0020\0068F@\006?\006\006\032\004\bV\020RR\023\020W\032\0020\0068F@\006?\006\006\032\004\bW\020RR%\020[\032\024\022\004\022\0028\000\022\n\022\b\022\004\022\0028\000050X8F@\006?\006\006\032\004\bY\020ZR\034\020]\032\0020\\8\004@\004X?\004?\006\f\n\004\b]\020^\032\004\b_\020`R\026\020b\032\0020E8B@\002X?\004?\006\006\032\004\ba\020G?\002\004\n\002\b\031?\006h"}, d2={"Lkotlinx/coroutines/channels/AbstractSendChannel;", "E", "<init>", "()V", "", "cause", "", "close", "(Ljava/lang/Throwable;)Z", "", "countQueueSize", "()I", "element", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/AddLastDesc;", "describeSendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "describeSendConflated", "Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "describeTryOffer", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "Lkotlinx/coroutines/channels/Send;", "send", "", "enqueueSend", "(Lkotlinx/coroutines/channels/Send;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/Closed;", "closed", "", "helpClose", "(Lkotlinx/coroutines/channels/Closed;)V", "helpCloseAndGetSendException", "(Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "Lkotlin/Function1;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "(Lkotlin/jvm/functions/Function1;)V", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "offer", "(Ljava/lang/Object;)Z", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onClosedIdempotent", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "R", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendFair$kotlinx_coroutines_core", "sendFair", "sendSuspend", "takeFirstReceiveOrPeekClosed", "()Lkotlinx/coroutines/channels/ReceiveOrClosed;", "takeFirstSendOrPeekClosed", "()Lkotlinx/coroutines/channels/Send;", "", "toString", "()Ljava/lang/String;", "helpCloseAndResumeWithSendException", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/channels/Closed;)V", "getBufferDebugString", "bufferDebugString", "getClosedForReceive", "()Lkotlinx/coroutines/channels/Closed;", "closedForReceive", "getClosedForSend", "closedForSend", "getFull", "()Z", "full", "isBufferAlwaysFull", "isBufferFull", "isClosedForSend", "isFull", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueue", "()Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueueDebugStateString", "queueDebugStateString", "SendBuffered", "SendBufferedDesc", "SendConflatedDesc", "SendSelect", "TryOfferDesc", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class AbstractSendChannel<E>
  implements SendChannel<E>
{
  private static final AtomicReferenceFieldUpdater onCloseHandler$FU = AtomicReferenceFieldUpdater.newUpdater(AbstractSendChannel.class, Object.class, "onCloseHandler");
  private volatile Object onCloseHandler = null;
  private final LockFreeLinkedListHead queue = new LockFreeLinkedListHead();
  
  public AbstractSendChannel() {}
  
  private final int countQueueSize()
  {
    LockFreeLinkedListHead localLockFreeLinkedListHead = this.queue;
    Object localObject = localLockFreeLinkedListHead.getNext();
    if (localObject != null)
    {
      localObject = (LockFreeLinkedListNode)localObject;
      int j;
      for (int i = 0; (Intrinsics.areEqual(localObject, localLockFreeLinkedListHead) ^ true); i = j)
      {
        j = i;
        if ((localObject instanceof LockFreeLinkedListNode)) {
          j = i + 1;
        }
        localObject = ((LockFreeLinkedListNode)localObject).getNextNode();
      }
      return i;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  private final Object enqueueSend(Send paramSend)
  {
    LockFreeLinkedListHead localLockFreeLinkedListHead;
    Object localObject1;
    if (isBufferAlwaysFull())
    {
      localLockFreeLinkedListHead = this.queue;
      do
      {
        localObject1 = localLockFreeLinkedListHead.getPrev();
        if (localObject1 == null) {
          break;
        }
        localObject1 = (LockFreeLinkedListNode)localObject1;
        if ((localObject1 instanceof ReceiveOrClosed)) {
          return localObject1;
        }
      } while (!((LockFreeLinkedListNode)localObject1).addNext((LockFreeLinkedListNode)paramSend, localLockFreeLinkedListHead));
      break label153;
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }
    else
    {
      localLockFreeLinkedListHead = this.queue;
      paramSend = (LockFreeLinkedListNode)paramSend;
      localObject1 = (LockFreeLinkedListNode.CondAddOp)new LockFreeLinkedListNode.CondAddOp(paramSend)
      {
        public Object prepare(LockFreeLinkedListNode paramAnonymousLockFreeLinkedListNode)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousLockFreeLinkedListNode, "affected");
          if (jdField_this.isBufferFull()) {
            paramAnonymousLockFreeLinkedListNode = null;
          } else {
            paramAnonymousLockFreeLinkedListNode = LockFreeLinkedListKt.getCONDITION_FALSE();
          }
          return paramAnonymousLockFreeLinkedListNode;
        }
      };
      int i;
      do
      {
        Object localObject2 = localLockFreeLinkedListHead.getPrev();
        if (localObject2 == null) {
          break label155;
        }
        localObject2 = (LockFreeLinkedListNode)localObject2;
        if ((localObject2 instanceof ReceiveOrClosed)) {
          return localObject2;
        }
        i = ((LockFreeLinkedListNode)localObject2).tryCondAddNext(paramSend, localLockFreeLinkedListHead, (LockFreeLinkedListNode.CondAddOp)localObject1);
        j = 1;
        if (i == 1) {
          break;
        }
      } while (i != 2);
      int j = 0;
      if (j == 0) {
        return AbstractChannelKt.ENQUEUE_FAILED;
      }
    }
    label153:
    return null;
    label155:
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  private final boolean getFull()
  {
    boolean bool;
    if ((!(this.queue.getNextNode() instanceof ReceiveOrClosed)) && (isBufferFull())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private final String getQueueDebugStateString()
  {
    LockFreeLinkedListNode localLockFreeLinkedListNode1 = this.queue.getNextNode();
    if (localLockFreeLinkedListNode1 == this.queue) {
      return "EmptyQueue";
    }
    Object localObject1;
    if ((localLockFreeLinkedListNode1 instanceof Closed))
    {
      localObject1 = localLockFreeLinkedListNode1.toString();
    }
    else if ((localLockFreeLinkedListNode1 instanceof Receive))
    {
      localObject1 = "ReceiveQueued";
    }
    else if ((localLockFreeLinkedListNode1 instanceof Send))
    {
      localObject1 = "SendQueued";
    }
    else
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("UNEXPECTED:");
      ((StringBuilder)localObject1).append(localLockFreeLinkedListNode1);
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    LockFreeLinkedListNode localLockFreeLinkedListNode2 = this.queue.getPrevNode();
    Object localObject2 = localObject1;
    if (localLockFreeLinkedListNode2 != localLockFreeLinkedListNode1)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(",queueSize=");
      ((StringBuilder)localObject2).append(countQueueSize());
      localObject1 = ((StringBuilder)localObject2).toString();
      localObject2 = localObject1;
      if ((localLockFreeLinkedListNode2 instanceof Closed))
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append(",closedForSend=");
        ((StringBuilder)localObject2).append(localLockFreeLinkedListNode2);
        localObject2 = ((StringBuilder)localObject2).toString();
      }
    }
    return localObject2;
  }
  
  private final void helpClose(Closed<?> paramClosed)
  {
    Object localObject1 = InlineList.constructor-impl$default(null, 1, null);
    for (;;)
    {
      LockFreeLinkedListNode localLockFreeLinkedListNode = paramClosed.getPrevNode();
      Object localObject2 = localLockFreeLinkedListNode;
      if (!(localLockFreeLinkedListNode instanceof Receive)) {
        localObject2 = null;
      }
      localObject2 = (Receive)localObject2;
      if (localObject2 == null) {
        break;
      }
      if (!((Receive)localObject2).remove()) {
        ((Receive)localObject2).helpRemove();
      } else {
        localObject1 = InlineList.plus-impl(localObject1, localObject2);
      }
    }
    if (localObject1 != null) {
      if (!(localObject1 instanceof ArrayList))
      {
        ((Receive)localObject1).resumeReceiveClosed(paramClosed);
      }
      else
      {
        if (localObject1 == null) {
          break label138;
        }
        localObject1 = (ArrayList)localObject1;
        for (int i = ((ArrayList)localObject1).size() - 1; i >= 0; i--) {
          ((Receive)((ArrayList)localObject1).get(i)).resumeReceiveClosed(paramClosed);
        }
      }
    }
    onClosedIdempotent((LockFreeLinkedListNode)paramClosed);
    return;
    label138:
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<E> /* = java.util.ArrayList<E> */");
  }
  
  private final Throwable helpCloseAndGetSendException(Closed<?> paramClosed)
  {
    helpClose(paramClosed);
    return paramClosed.getSendException();
  }
  
  private final void helpCloseAndResumeWithSendException(Continuation<?> paramContinuation, Closed<?> paramClosed)
  {
    helpClose(paramClosed);
    Throwable localThrowable = paramClosed.getSendException();
    paramClosed = Result.Companion;
    paramContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure(localThrowable)));
  }
  
  private final void invokeOnCloseHandler(Throwable paramThrowable)
  {
    Object localObject = this.onCloseHandler;
    if ((localObject != null) && (localObject != AbstractChannelKt.HANDLER_INVOKED) && (onCloseHandler$FU.compareAndSet(this, localObject, AbstractChannelKt.HANDLER_INVOKED))) {
      ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(localObject, 1)).invoke(paramThrowable);
    }
  }
  
  private final <R> void registerSelectSend(SelectInstance<? super R> paramSelectInstance, E paramE, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    Object localObject;
    do
    {
      if (paramSelectInstance.isSelected()) {
        return;
      }
      if (getFull())
      {
        SendSelect localSendSelect = new SendSelect(paramE, this, paramSelectInstance, paramFunction2);
        localObject = enqueueSend((Send)localSendSelect);
        if (localObject == null)
        {
          paramSelectInstance.disposeOnSelect((DisposableHandle)localSendSelect);
          return;
        }
        if (!(localObject instanceof Closed))
        {
          if ((localObject != AbstractChannelKt.ENQUEUE_FAILED) && (!(localObject instanceof Receive)))
          {
            paramSelectInstance = new StringBuilder();
            paramSelectInstance.append("enqueueSend returned ");
            paramSelectInstance.append(localObject);
            paramSelectInstance.append(' ');
            throw ((Throwable)new IllegalStateException(paramSelectInstance.toString().toString()));
          }
        }
        else {
          throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException((Closed)localObject));
        }
      }
      localObject = offerSelectInternal(paramE, paramSelectInstance);
      if (localObject == SelectKt.getALREADY_SELECTED()) {
        return;
      }
    } while ((localObject == AbstractChannelKt.OFFER_FAILED) || (localObject == AtomicKt.RETRY_ATOMIC));
    if (localObject == AbstractChannelKt.OFFER_SUCCESS)
    {
      UndispatchedKt.startCoroutineUnintercepted(paramFunction2, this, paramSelectInstance.getCompletion());
      return;
    }
    if ((localObject instanceof Closed)) {
      throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException((Closed)localObject));
    }
    paramSelectInstance = new StringBuilder();
    paramSelectInstance.append("offerSelectInternal returned ");
    paramSelectInstance.append(localObject);
    throw ((Throwable)new IllegalStateException(paramSelectInstance.toString().toString()));
  }
  
  public boolean close(Throwable paramThrowable)
  {
    Object localObject1 = new Closed(paramThrowable);
    LockFreeLinkedListHead localLockFreeLinkedListHead = this.queue;
    Object localObject2;
    boolean bool2;
    do
    {
      localObject2 = localLockFreeLinkedListHead.getPrev();
      if (localObject2 == null) {
        break label122;
      }
      localObject2 = (LockFreeLinkedListNode)localObject2;
      boolean bool1 = localObject2 instanceof Closed;
      bool2 = true;
      if (!(bool1 ^ true))
      {
        bool2 = false;
        break;
      }
    } while (!((LockFreeLinkedListNode)localObject2).addNext((LockFreeLinkedListNode)localObject1, localLockFreeLinkedListHead));
    if (!bool2)
    {
      localObject1 = this.queue.getPrevNode();
      if (localObject1 != null) {
        localObject1 = (Closed)localObject1;
      }
    }
    else
    {
      helpClose((Closed)localObject1);
      if (bool2) {
        invokeOnCloseHandler(paramThrowable);
      }
      return bool2;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.Closed<*>");
    label122:
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  protected final LockFreeLinkedListNode.AddLastDesc<?> describeSendBuffered(E paramE)
  {
    return (LockFreeLinkedListNode.AddLastDesc)new SendBufferedDesc(this.queue, paramE);
  }
  
  protected final LockFreeLinkedListNode.AddLastDesc<?> describeSendConflated(E paramE)
  {
    return (LockFreeLinkedListNode.AddLastDesc)new SendConflatedDesc(this.queue, paramE);
  }
  
  protected final TryOfferDesc<E> describeTryOffer(E paramE)
  {
    return new TryOfferDesc(paramE, this.queue);
  }
  
  protected String getBufferDebugString()
  {
    return "";
  }
  
  protected final Closed<?> getClosedForReceive()
  {
    Object localObject1 = this.queue.getNextNode();
    boolean bool = localObject1 instanceof Closed;
    Object localObject2 = null;
    if (!bool) {
      localObject1 = null;
    }
    Closed localClosed = (Closed)localObject1;
    localObject1 = localObject2;
    if (localClosed != null)
    {
      helpClose(localClosed);
      localObject1 = localClosed;
    }
    return localObject1;
  }
  
  protected final Closed<?> getClosedForSend()
  {
    Object localObject1 = this.queue.getPrevNode();
    boolean bool = localObject1 instanceof Closed;
    Object localObject2 = null;
    if (!bool) {
      localObject1 = null;
    }
    Closed localClosed = (Closed)localObject1;
    localObject1 = localObject2;
    if (localClosed != null)
    {
      helpClose(localClosed);
      localObject1 = localClosed;
    }
    return localObject1;
  }
  
  public final SelectClause2<E, SendChannel<E>> getOnSend()
  {
    (SelectClause2)new SelectClause2()
    {
      public <R> void registerSelectClause2(SelectInstance<? super R> paramAnonymousSelectInstance, E paramAnonymousE, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> paramAnonymousFunction2)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousSelectInstance, "select");
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunction2, "block");
        AbstractSendChannel.access$registerSelectSend(this.this$0, paramAnonymousSelectInstance, paramAnonymousE, paramAnonymousFunction2);
      }
    };
  }
  
  protected final LockFreeLinkedListHead getQueue()
  {
    return this.queue;
  }
  
  public void invokeOnClose(Function1<? super Throwable, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "handler");
    if (!onCloseHandler$FU.compareAndSet(this, null, paramFunction1))
    {
      localObject = this.onCloseHandler;
      if (localObject == AbstractChannelKt.HANDLER_INVOKED) {
        throw ((Throwable)new IllegalStateException("Another handler was already registered and successfully invoked"));
      }
      paramFunction1 = new StringBuilder();
      paramFunction1.append("Another handler was already registered: ");
      paramFunction1.append(localObject);
      throw ((Throwable)new IllegalStateException(paramFunction1.toString()));
    }
    Object localObject = getClosedForSend();
    if ((localObject != null) && (onCloseHandler$FU.compareAndSet(this, paramFunction1, AbstractChannelKt.HANDLER_INVOKED))) {
      paramFunction1.invoke(((Closed)localObject).closeCause);
    }
  }
  
  protected abstract boolean isBufferAlwaysFull();
  
  protected abstract boolean isBufferFull();
  
  public final boolean isClosedForSend()
  {
    boolean bool;
    if (getClosedForSend() != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isFull()
  {
    return getFull();
  }
  
  public final boolean offer(E paramE)
  {
    paramE = offerInternal(paramE);
    if (paramE == AbstractChannelKt.OFFER_SUCCESS) {
      return true;
    }
    if (paramE == AbstractChannelKt.OFFER_FAILED)
    {
      paramE = getClosedForSend();
      if (paramE == null) {
        return false;
      }
      throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException(paramE));
    }
    if ((paramE instanceof Closed)) {
      throw StackTraceRecoveryKt.recoverStackTrace(helpCloseAndGetSendException((Closed)paramE));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("offerInternal returned ");
    localStringBuilder.append(paramE);
    throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
  }
  
  protected Object offerInternal(E paramE)
  {
    ReceiveOrClosed localReceiveOrClosed;
    Symbol localSymbol;
    do
    {
      localReceiveOrClosed = takeFirstReceiveOrPeekClosed();
      if (localReceiveOrClosed == null) {
        break;
      }
      localSymbol = localReceiveOrClosed.tryResumeReceive(paramE, null);
    } while (localSymbol == null);
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int i;
      if (localSymbol == CancellableContinuationImplKt.RESUME_TOKEN) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    localReceiveOrClosed.completeResumeReceive(paramE);
    return localReceiveOrClosed.getOfferResult();
    return AbstractChannelKt.OFFER_FAILED;
  }
  
  protected Object offerSelectInternal(E paramE, SelectInstance<?> paramSelectInstance)
  {
    Intrinsics.checkParameterIsNotNull(paramSelectInstance, "select");
    TryOfferDesc localTryOfferDesc = describeTryOffer(paramE);
    paramSelectInstance = paramSelectInstance.performAtomicTrySelect((AtomicDesc)localTryOfferDesc);
    if (paramSelectInstance != null) {
      return paramSelectInstance;
    }
    paramSelectInstance = (ReceiveOrClosed)localTryOfferDesc.getResult();
    paramSelectInstance.completeResumeReceive(paramE);
    return paramSelectInstance.getOfferResult();
  }
  
  protected void onClosedIdempotent(LockFreeLinkedListNode paramLockFreeLinkedListNode)
  {
    Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "closed");
  }
  
  public final Object send(E paramE, Continuation<? super Unit> paramContinuation)
  {
    if (offerInternal(paramE) == AbstractChannelKt.OFFER_SUCCESS) {
      return Unit.INSTANCE;
    }
    paramE = sendSuspend(paramE, paramContinuation);
    if (paramE == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramE;
    }
    return Unit.INSTANCE;
  }
  
  protected final ReceiveOrClosed<?> sendBuffered(E paramE)
  {
    LockFreeLinkedListHead localLockFreeLinkedListHead = this.queue;
    paramE = (LockFreeLinkedListNode)new SendBuffered(paramE);
    Object localObject;
    do
    {
      localObject = localLockFreeLinkedListHead.getPrev();
      if (localObject == null) {
        break;
      }
      localObject = (LockFreeLinkedListNode)localObject;
      if ((localObject instanceof ReceiveOrClosed)) {
        return (ReceiveOrClosed)localObject;
      }
    } while (!((LockFreeLinkedListNode)localObject).addNext(paramE, localLockFreeLinkedListHead));
    return null;
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  public final Object sendFair$kotlinx_coroutines_core(E paramE, Continuation<? super Unit> paramContinuation)
  {
    if (offerInternal(paramE) == AbstractChannelKt.OFFER_SUCCESS)
    {
      paramE = YieldKt.yield(paramContinuation);
      if (paramE == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramE;
      }
      return Unit.INSTANCE;
    }
    paramE = sendSuspend(paramE, paramContinuation);
    if (paramE == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
      return paramE;
    }
    return Unit.INSTANCE;
  }
  
  protected ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed()
  {
    LockFreeLinkedListHead localLockFreeLinkedListHead = this.queue;
    for (;;)
    {
      Object localObject = localLockFreeLinkedListHead.getNext();
      if (localObject == null) {
        break;
      }
      localObject = (LockFreeLinkedListNode)localObject;
      if (localObject == (LockFreeLinkedListNode)localLockFreeLinkedListHead) {}
      while (!(localObject instanceof ReceiveOrClosed))
      {
        localObject = null;
        break;
      }
      if ((((ReceiveOrClosed)localObject instanceof Closed)) || (((LockFreeLinkedListNode)localObject).remove())) {
        return (ReceiveOrClosed)localObject;
      }
      ((LockFreeLinkedListNode)localObject).helpDelete();
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  protected final Send takeFirstSendOrPeekClosed()
  {
    LockFreeLinkedListHead localLockFreeLinkedListHead = this.queue;
    for (;;)
    {
      Object localObject = localLockFreeLinkedListHead.getNext();
      if (localObject == null) {
        break;
      }
      localObject = (LockFreeLinkedListNode)localObject;
      if (localObject == (LockFreeLinkedListNode)localLockFreeLinkedListHead) {}
      while (!(localObject instanceof Send))
      {
        localObject = null;
        break;
      }
      if ((((Send)localObject instanceof Closed)) || (((LockFreeLinkedListNode)localObject).remove())) {
        return (Send)localObject;
      }
      ((LockFreeLinkedListNode)localObject).helpDelete();
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(DebugStringsKt.getClassSimpleName(this));
    localStringBuilder.append('@');
    localStringBuilder.append(DebugStringsKt.getHexAddress(this));
    localStringBuilder.append('{');
    localStringBuilder.append(getQueueDebugStateString());
    localStringBuilder.append('}');
    localStringBuilder.append(getBufferDebugString());
    return localStringBuilder.toString();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\000\n\002\b\003\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\000\030\000*\006\b\001\020\001 \0012\0020\002B\r\022\006\020\003\032\0028\001?\006\002\020\004J\b\020\n\032\0020\013H\026J\024\020\f\032\0020\0132\n\020\r\032\006\022\002\b\0030\016H\026J\b\020\017\032\0020\020H\026J\024\020\021\032\004\030\0010\0222\b\020\023\032\004\030\0010\024H\026R\022\020\003\032\0028\0018\006X?\004?\006\004\n\002\020\005R\026\020\006\032\004\030\0010\0078VX?\004?\006\006\032\004\b\b\020\t?\002\004\n\002\b\031?\006\025"}, d2={"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "E", "Lkotlinx/coroutines/channels/Send;", "element", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "pollResult", "", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static final class SendBuffered<E>
    extends Send
  {
    public final E element;
    
    public SendBuffered(E paramE)
    {
      this.element = paramE;
    }
    
    public void completeResumeSend() {}
    
    public Object getPollResult()
    {
      return this.element;
    }
    
    public void resumeSendClosed(Closed<?> paramClosed)
    {
      Intrinsics.checkParameterIsNotNull(paramClosed, "closed");
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("SendBuffered@");
      localStringBuilder.append(DebugStringsKt.getHexAddress(this));
      localStringBuilder.append('(');
      localStringBuilder.append(this.element);
      localStringBuilder.append(')');
      return localStringBuilder.toString();
    }
    
    public Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      Symbol localSymbol = CancellableContinuationImplKt.RESUME_TOKEN;
      if (paramPrepareOp != null) {
        paramPrepareOp.finishPrepare();
      }
      return localSymbol;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\000\n\000\n\002\030\002\n\000\b\022\030\000*\004\b\001\020\0012\036\022\n\022\b\022\004\022\002H\0010\0030\002j\016\022\n\022\b\022\004\022\002H\0010\003`\004B\025\022\006\020\005\032\0020\006\022\006\020\007\032\0028\001?\006\002\020\bJ\022\020\t\032\004\030\0010\n2\006\020\013\032\0020\fH\024?\006\r"}, d2={"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBufferedDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "Lkotlinx/coroutines/internal/AddLastDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "element", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;Ljava/lang/Object;)V", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static class SendBufferedDesc<E>
    extends LockFreeLinkedListNode.AddLastDesc<AbstractSendChannel.SendBuffered<? extends E>>
  {
    public SendBufferedDesc(LockFreeLinkedListHead paramLockFreeLinkedListHead, E paramE)
    {
      super((LockFreeLinkedListNode)new AbstractSendChannel.SendBuffered(paramE));
    }
    
    protected Object failure(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "affected");
      if (!(paramLockFreeLinkedListNode instanceof Closed)) {
        if ((paramLockFreeLinkedListNode instanceof ReceiveOrClosed)) {
          paramLockFreeLinkedListNode = AbstractChannelKt.OFFER_FAILED;
        } else {
          paramLockFreeLinkedListNode = null;
        }
      }
      return paramLockFreeLinkedListNode;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\002\b\002\030\000*\004\b\001\020\0012\b\022\004\022\002H\0010\002B\025\022\006\020\003\032\0020\004\022\006\020\005\032\0028\001?\006\002\020\006J\030\020\007\032\0020\b2\006\020\t\032\0020\n2\006\020\013\032\0020\nH\024?\006\f"}, d2={"Lkotlinx/coroutines/channels/AbstractSendChannel$SendConflatedDesc;", "E", "Lkotlinx/coroutines/channels/AbstractSendChannel$SendBufferedDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "element", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;Ljava/lang/Object;)V", "finishOnSuccess", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "next", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class SendConflatedDesc<E>
    extends AbstractSendChannel.SendBufferedDesc<E>
  {
    public SendConflatedDesc(LockFreeLinkedListHead paramLockFreeLinkedListHead, E paramE)
    {
      super(paramE);
    }
    
    protected void finishOnSuccess(LockFreeLinkedListNode paramLockFreeLinkedListNode1, LockFreeLinkedListNode paramLockFreeLinkedListNode2)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode1, "affected");
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode2, "next");
      super.finishOnSuccess(paramLockFreeLinkedListNode1, paramLockFreeLinkedListNode2);
      paramLockFreeLinkedListNode2 = paramLockFreeLinkedListNode1;
      if (!(paramLockFreeLinkedListNode1 instanceof AbstractSendChannel.SendBuffered)) {
        paramLockFreeLinkedListNode2 = null;
      }
      paramLockFreeLinkedListNode1 = (AbstractSendChannel.SendBuffered)paramLockFreeLinkedListNode2;
      if (paramLockFreeLinkedListNode1 != null) {
        paramLockFreeLinkedListNode1.remove();
      }
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000T\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\b\002\030\000*\004\b\001\020\001*\004\b\002\020\0022\0020\0032\0020\004BX\022\b\020\005\032\004\030\0010\006\022\f\020\007\032\b\022\004\022\0028\0010\b\022\f\020\t\032\b\022\004\022\0028\0020\n\022(\020\013\032$\b\001\022\n\022\b\022\004\022\0028\0010\r\022\n\022\b\022\004\022\0028\0020\016\022\006\022\004\030\0010\0060\f?\001\000?\006\002\020\017J\b\020\023\032\0020\024H\026J\b\020\025\032\0020\024H\026J\024\020\026\032\0020\0242\n\020\027\032\006\022\002\b\0030\030H\026J\b\020\031\032\0020\032H\026J\024\020\033\032\004\030\0010\0342\b\020\035\032\004\030\0010\036H\026R7\020\013\032$\b\001\022\n\022\b\022\004\022\0028\0010\r\022\n\022\b\022\004\022\0028\0020\016\022\006\022\004\030\0010\0060\f8\006X?\004?\001\000?\006\004\n\002\020\020R\026\020\007\032\b\022\004\022\0028\0010\b8\006X?\004?\006\002\n\000R\026\020\005\032\004\030\0010\006X?\004?\006\b\n\000\032\004\b\021\020\022R\026\020\t\032\b\022\004\022\0028\0020\n8\006X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\037"}, d2={"Lkotlinx/coroutines/channels/AbstractSendChannel$SendSelect;", "E", "R", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/DisposableHandle;", "pollResult", "", "channel", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/AbstractSendChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "", "dispose", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class SendSelect<E, R>
    extends Send
    implements DisposableHandle
  {
    public final Function2<SendChannel<? super E>, Continuation<? super R>, Object> block;
    public final AbstractSendChannel<E> channel;
    private final Object pollResult;
    public final SelectInstance<R> select;
    
    public SendSelect(Object paramObject, AbstractSendChannel<E> paramAbstractSendChannel, SelectInstance<? super R> paramSelectInstance, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> paramFunction2)
    {
      this.pollResult = paramObject;
      this.channel = paramAbstractSendChannel;
      this.select = paramSelectInstance;
      this.block = paramFunction2;
    }
    
    public void completeResumeSend()
    {
      ContinuationKt.startCoroutine(this.block, this.channel, this.select.getCompletion());
    }
    
    public void dispose()
    {
      remove();
    }
    
    public Object getPollResult()
    {
      return this.pollResult;
    }
    
    public void resumeSendClosed(Closed<?> paramClosed)
    {
      Intrinsics.checkParameterIsNotNull(paramClosed, "closed");
      if (this.select.trySelect()) {
        this.select.resumeSelectWithException(paramClosed.getSendException());
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("SendSelect@");
      localStringBuilder.append(DebugStringsKt.getHexAddress(this));
      localStringBuilder.append('(');
      localStringBuilder.append(getPollResult());
      localStringBuilder.append(")[");
      localStringBuilder.append(this.channel);
      localStringBuilder.append(", ");
      localStringBuilder.append(this.select);
      localStringBuilder.append(']');
      return localStringBuilder.toString();
    }
    
    public Symbol tryResumeSend(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      return (Symbol)this.select.trySelectOther(paramPrepareOp);
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0006\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\b\004\030\000*\004\b\001\020\0012\036\022\n\022\b\022\004\022\002H\0010\0030\002j\016\022\n\022\b\022\004\022\002H\0010\003`\004B\025\022\006\020\005\032\0028\001\022\006\020\006\032\0020\007?\006\002\020\bJ\022\020\n\032\004\030\0010\0132\006\020\f\032\0020\rH\024J\026\020\016\032\004\030\0010\0132\n\020\017\032\0060\020j\002`\021H\026R\022\020\005\032\0028\0018\006X?\004?\006\004\n\002\020\t?\006\022"}, d2={"Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", "element", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", "Ljava/lang/Object;", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  protected static final class TryOfferDesc<E>
    extends LockFreeLinkedListNode.RemoveFirstDesc<ReceiveOrClosed<? super E>>
  {
    public final E element;
    
    public TryOfferDesc(E paramE, LockFreeLinkedListHead paramLockFreeLinkedListHead)
    {
      super();
      this.element = paramE;
    }
    
    protected Object failure(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "affected");
      if (!(paramLockFreeLinkedListNode instanceof Closed)) {
        if (!(paramLockFreeLinkedListNode instanceof ReceiveOrClosed)) {
          paramLockFreeLinkedListNode = AbstractChannelKt.OFFER_FAILED;
        } else {
          paramLockFreeLinkedListNode = null;
        }
      }
      return paramLockFreeLinkedListNode;
    }
    
    public Object onPrepare(LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      Intrinsics.checkParameterIsNotNull(paramPrepareOp, "prepareOp");
      LockFreeLinkedListNode localLockFreeLinkedListNode = paramPrepareOp.affected;
      if (localLockFreeLinkedListNode != null)
      {
        paramPrepareOp = ((ReceiveOrClosed)localLockFreeLinkedListNode).tryResumeReceive(this.element, paramPrepareOp);
        if (paramPrepareOp != null)
        {
          if (paramPrepareOp == AtomicKt.RETRY_ATOMIC) {
            return AtomicKt.RETRY_ATOMIC;
          }
          if (DebugKt.getASSERTIONS_ENABLED())
          {
            int i;
            if (paramPrepareOp == CancellableContinuationImplKt.RESUME_TOKEN) {
              i = 1;
            } else {
              i = 0;
            }
            if (i == 0) {
              throw ((Throwable)new AssertionError());
            }
          }
          return null;
        }
        return LockFreeLinkedList_commonKt.REMOVE_PREPARED;
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ReceiveOrClosed<E>");
    }
  }
}
