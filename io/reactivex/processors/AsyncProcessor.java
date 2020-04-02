package io.reactivex.processors;

import io.reactivex.annotations.CheckReturnValue;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.subscriptions.DeferredScalarSubscription;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class AsyncProcessor<T>
  extends FlowableProcessor<T>
{
  static final AsyncSubscription[] EMPTY = new AsyncSubscription[0];
  static final AsyncSubscription[] TERMINATED = new AsyncSubscription[0];
  Throwable error;
  final AtomicReference<AsyncSubscription<T>[]> subscribers = new AtomicReference(EMPTY);
  T value;
  
  AsyncProcessor() {}
  
  @CheckReturnValue
  public static <T> AsyncProcessor<T> create()
  {
    return new AsyncProcessor();
  }
  
  boolean add(AsyncSubscription<T> paramAsyncSubscription)
  {
    AsyncSubscription[] arrayOfAsyncSubscription1;
    AsyncSubscription[] arrayOfAsyncSubscription2;
    do
    {
      arrayOfAsyncSubscription1 = (AsyncSubscription[])this.subscribers.get();
      if (arrayOfAsyncSubscription1 == TERMINATED) {
        return false;
      }
      int i = arrayOfAsyncSubscription1.length;
      arrayOfAsyncSubscription2 = new AsyncSubscription[i + 1];
      System.arraycopy(arrayOfAsyncSubscription1, 0, arrayOfAsyncSubscription2, 0, i);
      arrayOfAsyncSubscription2[i] = paramAsyncSubscription;
    } while (!this.subscribers.compareAndSet(arrayOfAsyncSubscription1, arrayOfAsyncSubscription2));
    return true;
  }
  
  public Throwable getThrowable()
  {
    Throwable localThrowable;
    if (this.subscribers.get() == TERMINATED) {
      localThrowable = this.error;
    } else {
      localThrowable = null;
    }
    return localThrowable;
  }
  
  public T getValue()
  {
    Object localObject;
    if (this.subscribers.get() == TERMINATED) {
      localObject = this.value;
    } else {
      localObject = null;
    }
    return localObject;
  }
  
  @Deprecated
  public Object[] getValues()
  {
    Object localObject = getValue();
    Object[] arrayOfObject;
    if (localObject != null)
    {
      arrayOfObject = new Object[1];
      arrayOfObject[0] = localObject;
    }
    else
    {
      arrayOfObject = new Object[0];
    }
    return arrayOfObject;
  }
  
  @Deprecated
  public T[] getValues(T[] paramArrayOfT)
  {
    Object localObject1 = getValue();
    if (localObject1 == null)
    {
      if (paramArrayOfT.length != 0) {
        paramArrayOfT[0] = null;
      }
      return paramArrayOfT;
    }
    Object localObject2 = paramArrayOfT;
    if (paramArrayOfT.length == 0) {
      localObject2 = Arrays.copyOf(paramArrayOfT, 1);
    }
    localObject2[0] = localObject1;
    if (localObject2.length != 1) {
      localObject2[1] = null;
    }
    return localObject2;
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
    if (((AsyncSubscription[])this.subscribers.get()).length != 0) {
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
  
  public boolean hasValue()
  {
    boolean bool;
    if ((this.subscribers.get() == TERMINATED) && (this.value != null)) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public void onComplete()
  {
    Object localObject = this.subscribers.get();
    AsyncSubscription[] arrayOfAsyncSubscription = TERMINATED;
    if (localObject == arrayOfAsyncSubscription) {
      return;
    }
    localObject = this.value;
    arrayOfAsyncSubscription = (AsyncSubscription[])this.subscribers.getAndSet(arrayOfAsyncSubscription);
    int i = 0;
    int j = 0;
    if (localObject == null)
    {
      i = arrayOfAsyncSubscription.length;
      while (j < i)
      {
        arrayOfAsyncSubscription[j].onComplete();
        j++;
      }
    }
    int k = arrayOfAsyncSubscription.length;
    for (j = i; j < k; j++) {
      arrayOfAsyncSubscription[j].complete(localObject);
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
    Object localObject = this.subscribers.get();
    AsyncSubscription[] arrayOfAsyncSubscription = TERMINATED;
    if (localObject == arrayOfAsyncSubscription)
    {
      RxJavaPlugins.onError(paramThrowable);
      return;
    }
    this.value = null;
    this.error = paramThrowable;
    localObject = (AsyncSubscription[])this.subscribers.getAndSet(arrayOfAsyncSubscription);
    int i = localObject.length;
    for (int j = 0; j < i; j++) {
      localObject[j].onError(paramThrowable);
    }
  }
  
  public void onNext(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
    if (this.subscribers.get() == TERMINATED) {
      return;
    }
    this.value = paramT;
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
  
  void remove(AsyncSubscription<T> paramAsyncSubscription)
  {
    AsyncSubscription[] arrayOfAsyncSubscription1;
    AsyncSubscription[] arrayOfAsyncSubscription2;
    do
    {
      arrayOfAsyncSubscription1 = (AsyncSubscription[])this.subscribers.get();
      int i = arrayOfAsyncSubscription1.length;
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
        if (arrayOfAsyncSubscription1[k] == paramAsyncSubscription)
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
        arrayOfAsyncSubscription2 = EMPTY;
      }
      else
      {
        arrayOfAsyncSubscription2 = new AsyncSubscription[i - 1];
        System.arraycopy(arrayOfAsyncSubscription1, 0, arrayOfAsyncSubscription2, 0, m);
        System.arraycopy(arrayOfAsyncSubscription1, m + 1, arrayOfAsyncSubscription2, m, i - m - 1);
      }
    } while (!this.subscribers.compareAndSet(arrayOfAsyncSubscription1, arrayOfAsyncSubscription2));
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    AsyncSubscription localAsyncSubscription = new AsyncSubscription(paramSubscriber, this);
    paramSubscriber.onSubscribe(localAsyncSubscription);
    if (add(localAsyncSubscription))
    {
      if (localAsyncSubscription.isCancelled()) {
        remove(localAsyncSubscription);
      }
    }
    else
    {
      Throwable localThrowable = this.error;
      if (localThrowable != null)
      {
        paramSubscriber.onError(localThrowable);
      }
      else
      {
        paramSubscriber = this.value;
        if (paramSubscriber != null) {
          localAsyncSubscription.complete(paramSubscriber);
        } else {
          localAsyncSubscription.onComplete();
        }
      }
    }
  }
  
  static final class AsyncSubscription<T>
    extends DeferredScalarSubscription<T>
  {
    private static final long serialVersionUID = 5629876084736248016L;
    final AsyncProcessor<T> parent;
    
    AsyncSubscription(Subscriber<? super T> paramSubscriber, AsyncProcessor<T> paramAsyncProcessor)
    {
      super();
      this.parent = paramAsyncProcessor;
    }
    
    public void cancel()
    {
      if (super.tryCancel()) {
        this.parent.remove(this);
      }
    }
    
    void onComplete()
    {
      if (!isCancelled()) {
        this.downstream.onComplete();
      }
    }
    
    void onError(Throwable paramThrowable)
    {
      if (isCancelled()) {
        RxJavaPlugins.onError(paramThrowable);
      } else {
        this.downstream.onError(paramThrowable);
      }
    }
  }
}
