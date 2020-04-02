package io.reactivex;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.NotificationLite;

public final class Notification<T>
{
  static final Notification<Object> COMPLETE = new Notification(null);
  final Object value;
  
  private Notification(Object paramObject)
  {
    this.value = paramObject;
  }
  
  public static <T> Notification<T> createOnComplete()
  {
    return COMPLETE;
  }
  
  public static <T> Notification<T> createOnError(Throwable paramThrowable)
  {
    ObjectHelper.requireNonNull(paramThrowable, "error is null");
    return new Notification(NotificationLite.error(paramThrowable));
  }
  
  public static <T> Notification<T> createOnNext(T paramT)
  {
    ObjectHelper.requireNonNull(paramT, "value is null");
    return new Notification(paramT);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Notification))
    {
      paramObject = (Notification)paramObject;
      return ObjectHelper.equals(this.value, paramObject.value);
    }
    return false;
  }
  
  public Throwable getError()
  {
    Object localObject = this.value;
    if (NotificationLite.isError(localObject)) {
      return NotificationLite.getError(localObject);
    }
    return null;
  }
  
  public T getValue()
  {
    Object localObject = this.value;
    if ((localObject != null) && (!NotificationLite.isError(localObject))) {
      return this.value;
    }
    return null;
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
    return i;
  }
  
  public boolean isOnComplete()
  {
    boolean bool;
    if (this.value == null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean isOnError()
  {
    return NotificationLite.isError(this.value);
  }
  
  public boolean isOnNext()
  {
    Object localObject = this.value;
    boolean bool;
    if ((localObject != null) && (!NotificationLite.isError(localObject))) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public String toString()
  {
    Object localObject = this.value;
    if (localObject == null) {
      return "OnCompleteNotification";
    }
    if (NotificationLite.isError(localObject))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("OnErrorNotification[");
      localStringBuilder.append(NotificationLite.getError(localObject));
      localStringBuilder.append("]");
      return localStringBuilder.toString();
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("OnNextNotification[");
    ((StringBuilder)localObject).append(this.value);
    ((StringBuilder)localObject).append("]");
    return ((StringBuilder)localObject).toString();
  }
}
