package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableZip<T, R>
  extends Observable<R>
{
  final int bufferSize;
  final boolean delayError;
  final ObservableSource<? extends T>[] sources;
  final Iterable<? extends ObservableSource<? extends T>> sourcesIterable;
  final Function<? super Object[], ? extends R> zipper;
  
  public ObservableZip(ObservableSource<? extends T>[] paramArrayOfObservableSource, Iterable<? extends ObservableSource<? extends T>> paramIterable, Function<? super Object[], ? extends R> paramFunction, int paramInt, boolean paramBoolean)
  {
    this.sources = paramArrayOfObservableSource;
    this.sourcesIterable = paramIterable;
    this.zipper = paramFunction;
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
    new ZipCoordinator(paramObserver, this.zipper, j, this.delayError).subscribe((ObservableSource[])localObject1, this.bufferSize);
  }
  
  static final class ZipCoordinator<T, R>
    extends AtomicInteger
    implements Disposable
  {
    private static final long serialVersionUID = 2983708048395377667L;
    volatile boolean cancelled;
    final boolean delayError;
    final Observer<? super R> downstream;
    final ObservableZip.ZipObserver<T, R>[] observers;
    final T[] row;
    final Function<? super Object[], ? extends R> zipper;
    
    ZipCoordinator(Observer<? super R> paramObserver, Function<? super Object[], ? extends R> paramFunction, int paramInt, boolean paramBoolean)
    {
      this.downstream = paramObserver;
      this.zipper = paramFunction;
      this.observers = new ObservableZip.ZipObserver[paramInt];
      this.row = ((Object[])new Object[paramInt]);
      this.delayError = paramBoolean;
    }
    
    void cancel()
    {
      clear();
      cancelSources();
    }
    
    void cancelSources()
    {
      ObservableZip.ZipObserver[] arrayOfZipObserver = this.observers;
      int i = arrayOfZipObserver.length;
      for (int j = 0; j < i; j++) {
        arrayOfZipObserver[j].dispose();
      }
    }
    
    boolean checkTerminated(boolean paramBoolean1, boolean paramBoolean2, Observer<? super R> paramObserver, boolean paramBoolean3, ObservableZip.ZipObserver<?, ?> paramZipObserver)
    {
      if (this.cancelled)
      {
        cancel();
        return true;
      }
      if (paramBoolean1) {
        if (paramBoolean3)
        {
          if (paramBoolean2)
          {
            paramZipObserver = paramZipObserver.error;
            this.cancelled = true;
            cancel();
            if (paramZipObserver != null) {
              paramObserver.onError(paramZipObserver);
            } else {
              paramObserver.onComplete();
            }
            return true;
          }
        }
        else
        {
          paramZipObserver = paramZipObserver.error;
          if (paramZipObserver != null)
          {
            this.cancelled = true;
            cancel();
            paramObserver.onError(paramZipObserver);
            return true;
          }
          if (paramBoolean2)
          {
            this.cancelled = true;
            cancel();
            paramObserver.onComplete();
            return true;
          }
        }
      }
      return false;
    }
    
    void clear()
    {
      ObservableZip.ZipObserver[] arrayOfZipObserver = this.observers;
      int i = arrayOfZipObserver.length;
      for (int j = 0; j < i; j++) {
        arrayOfZipObserver[j].queue.clear();
      }
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        cancelSources();
        if (getAndIncrement() == 0) {
          clear();
        }
      }
    }
    
    /* Error */
    public void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 93	io/reactivex/internal/operators/observable/ObservableZip$ZipCoordinator:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 42	io/reactivex/internal/operators/observable/ObservableZip$ZipCoordinator:observers	[Lio/reactivex/internal/operators/observable/ObservableZip$ZipObserver;
      //   12: astore_1
      //   13: aload_0
      //   14: getfield 36	io/reactivex/internal/operators/observable/ObservableZip$ZipCoordinator:downstream	Lio/reactivex/Observer;
      //   17: astore_2
      //   18: aload_0
      //   19: getfield 47	io/reactivex/internal/operators/observable/ObservableZip$ZipCoordinator:row	[Ljava/lang/Object;
      //   22: astore_3
      //   23: aload_0
      //   24: getfield 49	io/reactivex/internal/operators/observable/ObservableZip$ZipCoordinator:delayError	Z
      //   27: istore 4
      //   29: iconst_1
      //   30: istore 5
      //   32: aload_1
      //   33: arraylength
      //   34: istore 6
      //   36: iconst_0
      //   37: istore 7
      //   39: iload 7
      //   41: istore 8
      //   43: iload 8
      //   45: istore 9
      //   47: iload 8
      //   49: istore 10
      //   51: iload 7
      //   53: istore 8
      //   55: iload 8
      //   57: iload 6
      //   59: if_icmpge +159 -> 218
      //   62: aload_1
      //   63: iload 8
      //   65: aaload
      //   66: astore 11
      //   68: aload_3
      //   69: iload 9
      //   71: aaload
      //   72: ifnonnull +78 -> 150
      //   75: aload 11
      //   77: getfield 97	io/reactivex/internal/operators/observable/ObservableZip$ZipObserver:done	Z
      //   80: istore 12
      //   82: aload 11
      //   84: getfield 86	io/reactivex/internal/operators/observable/ObservableZip$ZipObserver:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   87: invokevirtual 101	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   90: astore 13
      //   92: aload 13
      //   94: ifnonnull +9 -> 103
      //   97: iconst_1
      //   98: istore 14
      //   100: goto +6 -> 106
      //   103: iconst_0
      //   104: istore 14
      //   106: aload_0
      //   107: iload 12
      //   109: iload 14
      //   111: aload_2
      //   112: iload 4
      //   114: aload 11
      //   116: invokevirtual 103	io/reactivex/internal/operators/observable/ObservableZip$ZipCoordinator:checkTerminated	(ZZLio/reactivex/Observer;ZLio/reactivex/internal/operators/observable/ObservableZip$ZipObserver;)Z
      //   119: ifeq +4 -> 123
      //   122: return
      //   123: iload 14
      //   125: ifne +16 -> 141
      //   128: aload_3
      //   129: iload 9
      //   131: aload 13
      //   133: aastore
      //   134: iload 10
      //   136: istore 7
      //   138: goto +67 -> 205
      //   141: iload 10
      //   143: iconst_1
      //   144: iadd
      //   145: istore 7
      //   147: goto +58 -> 205
      //   150: iload 10
      //   152: istore 7
      //   154: aload 11
      //   156: getfield 97	io/reactivex/internal/operators/observable/ObservableZip$ZipObserver:done	Z
      //   159: ifeq +46 -> 205
      //   162: iload 10
      //   164: istore 7
      //   166: iload 4
      //   168: ifne +37 -> 205
      //   171: aload 11
      //   173: getfield 72	io/reactivex/internal/operators/observable/ObservableZip$ZipObserver:error	Ljava/lang/Throwable;
      //   176: astore 13
      //   178: iload 10
      //   180: istore 7
      //   182: aload 13
      //   184: ifnull +21 -> 205
      //   187: aload_0
      //   188: iconst_1
      //   189: putfield 66	io/reactivex/internal/operators/observable/ObservableZip$ZipCoordinator:cancelled	Z
      //   192: aload_0
      //   193: invokevirtual 68	io/reactivex/internal/operators/observable/ObservableZip$ZipCoordinator:cancel	()V
      //   196: aload_2
      //   197: aload 13
      //   199: invokeinterface 78 2 0
      //   204: return
      //   205: iinc 9 1
      //   208: iinc 8 1
      //   211: iload 7
      //   213: istore 10
      //   215: goto -160 -> 55
      //   218: iload 10
      //   220: ifeq +22 -> 242
      //   223: aload_0
      //   224: iload 5
      //   226: ineg
      //   227: invokevirtual 107	io/reactivex/internal/operators/observable/ObservableZip$ZipCoordinator:addAndGet	(I)I
      //   230: istore 8
      //   232: iload 8
      //   234: istore 5
      //   236: iload 8
      //   238: ifne -206 -> 32
      //   241: return
      //   242: aload_0
      //   243: getfield 38	io/reactivex/internal/operators/observable/ObservableZip$ZipCoordinator:zipper	Lio/reactivex/functions/Function;
      //   246: aload_3
      //   247: invokevirtual 110	[Ljava/lang/Object;:clone	()Ljava/lang/Object;
      //   250: invokeinterface 116 2 0
      //   255: ldc 118
      //   257: invokestatic 124	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
      //   260: astore 13
      //   262: aload_2
      //   263: aload 13
      //   265: invokeinterface 128 2 0
      //   270: aload_3
      //   271: aconst_null
      //   272: invokestatic 134	java/util/Arrays:fill	([Ljava/lang/Object;Ljava/lang/Object;)V
      //   275: goto -243 -> 32
      //   278: astore_3
      //   279: aload_3
      //   280: invokestatic 139	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   283: aload_0
      //   284: invokevirtual 68	io/reactivex/internal/operators/observable/ObservableZip$ZipCoordinator:cancel	()V
      //   287: aload_2
      //   288: aload_3
      //   289: invokeinterface 78 2 0
      //   294: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	295	0	this	ZipCoordinator
      //   12	51	1	arrayOfZipObserver	ObservableZip.ZipObserver[]
      //   17	271	2	localObserver	Observer
      //   22	249	3	arrayOfObject	Object[]
      //   278	11	3	localThrowable	Throwable
      //   27	140	4	bool1	boolean
      //   30	205	5	i	int
      //   34	26	6	j	int
      //   37	175	7	k	int
      //   41	196	8	m	int
      //   45	161	9	n	int
      //   49	170	10	i1	int
      //   66	106	11	localZipObserver	ObservableZip.ZipObserver
      //   80	28	12	bool2	boolean
      //   90	174	13	localObject	Object
      //   98	26	14	bool3	boolean
      // Exception table:
      //   from	to	target	type
      //   242	262	278	finally
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    public void subscribe(ObservableSource<? extends T>[] paramArrayOfObservableSource, int paramInt)
    {
      ObservableZip.ZipObserver[] arrayOfZipObserver = this.observers;
      int i = arrayOfZipObserver.length;
      int j = 0;
      for (int k = 0; k < i; k++) {
        arrayOfZipObserver[k] = new ObservableZip.ZipObserver(this, paramInt);
      }
      lazySet(0);
      this.downstream.onSubscribe(this);
      for (paramInt = j; paramInt < i; paramInt++)
      {
        if (this.cancelled) {
          return;
        }
        paramArrayOfObservableSource[paramInt].subscribe(arrayOfZipObserver[paramInt]);
      }
    }
  }
  
  static final class ZipObserver<T, R>
    implements Observer<T>
  {
    volatile boolean done;
    Throwable error;
    final ObservableZip.ZipCoordinator<T, R> parent;
    final SpscLinkedArrayQueue<T> queue;
    final AtomicReference<Disposable> upstream = new AtomicReference();
    
    ZipObserver(ObservableZip.ZipCoordinator<T, R> paramZipCoordinator, int paramInt)
    {
      this.parent = paramZipCoordinator;
      this.queue = new SpscLinkedArrayQueue(paramInt);
    }
    
    public void dispose()
    {
      DisposableHelper.dispose(this.upstream);
    }
    
    public void onComplete()
    {
      this.done = true;
      this.parent.drain();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.error = paramThrowable;
      this.done = true;
      this.parent.drain();
    }
    
    public void onNext(T paramT)
    {
      this.queue.offer(paramT);
      this.parent.drain();
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      DisposableHelper.setOnce(this.upstream, paramDisposable);
    }
  }
}
