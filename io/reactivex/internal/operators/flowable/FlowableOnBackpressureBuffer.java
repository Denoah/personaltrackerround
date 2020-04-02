package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Action;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableOnBackpressureBuffer<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final int bufferSize;
  final boolean delayError;
  final Action onOverflow;
  final boolean unbounded;
  
  public FlowableOnBackpressureBuffer(Flowable<T> paramFlowable, int paramInt, boolean paramBoolean1, boolean paramBoolean2, Action paramAction)
  {
    super(paramFlowable);
    this.bufferSize = paramInt;
    this.unbounded = paramBoolean1;
    this.delayError = paramBoolean2;
    this.onOverflow = paramAction;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new BackpressureBufferSubscriber(paramSubscriber, this.bufferSize, this.unbounded, this.delayError, this.onOverflow));
  }
  
  static final class BackpressureBufferSubscriber<T>
    extends BasicIntQueueSubscription<T>
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -2514538129242366402L;
    volatile boolean cancelled;
    final boolean delayError;
    volatile boolean done;
    final Subscriber<? super T> downstream;
    Throwable error;
    final Action onOverflow;
    boolean outputFused;
    final SimplePlainQueue<T> queue;
    final AtomicLong requested = new AtomicLong();
    Subscription upstream;
    
    BackpressureBufferSubscriber(Subscriber<? super T> paramSubscriber, int paramInt, boolean paramBoolean1, boolean paramBoolean2, Action paramAction)
    {
      this.downstream = paramSubscriber;
      this.onOverflow = paramAction;
      this.delayError = paramBoolean2;
      if (paramBoolean1) {
        paramSubscriber = new SpscLinkedArrayQueue(paramInt);
      } else {
        paramSubscriber = new SpscArrayQueue(paramInt);
      }
      this.queue = paramSubscriber;
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.upstream.cancel();
        if (getAndIncrement() == 0) {
          this.queue.clear();
        }
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Subscriber<? super T> paramSubscriber)
    {
      if (this.cancelled)
      {
        this.queue.clear();
        return true;
      }
      if (paramBoolean1)
      {
        Throwable localThrowable;
        if (this.delayError)
        {
          if (paramBoolean2)
          {
            localThrowable = this.error;
            if (localThrowable != null) {
              paramSubscriber.onError(localThrowable);
            } else {
              paramSubscriber.onComplete();
            }
            return true;
          }
        }
        else
        {
          localThrowable = this.error;
          if (localThrowable != null)
          {
            this.queue.clear();
            paramSubscriber.onError(localThrowable);
            return true;
          }
          if (paramBoolean2)
          {
            paramSubscriber.onComplete();
            return true;
          }
        }
      }
      return false;
    }
    
    public void clear()
    {
      this.queue.clear();
    }
    
    void drain()
    {
      if (getAndIncrement() == 0)
      {
        SimplePlainQueue localSimplePlainQueue = this.queue;
        Subscriber localSubscriber = this.downstream;
        int i = 1;
        int j;
        do
        {
          if (checkTerminated(this.done, localSimplePlainQueue.isEmpty(), localSubscriber)) {
            return;
          }
          long l1 = this.requested.get();
          boolean bool1;
          for (long l2 = 0L;; l2 += 1L)
          {
            bool1 = l2 < l1;
            if (!bool1) {
              break;
            }
            boolean bool2 = this.done;
            Object localObject = localSimplePlainQueue.poll();
            boolean bool3;
            if (localObject == null) {
              bool3 = true;
            } else {
              bool3 = false;
            }
            if (checkTerminated(bool2, bool3, localSubscriber)) {
              return;
            }
            if (bool3) {
              break;
            }
            localSubscriber.onNext(localObject);
          }
          if ((!bool1) && (checkTerminated(this.done, localSimplePlainQueue.isEmpty(), localSubscriber))) {
            return;
          }
          if ((l2 != 0L) && (l1 != Long.MAX_VALUE)) {
            this.requested.addAndGet(-l2);
          }
          j = addAndGet(-i);
          i = j;
        } while (j != 0);
      }
    }
    
    public boolean isEmpty()
    {
      return this.queue.isEmpty();
    }
    
    public void onComplete()
    {
      this.done = true;
      if (this.outputFused) {
        this.downstream.onComplete();
      } else {
        drain();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      if (this.outputFused) {
        this.downstream.onError(paramThrowable);
      } else {
        drain();
      }
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 59	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBuffer$BackpressureBufferSubscriber:queue	Lio/reactivex/internal/fuseable/SimplePlainQueue;
      //   4: aload_1
      //   5: invokeinterface 132 2 0
      //   10: ifne +51 -> 61
      //   13: aload_0
      //   14: getfield 67	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBuffer$BackpressureBufferSubscriber:upstream	Lorg/reactivestreams/Subscription;
      //   17: invokeinterface 71 1 0
      //   22: new 134	io/reactivex/exceptions/MissingBackpressureException
      //   25: dup
      //   26: ldc -120
      //   28: invokespecial 139	io/reactivex/exceptions/MissingBackpressureException:<init>	(Ljava/lang/String;)V
      //   31: astore_2
      //   32: aload_0
      //   33: getfield 47	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBuffer$BackpressureBufferSubscriber:onOverflow	Lio/reactivex/functions/Action;
      //   36: invokeinterface 144 1 0
      //   41: goto +14 -> 55
      //   44: astore_1
      //   45: aload_1
      //   46: invokestatic 149	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   49: aload_2
      //   50: aload_1
      //   51: invokevirtual 153	io/reactivex/exceptions/MissingBackpressureException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
      //   54: pop
      //   55: aload_0
      //   56: aload_2
      //   57: invokevirtual 154	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBuffer$BackpressureBufferSubscriber:onError	(Ljava/lang/Throwable;)V
      //   60: return
      //   61: aload_0
      //   62: getfield 126	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBuffer$BackpressureBufferSubscriber:outputFused	Z
      //   65: ifeq +16 -> 81
      //   68: aload_0
      //   69: getfield 45	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBuffer$BackpressureBufferSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   72: aconst_null
      //   73: invokeinterface 115 2 0
      //   78: goto +7 -> 85
      //   81: aload_0
      //   82: invokevirtual 128	io/reactivex/internal/operators/flowable/FlowableOnBackpressureBuffer$BackpressureBufferSubscriber:drain	()V
      //   85: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	86	0	this	BackpressureBufferSubscriber
      //   0	86	1	paramT	T
      //   31	26	2	localMissingBackpressureException	io.reactivex.exceptions.MissingBackpressureException
      // Exception table:
      //   from	to	target	type
      //   32	41	44	finally
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        paramSubscription.request(Long.MAX_VALUE);
      }
    }
    
    public T poll()
      throws Exception
    {
      return this.queue.poll();
    }
    
    public void request(long paramLong)
    {
      if ((!this.outputFused) && (SubscriptionHelper.validate(paramLong)))
      {
        BackpressureHelper.add(this.requested, paramLong);
        drain();
      }
    }
    
    public int requestFusion(int paramInt)
    {
      if ((paramInt & 0x2) != 0)
      {
        this.outputFused = true;
        return 2;
      }
      return 0;
    }
  }
}
