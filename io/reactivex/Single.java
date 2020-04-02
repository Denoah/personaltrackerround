package io.reactivex;

import io.reactivex.annotations.BackpressureKind;
import io.reactivex.annotations.BackpressureSupport;
import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.functions.Function6;
import io.reactivex.functions.Function7;
import io.reactivex.functions.Function8;
import io.reactivex.functions.Function9;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.fuseable.FuseToMaybe;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.observers.BiConsumerSingleObserver;
import io.reactivex.internal.observers.BlockingMultiObserver;
import io.reactivex.internal.observers.ConsumerSingleObserver;
import io.reactivex.internal.observers.FutureSingleObserver;
import io.reactivex.internal.operators.completable.CompletableFromSingle;
import io.reactivex.internal.operators.completable.CompletableToFlowable;
import io.reactivex.internal.operators.flowable.FlowableConcatMap;
import io.reactivex.internal.operators.flowable.FlowableConcatMapPublisher;
import io.reactivex.internal.operators.flowable.FlowableFlatMapPublisher;
import io.reactivex.internal.operators.flowable.FlowableSingleSingle;
import io.reactivex.internal.operators.maybe.MaybeFilterSingle;
import io.reactivex.internal.operators.maybe.MaybeFromSingle;
import io.reactivex.internal.operators.mixed.SingleFlatMapObservable;
import io.reactivex.internal.operators.observable.ObservableConcatMap;
import io.reactivex.internal.operators.observable.ObservableSingleSingle;
import io.reactivex.internal.operators.single.SingleAmb;
import io.reactivex.internal.operators.single.SingleCache;
import io.reactivex.internal.operators.single.SingleContains;
import io.reactivex.internal.operators.single.SingleCreate;
import io.reactivex.internal.operators.single.SingleDefer;
import io.reactivex.internal.operators.single.SingleDelay;
import io.reactivex.internal.operators.single.SingleDelayWithCompletable;
import io.reactivex.internal.operators.single.SingleDelayWithObservable;
import io.reactivex.internal.operators.single.SingleDelayWithPublisher;
import io.reactivex.internal.operators.single.SingleDelayWithSingle;
import io.reactivex.internal.operators.single.SingleDematerialize;
import io.reactivex.internal.operators.single.SingleDetach;
import io.reactivex.internal.operators.single.SingleDoAfterSuccess;
import io.reactivex.internal.operators.single.SingleDoAfterTerminate;
import io.reactivex.internal.operators.single.SingleDoFinally;
import io.reactivex.internal.operators.single.SingleDoOnDispose;
import io.reactivex.internal.operators.single.SingleDoOnError;
import io.reactivex.internal.operators.single.SingleDoOnEvent;
import io.reactivex.internal.operators.single.SingleDoOnSubscribe;
import io.reactivex.internal.operators.single.SingleDoOnSuccess;
import io.reactivex.internal.operators.single.SingleDoOnTerminate;
import io.reactivex.internal.operators.single.SingleEquals;
import io.reactivex.internal.operators.single.SingleError;
import io.reactivex.internal.operators.single.SingleFlatMap;
import io.reactivex.internal.operators.single.SingleFlatMapCompletable;
import io.reactivex.internal.operators.single.SingleFlatMapIterableFlowable;
import io.reactivex.internal.operators.single.SingleFlatMapIterableObservable;
import io.reactivex.internal.operators.single.SingleFlatMapMaybe;
import io.reactivex.internal.operators.single.SingleFlatMapPublisher;
import io.reactivex.internal.operators.single.SingleFromCallable;
import io.reactivex.internal.operators.single.SingleFromPublisher;
import io.reactivex.internal.operators.single.SingleFromUnsafeSource;
import io.reactivex.internal.operators.single.SingleHide;
import io.reactivex.internal.operators.single.SingleInternalHelper;
import io.reactivex.internal.operators.single.SingleJust;
import io.reactivex.internal.operators.single.SingleLift;
import io.reactivex.internal.operators.single.SingleMap;
import io.reactivex.internal.operators.single.SingleMaterialize;
import io.reactivex.internal.operators.single.SingleNever;
import io.reactivex.internal.operators.single.SingleObserveOn;
import io.reactivex.internal.operators.single.SingleOnErrorReturn;
import io.reactivex.internal.operators.single.SingleResumeNext;
import io.reactivex.internal.operators.single.SingleSubscribeOn;
import io.reactivex.internal.operators.single.SingleTakeUntil;
import io.reactivex.internal.operators.single.SingleTimeout;
import io.reactivex.internal.operators.single.SingleTimer;
import io.reactivex.internal.operators.single.SingleToFlowable;
import io.reactivex.internal.operators.single.SingleToObservable;
import io.reactivex.internal.operators.single.SingleUnsubscribeOn;
import io.reactivex.internal.operators.single.SingleUsing;
import io.reactivex.internal.operators.single.SingleZipArray;
import io.reactivex.internal.operators.single.SingleZipIterable;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public abstract class Single<T>
  implements SingleSource<T>
{
  public Single() {}
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> amb(Iterable<? extends SingleSource<? extends T>> paramIterable)
  {
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    return RxJavaPlugins.onAssembly(new SingleAmb(null, paramIterable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> ambArray(SingleSource<? extends T>... paramVarArgs)
  {
    if (paramVarArgs.length == 0) {
      return error(SingleInternalHelper.emptyThrower());
    }
    if (paramVarArgs.length == 1) {
      return wrap(paramVarArgs[0]);
    }
    return RxJavaPlugins.onAssembly(new SingleAmb(paramVarArgs, null));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(SingleSource<? extends T> paramSingleSource1, SingleSource<? extends T> paramSingleSource2)
  {
    ObjectHelper.requireNonNull(paramSingleSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source2 is null");
    return concat(Flowable.fromArray(new SingleSource[] { paramSingleSource1, paramSingleSource2 }));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(SingleSource<? extends T> paramSingleSource1, SingleSource<? extends T> paramSingleSource2, SingleSource<? extends T> paramSingleSource3)
  {
    ObjectHelper.requireNonNull(paramSingleSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source3 is null");
    return concat(Flowable.fromArray(new SingleSource[] { paramSingleSource1, paramSingleSource2, paramSingleSource3 }));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(SingleSource<? extends T> paramSingleSource1, SingleSource<? extends T> paramSingleSource2, SingleSource<? extends T> paramSingleSource3, SingleSource<? extends T> paramSingleSource4)
  {
    ObjectHelper.requireNonNull(paramSingleSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source3 is null");
    ObjectHelper.requireNonNull(paramSingleSource4, "source4 is null");
    return concat(Flowable.fromArray(new SingleSource[] { paramSingleSource1, paramSingleSource2, paramSingleSource3, paramSingleSource4 }));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(Iterable<? extends SingleSource<? extends T>> paramIterable)
  {
    return concat(Flowable.fromIterable(paramIterable));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(Publisher<? extends SingleSource<? extends T>> paramPublisher)
  {
    return concat(paramPublisher, 2);
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(Publisher<? extends SingleSource<? extends T>> paramPublisher, int paramInt)
  {
    ObjectHelper.requireNonNull(paramPublisher, "sources is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    return RxJavaPlugins.onAssembly(new FlowableConcatMapPublisher(paramPublisher, SingleInternalHelper.toFlowable(), paramInt, ErrorMode.IMMEDIATE));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concat(ObservableSource<? extends SingleSource<? extends T>> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "sources is null");
    return RxJavaPlugins.onAssembly(new ObservableConcatMap(paramObservableSource, SingleInternalHelper.toObservable(), 2, ErrorMode.IMMEDIATE));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concatArray(SingleSource<? extends T>... paramVarArgs)
  {
    return RxJavaPlugins.onAssembly(new FlowableConcatMap(Flowable.fromArray(paramVarArgs), SingleInternalHelper.toFlowable(), 2, ErrorMode.BOUNDARY));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concatArrayEager(SingleSource<? extends T>... paramVarArgs)
  {
    return Flowable.fromArray(paramVarArgs).concatMapEager(SingleInternalHelper.toFlowable());
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concatEager(Iterable<? extends SingleSource<? extends T>> paramIterable)
  {
    return Flowable.fromIterable(paramIterable).concatMapEager(SingleInternalHelper.toFlowable());
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concatEager(Publisher<? extends SingleSource<? extends T>> paramPublisher)
  {
    return Flowable.fromPublisher(paramPublisher).concatMapEager(SingleInternalHelper.toFlowable());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> create(SingleOnSubscribe<T> paramSingleOnSubscribe)
  {
    ObjectHelper.requireNonNull(paramSingleOnSubscribe, "source is null");
    return RxJavaPlugins.onAssembly(new SingleCreate(paramSingleOnSubscribe));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> defer(Callable<? extends SingleSource<? extends T>> paramCallable)
  {
    ObjectHelper.requireNonNull(paramCallable, "singleSupplier is null");
    return RxJavaPlugins.onAssembly(new SingleDefer(paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<Boolean> equals(SingleSource<? extends T> paramSingleSource1, SingleSource<? extends T> paramSingleSource2)
  {
    ObjectHelper.requireNonNull(paramSingleSource1, "first is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "second is null");
    return RxJavaPlugins.onAssembly(new SingleEquals(paramSingleSource1, paramSingleSource2));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> error(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "exception is null");
    return error(Functions.justCallable(paramThrowable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> error(Callable<? extends Throwable> paramCallable)
  {
    ObjectHelper.requireNonNull(paramCallable, "errorSupplier is null");
    return RxJavaPlugins.onAssembly(new SingleError(paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> fromCallable(Callable<? extends T> paramCallable)
  {
    ObjectHelper.requireNonNull(paramCallable, "callable is null");
    return RxJavaPlugins.onAssembly(new SingleFromCallable(paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> fromFuture(Future<? extends T> paramFuture)
  {
    return toSingle(Flowable.fromFuture(paramFuture));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> fromFuture(Future<? extends T> paramFuture, long paramLong, TimeUnit paramTimeUnit)
  {
    return toSingle(Flowable.fromFuture(paramFuture, paramLong, paramTimeUnit));
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public static <T> Single<T> fromFuture(Future<? extends T> paramFuture, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return toSingle(Flowable.fromFuture(paramFuture, paramLong, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public static <T> Single<T> fromFuture(Future<? extends T> paramFuture, Scheduler paramScheduler)
  {
    return toSingle(Flowable.fromFuture(paramFuture, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> fromObservable(ObservableSource<? extends T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "observableSource is null");
    return RxJavaPlugins.onAssembly(new ObservableSingleSingle(paramObservableSource, null));
  }
  
  @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> fromPublisher(Publisher<? extends T> paramPublisher)
  {
    ObjectHelper.requireNonNull(paramPublisher, "publisher is null");
    return RxJavaPlugins.onAssembly(new SingleFromPublisher(paramPublisher));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> just(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "item is null");
    return RxJavaPlugins.onAssembly(new SingleJust(paramT));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> merge(SingleSource<? extends T> paramSingleSource1, SingleSource<? extends T> paramSingleSource2)
  {
    ObjectHelper.requireNonNull(paramSingleSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source2 is null");
    return merge(Flowable.fromArray(new SingleSource[] { paramSingleSource1, paramSingleSource2 }));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> merge(SingleSource<? extends T> paramSingleSource1, SingleSource<? extends T> paramSingleSource2, SingleSource<? extends T> paramSingleSource3)
  {
    ObjectHelper.requireNonNull(paramSingleSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source3 is null");
    return merge(Flowable.fromArray(new SingleSource[] { paramSingleSource1, paramSingleSource2, paramSingleSource3 }));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> merge(SingleSource<? extends T> paramSingleSource1, SingleSource<? extends T> paramSingleSource2, SingleSource<? extends T> paramSingleSource3, SingleSource<? extends T> paramSingleSource4)
  {
    ObjectHelper.requireNonNull(paramSingleSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source3 is null");
    ObjectHelper.requireNonNull(paramSingleSource4, "source4 is null");
    return merge(Flowable.fromArray(new SingleSource[] { paramSingleSource1, paramSingleSource2, paramSingleSource3, paramSingleSource4 }));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> merge(Iterable<? extends SingleSource<? extends T>> paramIterable)
  {
    return merge(Flowable.fromIterable(paramIterable));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> merge(Publisher<? extends SingleSource<? extends T>> paramPublisher)
  {
    ObjectHelper.requireNonNull(paramPublisher, "sources is null");
    return RxJavaPlugins.onAssembly(new FlowableFlatMapPublisher(paramPublisher, SingleInternalHelper.toFlowable(), false, Integer.MAX_VALUE, Flowable.bufferSize()));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> merge(SingleSource<? extends SingleSource<? extends T>> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "source is null");
    return RxJavaPlugins.onAssembly(new SingleFlatMap(paramSingleSource, Functions.identity()));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeDelayError(SingleSource<? extends T> paramSingleSource1, SingleSource<? extends T> paramSingleSource2)
  {
    ObjectHelper.requireNonNull(paramSingleSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source2 is null");
    return mergeDelayError(Flowable.fromArray(new SingleSource[] { paramSingleSource1, paramSingleSource2 }));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeDelayError(SingleSource<? extends T> paramSingleSource1, SingleSource<? extends T> paramSingleSource2, SingleSource<? extends T> paramSingleSource3)
  {
    ObjectHelper.requireNonNull(paramSingleSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source3 is null");
    return mergeDelayError(Flowable.fromArray(new SingleSource[] { paramSingleSource1, paramSingleSource2, paramSingleSource3 }));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeDelayError(SingleSource<? extends T> paramSingleSource1, SingleSource<? extends T> paramSingleSource2, SingleSource<? extends T> paramSingleSource3, SingleSource<? extends T> paramSingleSource4)
  {
    ObjectHelper.requireNonNull(paramSingleSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source3 is null");
    ObjectHelper.requireNonNull(paramSingleSource4, "source4 is null");
    return mergeDelayError(Flowable.fromArray(new SingleSource[] { paramSingleSource1, paramSingleSource2, paramSingleSource3, paramSingleSource4 }));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeDelayError(Iterable<? extends SingleSource<? extends T>> paramIterable)
  {
    return mergeDelayError(Flowable.fromIterable(paramIterable));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeDelayError(Publisher<? extends SingleSource<? extends T>> paramPublisher)
  {
    ObjectHelper.requireNonNull(paramPublisher, "sources is null");
    return RxJavaPlugins.onAssembly(new FlowableFlatMapPublisher(paramPublisher, SingleInternalHelper.toFlowable(), true, Integer.MAX_VALUE, Flowable.bufferSize()));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> never()
  {
    return RxJavaPlugins.onAssembly(SingleNever.INSTANCE);
  }
  
  private Single<T> timeout0(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, SingleSource<? extends T> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new SingleTimeout(this, paramLong, paramTimeUnit, paramScheduler, paramSingleSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public static Single<Long> timer(long paramLong, TimeUnit paramTimeUnit)
  {
    return timer(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public static Single<Long> timer(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new SingleTimer(paramLong, paramTimeUnit, paramScheduler));
  }
  
  private static <T> Single<T> toSingle(Flowable<T> paramFlowable)
  {
    return RxJavaPlugins.onAssembly(new FlowableSingleSingle(paramFlowable, null));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> unsafeCreate(SingleSource<T> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "onSubscribe is null");
    if (!(paramSingleSource instanceof Single)) {
      return RxJavaPlugins.onAssembly(new SingleFromUnsafeSource(paramSingleSource));
    }
    throw new IllegalArgumentException("unsafeCreate(Single) should be upgraded");
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, U> Single<T> using(Callable<U> paramCallable, Function<? super U, ? extends SingleSource<? extends T>> paramFunction, Consumer<? super U> paramConsumer)
  {
    return using(paramCallable, paramFunction, paramConsumer, true);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, U> Single<T> using(Callable<U> paramCallable, Function<? super U, ? extends SingleSource<? extends T>> paramFunction, Consumer<? super U> paramConsumer, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramCallable, "resourceSupplier is null");
    ObjectHelper.requireNonNull(paramFunction, "singleFunction is null");
    ObjectHelper.requireNonNull(paramConsumer, "disposer is null");
    return RxJavaPlugins.onAssembly(new SingleUsing(paramCallable, paramFunction, paramConsumer, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<T> wrap(SingleSource<T> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "source is null");
    if ((paramSingleSource instanceof Single)) {
      return RxJavaPlugins.onAssembly((Single)paramSingleSource);
    }
    return RxJavaPlugins.onAssembly(new SingleFromUnsafeSource(paramSingleSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Single<R> zip(SingleSource<? extends T1> paramSingleSource, SingleSource<? extends T2> paramSingleSource1, SingleSource<? extends T3> paramSingleSource2, SingleSource<? extends T4> paramSingleSource3, SingleSource<? extends T5> paramSingleSource4, SingleSource<? extends T6> paramSingleSource5, SingleSource<? extends T7> paramSingleSource6, SingleSource<? extends T8> paramSingleSource7, SingleSource<? extends T9> paramSingleSource8, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> paramFunction9)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramSingleSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramSingleSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramSingleSource6, "source7 is null");
    ObjectHelper.requireNonNull(paramSingleSource7, "source8 is null");
    ObjectHelper.requireNonNull(paramSingleSource8, "source9 is null");
    return zipArray(Functions.toFunction(paramFunction9), new SingleSource[] { paramSingleSource, paramSingleSource1, paramSingleSource2, paramSingleSource3, paramSingleSource4, paramSingleSource5, paramSingleSource6, paramSingleSource7, paramSingleSource8 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Single<R> zip(SingleSource<? extends T1> paramSingleSource, SingleSource<? extends T2> paramSingleSource1, SingleSource<? extends T3> paramSingleSource2, SingleSource<? extends T4> paramSingleSource3, SingleSource<? extends T5> paramSingleSource4, SingleSource<? extends T6> paramSingleSource5, SingleSource<? extends T7> paramSingleSource6, SingleSource<? extends T8> paramSingleSource7, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> paramFunction8)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramSingleSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramSingleSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramSingleSource6, "source7 is null");
    ObjectHelper.requireNonNull(paramSingleSource7, "source8 is null");
    return zipArray(Functions.toFunction(paramFunction8), new SingleSource[] { paramSingleSource, paramSingleSource1, paramSingleSource2, paramSingleSource3, paramSingleSource4, paramSingleSource5, paramSingleSource6, paramSingleSource7 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, R> Single<R> zip(SingleSource<? extends T1> paramSingleSource, SingleSource<? extends T2> paramSingleSource1, SingleSource<? extends T3> paramSingleSource2, SingleSource<? extends T4> paramSingleSource3, SingleSource<? extends T5> paramSingleSource4, SingleSource<? extends T6> paramSingleSource5, SingleSource<? extends T7> paramSingleSource6, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> paramFunction7)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramSingleSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramSingleSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramSingleSource6, "source7 is null");
    return zipArray(Functions.toFunction(paramFunction7), new SingleSource[] { paramSingleSource, paramSingleSource1, paramSingleSource2, paramSingleSource3, paramSingleSource4, paramSingleSource5, paramSingleSource6 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, R> Single<R> zip(SingleSource<? extends T1> paramSingleSource, SingleSource<? extends T2> paramSingleSource1, SingleSource<? extends T3> paramSingleSource2, SingleSource<? extends T4> paramSingleSource3, SingleSource<? extends T5> paramSingleSource4, SingleSource<? extends T6> paramSingleSource5, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> paramFunction6)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramSingleSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramSingleSource5, "source6 is null");
    return zipArray(Functions.toFunction(paramFunction6), new SingleSource[] { paramSingleSource, paramSingleSource1, paramSingleSource2, paramSingleSource3, paramSingleSource4, paramSingleSource5 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, R> Single<R> zip(SingleSource<? extends T1> paramSingleSource, SingleSource<? extends T2> paramSingleSource1, SingleSource<? extends T3> paramSingleSource2, SingleSource<? extends T4> paramSingleSource3, SingleSource<? extends T5> paramSingleSource4, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> paramFunction5)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramSingleSource4, "source5 is null");
    return zipArray(Functions.toFunction(paramFunction5), new SingleSource[] { paramSingleSource, paramSingleSource1, paramSingleSource2, paramSingleSource3, paramSingleSource4 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, R> Single<R> zip(SingleSource<? extends T1> paramSingleSource, SingleSource<? extends T2> paramSingleSource1, SingleSource<? extends T3> paramSingleSource2, SingleSource<? extends T4> paramSingleSource3, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> paramFunction4)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramSingleSource3, "source4 is null");
    return zipArray(Functions.toFunction(paramFunction4), new SingleSource[] { paramSingleSource, paramSingleSource1, paramSingleSource2, paramSingleSource3 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, R> Single<R> zip(SingleSource<? extends T1> paramSingleSource, SingleSource<? extends T2> paramSingleSource1, SingleSource<? extends T3> paramSingleSource2, Function3<? super T1, ? super T2, ? super T3, ? extends R> paramFunction3)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramSingleSource2, "source3 is null");
    return zipArray(Functions.toFunction(paramFunction3), new SingleSource[] { paramSingleSource, paramSingleSource1, paramSingleSource2 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, R> Single<R> zip(SingleSource<? extends T1> paramSingleSource, SingleSource<? extends T2> paramSingleSource1, BiFunction<? super T1, ? super T2, ? extends R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "source1 is null");
    ObjectHelper.requireNonNull(paramSingleSource1, "source2 is null");
    return zipArray(Functions.toFunction(paramBiFunction), new SingleSource[] { paramSingleSource, paramSingleSource1 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Single<R> zip(Iterable<? extends SingleSource<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "zipper is null");
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    return RxJavaPlugins.onAssembly(new SingleZipIterable(paramIterable, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Single<R> zipArray(Function<? super Object[], ? extends R> paramFunction, SingleSource<? extends T>... paramVarArgs)
  {
    ObjectHelper.requireNonNull(paramFunction, "zipper is null");
    ObjectHelper.requireNonNull(paramVarArgs, "sources is null");
    if (paramVarArgs.length == 0) {
      return error(new NoSuchElementException());
    }
    return RxJavaPlugins.onAssembly(new SingleZipArray(paramVarArgs, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> ambWith(SingleSource<? extends T> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "other is null");
    return ambArray(new SingleSource[] { this, paramSingleSource });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> R as(SingleConverter<T, ? extends R> paramSingleConverter)
  {
    return ((SingleConverter)ObjectHelper.requireNonNull(paramSingleConverter, "converter is null")).apply(this);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final T blockingGet()
  {
    BlockingMultiObserver localBlockingMultiObserver = new BlockingMultiObserver();
    subscribe(localBlockingMultiObserver);
    return localBlockingMultiObserver.blockingGet();
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> cache()
  {
    return RxJavaPlugins.onAssembly(new SingleCache(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Single<U> cast(Class<? extends U> paramClass)
  {
    ObjectHelper.requireNonNull(paramClass, "clazz is null");
    return map(Functions.castFunction(paramClass));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Single<R> compose(SingleTransformer<? super T, ? extends R> paramSingleTransformer)
  {
    return wrap(((SingleTransformer)ObjectHelper.requireNonNull(paramSingleTransformer, "transformer is null")).apply(this));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> concatWith(SingleSource<? extends T> paramSingleSource)
  {
    return concat(this, paramSingleSource);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Boolean> contains(Object paramObject)
  {
    return contains(paramObject, ObjectHelper.equalsPredicate());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Boolean> contains(Object paramObject, BiPredicate<Object, Object> paramBiPredicate)
  {
    ObjectHelper.requireNonNull(paramObject, "value is null");
    ObjectHelper.requireNonNull(paramBiPredicate, "comparer is null");
    return RxJavaPlugins.onAssembly(new SingleContains(this, paramObject, paramBiPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Single<T> delay(long paramLong, TimeUnit paramTimeUnit)
  {
    return delay(paramLong, paramTimeUnit, Schedulers.computation(), false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Single<T> delay(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return delay(paramLong, paramTimeUnit, paramScheduler, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Single<T> delay(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new SingleDelay(this, paramLong, paramTimeUnit, paramScheduler, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Single<T> delay(long paramLong, TimeUnit paramTimeUnit, boolean paramBoolean)
  {
    return delay(paramLong, paramTimeUnit, Schedulers.computation(), paramBoolean);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Single<T> delaySubscription(long paramLong, TimeUnit paramTimeUnit)
  {
    return delaySubscription(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Single<T> delaySubscription(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return delaySubscription(Observable.timer(paramLong, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> delaySubscription(CompletableSource paramCompletableSource)
  {
    ObjectHelper.requireNonNull(paramCompletableSource, "other is null");
    return RxJavaPlugins.onAssembly(new SingleDelayWithCompletable(this, paramCompletableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Single<T> delaySubscription(ObservableSource<U> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return RxJavaPlugins.onAssembly(new SingleDelayWithObservable(this, paramObservableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Single<T> delaySubscription(SingleSource<U> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "other is null");
    return RxJavaPlugins.onAssembly(new SingleDelayWithSingle(this, paramSingleSource));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Single<T> delaySubscription(Publisher<U> paramPublisher)
  {
    ObjectHelper.requireNonNull(paramPublisher, "other is null");
    return RxJavaPlugins.onAssembly(new SingleDelayWithPublisher(this, paramPublisher));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Maybe<R> dematerialize(Function<? super T, Notification<R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "selector is null");
    return RxJavaPlugins.onAssembly(new SingleDematerialize(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> doAfterSuccess(Consumer<? super T> paramConsumer)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onAfterSuccess is null");
    return RxJavaPlugins.onAssembly(new SingleDoAfterSuccess(this, paramConsumer));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> doAfterTerminate(Action paramAction)
  {
    ObjectHelper.requireNonNull(paramAction, "onAfterTerminate is null");
    return RxJavaPlugins.onAssembly(new SingleDoAfterTerminate(this, paramAction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> doFinally(Action paramAction)
  {
    ObjectHelper.requireNonNull(paramAction, "onFinally is null");
    return RxJavaPlugins.onAssembly(new SingleDoFinally(this, paramAction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> doOnDispose(Action paramAction)
  {
    ObjectHelper.requireNonNull(paramAction, "onDispose is null");
    return RxJavaPlugins.onAssembly(new SingleDoOnDispose(this, paramAction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> doOnError(Consumer<? super Throwable> paramConsumer)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onError is null");
    return RxJavaPlugins.onAssembly(new SingleDoOnError(this, paramConsumer));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> doOnEvent(BiConsumer<? super T, ? super Throwable> paramBiConsumer)
  {
    ObjectHelper.requireNonNull(paramBiConsumer, "onEvent is null");
    return RxJavaPlugins.onAssembly(new SingleDoOnEvent(this, paramBiConsumer));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> doOnSubscribe(Consumer<? super Disposable> paramConsumer)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onSubscribe is null");
    return RxJavaPlugins.onAssembly(new SingleDoOnSubscribe(this, paramConsumer));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> doOnSuccess(Consumer<? super T> paramConsumer)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onSuccess is null");
    return RxJavaPlugins.onAssembly(new SingleDoOnSuccess(this, paramConsumer));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> doOnTerminate(Action paramAction)
  {
    ObjectHelper.requireNonNull(paramAction, "onTerminate is null");
    return RxJavaPlugins.onAssembly(new SingleDoOnTerminate(this, paramAction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> filter(Predicate<? super T> paramPredicate)
  {
    ObjectHelper.requireNonNull(paramPredicate, "predicate is null");
    return RxJavaPlugins.onAssembly(new MaybeFilterSingle(this, paramPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Single<R> flatMap(Function<? super T, ? extends SingleSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new SingleFlatMap(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new SingleFlatMapCompletable(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Maybe<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new SingleFlatMapMaybe(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMapObservable(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new SingleFlatMapObservable(this, paramFunction));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Flowable<R> flatMapPublisher(Function<? super T, ? extends Publisher<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new SingleFlatMapPublisher(this, paramFunction));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Flowable<U> flattenAsFlowable(Function<? super T, ? extends Iterable<? extends U>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new SingleFlatMapIterableFlowable(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<U> flattenAsObservable(Function<? super T, ? extends Iterable<? extends U>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new SingleFlatMapIterableObservable(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> hide()
  {
    return RxJavaPlugins.onAssembly(new SingleHide(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable ignoreElement()
  {
    return RxJavaPlugins.onAssembly(new CompletableFromSingle(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Single<R> lift(SingleOperator<? extends R, ? super T> paramSingleOperator)
  {
    ObjectHelper.requireNonNull(paramSingleOperator, "lift is null");
    return RxJavaPlugins.onAssembly(new SingleLift(this, paramSingleOperator));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Single<R> map(Function<? super T, ? extends R> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new SingleMap(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Notification<T>> materialize()
  {
    return RxJavaPlugins.onAssembly(new SingleMaterialize(this));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> mergeWith(SingleSource<? extends T> paramSingleSource)
  {
    return merge(this, paramSingleSource);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Single<T> observeOn(Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new SingleObserveOn(this, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> onErrorResumeNext(Single<? extends T> paramSingle)
  {
    ObjectHelper.requireNonNull(paramSingle, "resumeSingleInCaseOfError is null");
    return onErrorResumeNext(Functions.justFunction(paramSingle));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> onErrorResumeNext(Function<? super Throwable, ? extends SingleSource<? extends T>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "resumeFunctionInCaseOfError is null");
    return RxJavaPlugins.onAssembly(new SingleResumeNext(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> onErrorReturn(Function<Throwable, ? extends T> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "resumeFunction is null");
    return RxJavaPlugins.onAssembly(new SingleOnErrorReturn(this, paramFunction, null));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> onErrorReturnItem(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "value is null");
    return RxJavaPlugins.onAssembly(new SingleOnErrorReturn(this, null, paramT));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> onTerminateDetach()
  {
    return RxJavaPlugins.onAssembly(new SingleDetach(this));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> repeat()
  {
    return toFlowable().repeat();
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> repeat(long paramLong)
  {
    return toFlowable().repeat(paramLong);
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> repeatUntil(BooleanSupplier paramBooleanSupplier)
  {
    return toFlowable().repeatUntil(paramBooleanSupplier);
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> repeatWhen(Function<? super Flowable<Object>, ? extends Publisher<?>> paramFunction)
  {
    return toFlowable().repeatWhen(paramFunction);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> retry()
  {
    return toSingle(toFlowable().retry());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> retry(long paramLong)
  {
    return toSingle(toFlowable().retry(paramLong));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> retry(long paramLong, Predicate<? super Throwable> paramPredicate)
  {
    return toSingle(toFlowable().retry(paramLong, paramPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> retry(BiPredicate<? super Integer, ? super Throwable> paramBiPredicate)
  {
    return toSingle(toFlowable().retry(paramBiPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> retry(Predicate<? super Throwable> paramPredicate)
  {
    return toSingle(toFlowable().retry(paramPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> retryWhen(Function<? super Flowable<Throwable>, ? extends Publisher<?>> paramFunction)
  {
    return toSingle(toFlowable().retryWhen(paramFunction));
  }
  
  @SchedulerSupport("none")
  public final Disposable subscribe()
  {
    return subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable subscribe(BiConsumer<? super T, ? super Throwable> paramBiConsumer)
  {
    ObjectHelper.requireNonNull(paramBiConsumer, "onCallback is null");
    paramBiConsumer = new BiConsumerSingleObserver(paramBiConsumer);
    subscribe(paramBiConsumer);
    return paramBiConsumer;
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable subscribe(Consumer<? super T> paramConsumer)
  {
    return subscribe(paramConsumer, Functions.ON_ERROR_MISSING);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable subscribe(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onSuccess is null");
    ObjectHelper.requireNonNull(paramConsumer1, "onError is null");
    paramConsumer = new ConsumerSingleObserver(paramConsumer, paramConsumer1);
    subscribe(paramConsumer);
    return paramConsumer;
  }
  
  /* Error */
  @SchedulerSupport("none")
  public final void subscribe(SingleObserver<? super T> paramSingleObserver)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 849
    //   4: invokestatic 26	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   7: pop
    //   8: aload_0
    //   9: aload_1
    //   10: invokestatic 853	io/reactivex/plugins/RxJavaPlugins:onSubscribe	(Lio/reactivex/Single;Lio/reactivex/SingleObserver;)Lio/reactivex/SingleObserver;
    //   13: astore_1
    //   14: aload_1
    //   15: ldc_w 855
    //   18: invokestatic 26	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   21: pop
    //   22: aload_0
    //   23: aload_1
    //   24: invokevirtual 858	io/reactivex/Single:subscribeActual	(Lio/reactivex/SingleObserver;)V
    //   27: return
    //   28: astore_2
    //   29: aload_2
    //   30: invokestatic 864	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   33: new 847	java/lang/NullPointerException
    //   36: dup
    //   37: ldc_w 866
    //   40: invokespecial 867	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   43: astore_1
    //   44: aload_1
    //   45: aload_2
    //   46: invokevirtual 871	java/lang/NullPointerException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   49: pop
    //   50: aload_1
    //   51: athrow
    //   52: astore_1
    //   53: aload_1
    //   54: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	55	0	this	Single
    //   0	55	1	paramSingleObserver	SingleObserver<? super T>
    //   28	18	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   22	27	28	finally
    //   22	27	52	java/lang/NullPointerException
  }
  
  protected abstract void subscribeActual(SingleObserver<? super T> paramSingleObserver);
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Single<T> subscribeOn(Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new SingleSubscribeOn(this, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <E extends SingleObserver<? super T>> E subscribeWith(E paramE)
  {
    subscribe(paramE);
    return paramE;
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> takeUntil(CompletableSource paramCompletableSource)
  {
    ObjectHelper.requireNonNull(paramCompletableSource, "other is null");
    return takeUntil(new CompletableToFlowable(paramCompletableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <E> Single<T> takeUntil(SingleSource<? extends E> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "other is null");
    return takeUntil(new SingleToFlowable(paramSingleSource));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <E> Single<T> takeUntil(Publisher<E> paramPublisher)
  {
    ObjectHelper.requireNonNull(paramPublisher, "other is null");
    return RxJavaPlugins.onAssembly(new SingleTakeUntil(this, paramPublisher));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final TestObserver<T> test()
  {
    TestObserver localTestObserver = new TestObserver();
    subscribe(localTestObserver);
    return localTestObserver;
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final TestObserver<T> test(boolean paramBoolean)
  {
    TestObserver localTestObserver = new TestObserver();
    if (paramBoolean) {
      localTestObserver.cancel();
    }
    subscribe(localTestObserver);
    return localTestObserver;
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Single<T> timeout(long paramLong, TimeUnit paramTimeUnit)
  {
    return timeout0(paramLong, paramTimeUnit, Schedulers.computation(), null);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Single<T> timeout(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return timeout0(paramLong, paramTimeUnit, paramScheduler, null);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Single<T> timeout(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, SingleSource<? extends T> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "other is null");
    return timeout0(paramLong, paramTimeUnit, paramScheduler, paramSingleSource);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Single<T> timeout(long paramLong, TimeUnit paramTimeUnit, SingleSource<? extends T> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "other is null");
    return timeout0(paramLong, paramTimeUnit, Schedulers.computation(), paramSingleSource);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> R to(Function<? super Single<T>, R> paramFunction)
  {
    try
    {
      paramFunction = ((Function)ObjectHelper.requireNonNull(paramFunction, "convert is null")).apply(this);
      return paramFunction;
    }
    finally
    {
      Exceptions.throwIfFatal(paramFunction);
    }
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  @Deprecated
  public final Completable toCompletable()
  {
    return RxJavaPlugins.onAssembly(new CompletableFromSingle(this));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> toFlowable()
  {
    if ((this instanceof FuseToFlowable)) {
      return ((FuseToFlowable)this).fuseToFlowable();
    }
    return RxJavaPlugins.onAssembly(new SingleToFlowable(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Future<T> toFuture()
  {
    return (Future)subscribeWith(new FutureSingleObserver());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> toMaybe()
  {
    if ((this instanceof FuseToMaybe)) {
      return ((FuseToMaybe)this).fuseToMaybe();
    }
    return RxJavaPlugins.onAssembly(new MaybeFromSingle(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> toObservable()
  {
    if ((this instanceof FuseToObservable)) {
      return ((FuseToObservable)this).fuseToObservable();
    }
    return RxJavaPlugins.onAssembly(new SingleToObservable(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Single<T> unsubscribeOn(Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new SingleUnsubscribeOn(this, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Single<R> zipWith(SingleSource<U> paramSingleSource, BiFunction<? super T, ? super U, ? extends R> paramBiFunction)
  {
    return zip(this, paramSingleSource, paramBiFunction);
  }
}
