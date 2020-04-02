package io.reactivex.internal.operators.observable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableMergeWithCompletable<T>
  extends AbstractObservableWithUpstream<T, T>
{
  final CompletableSource other;
  
  public ObservableMergeWithCompletable(Observable<T> paramObservable, CompletableSource paramCompletableSource)
  {
    super(paramObservable);
    this.other = paramCompletableSource;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    MergeWithObserver localMergeWithObserver = new MergeWithObserver(paramObserver);
    paramObserver.onSubscribe(localMergeWithObserver);
    this.source.subscribe(localMergeWithObserver);
    this.other.subscribe(localMergeWithObserver.otherObserver);
  }
  
  static final class MergeWithObserver<T>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = -4592979584110982903L;
    final Observer<? super T> downstream;
    final AtomicThrowable error;
    final AtomicReference<Disposable> mainDisposable;
    volatile boolean mainDone;
    volatile boolean otherDone;
    final OtherObserver otherObserver;
    
    MergeWithObserver(Observer<? super T> paramObserver)
    {
      this.downstream = paramObserver;
      this.mainDisposable = new AtomicReference();
      this.otherObserver = new OtherObserver(this);
      this.error = new AtomicThrowable();
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.mainDisposable);
      DisposableHelper.dispose(this.otherObserver);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)this.mainDisposable.get());
    }
    
    public void onComplete()
    {
      this.mainDone = true;
      if (this.otherDone) {
        HalfSerializer.onComplete(this.downstream, this, this.error);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.mainDisposable);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
    }
    
    public void onNext(T paramT)
    {
      HalfSerializer.onNext(this.downstream, paramT, this, this.error);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.mainDisposable, paramDisposable);
    }
    
    void otherComplete()
    {
      this.otherDone = true;
      if (this.mainDone) {
        HalfSerializer.onComplete(this.downstream, this, this.error);
      }
    }
    
    void otherError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this.mainDisposable);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
    }
    
    static final class OtherObserver
      extends AtomicReference<Disposable>
      implements CompletableObserver
    {
      private static final long serialVersionUID = -2935427570954647017L;
      final ObservableMergeWithCompletable.MergeWithObserver<?> parent;
      
      OtherObserver(ObservableMergeWithCompletable.MergeWithObserver<?> paramMergeWithObserver)
      {
        this.parent = paramMergeWithObserver;
      }
      
      public void onComplete()
      {
        this.parent.otherComplete();
      }
      
      public void onError(Throwable paramThrowable)
      {
        this.parent.otherError(paramThrowable);
      }
      
      public void onSubscribe(Disposable paramDisposable)
      {
        DisposableHelper.setOnce(this, paramDisposable);
      }
    }
  }
}
