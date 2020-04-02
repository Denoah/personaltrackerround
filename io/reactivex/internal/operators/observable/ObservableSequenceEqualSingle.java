package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableSequenceEqualSingle<T>
  extends Single<Boolean>
  implements FuseToObservable<Boolean>
{
  final int bufferSize;
  final BiPredicate<? super T, ? super T> comparer;
  final ObservableSource<? extends T> first;
  final ObservableSource<? extends T> second;
  
  public ObservableSequenceEqualSingle(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, BiPredicate<? super T, ? super T> paramBiPredicate, int paramInt)
  {
    this.first = paramObservableSource1;
    this.second = paramObservableSource2;
    this.comparer = paramBiPredicate;
    this.bufferSize = paramInt;
  }
  
  public Observable<Boolean> fuseToObservable()
  {
    return RxJavaPlugins.onAssembly(new ObservableSequenceEqual(this.first, this.second, this.comparer, this.bufferSize));
  }
  
  public void subscribeActual(SingleObserver<? super Boolean> paramSingleObserver)
  {
    EqualCoordinator localEqualCoordinator = new EqualCoordinator(paramSingleObserver, this.bufferSize, this.first, this.second, this.comparer);
    paramSingleObserver.onSubscribe(localEqualCoordinator);
    localEqualCoordinator.subscribe();
  }
  
  static final class EqualCoordinator<T>
    extends AtomicInteger
    implements Disposable
  {
    private static final long serialVersionUID = -6178010334400373240L;
    volatile boolean cancelled;
    final BiPredicate<? super T, ? super T> comparer;
    final SingleObserver<? super Boolean> downstream;
    final ObservableSource<? extends T> first;
    final ObservableSequenceEqualSingle.EqualObserver<T>[] observers;
    final ArrayCompositeDisposable resources;
    final ObservableSource<? extends T> second;
    T v1;
    T v2;
    
    EqualCoordinator(SingleObserver<? super Boolean> paramSingleObserver, int paramInt, ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, BiPredicate<? super T, ? super T> paramBiPredicate)
    {
      this.downstream = paramSingleObserver;
      this.first = paramObservableSource1;
      this.second = paramObservableSource2;
      this.comparer = paramBiPredicate;
      paramSingleObserver = new ObservableSequenceEqualSingle.EqualObserver[2];
      this.observers = paramSingleObserver;
      paramSingleObserver[0] = new ObservableSequenceEqualSingle.EqualObserver(this, 0, paramInt);
      paramSingleObserver[1] = new ObservableSequenceEqualSingle.EqualObserver(this, 1, paramInt);
      this.resources = new ArrayCompositeDisposable(2);
    }
    
    void cancel(SpscLinkedArrayQueue<T> paramSpscLinkedArrayQueue1, SpscLinkedArrayQueue<T> paramSpscLinkedArrayQueue2)
    {
      this.cancelled = true;
      paramSpscLinkedArrayQueue1.clear();
      paramSpscLinkedArrayQueue2.clear();
    }
    
    public void dispose()
    {
      if (!this.cancelled)
      {
        this.cancelled = true;
        this.resources.dispose();
        if (getAndIncrement() == 0)
        {
          ObservableSequenceEqualSingle.EqualObserver[] arrayOfEqualObserver = this.observers;
          arrayOfEqualObserver[0].queue.clear();
          arrayOfEqualObserver[1].queue.clear();
        }
      }
    }
    
    /* Error */
    void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 82	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 52	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:observers	[Lio/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualObserver;
      //   12: astore_1
      //   13: aload_1
      //   14: iconst_0
      //   15: aaload
      //   16: astore_2
      //   17: aload_2
      //   18: getfield 86	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualObserver:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   21: astore_3
      //   22: aload_1
      //   23: iconst_1
      //   24: aaload
      //   25: astore 4
      //   27: aload 4
      //   29: getfield 86	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualObserver:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   32: astore_1
      //   33: iconst_1
      //   34: istore 5
      //   36: aload_0
      //   37: getfield 69	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:cancelled	Z
      //   40: ifeq +12 -> 52
      //   43: aload_3
      //   44: invokevirtual 74	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   47: aload_1
      //   48: invokevirtual 74	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   51: return
      //   52: aload_2
      //   53: getfield 90	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualObserver:done	Z
      //   56: istore 6
      //   58: iload 6
      //   60: ifeq +32 -> 92
      //   63: aload_2
      //   64: getfield 94	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualObserver:error	Ljava/lang/Throwable;
      //   67: astore 7
      //   69: aload 7
      //   71: ifnull +21 -> 92
      //   74: aload_0
      //   75: aload_3
      //   76: aload_1
      //   77: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:cancel	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   80: aload_0
      //   81: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   84: aload 7
      //   86: invokeinterface 102 2 0
      //   91: return
      //   92: aload 4
      //   94: getfield 90	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualObserver:done	Z
      //   97: istore 8
      //   99: iload 8
      //   101: ifeq +33 -> 134
      //   104: aload 4
      //   106: getfield 94	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualObserver:error	Ljava/lang/Throwable;
      //   109: astore 7
      //   111: aload 7
      //   113: ifnull +21 -> 134
      //   116: aload_0
      //   117: aload_3
      //   118: aload_1
      //   119: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:cancel	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   122: aload_0
      //   123: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   126: aload 7
      //   128: invokeinterface 102 2 0
      //   133: return
      //   134: aload_0
      //   135: getfield 104	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:v1	Ljava/lang/Object;
      //   138: ifnonnull +11 -> 149
      //   141: aload_0
      //   142: aload_3
      //   143: invokevirtual 108	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   146: putfield 104	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:v1	Ljava/lang/Object;
      //   149: aload_0
      //   150: getfield 104	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:v1	Ljava/lang/Object;
      //   153: ifnonnull +9 -> 162
      //   156: iconst_1
      //   157: istore 9
      //   159: goto +6 -> 165
      //   162: iconst_0
      //   163: istore 9
      //   165: aload_0
      //   166: getfield 110	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:v2	Ljava/lang/Object;
      //   169: ifnonnull +11 -> 180
      //   172: aload_0
      //   173: aload_1
      //   174: invokevirtual 108	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   177: putfield 110	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:v2	Ljava/lang/Object;
      //   180: aload_0
      //   181: getfield 110	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:v2	Ljava/lang/Object;
      //   184: ifnonnull +9 -> 193
      //   187: iconst_1
      //   188: istore 10
      //   190: goto +6 -> 196
      //   193: iconst_0
      //   194: istore 10
      //   196: iload 6
      //   198: ifeq +32 -> 230
      //   201: iload 8
      //   203: ifeq +27 -> 230
      //   206: iload 9
      //   208: ifeq +22 -> 230
      //   211: iload 10
      //   213: ifeq +17 -> 230
      //   216: aload_0
      //   217: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   220: iconst_1
      //   221: invokestatic 116	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   224: invokeinterface 120 2 0
      //   229: return
      //   230: iload 6
      //   232: ifeq +35 -> 267
      //   235: iload 8
      //   237: ifeq +30 -> 267
      //   240: iload 9
      //   242: iload 10
      //   244: if_icmpeq +23 -> 267
      //   247: aload_0
      //   248: aload_3
      //   249: aload_1
      //   250: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:cancel	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   253: aload_0
      //   254: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   257: iconst_0
      //   258: invokestatic 116	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   261: invokeinterface 120 2 0
      //   266: return
      //   267: iload 9
      //   269: ifne +87 -> 356
      //   272: iload 10
      //   274: ifne +82 -> 356
      //   277: aload_0
      //   278: getfield 48	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:comparer	Lio/reactivex/functions/BiPredicate;
      //   281: aload_0
      //   282: getfield 104	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:v1	Ljava/lang/Object;
      //   285: aload_0
      //   286: getfield 110	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:v2	Ljava/lang/Object;
      //   289: invokeinterface 126 3 0
      //   294: istore 8
      //   296: iload 8
      //   298: ifne +23 -> 321
      //   301: aload_0
      //   302: aload_3
      //   303: aload_1
      //   304: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:cancel	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   307: aload_0
      //   308: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   311: iconst_0
      //   312: invokestatic 116	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   315: invokeinterface 120 2 0
      //   320: return
      //   321: aload_0
      //   322: aconst_null
      //   323: putfield 104	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:v1	Ljava/lang/Object;
      //   326: aload_0
      //   327: aconst_null
      //   328: putfield 110	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:v2	Ljava/lang/Object;
      //   331: goto +25 -> 356
      //   334: astore_2
      //   335: aload_2
      //   336: invokestatic 131	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   339: aload_0
      //   340: aload_3
      //   341: aload_1
      //   342: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:cancel	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   345: aload_0
      //   346: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   349: aload_2
      //   350: invokeinterface 102 2 0
      //   355: return
      //   356: iload 9
      //   358: ifne +8 -> 366
      //   361: iload 10
      //   363: ifeq -327 -> 36
      //   366: aload_0
      //   367: iload 5
      //   369: ineg
      //   370: invokevirtual 135	io/reactivex/internal/operators/observable/ObservableSequenceEqualSingle$EqualCoordinator:addAndGet	(I)I
      //   373: istore 9
      //   375: iload 9
      //   377: istore 5
      //   379: iload 9
      //   381: ifne -345 -> 36
      //   384: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	385	0	this	EqualCoordinator
      //   12	330	1	localObject1	Object
      //   16	48	2	localObject2	Object
      //   334	16	2	localThrowable1	Throwable
      //   21	320	3	localSpscLinkedArrayQueue	SpscLinkedArrayQueue
      //   25	80	4	localObject3	Object
      //   34	344	5	i	int
      //   56	175	6	bool1	boolean
      //   67	60	7	localThrowable2	Throwable
      //   97	200	8	bool2	boolean
      //   157	223	9	j	int
      //   188	174	10	k	int
      // Exception table:
      //   from	to	target	type
      //   277	296	334	finally
    }
    
    public boolean isDisposed()
    {
      return this.cancelled;
    }
    
    boolean setDisposable(Disposable paramDisposable, int paramInt)
    {
      return this.resources.setResource(paramInt, paramDisposable);
    }
    
    void subscribe()
    {
      ObservableSequenceEqualSingle.EqualObserver[] arrayOfEqualObserver = this.observers;
      this.first.subscribe(arrayOfEqualObserver[0]);
      this.second.subscribe(arrayOfEqualObserver[1]);
    }
  }
  
  static final class EqualObserver<T>
    implements Observer<T>
  {
    volatile boolean done;
    Throwable error;
    final int index;
    final ObservableSequenceEqualSingle.EqualCoordinator<T> parent;
    final SpscLinkedArrayQueue<T> queue;
    
    EqualObserver(ObservableSequenceEqualSingle.EqualCoordinator<T> paramEqualCoordinator, int paramInt1, int paramInt2)
    {
      this.parent = paramEqualCoordinator;
      this.index = paramInt1;
      this.queue = new SpscLinkedArrayQueue(paramInt2);
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
      this.parent.setDisposable(paramDisposable, this.index);
    }
  }
}
