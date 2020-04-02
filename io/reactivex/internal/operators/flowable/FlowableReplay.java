package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamPublisher;
import io.reactivex.internal.subscribers.SubscriberResourceWrapper;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableReplay<T>
  extends ConnectableFlowable<T>
  implements HasUpstreamPublisher<T>, ResettableConnectable
{
  static final Callable DEFAULT_UNBOUNDED_FACTORY = new DefaultUnboundedFactory();
  final Callable<? extends ReplayBuffer<T>> bufferFactory;
  final AtomicReference<ReplaySubscriber<T>> current;
  final Publisher<T> onSubscribe;
  final Flowable<T> source;
  
  private FlowableReplay(Publisher<T> paramPublisher, Flowable<T> paramFlowable, AtomicReference<ReplaySubscriber<T>> paramAtomicReference, Callable<? extends ReplayBuffer<T>> paramCallable)
  {
    this.onSubscribe = paramPublisher;
    this.source = paramFlowable;
    this.current = paramAtomicReference;
    this.bufferFactory = paramCallable;
  }
  
  public static <T> ConnectableFlowable<T> create(Flowable<T> paramFlowable, int paramInt)
  {
    if (paramInt == Integer.MAX_VALUE) {
      return createFrom(paramFlowable);
    }
    return create(paramFlowable, new ReplayBufferTask(paramInt));
  }
  
  public static <T> ConnectableFlowable<T> create(Flowable<T> paramFlowable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return create(paramFlowable, paramLong, paramTimeUnit, paramScheduler, Integer.MAX_VALUE);
  }
  
  public static <T> ConnectableFlowable<T> create(Flowable<T> paramFlowable, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt)
  {
    return create(paramFlowable, new ScheduledReplayBufferTask(paramInt, paramLong, paramTimeUnit, paramScheduler));
  }
  
  static <T> ConnectableFlowable<T> create(Flowable<T> paramFlowable, Callable<? extends ReplayBuffer<T>> paramCallable)
  {
    AtomicReference localAtomicReference = new AtomicReference();
    return RxJavaPlugins.onAssembly(new FlowableReplay(new ReplayPublisher(localAtomicReference, paramCallable), paramFlowable, localAtomicReference, paramCallable));
  }
  
  public static <T> ConnectableFlowable<T> createFrom(Flowable<? extends T> paramFlowable)
  {
    return create(paramFlowable, DEFAULT_UNBOUNDED_FACTORY);
  }
  
  public static <U, R> Flowable<R> multicastSelector(Callable<? extends ConnectableFlowable<U>> paramCallable, Function<? super Flowable<U>, ? extends Publisher<R>> paramFunction)
  {
    return new MulticastFlowable(paramCallable, paramFunction);
  }
  
  public static <T> ConnectableFlowable<T> observeOn(ConnectableFlowable<T> paramConnectableFlowable, Scheduler paramScheduler)
  {
    return RxJavaPlugins.onAssembly(new ConnectableFlowableReplay(paramConnectableFlowable, paramConnectableFlowable.observeOn(paramScheduler)));
  }
  
  /* Error */
  public void connect(Consumer<? super Disposable> paramConsumer)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 83	io/reactivex/internal/operators/flowable/FlowableReplay:current	Ljava/util/concurrent/atomic/AtomicReference;
    //   4: invokevirtual 147	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
    //   7: checkcast 41	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber
    //   10: astore_2
    //   11: aload_2
    //   12: ifnull +12 -> 24
    //   15: aload_2
    //   16: astore_3
    //   17: aload_2
    //   18: invokevirtual 151	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber:isDisposed	()Z
    //   21: ifeq +40 -> 61
    //   24: aload_0
    //   25: getfield 85	io/reactivex/internal/operators/flowable/FlowableReplay:bufferFactory	Ljava/util/concurrent/Callable;
    //   28: invokeinterface 156 1 0
    //   33: checkcast 32	io/reactivex/internal/operators/flowable/FlowableReplay$ReplayBuffer
    //   36: astore_3
    //   37: new 41	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber
    //   40: dup
    //   41: aload_3
    //   42: invokespecial 159	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber:<init>	(Lio/reactivex/internal/operators/flowable/FlowableReplay$ReplayBuffer;)V
    //   45: astore_3
    //   46: aload_0
    //   47: getfield 83	io/reactivex/internal/operators/flowable/FlowableReplay:current	Ljava/util/concurrent/atomic/AtomicReference;
    //   50: aload_2
    //   51: aload_3
    //   52: invokevirtual 163	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
    //   55: ifne +6 -> 61
    //   58: goto -58 -> 0
    //   61: aload_3
    //   62: getfield 167	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber:shouldConnect	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   65: invokevirtual 171	java/util/concurrent/atomic/AtomicBoolean:get	()Z
    //   68: ifne +21 -> 89
    //   71: aload_3
    //   72: getfield 167	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber:shouldConnect	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   75: iconst_0
    //   76: iconst_1
    //   77: invokevirtual 174	java/util/concurrent/atomic/AtomicBoolean:compareAndSet	(ZZ)Z
    //   80: ifeq +9 -> 89
    //   83: iconst_1
    //   84: istore 4
    //   86: goto +6 -> 92
    //   89: iconst_0
    //   90: istore 4
    //   92: aload_1
    //   93: aload_3
    //   94: invokeinterface 180 2 0
    //   99: iload 4
    //   101: ifeq +11 -> 112
    //   104: aload_0
    //   105: getfield 81	io/reactivex/internal/operators/flowable/FlowableReplay:source	Lio/reactivex/Flowable;
    //   108: aload_3
    //   109: invokevirtual 186	io/reactivex/Flowable:subscribe	(Lio/reactivex/FlowableSubscriber;)V
    //   112: return
    //   113: astore_1
    //   114: iload 4
    //   116: ifeq +13 -> 129
    //   119: aload_3
    //   120: getfield 167	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber:shouldConnect	Ljava/util/concurrent/atomic/AtomicBoolean;
    //   123: iconst_1
    //   124: iconst_0
    //   125: invokevirtual 174	java/util/concurrent/atomic/AtomicBoolean:compareAndSet	(ZZ)Z
    //   128: pop
    //   129: aload_1
    //   130: invokestatic 192	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   133: aload_1
    //   134: invokestatic 198	io/reactivex/internal/util/ExceptionHelper:wrapOrThrow	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
    //   137: athrow
    //   138: astore_1
    //   139: aload_1
    //   140: invokestatic 192	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   143: aload_1
    //   144: invokestatic 198	io/reactivex/internal/util/ExceptionHelper:wrapOrThrow	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
    //   147: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	148	0	this	FlowableReplay
    //   0	148	1	paramConsumer	Consumer<? super Disposable>
    //   10	41	2	localReplaySubscriber	ReplaySubscriber
    //   16	104	3	localObject	Object
    //   84	31	4	i	int
    // Exception table:
    //   from	to	target	type
    //   92	99	113	finally
    //   24	37	138	finally
  }
  
  public void resetIf(Disposable paramDisposable)
  {
    this.current.compareAndSet((ReplaySubscriber)paramDisposable, null);
  }
  
  public Publisher<T> source()
  {
    return this.source;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.onSubscribe.subscribe(paramSubscriber);
  }
  
  static class BoundedReplayBuffer<T>
    extends AtomicReference<FlowableReplay.Node>
    implements FlowableReplay.ReplayBuffer<T>
  {
    private static final long serialVersionUID = 2346567790059478686L;
    long index;
    int size;
    FlowableReplay.Node tail;
    
    BoundedReplayBuffer()
    {
      FlowableReplay.Node localNode = new FlowableReplay.Node(null, 0L);
      this.tail = localNode;
      set(localNode);
    }
    
    final void addLast(FlowableReplay.Node paramNode)
    {
      this.tail.set(paramNode);
      this.tail = paramNode;
      this.size += 1;
    }
    
    final void collect(Collection<? super T> paramCollection)
    {
      FlowableReplay.Node localNode = getHead();
      for (;;)
      {
        localNode = (FlowableReplay.Node)localNode.get();
        if (localNode == null) {
          break;
        }
        Object localObject = leaveTransform(localNode.value);
        if ((NotificationLite.isComplete(localObject)) || (NotificationLite.isError(localObject))) {
          break;
        }
        paramCollection.add(NotificationLite.getValue(localObject));
      }
    }
    
    public final void complete()
    {
      Object localObject = enterTransform(NotificationLite.complete());
      long l = this.index + 1L;
      this.index = l;
      addLast(new FlowableReplay.Node(localObject, l));
      truncateFinal();
    }
    
    Object enterTransform(Object paramObject)
    {
      return paramObject;
    }
    
    public final void error(Throwable paramThrowable)
    {
      paramThrowable = enterTransform(NotificationLite.error(paramThrowable));
      long l = this.index + 1L;
      this.index = l;
      addLast(new FlowableReplay.Node(paramThrowable, l));
      truncateFinal();
    }
    
    FlowableReplay.Node getHead()
    {
      return (FlowableReplay.Node)get();
    }
    
    boolean hasCompleted()
    {
      boolean bool;
      if ((this.tail.value != null) && (NotificationLite.isComplete(leaveTransform(this.tail.value)))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean hasError()
    {
      boolean bool;
      if ((this.tail.value != null) && (NotificationLite.isError(leaveTransform(this.tail.value)))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    Object leaveTransform(Object paramObject)
    {
      return paramObject;
    }
    
    public final void next(T paramT)
    {
      paramT = enterTransform(NotificationLite.next(paramT));
      long l = this.index + 1L;
      this.index = l;
      addLast(new FlowableReplay.Node(paramT, l));
      truncate();
    }
    
    final void removeFirst()
    {
      FlowableReplay.Node localNode = (FlowableReplay.Node)((FlowableReplay.Node)get()).get();
      if (localNode != null)
      {
        this.size -= 1;
        setFirst(localNode);
        return;
      }
      throw new IllegalStateException("Empty list!");
    }
    
    final void removeSome(int paramInt)
    {
      FlowableReplay.Node localNode = (FlowableReplay.Node)get();
      while (paramInt > 0)
      {
        localNode = (FlowableReplay.Node)localNode.get();
        paramInt--;
        this.size -= 1;
      }
      setFirst(localNode);
    }
    
    /* Error */
    public final void replay(FlowableReplay.InnerSubscription<T> paramInnerSubscription)
    {
      // Byte code:
      //   0: aload_1
      //   1: monitorenter
      //   2: aload_1
      //   3: getfield 127	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:emitting	Z
      //   6: ifeq +11 -> 17
      //   9: aload_1
      //   10: iconst_1
      //   11: putfield 130	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:missed	Z
      //   14: aload_1
      //   15: monitorexit
      //   16: return
      //   17: aload_1
      //   18: iconst_1
      //   19: putfield 127	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:emitting	Z
      //   22: aload_1
      //   23: monitorexit
      //   24: aload_1
      //   25: invokevirtual 133	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:isDisposed	()Z
      //   28: ifeq +9 -> 37
      //   31: aload_1
      //   32: aconst_null
      //   33: putfield 135	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:index	Ljava/lang/Object;
      //   36: return
      //   37: aload_1
      //   38: invokevirtual 138	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:get	()J
      //   41: lstore_2
      //   42: lload_2
      //   43: ldc2_w 139
      //   46: lcmp
      //   47: ifne +9 -> 56
      //   50: iconst_1
      //   51: istore 4
      //   53: goto +6 -> 59
      //   56: iconst_0
      //   57: istore 4
      //   59: aload_1
      //   60: invokevirtual 142	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:index	()Ljava/lang/Object;
      //   63: checkcast 25	io/reactivex/internal/operators/flowable/FlowableReplay$Node
      //   66: astore 5
      //   68: aload 5
      //   70: astore 6
      //   72: aload 5
      //   74: ifnonnull +28 -> 102
      //   77: aload_0
      //   78: invokevirtual 46	io/reactivex/internal/operators/flowable/FlowableReplay$BoundedReplayBuffer:getHead	()Lio/reactivex/internal/operators/flowable/FlowableReplay$Node;
      //   81: astore 6
      //   83: aload_1
      //   84: aload 6
      //   86: putfield 135	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:index	Ljava/lang/Object;
      //   89: aload_1
      //   90: getfield 146	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:totalRequested	Ljava/util/concurrent/atomic/AtomicLong;
      //   93: aload 6
      //   95: getfield 147	io/reactivex/internal/operators/flowable/FlowableReplay$Node:index	J
      //   98: invokestatic 152	io/reactivex/internal/util/BackpressureHelper:add	(Ljava/util/concurrent/atomic/AtomicLong;J)J
      //   101: pop2
      //   102: lconst_0
      //   103: lstore 7
      //   105: lload_2
      //   106: lconst_0
      //   107: lcmp
      //   108: ifeq +121 -> 229
      //   111: aload 6
      //   113: invokevirtual 50	io/reactivex/internal/operators/flowable/FlowableReplay$Node:get	()Ljava/lang/Object;
      //   116: checkcast 25	io/reactivex/internal/operators/flowable/FlowableReplay$Node
      //   119: astore 5
      //   121: aload 5
      //   123: ifnull +106 -> 229
      //   126: aload_0
      //   127: aload 5
      //   129: getfield 54	io/reactivex/internal/operators/flowable/FlowableReplay$Node:value	Ljava/lang/Object;
      //   132: invokevirtual 58	io/reactivex/internal/operators/flowable/FlowableReplay$BoundedReplayBuffer:leaveTransform	(Ljava/lang/Object;)Ljava/lang/Object;
      //   135: astore 6
      //   137: aload 6
      //   139: aload_1
      //   140: getfield 156	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:child	Lorg/reactivestreams/Subscriber;
      //   143: invokestatic 160	io/reactivex/internal/util/NotificationLite:accept	(Ljava/lang/Object;Lorg/reactivestreams/Subscriber;)Z
      //   146: ifeq +9 -> 155
      //   149: aload_1
      //   150: aconst_null
      //   151: putfield 135	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:index	Ljava/lang/Object;
      //   154: return
      //   155: lload 7
      //   157: lconst_1
      //   158: ladd
      //   159: lstore 7
      //   161: lload_2
      //   162: lconst_1
      //   163: lsub
      //   164: lstore_2
      //   165: aload_1
      //   166: invokevirtual 133	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:isDisposed	()Z
      //   169: ifeq +9 -> 178
      //   172: aload_1
      //   173: aconst_null
      //   174: putfield 135	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:index	Ljava/lang/Object;
      //   177: return
      //   178: aload 5
      //   180: astore 6
      //   182: goto -77 -> 105
      //   185: astore 5
      //   187: aload 5
      //   189: invokestatic 165	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   192: aload_1
      //   193: aconst_null
      //   194: putfield 135	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:index	Ljava/lang/Object;
      //   197: aload_1
      //   198: invokevirtual 168	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:dispose	()V
      //   201: aload 6
      //   203: invokestatic 67	io/reactivex/internal/util/NotificationLite:isError	(Ljava/lang/Object;)Z
      //   206: ifne +22 -> 228
      //   209: aload 6
      //   211: invokestatic 64	io/reactivex/internal/util/NotificationLite:isComplete	(Ljava/lang/Object;)Z
      //   214: ifne +14 -> 228
      //   217: aload_1
      //   218: getfield 156	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:child	Lorg/reactivestreams/Subscriber;
      //   221: aload 5
      //   223: invokeinterface 173 2 0
      //   228: return
      //   229: lload 7
      //   231: lconst_0
      //   232: lcmp
      //   233: ifeq +21 -> 254
      //   236: aload_1
      //   237: aload 6
      //   239: putfield 135	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:index	Ljava/lang/Object;
      //   242: iload 4
      //   244: ifne +10 -> 254
      //   247: aload_1
      //   248: lload 7
      //   250: invokevirtual 177	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:produced	(J)J
      //   253: pop2
      //   254: aload_1
      //   255: monitorenter
      //   256: aload_1
      //   257: getfield 130	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:missed	Z
      //   260: ifne +11 -> 271
      //   263: aload_1
      //   264: iconst_0
      //   265: putfield 127	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:emitting	Z
      //   268: aload_1
      //   269: monitorexit
      //   270: return
      //   271: aload_1
      //   272: iconst_0
      //   273: putfield 130	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:missed	Z
      //   276: aload_1
      //   277: monitorexit
      //   278: goto -254 -> 24
      //   281: astore 6
      //   283: aload_1
      //   284: monitorexit
      //   285: aload 6
      //   287: athrow
      //   288: astore 6
      //   290: aload_1
      //   291: monitorexit
      //   292: aload 6
      //   294: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	295	0	this	BoundedReplayBuffer
      //   0	295	1	paramInnerSubscription	FlowableReplay.InnerSubscription<T>
      //   41	124	2	l1	long
      //   51	192	4	i	int
      //   66	113	5	localNode	FlowableReplay.Node
      //   185	37	5	localThrowable	Throwable
      //   70	168	6	localObject1	Object
      //   281	5	6	localObject2	Object
      //   288	5	6	localObject3	Object
      //   103	146	7	l2	long
      // Exception table:
      //   from	to	target	type
      //   137	154	185	finally
      //   256	270	281	finally
      //   271	278	281	finally
      //   283	285	281	finally
      //   2	16	288	finally
      //   17	24	288	finally
      //   290	292	288	finally
    }
    
    final void setFirst(FlowableReplay.Node paramNode)
    {
      set(paramNode);
    }
    
    final void trimHead()
    {
      FlowableReplay.Node localNode1 = (FlowableReplay.Node)get();
      if (localNode1.value != null)
      {
        FlowableReplay.Node localNode2 = new FlowableReplay.Node(null, 0L);
        localNode2.lazySet(localNode1.get());
        set(localNode2);
      }
    }
    
    void truncate() {}
    
    void truncateFinal()
    {
      trimHead();
    }
  }
  
  static final class ConnectableFlowableReplay<T>
    extends ConnectableFlowable<T>
  {
    private final ConnectableFlowable<T> cf;
    private final Flowable<T> flowable;
    
    ConnectableFlowableReplay(ConnectableFlowable<T> paramConnectableFlowable, Flowable<T> paramFlowable)
    {
      this.cf = paramConnectableFlowable;
      this.flowable = paramFlowable;
    }
    
    public void connect(Consumer<? super Disposable> paramConsumer)
    {
      this.cf.connect(paramConsumer);
    }
    
    protected void subscribeActual(Subscriber<? super T> paramSubscriber)
    {
      this.flowable.subscribe(paramSubscriber);
    }
  }
  
  static final class DefaultUnboundedFactory
    implements Callable<Object>
  {
    DefaultUnboundedFactory() {}
    
    public Object call()
    {
      return new FlowableReplay.UnboundedReplayBuffer(16);
    }
  }
  
  static final class InnerSubscription<T>
    extends AtomicLong
    implements Subscription, Disposable
  {
    static final long CANCELLED = Long.MIN_VALUE;
    private static final long serialVersionUID = -4453897557930727610L;
    final Subscriber<? super T> child;
    boolean emitting;
    Object index;
    boolean missed;
    final FlowableReplay.ReplaySubscriber<T> parent;
    final AtomicLong totalRequested;
    
    InnerSubscription(FlowableReplay.ReplaySubscriber<T> paramReplaySubscriber, Subscriber<? super T> paramSubscriber)
    {
      this.parent = paramReplaySubscriber;
      this.child = paramSubscriber;
      this.totalRequested = new AtomicLong();
    }
    
    public void cancel()
    {
      dispose();
    }
    
    public void dispose()
    {
      if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE)
      {
        this.parent.remove(this);
        this.parent.manageRequests();
        this.index = null;
      }
    }
    
    <U> U index()
    {
      return this.index;
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (get() == Long.MIN_VALUE) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public long produced(long paramLong)
    {
      return BackpressureHelper.producedCancel(this, paramLong);
    }
    
    public void request(long paramLong)
    {
      if ((SubscriptionHelper.validate(paramLong)) && (BackpressureHelper.addCancel(this, paramLong) != Long.MIN_VALUE))
      {
        BackpressureHelper.add(this.totalRequested, paramLong);
        this.parent.manageRequests();
        this.parent.buffer.replay(this);
      }
    }
  }
  
  static final class MulticastFlowable<R, U>
    extends Flowable<R>
  {
    private final Callable<? extends ConnectableFlowable<U>> connectableFactory;
    private final Function<? super Flowable<U>, ? extends Publisher<R>> selector;
    
    MulticastFlowable(Callable<? extends ConnectableFlowable<U>> paramCallable, Function<? super Flowable<U>, ? extends Publisher<R>> paramFunction)
    {
      this.connectableFactory = paramCallable;
      this.selector = paramFunction;
    }
    
    protected void subscribeActual(Subscriber<? super R> paramSubscriber)
    {
      try
      {
        ConnectableFlowable localConnectableFlowable = (ConnectableFlowable)ObjectHelper.requireNonNull(this.connectableFactory.call(), "The connectableFactory returned null");
        try
        {
          Publisher localPublisher = (Publisher)ObjectHelper.requireNonNull(this.selector.apply(localConnectableFlowable), "The selector returned a null Publisher");
          paramSubscriber = new SubscriberResourceWrapper(paramSubscriber);
          localPublisher.subscribe(paramSubscriber);
          localConnectableFlowable.connect(new DisposableConsumer(paramSubscriber));
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable2);
        EmptySubscription.error(localThrowable2, paramSubscriber);
      }
    }
    
    final class DisposableConsumer
      implements Consumer<Disposable>
    {
      private final SubscriberResourceWrapper<R> srw;
      
      DisposableConsumer()
      {
        Object localObject;
        this.srw = localObject;
      }
      
      public void accept(Disposable paramDisposable)
      {
        this.srw.setResource(paramDisposable);
      }
    }
  }
  
  static final class Node
    extends AtomicReference<Node>
  {
    private static final long serialVersionUID = 245354315435971818L;
    final long index;
    final Object value;
    
    Node(Object paramObject, long paramLong)
    {
      this.value = paramObject;
      this.index = paramLong;
    }
  }
  
  static abstract interface ReplayBuffer<T>
  {
    public abstract void complete();
    
    public abstract void error(Throwable paramThrowable);
    
    public abstract void next(T paramT);
    
    public abstract void replay(FlowableReplay.InnerSubscription<T> paramInnerSubscription);
  }
  
  static final class ReplayBufferTask<T>
    implements Callable<FlowableReplay.ReplayBuffer<T>>
  {
    private final int bufferSize;
    
    ReplayBufferTask(int paramInt)
    {
      this.bufferSize = paramInt;
    }
    
    public FlowableReplay.ReplayBuffer<T> call()
    {
      return new FlowableReplay.SizeBoundReplayBuffer(this.bufferSize);
    }
  }
  
  static final class ReplayPublisher<T>
    implements Publisher<T>
  {
    private final Callable<? extends FlowableReplay.ReplayBuffer<T>> bufferFactory;
    private final AtomicReference<FlowableReplay.ReplaySubscriber<T>> curr;
    
    ReplayPublisher(AtomicReference<FlowableReplay.ReplaySubscriber<T>> paramAtomicReference, Callable<? extends FlowableReplay.ReplayBuffer<T>> paramCallable)
    {
      this.curr = paramAtomicReference;
      this.bufferFactory = paramCallable;
    }
    
    /* Error */
    public void subscribe(Subscriber<? super T> paramSubscriber)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 23	io/reactivex/internal/operators/flowable/FlowableReplay$ReplayPublisher:curr	Ljava/util/concurrent/atomic/AtomicReference;
      //   4: invokevirtual 36	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   7: checkcast 38	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber
      //   10: astore_2
      //   11: aload_2
      //   12: astore_3
      //   13: aload_2
      //   14: ifnonnull +54 -> 68
      //   17: aload_0
      //   18: getfield 25	io/reactivex/internal/operators/flowable/FlowableReplay$ReplayPublisher:bufferFactory	Ljava/util/concurrent/Callable;
      //   21: invokeinterface 43 1 0
      //   26: checkcast 45	io/reactivex/internal/operators/flowable/FlowableReplay$ReplayBuffer
      //   29: astore_3
      //   30: new 38	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber
      //   33: dup
      //   34: aload_3
      //   35: invokespecial 48	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber:<init>	(Lio/reactivex/internal/operators/flowable/FlowableReplay$ReplayBuffer;)V
      //   38: astore_3
      //   39: aload_0
      //   40: getfield 23	io/reactivex/internal/operators/flowable/FlowableReplay$ReplayPublisher:curr	Ljava/util/concurrent/atomic/AtomicReference;
      //   43: aconst_null
      //   44: aload_3
      //   45: invokevirtual 52	java/util/concurrent/atomic/AtomicReference:compareAndSet	(Ljava/lang/Object;Ljava/lang/Object;)Z
      //   48: ifne +6 -> 54
      //   51: goto -51 -> 0
      //   54: goto +14 -> 68
      //   57: astore_3
      //   58: aload_3
      //   59: invokestatic 58	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   62: aload_3
      //   63: aload_1
      //   64: invokestatic 64	io/reactivex/internal/subscriptions/EmptySubscription:error	(Ljava/lang/Throwable;Lorg/reactivestreams/Subscriber;)V
      //   67: return
      //   68: new 66	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription
      //   71: dup
      //   72: aload_3
      //   73: aload_1
      //   74: invokespecial 69	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:<init>	(Lio/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber;Lorg/reactivestreams/Subscriber;)V
      //   77: astore_2
      //   78: aload_1
      //   79: aload_2
      //   80: invokeinterface 75 2 0
      //   85: aload_3
      //   86: aload_2
      //   87: invokevirtual 79	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber:add	(Lio/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription;)Z
      //   90: pop
      //   91: aload_2
      //   92: invokevirtual 83	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:isDisposed	()Z
      //   95: ifeq +9 -> 104
      //   98: aload_3
      //   99: aload_2
      //   100: invokevirtual 87	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber:remove	(Lio/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription;)V
      //   103: return
      //   104: aload_3
      //   105: invokevirtual 90	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber:manageRequests	()V
      //   108: aload_3
      //   109: getfield 94	io/reactivex/internal/operators/flowable/FlowableReplay$ReplaySubscriber:buffer	Lio/reactivex/internal/operators/flowable/FlowableReplay$ReplayBuffer;
      //   112: aload_2
      //   113: invokeinterface 97 2 0
      //   118: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	119	0	this	ReplayPublisher
      //   0	119	1	paramSubscriber	Subscriber<? super T>
      //   10	103	2	localObject1	Object
      //   12	33	3	localObject2	Object
      //   57	52	3	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   17	30	57	finally
    }
  }
  
  static final class ReplaySubscriber<T>
    extends AtomicReference<Subscription>
    implements FlowableSubscriber<T>, Disposable
  {
    static final FlowableReplay.InnerSubscription[] EMPTY = new FlowableReplay.InnerSubscription[0];
    static final FlowableReplay.InnerSubscription[] TERMINATED = new FlowableReplay.InnerSubscription[0];
    private static final long serialVersionUID = 7224554242710036740L;
    final FlowableReplay.ReplayBuffer<T> buffer;
    boolean done;
    final AtomicInteger management;
    long maxChildRequested;
    long maxUpstreamRequested;
    final AtomicBoolean shouldConnect;
    final AtomicReference<FlowableReplay.InnerSubscription<T>[]> subscribers;
    
    ReplaySubscriber(FlowableReplay.ReplayBuffer<T> paramReplayBuffer)
    {
      this.buffer = paramReplayBuffer;
      this.management = new AtomicInteger();
      this.subscribers = new AtomicReference(EMPTY);
      this.shouldConnect = new AtomicBoolean();
    }
    
    boolean add(FlowableReplay.InnerSubscription<T> paramInnerSubscription)
    {
      if (paramInnerSubscription != null)
      {
        FlowableReplay.InnerSubscription[] arrayOfInnerSubscription1;
        FlowableReplay.InnerSubscription[] arrayOfInnerSubscription2;
        do
        {
          arrayOfInnerSubscription1 = (FlowableReplay.InnerSubscription[])this.subscribers.get();
          if (arrayOfInnerSubscription1 == TERMINATED) {
            return false;
          }
          int i = arrayOfInnerSubscription1.length;
          arrayOfInnerSubscription2 = new FlowableReplay.InnerSubscription[i + 1];
          System.arraycopy(arrayOfInnerSubscription1, 0, arrayOfInnerSubscription2, 0, i);
          arrayOfInnerSubscription2[i] = paramInnerSubscription;
        } while (!this.subscribers.compareAndSet(arrayOfInnerSubscription1, arrayOfInnerSubscription2));
        return true;
      }
      throw null;
    }
    
    public void dispose()
    {
      this.subscribers.set(TERMINATED);
      SubscriptionHelper.cancel(this);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.subscribers.get() == TERMINATED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void manageRequests()
    {
      if (this.management.getAndIncrement() != 0) {
        return;
      }
      int i = 1;
      int k;
      do
      {
        if (isDisposed()) {
          return;
        }
        Object localObject = (FlowableReplay.InnerSubscription[])this.subscribers.get();
        long l1 = this.maxChildRequested;
        int j = localObject.length;
        k = 0;
        long l2 = l1;
        while (k < j)
        {
          l2 = Math.max(l2, localObject[k].totalRequested.get());
          k++;
        }
        long l3 = this.maxUpstreamRequested;
        localObject = (Subscription)get();
        l1 = l2 - l1;
        if (l1 != 0L)
        {
          this.maxChildRequested = l2;
          if (localObject != null)
          {
            if (l3 != 0L)
            {
              this.maxUpstreamRequested = 0L;
              ((Subscription)localObject).request(l3 + l1);
            }
            else
            {
              ((Subscription)localObject).request(l1);
            }
          }
          else
          {
            l1 = l3 + l1;
            l2 = l1;
            if (l1 < 0L) {
              l2 = Long.MAX_VALUE;
            }
            this.maxUpstreamRequested = l2;
          }
        }
        else if ((l3 != 0L) && (localObject != null))
        {
          this.maxUpstreamRequested = 0L;
          ((Subscription)localObject).request(l3);
        }
        k = this.management.addAndGet(-i);
        i = k;
      } while (k != 0);
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        this.buffer.complete();
        for (FlowableReplay.InnerSubscription localInnerSubscription : (FlowableReplay.InnerSubscription[])this.subscribers.getAndSet(TERMINATED)) {
          this.buffer.replay(localInnerSubscription);
        }
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!this.done)
      {
        this.done = true;
        this.buffer.error(paramThrowable);
        for (paramThrowable : (FlowableReplay.InnerSubscription[])this.subscribers.getAndSet(TERMINATED)) {
          this.buffer.replay(paramThrowable);
        }
      }
      RxJavaPlugins.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (!this.done)
      {
        this.buffer.next(paramT);
        for (paramT : (FlowableReplay.InnerSubscription[])this.subscribers.get()) {
          this.buffer.replay(paramT);
        }
      }
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.setOnce(this, paramSubscription))
      {
        manageRequests();
        for (paramSubscription : (FlowableReplay.InnerSubscription[])this.subscribers.get()) {
          this.buffer.replay(paramSubscription);
        }
      }
    }
    
    void remove(FlowableReplay.InnerSubscription<T> paramInnerSubscription)
    {
      FlowableReplay.InnerSubscription[] arrayOfInnerSubscription1;
      FlowableReplay.InnerSubscription[] arrayOfInnerSubscription2;
      do
      {
        arrayOfInnerSubscription1 = (FlowableReplay.InnerSubscription[])this.subscribers.get();
        int i = arrayOfInnerSubscription1.length;
        if (i == 0) {
          return;
        }
        int j = -1;
        int m;
        for (int k = 0;; k++)
        {
          m = j;
          if (k >= i) {
            break;
          }
          if (arrayOfInnerSubscription1[k].equals(paramInnerSubscription))
          {
            m = k;
            break;
          }
        }
        if (m < 0) {
          return;
        }
        if (i == 1)
        {
          arrayOfInnerSubscription2 = EMPTY;
        }
        else
        {
          arrayOfInnerSubscription2 = new FlowableReplay.InnerSubscription[i - 1];
          System.arraycopy(arrayOfInnerSubscription1, 0, arrayOfInnerSubscription2, 0, m);
          System.arraycopy(arrayOfInnerSubscription1, m + 1, arrayOfInnerSubscription2, m, i - m - 1);
        }
      } while (!this.subscribers.compareAndSet(arrayOfInnerSubscription1, arrayOfInnerSubscription2));
    }
  }
  
  static final class ScheduledReplayBufferTask<T>
    implements Callable<FlowableReplay.ReplayBuffer<T>>
  {
    private final int bufferSize;
    private final long maxAge;
    private final Scheduler scheduler;
    private final TimeUnit unit;
    
    ScheduledReplayBufferTask(int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      this.bufferSize = paramInt;
      this.maxAge = paramLong;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
    }
    
    public FlowableReplay.ReplayBuffer<T> call()
    {
      return new FlowableReplay.SizeAndTimeBoundReplayBuffer(this.bufferSize, this.maxAge, this.unit, this.scheduler);
    }
  }
  
  static final class SizeAndTimeBoundReplayBuffer<T>
    extends FlowableReplay.BoundedReplayBuffer<T>
  {
    private static final long serialVersionUID = 3457957419649567404L;
    final int limit;
    final long maxAge;
    final Scheduler scheduler;
    final TimeUnit unit;
    
    SizeAndTimeBoundReplayBuffer(int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      this.scheduler = paramScheduler;
      this.limit = paramInt;
      this.maxAge = paramLong;
      this.unit = paramTimeUnit;
    }
    
    Object enterTransform(Object paramObject)
    {
      return new Timed(paramObject, this.scheduler.now(this.unit), this.unit);
    }
    
    FlowableReplay.Node getHead()
    {
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      Object localObject1 = (FlowableReplay.Node)get();
      Object localObject3;
      for (Object localObject2 = (FlowableReplay.Node)((FlowableReplay.Node)localObject1).get(); localObject2 != null; localObject2 = localObject3)
      {
        localObject3 = (Timed)((FlowableReplay.Node)localObject2).value;
        if ((NotificationLite.isComplete(((Timed)localObject3).value())) || (NotificationLite.isError(((Timed)localObject3).value())) || (((Timed)localObject3).time() > l1 - l2)) {
          break;
        }
        localObject3 = (FlowableReplay.Node)((FlowableReplay.Node)localObject2).get();
        localObject1 = localObject2;
      }
      return localObject1;
    }
    
    Object leaveTransform(Object paramObject)
    {
      return ((Timed)paramObject).value();
    }
    
    void truncate()
    {
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      Object localObject1 = (FlowableReplay.Node)get();
      Object localObject2 = (FlowableReplay.Node)((FlowableReplay.Node)localObject1).get();
      int i = 0;
      while (localObject2 != null)
      {
        FlowableReplay.Node localNode;
        if (this.size > this.limit)
        {
          i++;
          this.size -= 1;
          localNode = (FlowableReplay.Node)((FlowableReplay.Node)localObject2).get();
          localObject1 = localObject2;
          localObject2 = localNode;
        }
        else
        {
          if (((Timed)((FlowableReplay.Node)localObject2).value).time() > l1 - l2) {
            break;
          }
          i++;
          this.size -= 1;
          localNode = (FlowableReplay.Node)((FlowableReplay.Node)localObject2).get();
          localObject1 = localObject2;
          localObject2 = localNode;
        }
      }
      if (i != 0) {
        setFirst((FlowableReplay.Node)localObject1);
      }
    }
    
    void truncateFinal()
    {
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      Object localObject1 = (FlowableReplay.Node)get();
      Object localObject2 = (FlowableReplay.Node)((FlowableReplay.Node)localObject1).get();
      int i = 0;
      while ((localObject2 != null) && (this.size > 1) && (((Timed)((FlowableReplay.Node)localObject2).value).time() <= l1 - l2))
      {
        i++;
        this.size -= 1;
        FlowableReplay.Node localNode = (FlowableReplay.Node)((FlowableReplay.Node)localObject2).get();
        localObject1 = localObject2;
        localObject2 = localNode;
      }
      if (i != 0) {
        setFirst((FlowableReplay.Node)localObject1);
      }
    }
  }
  
  static final class SizeBoundReplayBuffer<T>
    extends FlowableReplay.BoundedReplayBuffer<T>
  {
    private static final long serialVersionUID = -5898283885385201806L;
    final int limit;
    
    SizeBoundReplayBuffer(int paramInt)
    {
      this.limit = paramInt;
    }
    
    void truncate()
    {
      if (this.size > this.limit) {
        removeFirst();
      }
    }
  }
  
  static final class UnboundedReplayBuffer<T>
    extends ArrayList<Object>
    implements FlowableReplay.ReplayBuffer<T>
  {
    private static final long serialVersionUID = 7063189396499112664L;
    volatile int size;
    
    UnboundedReplayBuffer(int paramInt)
    {
      super();
    }
    
    public void complete()
    {
      add(NotificationLite.complete());
      this.size += 1;
    }
    
    public void error(Throwable paramThrowable)
    {
      add(NotificationLite.error(paramThrowable));
      this.size += 1;
    }
    
    public void next(T paramT)
    {
      add(NotificationLite.next(paramT));
      this.size += 1;
    }
    
    /* Error */
    public void replay(FlowableReplay.InnerSubscription<T> paramInnerSubscription)
    {
      // Byte code:
      //   0: aload_1
      //   1: monitorenter
      //   2: aload_1
      //   3: getfield 54	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:emitting	Z
      //   6: ifeq +11 -> 17
      //   9: aload_1
      //   10: iconst_1
      //   11: putfield 57	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:missed	Z
      //   14: aload_1
      //   15: monitorexit
      //   16: return
      //   17: aload_1
      //   18: iconst_1
      //   19: putfield 54	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:emitting	Z
      //   22: aload_1
      //   23: monitorexit
      //   24: aload_1
      //   25: getfield 61	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:child	Lorg/reactivestreams/Subscriber;
      //   28: astore_2
      //   29: aload_1
      //   30: invokevirtual 65	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:isDisposed	()Z
      //   33: ifeq +4 -> 37
      //   36: return
      //   37: aload_0
      //   38: getfield 34	io/reactivex/internal/operators/flowable/FlowableReplay$UnboundedReplayBuffer:size	I
      //   41: istore_3
      //   42: aload_1
      //   43: invokevirtual 68	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:index	()Ljava/lang/Object;
      //   46: checkcast 70	java/lang/Integer
      //   49: astore 4
      //   51: aload 4
      //   53: ifnull +13 -> 66
      //   56: aload 4
      //   58: invokevirtual 74	java/lang/Integer:intValue	()I
      //   61: istore 5
      //   63: goto +6 -> 69
      //   66: iconst_0
      //   67: istore 5
      //   69: aload_1
      //   70: invokevirtual 78	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:get	()J
      //   73: lstore 6
      //   75: lload 6
      //   77: lstore 8
      //   79: lconst_0
      //   80: lstore 10
      //   82: lload 8
      //   84: lconst_0
      //   85: lcmp
      //   86: ifeq +93 -> 179
      //   89: iload 5
      //   91: iload_3
      //   92: if_icmpge +87 -> 179
      //   95: aload_0
      //   96: iload 5
      //   98: invokevirtual 81	io/reactivex/internal/operators/flowable/FlowableReplay$UnboundedReplayBuffer:get	(I)Ljava/lang/Object;
      //   101: astore 4
      //   103: aload 4
      //   105: aload_2
      //   106: invokestatic 85	io/reactivex/internal/util/NotificationLite:accept	(Ljava/lang/Object;Lorg/reactivestreams/Subscriber;)Z
      //   109: istore 12
      //   111: iload 12
      //   113: ifeq +4 -> 117
      //   116: return
      //   117: aload_1
      //   118: invokevirtual 65	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:isDisposed	()Z
      //   121: ifeq +4 -> 125
      //   124: return
      //   125: iinc 5 1
      //   128: lload 8
      //   130: lconst_1
      //   131: lsub
      //   132: lstore 8
      //   134: lload 10
      //   136: lconst_1
      //   137: ladd
      //   138: lstore 10
      //   140: goto -58 -> 82
      //   143: astore 13
      //   145: aload 13
      //   147: invokestatic 90	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   150: aload_1
      //   151: invokevirtual 93	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:dispose	()V
      //   154: aload 4
      //   156: invokestatic 96	io/reactivex/internal/util/NotificationLite:isError	(Ljava/lang/Object;)Z
      //   159: ifne +19 -> 178
      //   162: aload 4
      //   164: invokestatic 99	io/reactivex/internal/util/NotificationLite:isComplete	(Ljava/lang/Object;)Z
      //   167: ifne +11 -> 178
      //   170: aload_2
      //   171: aload 13
      //   173: invokeinterface 104 2 0
      //   178: return
      //   179: lload 10
      //   181: lconst_0
      //   182: lcmp
      //   183: ifeq +28 -> 211
      //   186: aload_1
      //   187: iload 5
      //   189: invokestatic 108	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   192: putfield 111	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:index	Ljava/lang/Object;
      //   195: lload 6
      //   197: ldc2_w 112
      //   200: lcmp
      //   201: ifeq +10 -> 211
      //   204: aload_1
      //   205: lload 10
      //   207: invokevirtual 117	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:produced	(J)J
      //   210: pop2
      //   211: aload_1
      //   212: monitorenter
      //   213: aload_1
      //   214: getfield 57	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:missed	Z
      //   217: ifne +11 -> 228
      //   220: aload_1
      //   221: iconst_0
      //   222: putfield 54	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:emitting	Z
      //   225: aload_1
      //   226: monitorexit
      //   227: return
      //   228: aload_1
      //   229: iconst_0
      //   230: putfield 57	io/reactivex/internal/operators/flowable/FlowableReplay$InnerSubscription:missed	Z
      //   233: aload_1
      //   234: monitorexit
      //   235: goto -206 -> 29
      //   238: astore_2
      //   239: aload_1
      //   240: monitorexit
      //   241: aload_2
      //   242: athrow
      //   243: astore_2
      //   244: aload_1
      //   245: monitorexit
      //   246: aload_2
      //   247: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	248	0	this	UnboundedReplayBuffer
      //   0	248	1	paramInnerSubscription	FlowableReplay.InnerSubscription<T>
      //   28	143	2	localSubscriber	Subscriber
      //   238	4	2	localObject1	Object
      //   243	4	2	localObject2	Object
      //   41	52	3	i	int
      //   49	114	4	localObject3	Object
      //   61	127	5	j	int
      //   73	123	6	l1	long
      //   77	56	8	l2	long
      //   80	126	10	l3	long
      //   109	3	12	bool	boolean
      //   143	29	13	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   103	111	143	finally
      //   213	227	238	finally
      //   228	235	238	finally
      //   239	241	238	finally
      //   2	16	243	finally
      //   17	24	243	finally
      //   244	246	243	finally
    }
  }
}
