package androidx.core.app;

import android.app.Notification.Builder;

public abstract interface NotificationBuilderWithBuilderAccessor
{
  public abstract Notification.Builder getBuilder();
}
