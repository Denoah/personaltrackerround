package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\000\n\002\020\000\n\002\b\002\n\002\020\021\n\002\b\002\n\002\020\b\n\000\n\002\020\013\n\002\b\003\n\002\020\002\n\002\b\007\b\020\030\000*\b\b\000\020\001*\0020\0022\0020\002B\005?\006\002\020\003J\023\020\r\032\0020\0162\006\020\017\032\0028\000?\006\002\020\020J\006\020\021\032\0020\016J\b\020\022\032\0020\016H\002J\r\020\023\032\004\030\0018\000?\006\002\020\024R\030\020\004\032\n\022\006\022\004\030\0010\0020\005X?\016?\006\004\n\002\020\006R\016\020\007\032\0020\bX?\016?\006\002\n\000R\021\020\t\032\0020\n8F?\006\006\032\004\b\t\020\013R\016\020\f\032\0020\bX?\016?\006\002\n\000?\006\025"}, d2={"Lkotlinx/coroutines/internal/ArrayQueue;", "T", "", "()V", "elements", "", "[Ljava/lang/Object;", "head", "", "isEmpty", "", "()Z", "tail", "addLast", "", "element", "(Ljava/lang/Object;)V", "clear", "ensureCapacity", "removeFirstOrNull", "()Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public class ArrayQueue<T>
{
  private Object[] elements = new Object[16];
  private int head;
  private int tail;
  
  public ArrayQueue() {}
  
  private final void ensureCapacity()
  {
    Object[] arrayOfObject1 = this.elements;
    int i = arrayOfObject1.length;
    Object[] arrayOfObject2 = new Object[i << 1];
    ArraysKt.copyInto$default(arrayOfObject1, arrayOfObject2, 0, this.head, 0, 10, null);
    arrayOfObject1 = this.elements;
    int j = arrayOfObject1.length;
    int k = this.head;
    ArraysKt.copyInto$default(arrayOfObject1, arrayOfObject2, j - k, 0, k, 4, null);
    this.elements = arrayOfObject2;
    this.head = 0;
    this.tail = i;
  }
  
  public final void addLast(T paramT)
  {
    Intrinsics.checkParameterIsNotNull(paramT, "element");
    Object[] arrayOfObject = this.elements;
    int i = this.tail;
    arrayOfObject[i] = paramT;
    i = arrayOfObject.length - 1 & i + 1;
    this.tail = i;
    if (i == this.head) {
      ensureCapacity();
    }
  }
  
  public final void clear()
  {
    this.head = 0;
    this.tail = 0;
    this.elements = new Object[this.elements.length];
  }
  
  public final boolean isEmpty()
  {
    boolean bool;
    if (this.head == this.tail) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final T removeFirstOrNull()
  {
    int i = this.head;
    if (i == this.tail) {
      return null;
    }
    Object[] arrayOfObject = this.elements;
    Object localObject = arrayOfObject[i];
    arrayOfObject[i] = null;
    this.head = (i + 1 & arrayOfObject.length - 1);
    if (localObject != null) {
      return localObject;
    }
    throw new TypeCastException("null cannot be cast to non-null type T");
  }
}
