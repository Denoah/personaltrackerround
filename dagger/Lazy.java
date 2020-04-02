package dagger;

public abstract interface Lazy<T>
{
  public abstract T get();
}
