package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(bv={1, 0, 3}, d1={"\000L\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\003\n\002\020\036\n\002\b\002\n\002\020\021\n\002\020\000\n\002\b\007\n\002\020\013\n\002\b\002\n\002\020\002\n\002\b\021\n\002\030\002\n\002\b\013\n\002\030\002\n\002\030\002\n\002\b\025\b\007\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\002B\017\b\026\022\006\020\003\032\0020\004?\006\002\020\005B\007\b\026?\006\002\020\006B\025\b\026\022\f\020\007\032\b\022\004\022\0028\0000\b?\006\002\020\tJ\025\020\023\032\0020\0242\006\020\025\032\0028\000H\026?\006\002\020\026J\035\020\023\032\0020\0272\006\020\030\032\0020\0042\006\020\025\032\0028\000H\026?\006\002\020\031J\036\020\032\032\0020\0242\006\020\030\032\0020\0042\f\020\007\032\b\022\004\022\0028\0000\bH\026J\026\020\032\032\0020\0242\f\020\007\032\b\022\004\022\0028\0000\bH\026J\023\020\033\032\0020\0272\006\020\025\032\0028\000?\006\002\020\034J\023\020\035\032\0020\0272\006\020\025\032\0028\000?\006\002\020\034J\b\020\036\032\0020\027H\026J\026\020\037\032\0020\0242\006\020\025\032\0028\000H?\002?\006\002\020\026J\036\020 \032\0020\0272\006\020!\032\0020\0042\f\020\007\032\b\022\004\022\0028\0000\bH\002J\020\020\"\032\0020\0272\006\020#\032\0020\004H\002J\020\020$\032\0020\0042\006\020\030\032\0020\004H\002J\020\020%\032\0020\0272\006\020&\032\0020\004H\002J\035\020'\032\0020\0242\022\020(\032\016\022\004\022\0028\000\022\004\022\0020\0240)H?\bJ\013\020*\032\0028\000?\006\002\020+J\r\020,\032\004\030\0018\000?\006\002\020+J\026\020-\032\0028\0002\006\020\030\032\0020\004H?\002?\006\002\020.J\020\020/\032\0020\0042\006\020\030\032\0020\004H\002J\025\0200\032\0020\0042\006\020\025\032\0028\000H\026?\006\002\0201J\026\0202\032\0028\0002\006\020!\032\0020\004H?\b?\006\002\020.J\021\020!\032\0020\0042\006\020\030\032\0020\004H?\bJM\0203\032\0020\0272>\0204\032:\022\023\022\0210\004?\006\f\b6\022\b\b7\022\004\b\b(\016\022\033\022\031\022\006\022\004\030\0010\f0\013?\006\f\b6\022\b\b7\022\004\b\b(\007\022\004\022\0020\02705H\000?\006\002\b8J\b\0209\032\0020\024H\026J\013\020:\032\0028\000?\006\002\020+J\025\020;\032\0020\0042\006\020\025\032\0028\000H\026?\006\002\0201J\r\020<\032\004\030\0018\000?\006\002\020+J\020\020=\032\0020\0042\006\020\030\032\0020\004H\002J\035\020#\032\0020\0042\006\020>\032\0020\0042\006\020&\032\0020\004H\000?\006\002\b?J\020\020@\032\0020\0042\006\020\030\032\0020\004H\002J\025\020A\032\0020\0242\006\020\025\032\0028\000H\026?\006\002\020\026J\026\020B\032\0020\0242\f\020\007\032\b\022\004\022\0028\0000\bH\026J\025\020C\032\0028\0002\006\020\030\032\0020\004H\026?\006\002\020.J\013\020D\032\0028\000?\006\002\020+J\r\020E\032\004\030\0018\000?\006\002\020+J\013\020F\032\0028\000?\006\002\020+J\r\020G\032\004\030\0018\000?\006\002\020+J\026\020H\032\0020\0242\f\020\007\032\b\022\004\022\0028\0000\bH\026J\036\020I\032\0028\0002\006\020\030\032\0020\0042\006\020\025\032\0028\000H?\002?\006\002\020JR\030\020\n\032\n\022\006\022\004\030\0010\f0\013X?\016?\006\004\n\002\020\rR\016\020\016\032\0020\004X?\016?\006\002\n\000R\036\020\020\032\0020\0042\006\020\017\032\0020\004@RX?\016?\006\b\n\000\032\004\b\021\020\022?\006K"}, d2={"Lkotlin/collections/ArrayDeque;", "E", "Lkotlin/collections/AbstractMutableList;", "initialCapacity", "", "(I)V", "()V", "elements", "", "(Ljava/util/Collection;)V", "elementData", "", "", "[Ljava/lang/Object;", "head", "<set-?>", "size", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "", "index", "(ILjava/lang/Object;)V", "addAll", "addFirst", "(Ljava/lang/Object;)V", "addLast", "clear", "contains", "copyCollectionElements", "internalIndex", "copyElements", "newCapacity", "decremented", "ensureCapacity", "minCapacity", "filterInPlace", "predicate", "Lkotlin/Function1;", "first", "()Ljava/lang/Object;", "firstOrNull", "get", "(I)Ljava/lang/Object;", "incremented", "indexOf", "(Ljava/lang/Object;)I", "internalGet", "internalStructure", "structure", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "internalStructure$kotlin_stdlib", "isEmpty", "last", "lastIndexOf", "lastOrNull", "negativeMod", "oldCapacity", "newCapacity$kotlin_stdlib", "positiveMod", "remove", "removeAll", "removeAt", "removeFirst", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class ArrayDeque<E>
  extends AbstractMutableList<E>
{
  private Object[] elementData;
  private int head;
  private int size;
  
  public ArrayDeque()
  {
    this.elementData = ArrayDequeKt.access$getEmptyElementData$p();
  }
  
  public ArrayDeque(int paramInt)
  {
    if (paramInt == 0)
    {
      localObject = ArrayDequeKt.access$getEmptyElementData$p();
    }
    else
    {
      if (paramInt <= 0) {
        break label30;
      }
      localObject = new Object[paramInt];
    }
    this.elementData = ((Object[])localObject);
    return;
    label30:
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Illegal Capacity: ");
    ((StringBuilder)localObject).append(paramInt);
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString()));
  }
  
  public ArrayDeque(Collection<? extends E> paramCollection)
  {
    int i = 0;
    paramCollection = paramCollection.toArray(new Object[0]);
    if (paramCollection != null)
    {
      this.elementData = paramCollection;
      this.size = paramCollection.length;
      if (paramCollection.length == 0) {
        i = 1;
      }
      if (i != 0) {
        this.elementData = ArrayDequeKt.access$getEmptyElementData$p();
      }
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
  }
  
  private final void copyCollectionElements(int paramInt, Collection<? extends E> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    int i = this.elementData.length;
    while ((paramInt < i) && (localIterator.hasNext()))
    {
      this.elementData[paramInt] = localIterator.next();
      paramInt++;
    }
    paramInt = 0;
    i = this.head;
    while ((paramInt < i) && (localIterator.hasNext()))
    {
      this.elementData[paramInt] = localIterator.next();
      paramInt++;
    }
    this.size = (size() + paramCollection.size());
  }
  
  private final void copyElements(int paramInt)
  {
    Object[] arrayOfObject1 = new Object[paramInt];
    Object[] arrayOfObject2 = this.elementData;
    ArraysKt.copyInto(arrayOfObject2, arrayOfObject1, 0, this.head, arrayOfObject2.length);
    arrayOfObject2 = this.elementData;
    paramInt = arrayOfObject2.length;
    int i = this.head;
    ArraysKt.copyInto(arrayOfObject2, arrayOfObject1, paramInt - i, 0, i);
    this.head = 0;
    this.elementData = arrayOfObject1;
  }
  
  private final int decremented(int paramInt)
  {
    if (paramInt == 0) {
      paramInt = ArraysKt.getLastIndex(this.elementData);
    } else {
      paramInt--;
    }
    return paramInt;
  }
  
  private final void ensureCapacity(int paramInt)
  {
    if (paramInt >= 0)
    {
      Object[] arrayOfObject = this.elementData;
      if (paramInt <= arrayOfObject.length) {
        return;
      }
      if (arrayOfObject == ArrayDequeKt.access$getEmptyElementData$p())
      {
        this.elementData = new Object[RangesKt.coerceAtLeast(paramInt, 10)];
        return;
      }
      copyElements(newCapacity$kotlin_stdlib(this.elementData.length, paramInt));
      return;
    }
    throw ((Throwable)new IllegalStateException("Deque is too big."));
  }
  
  private final boolean filterInPlace(Function1<? super E, Boolean> paramFunction1)
  {
    boolean bool1 = isEmpty();
    int i = 0;
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool4 = bool2;
    if (!bool1)
    {
      int j;
      if (access$getElementData$p(this).length == 0) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        bool4 = bool2;
      }
      else
      {
        j = size();
        int k = access$positiveMod(this, access$getHead$p(this) + j);
        int m = access$getHead$p(this);
        Object localObject;
        if (access$getHead$p(this) < k)
        {
          i = access$getHead$p(this);
          j = m;
          while (i < k)
          {
            localObject = access$getElementData$p(this)[i];
            if (((Boolean)paramFunction1.invoke(localObject)).booleanValue())
            {
              access$getElementData$p(this)[j] = localObject;
              j++;
            }
            else
            {
              bool3 = true;
            }
            i++;
          }
          ArraysKt.fill(access$getElementData$p(this), null, j, k);
        }
        else
        {
          j = access$getHead$p(this);
          int n = access$getElementData$p(this).length;
          bool3 = false;
          while (j < n)
          {
            localObject = access$getElementData$p(this)[j];
            access$getElementData$p(this)[j] = null;
            if (((Boolean)paramFunction1.invoke(localObject)).booleanValue())
            {
              access$getElementData$p(this)[m] = localObject;
              m++;
            }
            else
            {
              bool3 = true;
            }
            j++;
          }
          j = access$positiveMod(this, m);
          for (m = i; m < k; m++)
          {
            localObject = access$getElementData$p(this)[m];
            access$getElementData$p(this)[m] = null;
            if (((Boolean)paramFunction1.invoke(localObject)).booleanValue())
            {
              access$getElementData$p(this)[j] = localObject;
              j = access$incremented(this, j);
            }
            else
            {
              bool3 = true;
            }
          }
        }
        bool4 = bool3;
        if (bool3)
        {
          access$setSize$p(this, access$negativeMod(this, j - access$getHead$p(this)));
          bool4 = bool3;
        }
      }
    }
    return bool4;
  }
  
  private final int incremented(int paramInt)
  {
    if (paramInt == ArraysKt.getLastIndex(this.elementData)) {
      paramInt = 0;
    } else {
      paramInt++;
    }
    return paramInt;
  }
  
  private final E internalGet(int paramInt)
  {
    return access$getElementData$p(this)[paramInt];
  }
  
  private final int internalIndex(int paramInt)
  {
    return access$positiveMod(this, access$getHead$p(this) + paramInt);
  }
  
  private final int negativeMod(int paramInt)
  {
    int i = paramInt;
    if (paramInt < 0) {
      i = paramInt + this.elementData.length;
    }
    return i;
  }
  
  private final int positiveMod(int paramInt)
  {
    Object[] arrayOfObject = this.elementData;
    int i = paramInt;
    if (paramInt >= arrayOfObject.length) {
      i = paramInt - arrayOfObject.length;
    }
    return i;
  }
  
  public void add(int paramInt, E paramE)
  {
    AbstractList.Companion.checkPositionIndex$kotlin_stdlib(paramInt, size());
    if (paramInt == size())
    {
      addLast(paramE);
      return;
    }
    if (paramInt == 0)
    {
      addFirst(paramE);
      return;
    }
    ensureCapacity(size() + 1);
    int i = access$positiveMod(this, access$getHead$p(this) + paramInt);
    Object[] arrayOfObject;
    if (paramInt < size() + 1 >> 1)
    {
      paramInt = decremented(i);
      int j = decremented(this.head);
      i = this.head;
      if (paramInt >= i)
      {
        arrayOfObject = this.elementData;
        arrayOfObject[j] = arrayOfObject[i];
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i, i + 1, paramInt + 1);
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i - 1, i, arrayOfObject.length);
        arrayOfObject = this.elementData;
        arrayOfObject[(arrayOfObject.length - 1)] = arrayOfObject[0];
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, 1, paramInt + 1);
      }
      this.elementData[paramInt] = paramE;
      this.head = j;
    }
    else
    {
      paramInt = size();
      paramInt = access$positiveMod(this, access$getHead$p(this) + paramInt);
      if (i < paramInt)
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i + 1, i, paramInt);
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, 1, 0, paramInt);
        arrayOfObject = this.elementData;
        arrayOfObject[0] = arrayOfObject[(arrayOfObject.length - 1)];
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i + 1, i, arrayOfObject.length - 1);
      }
      this.elementData[i] = paramE;
    }
    this.size = (size() + 1);
  }
  
  public boolean add(E paramE)
  {
    addLast(paramE);
    return true;
  }
  
  public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "elements");
    AbstractList.Companion.checkPositionIndex$kotlin_stdlib(paramInt, size());
    if (paramCollection.isEmpty()) {
      return false;
    }
    if (paramInt == size()) {
      return addAll(paramCollection);
    }
    ensureCapacity(size() + paramCollection.size());
    int i = size();
    int j = access$positiveMod(this, access$getHead$p(this) + i);
    i = access$positiveMod(this, access$getHead$p(this) + paramInt);
    int k = paramCollection.size();
    Object[] arrayOfObject;
    if (paramInt < size() + 1 >> 1)
    {
      j = this.head;
      paramInt = j - k;
      if (i >= j)
      {
        if (paramInt >= 0)
        {
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, j, i);
        }
        else
        {
          arrayOfObject = this.elementData;
          paramInt += arrayOfObject.length;
          int m = arrayOfObject.length - paramInt;
          if (m >= i - j)
          {
            ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, j, i);
          }
          else
          {
            ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, j, j + m);
            arrayOfObject = this.elementData;
            ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, this.head + m, i);
          }
        }
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, j, arrayOfObject.length);
        if (k >= i)
        {
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, arrayOfObject.length - k, 0, i);
        }
        else
        {
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, arrayOfObject.length - k, 0, k);
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, k, i);
        }
      }
      this.head = paramInt;
      copyCollectionElements(negativeMod(i - k), paramCollection);
    }
    else
    {
      paramInt = i + k;
      if (i < j)
      {
        k += j;
        arrayOfObject = this.elementData;
        if (k <= arrayOfObject.length)
        {
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, i, j);
        }
        else if (paramInt >= arrayOfObject.length)
        {
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt - arrayOfObject.length, i, j);
        }
        else
        {
          k = j - (k - arrayOfObject.length);
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, k, j);
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, i, k);
        }
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, k, 0, j);
        arrayOfObject = this.elementData;
        if (paramInt >= arrayOfObject.length)
        {
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt - arrayOfObject.length, i, arrayOfObject.length);
        }
        else
        {
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, arrayOfObject.length - k, arrayOfObject.length);
          arrayOfObject = this.elementData;
          ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt, i, arrayOfObject.length - k);
        }
      }
      copyCollectionElements(i, paramCollection);
    }
    return true;
  }
  
  public boolean addAll(Collection<? extends E> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "elements");
    if (paramCollection.isEmpty()) {
      return false;
    }
    ensureCapacity(size() + paramCollection.size());
    int i = size();
    copyCollectionElements(access$positiveMod(this, access$getHead$p(this) + i), paramCollection);
    return true;
  }
  
  public final void addFirst(E paramE)
  {
    ensureCapacity(size() + 1);
    int i = decremented(this.head);
    this.head = i;
    this.elementData[i] = paramE;
    this.size = (size() + 1);
  }
  
  public final void addLast(E paramE)
  {
    ensureCapacity(size() + 1);
    Object[] arrayOfObject = this.elementData;
    int i = size();
    arrayOfObject[access$positiveMod(this, access$getHead$p(this) + i)] = paramE;
    this.size = (size() + 1);
  }
  
  public void clear()
  {
    int i = size();
    int j = access$positiveMod(this, access$getHead$p(this) + i);
    i = this.head;
    if (i < j)
    {
      ArraysKt.fill(this.elementData, null, i, j);
    }
    else if ((isEmpty() ^ true))
    {
      Object[] arrayOfObject = this.elementData;
      ArraysKt.fill(arrayOfObject, null, this.head, arrayOfObject.length);
      ArraysKt.fill(this.elementData, null, 0, j);
    }
    this.head = 0;
    this.size = 0;
  }
  
  public boolean contains(Object paramObject)
  {
    boolean bool;
    if (indexOf(paramObject) != -1) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final E first()
  {
    if (!isEmpty())
    {
      int i = this.head;
      return access$getElementData$p(this)[i];
    }
    throw ((Throwable)new NoSuchElementException("ArrayDeque is empty."));
  }
  
  public final E firstOrNull()
  {
    Object localObject;
    if (isEmpty())
    {
      localObject = null;
    }
    else
    {
      int i = this.head;
      localObject = access$getElementData$p(this)[i];
    }
    return localObject;
  }
  
  public E get(int paramInt)
  {
    AbstractList.Companion.checkElementIndex$kotlin_stdlib(paramInt, size());
    paramInt = access$positiveMod(this, access$getHead$p(this) + paramInt);
    return access$getElementData$p(this)[paramInt];
  }
  
  public int getSize()
  {
    return this.size;
  }
  
  public int indexOf(Object paramObject)
  {
    int i = size();
    int j = access$positiveMod(this, access$getHead$p(this) + i);
    i = this.head;
    if (i < j) {
      while (i < j)
      {
        if (Intrinsics.areEqual(paramObject, this.elementData[i]))
        {
          j = this.head;
          return i - j;
        }
        i++;
      }
    }
    if (i >= j)
    {
      int k = this.elementData.length;
      for (;;)
      {
        if (i >= k) {
          break label104;
        }
        if (Intrinsics.areEqual(paramObject, this.elementData[i]))
        {
          j = this.head;
          break;
        }
        i++;
      }
      label104:
      for (i = 0;; i++)
      {
        if (i >= j) {
          break label146;
        }
        if (Intrinsics.areEqual(paramObject, this.elementData[i]))
        {
          i += this.elementData.length;
          j = this.head;
          break;
        }
      }
    }
    label146:
    return -1;
  }
  
  public final void internalStructure$kotlin_stdlib(Function2<? super Integer, ? super Object[], Unit> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "structure");
    int i = size();
    i = access$positiveMod(this, access$getHead$p(this) + i);
    if (isEmpty())
    {
      paramFunction2.invoke(Integer.valueOf(this.head), new Object[0]);
      return;
    }
    Object[] arrayOfObject1 = new Object[size()];
    int j = this.head;
    if (j < i)
    {
      ArraysKt.copyInto$default(this.elementData, arrayOfObject1, 0, j, i, 2, null);
      paramFunction2.invoke(Integer.valueOf(this.head), arrayOfObject1);
    }
    else
    {
      ArraysKt.copyInto$default(this.elementData, arrayOfObject1, 0, j, 0, 10, null);
      Object[] arrayOfObject2 = this.elementData;
      ArraysKt.copyInto(arrayOfObject2, arrayOfObject1, arrayOfObject2.length - this.head, 0, i);
      paramFunction2.invoke(Integer.valueOf(this.head - this.elementData.length), arrayOfObject1);
    }
  }
  
  public boolean isEmpty()
  {
    boolean bool;
    if (size() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final E last()
  {
    if (!isEmpty())
    {
      int i = CollectionsKt.getLastIndex(this);
      i = access$positiveMod(this, access$getHead$p(this) + i);
      return access$getElementData$p(this)[i];
    }
    throw ((Throwable)new NoSuchElementException("ArrayDeque is empty."));
  }
  
  public int lastIndexOf(Object paramObject)
  {
    int i = size();
    i = access$positiveMod(this, access$getHead$p(this) + i);
    int j = this.head;
    if (j < i)
    {
      i--;
      if (i >= j) {
        for (;;)
        {
          if (Intrinsics.areEqual(paramObject, this.elementData[i]))
          {
            j = this.head;
            return i - j;
          }
          if (i == j) {
            break;
          }
          i--;
        }
      }
    }
    else if (j > i)
    {
      i--;
      for (;;)
      {
        if (i < 0) {
          break label114;
        }
        if (Intrinsics.areEqual(paramObject, this.elementData[i]))
        {
          i += this.elementData.length;
          j = this.head;
          break;
        }
        i--;
      }
      label114:
      i = ArraysKt.getLastIndex(this.elementData);
      j = this.head;
      if (i >= j) {
        for (;;)
        {
          if (Intrinsics.areEqual(paramObject, this.elementData[i]))
          {
            j = this.head;
            break;
          }
          if (i == j) {
            break label164;
          }
          i--;
        }
      }
    }
    label164:
    return -1;
  }
  
  public final E lastOrNull()
  {
    Object localObject;
    if (isEmpty())
    {
      localObject = null;
    }
    else
    {
      int i = CollectionsKt.getLastIndex(this);
      i = access$positiveMod(this, access$getHead$p(this) + i);
      localObject = access$getElementData$p(this)[i];
    }
    return localObject;
  }
  
  public final int newCapacity$kotlin_stdlib(int paramInt1, int paramInt2)
  {
    int i = paramInt1 + (paramInt1 >> 1);
    paramInt1 = i;
    if (i - paramInt2 < 0) {
      paramInt1 = paramInt2;
    }
    i = paramInt1;
    if (paramInt1 - 2147483639 > 0) {
      if (paramInt2 > 2147483639) {
        i = Integer.MAX_VALUE;
      } else {
        i = 2147483639;
      }
    }
    return i;
  }
  
  public boolean remove(Object paramObject)
  {
    int i = indexOf(paramObject);
    if (i == -1) {
      return false;
    }
    remove(i);
    return true;
  }
  
  public boolean removeAll(Collection<? extends Object> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "elements");
    boolean bool1 = isEmpty();
    int i = 0;
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool4 = bool2;
    if (!bool1)
    {
      int j;
      if (access$getElementData$p(this).length == 0) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        bool4 = bool2;
      }
      else
      {
        j = size();
        int k = access$positiveMod(this, access$getHead$p(this) + j);
        j = access$getHead$p(this);
        int m;
        Object localObject;
        if (access$getHead$p(this) < k)
        {
          for (m = access$getHead$p(this); m < k; m++)
          {
            localObject = access$getElementData$p(this)[m];
            if ((paramCollection.contains(localObject) ^ true))
            {
              access$getElementData$p(this)[j] = localObject;
              j++;
            }
            else
            {
              bool3 = true;
            }
          }
          ArraysKt.fill(access$getElementData$p(this), null, j, k);
        }
        else
        {
          m = access$getHead$p(this);
          int n = access$getElementData$p(this).length;
          bool3 = false;
          while (m < n)
          {
            localObject = access$getElementData$p(this)[m];
            access$getElementData$p(this)[m] = null;
            if ((paramCollection.contains(localObject) ^ true))
            {
              access$getElementData$p(this)[j] = localObject;
              j++;
            }
            else
            {
              bool3 = true;
            }
            m++;
          }
          j = access$positiveMod(this, j);
          for (m = i; m < k; m++)
          {
            localObject = access$getElementData$p(this)[m];
            access$getElementData$p(this)[m] = null;
            if ((paramCollection.contains(localObject) ^ true))
            {
              access$getElementData$p(this)[j] = localObject;
              j = access$incremented(this, j);
            }
            else
            {
              bool3 = true;
            }
          }
        }
        bool4 = bool3;
        if (bool3)
        {
          access$setSize$p(this, access$negativeMod(this, j - access$getHead$p(this)));
          bool4 = bool3;
        }
      }
    }
    return bool4;
  }
  
  public E removeAt(int paramInt)
  {
    AbstractList.Companion.checkElementIndex$kotlin_stdlib(paramInt, size());
    if (paramInt == CollectionsKt.getLastIndex(this)) {
      return removeLast();
    }
    if (paramInt == 0) {
      return removeFirst();
    }
    int i = access$positiveMod(this, access$getHead$p(this) + paramInt);
    Object localObject = access$getElementData$p(this)[i];
    Object[] arrayOfObject;
    if (paramInt < size() >> 1)
    {
      paramInt = this.head;
      if (i >= paramInt)
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt + 1, paramInt, i);
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, 1, 0, i);
        arrayOfObject = this.elementData;
        arrayOfObject[0] = arrayOfObject[(arrayOfObject.length - 1)];
        paramInt = this.head;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, paramInt + 1, paramInt, arrayOfObject.length - 1);
      }
      arrayOfObject = this.elementData;
      paramInt = this.head;
      arrayOfObject[paramInt] = null;
      this.head = incremented(paramInt);
    }
    else
    {
      paramInt = CollectionsKt.getLastIndex(this);
      paramInt = access$positiveMod(this, access$getHead$p(this) + paramInt);
      if (i <= paramInt)
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i, i + 1, paramInt + 1);
      }
      else
      {
        arrayOfObject = this.elementData;
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, i, i + 1, arrayOfObject.length);
        arrayOfObject = this.elementData;
        arrayOfObject[(arrayOfObject.length - 1)] = arrayOfObject[0];
        ArraysKt.copyInto(arrayOfObject, arrayOfObject, 0, 1, paramInt + 1);
      }
      this.elementData[paramInt] = null;
    }
    this.size = (size() - 1);
    return localObject;
  }
  
  public final E removeFirst()
  {
    if (!isEmpty())
    {
      int i = this.head;
      Object localObject = access$getElementData$p(this)[i];
      Object[] arrayOfObject = this.elementData;
      i = this.head;
      arrayOfObject[i] = null;
      this.head = incremented(i);
      this.size = (size() - 1);
      return localObject;
    }
    throw ((Throwable)new NoSuchElementException("ArrayDeque is empty."));
  }
  
  public final E removeFirstOrNull()
  {
    Object localObject;
    if (isEmpty()) {
      localObject = null;
    } else {
      localObject = removeFirst();
    }
    return localObject;
  }
  
  public final E removeLast()
  {
    if (!isEmpty())
    {
      int i = CollectionsKt.getLastIndex(this);
      i = access$positiveMod(this, access$getHead$p(this) + i);
      Object localObject = access$getElementData$p(this)[i];
      this.elementData[i] = null;
      this.size = (size() - 1);
      return localObject;
    }
    throw ((Throwable)new NoSuchElementException("ArrayDeque is empty."));
  }
  
  public final E removeLastOrNull()
  {
    Object localObject;
    if (isEmpty()) {
      localObject = null;
    } else {
      localObject = removeLast();
    }
    return localObject;
  }
  
  public boolean retainAll(Collection<? extends Object> paramCollection)
  {
    Intrinsics.checkParameterIsNotNull(paramCollection, "elements");
    boolean bool1 = isEmpty();
    int i = 0;
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool4 = bool2;
    if (!bool1)
    {
      int j;
      if (access$getElementData$p(this).length == 0) {
        j = 1;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        bool4 = bool2;
      }
      else
      {
        j = size();
        int k = access$positiveMod(this, access$getHead$p(this) + j);
        int m = access$getHead$p(this);
        Object localObject;
        if (access$getHead$p(this) < k)
        {
          i = access$getHead$p(this);
          j = m;
          while (i < k)
          {
            localObject = access$getElementData$p(this)[i];
            if (paramCollection.contains(localObject))
            {
              access$getElementData$p(this)[j] = localObject;
              j++;
            }
            else
            {
              bool3 = true;
            }
            i++;
          }
          ArraysKt.fill(access$getElementData$p(this), null, j, k);
        }
        else
        {
          j = access$getHead$p(this);
          int n = access$getElementData$p(this).length;
          bool3 = false;
          while (j < n)
          {
            localObject = access$getElementData$p(this)[j];
            access$getElementData$p(this)[j] = null;
            if (paramCollection.contains(localObject))
            {
              access$getElementData$p(this)[m] = localObject;
              m++;
            }
            else
            {
              bool3 = true;
            }
            j++;
          }
          j = access$positiveMod(this, m);
          for (m = i; m < k; m++)
          {
            localObject = access$getElementData$p(this)[m];
            access$getElementData$p(this)[m] = null;
            if (paramCollection.contains(localObject))
            {
              access$getElementData$p(this)[j] = localObject;
              j = access$incremented(this, j);
            }
            else
            {
              bool3 = true;
            }
          }
        }
        bool4 = bool3;
        if (bool3)
        {
          access$setSize$p(this, access$negativeMod(this, j - access$getHead$p(this)));
          bool4 = bool3;
        }
      }
    }
    return bool4;
  }
  
  public E set(int paramInt, E paramE)
  {
    AbstractList.Companion.checkElementIndex$kotlin_stdlib(paramInt, size());
    paramInt = access$positiveMod(this, access$getHead$p(this) + paramInt);
    Object localObject = access$getElementData$p(this)[paramInt];
    this.elementData[paramInt] = paramE;
    return localObject;
  }
}
