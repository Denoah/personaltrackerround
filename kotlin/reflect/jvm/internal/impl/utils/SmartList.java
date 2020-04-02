package kotlin.reflect.jvm.internal.impl.utils;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class SmartList<E>
  extends AbstractList<E>
  implements RandomAccess
{
  private Object myElem;
  private int mySize;
  
  public SmartList() {}
  
  public void add(int paramInt, E paramE)
  {
    if (paramInt >= 0)
    {
      int i = this.mySize;
      if (paramInt <= i)
      {
        if (i == 0)
        {
          this.myElem = paramE;
        }
        else if ((i == 1) && (paramInt == 0))
        {
          this.myElem = new Object[] { paramE, this.myElem };
        }
        else
        {
          i = this.mySize;
          Object[] arrayOfObject1 = new Object[i + 1];
          if (i == 1)
          {
            arrayOfObject1[0] = this.myElem;
          }
          else
          {
            Object[] arrayOfObject2 = (Object[])this.myElem;
            System.arraycopy(arrayOfObject2, 0, arrayOfObject1, 0, paramInt);
            System.arraycopy(arrayOfObject2, paramInt, arrayOfObject1, paramInt + 1, this.mySize - paramInt);
          }
          arrayOfObject1[paramInt] = paramE;
          this.myElem = arrayOfObject1;
        }
        this.mySize += 1;
        this.modCount += 1;
        return;
      }
    }
    paramE = new StringBuilder();
    paramE.append("Index: ");
    paramE.append(paramInt);
    paramE.append(", Size: ");
    paramE.append(this.mySize);
    throw new IndexOutOfBoundsException(paramE.toString());
  }
  
  public boolean add(E paramE)
  {
    int i = this.mySize;
    if (i == 0)
    {
      this.myElem = paramE;
    }
    else if (i == 1)
    {
      this.myElem = new Object[] { this.myElem, paramE };
    }
    else
    {
      Object[] arrayOfObject1 = (Object[])this.myElem;
      int j = arrayOfObject1.length;
      Object[] arrayOfObject2 = arrayOfObject1;
      if (i >= j)
      {
        int k = j * 3 / 2 + 1;
        int m = i + 1;
        i = k;
        if (k < m) {
          i = m;
        }
        arrayOfObject2 = new Object[i];
        this.myElem = arrayOfObject2;
        System.arraycopy(arrayOfObject1, 0, arrayOfObject2, 0, j);
      }
      arrayOfObject2[this.mySize] = paramE;
    }
    this.mySize += 1;
    this.modCount += 1;
    return true;
  }
  
  public void clear()
  {
    this.myElem = null;
    this.mySize = 0;
    this.modCount += 1;
  }
  
  public E get(int paramInt)
  {
    if (paramInt >= 0)
    {
      int i = this.mySize;
      if (paramInt < i)
      {
        if (i == 1) {
          return this.myElem;
        }
        return ((Object[])this.myElem)[paramInt];
      }
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Index: ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(", Size: ");
    localStringBuilder.append(this.mySize);
    throw new IndexOutOfBoundsException(localStringBuilder.toString());
  }
  
  public Iterator<E> iterator()
  {
    int i = this.mySize;
    if (i == 0)
    {
      localObject = EmptyIterator.getInstance();
      if (localObject == null) {
        $$$reportNull$$$0(2);
      }
      return localObject;
    }
    if (i == 1) {
      return new SingletonIterator();
    }
    Object localObject = super.iterator();
    if (localObject == null) {
      $$$reportNull$$$0(3);
    }
    return localObject;
  }
  
  public E remove(int paramInt)
  {
    if (paramInt >= 0)
    {
      int i = this.mySize;
      if (paramInt < i)
      {
        if (i == 1)
        {
          localObject = this.myElem;
          this.myElem = null;
        }
        else
        {
          Object[] arrayOfObject = (Object[])this.myElem;
          localObject = arrayOfObject[paramInt];
          if (i == 2)
          {
            this.myElem = arrayOfObject[(1 - paramInt)];
          }
          else
          {
            i = i - paramInt - 1;
            if (i > 0) {
              System.arraycopy(arrayOfObject, paramInt + 1, arrayOfObject, paramInt, i);
            }
            arrayOfObject[(this.mySize - 1)] = null;
          }
        }
        this.mySize -= 1;
        this.modCount += 1;
        return localObject;
      }
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Index: ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append(", Size: ");
    ((StringBuilder)localObject).append(this.mySize);
    throw new IndexOutOfBoundsException(((StringBuilder)localObject).toString());
  }
  
  public E set(int paramInt, E paramE)
  {
    if (paramInt >= 0)
    {
      int i = this.mySize;
      if (paramInt < i)
      {
        Object localObject;
        if (i == 1)
        {
          localObject = this.myElem;
          this.myElem = paramE;
          paramE = localObject;
        }
        else
        {
          Object[] arrayOfObject = (Object[])this.myElem;
          localObject = arrayOfObject[paramInt];
          arrayOfObject[paramInt] = paramE;
          paramE = localObject;
        }
        return paramE;
      }
    }
    paramE = new StringBuilder();
    paramE.append("Index: ");
    paramE.append(paramInt);
    paramE.append(", Size: ");
    paramE.append(this.mySize);
    throw new IndexOutOfBoundsException(paramE.toString());
  }
  
  public int size()
  {
    return this.mySize;
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    if (paramArrayOfT == null) {
      $$$reportNull$$$0(4);
    }
    int i = paramArrayOfT.length;
    int j = this.mySize;
    if (j == 1)
    {
      if (i != 0)
      {
        paramArrayOfT[0] = this.myElem;
      }
      else
      {
        paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), 1);
        paramArrayOfT[0] = this.myElem;
        if (paramArrayOfT == null) {
          $$$reportNull$$$0(5);
        }
        return paramArrayOfT;
      }
    }
    else
    {
      if (i < j)
      {
        paramArrayOfT = (Object[])Arrays.copyOf((Object[])this.myElem, j, paramArrayOfT.getClass());
        if (paramArrayOfT == null) {
          $$$reportNull$$$0(6);
        }
        return paramArrayOfT;
      }
      if (j != 0) {
        System.arraycopy(this.myElem, 0, paramArrayOfT, 0, j);
      }
    }
    j = this.mySize;
    if (i > j) {
      paramArrayOfT[j] = null;
    }
    if (paramArrayOfT == null) {
      $$$reportNull$$$0(7);
    }
    return paramArrayOfT;
  }
  
  private static class EmptyIterator<T>
    implements Iterator<T>
  {
    private static final EmptyIterator INSTANCE = new EmptyIterator();
    
    private EmptyIterator() {}
    
    public static <T> EmptyIterator<T> getInstance()
    {
      return INSTANCE;
    }
    
    public boolean hasNext()
    {
      return false;
    }
    
    public T next()
    {
      throw new NoSuchElementException();
    }
    
    public void remove()
    {
      throw new IllegalStateException();
    }
  }
  
  private class SingletonIterator
    extends SmartList.SingletonIteratorBase<E>
  {
    private final int myInitialModCount = SmartList.this.modCount;
    
    public SingletonIterator()
    {
      super();
    }
    
    protected void checkCoModification()
    {
      if (SmartList.this.modCount == this.myInitialModCount) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("ModCount: ");
      localStringBuilder.append(SmartList.this.modCount);
      localStringBuilder.append("; expected: ");
      localStringBuilder.append(this.myInitialModCount);
      throw new ConcurrentModificationException(localStringBuilder.toString());
    }
    
    protected E getElement()
    {
      return SmartList.this.myElem;
    }
    
    public void remove()
    {
      checkCoModification();
      SmartList.this.clear();
    }
  }
  
  private static abstract class SingletonIteratorBase<T>
    implements Iterator<T>
  {
    private boolean myVisited;
    
    private SingletonIteratorBase() {}
    
    protected abstract void checkCoModification();
    
    protected abstract T getElement();
    
    public final boolean hasNext()
    {
      return this.myVisited ^ true;
    }
    
    public final T next()
    {
      if (!this.myVisited)
      {
        this.myVisited = true;
        checkCoModification();
        return getElement();
      }
      throw new NoSuchElementException();
    }
  }
}
