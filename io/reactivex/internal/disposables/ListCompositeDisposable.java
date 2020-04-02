package io.reactivex.internal.disposables;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class ListCompositeDisposable
  implements Disposable, DisposableContainer
{
  volatile boolean disposed;
  List<Disposable> resources;
  
  public ListCompositeDisposable() {}
  
  public ListCompositeDisposable(Iterable<? extends Disposable> paramIterable)
  {
    ObjectHelper.requireNonNull(paramIterable, "resources is null");
    this.resources = new LinkedList();
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext())
    {
      paramIterable = (Disposable)localIterator.next();
      ObjectHelper.requireNonNull(paramIterable, "Disposable item is null");
      this.resources.add(paramIterable);
    }
  }
  
  public ListCompositeDisposable(Disposable... paramVarArgs)
  {
    ObjectHelper.requireNonNull(paramVarArgs, "resources is null");
    this.resources = new LinkedList();
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++)
    {
      Disposable localDisposable = paramVarArgs[j];
      ObjectHelper.requireNonNull(localDisposable, "Disposable item is null");
      this.resources.add(localDisposable);
    }
  }
  
  public boolean add(Disposable paramDisposable)
  {
    ObjectHelper.requireNonNull(paramDisposable, "d is null");
    if (!this.disposed) {
      try
      {
        if (!this.disposed)
        {
          List localList = this.resources;
          Object localObject = localList;
          if (localList == null)
          {
            localObject = new java/util/LinkedList;
            ((LinkedList)localObject).<init>();
            this.resources = ((List)localObject);
          }
          ((List)localObject).add(paramDisposable);
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
    ObjectHelper.requireNonNull(paramVarArgs, "ds is null");
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
            localObject2 = new java/util/LinkedList;
            ((LinkedList)localObject2).<init>();
            this.resources = ((List)localObject2);
          }
          j = paramVarArgs.length;
          while (i < j)
          {
            localObject1 = paramVarArgs[i];
            ObjectHelper.requireNonNull(localObject1, "d is null");
            ((List)localObject2).add(localObject1);
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
      List localList = this.resources;
      this.resources = null;
      dispose(localList);
      return;
    }
    finally {}
  }
  
  public boolean delete(Disposable paramDisposable)
  {
    ObjectHelper.requireNonNull(paramDisposable, "Disposable item is null");
    if (this.disposed) {
      return false;
    }
    try
    {
      if (this.disposed) {
        return false;
      }
      List localList = this.resources;
      return (localList != null) && (localList.remove(paramDisposable));
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
      List localList = this.resources;
      this.resources = null;
      dispose(localList);
      return;
    }
    finally {}
  }
  
  /* Error */
  void dispose(List<Disposable> paramList)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +4 -> 5
    //   4: return
    //   5: aconst_null
    //   6: astore_2
    //   7: aload_1
    //   8: invokeinterface 80 1 0
    //   13: astore_3
    //   14: aload_2
    //   15: astore_1
    //   16: aload_3
    //   17: invokeinterface 44 1 0
    //   22: ifeq +57 -> 79
    //   25: aload_3
    //   26: invokeinterface 48 1 0
    //   31: checkcast 6	io/reactivex/disposables/Disposable
    //   34: astore_2
    //   35: aload_2
    //   36: invokeinterface 67 1 0
    //   41: goto -25 -> 16
    //   44: astore 4
    //   46: aload 4
    //   48: invokestatic 86	io/reactivex/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
    //   51: aload_1
    //   52: astore_2
    //   53: aload_1
    //   54: ifnonnull +11 -> 65
    //   57: new 88	java/util/ArrayList
    //   60: dup
    //   61: invokespecial 89	java/util/ArrayList:<init>	()V
    //   64: astore_2
    //   65: aload_2
    //   66: aload 4
    //   68: invokeinterface 56 2 0
    //   73: pop
    //   74: aload_2
    //   75: astore_1
    //   76: goto -60 -> 16
    //   79: aload_1
    //   80: ifnull +36 -> 116
    //   83: aload_1
    //   84: invokeinterface 93 1 0
    //   89: iconst_1
    //   90: if_icmpne +17 -> 107
    //   93: aload_1
    //   94: iconst_0
    //   95: invokeinterface 97 2 0
    //   100: checkcast 99	java/lang/Throwable
    //   103: invokestatic 105	io/reactivex/internal/util/ExceptionHelper:wrapOrThrow	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
    //   106: athrow
    //   107: new 107	io/reactivex/exceptions/CompositeException
    //   110: dup
    //   111: aload_1
    //   112: invokespecial 109	io/reactivex/exceptions/CompositeException:<init>	(Ljava/lang/Iterable;)V
    //   115: athrow
    //   116: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	117	0	this	ListCompositeDisposable
    //   0	117	1	paramList	List<Disposable>
    //   6	69	2	localObject	Object
    //   13	13	3	localIterator	Iterator
    //   44	23	4	localThrowable	Throwable
    // Exception table:
    //   from	to	target	type
    //   35	41	44	finally
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
}
