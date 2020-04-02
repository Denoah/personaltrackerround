package kotlinx.coroutines.internal;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\002\030\002\n\002\030\002\n\002\020\017\n\002\b\004\n\002\020\002\n\002\b\003\n\002\030\002\n\002\020\013\n\002\b\007\n\002\020\021\n\002\b\004\n\002\020\b\n\002\b\030\n\002\020\000\n\002\030\002\b\027\030\000*\022\b\000\020\003*\0020\001*\b\022\004\022\0028\0000\0022\00602j\002`3B\007?\006\004\b\004\020\005J\027\020\b\032\0020\0072\006\020\006\032\0028\000H\001?\006\004\b\b\020\tJ\025\020\n\032\0020\0072\006\020\006\032\0028\000?\006\004\b\n\020\tJ.\020\016\032\0020\f2\006\020\006\032\0028\0002\024\020\r\032\020\022\006\022\004\030\0018\000\022\004\022\0020\f0\013H?\b?\006\004\b\016\020\017J\r\020\020\032\0020\007?\006\004\b\020\020\005J\021\020\021\032\004\030\0018\000H\001?\006\004\b\021\020\022J\017\020\023\032\004\030\0018\000?\006\004\b\023\020\022J\027\020\025\032\n\022\006\022\004\030\0018\0000\024H\002?\006\004\b\025\020\026J\025\020\027\032\0020\f2\006\020\006\032\0028\000?\006\004\b\027\020\030J\027\020\033\032\0028\0002\006\020\032\032\0020\031H\001?\006\004\b\033\020\034J&\020\036\032\004\030\0018\0002\022\020\035\032\016\022\004\022\0028\000\022\004\022\0020\f0\013H?\b?\006\004\b\036\020\037J\017\020 \032\004\030\0018\000?\006\004\b \020\022J\030\020\"\032\0020\0072\006\020!\032\0020\031H?\020?\006\004\b\"\020#J\030\020$\032\0020\0072\006\020!\032\0020\031H?\020?\006\004\b$\020#J\037\020&\032\0020\0072\006\020!\032\0020\0312\006\020%\032\0020\031H\002?\006\004\b&\020'R \020(\032\f\022\006\022\004\030\0018\000\030\0010\0248\002@\002X?\016?\006\006\n\004\b(\020)R\023\020*\032\0020\f8F@\006?\006\006\032\004\b*\020+R$\0200\032\0020\0312\006\020,\032\0020\0318F@BX?\016?\006\f\032\004\b-\020.\"\004\b/\020#?\0061"}, d2={"Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "", "T", "<init>", "()V", "node", "", "addImpl", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)V", "addLast", "Lkotlin/Function1;", "", "cond", "addLastIf", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;Lkotlin/jvm/functions/Function1;)Z", "clear", "firstImpl", "()Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "peek", "", "realloc", "()[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "remove", "(Lkotlinx/coroutines/internal/ThreadSafeHeapNode;)Z", "", "index", "removeAtImpl", "(I)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "predicate", "removeFirstIf", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "removeFirstOrNull", "i", "siftDownFrom", "(I)V", "siftUpFrom", "j", "swap", "(II)V", "a", "[Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "isEmpty", "()Z", "value", "getSize", "()I", "setSize", "size", "kotlinx-coroutines-core", "", "Lkotlinx/coroutines/internal/SynchronizedObject;"}, k=1, mv={1, 1, 16})
public class ThreadSafeHeap<T extends ThreadSafeHeapNode,  extends Comparable<? super T>>
{
  private volatile int _size = 0;
  private T[] a;
  
  public ThreadSafeHeap() {}
  
  private final T[] realloc()
  {
    ThreadSafeHeapNode[] arrayOfThreadSafeHeapNode = this.a;
    Object localObject;
    if (arrayOfThreadSafeHeapNode == null)
    {
      localObject = new ThreadSafeHeapNode[4];
      this.a = ((ThreadSafeHeapNode[])localObject);
    }
    else
    {
      localObject = arrayOfThreadSafeHeapNode;
      if (getSize() >= arrayOfThreadSafeHeapNode.length)
      {
        localObject = Arrays.copyOf(arrayOfThreadSafeHeapNode, getSize() * 2);
        Intrinsics.checkExpressionValueIsNotNull(localObject, "java.util.Arrays.copyOf(this, newSize)");
        localObject = (ThreadSafeHeapNode[])localObject;
        this.a = ((ThreadSafeHeapNode[])localObject);
      }
    }
    return localObject;
  }
  
  private final void setSize(int paramInt)
  {
    this._size = paramInt;
  }
  
  private final void siftDownFrom(int paramInt)
  {
    for (int i = paramInt;; i = paramInt)
    {
      int j = i * 2 + 1;
      if (j >= getSize()) {
        return;
      }
      Object localObject1 = this.a;
      if (localObject1 == null) {
        Intrinsics.throwNpe();
      }
      int k = j + 1;
      paramInt = j;
      if (k < getSize())
      {
        localObject2 = localObject1[k];
        if (localObject2 == null) {
          Intrinsics.throwNpe();
        }
        Comparable localComparable = (Comparable)localObject2;
        localObject2 = localObject1[j];
        if (localObject2 == null) {
          Intrinsics.throwNpe();
        }
        paramInt = j;
        if (localComparable.compareTo(localObject2) < 0) {
          paramInt = k;
        }
      }
      Object localObject2 = localObject1[i];
      if (localObject2 == null) {
        Intrinsics.throwNpe();
      }
      localObject2 = (Comparable)localObject2;
      localObject1 = localObject1[paramInt];
      if (localObject1 == null) {
        Intrinsics.throwNpe();
      }
      if (((Comparable)localObject2).compareTo(localObject1) <= 0) {
        return;
      }
      swap(i, paramInt);
    }
  }
  
  private final void siftUpFrom(int paramInt)
  {
    for (;;)
    {
      if (paramInt <= 0) {
        return;
      }
      Object localObject = this.a;
      if (localObject == null) {
        Intrinsics.throwNpe();
      }
      int i = (paramInt - 1) / 2;
      Comparable localComparable = localObject[i];
      if (localComparable == null) {
        Intrinsics.throwNpe();
      }
      localComparable = (Comparable)localComparable;
      localObject = localObject[paramInt];
      if (localObject == null) {
        Intrinsics.throwNpe();
      }
      if (localComparable.compareTo(localObject) <= 0) {
        return;
      }
      swap(paramInt, i);
      paramInt = i;
    }
  }
  
  private final void swap(int paramInt1, int paramInt2)
  {
    ThreadSafeHeapNode[] arrayOfThreadSafeHeapNode = this.a;
    if (arrayOfThreadSafeHeapNode == null) {
      Intrinsics.throwNpe();
    }
    ThreadSafeHeapNode localThreadSafeHeapNode1 = arrayOfThreadSafeHeapNode[paramInt2];
    if (localThreadSafeHeapNode1 == null) {
      Intrinsics.throwNpe();
    }
    ThreadSafeHeapNode localThreadSafeHeapNode2 = arrayOfThreadSafeHeapNode[paramInt1];
    if (localThreadSafeHeapNode2 == null) {
      Intrinsics.throwNpe();
    }
    arrayOfThreadSafeHeapNode[paramInt1] = localThreadSafeHeapNode1;
    arrayOfThreadSafeHeapNode[paramInt2] = localThreadSafeHeapNode2;
    localThreadSafeHeapNode1.setIndex(paramInt1);
    localThreadSafeHeapNode2.setIndex(paramInt2);
  }
  
  public final void addImpl(T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "node");
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      if (paramT.getHeap() == null) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    paramT.setHeap((ThreadSafeHeap)this);
    ThreadSafeHeapNode[] arrayOfThreadSafeHeapNode = realloc();
    int i = getSize();
    setSize(i + 1);
    arrayOfThreadSafeHeapNode[i] = paramT;
    paramT.setIndex(i);
    siftUpFrom(i);
  }
  
  public final void addLast(T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "node");
    try
    {
      addImpl(paramT);
      paramT = Unit.INSTANCE;
      return;
    }
    finally
    {
      paramT = finally;
      throw paramT;
    }
  }
  
  public final boolean addLastIf(T paramT, Function1<? super T, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "node");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "cond");
    try
    {
      boolean bool;
      if (((Boolean)paramFunction1.invoke(firstImpl())).booleanValue())
      {
        addImpl(paramT);
        bool = true;
      }
      else
      {
        bool = false;
      }
      return bool;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public final void clear()
  {
    try
    {
      Object localObject1 = this.a;
      if (localObject1 != null) {
        ArraysKt.fill$default((Object[])localObject1, null, 0, 0, 6, null);
      }
      this._size = 0;
      localObject1 = Unit.INSTANCE;
      return;
    }
    finally {}
  }
  
  public final T firstImpl()
  {
    Object localObject = this.a;
    if (localObject != null) {
      localObject = localObject[0];
    } else {
      localObject = null;
    }
    return localObject;
  }
  
  public final int getSize()
  {
    return this._size;
  }
  
  public final boolean isEmpty()
  {
    boolean bool;
    if (getSize() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final T peek()
  {
    try
    {
      ThreadSafeHeapNode localThreadSafeHeapNode = firstImpl();
      return localThreadSafeHeapNode;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final boolean remove(T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "node");
    try
    {
      ThreadSafeHeap localThreadSafeHeap = paramT.getHeap();
      boolean bool = true;
      int i = 0;
      if (localThreadSafeHeap == null)
      {
        bool = false;
      }
      else
      {
        int j = paramT.getIndex();
        if (DebugKt.getASSERTIONS_ENABLED())
        {
          if (j >= 0) {
            i = 1;
          }
          if (i == 0)
          {
            paramT = new java/lang/AssertionError;
            paramT.<init>();
            throw ((Throwable)paramT);
          }
        }
        removeAtImpl(j);
      }
      return bool;
    }
    finally {}
  }
  
  public final T removeAtImpl(int paramInt)
  {
    boolean bool = DebugKt.getASSERTIONS_ENABLED();
    int i = 0;
    int j;
    if (bool)
    {
      if (getSize() > 0) {
        j = 1;
      } else {
        j = 0;
      }
      if (j == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    ThreadSafeHeapNode[] arrayOfThreadSafeHeapNode = this.a;
    if (arrayOfThreadSafeHeapNode == null) {
      Intrinsics.throwNpe();
    }
    setSize(getSize() - 1);
    if (paramInt < getSize())
    {
      swap(paramInt, getSize());
      j = (paramInt - 1) / 2;
      if (paramInt > 0)
      {
        localObject = arrayOfThreadSafeHeapNode[paramInt];
        if (localObject == null) {
          Intrinsics.throwNpe();
        }
        localObject = (Comparable)localObject;
        ThreadSafeHeapNode localThreadSafeHeapNode = arrayOfThreadSafeHeapNode[j];
        if (localThreadSafeHeapNode == null) {
          Intrinsics.throwNpe();
        }
        if (((Comparable)localObject).compareTo(localThreadSafeHeapNode) < 0)
        {
          swap(paramInt, j);
          siftUpFrom(j);
          break label166;
        }
      }
      siftDownFrom(paramInt);
    }
    label166:
    Object localObject = arrayOfThreadSafeHeapNode[getSize()];
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    if (DebugKt.getASSERTIONS_ENABLED())
    {
      paramInt = i;
      if (((ThreadSafeHeapNode)localObject).getHeap() == (ThreadSafeHeap)this) {
        paramInt = 1;
      }
      if (paramInt == 0) {
        throw ((Throwable)new AssertionError());
      }
    }
    ((ThreadSafeHeapNode)localObject).setHeap((ThreadSafeHeap)null);
    ((ThreadSafeHeapNode)localObject).setIndex(-1);
    arrayOfThreadSafeHeapNode[getSize()] = ((ThreadSafeHeapNode)null);
    return localObject;
  }
  
  public final T removeFirstIf(Function1<? super T, Boolean> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    try
    {
      ThreadSafeHeapNode localThreadSafeHeapNode1 = firstImpl();
      ThreadSafeHeapNode localThreadSafeHeapNode2 = null;
      if (localThreadSafeHeapNode1 != null)
      {
        if (((Boolean)paramFunction1.invoke(localThreadSafeHeapNode1)).booleanValue()) {
          localThreadSafeHeapNode2 = removeAtImpl(0);
        }
        return localThreadSafeHeapNode2;
      }
      InlineMarker.finallyStart(2);
      InlineMarker.finallyEnd(2);
      return null;
    }
    finally
    {
      InlineMarker.finallyStart(1);
      InlineMarker.finallyEnd(1);
    }
  }
  
  public final T removeFirstOrNull()
  {
    try
    {
      ThreadSafeHeapNode localThreadSafeHeapNode;
      if (getSize() > 0) {
        localThreadSafeHeapNode = removeAtImpl(0);
      } else {
        localThreadSafeHeapNode = null;
      }
      return localThreadSafeHeapNode;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}
