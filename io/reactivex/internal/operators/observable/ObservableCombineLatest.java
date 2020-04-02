package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableCombineLatest<T, R>
  extends Observable<R>
{
  final int bufferSize;
  final Function<? super Object[], ? extends R> combiner;
  final boolean delayError;
  final ObservableSource<? extends T>[] sources;
  final Iterable<? extends ObservableSource<? extends T>> sourcesIterable;
  
  public ObservableCombineLatest(ObservableSource<? extends T>[] paramArrayOfObservableSource, Iterable<? extends ObservableSource<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction, int paramInt, boolean paramBoolean)
  {
    this.sources = paramArrayOfObservableSource;
    this.sourcesIterable = paramIterable;
    this.combiner = paramFunction;
    this.bufferSize = paramInt;
    this.delayError = paramBoolean;
  }
  
  public void subscribeActual(Observer<? super R> paramObserver)
  {
    Object localObject1 = this.sources;
    if (localObject1 == null)
    {
      Object localObject2 = new Observable[8];
      Iterator localIterator = this.sourcesIterable.iterator();
      int i = 0;
      for (;;)
      {
        localObject1 = localObject2;
        j = i;
        if (!localIterator.hasNext()) {
          break;
        }
        ObservableSource localObservableSource = (ObservableSource)localIterator.next();
        localObject1 = localObject2;
        if (i == localObject2.length)
        {
          localObject1 = new ObservableSource[(i >> 2) + i];
          System.arraycopy(localObject2, 0, localObject1, 0, i);
        }
        localObject1[i] = localObservableSource;
        i++;
        localObject2 = localObject1;
      }
    }
    int j = localObject1.length;
    if (j == 0)
    {
      EmptyDisposable.complete(paramObserver);
      return;
    }
    new LatestCoordinator(paramObserver, this.combiner, j, this.bufferSize, this.delayError).subscribe((ObservableSource[])localObject1);
  }
  
  static final class CombinerObserver<T, R>
    extends AtomicReference<Disposable>
    implements Observer<T>
  {
    private static final long serialVersionUID = -4823716997131257941L;
    final int index;
    final ObservableCombineLatest.LatestCoordinator<T, R> parent;
    
    CombinerObserver(ObservableCombineLatest.LatestCoordinator<T, R> paramLatestCoordinator, int paramInt)
    {
      this.parent = paramLatestCoordinator;
      this.index = paramInt;
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this);
    }
    
    public void onComplete()
    {
      this.parent.innerComplete(this.index);
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.parent.innerError(this.index, paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      this.parent.innerNext(this.index, paramT);
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this, paramDisposable);
    }
  }
  
  static final class LatestCoordinator<T, R>
    extends AtomicInteger
    implements Disposable
  {
    private static final long serialVersionUID = 8567835998786448817L;
    int active;
    volatile boolean cancelled;
    final Function<? super Object[], ? extends R> combiner;
    int complete;
    final boolean delayError;
    volatile boolean done;
    final Observer<? super R> downstream;
    final AtomicThrowable errors = new AtomicThrowable();
    Object[] latest;
    final ObservableCombineLatest.CombinerObserver<T, R>[] observers;
    final SpscLinkedArrayQueue<Object[]> queue;
    
    LatestCoordinator(Observer<? super R> paramObserver, Function<? super Object[], ? extends R> paramFunction, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.combiner = paramFunction;
      this.delayError = paramBoolean;
      this.latest = new Object[paramInt1];
      paramObserver = new ObservableCombineLatest.CombinerObserver[paramInt1];
      for (int i = 0; i < paramInt1; i++) {
        paramObserver[i] = new ObservableCombineLatest.CombinerObserver(this, i);
      }
      this.observers = paramObserver;
      this.queue = new SpscLinkedArrayQueue(paramInt2);
    }
    
    void cancelSources()
    {
      ObservableCombineLatest.CombinerObserver[] arrayOfCombinerObserver = this.observers;
      int i = arrayOfCombinerObserver.length;
      for (int j = 0; j < i; j++) {
        arrayOfCombinerObserver[j].dispose();
      }
    }
    
    void clear(SpscLinkedArrayQueue<?> paramSpscLinkedArrayQueue)
    {
      try
      {
        this.latest = null;
        paramSpscLinkedArrayQueue.clear();
        return;
      }
      finally {}
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        cancelSources();
        if (getAndIncrement() == 0) {
          clear(this.queue);
        }
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 91	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 71	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 49	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:downstream	Lio/reactivex/Observer;
      //   17: astore_2
      //   18: aload_0
      //   19: getfield 53	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:delayError	Z
      //   22: istore_3
      //   23: iconst_1
      //   24: istore 4
      //   26: aload_0
      //   27: getfield 85	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:cancelled	Z
      //   30: ifeq +9 -> 39
      //   33: aload_0
      //   34: aload_1
      //   35: invokevirtual 93	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:clear	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   38: return
      //   39: iload_3
      //   40: ifne +36 -> 76
      //   43: aload_0
      //   44: getfield 47	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   47: invokevirtual 98	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   50: ifnull +26 -> 76
      //   53: aload_0
      //   54: invokevirtual 87	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:cancelSources	()V
      //   57: aload_0
      //   58: aload_1
      //   59: invokevirtual 93	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:clear	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   62: aload_2
      //   63: aload_0
      //   64: getfield 47	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   67: invokevirtual 102	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   70: invokeinterface 108 2 0
      //   75: return
      //   76: aload_0
      //   77: getfield 110	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:done	Z
      //   80: istore 5
      //   82: aload_1
      //   83: invokevirtual 113	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   86: checkcast 114	[Ljava/lang/Object;
      //   89: astore 6
      //   91: aload 6
      //   93: ifnonnull +9 -> 102
      //   96: iconst_1
      //   97: istore 7
      //   99: goto +6 -> 105
      //   102: iconst_0
      //   103: istore 7
      //   105: iload 5
      //   107: ifeq +42 -> 149
      //   110: iload 7
      //   112: ifeq +37 -> 149
      //   115: aload_0
      //   116: aload_1
      //   117: invokevirtual 93	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:clear	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   120: aload_0
      //   121: getfield 47	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   124: invokevirtual 102	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   127: astore_1
      //   128: aload_1
      //   129: ifnonnull +12 -> 141
      //   132: aload_2
      //   133: invokeinterface 117 1 0
      //   138: goto +10 -> 148
      //   141: aload_2
      //   142: aload_1
      //   143: invokeinterface 108 2 0
      //   148: return
      //   149: iload 7
      //   151: ifeq +22 -> 173
      //   154: aload_0
      //   155: iload 4
      //   157: ineg
      //   158: invokevirtual 121	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:addAndGet	(I)I
      //   161: istore 7
      //   163: iload 7
      //   165: istore 4
      //   167: iload 7
      //   169: ifne -143 -> 26
      //   172: return
      //   173: aload_0
      //   174: getfield 51	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:combiner	Lio/reactivex/functions/Function;
      //   177: aload 6
      //   179: invokeinterface 127 2 0
      //   184: ldc -127
      //   186: invokestatic 135	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   189: astore 6
      //   191: aload_2
      //   192: aload 6
      //   194: invokeinterface 139 2 0
      //   199: goto -173 -> 26
      //   202: astore 6
      //   204: aload 6
      //   206: invokestatic 144	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   209: aload_0
      //   210: getfield 47	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   213: aload 6
      //   215: invokevirtual 148	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   218: pop
      //   219: aload_0
      //   220: invokevirtual 87	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:cancelSources	()V
      //   223: aload_0
      //   224: aload_1
      //   225: invokevirtual 93	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:clear	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   228: aload_2
      //   229: aload_0
      //   230: getfield 47	io/reactivex/internal/operators/observable/ObservableCombineLatest$LatestCoordinator:errors	Lio/reactivex/internal/util/AtomicThrowable;
      //   233: invokevirtual 102	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   236: invokeinterface 108 2 0
      //   241: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	242	0	this	LatestCoordinator
      //   12	213	1	localObject1	Object
      //   17	212	2	localObserver	Observer
      //   22	18	3	bool1	boolean
      //   24	142	4	i	int
      //   80	26	5	bool2	boolean
      //   89	104	6	localObject2	Object
      //   202	12	6	localThrowable	Throwable
      //   97	71	7	j	int
      // Exception table:
      //   from	to	target	type
      //   173	191	202	finally
    }
    
    void innerComplete(int paramInt)
    {
      try
      {
        Object[] arrayOfObject = this.latest;
        if (arrayOfObject == null) {
          return;
        }
        if (arrayOfObject[paramInt] == null) {
          paramInt = 1;
        } else {
          paramInt = 0;
        }
        if (paramInt == 0)
        {
          int i = this.complete + 1;
          this.complete = i;
          if (i != arrayOfObject.length) {}
        }
        else
        {
          this.done = true;
        }
        if (paramInt != 0) {
          cancelSources();
        }
        drain();
        return;
      }
      finally {}
    }
    
    void innerError(int paramInt, Throwable paramThrowable)
    {
      if (this.errors.addThrowable(paramThrowable))
      {
        boolean bool = this.delayError;
        int i = 1;
        if (bool) {
          try
          {
            paramThrowable = this.latest;
            if (paramThrowable == null) {
              return;
            }
            if (paramThrowable[paramInt] == null) {
              paramInt = 1;
            } else {
              paramInt = 0;
            }
            if (paramInt == 0)
            {
              i = this.complete + 1;
              this.complete = i;
              if (i != paramThrowable.length) {}
            }
            else
            {
              this.done = true;
            }
            i = paramInt;
          }
          finally {}
        }
        if (i != 0) {
          cancelSources();
        }
        drain();
      }
      else
      {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    void innerNext(int paramInt, T paramT)
    {
      try
      {
        Object[] arrayOfObject = this.latest;
        if (arrayOfObject == null) {
          return;
        }
        Object localObject = arrayOfObject[paramInt];
        int i = this.active;
        int j = i;
        if (localObject == null)
        {
          j = i + 1;
          this.active = j;
        }
        arrayOfObject[paramInt] = paramT;
        if (j == arrayOfObject.length)
        {
          this.queue.offer(arrayOfObject.clone());
          paramInt = 1;
        }
        else
        {
          paramInt = 0;
        }
        if (paramInt != 0) {
          drain();
        }
        return;
      }
      finally {}
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void subscribe(ObservableSource<? extends T>[] paramArrayOfObservableSource)
    {
      ObservableCombineLatest.CombinerObserver[] arrayOfCombinerObserver = this.observers;
      int i = arrayOfCombinerObserver.length;
      this.downstream.onSubscribe(this);
      for (int j = 0; (j < i) && (!this.done) && (!this.cancelled); j++) {
        paramArrayOfObservableSource[j].subscribe(arrayOfCombinerObserver[j]);
      }
    }
  }
}
