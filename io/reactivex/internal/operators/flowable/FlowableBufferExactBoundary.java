package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferExactBoundary<T, U extends Collection<? super T>, B>
  extends AbstractFlowableWithUpstream<T, U>
{
  final Publisher<B> boundary;
  final Callable<U> bufferSupplier;
  
  public FlowableBufferExactBoundary(Flowable<T> paramFlowable, Publisher<B> paramPublisher, Callable<U> paramCallable)
  {
    super(paramFlowable);
    this.boundary = paramPublisher;
    this.bufferSupplier = paramCallable;
  }
  
  protected void subscribeActual(Subscriber<? super U> paramSubscriber)
  {
    this.source.subscribe(new BufferExactBoundarySubscriber(new SerializedSubscriber(paramSubscriber), this.bufferSupplier, this.boundary));
  }
  
  static final class BufferBoundarySubscriber<T, U extends Collection<? super T>, B>
    extends DisposableSubscriber<B>
  {
    final FlowableBufferExactBoundary.BufferExactBoundarySubscriber<T, U, B> parent;
    
    BufferBoundarySubscriber(FlowableBufferExactBoundary.BufferExactBoundarySubscriber<T, U, B> paramBufferExactBoundarySubscriber)
    {
      this.parent = paramBufferExactBoundarySubscriber;
    }
    
    public void onComplete()
    {
      this.parent.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.onError(paramThrowable);
    }
    
    public void onNext(B paramB)
    {
      this.parent.next();
    }
  }
  
  static final class BufferExactBoundarySubscriber<T, U extends Collection<? super T>, B>
    extends QueueDrainSubscriber<T, U, U>
    implements FlowableSubscriber<T>, Subscription, Disposable
  {
    final Publisher<B> boundary;
    U buffer;
    final Callable<U> bufferSupplier;
    Disposable other;
    Subscription upstream;
    
    BufferExactBoundarySubscriber(Subscriber<? super U> paramSubscriber, Callable<U> paramCallable, Publisher<B> paramPublisher)
    {
      super(new MpscLinkedQueue());
      this.bufferSupplier = paramCallable;
      this.boundary = paramPublisher;
    }
    
    public boolean accept(Subscriber<? super U> paramSubscriber, U paramU)
    {
      this.downstream.onNext(paramU);
      return true;
    }
    
    public void cancel()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.other.dispose();
        this.upstream.cancel();
        if (enter()) {
          this.queue.clear();
        }
      }
    }
    
    public void dispose()
    {
      cancel();
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    void next()
    {
      try
      {
        Collection localCollection1 = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
        try
        {
          Collection localCollection2 = this.buffer;
          if (localCollection2 == null) {
            return;
          }
          this.buffer = localCollection1;
          fastPathEmitMax(localCollection2, false, this);
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        cancel();
      }
    }
    
    public void onComplete()
    {
      try
      {
        Collection localCollection = this.buffer;
        if (localCollection == null) {
          return;
        }
        this.buffer = null;
        this.queue.offer(localCollection);
        this.done = true;
        if (enter()) {
          QueueDrainHelper.drainMaxLoop(this.queue, this.downstream, false, this, this);
        }
        return;
      }
      finally {}
    }
    
    public void onError(Throwable paramThrowable)
    {
      cancel();
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      try
      {
        Collection localCollection = this.buffer;
        if (localCollection == null) {
          return;
        }
        localCollection.add(paramT);
        return;
      }
      finally {}
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (!SubscriptionHelper.validate(this.upstream, paramSubscription)) {
        return;
      }
      this.upstream = paramSubscription;
      try
      {
        Object localObject = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
        this.buffer = ((Collection)localObject);
        localObject = new FlowableBufferExactBoundary.BufferBoundarySubscriber(this);
        this.other = ((Disposable)localObject);
        this.downstream.onSubscribe(this);
        if (!this.cancelled)
        {
          paramSubscription.request(Long.MAX_VALUE);
          this.boundary.subscribe((Subscriber)localObject);
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.cancelled = true;
        paramSubscription.cancel();
        EmptySubscription.error(localThrowable, this.downstream);
      }
    }
    
    public void request(long paramLong)
    {
      requested(paramLong);
    }
  }
}
