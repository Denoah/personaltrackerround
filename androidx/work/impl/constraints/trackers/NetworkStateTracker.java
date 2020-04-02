package androidx.work.impl.constraints.trackers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import androidx.core.net.ConnectivityManagerCompat;
import androidx.work.Logger;
import androidx.work.impl.constraints.NetworkState;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;

public class NetworkStateTracker
  extends ConstraintTracker<NetworkState>
{
  static final String TAG = Logger.tagWithPrefix("NetworkStateTracker");
  private NetworkStateBroadcastReceiver mBroadcastReceiver;
  private final ConnectivityManager mConnectivityManager = (ConnectivityManager)this.mAppContext.getSystemService("connectivity");
  private NetworkStateCallback mNetworkCallback;
  
  public NetworkStateTracker(Context paramContext, TaskExecutor paramTaskExecutor)
  {
    super(paramContext, paramTaskExecutor);
    if (isNetworkCallbackSupported()) {
      this.mNetworkCallback = new NetworkStateCallback();
    } else {
      this.mBroadcastReceiver = new NetworkStateBroadcastReceiver();
    }
  }
  
  private boolean isActiveNetworkValidated()
  {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    if (i < 23) {
      return false;
    }
    Object localObject = this.mConnectivityManager.getActiveNetwork();
    localObject = this.mConnectivityManager.getNetworkCapabilities((Network)localObject);
    boolean bool2 = bool1;
    if (localObject != null)
    {
      bool2 = bool1;
      if (((NetworkCapabilities)localObject).hasCapability(16)) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  private static boolean isNetworkCallbackSupported()
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 24) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  NetworkState getActiveNetworkState()
  {
    NetworkInfo localNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
    boolean bool1 = true;
    boolean bool2;
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected())) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    boolean bool3 = isActiveNetworkValidated();
    boolean bool4 = ConnectivityManagerCompat.isActiveNetworkMetered(this.mConnectivityManager);
    if ((localNetworkInfo == null) || (localNetworkInfo.isRoaming())) {
      bool1 = false;
    }
    return new NetworkState(bool2, bool3, bool4, bool1);
  }
  
  public NetworkState getInitialState()
  {
    return getActiveNetworkState();
  }
  
  public void startTracking()
  {
    if (isNetworkCallbackSupported())
    {
      try
      {
        Logger.get().debug(TAG, "Registering network callback", new Throwable[0]);
        this.mConnectivityManager.registerDefaultNetworkCallback(this.mNetworkCallback);
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        Logger.get().error(TAG, "Received exception while unregistering network callback", new Throwable[] { localIllegalArgumentException });
      }
    }
    else
    {
      Logger.get().debug(TAG, "Registering broadcast receiver", new Throwable[0]);
      this.mAppContext.registerReceiver(this.mBroadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
  }
  
  public void stopTracking()
  {
    if (isNetworkCallbackSupported())
    {
      try
      {
        Logger.get().debug(TAG, "Unregistering network callback", new Throwable[0]);
        this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        Logger.get().error(TAG, "Received exception while unregistering network callback", new Throwable[] { localIllegalArgumentException });
      }
    }
    else
    {
      Logger.get().debug(TAG, "Unregistering broadcast receiver", new Throwable[0]);
      this.mAppContext.unregisterReceiver(this.mBroadcastReceiver);
    }
  }
  
  private class NetworkStateBroadcastReceiver
    extends BroadcastReceiver
  {
    NetworkStateBroadcastReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ((paramIntent != null) && (paramIntent.getAction() != null) && (paramIntent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")))
      {
        Logger.get().debug(NetworkStateTracker.TAG, "Network broadcast received", new Throwable[0]);
        paramContext = NetworkStateTracker.this;
        paramContext.setState(paramContext.getActiveNetworkState());
      }
    }
  }
  
  private class NetworkStateCallback
    extends ConnectivityManager.NetworkCallback
  {
    NetworkStateCallback() {}
    
    public void onCapabilitiesChanged(Network paramNetwork, NetworkCapabilities paramNetworkCapabilities)
    {
      Logger.get().debug(NetworkStateTracker.TAG, String.format("Network capabilities changed: %s", new Object[] { paramNetworkCapabilities }), new Throwable[0]);
      paramNetwork = NetworkStateTracker.this;
      paramNetwork.setState(paramNetwork.getActiveNetworkState());
    }
    
    public void onLost(Network paramNetwork)
    {
      Logger.get().debug(NetworkStateTracker.TAG, "Network connection lost", new Throwable[0]);
      paramNetwork = NetworkStateTracker.this;
      paramNetwork.setState(paramNetwork.getActiveNetworkState());
    }
  }
}
