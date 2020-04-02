package io.reactivex.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public final class SafeObserver<T>
  implements Observer<T>, Disposable
{
  boolean done;
  final Observer<? super T> downstream;
  Disposable upstream;
  
  public SafeObserver(Observer<? super T> paramObserver)
  {
    this.downstream = paramObserver;
  }
  
  public void dispose()
  {
    this.upstream.dispose();
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
    //   1: getfield 38	io/reactivex/observers/SafeObserver:done	Z
    //   4: ifeq +4 -> 8
    //   7: return
    //   8: aload_0
    //   9: iconst_1
    //   10: putfield 38	io/reactivex/observers/SafeObserver:done	Z
    //   13: aload_0
    //   14: getfield 29	io/reactivex/observers/SafeObserver:upstream	Lio/reactivex/disposables/Disposable;
    //   17: ifnonnull +8 -> 25
    //   20: aload_0
    //   21: invokevirtual 41	io/reactivex/observers/SafeObserver:onCompleteNoSubscription	()V
    //   24: return
    //   25: aload_0
    //   26: getfield 23	io/reactivex/observers/SafeObserver:downstream	Lio/reactivex/Observer;
    //   29: invokeinterface 43 1 0
    //   34: goto +12 -> 46
    //   37: astore_1
    //   38: aload_1
    //   39: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   42: aload_1
    //   43: invokestatic 54	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   46: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	47	0	this	SafeObserver
    //   37	6	1	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   25	34	37	finally
  }
  
  /* Error */
  void onCompleteNoSubscription()
  {
    // Byte code:
    //   0: new 56	java/lang/NullPointerException
    //   3: dup
    //   4: ldc 58
    //   6: invokespecial 61	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   9: astore_1
    //   10: aload_0
    //   11: getfield 23	io/reactivex/observers/SafeObserver:downstream	Lio/reactivex/Observer;
    //   14: getstatic 67	io/reactivex/internal/disposables/EmptyDisposable:INSTANCE	Lio/reactivex/internal/disposables/EmptyDisposable;
    //   17: invokeinterface 71 2 0
    //   22: aload_0
    //   23: getfield 23	io/reactivex/observers/SafeObserver:downstream	Lio/reactivex/Observer;
    //   26: aload_1
    //   27: invokeinterface 72 2 0
    //   32: goto +30 -> 62
    //   35: astore_2
    //   36: aload_2
    //   37: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   40: new 74	io/reactivex/exceptions/CompositeException
    //   43: dup
    //   44: iconst_2
    //   45: anewarray 76	java/lang/Throwable
    //   48: dup
    //   49: iconst_0
    //   50: aload_1
    //   51: aastore
    //   52: dup
    //   53: iconst_1
    //   54: aload_2
    //   55: aastore
    //   56: invokespecial 79	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   59: invokestatic 54	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   62: return
    //   63: astore_2
    //   64: aload_2
    //   65: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   68: new 74	io/reactivex/exceptions/CompositeException
    //   71: dup
    //   72: iconst_2
    //   73: anewarray 76	java/lang/Throwable
    //   76: dup
    //   77: iconst_0
    //   78: aload_1
    //   79: aastore
    //   80: dup
    //   81: iconst_1
    //   82: aload_2
    //   83: aastore
    //   84: invokespecial 79	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   87: invokestatic 54	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   90: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	91	0	this	SafeObserver
    //   9	70	1	localNullPointerException	NullPointerException
    //   35	20	2	localThrowable1	Throwable
    //   63	20	2	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   22	32	35	finally
    //   10	22	63	finally
  }
  
  /* Error */
  public void onError(Throwable paramThrowable)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 38	io/reactivex/observers/SafeObserver:done	Z
    //   4: ifeq +8 -> 12
    //   7: aload_1
    //   8: invokestatic 54	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   11: return
    //   12: aload_0
    //   13: iconst_1
    //   14: putfield 38	io/reactivex/observers/SafeObserver:done	Z
    //   17: aload_0
    //   18: getfield 29	io/reactivex/observers/SafeObserver:upstream	Lio/reactivex/disposables/Disposable;
    //   21: ifnonnull +133 -> 154
    //   24: new 56	java/lang/NullPointerException
    //   27: dup
    //   28: ldc 58
    //   30: invokespecial 61	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   33: astore_2
    //   34: aload_0
    //   35: getfield 23	io/reactivex/observers/SafeObserver:downstream	Lio/reactivex/Observer;
    //   38: getstatic 67	io/reactivex/internal/disposables/EmptyDisposable:INSTANCE	Lio/reactivex/internal/disposables/EmptyDisposable;
    //   41: invokeinterface 71 2 0
    //   46: aload_0
    //   47: getfield 23	io/reactivex/observers/SafeObserver:downstream	Lio/reactivex/Observer;
    //   50: astore_3
    //   51: new 74	io/reactivex/exceptions/CompositeException
    //   54: astore 4
    //   56: aload 4
    //   58: iconst_2
    //   59: anewarray 76	java/lang/Throwable
    //   62: dup
    //   63: iconst_0
    //   64: aload_1
    //   65: aastore
    //   66: dup
    //   67: iconst_1
    //   68: aload_2
    //   69: aastore
    //   70: invokespecial 79	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   73: aload_3
    //   74: aload 4
    //   76: invokeinterface 72 2 0
    //   81: goto +37 -> 118
    //   84: astore 4
    //   86: aload 4
    //   88: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   91: new 74	io/reactivex/exceptions/CompositeException
    //   94: dup
    //   95: iconst_3
    //   96: anewarray 76	java/lang/Throwable
    //   99: dup
    //   100: iconst_0
    //   101: aload_1
    //   102: aastore
    //   103: dup
    //   104: iconst_1
    //   105: aload_2
    //   106: aastore
    //   107: dup
    //   108: iconst_2
    //   109: aload 4
    //   111: aastore
    //   112: invokespecial 79	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   115: invokestatic 54	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   118: return
    //   119: astore 4
    //   121: aload 4
    //   123: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   126: new 74	io/reactivex/exceptions/CompositeException
    //   129: dup
    //   130: iconst_3
    //   131: anewarray 76	java/lang/Throwable
    //   134: dup
    //   135: iconst_0
    //   136: aload_1
    //   137: aastore
    //   138: dup
    //   139: iconst_1
    //   140: aload_2
    //   141: aastore
    //   142: dup
    //   143: iconst_2
    //   144: aload 4
    //   146: aastore
    //   147: invokespecial 79	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   150: invokestatic 54	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   153: return
    //   154: aload_1
    //   155: astore_2
    //   156: aload_1
    //   157: ifnonnull +13 -> 170
    //   160: new 56	java/lang/NullPointerException
    //   163: dup
    //   164: ldc 81
    //   166: invokespecial 61	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   169: astore_2
    //   170: aload_0
    //   171: getfield 23	io/reactivex/observers/SafeObserver:downstream	Lio/reactivex/Observer;
    //   174: aload_2
    //   175: invokeinterface 72 2 0
    //   180: goto +30 -> 210
    //   183: astore_1
    //   184: aload_1
    //   185: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   188: new 74	io/reactivex/exceptions/CompositeException
    //   191: dup
    //   192: iconst_2
    //   193: anewarray 76	java/lang/Throwable
    //   196: dup
    //   197: iconst_0
    //   198: aload_2
    //   199: aastore
    //   200: dup
    //   201: iconst_1
    //   202: aload_1
    //   203: aastore
    //   204: invokespecial 79	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   207: invokestatic 54	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   210: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	211	0	this	SafeObserver
    //   0	211	1	paramThrowable	Throwable
    //   33	166	2	localObject	Object
    //   50	24	3	localObserver	Observer
    //   54	21	4	localCompositeException	io.reactivex.exceptions.CompositeException
    //   84	26	4	localThrowable1	Throwable
    //   119	26	4	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   46	81	84	finally
    //   34	46	119	finally
    //   170	180	183	finally
  }
  
  /* Error */
  public void onNext(T paramT)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 38	io/reactivex/observers/SafeObserver:done	Z
    //   4: ifeq +4 -> 8
    //   7: return
    //   8: aload_0
    //   9: getfield 29	io/reactivex/observers/SafeObserver:upstream	Lio/reactivex/disposables/Disposable;
    //   12: ifnonnull +8 -> 20
    //   15: aload_0
    //   16: invokevirtual 86	io/reactivex/observers/SafeObserver:onNextNoSubscription	()V
    //   19: return
    //   20: aload_1
    //   21: ifnonnull +57 -> 78
    //   24: new 56	java/lang/NullPointerException
    //   27: dup
    //   28: ldc 88
    //   30: invokespecial 61	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   33: astore_2
    //   34: aload_0
    //   35: getfield 29	io/reactivex/observers/SafeObserver:upstream	Lio/reactivex/disposables/Disposable;
    //   38: invokeinterface 31 1 0
    //   43: aload_0
    //   44: aload_2
    //   45: invokevirtual 89	io/reactivex/observers/SafeObserver:onError	(Ljava/lang/Throwable;)V
    //   48: return
    //   49: astore_1
    //   50: aload_1
    //   51: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   54: aload_0
    //   55: new 74	io/reactivex/exceptions/CompositeException
    //   58: dup
    //   59: iconst_2
    //   60: anewarray 76	java/lang/Throwable
    //   63: dup
    //   64: iconst_0
    //   65: aload_2
    //   66: aastore
    //   67: dup
    //   68: iconst_1
    //   69: aload_1
    //   70: aastore
    //   71: invokespecial 79	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   74: invokevirtual 89	io/reactivex/observers/SafeObserver:onError	(Ljava/lang/Throwable;)V
    //   77: return
    //   78: aload_0
    //   79: getfield 23	io/reactivex/observers/SafeObserver:downstream	Lio/reactivex/Observer;
    //   82: aload_1
    //   83: invokeinterface 91 2 0
    //   88: goto +22 -> 110
    //   91: astore_1
    //   92: aload_1
    //   93: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   96: aload_0
    //   97: getfield 29	io/reactivex/observers/SafeObserver:upstream	Lio/reactivex/disposables/Disposable;
    //   100: invokeinterface 31 1 0
    //   105: aload_0
    //   106: aload_1
    //   107: invokevirtual 89	io/reactivex/observers/SafeObserver:onError	(Ljava/lang/Throwable;)V
    //   110: return
    //   111: astore_2
    //   112: aload_2
    //   113: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   116: aload_0
    //   117: new 74	io/reactivex/exceptions/CompositeException
    //   120: dup
    //   121: iconst_2
    //   122: anewarray 76	java/lang/Throwable
    //   125: dup
    //   126: iconst_0
    //   127: aload_1
    //   128: aastore
    //   129: dup
    //   130: iconst_1
    //   131: aload_2
    //   132: aastore
    //   133: invokespecial 79	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   136: invokevirtual 89	io/reactivex/observers/SafeObserver:onError	(Ljava/lang/Throwable;)V
    //   139: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	140	0	this	SafeObserver
    //   0	140	1	paramT	T
    //   33	33	2	localNullPointerException	NullPointerException
    //   111	21	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   34	43	49	finally
    //   78	88	91	finally
    //   96	105	111	finally
  }
  
  /* Error */
  void onNextNoSubscription()
  {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: putfield 38	io/reactivex/observers/SafeObserver:done	Z
    //   5: new 56	java/lang/NullPointerException
    //   8: dup
    //   9: ldc 58
    //   11: invokespecial 61	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
    //   14: astore_1
    //   15: aload_0
    //   16: getfield 23	io/reactivex/observers/SafeObserver:downstream	Lio/reactivex/Observer;
    //   19: getstatic 67	io/reactivex/internal/disposables/EmptyDisposable:INSTANCE	Lio/reactivex/internal/disposables/EmptyDisposable;
    //   22: invokeinterface 71 2 0
    //   27: aload_0
    //   28: getfield 23	io/reactivex/observers/SafeObserver:downstream	Lio/reactivex/Observer;
    //   31: aload_1
    //   32: invokeinterface 72 2 0
    //   37: goto +30 -> 67
    //   40: astore_2
    //   41: aload_2
    //   42: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   45: new 74	io/reactivex/exceptions/CompositeException
    //   48: dup
    //   49: iconst_2
    //   50: anewarray 76	java/lang/Throwable
    //   53: dup
    //   54: iconst_0
    //   55: aload_1
    //   56: aastore
    //   57: dup
    //   58: iconst_1
    //   59: aload_2
    //   60: aastore
    //   61: invokespecial 79	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   64: invokestatic 54	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   67: return
    //   68: astore_2
    //   69: aload_2
    //   70: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   73: new 74	io/reactivex/exceptions/CompositeException
    //   76: dup
    //   77: iconst_2
    //   78: anewarray 76	java/lang/Throwable
    //   81: dup
    //   82: iconst_0
    //   83: aload_1
    //   84: aastore
    //   85: dup
    //   86: iconst_1
    //   87: aload_2
    //   88: aastore
    //   89: invokespecial 79	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   92: invokestatic 54	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   95: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	96	0	this	SafeObserver
    //   14	70	1	localNullPointerException	NullPointerException
    //   40	20	2	localThrowable1	Throwable
    //   68	20	2	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   27	37	40	finally
    //   15	27	68	finally
  }
  
  /* Error */
  public void onSubscribe(Disposable paramDisposable)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 29	io/reactivex/observers/SafeObserver:upstream	Lio/reactivex/disposables/Disposable;
    //   4: aload_1
    //   5: invokestatic 98	io/reactivex/internal/disposables/DisposableHelper:validate	(Lio/reactivex/disposables/Disposable;Lio/reactivex/disposables/Disposable;)Z
    //   8: ifeq +71 -> 79
    //   11: aload_0
    //   12: aload_1
    //   13: putfield 29	io/reactivex/observers/SafeObserver:upstream	Lio/reactivex/disposables/Disposable;
    //   16: aload_0
    //   17: getfield 23	io/reactivex/observers/SafeObserver:downstream	Lio/reactivex/Observer;
    //   20: aload_0
    //   21: invokeinterface 71 2 0
    //   26: goto +53 -> 79
    //   29: astore_2
    //   30: aload_2
    //   31: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   34: aload_0
    //   35: iconst_1
    //   36: putfield 38	io/reactivex/observers/SafeObserver:done	Z
    //   39: aload_1
    //   40: invokeinterface 31 1 0
    //   45: aload_2
    //   46: invokestatic 54	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   49: goto +30 -> 79
    //   52: astore_1
    //   53: aload_1
    //   54: invokestatic 49	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   57: new 74	io/reactivex/exceptions/CompositeException
    //   60: dup
    //   61: iconst_2
    //   62: anewarray 76	java/lang/Throwable
    //   65: dup
    //   66: iconst_0
    //   67: aload_2
    //   68: aastore
    //   69: dup
    //   70: iconst_1
    //   71: aload_1
    //   72: aastore
    //   73: invokespecial 79	io/reactivex/exceptions/CompositeException:<init>	([Ljava/lang/Throwable;)V
    //   76: invokestatic 54	io/reactivex/plugins/RxJavaPlugins:onError	(Ljava/lang/Throwable;)V
    //   79: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	80	0	this	SafeObserver
    //   0	80	1	paramDisposable	Disposable
    //   29	39	2	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   16	26	29	finally
    //   39	45	52	finally
  }
}
