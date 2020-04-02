package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservablePublishSelector<T, R>
  extends AbstractObservableWithUpstream<T, R>
{
  final Function<? super Observable<T>, ? extends ObservableSource<R>> selector;
  
  public ObservablePublishSelector(ObservableSource<T> paramObservableSource, Function<? super Observable<T>, ? extends ObservableSource<R>> paramFunction)
  {
    super(paramObservableSource);
    this.selector = paramFunction;
  }
  
  protected void subscribeActual(Observer<? super R> paramObserver)
  {
    PublishSubject localPublishSubject = PublishSubject.create();
    try
    {
      ObservableSource localObservableSource = (ObservableSource)ObjectHelper.requireNonNull(this.selector.apply(localPublishSubject), "The selector returned a null ObservableSource");
      paramObserver = new TargetObserver(paramObserver);
      localObservableSource.subscribe(paramObserver);
      this.source.subscribe(new SourceObserver(localPublishSubject, paramObserver));
      return;
    }
    finally
    {
      Exceptions.throwIfFatal(localThrowable);
      EmptyDisposable.error(localThrowable, paramObserver);
    }
  }
  
  static final class SourceObserver<T, R>
    implements Observer<T>
  {
    final PublishSubject<T> subject;
    final AtomicReference<Disposable> target;
    
    SourceObserver(PublishSubject<T> paramPublishSubject, AtomicReference<Disposable> paramAtomicReference)
    {
      this.subject = paramPublishSubject;
      this.target = paramAtomicReference;
    }
    
    public void onComplete()
    {
      this.subject.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.subject.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.subject.onNext(paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.target, paramDisposable);
    }
  }
  
  static final class TargetObserver<T, R>
    extends AtomicReference<Disposable>
    implements Observer<R>, Disposable
  {
    private static final long serialVersionUID = 854110278590336484L;
    final Observer<? super R> downstream;
    Disposable upstream;
    
    TargetObserver(Observer<? super R> paramObserver)
    {
      this.downstream = paramObserver;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    public void onComplete()
    {
      DisposableHelper.dispose(this);
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      DisposableHelper.dispose(this);
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(R paramR)
    {
      this.downstream.onNext(paramR);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
  }
}
