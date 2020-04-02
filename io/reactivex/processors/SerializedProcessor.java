package io.reactivex.processors;

import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

final class SerializedProcessor<T>
  extends FlowableProcessor<T>
{
  final FlowableProcessor<T> actual;
  volatile boolean done;
  boolean emitting;
  AppendOnlyLinkedArrayList<Object> queue;
  
  SerializedProcessor(FlowableProcessor<T> paramFlowableProcessor)
  {
    this.actual = paramFlowableProcessor;
  }
  
  void emitLoop()
  {
    for (;;)
    {
      try
      {
        AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList = this.queue;
        if (localAppendOnlyLinkedArrayList == null)
        {
          this.emitting = false;
          return;
        }
        this.queue = null;
        localAppendOnlyLinkedArrayList.accept(this.actual);
      }
      finally {}
    }
  }
  
  public Throwable getThrowable()
  {
    return this.actual.getThrowable();
  }
  
  public boolean hasComplete()
  {
    return this.actual.hasComplete();
  }
  
  public boolean hasSubscribers()
  {
    return this.actual.hasSubscribers();
  }
  
  public boolean hasThrowable()
  {
    return this.actual.hasThrowable();
  }
  
  public void onComplete()
  {
    if (this.done) {
      return;
    }
    try
    {
      if (this.done) {
        return;
      }
      this.done = true;
      if (this.emitting)
      {
        AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList1 = this.queue;
        AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList2 = localAppendOnlyLinkedArrayList1;
        if (localAppendOnlyLinkedArrayList1 == null)
        {
          localAppendOnlyLinkedArrayList2 = new io/reactivex/internal/util/AppendOnlyLinkedArrayList;
          localAppendOnlyLinkedArrayList2.<init>(4);
          this.queue = localAppendOnlyLinkedArrayList2;
        }
        localAppendOnlyLinkedArrayList2.add(NotificationLite.complete());
        return;
      }
      this.emitting = true;
      this.actual.onComplete();
      return;
    }
    finally {}
  }
  
  public void onError(Throwable paramThrowable)
  {
    if (this.done)
    {
      RxJavaPlugins.onError(paramThrowable);
      return;
    }
    try
    {
      boolean bool = this.done;
      int i = 1;
      if (!bool)
      {
        this.done = true;
        if (this.emitting)
        {
          AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList1 = this.queue;
          AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList2 = localAppendOnlyLinkedArrayList1;
          if (localAppendOnlyLinkedArrayList1 == null)
          {
            localAppendOnlyLinkedArrayList2 = new io/reactivex/internal/util/AppendOnlyLinkedArrayList;
            localAppendOnlyLinkedArrayList2.<init>(4);
            this.queue = localAppendOnlyLinkedArrayList2;
          }
          localAppendOnlyLinkedArrayList2.setFirst(NotificationLite.error(paramThrowable));
          return;
        }
        this.emitting = true;
        i = 0;
      }
      if (i != 0)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.actual.onError(paramThrowable);
      return;
    }
    finally {}
  }
  
  public void onNext(T paramT)
  {
    if (this.done) {
      return;
    }
    try
    {
      if (this.done) {
        return;
      }
      if (this.emitting)
      {
        AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList1 = this.queue;
        AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList2 = localAppendOnlyLinkedArrayList1;
        if (localAppendOnlyLinkedArrayList1 == null)
        {
          localAppendOnlyLinkedArrayList2 = new io/reactivex/internal/util/AppendOnlyLinkedArrayList;
          localAppendOnlyLinkedArrayList2.<init>(4);
          this.queue = localAppendOnlyLinkedArrayList2;
        }
        localAppendOnlyLinkedArrayList2.add(NotificationLite.next(paramT));
        return;
      }
      this.emitting = true;
      this.actual.onNext(paramT);
      emitLoop();
      return;
    }
    finally {}
  }
  
  public void onSubscribe(Subscription paramSubscription)
  {
    boolean bool = this.done;
    int i = 1;
    int j = 1;
    if (!bool) {
      try
      {
        if (this.done)
        {
          i = j;
        }
        else
        {
          if (this.emitting)
          {
            AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList1 = this.queue;
            AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList2 = localAppendOnlyLinkedArrayList1;
            if (localAppendOnlyLinkedArrayList1 == null)
            {
              localAppendOnlyLinkedArrayList2 = new io/reactivex/internal/util/AppendOnlyLinkedArrayList;
              localAppendOnlyLinkedArrayList2.<init>(4);
              this.queue = localAppendOnlyLinkedArrayList2;
            }
            localAppendOnlyLinkedArrayList2.add(NotificationLite.subscription(paramSubscription));
            return;
          }
          this.emitting = true;
          i = 0;
        }
      }
      finally {}
    }
    if (i != 0)
    {
      paramSubscription.cancel();
    }
    else
    {
      this.actual.onSubscribe(paramSubscription);
      emitLoop();
    }
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    this.actual.subscribe(paramSubscriber);
  }
}
