package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.plugins.RxJavaPlugins;

public abstract class BasicFuseableObserver<T, R>
  implements Observer<T>, QueueDisposable<R>
{
  protected boolean done;
  protected final Observer<? super R> downstream;
  protected QueueDisposable<T> qd;
  protected int sourceMode;
  protected Disposable upstream;
  
  public BasicFuseableObserver(Observer<? super R> paramObserver)
  {
    this.downstream = paramObserver;
  }
  
  protected void afterDownstream() {}
  
  protected boolean beforeDownstream()
  {
    return true;
  }
  
  public void clear()
  {
    this.qd.clear();
  }
  
  public void dispose()
  {
    this.upstream.dispose();
  }
  
  protected final void fail(Throwable paramThrowable)
  {
    Exceptions.throwIfFatal(paramThrowable);
    this.upstream.dispose();
    onError(paramThrowable);
  }
  
  public boolean isDisposed()
  {
    return this.upstream.isDisposed();
  }
  
  public boolean isEmpty()
  {
    return this.qd.isEmpty();
  }
  
  public final boolean offer(R paramR)
  {
    throw new UnsupportedOperationException("Should not be called!");
  }
  
  public final boolean offer(R paramR1, R paramR2)
  {
    throw new UnsupportedOperationException("Should not be called!");
  }
  
  public void onComplete()
  {
    if (this.done) {
      return;
    }
    this.done = true;
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
  
  public final void onSubscribe(Disposable paramDisposable)
  {
    if (DisposableHelper.validate(this.upstream, paramDisposable))
    {
      this.upstream = paramDisposable;
      if ((paramDisposable instanceof QueueDisposable)) {
        this.qd = ((QueueDisposable)paramDisposable);
      }
      if (beforeDownstream())
      {
        this.downstream.onSubscribe(this);
        afterDownstream();
      }
    }
  }
  
  protected final int transitiveBoundaryFusion(int paramInt)
  {
    QueueDisposable localQueueDisposable = this.qd;
    if ((localQueueDisposable != null) && ((paramInt & 0x4) == 0))
    {
      paramInt = localQueueDisposable.requestFusion(paramInt);
      if (paramInt != 0) {
        this.sourceMode = paramInt;
      }
      return paramInt;
    }
    return 0;
  }
}
