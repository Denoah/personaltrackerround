package androidx.camera.core.impl.utils;

import androidx.core.util.Preconditions;
import androidx.core.util.Supplier;
import java.io.Serializable;

public abstract class Optional<T>
  implements Serializable
{
  private static final long serialVersionUID = 0L;
  
  Optional() {}
  
  public static <T> Optional<T> absent()
  {
    return Absent.withType();
  }
  
  public static <T> Optional<T> fromNullable(T paramT)
  {
    if (paramT == null) {
      paramT = absent();
    } else {
      paramT = new Present(paramT);
    }
    return paramT;
  }
  
  public static <T> Optional<T> of(T paramT)
  {
    return new Present(Preconditions.checkNotNull(paramT));
  }
  
  public abstract boolean equals(Object paramObject);
  
  public abstract T get();
  
  public abstract int hashCode();
  
  public abstract boolean isPresent();
  
  public abstract Optional<T> or(Optional<? extends T> paramOptional);
  
  public abstract T or(Supplier<? extends T> paramSupplier);
  
  public abstract T or(T paramT);
  
  public abstract T orNull();
  
  public abstract String toString();
}
