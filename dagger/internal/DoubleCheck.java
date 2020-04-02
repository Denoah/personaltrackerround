package dagger.internal;

import dagger.Lazy;
import javax.inject.Provider;

public final class DoubleCheck<T>
  implements Provider<T>, Lazy<T>
{
  private static final Object UNINITIALIZED = new Object();
  private volatile Object instance = UNINITIALIZED;
  private volatile Provider<T> provider;
  
  private DoubleCheck(Provider<T> paramProvider)
  {
    this.provider = paramProvider;
  }
  
  public static <P extends Provider<T>, T> Lazy<T> lazy(P paramP)
  {
    if ((paramP instanceof Lazy)) {
      return (Lazy)paramP;
    }
    return new DoubleCheck((Provider)Preconditions.checkNotNull(paramP));
  }
  
  public static <P extends Provider<T>, T> Provider<T> provider(P paramP)
  {
    Preconditions.checkNotNull(paramP);
    if ((paramP instanceof DoubleCheck)) {
      return paramP;
    }
    return new DoubleCheck(paramP);
  }
  
  public static Object reentrantCheck(Object paramObject1, Object paramObject2)
  {
    int i;
    if ((paramObject1 != UNINITIALIZED) && (!(paramObject1 instanceof MemoizedSentinel))) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i != 0) && (paramObject1 != paramObject2))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Scoped provider was invoked recursively returning different results: ");
      localStringBuilder.append(paramObject1);
      localStringBuilder.append(" & ");
      localStringBuilder.append(paramObject2);
      localStringBuilder.append(". This is likely due to a circular dependency.");
      throw new IllegalStateException(localStringBuilder.toString());
    }
    return paramObject2;
  }
  
  public T get()
  {
    Object localObject1 = this.instance;
    Object localObject2 = localObject1;
    if (localObject1 == UNINITIALIZED) {
      try
      {
        localObject1 = this.instance;
        localObject2 = localObject1;
        if (localObject1 == UNINITIALIZED)
        {
          localObject2 = this.provider.get();
          this.instance = reentrantCheck(this.instance, localObject2);
          this.provider = null;
        }
      }
      finally {}
    }
    return ?;
  }
}
