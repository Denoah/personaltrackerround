package kotlinx.coroutines.flow;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BroadcastChannel;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;

@Metadata(bv={1, 0, 3}, d1={"kotlinx/coroutines/flow/FlowKt__BuildersKt", "kotlinx/coroutines/flow/FlowKt__ChannelsKt", "kotlinx/coroutines/flow/FlowKt__CollectKt", "kotlinx/coroutines/flow/FlowKt__CollectionKt", "kotlinx/coroutines/flow/FlowKt__ContextKt", "kotlinx/coroutines/flow/FlowKt__CountKt", "kotlinx/coroutines/flow/FlowKt__DelayKt", "kotlinx/coroutines/flow/FlowKt__DistinctKt", "kotlinx/coroutines/flow/FlowKt__EmittersKt", "kotlinx/coroutines/flow/FlowKt__ErrorsKt", "kotlinx/coroutines/flow/FlowKt__LimitKt", "kotlinx/coroutines/flow/FlowKt__MergeKt", "kotlinx/coroutines/flow/FlowKt__MigrationKt", "kotlinx/coroutines/flow/FlowKt__ReduceKt", "kotlinx/coroutines/flow/FlowKt__TransformKt", "kotlinx/coroutines/flow/FlowKt__ZipKt"}, k=4, mv={1, 1, 16})
public final class FlowKt
{
  public static final String DEFAULT_CONCURRENCY_PROPERTY_NAME = "kotlinx.coroutines.flow.defaultConcurrency";
  
  public static final <T> Flow<T> asFlow(Iterable<? extends T> paramIterable)
  {
    return FlowKt__BuildersKt.asFlow(paramIterable);
  }
  
  public static final <T> Flow<T> asFlow(Iterator<? extends T> paramIterator)
  {
    return FlowKt__BuildersKt.asFlow(paramIterator);
  }
  
  public static final <T> Flow<T> asFlow(Function0<? extends T> paramFunction0)
  {
    return FlowKt__BuildersKt.asFlow(paramFunction0);
  }
  
  public static final <T> Flow<T> asFlow(Function1<? super Continuation<? super T>, ? extends Object> paramFunction1)
  {
    return FlowKt__BuildersKt.asFlow(paramFunction1);
  }
  
  public static final Flow<Integer> asFlow(IntRange paramIntRange)
  {
    return FlowKt__BuildersKt.asFlow(paramIntRange);
  }
  
  public static final Flow<Long> asFlow(LongRange paramLongRange)
  {
    return FlowKt__BuildersKt.asFlow(paramLongRange);
  }
  
  public static final <T> Flow<T> asFlow(Sequence<? extends T> paramSequence)
  {
    return FlowKt__BuildersKt.asFlow(paramSequence);
  }
  
  public static final <T> Flow<T> asFlow(BroadcastChannel<T> paramBroadcastChannel)
  {
    return FlowKt__ChannelsKt.asFlow(paramBroadcastChannel);
  }
  
  public static final Flow<Integer> asFlow(int[] paramArrayOfInt)
  {
    return FlowKt__BuildersKt.asFlow(paramArrayOfInt);
  }
  
  public static final Flow<Long> asFlow(long[] paramArrayOfLong)
  {
    return FlowKt__BuildersKt.asFlow(paramArrayOfLong);
  }
  
  public static final <T> Flow<T> asFlow(T[] paramArrayOfT)
  {
    return FlowKt__BuildersKt.asFlow(paramArrayOfT);
  }
  
  public static final <T> BroadcastChannel<T> broadcastIn(Flow<? extends T> paramFlow, CoroutineScope paramCoroutineScope, CoroutineStart paramCoroutineStart)
  {
    return FlowKt__ChannelsKt.broadcastIn(paramFlow, paramCoroutineScope, paramCoroutineStart);
  }
  
  public static final <T> Flow<T> buffer(Flow<? extends T> paramFlow, int paramInt)
  {
    return FlowKt__ContextKt.buffer(paramFlow, paramInt);
  }
  
  public static final <T> Flow<T> callbackFlow(Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    return FlowKt__BuildersKt.callbackFlow(paramFunction2);
  }
  
  public static final <T> Flow<T> jdMethod_catch(Flow<? extends T> paramFlow, Function3<? super FlowCollector<? super T>, ? super Throwable, ? super Continuation<? super Unit>, ? extends Object> paramFunction3)
  {
    return FlowKt__ErrorsKt.jdMethod_catch(paramFlow, paramFunction3);
  }
  
  public static final <T> Object catchImpl(Flow<? extends T> paramFlow, FlowCollector<? super T> paramFlowCollector, Continuation<? super Throwable> paramContinuation)
  {
    return FlowKt__ErrorsKt.catchImpl(paramFlow, paramFlowCollector, paramContinuation);
  }
  
  public static final <T> Flow<T> channelFlow(Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    return FlowKt__BuildersKt.channelFlow(paramFunction2);
  }
  
  public static final Object collect(Flow<?> paramFlow, Continuation<? super Unit> paramContinuation)
  {
    return FlowKt__CollectKt.collect(paramFlow, paramContinuation);
  }
  
  public static final <T> Object collect(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction2, Continuation<? super Unit> paramContinuation)
  {
    return FlowKt__CollectKt.collect(paramFlow, paramFunction2, paramContinuation);
  }
  
  private static final Object collect$$forInline(Flow paramFlow, Function2 paramFunction2, Continuation paramContinuation)
  {
    return FlowKt__CollectKt.collect(paramFlow, paramFunction2, paramContinuation);
  }
  
  public static final <T> Object collectIndexed(Flow<? extends T> paramFlow, Function3<? super Integer, ? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction3, Continuation<? super Unit> paramContinuation)
  {
    return FlowKt__CollectKt.collectIndexed(paramFlow, paramFunction3, paramContinuation);
  }
  
  private static final Object collectIndexed$$forInline(Flow paramFlow, Function3 paramFunction3, Continuation paramContinuation)
  {
    return FlowKt__CollectKt.collectIndexed(paramFlow, paramFunction3, paramContinuation);
  }
  
  public static final <T> Object collectLatest(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction2, Continuation<? super Unit> paramContinuation)
  {
    return FlowKt__CollectKt.collectLatest(paramFlow, paramFunction2, paramContinuation);
  }
  
  public static final <T1, T2, R> Flow<R> combine(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    return FlowKt__ZipKt.combine(paramFlow, paramFlow1, paramFunction3);
  }
  
  public static final <T1, T2, T3, R> Flow<R> combine(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Flow<? extends T3> paramFlow2, Function4<? super T1, ? super T2, ? super T3, ? super Continuation<? super R>, ? extends Object> paramFunction4)
  {
    return FlowKt__ZipKt.combine(paramFlow, paramFlow1, paramFlow2, paramFunction4);
  }
  
  public static final <T1, T2, T3, T4, R> Flow<R> combine(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Flow<? extends T3> paramFlow2, Flow<? extends T4> paramFlow3, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super Continuation<? super R>, ? extends Object> paramFunction5)
  {
    return FlowKt__ZipKt.combine(paramFlow, paramFlow1, paramFlow2, paramFlow3, paramFunction5);
  }
  
  public static final <T1, T2, T3, T4, T5, R> Flow<R> combine(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Flow<? extends T3> paramFlow2, Flow<? extends T4> paramFlow3, Flow<? extends T5> paramFlow4, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super Continuation<? super R>, ? extends Object> paramFunction6)
  {
    return FlowKt__ZipKt.combine(paramFlow, paramFlow1, paramFlow2, paramFlow3, paramFlow4, paramFunction6);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'combineLatest' is 'combine'", replaceWith=@ReplaceWith(expression="this.combine(other, transform)", imports={}))
  public static final <T1, T2, R> Flow<R> combineLatest(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    return FlowKt__MigrationKt.combineLatest(paramFlow, paramFlow1, paramFunction3);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'combineLatest' is 'combine'", replaceWith=@ReplaceWith(expression="combine(this, other, other2, transform)", imports={}))
  public static final <T1, T2, T3, R> Flow<R> combineLatest(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Flow<? extends T3> paramFlow2, Function4<? super T1, ? super T2, ? super T3, ? super Continuation<? super R>, ? extends Object> paramFunction4)
  {
    return FlowKt__MigrationKt.combineLatest(paramFlow, paramFlow1, paramFlow2, paramFunction4);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'combineLatest' is 'combine'", replaceWith=@ReplaceWith(expression="combine(this, other, other2, other3, transform)", imports={}))
  public static final <T1, T2, T3, T4, R> Flow<R> combineLatest(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Flow<? extends T3> paramFlow2, Flow<? extends T4> paramFlow3, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super Continuation<? super R>, ? extends Object> paramFunction5)
  {
    return FlowKt__MigrationKt.combineLatest(paramFlow, paramFlow1, paramFlow2, paramFlow3, paramFunction5);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'combineLatest' is 'combine'", replaceWith=@ReplaceWith(expression="combine(this, other, other2, other3, transform)", imports={}))
  public static final <T1, T2, T3, T4, T5, R> Flow<R> combineLatest(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Flow<? extends T3> paramFlow2, Flow<? extends T4> paramFlow3, Flow<? extends T5> paramFlow4, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super Continuation<? super R>, ? extends Object> paramFunction6)
  {
    return FlowKt__MigrationKt.combineLatest(paramFlow, paramFlow1, paramFlow2, paramFlow3, paramFlow4, paramFunction6);
  }
  
  public static final <T1, T2, R> Flow<R> combineTransform(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Function4<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super Continuation<? super Unit>, ? extends Object> paramFunction4)
  {
    return FlowKt__ZipKt.combineTransform(paramFlow, paramFlow1, paramFunction4);
  }
  
  public static final <T1, T2, T3, R> Flow<R> combineTransform(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Flow<? extends T3> paramFlow2, Function5<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super T3, ? super Continuation<? super Unit>, ? extends Object> paramFunction5)
  {
    return FlowKt__ZipKt.combineTransform(paramFlow, paramFlow1, paramFlow2, paramFunction5);
  }
  
  public static final <T1, T2, T3, T4, R> Flow<R> combineTransform(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Flow<? extends T3> paramFlow2, Flow<? extends T4> paramFlow3, Function6<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super T3, ? super T4, ? super Continuation<? super Unit>, ? extends Object> paramFunction6)
  {
    return FlowKt__ZipKt.combineTransform(paramFlow, paramFlow1, paramFlow2, paramFlow3, paramFunction6);
  }
  
  public static final <T1, T2, T3, T4, T5, R> Flow<R> combineTransform(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Flow<? extends T3> paramFlow2, Flow<? extends T4> paramFlow3, Flow<? extends T5> paramFlow4, Function7<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super Continuation<? super Unit>, ? extends Object> paramFunction7)
  {
    return FlowKt__ZipKt.combineTransform(paramFlow, paramFlow1, paramFlow2, paramFlow3, paramFlow4, paramFunction7);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'compose' is 'let'", replaceWith=@ReplaceWith(expression="let(transformer)", imports={}))
  public static final <T, R> Flow<R> compose(Flow<? extends T> paramFlow, Function1<? super Flow<? extends T>, ? extends Flow<? extends R>> paramFunction1)
  {
    return FlowKt__MigrationKt.compose(paramFlow, paramFunction1);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'concatMap' is 'flatMapConcat'", replaceWith=@ReplaceWith(expression="flatMapConcat(mapper)", imports={}))
  public static final <T, R> Flow<R> concatMap(Flow<? extends T> paramFlow, Function1<? super T, ? extends Flow<? extends R>> paramFunction1)
  {
    return FlowKt__MigrationKt.concatMap(paramFlow, paramFunction1);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'concatWith' is 'onCompletion'. Use 'onCompletion { emit(value) }'", replaceWith=@ReplaceWith(expression="onCompletion { emit(value) }", imports={}))
  public static final <T> Flow<T> concatWith(Flow<? extends T> paramFlow, T paramT)
  {
    return FlowKt__MigrationKt.concatWith(paramFlow, paramT);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'concatWith' is 'onCompletion'. Use 'onCompletion { emitAll(other) }'", replaceWith=@ReplaceWith(expression="onCompletion { emitAll(other) }", imports={}))
  public static final <T> Flow<T> concatWith(Flow<? extends T> paramFlow1, Flow<? extends T> paramFlow2)
  {
    return FlowKt__MigrationKt.concatWith(paramFlow1, paramFlow2);
  }
  
  public static final <T> Flow<T> conflate(Flow<? extends T> paramFlow)
  {
    return FlowKt__ContextKt.conflate(paramFlow);
  }
  
  public static final <T> Flow<T> consumeAsFlow(ReceiveChannel<? extends T> paramReceiveChannel)
  {
    return FlowKt__ChannelsKt.consumeAsFlow(paramReceiveChannel);
  }
  
  public static final <T> Object count(Flow<? extends T> paramFlow, Continuation<? super Integer> paramContinuation)
  {
    return FlowKt__CountKt.count(paramFlow, paramContinuation);
  }
  
  public static final <T> Object count(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2, Continuation<? super Integer> paramContinuation)
  {
    return FlowKt__CountKt.count(paramFlow, paramFunction2, paramContinuation);
  }
  
  public static final <T> Flow<T> debounce(Flow<? extends T> paramFlow, long paramLong)
  {
    return FlowKt__DelayKt.debounce(paramFlow, paramLong);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use 'onEach { delay(timeMillis) }'", replaceWith=@ReplaceWith(expression="onEach { delay(timeMillis) }", imports={}))
  public static final <T> Flow<T> delayEach(Flow<? extends T> paramFlow, long paramLong)
  {
    return FlowKt__MigrationKt.delayEach(paramFlow, paramLong);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use 'onStart { delay(timeMillis) }'", replaceWith=@ReplaceWith(expression="onStart { delay(timeMillis) }", imports={}))
  public static final <T> Flow<T> delayFlow(Flow<? extends T> paramFlow, long paramLong)
  {
    return FlowKt__MigrationKt.delayFlow(paramFlow, paramLong);
  }
  
  public static final <T> Flow<T> distinctUntilChanged(Flow<? extends T> paramFlow)
  {
    return FlowKt__DistinctKt.distinctUntilChanged(paramFlow);
  }
  
  public static final <T> Flow<T> distinctUntilChanged(Flow<? extends T> paramFlow, Function2<? super T, ? super T, Boolean> paramFunction2)
  {
    return FlowKt__DistinctKt.distinctUntilChanged(paramFlow, paramFunction2);
  }
  
  public static final <T, K> Flow<T> distinctUntilChangedBy(Flow<? extends T> paramFlow, Function1<? super T, ? extends K> paramFunction1)
  {
    return FlowKt__DistinctKt.distinctUntilChangedBy(paramFlow, paramFunction1);
  }
  
  public static final <T> Flow<T> drop(Flow<? extends T> paramFlow, int paramInt)
  {
    return FlowKt__LimitKt.drop(paramFlow, paramInt);
  }
  
  public static final <T> Flow<T> dropWhile(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2)
  {
    return FlowKt__LimitKt.dropWhile(paramFlow, paramFunction2);
  }
  
  public static final <T> Object emitAll(FlowCollector<? super T> paramFlowCollector, ReceiveChannel<? extends T> paramReceiveChannel, Continuation<? super Unit> paramContinuation)
  {
    return FlowKt__ChannelsKt.emitAll(paramFlowCollector, paramReceiveChannel, paramContinuation);
  }
  
  public static final <T> Object emitAll(FlowCollector<? super T> paramFlowCollector, Flow<? extends T> paramFlow, Continuation<? super Unit> paramContinuation)
  {
    return FlowKt__CollectKt.emitAll(paramFlowCollector, paramFlow, paramContinuation);
  }
  
  private static final Object emitAll$$forInline(FlowCollector paramFlowCollector, Flow paramFlow, Continuation paramContinuation)
  {
    return FlowKt__CollectKt.emitAll(paramFlowCollector, paramFlow, paramContinuation);
  }
  
  public static final <T> Flow<T> emptyFlow()
  {
    return FlowKt__BuildersKt.emptyFlow();
  }
  
  public static final <T> Flow<T> filter(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2)
  {
    return FlowKt__TransformKt.filter(paramFlow, paramFunction2);
  }
  
  public static final <T> Flow<T> filterNot(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2)
  {
    return FlowKt__TransformKt.filterNot(paramFlow, paramFunction2);
  }
  
  public static final <T> Flow<T> filterNotNull(Flow<? extends T> paramFlow)
  {
    return FlowKt__TransformKt.filterNotNull(paramFlow);
  }
  
  public static final <T> Object first(Flow<? extends T> paramFlow, Continuation<? super T> paramContinuation)
  {
    return FlowKt__ReduceKt.first(paramFlow, paramContinuation);
  }
  
  public static final <T> Object first(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2, Continuation<? super T> paramContinuation)
  {
    return FlowKt__ReduceKt.first(paramFlow, paramFunction2, paramContinuation);
  }
  
  public static final ReceiveChannel<Unit> fixedPeriodTicker(CoroutineScope paramCoroutineScope, long paramLong1, long paramLong2)
  {
    return FlowKt__DelayKt.fixedPeriodTicker(paramCoroutineScope, paramLong1, paramLong2);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue is named flatMapConcat", replaceWith=@ReplaceWith(expression="flatMapConcat(mapper)", imports={}))
  public static final <T, R> Flow<R> flatMap(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> paramFunction2)
  {
    return FlowKt__MigrationKt.flatMap(paramFlow, paramFunction2);
  }
  
  public static final <T, R> Flow<R> flatMapConcat(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> paramFunction2)
  {
    return FlowKt__MergeKt.flatMapConcat(paramFlow, paramFunction2);
  }
  
  public static final <T, R> Flow<R> flatMapLatest(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> paramFunction2)
  {
    return FlowKt__MergeKt.flatMapLatest(paramFlow, paramFunction2);
  }
  
  public static final <T, R> Flow<R> flatMapMerge(Flow<? extends T> paramFlow, int paramInt, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> paramFunction2)
  {
    return FlowKt__MergeKt.flatMapMerge(paramFlow, paramInt, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'flatten' is 'flattenConcat'", replaceWith=@ReplaceWith(expression="flattenConcat()", imports={}))
  public static final <T> Flow<T> flatten(Flow<? extends Flow<? extends T>> paramFlow)
  {
    return FlowKt__MigrationKt.flatten(paramFlow);
  }
  
  public static final <T> Flow<T> flattenConcat(Flow<? extends Flow<? extends T>> paramFlow)
  {
    return FlowKt__MergeKt.flattenConcat(paramFlow);
  }
  
  public static final <T> Flow<T> flattenMerge(Flow<? extends Flow<? extends T>> paramFlow, int paramInt)
  {
    return FlowKt__MergeKt.flattenMerge(paramFlow, paramInt);
  }
  
  public static final <T> Flow<T> flow(Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    return FlowKt__BuildersKt.flow(paramFunction2);
  }
  
  public static final <T1, T2, R> Flow<R> flowCombine(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    return FlowKt__ZipKt.flowCombine(paramFlow, paramFlow1, paramFunction3);
  }
  
  public static final <T1, T2, R> Flow<R> flowCombineTransform(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Function4<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super Continuation<? super Unit>, ? extends Object> paramFunction4)
  {
    return FlowKt__ZipKt.flowCombineTransform(paramFlow, paramFlow1, paramFunction4);
  }
  
  public static final <T> Flow<T> flowOf(T paramT)
  {
    return FlowKt__BuildersKt.flowOf(paramT);
  }
  
  public static final <T> Flow<T> flowOf(T... paramVarArgs)
  {
    return FlowKt__BuildersKt.flowOf(paramVarArgs);
  }
  
  public static final <T> Flow<T> flowOn(Flow<? extends T> paramFlow, CoroutineContext paramCoroutineContext)
  {
    return FlowKt__ContextKt.flowOn(paramFlow, paramCoroutineContext);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Use channelFlow with awaitClose { } instead of flowViaChannel and invokeOnClose { }.")
  public static final <T> Flow<T> flowViaChannel(int paramInt, Function2<? super CoroutineScope, ? super SendChannel<? super T>, Unit> paramFunction2)
  {
    return FlowKt__BuildersKt.flowViaChannel(paramInt, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="flowWith is deprecated without replacement, please refer to its KDoc for an explanation")
  public static final <T, R> Flow<R> flowWith(Flow<? extends T> paramFlow, CoroutineContext paramCoroutineContext, int paramInt, Function1<? super Flow<? extends T>, ? extends Flow<? extends R>> paramFunction1)
  {
    return FlowKt__ContextKt.flowWith(paramFlow, paramCoroutineContext, paramInt, paramFunction1);
  }
  
  public static final <T, R> Object fold(Flow<? extends T> paramFlow, R paramR, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> paramFunction3, Continuation<? super R> paramContinuation)
  {
    return FlowKt__ReduceKt.fold(paramFlow, paramR, paramFunction3, paramContinuation);
  }
  
  private static final Object fold$$forInline(Flow paramFlow, Object paramObject, Function3 paramFunction3, Continuation paramContinuation)
  {
    return FlowKt__ReduceKt.fold(paramFlow, paramObject, paramFunction3, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'forEach' is 'collect'", replaceWith=@ReplaceWith(expression="collect(block)", imports={}))
  public static final <T> void forEach(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    FlowKt__MigrationKt.forEach(paramFlow, paramFunction2);
  }
  
  public static final int getDEFAULT_CONCURRENCY()
  {
    return FlowKt__MergeKt.getDEFAULT_CONCURRENCY();
  }
  
  public static final <T> Job launchIn(Flow<? extends T> paramFlow, CoroutineScope paramCoroutineScope)
  {
    return FlowKt__CollectKt.launchIn(paramFlow, paramCoroutineScope);
  }
  
  public static final <T, R> Flow<R> map(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    return FlowKt__TransformKt.map(paramFlow, paramFunction2);
  }
  
  public static final <T, R> Flow<R> mapLatest(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    return FlowKt__MergeKt.mapLatest(paramFlow, paramFunction2);
  }
  
  public static final <T, R> Flow<R> mapNotNull(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    return FlowKt__TransformKt.mapNotNull(paramFlow, paramFunction2);
  }
  
  public static final <T> Flow<T> merge(Iterable<? extends Flow<? extends T>> paramIterable)
  {
    return FlowKt__MergeKt.merge(paramIterable);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'merge' is 'flattenConcat'", replaceWith=@ReplaceWith(expression="flattenConcat()", imports={}))
  public static final <T> Flow<T> merge(Flow<? extends Flow<? extends T>> paramFlow)
  {
    return FlowKt__MigrationKt.merge(paramFlow);
  }
  
  public static final <T> Flow<T> merge(Flow<? extends T>... paramVarArgs)
  {
    return FlowKt__MergeKt.merge(paramVarArgs);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Collect flow in the desired context instead")
  public static final <T> Flow<T> observeOn(Flow<? extends T> paramFlow, CoroutineContext paramCoroutineContext)
  {
    return FlowKt__MigrationKt.observeOn(paramFlow, paramCoroutineContext);
  }
  
  public static final <T> Flow<T> onCompletion(Flow<? extends T> paramFlow, Function3<? super FlowCollector<? super T>, ? super Throwable, ? super Continuation<? super Unit>, ? extends Object> paramFunction3)
  {
    return FlowKt__EmittersKt.onCompletion(paramFlow, paramFunction3);
  }
  
  public static final <T> Flow<T> onEach(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    return FlowKt__TransformKt.onEach(paramFlow, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Use catch { e -> if (predicate(e)) emitAll(fallback) else throw e }", replaceWith=@ReplaceWith(expression="catch { e -> if (predicate(e)) emitAll(fallback) else throw e }", imports={}))
  public static final <T> Flow<T> onErrorCollect(Flow<? extends T> paramFlow1, Flow<? extends T> paramFlow2, Function1<? super Throwable, Boolean> paramFunction1)
  {
    return FlowKt__ErrorsKt.onErrorCollect(paramFlow1, paramFlow2, paramFunction1);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emitAll(fallback) }'", replaceWith=@ReplaceWith(expression="catch { emitAll(fallback) }", imports={}))
  public static final <T> Flow<T> onErrorResume(Flow<? extends T> paramFlow1, Flow<? extends T> paramFlow2)
  {
    return FlowKt__MigrationKt.onErrorResume(paramFlow1, paramFlow2);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emitAll(fallback) }'", replaceWith=@ReplaceWith(expression="catch { emitAll(fallback) }", imports={}))
  public static final <T> Flow<T> onErrorResumeNext(Flow<? extends T> paramFlow1, Flow<? extends T> paramFlow2)
  {
    return FlowKt__MigrationKt.onErrorResumeNext(paramFlow1, paramFlow2);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emit(fallback) }'", replaceWith=@ReplaceWith(expression="catch { emit(fallback) }", imports={}))
  public static final <T> Flow<T> onErrorReturn(Flow<? extends T> paramFlow, T paramT)
  {
    return FlowKt__MigrationKt.onErrorReturn(paramFlow, paramT);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { e -> if (predicate(e)) emit(fallback) else throw e }'", replaceWith=@ReplaceWith(expression="catch { e -> if (predicate(e)) emit(fallback) else throw e }", imports={}))
  public static final <T> Flow<T> onErrorReturn(Flow<? extends T> paramFlow, T paramT, Function1<? super Throwable, Boolean> paramFunction1)
  {
    return FlowKt__MigrationKt.onErrorReturn(paramFlow, paramT, paramFunction1);
  }
  
  public static final <T> Flow<T> onStart(Flow<? extends T> paramFlow, Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    return FlowKt__EmittersKt.onStart(paramFlow, paramFunction2);
  }
  
  public static final <T> ReceiveChannel<T> produceIn(Flow<? extends T> paramFlow, CoroutineScope paramCoroutineScope)
  {
    return FlowKt__ChannelsKt.produceIn(paramFlow, paramCoroutineScope);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Collect flow in the desired context instead")
  public static final <T> Flow<T> publishOn(Flow<? extends T> paramFlow, CoroutineContext paramCoroutineContext)
  {
    return FlowKt__MigrationKt.publishOn(paramFlow, paramCoroutineContext);
  }
  
  public static final <S, T extends S> Object reduce(Flow<? extends T> paramFlow, Function3<? super S, ? super T, ? super Continuation<? super S>, ? extends Object> paramFunction3, Continuation<? super S> paramContinuation)
  {
    return FlowKt__ReduceKt.reduce(paramFlow, paramFunction3, paramContinuation);
  }
  
  public static final <T> Flow<T> retry(Flow<? extends T> paramFlow, long paramLong, Function2<? super Throwable, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2)
  {
    return FlowKt__ErrorsKt.retry(paramFlow, paramLong, paramFunction2);
  }
  
  public static final <T> Flow<T> retryWhen(Flow<? extends T> paramFlow, Function4<? super FlowCollector<? super T>, ? super Throwable, ? super Long, ? super Continuation<? super Boolean>, ? extends Object> paramFunction4)
  {
    return FlowKt__ErrorsKt.retryWhen(paramFlow, paramFunction4);
  }
  
  public static final <T> Flow<T> sample(Flow<? extends T> paramFlow, long paramLong)
  {
    return FlowKt__DelayKt.sample(paramFlow, paramLong);
  }
  
  public static final <T, R> Flow<R> scan(Flow<? extends T> paramFlow, R paramR, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    return FlowKt__TransformKt.scan(paramFlow, paramR, paramFunction3);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow has less verbose 'scan' shortcut", replaceWith=@ReplaceWith(expression="scan(initial, operation)", imports={}))
  public static final <T, R> Flow<R> scanFold(Flow<? extends T> paramFlow, R paramR, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    return FlowKt__MigrationKt.scanFold(paramFlow, paramR, paramFunction3);
  }
  
  public static final <T> Flow<T> scanReduce(Flow<? extends T> paramFlow, Function3<? super T, ? super T, ? super Continuation<? super T>, ? extends Object> paramFunction3)
  {
    return FlowKt__TransformKt.scanReduce(paramFlow, paramFunction3);
  }
  
  public static final <T> Object single(Flow<? extends T> paramFlow, Continuation<? super T> paramContinuation)
  {
    return FlowKt__ReduceKt.single(paramFlow, paramContinuation);
  }
  
  public static final <T> Object singleOrNull(Flow<? extends T> paramFlow, Continuation<? super T> paramContinuation)
  {
    return FlowKt__ReduceKt.singleOrNull(paramFlow, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'skip' is 'drop'", replaceWith=@ReplaceWith(expression="drop(count)", imports={}))
  public static final <T> Flow<T> skip(Flow<? extends T> paramFlow, int paramInt)
  {
    return FlowKt__MigrationKt.skip(paramFlow, paramInt);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'startWith' is 'onStart'. Use 'onStart { emit(value) }'", replaceWith=@ReplaceWith(expression="onStart { emit(value) }", imports={}))
  public static final <T> Flow<T> startWith(Flow<? extends T> paramFlow, T paramT)
  {
    return FlowKt__MigrationKt.startWith(paramFlow, paramT);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogue of 'startWith' is 'onStart'. Use 'onStart { emitAll(other) }'", replaceWith=@ReplaceWith(expression="onStart { emitAll(other) }", imports={}))
  public static final <T> Flow<T> startWith(Flow<? extends T> paramFlow1, Flow<? extends T> paramFlow2)
  {
    return FlowKt__MigrationKt.startWith(paramFlow1, paramFlow2);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Use launchIn with onEach, onCompletion and catch operators instead")
  public static final <T> void subscribe(Flow<? extends T> paramFlow)
  {
    FlowKt__MigrationKt.subscribe(paramFlow);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Use launchIn with onEach, onCompletion and catch operators instead")
  public static final <T> void subscribe(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction2)
  {
    FlowKt__MigrationKt.subscribe(paramFlow, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Use launchIn with onEach, onCompletion and catch operators instead")
  public static final <T> void subscribe(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction2, Function2<? super Throwable, ? super Continuation<? super Unit>, ? extends Object> paramFunction21)
  {
    FlowKt__MigrationKt.subscribe(paramFlow, paramFunction2, paramFunction21);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Use flowOn instead")
  public static final <T> Flow<T> subscribeOn(Flow<? extends T> paramFlow, CoroutineContext paramCoroutineContext)
  {
    return FlowKt__MigrationKt.subscribeOn(paramFlow, paramCoroutineContext);
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="Flow analogues of 'switchMap' are 'transformLatest', 'flatMapLatest' and 'mapLatest'", replaceWith=@ReplaceWith(expression="this.flatMapLatest(transform)", imports={}))
  public static final <T, R> Flow<R> switchMap(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> paramFunction2)
  {
    return FlowKt__MigrationKt.switchMap(paramFlow, paramFunction2);
  }
  
  public static final <T> Flow<T> take(Flow<? extends T> paramFlow, int paramInt)
  {
    return FlowKt__LimitKt.take(paramFlow, paramInt);
  }
  
  public static final <T> Flow<T> takeWhile(Flow<? extends T> paramFlow, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2)
  {
    return FlowKt__LimitKt.takeWhile(paramFlow, paramFunction2);
  }
  
  public static final <T, C extends Collection<? super T>> Object toCollection(Flow<? extends T> paramFlow, C paramC, Continuation<? super C> paramContinuation)
  {
    return FlowKt__CollectionKt.toCollection(paramFlow, paramC, paramContinuation);
  }
  
  public static final <T> Object toList(Flow<? extends T> paramFlow, List<T> paramList, Continuation<? super List<? extends T>> paramContinuation)
  {
    return FlowKt__CollectionKt.toList(paramFlow, paramList, paramContinuation);
  }
  
  public static final <T> Object toSet(Flow<? extends T> paramFlow, Set<T> paramSet, Continuation<? super Set<? extends T>> paramContinuation)
  {
    return FlowKt__CollectionKt.toSet(paramFlow, paramSet, paramContinuation);
  }
  
  public static final <T, R> Flow<R> transform(Flow<? extends T> paramFlow, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction3)
  {
    return FlowKt__EmittersKt.transform(paramFlow, paramFunction3);
  }
  
  public static final <T, R> Flow<R> transformLatest(Flow<? extends T> paramFlow, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction3)
  {
    return FlowKt__MergeKt.transformLatest(paramFlow, paramFunction3);
  }
  
  public static final <T, R> Flow<R> unsafeTransform(Flow<? extends T> paramFlow, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> paramFunction3)
  {
    return FlowKt__EmittersKt.unsafeTransform(paramFlow, paramFunction3);
  }
  
  public static final <T> Flow<IndexedValue<T>> withIndex(Flow<? extends T> paramFlow)
  {
    return FlowKt__TransformKt.withIndex(paramFlow);
  }
  
  public static final <T1, T2, R> Flow<R> zip(Flow<? extends T1> paramFlow, Flow<? extends T2> paramFlow1, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    return FlowKt__ZipKt.zip(paramFlow, paramFlow1, paramFunction3);
  }
}
