package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscribers.SinglePostCompleteSubscriber;
import org.reactivestreams.Subscriber;

public final class FlowableOnErrorReturn<T>
  extends AbstractFlowableWithUpstream<T, T>
{
  final Function<? super Throwable, ? extends T> valueSupplier;
  
  public FlowableOnErrorReturn(Flowable<T> paramFlowable, Function<? super Throwable, ? extends T> paramFunction)
  {
    super(paramFlowable);
    this.valueSupplier = paramFunction;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.source.subscribe(new OnErrorReturnSubscriber(paramSubscriber, this.valueSupplier));
  }
  
  static final class OnErrorReturnSubscriber<T>
    extends SinglePostCompleteSubscriber<T, T>
  {
    private static final long serialVersionUID = -3740826063558713822L;
    final Function<? super Throwable, ? extends T> valueSupplier;
    
    OnErrorReturnSubscriber(Subscriber<? super T> paramSubscriber, Function<? super Throwable, ? extends T> paramFunction)
    {
      super();
      this.valueSupplier = paramFunction;
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      try
      {
        Object localObject = ObjectHelper.requireNonNull(this.valueSupplier.apply(paramThrowable), "The valueSupplier returned a null value");
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
      this.produced += 1L;
      this.downstream.onNext(paramT);
    }
  }
}
