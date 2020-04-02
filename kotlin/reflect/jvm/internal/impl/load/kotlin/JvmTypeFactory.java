package kotlin.reflect.jvm.internal.impl.load.kotlin;

public abstract interface JvmTypeFactory<T>
{
  public abstract T boxType(T paramT);
  
  public abstract T createFromString(String paramString);
  
  public abstract T createObjectType(String paramString);
  
  public abstract T getJavaLangClassType();
  
  public abstract String toString(T paramT);
}
