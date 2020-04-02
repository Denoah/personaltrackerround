package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.QueueDrainHelper;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferBoundarySupplier<T, U extends Collection<? super T>, B>
  extends AbstractFlowableWithUpstream<T, U>
{
  final Callable<? extends Publisher<B>> boundarySupplier;
  final Callable<U> bufferSupplier;
  
  public FlowableBufferBoundarySupplier(Flowable<T> paramFlowable, Callable<? extends Publisher<B>> paramCallable, Callable<U> paramCallable1)
  {
    super(paramFlowable);
    this.boundarySupplier = paramCallable;
    this.bufferSupplier = paramCallable1;
  }
  
  protected void subscribeActual(Subscriber<? super U> paramSubscriber)
  {
    this.source.subscribe(new BufferBoundarySupplierSubscriber(new SerializedSubscriber(paramSubscriber), this.bufferSupplier, this.boundarySupplier));
  }
  
  static final class BufferBoundarySubscriber<T, U extends Collection<? super T>, B>
    extends DisposableSubscriber<B>
  {
    boolean once;
    final FlowableBufferBoundarySupplier.BufferBoundarySupplierSubscriber<T, U, B> parent;
    
    BufferBoundarySubscriber(FlowableBufferBoundarySupplier.BufferBoundarySupplierSubscriber<T, U, B> paramBufferBoundarySupplierSubscriber)
    {
      this.parent = paramBufferBoundarySupplierSubscriber;
    }
    
    public void onComplete()
    {
      if (this.once) {
        return;
      }
      this.once = true;
      this.parent.next();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.once)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.once = true;
      this.parent.onError(paramThrowable);
    }
    
    public void onNext(B paramB)
    {
      if (this.once) {
        return;
      }
      this.once = true;
      cancel();
      this.parent.next();
    }
  }
  
  static final class BufferBoundarySupplierSubscriber<T, U extends Collection<? super T>, B>
    extends QueueDrainSubscriber<T, U, U>
    implements FlowableSubscriber<T>, Subscription, Disposable
  {
    final Callable<? extends Publisher<B>> boundarySupplier;
    U buffer;
    final Callable<U> bufferSupplier;
    final AtomicReference<Disposable> other = new AtomicReference();
    Subscription upstream;
    
    BufferBoundarySupplierSubscriber(Subscriber<? super U> paramSubscriber, Callable<U> paramCallable, Callable<? extends Publisher<B>> paramCallable1)
    {
      super(new MpscLinkedQueue());
      this.bufferSupplier = paramCallable;
      this.boundarySupplier = paramCallable1;
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
        this.upstream.cancel();
        disposeOther();
        if (enter()) {
          this.queue.clear();
        }
      }
    }
    
    public void dispose()
    {
      this.upstream.cancel();
      disposeOther();
    }
    
    void disposeOther()
    {
      DisposableHelper.dispose(this.other);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.other.get() == DisposableHelper.DISPOSED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void next()
    {
      try
      {
        Collection localCollection1 = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
        try
        {
          Publisher localPublisher = (Publisher)ObjectHelper.requireNonNull(this.boundarySupplier.call(), "The boundary publisher supplied is null");
          FlowableBufferBoundarySupplier.BufferBoundarySubscriber localBufferBoundarySubscriber = new FlowableBufferBoundarySupplier.BufferBoundarySubscriber(this);
          if (DisposableHelper.replace(this.other, localBufferBoundarySubscriber)) {
            try
            {
              Collection localCollection2 = this.buffer;
              if (localCollection2 == null) {
                return;
              }
              this.buffer = localCollection1;
              localPublisher.subscribe(localBufferBoundarySubscriber);
              fastPathEmitMax(localCollection2, false, this);
            }
            finally {}
          }
          return;
        }
        finally
        {
          Exceptions.throwIfFatal(localThrowable1);
          this.cancelled = true;
          this.upstream.cancel();
          this.downstream.onError(localThrowable1);
          return;
        }
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable2);
        cancel();
        this.downstream.onError(localThrowable2);
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
      Subscriber localSubscriber = this.downstream;
      try
      {
        Object localObject = (Collection)ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The buffer supplied is null");
        this.buffer = ((Collection)localObject);
        try
        {
          localObject = (Publisher)ObjectHelper.requireNonNull(this.boundarySupplier.call(), "The boundary publisher supplied is null");
          FlowableBufferBoundarySupplier.BufferBoundarySubscriber localBufferBoundarySubscriber = new FlowableBufferBoundarySupplier.BufferBoundarySubscriber(this);
          this.other.set(localBufferBoundarySubscriber);
          localSubscriber.onSubscribe(this);
          if (!this.cancelled)
          {
            paramSubscription.request(Long.MAX_VALUE);
            ((Publisher)localObject).subscribe(localBufferBoundarySubscriber);
          }
          return;
        }
        finally {}
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable2);
        this.cancelled = true;
        paramSubscription.cancel();
        EmptySubscription.error(localThrowable2, localSubscriber);
      }
    }
    
    public void request(long paramLong)
    {
      requested(paramLong);
    }
  }
}
