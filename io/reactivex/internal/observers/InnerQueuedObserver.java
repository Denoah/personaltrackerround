package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.util.QueueDrainHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class InnerQueuedObserver<T>
  extends AtomicReference<Disposable>
  implements Observer<T>, Disposable
{
  private static final long serialVersionUID = -5417183359794346637L;
  volatile boolean done;
  int fusionMode;
  final InnerQueuedObserverSupport<T> parent;
  final int prefetch;
  SimpleQueue<T> queue;
  
  public InnerQueuedObserver(InnerQueuedObserverSupport<T> paramInnerQueuedObserverSupport, int paramInt)
  {
    this.parent = paramInnerQueuedObserverSupport;
    this.prefetch = paramInt;
  }
  
  public void dispose()
  {
    DisposableHelper.dispose(this);
  }
  
  public int fusionMode()
  {
    return this.fusionMode;
  }
  
  public boolean isDisposed()
  {
    return DisposableHelper.isDisposed((Disposable)get());
  }
  
  public boolean isDone()
  {
    return this.done;
  }
  
  public void onComplete()
  {
    this.parent.innerComplete(this);
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.parent.innerError(this, paramThrowable);
  }
  
  public void onNext(T paramT)
  {
    if (this.fusionMode == 0) {
      this.parent.innerNext(this, paramT);
    } else {
      this.parent.drain();
    }
  }
  
  public void onSubscribe(Disposable paramDisposable)
  {
    if (DisposableHelper.setOnce(this, paramDisposable))
    {
      if ((paramDisposable instanceof QueueDisposable))
      {
        paramDisposable = (QueueDisposable)paramDisposable;
        int i = paramDisposable.requestFusion(3);
        if (i == 1)
        {
          this.fusionMode = i;
          this.queue = paramDisposable;
          this.done = true;
          this.parent.innerComplete(this);
          return;
        }
        if (i == 2)
        {
          this.fusionMode = i;
          this.queue = paramDisposable;
          return;
        }
      }
      this.queue = QueueDrainHelper.createQueue(-this.prefetch);
    }
  }
  
  public SimpleQueue<T> queue()
  {
    return this.queue;
  }
  
  public void setDone()
  {
    this.done = true;
  }
}
