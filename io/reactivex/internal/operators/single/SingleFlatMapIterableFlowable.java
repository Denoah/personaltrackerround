package io.reactivex.internal.operators.single;

import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.BasicIntQueueSubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;

public final class SingleFlatMapIterableFlowable<T, R>
  extends Flowable<R>
{
  final Function<? super T, ? extends Iterable<? extends R>> mapper;
  final SingleSource<T> source;
  
  public SingleFlatMapIterableFlowable(SingleSource<T> paramSingleSource, Function<? super T, ? extends Iterable<? extends R>> paramFunction)
  {
    this.source = paramSingleSource;
    this.mapper = paramFunction;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    this.source.subscribe(new FlatMapIterableObserver(paramSubscriber, this.mapper));
  }
  
  static final class FlatMapIterableObserver<T, R>
    extends BasicIntQueueSubscription<R>
    implements SingleObserver<T>
  {
    private static final long serialVersionUID = -8938804753851907758L;
    volatile boolean cancelled;
    final Subscriber<? super R> downstream;
    volatile Iterator<? extends R> it;
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    boolean outputFused;
    final AtomicLong requested;
    Disposable upstream;
    
    FlatMapIterableObserver(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends Iterable<? extends R>> paramFunction)
    {
      this.downstream = paramSubscriber;
      this.mapper = paramFunction;
      this.requested = new AtomicLong();
    }
    
    public void cancel()
    {
      this.cancelled = true;
      this.upstream.dispose();
      this.upstream = DisposableHelper.DISPOSED;
    }
    
    public void clear()
    {
      this.it = null;
    }
    
    void drain()
    {
      if (getAndIncrement() != 0) {
        return;
      }
      Subscriber localSubscriber = this.downstream;
      Iterator localIterator1 = this.it;
      if ((this.outputFused) && (localIterator1 != null))
      {
        localSubscriber.onNext(null);
        localSubscriber.onComplete();
        return;
      }
      int i = 1;
      for (;;)
      {
        if (localIterator1 != null)
        {
          long l1 = this.requested.get();
          if (l1 == Long.MAX_VALUE)
          {
            slowPath(localSubscriber, localIterator1);
            return;
          }
          long l2 = 0L;
          while (l2 != l1)
          {
            if (this.cancelled) {
              return;
            }
            try
            {
              Object localObject = ObjectHelper.requireNonNull(localIterator1.next(), "The iterator returned a null value");
              localSubscriber.onNext(localObject);
              if (this.cancelled) {
                return;
              }
              l2 += 1L;
              try
              {
                boolean bool = localIterator1.hasNext();
                if (bool) {
                  continue;
                }
                localSubscriber.onComplete();
                return;
              }
              finally {}
              if (l2 == 0L) {
                break label191;
              }
            }
            finally
            {
              Exceptions.throwIfFatal(localThrowable2);
              localSubscriber.onError(localThrowable2);
              return;
            }
          }
          BackpressureHelper.produced(this.requested, l2);
        }
        label191:
        int j = addAndGet(-i);
        if (j == 0) {
          return;
        }
        i = j;
        if (localThrowable2 == null)
        {
          Iterator localIterator2 = this.it;
          i = j;
        }
      }
    }
    
    public boolean isEmpty()
    {
      boolean bool;
      if (this.it == null) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.upstream = DisposableHelper.DISPOSED;
      this.downstream.onError(paramThrowable);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
    
    public void onSuccess(T paramT)
    {
      try
      {
        paramT = ((Iterable)this.mapper.apply(paramT)).iterator();
        boolean bool = paramT.hasNext();
        if (!bool)
        {
          this.downstream.onComplete();
          return;
        }
        this.it = paramT;
        drain();
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.downstream.onError(paramT);
      }
    }
    
    public R poll()
      throws Exception
    {
      Iterator localIterator = this.it;
      if (localIterator != null)
      {
        Object localObject = ObjectHelper.requireNonNull(localIterator.next(), "The iterator returned a null value");
        if (!localIterator.hasNext()) {
          this.it = null;
        }
        return localObject;
      }
      return null;
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong))
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
    
    void slowPath(Subscriber<? super R> paramSubscriber, Iterator<? extends R> paramIterator)
    {
      for (;;)
      {
        if (this.cancelled) {
          return;
        }
        try
        {
          Object localObject = paramIterator.next();
          paramSubscriber.onNext(localObject);
          if (this.cancelled) {
            return;
          }
          try
          {
            boolean bool = paramIterator.hasNext();
            if (bool) {
              continue;
            }
            paramSubscriber.onComplete();
            return;
          }
          finally {}
          return;
        }
        finally
        {
          Exceptions.throwIfFatal(paramIterator);
          paramSubscriber.onError(paramIterator);
        }
      }
    }
  }
}
