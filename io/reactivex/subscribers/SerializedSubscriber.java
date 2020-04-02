package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class SerializedSubscriber<T>
  implements FlowableSubscriber<T>, Subscription
{
  static final int QUEUE_LINK_SIZE = 4;
  final boolean delayError;
  volatile boolean done;
  final Subscriber<? super T> downstream;
  boolean emitting;
  AppendOnlyLinkedArrayList<Object> queue;
  Subscription upstream;
  
  public SerializedSubscriber(Subscriber<? super T> paramSubscriber)
  {
    this(paramSubscriber, false);
  }
  
  public SerializedSubscriber(Subscriber<? super T> paramSubscriber, boolean paramBoolean)
  {
    this.downstream = paramSubscriber;
    this.delayError = paramBoolean;
  }
  
  public void cancel()
  {
    this.upstream.cancel();
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
        if (!localAppendOnlyLinkedArrayList.accept(this.downstream)) {
          continue;
        }
        return;
      }
      finally {}
    }
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
      this.done = true;
      this.emitting = true;
      this.downstream.onComplete();
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
        if (this.emitting)
        {
          this.done = true;
          AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList1 = this.queue;
          AppendOnlyLinkedArrayList localAppendOnlyLinkedArrayList2 = localAppendOnlyLinkedArrayList1;
          if (localAppendOnlyLinkedArrayList1 == null)
          {
            localAppendOnlyLinkedArrayList2 = new io/reactivex/internal/util/AppendOnlyLinkedArrayList;
            localAppendOnlyLinkedArrayList2.<init>(4);
            this.queue = localAppendOnlyLinkedArrayList2;
          }
          paramThrowable = NotificationLite.error(paramThrowable);
          if (this.delayError) {
            localAppendOnlyLinkedArrayList2.add(paramThrowable);
          } else {
            localAppendOnlyLinkedArrayList2.setFirst(paramThrowable);
          }
          return;
        }
        this.done = true;
        this.emitting = true;
        i = 0;
      }
      if (i != 0)
      {
        RxJavaPlugins.onError(paramThrowable);
        return;
      }
      this.downstream.onError(paramThrowable);
      return;
    }
    finally {}
  }
  
  public void onNext(T paramT)
  {
    if (this.done) {
      return;
    }
    if (paramT == null)
    {
      this.upstream.cancel();
      onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
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
      this.downstream.onNext(paramT);
      emitLoop();
      return;
    }
    finally {}
  }
  
  public void onSubscribe(Subscription paramSubscription)
  {
    if (SubscriptionHelper.validate(this.upstream, paramSubscription))
    {
      this.upstream = paramSubscription;
      this.downstream.onSubscribe(this);
    }
  }
  
  public void request(long paramLong)
  {
    this.upstream.request(paramLong);
  }
}
