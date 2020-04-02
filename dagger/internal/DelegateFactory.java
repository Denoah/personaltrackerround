package dagger.internal;

import javax.inject.Provider;

public final class DelegateFactory<T>
  implements Factory<T>
{
  private Provider<T> delegate;
  
  public DelegateFactory() {}
  
  public static <T> void setDelegate(Provider<T> paramProvider1, Provider<T> paramProvider2)
  {
    Preconditions.checkNotNull(paramProvider2);
    paramProvider1 = (DelegateFactory)paramProvider1;
    if (paramProvider1.delegate == null)
    {
      paramProvider1.delegate = paramProvider2;
      return;
    }
    throw new IllegalStateException();
  }
  
  public T get()
  {
    Provider localProvider = this.delegate;
    if (localProvider != null) {
      return localProvider.get();
    }
    throw new IllegalStateException();
  }
  
  Provider<T> getDelegate()
  {
    return (Provider)Preconditions.checkNotNull(this.delegate);
  }
  
  @Deprecated
  public void setDelegatedProvider(Provider<T> paramProvider)
  {
    setDelegate(this, paramProvider);
  }
}
