package dagger;

public abstract interface MembersInjector<T>
{
  public abstract void injectMembers(T paramT);
}
