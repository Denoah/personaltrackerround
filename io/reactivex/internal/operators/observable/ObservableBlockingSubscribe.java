package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.BlockingObserver;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.BlockingIgnoringReceiver;
import io.reactivex.internal.util.ExceptionHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class ObservableBlockingSubscribe
{
  private ObservableBlockingSubscribe()
  {
    throw new IllegalStateException("No instances!");
  }
  
  public static <T> void subscribe(ObservableSource<? extends T> paramObservableSource)
  {
    BlockingIgnoringReceiver localBlockingIgnoringReceiver = new BlockingIgnoringReceiver();
    LambdaObserver localLambdaObserver = new LambdaObserver(Functions.emptyConsumer(), localBlockingIgnoringReceiver, localBlockingIgnoringReceiver, Functions.emptyConsumer());
    paramObservableSource.subscribe(localLambdaObserver);
    BlockingHelper.awaitForComplete(localBlockingIgnoringReceiver, localLambdaObserver);
    paramObservableSource = localBlockingIgnoringReceiver.error;
    if (paramObservableSource == null) {
      return;
    }
    throw ExceptionHelper.wrapOrThrow(paramObservableSource);
  }
  
  public static <T> void subscribe(ObservableSource<? extends T> paramObservableSource, Observer<? super T> paramObserver)
  {
    LinkedBlockingQueue localLinkedBlockingQueue = new LinkedBlockingQueue();
    BlockingObserver localBlockingObserver = new BlockingObserver(localLinkedBlockingQueue);
    paramObserver.onSubscribe(localBlockingObserver);
    paramObservableSource.subscribe(localBlockingObserver);
    Object localObject2;
    do
    {
      if (localBlockingObserver.isDisposed()) {
        break;
      }
      Object localObject1 = localLinkedBlockingQueue.poll();
      localObject2 = localObject1;
      if (localObject1 == null) {
        try
        {
          localObject2 = localLinkedBlockingQueue.take();
        }
        catch (InterruptedException paramObservableSource)
        {
          localBlockingObserver.dispose();
          paramObserver.onError(paramObservableSource);
          return;
        }
      }
    } while ((!localBlockingObserver.isDisposed()) && (paramObservableSource != BlockingObserver.TERMINATED) && (!NotificationLite.acceptFull(localObject2, paramObserver)));
  }
  
  public static <T> void subscribe(ObservableSource<? extends T> paramObservableSource, Consumer<? super T> paramConsumer, Consumer<? super Throwable> paramConsumer1, Action paramAction)
  {
    ObjectHelper.requireNonNull(paramConsumer, "onNext is null");
    ObjectHelper.requireNonNull(paramConsumer1, "onError is null");
    ObjectHelper.requireNonNull(paramAction, "onComplete is null");
    subscribe(paramObservableSource, new LambdaObserver(paramConsumer, paramConsumer1, paramAction, Functions.emptyConsumer()));
  }
}
