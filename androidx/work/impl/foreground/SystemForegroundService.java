package androidx.work.impl.foreground;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.LifecycleService;
import androidx.work.Logger;

public class SystemForegroundService
  extends LifecycleService
  implements SystemForegroundDispatcher.Callback
{
  private static final String TAG = Logger.tagWithPrefix("SystemFgService");
  private static SystemForegroundService sForegroundService = null;
  SystemForegroundDispatcher mDispatcher;
  private Handler mHandler;
  private boolean mIsShutdown;
  NotificationManager mNotificationManager;
  
  public SystemForegroundService() {}
  
  public static SystemForegroundService getInstance()
  {
    return sForegroundService;
  }
  
  private void initializeDispatcher()
  {
    this.mHandler = new Handler(Looper.getMainLooper());
    this.mNotificationManager = ((NotificationManager)getApplicationContext().getSystemService("notification"));
    SystemForegroundDispatcher localSystemForegroundDispatcher = new SystemForegroundDispatcher(getApplicationContext());
    this.mDispatcher = localSystemForegroundDispatcher;
    localSystemForegroundDispatcher.setCallback(this);
  }
  
  public void cancelNotification(final int paramInt)
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        SystemForegroundService.this.mNotificationManager.cancel(paramInt);
      }
    });
  }
  
  public void notify(final int paramInt, final Notification paramNotification)
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        SystemForegroundService.this.mNotificationManager.notify(paramInt, paramNotification);
      }
    });
  }
  
  public void onCreate()
  {
    super.onCreate();
    sForegroundService = this;
    initializeDispatcher();
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.mDispatcher.onDestroy();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    super.onStartCommand(paramIntent, paramInt1, paramInt2);
    if (this.mIsShutdown)
    {
      Logger.get().info(TAG, "Re-initializing SystemForegroundService after a request to shut-down.", new Throwable[0]);
      this.mDispatcher.onDestroy();
      initializeDispatcher();
      this.mIsShutdown = false;
    }
    if (paramIntent != null) {
      this.mDispatcher.onStartCommand(paramIntent);
    }
    return 3;
  }
  
  public void startForeground(final int paramInt1, final int paramInt2, final Notification paramNotification)
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        if (Build.VERSION.SDK_INT >= 29) {
          SystemForegroundService.this.startForeground(paramInt1, paramNotification, paramInt2);
        } else {
          SystemForegroundService.this.startForeground(paramInt1, paramNotification);
        }
      }
    });
  }
  
  public void stop()
  {
    this.mIsShutdown = true;
    Logger.get().debug(TAG, "All commands completed.", new Throwable[0]);
    if (Build.VERSION.SDK_INT >= 26) {
      stopForeground(true);
    }
    sForegroundService = null;
    stopSelf();
  }
  
  public void stopForegroundService()
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        SystemForegroundService.this.mDispatcher.handleStop();
      }
    });
  }
}
