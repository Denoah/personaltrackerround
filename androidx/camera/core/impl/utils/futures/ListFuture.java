package androidx.camera.core.impl.utils.futures;

import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.CallbackToFutureAdapter.Completer;
import androidx.concurrent.futures.CallbackToFutureAdapter.Resolver;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

class ListFuture<V>
  implements ListenableFuture<List<V>>
{
  private final boolean mAllMustSucceed;
  List<? extends ListenableFuture<? extends V>> mFutures;
  private final AtomicInteger mRemaining;
  private final ListenableFuture<List<V>> mResult;
  CallbackToFutureAdapter.Completer<List<V>> mResultNotifier;
  List<V> mValues;
  
  ListFuture(List<? extends ListenableFuture<? extends V>> paramList, boolean paramBoolean, Executor paramExecutor)
  {
    this.mFutures = ((List)Preconditions.checkNotNull(paramList));
    this.mValues = new ArrayList(paramList.size());
    this.mAllMustSucceed = paramBoolean;
    this.mRemaining = new AtomicInteger(paramList.size());
    this.mResult = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver()
    {
      public Object attachCompleter(CallbackToFutureAdapter.Completer<List<V>> paramAnonymousCompleter)
      {
        boolean bool;
        if (ListFuture.this.mResultNotifier == null) {
          bool = true;
        } else {
          bool = false;
        }
        Preconditions.checkState(bool, "The result can only set once!");
        ListFuture.this.mResultNotifier = paramAnonymousCompleter;
        paramAnonymousCompleter = new StringBuilder();
        paramAnonymousCompleter.append("ListFuture[");
        paramAnonymousCompleter.append(this);
        paramAnonymousCompleter.append("]");
        return paramAnonymousCompleter.toString();
      }
    });
    init(paramExecutor);
  }
  
  /* Error */
  private void callAllGets()
    throws InterruptedException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 43	androidx/camera/core/impl/utils/futures/ListFuture:mFutures	Ljava/util/List;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnull +70 -> 76
    //   9: aload_0
    //   10: invokevirtual 88	androidx/camera/core/impl/utils/futures/ListFuture:isDone	()Z
    //   13: ifne +63 -> 76
    //   16: aload_1
    //   17: invokeinterface 92 1 0
    //   22: astore_2
    //   23: aload_2
    //   24: invokeinterface 97 1 0
    //   29: ifeq +47 -> 76
    //   32: aload_2
    //   33: invokeinterface 101 1 0
    //   38: checkcast 7	com/google/common/util/concurrent/ListenableFuture
    //   41: astore_1
    //   42: aload_1
    //   43: invokeinterface 102 1 0
    //   48: ifne -25 -> 23
    //   51: aload_1
    //   52: invokeinterface 105 1 0
    //   57: pop
    //   58: goto -16 -> 42
    //   61: astore_3
    //   62: aload_0
    //   63: getfield 56	androidx/camera/core/impl/utils/futures/ListFuture:mAllMustSucceed	Z
    //   66: ifeq -24 -> 42
    //   69: return
    //   70: astore_1
    //   71: aload_1
    //   72: athrow
    //   73: astore_1
    //   74: aload_1
    //   75: athrow
    //   76: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	77	0	this	ListFuture
    //   4	48	1	localObject1	Object
    //   70	2	1	localInterruptedException	InterruptedException
    //   73	2	1	localError	Error
    //   22	11	2	localIterator	Iterator
    //   61	1	3	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   51	58	61	finally
    //   51	58	70	java/lang/InterruptedException
    //   51	58	73	java/lang/Error
  }
  
  private void init(Executor paramExecutor)
  {
    addListener(new Runnable()
    {
      public void run()
      {
        ListFuture.this.mValues = null;
        ListFuture.this.mFutures = null;
      }
    }, CameraXExecutors.directExecutor());
    if (this.mFutures.isEmpty())
    {
      this.mResultNotifier.set(new ArrayList(this.mValues));
      return;
    }
    int i = 0;
    for (final int j = 0; j < this.mFutures.size(); j++) {
      this.mValues.add(null);
    }
    List localList = this.mFutures;
    for (j = i; j < localList.size(); j++)
    {
      final ListenableFuture localListenableFuture = (ListenableFuture)localList.get(j);
      localListenableFuture.addListener(new Runnable()
      {
        public void run()
        {
          ListFuture.this.setOneValue(j, localListenableFuture);
        }
      }, paramExecutor);
    }
  }
  
  public void addListener(Runnable paramRunnable, Executor paramExecutor)
  {
    this.mResult.addListener(paramRunnable, paramExecutor);
  }
  
  public boolean cancel(boolean paramBoolean)
  {
    Object localObject = this.mFutures;
    if (localObject != null)
    {
      localObject = ((List)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((ListenableFuture)((Iterator)localObject).next()).cancel(paramBoolean);
      }
    }
    return this.mResult.cancel(paramBoolean);
  }
  
  public List<V> get()
    throws InterruptedException, ExecutionException
  {
    callAllGets();
    return (List)this.mResult.get();
  }
  
  public List<V> get(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException, ExecutionException, TimeoutException
  {
    return (List)this.mResult.get(paramLong, paramTimeUnit);
  }
  
  public boolean isCancelled()
  {
    return this.mResult.isCancelled();
  }
  
  public boolean isDone()
  {
    return this.mResult.isDone();
  }
  
  /* Error */
  void setOneValue(int paramInt, java.util.concurrent.Future<? extends V> paramFuture)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 54	androidx/camera/core/impl/utils/futures/ListFuture:mValues	Ljava/util/List;
    //   4: astore_3
    //   5: aload_0
    //   6: invokevirtual 88	androidx/camera/core/impl/utils/futures/ListFuture:isDone	()Z
    //   9: ifne +494 -> 503
    //   12: aload_3
    //   13: ifnonnull +6 -> 19
    //   16: goto +487 -> 503
    //   19: iconst_1
    //   20: istore 4
    //   22: iconst_1
    //   23: istore 5
    //   25: iconst_1
    //   26: istore 6
    //   28: iconst_1
    //   29: istore 7
    //   31: iconst_1
    //   32: istore 8
    //   34: iconst_1
    //   35: istore 9
    //   37: aload_2
    //   38: invokeinterface 174 1 0
    //   43: ldc -80
    //   45: invokestatic 180	androidx/core/util/Preconditions:checkState	(ZLjava/lang/String;)V
    //   48: aload_3
    //   49: iload_1
    //   50: aload_2
    //   51: invokestatic 186	androidx/camera/core/impl/utils/futures/Futures:getUninterruptibly	(Ljava/util/concurrent/Future;)Ljava/lang/Object;
    //   54: invokeinterface 189 3 0
    //   59: pop
    //   60: aload_0
    //   61: getfield 61	androidx/camera/core/impl/utils/futures/ListFuture:mRemaining	Ljava/util/concurrent/atomic/AtomicInteger;
    //   64: invokevirtual 192	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   67: istore_1
    //   68: iload_1
    //   69: iflt +6 -> 75
    //   72: goto +6 -> 78
    //   75: iconst_0
    //   76: istore 9
    //   78: iload 9
    //   80: ldc -62
    //   82: invokestatic 180	androidx/core/util/Preconditions:checkState	(ZLjava/lang/String;)V
    //   85: iload_1
    //   86: ifne +346 -> 432
    //   89: aload_0
    //   90: getfield 54	androidx/camera/core/impl/utils/futures/ListFuture:mValues	Ljava/util/List;
    //   93: astore_2
    //   94: aload_2
    //   95: ifnull +26 -> 121
    //   98: aload_0
    //   99: getfield 122	androidx/camera/core/impl/utils/futures/ListFuture:mResultNotifier	Landroidx/concurrent/futures/CallbackToFutureAdapter$Completer;
    //   102: astore_3
    //   103: new 45	java/util/ArrayList
    //   106: dup
    //   107: aload_2
    //   108: invokespecial 125	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
    //   111: astore_2
    //   112: aload_3
    //   113: aload_2
    //   114: invokevirtual 131	androidx/concurrent/futures/CallbackToFutureAdapter$Completer:set	(Ljava/lang/Object;)Z
    //   117: pop
    //   118: goto +314 -> 432
    //   121: aload_0
    //   122: invokevirtual 88	androidx/camera/core/impl/utils/futures/ListFuture:isDone	()Z
    //   125: invokestatic 197	androidx/core/util/Preconditions:checkState	(Z)V
    //   128: goto +304 -> 432
    //   131: astore_3
    //   132: goto +301 -> 433
    //   135: astore_2
    //   136: aload_0
    //   137: getfield 122	androidx/camera/core/impl/utils/futures/ListFuture:mResultNotifier	Landroidx/concurrent/futures/CallbackToFutureAdapter$Completer;
    //   140: aload_2
    //   141: invokevirtual 201	androidx/concurrent/futures/CallbackToFutureAdapter$Completer:setException	(Ljava/lang/Throwable;)Z
    //   144: pop
    //   145: aload_0
    //   146: getfield 61	androidx/camera/core/impl/utils/futures/ListFuture:mRemaining	Ljava/util/concurrent/atomic/AtomicInteger;
    //   149: invokevirtual 192	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   152: istore_1
    //   153: iload_1
    //   154: iflt +10 -> 164
    //   157: iload 4
    //   159: istore 9
    //   161: goto +6 -> 167
    //   164: iconst_0
    //   165: istore 9
    //   167: iload 9
    //   169: ldc -62
    //   171: invokestatic 180	androidx/core/util/Preconditions:checkState	(ZLjava/lang/String;)V
    //   174: iload_1
    //   175: ifne +257 -> 432
    //   178: aload_0
    //   179: getfield 54	androidx/camera/core/impl/utils/futures/ListFuture:mValues	Ljava/util/List;
    //   182: astore_2
    //   183: aload_2
    //   184: ifnull -63 -> 121
    //   187: aload_0
    //   188: getfield 122	androidx/camera/core/impl/utils/futures/ListFuture:mResultNotifier	Landroidx/concurrent/futures/CallbackToFutureAdapter$Completer;
    //   191: astore_3
    //   192: new 45	java/util/ArrayList
    //   195: dup
    //   196: aload_2
    //   197: invokespecial 125	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
    //   200: astore_2
    //   201: goto -89 -> 112
    //   204: astore_2
    //   205: aload_0
    //   206: getfield 56	androidx/camera/core/impl/utils/futures/ListFuture:mAllMustSucceed	Z
    //   209: ifeq +12 -> 221
    //   212: aload_0
    //   213: getfield 122	androidx/camera/core/impl/utils/futures/ListFuture:mResultNotifier	Landroidx/concurrent/futures/CallbackToFutureAdapter$Completer;
    //   216: aload_2
    //   217: invokevirtual 201	androidx/concurrent/futures/CallbackToFutureAdapter$Completer:setException	(Ljava/lang/Throwable;)Z
    //   220: pop
    //   221: aload_0
    //   222: getfield 61	androidx/camera/core/impl/utils/futures/ListFuture:mRemaining	Ljava/util/concurrent/atomic/AtomicInteger;
    //   225: invokevirtual 192	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   228: istore_1
    //   229: iload_1
    //   230: iflt +10 -> 240
    //   233: iload 5
    //   235: istore 9
    //   237: goto +6 -> 243
    //   240: iconst_0
    //   241: istore 9
    //   243: iload 9
    //   245: ldc -62
    //   247: invokestatic 180	androidx/core/util/Preconditions:checkState	(ZLjava/lang/String;)V
    //   250: iload_1
    //   251: ifne +181 -> 432
    //   254: aload_0
    //   255: getfield 54	androidx/camera/core/impl/utils/futures/ListFuture:mValues	Ljava/util/List;
    //   258: astore_2
    //   259: aload_2
    //   260: ifnull -139 -> 121
    //   263: aload_0
    //   264: getfield 122	androidx/camera/core/impl/utils/futures/ListFuture:mResultNotifier	Landroidx/concurrent/futures/CallbackToFutureAdapter$Completer;
    //   267: astore_3
    //   268: new 45	java/util/ArrayList
    //   271: dup
    //   272: aload_2
    //   273: invokespecial 125	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
    //   276: astore_2
    //   277: goto -165 -> 112
    //   280: astore_2
    //   281: aload_0
    //   282: getfield 56	androidx/camera/core/impl/utils/futures/ListFuture:mAllMustSucceed	Z
    //   285: ifeq +15 -> 300
    //   288: aload_0
    //   289: getfield 122	androidx/camera/core/impl/utils/futures/ListFuture:mResultNotifier	Landroidx/concurrent/futures/CallbackToFutureAdapter$Completer;
    //   292: aload_2
    //   293: invokevirtual 205	java/util/concurrent/ExecutionException:getCause	()Ljava/lang/Throwable;
    //   296: invokevirtual 201	androidx/concurrent/futures/CallbackToFutureAdapter$Completer:setException	(Ljava/lang/Throwable;)Z
    //   299: pop
    //   300: aload_0
    //   301: getfield 61	androidx/camera/core/impl/utils/futures/ListFuture:mRemaining	Ljava/util/concurrent/atomic/AtomicInteger;
    //   304: invokevirtual 192	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   307: istore_1
    //   308: iload_1
    //   309: iflt +10 -> 319
    //   312: iload 6
    //   314: istore 9
    //   316: goto +6 -> 322
    //   319: iconst_0
    //   320: istore 9
    //   322: iload 9
    //   324: ldc -62
    //   326: invokestatic 180	androidx/core/util/Preconditions:checkState	(ZLjava/lang/String;)V
    //   329: iload_1
    //   330: ifne +102 -> 432
    //   333: aload_0
    //   334: getfield 54	androidx/camera/core/impl/utils/futures/ListFuture:mValues	Ljava/util/List;
    //   337: astore_2
    //   338: aload_2
    //   339: ifnull -218 -> 121
    //   342: aload_0
    //   343: getfield 122	androidx/camera/core/impl/utils/futures/ListFuture:mResultNotifier	Landroidx/concurrent/futures/CallbackToFutureAdapter$Completer;
    //   346: astore_3
    //   347: new 45	java/util/ArrayList
    //   350: dup
    //   351: aload_2
    //   352: invokespecial 125	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
    //   355: astore_2
    //   356: goto -244 -> 112
    //   359: astore_2
    //   360: aload_0
    //   361: getfield 56	androidx/camera/core/impl/utils/futures/ListFuture:mAllMustSucceed	Z
    //   364: ifeq +9 -> 373
    //   367: aload_0
    //   368: iconst_0
    //   369: invokevirtual 206	androidx/camera/core/impl/utils/futures/ListFuture:cancel	(Z)Z
    //   372: pop
    //   373: aload_0
    //   374: getfield 61	androidx/camera/core/impl/utils/futures/ListFuture:mRemaining	Ljava/util/concurrent/atomic/AtomicInteger;
    //   377: invokevirtual 192	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   380: istore_1
    //   381: iload_1
    //   382: iflt +10 -> 392
    //   385: iload 7
    //   387: istore 9
    //   389: goto +6 -> 395
    //   392: iconst_0
    //   393: istore 9
    //   395: iload 9
    //   397: ldc -62
    //   399: invokestatic 180	androidx/core/util/Preconditions:checkState	(ZLjava/lang/String;)V
    //   402: iload_1
    //   403: ifne +29 -> 432
    //   406: aload_0
    //   407: getfield 54	androidx/camera/core/impl/utils/futures/ListFuture:mValues	Ljava/util/List;
    //   410: astore_2
    //   411: aload_2
    //   412: ifnull -291 -> 121
    //   415: aload_0
    //   416: getfield 122	androidx/camera/core/impl/utils/futures/ListFuture:mResultNotifier	Landroidx/concurrent/futures/CallbackToFutureAdapter$Completer;
    //   419: astore_3
    //   420: new 45	java/util/ArrayList
    //   423: dup
    //   424: aload_2
    //   425: invokespecial 125	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
    //   428: astore_2
    //   429: goto -317 -> 112
    //   432: return
    //   433: aload_0
    //   434: getfield 61	androidx/camera/core/impl/utils/futures/ListFuture:mRemaining	Ljava/util/concurrent/atomic/AtomicInteger;
    //   437: invokevirtual 192	java/util/concurrent/atomic/AtomicInteger:decrementAndGet	()I
    //   440: istore_1
    //   441: iload_1
    //   442: iflt +10 -> 452
    //   445: iload 8
    //   447: istore 9
    //   449: goto +6 -> 455
    //   452: iconst_0
    //   453: istore 9
    //   455: iload 9
    //   457: ldc -62
    //   459: invokestatic 180	androidx/core/util/Preconditions:checkState	(ZLjava/lang/String;)V
    //   462: iload_1
    //   463: ifne +38 -> 501
    //   466: aload_0
    //   467: getfield 54	androidx/camera/core/impl/utils/futures/ListFuture:mValues	Ljava/util/List;
    //   470: astore_2
    //   471: aload_2
    //   472: ifnull +22 -> 494
    //   475: aload_0
    //   476: getfield 122	androidx/camera/core/impl/utils/futures/ListFuture:mResultNotifier	Landroidx/concurrent/futures/CallbackToFutureAdapter$Completer;
    //   479: new 45	java/util/ArrayList
    //   482: dup
    //   483: aload_2
    //   484: invokespecial 125	java/util/ArrayList:<init>	(Ljava/util/Collection;)V
    //   487: invokevirtual 131	androidx/concurrent/futures/CallbackToFutureAdapter$Completer:set	(Ljava/lang/Object;)Z
    //   490: pop
    //   491: goto +10 -> 501
    //   494: aload_0
    //   495: invokevirtual 88	androidx/camera/core/impl/utils/futures/ListFuture:isDone	()Z
    //   498: invokestatic 197	androidx/core/util/Preconditions:checkState	(Z)V
    //   501: aload_3
    //   502: athrow
    //   503: aload_0
    //   504: getfield 56	androidx/camera/core/impl/utils/futures/ListFuture:mAllMustSucceed	Z
    //   507: ldc -48
    //   509: invokestatic 180	androidx/core/util/Preconditions:checkState	(ZLjava/lang/String;)V
    //   512: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	513	0	this	ListFuture
    //   0	513	1	paramInt	int
    //   0	513	2	paramFuture	java.util.concurrent.Future<? extends V>
    //   4	109	3	localObject1	Object
    //   131	1	3	localObject2	Object
    //   191	311	3	localCompleter	CallbackToFutureAdapter.Completer
    //   20	138	4	bool1	boolean
    //   23	211	5	bool2	boolean
    //   26	287	6	bool3	boolean
    //   29	357	7	bool4	boolean
    //   32	414	8	bool5	boolean
    //   35	421	9	bool6	boolean
    // Exception table:
    //   from	to	target	type
    //   37	60	131	finally
    //   136	145	131	finally
    //   205	221	131	finally
    //   281	300	131	finally
    //   360	373	131	finally
    //   37	60	135	java/lang/Error
    //   37	60	204	java/lang/RuntimeException
    //   37	60	280	java/util/concurrent/ExecutionException
    //   37	60	359	java/util/concurrent/CancellationException
  }
}
