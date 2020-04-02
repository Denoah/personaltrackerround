package io.reactivex.disposables;

import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.OpenHashSet;
import java.util.Iterator;

public final class CompositeDisposable
  implements Disposable, DisposableContainer
{
  volatile boolean disposed;
  OpenHashSet<Disposable> resources;
  
  public CompositeDisposable() {}
  
  public CompositeDisposable(Iterable<? extends Disposable> paramIterable)
  {
    ObjectHelper.requireNonNull(paramIterable, "disposables is null");
    this.resources = new OpenHashSet();
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext())
    {
      paramIterable = (Disposable)localIterator.next();
      ObjectHelper.requireNonNull(paramIterable, "A Disposable item in the disposables sequence is null");
      this.resources.add(paramIterable);
    }
  }
  
  public CompositeDisposable(Disposable... paramVarArgs)
  {
    ObjectHelper.requireNonNull(paramVarArgs, "disposables is null");
    this.resources = new OpenHashSet(paramVarArgs.length + 1);
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++)
    {
      Disposable localDisposable = paramVarArgs[j];
      ObjectHelper.requireNonNull(localDisposable, "A Disposable in the disposables array is null");
      this.resources.add(localDisposable);
    }
  }
  
  public boolean add(Disposable paramDisposable)
  {
    ObjectHelper.requireNonNull(paramDisposable, "disposable is null");
    if (!this.disposed) {
      try
      {
        if (!this.disposed)
        {
          OpenHashSet localOpenHashSet1 = this.resources;
          OpenHashSet localOpenHashSet2 = localOpenHashSet1;
          if (localOpenHashSet1 == null)
          {
            localOpenHashSet2 = new io/reactivex/internal/util/OpenHashSet;
            localOpenHashSet2.<init>();
            this.resources = localOpenHashSet2;
          }
          localOpenHashSet2.add(paramDisposable);
          return true;
        }
      }
      finally {}
    }
    paramDisposable.dispose();
    return false;
  }
  
  public boolean addAll(Disposable... paramVarArgs)
  {
    ObjectHelper.requireNonNull(paramVarArgs, "disposables is null");
    boolean bool = this.disposed;
    int i = 0;
    if (!bool) {
      try
      {
        if (!this.disposed)
        {
          Object localObject1 = this.resources;
          Object localObject2 = localObject1;
          if (localObject1 == null)
          {
            localObject2 = new io/reactivex/internal/util/OpenHashSet;
            ((OpenHashSet)localObject2).<init>(paramVarArgs.length + 1);
            this.resources = ((OpenHashSet)localObject2);
          }
          j = paramVarArgs.length;
          while (i < j)
          {
            localObject1 = paramVarArgs[i];
            ObjectHelper.requireNonNull(localObject1, "A Disposable in the disposables array is null");
            ((OpenHashSet)localObject2).add(localObject1);
            i++;
          }
          return true;
        }
      }
      finally {}
    }
    int j = paramVarArgs.length;
    for (i = 0; i < j; i++) {
      paramVarArgs[i].dispose();
    }
    return false;
  }
  
  public void clear()
  {
    if (this.disposed) {
      return;
    }
    try
    {
      if (this.disposed) {
        return;
      }
      OpenHashSet localOpenHashSet = this.resources;
      this.resources = null;
      dispose(localOpenHashSet);
      return;
    }
    finally {}
  }
  
  public boolean delete(Disposable paramDisposable)
  {
    ObjectHelper.requireNonNull(paramDisposable, "disposables is null");
    if (this.disposed) {
      return false;
    }
    try
    {
      if (this.disposed) {
        return false;
      }
      OpenHashSet localOpenHashSet = this.resources;
      return (localOpenHashSet != null) && (localOpenHashSet.remove(paramDisposable));
    }
    finally {}
  }
  
  public void dispose()
  {
    if (this.disposed) {
      return;
    }
    try
    {
      if (this.disposed) {
        return;
      }
      this.disposed = true;
      OpenHashSet localOpenHashSet = this.resources;
      this.resources = null;
      dispose(localOpenHashSet);
      return;
    }
    finally {}
  }
  
  /* Error */
  void dispose(OpenHashSet<Disposable> paramOpenHashSet)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +4 -> 5
    //   4: return
    //   5: aconst_null
    //   6: astore_2
    //   7: aload_1
    //   8: invokevirtual 84	io/reactivex/internal/util/OpenHashSet:keys	()[Ljava/lang/Object;
    //   11: astore_3
    //   12: aload_3
    //   13: arraylength
    //   14: istore 4
    //   16: iconst_0
    //   17: istore 5
    //   19: aload_2
    //   20: astore_1
    //   21: iload 5
    //   23: iload 4
    //   25: if_icmpge +72 -> 97
    //   28: aload_3
    //   29: iload 5
    //   31: aaload
    //   32: astore 6
    //   34: aload_1
    //   35: astore_2
    //   36: aload 6
    //   38: instanceof 6
    //   41: ifeq +48 -> 89
    //   44: aload 6
    //   46: checkcast 6	io/reactivex/disposables/Disposable
    //   49: invokeinterface 70 1 0
    //   54: aload_1
    //   55: astore_2
    //   56: goto +33 -> 89
    //   59: astore 6
    //   61: aload 6
    //   63: invokestatic 90	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   66: aload_1
    //   67: astore_2
    //   68: aload_1
    //   69: ifnonnull +11 -> 80
    //   72: new 92	java/util/ArrayList
    //   75: dup
    //   76: invokespecial 93	java/util/ArrayList:<init>	()V
    //   79: astore_2
    //   80: aload_2
    //   81: aload 6
    //   83: invokeinterface 96 2 0
    //   88: pop
    //   89: iinc 5 1
    //   92: aload_2
    //   93: astore_1
    //   94: goto -73 -> 21
    //   97: aload_1
    //   98: ifnull +36 -> 134
    //   101: aload_1
    //   102: invokeinterface 100 1 0
    //   107: iconst_1
    //   108: if_icmpne +17 -> 125
    //   111: aload_1
    //   112: iconst_0
    //   113: invokeinterface 104 2 0
    //   118: checkcast 106	java/lang/Throwable
    //   121: invokestatic 112	io/reactivex/internal/util/ExceptionHelper:wrapOrThrow	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
    //   124: athrow
    //   125: new 114	io/reactivex/exceptions/CompositeException
    //   128: dup
    //   129: aload_1
    //   130: invokespecial 116	io/reactivex/exceptions/CompositeException:<init>	(Ljava/lang/Iterable;)V
    //   133: athrow
    //   134: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	135	0	this	CompositeDisposable
    //   0	135	1	paramOpenHashSet	OpenHashSet<Disposable>
    //   6	87	2	localObject1	Object
    //   11	18	3	arrayOfObject	Object[]
    //   14	12	4	i	int
    //   17	73	5	j	int
    //   32	13	6	localObject2	Object
    //   59	23	6	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   44	54	59	finally
  }
  
  public boolean isDisposed()
  {
    return this.disposed;
  }
  
  public boolean remove(Disposable paramDisposable)
  {
    if (delete(paramDisposable))
    {
      paramDisposable.dispose();
      return true;
    }
    return false;
  }
  
  public int size()
  {
    boolean bool = this.disposed;
    int i = 0;
    if (bool) {
      return 0;
    }
    try
    {
      if (this.disposed) {
        return 0;
      }
      OpenHashSet localOpenHashSet = this.resources;
      if (localOpenHashSet != null) {
        i = localOpenHashSet.size();
      }
      return i;
    }
    finally {}
  }
}
