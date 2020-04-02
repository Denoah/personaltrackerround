package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableDoAfterNext<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Consumer<? super T> onAfterNext;
  
  public FlowableDoAfterNext(Flowable<T> paramFlowable, Consumer<? super T> paramConsumer)
  {
    super(paramFlowable);
    this.onAfterNext = paramConsumer;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    if ((paramSubscriber instanceof ConditionalSubscriber)) {
      this.source.subscribe(new DoAfterConditionalSubscriber((ConditionalSubscriber)paramSubscriber, this.onAfterNext));
    } else {
      this.source.subscribe(new DoAfterSubscriber(paramSubscriber, this.onAfterNext));
    }
  }
  
  static final class DoAfterConditionalSubscriber<T>
    extends BasicFuseableConditionalSubscriber<T, T>
  {
    final Consumer<? super T> onAfterNext;
    
    DoAfterConditionalSubscriber(ConditionalSubscriber<? super T> paramConditionalSubscriber, Consumer<? super T> paramConsumer)
    {
      super();
      this.onAfterNext = paramConsumer;
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 27	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterConditionalSubscriber:downstream	Lio/reactivex/internal/fuseable/ConditionalSubscriber;
      //   4: aload_1
      //   5: invokeinterface 31 2 0
      //   10: aload_0
      //   11: getfield 35	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterConditionalSubscriber:sourceMode	I
      //   14: ifne +22 -> 36
      //   17: aload_0
      //   18: getfield 18	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterConditionalSubscriber:onAfterNext	Lio/reactivex/functions/Consumer;
      //   21: aload_1
      //   22: invokeinterface 40 2 0
      //   27: goto +9 -> 36
      //   30: astore_1
      //   31: aload_0
      //   32: aload_1
      //   33: invokevirtual 44	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterConditionalSubscriber:fail	(Ljava/lang/Throwable;)V
      //   36: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	37	0	this	DoAfterConditionalSubscriber
      //   0	37	1	paramT	T
      // Exception table:
      //   from	to	target	type
      //   17	27	30	finally
    }
    
    public T poll()
      throws Exception
    {
      Object localObject = this.qs.poll();
      if (localObject != null) {
        this.onAfterNext.accept(localObject);
      }
      return localObject;
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
    
    /* Error */
    public boolean tryOnNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 27	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterConditionalSubscriber:downstream	Lio/reactivex/internal/fuseable/ConditionalSubscriber;
      //   4: aload_1
      //   5: invokeinterface 68 2 0
      //   10: istore_2
      //   11: aload_0
      //   12: getfield 18	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterConditionalSubscriber:onAfterNext	Lio/reactivex/functions/Consumer;
      //   15: aload_1
      //   16: invokeinterface 40 2 0
      //   21: goto +9 -> 30
      //   24: astore_1
      //   25: aload_0
      //   26: aload_1
      //   27: invokevirtual 44	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterConditionalSubscriber:fail	(Ljava/lang/Throwable;)V
      //   30: iload_2
      //   31: ireturn
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	32	0	this	DoAfterConditionalSubscriber
      //   0	32	1	paramT	T
      //   10	21	2	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   11	21	24	finally
    }
  }
  
  static final class DoAfterSubscriber<T>
    extends BasicFuseableSubscriber<T, T>
  {
    final Consumer<? super T> onAfterNext;
    
    DoAfterSubscriber(Subscriber<? super T> paramSubscriber, Consumer<? super T> paramConsumer)
    {
      super();
      this.onAfterNext = paramConsumer;
    }
    
    /* Error */
    public void onNext(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 27	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterSubscriber:done	Z
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 31	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterSubscriber:downstream	Lorg/reactivestreams/Subscriber;
      //   12: aload_1
      //   13: invokeinterface 35 2 0
      //   18: aload_0
      //   19: getfield 39	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterSubscriber:sourceMode	I
      //   22: ifne +22 -> 44
      //   25: aload_0
      //   26: getfield 18	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterSubscriber:onAfterNext	Lio/reactivex/functions/Consumer;
      //   29: aload_1
      //   30: invokeinterface 44 2 0
      //   35: goto +9 -> 44
      //   38: astore_1
      //   39: aload_0
      //   40: aload_1
      //   41: invokevirtual 48	io/reactivex/internal/operators/flowable/FlowableDoAfterNext$DoAfterSubscriber:fail	(Ljava/lang/Throwable;)V
      //   44: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	45	0	this	DoAfterSubscriber
      //   0	45	1	paramT	T
      // Exception table:
      //   from	to	target	type
      //   25	35	38	finally
    }
    
    public T poll()
      throws Exception
    {
      Object localObject = this.qs.poll();
      if (localObject != null) {
        this.onAfterNext.accept(localObject);
      }
      return localObject;
    }
    
    public int requestFusion(int paramInt)
    {
      return transitiveBoundaryFusion(paramInt);
    }
  }
}
