package io.reactivex.schedulers;

import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.TimeUnit;

public final class Timed<T>
{
  final long time;
  final TimeUnit unit;
  final T value;
  
  public Timed(T paramT, long paramLong, TimeUnit paramTimeUnit)
  {
    this.value = paramT;
    this.time = paramLong;
    this.unit = ((TimeUnit)ObjectHelper.requireNonNull(paramTimeUnit, "unit is null"));
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Timed;
    boolean bool2 = false;
    boolean bool3 = bool2;
    if (bool1)
    {
      paramObject = (Timed)paramObject;
      bool3 = bool2;
      if (ObjectHelper.equals(this.value, paramObject.value))
      {
        bool3 = bool2;
        if (this.time == paramObject.time)
        {
          bool3 = bool2;
          if (ObjectHelper.equals(this.unit, paramObject.unit)) {
            bool3 = true;
          }
        }
      }
    }
    return bool3;
  }
  
  public int hashCode()
  {
    Object localObject = this.value;
    int i;
    if (localObject != null) {
      i = localObject.hashCode();
    } else {
      i = 0;
    }
    long l = this.time;
    return (i * 31 + (int)(l ^ l >>> 31)) * 31 + this.unit.hashCode();
  }
  
  public long time()
  {
    return this.time;
  }
  
  public long time(TimeUnit paramTimeUnit)
  {
    return paramTimeUnit.convert(this.time, this.unit);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Timed[time=");
    localStringBuilder.append(this.time);
    localStringBuilder.append(", unit=");
    localStringBuilder.append(this.unit);
    localStringBuilder.append(", value=");
    localStringBuilder.append(this.value);
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  public TimeUnit unit()
  {
    return this.unit;
  }
  
  public T value()
  {
    return this.value;
  }
}
