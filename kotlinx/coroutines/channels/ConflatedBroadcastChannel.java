package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(bv={1, 0, 3}, d1={"\000t\n\002\030\002\n\002\b\005\n\002\020\021\n\002\030\002\n\002\b\004\n\002\020\003\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\002\030\002\n\002\020\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\020\000\n\002\b\t\n\002\030\002\n\002\b\r\n\002\030\002\b\007\030\000 A*\004\b\000\020\0012\b\022\004\022\0028\0000F:\004BACDB\021\b\026\022\006\020\002\032\0028\000?\006\004\b\003\020\004B\007?\006\004\b\003\020\005J?\020\n\032\016\022\n\022\b\022\004\022\0028\0000\0070\0062\024\020\b\032\020\022\n\022\b\022\004\022\0028\0000\007\030\0010\0062\f\020\t\032\b\022\004\022\0028\0000\007H\002?\006\004\b\n\020\013J\031\020\017\032\0020\0162\b\020\r\032\004\030\0010\fH\027?\006\004\b\017\020\020J\037\020\017\032\0020\0232\016\020\r\032\n\030\0010\021j\004\030\001`\022H\026?\006\004\b\017\020\024J\031\020\025\032\0020\0162\b\020\r\032\004\030\0010\fH\026?\006\004\b\025\020\020J\035\020\026\032\0020\0232\f\020\t\032\b\022\004\022\0028\0000\007H\002?\006\004\b\026\020\027J)\020\033\032\0020\0232\030\020\032\032\024\022\006\022\004\030\0010\f\022\004\022\0020\0230\030j\002`\031H\026?\006\004\b\033\020\034J\031\020\035\032\0020\0232\b\020\r\032\004\030\0010\fH\002?\006\004\b\035\020\036J\027\020 \032\0020\0162\006\020\037\032\0028\000H\026?\006\004\b \020!J\031\020#\032\004\030\0010\"2\006\020\037\032\0028\000H\002?\006\004\b#\020$J\025\020&\032\b\022\004\022\0028\0000%H\026?\006\004\b&\020'JX\0200\032\0020\023\"\004\b\001\020(2\f\020*\032\b\022\004\022\0028\0010)2\006\020\037\032\0028\0002(\020/\032$\b\001\022\n\022\b\022\004\022\0028\0000,\022\n\022\b\022\004\022\0028\0010-\022\006\022\004\030\0010.0+H\002?\001\000?\006\004\b0\0201J?\0202\032\020\022\n\022\b\022\004\022\0028\0000\007\030\0010\0062\022\020\b\032\016\022\n\022\b\022\004\022\0028\0000\0070\0062\f\020\t\032\b\022\004\022\0028\0000\007H\002?\006\004\b2\020\013J\033\0203\032\0020\0232\006\020\037\032\0028\000H?@?\001\000?\006\004\b3\0204R\026\0205\032\0020\0168V@\026X?\004?\006\006\032\004\b5\0206R\026\0207\032\0020\0168V@\026X?\004?\006\006\032\004\b7\0206R(\020;\032\024\022\004\022\0028\000\022\n\022\b\022\004\022\0028\0000,088V@\026X?\004?\006\006\032\004\b9\020:R\031\020\002\032\0028\0008F@\006?\006\f\022\004\b>\020\005\032\004\b<\020=R\025\020@\032\004\030\0018\0008F@\006?\006\006\032\004\b?\020=?\002\004\n\002\b\031?\006E"}, d2={"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "E", "value", "<init>", "(Ljava/lang/Object;)V", "()V", "", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "list", "subscriber", "addSubscriber", "([Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "", "(Ljava/util/concurrent/CancellationException;)V", "close", "closeSubscriber", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)V", "Lkotlin/Function1;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "(Lkotlin/jvm/functions/Function1;)V", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "element", "offer", "(Ljava/lang/Object;)Z", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "offerInternal", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "openSubscription", "()Lkotlinx/coroutines/channels/ReceiveChannel;", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "removeSubscriber", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isClosedForSend", "()Z", "isFull", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "getValue", "()Ljava/lang/Object;", "value$annotations", "getValueOrNull", "valueOrNull", "Companion", "Closed", "State", "Subscriber", "kotlinx-coroutines-core", "Lkotlinx/coroutines/channels/BroadcastChannel;"}, k=1, mv={1, 1, 16})
public final class ConflatedBroadcastChannel<E>
  implements BroadcastChannel<E>
{
  private static final Closed CLOSED;
  private static final Companion Companion = new Companion(null);
  private static final State<Object> INITIAL_STATE;
  private static final Symbol UNDEFINED;
  private static final AtomicReferenceFieldUpdater _state$FU = AtomicReferenceFieldUpdater.newUpdater(ConflatedBroadcastChannel.class, Object.class, "_state");
  private static final AtomicIntegerFieldUpdater _updating$FU = AtomicIntegerFieldUpdater.newUpdater(ConflatedBroadcastChannel.class, "_updating");
  private static final AtomicReferenceFieldUpdater onCloseHandler$FU = AtomicReferenceFieldUpdater.newUpdater(ConflatedBroadcastChannel.class, Object.class, "onCloseHandler");
  private volatile Object _state = INITIAL_STATE;
  private volatile int _updating = 0;
  private volatile Object onCloseHandler = null;
  
  static
  {
    CLOSED = new Closed(null);
    Symbol localSymbol = new Symbol("UNDEFINED");
    UNDEFINED = localSymbol;
    INITIAL_STATE = new State(localSymbol, null);
  }
  
  public ConflatedBroadcastChannel() {}
  
  public ConflatedBroadcastChannel(E paramE)
  {
    this();
    _state$FU.lazySet(this, new State(paramE, null));
  }
  
  private final Subscriber<E>[] addSubscriber(Subscriber<E>[] paramArrayOfSubscriber, Subscriber<E> paramSubscriber)
  {
    if (paramArrayOfSubscriber == null)
    {
      paramArrayOfSubscriber = new Subscriber[1];
      for (int i = 0; i < 1; i++) {
        paramArrayOfSubscriber[i] = paramSubscriber;
      }
      return paramArrayOfSubscriber;
    }
    return (Subscriber[])ArraysKt.plus(paramArrayOfSubscriber, paramSubscriber);
  }
  
  private final void closeSubscriber(Subscriber<E> paramSubscriber)
  {
    Object localObject1;
    Object localObject3;
    do
    {
      localObject1 = this._state;
      if ((localObject1 instanceof Closed)) {
        return;
      }
      if (!(localObject1 instanceof State)) {
        break label88;
      }
      Object localObject2 = (State)localObject1;
      localObject3 = ((State)localObject2).value;
      if (localObject1 == null) {
        break;
      }
      localObject2 = ((State)localObject2).subscribers;
      if (localObject2 == null) {
        Intrinsics.throwNpe();
      }
      localObject3 = new State(localObject3, removeSubscriber((Subscriber[])localObject2, paramSubscriber));
    } while (!_state$FU.compareAndSet(this, localObject1, localObject3));
    return;
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ConflatedBroadcastChannel.State<E>");
    label88:
    paramSubscriber = new StringBuilder();
    paramSubscriber.append("Invalid state ");
    paramSubscriber.append(localObject1);
    throw ((Throwable)new IllegalStateException(paramSubscriber.toString().toString()));
  }
  
  private final void invokeOnCloseHandler(Throwable paramThrowable)
  {
    Object localObject = this.onCloseHandler;
    if ((localObject != null) && (localObject != AbstractChannelKt.HANDLER_INVOKED) && (onCloseHandler$FU.compareAndSet(this, localObject, AbstractChannelKt.HANDLER_INVOKED))) {
      ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity(localObject, 1)).invoke(paramThrowable);
    }
  }
  
  private final Closed offerInternal(E paramE)
  {
    if (!_updating$FU.compareAndSet(this, 0, 1)) {
      return null;
    }
    try
    {
      State localState;
      do
      {
        localObject = this._state;
        if ((localObject instanceof Closed))
        {
          paramE = (Closed)localObject;
          return paramE;
        }
        if (!(localObject instanceof State)) {
          break label137;
        }
        localState = new kotlinx/coroutines/channels/ConflatedBroadcastChannel$State;
        if (localObject == null) {
          break;
        }
        localState.<init>(paramE, ((State)localObject).subscribers);
      } while (!_state$FU.compareAndSet(this, localObject, localState));
      Object localObject = ((State)localObject).subscribers;
      if (localObject != null)
      {
        int i = localObject.length;
        for (int j = 0; j < i; j++) {
          localObject[j].offerInternal(paramE);
        }
      }
      return null;
      paramE = new kotlin/TypeCastException;
      paramE.<init>("null cannot be cast to non-null type kotlinx.coroutines.channels.ConflatedBroadcastChannel.State<E>");
      throw paramE;
      label137:
      paramE = new java/lang/StringBuilder;
      paramE.<init>();
      paramE.append("Invalid state ");
      paramE.append(localObject);
      paramE = paramE.toString();
      localObject = new java/lang/IllegalStateException;
      ((IllegalStateException)localObject).<init>(paramE.toString());
      throw ((Throwable)localObject);
    }
    finally
    {
      this._updating = 0;
    }
  }
  
  private final <R> void registerSelectSend(SelectInstance<? super R> paramSelectInstance, E paramE, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    if (!paramSelectInstance.trySelect()) {
      return;
    }
    paramE = offerInternal(paramE);
    if (paramE != null)
    {
      paramSelectInstance.resumeSelectWithException(paramE.getSendException());
      return;
    }
    UndispatchedKt.startCoroutineUnintercepted(paramFunction2, this, paramSelectInstance.getCompletion());
  }
  
  private final Subscriber<E>[] removeSubscriber(Subscriber<E>[] paramArrayOfSubscriber, Subscriber<E> paramSubscriber)
  {
    int i = paramArrayOfSubscriber.length;
    int j = ArraysKt.indexOf(paramArrayOfSubscriber, paramSubscriber);
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      int k;
      if (j >= 0) {
        k = 1;
      } else {
        k = 0;
      }
      if (k == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    if (i == 1) {
      return null;
    }
    paramSubscriber = new Subscriber[i - 1];
    ArraysKt.copyInto$default(paramArrayOfSubscriber, paramSubscriber, 0, 0, j, 6, null);
    ArraysKt.copyInto$default(paramArrayOfSubscriber, paramSubscriber, j, j + 1, 0, 8, null);
    return paramSubscriber;
  }
  
  public void cancel(CancellationException paramCancellationException)
  {
    close((Throwable)paramCancellationException);
  }
  
  public boolean close(Throwable paramThrowable)
  {
    Object localObject1;
    int i;
    Object localObject2;
    do
    {
      localObject1 = this._state;
      boolean bool = localObject1 instanceof Closed;
      i = 0;
      if (bool) {
        return false;
      }
      if (!(localObject1 instanceof State)) {
        break;
      }
      if (paramThrowable == null) {
        localObject2 = CLOSED;
      } else {
        localObject2 = new Closed(paramThrowable);
      }
    } while (!_state$FU.compareAndSet(this, localObject1, localObject2));
    if (localObject1 != null)
    {
      localObject2 = ((State)localObject1).subscribers;
      if (localObject2 != null)
      {
        int j = localObject2.length;
        while (i < j)
        {
          localObject2[i].close(paramThrowable);
          i++;
        }
      }
      invokeOnCloseHandler(paramThrowable);
      return true;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ConflatedBroadcastChannel.State<E>");
    paramThrowable = new StringBuilder();
    paramThrowable.append("Invalid state ");
    paramThrowable.append(localObject1);
    throw ((Throwable)new IllegalStateException(paramThrowable.toString().toString()));
  }
  
  public SelectClause2<E, SendChannel<E>> getOnSend()
  {
    (SelectClause2)new SelectClause2()
    {
      public <R> void registerSelectClause2(SelectInstance<? super R> paramAnonymousSelectInstance, E paramAnonymousE, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> paramAnonymousFunction2)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousSelectInstance, "select");
        Intrinsics.checkParameterIsNotNull(paramAnonymousFunction2, "block");
        ConflatedBroadcastChannel.access$registerSelectSend(this.this$0, paramAnonymousSelectInstance, paramAnonymousE, paramAnonymousFunction2);
      }
    };
  }
  
  public final E getValue()
  {
    Object localObject = this._state;
    if (!(localObject instanceof Closed))
    {
      if ((localObject instanceof State))
      {
        localObject = (State)localObject;
        if (((State)localObject).value != UNDEFINED) {
          return ((State)localObject).value;
        }
        throw ((Throwable)new IllegalStateException("No value"));
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Invalid state ");
      localStringBuilder.append(localObject);
      throw ((Throwable)new IllegalStateException(localStringBuilder.toString().toString()));
    }
    throw ((Closed)localObject).getValueException();
  }
  
  public final E getValueOrNull()
  {
    Object localObject1 = this._state;
    boolean bool = localObject1 instanceof Closed;
    Object localObject2 = null;
    if (!bool)
    {
      if (!(localObject1 instanceof State)) {
        break label52;
      }
      Symbol localSymbol = UNDEFINED;
      localObject1 = ((State)localObject1).value;
      if (localObject1 != localSymbol) {
        localObject2 = localObject1;
      }
    }
    return localObject2;
    label52:
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Invalid state ");
    ((StringBuilder)localObject2).append(localObject1);
    throw ((Throwable)new IllegalStateException(((StringBuilder)localObject2).toString().toString()));
  }
  
  public void invokeOnClose(Function1<? super Throwable, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "handler");
    if (!onCloseHandler$FU.compareAndSet(this, null, paramFunction1))
    {
      paramFunction1 = this.onCloseHandler;
      if (paramFunction1 == AbstractChannelKt.HANDLER_INVOKED) {
        throw ((Throwable)new IllegalStateException("Another handler was already registered and successfully invoked"));
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Another handler was already registered: ");
      ((StringBuilder)localObject).append(paramFunction1);
      throw ((Throwable)new IllegalStateException(((StringBuilder)localObject).toString()));
    }
    Object localObject = this._state;
    if (((localObject instanceof Closed)) && (onCloseHandler$FU.compareAndSet(this, paramFunction1, AbstractChannelKt.HANDLER_INVOKED))) {
      paramFunction1.invoke(((Closed)localObject).closeCause);
    }
  }
  
  public boolean isClosedForSend()
  {
    return this._state instanceof Closed;
  }
  
  public boolean isFull()
  {
    return false;
  }
  
  public boolean offer(E paramE)
  {
    paramE = offerInternal(paramE);
    if (paramE == null) {
      return true;
    }
    throw paramE.getSendException();
  }
  
  public ReceiveChannel<E> openSubscription()
  {
    Object localObject1 = new Subscriber(this);
    Object localObject2;
    Object localObject3;
    do
    {
      localObject2 = this._state;
      if ((localObject2 instanceof Closed))
      {
        ((Subscriber)localObject1).close(((Closed)localObject2).closeCause);
        return (ReceiveChannel)localObject1;
      }
      if (!(localObject2 instanceof State)) {
        break label127;
      }
      State localState = (State)localObject2;
      if (localState.value != UNDEFINED) {
        ((Subscriber)localObject1).offerInternal(localState.value);
      }
      localObject3 = localState.value;
      if (localObject2 == null) {
        break;
      }
      localObject3 = new State(localObject3, addSubscriber(localState.subscribers, (Subscriber)localObject1));
    } while (!_state$FU.compareAndSet(this, localObject2, localObject3));
    return (ReceiveChannel)localObject1;
    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.ConflatedBroadcastChannel.State<E>");
    label127:
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Invalid state ");
    ((StringBuilder)localObject1).append(localObject2);
    throw ((Throwable)new IllegalStateException(((StringBuilder)localObject1).toString().toString()));
  }
  
  public Object send(E paramE, Continuation<? super Unit> paramContinuation)
  {
    paramE = offerInternal(paramE);
    if (paramE == null)
    {
      if (paramE == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
        return paramE;
      }
      return Unit.INSTANCE;
    }
    throw paramE.getSendException();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\000\n\002\020\003\n\002\b\007\b\002\030\0002\0020\001B\017\022\b\020\002\032\004\030\0010\003?\006\002\020\004R\022\020\002\032\004\030\0010\0038\006X?\004?\006\002\n\000R\021\020\005\032\0020\0038F?\006\006\032\004\b\006\020\007R\021\020\b\032\0020\0038F?\006\006\032\004\b\t\020\007?\006\n"}, d2={"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "", "closeCause", "", "(Ljava/lang/Throwable;)V", "sendException", "getSendException", "()Ljava/lang/Throwable;", "valueException", "getValueException", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class Closed
  {
    public final Throwable closeCause;
    
    public Closed(Throwable paramThrowable)
    {
      this.closeCause = paramThrowable;
    }
    
    public final Throwable getSendException()
    {
      Throwable localThrowable = this.closeCause;
      if (localThrowable == null) {
        localThrowable = (Throwable)new ClosedSendChannelException("Channel was closed");
      }
      return localThrowable;
    }
    
    public final Throwable getValueException()
    {
      Throwable localThrowable = this.closeCause;
      if (localThrowable == null) {
        localThrowable = (Throwable)new IllegalStateException("Channel was closed");
      }
      return localThrowable;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\026\020\003\032\0020\0048\002X?\004?\006\b\n\000\022\004\b\005\020\002R\026\020\006\032\n\022\006\022\004\030\0010\0010\007X?\004?\006\002\n\000R\026\020\b\032\0020\t8\002X?\004?\006\b\n\000\022\004\b\n\020\002?\006\013"}, d2={"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Companion;", "", "()V", "CLOSED", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Closed;", "CLOSED$annotations", "INITIAL_STATE", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$State;", "UNDEFINED", "Lkotlinx/coroutines/internal/Symbol;", "UNDEFINED$annotations", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class Companion
  {
    private Companion() {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\021\n\002\030\002\n\002\b\003\b\002\030\000*\004\b\001\020\0012\0020\002B%\022\b\020\003\032\004\030\0010\002\022\024\020\004\032\020\022\n\022\b\022\004\022\0028\0010\006\030\0010\005?\006\002\020\007R \020\004\032\020\022\n\022\b\022\004\022\0028\0010\006\030\0010\0058\006X?\004?\006\004\n\002\020\bR\022\020\003\032\004\030\0010\0028\006X?\004?\006\002\n\000?\006\t"}, d2={"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$State;", "E", "", "value", "subscribers", "", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "(Ljava/lang/Object;[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;)V", "[Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class State<E>
  {
    public final ConflatedBroadcastChannel.Subscriber<E>[] subscribers;
    public final Object value;
    
    public State(Object paramObject, ConflatedBroadcastChannel.Subscriber<E>[] paramArrayOfSubscriber)
    {
      this.value = paramObject;
      this.subscribers = paramArrayOfSubscriber;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\003\n\002\020\002\n\000\n\002\020\013\n\000\b\002\030\000*\004\b\001\020\0012\b\022\004\022\002H\0010\0022\b\022\004\022\002H\0010\003B\023\022\f\020\004\032\b\022\004\022\0028\0010\005?\006\002\020\006J\025\020\007\032\0020\b2\006\020\t\032\0028\001H\026?\006\002\020\nJ\020\020\013\032\0020\f2\006\020\r\032\0020\016H\024R\024\020\004\032\b\022\004\022\0028\0010\005X?\004?\006\002\n\000?\006\017"}, d2={"Lkotlinx/coroutines/channels/ConflatedBroadcastChannel$Subscriber;", "E", "Lkotlinx/coroutines/channels/ConflatedChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "broadcastChannel", "Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;", "(Lkotlinx/coroutines/channels/ConflatedBroadcastChannel;)V", "offerInternal", "", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "onCancelIdempotent", "", "wasClosed", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private static final class Subscriber<E>
    extends ConflatedChannel<E>
    implements ReceiveChannel<E>
  {
    private final ConflatedBroadcastChannel<E> broadcastChannel;
    
    public Subscriber(ConflatedBroadcastChannel<E> paramConflatedBroadcastChannel)
    {
      this.broadcastChannel = paramConflatedBroadcastChannel;
    }
    
    public Object offerInternal(E paramE)
    {
      return super.offerInternal(paramE);
    }
    
    protected void onCancelIdempotent(boolean paramBoolean)
    {
      if (paramBoolean) {
        ConflatedBroadcastChannel.access$closeSubscriber(this.broadcastChannel, this);
      }
    }
  }
}
