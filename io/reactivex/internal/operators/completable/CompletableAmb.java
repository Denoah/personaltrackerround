package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CompletableAmb
  extends Completable
{
  private final CompletableSource[] sources;
  private final Iterable<? extends CompletableSource> sourcesIterable;
  
  public CompletableAmb(CompletableSource[] paramArrayOfCompletableSource, Iterable<? extends CompletableSource> paramIterable)
  {
    this.sources = paramArrayOfCompletableSource;
    this.sourcesIterable = paramIterable;
  }
  
  public void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    Object localObject1 = this.sources;
    int j;
    CompletableSource localCompletableSource;
    if (localObject1 == null)
    {
      Object localObject2 = new CompletableSource[8];
      try
      {
        localObject3 = this.sourcesIterable.iterator();
        i = 0;
        for (;;)
        {
          localObject1 = localObject2;
          j = i;
          if (!((Iterator)localObject3).hasNext()) {
            break;
          }
          localCompletableSource = (CompletableSource)((Iterator)localObject3).next();
          if (localCompletableSource == null)
          {
            localObject2 = new java/lang/NullPointerException;
            ((NullPointerException)localObject2).<init>("One of the sources is null");
            EmptyDisposable.error((Throwable)localObject2, paramCompletableObserver);
            return;
          }
          localObject1 = localObject2;
          if (i == localObject2.length)
          {
            localObject1 = new CompletableSource[(i >> 2) + i];
            System.arraycopy(localObject2, 0, localObject1, 0, i);
          }
          localObject1[i] = localCompletableSource;
          i++;
          localObject2 = localObject1;
        }
        j = localObject1.length;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        EmptyDisposable.error(localThrowable, paramCompletableObserver);
        return;
      }
    }
    CompositeDisposable localCompositeDisposable = new CompositeDisposable();
    paramCompletableObserver.onSubscribe(localCompositeDisposable);
    Object localObject3 = new AtomicBoolean();
    for (int i = 0; i < j; i++)
    {
      localCompletableSource = localObject1[i];
      if (localCompositeDisposable.isDisposed()) {
        return;
      }
      if (localCompletableSource == null)
      {
        localObject1 = new NullPointerException("One of the sources is null");
        if (((AtomicBoolean)localObject3).compareAndSet(false, true))
        {
          localCompositeDisposable.dispose();
          paramCompletableObserver.onError((Throwable)localObject1);
        }
        else
        {
          RxJavaPlugins.onError((Throwable)localObject1);
        }
        return;
      }
      localCompletableSource.subscribe(new Amb((AtomicBoolean)localObject3, localCompositeDisposable, paramCompletableObserver));
    }
    if (j == 0) {
      paramCompletableObserver.onComplete();
    }
  }
  
  static final class Amb
    implements CompletableObserver
  {
    final CompletableObserver downstream;
    final AtomicBoolean once;
    final CompositeDisposable set;
    Disposable upstream;
    
    Amb(AtomicBoolean paramAtomicBoolean, CompositeDisposable paramCompositeDisposable, CompletableObserver paramCompletableObserver)
    {
      this.once = paramAtomicBoolean;
      this.set = paramCompositeDisposable;
      this.downstream = paramCompletableObserver;
    }
    
    public void onComplete()
    {
      if (this.once.compareAndSet(false, true))
      {
        this.set.delete(this.upstream);
        this.set.dispose();
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.once.compareAndSet(false, true))
      {
        this.set.delete(this.upstream);
        this.set.dispose();
        this.downstream.onError(paramThrowable);
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.upstream = paramDisposable;
      this.set.add(paramDisposable);
    }
  }
}
