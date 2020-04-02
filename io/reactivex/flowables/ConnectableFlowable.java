package io.reactivex.flowables;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.flowable.FlowableAutoConnect;
import io.reactivex.internal.operators.flowable.FlowableRefCount;
import io.reactivex.internal.util.ConnectConsumer;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public abstract class ConnectableFlowable<T>
  extends Flowable<T>
{
  public ConnectableFlowable() {}
  
  public Flowable<T> autoConnect()
  {
    return autoConnect(1);
  }
  
  public Flowable<T> autoConnect(int paramInt)
  {
    return autoConnect(paramInt, Functions.emptyConsumer());
  }
  
  public Flowable<T> autoConnect(int paramInt, Consumer<? super Disposable> paramConsumer)
  {
    if (paramInt <= 0)
    {
      connect(paramConsumer);
      return RxJavaPlugins.onAssembly(this);
    }
    return RxJavaPlugins.onAssembly(new FlowableAutoConnect(this, paramInt, paramConsumer));
  }
  
  public final Disposable connect()
  {
    ConnectConsumer localConnectConsumer = new ConnectConsumer();
    connect(localConnectConsumer);
    return localConnectConsumer.disposable;
  }
  
  public abstract void connect(Consumer<? super Disposable> paramConsumer);
  
  @BackpressureSupport(BackpressureKind.PASS_THROUGH)
  @CheckReturnValue
  @SchedulerSupport("none")
  public Flowable<T> refCount()
  {
    return RxJavaPlugins.onAssembly(new FlowableRefCount(this));
  }
  
  @BackpressureSupport(BackpressureKind.PASS_THROUGH)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> refCount(int paramInt)
  {
    return refCount(paramInt, 0L, TimeUnit.NANOSECONDS, Schedulers.trampoline());
  }
  
  @BackpressureSupport(BackpressureKind.PASS_THROUGH)
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Flowable<T> refCount(int paramInt, long paramLong, TimeUnit paramTimeUnit)
  {
    return refCount(paramInt, paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @BackpressureSupport(BackpressureKind.PASS_THROUGH)
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Flowable<T> refCount(int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.verifyPositive(paramInt, "subscriberCount");
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new FlowableRefCount(this, paramInt, paramLong, paramTimeUnit, paramScheduler));
  }
  
  @BackpressureSupport(BackpressureKind.PASS_THROUGH)
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Flowable<T> refCount(long paramLong, TimeUnit paramTimeUnit)
  {
    return refCount(1, paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @BackpressureSupport(BackpressureKind.PASS_THROUGH)
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Flowable<T> refCount(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return refCount(1, paramLong, paramTimeUnit, paramScheduler);
  }
}
