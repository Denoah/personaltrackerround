package kotlinx.coroutines.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.DefaultImpls;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.Delay.DefaultImpls;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;

@Deprecated(level=DeprecationLevel.WARNING, message="This API has been deprecated to integrate with Structured Concurrency.", replaceWith=@ReplaceWith(expression="TestCoroutineScope", imports={"kotlin.coroutines.test"}))
@Metadata(bv={1, 0, 3}, d1={"\000~\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\020\t\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020 \n\002\020\003\n\002\b\003\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020!\n\002\b\003\n\002\030\002\n\000\n\002\020\002\n\002\b\004\n\002\030\002\n\002\020\013\n\002\b\006\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\t\b\007\030\0002\0020\001:\001<B\021\022\n\b\002\020\002\032\004\030\0010\003?\006\002\020\004J\030\020\026\032\0020\0062\006\020\027\032\0020\0062\b\b\002\020\030\032\0020\031J\030\020\032\032\0020\0332\006\020\034\032\0020\0062\b\b\002\020\030\032\0020\031J$\020\035\032\0020\0332\b\b\002\020\036\032\0020\0032\022\020\037\032\016\022\004\022\0020\r\022\004\022\0020!0 J$\020\"\032\0020\0332\b\b\002\020\036\032\0020\0032\022\020\037\032\016\022\004\022\0020\r\022\004\022\0020!0 J*\020#\032\0020\0332\b\b\002\020\036\032\0020\0032\030\020\037\032\024\022\n\022\b\022\004\022\0020\r0\f\022\004\022\0020!0 J$\020$\032\0020\0332\b\b\002\020\036\032\0020\0032\022\020\037\032\016\022\004\022\0020\r\022\004\022\0020!0 J\006\020%\032\0020\033J\024\020&\032\0020\0332\n\020'\032\0060(j\002`)H\002J5\020*\032\002H+\"\004\b\000\020+2\006\020,\032\002H+2\030\020-\032\024\022\004\022\002H+\022\004\022\0020/\022\004\022\002H+0.H\026?\006\002\0200J(\0201\032\004\030\001H2\"\b\b\000\0202*\0020/2\f\0203\032\b\022\004\022\002H204H?\002?\006\002\0205J\024\0206\032\0020\0012\n\0203\032\006\022\002\b\00304H\026J\020\0207\032\0020\0062\b\b\002\020\030\032\0020\031J\034\0208\032\0020\0222\n\020'\032\0060(j\002`)2\006\020\027\032\0020\006H\002J\b\0209\032\0020\006H\002J\b\020:\032\0020\003H\026J\006\020;\032\0020\033J\020\020;\032\0020\0332\006\020\034\032\0020\006H\002R\016\020\005\032\0020\006X?\016?\006\002\n\000R\022\020\007\032\0060\bR\0020\000X?\004?\006\002\n\000R\016\020\t\032\0020\nX?\004?\006\002\n\000R\027\020\013\032\b\022\004\022\0020\r0\f8F?\006\006\032\004\b\016\020\017R\020\020\002\032\004\030\0010\003X?\004?\006\002\n\000R\024\020\020\032\b\022\004\022\0020\0220\021X?\004?\006\002\n\000R\016\020\023\032\0020\006X?\016?\006\002\n\000R\024\020\024\032\b\022\004\022\0020\r0\025X?\004?\006\002\n\000?\006="}, d2={"Lkotlinx/coroutines/test/TestCoroutineContext;", "Lkotlin/coroutines/CoroutineContext;", "name", "", "(Ljava/lang/String;)V", "counter", "", "ctxDispatcher", "Lkotlinx/coroutines/test/TestCoroutineContext$Dispatcher;", "ctxHandler", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "exceptions", "", "", "getExceptions", "()Ljava/util/List;", "queue", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/test/TimedRunnableObsolete;", "time", "uncaughtExceptions", "", "advanceTimeBy", "delayTime", "unit", "Ljava/util/concurrent/TimeUnit;", "advanceTimeTo", "", "targetTime", "assertAllUnhandledExceptions", "message", "predicate", "Lkotlin/Function1;", "", "assertAnyUnhandledException", "assertExceptions", "assertUnhandledException", "cancelAllActions", "enqueue", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "Lkotlin/coroutines/CoroutineContext$Element;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusKey", "now", "postDelayed", "processNextEvent", "toString", "triggerActions", "Dispatcher", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class TestCoroutineContext
  implements CoroutineContext
{
  private long counter;
  private final Dispatcher ctxDispatcher;
  private final CoroutineExceptionHandler ctxHandler;
  private final String name;
  private final ThreadSafeHeap<TimedRunnableObsolete> queue;
  private long time;
  private final List<Throwable> uncaughtExceptions;
  
  public TestCoroutineContext()
  {
    this(null, 1, null);
  }
  
  public TestCoroutineContext(String paramString)
  {
    this.name = paramString;
    this.uncaughtExceptions = ((List)new ArrayList());
    this.ctxDispatcher = new Dispatcher();
    this.ctxHandler = ((CoroutineExceptionHandler)new AbstractCoroutineContextElement((CoroutineContext.Key)CoroutineExceptionHandler.Key)
    {
      public void handleException(CoroutineContext paramAnonymousCoroutineContext, Throwable paramAnonymousThrowable)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousCoroutineContext, "context");
        Intrinsics.checkParameterIsNotNull(paramAnonymousThrowable, "exception");
        ((Collection)TestCoroutineContext.access$getUncaughtExceptions$p(jdField_this)).add(paramAnonymousThrowable);
      }
    });
    this.queue = new ThreadSafeHeap();
  }
  
  private final void enqueue(Runnable paramRunnable)
  {
    ThreadSafeHeap localThreadSafeHeap = this.queue;
    long l = this.counter;
    this.counter = (1L + l);
    localThreadSafeHeap.addLast((ThreadSafeHeapNode)new TimedRunnableObsolete(paramRunnable, l, 0L, 4, null));
  }
  
  private final TimedRunnableObsolete postDelayed(Runnable paramRunnable, long paramLong)
  {
    long l = this.counter;
    this.counter = (1L + l);
    paramRunnable = new TimedRunnableObsolete(paramRunnable, l, this.time + TimeUnit.MILLISECONDS.toNanos(paramLong));
    this.queue.addLast((ThreadSafeHeapNode)paramRunnable);
    return paramRunnable;
  }
  
  private final long processNextEvent()
  {
    TimedRunnableObsolete localTimedRunnableObsolete = (TimedRunnableObsolete)this.queue.peek();
    if (localTimedRunnableObsolete != null) {
      triggerActions(localTimedRunnableObsolete.time);
    }
    long l;
    if (this.queue.isEmpty()) {
      l = Long.MAX_VALUE;
    } else {
      l = 0L;
    }
    return l;
  }
  
  private final void triggerActions(long paramLong)
  {
    synchronized (this.queue)
    {
      for (;;)
      {
        ThreadSafeHeapNode localThreadSafeHeapNode = ???.firstImpl();
        Object localObject1 = null;
        Object localObject2 = null;
        if (localThreadSafeHeapNode != null)
        {
          int i;
          if (((TimedRunnableObsolete)localThreadSafeHeapNode).time <= paramLong) {
            i = 1;
          } else {
            i = 0;
          }
          if (i != 0) {
            localObject2 = ???.removeAtImpl(0);
          }
        }
        else
        {
          localObject2 = localObject1;
        }
        localObject2 = (TimedRunnableObsolete)localObject2;
        if (localObject2 == null) {
          break;
        }
        if (((TimedRunnableObsolete)localObject2).time != 0L) {
          this.time = ((TimedRunnableObsolete)localObject2).time;
        }
        ((TimedRunnableObsolete)localObject2).run();
      }
      return;
    }
  }
  
  public final long advanceTimeBy(long paramLong, TimeUnit paramTimeUnit)
  {
    Intrinsics.checkParameterIsNotNull(paramTimeUnit, "unit");
    long l = this.time;
    advanceTimeTo(paramTimeUnit.toNanos(paramLong) + l, TimeUnit.NANOSECONDS);
    return paramTimeUnit.convert(this.time - l, TimeUnit.NANOSECONDS);
  }
  
  public final void advanceTimeTo(long paramLong, TimeUnit paramTimeUnit)
  {
    Intrinsics.checkParameterIsNotNull(paramTimeUnit, "unit");
    paramLong = paramTimeUnit.toNanos(paramLong);
    triggerActions(paramLong);
    if (paramLong > this.time) {
      this.time = paramLong;
    }
  }
  
  public final void assertAllUnhandledExceptions(String paramString, Function1<? super Throwable, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "message");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    Object localObject = (Iterable)this.uncaughtExceptions;
    boolean bool = localObject instanceof Collection;
    int i = 1;
    int j;
    if ((bool) && (((Collection)localObject).isEmpty()))
    {
      j = i;
    }
    else
    {
      localObject = ((Iterable)localObject).iterator();
      do
      {
        j = i;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
      } while (((Boolean)paramFunction1.invoke(((Iterator)localObject).next())).booleanValue());
      j = 0;
    }
    if (j != 0)
    {
      this.uncaughtExceptions.clear();
      return;
    }
    throw ((Throwable)new AssertionError(paramString));
  }
  
  public final void assertAnyUnhandledException(String paramString, Function1<? super Throwable, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "message");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    Object localObject = (Iterable)this.uncaughtExceptions;
    boolean bool = localObject instanceof Collection;
    int i = 0;
    int j;
    if ((bool) && (((Collection)localObject).isEmpty()))
    {
      j = i;
    }
    else
    {
      localObject = ((Iterable)localObject).iterator();
      do
      {
        j = i;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
      } while (!((Boolean)paramFunction1.invoke(((Iterator)localObject).next())).booleanValue());
      j = 1;
    }
    if (j != 0)
    {
      this.uncaughtExceptions.clear();
      return;
    }
    throw ((Throwable)new AssertionError(paramString));
  }
  
  public final void assertExceptions(String paramString, Function1<? super List<? extends Throwable>, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "message");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    if (((Boolean)paramFunction1.invoke(this.uncaughtExceptions)).booleanValue())
    {
      this.uncaughtExceptions.clear();
      return;
    }
    throw ((Throwable)new AssertionError(paramString));
  }
  
  public final void assertUnhandledException(String paramString, Function1<? super Throwable, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "message");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    if ((this.uncaughtExceptions.size() == 1) && (((Boolean)paramFunction1.invoke(this.uncaughtExceptions.get(0))).booleanValue()))
    {
      this.uncaughtExceptions.clear();
      return;
    }
    throw ((Throwable)new AssertionError(paramString));
  }
  
  public final void cancelAllActions()
  {
    if (!this.queue.isEmpty()) {
      this.queue.clear();
    }
  }
  
  public <R> R fold(R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
    return paramFunction2.invoke(paramFunction2.invoke(paramR, this.ctxDispatcher), this.ctxHandler);
  }
  
  public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    if (paramKey == ContinuationInterceptor.Key)
    {
      paramKey = this.ctxDispatcher;
      if (paramKey != null) {
        paramKey = (CoroutineContext.Element)paramKey;
      } else {
        throw new TypeCastException("null cannot be cast to non-null type E");
      }
    }
    else if (paramKey == CoroutineExceptionHandler.Key)
    {
      paramKey = this.ctxHandler;
      if (paramKey != null) {
        paramKey = (CoroutineContext.Element)paramKey;
      } else {
        throw new TypeCastException("null cannot be cast to non-null type E");
      }
    }
    else
    {
      paramKey = null;
    }
    return paramKey;
  }
  
  public final List<Throwable> getExceptions()
  {
    return this.uncaughtExceptions;
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key<?> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    if (paramKey == ContinuationInterceptor.Key) {
      paramKey = (CoroutineContext)this.ctxHandler;
    } else if (paramKey == CoroutineExceptionHandler.Key) {
      paramKey = (CoroutineContext)this.ctxDispatcher;
    } else {
      paramKey = (CoroutineContext)this;
    }
    return paramKey;
  }
  
  public final long now(TimeUnit paramTimeUnit)
  {
    Intrinsics.checkParameterIsNotNull(paramTimeUnit, "unit");
    return paramTimeUnit.convert(this.time, TimeUnit.NANOSECONDS);
  }
  
  public CoroutineContext plus(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return CoroutineContext.DefaultImpls.plus(this, paramCoroutineContext);
  }
  
  public String toString()
  {
    Object localObject = this.name;
    if (localObject == null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("TestCoroutineContext@");
      ((StringBuilder)localObject).append(DebugStringsKt.getHexAddress(this));
      localObject = ((StringBuilder)localObject).toString();
    }
    return localObject;
  }
  
  public final void triggerActions()
  {
    triggerActions(this.time);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000F\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\003\n\002\030\002\n\000\n\002\020\013\n\000\n\002\020\016\n\000\b?\004\030\0002\0020\0012\0020\002B\005?\006\002\020\003J\034\020\004\032\0020\0052\006\020\006\032\0020\0072\n\020\b\032\0060\tj\002`\nH\026J\034\020\013\032\0020\f2\006\020\r\032\0020\0162\n\020\b\032\0060\tj\002`\nH\026J\b\020\017\032\0020\016H\026J\036\020\020\032\0020\0052\006\020\r\032\0020\0162\f\020\021\032\b\022\004\022\0020\0050\022H\026J\b\020\023\032\0020\024H\026J\b\020\025\032\0020\026H\026?\006\027"}, d2={"Lkotlinx/coroutines/test/TestCoroutineContext$Dispatcher;", "Lkotlinx/coroutines/EventLoop;", "Lkotlinx/coroutines/Delay;", "(Lkotlinx/coroutines/test/TestCoroutineContext;)V", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "", "processNextEvent", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "shouldBeProcessedFromContext", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  private final class Dispatcher
    extends EventLoop
    implements Delay
  {
    public Dispatcher()
    {
      EventLoop.incrementUseCount$default(this, false, 1, null);
    }
    
    public Object delay(long paramLong, Continuation<? super Unit> paramContinuation)
    {
      return Delay.DefaultImpls.delay(this, paramLong, paramContinuation);
    }
    
    public void dispatch(CoroutineContext paramCoroutineContext, Runnable paramRunnable)
    {
      Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
      Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
      TestCoroutineContext.access$enqueue(this.this$0, paramRunnable);
    }
    
    public DisposableHandle invokeOnTimeout(long paramLong, Runnable paramRunnable)
    {
      Intrinsics.checkParameterIsNotNull(paramRunnable, "block");
      (DisposableHandle)new DisposableHandle()
      {
        public void dispose()
        {
          TestCoroutineContext.access$getQueue$p(this.this$0.this$0).remove((ThreadSafeHeapNode)this.$node);
        }
      };
    }
    
    public long processNextEvent()
    {
      return TestCoroutineContext.access$processNextEvent(this.this$0);
    }
    
    public void scheduleResumeAfterDelay(long paramLong, final CancellableContinuation<? super Unit> paramCancellableContinuation)
    {
      Intrinsics.checkParameterIsNotNull(paramCancellableContinuation, "continuation");
      TestCoroutineContext.access$postDelayed(this.this$0, (Runnable)new Runnable()
      {
        public final void run()
        {
          paramCancellableContinuation.resumeUndispatched(this.this$0, Unit.INSTANCE);
        }
      }, paramLong);
    }
    
    public boolean shouldBeProcessedFromContext()
    {
      return true;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Dispatcher(");
      localStringBuilder.append(this.this$0);
      localStringBuilder.append(')');
      return localStringBuilder.toString();
    }
  }
}
