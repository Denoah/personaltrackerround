package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.SequentialDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.processors.UnicastProcessor;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableWindowTimed<T>
  extends AbstractFlowableWithUpstream<T, Flowable<T>>
{
  final int bufferSize;
  final long maxSize;
  final boolean restartTimerOnMaxSize;
  final Scheduler scheduler;
  final long timeskip;
  final long timespan;
  final TimeUnit unit;
  
  public FlowableWindowTimed(Flowable<T> paramFlowable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler paramScheduler, long paramLong3, int paramInt, boolean paramBoolean)
  {
    super(paramFlowable);
    this.timespan = paramLong1;
    this.timeskip = paramLong2;
    this.unit = paramTimeUnit;
    this.scheduler = paramScheduler;
    this.maxSize = paramLong3;
    this.bufferSize = paramInt;
    this.restartTimerOnMaxSize = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super Flowable<T>> paramSubscriber)
  {
    paramSubscriber = new SerializedSubscriber(paramSubscriber);
    if (this.timespan == this.timeskip)
    {
      if (this.maxSize == Long.MAX_VALUE)
      {
        this.source.subscribe(new WindowExactUnboundedSubscriber(paramSubscriber, this.timespan, this.unit, this.scheduler, this.bufferSize));
        return;
      }
      this.source.subscribe(new WindowExactBoundedSubscriber(paramSubscriber, this.timespan, this.unit, this.scheduler, this.bufferSize, this.maxSize, this.restartTimerOnMaxSize));
      return;
    }
    this.source.subscribe(new WindowSkipSubscriber(paramSubscriber, this.timespan, this.timeskip, this.unit, this.scheduler.createWorker(), this.bufferSize));
  }
  
  static final class WindowExactBoundedSubscriber<T>
    extends QueueDrainSubscriber<T, Object, Flowable<T>>
    implements Subscription
  {
    final int bufferSize;
    long count;
    final long maxSize;
    long producerIndex;
    final boolean restartTimerOnMaxSize;
    final Scheduler scheduler;
    volatile boolean terminated;
    final SequentialDisposable timer = new SequentialDisposable();
    final long timespan;
    final TimeUnit unit;
    Subscription upstream;
    UnicastProcessor<T> window;
    final Scheduler.Worker worker;
    
    WindowExactBoundedSubscriber(Subscriber<? super Flowable<T>> paramSubscriber, long paramLong1, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt, long paramLong2, boolean paramBoolean)
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
    
    public void cancel()
    {
      this.cancelled = true;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.timer);
      Scheduler.Worker localWorker = this.worker;
      if (localWorker != null) {
        localWorker.dispose();
      }
    }
    
    void drainLoop()
    {
      SimplePlainQueue localSimplePlainQueue = this.queue;
      Subscriber localSubscriber = this.downstream;
      Object localObject1 = this.window;
      int i = 1;
      Object localObject2;
      int j;
      boolean bool2;
      do
      {
        if (this.terminated)
        {
          this.upstream.cancel();
          localSimplePlainQueue.clear();
          dispose();
          return;
        }
        boolean bool1 = this.done;
        localObject2 = localSimplePlainQueue.poll();
        if (localObject2 == null) {
          j = 1;
        } else {
          j = 0;
        }
        bool2 = localObject2 instanceof ConsumerIndexHolder;
        if ((bool1) && ((j != 0) || (bool2)))
        {
          this.window = null;
          localSimplePlainQueue.clear();
          localObject2 = this.error;
          if (localObject2 != null) {
            ((UnicastProcessor)localObject1).onError((Throwable)localObject2);
          } else {
            ((UnicastProcessor)localObject1).onComplete();
          }
          dispose();
          return;
        }
        if (j == 0) {
          break;
        }
        j = leave(-i);
        i = j;
      } while (j != 0);
      return;
      Object localObject3;
      if (bool2)
      {
        localObject3 = (ConsumerIndexHolder)localObject2;
        if (!this.restartTimerOnMaxSize)
        {
          localObject2 = localObject1;
          if (this.producerIndex == ((ConsumerIndexHolder)localObject3).index) {}
        }
      }
      for (;;)
      {
        localObject1 = localObject2;
        break;
        ((UnicastProcessor)localObject1).onComplete();
        this.count = 0L;
        localObject1 = UnicastProcessor.create(this.bufferSize);
        this.window = ((UnicastProcessor)localObject1);
        long l = requested();
        if (l != 0L)
        {
          localSubscriber.onNext(localObject1);
          localObject2 = localObject1;
          if (l != Long.MAX_VALUE)
          {
            produced(1L);
            localObject2 = localObject1;
          }
        }
        else
        {
          this.window = null;
          this.queue.clear();
          this.upstream.cancel();
          localSubscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
          dispose();
          return;
          ((UnicastProcessor)localObject1).onNext(NotificationLite.getValue(localObject2));
          l = this.count + 1L;
          if (l >= this.maxSize)
          {
            this.producerIndex += 1L;
            this.count = 0L;
            ((UnicastProcessor)localObject1).onComplete();
            l = requested();
            if (l != 0L)
            {
              localObject2 = UnicastProcessor.create(this.bufferSize);
              this.window = ((UnicastProcessor)localObject2);
              this.downstream.onNext(localObject2);
              if (l != Long.MAX_VALUE) {
                produced(1L);
              }
              if (this.restartTimerOnMaxSize)
              {
                ((Disposable)this.timer.get()).dispose();
                localObject3 = this.worker;
                localObject1 = new ConsumerIndexHolder(this.producerIndex, this);
                l = this.timespan;
                localObject1 = ((Scheduler.Worker)localObject3).schedulePeriodically((Runnable)localObject1, l, l, this.unit);
                this.timer.replace((Disposable)localObject1);
              }
            }
            else
            {
              this.window = null;
              this.upstream.cancel();
              this.downstream.onError(new MissingBackpressureException("Could not deliver window due to lack of requests"));
              dispose();
            }
          }
          else
          {
            this.count = l;
            localObject2 = localObject1;
          }
        }
      }
    }
    
    public void onComplete()
    {
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      this.downstream.onComplete();
      dispose();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      this.downstream.onError(paramThrowable);
      dispose();
    }
    
    public void onNext(T paramT)
    {
      if (this.terminated) {
        return;
      }
      if (fastEnter())
      {
        Object localObject = this.window;
        ((UnicastProcessor)localObject).onNext(paramT);
        long l = this.count + 1L;
        if (l >= this.maxSize)
        {
          this.producerIndex += 1L;
          this.count = 0L;
          ((UnicastProcessor)localObject).onComplete();
          l = requested();
          if (l != 0L)
          {
            paramT = UnicastProcessor.create(this.bufferSize);
            this.window = paramT;
            this.downstream.onNext(paramT);
            if (l != Long.MAX_VALUE) {
              produced(1L);
            }
            if (this.restartTimerOnMaxSize)
            {
              ((Disposable)this.timer.get()).dispose();
              localObject = this.worker;
              paramT = new ConsumerIndexHolder(this.producerIndex, this);
              l = this.timespan;
              paramT = ((Scheduler.Worker)localObject).schedulePeriodically(paramT, l, l, this.unit);
              this.timer.replace(paramT);
            }
          }
          else
          {
            this.window = null;
            this.upstream.cancel();
            this.downstream.onError(new MissingBackpressureException("Could not deliver window due to lack of requests"));
            dispose();
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
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        Object localObject1 = this.downstream;
        ((Subscriber)localObject1).onSubscribe(this);
        if (this.cancelled) {
          return;
        }
        Object localObject2 = UnicastProcessor.create(this.bufferSize);
        this.window = ((UnicastProcessor)localObject2);
        long l = requested();
        if (l != 0L)
        {
          ((Subscriber)localObject1).onNext(localObject2);
          if (l != Long.MAX_VALUE) {
            produced(1L);
          }
          localObject1 = new ConsumerIndexHolder(this.producerIndex, this);
          if (this.restartTimerOnMaxSize)
          {
            localObject2 = this.worker;
            l = this.timespan;
            localObject1 = ((Scheduler.Worker)localObject2).schedulePeriodically((Runnable)localObject1, l, l, this.unit);
          }
          else
          {
            localObject2 = this.scheduler;
            l = this.timespan;
            localObject1 = ((Scheduler)localObject2).schedulePeriodicallyDirect((Runnable)localObject1, l, l, this.unit);
          }
          if (this.timer.replace((Disposable)localObject1)) {
            paramSubscription.request(Long.MAX_VALUE);
          }
        }
        else
        {
          this.cancelled = true;
          paramSubscription.cancel();
          ((Subscriber)localObject1).onError(new MissingBackpressureException("Could not deliver initial window due to lack of requests."));
        }
      }
    }
    
    public void request(long paramLong)
    {
      requested(paramLong);
    }
    
    static final class ConsumerIndexHolder
      implements Runnable
    {
      final long index;
      final FlowableWindowTimed.WindowExactBoundedSubscriber<?> parent;
      
      ConsumerIndexHolder(long paramLong, FlowableWindowTimed.WindowExactBoundedSubscriber<?> paramWindowExactBoundedSubscriber)
      {
        this.index = paramLong;
        this.parent = paramWindowExactBoundedSubscriber;
      }
      
      public void run()
      {
        FlowableWindowTimed.WindowExactBoundedSubscriber localWindowExactBoundedSubscriber = this.parent;
        if (!localWindowExactBoundedSubscriber.cancelled)
        {
          localWindowExactBoundedSubscriber.queue.offer(this);
        }
        else
        {
          localWindowExactBoundedSubscriber.terminated = true;
          localWindowExactBoundedSubscriber.dispose();
        }
        if (localWindowExactBoundedSubscriber.enter()) {
          localWindowExactBoundedSubscriber.drainLoop();
        }
      }
    }
  }
  
  static final class WindowExactUnboundedSubscriber<T>
    extends QueueDrainSubscriber<T, Object, Flowable<T>>
    implements FlowableSubscriber<T>, Subscription, Runnable
  {
    static final Object NEXT = new Object();
    final int bufferSize;
    final Scheduler scheduler;
    volatile boolean terminated;
    final SequentialDisposable timer = new SequentialDisposable();
    final long timespan;
    final TimeUnit unit;
    Subscription upstream;
    UnicastProcessor<T> window;
    
    WindowExactUnboundedSubscriber(Subscriber<? super Flowable<T>> paramSubscriber, long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler, int paramInt)
    {
      super(new MpscLinkedQueue());
      this.timespan = paramLong;
      this.unit = paramTimeUnit;
      this.scheduler = paramScheduler;
      this.bufferSize = paramInt;
    }
    
    public void cancel()
    {
      this.cancelled = true;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.timer);
    }
    
    void drainLoop()
    {
      SimplePlainQueue localSimplePlainQueue = this.queue;
      Subscriber localSubscriber = this.downstream;
      Object localObject1 = this.window;
      int i = 1;
      for (;;)
      {
        boolean bool1 = this.terminated;
        boolean bool2 = this.done;
        Object localObject2 = localSimplePlainQueue.poll();
        if ((bool2) && ((localObject2 == null) || (localObject2 == NEXT)))
        {
          this.window = null;
          localSimplePlainQueue.clear();
          dispose();
          localObject2 = this.error;
          if (localObject2 != null) {
            ((UnicastProcessor)localObject1).onError((Throwable)localObject2);
          } else {
            ((UnicastProcessor)localObject1).onComplete();
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
          ((UnicastProcessor)localObject1).onComplete();
          if (!bool1)
          {
            localObject2 = UnicastProcessor.create(this.bufferSize);
            this.window = ((UnicastProcessor)localObject2);
            long l = requested();
            if (l != 0L)
            {
              localSubscriber.onNext(localObject2);
              localObject1 = localObject2;
              if (l != Long.MAX_VALUE)
              {
                produced(1L);
                localObject1 = localObject2;
              }
            }
            else
            {
              this.window = null;
              this.queue.clear();
              this.upstream.cancel();
              dispose();
              localSubscriber.onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
            }
          }
          else
          {
            this.upstream.cancel();
          }
        }
        else
        {
          ((UnicastProcessor)localObject1).onNext(NotificationLite.getValue(localObject2));
        }
      }
    }
    
    public void onComplete()
    {
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      this.downstream.onComplete();
      dispose();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      this.downstream.onError(paramThrowable);
      dispose();
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
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.window = UnicastProcessor.create(this.bufferSize);
        Object localObject = this.downstream;
        ((Subscriber)localObject).onSubscribe(this);
        long l = requested();
        if (l != 0L)
        {
          ((Subscriber)localObject).onNext(this.window);
          if (l != Long.MAX_VALUE) {
            produced(1L);
          }
          if (!this.cancelled)
          {
            localObject = this.timer;
            Scheduler localScheduler = this.scheduler;
            l = this.timespan;
            if (((SequentialDisposable)localObject).replace(localScheduler.schedulePeriodicallyDirect(this, l, l, this.unit))) {
              paramSubscription.request(Long.MAX_VALUE);
            }
          }
        }
        else
        {
          this.cancelled = true;
          paramSubscription.cancel();
          ((Subscriber)localObject).onError(new MissingBackpressureException("Could not deliver first window due to lack of requests."));
        }
      }
    }
    
    public void request(long paramLong)
    {
      requested(paramLong);
    }
    
    public void run()
    {
      if (this.cancelled)
      {
        this.terminated = true;
        dispose();
      }
      this.queue.offer(NEXT);
      if (enter()) {
        drainLoop();
      }
    }
  }
  
  static final class WindowSkipSubscriber<T>
    extends QueueDrainSubscriber<T, Object, Flowable<T>>
    implements Subscription, Runnable
  {
    final int bufferSize;
    volatile boolean terminated;
    final long timeskip;
    final long timespan;
    final TimeUnit unit;
    Subscription upstream;
    final List<UnicastProcessor<T>> windows;
    final Scheduler.Worker worker;
    
    WindowSkipSubscriber(Subscriber<? super Flowable<T>> paramSubscriber, long paramLong1, long paramLong2, TimeUnit paramTimeUnit, Scheduler.Worker paramWorker, int paramInt)
    {
      super(new MpscLinkedQueue());
      this.timespan = paramLong1;
      this.timeskip = paramLong2;
      this.unit = paramTimeUnit;
      this.worker = paramWorker;
      this.bufferSize = paramInt;
      this.windows = new LinkedList();
    }
    
    public void cancel()
    {
      this.cancelled = true;
    }
    
    void complete(UnicastProcessor<T> paramUnicastProcessor)
    {
      this.queue.offer(new SubjectWork(paramUnicastProcessor, false));
      if (enter()) {
        drainLoop();
      }
    }
    
    public void dispose()
    {
      this.worker.dispose();
    }
    
    void drainLoop()
    {
      Object localObject1 = this.queue;
      Object localObject2 = this.downstream;
      List localList = this.windows;
      int i = 1;
      for (;;)
      {
        if (this.terminated)
        {
          this.upstream.cancel();
          dispose();
          ((SimplePlainQueue)localObject1).clear();
          localList.clear();
          return;
        }
        boolean bool1 = this.done;
        Object localObject3 = ((SimplePlainQueue)localObject1).poll();
        int j;
        if (localObject3 == null) {
          j = 1;
        } else {
          j = 0;
        }
        boolean bool2 = localObject3 instanceof SubjectWork;
        if ((bool1) && ((j != 0) || (bool2)))
        {
          ((SimplePlainQueue)localObject1).clear();
          localObject2 = this.error;
          if (localObject2 != null)
          {
            localObject1 = localList.iterator();
            while (((Iterator)localObject1).hasNext()) {
              ((UnicastProcessor)((Iterator)localObject1).next()).onError((Throwable)localObject2);
            }
          }
          localObject1 = localList.iterator();
          while (((Iterator)localObject1).hasNext()) {
            ((UnicastProcessor)((Iterator)localObject1).next()).onComplete();
          }
          localList.clear();
          dispose();
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
          localObject3 = (SubjectWork)localObject3;
          if (((SubjectWork)localObject3).open)
          {
            if (!this.cancelled)
            {
              long l = requested();
              if (l != 0L)
              {
                localObject3 = UnicastProcessor.create(this.bufferSize);
                localList.add(localObject3);
                ((Subscriber)localObject2).onNext(localObject3);
                if (l != Long.MAX_VALUE) {
                  produced(1L);
                }
                this.worker.schedule(new Completion((UnicastProcessor)localObject3), this.timespan, this.unit);
              }
              else
              {
                ((Subscriber)localObject2).onError(new MissingBackpressureException("Can't emit window due to lack of requests"));
              }
            }
          }
          else
          {
            localList.remove(((SubjectWork)localObject3).w);
            ((SubjectWork)localObject3).w.onComplete();
            if ((localList.isEmpty()) && (this.cancelled)) {
              this.terminated = true;
            }
          }
        }
        else
        {
          Iterator localIterator = localList.iterator();
          while (localIterator.hasNext()) {
            ((UnicastProcessor)localIterator.next()).onNext(localObject3);
          }
        }
      }
    }
    
    public void onComplete()
    {
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      this.downstream.onComplete();
      dispose();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      if (enter()) {
        drainLoop();
      }
      this.downstream.onError(paramThrowable);
      dispose();
    }
    
    public void onNext(T paramT)
    {
      if (fastEnter())
      {
        Iterator localIterator = this.windows.iterator();
        while (localIterator.hasNext()) {
          ((UnicastProcessor)localIterator.next()).onNext(paramT);
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
    
    public void onSubscribe(Subscription paramSubscription)
    {
      if (SubscriptionHelper.validate(this.upstream, paramSubscription))
      {
        this.upstream = paramSubscription;
        this.downstream.onSubscribe(this);
        if (this.cancelled) {
          return;
        }
        long l = requested();
        if (l != 0L)
        {
          Object localObject = UnicastProcessor.create(this.bufferSize);
          this.windows.add(localObject);
          this.downstream.onNext(localObject);
          if (l != Long.MAX_VALUE) {
            produced(1L);
          }
          this.worker.schedule(new Completion((UnicastProcessor)localObject), this.timespan, this.unit);
          localObject = this.worker;
          l = this.timeskip;
          ((Scheduler.Worker)localObject).schedulePeriodically(this, l, l, this.unit);
          paramSubscription.request(Long.MAX_VALUE);
        }
        else
        {
          paramSubscription.cancel();
          this.downstream.onError(new MissingBackpressureException("Could not emit the first window due to lack of requests"));
        }
      }
    }
    
    public void request(long paramLong)
    {
      requested(paramLong);
    }
    
    public void run()
    {
      SubjectWork localSubjectWork = new SubjectWork(UnicastProcessor.create(this.bufferSize), true);
      if (!this.cancelled) {
        this.queue.offer(localSubjectWork);
      }
      if (enter()) {
        drainLoop();
      }
    }
    
    final class Completion
      implements Runnable
    {
      private final UnicastProcessor<T> processor;
      
      Completion()
      {
        Object localObject;
        this.processor = localObject;
      }
      
      public void run()
      {
        FlowableWindowTimed.WindowSkipSubscriber.this.complete(this.processor);
      }
    }
    
    static final class SubjectWork<T>
    {
      final boolean open;
      final UnicastProcessor<T> w;
      
      SubjectWork(UnicastProcessor<T> paramUnicastProcessor, boolean paramBoolean)
      {
        this.w = paramUnicastProcessor;
        this.open = paramBoolean;
      }
    }
  }
}
