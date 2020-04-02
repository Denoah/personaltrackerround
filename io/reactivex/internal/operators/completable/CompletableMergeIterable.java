package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableMergeIterable
  extends Completable
{
  final Iterable<? extends CompletableSource> sources;
  
  public CompletableMergeIterable(Iterable<? extends CompletableSource> paramIterable)
  {
    this.sources = paramIterable;
  }
  
  /* Error */
  public void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    // Byte code:
    //   0: new 24	io/reactivex/disposables/CompositeDisposable
    //   3: dup
    //   4: invokespecial 25	io/reactivex/disposables/CompositeDisposable:<init>	()V
    //   7: astore_2
    //   8: aload_1
    //   9: aload_2
    //   10: invokeinterface 31 2 0
    //   15: aload_0
    //   16: getfield 17	io/reactivex/internal/operators/completable/CompletableMergeIterable:sources	Ljava/lang/Iterable;
    //   19: invokeinterface 37 1 0
    //   24: ldc 39
    //   26: invokestatic 45	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   29: checkcast 47	java/util/Iterator
    //   32: astore_3
    //   33: new 49	java/util/concurrent/atomic/AtomicInteger
    //   36: dup
    //   37: iconst_1
    //   38: invokespecial 52	java/util/concurrent/atomic/AtomicInteger:<init>	(I)V
    //   41: astore 4
    //   43: new 6	io/reactivex/internal/operators/completable/CompletableMergeIterable$MergeCompletableObserver
    //   46: dup
    //   47: aload_1
    //   48: aload_2
    //   49: aload 4
    //   51: invokespecial 55	io/reactivex/internal/operators/completable/CompletableMergeIterable$MergeCompletableObserver:<init>	(Lio/reactivex/CompletableObserver;Lio/reactivex/disposables/CompositeDisposable;Ljava/util/concurrent/atomic/AtomicInteger;)V
    //   54: astore_1
    //   55: aload_2
    //   56: invokevirtual 59	io/reactivex/disposables/CompositeDisposable:isDisposed	()Z
    //   59: ifeq +4 -> 63
    //   62: return
    //   63: aload_3
    //   64: invokeinterface 62 1 0
    //   69: istore 5
    //   71: iload 5
    //   73: ifne +8 -> 81
    //   76: aload_1
    //   77: invokevirtual 65	io/reactivex/internal/operators/completable/CompletableMergeIterable$MergeCompletableObserver:onComplete	()V
    //   80: return
    //   81: aload_2
    //   82: invokevirtual 59	io/reactivex/disposables/CompositeDisposable:isDisposed	()Z
    //   85: ifeq +4 -> 89
    //   88: return
    //   89: aload_3
    //   90: invokeinterface 69 1 0
    //   95: ldc 71
    //   97: invokestatic 45	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   100: checkcast 73	io/reactivex/CompletableSource
    //   103: astore 6
    //   105: aload_2
    //   106: invokevirtual 59	io/reactivex/disposables/CompositeDisposable:isDisposed	()Z
    //   109: ifeq +4 -> 113
    //   112: return
    //   113: aload 4
    //   115: invokevirtual 77	java/util/concurrent/atomic/AtomicInteger:getAndIncrement	()I
    //   118: pop
    //   119: aload 6
    //   121: aload_1
    //   122: invokeinterface 80 2 0
    //   127: goto -72 -> 55
    //   130: astore_3
    //   131: aload_3
    //   132: invokestatic 86	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   135: aload_2
    //   136: invokevirtual 89	io/reactivex/disposables/CompositeDisposable:dispose	()V
    //   139: aload_1
    //   140: aload_3
    //   141: invokevirtual 92	io/reactivex/internal/operators/completable/CompletableMergeIterable$MergeCompletableObserver:onError	(Ljava/lang/Throwable;)V
    //   144: return
    //   145: astore_3
    //   146: aload_3
    //   147: invokestatic 86	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   150: aload_2
    //   151: invokevirtual 89	io/reactivex/disposables/CompositeDisposable:dispose	()V
    //   154: aload_1
    //   155: aload_3
    //   156: invokevirtual 92	io/reactivex/internal/operators/completable/CompletableMergeIterable$MergeCompletableObserver:onError	(Ljava/lang/Throwable;)V
    //   159: return
    //   160: astore_2
    //   161: aload_2
    //   162: invokestatic 86	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   165: aload_1
    //   166: aload_2
    //   167: invokeinterface 93 2 0
    //   172: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	173	0	this	CompletableMergeIterable
    //   0	173	1	paramCompletableObserver	CompletableObserver
    //   7	144	2	localCompositeDisposable	CompositeDisposable
    //   160	7	2	localThrowable1	Throwable
    //   32	58	3	localIterator	java.util.Iterator
    //   130	11	3	localThrowable2	Throwable
    //   145	11	3	localThrowable3	Throwable
    //   41	73	4	localAtomicInteger	AtomicInteger
    //   69	3	5	bool	boolean
    //   103	17	6	localCompletableSource	CompletableSource
    // Exception table:
    //   from	to	target	type
    //   89	105	130	finally
    //   63	71	145	finally
    //   15	33	160	finally
  }
  
  static final class MergeCompletableObserver
    extends AtomicBoolean
    implements CompletableObserver
  {
    private static final long serialVersionUID = -7730517613164279224L;
    final CompletableObserver downstream;
    final CompositeDisposable set;
    final AtomicInteger wip;
    
    MergeCompletableObserver(CompletableObserver paramCompletableObserver, CompositeDisposable paramCompositeDisposable, AtomicInteger paramAtomicInteger)
    {
      this.downstream = paramCompletableObserver;
      this.set = paramCompositeDisposable;
      this.wip = paramAtomicInteger;
    }
    
    public void onComplete()
    {
      if ((this.wip.decrementAndGet() == 0) && (compareAndSet(false, true))) {
        this.downstream.onComplete();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.set.dispose();
      if (compareAndSet(false, true)) {
        this.downstream.onError(paramThrowable);
      } else {
        RxJavaPlugins.onError(paramThrowable);
      }
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      this.set.add(paramDisposable);
    }
  }
}
