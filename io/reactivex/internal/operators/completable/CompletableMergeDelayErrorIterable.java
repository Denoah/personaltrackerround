package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;

public final class CompletableMergeDelayErrorIterable
  extends Completable
{
  final Iterable<? extends CompletableSource> sources;
  
  public CompletableMergeDelayErrorIterable(Iterable<? extends CompletableSource> paramIterable)
  {
    this.sources = paramIterable;
  }
  
  /* Error */
  public void subscribeActual(io.reactivex.CompletableObserver paramCompletableObserver)
  {
    // Byte code:
    //   0: new 21	io/reactivex/disposables/CompositeDisposable
    //   3: dup
    //   4: invokespecial 22	io/reactivex/disposables/CompositeDisposable:<init>	()V
    //   7: astore_2
    //   8: aload_1
    //   9: aload_2
    //   10: invokeinterface 28 2 0
    //   15: aload_0
    //   16: getfield 14	io/reactivex/internal/operators/completable/CompletableMergeDelayErrorIterable:sources	Ljava/lang/Iterable;
    //   19: invokeinterface 34 1 0
    //   24: ldc 36
    //   26: invokestatic 42	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   29: checkcast 44	java/util/Iterator
    //   32: astore_3
    //   33: new 46	java/util/concurrent/atomic/AtomicInteger
    //   36: dup
    //   37: iconst_1
    //   38: invokespecial 49	java/util/concurrent/atomic/AtomicInteger:<init>	(I)V
    //   41: astore 4
    //   43: new 51	io/reactivex/internal/util/AtomicThrowable
    //   46: dup
    //   47: invokespecial 52	io/reactivex/internal/util/AtomicThrowable:<init>	()V
    //   50: astore 5
    //   52: aload_2
    //   53: invokevirtual 56	io/reactivex/disposables/CompositeDisposable:isDisposed	()Z
    //   56: ifeq +4 -> 60
    //   59: return
    //   60: aload_3
    //   61: invokeinterface 59 1 0
    //   66: istore 6
    //   68: iload 6
    //   70: ifne +6 -> 76
    //   73: goto +91 -> 164
    //   76: aload_2
    //   77: invokevirtual 56	io/reactivex/disposables/CompositeDisposable:isDisposed	()Z
    //   80: ifeq +4 -> 84
    //   83: return
    //   84: aload_3
    //   85: invokeinterface 63 1 0
    //   90: ldc 65
    //   92: invokestatic 42	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   95: checkcast 67	io/reactivex/CompletableSource
    //   98: astore 7
    //   100: aload_2
    //   101: invokevirtual 56	io/reactivex/disposables/CompositeDisposable:isDisposed	()Z
    //   104: ifeq +4 -> 108
    //   107: return
    //   108: aload 4
    //   110: invokevirtual 71	java/util/concurrent/atomic/AtomicInteger:getAndIncrement	()I
    //   113: pop
    //   114: aload 7
    //   116: new 73	io/reactivex/internal/operators/completable/CompletableMergeDelayErrorArray$MergeInnerCompletableObserver
    //   119: dup
    //   120: aload_1
    //   121: aload_2
    //   122: aload 5
    //   124: aload 4
    //   126: invokespecial 76	io/reactivex/internal/operators/completable/CompletableMergeDelayErrorArray$MergeInnerCompletableObserver:<init>	(Lio/reactivex/CompletableObserver;Lio/reactivex/disposables/CompositeDisposable;Lio/reactivex/internal/util/AtomicThrowable;Ljava/util/concurrent/atomic/AtomicInteger;)V
    //   129: invokeinterface 79 2 0
    //   134: goto -82 -> 52
    //   137: astore_3
    //   138: aload_3
    //   139: invokestatic 85	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   142: aload 5
    //   144: aload_3
    //   145: invokevirtual 89	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
    //   148: pop
    //   149: goto +15 -> 164
    //   152: astore_3
    //   153: aload_3
    //   154: invokestatic 85	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   157: aload 5
    //   159: aload_3
    //   160: invokevirtual 89	io/reactivex/internal/util/AtomicThrowable:addThrowable	(Ljava/lang/Throwable;)Z
    //   163: pop
    //   164: aload 4
    //   166: invokevirtual 92	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   169: ifne +32 -> 201
    //   172: aload 5
    //   174: invokevirtual 96	io/reactivex/internal/util/AtomicThrowable:terminate	()Ljava/lang/Throwable;
    //   177: astore 5
    //   179: aload 5
    //   181: ifnonnull +12 -> 193
    //   184: aload_1
    //   185: invokeinterface 99 1 0
    //   190: goto +11 -> 201
    //   193: aload_1
    //   194: aload 5
    //   196: invokeinterface 102 2 0
    //   201: return
    //   202: astore 5
    //   204: aload 5
    //   206: invokestatic 85	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   209: aload_1
    //   210: aload 5
    //   212: invokeinterface 102 2 0
    //   217: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	218	0	this	CompletableMergeDelayErrorIterable
    //   0	218	1	paramCompletableObserver	io.reactivex.CompletableObserver
    //   7	115	2	localCompositeDisposable	io.reactivex.disposables.CompositeDisposable
    //   32	53	3	localIterator	java.util.Iterator
    //   137	8	3	localThrowable1	Throwable
    //   152	8	3	localThrowable2	Throwable
    //   41	124	4	localAtomicInteger	java.util.concurrent.atomic.AtomicInteger
    //   50	145	5	localObject	Object
    //   202	9	5	localThrowable3	Throwable
    //   66	3	6	bool	boolean
    //   98	17	7	localCompletableSource	CompletableSource
    // Exception table:
    //   from	to	target	type
    //   84	100	137	finally
    //   60	68	152	finally
    //   15	33	202	finally
  }
}
