package javax.inject;

public abstract interface Provider<T>
{
  public abstract T get();
}
