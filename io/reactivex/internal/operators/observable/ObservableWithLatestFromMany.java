package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.internal.util.HalfSerializer;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class ObservableWithLatestFromMany<T, R>
  extends AbstractObservableWithUpstream<T, R>
{
  final Function<? super Object[], R> combiner;
  final ObservableSource<?>[] otherArray;
  final Iterable<? extends ObservableSource<?>> otherIterable;
  
  public ObservableWithLatestFromMany(ObservableSource<T> paramObservableSource, Iterable<? extends ObservableSource<?>> paramIterable, Function<? super Object[], R> paramFunction)
  {
    super(paramObservableSource);
    this.otherArray = null;
    this.otherIterable = paramIterable;
    this.combiner = paramFunction;
  }
  
  public ObservableWithLatestFromMany(ObservableSource<T> paramObservableSource, ObservableSource<?>[] paramArrayOfObservableSource, Function<? super Object[], R> paramFunction)
  {
    super(paramObservableSource);
    this.otherArray = paramArrayOfObservableSource;
    this.otherIterable = null;
    this.combiner = paramFunction;
  }
  
  protected void subscribeActual(Observer<? super R> paramObserver)
  {
    Object localObject1 = this.otherArray;
    int j;
    if (localObject1 == null)
    {
      Object localObject2 = new ObservableSource[8];
      try
      {
        Iterator localIterator = this.otherIterable.iterator();
        int i = 0;
        for (;;)
        {
          localObject1 = localObject2;
          j = i;
          if (!localIterator.hasNext()) {
            break;
          }
          ObservableSource localObservableSource = (ObservableSource)localIterator.next();
          localObject1 = localObject2;
          if (i == localObject2.length) {
            localObject1 = (ObservableSource[])Arrays.copyOf((Object[])localObject2, (i >> 1) + i);
          }
          localObject1[i] = localObservableSource;
          i++;
          localObject2 = localObject1;
        }
        j = localObject1.length;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        EmptyDisposable.error(localThrowable, paramObserver);
        return;
      }
    }
    if (j == 0)
    {
      new ObservableMap(this.source, new SingletonArrayFunc()).subscribeActual(paramObserver);
      return;
    }
    WithLatestFromObserver localWithLatestFromObserver = new WithLatestFromObserver(paramObserver, this.combiner, j);
    paramObserver.onSubscribe(localWithLatestFromObserver);
    localWithLatestFromObserver.subscribe((ObservableSource[])localObject1, j);
    this.source.subscribe(localWithLatestFromObserver);
  }
  
  final class SingletonArrayFunc
    implements Function<T, R>
  {
    SingletonArrayFunc() {}
    
    public R apply(T paramT)
      throws Exception
    {
      return ObjectHelper.requireNonNull(ObservableWithLatestFromMany.this.combiner.apply(new Object[] { paramT }), "The combiner returned a null value");
    }
  }
  
  static final class WithLatestFromObserver<T, R>
    extends AtomicInteger
    implements Observer<T>, Disposable
  {
    private static final long serialVersionUID = 1577321883966341961L;
    final Function<? super Object[], R> combiner;
    volatile boolean done;
    final Observer<? super R> downstream;
    final AtomicThrowable error;
    final ObservableWithLatestFromMany.WithLatestInnerObserver[] observers;
    final AtomicReference<Disposable> upstream;
    final AtomicReferenceArray<Object> values;
    
    WithLatestFromObserver(Observer<? super R> paramObserver, Function<? super Object[], R> paramFunction, int paramInt)
    {
      this.downstream = paramObserver;
      this.combiner = paramFunction;
      paramObserver = new ObservableWithLatestFromMany.WithLatestInnerObserver[paramInt];
      for (int i = 0; i < paramInt; i++) {
        paramObserver[i] = new ObservableWithLatestFromMany.WithLatestInnerObserver(this, i);
      }
      this.observers = paramObserver;
      this.values = new AtomicReferenceArray(paramInt);
      this.upstream = new AtomicReference();
      this.error = new AtomicThrowable();
    }
    
    void cancelAllBut(int paramInt)
    {
      ObservableWithLatestFromMany.WithLatestInnerObserver[] arrayOfWithLatestInnerObserver = this.observers;
      for (int i = 0; i < arrayOfWithLatestInnerObserver.length; i++) {
        if (i != paramInt) {
          arrayOfWithLatestInnerObserver[i].dispose();
        }
      }
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.upstream);
      ObservableWithLatestFromMany.WithLatestInnerObserver[] arrayOfWithLatestInnerObserver = this.observers;
      int i = arrayOfWithLatestInnerObserver.length;
      for (int j = 0; j < i; j++) {
        arrayOfWithLatestInnerObserver[j].dispose();
      }
    }
    
    void innerComplete(int paramInt, boolean paramBoolean)
    {
      if (!paramBoolean)
      {
        this.done = true;
        cancelAllBut(paramInt);
        HalfSerializer.onComplete(this.downstream, this, this.error);
      }
    }
    
    void innerError(int paramInt, Throwable paramThrowable)
    {
      this.done = true;
      DisposableHelper.dispose(this.upstream);
      cancelAllBut(paramInt);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
    }
    
    void innerNext(int paramInt, Object paramObject)
    {
      this.values.set(paramInt, paramObject);
    }
    
    public boolean isDisposed()
    {
      return DisposableHelper.isDisposed((Disposable)this.upstream.get());
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        cancelAllBut(-1);
        HalfSerializer.onComplete(this.downstream, this, this.error);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.done)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.done = true;
      cancelAllBut(-1);
      HalfSerializer.onError(this.downstream, paramThrowable, this, this.error);
    }
    
    public void onNext(T paramT)
    {
      if (this.done) {
        return;
      }
      AtomicReferenceArray localAtomicReferenceArray = this.values;
      int i = localAtomicReferenceArray.length();
      Object[] arrayOfObject = new Object[i + 1];
      int j = 0;
      arrayOfObject[0] = paramT;
      while (j < i)
      {
        paramT = localAtomicReferenceArray.get(j);
        if (paramT == null) {
          return;
        }
        j++;
        arrayOfObject[j] = paramT;
      }
      try
      {
        paramT = ObjectHelper.requireNonNull(this.combiner.apply(arrayOfObject), "combiner returned a null value");
        HalfSerializer.onNext(this.downstream, paramT, this, this.error);
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(paramT);
        dispose();
        onError(paramT);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.upstream, paramDisposable);
    }
    
    void subscribe(ObservableSource<?>[] paramArrayOfObservableSource, int paramInt)
    {
      ObservableWithLatestFromMany.WithLatestInnerObserver[] arrayOfWithLatestInnerObserver = this.observers;
      AtomicReference localAtomicReference = this.upstream;
      for (int i = 0; (i < paramInt) && (!DisposableHelper.isDisposed((Disposable)localAtomicReference.get())) && (!this.done); i++) {
        paramArrayOfObservableSource[i].subscribe(arrayOfWithLatestInnerObserver[i]);
      }
    }
  }
  
  static final class WithLatestInnerObserver
    extends AtomicReference<Disposable>
    implements Observer<Object>
  {
    private static final long serialVersionUID = 3256684027868224024L;
    boolean hasValue;
    final int index;
    final ObservableWithLatestFromMany.WithLatestFromObserver<?, ?> parent;
    
    WithLatestInnerObserver(ObservableWithLatestFromMany.WithLatestFromObserver<?, ?> paramWithLatestFromObserver, int paramInt)
    {
      this.parent = paramWithLatestFromObserver;
      this.index = paramInt;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public void onComplete()
    {
      this.parent.innerComplete(this.index, this.hasValue);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.innerError(this.index, paramThrowable);
    }
    
    public void onNext(Object paramObject)
    {
      if (!this.hasValue) {
        this.hasValue = true;
      }
      this.parent.innerNext(this.index, paramObject);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
  }
}
