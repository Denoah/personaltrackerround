package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableResumeNext
  extends Completable
{
  final Function<? super Throwable, ? extends CompletableSource> errorMapper;
  final CompletableSource source;
  
  public CompletableResumeNext(CompletableSource paramCompletableSource, Function<? super Throwable, ? extends CompletableSource> paramFunction)
  {
    this.source = paramCompletableSource;
    this.errorMapper = paramFunction;
  }
  
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    ResumeNextObserver localResumeNextObserver = new ResumeNextObserver(paramCompletableObserver, this.errorMapper);
    paramCompletableObserver.onSubscribe(localResumeNextObserver);
    this.source.subscribe(localResumeNextObserver);
  }
  
  static final class ResumeNextObserver
    extends AtomicReference<Disposable>
    implements CompletableObserver, Disposable
  {
    private static final long serialVersionUID = 5018523762564524046L;
    final CompletableObserver downstream;
    final Function<? super Throwable, ? extends CompletableSource> errorMapper;
    boolean once;
    
    ResumeNextObserver(CompletableObserver paramCompletableObserver, Function<? super Throwable, ? extends CompletableSource> paramFunction)
    {
      this.downstream = paramCompletableObserver;
      this.errorMapper = paramFunction;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)get());
    }
    
    public void onComplete()
    {
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.once)
      {
        this.downstream.onError(paramThrowable);
        return;
      }
      this.once = true;
      try
      {
        CompletableSource localCompletableSource = (CompletableSource)ObjectHelper.requireNonNull(this.errorMapper.apply(paramThrowable), "The errorMapper returned a null CompletableSource");
        localCompletableSource.subscribe(this);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        this.downstream.onError(new CompositeException(new Throwable[] { paramThrowable, localThrowable }));
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.replace(this, paramDisposable);
    }
  }
}
