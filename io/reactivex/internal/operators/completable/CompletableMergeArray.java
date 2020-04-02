package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableMergeArray
  extends Completable
{
  final CompletableSource[] sources;
  
  public CompletableMergeArray(CompletableSource[] paramArrayOfCompletableSource)
  {
    this.sources = paramArrayOfCompletableSource;
  }
  
  public void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    CompositeDisposable localCompositeDisposable = new CompositeDisposable();
    InnerCompletableObserver localInnerCompletableObserver = new InnerCompletableObserver(paramCompletableObserver, new AtomicBoolean(), localCompositeDisposable, this.sources.length + 1);
    paramCompletableObserver.onSubscribe(localCompositeDisposable);
    for (Object localObject : this.sources)
    {
      if (localCompositeDisposable.isDisposed()) {
        return;
      }
      if (localObject == null)
      {
        localCompositeDisposable.dispose();
        localInnerCompletableObserver.onError(new NullPointerException("A completable source is null"));
        return;
      }
      localObject.subscribe(localInnerCompletableObserver);
    }
    localInnerCompletableObserver.onComplete();
  }
  
  static final class InnerCompletableObserver
    extends AtomicInteger
    implements CompletableObserver
  {
    private static final long serialVersionUID = -8360547806504310570L;
    final CompletableObserver downstream;
    final AtomicBoolean once;
    final CompositeDisposable set;
    
    InnerCompletableObserver(CompletableObserver paramCompletableObserver, AtomicBoolean paramAtomicBoolean, CompositeDisposable paramCompositeDisposable, int paramInt)
    {
      this.downstream = paramCompletableObserver;
      this.once = paramAtomicBoolean;
      this.set = paramCompositeDisposable;
      lazySet(paramInt);
    }
    
    public void onComplete()
    {
      if ((decrementAndGet() == 0) && (this.once.compareAndSet(false, true))) {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.set.dispose();
      if (this.once.compareAndSet(false, true)) {
        this.downstream.onError(paramThrowable);
      } else {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.set.add(paramDisposable);
    }
  }
}
