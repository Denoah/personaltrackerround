package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.disposables.ArrayCompositeDisposable;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.atomic.AtomicInteger;

public final class ObservableSequenceEqual<T>
  extends Observable<Boolean>
{
  final int bufferSize;
  final BiPredicate<? super T, ? super T> comparer;
  final ObservableSource<? extends T> first;
  final ObservableSource<? extends T> second;
  
  public ObservableSequenceEqual(ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, BiPredicate<? super T, ? super T> paramBiPredicate, int paramInt)
  {
    this.first = paramObservableSource1;
    this.second = paramObservableSource2;
    this.comparer = paramBiPredicate;
    this.bufferSize = paramInt;
  }
  
  public void subscribeActual(Observer<? super Boolean> paramObserver)
  {
    EqualCoordinator localEqualCoordinator = new EqualCoordinator(paramObserver, this.bufferSize, this.first, this.second, this.comparer);
    paramObserver.onSubscribe(localEqualCoordinator);
    localEqualCoordinator.subscribe();
  }
  
  static final class EqualCoordinator<T>
    extends AtomicInteger
    implements Disposable
  {
    private static final long serialVersionUID = -6178010334400373240L;
    volatile boolean cancelled;
    final BiPredicate<? super T, ? super T> comparer;
    final Observer<? super Boolean> downstream;
    final ObservableSource<? extends T> first;
    final ObservableSequenceEqual.EqualObserver<T>[] observers;
    final ArrayCompositeDisposable resources;
    final ObservableSource<? extends T> second;
    T v1;
    T v2;
    
    EqualCoordinator(Observer<? super Boolean> paramObserver, int paramInt, ObservableSource<? extends T> paramObservableSource1, ObservableSource<? extends T> paramObservableSource2, BiPredicate<? super T, ? super T> paramBiPredicate)
    {
      this.downstream = paramObserver;
      this.first = paramObservableSource1;
      this.second = paramObservableSource2;
      this.comparer = paramBiPredicate;
      paramObserver = new ObservableSequenceEqual.EqualObserver[2];
      this.observers = paramObserver;
      paramObserver[0] = new ObservableSequenceEqual.EqualObserver(this, 0, paramInt);
      paramObserver[1] = new ObservableSequenceEqual.EqualObserver(this, 1, paramInt);
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
          ObservableSequenceEqual.EqualObserver[] arrayOfEqualObserver = this.observers;
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
      //   1: invokevirtual 82	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: aload_0
      //   9: getfield 52	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:observers	[Lio/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualObserver;
      //   12: astore_1
      //   13: aload_1
      //   14: iconst_0
      //   15: aaload
      //   16: astore_2
      //   17: aload_2
      //   18: getfield 86	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualObserver:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   21: astore_3
      //   22: aload_1
      //   23: iconst_1
      //   24: aaload
      //   25: astore 4
      //   27: aload 4
      //   29: getfield 86	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualObserver:queue	Lio/reactivex/internal/queue/SpscLinkedArrayQueue;
      //   32: astore_1
      //   33: iconst_1
      //   34: istore 5
      //   36: aload_0
      //   37: getfield 69	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:cancelled	Z
      //   40: ifeq +12 -> 52
      //   43: aload_3
      //   44: invokevirtual 74	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   47: aload_1
      //   48: invokevirtual 74	io/reactivex/internal/queue/SpscLinkedArrayQueue:clear	()V
      //   51: return
      //   52: aload_2
      //   53: getfield 90	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualObserver:done	Z
      //   56: istore 6
      //   58: iload 6
      //   60: ifeq +32 -> 92
      //   63: aload_2
      //   64: getfield 94	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualObserver:error	Ljava/lang/Throwable;
      //   67: astore 7
      //   69: aload 7
      //   71: ifnull +21 -> 92
      //   74: aload_0
      //   75: aload_3
      //   76: aload_1
      //   77: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:cancel	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   80: aload_0
      //   81: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:downstream	Lio/reactivex/Observer;
      //   84: aload 7
      //   86: invokeinterface 102 2 0
      //   91: return
      //   92: aload 4
      //   94: getfield 90	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualObserver:done	Z
      //   97: istore 8
      //   99: iload 8
      //   101: ifeq +33 -> 134
      //   104: aload 4
      //   106: getfield 94	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualObserver:error	Ljava/lang/Throwable;
      //   109: astore 7
      //   111: aload 7
      //   113: ifnull +21 -> 134
      //   116: aload_0
      //   117: aload_3
      //   118: aload_1
      //   119: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:cancel	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   122: aload_0
      //   123: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:downstream	Lio/reactivex/Observer;
      //   126: aload 7
      //   128: invokeinterface 102 2 0
      //   133: return
      //   134: aload_0
      //   135: getfield 104	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:v1	Ljava/lang/Object;
      //   138: ifnonnull +11 -> 149
      //   141: aload_0
      //   142: aload_3
      //   143: invokevirtual 108	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   146: putfield 104	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:v1	Ljava/lang/Object;
      //   149: aload_0
      //   150: getfield 104	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:v1	Ljava/lang/Object;
      //   153: ifnonnull +9 -> 162
      //   156: iconst_1
      //   157: istore 9
      //   159: goto +6 -> 165
      //   162: iconst_0
      //   163: istore 9
      //   165: aload_0
      //   166: getfield 110	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:v2	Ljava/lang/Object;
      //   169: ifnonnull +11 -> 180
      //   172: aload_0
      //   173: aload_1
      //   174: invokevirtual 108	io/reactivex/internal/queue/SpscLinkedArrayQueue:poll	()Ljava/lang/Object;
      //   177: putfield 110	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:v2	Ljava/lang/Object;
      //   180: aload_0
      //   181: getfield 110	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:v2	Ljava/lang/Object;
      //   184: ifnonnull +9 -> 193
      //   187: iconst_1
      //   188: istore 10
      //   190: goto +6 -> 196
      //   193: iconst_0
      //   194: istore 10
      //   196: iload 6
      //   198: ifeq +41 -> 239
      //   201: iload 8
      //   203: ifeq +36 -> 239
      //   206: iload 9
      //   208: ifeq +31 -> 239
      //   211: iload 10
      //   213: ifeq +26 -> 239
      //   216: aload_0
      //   217: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:downstream	Lio/reactivex/Observer;
      //   220: iconst_1
      //   221: invokestatic 116	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   224: invokeinterface 120 2 0
      //   229: aload_0
      //   230: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:downstream	Lio/reactivex/Observer;
      //   233: invokeinterface 123 1 0
      //   238: return
      //   239: iload 6
      //   241: ifeq +44 -> 285
      //   244: iload 8
      //   246: ifeq +39 -> 285
      //   249: iload 9
      //   251: iload 10
      //   253: if_icmpeq +32 -> 285
      //   256: aload_0
      //   257: aload_3
      //   258: aload_1
      //   259: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:cancel	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   262: aload_0
      //   263: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:downstream	Lio/reactivex/Observer;
      //   266: iconst_0
      //   267: invokestatic 116	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   270: invokeinterface 120 2 0
      //   275: aload_0
      //   276: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:downstream	Lio/reactivex/Observer;
      //   279: invokeinterface 123 1 0
      //   284: return
      //   285: iload 9
      //   287: ifne +96 -> 383
      //   290: iload 10
      //   292: ifne +91 -> 383
      //   295: aload_0
      //   296: getfield 48	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:comparer	Lio/reactivex/functions/BiPredicate;
      //   299: aload_0
      //   300: getfield 104	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:v1	Ljava/lang/Object;
      //   303: aload_0
      //   304: getfield 110	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:v2	Ljava/lang/Object;
      //   307: invokeinterface 129 3 0
      //   312: istore 6
      //   314: iload 6
      //   316: ifne +32 -> 348
      //   319: aload_0
      //   320: aload_3
      //   321: aload_1
      //   322: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:cancel	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   325: aload_0
      //   326: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:downstream	Lio/reactivex/Observer;
      //   329: iconst_0
      //   330: invokestatic 116	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   333: invokeinterface 120 2 0
      //   338: aload_0
      //   339: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:downstream	Lio/reactivex/Observer;
      //   342: invokeinterface 123 1 0
      //   347: return
      //   348: aload_0
      //   349: aconst_null
      //   350: putfield 104	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:v1	Ljava/lang/Object;
      //   353: aload_0
      //   354: aconst_null
      //   355: putfield 110	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:v2	Ljava/lang/Object;
      //   358: goto +25 -> 383
      //   361: astore_2
      //   362: aload_2
      //   363: invokestatic 134	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   366: aload_0
      //   367: aload_3
      //   368: aload_1
      //   369: invokevirtual 96	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:cancel	(Lio/reactivex/internal/queue/SpscLinkedArrayQueue;Lio/reactivex/internal/queue/SpscLinkedArrayQueue;)V
      //   372: aload_0
      //   373: getfield 42	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:downstream	Lio/reactivex/Observer;
      //   376: aload_2
      //   377: invokeinterface 102 2 0
      //   382: return
      //   383: iload 9
      //   385: ifne +8 -> 393
      //   388: iload 10
      //   390: ifeq -354 -> 36
      //   393: aload_0
      //   394: iload 5
      //   396: ineg
      //   397: invokevirtual 138	io/reactivex/internal/operators/observable/ObservableSequenceEqual$EqualCoordinator:addAndGet	(I)I
      //   400: istore 9
      //   402: iload 9
      //   404: istore 5
      //   406: iload 9
      //   408: ifne -372 -> 36
      //   411: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	412	0	this	EqualCoordinator
      //   12	357	1	localObject1	Object
      //   16	48	2	localObject2	Object
      //   361	16	2	localThrowable1	Throwable
      //   21	347	3	localSpscLinkedArrayQueue	SpscLinkedArrayQueue
      //   25	80	4	localObject3	Object
      //   34	371	5	i	int
      //   56	259	6	bool1	boolean
      //   67	60	7	localThrowable2	Throwable
      //   97	148	8	bool2	boolean
      //   157	250	9	j	int
      //   188	201	10	k	int
      // Exception table:
      //   from	to	target	type
      //   295	314	361	finally
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
      ObservableSequenceEqual.EqualObserver[] arrayOfEqualObserver = this.observers;
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
    final ObservableSequenceEqual.EqualCoordinator<T> parent;
    final SpscLinkedArrayQueue<T> queue;
    
    EqualObserver(ObservableSequenceEqual.EqualCoordinator<T> paramEqualCoordinator, int paramInt1, int paramInt2)
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
