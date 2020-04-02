package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

public final class FlowableMapNotification<T, R>
  extends AbstractFlowableWithUpstream<T, R>
{
  final Callable<? extends R> onCompleteSupplier;
  final Function<? super Throwable, ? extends R> onErrorMapper;
  final Function<? super T, ? extends R> onNextMapper;
  
  public FlowableMapNotification(Flowable<T> paramFlowable, Function<? super T, ? extends R> paramFunction, Function<? super Throwable, ? extends R> paramFunction1, Callable<? extends R> paramCallable)
  {
    super(paramFlowable);
    this.onNextMapper = paramFunction;
    this.onErrorMapper = paramFunction1;
    this.onCompleteSupplier = paramCallable;
  }
  
  protected void subscribeActual(Subscriber<? super R> paramSubscriber)
  {
    this.source.subscribe(new MapNotificationSubscriber(paramSubscriber, this.onNextMapper, this.onErrorMapper, this.onCompleteSupplier));
  }
  
  static final class MapNotificationSubscriber<T, R>
    extends SinglePostCompleteSubscriber<T, R>
  {
    private static final long serialVersionUID = 2757120512858778108L;
    final Callable<? extends R> onCompleteSupplier;
    final Function<? super Throwable, ? extends R> onErrorMapper;
    final Function<? super T, ? extends R> onNextMapper;
    
    MapNotificationSubscriber(Subscriber<? super R> paramSubscriber, Function<? super T, ? extends R> paramFunction, Function<? super Throwable, ? extends R> paramFunction1, Callable<? extends R> paramCallable)
    {
      super();
      this.onNextMapper = paramFunction;
      this.onErrorMapper = paramFunction1;
      this.onCompleteSupplier = paramCallable;
    }
    
    public void onComplete()
    {
      try
      {
        Object localObject = ObjectHelper.requireNonNull(this.onCompleteSupplier.call(), "The onComplete publisher returned is null");
        complete(localObject);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(localThrowable);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        Object localObject = ObjectHelper.requireNonNull(this.onErrorMapper.apply(paramThrowable), "The onError publisher returned is null");
        complete(localObject);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(new CompositeException(new Throwable[] { paramThrowable, localThrowable }));
      }
    }
    
    public void onNext(T paramT)
    {
      try
      {
        paramT = ObjectHelper.requireNonNull(this.onNextMapper.apply(paramT), "The onNext publisher returned is null");
        this.produced += 1L;
        this.downstream.onNext(paramT);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        this.downstream.onError(paramT);
      }
    }
  }
}
