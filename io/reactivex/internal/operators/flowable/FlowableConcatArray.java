package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.internal.subscriptions.SubscriptionArbiter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableConcatArray<T>
  extends Flowable<T>
{
  final boolean delayError;
  final Publisher<? extends T>[] sources;
  
  public FlowableConcatArray(Publisher<? extends T>[] paramArrayOfPublisher, boolean paramBoolean)
  {
    this.sources = paramArrayOfPublisher;
    this.delayError = paramBoolean;
  }
  
  protected void subscribeActual(Subscriber<? super T> paramSubscriber)
  {
    ConcatArraySubscriber localConcatArraySubscriber = new ConcatArraySubscriber(this.sources, this.delayError, paramSubscriber);
    paramSubscriber.onSubscribe(localConcatArraySubscriber);
    localConcatArraySubscriber.onComplete();
  }
  
  static final class ConcatArraySubscriber<T>
    extends SubscriptionArbiter
    implements FlowableSubscriber<T>
  {
    private static final long serialVersionUID = -8158322871608889516L;
    final boolean delayError;
    final Subscriber<? super T> downstream;
    List<Throwable> errors;
    int index;
    long produced;
    final Publisher<? extends T>[] sources;
    final AtomicInteger wip;
    
    ConcatArraySubscriber(Publisher<? extends T>[] paramArrayOfPublisher, boolean paramBoolean, Subscriber<? super T> paramSubscriber)
    {
      super();
      this.downstream = paramSubscriber;
      this.sources = paramArrayOfPublisher;
      this.delayError = paramBoolean;
      this.wip = new AtomicInteger();
    }
    
    public void onComplete()
    {
      if (this.wip.getAndIncrement() == 0)
      {
        Publisher[] arrayOfPublisher = this.sources;
        int i = arrayOfPublisher.length;
        int j = this.index;
        label195:
        do
        {
          Object localObject;
          NullPointerException localNullPointerException;
          for (;;)
          {
            if (j == i)
            {
              localObject = this.errors;
              if (localObject != null)
              {
                if (((List)localObject).size() == 1) {
                  this.downstream.onError((Throwable)((List)localObject).get(0));
                } else {
                  this.downstream.onError(new CompositeException((Iterable)localObject));
                }
              }
              else {
                this.downstream.onComplete();
              }
              return;
            }
            localObject = arrayOfPublisher[j];
            if (localObject != null) {
              break label195;
            }
            localNullPointerException = new NullPointerException("A Publisher entry is null");
            if (!this.delayError) {
              break;
            }
            List localList = this.errors;
            localObject = localList;
            if (localList == null)
            {
              localObject = new ArrayList(i - j + 1);
              this.errors = ((List)localObject);
            }
            ((List)localObject).add(localNullPointerException);
            j++;
          }
          this.downstream.onError(localNullPointerException);
          return;
          long l = this.produced;
          if (l != 0L)
          {
            this.produced = 0L;
            produced(l);
          }
          ((Publisher)localObject).subscribe(this);
          j++;
          this.index = j;
        } while (this.wip.decrementAndGet() != 0);
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.delayError)
      {
        List localList = this.errors;
        Object localObject = localList;
        if (localList == null)
        {
          localObject = new ArrayList(this.sources.length - this.index + 1);
          this.errors = ((List)localObject);
        }
        ((List)localObject).add(paramThrowable);
        onComplete();
      }
      else
      {
        this.downstream.onError(paramThrowable);
      }
    }
    
    public void onNext(T paramT)
    {
      this.produced += 1L;
      this.downstream.onNext(paramT);
    }
    
    public void onSubscribe(Subscription paramSubscription)
    {
      setSubscription(paramSubscription);
    }
  }
}
