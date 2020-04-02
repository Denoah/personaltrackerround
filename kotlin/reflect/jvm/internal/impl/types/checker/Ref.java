package kotlin.reflect.jvm.internal.impl.types.checker;

public final class Ref<T>
{
  private T value;
  
  public Ref(T paramT)
  {
    this.value = paramT;
  }
  
  public final T getValue()
  {
    return this.value;
  }
}
