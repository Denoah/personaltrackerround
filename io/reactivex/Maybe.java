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
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.observers.BlockingMultiObserver;
import io.reactivex.internal.operators.flowable.FlowableConcatMapPublisher;
import io.reactivex.internal.operators.flowable.FlowableFlatMapPublisher;
import io.reactivex.internal.operators.maybe.MaybeAmb;
import io.reactivex.internal.operators.maybe.MaybeCache;
import io.reactivex.internal.operators.maybe.MaybeCallbackObserver;
import io.reactivex.internal.operators.maybe.MaybeConcatArray;
import io.reactivex.internal.operators.maybe.MaybeConcatArrayDelayError;
import io.reactivex.internal.operators.maybe.MaybeConcatIterable;
import io.reactivex.internal.operators.maybe.MaybeContains;
import io.reactivex.internal.operators.maybe.MaybeCount;
import io.reactivex.internal.operators.maybe.MaybeCreate;
import io.reactivex.internal.operators.maybe.MaybeDefer;
import io.reactivex.internal.operators.maybe.MaybeDelay;
import io.reactivex.internal.operators.maybe.MaybeDelayOtherPublisher;
import io.reactivex.internal.operators.maybe.MaybeDelaySubscriptionOtherPublisher;
import io.reactivex.internal.operators.maybe.MaybeDetach;
import io.reactivex.internal.operators.maybe.MaybeDoAfterSuccess;
import io.reactivex.internal.operators.maybe.MaybeDoFinally;
import io.reactivex.internal.operators.maybe.MaybeDoOnEvent;
import io.reactivex.internal.operators.maybe.MaybeDoOnTerminate;
import io.reactivex.internal.operators.maybe.MaybeEmpty;
import io.reactivex.internal.operators.maybe.MaybeEqualSingle;
import io.reactivex.internal.operators.maybe.MaybeError;
import io.reactivex.internal.operators.maybe.MaybeErrorCallable;
import io.reactivex.internal.operators.maybe.MaybeFilter;
import io.reactivex.internal.operators.maybe.MaybeFlatMapBiSelector;
import io.reactivex.internal.operators.maybe.MaybeFlatMapCompletable;
import io.reactivex.internal.operators.maybe.MaybeFlatMapIterableFlowable;
import io.reactivex.internal.operators.maybe.MaybeFlatMapIterableObservable;
import io.reactivex.internal.operators.maybe.MaybeFlatMapNotification;
import io.reactivex.internal.operators.maybe.MaybeFlatMapSingle;
import io.reactivex.internal.operators.maybe.MaybeFlatMapSingleElement;
import io.reactivex.internal.operators.maybe.MaybeFlatten;
import io.reactivex.internal.operators.maybe.MaybeFromAction;
import io.reactivex.internal.operators.maybe.MaybeFromCallable;
import io.reactivex.internal.operators.maybe.MaybeFromCompletable;
import io.reactivex.internal.operators.maybe.MaybeFromFuture;
import io.reactivex.internal.operators.maybe.MaybeFromRunnable;
import io.reactivex.internal.operators.maybe.MaybeFromSingle;
import io.reactivex.internal.operators.maybe.MaybeHide;
import io.reactivex.internal.operators.maybe.MaybeIgnoreElementCompletable;
import io.reactivex.internal.operators.maybe.MaybeIsEmptySingle;
import io.reactivex.internal.operators.maybe.MaybeJust;
import io.reactivex.internal.operators.maybe.MaybeLift;
import io.reactivex.internal.operators.maybe.MaybeMap;
import io.reactivex.internal.operators.maybe.MaybeMaterialize;
import io.reactivex.internal.operators.maybe.MaybeMergeArray;
import io.reactivex.internal.operators.maybe.MaybeNever;
import io.reactivex.internal.operators.maybe.MaybeObserveOn;
import io.reactivex.internal.operators.maybe.MaybeOnErrorComplete;
import io.reactivex.internal.operators.maybe.MaybeOnErrorNext;
import io.reactivex.internal.operators.maybe.MaybeOnErrorReturn;
import io.reactivex.internal.operators.maybe.MaybePeek;
import io.reactivex.internal.operators.maybe.MaybeSubscribeOn;
import io.reactivex.internal.operators.maybe.MaybeSwitchIfEmpty;
import io.reactivex.internal.operators.maybe.MaybeSwitchIfEmptySingle;
import io.reactivex.internal.operators.maybe.MaybeTakeUntilMaybe;
import io.reactivex.internal.operators.maybe.MaybeTakeUntilPublisher;
import io.reactivex.internal.operators.maybe.MaybeTimeoutMaybe;
import io.reactivex.internal.operators.maybe.MaybeTimeoutPublisher;
import io.reactivex.internal.operators.maybe.MaybeTimer;
import io.reactivex.internal.operators.maybe.MaybeToFlowable;
import io.reactivex.internal.operators.maybe.MaybeToObservable;
import io.reactivex.internal.operators.maybe.MaybeToPublisher;
import io.reactivex.internal.operators.maybe.MaybeToSingle;
import io.reactivex.internal.operators.maybe.MaybeUnsafeCreate;
import io.reactivex.internal.operators.maybe.MaybeUnsubscribeOn;
import io.reactivex.internal.operators.maybe.MaybeUsing;
import io.reactivex.internal.operators.maybe.MaybeZipArray;
import io.reactivex.internal.operators.maybe.MaybeZipIterable;
import io.reactivex.internal.operators.mixed.MaybeFlatMapObservable;
import io.reactivex.internal.operators.mixed.MaybeFlatMapPublisher;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public abstract class Maybe<T>
  implements MaybeSource<T>
{
  public Maybe() {}
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> amb(Iterable<? extends MaybeSource<? extends T>> paramIterable)
  {
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    return RxJavaPlugins.onAssembly(new MaybeAmb(null, paramIterable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> ambArray(MaybeSource<? extends T>... paramVarArgs)
  {
    if (paramVarArgs.length == 0) {
      return empty();
    }
    if (paramVarArgs.length == 1) {
      return wrap(paramVarArgs[0]);
    }
    return RxJavaPlugins.onAssembly(new MaybeAmb(paramVarArgs, null));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(MaybeSource<? extends T> paramMaybeSource1, MaybeSource<? extends T> paramMaybeSource2)
  {
    ObjectHelper.requireNonNull(paramMaybeSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source2 is null");
    return concatArray(new MaybeSource[] { paramMaybeSource1, paramMaybeSource2 });
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(MaybeSource<? extends T> paramMaybeSource1, MaybeSource<? extends T> paramMaybeSource2, MaybeSource<? extends T> paramMaybeSource3)
  {
    ObjectHelper.requireNonNull(paramMaybeSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source3 is null");
    return concatArray(new MaybeSource[] { paramMaybeSource1, paramMaybeSource2, paramMaybeSource3 });
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(MaybeSource<? extends T> paramMaybeSource1, MaybeSource<? extends T> paramMaybeSource2, MaybeSource<? extends T> paramMaybeSource3, MaybeSource<? extends T> paramMaybeSource4)
  {
    ObjectHelper.requireNonNull(paramMaybeSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source3 is null");
    ObjectHelper.requireNonNull(paramMaybeSource4, "source4 is null");
    return concatArray(new MaybeSource[] { paramMaybeSource1, paramMaybeSource2, paramMaybeSource3, paramMaybeSource4 });
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(Iterable<? extends MaybeSource<? extends T>> paramIterable)
  {
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    return RxJavaPlugins.onAssembly(new MaybeConcatIterable(paramIterable));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(Publisher<? extends MaybeSource<? extends T>> paramPublisher)
  {
    return concat(paramPublisher, 2);
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concat(Publisher<? extends MaybeSource<? extends T>> paramPublisher, int paramInt)
  {
    ObjectHelper.requireNonNull(paramPublisher, "sources is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    return RxJavaPlugins.onAssembly(new FlowableConcatMapPublisher(paramPublisher, MaybeToPublisher.instance(), paramInt, ErrorMode.IMMEDIATE));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concatArray(MaybeSource<? extends T>... paramVarArgs)
  {
    ObjectHelper.requireNonNull(paramVarArgs, "sources is null");
    if (paramVarArgs.length == 0) {
      return Flowable.empty();
    }
    if (paramVarArgs.length == 1) {
      return RxJavaPlugins.onAssembly(new MaybeToFlowable(paramVarArgs[0]));
    }
    return RxJavaPlugins.onAssembly(new MaybeConcatArray(paramVarArgs));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concatArrayDelayError(MaybeSource<? extends T>... paramVarArgs)
  {
    if (paramVarArgs.length == 0) {
      return Flowable.empty();
    }
    if (paramVarArgs.length == 1) {
      return RxJavaPlugins.onAssembly(new MaybeToFlowable(paramVarArgs[0]));
    }
    return RxJavaPlugins.onAssembly(new MaybeConcatArrayDelayError(paramVarArgs));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concatArrayEager(MaybeSource<? extends T>... paramVarArgs)
  {
    return Flowable.fromArray(paramVarArgs).concatMapEager(MaybeToPublisher.instance());
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concatDelayError(Iterable<? extends MaybeSource<? extends T>> paramIterable)
  {
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    return Flowable.fromIterable(paramIterable).concatMapDelayError(MaybeToPublisher.instance());
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concatDelayError(Publisher<? extends MaybeSource<? extends T>> paramPublisher)
  {
    return Flowable.fromPublisher(paramPublisher).concatMapDelayError(MaybeToPublisher.instance());
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concatEager(Iterable<? extends MaybeSource<? extends T>> paramIterable)
  {
    return Flowable.fromIterable(paramIterable).concatMapEager(MaybeToPublisher.instance());
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> concatEager(Publisher<? extends MaybeSource<? extends T>> paramPublisher)
  {
    return Flowable.fromPublisher(paramPublisher).concatMapEager(MaybeToPublisher.instance());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> create(MaybeOnSubscribe<T> paramMaybeOnSubscribe)
  {
    ObjectHelper.requireNonNull(paramMaybeOnSubscribe, "onSubscribe is null");
    return RxJavaPlugins.onAssembly(new MaybeCreate(paramMaybeOnSubscribe));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> defer(Callable<? extends MaybeSource<? extends T>> paramCallable)
  {
    ObjectHelper.requireNonNull(paramCallable, "maybeSupplier is null");
    return RxJavaPlugins.onAssembly(new MaybeDefer(paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> empty()
  {
    return RxJavaPlugins.onAssembly(MaybeEmpty.INSTANCE);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> error(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "exception is null");
    return RxJavaPlugins.onAssembly(new MaybeError(paramThrowable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> error(Callable<? extends Throwable> paramCallable)
  {
    ObjectHelper.requireNonNull(paramCallable, "errorSupplier is null");
    return RxJavaPlugins.onAssembly(new MaybeErrorCallable(paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> fromAction(Action paramAction)
  {
    ObjectHelper.requireNonNull(paramAction, "run is null");
    return RxJavaPlugins.onAssembly(new MaybeFromAction(paramAction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> fromCallable(Callable<? extends T> paramCallable)
  {
    ObjectHelper.requireNonNull(paramCallable, "callable is null");
    return RxJavaPlugins.onAssembly(new MaybeFromCallable(paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> fromCompletable(CompletableSource paramCompletableSource)
  {
    ObjectHelper.requireNonNull(paramCompletableSource, "completableSource is null");
    return RxJavaPlugins.onAssembly(new MaybeFromCompletable(paramCompletableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> fromFuture(Future<? extends T> paramFuture)
  {
    ObjectHelper.requireNonNull(paramFuture, "future is null");
    return RxJavaPlugins.onAssembly(new MaybeFromFuture(paramFuture, 0L, null));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> fromFuture(Future<? extends T> paramFuture, long paramLong, TimeUnit paramTimeUnit)
  {
    ObjectHelper.requireNonNull(paramFuture, "future is null");
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    return RxJavaPlugins.onAssembly(new MaybeFromFuture(paramFuture, paramLong, paramTimeUnit));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> fromRunnable(Runnable paramRunnable)
  {
    ObjectHelper.requireNonNull(paramRunnable, "run is null");
    return RxJavaPlugins.onAssembly(new MaybeFromRunnable(paramRunnable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> fromSingle(SingleSource<T> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "singleSource is null");
    return RxJavaPlugins.onAssembly(new MaybeFromSingle(paramSingleSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> just(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "item is null");
    return RxJavaPlugins.onAssembly(new MaybeJust(paramT));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> merge(MaybeSource<? extends T> paramMaybeSource1, MaybeSource<? extends T> paramMaybeSource2)
  {
    ObjectHelper.requireNonNull(paramMaybeSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source2 is null");
    return mergeArray(new MaybeSource[] { paramMaybeSource1, paramMaybeSource2 });
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> merge(MaybeSource<? extends T> paramMaybeSource1, MaybeSource<? extends T> paramMaybeSource2, MaybeSource<? extends T> paramMaybeSource3)
  {
    ObjectHelper.requireNonNull(paramMaybeSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source3 is null");
    return mergeArray(new MaybeSource[] { paramMaybeSource1, paramMaybeSource2, paramMaybeSource3 });
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> merge(MaybeSource<? extends T> paramMaybeSource1, MaybeSource<? extends T> paramMaybeSource2, MaybeSource<? extends T> paramMaybeSource3, MaybeSource<? extends T> paramMaybeSource4)
  {
    ObjectHelper.requireNonNull(paramMaybeSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source3 is null");
    ObjectHelper.requireNonNull(paramMaybeSource4, "source4 is null");
    return mergeArray(new MaybeSource[] { paramMaybeSource1, paramMaybeSource2, paramMaybeSource3, paramMaybeSource4 });
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> merge(Iterable<? extends MaybeSource<? extends T>> paramIterable)
  {
    return merge(Flowable.fromIterable(paramIterable));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> merge(Publisher<? extends MaybeSource<? extends T>> paramPublisher)
  {
    return merge(paramPublisher, Integer.MAX_VALUE);
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> merge(Publisher<? extends MaybeSource<? extends T>> paramPublisher, int paramInt)
  {
    ObjectHelper.requireNonNull(paramPublisher, "source is null");
    ObjectHelper.verifyPositive(paramInt, "maxConcurrency");
    return RxJavaPlugins.onAssembly(new FlowableFlatMapPublisher(paramPublisher, MaybeToPublisher.instance(), false, paramInt, 1));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> merge(MaybeSource<? extends MaybeSource<? extends T>> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "source is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatten(paramMaybeSource, Functions.identity()));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeArray(MaybeSource<? extends T>... paramVarArgs)
  {
    ObjectHelper.requireNonNull(paramVarArgs, "sources is null");
    if (paramVarArgs.length == 0) {
      return Flowable.empty();
    }
    if (paramVarArgs.length == 1) {
      return RxJavaPlugins.onAssembly(new MaybeToFlowable(paramVarArgs[0]));
    }
    return RxJavaPlugins.onAssembly(new MaybeMergeArray(paramVarArgs));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeArrayDelayError(MaybeSource<? extends T>... paramVarArgs)
  {
    if (paramVarArgs.length == 0) {
      return Flowable.empty();
    }
    return Flowable.fromArray(paramVarArgs).flatMap(MaybeToPublisher.instance(), true, paramVarArgs.length);
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeDelayError(MaybeSource<? extends T> paramMaybeSource1, MaybeSource<? extends T> paramMaybeSource2)
  {
    ObjectHelper.requireNonNull(paramMaybeSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source2 is null");
    return mergeArrayDelayError(new MaybeSource[] { paramMaybeSource1, paramMaybeSource2 });
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeDelayError(MaybeSource<? extends T> paramMaybeSource1, MaybeSource<? extends T> paramMaybeSource2, MaybeSource<? extends T> paramMaybeSource3)
  {
    ObjectHelper.requireNonNull(paramMaybeSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source3 is null");
    return mergeArrayDelayError(new MaybeSource[] { paramMaybeSource1, paramMaybeSource2, paramMaybeSource3 });
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeDelayError(MaybeSource<? extends T> paramMaybeSource1, MaybeSource<? extends T> paramMaybeSource2, MaybeSource<? extends T> paramMaybeSource3, MaybeSource<? extends T> paramMaybeSource4)
  {
    ObjectHelper.requireNonNull(paramMaybeSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source3 is null");
    ObjectHelper.requireNonNull(paramMaybeSource4, "source4 is null");
    return mergeArrayDelayError(new MaybeSource[] { paramMaybeSource1, paramMaybeSource2, paramMaybeSource3, paramMaybeSource4 });
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeDelayError(Iterable<? extends MaybeSource<? extends T>> paramIterable)
  {
    return Flowable.fromIterable(paramIterable).flatMap(MaybeToPublisher.instance(), true);
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeDelayError(Publisher<? extends MaybeSource<? extends T>> paramPublisher)
  {
    return mergeDelayError(paramPublisher, Integer.MAX_VALUE);
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Flowable<T> mergeDelayError(Publisher<? extends MaybeSource<? extends T>> paramPublisher, int paramInt)
  {
    ObjectHelper.requireNonNull(paramPublisher, "source is null");
    ObjectHelper.verifyPositive(paramInt, "maxConcurrency");
    return RxJavaPlugins.onAssembly(new FlowableFlatMapPublisher(paramPublisher, MaybeToPublisher.instance(), true, paramInt, 1));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> never()
  {
    return RxJavaPlugins.onAssembly(MaybeNever.INSTANCE);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<Boolean> sequenceEqual(MaybeSource<? extends T> paramMaybeSource1, MaybeSource<? extends T> paramMaybeSource2)
  {
    return sequenceEqual(paramMaybeSource1, paramMaybeSource2, ObjectHelper.equalsPredicate());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<Boolean> sequenceEqual(MaybeSource<? extends T> paramMaybeSource1, MaybeSource<? extends T> paramMaybeSource2, BiPredicate<? super T, ? super T> paramBiPredicate)
  {
    ObjectHelper.requireNonNull(paramMaybeSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramBiPredicate, "isEqual is null");
    return RxJavaPlugins.onAssembly(new MaybeEqualSingle(paramMaybeSource1, paramMaybeSource2, paramBiPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public static Maybe<Long> timer(long paramLong, TimeUnit paramTimeUnit)
  {
    return timer(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public static Maybe<Long> timer(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new MaybeTimer(Math.max(0L, paramLong), paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> unsafeCreate(MaybeSource<T> paramMaybeSource)
  {
    if (!(paramMaybeSource instanceof Maybe))
    {
      ObjectHelper.requireNonNull(paramMaybeSource, "onSubscribe is null");
      return RxJavaPlugins.onAssembly(new MaybeUnsafeCreate(paramMaybeSource));
    }
    throw new IllegalArgumentException("unsafeCreate(Maybe) should be upgraded");
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, D> Maybe<T> using(Callable<? extends D> paramCallable, Function<? super D, ? extends MaybeSource<? extends T>> paramFunction, Consumer<? super D> paramConsumer)
  {
    return using(paramCallable, paramFunction, paramConsumer, true);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, D> Maybe<T> using(Callable<? extends D> paramCallable, Function<? super D, ? extends MaybeSource<? extends T>> paramFunction, Consumer<? super D> paramConsumer, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramCallable, "resourceSupplier is null");
    ObjectHelper.requireNonNull(paramFunction, "sourceSupplier is null");
    ObjectHelper.requireNonNull(paramConsumer, "disposer is null");
    return RxJavaPlugins.onAssembly(new MaybeUsing(paramCallable, paramFunction, paramConsumer, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Maybe<T> wrap(MaybeSource<T> paramMaybeSource)
  {
    if ((paramMaybeSource instanceof Maybe)) {
      return RxJavaPlugins.onAssembly((Maybe)paramMaybeSource);
    }
    ObjectHelper.requireNonNull(paramMaybeSource, "onSubscribe is null");
    return RxJavaPlugins.onAssembly(new MaybeUnsafeCreate(paramMaybeSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Maybe<R> zip(MaybeSource<? extends T1> paramMaybeSource, MaybeSource<? extends T2> paramMaybeSource1, MaybeSource<? extends T3> paramMaybeSource2, MaybeSource<? extends T4> paramMaybeSource3, MaybeSource<? extends T5> paramMaybeSource4, MaybeSource<? extends T6> paramMaybeSource5, MaybeSource<? extends T7> paramMaybeSource6, MaybeSource<? extends T8> paramMaybeSource7, MaybeSource<? extends T9> paramMaybeSource8, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> paramFunction9)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramMaybeSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramMaybeSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramMaybeSource6, "source7 is null");
    ObjectHelper.requireNonNull(paramMaybeSource7, "source8 is null");
    ObjectHelper.requireNonNull(paramMaybeSource8, "source9 is null");
    return zipArray(Functions.toFunction(paramFunction9), new MaybeSource[] { paramMaybeSource, paramMaybeSource1, paramMaybeSource2, paramMaybeSource3, paramMaybeSource4, paramMaybeSource5, paramMaybeSource6, paramMaybeSource7, paramMaybeSource8 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Maybe<R> zip(MaybeSource<? extends T1> paramMaybeSource, MaybeSource<? extends T2> paramMaybeSource1, MaybeSource<? extends T3> paramMaybeSource2, MaybeSource<? extends T4> paramMaybeSource3, MaybeSource<? extends T5> paramMaybeSource4, MaybeSource<? extends T6> paramMaybeSource5, MaybeSource<? extends T7> paramMaybeSource6, MaybeSource<? extends T8> paramMaybeSource7, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> paramFunction8)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramMaybeSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramMaybeSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramMaybeSource6, "source7 is null");
    ObjectHelper.requireNonNull(paramMaybeSource7, "source8 is null");
    return zipArray(Functions.toFunction(paramFunction8), new MaybeSource[] { paramMaybeSource, paramMaybeSource1, paramMaybeSource2, paramMaybeSource3, paramMaybeSource4, paramMaybeSource5, paramMaybeSource6, paramMaybeSource7 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, R> Maybe<R> zip(MaybeSource<? extends T1> paramMaybeSource, MaybeSource<? extends T2> paramMaybeSource1, MaybeSource<? extends T3> paramMaybeSource2, MaybeSource<? extends T4> paramMaybeSource3, MaybeSource<? extends T5> paramMaybeSource4, MaybeSource<? extends T6> paramMaybeSource5, MaybeSource<? extends T7> paramMaybeSource6, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> paramFunction7)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramMaybeSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramMaybeSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramMaybeSource6, "source7 is null");
    return zipArray(Functions.toFunction(paramFunction7), new MaybeSource[] { paramMaybeSource, paramMaybeSource1, paramMaybeSource2, paramMaybeSource3, paramMaybeSource4, paramMaybeSource5, paramMaybeSource6 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, R> Maybe<R> zip(MaybeSource<? extends T1> paramMaybeSource, MaybeSource<? extends T2> paramMaybeSource1, MaybeSource<? extends T3> paramMaybeSource2, MaybeSource<? extends T4> paramMaybeSource3, MaybeSource<? extends T5> paramMaybeSource4, MaybeSource<? extends T6> paramMaybeSource5, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> paramFunction6)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramMaybeSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramMaybeSource5, "source6 is null");
    return zipArray(Functions.toFunction(paramFunction6), new MaybeSource[] { paramMaybeSource, paramMaybeSource1, paramMaybeSource2, paramMaybeSource3, paramMaybeSource4, paramMaybeSource5 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, R> Maybe<R> zip(MaybeSource<? extends T1> paramMaybeSource, MaybeSource<? extends T2> paramMaybeSource1, MaybeSource<? extends T3> paramMaybeSource2, MaybeSource<? extends T4> paramMaybeSource3, MaybeSource<? extends T5> paramMaybeSource4, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> paramFunction5)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramMaybeSource4, "source5 is null");
    return zipArray(Functions.toFunction(paramFunction5), new MaybeSource[] { paramMaybeSource, paramMaybeSource1, paramMaybeSource2, paramMaybeSource3, paramMaybeSource4 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, R> Maybe<R> zip(MaybeSource<? extends T1> paramMaybeSource, MaybeSource<? extends T2> paramMaybeSource1, MaybeSource<? extends T3> paramMaybeSource2, MaybeSource<? extends T4> paramMaybeSource3, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> paramFunction4)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramMaybeSource3, "source4 is null");
    return zipArray(Functions.toFunction(paramFunction4), new MaybeSource[] { paramMaybeSource, paramMaybeSource1, paramMaybeSource2, paramMaybeSource3 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, R> Maybe<R> zip(MaybeSource<? extends T1> paramMaybeSource, MaybeSource<? extends T2> paramMaybeSource1, MaybeSource<? extends T3> paramMaybeSource2, Function3<? super T1, ? super T2, ? super T3, ? extends R> paramFunction3)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramMaybeSource2, "source3 is null");
    return zipArray(Functions.toFunction(paramFunction3), new MaybeSource[] { paramMaybeSource, paramMaybeSource1, paramMaybeSource2 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, R> Maybe<R> zip(MaybeSource<? extends T1> paramMaybeSource, MaybeSource<? extends T2> paramMaybeSource1, BiFunction<? super T1, ? super T2, ? extends R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "source1 is null");
    ObjectHelper.requireNonNull(paramMaybeSource1, "source2 is null");
    return zipArray(Functions.toFunction(paramBiFunction), new MaybeSource[] { paramMaybeSource, paramMaybeSource1 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Maybe<R> zip(Iterable<? extends MaybeSource<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "zipper is null");
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    return RxJavaPlugins.onAssembly(new MaybeZipIterable(paramIterable, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Maybe<R> zipArray(Function<? super Object[], ? extends R> paramFunction, MaybeSource<? extends T>... paramVarArgs)
  {
    ObjectHelper.requireNonNull(paramVarArgs, "sources is null");
    if (paramVarArgs.length == 0) {
      return empty();
    }
    ObjectHelper.requireNonNull(paramFunction, "zipper is null");
    return RxJavaPlugins.onAssembly(new MaybeZipArray(paramVarArgs, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> ambWith(MaybeSource<? extends T> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "other is null");
    return ambArray(new MaybeSource[] { this, paramMaybeSource });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> R as(MaybeConverter<T, ? extends R> paramMaybeConverter)
  {
    return ((MaybeConverter)ObjectHelper.requireNonNull(paramMaybeConverter, "converter is null")).apply(this);
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
  public final T blockingGet(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "defaultValue is null");
    BlockingMultiObserver localBlockingMultiObserver = new BlockingMultiObserver();
    subscribe(localBlockingMultiObserver);
    return localBlockingMultiObserver.blockingGet(paramT);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> cache()
  {
    return RxJavaPlugins.onAssembly(new MaybeCache(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Maybe<U> cast(Class<? extends U> paramClass)
  {
    ObjectHelper.requireNonNull(paramClass, "clazz is null");
    return map(Functions.castFunction(paramClass));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Maybe<R> compose(MaybeTransformer<? super T, ? extends R> paramMaybeTransformer)
  {
    return wrap(((MaybeTransformer)ObjectHelper.requireNonNull(paramMaybeTransformer, "transformer is null")).apply(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Maybe<R> concatMap(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatten(this, paramFunction));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> concatWith(MaybeSource<? extends T> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "other is null");
    return concat(this, paramMaybeSource);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Boolean> contains(Object paramObject)
  {
    ObjectHelper.requireNonNull(paramObject, "item is null");
    return RxJavaPlugins.onAssembly(new MaybeContains(this, paramObject));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Long> count()
  {
    return RxJavaPlugins.onAssembly(new MaybeCount(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> defaultIfEmpty(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "defaultItem is null");
    return switchIfEmpty(just(paramT));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Maybe<T> delay(long paramLong, TimeUnit paramTimeUnit)
  {
    return delay(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Maybe<T> delay(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new MaybeDelay(this, Math.max(0L, paramLong), paramTimeUnit, paramScheduler));
  }
  
  @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, V> Maybe<T> delay(Publisher<U> paramPublisher)
  {
    ObjectHelper.requireNonNull(paramPublisher, "delayIndicator is null");
    return RxJavaPlugins.onAssembly(new MaybeDelayOtherPublisher(this, paramPublisher));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Maybe<T> delaySubscription(long paramLong, TimeUnit paramTimeUnit)
  {
    return delaySubscription(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Maybe<T> delaySubscription(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return delaySubscription(Flowable.timer(paramLong, paramTimeUnit, paramScheduler));
  }
  
  @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Maybe<T> delaySubscription(Publisher<U> paramPublisher)
  {
    ObjectHelper.requireNonNull(paramPublisher, "subscriptionIndicator is null");
    return RxJavaPlugins.onAssembly(new MaybeDelaySubscriptionOtherPublisher(this, paramPublisher));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> doAfterSuccess(Consumer<? super T> paramConsumer)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onAfterSuccess is null");
    return RxJavaPlugins.onAssembly(new MaybeDoAfterSuccess(this, paramConsumer));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> doAfterTerminate(Action paramAction)
  {
    return RxJavaPlugins.onAssembly(new MaybePeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, (Action)ObjectHelper.requireNonNull(paramAction, "onAfterTerminate is null"), Functions.EMPTY_ACTION));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> doFinally(Action paramAction)
  {
    ObjectHelper.requireNonNull(paramAction, "onFinally is null");
    return RxJavaPlugins.onAssembly(new MaybeDoFinally(this, paramAction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> doOnComplete(Action paramAction)
  {
    return RxJavaPlugins.onAssembly(new MaybePeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), (Action)ObjectHelper.requireNonNull(paramAction, "onComplete is null"), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> doOnDispose(Action paramAction)
  {
    return RxJavaPlugins.onAssembly(new MaybePeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, (Action)ObjectHelper.requireNonNull(paramAction, "onDispose is null")));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> doOnError(Consumer<? super Throwable> paramConsumer)
  {
    return RxJavaPlugins.onAssembly(new MaybePeek(this, Functions.emptyConsumer(), Functions.emptyConsumer(), (Consumer)ObjectHelper.requireNonNull(paramConsumer, "onError is null"), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> doOnEvent(BiConsumer<? super T, ? super Throwable> paramBiConsumer)
  {
    ObjectHelper.requireNonNull(paramBiConsumer, "onEvent is null");
    return RxJavaPlugins.onAssembly(new MaybeDoOnEvent(this, paramBiConsumer));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> doOnSubscribe(Consumer<? super Disposable> paramConsumer)
  {
    return RxJavaPlugins.onAssembly(new MaybePeek(this, (Consumer)ObjectHelper.requireNonNull(paramConsumer, "onSubscribe is null"), Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> doOnSuccess(Consumer<? super T> paramConsumer)
  {
    return RxJavaPlugins.onAssembly(new MaybePeek(this, Functions.emptyConsumer(), (Consumer)ObjectHelper.requireNonNull(paramConsumer, "onSuccess is null"), Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> doOnTerminate(Action paramAction)
  {
    ObjectHelper.requireNonNull(paramAction, "onTerminate is null");
    return RxJavaPlugins.onAssembly(new MaybeDoOnTerminate(this, paramAction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> filter(Predicate<? super T> paramPredicate)
  {
    ObjectHelper.requireNonNull(paramPredicate, "predicate is null");
    return RxJavaPlugins.onAssembly(new MaybeFilter(this, paramPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Maybe<R> flatMap(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatten(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Maybe<R> flatMap(Function<? super T, ? extends MaybeSource<? extends U>> paramFunction, BiFunction<? super T, ? super U, ? extends R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.requireNonNull(paramBiFunction, "resultSelector is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatMapBiSelector(this, paramFunction, paramBiFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Maybe<R> flatMap(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction, Function<? super Throwable, ? extends MaybeSource<? extends R>> paramFunction1, Callable<? extends MaybeSource<? extends R>> paramCallable)
  {
    ObjectHelper.requireNonNull(paramFunction, "onSuccessMapper is null");
    ObjectHelper.requireNonNull(paramFunction1, "onErrorMapper is null");
    ObjectHelper.requireNonNull(paramCallable, "onCompleteSupplier is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatMapNotification(this, paramFunction, paramFunction1, paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatMapCompletable(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMapObservable(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatMapObservable(this, paramFunction));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Flowable<R> flatMapPublisher(Function<? super T, ? extends Publisher<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatMapPublisher(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Single<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatMapSingle(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Maybe<R> flatMapSingleElement(Function<? super T, ? extends SingleSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatMapSingleElement(this, paramFunction));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Flowable<U> flattenAsFlowable(Function<? super T, ? extends Iterable<? extends U>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatMapIterableFlowable(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<U> flattenAsObservable(Function<? super T, ? extends Iterable<? extends U>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new MaybeFlatMapIterableObservable(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> hide()
  {
    return RxJavaPlugins.onAssembly(new MaybeHide(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable ignoreElement()
  {
    return RxJavaPlugins.onAssembly(new MaybeIgnoreElementCompletable(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Boolean> isEmpty()
  {
    return RxJavaPlugins.onAssembly(new MaybeIsEmptySingle(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Maybe<R> lift(MaybeOperator<? extends R, ? super T> paramMaybeOperator)
  {
    ObjectHelper.requireNonNull(paramMaybeOperator, "lift is null");
    return RxJavaPlugins.onAssembly(new MaybeLift(this, paramMaybeOperator));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Maybe<R> map(Function<? super T, ? extends R> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new MaybeMap(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Notification<T>> materialize()
  {
    return RxJavaPlugins.onAssembly(new MaybeMaterialize(this));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> mergeWith(MaybeSource<? extends T> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "other is null");
    return merge(this, paramMaybeSource);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Maybe<T> observeOn(Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new MaybeObserveOn(this, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Maybe<U> ofType(Class<U> paramClass)
  {
    ObjectHelper.requireNonNull(paramClass, "clazz is null");
    return filter(Functions.isInstanceOf(paramClass)).cast(paramClass);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> onErrorComplete()
  {
    return onErrorComplete(Functions.alwaysTrue());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> onErrorComplete(Predicate<? super Throwable> paramPredicate)
  {
    ObjectHelper.requireNonNull(paramPredicate, "predicate is null");
    return RxJavaPlugins.onAssembly(new MaybeOnErrorComplete(this, paramPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> onErrorResumeNext(MaybeSource<? extends T> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "next is null");
    return onErrorResumeNext(Functions.justFunction(paramMaybeSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> onErrorResumeNext(Function<? super Throwable, ? extends MaybeSource<? extends T>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "resumeFunction is null");
    return RxJavaPlugins.onAssembly(new MaybeOnErrorNext(this, paramFunction, true));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> onErrorReturn(Function<? super Throwable, ? extends T> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "valueSupplier is null");
    return RxJavaPlugins.onAssembly(new MaybeOnErrorReturn(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> onErrorReturnItem(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "item is null");
    return onErrorReturn(Functions.justFunction(paramT));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> onExceptionResumeNext(MaybeSource<? extends T> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "next is null");
    return RxJavaPlugins.onAssembly(new MaybeOnErrorNext(this, Functions.justFunction(paramMaybeSource), false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> onTerminateDetach()
  {
    return RxJavaPlugins.onAssembly(new MaybeDetach(this));
  }
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> repeat()
  {
    return repeat(Long.MAX_VALUE);
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
  public final Maybe<T> retry()
  {
    return retry(Long.MAX_VALUE, Functions.alwaysTrue());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> retry(long paramLong)
  {
    return retry(paramLong, Functions.alwaysTrue());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> retry(long paramLong, Predicate<? super Throwable> paramPredicate)
  {
    return toFlowable().retry(paramLong, paramPredicate).singleElement();
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> retry(BiPredicate<? super Integer, ? super Throwable> paramBiPredicate)
  {
    return toFlowable().retry(paramBiPredicate).singleElement();
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> retry(Predicate<? super Throwable> paramPredicate)
  {
    return retry(Long.MAX_VALUE, paramPredicate);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> retryUntil(BooleanSupplier paramBooleanSupplier)
  {
    ObjectHelper.requireNonNull(paramBooleanSupplier, "stop is null");
    return retry(Long.MAX_VALUE, Functions.predicateReverseFor(paramBooleanSupplier));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> retryWhen(Function<? super Flowable<Throwable>, ? extends Publisher<?>> paramFunction)
  {
    return toFlowable().retryWhen(paramFunction).singleElement();
  }
  
  @SchedulerSupport("none")
  public final Disposable subscribe()
  {
    return subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable subscribe(Consumer<? super T> paramConsumer)
  {
    return subscribe(paramConsumer, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable subscribe(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1)
  {
    return subscribe(paramConsumer, paramConsumer1, Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable subscribe(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onSuccess is null");
    ObjectHelper.requireNonNull(paramConsumer1, "onError is null");
    ObjectHelper.requireNonNull(paramAction, "onComplete is null");
    return (Disposable)subscribeWith(new MaybeCallbackObserver(paramConsumer, paramConsumer1, paramAction));
  }
  
  /* Error */
  @SchedulerSupport("none")
  public final void subscribe(MaybeObserver<? super T> paramMaybeObserver)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 919
    //   4: invokestatic 26	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   7: pop
    //   8: aload_0
    //   9: aload_1
    //   10: invokestatic 923	io/reactivex/plugins/RxJavaPlugins:onSubscribe	(Lio/reactivex/Maybe;Lio/reactivex/MaybeObserver;)Lio/reactivex/MaybeObserver;
    //   13: astore_1
    //   14: aload_1
    //   15: ldc_w 925
    //   18: invokestatic 26	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   21: pop
    //   22: aload_0
    //   23: aload_1
    //   24: invokevirtual 928	io/reactivex/Maybe:subscribeActual	(Lio/reactivex/MaybeObserver;)V
    //   27: return
    //   28: astore_2
    //   29: aload_2
    //   30: invokestatic 933	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   33: new 917	java/lang/NullPointerException
    //   36: dup
    //   37: ldc_w 935
    //   40: invokespecial 936	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   43: astore_1
    //   44: aload_1
    //   45: aload_2
    //   46: invokevirtual 940	java/lang/NullPointerException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   49: pop
    //   50: aload_1
    //   51: athrow
    //   52: astore_1
    //   53: aload_1
    //   54: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	55	0	this	Maybe
    //   0	55	1	paramMaybeObserver	MaybeObserver<? super T>
    //   28	18	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   22	27	28	finally
    //   22	27	52	java/lang/NullPointerException
  }
  
  protected abstract void subscribeActual(MaybeObserver<? super T> paramMaybeObserver);
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Maybe<T> subscribeOn(Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new MaybeSubscribeOn(this, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <E extends MaybeObserver<? super T>> E subscribeWith(E paramE)
  {
    subscribe(paramE);
    return paramE;
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> switchIfEmpty(MaybeSource<? extends T> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "other is null");
    return RxJavaPlugins.onAssembly(new MaybeSwitchIfEmpty(this, paramMaybeSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> switchIfEmpty(SingleSource<? extends T> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "other is null");
    return RxJavaPlugins.onAssembly(new MaybeSwitchIfEmptySingle(this, paramSingleSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Maybe<T> takeUntil(MaybeSource<U> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "other is null");
    return RxJavaPlugins.onAssembly(new MaybeTakeUntilMaybe(this, paramMaybeSource));
  }
  
  @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Maybe<T> takeUntil(Publisher<U> paramPublisher)
  {
    ObjectHelper.requireNonNull(paramPublisher, "other is null");
    return RxJavaPlugins.onAssembly(new MaybeTakeUntilPublisher(this, paramPublisher));
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
  public final Maybe<T> timeout(long paramLong, TimeUnit paramTimeUnit)
  {
    return timeout(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Maybe<T> timeout(long paramLong, TimeUnit paramTimeUnit, MaybeSource<? extends T> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "fallback is null");
    return timeout(paramLong, paramTimeUnit, Schedulers.computation(), paramMaybeSource);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Maybe<T> timeout(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return timeout(timer(paramLong, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Maybe<T> timeout(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, MaybeSource<? extends T> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "fallback is null");
    return timeout(timer(paramLong, paramTimeUnit, paramScheduler), paramMaybeSource);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Maybe<T> timeout(MaybeSource<U> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "timeoutIndicator is null");
    return RxJavaPlugins.onAssembly(new MaybeTimeoutMaybe(this, paramMaybeSource, null));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Maybe<T> timeout(MaybeSource<U> paramMaybeSource, MaybeSource<? extends T> paramMaybeSource1)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "timeoutIndicator is null");
    ObjectHelper.requireNonNull(paramMaybeSource1, "fallback is null");
    return RxJavaPlugins.onAssembly(new MaybeTimeoutMaybe(this, paramMaybeSource, paramMaybeSource1));
  }
  
  @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Maybe<T> timeout(Publisher<U> paramPublisher)
  {
    ObjectHelper.requireNonNull(paramPublisher, "timeoutIndicator is null");
    return RxJavaPlugins.onAssembly(new MaybeTimeoutPublisher(this, paramPublisher, null));
  }
  
  @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Maybe<T> timeout(Publisher<U> paramPublisher, MaybeSource<? extends T> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramPublisher, "timeoutIndicator is null");
    ObjectHelper.requireNonNull(paramMaybeSource, "fallback is null");
    return RxJavaPlugins.onAssembly(new MaybeTimeoutPublisher(this, paramPublisher, paramMaybeSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> R to(Function<? super Maybe<T>, R> paramFunction)
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
  
  @BackpressureSupport(BackpressureKind.FULL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> toFlowable()
  {
    if ((this instanceof FuseToFlowable)) {
      return ((FuseToFlowable)this).fuseToFlowable();
    }
    return RxJavaPlugins.onAssembly(new MaybeToFlowable(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> toObservable()
  {
    if ((this instanceof FuseToObservable)) {
      return ((FuseToObservable)this).fuseToObservable();
    }
    return RxJavaPlugins.onAssembly(new MaybeToObservable(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> toSingle()
  {
    return RxJavaPlugins.onAssembly(new MaybeToSingle(this, null));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> toSingle(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "defaultValue is null");
    return RxJavaPlugins.onAssembly(new MaybeToSingle(this, paramT));
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Maybe<T> unsubscribeOn(Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new MaybeUnsubscribeOn(this, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Maybe<R> zipWith(MaybeSource<? extends U> paramMaybeSource, BiFunction<? super T, ? super U, ? extends R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "other is null");
    return zip(this, paramMaybeSource, paramBiFunction);
  }
}
