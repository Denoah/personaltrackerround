package io.reactivex.internal.functions;

import io.reactivex.functions.BiPredicate;

public final class ObjectHelper
{
  static final BiPredicate<Object, Object> EQUALS = new BiObjectPredicate();
  
  private ObjectHelper()
  {
    throw new IllegalStateException("No instances!");
  }
  
  public static int compare(int paramInt1, int paramInt2)
  {
    if (paramInt1 < paramInt2) {
      paramInt1 = -1;
    } else if (paramInt1 > paramInt2) {
      paramInt1 = 1;
    } else {
      paramInt1 = 0;
    }
    return paramInt1;
  }
  
  public static int compare(long paramLong1, long paramLong2)
  {
    boolean bool = paramLong1 < paramLong2;
    int i;
    if (bool) {
      i = -1;
    } else if (i > 0) {
      i = 1;
    } else {
      i = 0;
    }
    return i;
  }
  
  public static boolean equals(Object paramObject1, Object paramObject2)
  {
    boolean bool;
    if ((paramObject1 != paramObject2) && ((paramObject1 == null) || (!paramObject1.equals(paramObject2)))) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static <T> BiPredicate<T, T> equalsPredicate()
  {
    return EQUALS;
  }
  
  public static int hashCode(Object paramObject)
  {
    int i;
    if (paramObject != null) {
      i = paramObject.hashCode();
    } else {
      i = 0;
    }
    return i;
  }
  
  @Deprecated
  public static long requireNonNull(long paramLong, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Null check on a primitive: ");
    localStringBuilder.append(paramString);
    throw new InternalError(localStringBuilder.toString());
  }
  
  public static <T> T requireNonNull(T paramT, String paramString)
  {
    if (paramT != null) {
      return paramT;
    }
    throw new NullPointerException(paramString);
  }
  
  public static int verifyPositive(int paramInt, String paramString)
  {
    if (paramInt > 0) {
      return paramInt;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(" > 0 required but it was ");
    localStringBuilder.append(paramInt);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public static long verifyPositive(long paramLong, String paramString)
  {
    if (paramLong > 0L) {
      return paramLong;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(" > 0 required but it was ");
    localStringBuilder.append(paramLong);
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  static final class BiObjectPredicate
    implements BiPredicate<Object, Object>
  {
    BiObjectPredicate() {}
    
    public boolean test(Object paramObject1, Object paramObject2)
    {
      return ObjectHelper.equals(paramObject1, paramObject2);
    }
  }
}
