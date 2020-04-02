package androidx.room;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

class MultiInstanceInvalidationClient
{
  final Context mAppContext;
  final IMultiInstanceInvalidationCallback mCallback = new IMultiInstanceInvalidationCallback.Stub()
  {
    public void onInvalidation(final String[] paramAnonymousArrayOfString)
    {
      MultiInstanceInvalidationClient.this.mExecutor.execute(new Runnable()
      {
        public void run()
        {
          MultiInstanceInvalidationClient.this.mInvalidationTracker.notifyObserversByTableNames(paramAnonymousArrayOfString);
        }
      });
    }
  };
  int mClientId;
  final Executor mExecutor;
  final InvalidationTracker mInvalidationTracker;
  final String mName;
  final InvalidationTracker.Observer mObserver;
  final Runnable mRemoveObserverRunnable = new Runnable()
  {
    public void run()
    {
      MultiInstanceInvalidationClient.this.mInvalidationTracker.removeObserver(MultiInstanceInvalidationClient.this.mObserver);
    }
  };
  IMultiInstanceInvalidationService mService;
  final ServiceConnection mServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      MultiInstanceInvalidationClient.this.mService = IMultiInstanceInvalidationService.Stub.asInterface(paramAnonymousIBinder);
      MultiInstanceInvalidationClient.this.mExecutor.execute(MultiInstanceInvalidationClient.this.mSetUpRunnable);
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      MultiInstanceInvalidationClient.this.mExecutor.execute(MultiInstanceInvalidationClient.this.mRemoveObserverRunnable);
      MultiInstanceInvalidationClient.this.mService = null;
    }
  };
  final Runnable mSetUpRunnable = new Runnable()
  {
    public void run()
    {
      try
      {
        IMultiInstanceInvalidationService localIMultiInstanceInvalidationService = MultiInstanceInvalidationClient.this.mService;
        if (localIMultiInstanceInvalidationService != null)
        {
          MultiInstanceInvalidationClient.this.mClientId = localIMultiInstanceInvalidationService.registerCallback(MultiInstanceInvalidationClient.this.mCallback, MultiInstanceInvalidationClient.this.mName);
          MultiInstanceInvalidationClient.this.mInvalidationTracker.addObserver(MultiInstanceInvalidationClient.this.mObserver);
        }
      }
      catch (RemoteException localRemoteException)
      {
        Log.w("ROOM", "Cannot register multi-instance invalidation callback", localRemoteException);
      }
    }
  };
  final AtomicBoolean mStopped = new AtomicBoolean(false);
  private final Runnable mTearDownRunnable = new Runnable()
  {
    public void run()
    {
      MultiInstanceInvalidationClient.this.mInvalidationTracker.removeObserver(MultiInstanceInvalidationClient.this.mObserver);
      try
      {
        IMultiInstanceInvalidationService localIMultiInstanceInvalidationService = MultiInstanceInvalidationClient.this.mService;
        if (localIMultiInstanceInvalidationService != null) {
          localIMultiInstanceInvalidationService.unregisterCallback(MultiInstanceInvalidationClient.this.mCallback, MultiInstanceInvalidationClient.this.mClientId);
        }
      }
      catch (RemoteException localRemoteException)
      {
        Log.w("ROOM", "Cannot unregister multi-instance invalidation callback", localRemoteException);
      }
      MultiInstanceInvalidationClient.this.mAppContext.unbindService(MultiInstanceInvalidationClient.this.mServiceConnection);
    }
  };
  
  MultiInstanceInvalidationClient(Context paramContext, String paramString, InvalidationTracker paramInvalidationTracker, Executor paramExecutor)
  {
    this.mAppContext = paramContext.getApplicationContext();
    this.mName = paramString;
    this.mInvalidationTracker = paramInvalidationTracker;
    this.mExecutor = paramExecutor;
    this.mObserver = new InvalidationTracker.Observer(paramInvalidationTracker.mTableNames)
    {
      boolean isRemote()
      {
        return true;
      }
      
      public void onInvalidated(Set<String> paramAnonymousSet)
      {
        if (MultiInstanceInvalidationClient.this.mStopped.get()) {
          return;
        }
        try
        {
          IMultiInstanceInvalidationService localIMultiInstanceInvalidationService = MultiInstanceInvalidationClient.this.mService;
          if (localIMultiInstanceInvalidationService != null) {
            localIMultiInstanceInvalidationService.broadcastInvalidation(MultiInstanceInvalidationClient.this.mClientId, (String[])paramAnonymousSet.toArray(new String[0]));
          }
        }
        catch (RemoteException paramAnonymousSet)
        {
          Log.w("ROOM", "Cannot broadcast invalidation", paramAnonymousSet);
        }
      }
    };
    paramContext = new Intent(this.mAppContext, MultiInstanceInvalidationService.class);
    this.mAppContext.bindService(paramContext, this.mServiceConnection, 1);
  }
  
  void stop()
  {
    if (this.mStopped.compareAndSet(false, true)) {
      this.mExecutor.execute(this.mTearDownRunnable);
    }
  }
}
