package androidx.camera.core.impl.utils;

import androidx.core.util.Preconditions;
import androidx.core.util.Supplier;

final class Absent<T>
  extends Optional<T>
{
  static final Absent<Object> sInstance = new Absent();
  private static final long serialVersionUID = 0L;
  
  private Absent() {}
  
  private Object readResolve()
  {
    return sInstance;
  }
  
  static <T> Optional<T> withType()
  {
    return sInstance;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (paramObject == this) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public T get()
  {
    throw new IllegalStateException("Optional.get() cannot be called on an absent value");
  }
  
  public int hashCode()
  {
    return 2040732332;
  }
  
  public boolean isPresent()
  {
    return false;
  }
  
  public Optional<T> or(Optional<? extends T> paramOptional)
  {
    return (Optional)Preconditions.checkNotNull(paramOptional);
  }
  
  public T or(Supplier<? extends T> paramSupplier)
  {
    return Preconditions.checkNotNull(paramSupplier.get(), "use Optional.orNull() instead of a Supplier that returns null");
  }
  
  public T or(T paramT)
  {
    return Preconditions.checkNotNull(paramT, "use Optional.orNull() instead of Optional.or(null)");
  }
  
  public T orNull()
  {
    return null;
  }
  
  public String toString()
  {
    return "Optional.absent()";
  }
}
