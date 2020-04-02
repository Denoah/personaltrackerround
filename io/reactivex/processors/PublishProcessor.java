package io.reactivex.processors;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class PublishProcessor<T>
  extends FlowableProcessor<T>
{
  static final PublishSubscription[] EMPTY = new PublishSubscription[0];
  static final PublishSubscription[] TERMINATED = new PublishSubscription[0];
  Throwable error;
  final AtomicReference<PublishSubscription<T>[]> subscribers = new AtomicReference(EMPTY);
  
  PublishProcessor() {}
  
  @CheckReturnValue
  public static <T> PublishProcessor<T> create()
  {
    return new PublishProcessor();
  }
  
  boolean add(PublishSubscription<T> paramPublishSubscription)
  {
    PublishSubscription[] arrayOfPublishSubscription1;
    PublishSubscription[] arrayOfPublishSubscription2;
    do
    {
      arrayOfPublishSubscription1 = (PublishSubscription[])this.subscribers.get();
      if (arrayOfPublishSubscription1 == TERMINATED) {
        return false;
      }
      int i = arrayOfPublishSubscription1.length;
      arrayOfPublishSubscription2 = new PublishSubscription[i + 1];
      System.arraycopy(arrayOfPublishSubscription1, 0, arrayOfPublishSubscription2, 0, i);
      arrayOfPublishSubscription2[i] = paramPublishSubscription;
    } while (!this.subscribers.compareAndSet(arrayOfPublishSubscription1, arrayOfPublishSubscription2));
    return true;
  }
  
  public Throwable getThrowable()
  {
    if (this.subscribers.get() == TERMINATED) {
      return this.error;
    }
    return null;
  }
  
  public boolean hasComplete()
  {
    boolean bool;
    if ((this.subscribers.get() == TERMINATED) && (this.error == null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasSubscribers()
  {
    boolean bool;
    if (((PublishSubscription[])this.subscribers.get()).length != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean hasThrowable()
  {
    boolean bool;
    if ((this.subscribers.get() == TERMINATED) && (this.error != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean offer(T paramT)
  {
    if (paramT == null)
    {
      onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
      return true;
    }
    PublishSubscription[] arrayOfPublishSubscription = (PublishSubscription[])this.subscribers.get();
    int i = arrayOfPublishSubscription.length;
    int j = 0;
    for (int k = 0; k < i; k++) {
      if (arrayOfPublishSubscription[k].isFull()) {
        return false;
      }
    }
    i = arrayOfPublishSubscription.length;
    for (k = j; k < i; k++) {
      arrayOfPublishSubscription[k].onNext(paramT);
    }
    return true;
  }
  
  public void onComplete()
  {
    Object localObject = this.subscribers.get();
    PublishSubscription[] arrayOfPublishSubscription = TERMINATED;
    if (localObject == arrayOfPublishSubscription) {
      return;
    }
    localObject = (PublishSubscription[])this.subscribers.getAndSet(arrayOfPublishSubscription);
    int i = localObject.length;
    for (int j = 0; j < i; j++) {
      localObject[j].onComplete();
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
    Object localObject = this.subscribers.get();
    PublishSubscription[] arrayOfPublishSubscription = TERMINATED;
    if (localObject == arrayOfPublishSubscription)
    {
      RxJavaPlugins.onError(paramThrowable);
      return;
    }
    this.error = paramThrowable;
    arrayOfPublishSubscription = (PublishSubscription[])this.subscribers.getAndSet(arrayOfPublishSubscription);
    int i = arrayOfPublishSubscription.length;
    for (int j = 0; j < i; j++) {
      arrayOfPublishSubscription[j].onError(paramThrowable);
    }
  }
  
  public void onNext(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
    PublishSubscription[] arrayOfPublishSubscription = (PublishSubscription[])this.subscribers.get();
    int i = arrayOfPublishSubscription.length;
    for (int j = 0; j < i; j++) {
      arrayOfPublishSubscription[j].onNext(paramT);
    }
  }
  
  public void onSubscribe(Subscription paramSubscription)
  {
    if (this.subscribers.get() == TERMINATED)
    {
      paramSubscription.cancel();
      return;
    }
    paramSubscription.request(Long.MAX_VALUE);
  }
  
  void remove(PublishSubscription<T> paramPublishSubscription)
  {
    PublishSubscription[] arrayOfPublishSubscription1;
    PublishSubscription[] arrayOfPublishSubscription2;
    do
    {
      arrayOfPublishSubscription1 = (PublishSubscription[])this.subscribers.get();
      if ((arrayOfPublishSubscription1 == TERMINATED) || (arrayOfPublishSubscription1 == EMPTY)) {
        break;
      }
      int i = arrayOfPublishSubscription1.length;
      int j = -1;
      int m;
      for (int k = 0;; k++)
      {
        m = j;
        if (k >= i) {
          break;
        }
        if (arrayOfPublishSubscription1[k] == paramPublishSubscription)
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
        arrayOfPublishSubscription2 = EMPTY;
      }
      else
      {
        arrayOfPublishSubscription2 = new PublishSubscription[i - 1];
        System.arraycopy(arrayOfPublishSubscription1, 0, arrayOfPublishSubscription2, 0, m);
        System.arraycopy(arrayOfPublishSubscription1, m + 1, arrayOfPublishSubscription2, m, i - m - 1);
      }
    } while (!this.subscribers.compareAndSet(arrayOfPublishSubscription1, arrayOfPublishSubscription2));
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    Object localObject = new PublishSubscription(paramSubscriber, this);
    paramSubscriber.onSubscribe((Subscription)localObject);
    if (add((PublishSubscription)localObject))
    {
      if (((PublishSubscription)localObject).isCancelled()) {
        remove((PublishSubscription)localObject);
      }
    }
    else
    {
      localObject = this.error;
      if (localObject != null) {
        paramSubscriber.onError((Throwable)localObject);
      } else {
        paramSubscriber.onComplete();
      }
    }
  }
  
  static final class PublishSubscription<T>
    extends AtomicLong
    implements Subscription
  {
    private static final long serialVersionUID = 3562861878281475070L;
    final Subscriber<? super T> downstream;
    final PublishProcessor<T> parent;
    
    PublishSubscription(Subscriber<? super T> paramSubscriber, PublishProcessor<T> paramPublishProcessor)
    {
      this.downstream = paramSubscriber;
      this.parent = paramPublishProcessor;
    }
    
    public void cancel()
    {
      if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
        this.parent.remove(this);
      }
    }
    
    public boolean isCancelled()
    {
      boolean bool;
      if (get() == Long.MIN_VALUE) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    boolean isFull()
    {
      boolean bool;
      if (get() == 0L) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public void onComplete()
    {
      if (get() != Long.MIN_VALUE) {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (get() != Long.MIN_VALUE) {
        this.downstream.onError(paramThrowable);
      } else {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      long l = get();
      if (l == Long.MIN_VALUE) {
        return;
      }
      if (l != 0L)
      {
        this.downstream.onNext(paramT);
        BackpressureHelper.producedCancel(this, 1L);
      }
      else
      {
        cancel();
        this.downstream.onError(new MissingBackpressureException("Could not emit value due to lack of requests"));
      }
    }
    
    public void request(long paramLong)
    {
      if (SubscriptionHelper.validate(paramLong)) {
        BackpressureHelper.addCancel(this, paramLong);
      }
    }
  }
}
