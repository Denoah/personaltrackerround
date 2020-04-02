package kotlin.reflect.jvm.internal;

import java.lang.ref.SoftReference;
import kotlin.jvm.functions.Function0;

public class ReflectProperties
{
  public static <T> LazyVal<T> lazy(Function0<T> paramFunction0)
  {
    if (paramFunction0 == null) {
      $$$reportNull$$$0(0);
    }
    return new LazyVal(paramFunction0);
  }
  
  public static <T> LazySoftVal<T> lazySoft(T paramT, Function0<T> paramFunction0)
  {
    if (paramFunction0 == null) {
      $$$reportNull$$$0(1);
    }
    return new LazySoftVal(paramT, paramFunction0);
  }
  
  public static <T> LazySoftVal<T> lazySoft(Function0<T> paramFunction0)
  {
    if (paramFunction0 == null) {
      $$$reportNull$$$0(2);
    }
    return lazySoft(null, paramFunction0);
  }
  
  public static class LazySoftVal<T>
    extends ReflectProperties.Val<T>
  {
    private final Function0<T> initializer;
    private SoftReference<Object> value = null;
    
    public LazySoftVal(T paramT, Function0<T> paramFunction0)
    {
      this.initializer = paramFunction0;
      if (paramT != null) {
        this.value = new SoftReference(escape(paramT));
      }
    }
    
    public T invoke()
    {
      Object localObject = this.value;
      if (localObject != null)
      {
        localObject = ((SoftReference)localObject).get();
        if (localObject != null) {
          return unescape(localObject);
        }
      }
      localObject = this.initializer.invoke();
      this.value = new SoftReference(escape(localObject));
      return localObject;
    }
  }
  
  public static class LazyVal<T>
    extends ReflectProperties.Val<T>
  {
    private final Function0<T> initializer;
    private Object value = null;
    
    public LazyVal(Function0<T> paramFunction0)
    {
      this.initializer = paramFunction0;
    }
    
    public T invoke()
    {
      Object localObject = this.value;
      if (localObject != null) {
        return unescape(localObject);
      }
      localObject = this.initializer.invoke();
      this.value = escape(localObject);
      return localObject;
    }
  }
  
  public static abstract class Val<T>
  {
    private static final Object NULL_VALUE = new Object() {};
    
    public Val() {}
    
    protected Object escape(T paramT)
    {
      Object localObject = paramT;
      if (paramT == null) {
        localObject = NULL_VALUE;
      }
      return localObject;
    }
    
    public final T getValue(Object paramObject1, Object paramObject2)
    {
      return invoke();
    }
    
    public abstract T invoke();
    
    protected T unescape(Object paramObject)
    {
      Object localObject = paramObject;
      if (paramObject == NULL_VALUE) {
        localObject = null;
      }
      return localObject;
    }
  }
}
