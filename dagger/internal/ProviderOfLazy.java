package dagger.internal;

import dagger.Lazy;
import javax.inject.Provider;

public final class ProviderOfLazy<T>
  implements Provider<Lazy<T>>
{
  private final Provider<T> provider;
  
  private ProviderOfLazy(Provider<T> paramProvider)
  {
    this.provider = paramProvider;
  }
  
  public static <T> Provider<Lazy<T>> create(Provider<T> paramProvider)
  {
    return new ProviderOfLazy((Provider)Preconditions.checkNotNull(paramProvider));
  }
  
  public Lazy<T> get()
  {
    return DoubleCheck.lazy(this.provider);
  }
}
