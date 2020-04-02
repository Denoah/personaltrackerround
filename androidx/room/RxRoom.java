package androidx.room;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

public class RxRoom
{
  public static final Object NOTHING = new Object();
  
  @Deprecated
  public RxRoom() {}
  
  public static <T> Flowable<T> createFlowable(RoomDatabase paramRoomDatabase, boolean paramBoolean, String[] paramArrayOfString, Callable<T> paramCallable)
  {
    Scheduler localScheduler = Schedulers.from(getExecutor(paramRoomDatabase, paramBoolean));
    paramCallable = Maybe.fromCallable(paramCallable);
    createFlowable(paramRoomDatabase, paramArrayOfString).subscribeOn(localScheduler).unsubscribeOn(localScheduler).observeOn(localScheduler).flatMapMaybe(new Function()
    {
      public MaybeSource<T> apply(Object paramAnonymousObject)
        throws Exception
      {
        return this.val$maybe;
      }
    });
  }
  
  public static Flowable<Object> createFlowable(final RoomDatabase paramRoomDatabase, String... paramVarArgs)
  {
    Flowable.create(new FlowableOnSubscribe()
    {
      public void subscribe(final FlowableEmitter<Object> paramAnonymousFlowableEmitter)
        throws Exception
      {
        final InvalidationTracker.Observer local1 = new InvalidationTracker.Observer(this.val$tableNames)
        {
          public void onInvalidated(Set<String> paramAnonymous2Set)
          {
            if (!paramAnonymousFlowableEmitter.isCancelled()) {
              paramAnonymousFlowableEmitter.onNext(RxRoom.NOTHING);
            }
          }
        };
        if (!paramAnonymousFlowableEmitter.isCancelled())
        {
          paramRoomDatabase.getInvalidationTracker().addObserver(local1);
          paramAnonymousFlowableEmitter.setDisposable(Disposables.fromAction(new Action()
          {
            public void run()
              throws Exception
            {
              RxRoom.1.this.val$database.getInvalidationTracker().removeObserver(local1);
            }
          }));
        }
        if (!paramAnonymousFlowableEmitter.isCancelled()) {
          paramAnonymousFlowableEmitter.onNext(RxRoom.NOTHING);
        }
      }
    }, BackpressureStrategy.LATEST);
  }
  
  @Deprecated
  public static <T> Flowable<T> createFlowable(RoomDatabase paramRoomDatabase, String[] paramArrayOfString, Callable<T> paramCallable)
  {
    return createFlowable(paramRoomDatabase, false, paramArrayOfString, paramCallable);
  }
  
  public static <T> Observable<T> createObservable(RoomDatabase paramRoomDatabase, boolean paramBoolean, String[] paramArrayOfString, Callable<T> paramCallable)
  {
    Scheduler localScheduler = Schedulers.from(getExecutor(paramRoomDatabase, paramBoolean));
    paramCallable = Maybe.fromCallable(paramCallable);
    createObservable(paramRoomDatabase, paramArrayOfString).subscribeOn(localScheduler).unsubscribeOn(localScheduler).observeOn(localScheduler).flatMapMaybe(new Function()
    {
      public MaybeSource<T> apply(Object paramAnonymousObject)
        throws Exception
      {
        return this.val$maybe;
      }
    });
  }
  
  public static Observable<Object> createObservable(final RoomDatabase paramRoomDatabase, String... paramVarArgs)
  {
    Observable.create(new ObservableOnSubscribe()
    {
      public void subscribe(final ObservableEmitter<Object> paramAnonymousObservableEmitter)
        throws Exception
      {
        final InvalidationTracker.Observer local1 = new InvalidationTracker.Observer(this.val$tableNames)
        {
          public void onInvalidated(Set<String> paramAnonymous2Set)
          {
            paramAnonymousObservableEmitter.onNext(RxRoom.NOTHING);
          }
        };
        paramRoomDatabase.getInvalidationTracker().addObserver(local1);
        paramAnonymousObservableEmitter.setDisposable(Disposables.fromAction(new Action()
        {
          public void run()
            throws Exception
          {
            RxRoom.3.this.val$database.getInvalidationTracker().removeObserver(local1);
          }
        }));
        paramAnonymousObservableEmitter.onNext(RxRoom.NOTHING);
      }
    });
  }
  
  @Deprecated
  public static <T> Observable<T> createObservable(RoomDatabase paramRoomDatabase, String[] paramArrayOfString, Callable<T> paramCallable)
  {
    return createObservable(paramRoomDatabase, false, paramArrayOfString, paramCallable);
  }
  
  public static <T> Single<T> createSingle(Callable<T> paramCallable)
  {
    Single.create(new SingleOnSubscribe()
    {
      public void subscribe(SingleEmitter<T> paramAnonymousSingleEmitter)
        throws Exception
      {
        try
        {
          paramAnonymousSingleEmitter.onSuccess(this.val$callable.call());
        }
        catch (EmptyResultSetException localEmptyResultSetException)
        {
          paramAnonymousSingleEmitter.tryOnError(localEmptyResultSetException);
        }
      }
    });
  }
  
  private static Executor getExecutor(RoomDatabase paramRoomDatabase, boolean paramBoolean)
  {
    if (paramBoolean) {
      return paramRoomDatabase.getTransactionExecutor();
    }
    return paramRoomDatabase.getQueryExecutor();
  }
}
