package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public final class CompletableUsing<R>
  extends Completable
{
  final Function<? super R, ? extends CompletableSource> completableFunction;
  final Consumer<? super R> disposer;
  final boolean eager;
  final Callable<R> resourceSupplier;
  
  public CompletableUsing(Callable<R> paramCallable, Function<? super R, ? extends CompletableSource> paramFunction, Consumer<? super R> paramConsumer, boolean paramBoolean)
  {
    this.resourceSupplier = paramCallable;
    this.completableFunction = paramFunction;
    this.disposer = paramConsumer;
    this.eager = paramBoolean;
  }
  
  /* Error */
  protected void subscribeActual(CompletableObserver paramCompletableObserver)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 26	io/reactivex/internal/operators/completable/CompletableUsing:resourceSupplier	Ljava/util/concurrent/Callable;
    //   4: invokeinterface 43 1 0
    //   9: astore_2
    //   10: aload_0
    //   11: getfield 28	io/reactivex/internal/operators/completable/CompletableUsing:completableFunction	Lio/reactivex/functions/Function;
    //   14: aload_2
    //   15: invokeinterface 49 2 0
    //   20: ldc 51
    //   22: invokestatic 57	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   25: checkcast 59	io/reactivex/CompletableSource
    //   28: astore_3
    //   29: aload_3
    //   30: new 7	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver
    //   33: dup
    //   34: aload_1
    //   35: aload_2
    //   36: aload_0
    //   37: getfield 30	io/reactivex/internal/operators/completable/CompletableUsing:disposer	Lio/reactivex/functions/Consumer;
    //   40: aload_0
    //   41: getfield 32	io/reactivex/internal/operators/completable/CompletableUsing:eager	Z
    //   44: invokespecial 62	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:<init>	(Lio/reactivex/CompletableObserver;Ljava/lang/Object;Lio/reactivex/functions/Consumer;Z)V
    //   47: invokeinterface 65 2 0
    //   52: return
    //   53: astore_3
    //   54: aload_3
    //   55: invokestatic 71	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   58: aload_0
    //   59: getfield 32	io/reactivex/internal/operators/completable/CompletableUsing:eager	Z
    //   62: ifeq +45 -> 107
    //   65: aload_0
    //   66: getfield 30	io/reactivex/internal/operators/completable/CompletableUsing:disposer	Lio/reactivex/functions/Consumer;
    //   69: aload_2
    //   70: invokeinterface 77 2 0
    //   75: goto +32 -> 107
    //   78: astore_2
    //   79: aload_2
    //   80: invokestatic 71	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   83: new 79	io/reactivex/exceptions/CompositeException
    //   86: dup
    //   87: iconst_2
    //   88: anewarray 81	java/lang/Throwable
    //   91: dup
    //   92: iconst_0
    //   93: aload_3
    //   94: aastore
    //   95: dup
    //   96: iconst_1
    //   97: aload_2
    //   98: aastore
    //   99: invokespecial 84	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   102: aload_1
    //   103: invokestatic 90	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/CompletableObserver;)V
    //   106: return
    //   107: aload_3
    //   108: aload_1
    //   109: invokestatic 90	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/CompletableObserver;)V
    //   112: aload_0
    //   113: getfield 32	io/reactivex/internal/operators/completable/CompletableUsing:eager	Z
    //   116: ifne +25 -> 141
    //   119: aload_0
    //   120: getfield 30	io/reactivex/internal/operators/completable/CompletableUsing:disposer	Lio/reactivex/functions/Consumer;
    //   123: aload_2
    //   124: invokeinterface 77 2 0
    //   129: goto +12 -> 141
    //   132: astore_1
    //   133: aload_1
    //   134: invokestatic 71	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   137: aload_1
    //   138: invokestatic 95	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   141: return
    //   142: astore_2
    //   143: aload_2
    //   144: invokestatic 71	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   147: aload_2
    //   148: aload_1
    //   149: invokestatic 90	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/CompletableObserver;)V
    //   152: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	153	0	this	CompletableUsing
    //   0	153	1	paramCompletableObserver	CompletableObserver
    //   9	61	2	localObject	Object
    //   78	46	2	localThrowable1	Throwable
    //   142	6	2	localThrowable2	Throwable
    //   28	2	3	localCompletableSource	CompletableSource
    //   53	55	3	localThrowable3	Throwable
    // Exception table:
    //   from	to	target	type
    //   10	29	53	finally
    //   65	75	78	finally
    //   119	129	132	finally
    //   0	10	142	finally
  }
  
  static final class UsingObserver<R>
    extends AtomicReference<Object>
    implements CompletableObserver, Disposable
  {
    private static final long serialVersionUID = -674404550052917487L;
    final Consumer<? super R> disposer;
    final CompletableObserver downstream;
    final boolean eager;
    Disposable upstream;
    
    UsingObserver(CompletableObserver paramCompletableObserver, R paramR, Consumer<? super R> paramConsumer, boolean paramBoolean)
    {
      super();
      this.downstream = paramCompletableObserver;
      this.disposer = paramConsumer;
      this.eager = paramBoolean;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      this.upstream = DisposableHelper.DISPOSED;
      disposeResourceAfter();
    }
    
    /* Error */
    void disposeResourceAfter()
    {
      // Byte code:
      //   0: aload_0
      //   1: aload_0
      //   2: invokevirtual 58	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
      //   5: astore_1
      //   6: aload_1
      //   7: aload_0
      //   8: if_acmpeq +25 -> 33
      //   11: aload_0
      //   12: getfield 34	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:disposer	Lio/reactivex/functions/Consumer;
      //   15: aload_1
      //   16: invokeinterface 63 2 0
      //   21: goto +12 -> 33
      //   24: astore_1
      //   25: aload_1
      //   26: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   29: aload_1
      //   30: invokestatic 74	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   33: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	34	0	this	UsingObserver
      //   5	11	1	localObject	Object
      //   24	6	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   11	21	24	finally
    }
    
    public boolean isDisposed()
    {
      return this.upstream.isDisposed();
    }
    
    /* Error */
    public void onComplete()
    {
      // Byte code:
      //   0: aload_0
      //   1: getstatic 51	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
      //   4: putfield 43	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   7: aload_0
      //   8: getfield 36	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:eager	Z
      //   11: ifeq +43 -> 54
      //   14: aload_0
      //   15: aload_0
      //   16: invokevirtual 58	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
      //   19: astore_1
      //   20: aload_1
      //   21: aload_0
      //   22: if_acmpeq +31 -> 53
      //   25: aload_0
      //   26: getfield 34	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:disposer	Lio/reactivex/functions/Consumer;
      //   29: aload_1
      //   30: invokeinterface 63 2 0
      //   35: goto +19 -> 54
      //   38: astore_1
      //   39: aload_1
      //   40: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   43: aload_0
      //   44: getfield 32	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:downstream	Lio/reactivex/CompletableObserver;
      //   47: aload_1
      //   48: invokeinterface 80 2 0
      //   53: return
      //   54: aload_0
      //   55: getfield 32	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:downstream	Lio/reactivex/CompletableObserver;
      //   58: invokeinterface 82 1 0
      //   63: aload_0
      //   64: getfield 36	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:eager	Z
      //   67: ifne +7 -> 74
      //   70: aload_0
      //   71: invokevirtual 54	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:disposeResourceAfter	()V
      //   74: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	75	0	this	UsingObserver
      //   19	11	1	localObject	Object
      //   38	10	1	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   25	35	38	finally
    }
    
    /* Error */
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getstatic 51	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
      //   4: putfield 43	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   7: aload_1
      //   8: astore_2
      //   9: aload_0
      //   10: getfield 36	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:eager	Z
      //   13: ifeq +58 -> 71
      //   16: aload_0
      //   17: aload_0
      //   18: invokevirtual 58	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
      //   21: astore_2
      //   22: aload_2
      //   23: aload_0
      //   24: if_acmpeq +46 -> 70
      //   27: aload_0
      //   28: getfield 34	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:disposer	Lio/reactivex/functions/Consumer;
      //   31: aload_2
      //   32: invokeinterface 63 2 0
      //   37: aload_1
      //   38: astore_2
      //   39: goto +32 -> 71
      //   42: astore_2
      //   43: aload_2
      //   44: invokestatic 69	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   47: new 84	io/reactivex/exceptions/CompositeException
      //   50: dup
      //   51: iconst_2
      //   52: anewarray 86	java/lang/Throwable
      //   55: dup
      //   56: iconst_0
      //   57: aload_1
      //   58: aastore
      //   59: dup
      //   60: iconst_1
      //   61: aload_2
      //   62: aastore
      //   63: invokespecial 89	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   66: astore_2
      //   67: goto +4 -> 71
      //   70: return
      //   71: aload_0
      //   72: getfield 32	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:downstream	Lio/reactivex/CompletableObserver;
      //   75: aload_2
      //   76: invokeinterface 80 2 0
      //   81: aload_0
      //   82: getfield 36	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:eager	Z
      //   85: ifne +7 -> 92
      //   88: aload_0
      //   89: invokevirtual 54	io/reactivex/internal/operators/completable/CompletableUsing$UsingObserver:disposeResourceAfter	()V
      //   92: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	93	0	this	UsingObserver
      //   0	93	1	paramThrowable	Throwable
      //   8	31	2	localObject	Object
      //   42	20	2	localThrowable	Throwable
      //   66	10	2	localCompositeException	io.reactivex.exceptions.CompositeException
      // Exception table:
      //   from	to	target	type
      //   27	37	42	finally
    }
    
    public void onSubscribe(Disposable paramDisposable)
    {
      if (DisposableHelper.validate(this.upstream, paramDisposable))
      {
        this.upstream = paramDisposable;
        this.downstream.onSubscribe(this);
      }
    }
  }
}
