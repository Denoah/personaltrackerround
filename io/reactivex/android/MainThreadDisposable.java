package io.reactivex.android;

import android.os.Looper;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class MainThreadDisposable
  implements Disposable
{
  private final AtomicBoolean unsubscribed = new AtomicBoolean();
  
  public MainThreadDisposable() {}
  
  public static void verifyMainThread()
  {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Expected to be called on the main thread but was ");
    localStringBuilder.append(Thread.currentThread().getName());
    throw new IllegalStateException(localStringBuilder.toString());
  }
  
  public final void dispose()
  {
    if (this.unsubscribed.compareAndSet(false, true)) {
      if (Looper.myLooper() == Looper.getMainLooper()) {
        onDispose();
      } else {
        AndroidSchedulers.mainThread().scheduleDirect(new Runnable()
        {
          public void run()
          {
            MainThreadDisposable.this.onDispose();
          }
        });
      }
    }
  }
  
  public final boolean isDisposed()
  {
    return this.unsubscribed.get();
  }
  
  protected abstract void onDispose();
}
