package kotlinx.coroutines.channels;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancelHandler;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuation.DefaultImpls;
import kotlinx.coroutines.CancellableContinuationImplKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicKt;
import kotlinx.coroutines.internal.InlineList;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.CondAddOp;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.PrepareOp;
import kotlinx.coroutines.internal.LockFreeLinkedListNode.RemoveFirstDesc;
import kotlinx.coroutines.internal.LockFreeLinkedList_commonKt;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(bv={1, 0, 3}, d1={"\000?\001\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\007\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\003\n\002\020\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\030\002\n\002\b\024\n\002\030\002\n\000\n\002\030\002\n\002\b\n\b \030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003:\006JKLMNOB\005?\006\002\020\004J\022\020\026\032\0020\0062\b\020\027\032\004\030\0010\030H\007J\026\020\026\032\0020\0312\016\020\027\032\n\030\0010\032j\004\030\001`\033J\027\020\034\032\0020\0062\b\020\027\032\004\030\0010\030H\000?\006\002\b\035J\016\020\036\032\b\022\004\022\0028\0000\037H\004J\026\020 \032\0020\0062\f\020!\032\b\022\004\022\0028\0000\"H\002JR\020#\032\0020\006\"\004\b\001\020$2\f\020%\032\b\022\004\022\002H$0&2$\020'\032 \b\001\022\006\022\004\030\0010)\022\n\022\b\022\004\022\002H$0*\022\006\022\004\030\0010)0(2\006\020+\032\0020,H\002?\001\000?\006\002\020-J\017\020.\032\b\022\004\022\0028\0000/H?\002J\020\0200\032\0020\0312\006\0201\032\0020\006H\024J\b\0202\032\0020\031H\024J\b\0203\032\0020\031H\024J\r\0204\032\004\030\0018\000?\006\002\0205J\n\0206\032\004\030\0010)H\024J\026\0207\032\004\030\0010)2\n\020%\032\006\022\002\b\0030&H\024J\021\020!\032\0028\000H?@?\001\000?\006\002\0208J\032\0209\032\b\022\004\022\0028\0000\022H?@?\001\000?\001\000?\006\002\0208J\023\020:\032\004\030\0018\000H?@?\001\000?\006\002\0208J\031\020;\032\004\030\0018\0002\b\020<\032\004\030\0010)H\002?\006\002\020=J\037\020>\032\002H$\"\004\b\001\020$2\006\020+\032\0020,H?@?\001\000?\006\002\020?JR\020@\032\0020\031\"\004\b\001\020$2\f\020%\032\b\022\004\022\002H$0&2\006\020+\032\0020,2$\020'\032 \b\001\022\006\022\004\030\0010)\022\n\022\b\022\004\022\002H$0*\022\006\022\004\030\0010)0(H\002?\001\000?\006\002\020AJ \020B\032\0020\0312\n\020C\032\006\022\002\b\0030D2\n\020!\032\006\022\002\b\0030\"H\002J\020\020E\032\n\022\004\022\0028\000\030\0010FH\024JX\020G\032\0020\031\"\004\b\001\020$* \b\001\022\006\022\004\030\0010)\022\n\022\b\022\004\022\002H$0*\022\006\022\004\030\0010)0(2\f\020%\032\b\022\004\022\002H$0&2\006\020+\032\0020,2\b\020H\032\004\030\0010)H\002?\001\000?\006\002\020IR\024\020\005\032\0020\0068DX?\004?\006\006\032\004\b\007\020\bR\022\020\t\032\0020\006X¤\004?\006\006\032\004\b\t\020\bR\022\020\n\032\0020\006X¤\004?\006\006\032\004\b\n\020\bR\021\020\013\032\0020\0068F?\006\006\032\004\b\013\020\bR\021\020\f\032\0020\0068F?\006\006\032\004\b\f\020\bR\027\020\r\032\b\022\004\022\0028\0000\0168F?\006\006\032\004\b\017\020\020R \020\021\032\016\022\n\022\b\022\004\022\0028\0000\0220\0168F?\001\000?\006\006\032\004\b\023\020\020R\031\020\024\032\n\022\006\022\004\030\0018\0000\0168F?\006\006\032\004\b\025\020\020?\002\004\n\002\b\031?\006P"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel;", "E", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/Channel;", "()V", "hasReceiveOrClosed", "", "getHasReceiveOrClosed", "()Z", "isBufferAlwaysEmpty", "isBufferEmpty", "isClosedForReceive", "isEmpty", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveOrClosed", "Lkotlinx/coroutines/channels/ValueOrClosed;", "getOnReceiveOrClosed", "onReceiveOrNull", "getOnReceiveOrNull", "cancel", "cause", "", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "cancelInternal$kotlinx_coroutines_core", "describeTryPoll", "Lkotlinx/coroutines/channels/AbstractChannel$TryPollDesc;", "enqueueReceive", "receive", "Lkotlinx/coroutines/channels/Receive;", "enqueueReceiveSelect", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "", "Lkotlin/coroutines/Continuation;", "receiveMode", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;I)Z", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "onCancelIdempotent", "wasClosed", "onReceiveDequeued", "onReceiveEnqueued", "poll", "()Ljava/lang/Object;", "pollInternal", "pollSelectInternal", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveOrClosed", "receiveOrNull", "receiveOrNullResult", "result", "(Ljava/lang/Object;)Ljava/lang/Object;", "receiveSuspend", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerSelectReceiveMode", "(Lkotlinx/coroutines/selects/SelectInstance;ILkotlin/jvm/functions/Function2;)V", "removeReceiveOnCancel", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "takeFirstReceiveOrPeekClosed", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "tryStartBlockUnintercepted", "value", "(Lkotlin/jvm/functions/Function2;Lkotlinx/coroutines/selects/SelectInstance;ILjava/lang/Object;)V", "Itr", "ReceiveElement", "ReceiveHasNext", "ReceiveSelect", "RemoveReceiveOnCancel", "TryPollDesc", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract class AbstractChannel<E>
  extends AbstractSendChannel<E>
  implements Channel<E>
{
  public AbstractChannel() {}
  
  private final boolean enqueueReceive(Receive<? super E> paramReceive)
  {
    boolean bool1 = isBufferAlwaysEmpty();
    boolean bool2 = false;
    LockFreeLinkedListHead localLockFreeLinkedListHead;
    Object localObject1;
    if (bool1)
    {
      localLockFreeLinkedListHead = getQueue();
      do
      {
        localObject1 = localLockFreeLinkedListHead.getPrev();
        if (localObject1 == null) {
          break;
        }
        localObject1 = (LockFreeLinkedListNode)localObject1;
        if (!(localObject1 instanceof Send ^ true))
        {
          bool1 = bool2;
          break label171;
        }
      } while (!((LockFreeLinkedListNode)localObject1).addNext((LockFreeLinkedListNode)paramReceive, localLockFreeLinkedListHead));
      break label169;
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }
    else
    {
      localLockFreeLinkedListHead = getQueue();
      localObject1 = (LockFreeLinkedListNode)paramReceive;
      paramReceive = (LockFreeLinkedListNode.CondAddOp)new LockFreeLinkedListNode.CondAddOp((LockFreeLinkedListNode)localObject1)
      {
        public Object prepare(LockFreeLinkedListNode paramAnonymousLockFreeLinkedListNode)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousLockFreeLinkedListNode, "affected");
          if (jdField_this.isBufferEmpty()) {
            paramAnonymousLockFreeLinkedListNode = null;
          } else {
            paramAnonymousLockFreeLinkedListNode = LockFreeLinkedListKt.getCONDITION_FALSE();
          }
          return paramAnonymousLockFreeLinkedListNode;
        }
      };
      for (;;)
      {
        Object localObject2 = localLockFreeLinkedListHead.getPrev();
        if (localObject2 == null) {
          break label181;
        }
        localObject2 = (LockFreeLinkedListNode)localObject2;
        if (!(localObject2 instanceof Send ^ true))
        {
          bool1 = bool2;
          break label171;
        }
        int i = ((LockFreeLinkedListNode)localObject2).tryCondAddNext((LockFreeLinkedListNode)localObject1, localLockFreeLinkedListHead, paramReceive);
        if (i == 1) {
          break;
        }
        bool1 = bool2;
        if (i == 2) {
          break label171;
        }
      }
    }
    label169:
    bool1 = true;
    label171:
    if (bool1) {
      onReceiveEnqueued();
    }
    return bool1;
    label181:
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
  }
  
  private final <R> boolean enqueueReceiveSelect(SelectInstance<? super R> paramSelectInstance, Function2<Object, ? super Continuation<? super R>, ? extends Object> paramFunction2, int paramInt)
  {
    paramFunction2 = new ReceiveSelect(this, paramSelectInstance, paramFunction2, paramInt);
    boolean bool = enqueueReceive((Receive)paramFunction2);
    if (bool) {
      paramSelectInstance.disposeOnSelect((DisposableHandle)paramFunction2);
    }
    return bool;
  }
  
  private final E receiveOrNullResult(Object paramObject)
  {
    if ((paramObject instanceof Closed))
    {
      paramObject = (Closed)paramObject;
      if (paramObject.closeCause == null) {
        return null;
      }
      throw StackTraceRecoveryKt.recoverStackTrace(paramObject.closeCause);
    }
    return paramObject;
  }
  
  private final <R> void registerSelectReceiveMode(SelectInstance<? super R> paramSelectInstance, int paramInt, Function2<Object, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    for (;;)
    {
      if (paramSelectInstance.isSelected()) {
        return;
      }
      if (isEmpty())
      {
        if (!enqueueReceiveSelect(paramSelectInstance, paramFunction2, paramInt)) {}
      }
      else
      {
        Object localObject = pollSelectInternal(paramSelectInstance);
        if (localObject == SelectKt.getALREADY_SELECTED()) {
          return;
        }
        if ((localObject != AbstractChannelKt.POLL_FAILED) && (localObject != AtomicKt.RETRY_ATOMIC)) {
          tryStartBlockUnintercepted(paramFunction2, paramSelectInstance, paramInt, localObject);
        }
      }
    }
  }
  
  private final void removeReceiveOnCancel(CancellableContinuation<?> paramCancellableContinuation, Receive<?> paramReceive)
  {
    paramCancellableContinuation.invokeOnCancellation((Function1)new RemoveReceiveOnCancel(paramReceive));
  }
  
  private final <R> void tryStartBlockUnintercepted(Function2<Object, ? super Continuation<? super R>, ? extends Object> paramFunction2, SelectInstance<? super R> paramSelectInstance, int paramInt, Object paramObject)
  {
    boolean bool = paramObject instanceof Closed;
    ValueOrClosed.Companion localCompanion;
    if (bool)
    {
      if (paramInt != 0)
      {
        if (paramInt != 1)
        {
          if (paramInt == 2)
          {
            if (!paramSelectInstance.trySelect()) {
              return;
            }
            localCompanion = ValueOrClosed.Companion;
            UndispatchedKt.startCoroutineUnintercepted(paramFunction2, ValueOrClosed.box-impl(ValueOrClosed.constructor-impl(new ValueOrClosed.Closed(((Closed)paramObject).closeCause))), paramSelectInstance.getCompletion());
          }
        }
        else
        {
          paramObject = (Closed)paramObject;
          if (paramObject.closeCause == null)
          {
            if (!paramSelectInstance.trySelect()) {
              return;
            }
            UndispatchedKt.startCoroutineUnintercepted(paramFunction2, null, paramSelectInstance.getCompletion());
          }
          else
          {
            throw StackTraceRecoveryKt.recoverStackTrace(paramObject.getReceiveException());
          }
        }
      }
      else {
        throw StackTraceRecoveryKt.recoverStackTrace(((Closed)paramObject).getReceiveException());
      }
    }
    else if (paramInt == 2)
    {
      localCompanion = ValueOrClosed.Companion;
      if (bool) {
        paramObject = ValueOrClosed.constructor-impl(new ValueOrClosed.Closed(((Closed)paramObject).closeCause));
      } else {
        paramObject = ValueOrClosed.constructor-impl(paramObject);
      }
      UndispatchedKt.startCoroutineUnintercepted(paramFunction2, ValueOrClosed.box-impl(paramObject), paramSelectInstance.getCompletion());
    }
    else
    {
      UndispatchedKt.startCoroutineUnintercepted(paramFunction2, paramObject, paramSelectInstance.getCompletion());
    }
  }
  
  public final void cancel(CancellationException paramCancellationException)
  {
    if (paramCancellationException == null)
    {
      paramCancellationException = new StringBuilder();
      paramCancellationException.append(DebugStringsKt.getClassSimpleName(this));
      paramCancellationException.append(" was cancelled");
      paramCancellationException = new CancellationException(paramCancellationException.toString());
    }
    cancelInternal$kotlinx_coroutines_core((Throwable)paramCancellationException);
  }
  
  public final boolean cancelInternal$kotlinx_coroutines_core(Throwable paramThrowable)
  {
    boolean bool = close(paramThrowable);
    onCancelIdempotent(bool);
    return bool;
  }
  
  protected final TryPollDesc<E> describeTryPoll()
  {
    return new TryPollDesc(getQueue());
  }
  
  protected final boolean getHasReceiveOrClosed()
  {
    return getQueue().getNextNode() instanceof ReceiveOrClosed;
  }
  
  public final SelectClause1<E> getOnReceive()
  {
    (SelectClause1)new SelectClause1()
    {
      public <R> void registerSelectClause1(SelectInstance<? super R> paramAnonymousSelectInstance, Function2<? super E, ? super Continuation<? super R>, ? extends Object> paramAnonymousFunction2)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousSelectInstance, "select");
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunction2, "block");
        AbstractChannel.access$registerSelectReceiveMode(this.this$0, paramAnonymousSelectInstance, 0, paramAnonymousFunction2);
      }
    };
  }
  
  public final SelectClause1<ValueOrClosed<E>> getOnReceiveOrClosed()
  {
    (SelectClause1)new SelectClause1()
    {
      public <R> void registerSelectClause1(SelectInstance<? super R> paramAnonymousSelectInstance, Function2<? super ValueOrClosed<? extends E>, ? super Continuation<? super R>, ? extends Object> paramAnonymousFunction2)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousSelectInstance, "select");
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunction2, "block");
        AbstractChannel.access$registerSelectReceiveMode(this.this$0, paramAnonymousSelectInstance, 2, paramAnonymousFunction2);
      }
    };
  }
  
  public final SelectClause1<E> getOnReceiveOrNull()
  {
    (SelectClause1)new SelectClause1()
    {
      public <R> void registerSelectClause1(SelectInstance<? super R> paramAnonymousSelectInstance, Function2<? super E, ? super Continuation<? super R>, ? extends Object> paramAnonymousFunction2)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousSelectInstance, "select");
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunction2, "block");
        AbstractChannel.access$registerSelectReceiveMode(this.this$0, paramAnonymousSelectInstance, 1, paramAnonymousFunction2);
      }
    };
  }
  
  protected abstract boolean isBufferAlwaysEmpty();
  
  protected abstract boolean isBufferEmpty();
  
  public final boolean isClosedForReceive()
  {
    boolean bool;
    if ((getClosedForReceive() != null) && (isBufferEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final boolean isEmpty()
  {
    boolean bool;
    if ((!(getQueue().getNextNode() instanceof Send)) && (isBufferEmpty())) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final ChannelIterator<E> iterator()
  {
    return (ChannelIterator)new Itr(this);
  }
  
  protected void onCancelIdempotent(boolean paramBoolean)
  {
    Closed localClosed = getClosedForSend();
    if (localClosed != null)
    {
      Object localObject = InlineList.constructor-impl$default(null, 1, null);
      for (;;)
      {
        LockFreeLinkedListNode localLockFreeLinkedListNode = localClosed.getPrevNode();
        if ((localLockFreeLinkedListNode instanceof LockFreeLinkedListHead))
        {
          if (localObject != null) {
            if (!(localObject instanceof ArrayList))
            {
              ((Send)localObject).resumeSendClosed(localClosed);
            }
            else
            {
              if (localObject == null) {
                break label97;
              }
              localObject = (ArrayList)localObject;
              for (int i = ((ArrayList)localObject).size() - 1; i >= 0; i--) {
                ((Send)((ArrayList)localObject).get(i)).resumeSendClosed(localClosed);
              }
            }
          }
          return;
          label97:
          throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<E> /* = java.util.ArrayList<E> */");
        }
        if ((DebugKt.getASSERTIONS_ENABLED()) && (!(localLockFreeLinkedListNode instanceof Send))) {
          throw ((Throwable)new AssertionError());
        }
        if (!localLockFreeLinkedListNode.remove())
        {
          localLockFreeLinkedListNode.helpRemove();
        }
        else
        {
          if (localLockFreeLinkedListNode == null) {
            break;
          }
          localObject = InlineList.plus-impl(localObject, (Send)localLockFreeLinkedListNode);
        }
      }
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.Send");
    }
    throw ((Throwable)new IllegalStateException("Cannot happen".toString()));
  }
  
  protected void onReceiveDequeued() {}
  
  protected void onReceiveEnqueued() {}
  
  public final E poll()
  {
    Object localObject = pollInternal();
    if (localObject == AbstractChannelKt.POLL_FAILED) {
      localObject = null;
    } else {
      localObject = receiveOrNullResult(localObject);
    }
    return localObject;
  }
  
  protected Object pollInternal()
  {
    Send localSend;
    Symbol localSymbol;
    do
    {
      localSend = takeFirstSendOrPeekClosed();
      if (localSend == null) {
        break;
      }
      localSymbol = localSend.tryResumeSend(null);
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
    localSend.completeResumeSend();
    return localSend.getPollResult();
    return AbstractChannelKt.POLL_FAILED;
  }
  
  protected Object pollSelectInternal(SelectInstance<?> paramSelectInstance)
  {
    Intrinsics.checkParameterIsNotNull(paramSelectInstance, "select");
    TryPollDesc localTryPollDesc = describeTryPoll();
    paramSelectInstance = paramSelectInstance.performAtomicTrySelect((AtomicDesc)localTryPollDesc);
    if (paramSelectInstance != null) {
      return paramSelectInstance;
    }
    ((Send)localTryPollDesc.getResult()).completeResumeSend();
    return ((Send)localTryPollDesc.getResult()).getPollResult();
  }
  
  public final Object receive(Continuation<? super E> paramContinuation)
  {
    Object localObject = pollInternal();
    if ((localObject != AbstractChannelKt.POLL_FAILED) && (!(localObject instanceof Closed))) {
      return localObject;
    }
    return receiveSuspend(0, paramContinuation);
  }
  
  public final Object receiveOrClosed(Continuation<? super ValueOrClosed<? extends E>> paramContinuation)
  {
    Object localObject = pollInternal();
    if (localObject != AbstractChannelKt.POLL_FAILED)
    {
      if ((localObject instanceof Closed))
      {
        paramContinuation = ValueOrClosed.Companion;
        paramContinuation = ValueOrClosed.constructor-impl(new ValueOrClosed.Closed(((Closed)localObject).closeCause));
      }
      else
      {
        paramContinuation = ValueOrClosed.Companion;
        paramContinuation = ValueOrClosed.constructor-impl(localObject);
      }
      return ValueOrClosed.box-impl(paramContinuation);
    }
    return receiveSuspend(2, paramContinuation);
  }
  
  public final Object receiveOrNull(Continuation<? super E> paramContinuation)
  {
    Object localObject = pollInternal();
    if ((localObject != AbstractChannelKt.POLL_FAILED) && (!(localObject instanceof Closed))) {
      return localObject;
    }
    return receiveSuspend(1, paramContinuation);
  }
  
  protected ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed()
  {
    ReceiveOrClosed localReceiveOrClosed = super.takeFirstReceiveOrPeekClosed();
    if ((localReceiveOrClosed != null) && (!(localReceiveOrClosed instanceof Closed))) {
      onReceiveDequeued();
    }
    return localReceiveOrClosed;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\000\n\002\b\005\n\002\020\013\n\002\b\005\b\002\030\000*\004\b\001\020\0012\b\022\004\022\002H\0010\002B\023\022\f\020\003\032\b\022\004\022\0028\0010\004?\006\002\020\005J\021\020\016\032\0020\017H?B?\001\000?\006\002\020\020J\022\020\021\032\0020\0172\b\020\b\032\004\030\0010\tH\002J\021\020\022\032\0020\017H?@?\001\000?\006\002\020\020J\016\020\023\032\0028\001H?\002?\006\002\020\013R\027\020\003\032\b\022\004\022\0028\0010\004?\006\b\n\000\032\004\b\006\020\007R\034\020\b\032\004\030\0010\tX?\016?\006\016\n\000\032\004\b\n\020\013\"\004\b\f\020\r?\002\004\n\002\b\031?\006\024"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$Itr;", "E", "Lkotlinx/coroutines/channels/ChannelIterator;", "channel", "Lkotlinx/coroutines/channels/AbstractChannel;", "(Lkotlinx/coroutines/channels/AbstractChannel;)V", "getChannel", "()Lkotlinx/coroutines/channels/AbstractChannel;", "result", "", "getResult", "()Ljava/lang/Object;", "setResult", "(Ljava/lang/Object;)V", "hasNext", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasNextResult", "hasNextSuspend", "next", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class Itr<E>
    implements ChannelIterator<E>
  {
    private final AbstractChannel<E> channel;
    private Object result;
    
    public Itr(AbstractChannel<E> paramAbstractChannel)
    {
      this.channel = paramAbstractChannel;
      this.result = AbstractChannelKt.POLL_FAILED;
    }
    
    private final boolean hasNextResult(Object paramObject)
    {
      if ((paramObject instanceof Closed))
      {
        paramObject = (Closed)paramObject;
        if (paramObject.closeCause == null) {
          return false;
        }
        throw StackTraceRecoveryKt.recoverStackTrace(paramObject.getReceiveException());
      }
      return true;
    }
    
    public final AbstractChannel<E> getChannel()
    {
      return this.channel;
    }
    
    public final Object getResult()
    {
      return this.result;
    }
    
    public Object hasNext(Continuation<? super Boolean> paramContinuation)
    {
      if (this.result != AbstractChannelKt.POLL_FAILED) {
        return Boxing.boxBoolean(hasNextResult(this.result));
      }
      Object localObject = this.channel.pollInternal();
      this.result = localObject;
      if (localObject != AbstractChannelKt.POLL_FAILED) {
        return Boxing.boxBoolean(hasNextResult(this.result));
      }
      return hasNextSuspend(paramContinuation);
    }
    
    public E next()
    {
      Object localObject = this.result;
      if (!(localObject instanceof Closed))
      {
        if (localObject != AbstractChannelKt.POLL_FAILED)
        {
          this.result = AbstractChannelKt.POLL_FAILED;
          return localObject;
        }
        throw ((Throwable)new IllegalStateException("'hasNext' should be called prior to 'next' invocation"));
      }
      throw StackTraceRecoveryKt.recoverStackTrace(((Closed)localObject).getReceiveException());
    }
    
    public final void setResult(Object paramObject)
    {
      this.result = paramObject;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000B\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\000\n\002\020\b\n\002\b\002\n\002\020\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\002\030\000*\006\b\001\020\001 \0002\b\022\004\022\002H\0010\002B\035\022\016\020\003\032\n\022\006\022\004\030\0010\0050\004\022\006\020\006\032\0020\007?\006\002\020\bJ\025\020\t\032\0020\n2\006\020\013\032\0028\001H\026?\006\002\020\fJ\024\020\r\032\0020\n2\n\020\016\032\006\022\002\b\0030\017H\026J\025\020\020\032\004\030\0010\0052\006\020\013\032\0028\001?\006\002\020\021J\b\020\022\032\0020\023H\026J!\020\024\032\004\030\0010\0252\006\020\013\032\0028\0012\b\020\026\032\004\030\0010\027H\026?\006\002\020\030R\030\020\003\032\n\022\006\022\004\030\0010\0050\0048\006X?\004?\006\002\n\000R\020\020\006\032\0020\0078\006X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\031"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElement;", "E", "Lkotlinx/coroutines/channels/Receive;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "receiveMode", "", "(Lkotlinx/coroutines/CancellableContinuation;I)V", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "resumeValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class ReceiveElement<E>
    extends Receive<E>
  {
    public final CancellableContinuation<Object> cont;
    public final int receiveMode;
    
    public ReceiveElement(CancellableContinuation<Object> paramCancellableContinuation, int paramInt)
    {
      this.cont = paramCancellableContinuation;
      this.receiveMode = paramInt;
    }
    
    public void completeResumeReceive(E paramE)
    {
      this.cont.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
    }
    
    public void resumeReceiveClosed(Closed<?> paramClosed)
    {
      Intrinsics.checkParameterIsNotNull(paramClosed, "closed");
      Continuation localContinuation;
      if ((this.receiveMode == 1) && (paramClosed.closeCause == null))
      {
        localContinuation = (Continuation)this.cont;
        paramClosed = Result.Companion;
        localContinuation.resumeWith(Result.constructor-impl(null));
      }
      else
      {
        Object localObject;
        if (this.receiveMode == 2)
        {
          localContinuation = (Continuation)this.cont;
          localObject = ValueOrClosed.Companion;
          paramClosed = ValueOrClosed.box-impl(ValueOrClosed.constructor-impl(new ValueOrClosed.Closed(paramClosed.closeCause)));
          localObject = Result.Companion;
          localContinuation.resumeWith(Result.constructor-impl(paramClosed));
        }
        else
        {
          localContinuation = (Continuation)this.cont;
          localObject = paramClosed.getReceiveException();
          paramClosed = Result.Companion;
          localContinuation.resumeWith(Result.constructor-impl(ResultKt.createFailure((Throwable)localObject)));
        }
      }
    }
    
    public final Object resumeValue(E paramE)
    {
      if (this.receiveMode == 2)
      {
        ValueOrClosed.Companion localCompanion = ValueOrClosed.Companion;
        paramE = ValueOrClosed.box-impl(ValueOrClosed.constructor-impl(paramE));
      }
      return paramE;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("ReceiveElement@");
      localStringBuilder.append(DebugStringsKt.getHexAddress(this));
      localStringBuilder.append("[receiveMode=");
      localStringBuilder.append(this.receiveMode);
      localStringBuilder.append(']');
      return localStringBuilder.toString();
    }
    
    public Symbol tryResumeReceive(E paramE, LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      CancellableContinuation localCancellableContinuation = this.cont;
      Object localObject = resumeValue(paramE);
      if (paramPrepareOp != null) {
        paramE = paramPrepareOp.desc;
      } else {
        paramE = null;
      }
      paramE = localCancellableContinuation.tryResume(localObject, paramE);
      if (paramE != null)
      {
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          int i;
          if (paramE == CancellableContinuationImplKt.RESUME_TOKEN) {
            i = 1;
          } else {
            i = 0;
          }
          if (i == 0) {
            throw ((Throwable)new AssertionError());
          }
        }
        if (paramPrepareOp != null) {
          paramPrepareOp.finishPrepare();
        }
        return CancellableContinuationImplKt.RESUME_TOKEN;
      }
      return null;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\013\n\002\b\002\n\002\020\002\n\002\b\004\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\002\030\000*\004\b\001\020\0012\b\022\004\022\002H\0010\002B!\022\f\020\003\032\b\022\004\022\0028\0010\004\022\f\020\005\032\b\022\004\022\0020\0070\006?\006\002\020\bJ\025\020\t\032\0020\n2\006\020\013\032\0028\001H\026?\006\002\020\fJ\024\020\r\032\0020\n2\n\020\016\032\006\022\002\b\0030\017H\026J\b\020\020\032\0020\021H\026J!\020\022\032\004\030\0010\0232\006\020\013\032\0028\0012\b\020\024\032\004\030\0010\025H\026?\006\002\020\026R\026\020\005\032\b\022\004\022\0020\0070\0068\006X?\004?\006\002\n\000R\026\020\003\032\b\022\004\022\0028\0010\0048\006X?\004?\006\002\n\000?\002\004\n\002\b\031?\006\027"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveHasNext;", "E", "Lkotlinx/coroutines/channels/Receive;", "iterator", "Lkotlinx/coroutines/channels/AbstractChannel$Itr;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/channels/AbstractChannel$Itr;Lkotlinx/coroutines/CancellableContinuation;)V", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class ReceiveHasNext<E>
    extends Receive<E>
  {
    public final CancellableContinuation<Boolean> cont;
    public final AbstractChannel.Itr<E> iterator;
    
    public ReceiveHasNext(AbstractChannel.Itr<E> paramItr, CancellableContinuation<? super Boolean> paramCancellableContinuation)
    {
      this.iterator = paramItr;
      this.cont = paramCancellableContinuation;
    }
    
    public void completeResumeReceive(E paramE)
    {
      this.iterator.setResult(paramE);
      this.cont.completeResume(CancellableContinuationImplKt.RESUME_TOKEN);
    }
    
    public void resumeReceiveClosed(Closed<?> paramClosed)
    {
      Intrinsics.checkParameterIsNotNull(paramClosed, "closed");
      Object localObject;
      if (paramClosed.closeCause == null) {
        localObject = CancellableContinuation.DefaultImpls.tryResume$default(this.cont, Boolean.valueOf(false), null, 2, null);
      } else {
        localObject = this.cont.tryResumeWithException(StackTraceRecoveryKt.recoverStackTrace(paramClosed.getReceiveException(), (Continuation)this.cont));
      }
      if (localObject != null)
      {
        this.iterator.setResult(paramClosed);
        this.cont.completeResume(localObject);
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("ReceiveHasNext@");
      localStringBuilder.append(DebugStringsKt.getHexAddress(this));
      return localStringBuilder.toString();
    }
    
    public Symbol tryResumeReceive(E paramE, LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      CancellableContinuation localCancellableContinuation = this.cont;
      int i = 1;
      if (paramPrepareOp != null) {
        paramE = paramPrepareOp.desc;
      } else {
        paramE = null;
      }
      paramE = localCancellableContinuation.tryResume(Boolean.valueOf(true), paramE);
      if (paramE != null)
      {
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          if (paramE != CancellableContinuationImplKt.RESUME_TOKEN) {
            i = 0;
          }
          if (i == 0) {
            throw ((Throwable)new AssertionError());
          }
        }
        if (paramPrepareOp != null) {
          paramPrepareOp.finishPrepare();
        }
        return CancellableContinuationImplKt.RESUME_TOKEN;
      }
      return null;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000V\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\020\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\002\n\002\b\005\n\002\030\002\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\002\030\000*\004\b\001\020\001*\004\b\002\020\0022\b\022\004\022\002H\0020\0032\0020\004BR\022\f\020\005\032\b\022\004\022\0028\0020\006\022\f\020\007\032\b\022\004\022\0028\0010\b\022$\020\t\032 \b\001\022\006\022\004\030\0010\013\022\n\022\b\022\004\022\0028\0010\f\022\006\022\004\030\0010\0130\n\022\006\020\r\032\0020\016?\001\000?\006\002\020\017J\025\020\021\032\0020\0222\006\020\023\032\0028\002H\026?\006\002\020\024J\b\020\025\032\0020\022H\026J\024\020\026\032\0020\0222\n\020\027\032\006\022\002\b\0030\030H\026J\b\020\031\032\0020\032H\026J!\020\033\032\004\030\0010\0342\006\020\023\032\0028\0022\b\020\035\032\004\030\0010\036H\026?\006\002\020\037R3\020\t\032 \b\001\022\006\022\004\030\0010\013\022\n\022\b\022\004\022\0028\0010\f\022\006\022\004\030\0010\0130\n8\006X?\004?\001\000?\006\004\n\002\020\020R\026\020\005\032\b\022\004\022\0028\0020\0068\006X?\004?\006\002\n\000R\020\020\r\032\0020\0168\006X?\004?\006\002\n\000R\026\020\007\032\b\022\004\022\0028\0010\b8\006X?\004?\006\002\n\000?\002\004\n\002\b\031?\006 "}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveSelect;", "R", "E", "Lkotlinx/coroutines/channels/Receive;", "Lkotlinx/coroutines/DisposableHandle;", "channel", "Lkotlinx/coroutines/channels/AbstractChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "", "Lkotlin/coroutines/Continuation;", "receiveMode", "", "(Lkotlinx/coroutines/channels/AbstractChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;I)V", "Lkotlin/jvm/functions/Function2;", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "dispose", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class ReceiveSelect<R, E>
    extends Receive<E>
    implements DisposableHandle
  {
    public final Function2<Object, Continuation<? super R>, Object> block;
    public final AbstractChannel<E> channel;
    public final int receiveMode;
    public final SelectInstance<R> select;
    
    public ReceiveSelect(AbstractChannel<E> paramAbstractChannel, SelectInstance<? super R> paramSelectInstance, Function2<Object, ? super Continuation<? super R>, ? extends Object> paramFunction2, int paramInt)
    {
      this.channel = paramAbstractChannel;
      this.select = paramSelectInstance;
      this.block = paramFunction2;
      this.receiveMode = paramInt;
    }
    
    public void completeResumeReceive(E paramE)
    {
      Function2 localFunction2 = this.block;
      Object localObject = paramE;
      if (this.receiveMode == 2)
      {
        localObject = ValueOrClosed.Companion;
        localObject = ValueOrClosed.box-impl(ValueOrClosed.constructor-impl(paramE));
      }
      ContinuationKt.startCoroutine(localFunction2, localObject, this.select.getCompletion());
    }
    
    public void dispose()
    {
      if (remove()) {
        this.channel.onReceiveDequeued();
      }
    }
    
    public void resumeReceiveClosed(Closed<?> paramClosed)
    {
      Intrinsics.checkParameterIsNotNull(paramClosed, "closed");
      if (!this.select.trySelect()) {
        return;
      }
      int i = this.receiveMode;
      if (i != 0)
      {
        if (i != 1)
        {
          if (i == 2)
          {
            Function2 localFunction2 = this.block;
            ValueOrClosed.Companion localCompanion = ValueOrClosed.Companion;
            ContinuationKt.startCoroutine(localFunction2, ValueOrClosed.box-impl(ValueOrClosed.constructor-impl(new ValueOrClosed.Closed(paramClosed.closeCause))), this.select.getCompletion());
          }
        }
        else if (paramClosed.closeCause == null) {
          ContinuationKt.startCoroutine(this.block, null, this.select.getCompletion());
        } else {
          this.select.resumeSelectWithException(paramClosed.getReceiveException());
        }
      }
      else {
        this.select.resumeSelectWithException(paramClosed.getReceiveException());
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("ReceiveSelect@");
      localStringBuilder.append(DebugStringsKt.getHexAddress(this));
      localStringBuilder.append('[');
      localStringBuilder.append(this.select);
      localStringBuilder.append(",receiveMode=");
      localStringBuilder.append(this.receiveMode);
      localStringBuilder.append(']');
      return localStringBuilder.toString();
    }
    
    public Symbol tryResumeReceive(E paramE, LockFreeLinkedListNode.PrepareOp paramPrepareOp)
    {
      return (Symbol)this.select.trySelectOther(paramPrepareOp);
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b?\004\030\0002\0020\001B\021\022\n\020\002\032\006\022\002\b\0030\003?\006\002\020\004J\023\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH?\002J\b\020\t\032\0020\nH\026R\022\020\002\032\006\022\002\b\0030\003X?\004?\006\002\n\000?\006\013"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$RemoveReceiveOnCancel;", "Lkotlinx/coroutines/CancelHandler;", "receive", "Lkotlinx/coroutines/channels/Receive;", "(Lkotlinx/coroutines/channels/AbstractChannel;Lkotlinx/coroutines/channels/Receive;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private final class RemoveReceiveOnCancel
    extends CancelHandler
  {
    private final Receive<?> receive;
    
    public RemoveReceiveOnCancel()
    {
      this.receive = localObject;
    }
    
    public void invoke(Throwable paramThrowable)
    {
      if (this.receive.remove()) {
        AbstractChannel.this.onReceiveDequeued();
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("RemoveReceiveOnCancel[");
      localStringBuilder.append(this.receive);
      localStringBuilder.append(']');
      return localStringBuilder.toString();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\000\b\004\030\000*\004\b\001\020\0012\022\022\004\022\0020\0030\002j\b\022\004\022\0020\003`\004B\r\022\006\020\005\032\0020\006?\006\002\020\007J\022\020\b\032\004\030\0010\t2\006\020\n\032\0020\013H\024J\026\020\f\032\004\030\0010\t2\n\020\r\032\0060\016j\002`\017H\026?\006\020"}, d2={"Lkotlinx/coroutines/channels/AbstractChannel$TryPollDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onPrepare", "prepareOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "Lkotlinx/coroutines/internal/PrepareOp;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  protected static final class TryPollDesc<E>
    extends LockFreeLinkedListNode.RemoveFirstDesc<Send>
  {
    public TryPollDesc(LockFreeLinkedListHead paramLockFreeLinkedListHead)
    {
      super();
    }
    
    protected Object failure(LockFreeLinkedListNode paramLockFreeLinkedListNode)
    {
      Intrinsics.checkParameterIsNotNull(paramLockFreeLinkedListNode, "affected");
      if (!(paramLockFreeLinkedListNode instanceof Closed)) {
        if (!(paramLockFreeLinkedListNode instanceof Send)) {
          paramLockFreeLinkedListNode = AbstractChannelKt.POLL_FAILED;
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
        paramPrepareOp = ((Send)localLockFreeLinkedListNode).tryResumeSend(paramPrepareOp);
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
      throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.Send");
    }
  }
}
