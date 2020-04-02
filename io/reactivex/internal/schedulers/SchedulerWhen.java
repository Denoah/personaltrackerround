package io.reactivex.internal.schedulers;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Function;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SchedulerWhen
  extends Scheduler
  implements Disposable
{
  static final Disposable DISPOSED = Disposables.disposed();
  static final Disposable SUBSCRIBED = new SubscribedDisposable();
  private final Scheduler actualScheduler;
  private Disposable disposable;
  private final FlowableProcessor<Flowable<Completable>> workerProcessor;
  
  public SchedulerWhen(Function<Flowable<Flowable<Completable>>, Completable> paramFunction, Scheduler paramScheduler)
  {
    this.actualScheduler = paramScheduler;
    paramScheduler = UnicastProcessor.create().toSerialized();
    this.workerProcessor = paramScheduler;
    try
    {
      this.disposable = ((Completable)paramFunction.apply(paramScheduler)).subscribe();
      return;
    }
    finally {}
  }
  
  public Scheduler.Worker createWorker()
  {
    Scheduler.Worker localWorker = this.actualScheduler.createWorker();
    Object localObject = UnicastProcessor.create().toSerialized();
    Flowable localFlowable = ((FlowableProcessor)localObject).map(new CreateWorkerFunction(localWorker));
    localObject = new QueueWorker((FlowableProcessor)localObject, localWorker);
    this.workerProcessor.onNext(localFlowable);
    return localObject;
  }
  
  public void dispose()
  {
    this.disposable.dispose();
  }
  
  public boolean isDisposed()
  {
    return this.disposable.isDisposed();
  }
  
  static final class CreateWorkerFunction
    implements Function<SchedulerWhen.ScheduledAction, Completable>
  {
    final Scheduler.Worker actualWorker;
    
    CreateWorkerFunction(Scheduler.Worker paramWorker)
    {
      this.actualWorker = paramWorker;
    }
    
    public Completable apply(SchedulerWhen.ScheduledAction paramScheduledAction)
    {
      return new WorkerCompletable(paramScheduledAction);
    }
    
    final class WorkerCompletable
      extends Completable
    {
      final SchedulerWhen.ScheduledAction action;
      
      WorkerCompletable(SchedulerWhen.ScheduledAction paramScheduledAction)
      {
        this.action = paramScheduledAction;
      }
      
      protected void subscribeActual(CompletableObserver paramCompletableObserver)
      {
        paramCompletableObserver.onSubscribe(this.action);
        this.action.call(SchedulerWhen.CreateWorkerFunction.this.actualWorker, paramCompletableObserver);
      }
    }
  }
  
  static class DelayedAction
    extends SchedulerWhen.ScheduledAction
  {
    private final Runnable action;
    private final long delayTime;
    private final TimeUnit unit;
    
    DelayedAction(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
    {
      this.action = paramRunnable;
      this.delayTime = paramLong;
      this.unit = paramTimeUnit;
    }
    
    protected Disposable callActual(Scheduler.Worker paramWorker, CompletableObserver paramCompletableObserver)
    {
      return paramWorker.schedule(new SchedulerWhen.OnCompletedAction(this.action, paramCompletableObserver), this.delayTime, this.unit);
    }
  }
  
  static class ImmediateAction
    extends SchedulerWhen.ScheduledAction
  {
    private final Runnable action;
    
    ImmediateAction(Runnable paramRunnable)
    {
      this.action = paramRunnable;
    }
    
    protected Disposable callActual(Scheduler.Worker paramWorker, CompletableObserver paramCompletableObserver)
    {
      return paramWorker.schedule(new SchedulerWhen.OnCompletedAction(this.action, paramCompletableObserver));
    }
  }
  
  static class OnCompletedAction
    implements Runnable
  {
    final Runnable action;
    final CompletableObserver actionCompletable;
    
    OnCompletedAction(Runnable paramRunnable, CompletableObserver paramCompletableObserver)
    {
      this.action = paramRunnable;
      this.actionCompletable = paramCompletableObserver;
    }
    
    public void run()
    {
      try
      {
        this.action.run();
        return;
      }
      finally
      {
        this.actionCompletable.onComplete();
      }
    }
  }
  
  static final class QueueWorker
    extends Scheduler.Worker
  {
    private final FlowableProcessor<SchedulerWhen.ScheduledAction> actionProcessor;
    private final Scheduler.Worker actualWorker;
    private final AtomicBoolean unsubscribed;
    
    QueueWorker(FlowableProcessor<SchedulerWhen.ScheduledAction> paramFlowableProcessor, Scheduler.Worker paramWorker)
    {
      this.actionProcessor = paramFlowableProcessor;
      this.actualWorker = paramWorker;
      this.unsubscribed = new AtomicBoolean();
    }
    
    public void dispose()
    {
      if (this.unsubscribed.compareAndSet(false, true))
      {
        this.actionProcessor.onComplete();
        this.actualWorker.dispose();
      }
    }
    
    public boolean isDisposed()
    {
      return this.unsubscribed.get();
    }
    
    public Disposable schedule(Runnable paramRunnable)
    {
      paramRunnable = new SchedulerWhen.ImmediateAction(paramRunnable);
      this.actionProcessor.onNext(paramRunnable);
      return paramRunnable;
    }
    
    public Disposable schedule(Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit)
    {
      paramRunnable = new SchedulerWhen.DelayedAction(paramRunnable, paramLong, paramTimeUnit);
      this.actionProcessor.onNext(paramRunnable);
      return paramRunnable;
    }
  }
  
  static abstract class ScheduledAction
    extends AtomicReference<Disposable>
    implements Disposable
  {
    ScheduledAction()
    {
      super();
    }
    
    void call(Scheduler.Worker paramWorker, CompletableObserver paramCompletableObserver)
    {
      Disposable localDisposable = (Disposable)get();
      if (localDisposable == SchedulerWhen.DISPOSED) {
        return;
      }
      if (localDisposable != SchedulerWhen.SUBSCRIBED) {
        return;
      }
      paramWorker = callActual(paramWorker, paramCompletableObserver);
      if (!compareAndSet(SchedulerWhen.SUBSCRIBED, paramWorker)) {
        paramWorker.dispose();
      }
    }
    
    protected abstract Disposable callActual(Scheduler.Worker paramWorker, CompletableObserver paramCompletableObserver);
    
    public void dispose()
    {
      Disposable localDisposable1 = SchedulerWhen.DISPOSED;
      Disposable localDisposable2;
      do
      {
        localDisposable2 = (Disposable)get();
        if (localDisposable2 == SchedulerWhen.DISPOSED) {
          return;
        }
      } while (!compareAndSet(localDisposable2, localDisposable1));
      if (localDisposable2 != SchedulerWhen.SUBSCRIBED) {
        localDisposable2.dispose();
      }
    }
    
    public boolean isDisposed()
    {
      return ((Disposable)get()).isDisposed();
    }
  }
  
  static final class SubscribedDisposable
    implements Disposable
  {
    SubscribedDisposable() {}
    
    public void dispose() {}
    
    public boolean isDisposed()
    {
      return false;
    }
  }
}
