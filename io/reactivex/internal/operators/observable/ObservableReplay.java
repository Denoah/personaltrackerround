package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.disposables.ResettableConnectable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.HasUpstreamObservableSource;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableReplay<T>
  extends ConnectableObservable<T>
  implements HasUpstreamObservableSource<T>, ResettableConnectable
{
  static final BufferSupplier DEFAULT_UNBOUNDED_FACTORY = new UnBoundedFactory();
  final BufferSupplier<T> bufferFactory;
  final AtomicReference<ReplayObserver<T>> current;
  final ObservableSource<T> onSubscribe;
  final ObservableSource<T> source;
  
  private ObservableReplay(ObservableSource<T> paramObservableSource1, ObservableSource<T> paramObservableSource2, AtomicReference<ReplayObserver<T>> paramAtomicReference, BufferSupplier<T> paramBufferSupplier)
  {
    this.onSubscribe = paramObservableSource1;
    this.source = paramObservableSource2;
    this.current = paramAtomicReference;
    this.bufferFactory = paramBufferSupplier;
  }
  
  public static <T> ConnectableObservable<T> create(ObservableSource<T> paramObservableSource, int paramInt)
  {
    if (paramInt == Integer.MAX_VALUE) {
      return createFrom(paramObservableSource);
    }
    return create(paramObservableSource, new ReplayBufferSupplier(paramInt));
  }
  
  public static <T> ConnectableObservable<T> create(ObservableSource<T> paramObservableSource, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    return create(paramObservableSource, paramLong, paramTimeUnit, paramScheduler, Integer.MAX_VALUE);
  }
  
  public static <T> ConnectableObservable<T> create(ObservableSource<T> paramObservableSource, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt)
  {
    return create(paramObservableSource, new ScheduledReplaySupplier(paramInt, paramLong, paramTimeUnit, paramScheduler));
  }
  
  static <T> ConnectableObservable<T> create(ObservableSource<T> paramObservableSource, BufferSupplier<T> paramBufferSupplier)
  {
    AtomicReference localAtomicReference = new AtomicReference();
    return RxJavaPlugins.onAssembly(new ObservableReplay(new ReplaySource(localAtomicReference, paramBufferSupplier), paramObservableSource, localAtomicReference, paramBufferSupplier));
  }
  
  public static <T> ConnectableObservable<T> createFrom(ObservableSource<? extends T> paramObservableSource)
  {
    return create(paramObservableSource, DEFAULT_UNBOUNDED_FACTORY);
  }
  
  public static <U, R> Observable<R> multicastSelector(Callable<? extends ConnectableObservable<U>> paramCallable, Function<? super Observable<U>, ? extends ObservableSource<R>> paramFunction)
  {
    return RxJavaPlugins.onAssembly(new MulticastReplay(paramCallable, paramFunction));
  }
  
  public static <T> ConnectableObservable<T> observeOn(ConnectableObservable<T> paramConnectableObservable, Scheduler paramScheduler)
  {
    return RxJavaPlugins.onAssembly(new Replay(paramConnectableObservable, paramConnectableObservable.observeOn(paramScheduler)));
  }
  
  public void connect(Consumer<? super Disposable> paramConsumer)
  {
    ReplayObserver localReplayObserver1;
    ReplayObserver localReplayObserver2;
    do
    {
      localReplayObserver1 = (ReplayObserver)this.current.get();
      if (localReplayObserver1 != null)
      {
        localReplayObserver2 = localReplayObserver1;
        if (!localReplayObserver1.isDisposed()) {
          break;
        }
      }
      localReplayObserver2 = new ReplayObserver(this.bufferFactory.call());
    } while (!this.current.compareAndSet(localReplayObserver1, localReplayObserver2));
    int i;
    if ((!localReplayObserver2.shouldConnect.get()) && (localReplayObserver2.shouldConnect.compareAndSet(false, true))) {
      i = 1;
    } else {
      i = 0;
    }
    try
    {
      paramConsumer.accept(localReplayObserver2);
      if (i != 0) {
        this.source.subscribe(localReplayObserver2);
      }
      return;
    }
    finally
    {
      if (i != 0) {
        localReplayObserver2.shouldConnect.compareAndSet(true, false);
      }
      Exceptions.throwIfFatal(paramConsumer);
    }
  }
  
  public void resetIf(Disposable paramDisposable)
  {
    this.current.compareAndSet((ReplayObserver)paramDisposable, null);
  }
  
  public ObservableSource<T> source()
  {
    return this.source;
  }
  
  protected void subscribeActual(Observer<? super T> paramObserver)
  {
    this.onSubscribe.subscribe(paramObserver);
  }
  
  static abstract class BoundedReplayBuffer<T>
    extends AtomicReference<ObservableReplay.Node>
    implements ObservableReplay.ReplayBuffer<T>
  {
    private static final long serialVersionUID = 2346567790059478686L;
    int size;
    ObservableReplay.Node tail;
    
    BoundedReplayBuffer()
    {
      ObservableReplay.Node localNode = new ObservableReplay.Node(null);
      this.tail = localNode;
      set(localNode);
    }
    
    final void addLast(ObservableReplay.Node paramNode)
    {
      this.tail.set(paramNode);
      this.tail = paramNode;
      this.size += 1;
    }
    
    final void collect(Collection<? super T> paramCollection)
    {
      ObservableReplay.Node localNode = getHead();
      for (;;)
      {
        localNode = (ObservableReplay.Node)localNode.get();
        if (localNode == null) {
          break;
        }
        Object localObject = leaveTransform(localNode.value);
        if ((NotificationLite.isComplete(localObject)) || (NotificationLite.isError(localObject))) {
          break;
        }
        paramCollection.add(NotificationLite.getValue(localObject));
      }
    }
    
    public final void complete()
    {
      addLast(new ObservableReplay.Node(enterTransform(NotificationLite.complete())));
      truncateFinal();
    }
    
    Object enterTransform(Object paramObject)
    {
      return paramObject;
    }
    
    public final void error(Throwable paramThrowable)
    {
      addLast(new ObservableReplay.Node(enterTransform(NotificationLite.error(paramThrowable))));
      truncateFinal();
    }
    
    ObservableReplay.Node getHead()
    {
      return (ObservableReplay.Node)get();
    }
    
    boolean hasCompleted()
    {
      boolean bool;
      if ((this.tail.value != null) && (NotificationLite.isComplete(leaveTransform(this.tail.value)))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean hasError()
    {
      boolean bool;
      if ((this.tail.value != null) && (NotificationLite.isError(leaveTransform(this.tail.value)))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    Object leaveTransform(Object paramObject)
    {
      return paramObject;
    }
    
    public final void next(T paramT)
    {
      addLast(new ObservableReplay.Node(enterTransform(NotificationLite.next(paramT))));
      truncate();
    }
    
    final void removeFirst()
    {
      ObservableReplay.Node localNode = (ObservableReplay.Node)((ObservableReplay.Node)get()).get();
      this.size -= 1;
      setFirst(localNode);
    }
    
    final void removeSome(int paramInt)
    {
      ObservableReplay.Node localNode = (ObservableReplay.Node)get();
      while (paramInt > 0)
      {
        localNode = (ObservableReplay.Node)localNode.get();
        paramInt--;
        this.size -= 1;
      }
      setFirst(localNode);
    }
    
    public final void replay(ObservableReplay.InnerDisposable<T> paramInnerDisposable)
    {
      if (paramInnerDisposable.getAndIncrement() != 0) {
        return;
      }
      int i = 1;
      int j;
      do
      {
        ObservableReplay.Node localNode1 = (ObservableReplay.Node)paramInnerDisposable.index();
        ObservableReplay.Node localNode2 = localNode1;
        if (localNode1 == null)
        {
          localNode2 = getHead();
          paramInnerDisposable.index = localNode2;
        }
        for (;;)
        {
          if (paramInnerDisposable.isDisposed())
          {
            paramInnerDisposable.index = null;
            return;
          }
          localNode1 = (ObservableReplay.Node)localNode2.get();
          if (localNode1 == null) {
            break;
          }
          if (NotificationLite.accept(leaveTransform(localNode1.value), paramInnerDisposable.child))
          {
            paramInnerDisposable.index = null;
            return;
          }
          localNode2 = localNode1;
        }
        paramInnerDisposable.index = localNode2;
        j = paramInnerDisposable.addAndGet(-i);
        i = j;
      } while (j != 0);
    }
    
    final void setFirst(ObservableReplay.Node paramNode)
    {
      set(paramNode);
    }
    
    final void trimHead()
    {
      ObservableReplay.Node localNode1 = (ObservableReplay.Node)get();
      if (localNode1.value != null)
      {
        ObservableReplay.Node localNode2 = new ObservableReplay.Node(null);
        localNode2.lazySet(localNode1.get());
        set(localNode2);
      }
    }
    
    abstract void truncate();
    
    void truncateFinal()
    {
      trimHead();
    }
  }
  
  static abstract interface BufferSupplier<T>
  {
    public abstract ObservableReplay.ReplayBuffer<T> call();
  }
  
  static final class DisposeConsumer<R>
    implements Consumer<Disposable>
  {
    private final ObserverResourceWrapper<R> srw;
    
    DisposeConsumer(ObserverResourceWrapper<R> paramObserverResourceWrapper)
    {
      this.srw = paramObserverResourceWrapper;
    }
    
    public void accept(Disposable paramDisposable)
    {
      this.srw.setResource(paramDisposable);
    }
  }
  
  static final class InnerDisposable<T>
    extends AtomicInteger
    implements Disposable
  {
    private static final long serialVersionUID = 2728361546769921047L;
    volatile boolean cancelled;
    final Observer<? super T> child;
    Object index;
    final ObservableReplay.ReplayObserver<T> parent;
    
    InnerDisposable(ObservableReplay.ReplayObserver<T> paramReplayObserver, Observer<? super T> paramObserver)
    {
      this.parent = paramReplayObserver;
      this.child = paramObserver;
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.parent.remove(this);
        this.index = null;
      }
    }
    
    <U> U index()
    {
      return this.index;
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
  }
  
  static final class MulticastReplay<R, U>
    extends Observable<R>
  {
    private final Callable<? extends ConnectableObservable<U>> connectableFactory;
    private final Function<? super Observable<U>, ? extends ObservableSource<R>> selector;
    
    MulticastReplay(Callable<? extends ConnectableObservable<U>> paramCallable, Function<? super Observable<U>, ? extends ObservableSource<R>> paramFunction)
    {
      this.connectableFactory = paramCallable;
      this.selector = paramFunction;
    }
    
    protected void subscribeActual(Observer<? super R> paramObserver)
    {
      try
      {
        ConnectableObservable localConnectableObservable = (ConnectableObservable)ObjectHelper.requireNonNull(this.connectableFactory.call(), "The connectableFactory returned a null ConnectableObservable");
        ObservableSource localObservableSource = (ObservableSource)ObjectHelper.requireNonNull(this.selector.apply(localConnectableObservable), "The selector returned a null ObservableSource");
        paramObserver = new ObserverResourceWrapper(paramObserver);
        localObservableSource.subscribe(paramObserver);
        localConnectableObservable.connect(new ObservableReplay.DisposeConsumer(paramObserver));
        return;
      }
      finally
      {
        Exceptions.throwIfFatal(localThrowable);
        EmptyDisposable.error(localThrowable, paramObserver);
      }
    }
  }
  
  static final class Node
    extends AtomicReference<Node>
  {
    private static final long serialVersionUID = 245354315435971818L;
    final Object value;
    
    Node(Object paramObject)
    {
      this.value = paramObject;
    }
  }
  
  static final class Replay<T>
    extends ConnectableObservable<T>
  {
    private final ConnectableObservable<T> co;
    private final Observable<T> observable;
    
    Replay(ConnectableObservable<T> paramConnectableObservable, Observable<T> paramObservable)
    {
      this.co = paramConnectableObservable;
      this.observable = paramObservable;
    }
    
    public void connect(Consumer<? super Disposable> paramConsumer)
    {
      this.co.connect(paramConsumer);
    }
    
    protected void subscribeActual(Observer<? super T> paramObserver)
    {
      this.observable.subscribe(paramObserver);
    }
  }
  
  static abstract interface ReplayBuffer<T>
  {
    public abstract void complete();
    
    public abstract void error(Throwable paramThrowable);
    
    public abstract void next(T paramT);
    
    public abstract void replay(ObservableReplay.InnerDisposable<T> paramInnerDisposable);
  }
  
  static final class ReplayBufferSupplier<T>
    implements ObservableReplay.BufferSupplier<T>
  {
    private final int bufferSize;
    
    ReplayBufferSupplier(int paramInt)
    {
      this.bufferSize = paramInt;
    }
    
    public ObservableReplay.ReplayBuffer<T> call()
    {
      return new ObservableReplay.SizeBoundReplayBuffer(this.bufferSize);
    }
  }
  
  static final class ReplayObserver<T>
    extends AtomicReference<Disposable>
    implements Observer<T>, Disposable
  {
    static final ObservableReplay.InnerDisposable[] EMPTY = new ObservableReplay.InnerDisposable[0];
    static final ObservableReplay.InnerDisposable[] TERMINATED = new ObservableReplay.InnerDisposable[0];
    private static final long serialVersionUID = -533785617179540163L;
    final ObservableReplay.ReplayBuffer<T> buffer;
    boolean done;
    final AtomicReference<ObservableReplay.InnerDisposable[]> observers;
    final AtomicBoolean shouldConnect;
    
    ReplayObserver(ObservableReplay.ReplayBuffer<T> paramReplayBuffer)
    {
      this.buffer = paramReplayBuffer;
      this.observers = new AtomicReference(EMPTY);
      this.shouldConnect = new AtomicBoolean();
    }
    
    boolean add(ObservableReplay.InnerDisposable<T> paramInnerDisposable)
    {
      ObservableReplay.InnerDisposable[] arrayOfInnerDisposable1;
      ObservableReplay.InnerDisposable[] arrayOfInnerDisposable2;
      do
      {
        arrayOfInnerDisposable1 = (ObservableReplay.InnerDisposable[])this.observers.get();
        if (arrayOfInnerDisposable1 == TERMINATED) {
          return false;
        }
        int i = arrayOfInnerDisposable1.length;
        arrayOfInnerDisposable2 = new ObservableReplay.InnerDisposable[i + 1];
        System.arraycopy(arrayOfInnerDisposable1, 0, arrayOfInnerDisposable2, 0, i);
        arrayOfInnerDisposable2[i] = paramInnerDisposable;
      } while (!this.observers.compareAndSet(arrayOfInnerDisposable1, arrayOfInnerDisposable2));
      return true;
    }
    
    public void dispose()
    {
      this.observers.set(TERMINATED);
      DisposableHelper.dispose(this);
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.observers.get() == TERMINATED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      if (!this.done)
      {
        this.done = true;
        this.buffer.complete();
        replayFinal();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!this.done)
      {
        this.done = true;
        this.buffer.error(paramThrowable);
        replayFinal();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      if (!this.done)
      {
        this.buffer.next(paramT);
        replay();
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.setOnce(this, paramDisposable)) {
        replay();
      }
    }
    
    void remove(ObservableReplay.InnerDisposable<T> paramInnerDisposable)
    {
      ObservableReplay.InnerDisposable[] arrayOfInnerDisposable1;
      ObservableReplay.InnerDisposable[] arrayOfInnerDisposable2;
      do
      {
        arrayOfInnerDisposable1 = (ObservableReplay.InnerDisposable[])this.observers.get();
        int i = arrayOfInnerDisposable1.length;
        if (i == 0) {
          return;
        }
        int j = -1;
        int m;
        for (int k = 0;; k++)
        {
          m = j;
          if (k >= i) {
            break;
          }
          if (arrayOfInnerDisposable1[k].equals(paramInnerDisposable))
          {
            m = k;
            break;
          }
        }
        if (m < 0) {
          return;
        }
        if (i == 1)
        {
          arrayOfInnerDisposable2 = EMPTY;
        }
        else
        {
          arrayOfInnerDisposable2 = new ObservableReplay.InnerDisposable[i - 1];
          System.arraycopy(arrayOfInnerDisposable1, 0, arrayOfInnerDisposable2, 0, m);
          System.arraycopy(arrayOfInnerDisposable1, m + 1, arrayOfInnerDisposable2, m, i - m - 1);
        }
      } while (!this.observers.compareAndSet(arrayOfInnerDisposable1, arrayOfInnerDisposable2));
    }
    
    void replay()
    {
      for (ObservableReplay.InnerDisposable localInnerDisposable : (ObservableReplay.InnerDisposable[])this.observers.get()) {
        this.buffer.replay(localInnerDisposable);
      }
    }
    
    void replayFinal()
    {
      for (ObservableReplay.InnerDisposable localInnerDisposable : (ObservableReplay.InnerDisposable[])this.observers.getAndSet(TERMINATED)) {
        this.buffer.replay(localInnerDisposable);
      }
    }
  }
  
  static final class ReplaySource<T>
    implements ObservableSource<T>
  {
    private final ObservableReplay.BufferSupplier<T> bufferFactory;
    private final AtomicReference<ObservableReplay.ReplayObserver<T>> curr;
    
    ReplaySource(AtomicReference<ObservableReplay.ReplayObserver<T>> paramAtomicReference, ObservableReplay.BufferSupplier<T> paramBufferSupplier)
    {
      this.curr = paramAtomicReference;
      this.bufferFactory = paramBufferSupplier;
    }
    
    public void subscribe(Observer<? super T> paramObserver)
    {
      Object localObject2;
      do
      {
        localObject1 = (ObservableReplay.ReplayObserver)this.curr.get();
        localObject2 = localObject1;
        if (localObject1 != null) {
          break;
        }
        localObject2 = new ObservableReplay.ReplayObserver(this.bufferFactory.call());
      } while (!this.curr.compareAndSet(null, localObject2));
      Object localObject1 = new ObservableReplay.InnerDisposable((ObservableReplay.ReplayObserver)localObject2, paramObserver);
      paramObserver.onSubscribe((Disposable)localObject1);
      ((ObservableReplay.ReplayObserver)localObject2).add((ObservableReplay.InnerDisposable)localObject1);
      if (((ObservableReplay.InnerDisposable)localObject1).isDisposed())
      {
        ((ObservableReplay.ReplayObserver)localObject2).remove((ObservableReplay.InnerDisposable)localObject1);
        return;
      }
      ((ObservableReplay.ReplayObserver)localObject2).buffer.replay((ObservableReplay.InnerDisposable)localObject1);
    }
  }
  
  static final class ScheduledReplaySupplier<T>
    implements ObservableReplay.BufferSupplier<T>
  {
    private final int bufferSize;
    private final long maxAge;
    private final Scheduler scheduler;
    private final TimeUnit unit;
    
    ScheduledReplaySupplier(int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      this.bufferSize = paramInt;
      this.maxAge = paramLong;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
    }
    
    public ObservableReplay.ReplayBuffer<T> call()
    {
      return new ObservableReplay.SizeAndTimeBoundReplayBuffer(this.bufferSize, this.maxAge, this.unit, this.scheduler);
    }
  }
  
  static final class SizeAndTimeBoundReplayBuffer<T>
    extends ObservableReplay.BoundedReplayBuffer<T>
  {
    private static final long serialVersionUID = 3457957419649567404L;
    final int limit;
    final long maxAge;
    final Scheduler scheduler;
    final TimeUnit unit;
    
    SizeAndTimeBoundReplayBuffer(int paramInt, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
    {
      this.scheduler = paramScheduler;
      this.limit = paramInt;
      this.maxAge = paramLong;
      this.unit = paramTimeUnit;
    }
    
    Object enterTransform(Object paramObject)
    {
      return new Timed(paramObject, this.scheduler.now(this.unit), this.unit);
    }
    
    ObservableReplay.Node getHead()
    {
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      Object localObject1 = (ObservableReplay.Node)get();
      Object localObject3;
      for (Object localObject2 = (ObservableReplay.Node)((ObservableReplay.Node)localObject1).get(); localObject2 != null; localObject2 = localObject3)
      {
        localObject3 = (Timed)((ObservableReplay.Node)localObject2).value;
        if ((NotificationLite.isComplete(((Timed)localObject3).value())) || (NotificationLite.isError(((Timed)localObject3).value())) || (((Timed)localObject3).time() > l1 - l2)) {
          break;
        }
        localObject3 = (ObservableReplay.Node)((ObservableReplay.Node)localObject2).get();
        localObject1 = localObject2;
      }
      return localObject1;
    }
    
    Object leaveTransform(Object paramObject)
    {
      return ((Timed)paramObject).value();
    }
    
    void truncate()
    {
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      Object localObject1 = (ObservableReplay.Node)get();
      Object localObject2 = (ObservableReplay.Node)((ObservableReplay.Node)localObject1).get();
      int i = 0;
      while (localObject2 != null)
      {
        ObservableReplay.Node localNode;
        if (this.size > this.limit)
        {
          i++;
          this.size -= 1;
          localNode = (ObservableReplay.Node)((ObservableReplay.Node)localObject2).get();
          localObject1 = localObject2;
          localObject2 = localNode;
        }
        else
        {
          if (((Timed)((ObservableReplay.Node)localObject2).value).time() > l1 - l2) {
            break;
          }
          i++;
          this.size -= 1;
          localNode = (ObservableReplay.Node)((ObservableReplay.Node)localObject2).get();
          localObject1 = localObject2;
          localObject2 = localNode;
        }
      }
      if (i != 0) {
        setFirst((ObservableReplay.Node)localObject1);
      }
    }
    
    void truncateFinal()
    {
      long l1 = this.scheduler.now(this.unit);
      long l2 = this.maxAge;
      Object localObject1 = (ObservableReplay.Node)get();
      Object localObject2 = (ObservableReplay.Node)((ObservableReplay.Node)localObject1).get();
      int i = 0;
      while ((localObject2 != null) && (this.size > 1) && (((Timed)((ObservableReplay.Node)localObject2).value).time() <= l1 - l2))
      {
        i++;
        this.size -= 1;
        ObservableReplay.Node localNode = (ObservableReplay.Node)((ObservableReplay.Node)localObject2).get();
        localObject1 = localObject2;
        localObject2 = localNode;
      }
      if (i != 0) {
        setFirst((ObservableReplay.Node)localObject1);
      }
    }
  }
  
  static final class SizeBoundReplayBuffer<T>
    extends ObservableReplay.BoundedReplayBuffer<T>
  {
    private static final long serialVersionUID = -5898283885385201806L;
    final int limit;
    
    SizeBoundReplayBuffer(int paramInt)
    {
      this.limit = paramInt;
    }
    
    void truncate()
    {
      if (this.size > this.limit) {
        removeFirst();
      }
    }
  }
  
  static final class UnBoundedFactory
    implements ObservableReplay.BufferSupplier<Object>
  {
    UnBoundedFactory() {}
    
    public ObservableReplay.ReplayBuffer<Object> call()
    {
      return new ObservableReplay.UnboundedReplayBuffer(16);
    }
  }
  
  static final class UnboundedReplayBuffer<T>
    extends ArrayList<Object>
    implements ObservableReplay.ReplayBuffer<T>
  {
    private static final long serialVersionUID = 7063189396499112664L;
    volatile int size;
    
    UnboundedReplayBuffer(int paramInt)
    {
      super();
    }
    
    public void complete()
    {
      add(NotificationLite.complete());
      this.size += 1;
    }
    
    public void error(Throwable paramThrowable)
    {
      add(NotificationLite.error(paramThrowable));
      this.size += 1;
    }
    
    public void next(T paramT)
    {
      add(NotificationLite.next(paramT));
      this.size += 1;
    }
    
    public void replay(ObservableReplay.InnerDisposable<T> paramInnerDisposable)
    {
      if (paramInnerDisposable.getAndIncrement() != 0) {
        return;
      }
      Observer localObserver = paramInnerDisposable.child;
      int i = 1;
      int k;
      do
      {
        if (paramInnerDisposable.isDisposed()) {
          return;
        }
        int j = this.size;
        Integer localInteger = (Integer)paramInnerDisposable.index();
        if (localInteger != null) {
          k = localInteger.intValue();
        }
        for (k = 0; k < j; k++)
        {
          if (NotificationLite.accept(get(k), localObserver)) {
            return;
          }
          if (paramInnerDisposable.isDisposed()) {
            return;
          }
        }
        paramInnerDisposable.index = Integer.valueOf(k);
        k = paramInnerDisposable.addAndGet(-i);
        i = k;
      } while (k != 0);
    }
  }
}
