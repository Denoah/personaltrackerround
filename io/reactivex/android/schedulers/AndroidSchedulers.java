package io.reactivex.android.schedulers;

import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import java.util.concurrent.Callable;

public final class AndroidSchedulers
{
  private static final Scheduler MAIN_THREAD = RxAndroidPlugins.initMainThreadScheduler(new Callable()
  {
    public Scheduler call()
      throws Exception
    {
      return AndroidSchedulers.MainHolder.DEFAULT;
    }
  });
  
  private AndroidSchedulers()
  {
    throw new AssertionError("No instances.");
  }
  
  public static Scheduler from(Looper paramLooper)
  {
    return from(paramLooper, false);
  }
  
  public static Scheduler from(Looper paramLooper, boolean paramBoolean)
  {
    if (paramLooper != null)
    {
      boolean bool;
      if (Build.VERSION.SDK_INT < 16)
      {
        bool = false;
      }
      else
      {
        bool = paramBoolean;
        if (paramBoolean)
        {
          bool = paramBoolean;
          if (Build.VERSION.SDK_INT < 22)
          {
            Message localMessage = Message.obtain();
            try
            {
              localMessage.setAsynchronous(true);
            }
            catch (NoSuchMethodError localNoSuchMethodError)
            {
              paramBoolean = false;
            }
            localMessage.recycle();
            bool = paramBoolean;
          }
        }
      }
      return new HandlerScheduler(new Handler(paramLooper), bool);
    }
    throw new NullPointerException("looper == null");
  }
  
  public static Scheduler mainThread()
  {
    return RxAndroidPlugins.onMainThreadScheduler(MAIN_THREAD);
  }
  
  private static final class MainHolder
  {
    static final Scheduler DEFAULT = new HandlerScheduler(new Handler(Looper.getMainLooper()), false);
    
    private MainHolder() {}
  }
}
