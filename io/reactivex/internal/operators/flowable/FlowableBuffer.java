package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBuffer<T, C extends Collection<? super T>>
  extends AbstractFlowableWithUpstream<T, C>
{
  final Callable<C> bufferSupplier;
  final int size;
  final int skip;
  
  public FlowableBuffer(Flowable<T> paramFlowable, int paramInt1, int paramInt2, Callable<C> paramCallable)
  {
    super(paramFlowable);
    this.size = paramInt1;
    this.skip = paramInt2;
    this.bufferSupplier = paramCallable;
  }
  
  public void subscribeActual(Subscriber<? super C> paramSubscriber)
  {
    int i = this.size;
    int j = this.skip;
    if (i == j) {
      this.source.subscribe(new PublisherBufferExactSubscriber(paramSubscriber, this.size, this.bufferSupplier));
    } else if (j > i) {
      this.source.subscribe(new PublisherBufferSkipSubscriber(paramSubscriber, this.size, this.skip, this.bufferSupplier));
    } else {
      this.source.subscribe(new PublisherBufferOverlappingSubscriber(paramSubscriber, this.size, this.skip, this.bufferSupplier));
    }
  }
  
  static final class PublisherBufferExactSubscriber<T, C extends Collection<? super T>>
    implements FlowableSubscriber<T>, Subscription
  {
    C buffer;
    final Callable<C> bufferSupplier;
    boolean done;
    final Subscriber<? super C> downstream;
    int index;
    final int size;
    Subscription upstream;
    
    PublisherBufferExactSubscriber(Subscriber<? super C> paramSubscriber, int paramInt, Callable<C> paramCallable)
    {
      this.downstream = paramSubscriber;
      this.size = paramInt;
      this.bufferSupplier = paramCallable;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      Collection localCollection = this.buffer;
      if ((localCollection != null) && (!localCollection.isEmpty())) {
        this.downstream.onNext(localCollection);
      }
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 50	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 52	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:buffer	Ljava/util/Collection;
      //   12: astore_2
      //   13: aload_2
      //   14: astore_3
      //   15: aload_2
      //   16: ifnonnull +44 -> 60
      //   19: aload_0
      //   20: getfield 39	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:bufferSupplier	Ljava/util/concurrent/Callable;
      //   23: invokeinterface 79 1 0
      //   28: ldc 81
      //   30: invokestatic 87	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   33: checkcast 54	java/util/Collection
      //   36: astore_3
      //   37: aload_0
      //   38: aload_3
      //   39: putfield 52	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:buffer	Ljava/util/Collection;
      //   42: goto +18 -> 60
      //   45: astore_1
      //   46: aload_1
      //   47: invokestatic 92	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   50: aload_0
      //   51: invokevirtual 93	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:cancel	()V
      //   54: aload_0
      //   55: aload_1
      //   56: invokevirtual 94	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:onError	(Ljava/lang/Throwable;)V
      //   59: return
      //   60: aload_3
      //   61: aload_1
      //   62: invokeinterface 98 2 0
      //   67: pop
      //   68: aload_0
      //   69: getfield 100	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:index	I
      //   72: iconst_1
      //   73: iadd
      //   74: istore 4
      //   76: iload 4
      //   78: aload_0
      //   79: getfield 37	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:size	I
      //   82: if_icmpne +26 -> 108
      //   85: aload_0
      //   86: iconst_0
      //   87: putfield 100	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:index	I
      //   90: aload_0
      //   91: aconst_null
      //   92: putfield 52	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:buffer	Ljava/util/Collection;
      //   95: aload_0
      //   96: getfield 35	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   99: aload_3
      //   100: invokeinterface 64 2 0
      //   105: goto +9 -> 114
      //   108: aload_0
      //   109: iload 4
      //   111: putfield 100	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferExactSubscriber:index	I
      //   114: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	115	0	this	PublisherBufferExactSubscriber
      //   0	115	1	paramT	T
      //   12	4	2	localCollection1	Collection
      //   14	86	3	localCollection2	Collection
      //   74	36	4	i	int
      // Exception table:
      //   from	to	target	type
      //   19	37	45	finally
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        this.upstream.request(BackpressureHelper.multiplyCap(paramLong, this.size));
      }
    }
  }
  
  static final class PublisherBufferOverlappingSubscriber<T, C extends Collection<? super T>>
    extends AtomicLong
    implements FlowableSubscriber<T>, Subscription, BooleanSupplier
  {
    private static final long serialVersionUID = -7370244972039324525L;
    final Callable<C> bufferSupplier;
    final ArrayDeque<C> buffers;
    volatile boolean cancelled;
    boolean done;
    final Subscriber<? super C> downstream;
    int index;
    final AtomicBoolean once;
    long produced;
    final int size;
    final int skip;
    Subscription upstream;
    
    PublisherBufferOverlappingSubscriber(Subscriber<? super C> paramSubscriber, int paramInt1, int paramInt2, Callable<C> paramCallable)
    {
      this.downstream = paramSubscriber;
      this.size = paramInt1;
      this.skip = paramInt2;
      this.bufferSupplier = paramCallable;
      this.once = new AtomicBoolean();
      this.buffers = new ArrayDeque();
    }
    
    public void cancel()
    {
      this.cancelled = true;
      this.upstream.cancel();
    }
    
    public boolean getAsBoolean()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      long l = this.produced;
      if (l != 0L) {
        BackpressureHelper.produced(this, l);
      }
      QueueDrainHelper.postComplete(this.downstream, this.buffers, this, this);
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.buffers.clear();
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 77	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 62	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:buffers	Ljava/util/ArrayDeque;
      //   12: astore_2
      //   13: aload_0
      //   14: getfield 106	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:index	I
      //   17: istore_3
      //   18: iload_3
      //   19: iconst_1
      //   20: iadd
      //   21: istore 4
      //   23: iload_3
      //   24: ifne +47 -> 71
      //   27: aload_0
      //   28: getfield 52	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:bufferSupplier	Ljava/util/concurrent/Callable;
      //   31: invokeinterface 112 1 0
      //   36: ldc 114
      //   38: invokestatic 120	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   41: checkcast 122	java/util/Collection
      //   44: astore 5
      //   46: aload_2
      //   47: aload 5
      //   49: invokevirtual 126	java/util/ArrayDeque:offer	(Ljava/lang/Object;)Z
      //   52: pop
      //   53: goto +18 -> 71
      //   56: astore_1
      //   57: aload_1
      //   58: invokestatic 131	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   61: aload_0
      //   62: invokevirtual 132	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:cancel	()V
      //   65: aload_0
      //   66: aload_1
      //   67: invokevirtual 133	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:onError	(Ljava/lang/Throwable;)V
      //   70: return
      //   71: aload_2
      //   72: invokevirtual 136	java/util/ArrayDeque:peek	()Ljava/lang/Object;
      //   75: checkcast 122	java/util/Collection
      //   78: astore 5
      //   80: aload 5
      //   82: ifnull +54 -> 136
      //   85: aload 5
      //   87: invokeinterface 139 1 0
      //   92: iconst_1
      //   93: iadd
      //   94: aload_0
      //   95: getfield 48	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:size	I
      //   98: if_icmpne +38 -> 136
      //   101: aload_2
      //   102: invokevirtual 142	java/util/ArrayDeque:poll	()Ljava/lang/Object;
      //   105: pop
      //   106: aload 5
      //   108: aload_1
      //   109: invokeinterface 145 2 0
      //   114: pop
      //   115: aload_0
      //   116: aload_0
      //   117: getfield 79	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:produced	J
      //   120: lconst_1
      //   121: ladd
      //   122: putfield 79	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:produced	J
      //   125: aload_0
      //   126: getfield 46	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   129: aload 5
      //   131: invokeinterface 147 2 0
      //   136: aload_2
      //   137: invokevirtual 151	java/util/ArrayDeque:iterator	()Ljava/util/Iterator;
      //   140: astore_2
      //   141: aload_2
      //   142: invokeinterface 156 1 0
      //   147: ifeq +22 -> 169
      //   150: aload_2
      //   151: invokeinterface 159 1 0
      //   156: checkcast 122	java/util/Collection
      //   159: aload_1
      //   160: invokeinterface 145 2 0
      //   165: pop
      //   166: goto -25 -> 141
      //   169: iload 4
      //   171: istore_3
      //   172: iload 4
      //   174: aload_0
      //   175: getfield 50	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:skip	I
      //   178: if_icmpne +5 -> 183
      //   181: iconst_0
      //   182: istore_3
      //   183: aload_0
      //   184: iload_3
      //   185: putfield 106	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferOverlappingSubscriber:index	I
      //   188: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	189	0	this	PublisherBufferOverlappingSubscriber
      //   0	189	1	paramT	T
      //   12	139	2	localObject	Object
      //   17	168	3	i	int
      //   21	158	4	j	int
      //   44	86	5	localCollection	Collection
      // Exception table:
      //   from	to	target	type
      //   27	46	56	finally
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
      {
        if (QueueDrainHelper.postCompleteRequest(paramLong, this.downstream, this.buffers, this, this)) {
          return;
        }
        if ((!this.once.get()) && (this.once.compareAndSet(false, true)))
        {
          paramLong = BackpressureHelper.multiplyCap(this.skip, paramLong - 1L);
          paramLong = BackpressureHelper.addCap(this.size, paramLong);
          this.upstream.request(paramLong);
        }
        else
        {
          paramLong = BackpressureHelper.multiplyCap(this.skip, paramLong);
          this.upstream.request(paramLong);
        }
      }
    }
  }
  
  static final class PublisherBufferSkipSubscriber<T, C extends Collection<? super T>>
    extends AtomicInteger
    implements FlowableSubscriber<T>, Subscription
  {
    private static final long serialVersionUID = -5616169793639412593L;
    C buffer;
    final Callable<C> bufferSupplier;
    boolean done;
    final Subscriber<? super C> downstream;
    int index;
    final int size;
    final int skip;
    Subscription upstream;
    
    PublisherBufferSkipSubscriber(Subscriber<? super C> paramSubscriber, int paramInt1, int paramInt2, Callable<C> paramCallable)
    {
      this.downstream = paramSubscriber;
      this.size = paramInt1;
      this.skip = paramInt2;
      this.bufferSupplier = paramCallable;
    }
    
    public void cancel()
    {
      this.upstream.cancel();
    }
    
    public void onComplete()
    {
      if (this.done) {
        return;
      }
      this.done = true;
      Collection localCollection = this.buffer;
      this.buffer = null;
      if (localCollection != null) {
        this.downstream.onNext(localCollection);
      }
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      this.buffer = null;
      this.downstream.onError(paramThrowable);
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 57	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 59	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:buffer	Ljava/util/Collection;
      //   12: astore_2
      //   13: aload_0
      //   14: getfield 76	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:index	I
      //   17: istore_3
      //   18: iload_3
      //   19: iconst_1
      //   20: iadd
      //   21: istore 4
      //   23: iload_3
      //   24: ifne +44 -> 68
      //   27: aload_0
      //   28: getfield 46	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:bufferSupplier	Ljava/util/concurrent/Callable;
      //   31: invokeinterface 82 1 0
      //   36: ldc 84
      //   38: invokestatic 90	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   41: checkcast 92	java/util/Collection
      //   44: astore_2
      //   45: aload_0
      //   46: aload_2
      //   47: putfield 59	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:buffer	Ljava/util/Collection;
      //   50: goto +18 -> 68
      //   53: astore_1
      //   54: aload_1
      //   55: invokestatic 97	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   58: aload_0
      //   59: invokevirtual 98	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:cancel	()V
      //   62: aload_0
      //   63: aload_1
      //   64: invokevirtual 99	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:onError	(Ljava/lang/Throwable;)V
      //   67: return
      //   68: aload_2
      //   69: ifnull +39 -> 108
      //   72: aload_2
      //   73: aload_1
      //   74: invokeinterface 103 2 0
      //   79: pop
      //   80: aload_2
      //   81: invokeinterface 106 1 0
      //   86: aload_0
      //   87: getfield 42	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:size	I
      //   90: if_icmpne +18 -> 108
      //   93: aload_0
      //   94: aconst_null
      //   95: putfield 59	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:buffer	Ljava/util/Collection;
      //   98: aload_0
      //   99: getfield 40	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   102: aload_2
      //   103: invokeinterface 65 2 0
      //   108: iload 4
      //   110: istore_3
      //   111: iload 4
      //   113: aload_0
      //   114: getfield 44	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:skip	I
      //   117: if_icmpne +5 -> 122
      //   120: iconst_0
      //   121: istore_3
      //   122: aload_0
      //   123: iload_3
      //   124: putfield 76	io/reactivex/internal/operators/flowable/FlowableBuffer$PublisherBufferSkipSubscriber:index	I
      //   127: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	128	0	this	PublisherBufferSkipSubscriber
      //   0	128	1	paramT	T
      //   12	91	2	localCollection	Collection
      //   17	107	3	i	int
      //   21	97	4	j	int
      // Exception table:
      //   from	to	target	type
      //   27	45	53	finally
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        if ((get() == 0) && (compareAndSet(0, 1)))
        {
          long l = BackpressureHelper.multiplyCap(paramLong, this.size);
          paramLong = BackpressureHelper.multiplyCap(this.skip - this.size, paramLong - 1L);
          this.upstream.request(BackpressureHelper.addCap(l, paramLong));
        }
        else
        {
          this.upstream.request(BackpressureHelper.multiplyCap(this.skip, paramLong));
        }
      }
    }
  }
}
