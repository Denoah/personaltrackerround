package io.reactivex.internal.util;

import io.reactivex.Observer;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Predicate;
import org.reactivestreams.Subscriber;

public class AppendOnlyLinkedArrayList<T>
{
  final int capacity;
  final Object[] head;
  int offset;
  Object[] tail;
  
  public AppendOnlyLinkedArrayList(int paramInt)
  {
    this.capacity = paramInt;
    Object[] arrayOfObject = new Object[paramInt + 1];
    this.head = arrayOfObject;
    this.tail = arrayOfObject;
  }
  
  public <U> boolean accept(Observer<? super U> paramObserver)
  {
    Object[] arrayOfObject = this.head;
    int i = this.capacity;
    for (;;)
    {
      int j = 0;
      if (arrayOfObject == null) {
        break;
      }
      while (j < i)
      {
        Object localObject = arrayOfObject[j];
        if (localObject == null) {
          break;
        }
        if (NotificationLite.acceptFull(localObject, paramObserver)) {
          return true;
        }
        j++;
      }
      arrayOfObject = (Object[])arrayOfObject[i];
    }
    return false;
  }
  
  public <U> boolean accept(Subscriber<? super U> paramSubscriber)
  {
    Object[] arrayOfObject = this.head;
    int i = this.capacity;
    for (;;)
    {
      int j = 0;
      if (arrayOfObject == null) {
        break;
      }
      while (j < i)
      {
        Object localObject = arrayOfObject[j];
        if (localObject == null) {
          break;
        }
        if (NotificationLite.acceptFull(localObject, paramSubscriber)) {
          return true;
        }
        j++;
      }
      arrayOfObject = (Object[])arrayOfObject[i];
    }
    return false;
  }
  
  public void add(T paramT)
  {
    int i = this.capacity;
    int j = this.offset;
    int k = j;
    if (j == i)
    {
      Object[] arrayOfObject = new Object[i + 1];
      this.tail[i] = arrayOfObject;
      this.tail = arrayOfObject;
      k = 0;
    }
    this.tail[k] = paramT;
    this.offset = (k + 1);
  }
  
  public void forEachWhile(NonThrowingPredicate<? super T> paramNonThrowingPredicate)
  {
    Object[] arrayOfObject = this.head;
    int i = this.capacity;
    while (arrayOfObject != null)
    {
      for (int j = 0; j < i; j++)
      {
        Object localObject = arrayOfObject[j];
        if (localObject == null) {
          break;
        }
        if (paramNonThrowingPredicate.test(localObject)) {
          return;
        }
      }
      arrayOfObject = (Object[])arrayOfObject[i];
    }
  }
  
  public <S> void forEachWhile(S paramS, BiPredicate<? super S, ? super T> paramBiPredicate)
    throws Exception
  {
    Object[] arrayOfObject = this.head;
    int i = this.capacity;
    for (;;)
    {
      for (int j = 0; j < i; j++)
      {
        Object localObject = arrayOfObject[j];
        if (localObject == null) {
          return;
        }
        if (paramBiPredicate.test(paramS, localObject)) {
          return;
        }
      }
      arrayOfObject = (Object[])arrayOfObject[i];
    }
  }
  
  public void setFirst(T paramT)
  {
    this.head[0] = paramT;
  }
  
  public static abstract interface NonThrowingPredicate<T>
    extends Predicate<T>
  {
    public abstract boolean test(T paramT);
  }
}
