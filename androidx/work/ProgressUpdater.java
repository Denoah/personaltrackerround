package androidx.work;

import android.content.Context;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;

public abstract interface ProgressUpdater
{
  public abstract ListenableFuture<Void> updateProgress(Context paramContext, UUID paramUUID, Data paramData);
}
