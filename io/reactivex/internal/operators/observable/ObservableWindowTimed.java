package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.subjects.UnicastSubject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWindowTimed<T>
  extends AbstractObservableWithUpstream<T, Observable<T>>
{
  final int bufferSize;
  final long maxSize;
  final boolean restartTimerOnMaxSize;
  final Scheduler scheduler;
  final long timeskip;
  final long timespan;
  final TimeUnit unit;
  
  public ObservableWindowTimed(ObservableSource<T> paramObservableSource, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler, long paramLong3, int paramInt, boolean paramBoolean)
  {
    super(paramObservableSource);
    this.timespan = paramLong1;
    this.timeskip = paramLong2;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.maxSize = paramLong3;
    this.bufferSize = paramInt;
    this.restartTimerOnMaxSize = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super Observable<T>> paramObserver)
  {
    paramObserver = new SerializedObserver(paramObserver);
    if (this.timespan == this.timeskip)
    {
      if (this.maxSize == Long.MAX_VALUE)
      {
        this.source.subscribe(new WindowExactUnboundedObserver(paramObserver, this.timespan, this.unit, this.scheduler, this.bufferSize));
        return;
      }
      this.source.subscribe(new WindowExactBoundedObserver(paramObserver, this.timespan, this.unit, this.scheduler, this.bufferSize, this.maxSize, this.restartTimerOnMaxSize));
      return;
    }
    this.source.subscribe(new WindowSkipObserver(paramObserver, this.timespan, this.timeskip, this.unit, this.scheduler.createWorker(), this.bufferSize));
  }
  
  static final class WindowExactBoundedObserver<T>
    extends QueueDrainObserver<T, Object, Observable<T>>
    implements Disposable
  {
    final int bufferSize;
    long count;
    final long maxSize;
    long producerIndex;
    final boolean restartTimerOnMaxSize;
    final Scheduler scheduler;
    volatile boolean terminated;
    final AtomicReference<Disposable> timer = new AtomicReference();
    final long timespan;
    final TimeUnit unit;
    Disposable upstream;
    UnicastSubject<T> window;
    final Scheduler.Worker worker;
    
    WindowExactBoundedObserver(Observer<? super Observable<T>> paramObserver, long paramLong1, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt, long paramLong2, boolean paramBoolean)
    {
      super(new MpscLinkedQueue());
      this.timespan = paramLong1;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
      this.bufferSize = paramInt;
      this.maxSize = paramLong2;
      this.restartTimerOnMaxSize = paramBoolean;
      if (paramBoolean) {
        this.worker = paramScheduler.createWorker();
      } else {
        this.worker = null;
      }
    }
    
    public void dispose()
    {
      this.cancelled = true;
    }
    
    void disposeTimer()
    {
      DisposableHelper.dispose(this.timer);
      Scheduler.Worker localWorker = this.worker;
      if (localWorker != null) {
        localWorker.dispose();
      }
    }
    
    void drainLoop()
    {
      MpscLinkedQueue localMpscLinkedQueue = (MpscLinkedQueue)this.queue;
      Observer localObserver = this.downstream;
      Object localObject1 = this.window;
      int i = 1;
      for (;;)
      {
        if (this.terminated)
        {
          this.upstream.dispose();
          localMpscLinkedQueue.clear();
          disposeTimer();
          return;
        }
        boolean bool1 = this.done;
        Object localObject2 = localMpscLinkedQueue.poll();
        int j;
        if (localObject2 == null) {
          j = 1;
        } else {
          j = 0;
        }
        boolean bool2 = localObject2 instanceof ConsumerIndexHolder;
        if ((bool1) && ((j != 0) || (bool2)))
        {
          this.window = null;
          localMpscLinkedQueue.clear();
          disposeTimer();
          localObject2 = this.error;
          if (localObject2 != null) {
            ((UnicastSubject)localObject1).onError((Throwable)localObject2);
          } else {
            ((UnicastSubject)localObject1).onComplete();
          }
          return;
        }
        if (j != 0)
        {
          j = leave(-i);
          i = j;
          if (j != 0) {}
        }
        else if (bool2)
        {
          localObject2 = (ConsumerIndexHolder)localObject2;
          if ((this.restartTimerOnMaxSize) || (this.producerIndex == ((ConsumerIndexHolder)localObject2).index))
          {
            ((UnicastSubject)localObject1).onComplete();
            this.count = 0L;
            localObject1 = UnicastSubject.create(this.bufferSize);
            this.window = ((UnicastSubject)localObject1);
            localObserver.onNext(localObject1);
          }
        }
        else
        {
          ((UnicastSubject)localObject1).onNext(NotificationLite.getValue(localObject2));
          long l = this.count + 1L;
          if (l >= this.maxSize)
          {
            this.producerIndex += 1L;
            this.count = 0L;
            ((UnicastSubject)localObject1).onComplete();
            localObject2 = UnicastSubject.create(this.bufferSize);
            this.window = ((UnicastSubject)localObject2);
            this.downstream.onNext(localObject2);
            localObject1 = localObject2;
            if (this.restartTimerOnMaxSize)
            {
              Disposable localDisposable = (Disposable)this.timer.get();
              localDisposable.dispose();
              localObject1 = this.worker;
              Object localObject3 = new ConsumerIndexHolder(this.producerIndex, this);
              l = this.timespan;
              localObject3 = ((Scheduler.Worker)localObject1).schedulePeriodically((Runnable)localObject3, l, l, this.unit);
              localObject1 = localObject2;
              if (!this.timer.compareAndSet(localDisposable, localObject3))
              {
                ((Disposable)localObject3).dispose();
                localObject1 = localObject2;
              }
            }
          }
          else
          {
            this.count = l;
          }
        }
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      this.downstream.onComplete();
      disposeTimer();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      this.downstream.onError(paramThrowable);
      disposeTimer();
    }
    
    public void onNext(T paramT)
    {
      if (this.terminated) {
        return;
      }
      if (fastEnter())
      {
        Object localObject = this.window;
        ((UnicastSubject)localObject).onNext(paramT);
        long l = this.count + 1L;
        if (l >= this.maxSize)
        {
          this.producerIndex += 1L;
          this.count = 0L;
          ((UnicastSubject)localObject).onComplete();
          paramT = UnicastSubject.create(this.bufferSize);
          this.window = paramT;
          this.downstream.onNext(paramT);
          if (this.restartTimerOnMaxSize)
          {
            ((Disposable)this.timer.get()).dispose();
            paramT = this.worker;
            localObject = new ConsumerIndexHolder(this.producerIndex, this);
            l = this.timespan;
            paramT = paramT.schedulePeriodically((Runnable)localObject, l, l, this.unit);
            DisposableHelper.replace(this.timer, paramT);
          }
        }
        else
        {
          this.count = l;
        }
        if (leave(-1) != 0) {}
      }
      else
      {
        this.queue.offer(NotificationLite.next(paramT));
        if (!enter()) {
          return;
        }
      }
      drainLoop();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        Object localObject = this.downstream;
        ((Observer)localObject).onSubscribe(this);
        if (this.cancelled) {
          return;
        }
        paramDisposable = UnicastSubject.create(this.bufferSize);
        this.window = paramDisposable;
        ((Observer)localObject).onNext(paramDisposable);
        paramDisposable = new ConsumerIndexHolder(this.producerIndex, this);
        long l;
        if (this.restartTimerOnMaxSize)
        {
          localObject = this.worker;
          l = this.timespan;
          paramDisposable = ((Scheduler.Worker)localObject).schedulePeriodically(paramDisposable, l, l, this.unit);
        }
        else
        {
          localObject = this.scheduler;
          l = this.timespan;
          paramDisposable = ((Scheduler)localObject).schedulePeriodicallyDirect(paramDisposable, l, l, this.unit);
        }
        DisposableHelper.replace(this.timer, paramDisposable);
      }
    }
    
    static final class ConsumerIndexHolder
      implements Runnable
    {
      final long index;
      final ObservableWindowTimed.WindowExactBoundedObserver<?> parent;
      
      ConsumerIndexHolder(long paramLong, ObservableWindowTimed.WindowExactBoundedObserver<?> paramWindowExactBoundedObserver)
      {
        this.index = paramLong;
        this.parent = paramWindowExactBoundedObserver;
      }
      
      public void run()
      {
        ObservableWindowTimed.WindowExactBoundedObserver localWindowExactBoundedObserver = this.parent;
        if (!localWindowExactBoundedObserver.cancelled)
        {
          localWindowExactBoundedObserver.queue.offer(this);
        }
        else
        {
          localWindowExactBoundedObserver.terminated = true;
          localWindowExactBoundedObserver.disposeTimer();
        }
        if (localWindowExactBoundedObserver.enter()) {
          localWindowExactBoundedObserver.drainLoop();
        }
      }
    }
  }
  
  static final class WindowExactUnboundedObserver<T>
    extends QueueDrainObserver<T, Object, Observable<T>>
    implements Observer<T>, Disposable, Runnable
  {
    static final Object NEXT = new Object();
    final int bufferSize;
    final Scheduler scheduler;
    volatile boolean terminated;
    final AtomicReference<Disposable> timer = new AtomicReference();
    final long timespan;
    final TimeUnit unit;
    Disposable upstream;
    UnicastSubject<T> window;
    
    WindowExactUnboundedObserver(Observer<? super Observable<T>> paramObserver, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt)
    {
      super(new MpscLinkedQueue());
      this.timespan = paramLong;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
      this.bufferSize = paramInt;
    }
    
    public void dispose()
    {
      this.cancelled = true;
    }
    
    void disposeTimer()
    {
      DisposableHelper.dispose(this.timer);
    }
    
    void drainLoop()
    {
      Object localObject1 = (MpscLinkedQueue)this.queue;
      Observer localObserver = this.downstream;
      UnicastSubject localUnicastSubject = this.window;
      int i = 1;
      for (;;)
      {
        boolean bool1 = this.terminated;
        boolean bool2 = this.done;
        Object localObject2 = ((MpscLinkedQueue)localObject1).poll();
        if ((bool2) && ((localObject2 == null) || (localObject2 == NEXT)))
        {
          this.window = null;
          ((MpscLinkedQueue)localObject1).clear();
          disposeTimer();
          localObject1 = this.error;
          if (localObject1 != null) {
            localUnicastSubject.onError((Throwable)localObject1);
          } else {
            localUnicastSubject.onComplete();
          }
          return;
        }
        if (localObject2 == null)
        {
          int j = leave(-i);
          i = j;
          if (j != 0) {}
        }
        else if (localObject2 == NEXT)
        {
          localUnicastSubject.onComplete();
          if (!bool1)
          {
            localUnicastSubject = UnicastSubject.create(this.bufferSize);
            this.window = localUnicastSubject;
            localObserver.onNext(localUnicastSubject);
          }
          else
          {
            this.upstream.dispose();
          }
        }
        else
        {
          localUnicastSubject.onNext(NotificationLite.getValue(localObject2));
        }
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      disposeTimer();
      this.downstream.onComplete();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      disposeTimer();
      this.downstream.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      if (this.terminated) {
        return;
      }
      if (fastEnter())
      {
        this.window.onNext(paramT);
        if (leave(-1) != 0) {}
      }
      else
      {
        this.queue.offer(NotificationLite.next(paramT));
        if (!enter()) {
          return;
        }
      }
      drainLoop();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.window = UnicastSubject.create(this.bufferSize);
        paramDisposable = this.downstream;
        paramDisposable.onSubscribe(this);
        paramDisposable.onNext(this.window);
        if (!this.cancelled)
        {
          paramDisposable = this.scheduler;
          long l = this.timespan;
          paramDisposable = paramDisposable.schedulePeriodicallyDirect(this, l, l, this.unit);
          DisposableHelper.replace(this.timer, paramDisposable);
        }
      }
    }
    
    public void run()
    {
      if (this.cancelled)
      {
        this.terminated = true;
        disposeTimer();
      }
      this.queue.offer(NEXT);
      if (enter()) {
        drainLoop();
      }
    }
  }
  
  static final class WindowSkipObserver<T>
    extends QueueDrainObserver<T, Object, Observable<T>>
    implements Disposable, Runnable
  {
    final int bufferSize;
    volatile boolean terminated;
    final long timeskip;
    final long timespan;
    final TimeUnit unit;
    Disposable upstream;
    final List<UnicastSubject<T>> windows;
    final Scheduler.Worker worker;
    
    WindowSkipObserver(Observer<? super Observable<T>> paramObserver, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker, int paramInt)
    {
      super(new MpscLinkedQueue());
      this.timespan = paramLong1;
      this.timeskip = paramLong2;
      this.unit = paramTimeUnit;
      this.worker = paramWorker;
      this.bufferSize = paramInt;
      this.windows = new LinkedList();
    }
    
    void complete(UnicastSubject<T> paramUnicastSubject)
    {
      this.queue.offer(new SubjectWork(paramUnicastSubject, false));
      if (enter()) {
        drainLoop();
      }
    }
    
    public void dispose()
    {
      this.cancelled = true;
    }
    
    void disposeWorker()
    {
      this.worker.dispose();
    }
    
    void drainLoop()
    {
      Object localObject1 = (MpscLinkedQueue)this.queue;
      Object localObject2 = this.downstream;
      List localList = this.windows;
      int i = 1;
      for (;;)
      {
        if (this.terminated)
        {
          this.upstream.dispose();
          disposeWorker();
          ((MpscLinkedQueue)localObject1).clear();
          localList.clear();
          return;
        }
        boolean bool1 = this.done;
        Object localObject3 = ((MpscLinkedQueue)localObject1).poll();
        int j;
        if (localObject3 == null) {
          j = 1;
        } else {
          j = 0;
        }
        boolean bool2 = localObject3 instanceof SubjectWork;
        if ((bool1) && ((j != 0) || (bool2)))
        {
          ((MpscLinkedQueue)localObject1).clear();
          localObject1 = this.error;
          if (localObject1 != null)
          {
            localObject2 = localList.iterator();
            while (((Iterator)localObject2).hasNext()) {
              ((UnicastSubject)((Iterator)localObject2).next()).onError((Throwable)localObject1);
            }
          }
          localObject1 = localList.iterator();
          while (((Iterator)localObject1).hasNext()) {
            ((UnicastSubject)((Iterator)localObject1).next()).onComplete();
          }
          disposeWorker();
          localList.clear();
          return;
        }
        if (j != 0)
        {
          j = leave(-i);
          i = j;
          if (j != 0) {}
        }
        else
        {
          Object localObject4;
          if (bool2)
          {
            localObject4 = (SubjectWork)localObject3;
            if (((SubjectWork)localObject4).open)
            {
              if (!this.cancelled)
              {
                localObject4 = UnicastSubject.create(this.bufferSize);
                localList.add(localObject4);
                ((Observer)localObject2).onNext(localObject4);
                this.worker.schedule(new CompletionTask((UnicastSubject)localObject4), this.timespan, this.unit);
              }
            }
            else
            {
              localList.remove(((SubjectWork)localObject4).w);
              ((SubjectWork)localObject4).w.onComplete();
              if ((localList.isEmpty()) && (this.cancelled)) {
                this.terminated = true;
              }
            }
          }
          else
          {
            localObject4 = localList.iterator();
            while (((Iterator)localObject4).hasNext()) {
              ((UnicastSubject)((Iterator)localObject4).next()).onNext(localObject3);
            }
          }
        }
      }
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void onComplete()
    {
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      this.downstream.onComplete();
      disposeWorker();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      this.downstream.onError(paramThrowable);
      disposeWorker();
    }
    
    public void onNext(T paramT)
    {
      if (fastEnter())
      {
        Iterator localIterator = this.windows.iterator();
        while (localIterator.hasNext()) {
          ((UnicastSubject)localIterator.next()).onNext(paramT);
        }
        if (leave(-1) != 0) {}
      }
      else
      {
        this.queue.offer(paramT);
        if (!enter()) {
          return;
        }
      }
      drainLoop();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
        if (this.cancelled) {
          return;
        }
        paramDisposable = UnicastSubject.create(this.bufferSize);
        this.windows.add(paramDisposable);
        this.downstream.onNext(paramDisposable);
        this.worker.schedule(new CompletionTask(paramDisposable), this.timespan, this.unit);
        paramDisposable = this.worker;
        long l = this.timeskip;
        paramDisposable.schedulePeriodically(this, l, l, this.unit);
      }
    }
    
    public void run()
    {
      SubjectWork localSubjectWork = new SubjectWork(UnicastSubject.create(this.bufferSize), true);
      if (!this.cancelled) {
        this.queue.offer(localSubjectWork);
      }
      if (enter()) {
        drainLoop();
      }
    }
    
    final class CompletionTask
      implements Runnable
    {
      private final UnicastSubject<T> w;
      
      CompletionTask()
      {
        Object localObject;
        this.w = localObject;
      }
      
      public void run()
      {
        ObservableWindowTimed.WindowSkipObserver.this.complete(this.w);
      }
    }
    
    static final class SubjectWork<T>
    {
      final boolean open;
      final UnicastSubject<T> w;
      
      SubjectWork(UnicastSubject<T> paramUnicastSubject, boolean paramBoolean)
      {
        this.w = paramUnicastSubject;
        this.open = paramBoolean;
      }
    }
  }
}
