package androidx.work;

import android.content.Context;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;

public abstract interface ForegroundUpdater
{
  public abstract ListenableFuture<Void> setForegroundAsync(Context paramContext, UUID paramUUID, ForegroundInfo paramForegroundInfo);
}
