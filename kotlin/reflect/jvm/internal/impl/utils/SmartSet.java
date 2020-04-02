package kotlin.reflect.jvm.internal.impl.utils;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;

public final class SmartSet<T>
  extends AbstractSet<T>
{
  public static final Companion Companion = new Companion(null);
  private Object data;
  private int size;
  
  private SmartSet() {}
  
  @JvmStatic
  public static final <T> SmartSet<T> create()
  {
    return Companion.create();
  }
  
  public boolean add(T paramT)
  {
    if (size() == 0)
    {
      this.data = paramT;
    }
    else if (size() == 1)
    {
      if (Intrinsics.areEqual(this.data, paramT)) {
        return false;
      }
      this.data = new Object[] { this.data, paramT };
    }
    else
    {
      Object localObject;
      if (size() < 5)
      {
        localObject = this.data;
        if (localObject != null)
        {
          localObject = (Object[])localObject;
          if (ArraysKt.contains((Object[])localObject, paramT)) {
            return false;
          }
          if (size() == 4)
          {
            localObject = SetsKt.linkedSetOf(Arrays.copyOf((Object[])localObject, localObject.length));
            ((LinkedHashSet)localObject).add(paramT);
            paramT = (TT)localObject;
          }
          else
          {
            localObject = Arrays.copyOf((Object[])localObject, size() + 1);
            Intrinsics.checkExpressionValueIsNotNull(localObject, "java.util.Arrays.copyOf(this, newSize)");
            localObject[(localObject.length - 1)] = paramT;
            paramT = (TT)localObject;
          }
          this.data = paramT;
        }
        else
        {
          throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
      }
      else
      {
        localObject = this.data;
        if (localObject == null) {
          break label199;
        }
        if (!TypeIntrinsics.asMutableSet(localObject).add(paramT)) {
          return false;
        }
      }
    }
    setSize(size() + 1);
    return true;
    label199:
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableSet<T>");
  }
  
  public void clear()
  {
    this.data = null;
    setSize(0);
  }
  
  public boolean contains(Object paramObject)
  {
    boolean bool;
    if (size() == 0)
    {
      bool = false;
    }
    else if (size() == 1)
    {
      bool = Intrinsics.areEqual(this.data, paramObject);
    }
    else
    {
      Object localObject;
      if (size() < 5)
      {
        localObject = this.data;
        if (localObject != null) {
          bool = ArraysKt.contains((Object[])localObject, paramObject);
        } else {
          throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
      }
      else
      {
        localObject = this.data;
        if (localObject == null) {
          break label93;
        }
        bool = ((Set)localObject).contains(paramObject);
      }
    }
    return bool;
    label93:
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Set<T>");
  }
  
  public int getSize()
  {
    return this.size;
  }
  
  public Iterator<T> iterator()
  {
    Object localObject;
    if (size() == 0)
    {
      localObject = Collections.emptySet().iterator();
    }
    else if (size() == 1)
    {
      localObject = (Iterator)new SingletonIterator(this.data);
    }
    else if (size() < 5)
    {
      localObject = this.data;
      if (localObject != null) {
        localObject = (Iterator)new ArrayIterator((Object[])localObject);
      } else {
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
      }
    }
    else
    {
      localObject = this.data;
      if (localObject == null) {
        break label111;
      }
      localObject = TypeIntrinsics.asMutableSet(localObject).iterator();
    }
    return localObject;
    label111:
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableSet<T>");
  }
  
  public void setSize(int paramInt)
  {
    this.size = paramInt;
  }
  
  private static final class ArrayIterator<T>
    implements Iterator<T>, KMutableIterator
  {
    private final Iterator<T> arrayIterator;
    
    public ArrayIterator(T[] paramArrayOfT)
    {
      this.arrayIterator = ArrayIteratorKt.iterator(paramArrayOfT);
    }
    
    public boolean hasNext()
    {
      return this.arrayIterator.hasNext();
    }
    
    public T next()
    {
      return this.arrayIterator.next();
    }
    
    public Void remove()
    {
      throw ((Throwable)new UnsupportedOperationException());
    }
  }
  
  public static final class Companion
  {
    private Companion() {}
    
    @JvmStatic
    public final <T> SmartSet<T> create()
    {
      return new SmartSet(null);
    }
    
    @JvmStatic
    public final <T> SmartSet<T> create(Collection<? extends T> paramCollection)
    {
      Intrinsics.checkParameterIsNotNull(paramCollection, "set");
      SmartSet localSmartSet = new SmartSet(null);
      localSmartSet.addAll(paramCollection);
      return localSmartSet;
    }
  }
  
  private static final class SingletonIterator<T>
    implements Iterator<T>, KMutableIterator
  {
    private final T element;
    private boolean hasNext;
    
    public SingletonIterator(T paramT)
    {
      this.element = paramT;
      this.hasNext = true;
    }
    
    public boolean hasNext()
    {
      return this.hasNext;
    }
    
    public T next()
    {
      if (this.hasNext)
      {
        this.hasNext = false;
        return this.element;
      }
      throw ((Throwable)new NoSuchElementException());
    }
    
    public Void remove()
    {
      throw ((Throwable)new UnsupportedOperationException());
    }
  }
}
