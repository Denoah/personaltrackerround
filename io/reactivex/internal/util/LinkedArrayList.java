package io.reactivex.internal.util;

import java.util.ArrayList;
import java.util.List;

public class LinkedArrayList
{
  final int capacityHint;
  Object[] head;
  int indexInTail;
  volatile int size;
  Object[] tail;
  
  public LinkedArrayList(int paramInt)
  {
    this.capacityHint = paramInt;
  }
  
  public void add(Object paramObject)
  {
    Object[] arrayOfObject;
    if (this.size == 0)
    {
      arrayOfObject = new Object[this.capacityHint + 1];
      this.head = arrayOfObject;
      this.tail = arrayOfObject;
      arrayOfObject[0] = paramObject;
      this.indexInTail = 1;
      this.size = 1;
    }
    else
    {
      int i = this.indexInTail;
      int j = this.capacityHint;
      if (i == j)
      {
        arrayOfObject = new Object[j + 1];
        arrayOfObject[0] = paramObject;
        this.tail[j] = arrayOfObject;
        this.tail = arrayOfObject;
        this.indexInTail = 1;
        this.size += 1;
      }
      else
      {
        this.tail[i] = paramObject;
        this.indexInTail = (i + 1);
        this.size += 1;
      }
    }
  }
  
  public Object[] head()
  {
    return this.head;
  }
  
  public int size()
  {
    return this.size;
  }
  
  public String toString()
  {
    int i = this.capacityHint;
    int j = this.size;
    ArrayList localArrayList = new ArrayList(j + 1);
    Object[] arrayOfObject = head();
    int k = 0;
    int m = k;
    while (k < j)
    {
      localArrayList.add(arrayOfObject[m]);
      int n = k + 1;
      int i1 = m + 1;
      k = n;
      m = i1;
      if (i1 == i)
      {
        arrayOfObject = (Object[])arrayOfObject[i];
        m = 0;
        k = n;
      }
    }
    return localArrayList.toString();
  }
}
