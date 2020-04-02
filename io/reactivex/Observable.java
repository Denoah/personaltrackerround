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
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.observers.BlockingFirstObserver;
import io.reactivex.internal.observers.BlockingLastObserver;
import io.reactivex.internal.observers.ForEachWhileObserver;
import io.reactivex.internal.observers.FutureObserver;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.internal.operators.flowable.FlowableFromObservable;
import io.reactivex.internal.operators.flowable.FlowableOnBackpressureError;
import io.reactivex.internal.operators.mixed.ObservableConcatMapCompletable;
import io.reactivex.internal.operators.mixed.ObservableConcatMapMaybe;
import io.reactivex.internal.operators.mixed.ObservableConcatMapSingle;
import io.reactivex.internal.operators.mixed.ObservableSwitchMapCompletable;
import io.reactivex.internal.operators.mixed.ObservableSwitchMapMaybe;
import io.reactivex.internal.operators.mixed.ObservableSwitchMapSingle;
import io.reactivex.internal.operators.observable.BlockingObservableIterable;
import io.reactivex.internal.operators.observable.BlockingObservableLatest;
import io.reactivex.internal.operators.observable.BlockingObservableMostRecent;
import io.reactivex.internal.operators.observable.BlockingObservableNext;
import io.reactivex.internal.operators.observable.ObservableAllSingle;
import io.reactivex.internal.operators.observable.ObservableAmb;
import io.reactivex.internal.operators.observable.ObservableAnySingle;
import io.reactivex.internal.operators.observable.ObservableBlockingSubscribe;
import io.reactivex.internal.operators.observable.ObservableBuffer;
import io.reactivex.internal.operators.observable.ObservableBufferBoundary;
import io.reactivex.internal.operators.observable.ObservableBufferBoundarySupplier;
import io.reactivex.internal.operators.observable.ObservableBufferExactBoundary;
import io.reactivex.internal.operators.observable.ObservableBufferTimed;
import io.reactivex.internal.operators.observable.ObservableCache;
import io.reactivex.internal.operators.observable.ObservableCollectSingle;
import io.reactivex.internal.operators.observable.ObservableCombineLatest;
import io.reactivex.internal.operators.observable.ObservableConcatMap;
import io.reactivex.internal.operators.observable.ObservableConcatMapEager;
import io.reactivex.internal.operators.observable.ObservableConcatWithCompletable;
import io.reactivex.internal.operators.observable.ObservableConcatWithMaybe;
import io.reactivex.internal.operators.observable.ObservableConcatWithSingle;
import io.reactivex.internal.operators.observable.ObservableCountSingle;
import io.reactivex.internal.operators.observable.ObservableCreate;
import io.reactivex.internal.operators.observable.ObservableDebounce;
import io.reactivex.internal.operators.observable.ObservableDebounceTimed;
import io.reactivex.internal.operators.observable.ObservableDefer;
import io.reactivex.internal.operators.observable.ObservableDelay;
import io.reactivex.internal.operators.observable.ObservableDelaySubscriptionOther;
import io.reactivex.internal.operators.observable.ObservableDematerialize;
import io.reactivex.internal.operators.observable.ObservableDetach;
import io.reactivex.internal.operators.observable.ObservableDistinct;
import io.reactivex.internal.operators.observable.ObservableDistinctUntilChanged;
import io.reactivex.internal.operators.observable.ObservableDoAfterNext;
import io.reactivex.internal.operators.observable.ObservableDoFinally;
import io.reactivex.internal.operators.observable.ObservableDoOnEach;
import io.reactivex.internal.operators.observable.ObservableDoOnLifecycle;
import io.reactivex.internal.operators.observable.ObservableElementAtMaybe;
import io.reactivex.internal.operators.observable.ObservableElementAtSingle;
import io.reactivex.internal.operators.observable.ObservableEmpty;
import io.reactivex.internal.operators.observable.ObservableError;
import io.reactivex.internal.operators.observable.ObservableFilter;
import io.reactivex.internal.operators.observable.ObservableFlatMap;
import io.reactivex.internal.operators.observable.ObservableFlatMapCompletableCompletable;
import io.reactivex.internal.operators.observable.ObservableFlatMapMaybe;
import io.reactivex.internal.operators.observable.ObservableFlatMapSingle;
import io.reactivex.internal.operators.observable.ObservableFlattenIterable;
import io.reactivex.internal.operators.observable.ObservableFromArray;
import io.reactivex.internal.operators.observable.ObservableFromCallable;
import io.reactivex.internal.operators.observable.ObservableFromFuture;
import io.reactivex.internal.operators.observable.ObservableFromIterable;
import io.reactivex.internal.operators.observable.ObservableFromPublisher;
import io.reactivex.internal.operators.observable.ObservableFromUnsafeSource;
import io.reactivex.internal.operators.observable.ObservableGenerate;
import io.reactivex.internal.operators.observable.ObservableGroupBy;
import io.reactivex.internal.operators.observable.ObservableGroupJoin;
import io.reactivex.internal.operators.observable.ObservableHide;
import io.reactivex.internal.operators.observable.ObservableIgnoreElements;
import io.reactivex.internal.operators.observable.ObservableIgnoreElementsCompletable;
import io.reactivex.internal.operators.observable.ObservableInternalHelper;
import io.reactivex.internal.operators.observable.ObservableInterval;
import io.reactivex.internal.operators.observable.ObservableIntervalRange;
import io.reactivex.internal.operators.observable.ObservableJoin;
import io.reactivex.internal.operators.observable.ObservableJust;
import io.reactivex.internal.operators.observable.ObservableLastMaybe;
import io.reactivex.internal.operators.observable.ObservableLastSingle;
import io.reactivex.internal.operators.observable.ObservableLift;
import io.reactivex.internal.operators.observable.ObservableMap;
import io.reactivex.internal.operators.observable.ObservableMapNotification;
import io.reactivex.internal.operators.observable.ObservableMaterialize;
import io.reactivex.internal.operators.observable.ObservableMergeWithCompletable;
import io.reactivex.internal.operators.observable.ObservableMergeWithMaybe;
import io.reactivex.internal.operators.observable.ObservableMergeWithSingle;
import io.reactivex.internal.operators.observable.ObservableNever;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import io.reactivex.internal.operators.observable.ObservableOnErrorNext;
import io.reactivex.internal.operators.observable.ObservableOnErrorReturn;
import io.reactivex.internal.operators.observable.ObservablePublish;
import io.reactivex.internal.operators.observable.ObservablePublishSelector;
import io.reactivex.internal.operators.observable.ObservableRange;
import io.reactivex.internal.operators.observable.ObservableRangeLong;
import io.reactivex.internal.operators.observable.ObservableReduceMaybe;
import io.reactivex.internal.operators.observable.ObservableReduceSeedSingle;
import io.reactivex.internal.operators.observable.ObservableReduceWithSingle;
import io.reactivex.internal.operators.observable.ObservableRepeat;
import io.reactivex.internal.operators.observable.ObservableRepeatUntil;
import io.reactivex.internal.operators.observable.ObservableRepeatWhen;
import io.reactivex.internal.operators.observable.ObservableReplay;
import io.reactivex.internal.operators.observable.ObservableRetryBiPredicate;
import io.reactivex.internal.operators.observable.ObservableRetryPredicate;
import io.reactivex.internal.operators.observable.ObservableRetryWhen;
import io.reactivex.internal.operators.observable.ObservableSampleTimed;
import io.reactivex.internal.operators.observable.ObservableSampleWithObservable;
import io.reactivex.internal.operators.observable.ObservableScalarXMap;
import io.reactivex.internal.operators.observable.ObservableScan;
import io.reactivex.internal.operators.observable.ObservableScanSeed;
import io.reactivex.internal.operators.observable.ObservableSequenceEqualSingle;
import io.reactivex.internal.operators.observable.ObservableSerialized;
import io.reactivex.internal.operators.observable.ObservableSingleMaybe;
import io.reactivex.internal.operators.observable.ObservableSingleSingle;
import io.reactivex.internal.operators.observable.ObservableSkip;
import io.reactivex.internal.operators.observable.ObservableSkipLast;
import io.reactivex.internal.operators.observable.ObservableSkipLastTimed;
import io.reactivex.internal.operators.observable.ObservableSkipUntil;
import io.reactivex.internal.operators.observable.ObservableSkipWhile;
import io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import io.reactivex.internal.operators.observable.ObservableSwitchIfEmpty;
import io.reactivex.internal.operators.observable.ObservableSwitchMap;
import io.reactivex.internal.operators.observable.ObservableTake;
import io.reactivex.internal.operators.observable.ObservableTakeLast;
import io.reactivex.internal.operators.observable.ObservableTakeLastOne;
import io.reactivex.internal.operators.observable.ObservableTakeLastTimed;
import io.reactivex.internal.operators.observable.ObservableTakeUntil;
import io.reactivex.internal.operators.observable.ObservableTakeUntilPredicate;
import io.reactivex.internal.operators.observable.ObservableTakeWhile;
import io.reactivex.internal.operators.observable.ObservableThrottleFirstTimed;
import io.reactivex.internal.operators.observable.ObservableThrottleLatest;
import io.reactivex.internal.operators.observable.ObservableTimeInterval;
import io.reactivex.internal.operators.observable.ObservableTimeout;
import io.reactivex.internal.operators.observable.ObservableTimeoutTimed;
import io.reactivex.internal.operators.observable.ObservableTimer;
import io.reactivex.internal.operators.observable.ObservableToList;
import io.reactivex.internal.operators.observable.ObservableToListSingle;
import io.reactivex.internal.operators.observable.ObservableUnsubscribeOn;
import io.reactivex.internal.operators.observable.ObservableUsing;
import io.reactivex.internal.operators.observable.ObservableWindow;
import io.reactivex.internal.operators.observable.ObservableWindowBoundary;
import io.reactivex.internal.operators.observable.ObservableWindowBoundarySelector;
import io.reactivex.internal.operators.observable.ObservableWindowBoundarySupplier;
import io.reactivex.internal.operators.observable.ObservableWindowTimed;
import io.reactivex.internal.operators.observable.ObservableWithLatestFrom;
import io.reactivex.internal.operators.observable.ObservableWithLatestFromMany;
import io.reactivex.internal.operators.observable.ObservableZip;
import io.reactivex.internal.operators.observable.ObservableZipIterable;
import io.reactivex.internal.util.ArrayListSupplier;
import io.reactivex.internal.util.ErrorMode;
import io.reactivex.internal.util.HashMapSupplier;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.observers.SafeObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

public abstract class Observable<T>
  implements ObservableSource<T>
{
  public Observable() {}
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> amb(Iterable<? extends ObservableSource<? extends T>> paramIterable)
  {
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    return RxJavaPlugins.onAssembly(new ObservableAmb(null, paramIterable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> ambArray(ObservableSource<? extends T>... paramVarArgs)
  {
    ObjectHelper.requireNonNull(paramVarArgs, "sources is null");
    int i = paramVarArgs.length;
    if (i == 0) {
      return empty();
    }
    if (i == 1) {
      return wrap(paramVarArgs[0]);
    }
    return RxJavaPlugins.onAssembly(new ObservableAmb(paramVarArgs, null));
  }
  
  public static int bufferSize()
  {
    return Flowable.bufferSize();
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> combineLatest(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, ObservableSource<? extends T5> paramObservableSource4, ObservableSource<? extends T6> paramObservableSource5, ObservableSource<? extends T7> paramObservableSource6, ObservableSource<? extends T8> paramObservableSource7, ObservableSource<? extends T9> paramObservableSource8, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> paramFunction9)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramObservableSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramObservableSource6, "source7 is null");
    ObjectHelper.requireNonNull(paramObservableSource7, "source8 is null");
    ObjectHelper.requireNonNull(paramObservableSource8, "source9 is null");
    return combineLatest(Functions.toFunction(paramFunction9), bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4, paramObservableSource5, paramObservableSource6, paramObservableSource7, paramObservableSource8 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> combineLatest(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, ObservableSource<? extends T5> paramObservableSource4, ObservableSource<? extends T6> paramObservableSource5, ObservableSource<? extends T7> paramObservableSource6, ObservableSource<? extends T8> paramObservableSource7, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> paramFunction8)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramObservableSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramObservableSource6, "source7 is null");
    ObjectHelper.requireNonNull(paramObservableSource7, "source8 is null");
    return combineLatest(Functions.toFunction(paramFunction8), bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4, paramObservableSource5, paramObservableSource6, paramObservableSource7 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> combineLatest(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, ObservableSource<? extends T5> paramObservableSource4, ObservableSource<? extends T6> paramObservableSource5, ObservableSource<? extends T7> paramObservableSource6, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> paramFunction7)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramObservableSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramObservableSource6, "source7 is null");
    return combineLatest(Functions.toFunction(paramFunction7), bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4, paramObservableSource5, paramObservableSource6 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, R> Observable<R> combineLatest(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, ObservableSource<? extends T5> paramObservableSource4, ObservableSource<? extends T6> paramObservableSource5, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> paramFunction6)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramObservableSource5, "source6 is null");
    return combineLatest(Functions.toFunction(paramFunction6), bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4, paramObservableSource5 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, R> Observable<R> combineLatest(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, ObservableSource<? extends T5> paramObservableSource4, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> paramFunction5)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source5 is null");
    return combineLatest(Functions.toFunction(paramFunction5), bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, R> Observable<R> combineLatest(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> paramFunction4)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    return combineLatest(Functions.toFunction(paramFunction4), bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, R> Observable<R> combineLatest(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, Function3<? super T1, ? super T2, ? super T3, ? extends R> paramFunction3)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    return combineLatest(Functions.toFunction(paramFunction3), bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, R> Observable<R> combineLatest(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, BiFunction<? super T1, ? super T2, ? extends R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    return combineLatest(Functions.toFunction(paramBiFunction), bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> combineLatest(Function<? super Object[], ? extends R> paramFunction, int paramInt, ObservableSource<? extends T>... paramVarArgs)
  {
    return combineLatest(paramVarArgs, paramFunction, paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> combineLatest(Iterable<? extends ObservableSource<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction)
  {
    return combineLatest(paramIterable, paramFunction, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> combineLatest(Iterable<? extends ObservableSource<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    ObjectHelper.requireNonNull(paramFunction, "combiner is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableCombineLatest(null, paramIterable, paramFunction, paramInt << 1, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> combineLatest(ObservableSource<? extends T>[] paramArrayOfObservableSource, Function<? super Object[], ? extends R> paramFunction)
  {
    return combineLatest(paramArrayOfObservableSource, paramFunction, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> combineLatest(ObservableSource<? extends T>[] paramArrayOfObservableSource, Function<? super Object[], ? extends R> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramArrayOfObservableSource, "sources is null");
    if (paramArrayOfObservableSource.length == 0) {
      return empty();
    }
    ObjectHelper.requireNonNull(paramFunction, "combiner is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableCombineLatest(paramArrayOfObservableSource, null, paramFunction, paramInt << 1, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> combineLatestDelayError(Function<? super Object[], ? extends R> paramFunction, int paramInt, ObservableSource<? extends T>... paramVarArgs)
  {
    return combineLatestDelayError(paramVarArgs, paramFunction, paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> combineLatestDelayError(Iterable<? extends ObservableSource<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction)
  {
    return combineLatestDelayError(paramIterable, paramFunction, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> combineLatestDelayError(Iterable<? extends ObservableSource<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    ObjectHelper.requireNonNull(paramFunction, "combiner is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableCombineLatest(null, paramIterable, paramFunction, paramInt << 1, true));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> combineLatestDelayError(ObservableSource<? extends T>[] paramArrayOfObservableSource, Function<? super Object[], ? extends R> paramFunction)
  {
    return combineLatestDelayError(paramArrayOfObservableSource, paramFunction, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> combineLatestDelayError(ObservableSource<? extends T>[] paramArrayOfObservableSource, Function<? super Object[], ? extends R> paramFunction, int paramInt)
  {
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    ObjectHelper.requireNonNull(paramFunction, "combiner is null");
    if (paramArrayOfObservableSource.length == 0) {
      return empty();
    }
    return RxJavaPlugins.onAssembly(new ObservableCombineLatest(paramArrayOfObservableSource, null, paramFunction, paramInt << 1, true));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concat(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource)
  {
    return concat(paramObservableSource, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concat(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource, int paramInt)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "sources is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    return RxJavaPlugins.onAssembly(new ObservableConcatMap(paramObservableSource, Functions.identity(), paramInt, ErrorMode.IMMEDIATE));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concat(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2)
  {
    ObjectHelper.requireNonNull(paramObservableSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source2 is null");
    return concatArray(new ObservableSource[] { paramObservableSource1, paramObservableSource2 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concat(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, ObservableSource<? extends T> paramObservableSource3)
  {
    ObjectHelper.requireNonNull(paramObservableSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source3 is null");
    return concatArray(new ObservableSource[] { paramObservableSource1, paramObservableSource2, paramObservableSource3 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concat(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, ObservableSource<? extends T> paramObservableSource3, ObservableSource<? extends T> paramObservableSource4)
  {
    ObjectHelper.requireNonNull(paramObservableSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source4 is null");
    return concatArray(new ObservableSource[] { paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concat(Iterable<? extends ObservableSource<? extends T>> paramIterable)
  {
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    return fromIterable(paramIterable).concatMapDelayError(Functions.identity(), bufferSize(), false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatArray(ObservableSource<? extends T>... paramVarArgs)
  {
    if (paramVarArgs.length == 0) {
      return empty();
    }
    if (paramVarArgs.length == 1) {
      return wrap(paramVarArgs[0]);
    }
    return RxJavaPlugins.onAssembly(new ObservableConcatMap(fromArray(paramVarArgs), Functions.identity(), bufferSize(), ErrorMode.BOUNDARY));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatArrayDelayError(ObservableSource<? extends T>... paramVarArgs)
  {
    if (paramVarArgs.length == 0) {
      return empty();
    }
    if (paramVarArgs.length == 1) {
      return wrap(paramVarArgs[0]);
    }
    return concatDelayError(fromArray(paramVarArgs));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatArrayEager(int paramInt1, int paramInt2, ObservableSource<? extends T>... paramVarArgs)
  {
    return fromArray(paramVarArgs).concatMapEagerDelayError(Functions.identity(), paramInt1, paramInt2, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatArrayEager(ObservableSource<? extends T>... paramVarArgs)
  {
    return concatArrayEager(bufferSize(), bufferSize(), paramVarArgs);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatArrayEagerDelayError(int paramInt1, int paramInt2, ObservableSource<? extends T>... paramVarArgs)
  {
    return fromArray(paramVarArgs).concatMapEagerDelayError(Functions.identity(), paramInt1, paramInt2, true);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatArrayEagerDelayError(ObservableSource<? extends T>... paramVarArgs)
  {
    return concatArrayEagerDelayError(bufferSize(), bufferSize(), paramVarArgs);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatDelayError(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource)
  {
    return concatDelayError(paramObservableSource, bufferSize(), true);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatDelayError(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource, int paramInt, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "sources is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch is null");
    Function localFunction = Functions.identity();
    ErrorMode localErrorMode;
    if (paramBoolean) {
      localErrorMode = ErrorMode.END;
    } else {
      localErrorMode = ErrorMode.BOUNDARY;
    }
    return RxJavaPlugins.onAssembly(new ObservableConcatMap(paramObservableSource, localFunction, paramInt, localErrorMode));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatDelayError(Iterable<? extends ObservableSource<? extends T>> paramIterable)
  {
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    return concatDelayError(fromIterable(paramIterable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatEager(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource)
  {
    return concatEager(paramObservableSource, bufferSize(), bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatEager(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource, int paramInt1, int paramInt2)
  {
    return wrap(paramObservableSource).concatMapEager(Functions.identity(), paramInt1, paramInt2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatEager(Iterable<? extends ObservableSource<? extends T>> paramIterable)
  {
    return concatEager(paramIterable, bufferSize(), bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> concatEager(Iterable<? extends ObservableSource<? extends T>> paramIterable, int paramInt1, int paramInt2)
  {
    return fromIterable(paramIterable).concatMapEagerDelayError(Functions.identity(), paramInt1, paramInt2, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> create(ObservableOnSubscribe<T> paramObservableOnSubscribe)
  {
    ObjectHelper.requireNonNull(paramObservableOnSubscribe, "source is null");
    return RxJavaPlugins.onAssembly(new ObservableCreate(paramObservableOnSubscribe));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> defer(Callable<? extends ObservableSource<? extends T>> paramCallable)
  {
    ObjectHelper.requireNonNull(paramCallable, "supplier is null");
    return RxJavaPlugins.onAssembly(new ObservableDefer(paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  private Observable<T> doOnEach(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction1, Action paramAction2)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onNext is null");
    ObjectHelper.requireNonNull(paramConsumer1, "onError is null");
    ObjectHelper.requireNonNull(paramAction1, "onComplete is null");
    ObjectHelper.requireNonNull(paramAction2, "onAfterTerminate is null");
    return RxJavaPlugins.onAssembly(new ObservableDoOnEach(this, paramConsumer, paramConsumer1, paramAction1, paramAction2));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> empty()
  {
    return RxJavaPlugins.onAssembly(ObservableEmpty.INSTANCE);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> error(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "exception is null");
    return error(Functions.justCallable(paramThrowable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> error(Callable<? extends Throwable> paramCallable)
  {
    ObjectHelper.requireNonNull(paramCallable, "errorSupplier is null");
    return RxJavaPlugins.onAssembly(new ObservableError(paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> fromArray(T... paramVarArgs)
  {
    ObjectHelper.requireNonNull(paramVarArgs, "items is null");
    if (paramVarArgs.length == 0) {
      return empty();
    }
    if (paramVarArgs.length == 1) {
      return just(paramVarArgs[0]);
    }
    return RxJavaPlugins.onAssembly(new ObservableFromArray(paramVarArgs));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> fromCallable(Callable<? extends T> paramCallable)
  {
    ObjectHelper.requireNonNull(paramCallable, "supplier is null");
    return RxJavaPlugins.onAssembly(new ObservableFromCallable(paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> fromFuture(Future<? extends T> paramFuture)
  {
    ObjectHelper.requireNonNull(paramFuture, "future is null");
    return RxJavaPlugins.onAssembly(new ObservableFromFuture(paramFuture, 0L, null));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> fromFuture(Future<? extends T> paramFuture, long paramLong, TimeUnit paramTimeUnit)
  {
    ObjectHelper.requireNonNull(paramFuture, "future is null");
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    return RxJavaPlugins.onAssembly(new ObservableFromFuture(paramFuture, paramLong, paramTimeUnit));
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public static <T> Observable<T> fromFuture(Future<? extends T> paramFuture, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return fromFuture(paramFuture, paramLong, paramTimeUnit).subscribeOn(paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public static <T> Observable<T> fromFuture(Future<? extends T> paramFuture, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return fromFuture(paramFuture).subscribeOn(paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> fromIterable(Iterable<? extends T> paramIterable)
  {
    ObjectHelper.requireNonNull(paramIterable, "source is null");
    return RxJavaPlugins.onAssembly(new ObservableFromIterable(paramIterable));
  }
  
  @BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> fromPublisher(Publisher<? extends T> paramPublisher)
  {
    ObjectHelper.requireNonNull(paramPublisher, "publisher is null");
    return RxJavaPlugins.onAssembly(new ObservableFromPublisher(paramPublisher));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> generate(Consumer<Emitter<T>> paramConsumer)
  {
    ObjectHelper.requireNonNull(paramConsumer, "generator is null");
    return generate(Functions.nullSupplier(), ObservableInternalHelper.simpleGenerator(paramConsumer), Functions.emptyConsumer());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, S> Observable<T> generate(Callable<S> paramCallable, BiConsumer<S, Emitter<T>> paramBiConsumer)
  {
    ObjectHelper.requireNonNull(paramBiConsumer, "generator is null");
    return generate(paramCallable, ObservableInternalHelper.simpleBiGenerator(paramBiConsumer), Functions.emptyConsumer());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, S> Observable<T> generate(Callable<S> paramCallable, BiConsumer<S, Emitter<T>> paramBiConsumer, Consumer<? super S> paramConsumer)
  {
    ObjectHelper.requireNonNull(paramBiConsumer, "generator is null");
    return generate(paramCallable, ObservableInternalHelper.simpleBiGenerator(paramBiConsumer), paramConsumer);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, S> Observable<T> generate(Callable<S> paramCallable, BiFunction<S, Emitter<T>, S> paramBiFunction)
  {
    return generate(paramCallable, paramBiFunction, Functions.emptyConsumer());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, S> Observable<T> generate(Callable<S> paramCallable, BiFunction<S, Emitter<T>, S> paramBiFunction, Consumer<? super S> paramConsumer)
  {
    ObjectHelper.requireNonNull(paramCallable, "initialState is null");
    ObjectHelper.requireNonNull(paramBiFunction, "generator is null");
    ObjectHelper.requireNonNull(paramConsumer, "disposeState is null");
    return RxJavaPlugins.onAssembly(new ObservableGenerate(paramCallable, paramBiFunction, paramConsumer));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public static Observable<Long> interval(long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
  {
    return interval(paramLong1, paramLong2, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public static Observable<Long> interval(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableInterval(Math.max(0L, paramLong1), Math.max(0L, paramLong2), paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public static Observable<Long> interval(long paramLong, TimeUnit paramTimeUnit)
  {
    return interval(paramLong, paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public static Observable<Long> interval(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return interval(paramLong, paramLong, paramTimeUnit, paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public static Observable<Long> intervalRange(long paramLong1, long paramLong2, long paramLong3, long paramLong4, TimeUnit paramTimeUnit)
  {
    return intervalRange(paramLong1, paramLong2, paramLong3, paramLong4, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public static Observable<Long> intervalRange(long paramLong1, long paramLong2, long paramLong3, long paramLong4, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    boolean bool = paramLong2 < 0L;
    if (!bool)
    {
      if (!bool) {
        return empty().delay(paramLong3, paramTimeUnit, paramScheduler);
      }
      paramLong2 = paramLong1 + (paramLong2 - 1L);
      if ((paramLong1 > 0L) && (paramLong2 < 0L)) {
        throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
      }
      ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
      ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
      return RxJavaPlugins.onAssembly(new ObservableIntervalRange(paramLong1, paramLong2, Math.max(0L, paramLong3), Math.max(0L, paramLong4), paramTimeUnit, paramScheduler));
    }
    paramTimeUnit = new StringBuilder();
    paramTimeUnit.append("count >= 0 required but it was ");
    paramTimeUnit.append(paramLong2);
    throw new IllegalArgumentException(paramTimeUnit.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> just(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "item is null");
    return RxJavaPlugins.onAssembly(new ObservableJust(paramT));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> just(T paramT1, T paramT2)
  {
    ObjectHelper.requireNonNull(paramT1, "item1 is null");
    ObjectHelper.requireNonNull(paramT2, "item2 is null");
    return fromArray(new Object[] { paramT1, paramT2 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> just(T paramT1, T paramT2, T paramT3)
  {
    ObjectHelper.requireNonNull(paramT1, "item1 is null");
    ObjectHelper.requireNonNull(paramT2, "item2 is null");
    ObjectHelper.requireNonNull(paramT3, "item3 is null");
    return fromArray(new Object[] { paramT1, paramT2, paramT3 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> just(T paramT1, T paramT2, T paramT3, T paramT4)
  {
    ObjectHelper.requireNonNull(paramT1, "item1 is null");
    ObjectHelper.requireNonNull(paramT2, "item2 is null");
    ObjectHelper.requireNonNull(paramT3, "item3 is null");
    ObjectHelper.requireNonNull(paramT4, "item4 is null");
    return fromArray(new Object[] { paramT1, paramT2, paramT3, paramT4 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> just(T paramT1, T paramT2, T paramT3, T paramT4, T paramT5)
  {
    ObjectHelper.requireNonNull(paramT1, "item1 is null");
    ObjectHelper.requireNonNull(paramT2, "item2 is null");
    ObjectHelper.requireNonNull(paramT3, "item3 is null");
    ObjectHelper.requireNonNull(paramT4, "item4 is null");
    ObjectHelper.requireNonNull(paramT5, "item5 is null");
    return fromArray(new Object[] { paramT1, paramT2, paramT3, paramT4, paramT5 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> just(T paramT1, T paramT2, T paramT3, T paramT4, T paramT5, T paramT6)
  {
    ObjectHelper.requireNonNull(paramT1, "item1 is null");
    ObjectHelper.requireNonNull(paramT2, "item2 is null");
    ObjectHelper.requireNonNull(paramT3, "item3 is null");
    ObjectHelper.requireNonNull(paramT4, "item4 is null");
    ObjectHelper.requireNonNull(paramT5, "item5 is null");
    ObjectHelper.requireNonNull(paramT6, "item6 is null");
    return fromArray(new Object[] { paramT1, paramT2, paramT3, paramT4, paramT5, paramT6 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> just(T paramT1, T paramT2, T paramT3, T paramT4, T paramT5, T paramT6, T paramT7)
  {
    ObjectHelper.requireNonNull(paramT1, "item1 is null");
    ObjectHelper.requireNonNull(paramT2, "item2 is null");
    ObjectHelper.requireNonNull(paramT3, "item3 is null");
    ObjectHelper.requireNonNull(paramT4, "item4 is null");
    ObjectHelper.requireNonNull(paramT5, "item5 is null");
    ObjectHelper.requireNonNull(paramT6, "item6 is null");
    ObjectHelper.requireNonNull(paramT7, "item7 is null");
    return fromArray(new Object[] { paramT1, paramT2, paramT3, paramT4, paramT5, paramT6, paramT7 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> just(T paramT1, T paramT2, T paramT3, T paramT4, T paramT5, T paramT6, T paramT7, T paramT8)
  {
    ObjectHelper.requireNonNull(paramT1, "item1 is null");
    ObjectHelper.requireNonNull(paramT2, "item2 is null");
    ObjectHelper.requireNonNull(paramT3, "item3 is null");
    ObjectHelper.requireNonNull(paramT4, "item4 is null");
    ObjectHelper.requireNonNull(paramT5, "item5 is null");
    ObjectHelper.requireNonNull(paramT6, "item6 is null");
    ObjectHelper.requireNonNull(paramT7, "item7 is null");
    ObjectHelper.requireNonNull(paramT8, "item8 is null");
    return fromArray(new Object[] { paramT1, paramT2, paramT3, paramT4, paramT5, paramT6, paramT7, paramT8 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> just(T paramT1, T paramT2, T paramT3, T paramT4, T paramT5, T paramT6, T paramT7, T paramT8, T paramT9)
  {
    ObjectHelper.requireNonNull(paramT1, "item1 is null");
    ObjectHelper.requireNonNull(paramT2, "item2 is null");
    ObjectHelper.requireNonNull(paramT3, "item3 is null");
    ObjectHelper.requireNonNull(paramT4, "item4 is null");
    ObjectHelper.requireNonNull(paramT5, "item5 is null");
    ObjectHelper.requireNonNull(paramT6, "item6 is null");
    ObjectHelper.requireNonNull(paramT7, "item7 is null");
    ObjectHelper.requireNonNull(paramT8, "item8 is null");
    ObjectHelper.requireNonNull(paramT9, "item9 is null");
    return fromArray(new Object[] { paramT1, paramT2, paramT3, paramT4, paramT5, paramT6, paramT7, paramT8, paramT9 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> just(T paramT1, T paramT2, T paramT3, T paramT4, T paramT5, T paramT6, T paramT7, T paramT8, T paramT9, T paramT10)
  {
    ObjectHelper.requireNonNull(paramT1, "item1 is null");
    ObjectHelper.requireNonNull(paramT2, "item2 is null");
    ObjectHelper.requireNonNull(paramT3, "item3 is null");
    ObjectHelper.requireNonNull(paramT4, "item4 is null");
    ObjectHelper.requireNonNull(paramT5, "item5 is null");
    ObjectHelper.requireNonNull(paramT6, "item6 is null");
    ObjectHelper.requireNonNull(paramT7, "item7 is null");
    ObjectHelper.requireNonNull(paramT8, "item8 is null");
    ObjectHelper.requireNonNull(paramT9, "item9 is null");
    ObjectHelper.requireNonNull(paramT10, "item10 is null");
    return fromArray(new Object[] { paramT1, paramT2, paramT3, paramT4, paramT5, paramT6, paramT7, paramT8, paramT9, paramT10 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> merge(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "sources is null");
    return RxJavaPlugins.onAssembly(new ObservableFlatMap(paramObservableSource, Functions.identity(), false, Integer.MAX_VALUE, bufferSize()));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> merge(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource, int paramInt)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "sources is null");
    ObjectHelper.verifyPositive(paramInt, "maxConcurrency");
    return RxJavaPlugins.onAssembly(new ObservableFlatMap(paramObservableSource, Functions.identity(), false, paramInt, bufferSize()));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> merge(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2)
  {
    ObjectHelper.requireNonNull(paramObservableSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source2 is null");
    return fromArray(new ObservableSource[] { paramObservableSource1, paramObservableSource2 }).flatMap(Functions.identity(), false, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> merge(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, ObservableSource<? extends T> paramObservableSource3)
  {
    ObjectHelper.requireNonNull(paramObservableSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source3 is null");
    return fromArray(new ObservableSource[] { paramObservableSource1, paramObservableSource2, paramObservableSource3 }).flatMap(Functions.identity(), false, 3);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> merge(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, ObservableSource<? extends T> paramObservableSource3, ObservableSource<? extends T> paramObservableSource4)
  {
    ObjectHelper.requireNonNull(paramObservableSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source4 is null");
    return fromArray(new ObservableSource[] { paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4 }).flatMap(Functions.identity(), false, 4);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> paramIterable)
  {
    return fromIterable(paramIterable).flatMap(Functions.identity());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> paramIterable, int paramInt)
  {
    return fromIterable(paramIterable).flatMap(Functions.identity(), paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> paramIterable, int paramInt1, int paramInt2)
  {
    return fromIterable(paramIterable).flatMap(Functions.identity(), false, paramInt1, paramInt2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeArray(int paramInt1, int paramInt2, ObservableSource<? extends T>... paramVarArgs)
  {
    return fromArray(paramVarArgs).flatMap(Functions.identity(), false, paramInt1, paramInt2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeArray(ObservableSource<? extends T>... paramVarArgs)
  {
    return fromArray(paramVarArgs).flatMap(Functions.identity(), paramVarArgs.length);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeArrayDelayError(int paramInt1, int paramInt2, ObservableSource<? extends T>... paramVarArgs)
  {
    return fromArray(paramVarArgs).flatMap(Functions.identity(), true, paramInt1, paramInt2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeArrayDelayError(ObservableSource<? extends T>... paramVarArgs)
  {
    return fromArray(paramVarArgs).flatMap(Functions.identity(), true, paramVarArgs.length);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeDelayError(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "sources is null");
    return RxJavaPlugins.onAssembly(new ObservableFlatMap(paramObservableSource, Functions.identity(), true, Integer.MAX_VALUE, bufferSize()));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeDelayError(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource, int paramInt)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "sources is null");
    ObjectHelper.verifyPositive(paramInt, "maxConcurrency");
    return RxJavaPlugins.onAssembly(new ObservableFlatMap(paramObservableSource, Functions.identity(), true, paramInt, bufferSize()));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeDelayError(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2)
  {
    ObjectHelper.requireNonNull(paramObservableSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source2 is null");
    return fromArray(new ObservableSource[] { paramObservableSource1, paramObservableSource2 }).flatMap(Functions.identity(), true, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeDelayError(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, ObservableSource<? extends T> paramObservableSource3)
  {
    ObjectHelper.requireNonNull(paramObservableSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source3 is null");
    return fromArray(new ObservableSource[] { paramObservableSource1, paramObservableSource2, paramObservableSource3 }).flatMap(Functions.identity(), true, 3);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeDelayError(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, ObservableSource<? extends T> paramObservableSource3, ObservableSource<? extends T> paramObservableSource4)
  {
    ObjectHelper.requireNonNull(paramObservableSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source4 is null");
    return fromArray(new ObservableSource[] { paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4 }).flatMap(Functions.identity(), true, 4);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> paramIterable)
  {
    return fromIterable(paramIterable).flatMap(Functions.identity(), true);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> paramIterable, int paramInt)
  {
    return fromIterable(paramIterable).flatMap(Functions.identity(), true, paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> paramIterable, int paramInt1, int paramInt2)
  {
    return fromIterable(paramIterable).flatMap(Functions.identity(), true, paramInt1, paramInt2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> never()
  {
    return RxJavaPlugins.onAssembly(ObservableNever.INSTANCE);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static Observable<Integer> range(int paramInt1, int paramInt2)
  {
    if (paramInt2 >= 0)
    {
      if (paramInt2 == 0) {
        return empty();
      }
      if (paramInt2 == 1) {
        return just(Integer.valueOf(paramInt1));
      }
      if (paramInt1 + (paramInt2 - 1) <= 2147483647L) {
        return RxJavaPlugins.onAssembly(new ObservableRange(paramInt1, paramInt2));
      }
      throw new IllegalArgumentException("Integer overflow");
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("count >= 0 required but it was ");
    localStringBuilder.append(paramInt2);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static Observable<Long> rangeLong(long paramLong1, long paramLong2)
  {
    boolean bool = paramLong2 < 0L;
    if (!bool)
    {
      if (!bool) {
        return empty();
      }
      if (paramLong2 == 1L) {
        return just(Long.valueOf(paramLong1));
      }
      if ((paramLong1 > 0L) && (paramLong2 - 1L + paramLong1 < 0L)) {
        throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
      }
      return RxJavaPlugins.onAssembly(new ObservableRangeLong(paramLong1, paramLong2));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("count >= 0 required but it was ");
    localStringBuilder.append(paramLong2);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2)
  {
    return sequenceEqual(paramObservableSource1, paramObservableSource2, ObjectHelper.equalsPredicate(), bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, int paramInt)
  {
    return sequenceEqual(paramObservableSource1, paramObservableSource2, ObjectHelper.equalsPredicate(), paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, BiPredicate<? super T, ? super T> paramBiPredicate)
  {
    return sequenceEqual(paramObservableSource1, paramObservableSource2, paramBiPredicate, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, BiPredicate<? super T, ? super T> paramBiPredicate, int paramInt)
  {
    ObjectHelper.requireNonNull(paramObservableSource1, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source2 is null");
    ObjectHelper.requireNonNull(paramBiPredicate, "isEqual is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableSequenceEqualSingle(paramObservableSource1, paramObservableSource2, paramBiPredicate, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> switchOnNext(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource)
  {
    return switchOnNext(paramObservableSource, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> switchOnNext(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource, int paramInt)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "sources is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableSwitchMap(paramObservableSource, Functions.identity(), paramInt, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> switchOnNextDelayError(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource)
  {
    return switchOnNextDelayError(paramObservableSource, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> switchOnNextDelayError(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource, int paramInt)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "sources is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    return RxJavaPlugins.onAssembly(new ObservableSwitchMap(paramObservableSource, Functions.identity(), paramInt, true));
  }
  
  private Observable<T> timeout0(long paramLong, TimeUnit paramTimeUnit, ObservableSource<? extends T> paramObservableSource, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "timeUnit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableTimeoutTimed(this, paramLong, paramTimeUnit, paramScheduler, paramObservableSource));
  }
  
  private <U, V> Observable<T> timeout0(ObservableSource<U> paramObservableSource, Function<? super T, ? extends ObservableSource<V>> paramFunction, ObservableSource<? extends T> paramObservableSource1)
  {
    ObjectHelper.requireNonNull(paramFunction, "itemTimeoutIndicator is null");
    return RxJavaPlugins.onAssembly(new ObservableTimeout(this, paramObservableSource, paramFunction, paramObservableSource1));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public static Observable<Long> timer(long paramLong, TimeUnit paramTimeUnit)
  {
    return timer(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public static Observable<Long> timer(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableTimer(Math.max(paramLong, 0L), paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> unsafeCreate(ObservableSource<T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "onSubscribe is null");
    if (!(paramObservableSource instanceof Observable)) {
      return RxJavaPlugins.onAssembly(new ObservableFromUnsafeSource(paramObservableSource));
    }
    throw new IllegalArgumentException("unsafeCreate(Observable) should be upgraded");
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, D> Observable<T> using(Callable<? extends D> paramCallable, Function<? super D, ? extends ObservableSource<? extends T>> paramFunction, Consumer<? super D> paramConsumer)
  {
    return using(paramCallable, paramFunction, paramConsumer, true);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, D> Observable<T> using(Callable<? extends D> paramCallable, Function<? super D, ? extends ObservableSource<? extends T>> paramFunction, Consumer<? super D> paramConsumer, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramCallable, "resourceSupplier is null");
    ObjectHelper.requireNonNull(paramFunction, "sourceSupplier is null");
    ObjectHelper.requireNonNull(paramConsumer, "disposer is null");
    return RxJavaPlugins.onAssembly(new ObservableUsing(paramCallable, paramFunction, paramConsumer, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T> Observable<T> wrap(ObservableSource<T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source is null");
    if ((paramObservableSource instanceof Observable)) {
      return RxJavaPlugins.onAssembly((Observable)paramObservableSource);
    }
    return RxJavaPlugins.onAssembly(new ObservableFromUnsafeSource(paramObservableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> zip(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, ObservableSource<? extends T5> paramObservableSource4, ObservableSource<? extends T6> paramObservableSource5, ObservableSource<? extends T7> paramObservableSource6, ObservableSource<? extends T8> paramObservableSource7, ObservableSource<? extends T9> paramObservableSource8, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> paramFunction9)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramObservableSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramObservableSource6, "source7 is null");
    ObjectHelper.requireNonNull(paramObservableSource7, "source8 is null");
    ObjectHelper.requireNonNull(paramObservableSource8, "source9 is null");
    return zipArray(Functions.toFunction(paramFunction9), false, bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4, paramObservableSource5, paramObservableSource6, paramObservableSource7, paramObservableSource8 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> zip(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, ObservableSource<? extends T5> paramObservableSource4, ObservableSource<? extends T6> paramObservableSource5, ObservableSource<? extends T7> paramObservableSource6, ObservableSource<? extends T8> paramObservableSource7, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> paramFunction8)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramObservableSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramObservableSource6, "source7 is null");
    ObjectHelper.requireNonNull(paramObservableSource7, "source8 is null");
    return zipArray(Functions.toFunction(paramFunction8), false, bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4, paramObservableSource5, paramObservableSource6, paramObservableSource7 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> zip(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, ObservableSource<? extends T5> paramObservableSource4, ObservableSource<? extends T6> paramObservableSource5, ObservableSource<? extends T7> paramObservableSource6, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> paramFunction7)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramObservableSource5, "source6 is null");
    ObjectHelper.requireNonNull(paramObservableSource6, "source7 is null");
    return zipArray(Functions.toFunction(paramFunction7), false, bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4, paramObservableSource5, paramObservableSource6 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, T6, R> Observable<R> zip(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, ObservableSource<? extends T5> paramObservableSource4, ObservableSource<? extends T6> paramObservableSource5, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> paramFunction6)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source5 is null");
    ObjectHelper.requireNonNull(paramObservableSource5, "source6 is null");
    return zipArray(Functions.toFunction(paramFunction6), false, bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4, paramObservableSource5 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, T5, R> Observable<R> zip(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, ObservableSource<? extends T5> paramObservableSource4, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> paramFunction5)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    ObjectHelper.requireNonNull(paramObservableSource4, "source5 is null");
    return zipArray(Functions.toFunction(paramFunction5), false, bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3, paramObservableSource4 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, T4, R> Observable<R> zip(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, ObservableSource<? extends T4> paramObservableSource3, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> paramFunction4)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "source4 is null");
    return zipArray(Functions.toFunction(paramFunction4), false, bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, T3, R> Observable<R> zip(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, ObservableSource<? extends T3> paramObservableSource2, Function3<? super T1, ? super T2, ? super T3, ? extends R> paramFunction3)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "source3 is null");
    return zipArray(Functions.toFunction(paramFunction3), false, bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, R> Observable<R> zip(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, BiFunction<? super T1, ? super T2, ? extends R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    return zipArray(Functions.toFunction(paramBiFunction), false, bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, R> Observable<R> zip(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, BiFunction<? super T1, ? super T2, ? extends R> paramBiFunction, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    return zipArray(Functions.toFunction(paramBiFunction), paramBoolean, bufferSize(), new ObservableSource[] { paramObservableSource, paramObservableSource1 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T1, T2, R> Observable<R> zip(ObservableSource<? extends T1> paramObservableSource, ObservableSource<? extends T2> paramObservableSource1, BiFunction<? super T1, ? super T2, ? extends R> paramBiFunction, boolean paramBoolean, int paramInt)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "source1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "source2 is null");
    return zipArray(Functions.toFunction(paramBiFunction), paramBoolean, paramInt, new ObservableSource[] { paramObservableSource, paramObservableSource1 });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> zip(ObservableSource<? extends ObservableSource<? extends T>> paramObservableSource, Function<? super Object[], ? extends R> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "zipper is null");
    ObjectHelper.requireNonNull(paramObservableSource, "sources is null");
    return RxJavaPlugins.onAssembly(new ObservableToList(paramObservableSource, 16).flatMap(ObservableInternalHelper.zipIterable(paramFunction)));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> zip(Iterable<? extends ObservableSource<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "zipper is null");
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    return RxJavaPlugins.onAssembly(new ObservableZip(null, paramIterable, paramFunction, bufferSize(), false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> zipArray(Function<? super Object[], ? extends R> paramFunction, boolean paramBoolean, int paramInt, ObservableSource<? extends T>... paramVarArgs)
  {
    if (paramVarArgs.length == 0) {
      return empty();
    }
    ObjectHelper.requireNonNull(paramFunction, "zipper is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableZip(paramVarArgs, null, paramFunction, paramInt, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public static <T, R> Observable<R> zipIterable(Iterable<? extends ObservableSource<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction, boolean paramBoolean, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "zipper is null");
    ObjectHelper.requireNonNull(paramIterable, "sources is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableZip(null, paramIterable, paramFunction, paramInt, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Boolean> all(Predicate<? super T> paramPredicate)
  {
    ObjectHelper.requireNonNull(paramPredicate, "predicate is null");
    return RxJavaPlugins.onAssembly(new ObservableAllSingle(this, paramPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> ambWith(ObservableSource<? extends T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return ambArray(new ObservableSource[] { this, paramObservableSource });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Boolean> any(Predicate<? super T> paramPredicate)
  {
    ObjectHelper.requireNonNull(paramPredicate, "predicate is null");
    return RxJavaPlugins.onAssembly(new ObservableAnySingle(this, paramPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> R as(ObservableConverter<T, ? extends R> paramObservableConverter)
  {
    return ((ObservableConverter)ObjectHelper.requireNonNull(paramObservableConverter, "converter is null")).apply(this);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final T blockingFirst()
  {
    Object localObject = new BlockingFirstObserver();
    subscribe((Observer)localObject);
    localObject = ((BlockingFirstObserver)localObject).blockingGet();
    if (localObject != null) {
      return localObject;
    }
    throw new NoSuchElementException();
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final T blockingFirst(T paramT)
  {
    Object localObject = new BlockingFirstObserver();
    subscribe((Observer)localObject);
    localObject = ((BlockingFirstObserver)localObject).blockingGet();
    if (localObject != null) {
      paramT = (TT)localObject;
    }
    return paramT;
  }
  
  /* Error */
  @SchedulerSupport("none")
  public final void blockingForEach(Consumer<? super T> paramConsumer)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 763	io/reactivex/Observable:blockingIterable	()Ljava/lang/Iterable;
    //   4: invokeinterface 769 1 0
    //   9: astore_2
    //   10: aload_2
    //   11: invokeinterface 775 1 0
    //   16: ifeq +37 -> 53
    //   19: aload_1
    //   20: aload_2
    //   21: invokeinterface 778 1 0
    //   26: invokeinterface 783 2 0
    //   31: goto -21 -> 10
    //   34: astore_1
    //   35: aload_1
    //   36: invokestatic 789	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   39: aload_2
    //   40: checkcast 791	io/reactivex/disposables/Disposable
    //   43: invokeinterface 794 1 0
    //   48: aload_1
    //   49: invokestatic 800	io/reactivex/internal/util/ExceptionHelper:wrapOrThrow	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
    //   52: athrow
    //   53: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	54	0	this	Observable
    //   0	54	1	paramConsumer	Consumer<? super T>
    //   9	31	2	localIterator	java.util.Iterator
    // Exception table:
    //   from	to	target	type
    //   19	31	34	finally
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Iterable<T> blockingIterable()
  {
    return blockingIterable(bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Iterable<T> blockingIterable(int paramInt)
  {
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return new BlockingObservableIterable(this, paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final T blockingLast()
  {
    Object localObject = new BlockingLastObserver();
    subscribe((Observer)localObject);
    localObject = ((BlockingLastObserver)localObject).blockingGet();
    if (localObject != null) {
      return localObject;
    }
    throw new NoSuchElementException();
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final T blockingLast(T paramT)
  {
    Object localObject = new BlockingLastObserver();
    subscribe((Observer)localObject);
    localObject = ((BlockingLastObserver)localObject).blockingGet();
    if (localObject != null) {
      paramT = (TT)localObject;
    }
    return paramT;
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Iterable<T> blockingLatest()
  {
    return new BlockingObservableLatest(this);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Iterable<T> blockingMostRecent(T paramT)
  {
    return new BlockingObservableMostRecent(this, paramT);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Iterable<T> blockingNext()
  {
    return new BlockingObservableNext(this);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final T blockingSingle()
  {
    Object localObject = singleElement().blockingGet();
    if (localObject != null) {
      return localObject;
    }
    throw new NoSuchElementException();
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final T blockingSingle(T paramT)
  {
    return single(paramT).blockingGet();
  }
  
  @SchedulerSupport("none")
  public final void blockingSubscribe()
  {
    ObservableBlockingSubscribe.subscribe(this);
  }
  
  @SchedulerSupport("none")
  public final void blockingSubscribe(Observer<? super T> paramObserver)
  {
    ObservableBlockingSubscribe.subscribe(this, paramObserver);
  }
  
  @SchedulerSupport("none")
  public final void blockingSubscribe(Consumer<? super T> paramConsumer)
  {
    ObservableBlockingSubscribe.subscribe(this, paramConsumer, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
  }
  
  @SchedulerSupport("none")
  public final void blockingSubscribe(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1)
  {
    ObservableBlockingSubscribe.subscribe(this, paramConsumer, paramConsumer1, Functions.EMPTY_ACTION);
  }
  
  @SchedulerSupport("none")
  public final void blockingSubscribe(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction)
  {
    ObservableBlockingSubscribe.subscribe(this, paramConsumer, paramConsumer1, paramAction);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<List<T>> buffer(int paramInt)
  {
    return buffer(paramInt, paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<List<T>> buffer(int paramInt1, int paramInt2)
  {
    return buffer(paramInt1, paramInt2, ArrayListSupplier.asCallable());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U extends Collection<? super T>> Observable<U> buffer(int paramInt1, int paramInt2, Callable<U> paramCallable)
  {
    ObjectHelper.verifyPositive(paramInt1, "count");
    ObjectHelper.verifyPositive(paramInt2, "skip");
    ObjectHelper.requireNonNull(paramCallable, "bufferSupplier is null");
    return RxJavaPlugins.onAssembly(new ObservableBuffer(this, paramInt1, paramInt2, paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U extends Collection<? super T>> Observable<U> buffer(int paramInt, Callable<U> paramCallable)
  {
    return buffer(paramInt, paramInt, paramCallable);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<List<T>> buffer(long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
  {
    return buffer(paramLong1, paramLong2, paramTimeUnit, Schedulers.computation(), ArrayListSupplier.asCallable());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<List<T>> buffer(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return buffer(paramLong1, paramLong2, paramTimeUnit, paramScheduler, ArrayListSupplier.asCallable());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final <U extends Collection<? super T>> Observable<U> buffer(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler, Callable<U> paramCallable)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    ObjectHelper.requireNonNull(paramCallable, "bufferSupplier is null");
    return RxJavaPlugins.onAssembly(new ObservableBufferTimed(this, paramLong1, paramLong2, paramTimeUnit, paramScheduler, paramCallable, Integer.MAX_VALUE, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<List<T>> buffer(long paramLong, TimeUnit paramTimeUnit)
  {
    return buffer(paramLong, paramTimeUnit, Schedulers.computation(), Integer.MAX_VALUE);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<List<T>> buffer(long paramLong, TimeUnit paramTimeUnit, int paramInt)
  {
    return buffer(paramLong, paramTimeUnit, Schedulers.computation(), paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<List<T>> buffer(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return buffer(paramLong, paramTimeUnit, paramScheduler, Integer.MAX_VALUE, ArrayListSupplier.asCallable(), false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<List<T>> buffer(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt)
  {
    return buffer(paramLong, paramTimeUnit, paramScheduler, paramInt, ArrayListSupplier.asCallable(), false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final <U extends Collection<? super T>> Observable<U> buffer(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt, Callable<U> paramCallable, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    ObjectHelper.requireNonNull(paramCallable, "bufferSupplier is null");
    ObjectHelper.verifyPositive(paramInt, "count");
    return RxJavaPlugins.onAssembly(new ObservableBufferTimed(this, paramLong, paramLong, paramTimeUnit, paramScheduler, paramCallable, paramInt, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <B> Observable<List<T>> buffer(ObservableSource<B> paramObservableSource)
  {
    return buffer(paramObservableSource, ArrayListSupplier.asCallable());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <B> Observable<List<T>> buffer(ObservableSource<B> paramObservableSource, int paramInt)
  {
    ObjectHelper.verifyPositive(paramInt, "initialCapacity");
    return buffer(paramObservableSource, Functions.createArrayList(paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <TOpening, TClosing> Observable<List<T>> buffer(ObservableSource<? extends TOpening> paramObservableSource, Function<? super TOpening, ? extends ObservableSource<? extends TClosing>> paramFunction)
  {
    return buffer(paramObservableSource, paramFunction, ArrayListSupplier.asCallable());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <TOpening, TClosing, U extends Collection<? super T>> Observable<U> buffer(ObservableSource<? extends TOpening> paramObservableSource, Function<? super TOpening, ? extends ObservableSource<? extends TClosing>> paramFunction, Callable<U> paramCallable)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "openingIndicator is null");
    ObjectHelper.requireNonNull(paramFunction, "closingIndicator is null");
    ObjectHelper.requireNonNull(paramCallable, "bufferSupplier is null");
    return RxJavaPlugins.onAssembly(new ObservableBufferBoundary(this, paramObservableSource, paramFunction, paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <B, U extends Collection<? super T>> Observable<U> buffer(ObservableSource<B> paramObservableSource, Callable<U> paramCallable)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "boundary is null");
    ObjectHelper.requireNonNull(paramCallable, "bufferSupplier is null");
    return RxJavaPlugins.onAssembly(new ObservableBufferExactBoundary(this, paramObservableSource, paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <B> Observable<List<T>> buffer(Callable<? extends ObservableSource<B>> paramCallable)
  {
    return buffer(paramCallable, ArrayListSupplier.asCallable());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <B, U extends Collection<? super T>> Observable<U> buffer(Callable<? extends ObservableSource<B>> paramCallable, Callable<U> paramCallable1)
  {
    ObjectHelper.requireNonNull(paramCallable, "boundarySupplier is null");
    ObjectHelper.requireNonNull(paramCallable1, "bufferSupplier is null");
    return RxJavaPlugins.onAssembly(new ObservableBufferBoundarySupplier(this, paramCallable, paramCallable1));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> cache()
  {
    return cacheWithInitialCapacity(16);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> cacheWithInitialCapacity(int paramInt)
  {
    ObjectHelper.verifyPositive(paramInt, "initialCapacity");
    return RxJavaPlugins.onAssembly(new ObservableCache(this, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<U> cast(Class<U> paramClass)
  {
    ObjectHelper.requireNonNull(paramClass, "clazz is null");
    return map(Functions.castFunction(paramClass));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Single<U> collect(Callable<? extends U> paramCallable, BiConsumer<? super U, ? super T> paramBiConsumer)
  {
    ObjectHelper.requireNonNull(paramCallable, "initialValueSupplier is null");
    ObjectHelper.requireNonNull(paramBiConsumer, "collector is null");
    return RxJavaPlugins.onAssembly(new ObservableCollectSingle(this, paramCallable, paramBiConsumer));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Single<U> collectInto(U paramU, BiConsumer<? super U, ? super T> paramBiConsumer)
  {
    ObjectHelper.requireNonNull(paramU, "initialValue is null");
    return collect(Functions.justCallable(paramU), paramBiConsumer);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> compose(ObservableTransformer<? super T, ? extends R> paramObservableTransformer)
  {
    return wrap(((ObservableTransformer)ObjectHelper.requireNonNull(paramObservableTransformer, "composer is null")).apply(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMap(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction)
  {
    return concatMap(paramFunction, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMap(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    if ((this instanceof ScalarCallable))
    {
      Object localObject = ((ScalarCallable)this).call();
      if (localObject == null) {
        return empty();
      }
      return ObservableScalarXMap.scalarXMap(localObject, paramFunction);
    }
    return RxJavaPlugins.onAssembly(new ObservableConcatMap(this, paramFunction, paramInt, ErrorMode.IMMEDIATE));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable concatMapCompletable(Function<? super T, ? extends CompletableSource> paramFunction)
  {
    return concatMapCompletable(paramFunction, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable concatMapCompletable(Function<? super T, ? extends CompletableSource> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt, "capacityHint");
    return RxJavaPlugins.onAssembly(new ObservableConcatMapCompletable(this, paramFunction, ErrorMode.IMMEDIATE, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable concatMapCompletableDelayError(Function<? super T, ? extends CompletableSource> paramFunction)
  {
    return concatMapCompletableDelayError(paramFunction, true, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable concatMapCompletableDelayError(Function<? super T, ? extends CompletableSource> paramFunction, boolean paramBoolean)
  {
    return concatMapCompletableDelayError(paramFunction, paramBoolean, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable concatMapCompletableDelayError(Function<? super T, ? extends CompletableSource> paramFunction, boolean paramBoolean, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    ErrorMode localErrorMode;
    if (paramBoolean) {
      localErrorMode = ErrorMode.END;
    } else {
      localErrorMode = ErrorMode.BOUNDARY;
    }
    return RxJavaPlugins.onAssembly(new ObservableConcatMapCompletable(this, paramFunction, localErrorMode, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction)
  {
    return concatMapDelayError(paramFunction, bufferSize(), true);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, int paramInt, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    Object localObject;
    if ((this instanceof ScalarCallable))
    {
      localObject = ((ScalarCallable)this).call();
      if (localObject == null) {
        return empty();
      }
      return ObservableScalarXMap.scalarXMap(localObject, paramFunction);
    }
    if (paramBoolean) {
      localObject = ErrorMode.END;
    } else {
      localObject = ErrorMode.BOUNDARY;
    }
    return RxJavaPlugins.onAssembly(new ObservableConcatMap(this, paramFunction, paramInt, (ErrorMode)localObject));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapEager(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction)
  {
    return concatMapEager(paramFunction, Integer.MAX_VALUE, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapEager(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, int paramInt1, int paramInt2)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt1, "maxConcurrency");
    ObjectHelper.verifyPositive(paramInt2, "prefetch");
    return RxJavaPlugins.onAssembly(new ObservableConcatMapEager(this, paramFunction, ErrorMode.IMMEDIATE, paramInt1, paramInt2));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapEagerDelayError(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt1, "maxConcurrency");
    ObjectHelper.verifyPositive(paramInt2, "prefetch");
    ErrorMode localErrorMode;
    if (paramBoolean) {
      localErrorMode = ErrorMode.END;
    } else {
      localErrorMode = ErrorMode.BOUNDARY;
    }
    return RxJavaPlugins.onAssembly(new ObservableConcatMapEager(this, paramFunction, localErrorMode, paramInt1, paramInt2));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapEagerDelayError(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, boolean paramBoolean)
  {
    return concatMapEagerDelayError(paramFunction, Integer.MAX_VALUE, bufferSize(), paramBoolean);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<U> concatMapIterable(Function<? super T, ? extends Iterable<? extends U>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableFlattenIterable(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<U> concatMapIterable(Function<? super T, ? extends Iterable<? extends U>> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    return concatMap(ObservableInternalHelper.flatMapIntoIterable(paramFunction), paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction)
  {
    return concatMapMaybe(paramFunction, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    return RxJavaPlugins.onAssembly(new ObservableConcatMapMaybe(this, paramFunction, ErrorMode.IMMEDIATE, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapMaybeDelayError(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction)
  {
    return concatMapMaybeDelayError(paramFunction, true, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapMaybeDelayError(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction, boolean paramBoolean)
  {
    return concatMapMaybeDelayError(paramFunction, paramBoolean, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapMaybeDelayError(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction, boolean paramBoolean, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    ErrorMode localErrorMode;
    if (paramBoolean) {
      localErrorMode = ErrorMode.END;
    } else {
      localErrorMode = ErrorMode.BOUNDARY;
    }
    return RxJavaPlugins.onAssembly(new ObservableConcatMapMaybe(this, paramFunction, localErrorMode, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> paramFunction)
  {
    return concatMapSingle(paramFunction, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    return RxJavaPlugins.onAssembly(new ObservableConcatMapSingle(this, paramFunction, ErrorMode.IMMEDIATE, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapSingleDelayError(Function<? super T, ? extends SingleSource<? extends R>> paramFunction)
  {
    return concatMapSingleDelayError(paramFunction, true, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapSingleDelayError(Function<? super T, ? extends SingleSource<? extends R>> paramFunction, boolean paramBoolean)
  {
    return concatMapSingleDelayError(paramFunction, paramBoolean, 2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> concatMapSingleDelayError(Function<? super T, ? extends SingleSource<? extends R>> paramFunction, boolean paramBoolean, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt, "prefetch");
    ErrorMode localErrorMode;
    if (paramBoolean) {
      localErrorMode = ErrorMode.END;
    } else {
      localErrorMode = ErrorMode.BOUNDARY;
    }
    return RxJavaPlugins.onAssembly(new ObservableConcatMapSingle(this, paramFunction, localErrorMode, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> concatWith(CompletableSource paramCompletableSource)
  {
    ObjectHelper.requireNonNull(paramCompletableSource, "other is null");
    return RxJavaPlugins.onAssembly(new ObservableConcatWithCompletable(this, paramCompletableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> concatWith(MaybeSource<? extends T> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "other is null");
    return RxJavaPlugins.onAssembly(new ObservableConcatWithMaybe(this, paramMaybeSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> concatWith(ObservableSource<? extends T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return concat(this, paramObservableSource);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> concatWith(SingleSource<? extends T> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "other is null");
    return RxJavaPlugins.onAssembly(new ObservableConcatWithSingle(this, paramSingleSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Boolean> contains(Object paramObject)
  {
    ObjectHelper.requireNonNull(paramObject, "element is null");
    return any(Functions.equalsWith(paramObject));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Long> count()
  {
    return RxJavaPlugins.onAssembly(new ObservableCountSingle(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> debounce(long paramLong, TimeUnit paramTimeUnit)
  {
    return debounce(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> debounce(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableDebounceTimed(this, paramLong, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<T> debounce(Function<? super T, ? extends ObservableSource<U>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "debounceSelector is null");
    return RxJavaPlugins.onAssembly(new ObservableDebounce(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> defaultIfEmpty(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "defaultItem is null");
    return switchIfEmpty(just(paramT));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> delay(long paramLong, TimeUnit paramTimeUnit)
  {
    return delay(paramLong, paramTimeUnit, Schedulers.computation(), false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> delay(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return delay(paramLong, paramTimeUnit, paramScheduler, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> delay(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableDelay(this, paramLong, paramTimeUnit, paramScheduler, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> delay(long paramLong, TimeUnit paramTimeUnit, boolean paramBoolean)
  {
    return delay(paramLong, paramTimeUnit, Schedulers.computation(), paramBoolean);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, V> Observable<T> delay(ObservableSource<U> paramObservableSource, Function<? super T, ? extends ObservableSource<V>> paramFunction)
  {
    return delaySubscription(paramObservableSource).delay(paramFunction);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<T> delay(Function<? super T, ? extends ObservableSource<U>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "itemDelay is null");
    return flatMap(ObservableInternalHelper.itemDelay(paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> delaySubscription(long paramLong, TimeUnit paramTimeUnit)
  {
    return delaySubscription(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> delaySubscription(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return delaySubscription(timer(paramLong, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<T> delaySubscription(ObservableSource<U> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return RxJavaPlugins.onAssembly(new ObservableDelaySubscriptionOther(this, paramObservableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  @Deprecated
  public final <T2> Observable<T2> dematerialize()
  {
    return RxJavaPlugins.onAssembly(new ObservableDematerialize(this, Functions.identity()));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> dematerialize(Function<? super T, Notification<R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "selector is null");
    return RxJavaPlugins.onAssembly(new ObservableDematerialize(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> distinct()
  {
    return distinct(Functions.identity(), Functions.createHashSet());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K> Observable<T> distinct(Function<? super T, K> paramFunction)
  {
    return distinct(paramFunction, Functions.createHashSet());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K> Observable<T> distinct(Function<? super T, K> paramFunction, Callable<? extends Collection<? super K>> paramCallable)
  {
    ObjectHelper.requireNonNull(paramFunction, "keySelector is null");
    ObjectHelper.requireNonNull(paramCallable, "collectionSupplier is null");
    return RxJavaPlugins.onAssembly(new ObservableDistinct(this, paramFunction, paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> distinctUntilChanged()
  {
    return distinctUntilChanged(Functions.identity());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> distinctUntilChanged(BiPredicate<? super T, ? super T> paramBiPredicate)
  {
    ObjectHelper.requireNonNull(paramBiPredicate, "comparer is null");
    return RxJavaPlugins.onAssembly(new ObservableDistinctUntilChanged(this, Functions.identity(), paramBiPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K> Observable<T> distinctUntilChanged(Function<? super T, K> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "keySelector is null");
    return RxJavaPlugins.onAssembly(new ObservableDistinctUntilChanged(this, paramFunction, ObjectHelper.equalsPredicate()));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doAfterNext(Consumer<? super T> paramConsumer)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onAfterNext is null");
    return RxJavaPlugins.onAssembly(new ObservableDoAfterNext(this, paramConsumer));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doAfterTerminate(Action paramAction)
  {
    ObjectHelper.requireNonNull(paramAction, "onFinally is null");
    return doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), Functions.EMPTY_ACTION, paramAction);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doFinally(Action paramAction)
  {
    ObjectHelper.requireNonNull(paramAction, "onFinally is null");
    return RxJavaPlugins.onAssembly(new ObservableDoFinally(this, paramAction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doOnComplete(Action paramAction)
  {
    return doOnEach(Functions.emptyConsumer(), Functions.emptyConsumer(), paramAction, Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doOnDispose(Action paramAction)
  {
    return doOnLifecycle(Functions.emptyConsumer(), paramAction);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doOnEach(Observer<? super T> paramObserver)
  {
    ObjectHelper.requireNonNull(paramObserver, "observer is null");
    return doOnEach(ObservableInternalHelper.observerOnNext(paramObserver), ObservableInternalHelper.observerOnError(paramObserver), ObservableInternalHelper.observerOnComplete(paramObserver), Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doOnEach(Consumer<? super Notification<T>> paramConsumer)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onNotification is null");
    return doOnEach(Functions.notificationOnNext(paramConsumer), Functions.notificationOnError(paramConsumer), Functions.notificationOnComplete(paramConsumer), Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doOnError(Consumer<? super Throwable> paramConsumer)
  {
    return doOnEach(Functions.emptyConsumer(), paramConsumer, Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doOnLifecycle(Consumer<? super Disposable> paramConsumer, Action paramAction)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onSubscribe is null");
    ObjectHelper.requireNonNull(paramAction, "onDispose is null");
    return RxJavaPlugins.onAssembly(new ObservableDoOnLifecycle(this, paramConsumer, paramAction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doOnNext(Consumer<? super T> paramConsumer)
  {
    return doOnEach(paramConsumer, Functions.emptyConsumer(), Functions.EMPTY_ACTION, Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doOnSubscribe(Consumer<? super Disposable> paramConsumer)
  {
    return doOnLifecycle(paramConsumer, Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> doOnTerminate(Action paramAction)
  {
    ObjectHelper.requireNonNull(paramAction, "onTerminate is null");
    return doOnEach(Functions.emptyConsumer(), Functions.actionConsumer(paramAction), paramAction, Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> elementAt(long paramLong)
  {
    if (paramLong >= 0L) {
      return RxJavaPlugins.onAssembly(new ObservableElementAtMaybe(this, paramLong));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("index >= 0 required but it was ");
    localStringBuilder.append(paramLong);
    throw new IndexOutOfBoundsException(localStringBuilder.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> elementAt(long paramLong, T paramT)
  {
    if (paramLong >= 0L)
    {
      ObjectHelper.requireNonNull(paramT, "defaultItem is null");
      return RxJavaPlugins.onAssembly(new ObservableElementAtSingle(this, paramLong, paramT));
    }
    paramT = new StringBuilder();
    paramT.append("index >= 0 required but it was ");
    paramT.append(paramLong);
    throw new IndexOutOfBoundsException(paramT.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> elementAtOrError(long paramLong)
  {
    if (paramLong >= 0L) {
      return RxJavaPlugins.onAssembly(new ObservableElementAtSingle(this, paramLong, null));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("index >= 0 required but it was ");
    localStringBuilder.append(paramLong);
    throw new IndexOutOfBoundsException(localStringBuilder.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> filter(Predicate<? super T> paramPredicate)
  {
    ObjectHelper.requireNonNull(paramPredicate, "predicate is null");
    return RxJavaPlugins.onAssembly(new ObservableFilter(this, paramPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> first(T paramT)
  {
    return elementAt(0L, paramT);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> firstElement()
  {
    return elementAt(0L);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> firstOrError()
  {
    return elementAtOrError(0L);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction)
  {
    return flatMap(paramFunction, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, int paramInt)
  {
    return flatMap(paramFunction, false, paramInt, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> paramFunction, BiFunction<? super T, ? super U, ? extends R> paramBiFunction)
  {
    return flatMap(paramFunction, paramBiFunction, false, bufferSize(), bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> paramFunction, BiFunction<? super T, ? super U, ? extends R> paramBiFunction, int paramInt)
  {
    return flatMap(paramFunction, paramBiFunction, false, paramInt, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> paramFunction, BiFunction<? super T, ? super U, ? extends R> paramBiFunction, boolean paramBoolean)
  {
    return flatMap(paramFunction, paramBiFunction, paramBoolean, bufferSize(), bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> paramFunction, BiFunction<? super T, ? super U, ? extends R> paramBiFunction, boolean paramBoolean, int paramInt)
  {
    return flatMap(paramFunction, paramBiFunction, paramBoolean, paramInt, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> paramFunction, BiFunction<? super T, ? super U, ? extends R> paramBiFunction, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.requireNonNull(paramBiFunction, "combiner is null");
    return flatMap(ObservableInternalHelper.flatMapWithCombiner(paramFunction, paramBiFunction), paramBoolean, paramInt1, paramInt2);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, Function<? super Throwable, ? extends ObservableSource<? extends R>> paramFunction1, Callable<? extends ObservableSource<? extends R>> paramCallable)
  {
    ObjectHelper.requireNonNull(paramFunction, "onNextMapper is null");
    ObjectHelper.requireNonNull(paramFunction1, "onErrorMapper is null");
    ObjectHelper.requireNonNull(paramCallable, "onCompleteSupplier is null");
    return merge(new ObservableMapNotification(this, paramFunction, paramFunction1, paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, Function<Throwable, ? extends ObservableSource<? extends R>> paramFunction1, Callable<? extends ObservableSource<? extends R>> paramCallable, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "onNextMapper is null");
    ObjectHelper.requireNonNull(paramFunction1, "onErrorMapper is null");
    ObjectHelper.requireNonNull(paramCallable, "onCompleteSupplier is null");
    return merge(new ObservableMapNotification(this, paramFunction, paramFunction1, paramCallable), paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, boolean paramBoolean)
  {
    return flatMap(paramFunction, paramBoolean, Integer.MAX_VALUE);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, boolean paramBoolean, int paramInt)
  {
    return flatMap(paramFunction, paramBoolean, paramInt, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt1, "maxConcurrency");
    ObjectHelper.verifyPositive(paramInt2, "bufferSize");
    if ((this instanceof ScalarCallable))
    {
      Object localObject = ((ScalarCallable)this).call();
      if (localObject == null) {
        return empty();
      }
      return ObservableScalarXMap.scalarXMap(localObject, paramFunction);
    }
    return RxJavaPlugins.onAssembly(new ObservableFlatMap(this, paramFunction, paramBoolean, paramInt1, paramInt2));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> paramFunction)
  {
    return flatMapCompletable(paramFunction, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> paramFunction, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableFlatMapCompletableCompletable(this, paramFunction, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<U> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableFlattenIterable(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, V> Observable<V> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> paramFunction, BiFunction<? super T, ? super U, ? extends V> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.requireNonNull(paramBiFunction, "resultSelector is null");
    return flatMap(ObservableInternalHelper.flatMapIntoIterable(paramFunction), paramBiFunction, false, bufferSize(), bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction)
  {
    return flatMapMaybe(paramFunction, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableFlatMapMaybe(this, paramFunction, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> paramFunction)
  {
    return flatMapSingle(paramFunction, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> paramFunction, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableFlatMapSingle(this, paramFunction, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable forEach(Consumer<? super T> paramConsumer)
  {
    return subscribe(paramConsumer);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable forEachWhile(Predicate<? super T> paramPredicate)
  {
    return forEachWhile(paramPredicate, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable forEachWhile(Predicate<? super T> paramPredicate, Consumer<? super Throwable> paramConsumer)
  {
    return forEachWhile(paramPredicate, paramConsumer, Functions.EMPTY_ACTION);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable forEachWhile(Predicate<? super T> paramPredicate, Consumer<? super Throwable> paramConsumer, Action paramAction)
  {
    ObjectHelper.requireNonNull(paramPredicate, "onNext is null");
    ObjectHelper.requireNonNull(paramConsumer, "onError is null");
    ObjectHelper.requireNonNull(paramAction, "onComplete is null");
    paramPredicate = new ForEachWhileObserver(paramPredicate, paramConsumer, paramAction);
    subscribe(paramPredicate);
    return paramPredicate;
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K> Observable<GroupedObservable<K, T>> groupBy(Function<? super T, ? extends K> paramFunction)
  {
    return groupBy(paramFunction, Functions.identity(), false, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K, V> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1)
  {
    return groupBy(paramFunction, paramFunction1, false, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K, V> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1, boolean paramBoolean)
  {
    return groupBy(paramFunction, paramFunction1, paramBoolean, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K, V> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1, boolean paramBoolean, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "keySelector is null");
    ObjectHelper.requireNonNull(paramFunction1, "valueSelector is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableGroupBy(this, paramFunction, paramFunction1, paramInt, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K> Observable<GroupedObservable<K, T>> groupBy(Function<? super T, ? extends K> paramFunction, boolean paramBoolean)
  {
    return groupBy(paramFunction, Functions.identity(), paramBoolean, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <TRight, TLeftEnd, TRightEnd, R> Observable<R> groupJoin(ObservableSource<? extends TRight> paramObservableSource, Function<? super T, ? extends ObservableSource<TLeftEnd>> paramFunction, Function<? super TRight, ? extends ObservableSource<TRightEnd>> paramFunction1, BiFunction<? super T, ? super Observable<TRight>, ? extends R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    ObjectHelper.requireNonNull(paramFunction, "leftEnd is null");
    ObjectHelper.requireNonNull(paramFunction1, "rightEnd is null");
    ObjectHelper.requireNonNull(paramBiFunction, "resultSelector is null");
    return RxJavaPlugins.onAssembly(new ObservableGroupJoin(this, paramObservableSource, paramFunction, paramFunction1, paramBiFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> hide()
  {
    return RxJavaPlugins.onAssembly(new ObservableHide(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable ignoreElements()
  {
    return RxJavaPlugins.onAssembly(new ObservableIgnoreElementsCompletable(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<Boolean> isEmpty()
  {
    return all(Functions.alwaysFalse());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <TRight, TLeftEnd, TRightEnd, R> Observable<R> join(ObservableSource<? extends TRight> paramObservableSource, Function<? super T, ? extends ObservableSource<TLeftEnd>> paramFunction, Function<? super TRight, ? extends ObservableSource<TRightEnd>> paramFunction1, BiFunction<? super T, ? super TRight, ? extends R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    ObjectHelper.requireNonNull(paramFunction, "leftEnd is null");
    ObjectHelper.requireNonNull(paramFunction1, "rightEnd is null");
    ObjectHelper.requireNonNull(paramBiFunction, "resultSelector is null");
    return RxJavaPlugins.onAssembly(new ObservableJoin(this, paramObservableSource, paramFunction, paramFunction1, paramBiFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> last(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "defaultItem is null");
    return RxJavaPlugins.onAssembly(new ObservableLastSingle(this, paramT));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> lastElement()
  {
    return RxJavaPlugins.onAssembly(new ObservableLastMaybe(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> lastOrError()
  {
    return RxJavaPlugins.onAssembly(new ObservableLastSingle(this, null));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> lift(ObservableOperator<? extends R, ? super T> paramObservableOperator)
  {
    ObjectHelper.requireNonNull(paramObservableOperator, "lifter is null");
    return RxJavaPlugins.onAssembly(new ObservableLift(this, paramObservableOperator));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> map(Function<? super T, ? extends R> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableMap(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Notification<T>> materialize()
  {
    return RxJavaPlugins.onAssembly(new ObservableMaterialize(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> mergeWith(CompletableSource paramCompletableSource)
  {
    ObjectHelper.requireNonNull(paramCompletableSource, "other is null");
    return RxJavaPlugins.onAssembly(new ObservableMergeWithCompletable(this, paramCompletableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> mergeWith(MaybeSource<? extends T> paramMaybeSource)
  {
    ObjectHelper.requireNonNull(paramMaybeSource, "other is null");
    return RxJavaPlugins.onAssembly(new ObservableMergeWithMaybe(this, paramMaybeSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> mergeWith(ObservableSource<? extends T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return merge(this, paramObservableSource);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> mergeWith(SingleSource<? extends T> paramSingleSource)
  {
    ObjectHelper.requireNonNull(paramSingleSource, "other is null");
    return RxJavaPlugins.onAssembly(new ObservableMergeWithSingle(this, paramSingleSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> observeOn(Scheduler paramScheduler)
  {
    return observeOn(paramScheduler, false, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> observeOn(Scheduler paramScheduler, boolean paramBoolean)
  {
    return observeOn(paramScheduler, paramBoolean, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> observeOn(Scheduler paramScheduler, boolean paramBoolean, int paramInt)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableObserveOn(this, paramScheduler, paramBoolean, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<U> ofType(Class<U> paramClass)
  {
    ObjectHelper.requireNonNull(paramClass, "clazz is null");
    return filter(Functions.isInstanceOf(paramClass)).cast(paramClass);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> onErrorResumeNext(ObservableSource<? extends T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "next is null");
    return onErrorResumeNext(Functions.justFunction(paramObservableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> onErrorResumeNext(Function<? super Throwable, ? extends ObservableSource<? extends T>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "resumeFunction is null");
    return RxJavaPlugins.onAssembly(new ObservableOnErrorNext(this, paramFunction, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> onErrorReturn(Function<? super Throwable, ? extends T> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "valueSupplier is null");
    return RxJavaPlugins.onAssembly(new ObservableOnErrorReturn(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> onErrorReturnItem(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "item is null");
    return onErrorReturn(Functions.justFunction(paramT));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> onExceptionResumeNext(ObservableSource<? extends T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "next is null");
    return RxJavaPlugins.onAssembly(new ObservableOnErrorNext(this, Functions.justFunction(paramObservableSource), true));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> onTerminateDetach()
  {
    return RxJavaPlugins.onAssembly(new ObservableDetach(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> publish(Function<? super Observable<T>, ? extends ObservableSource<R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "selector is null");
    return RxJavaPlugins.onAssembly(new ObservablePublishSelector(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final ConnectableObservable<T> publish()
  {
    return ObservablePublish.create(this);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> reduce(BiFunction<T, T, T> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramBiFunction, "reducer is null");
    return RxJavaPlugins.onAssembly(new ObservableReduceMaybe(this, paramBiFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Single<R> reduce(R paramR, BiFunction<R, ? super T, R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramR, "seed is null");
    ObjectHelper.requireNonNull(paramBiFunction, "reducer is null");
    return RxJavaPlugins.onAssembly(new ObservableReduceSeedSingle(this, paramR, paramBiFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Single<R> reduceWith(Callable<R> paramCallable, BiFunction<R, ? super T, R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramCallable, "seedSupplier is null");
    ObjectHelper.requireNonNull(paramBiFunction, "reducer is null");
    return RxJavaPlugins.onAssembly(new ObservableReduceWithSingle(this, paramCallable, paramBiFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> repeat()
  {
    return repeat(Long.MAX_VALUE);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> repeat(long paramLong)
  {
    boolean bool = paramLong < 0L;
    if (!bool)
    {
      if (!bool) {
        return empty();
      }
      return RxJavaPlugins.onAssembly(new ObservableRepeat(this, paramLong));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("times >= 0 required but it was ");
    localStringBuilder.append(paramLong);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> repeatUntil(BooleanSupplier paramBooleanSupplier)
  {
    ObjectHelper.requireNonNull(paramBooleanSupplier, "stop is null");
    return RxJavaPlugins.onAssembly(new ObservableRepeatUntil(this, paramBooleanSupplier));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> repeatWhen(Function<? super Observable<Object>, ? extends ObservableSource<?>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "handler is null");
    return RxJavaPlugins.onAssembly(new ObservableRepeatWhen(this, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "selector is null");
    return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this), paramFunction);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "selector is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this, paramInt), paramFunction);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> paramFunction, int paramInt, long paramLong, TimeUnit paramTimeUnit)
  {
    return replay(paramFunction, paramInt, paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> paramFunction, int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramFunction, "selector is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this, paramInt, paramLong, paramTimeUnit, paramScheduler), paramFunction);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> paramFunction, int paramInt, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramFunction, "selector is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this, paramInt), ObservableInternalHelper.replayFunction(paramFunction, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> paramFunction, long paramLong, TimeUnit paramTimeUnit)
  {
    return replay(paramFunction, paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> paramFunction, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramFunction, "selector is null");
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this, paramLong, paramTimeUnit, paramScheduler), paramFunction);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> paramFunction, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramFunction, "selector is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return ObservableReplay.multicastSelector(ObservableInternalHelper.replayCallable(this), ObservableInternalHelper.replayFunction(paramFunction, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final ConnectableObservable<T> replay()
  {
    return ObservableReplay.createFrom(this);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final ConnectableObservable<T> replay(int paramInt)
  {
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return ObservableReplay.create(this, paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final ConnectableObservable<T> replay(int paramInt, long paramLong, TimeUnit paramTimeUnit)
  {
    return replay(paramInt, paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final ConnectableObservable<T> replay(int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return ObservableReplay.create(this, paramLong, paramTimeUnit, paramScheduler, paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final ConnectableObservable<T> replay(int paramInt, Scheduler paramScheduler)
  {
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return ObservableReplay.observeOn(replay(paramInt), paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final ConnectableObservable<T> replay(long paramLong, TimeUnit paramTimeUnit)
  {
    return replay(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final ConnectableObservable<T> replay(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return ObservableReplay.create(this, paramLong, paramTimeUnit, paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final ConnectableObservable<T> replay(Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return ObservableReplay.observeOn(replay(), paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> retry()
  {
    return retry(Long.MAX_VALUE, Functions.alwaysTrue());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> retry(long paramLong)
  {
    return retry(paramLong, Functions.alwaysTrue());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> retry(long paramLong, Predicate<? super Throwable> paramPredicate)
  {
    if (paramLong >= 0L)
    {
      ObjectHelper.requireNonNull(paramPredicate, "predicate is null");
      return RxJavaPlugins.onAssembly(new ObservableRetryPredicate(this, paramLong, paramPredicate));
    }
    paramPredicate = new StringBuilder();
    paramPredicate.append("times >= 0 required but it was ");
    paramPredicate.append(paramLong);
    throw new IllegalArgumentException(paramPredicate.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> retry(BiPredicate<? super Integer, ? super Throwable> paramBiPredicate)
  {
    ObjectHelper.requireNonNull(paramBiPredicate, "predicate is null");
    return RxJavaPlugins.onAssembly(new ObservableRetryBiPredicate(this, paramBiPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> retry(Predicate<? super Throwable> paramPredicate)
  {
    return retry(Long.MAX_VALUE, paramPredicate);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> retryUntil(BooleanSupplier paramBooleanSupplier)
  {
    ObjectHelper.requireNonNull(paramBooleanSupplier, "stop is null");
    return retry(Long.MAX_VALUE, Functions.predicateReverseFor(paramBooleanSupplier));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> retryWhen(Function<? super Observable<Throwable>, ? extends ObservableSource<?>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "handler is null");
    return RxJavaPlugins.onAssembly(new ObservableRetryWhen(this, paramFunction));
  }
  
  @SchedulerSupport("none")
  public final void safeSubscribe(Observer<? super T> paramObserver)
  {
    ObjectHelper.requireNonNull(paramObserver, "observer is null");
    if ((paramObserver instanceof SafeObserver)) {
      subscribe(paramObserver);
    } else {
      subscribe(new SafeObserver(paramObserver));
    }
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> sample(long paramLong, TimeUnit paramTimeUnit)
  {
    return sample(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> sample(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableSampleTimed(this, paramLong, paramTimeUnit, paramScheduler, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> sample(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableSampleTimed(this, paramLong, paramTimeUnit, paramScheduler, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> sample(long paramLong, TimeUnit paramTimeUnit, boolean paramBoolean)
  {
    return sample(paramLong, paramTimeUnit, Schedulers.computation(), paramBoolean);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<T> sample(ObservableSource<U> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "sampler is null");
    return RxJavaPlugins.onAssembly(new ObservableSampleWithObservable(this, paramObservableSource, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<T> sample(ObservableSource<U> paramObservableSource, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "sampler is null");
    return RxJavaPlugins.onAssembly(new ObservableSampleWithObservable(this, paramObservableSource, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> scan(BiFunction<T, T, T> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramBiFunction, "accumulator is null");
    return RxJavaPlugins.onAssembly(new ObservableScan(this, paramBiFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> scan(R paramR, BiFunction<R, ? super T, R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramR, "initialValue is null");
    return scanWith(Functions.justCallable(paramR), paramBiFunction);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> scanWith(Callable<R> paramCallable, BiFunction<R, ? super T, R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramCallable, "seedSupplier is null");
    ObjectHelper.requireNonNull(paramBiFunction, "accumulator is null");
    return RxJavaPlugins.onAssembly(new ObservableScanSeed(this, paramCallable, paramBiFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> serialize()
  {
    return RxJavaPlugins.onAssembly(new ObservableSerialized(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> share()
  {
    return publish().refCount();
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> single(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "defaultItem is null");
    return RxJavaPlugins.onAssembly(new ObservableSingleSingle(this, paramT));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Maybe<T> singleElement()
  {
    return RxJavaPlugins.onAssembly(new ObservableSingleMaybe(this));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<T> singleOrError()
  {
    return RxJavaPlugins.onAssembly(new ObservableSingleSingle(this, null));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> skip(long paramLong)
  {
    if (paramLong <= 0L) {
      return RxJavaPlugins.onAssembly(this);
    }
    return RxJavaPlugins.onAssembly(new ObservableSkip(this, paramLong));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> skip(long paramLong, TimeUnit paramTimeUnit)
  {
    return skipUntil(timer(paramLong, paramTimeUnit));
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> skip(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return skipUntil(timer(paramLong, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> skipLast(int paramInt)
  {
    if (paramInt >= 0)
    {
      if (paramInt == 0) {
        return RxJavaPlugins.onAssembly(this);
      }
      return RxJavaPlugins.onAssembly(new ObservableSkipLast(this, paramInt));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("count >= 0 required but it was ");
    localStringBuilder.append(paramInt);
    throw new IndexOutOfBoundsException(localStringBuilder.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:trampoline")
  public final Observable<T> skipLast(long paramLong, TimeUnit paramTimeUnit)
  {
    return skipLast(paramLong, paramTimeUnit, Schedulers.trampoline(), false, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> skipLast(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return skipLast(paramLong, paramTimeUnit, paramScheduler, false, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> skipLast(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    return skipLast(paramLong, paramTimeUnit, paramScheduler, paramBoolean, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> skipLast(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean, int paramInt)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableSkipLastTimed(this, paramLong, paramTimeUnit, paramScheduler, paramInt << 1, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:trampoline")
  public final Observable<T> skipLast(long paramLong, TimeUnit paramTimeUnit, boolean paramBoolean)
  {
    return skipLast(paramLong, paramTimeUnit, Schedulers.trampoline(), paramBoolean, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<T> skipUntil(ObservableSource<U> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return RxJavaPlugins.onAssembly(new ObservableSkipUntil(this, paramObservableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> skipWhile(Predicate<? super T> paramPredicate)
  {
    ObjectHelper.requireNonNull(paramPredicate, "predicate is null");
    return RxJavaPlugins.onAssembly(new ObservableSkipWhile(this, paramPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> sorted()
  {
    return toList().toObservable().map(Functions.listSorter(Functions.naturalComparator())).flatMapIterable(Functions.identity());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> sorted(Comparator<? super T> paramComparator)
  {
    ObjectHelper.requireNonNull(paramComparator, "sortFunction is null");
    return toList().toObservable().map(Functions.listSorter(paramComparator)).flatMapIterable(Functions.identity());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> startWith(ObservableSource<? extends T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return concatArray(new ObservableSource[] { paramObservableSource, this });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> startWith(Iterable<? extends T> paramIterable)
  {
    return concatArray(new ObservableSource[] { fromIterable(paramIterable), this });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> startWith(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "item is null");
    return concatArray(new ObservableSource[] { just(paramT), this });
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> startWithArray(T... paramVarArgs)
  {
    paramVarArgs = fromArray(paramVarArgs);
    if (paramVarArgs == empty()) {
      return RxJavaPlugins.onAssembly(this);
    }
    return concatArray(new ObservableSource[] { paramVarArgs, this });
  }
  
  @SchedulerSupport("none")
  public final Disposable subscribe()
  {
    return subscribe(Functions.emptyConsumer(), Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, Functions.emptyConsumer());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable subscribe(Consumer<? super T> paramConsumer)
  {
    return subscribe(paramConsumer, Functions.ON_ERROR_MISSING, Functions.EMPTY_ACTION, Functions.emptyConsumer());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable subscribe(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1)
  {
    return subscribe(paramConsumer, paramConsumer1, Functions.EMPTY_ACTION, Functions.emptyConsumer());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable subscribe(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction)
  {
    return subscribe(paramConsumer, paramConsumer1, paramAction, Functions.emptyConsumer());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Disposable subscribe(Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction, Consumer<? super Disposable> paramConsumer2)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onNext is null");
    ObjectHelper.requireNonNull(paramConsumer1, "onError is null");
    ObjectHelper.requireNonNull(paramAction, "onComplete is null");
    ObjectHelper.requireNonNull(paramConsumer2, "onSubscribe is null");
    paramConsumer = new LambdaObserver(paramConsumer, paramConsumer1, paramAction, paramConsumer2);
    subscribe(paramConsumer);
    return paramConsumer;
  }
  
  /* Error */
  @SchedulerSupport("none")
  public final void subscribe(Observer<? super T> paramObserver)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 1267
    //   4: invokestatic 28	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   7: pop
    //   8: aload_0
    //   9: aload_1
    //   10: invokestatic 1898	io/reactivex/plugins/RxJavaPlugins:onSubscribe	(Lio/reactivex/Observable;Lio/reactivex/Observer;)Lio/reactivex/Observer;
    //   13: astore_1
    //   14: aload_1
    //   15: ldc_w 1900
    //   18: invokestatic 28	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   21: pop
    //   22: aload_0
    //   23: aload_1
    //   24: invokevirtual 1903	io/reactivex/Observable:subscribeActual	(Lio/reactivex/Observer;)V
    //   27: return
    //   28: astore_1
    //   29: aload_1
    //   30: invokestatic 789	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   33: aload_1
    //   34: invokestatic 1906	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   37: new 1894	java/lang/NullPointerException
    //   40: dup
    //   41: ldc_w 1908
    //   44: invokespecial 1909	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   47: astore_2
    //   48: aload_2
    //   49: aload_1
    //   50: invokevirtual 1913	java/lang/NullPointerException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   53: pop
    //   54: aload_2
    //   55: athrow
    //   56: astore_1
    //   57: aload_1
    //   58: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	59	0	this	Observable
    //   0	59	1	paramObserver	Observer<? super T>
    //   47	8	2	localNullPointerException	NullPointerException
    // Exception table:
    //   from	to	target	type
    //   8	27	28	finally
    //   8	27	56	java/lang/NullPointerException
  }
  
  protected abstract void subscribeActual(Observer<? super T> paramObserver);
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> subscribeOn(Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableSubscribeOn(this, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <E extends Observer<? super T>> E subscribeWith(E paramE)
  {
    subscribe(paramE);
    return paramE;
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> switchIfEmpty(ObservableSource<? extends T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return RxJavaPlugins.onAssembly(new ObservableSwitchIfEmpty(this, paramObservableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> switchMap(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction)
  {
    return switchMap(paramFunction, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> switchMap(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    if ((this instanceof ScalarCallable))
    {
      Object localObject = ((ScalarCallable)this).call();
      if (localObject == null) {
        return empty();
      }
      return ObservableScalarXMap.scalarXMap(localObject, paramFunction);
    }
    return RxJavaPlugins.onAssembly(new ObservableSwitchMap(this, paramFunction, paramInt, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable switchMapCompletable(Function<? super T, ? extends CompletableSource> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableSwitchMapCompletable(this, paramFunction, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Completable switchMapCompletableDelayError(Function<? super T, ? extends CompletableSource> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableSwitchMapCompletable(this, paramFunction, true));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> switchMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction)
  {
    return switchMapDelayError(paramFunction, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> switchMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    if ((this instanceof ScalarCallable))
    {
      Object localObject = ((ScalarCallable)this).call();
      if (localObject == null) {
        return empty();
      }
      return ObservableScalarXMap.scalarXMap(localObject, paramFunction);
    }
    return RxJavaPlugins.onAssembly(new ObservableSwitchMap(this, paramFunction, paramInt, true));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> switchMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableSwitchMapMaybe(this, paramFunction, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> switchMapMaybeDelayError(Function<? super T, ? extends MaybeSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableSwitchMapMaybe(this, paramFunction, true));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> switchMapSingle(Function<? super T, ? extends SingleSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableSwitchMapSingle(this, paramFunction, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> switchMapSingleDelayError(Function<? super T, ? extends SingleSource<? extends R>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "mapper is null");
    return RxJavaPlugins.onAssembly(new ObservableSwitchMapSingle(this, paramFunction, true));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> take(long paramLong)
  {
    if (paramLong >= 0L) {
      return RxJavaPlugins.onAssembly(new ObservableTake(this, paramLong));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("count >= 0 required but it was ");
    localStringBuilder.append(paramLong);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> take(long paramLong, TimeUnit paramTimeUnit)
  {
    return takeUntil(timer(paramLong, paramTimeUnit));
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> take(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return takeUntil(timer(paramLong, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> takeLast(int paramInt)
  {
    if (paramInt >= 0)
    {
      if (paramInt == 0) {
        return RxJavaPlugins.onAssembly(new ObservableIgnoreElements(this));
      }
      if (paramInt == 1) {
        return RxJavaPlugins.onAssembly(new ObservableTakeLastOne(this));
      }
      return RxJavaPlugins.onAssembly(new ObservableTakeLast(this, paramInt));
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("count >= 0 required but it was ");
    localStringBuilder.append(paramInt);
    throw new IndexOutOfBoundsException(localStringBuilder.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:trampoline")
  public final Observable<T> takeLast(long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
  {
    return takeLast(paramLong1, paramLong2, paramTimeUnit, Schedulers.trampoline(), false, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> takeLast(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return takeLast(paramLong1, paramLong2, paramTimeUnit, paramScheduler, false, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> takeLast(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean, int paramInt)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    if (paramLong1 >= 0L) {
      return RxJavaPlugins.onAssembly(new ObservableTakeLastTimed(this, paramLong1, paramLong2, paramTimeUnit, paramScheduler, paramInt, paramBoolean));
    }
    paramTimeUnit = new StringBuilder();
    paramTimeUnit.append("count >= 0 required but it was ");
    paramTimeUnit.append(paramLong1);
    throw new IndexOutOfBoundsException(paramTimeUnit.toString());
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:trampoline")
  public final Observable<T> takeLast(long paramLong, TimeUnit paramTimeUnit)
  {
    return takeLast(paramLong, paramTimeUnit, Schedulers.trampoline(), false, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> takeLast(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return takeLast(paramLong, paramTimeUnit, paramScheduler, false, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> takeLast(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    return takeLast(paramLong, paramTimeUnit, paramScheduler, paramBoolean, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> takeLast(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean, int paramInt)
  {
    return takeLast(Long.MAX_VALUE, paramLong, paramTimeUnit, paramScheduler, paramBoolean, paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:trampoline")
  public final Observable<T> takeLast(long paramLong, TimeUnit paramTimeUnit, boolean paramBoolean)
  {
    return takeLast(paramLong, paramTimeUnit, Schedulers.trampoline(), paramBoolean, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U> Observable<T> takeUntil(ObservableSource<U> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return RxJavaPlugins.onAssembly(new ObservableTakeUntil(this, paramObservableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> takeUntil(Predicate<? super T> paramPredicate)
  {
    ObjectHelper.requireNonNull(paramPredicate, "stopPredicate is null");
    return RxJavaPlugins.onAssembly(new ObservableTakeUntilPredicate(this, paramPredicate));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<T> takeWhile(Predicate<? super T> paramPredicate)
  {
    ObjectHelper.requireNonNull(paramPredicate, "predicate is null");
    return RxJavaPlugins.onAssembly(new ObservableTakeWhile(this, paramPredicate));
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
      localTestObserver.dispose();
    }
    subscribe(localTestObserver);
    return localTestObserver;
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> throttleFirst(long paramLong, TimeUnit paramTimeUnit)
  {
    return throttleFirst(paramLong, paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> throttleFirst(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableThrottleFirstTimed(this, paramLong, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> throttleLast(long paramLong, TimeUnit paramTimeUnit)
  {
    return sample(paramLong, paramTimeUnit);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> throttleLast(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return sample(paramLong, paramTimeUnit, paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> throttleLatest(long paramLong, TimeUnit paramTimeUnit)
  {
    return throttleLatest(paramLong, paramTimeUnit, Schedulers.computation(), false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> throttleLatest(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return throttleLatest(paramLong, paramTimeUnit, paramScheduler, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> throttleLatest(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, boolean paramBoolean)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableThrottleLatest(this, paramLong, paramTimeUnit, paramScheduler, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> throttleLatest(long paramLong, TimeUnit paramTimeUnit, boolean paramBoolean)
  {
    return throttleLatest(paramLong, paramTimeUnit, Schedulers.computation(), paramBoolean);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> throttleWithTimeout(long paramLong, TimeUnit paramTimeUnit)
  {
    return debounce(paramLong, paramTimeUnit);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> throttleWithTimeout(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return debounce(paramLong, paramTimeUnit, paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Timed<T>> timeInterval()
  {
    return timeInterval(TimeUnit.MILLISECONDS, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Timed<T>> timeInterval(Scheduler paramScheduler)
  {
    return timeInterval(TimeUnit.MILLISECONDS, paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Timed<T>> timeInterval(TimeUnit paramTimeUnit)
  {
    return timeInterval(paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Timed<T>> timeInterval(TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableTimeInterval(this, paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> timeout(long paramLong, TimeUnit paramTimeUnit)
  {
    return timeout0(paramLong, paramTimeUnit, null, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<T> timeout(long paramLong, TimeUnit paramTimeUnit, ObservableSource<? extends T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return timeout0(paramLong, paramTimeUnit, paramObservableSource, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> timeout(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return timeout0(paramLong, paramTimeUnit, null, paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> timeout(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, ObservableSource<? extends T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return timeout0(paramLong, paramTimeUnit, paramObservableSource, paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, V> Observable<T> timeout(ObservableSource<U> paramObservableSource, Function<? super T, ? extends ObservableSource<V>> paramFunction)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "firstTimeoutIndicator is null");
    return timeout0(paramObservableSource, paramFunction, null);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, V> Observable<T> timeout(ObservableSource<U> paramObservableSource, Function<? super T, ? extends ObservableSource<V>> paramFunction, ObservableSource<? extends T> paramObservableSource1)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "firstTimeoutIndicator is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "other is null");
    return timeout0(paramObservableSource, paramFunction, paramObservableSource1);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <V> Observable<T> timeout(Function<? super T, ? extends ObservableSource<V>> paramFunction)
  {
    return timeout0(null, paramFunction, null);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <V> Observable<T> timeout(Function<? super T, ? extends ObservableSource<V>> paramFunction, ObservableSource<? extends T> paramObservableSource)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return timeout0(null, paramFunction, paramObservableSource);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Timed<T>> timestamp()
  {
    return timestamp(TimeUnit.MILLISECONDS, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Timed<T>> timestamp(Scheduler paramScheduler)
  {
    return timestamp(TimeUnit.MILLISECONDS, paramScheduler);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Timed<T>> timestamp(TimeUnit paramTimeUnit)
  {
    return timestamp(paramTimeUnit, Schedulers.computation());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Timed<T>> timestamp(TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return map(Functions.timestampWith(paramTimeUnit, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> R to(Function<? super Observable<T>, R> paramFunction)
  {
    try
    {
      paramFunction = ((Function)ObjectHelper.requireNonNull(paramFunction, "converter is null")).apply(this);
      return paramFunction;
    }
    finally
    {
      Exceptions.throwIfFatal(paramFunction);
    }
  }
  
  @BackpressureSupport(BackpressureKind.SPECIAL)
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Flowable<T> toFlowable(BackpressureStrategy paramBackpressureStrategy)
  {
    FlowableFromObservable localFlowableFromObservable = new FlowableFromObservable(this);
    int i = 1.$SwitchMap$io$reactivex$BackpressureStrategy[paramBackpressureStrategy.ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 3)
        {
          if (i != 4) {
            return localFlowableFromObservable.onBackpressureBuffer();
          }
          return RxJavaPlugins.onAssembly(new FlowableOnBackpressureError(localFlowableFromObservable));
        }
        return localFlowableFromObservable;
      }
      return localFlowableFromObservable.onBackpressureLatest();
    }
    return localFlowableFromObservable.onBackpressureDrop();
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Future<T> toFuture()
  {
    return (Future)subscribeWith(new FutureObserver());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<List<T>> toList()
  {
    return toList(16);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<List<T>> toList(int paramInt)
  {
    ObjectHelper.verifyPositive(paramInt, "capacityHint");
    return RxJavaPlugins.onAssembly(new ObservableToListSingle(this, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U extends Collection<? super T>> Single<U> toList(Callable<U> paramCallable)
  {
    ObjectHelper.requireNonNull(paramCallable, "collectionSupplier is null");
    return RxJavaPlugins.onAssembly(new ObservableToListSingle(this, paramCallable));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K> Single<Map<K, T>> toMap(Function<? super T, ? extends K> paramFunction)
  {
    ObjectHelper.requireNonNull(paramFunction, "keySelector is null");
    return collect(HashMapSupplier.asCallable(), Functions.toMapKeySelector(paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K, V> Single<Map<K, V>> toMap(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1)
  {
    ObjectHelper.requireNonNull(paramFunction, "keySelector is null");
    ObjectHelper.requireNonNull(paramFunction1, "valueSelector is null");
    return collect(HashMapSupplier.asCallable(), Functions.toMapKeyValueSelector(paramFunction, paramFunction1));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K, V> Single<Map<K, V>> toMap(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1, Callable<? extends Map<K, V>> paramCallable)
  {
    ObjectHelper.requireNonNull(paramFunction, "keySelector is null");
    ObjectHelper.requireNonNull(paramFunction1, "valueSelector is null");
    ObjectHelper.requireNonNull(paramCallable, "mapSupplier is null");
    return collect(paramCallable, Functions.toMapKeyValueSelector(paramFunction, paramFunction1));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K> Single<Map<K, Collection<T>>> toMultimap(Function<? super T, ? extends K> paramFunction)
  {
    return toMultimap(paramFunction, Functions.identity(), HashMapSupplier.asCallable(), ArrayListSupplier.asFunction());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K, V> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1)
  {
    return toMultimap(paramFunction, paramFunction1, HashMapSupplier.asCallable(), ArrayListSupplier.asFunction());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K, V> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1, Callable<Map<K, Collection<V>>> paramCallable)
  {
    return toMultimap(paramFunction, paramFunction1, paramCallable, ArrayListSupplier.asFunction());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <K, V> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> paramFunction, Function<? super T, ? extends V> paramFunction1, Callable<? extends Map<K, Collection<V>>> paramCallable, Function<? super K, ? extends Collection<? super V>> paramFunction2)
  {
    ObjectHelper.requireNonNull(paramFunction, "keySelector is null");
    ObjectHelper.requireNonNull(paramFunction1, "valueSelector is null");
    ObjectHelper.requireNonNull(paramCallable, "mapSupplier is null");
    ObjectHelper.requireNonNull(paramFunction2, "collectionFactory is null");
    return collect(paramCallable, Functions.toMultimapKeyValueSelector(paramFunction, paramFunction1, paramFunction2));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<List<T>> toSortedList()
  {
    return toSortedList(Functions.naturalOrder());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<List<T>> toSortedList(int paramInt)
  {
    return toSortedList(Functions.naturalOrder(), paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<List<T>> toSortedList(Comparator<? super T> paramComparator)
  {
    ObjectHelper.requireNonNull(paramComparator, "comparator is null");
    return toList().map(Functions.listSorter(paramComparator));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Single<List<T>> toSortedList(Comparator<? super T> paramComparator, int paramInt)
  {
    ObjectHelper.requireNonNull(paramComparator, "comparator is null");
    return toList(paramInt).map(Functions.listSorter(paramComparator));
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<T> unsubscribeOn(Scheduler paramScheduler)
  {
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    return RxJavaPlugins.onAssembly(new ObservableUnsubscribeOn(this, paramScheduler));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Observable<T>> window(long paramLong)
  {
    return window(paramLong, paramLong, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Observable<T>> window(long paramLong1, long paramLong2)
  {
    return window(paramLong1, paramLong2, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final Observable<Observable<T>> window(long paramLong1, long paramLong2, int paramInt)
  {
    ObjectHelper.verifyPositive(paramLong1, "count");
    ObjectHelper.verifyPositive(paramLong2, "skip");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableWindow(this, paramLong1, paramLong2, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<Observable<T>> window(long paramLong1, long paramLong2, TimeUnit paramTimeUnit)
  {
    return window(paramLong1, paramLong2, paramTimeUnit, Schedulers.computation(), bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<Observable<T>> window(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return window(paramLong1, paramLong2, paramTimeUnit, paramScheduler, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<Observable<T>> window(long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt)
  {
    ObjectHelper.verifyPositive(paramLong1, "timespan");
    ObjectHelper.verifyPositive(paramLong2, "timeskip");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    return RxJavaPlugins.onAssembly(new ObservableWindowTimed(this, paramLong1, paramLong2, paramTimeUnit, paramScheduler, Long.MAX_VALUE, paramInt, false));
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<Observable<T>> window(long paramLong, TimeUnit paramTimeUnit)
  {
    return window(paramLong, paramTimeUnit, Schedulers.computation(), Long.MAX_VALUE, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<Observable<T>> window(long paramLong1, TimeUnit paramTimeUnit, long paramLong2)
  {
    return window(paramLong1, paramTimeUnit, Schedulers.computation(), paramLong2, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("io.reactivex:computation")
  public final Observable<Observable<T>> window(long paramLong1, TimeUnit paramTimeUnit, long paramLong2, boolean paramBoolean)
  {
    return window(paramLong1, paramTimeUnit, Schedulers.computation(), paramLong2, paramBoolean);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<Observable<T>> window(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return window(paramLong, paramTimeUnit, paramScheduler, Long.MAX_VALUE, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<Observable<T>> window(long paramLong1, TimeUnit paramTimeUnit, Scheduler paramScheduler, long paramLong2)
  {
    return window(paramLong1, paramTimeUnit, paramScheduler, paramLong2, false);
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<Observable<T>> window(long paramLong1, TimeUnit paramTimeUnit, Scheduler paramScheduler, long paramLong2, boolean paramBoolean)
  {
    return window(paramLong1, paramTimeUnit, paramScheduler, paramLong2, paramBoolean, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("custom")
  public final Observable<Observable<T>> window(long paramLong1, TimeUnit paramTimeUnit, Scheduler paramScheduler, long paramLong2, boolean paramBoolean, int paramInt)
  {
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    ObjectHelper.requireNonNull(paramScheduler, "scheduler is null");
    ObjectHelper.requireNonNull(paramTimeUnit, "unit is null");
    ObjectHelper.verifyPositive(paramLong2, "count");
    return RxJavaPlugins.onAssembly(new ObservableWindowTimed(this, paramLong1, paramLong1, paramTimeUnit, paramScheduler, paramLong2, paramInt, paramBoolean));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <B> Observable<Observable<T>> window(ObservableSource<B> paramObservableSource)
  {
    return window(paramObservableSource, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <B> Observable<Observable<T>> window(ObservableSource<B> paramObservableSource, int paramInt)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "boundary is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableWindowBoundary(this, paramObservableSource, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, V> Observable<Observable<T>> window(ObservableSource<U> paramObservableSource, Function<? super U, ? extends ObservableSource<V>> paramFunction)
  {
    return window(paramObservableSource, paramFunction, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, V> Observable<Observable<T>> window(ObservableSource<U> paramObservableSource, Function<? super U, ? extends ObservableSource<V>> paramFunction, int paramInt)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "openingIndicator is null");
    ObjectHelper.requireNonNull(paramFunction, "closingIndicator is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableWindowBoundarySelector(this, paramObservableSource, paramFunction, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <B> Observable<Observable<T>> window(Callable<? extends ObservableSource<B>> paramCallable)
  {
    return window(paramCallable, bufferSize());
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <B> Observable<Observable<T>> window(Callable<? extends ObservableSource<B>> paramCallable, int paramInt)
  {
    ObjectHelper.requireNonNull(paramCallable, "boundary is null");
    ObjectHelper.verifyPositive(paramInt, "bufferSize");
    return RxJavaPlugins.onAssembly(new ObservableWindowBoundarySupplier(this, paramCallable, paramInt));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <T1, T2, T3, T4, R> Observable<R> withLatestFrom(ObservableSource<T1> paramObservableSource, ObservableSource<T2> paramObservableSource1, ObservableSource<T3> paramObservableSource2, ObservableSource<T4> paramObservableSource3, Function5<? super T, ? super T1, ? super T2, ? super T3, ? super T4, R> paramFunction5)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "o1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "o2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "o3 is null");
    ObjectHelper.requireNonNull(paramObservableSource3, "o4 is null");
    ObjectHelper.requireNonNull(paramFunction5, "combiner is null");
    paramFunction5 = Functions.toFunction(paramFunction5);
    return withLatestFrom(new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2, paramObservableSource3 }, paramFunction5);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <T1, T2, T3, R> Observable<R> withLatestFrom(ObservableSource<T1> paramObservableSource, ObservableSource<T2> paramObservableSource1, ObservableSource<T3> paramObservableSource2, Function4<? super T, ? super T1, ? super T2, ? super T3, R> paramFunction4)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "o1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "o2 is null");
    ObjectHelper.requireNonNull(paramObservableSource2, "o3 is null");
    ObjectHelper.requireNonNull(paramFunction4, "combiner is null");
    paramFunction4 = Functions.toFunction(paramFunction4);
    return withLatestFrom(new ObservableSource[] { paramObservableSource, paramObservableSource1, paramObservableSource2 }, paramFunction4);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <T1, T2, R> Observable<R> withLatestFrom(ObservableSource<T1> paramObservableSource, ObservableSource<T2> paramObservableSource1, Function3<? super T, ? super T1, ? super T2, R> paramFunction3)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "o1 is null");
    ObjectHelper.requireNonNull(paramObservableSource1, "o2 is null");
    ObjectHelper.requireNonNull(paramFunction3, "combiner is null");
    paramFunction3 = Functions.toFunction(paramFunction3);
    return withLatestFrom(new ObservableSource[] { paramObservableSource, paramObservableSource1 }, paramFunction3);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Observable<R> withLatestFrom(ObservableSource<? extends U> paramObservableSource, BiFunction<? super T, ? super U, ? extends R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    ObjectHelper.requireNonNull(paramBiFunction, "combiner is null");
    return RxJavaPlugins.onAssembly(new ObservableWithLatestFrom(this, paramBiFunction, paramObservableSource));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> withLatestFrom(Iterable<? extends ObservableSource<?>> paramIterable, Function<? super Object[], R> paramFunction)
  {
    ObjectHelper.requireNonNull(paramIterable, "others is null");
    ObjectHelper.requireNonNull(paramFunction, "combiner is null");
    return RxJavaPlugins.onAssembly(new ObservableWithLatestFromMany(this, paramIterable, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <R> Observable<R> withLatestFrom(ObservableSource<?>[] paramArrayOfObservableSource, Function<? super Object[], R> paramFunction)
  {
    ObjectHelper.requireNonNull(paramArrayOfObservableSource, "others is null");
    ObjectHelper.requireNonNull(paramFunction, "combiner is null");
    return RxJavaPlugins.onAssembly(new ObservableWithLatestFromMany(this, paramArrayOfObservableSource, paramFunction));
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Observable<R> zipWith(ObservableSource<? extends U> paramObservableSource, BiFunction<? super T, ? super U, ? extends R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramObservableSource, "other is null");
    return zip(this, paramObservableSource, paramBiFunction);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Observable<R> zipWith(ObservableSource<? extends U> paramObservableSource, BiFunction<? super T, ? super U, ? extends R> paramBiFunction, boolean paramBoolean)
  {
    return zip(this, paramObservableSource, paramBiFunction, paramBoolean);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Observable<R> zipWith(ObservableSource<? extends U> paramObservableSource, BiFunction<? super T, ? super U, ? extends R> paramBiFunction, boolean paramBoolean, int paramInt)
  {
    return zip(this, paramObservableSource, paramBiFunction, paramBoolean, paramInt);
  }
  
  @CheckReturnValue
  @SchedulerSupport("none")
  public final <U, R> Observable<R> zipWith(Iterable<U> paramIterable, BiFunction<? super T, ? super U, ? extends R> paramBiFunction)
  {
    ObjectHelper.requireNonNull(paramIterable, "other is null");
    ObjectHelper.requireNonNull(paramBiFunction, "zipper is null");
    return RxJavaPlugins.onAssembly(new ObservableZipIterable(this, paramIterable, paramBiFunction));
  }
}
