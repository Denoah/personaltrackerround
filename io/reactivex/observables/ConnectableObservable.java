package io.reactivex.observables;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableAutoConnect;
import io.reactivex.internal.operators.observable.ObservableRefCount;
import io.reactivex.internal.util.ConnectConsumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public abstract class ConnectableObservable<T>
  extends Observable<T>
{
  public ConnectableObservable() {}
  
  public Observable<T> autoConnect()
  {
    return autoConnect(1);
  }
  
  public Observable<T> autoConnect(int paramInt)
  {
    return autoConnect(paramInt, Functions.emptyConsumer());
  }
  
  public Observable<T> autoConnect(int paramInt, Consumer<? super Disposable> paramConsumer)
  {
    if (paramInt <= 0)
    {
      connect(paramConsumer);
      return RxJavaPlugins.onAssembly(this);
    }
    return RxJavaPlugins.onAssembly(new ObservableAutoConnect(this, paramInt, paramConsumer));
  }
  
  public final Disposable connect()
  {
    ConnectConsumer localConnectConsumer = new ConnectConsumer();
    connect(localConnectConsumer);
    return localConnectConsumer.disposable;
  }
  
  public abstract void connect(Consumer<? super Disposable> paramConsumer);
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public Observable<T> refCount()
  {
    return RxJavaPlugins.onAssembly(new ObservableRefCount(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> refCount(int paramInt)
  {
    return refCount(paramInt, 0L, TimeUnit.NANOSECONDS, Schedulers.trampoline());
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> refCount(int paramInt, long paramLong, TimeUnit paramTimeUnit)
  {
    return refCount(paramInt, paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> refCount(int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.verifyPositive(paramInt, "subscriberCount");
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableRefCount(this, paramInt, paramLong, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> refCount(long paramLong, TimeUnit paramTimeUnit)
  {
    return refCount(1, paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> refCount(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return refCount(1, paramLong, paramTimeUnit, paramScheduler);
  }
}
