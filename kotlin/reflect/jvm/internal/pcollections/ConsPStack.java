package kotlin.reflect.jvm.internal.pcollections;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class ConsPStack<E>
  implements Iterable<E>
{
  private static final ConsPStack<Object> EMPTY = new ConsPStack();
  final E first;
  final ConsPStack<E> rest;
  private final int size;
  
  private ConsPStack()
  {
    this.size = 0;
    this.first = null;
    this.rest = null;
  }
  
  private ConsPStack(E paramE, ConsPStack<E> paramConsPStack)
  {
    this.first = paramE;
    this.rest = paramConsPStack;
    paramConsPStack.size += 1;
  }
  
  public static <E> ConsPStack<E> empty()
  {
    return EMPTY;
  }
  
  private Iterator<E> iterator(int paramInt)
  {
    return new Itr(subList(paramInt));
  }
  
  private ConsPStack<E> minus(Object paramObject)
  {
    if (this.size == 0) {
      return this;
    }
    if (this.first.equals(paramObject)) {
      return this.rest;
    }
    paramObject = this.rest.minus(paramObject);
    if (paramObject == this.rest) {
      return this;
    }
    return new ConsPStack(this.first, paramObject);
  }
  
  private ConsPStack<E> subList(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= this.size))
    {
      if (paramInt == 0) {
        return this;
      }
      return this.rest.subList(paramInt - 1);
    }
    throw new IndexOutOfBoundsException();
  }
  
  public E get(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= this.size)) {
      try
      {
        Object localObject = iterator(paramInt).next();
        return localObject;
      }
      catch (NoSuchElementException localNoSuchElementException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Index: ");
        localStringBuilder.append(paramInt);
        throw new IndexOutOfBoundsException(localStringBuilder.toString());
      }
    }
    throw new IndexOutOfBoundsException();
  }
  
  public Iterator<E> iterator()
  {
    return iterator(0);
  }
  
  public ConsPStack<E> minus(int paramInt)
  {
    return minus(get(paramInt));
  }
  
  public ConsPStack<E> plus(E paramE)
  {
    return new ConsPStack(paramE, this);
  }
  
  public int size()
  {
    return this.size;
  }
  
  private static class Itr<E>
    implements Iterator<E>
  {
    private ConsPStack<E> next;
    
    public Itr(ConsPStack<E> paramConsPStack)
    {
      this.next = paramConsPStack;
    }
    
    public boolean hasNext()
    {
      boolean bool;
      if (this.next.size > 0) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public E next()
    {
      Object localObject = this.next.first;
      this.next = this.next.rest;
      return localObject;
    }
    
    public void remove()
    {
      throw new UnsupportedOperationException();
    }
  }
}
