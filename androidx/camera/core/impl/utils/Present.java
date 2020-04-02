package androidx.camera.core.impl.utils;

import androidx.core.util.Preconditions;
import androidx.core.util.Supplier;

final class Present<T>
  extends Optional<T>
{
  private static final long serialVersionUID = 0L;
  private final T mReference;
  
  Present(T paramT)
  {
    this.mReference = paramT;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Present))
    {
      paramObject = (Present)paramObject;
      return this.mReference.equals(paramObject.mReference);
    }
    return false;
  }
  
  public T get()
  {
    return this.mReference;
  }
  
  public int hashCode()
  {
    return this.mReference.hashCode() + 1502476572;
  }
  
  public boolean isPresent()
  {
    return true;
  }
  
  public Optional<T> or(Optional<? extends T> paramOptional)
  {
    Preconditions.checkNotNull(paramOptional);
    return this;
  }
  
  public T or(Supplier<? extends T> paramSupplier)
  {
    Preconditions.checkNotNull(paramSupplier);
    return this.mReference;
  }
  
  public T or(T paramT)
  {
    Preconditions.checkNotNull(paramT, "use Optional.orNull() instead of Optional.or(null)");
    return this.mReference;
  }
  
  public T orNull()
  {
    return this.mReference;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Optional.of(");
    localStringBuilder.append(this.mReference);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }
}
