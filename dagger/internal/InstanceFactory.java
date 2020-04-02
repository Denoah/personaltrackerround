package dagger.internal;

import dagger.Lazy;

public final class InstanceFactory<T>
  implements Factory<T>, Lazy<T>
{
  private static final InstanceFactory<Object> NULL_INSTANCE_FACTORY = new InstanceFactory(null);
  private final T instance;
  
  private InstanceFactory(T paramT)
  {
    this.instance = paramT;
  }
  
  public static <T> Factory<T> create(T paramT)
  {
    return new InstanceFactory(Preconditions.checkNotNull(paramT, "instance cannot be null"));
  }
  
  public static <T> Factory<T> createNullable(T paramT)
  {
    if (paramT == null) {
      paramT = nullInstanceFactory();
    } else {
      paramT = new InstanceFactory(paramT);
    }
    return paramT;
  }
  
  private static <T> InstanceFactory<T> nullInstanceFactory()
  {
    return NULL_INSTANCE_FACTORY;
  }
  
  public T get()
  {
    return this.instance;
  }
}
