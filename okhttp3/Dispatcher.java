package okhttp3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealCall;
import okhttp3.internal.connection.RealCall.AsyncCall;

@Metadata(bv={1, 0, 3}, d1={"\000\\\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\006\n\002\020\b\n\002\b\b\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\b\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\020 \n\002\030\002\n\002\b\004\030\0002\0020\001B\017\b\026\022\006\020\002\032\0020\003?\006\002\020\004B\005?\006\002\020\005J\006\020\036\032\0020\037J\031\020 \032\0020\0372\n\020!\032\0060\032R\0020\033H\000?\006\002\b\"J\025\020#\032\0020\0372\006\020!\032\0020\033H\000?\006\002\b$J\r\020\002\032\0020\003H\007?\006\002\b%J\026\020&\032\b\030\0010\032R\0020\0332\006\020'\032\0020(H\002J)\020)\032\0020\037\"\004\b\000\020*2\f\020+\032\b\022\004\022\002H*0,2\006\020!\032\002H*H\002?\006\002\020-J\025\020)\032\0020\0372\006\020!\032\0020\033H\000?\006\002\b.J\031\020)\032\0020\0372\n\020!\032\0060\032R\0020\033H\000?\006\002\b.J\b\020/\032\00200H\002J\f\0201\032\b\022\004\022\0020302J\006\0204\032\0020\020J\f\0205\032\b\022\004\022\0020302J\006\0206\032\0020\020R\021\020\002\032\0020\0038G?\006\006\032\004\b\002\020\006R\020\020\007\032\004\030\0010\003X?\016?\006\002\n\000R*\020\n\032\004\030\0010\t2\b\020\b\032\004\030\0010\t8F@FX?\016?\006\016\n\000\032\004\b\013\020\f\"\004\b\r\020\016R&\020\017\032\0020\0202\006\020\017\032\0020\0208F@FX?\016?\006\016\n\000\032\004\b\021\020\022\"\004\b\023\020\024R&\020\025\032\0020\0202\006\020\025\032\0020\0208F@FX?\016?\006\016\n\000\032\004\b\026\020\022\"\004\b\027\020\024R\030\020\030\032\f\022\b\022\0060\032R\0020\0330\031X?\004?\006\002\n\000R\030\020\034\032\f\022\b\022\0060\032R\0020\0330\031X?\004?\006\002\n\000R\024\020\035\032\b\022\004\022\0020\0330\031X?\004?\006\002\n\000?\0067"}, d2={"Lokhttp3/Dispatcher;", "", "executorService", "Ljava/util/concurrent/ExecutorService;", "(Ljava/util/concurrent/ExecutorService;)V", "()V", "()Ljava/util/concurrent/ExecutorService;", "executorServiceOrNull", "<set-?>", "Ljava/lang/Runnable;", "idleCallback", "getIdleCallback", "()Ljava/lang/Runnable;", "setIdleCallback", "(Ljava/lang/Runnable;)V", "maxRequests", "", "getMaxRequests", "()I", "setMaxRequests", "(I)V", "maxRequestsPerHost", "getMaxRequestsPerHost", "setMaxRequestsPerHost", "readyAsyncCalls", "Ljava/util/ArrayDeque;", "Lokhttp3/internal/connection/RealCall$AsyncCall;", "Lokhttp3/internal/connection/RealCall;", "runningAsyncCalls", "runningSyncCalls", "cancelAll", "", "enqueue", "call", "enqueue$okhttp", "executed", "executed$okhttp", "-deprecated_executorService", "findExistingCallWithHost", "host", "", "finished", "T", "calls", "Ljava/util/Deque;", "(Ljava/util/Deque;Ljava/lang/Object;)V", "finished$okhttp", "promoteAndExecute", "", "queuedCalls", "", "Lokhttp3/Call;", "queuedCallsCount", "runningCalls", "runningCallsCount", "okhttp"}, k=1, mv={1, 1, 16})
public final class Dispatcher
{
  private ExecutorService executorServiceOrNull;
  private Runnable idleCallback;
  private int maxRequests = 64;
  private int maxRequestsPerHost = 5;
  private final ArrayDeque<RealCall.AsyncCall> readyAsyncCalls = new ArrayDeque();
  private final ArrayDeque<RealCall.AsyncCall> runningAsyncCalls = new ArrayDeque();
  private final ArrayDeque<RealCall> runningSyncCalls = new ArrayDeque();
  
  public Dispatcher() {}
  
  public Dispatcher(ExecutorService paramExecutorService)
  {
    this();
    this.executorServiceOrNull = paramExecutorService;
  }
  
  private final RealCall.AsyncCall findExistingCallWithHost(String paramString)
  {
    Iterator localIterator = this.runningAsyncCalls.iterator();
    RealCall.AsyncCall localAsyncCall;
    while (localIterator.hasNext())
    {
      localAsyncCall = (RealCall.AsyncCall)localIterator.next();
      if (Intrinsics.areEqual(localAsyncCall.getHost(), paramString)) {
        return localAsyncCall;
      }
    }
    localIterator = this.readyAsyncCalls.iterator();
    while (localIterator.hasNext())
    {
      localAsyncCall = (RealCall.AsyncCall)localIterator.next();
      if (Intrinsics.areEqual(localAsyncCall.getHost(), paramString)) {
        return localAsyncCall;
      }
    }
    return null;
  }
  
  private final <T> void finished(Deque<T> paramDeque, T paramT)
  {
    try
    {
      if (paramDeque.remove(paramT))
      {
        paramDeque = this.idleCallback;
        paramT = Unit.INSTANCE;
        if ((!promoteAndExecute()) && (paramDeque != null)) {
          paramDeque.run();
        }
        return;
      }
      paramDeque = new java/lang/AssertionError;
      paramDeque.<init>("Call wasn't in-flight!");
      throw ((Throwable)paramDeque);
    }
    finally {}
  }
  
  private final boolean promoteAndExecute()
  {
    Object localObject3;
    if ((Util.assertionsEnabled) && (Thread.holdsLock(this)))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Thread ");
      localObject3 = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localObject3, "Thread.currentThread()");
      ((StringBuilder)localObject1).append(((Thread)localObject3).getName());
      ((StringBuilder)localObject1).append(" MUST NOT hold lock on ");
      ((StringBuilder)localObject1).append(this);
      throw ((Throwable)new AssertionError(((StringBuilder)localObject1).toString()));
    }
    Object localObject1 = (List)new ArrayList();
    try
    {
      Iterator localIterator = this.readyAsyncCalls.iterator();
      Intrinsics.checkExpressionValueIsNotNull(localIterator, "readyAsyncCalls.iterator()");
      while (localIterator.hasNext())
      {
        localObject3 = (RealCall.AsyncCall)localIterator.next();
        if (this.runningAsyncCalls.size() >= this.maxRequests) {
          break;
        }
        if (((RealCall.AsyncCall)localObject3).getCallsPerHost().get() < this.maxRequestsPerHost)
        {
          localIterator.remove();
          ((RealCall.AsyncCall)localObject3).getCallsPerHost().incrementAndGet();
          Intrinsics.checkExpressionValueIsNotNull(localObject3, "asyncCall");
          ((List)localObject1).add(localObject3);
          this.runningAsyncCalls.add(localObject3);
        }
      }
      int i = runningCallsCount();
      int j = 0;
      boolean bool;
      if (i > 0) {
        bool = true;
      } else {
        bool = false;
      }
      localObject3 = Unit.INSTANCE;
      i = ((List)localObject1).size();
      while (j < i)
      {
        ((RealCall.AsyncCall)((List)localObject1).get(j)).executeOn(executorService());
        j++;
      }
      return bool;
    }
    finally {}
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="executorService", imports={}))
  public final ExecutorService -deprecated_executorService()
  {
    return executorService();
  }
  
  public final void cancelAll()
  {
    try
    {
      Iterator localIterator = this.readyAsyncCalls.iterator();
      while (localIterator.hasNext()) {
        ((RealCall.AsyncCall)localIterator.next()).getCall().cancel();
      }
      localIterator = this.runningAsyncCalls.iterator();
      while (localIterator.hasNext()) {
        ((RealCall.AsyncCall)localIterator.next()).getCall().cancel();
      }
      localIterator = this.runningSyncCalls.iterator();
      while (localIterator.hasNext()) {
        ((RealCall)localIterator.next()).cancel();
      }
      return;
    }
    finally {}
  }
  
  public final void enqueue$okhttp(RealCall.AsyncCall paramAsyncCall)
  {
    Intrinsics.checkParameterIsNotNull(paramAsyncCall, "call");
    try
    {
      this.readyAsyncCalls.add(paramAsyncCall);
      if (!paramAsyncCall.getCall().getForWebSocket())
      {
        RealCall.AsyncCall localAsyncCall = findExistingCallWithHost(paramAsyncCall.getHost());
        if (localAsyncCall != null) {
          paramAsyncCall.reuseCallsPerHostFrom(localAsyncCall);
        }
      }
      paramAsyncCall = Unit.INSTANCE;
      promoteAndExecute();
      return;
    }
    finally {}
  }
  
  public final void executed$okhttp(RealCall paramRealCall)
  {
    try
    {
      Intrinsics.checkParameterIsNotNull(paramRealCall, "call");
      this.runningSyncCalls.add(paramRealCall);
      return;
    }
    finally
    {
      paramRealCall = finally;
      throw paramRealCall;
    }
  }
  
  public final ExecutorService executorService()
  {
    try
    {
      if (this.executorServiceOrNull == null)
      {
        localObject1 = new java/util/concurrent/ThreadPoolExecutor;
        TimeUnit localTimeUnit = TimeUnit.SECONDS;
        Object localObject3 = new java/util/concurrent/SynchronousQueue;
        ((SynchronousQueue)localObject3).<init>();
        BlockingQueue localBlockingQueue = (BlockingQueue)localObject3;
        localObject3 = new java/lang/StringBuilder;
        ((StringBuilder)localObject3).<init>();
        ((StringBuilder)localObject3).append(Util.okHttpName);
        ((StringBuilder)localObject3).append(" Dispatcher");
        ((ThreadPoolExecutor)localObject1).<init>(0, Integer.MAX_VALUE, 60L, localTimeUnit, localBlockingQueue, Util.threadFactory(((StringBuilder)localObject3).toString(), false));
        this.executorServiceOrNull = ((ExecutorService)localObject1);
      }
      Object localObject1 = this.executorServiceOrNull;
      if (localObject1 == null) {
        Intrinsics.throwNpe();
      }
      return localObject1;
    }
    finally {}
  }
  
  public final void finished$okhttp(RealCall.AsyncCall paramAsyncCall)
  {
    Intrinsics.checkParameterIsNotNull(paramAsyncCall, "call");
    paramAsyncCall.getCallsPerHost().decrementAndGet();
    finished((Deque)this.runningAsyncCalls, paramAsyncCall);
  }
  
  public final void finished$okhttp(RealCall paramRealCall)
  {
    Intrinsics.checkParameterIsNotNull(paramRealCall, "call");
    finished((Deque)this.runningSyncCalls, paramRealCall);
  }
  
  public final Runnable getIdleCallback()
  {
    try
    {
      Runnable localRunnable = this.idleCallback;
      return localRunnable;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final int getMaxRequests()
  {
    try
    {
      int i = this.maxRequests;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final int getMaxRequestsPerHost()
  {
    try
    {
      int i = this.maxRequestsPerHost;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final List<Call> queuedCalls()
  {
    try
    {
      Object localObject1 = (Iterable)this.readyAsyncCalls;
      Object localObject2 = new java/util/ArrayList;
      ((ArrayList)localObject2).<init>(CollectionsKt.collectionSizeOrDefault((Iterable)localObject1, 10));
      localObject2 = (Collection)localObject2;
      localObject1 = ((Iterable)localObject1).iterator();
      while (((Iterator)localObject1).hasNext()) {
        ((Collection)localObject2).add(((RealCall.AsyncCall)((Iterator)localObject1).next()).getCall());
      }
      localObject2 = Collections.unmodifiableList((List)localObject2);
      Intrinsics.checkExpressionValueIsNotNull(localObject2, "Collections.unmodifiable…yncCalls.map { it.call })");
      return localObject2;
    }
    finally {}
  }
  
  public final int queuedCallsCount()
  {
    try
    {
      int i = this.readyAsyncCalls.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final List<Call> runningCalls()
  {
    try
    {
      Object localObject1 = (Collection)this.runningSyncCalls;
      Object localObject3 = (Iterable)this.runningAsyncCalls;
      Object localObject4 = new java/util/ArrayList;
      ((ArrayList)localObject4).<init>(CollectionsKt.collectionSizeOrDefault((Iterable)localObject3, 10));
      localObject4 = (Collection)localObject4;
      localObject3 = ((Iterable)localObject3).iterator();
      while (((Iterator)localObject3).hasNext()) {
        ((Collection)localObject4).add(((RealCall.AsyncCall)((Iterator)localObject3).next()).getCall());
      }
      localObject1 = Collections.unmodifiableList(CollectionsKt.plus((Collection)localObject1, (Iterable)localObject4));
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "Collections.unmodifiable…yncCalls.map { it.call })");
      return localObject1;
    }
    finally {}
  }
  
  public final int runningCallsCount()
  {
    try
    {
      int i = this.runningAsyncCalls.size();
      int j = this.runningSyncCalls.size();
      return i + j;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void setIdleCallback(Runnable paramRunnable)
  {
    try
    {
      this.idleCallback = paramRunnable;
      return;
    }
    finally
    {
      paramRunnable = finally;
      throw paramRunnable;
    }
  }
  
  public final void setMaxRequests(int paramInt)
  {
    int i = 1;
    if (paramInt < 1) {
      i = 0;
    }
    if (i != 0) {
      try
      {
        this.maxRequests = paramInt;
        Unit localUnit = Unit.INSTANCE;
        promoteAndExecute();
        return;
      }
      finally {}
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("max < 1: ");
    localStringBuilder.append(paramInt);
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
  }
  
  public final void setMaxRequestsPerHost(int paramInt)
  {
    int i = 1;
    if (paramInt < 1) {
      i = 0;
    }
    if (i != 0) {
      try
      {
        this.maxRequestsPerHost = paramInt;
        Unit localUnit = Unit.INSTANCE;
        promoteAndExecute();
        return;
      }
      finally {}
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("max < 1: ");
    localStringBuilder.append(paramInt);
    throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
  }
}
