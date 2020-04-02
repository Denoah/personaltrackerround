package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleUsing<T, U>
  extends Single<T>
{
  final Consumer<? super U> disposer;
  final boolean eager;
  final Callable<U> resourceSupplier;
  final Function<? super U, ? extends SingleSource<? extends T>> singleFunction;
  
  public SingleUsing(Callable<U> paramCallable, Function<? super U, ? extends SingleSource<? extends T>> paramFunction, Consumer<? super U> paramConsumer, boolean paramBoolean)
  {
    this.resourceSupplier = paramCallable;
    this.singleFunction = paramFunction;
    this.disposer = paramConsumer;
    this.eager = paramBoolean;
  }
  
  /* Error */
  protected void subscribeActual(SingleObserver<? super T> paramSingleObserver)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 26	io/reactivex/internal/operators/single/SingleUsing:resourceSupplier	Ljava/util/concurrent/Callable;
    //   4: invokeinterface 43 1 0
    //   9: astore_2
    //   10: aload_0
    //   11: getfield 28	io/reactivex/internal/operators/single/SingleUsing:singleFunction	Lio/reactivex/functions/Function;
    //   14: aload_2
    //   15: invokeinterface 49 2 0
    //   20: ldc 51
    //   22: invokestatic 57	io/reactivex/internal/functions/ObjectHelper:requireNonNull	(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    //   25: checkcast 59	io/reactivex/SingleSource
    //   28: astore_3
    //   29: aload_3
    //   30: new 7	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver
    //   33: dup
    //   34: aload_1
    //   35: aload_2
    //   36: aload_0
    //   37: getfield 32	io/reactivex/internal/operators/single/SingleUsing:eager	Z
    //   40: aload_0
    //   41: getfield 30	io/reactivex/internal/operators/single/SingleUsing:disposer	Lio/reactivex/functions/Consumer;
    //   44: invokespecial 62	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:<init>	(Lio/reactivex/SingleObserver;Ljava/lang/Object;ZLio/reactivex/functions/Consumer;)V
    //   47: invokeinterface 65 2 0
    //   52: return
    //   53: astore_3
    //   54: aload_3
    //   55: invokestatic 71	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   58: aload_3
    //   59: astore 4
    //   61: aload_0
    //   62: getfield 32	io/reactivex/internal/operators/single/SingleUsing:eager	Z
    //   65: ifeq +48 -> 113
    //   68: aload_0
    //   69: getfield 30	io/reactivex/internal/operators/single/SingleUsing:disposer	Lio/reactivex/functions/Consumer;
    //   72: aload_2
    //   73: invokeinterface 77 2 0
    //   78: aload_3
    //   79: astore 4
    //   81: goto +32 -> 113
    //   84: astore 4
    //   86: aload 4
    //   88: invokestatic 71	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   91: new 79	io/reactivex/exceptions/CompositeException
    //   94: dup
    //   95: iconst_2
    //   96: anewarray 81	java/lang/Throwable
    //   99: dup
    //   100: iconst_0
    //   101: aload_3
    //   102: aastore
    //   103: dup
    //   104: iconst_1
    //   105: aload 4
    //   107: aastore
    //   108: invokespecial 84	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   111: astore 4
    //   113: aload 4
    //   115: aload_1
    //   116: invokestatic 90	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/SingleObserver;)V
    //   119: aload_0
    //   120: getfield 32	io/reactivex/internal/operators/single/SingleUsing:eager	Z
    //   123: ifne +25 -> 148
    //   126: aload_0
    //   127: getfield 30	io/reactivex/internal/operators/single/SingleUsing:disposer	Lio/reactivex/functions/Consumer;
    //   130: aload_2
    //   131: invokeinterface 77 2 0
    //   136: goto +12 -> 148
    //   139: astore_1
    //   140: aload_1
    //   141: invokestatic 71	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   144: aload_1
    //   145: invokestatic 95	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   148: return
    //   149: astore_3
    //   150: aload_3
    //   151: invokestatic 71	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   154: aload_3
    //   155: aload_1
    //   156: invokestatic 90	io/reactivex/internal/disposables/EmptyDisposable:error	(Ljava/lang/Throwable;Lio/reactivex/SingleObserver;)V
    //   159: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	160	0	this	SingleUsing
    //   0	160	1	paramSingleObserver	SingleObserver<? super T>
    //   9	122	2	localObject	Object
    //   28	2	3	localSingleSource	SingleSource
    //   53	49	3	localThrowable1	Throwable
    //   149	6	3	localThrowable2	Throwable
    //   59	21	4	localThrowable3	Throwable
    //   84	22	4	localThrowable4	Throwable
    //   111	3	4	localCompositeException	io.reactivex.exceptions.CompositeException
    // Exception table:
    //   from	to	target	type
    //   10	29	53	finally
    //   68	78	84	finally
    //   126	136	139	finally
    //   0	10	149	finally
  }
  
  static final class UsingSingleObserver<T, U>
    extends AtomicReference<Object>
    implements SingleObserver<T>, Disposable
  {
    private static final long serialVersionUID = -5331524057054083935L;
    final Consumer<? super U> disposer;
    final SingleObserver<? super T> downstream;
    final boolean eager;
    Disposable upstream;
    
    UsingSingleObserver(SingleObserver<? super T> paramSingleObserver, U paramU, boolean paramBoolean, Consumer<? super U> paramConsumer)
    {
      super();
      this.downstream = paramSingleObserver;
      this.eager = paramBoolean;
      this.disposer = paramConsumer;
    }
    
    public void dispose()
    {
      this.upstream.dispose();
      this.upstream = DisposableHelper.DISPOSED;
      disposeAfter();
    }
    
    /* Error */
    void disposeAfter()
    {
      // Byte code:
      //   0: aload_0
      //   1: aload_0
      //   2: invokevirtual 59	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
      //   5: astore_1
      //   6: aload_1
      //   7: aload_0
      //   8: if_acmpeq +25 -> 33
      //   11: aload_0
      //   12: getfield 37	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:disposer	Lio/reactivex/functions/Consumer;
      //   15: aload_1
      //   16: invokeinterface 64 2 0
      //   21: goto +12 -> 33
      //   24: astore_1
      //   25: aload_1
      //   26: invokestatic 70	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   29: aload_1
      //   30: invokestatic 75	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
      //   33: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	34	0	this	UsingSingleObserver
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
    public void onError(Throwable paramThrowable)
    {
      // Byte code:
      //   0: aload_0
      //   1: getstatic 52	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
      //   4: putfield 44	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   7: aload_1
      //   8: astore_2
      //   9: aload_0
      //   10: getfield 35	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:eager	Z
      //   13: ifeq +58 -> 71
      //   16: aload_0
      //   17: aload_0
      //   18: invokevirtual 59	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
      //   21: astore_2
      //   22: aload_2
      //   23: aload_0
      //   24: if_acmpeq +46 -> 70
      //   27: aload_0
      //   28: getfield 37	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:disposer	Lio/reactivex/functions/Consumer;
      //   31: aload_2
      //   32: invokeinterface 64 2 0
      //   37: aload_1
      //   38: astore_2
      //   39: goto +32 -> 71
      //   42: astore_2
      //   43: aload_2
      //   44: invokestatic 70	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   47: new 81	io/reactivex/exceptions/CompositeException
      //   50: dup
      //   51: iconst_2
      //   52: anewarray 83	java/lang/Throwable
      //   55: dup
      //   56: iconst_0
      //   57: aload_1
      //   58: aastore
      //   59: dup
      //   60: iconst_1
      //   61: aload_2
      //   62: aastore
      //   63: invokespecial 86	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
      //   66: astore_2
      //   67: goto +4 -> 71
      //   70: return
      //   71: aload_0
      //   72: getfield 33	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:downstream	Lio/reactivex/SingleObserver;
      //   75: aload_2
      //   76: invokeinterface 87 2 0
      //   81: aload_0
      //   82: getfield 35	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:eager	Z
      //   85: ifne +7 -> 92
      //   88: aload_0
      //   89: invokevirtual 55	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:disposeAfter	()V
      //   92: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	93	0	this	UsingSingleObserver
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
    
    /* Error */
    public void onSuccess(T paramT)
    {
      // Byte code:
      //   0: aload_0
      //   1: getstatic 52	io/reactivex/internal/disposables/DisposableHelper:DISPOSED	Lio/reactivex/internal/disposables/DisposableHelper;
      //   4: putfield 44	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:upstream	Lio/reactivex/disposables/Disposable;
      //   7: aload_0
      //   8: getfield 35	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:eager	Z
      //   11: ifeq +43 -> 54
      //   14: aload_0
      //   15: aload_0
      //   16: invokevirtual 59	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:getAndSet	(Ljava/lang/Object;)Ljava/lang/Object;
      //   19: astore_2
      //   20: aload_2
      //   21: aload_0
      //   22: if_acmpeq +31 -> 53
      //   25: aload_0
      //   26: getfield 37	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:disposer	Lio/reactivex/functions/Consumer;
      //   29: aload_2
      //   30: invokeinterface 64 2 0
      //   35: goto +19 -> 54
      //   38: astore_1
      //   39: aload_1
      //   40: invokestatic 70	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   43: aload_0
      //   44: getfield 33	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:downstream	Lio/reactivex/SingleObserver;
      //   47: aload_1
      //   48: invokeinterface 87 2 0
      //   53: return
      //   54: aload_0
      //   55: getfield 33	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:downstream	Lio/reactivex/SingleObserver;
      //   58: aload_1
      //   59: invokeinterface 98 2 0
      //   64: aload_0
      //   65: getfield 35	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:eager	Z
      //   68: ifne +7 -> 75
      //   71: aload_0
      //   72: invokevirtual 55	io/reactivex/internal/operators/single/SingleUsing$UsingSingleObserver:disposeAfter	()V
      //   75: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	76	0	this	UsingSingleObserver
      //   0	76	1	paramT	T
      //   19	11	2	localObject	Object
      // Exception table:
      //   from	to	target	type
      //   25	35	38	finally
    }
  }
}
