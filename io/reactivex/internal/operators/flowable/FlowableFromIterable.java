package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.subscriptions.BasicQueueSubscription;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.Iterator;
import org.reactivestreams.Subscriber;

public final class FlowableFromIterable<T>
  extends Flowable<T>
{
  final Iterable<? extends T> source;
  
  public FlowableFromIterable(Iterable<? extends T> paramIterable)
  {
    this.source = paramIterable;
  }
  
  public static <T> void subscribe(Subscriber<? super T> paramSubscriber, Iterator<? extends T> paramIterator)
  {
    try
    {
      boolean bool = paramIterator.hasNext();
      if (!bool)
      {
        EmptySubscription.complete(paramSubscriber);
        return;
      }
      if ((paramSubscriber instanceof ConditionalSubscriber)) {
        paramSubscriber.onSubscribe(new IteratorConditionalSubscription((ConditionalSubscriber)paramSubscriber, paramIterator));
      } else {
        paramSubscriber.onSubscribe(new IteratorSubscription(paramSubscriber, paramIterator));
      }
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(paramIterator);
      EmptySubscription.error(paramIterator, paramSubscriber);
    }
  }
  
  public void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    try
    {
      Iterator localIterator = this.source.iterator();
      subscribe(paramSubscriber, localIterator);
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptySubscription.error(localThrowable, paramSubscriber);
    }
  }
  
  static abstract class BaseRangeSubscription<T>
    extends BasicQueueSubscription<T>
  {
    private static final long serialVersionUID = -2252972430506210021L;
    volatile boolean cancelled;
    Iterator<? extends T> it;
    boolean once;
    
    BaseRangeSubscription(Iterator<? extends T> paramIterator)
    {
      this.it = paramIterator;
    }
    
    public final void cancel()
    {
      this.cancelled = true;
    }
    
    public final void clear()
    {
      this.it = null;
    }
    
    abstract void fastPath();
    
    public final boolean isEmpty()
    {
      Iterator localIterator = this.it;
      boolean bool;
      if ((localIterator != null) && (localIterator.hasNext())) {
        bool = false;
      } else {
        bool = true;
      }
      return bool;
    }
    
    public final T poll()
    {
      Iterator localIterator = this.it;
      if (localIterator == null) {
        return null;
      }
      if (!this.once) {
        this.once = true;
      } else if (!localIterator.hasNext()) {
        return null;
      }
      return ObjectHelper.requireNonNull(this.it.next(), "Iterator.next() returned a null value");
    }
    
    public final void request(long paramLong)
    {
      if ((SubscriptionHelper.validate(paramLong)) && (BackpressureHelper.add(this, paramLong) == 0L)) {
        if (paramLong == Long.MAX_VALUE) {
          fastPath();
        } else {
          slowPath(paramLong);
        }
      }
    }
    
    public final int requestFusion(int paramInt)
    {
      return paramInt & 0x1;
    }
    
    abstract void slowPath(long paramLong);
  }
  
  static final class IteratorConditionalSubscription<T>
    extends FlowableFromIterable.BaseRangeSubscription<T>
  {
    private static final long serialVersionUID = -6022804456014692607L;
    final ConditionalSubscriber<? super T> downstream;
    
    IteratorConditionalSubscription(ConditionalSubscriber<? super T> paramConditionalSubscriber, Iterator<? extends T> paramIterator)
    {
      super();
      this.downstream = paramConditionalSubscriber;
    }
    
    void fastPath()
    {
      Iterator localIterator = this.it;
      ConditionalSubscriber localConditionalSubscriber = this.downstream;
      for (;;)
      {
        if (this.cancelled) {
          return;
        }
        try
        {
          Object localObject = localIterator.next();
          if (this.cancelled) {
            return;
          }
          if (localObject == null)
          {
            localConditionalSubscriber.onError(new NullPointerException("Iterator.next() returned a null value"));
            return;
          }
          localConditionalSubscriber.tryOnNext(localObject);
          if (this.cancelled) {
            return;
          }
          try
          {
            boolean bool = localIterator.hasNext();
            if (bool) {
              continue;
            }
            if (!this.cancelled) {
              localConditionalSubscriber.onComplete();
            }
            return;
          }
          finally {}
          return;
        }
        finally
        {
          Exceptions.throwIfFatal(localThrowable2);
          localConditionalSubscriber.onError(localThrowable2);
        }
      }
    }
    
    /* Error */
    void slowPath(long paramLong)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 31	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorConditionalSubscription:it	Ljava/util/Iterator;
      //   4: astore_3
      //   5: aload_0
      //   6: getfield 22	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorConditionalSubscription:downstream	Lio/reactivex/internal/fuseable/ConditionalSubscriber;
      //   9: astore 4
      //   11: lconst_0
      //   12: lstore 5
      //   14: lload 5
      //   16: lload_1
      //   17: lcmp
      //   18: ifeq +138 -> 156
      //   21: aload_0
      //   22: getfield 35	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorConditionalSubscription:cancelled	Z
      //   25: ifeq +4 -> 29
      //   28: return
      //   29: aload_3
      //   30: invokeinterface 41 1 0
      //   35: astore 7
      //   37: aload_0
      //   38: getfield 35	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorConditionalSubscription:cancelled	Z
      //   41: ifeq +4 -> 45
      //   44: return
      //   45: aload 7
      //   47: ifnonnull +20 -> 67
      //   50: aload 4
      //   52: new 43	java/lang/NullPointerException
      //   55: dup
      //   56: ldc 45
      //   58: invokespecial 48	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
      //   61: invokeinterface 54 2 0
      //   66: return
      //   67: aload 4
      //   69: aload 7
      //   71: invokeinterface 58 2 0
      //   76: istore 8
      //   78: aload_0
      //   79: getfield 35	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorConditionalSubscription:cancelled	Z
      //   82: ifeq +4 -> 86
      //   85: return
      //   86: aload_3
      //   87: invokeinterface 62 1 0
      //   92: istore 9
      //   94: iload 9
      //   96: ifne +18 -> 114
      //   99: aload_0
      //   100: getfield 35	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorConditionalSubscription:cancelled	Z
      //   103: ifne +10 -> 113
      //   106: aload 4
      //   108: invokeinterface 65 1 0
      //   113: return
      //   114: iload 8
      //   116: ifeq -102 -> 14
      //   119: lload 5
      //   121: lconst_1
      //   122: ladd
      //   123: lstore 5
      //   125: goto -111 -> 14
      //   128: astore_3
      //   129: aload_3
      //   130: invokestatic 70	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   133: aload 4
      //   135: aload_3
      //   136: invokeinterface 54 2 0
      //   141: return
      //   142: astore_3
      //   143: aload_3
      //   144: invokestatic 70	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   147: aload 4
      //   149: aload_3
      //   150: invokeinterface 54 2 0
      //   155: return
      //   156: aload_0
      //   157: invokevirtual 76	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorConditionalSubscription:get	()J
      //   160: lstore 10
      //   162: lload 10
      //   164: lstore_1
      //   165: lload 5
      //   167: lload 10
      //   169: lcmp
      //   170: ifne -156 -> 14
      //   173: aload_0
      //   174: lload 5
      //   176: lneg
      //   177: invokevirtual 80	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorConditionalSubscription:addAndGet	(J)J
      //   180: lstore 5
      //   182: lload 5
      //   184: lstore_1
      //   185: lload 5
      //   187: lconst_0
      //   188: lcmp
      //   189: ifne -178 -> 11
      //   192: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	193	0	this	IteratorConditionalSubscription
      //   0	193	1	paramLong	long
      //   4	83	3	localIterator	Iterator
      //   128	8	3	localThrowable1	Throwable
      //   142	8	3	localThrowable2	Throwable
      //   9	139	4	localConditionalSubscriber	ConditionalSubscriber
      //   12	174	5	l1	long
      //   35	35	7	localObject	Object
      //   76	39	8	bool1	boolean
      //   92	3	9	bool2	boolean
      //   160	8	10	l2	long
      // Exception table:
      //   from	to	target	type
      //   86	94	128	finally
      //   29	37	142	finally
    }
  }
  
  static final class IteratorSubscription<T>
    extends FlowableFromIterable.BaseRangeSubscription<T>
  {
    private static final long serialVersionUID = -6022804456014692607L;
    final Subscriber<? super T> downstream;
    
    IteratorSubscription(Subscriber<? super T> paramSubscriber, Iterator<? extends T> paramIterator)
    {
      super();
      this.downstream = paramSubscriber;
    }
    
    void fastPath()
    {
      Iterator localIterator = this.it;
      Subscriber localSubscriber = this.downstream;
      for (;;)
      {
        if (this.cancelled) {
          return;
        }
        try
        {
          Object localObject = localIterator.next();
          if (this.cancelled) {
            return;
          }
          if (localObject == null)
          {
            localSubscriber.onError(new NullPointerException("Iterator.next() returned a null value"));
            return;
          }
          localSubscriber.onNext(localObject);
          if (this.cancelled) {
            return;
          }
          try
          {
            boolean bool = localIterator.hasNext();
            if (bool) {
              continue;
            }
            if (!this.cancelled) {
              localSubscriber.onComplete();
            }
            return;
          }
          finally {}
          return;
        }
        finally
        {
          Exceptions.throwIfFatal(localThrowable2);
          localSubscriber.onError(localThrowable2);
        }
      }
    }
    
    /* Error */
    void slowPath(long paramLong)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 31	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorSubscription:it	Ljava/util/Iterator;
      //   4: astore_3
      //   5: aload_0
      //   6: getfield 22	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorSubscription:downstream	Lorg/reactivestreams/Subscriber;
      //   9: astore 4
      //   11: lconst_0
      //   12: lstore 5
      //   14: lload 5
      //   16: lload_1
      //   17: lcmp
      //   18: ifeq +131 -> 149
      //   21: aload_0
      //   22: getfield 35	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorSubscription:cancelled	Z
      //   25: ifeq +4 -> 29
      //   28: return
      //   29: aload_3
      //   30: invokeinterface 41 1 0
      //   35: astore 7
      //   37: aload_0
      //   38: getfield 35	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorSubscription:cancelled	Z
      //   41: ifeq +4 -> 45
      //   44: return
      //   45: aload 7
      //   47: ifnonnull +20 -> 67
      //   50: aload 4
      //   52: new 43	java/lang/NullPointerException
      //   55: dup
      //   56: ldc 45
      //   58: invokespecial 48	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
      //   61: invokeinterface 54 2 0
      //   66: return
      //   67: aload 4
      //   69: aload 7
      //   71: invokeinterface 58 2 0
      //   76: aload_0
      //   77: getfield 35	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorSubscription:cancelled	Z
      //   80: ifeq +4 -> 84
      //   83: return
      //   84: aload_3
      //   85: invokeinterface 62 1 0
      //   90: istore 8
      //   92: iload 8
      //   94: ifne +18 -> 112
      //   97: aload_0
      //   98: getfield 35	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorSubscription:cancelled	Z
      //   101: ifne +10 -> 111
      //   104: aload 4
      //   106: invokeinterface 65 1 0
      //   111: return
      //   112: lload 5
      //   114: lconst_1
      //   115: ladd
      //   116: lstore 5
      //   118: goto -104 -> 14
      //   121: astore_3
      //   122: aload_3
      //   123: invokestatic 70	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   126: aload 4
      //   128: aload_3
      //   129: invokeinterface 54 2 0
      //   134: return
      //   135: astore_3
      //   136: aload_3
      //   137: invokestatic 70	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   140: aload 4
      //   142: aload_3
      //   143: invokeinterface 54 2 0
      //   148: return
      //   149: aload_0
      //   150: invokevirtual 76	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorSubscription:get	()J
      //   153: lstore 9
      //   155: lload 9
      //   157: lstore_1
      //   158: lload 5
      //   160: lload 9
      //   162: lcmp
      //   163: ifne -149 -> 14
      //   166: aload_0
      //   167: lload 5
      //   169: lneg
      //   170: invokevirtual 80	io/reactivex/internal/operators/flowable/FlowableFromIterable$IteratorSubscription:addAndGet	(J)J
      //   173: lstore 5
      //   175: lload 5
      //   177: lstore_1
      //   178: lload 5
      //   180: lconst_0
      //   181: lcmp
      //   182: ifne -171 -> 11
      //   185: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	186	0	this	IteratorSubscription
      //   0	186	1	paramLong	long
      //   4	81	3	localIterator	Iterator
      //   121	8	3	localThrowable1	Throwable
      //   135	8	3	localThrowable2	Throwable
      //   9	132	4	localSubscriber	Subscriber
      //   12	167	5	l1	long
      //   35	35	7	localObject	Object
      //   90	3	8	bool	boolean
      //   153	8	9	l2	long
      // Exception table:
      //   from	to	target	type
      //   84	92	121	finally
      //   29	37	135	finally
    }
  }
}
