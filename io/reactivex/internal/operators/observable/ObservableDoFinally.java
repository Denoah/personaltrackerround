package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.observers.BasicIntQueueDisposable;

public final class ObservableDoFinally<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final Action onFinally;
  
  public ObservableDoFinally(ObservableSource<T> paramObservableSource, Action paramAction)
  {
    super(paramObservableSource);
    this.onFinally = paramAction;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.source.subscribe(new DoFinallyObserver(paramObserver, this.onFinally));
  }
  
  static final class DoFinallyObserver<T>
    extends BasicIntQueueDisposable<T>
    implements Observer<T>
  {
    private static final long serialVersionUID = 4109457741734051389L;
    final Observer<? super T> downstream;
    final Action onFinally;
    QueueDisposable<T> qd;
    boolean syncFused;
    Disposable upstream;
    
    DoFinallyObserver(Observer<? super T> paramObserver, Action paramAction)
    {
      this.downstream = paramObserver;
      this.onFinally = paramAction;
    }
    
    public void clear()
    {
      this.qd.clear();
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      runFinally();
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public boolean isEmpty()
    {
      return this.qd.isEmpty();
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
      runFinally();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.downstream.onError(paramThrowable);
      runFinally();
    }
    
    public void onNext(T paramT)
    {
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        if ((paramDisposable instanceof QueueDisposable)) {
          this.qd = ((QueueDisposable)paramDisposable);
        }
        this.downstream.onSubscribe(this);
      }
    }
    
    public T poll()
      throws Exception
    {
      Object localObject = this.qd.poll();
      if ((localObject == null) && (this.syncFused)) {
        runFinally();
      }
      return localObject;
    }
    
    public int requestFusion(int paramInt)
    {
      QueueDisposable localQueueDisposable = this.qd;
      boolean bool = false;
      if ((localQueueDisposable != null) && ((paramInt & 0x4) == 0))
      {
        paramInt = localQueueDisposable.requestFusion(paramInt);
        if (paramInt != 0)
        {
          if (paramInt == 1) {
            bool = true;
          }
          this.syncFused = bool;
        }
        return paramInt;
      }
      return 0;
    }
    
    /* Error */
    void runFinally()
    {
      // Byte code:
      //   0: aload_0
      //   1: iconst_0
      //   2: iconst_1
      //   3: invokevirtual 102	io/reactivex/internal/operators/observable/ObservableDoFinally$DoFinallyObserver:compareAndSet	(II)Z
      //   6: ifeq +24 -> 30
      //   9: aload_0
      //   10: getfield 35	io/reactivex/internal/operators/observable/ObservableDoFinally$DoFinallyObserver:onFinally	Lio/reactivex/functions/Action;
      //   13: invokeinterface 107 1 0
      //   18: goto +12 -> 30
      //   21: astore_1
      //   22: aload_1
      //   23: invokestatic 112	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   26: aload_1
      //   27: invokestatic 115	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   30: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	31	0	this	DoFinallyObserver
      //   21	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   9	18	21	finally
    }
  }
}
