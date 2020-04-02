package dagger.internal;

import javax.inject.Provider;

public final class SingleCheck<T>
  implements Provider<T>
{
  private static final Object UNINITIALIZED = new Object();
  private volatile Object instance = UNINITIALIZED;
  private volatile Provider<T> provider;
  
  private SingleCheck(Provider<T> paramProvider)
  {
    this.provider = paramProvider;
  }
  
  public static <P extends Provider<T>, T> Provider<T> provider(P paramP)
  {
    if ((!(paramP instanceof SingleCheck)) && (!(paramP instanceof DoubleCheck))) {
      return new SingleCheck((Provider)Preconditions.checkNotNull(paramP));
    }
    return paramP;
  }
  
  public T get()
  {
    Object localObject1 = this.instance;
    Object localObject2 = localObject1;
    if (localObject1 == UNINITIALIZED)
    {
      localObject2 = this.provider;
      if (localObject2 == null)
      {
        localObject2 = this.instance;
      }
      else
      {
        localObject2 = ((Provider)localObject2).get();
        this.instance = localObject2;
        this.provider = null;
      }
    }
    return localObject2;
  }
}
