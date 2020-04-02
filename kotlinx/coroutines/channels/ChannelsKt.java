package kotlinx.coroutines.channels;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.selects.SelectClause1;

@Metadata(bv={1, 0, 3}, d1={"kotlinx/coroutines/channels/ChannelsKt__ChannelsKt", "kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt"}, k=4, mv={1, 1, 16})
public final class ChannelsKt
{
  public static final String DEFAULT_CLOSE_MESSAGE = "Channel was closed";
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object all(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super Boolean> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.all(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object all$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.all(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object any(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super Boolean> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.any(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object any(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super Boolean> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.any(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object any$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.any(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, K, V> Object associate(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, ? extends Pair<? extends K, ? extends V>> paramFunction1, Continuation<? super Map<K, ? extends V>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associate(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object associate$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associate(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, K> Object associateBy(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, ? extends K> paramFunction1, Continuation<? super Map<K, ? extends E>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associateBy(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, K, V> Object associateBy(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, ? extends K> paramFunction1, Function1<? super E, ? extends V> paramFunction11, Continuation<? super Map<K, ? extends V>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associateBy(paramReceiveChannel, paramFunction1, paramFunction11, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object associateBy$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associateBy(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object associateBy$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction11, Function1 paramFunction12, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associateBy(paramReceiveChannel, paramFunction11, paramFunction12, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, K, M extends Map<? super K, ? super E>> Object associateByTo(ReceiveChannel<? extends E> paramReceiveChannel, M paramM, Function1<? super E, ? extends K> paramFunction1, Continuation<? super M> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associateByTo(paramReceiveChannel, paramM, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, K, V, M extends Map<? super K, ? super V>> Object associateByTo(ReceiveChannel<? extends E> paramReceiveChannel, M paramM, Function1<? super E, ? extends K> paramFunction1, Function1<? super E, ? extends V> paramFunction11, Continuation<? super M> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associateByTo(paramReceiveChannel, paramM, paramFunction1, paramFunction11, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object associateByTo$$forInline(ReceiveChannel paramReceiveChannel, Map paramMap, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associateByTo(paramReceiveChannel, paramMap, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object associateByTo$$forInline(ReceiveChannel paramReceiveChannel, Map paramMap, Function1 paramFunction11, Function1 paramFunction12, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associateByTo(paramReceiveChannel, paramMap, paramFunction11, paramFunction12, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, K, V, M extends Map<? super K, ? super V>> Object associateTo(ReceiveChannel<? extends E> paramReceiveChannel, M paramM, Function1<? super E, ? extends Pair<? extends K, ? extends V>> paramFunction1, Continuation<? super M> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associateTo(paramReceiveChannel, paramM, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object associateTo$$forInline(ReceiveChannel paramReceiveChannel, Map paramMap, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.associateTo(paramReceiveChannel, paramMap, paramFunction1, paramContinuation);
  }
  
  public static final void cancelConsumed(ReceiveChannel<?> paramReceiveChannel, Throwable paramThrowable)
  {
    ChannelsKt__Channels_commonKt.cancelConsumed(paramReceiveChannel, paramThrowable);
  }
  
  public static final <E, R> R consume(BroadcastChannel<E> paramBroadcastChannel, Function1<? super ReceiveChannel<? extends E>, ? extends R> paramFunction1)
  {
    return ChannelsKt__Channels_commonKt.consume(paramBroadcastChannel, paramFunction1);
  }
  
  public static final <E, R> R consume(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super ReceiveChannel<? extends E>, ? extends R> paramFunction1)
  {
    return ChannelsKt__Channels_commonKt.consume(paramReceiveChannel, paramFunction1);
  }
  
  public static final <E> Object consumeEach(BroadcastChannel<E> paramBroadcastChannel, Function1<? super E, Unit> paramFunction1, Continuation<? super Unit> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.consumeEach(paramBroadcastChannel, paramFunction1, paramContinuation);
  }
  
  public static final <E> Object consumeEach(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Unit> paramFunction1, Continuation<? super Unit> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.consumeEach(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  private static final Object consumeEach$$forInline(BroadcastChannel paramBroadcastChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.consumeEach(paramBroadcastChannel, paramFunction1, paramContinuation);
  }
  
  private static final Object consumeEach$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.consumeEach(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object consumeEachIndexed(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super IndexedValue<? extends E>, Unit> paramFunction1, Continuation<? super Unit> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.consumeEachIndexed(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object consumeEachIndexed$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.consumeEachIndexed(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final Function1<Throwable, Unit> consumes(ReceiveChannel<?> paramReceiveChannel)
  {
    return ChannelsKt__Channels_commonKt.consumes(paramReceiveChannel);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final Function1<Throwable, Unit> consumesAll(ReceiveChannel<?>... paramVarArgs)
  {
    return ChannelsKt__Channels_commonKt.consumesAll(paramVarArgs);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object count(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super Integer> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.count(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object count(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super Integer> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.count(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object count$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.count(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> ReceiveChannel<E> distinct(ReceiveChannel<? extends E> paramReceiveChannel)
  {
    return ChannelsKt__Channels_commonKt.distinct(paramReceiveChannel);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, K> ReceiveChannel<E> distinctBy(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function2<? super E, ? super Continuation<? super K>, ? extends Object> paramFunction2)
  {
    return ChannelsKt__Channels_commonKt.distinctBy(paramReceiveChannel, paramCoroutineContext, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> ReceiveChannel<E> drop(ReceiveChannel<? extends E> paramReceiveChannel, int paramInt, CoroutineContext paramCoroutineContext)
  {
    return ChannelsKt__Channels_commonKt.drop(paramReceiveChannel, paramInt, paramCoroutineContext);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> ReceiveChannel<E> dropWhile(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2)
  {
    return ChannelsKt__Channels_commonKt.dropWhile(paramReceiveChannel, paramCoroutineContext, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object elementAt(ReceiveChannel<? extends E> paramReceiveChannel, int paramInt, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.elementAt(paramReceiveChannel, paramInt, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object elementAtOrElse(ReceiveChannel<? extends E> paramReceiveChannel, int paramInt, Function1<? super Integer, ? extends E> paramFunction1, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.elementAtOrElse(paramReceiveChannel, paramInt, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object elementAtOrElse$$forInline(ReceiveChannel paramReceiveChannel, int paramInt, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.elementAtOrElse(paramReceiveChannel, paramInt, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object elementAtOrNull(ReceiveChannel<? extends E> paramReceiveChannel, int paramInt, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.elementAtOrNull(paramReceiveChannel, paramInt, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> ReceiveChannel<E> filter(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2)
  {
    return ChannelsKt__Channels_commonKt.filter(paramReceiveChannel, paramCoroutineContext, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> ReceiveChannel<E> filterIndexed(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function3<? super Integer, ? super E, ? super Continuation<? super Boolean>, ? extends Object> paramFunction3)
  {
    return ChannelsKt__Channels_commonKt.filterIndexed(paramReceiveChannel, paramCoroutineContext, paramFunction3);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, C extends Collection<? super E>> Object filterIndexedTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function2<? super Integer, ? super E, Boolean> paramFunction2, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterIndexedTo(paramReceiveChannel, paramC, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, C extends SendChannel<? super E>> Object filterIndexedTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function2<? super Integer, ? super E, Boolean> paramFunction2, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterIndexedTo(paramReceiveChannel, paramC, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object filterIndexedTo$$forInline(ReceiveChannel paramReceiveChannel, Collection paramCollection, Function2 paramFunction2, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterIndexedTo(paramReceiveChannel, paramCollection, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object filterIndexedTo$$forInline(ReceiveChannel paramReceiveChannel, SendChannel paramSendChannel, Function2 paramFunction2, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterIndexedTo(paramReceiveChannel, paramSendChannel, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> ReceiveChannel<E> filterNot(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2)
  {
    return ChannelsKt__Channels_commonKt.filterNot(paramReceiveChannel, paramCoroutineContext, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> ReceiveChannel<E> filterNotNull(ReceiveChannel<? extends E> paramReceiveChannel)
  {
    return ChannelsKt__Channels_commonKt.filterNotNull(paramReceiveChannel);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, C extends Collection<? super E>> Object filterNotNullTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterNotNullTo(paramReceiveChannel, paramC, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, C extends SendChannel<? super E>> Object filterNotNullTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterNotNullTo(paramReceiveChannel, paramC, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, C extends Collection<? super E>> Object filterNotTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function1<? super E, Boolean> paramFunction1, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterNotTo(paramReceiveChannel, paramC, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, C extends SendChannel<? super E>> Object filterNotTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function1<? super E, Boolean> paramFunction1, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterNotTo(paramReceiveChannel, paramC, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object filterNotTo$$forInline(ReceiveChannel paramReceiveChannel, Collection paramCollection, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterNotTo(paramReceiveChannel, paramCollection, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object filterNotTo$$forInline(ReceiveChannel paramReceiveChannel, SendChannel paramSendChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterNotTo(paramReceiveChannel, paramSendChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, C extends Collection<? super E>> Object filterTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function1<? super E, Boolean> paramFunction1, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterTo(paramReceiveChannel, paramC, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, C extends SendChannel<? super E>> Object filterTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function1<? super E, Boolean> paramFunction1, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterTo(paramReceiveChannel, paramC, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object filterTo$$forInline(ReceiveChannel paramReceiveChannel, Collection paramCollection, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterTo(paramReceiveChannel, paramCollection, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object filterTo$$forInline(ReceiveChannel paramReceiveChannel, SendChannel paramSendChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.filterTo(paramReceiveChannel, paramSendChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object find(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.find(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object find$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.find(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object findLast(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.findLast(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object findLast$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.findLast(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object first(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.first(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object first(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.first(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object first$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.first(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object firstOrNull(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.firstOrNull(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object firstOrNull(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.firstOrNull(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object firstOrNull$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.firstOrNull(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R> ReceiveChannel<R> flatMap(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function2<? super E, ? super Continuation<? super ReceiveChannel<? extends R>>, ? extends Object> paramFunction2)
  {
    return ChannelsKt__Channels_commonKt.flatMap(paramReceiveChannel, paramCoroutineContext, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R> Object fold(ReceiveChannel<? extends E> paramReceiveChannel, R paramR, Function2<? super R, ? super E, ? extends R> paramFunction2, Continuation<? super R> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.fold(paramReceiveChannel, paramR, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object fold$$forInline(ReceiveChannel paramReceiveChannel, Object paramObject, Function2 paramFunction2, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.fold(paramReceiveChannel, paramObject, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R> Object foldIndexed(ReceiveChannel<? extends E> paramReceiveChannel, R paramR, Function3<? super Integer, ? super R, ? super E, ? extends R> paramFunction3, Continuation<? super R> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.foldIndexed(paramReceiveChannel, paramR, paramFunction3, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object foldIndexed$$forInline(ReceiveChannel paramReceiveChannel, Object paramObject, Function3 paramFunction3, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.foldIndexed(paramReceiveChannel, paramObject, paramFunction3, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, K> Object groupBy(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, ? extends K> paramFunction1, Continuation<? super Map<K, ? extends List<? extends E>>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.groupBy(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, K, V> Object groupBy(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, ? extends K> paramFunction1, Function1<? super E, ? extends V> paramFunction11, Continuation<? super Map<K, ? extends List<? extends V>>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.groupBy(paramReceiveChannel, paramFunction1, paramFunction11, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object groupBy$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.groupBy(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object groupBy$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction11, Function1 paramFunction12, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.groupBy(paramReceiveChannel, paramFunction11, paramFunction12, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, K, M extends Map<? super K, List<E>>> Object groupByTo(ReceiveChannel<? extends E> paramReceiveChannel, M paramM, Function1<? super E, ? extends K> paramFunction1, Continuation<? super M> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.groupByTo(paramReceiveChannel, paramM, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, K, V, M extends Map<? super K, List<V>>> Object groupByTo(ReceiveChannel<? extends E> paramReceiveChannel, M paramM, Function1<? super E, ? extends K> paramFunction1, Function1<? super E, ? extends V> paramFunction11, Continuation<? super M> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.groupByTo(paramReceiveChannel, paramM, paramFunction1, paramFunction11, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object groupByTo$$forInline(ReceiveChannel paramReceiveChannel, Map paramMap, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.groupByTo(paramReceiveChannel, paramMap, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object groupByTo$$forInline(ReceiveChannel paramReceiveChannel, Map paramMap, Function1 paramFunction11, Function1 paramFunction12, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.groupByTo(paramReceiveChannel, paramMap, paramFunction11, paramFunction12, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object indexOf(ReceiveChannel<? extends E> paramReceiveChannel, E paramE, Continuation<? super Integer> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.indexOf(paramReceiveChannel, paramE, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object indexOfFirst(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super Integer> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.indexOfFirst(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object indexOfFirst$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.indexOfFirst(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object indexOfLast(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super Integer> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.indexOfLast(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object indexOfLast$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.indexOfLast(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object last(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.last(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object last(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.last(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object last$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.last(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object lastIndexOf(ReceiveChannel<? extends E> paramReceiveChannel, E paramE, Continuation<? super Integer> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.lastIndexOf(paramReceiveChannel, paramE, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object lastOrNull(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.lastOrNull(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object lastOrNull(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.lastOrNull(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object lastOrNull$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.lastOrNull(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R> ReceiveChannel<R> map(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function2<? super E, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    return ChannelsKt__Channels_commonKt.map(paramReceiveChannel, paramCoroutineContext, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R> ReceiveChannel<R> mapIndexed(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    return ChannelsKt__Channels_commonKt.mapIndexed(paramReceiveChannel, paramCoroutineContext, paramFunction3);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R> ReceiveChannel<R> mapIndexedNotNull(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> paramFunction3)
  {
    return ChannelsKt__Channels_commonKt.mapIndexedNotNull(paramReceiveChannel, paramCoroutineContext, paramFunction3);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R, C extends Collection<? super R>> Object mapIndexedNotNullTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function2<? super Integer, ? super E, ? extends R> paramFunction2, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapIndexedNotNullTo(paramReceiveChannel, paramC, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R, C extends SendChannel<? super R>> Object mapIndexedNotNullTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function2<? super Integer, ? super E, ? extends R> paramFunction2, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapIndexedNotNullTo(paramReceiveChannel, paramC, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object mapIndexedNotNullTo$$forInline(ReceiveChannel paramReceiveChannel, Collection paramCollection, Function2 paramFunction2, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapIndexedNotNullTo(paramReceiveChannel, paramCollection, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object mapIndexedNotNullTo$$forInline(ReceiveChannel paramReceiveChannel, SendChannel paramSendChannel, Function2 paramFunction2, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapIndexedNotNullTo(paramReceiveChannel, paramSendChannel, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R, C extends Collection<? super R>> Object mapIndexedTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function2<? super Integer, ? super E, ? extends R> paramFunction2, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapIndexedTo(paramReceiveChannel, paramC, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R, C extends SendChannel<? super R>> Object mapIndexedTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function2<? super Integer, ? super E, ? extends R> paramFunction2, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapIndexedTo(paramReceiveChannel, paramC, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object mapIndexedTo$$forInline(ReceiveChannel paramReceiveChannel, Collection paramCollection, Function2 paramFunction2, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapIndexedTo(paramReceiveChannel, paramCollection, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object mapIndexedTo$$forInline(ReceiveChannel paramReceiveChannel, SendChannel paramSendChannel, Function2 paramFunction2, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapIndexedTo(paramReceiveChannel, paramSendChannel, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R> ReceiveChannel<R> mapNotNull(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function2<? super E, ? super Continuation<? super R>, ? extends Object> paramFunction2)
  {
    return ChannelsKt__Channels_commonKt.mapNotNull(paramReceiveChannel, paramCoroutineContext, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R, C extends Collection<? super R>> Object mapNotNullTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function1<? super E, ? extends R> paramFunction1, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapNotNullTo(paramReceiveChannel, paramC, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R, C extends SendChannel<? super R>> Object mapNotNullTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function1<? super E, ? extends R> paramFunction1, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapNotNullTo(paramReceiveChannel, paramC, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object mapNotNullTo$$forInline(ReceiveChannel paramReceiveChannel, Collection paramCollection, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapNotNullTo(paramReceiveChannel, paramCollection, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object mapNotNullTo$$forInline(ReceiveChannel paramReceiveChannel, SendChannel paramSendChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapNotNullTo(paramReceiveChannel, paramSendChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R, C extends Collection<? super R>> Object mapTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function1<? super E, ? extends R> paramFunction1, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapTo(paramReceiveChannel, paramC, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R, C extends SendChannel<? super R>> Object mapTo(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Function1<? super E, ? extends R> paramFunction1, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapTo(paramReceiveChannel, paramC, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object mapTo$$forInline(ReceiveChannel paramReceiveChannel, Collection paramCollection, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapTo(paramReceiveChannel, paramCollection, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object mapTo$$forInline(ReceiveChannel paramReceiveChannel, SendChannel paramSendChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.mapTo(paramReceiveChannel, paramSendChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R extends Comparable<? super R>> Object maxBy(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, ? extends R> paramFunction1, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.maxBy(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object maxBy$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.maxBy(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object maxWith(ReceiveChannel<? extends E> paramReceiveChannel, Comparator<? super E> paramComparator, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.maxWith(paramReceiveChannel, paramComparator, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R extends Comparable<? super R>> Object minBy(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, ? extends R> paramFunction1, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.minBy(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object minBy$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.minBy(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object minWith(ReceiveChannel<? extends E> paramReceiveChannel, Comparator<? super E> paramComparator, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.minWith(paramReceiveChannel, paramComparator, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object none(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super Boolean> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.none(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object none(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super Boolean> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.none(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object none$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.none(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  public static final <E> SelectClause1<E> onReceiveOrNull(ReceiveChannel<? extends E> paramReceiveChannel)
  {
    return ChannelsKt__Channels_commonKt.onReceiveOrNull(paramReceiveChannel);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object partition(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super Pair<? extends List<? extends E>, ? extends List<? extends E>>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.partition(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object partition$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.partition(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  public static final <E> Object receiveOrNull(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.receiveOrNull(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <S, E extends S> Object reduce(ReceiveChannel<? extends E> paramReceiveChannel, Function2<? super S, ? super E, ? extends S> paramFunction2, Continuation<? super S> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.reduce(paramReceiveChannel, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object reduce$$forInline(ReceiveChannel paramReceiveChannel, Function2 paramFunction2, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.reduce(paramReceiveChannel, paramFunction2, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <S, E extends S> Object reduceIndexed(ReceiveChannel<? extends E> paramReceiveChannel, Function3<? super Integer, ? super S, ? super E, ? extends S> paramFunction3, Continuation<? super S> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.reduceIndexed(paramReceiveChannel, paramFunction3, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object reduceIndexed$$forInline(ReceiveChannel paramReceiveChannel, Function3 paramFunction3, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.reduceIndexed(paramReceiveChannel, paramFunction3, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> ReceiveChannel<E> requireNoNulls(ReceiveChannel<? extends E> paramReceiveChannel)
  {
    return ChannelsKt__Channels_commonKt.requireNoNulls(paramReceiveChannel);
  }
  
  public static final <E> void sendBlocking(SendChannel<? super E> paramSendChannel, E paramE)
  {
    ChannelsKt__ChannelsKt.sendBlocking(paramSendChannel, paramE);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object single(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.single(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object single(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.single(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object single$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.single(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object singleOrNull(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.singleOrNull(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object singleOrNull(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Boolean> paramFunction1, Continuation<? super E> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.singleOrNull(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object singleOrNull$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.singleOrNull(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object sumBy(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Integer> paramFunction1, Continuation<? super Integer> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.sumBy(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object sumBy$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.sumBy(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object sumByDouble(ReceiveChannel<? extends E> paramReceiveChannel, Function1<? super E, Double> paramFunction1, Continuation<? super Double> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.sumByDouble(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  private static final Object sumByDouble$$forInline(ReceiveChannel paramReceiveChannel, Function1 paramFunction1, Continuation paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.sumByDouble(paramReceiveChannel, paramFunction1, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> ReceiveChannel<E> take(ReceiveChannel<? extends E> paramReceiveChannel, int paramInt, CoroutineContext paramCoroutineContext)
  {
    return ChannelsKt__Channels_commonKt.take(paramReceiveChannel, paramInt, paramCoroutineContext);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> ReceiveChannel<E> takeWhile(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> paramFunction2)
  {
    return ChannelsKt__Channels_commonKt.takeWhile(paramReceiveChannel, paramCoroutineContext, paramFunction2);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, C extends SendChannel<? super E>> Object toChannel(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.toChannel(paramReceiveChannel, paramC, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, C extends Collection<? super E>> Object toCollection(ReceiveChannel<? extends E> paramReceiveChannel, C paramC, Continuation<? super C> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.toCollection(paramReceiveChannel, paramC, paramContinuation);
  }
  
  public static final <E> Object toList(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super List<? extends E>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.toList(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <K, V, M extends Map<? super K, ? super V>> Object toMap(ReceiveChannel<? extends Pair<? extends K, ? extends V>> paramReceiveChannel, M paramM, Continuation<? super M> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.toMap(paramReceiveChannel, paramM, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <K, V> Object toMap(ReceiveChannel<? extends Pair<? extends K, ? extends V>> paramReceiveChannel, Continuation<? super Map<K, ? extends V>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.toMap(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object toMutableList(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super List<E>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.toMutableList(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object toMutableSet(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super Set<E>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.toMutableSet(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> Object toSet(ReceiveChannel<? extends E> paramReceiveChannel, Continuation<? super Set<? extends E>> paramContinuation)
  {
    return ChannelsKt__Channels_commonKt.toSet(paramReceiveChannel, paramContinuation);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E> ReceiveChannel<IndexedValue<E>> withIndex(ReceiveChannel<? extends E> paramReceiveChannel, CoroutineContext paramCoroutineContext)
  {
    return ChannelsKt__Channels_commonKt.withIndex(paramReceiveChannel, paramCoroutineContext);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R> ReceiveChannel<Pair<E, R>> zip(ReceiveChannel<? extends E> paramReceiveChannel, ReceiveChannel<? extends R> paramReceiveChannel1)
  {
    return ChannelsKt__Channels_commonKt.zip(paramReceiveChannel, paramReceiveChannel1);
  }
  
  @Deprecated(level=DeprecationLevel.WARNING, message="Channel operators are deprecated in favour of Flow and will be removed in 1.4")
  public static final <E, R, V> ReceiveChannel<V> zip(ReceiveChannel<? extends E> paramReceiveChannel, ReceiveChannel<? extends R> paramReceiveChannel1, CoroutineContext paramCoroutineContext, Function2<? super E, ? super R, ? extends V> paramFunction2)
  {
    return ChannelsKt__Channels_commonKt.zip(paramReceiveChannel, paramReceiveChannel1, paramCoroutineContext, paramFunction2);
  }
}
