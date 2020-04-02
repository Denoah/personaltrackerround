package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiPredicate;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Publisher;

public final class FlowableSequenceEqualSingle<T>
  extends Single<Boolean>
  implements FuseToFlowable<Boolean>
{
  final BiPredicate<? super T, ? super T> comparer;
  final Publisher<? extends T> first;
  final int prefetch;
  final Publisher<? extends T> second;
  
  public FlowableSequenceEqualSingle(Publisher<? extends T> paramPublisher1, Publisher<? extends T> paramPublisher2, BiPredicate<? super T, ? super T> paramBiPredicate, int paramInt)
  {
    this.first = paramPublisher1;
    this.second = paramPublisher2;
    this.comparer = paramBiPredicate;
    this.prefetch = paramInt;
  }
  
  public Flowable<Boolean> fuseToFlowable()
  {
    return RxJavaPlugins.onAssembly(new FlowableSequenceEqual(this.first, this.second, this.comparer, this.prefetch));
  }
  
  public void subscribeActual(SingleObserver<? super Boolean> paramSingleObserver)
  {
    EqualCoordinator localEqualCoordinator = new EqualCoordinator(paramSingleObserver, this.prefetch, this.comparer);
    paramSingleObserver.onSubscribe(localEqualCoordinator);
    localEqualCoordinator.subscribe(this.first, this.second);
  }
  
  static final class EqualCoordinator<T>
    extends AtomicInteger
    implements Disposable, FlowableSequenceEqual.EqualCoordinatorHelper
  {
    private static final long serialVersionUID = -6178010334400373240L;
    final BiPredicate<? super T, ? super T> comparer;
    final SingleObserver<? super Boolean> downstream;
    final AtomicThrowable error;
    final FlowableSequenceEqual.EqualSubscriber<T> first;
    final FlowableSequenceEqual.EqualSubscriber<T> second;
    T v1;
    T v2;
    
    EqualCoordinator(SingleObserver<? super Boolean> paramSingleObserver, int paramInt, BiPredicate<? super T, ? super T> paramBiPredicate)
    {
      this.downstream = paramSingleObserver;
      this.comparer = paramBiPredicate;
      this.first = new FlowableSequenceEqual.EqualSubscriber(this, paramInt);
      this.second = new FlowableSequenceEqual.EqualSubscriber(this, paramInt);
      this.error = new AtomicThrowable();
    }
    
    void cancelAndClear()
    {
      this.first.cancel();
      this.first.clear();
      this.second.cancel();
      this.second.clear();
    }
    
    public void dispose()
    {
      this.first.cancel();
      this.second.cancel();
      if (getAndIncrement() == 0)
      {
        this.first.clear();
        this.second.clear();
      }
    }
    
    /* Error */
    public void drain()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 70	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:getAndIncrement	()I
      //   4: ifeq +4 -> 8
      //   7: return
      //   8: iconst_1
      //   9: istore_1
      //   10: aload_0
      //   11: getfield 48	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:first	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   14: getfield 75	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   17: astore_2
      //   18: aload_0
      //   19: getfield 50	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:second	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   22: getfield 75	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:queue	Lio/reactivex/internal/fuseable/SimpleQueue;
      //   25: astore_3
      //   26: aload_2
      //   27: ifnull +434 -> 461
      //   30: aload_3
      //   31: ifnull +430 -> 461
      //   34: aload_0
      //   35: invokevirtual 79	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:isDisposed	()Z
      //   38: ifeq +18 -> 56
      //   41: aload_0
      //   42: getfield 48	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:first	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   45: invokevirtual 65	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:clear	()V
      //   48: aload_0
      //   49: getfield 50	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:second	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   52: invokevirtual 65	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:clear	()V
      //   55: return
      //   56: aload_0
      //   57: getfield 55	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   60: invokevirtual 83	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   63: checkcast 85	java/lang/Throwable
      //   66: ifnull +24 -> 90
      //   69: aload_0
      //   70: invokevirtual 87	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:cancelAndClear	()V
      //   73: aload_0
      //   74: getfield 39	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   77: aload_0
      //   78: getfield 55	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   81: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   84: invokeinterface 97 2 0
      //   89: return
      //   90: aload_0
      //   91: getfield 48	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:first	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   94: getfield 101	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:done	Z
      //   97: istore 4
      //   99: aload_0
      //   100: getfield 103	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:v1	Ljava/lang/Object;
      //   103: astore 5
      //   105: aload 5
      //   107: astore 6
      //   109: aload 5
      //   111: ifnonnull +58 -> 169
      //   114: aload_2
      //   115: invokeinterface 108 1 0
      //   120: astore 6
      //   122: aload_0
      //   123: aload 6
      //   125: putfield 103	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:v1	Ljava/lang/Object;
      //   128: goto +41 -> 169
      //   131: astore 6
      //   133: aload 6
      //   135: invokestatic 113	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   138: aload_0
      //   139: invokevirtual 87	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:cancelAndClear	()V
      //   142: aload_0
      //   143: getfield 55	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   146: aload 6
      //   148: invokevirtual 117	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   151: pop
      //   152: aload_0
      //   153: getfield 39	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   156: aload_0
      //   157: getfield 55	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   160: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   163: invokeinterface 97 2 0
      //   168: return
      //   169: aload 6
      //   171: ifnonnull +9 -> 180
      //   174: iconst_1
      //   175: istore 7
      //   177: goto +6 -> 183
      //   180: iconst_0
      //   181: istore 7
      //   183: aload_0
      //   184: getfield 50	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:second	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   187: getfield 101	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:done	Z
      //   190: istore 8
      //   192: aload_0
      //   193: getfield 119	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:v2	Ljava/lang/Object;
      //   196: astore 9
      //   198: aload 9
      //   200: astore 5
      //   202: aload 9
      //   204: ifnonnull +58 -> 262
      //   207: aload_3
      //   208: invokeinterface 108 1 0
      //   213: astore 5
      //   215: aload_0
      //   216: aload 5
      //   218: putfield 119	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:v2	Ljava/lang/Object;
      //   221: goto +41 -> 262
      //   224: astore 6
      //   226: aload 6
      //   228: invokestatic 113	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   231: aload_0
      //   232: invokevirtual 87	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:cancelAndClear	()V
      //   235: aload_0
      //   236: getfield 55	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   239: aload 6
      //   241: invokevirtual 117	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   244: pop
      //   245: aload_0
      //   246: getfield 39	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   249: aload_0
      //   250: getfield 55	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   253: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   256: invokeinterface 97 2 0
      //   261: return
      //   262: aload 5
      //   264: ifnonnull +9 -> 273
      //   267: iconst_1
      //   268: istore 10
      //   270: goto +6 -> 276
      //   273: iconst_0
      //   274: istore 10
      //   276: iload 4
      //   278: ifeq +32 -> 310
      //   281: iload 8
      //   283: ifeq +27 -> 310
      //   286: iload 7
      //   288: ifeq +22 -> 310
      //   291: iload 10
      //   293: ifeq +17 -> 310
      //   296: aload_0
      //   297: getfield 39	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   300: iconst_1
      //   301: invokestatic 125	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   304: invokeinterface 129 2 0
      //   309: return
      //   310: iload 4
      //   312: ifeq +33 -> 345
      //   315: iload 8
      //   317: ifeq +28 -> 345
      //   320: iload 7
      //   322: iload 10
      //   324: if_icmpeq +21 -> 345
      //   327: aload_0
      //   328: invokevirtual 87	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:cancelAndClear	()V
      //   331: aload_0
      //   332: getfield 39	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   335: iconst_0
      //   336: invokestatic 125	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   339: invokeinterface 129 2 0
      //   344: return
      //   345: iload 7
      //   347: ifne +170 -> 517
      //   350: iload 10
      //   352: ifeq +6 -> 358
      //   355: goto +162 -> 517
      //   358: aload_0
      //   359: getfield 41	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:comparer	Lio/reactivex/functions/BiPredicate;
      //   362: aload 6
      //   364: aload 5
      //   366: invokeinterface 135 3 0
      //   371: istore 4
      //   373: iload 4
      //   375: ifne +21 -> 396
      //   378: aload_0
      //   379: invokevirtual 87	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:cancelAndClear	()V
      //   382: aload_0
      //   383: getfield 39	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   386: iconst_0
      //   387: invokestatic 125	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
      //   390: invokeinterface 129 2 0
      //   395: return
      //   396: aload_0
      //   397: aconst_null
      //   398: putfield 103	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:v1	Ljava/lang/Object;
      //   401: aload_0
      //   402: aconst_null
      //   403: putfield 119	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:v2	Ljava/lang/Object;
      //   406: aload_0
      //   407: getfield 48	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:first	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   410: invokevirtual 138	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:request	()V
      //   413: aload_0
      //   414: getfield 50	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:second	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   417: invokevirtual 138	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:request	()V
      //   420: goto -386 -> 34
      //   423: astore 6
      //   425: aload 6
      //   427: invokestatic 113	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   430: aload_0
      //   431: invokevirtual 87	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:cancelAndClear	()V
      //   434: aload_0
      //   435: getfield 55	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   438: aload 6
      //   440: invokevirtual 117	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
      //   443: pop
      //   444: aload_0
      //   445: getfield 39	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   448: aload_0
      //   449: getfield 55	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   452: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   455: invokeinterface 97 2 0
      //   460: return
      //   461: aload_0
      //   462: invokevirtual 79	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:isDisposed	()Z
      //   465: ifeq +18 -> 483
      //   468: aload_0
      //   469: getfield 48	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:first	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   472: invokevirtual 65	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:clear	()V
      //   475: aload_0
      //   476: getfield 50	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:second	Lio/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber;
      //   479: invokevirtual 65	io/reactivex/internal/operators/flowable/FlowableSequenceEqual$EqualSubscriber:clear	()V
      //   482: return
      //   483: aload_0
      //   484: getfield 55	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   487: invokevirtual 83	io/reactivex/internal/util/AtomicThrowable:get	()Ljava/lang/Object;
      //   490: checkcast 85	java/lang/Throwable
      //   493: ifnull +24 -> 517
      //   496: aload_0
      //   497: invokevirtual 87	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:cancelAndClear	()V
      //   500: aload_0
      //   501: getfield 39	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:downstream	Lio/reactivex/SingleObserver;
      //   504: aload_0
      //   505: getfield 55	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:error	Lio/reactivex/internal/util/AtomicThrowable;
      //   508: invokevirtual 91	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
      //   511: invokeinterface 97 2 0
      //   516: return
      //   517: aload_0
      //   518: iload_1
      //   519: ineg
      //   520: invokevirtual 142	io/reactivex/internal/operators/flowable/FlowableSequenceEqualSingle$EqualCoordinator:addAndGet	(I)I
      //   523: istore 7
      //   525: iload 7
      //   527: istore_1
      //   528: iload 7
      //   530: ifne -520 -> 10
      //   533: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	534	0	this	EqualCoordinator
      //   9	519	1	i	int
      //   17	98	2	localSimpleQueue1	io.reactivex.internal.fuseable.SimpleQueue
      //   25	183	3	localSimpleQueue2	io.reactivex.internal.fuseable.SimpleQueue
      //   97	277	4	bool1	boolean
      //   103	262	5	localObject1	Object
      //   107	17	6	localObject2	Object
      //   131	39	6	localThrowable1	Throwable
      //   224	139	6	localThrowable2	Throwable
      //   423	16	6	localThrowable3	Throwable
      //   175	354	7	j	int
      //   190	126	8	bool2	boolean
      //   196	7	9	localObject3	Object
      //   268	83	10	k	int
      // Exception table:
      //   from	to	target	type
      //   114	122	131	finally
      //   207	215	224	finally
      //   358	373	423	finally
    }
    
    public void innerError(Throwable paramThrowable)
    {
      if (this.error.addThrowable(paramThrowable)) {
        drain();
      } else {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public boolean isDisposed()
    {
      boolean bool;
      if (this.first.get() == SubscriptionHelper.CANCELLED) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    void subscribe(Publisher<? extends T> paramPublisher1, Publisher<? extends T> paramPublisher2)
    {
      paramPublisher1.subscribe(this.first);
      paramPublisher2.subscribe(this.second);
    }
  }
}
