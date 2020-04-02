package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableMergeDelayErrorArray
  extends Completable
{
  final CompletableSource[] sources;
  
  public CompletableMergeDelayErrorArray(CompletableSource[] paramArrayOfCompletableSource)
  {
    this.sources = paramArrayOfCompletableSource;
  }
  
  public void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    CompositeDisposable localCompositeDisposable = new CompositeDisposable();
    AtomicInteger localAtomicInteger = new AtomicInteger(this.sources.length + 1);
    AtomicThrowable localAtomicThrowable = new AtomicThrowable();
    paramCompletableObserver.onSubscribe(localCompositeDisposable);
    Object localObject;
    for (localObject : this.sources)
    {
      if (localCompositeDisposable.isDisposed()) {
        return;
      }
      if (localObject == null)
      {
        localAtomicThrowable.addThrowable(new NullPointerException("A completable source is null"));
        localAtomicInteger.decrementAndGet();
      }
      else
      {
        ((CompletableSource)localObject).subscribe(new MergeInnerCompletableObserver(paramCompletableObserver, localCompositeDisposable, localAtomicThrowable, localAtomicInteger));
      }
    }
    if (localAtomicInteger.decrementAndGet() == 0)
    {
      localObject = localAtomicThrowable.terminate();
      if (localObject == null) {
        paramCompletableObserver.onComplete();
      } else {
        paramCompletableObserver.onError((Throwable)localObject);
      }
    }
  }
  
  static final class MergeInnerCompletableObserver
    implements CompletableObserver
  {
    final CompletableObserver downstream;
    final AtomicThrowable error;
    final CompositeDisposable set;
    final AtomicInteger wip;
    
    MergeInnerCompletableObserver(CompletableObserver paramCompletableObserver, CompositeDisposable paramCompositeDisposable, AtomicThrowable paramAtomicThrowable, AtomicInteger paramAtomicInteger)
    {
      this.downstream = paramCompletableObserver;
      this.set = paramCompositeDisposable;
      this.error = paramAtomicThrowable;
      this.wip = paramAtomicInteger;
    }
    
    public void onComplete()
    {
      tryTerminate();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.error.addThrowable(paramThrowable)) {
        tryTerminate();
      } else {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.set.add(paramDisposable);
    }
    
    void tryTerminate()
    {
      if (this.wip.decrementAndGet() == 0)
      {
        Throwable localThrowable = this.error.terminate();
        if (localThrowable == null) {
          this.downstream.onComplete();
        } else {
          this.downstream.onError(localThrowable);
        }
      }
    }
  }
}
