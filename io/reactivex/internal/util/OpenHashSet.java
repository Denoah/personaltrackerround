package io.reactivex.internal.util;

public final class OpenHashSet<T>
{
  private static final int INT_PHI = -1640531527;
  T[] keys;
  final float loadFactor;
  int mask;
  int maxSize;
  int size;
  
  public OpenHashSet()
  {
    this(16, 0.75F);
  }
  
  public OpenHashSet(int paramInt)
  {
    this(paramInt, 0.75F);
  }
  
  public OpenHashSet(int paramInt, float paramFloat)
  {
    this.loadFactor = paramFloat;
    paramInt = Pow2.roundToPowerOfTwo(paramInt);
    this.mask = (paramInt - 1);
    this.maxSize = ((int)(paramFloat * paramInt));
    this.keys = ((Object[])new Object[paramInt]);
  }
  
  static int mix(int paramInt)
  {
    paramInt *= -1640531527;
    return paramInt ^ paramInt >>> 16;
  }
  
  public boolean add(T paramT)
  {
    Object[] arrayOfObject = this.keys;
    int i = this.mask;
    int j = mix(paramT.hashCode()) & i;
    Object localObject = arrayOfObject[j];
    int k = j;
    if (localObject != null)
    {
      if (localObject.equals(paramT)) {
        return false;
      }
      do
      {
        j = j + 1 & i;
        localObject = arrayOfObject[j];
        if (localObject == null)
        {
          k = j;
          break;
        }
      } while (!localObject.equals(paramT));
      return false;
    }
    arrayOfObject[k] = paramT;
    j = this.size + 1;
    this.size = j;
    if (j >= this.maxSize) {
      rehash();
    }
    return true;
  }
  
  public Object[] keys()
  {
    return this.keys;
  }
  
  void rehash()
  {
    Object[] arrayOfObject1 = this.keys;
    int i = arrayOfObject1.length;
    int j = i << 1;
    int k = j - 1;
    Object[] arrayOfObject2 = (Object[])new Object[j];
    for (int m = this.size; m != 0; m--)
    {
      do
      {
        i--;
      } while (arrayOfObject1[i] == null);
      int n = mix(arrayOfObject1[i].hashCode()) & k;
      int i1 = n;
      if (arrayOfObject2[n] != null)
      {
        i1 = n;
        do
        {
          n = i1 + 1 & k;
          i1 = n;
        } while (arrayOfObject2[n] != null);
        i1 = n;
      }
      arrayOfObject2[i1] = arrayOfObject1[i];
    }
    this.mask = k;
    this.maxSize = ((int)(j * this.loadFactor));
    this.keys = arrayOfObject2;
  }
  
  public boolean remove(T paramT)
  {
    Object[] arrayOfObject = this.keys;
    int i = this.mask;
    int j = mix(paramT.hashCode()) & i;
    Object localObject = arrayOfObject[j];
    if (localObject == null) {
      return false;
    }
    int k = j;
    if (localObject.equals(paramT)) {
      return removeEntry(j, arrayOfObject, i);
    }
    do
    {
      j = k + 1 & i;
      localObject = arrayOfObject[j];
      if (localObject == null) {
        return false;
      }
      k = j;
    } while (!localObject.equals(paramT));
    return removeEntry(j, arrayOfObject, i);
  }
  
  boolean removeEntry(int paramInt1, T[] paramArrayOfT, int paramInt2)
  {
    this.size -= 1;
    for (int i = paramInt1 + 1;; i++)
    {
      i &= paramInt2;
      T ? = paramArrayOfT[i];
      if (? == null)
      {
        paramArrayOfT[paramInt1] = null;
        return true;
      }
      int j = mix(?.hashCode()) & paramInt2;
      if (paramInt1 <= i ? (paramInt1 >= j) || (j > i) : (paramInt1 >= j) && (j > i))
      {
        paramArrayOfT[paramInt1] = ?;
        paramInt1 = i;
        break;
      }
    }
  }
  
  public int size()
  {
    return this.size;
  }
}
