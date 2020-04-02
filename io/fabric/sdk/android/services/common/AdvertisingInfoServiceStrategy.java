package io.fabric.sdk.android.services.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class AdvertisingInfoServiceStrategy
  implements AdvertisingInfoStrategy
{
  public static final String GOOGLE_PLAY_SERVICES_INTENT = "com.google.android.gms.ads.identifier.service.START";
  public static final String GOOGLE_PLAY_SERVICES_INTENT_PACKAGE_NAME = "com.google.android.gms";
  private static final String GOOGLE_PLAY_SERVICE_PACKAGE_NAME = "com.android.vending";
  private final Context context;
  
  public AdvertisingInfoServiceStrategy(Context paramContext)
  {
    this.context = paramContext.getApplicationContext();
  }
  
  /* Error */
  public AdvertisingInfo getAdvertisingInfo()
  {
    // Byte code:
    //   0: invokestatic 52	android/os/Looper:myLooper	()Landroid/os/Looper;
    //   3: invokestatic 55	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   6: if_acmpne +17 -> 23
    //   9: invokestatic 61	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   12: ldc 63
    //   14: ldc 65
    //   16: invokeinterface 71 3 0
    //   21: aconst_null
    //   22: areturn
    //   23: aload_0
    //   24: getfield 39	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   27: invokevirtual 75	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   30: ldc 24
    //   32: iconst_0
    //   33: invokevirtual 81	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   36: pop
    //   37: new 10	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingConnection
    //   40: dup
    //   41: aconst_null
    //   42: invokespecial 84	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingConnection:<init>	(Lio/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$1;)V
    //   45: astore_1
    //   46: new 86	android/content/Intent
    //   49: dup
    //   50: ldc 18
    //   52: invokespecial 89	android/content/Intent:<init>	(Ljava/lang/String;)V
    //   55: astore_2
    //   56: aload_2
    //   57: ldc 21
    //   59: invokevirtual 93	android/content/Intent:setPackage	(Ljava/lang/String;)Landroid/content/Intent;
    //   62: pop
    //   63: aload_0
    //   64: getfield 39	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   67: aload_2
    //   68: aload_1
    //   69: iconst_1
    //   70: invokevirtual 97	android/content/Context:bindService	(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z
    //   73: istore_3
    //   74: iload_3
    //   75: ifeq +83 -> 158
    //   78: new 13	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface
    //   81: astore_2
    //   82: aload_2
    //   83: aload_1
    //   84: invokevirtual 101	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingConnection:getBinder	()Landroid/os/IBinder;
    //   87: invokespecial 104	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:<init>	(Landroid/os/IBinder;)V
    //   90: new 106	io/fabric/sdk/android/services/common/AdvertisingInfo
    //   93: astore 4
    //   95: aload 4
    //   97: aload_2
    //   98: invokevirtual 110	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:getId	()Ljava/lang/String;
    //   101: aload_2
    //   102: invokevirtual 114	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:isLimitAdTrackingEnabled	()Z
    //   105: invokespecial 117	io/fabric/sdk/android/services/common/AdvertisingInfo:<init>	(Ljava/lang/String;Z)V
    //   108: aload_0
    //   109: getfield 39	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   112: aload_1
    //   113: invokevirtual 121	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   116: aload 4
    //   118: areturn
    //   119: astore_2
    //   120: goto +28 -> 148
    //   123: astore_2
    //   124: invokestatic 61	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   127: ldc 63
    //   129: ldc 123
    //   131: aload_2
    //   132: invokeinterface 127 4 0
    //   137: aload_0
    //   138: getfield 39	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   141: aload_1
    //   142: invokevirtual 121	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   145: goto +42 -> 187
    //   148: aload_0
    //   149: getfield 39	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy:context	Landroid/content/Context;
    //   152: aload_1
    //   153: invokevirtual 121	android/content/Context:unbindService	(Landroid/content/ServiceConnection;)V
    //   156: aload_2
    //   157: athrow
    //   158: invokestatic 61	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   161: ldc 63
    //   163: ldc -127
    //   165: invokeinterface 71 3 0
    //   170: goto +17 -> 187
    //   173: astore_1
    //   174: invokestatic 61	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   177: ldc 63
    //   179: ldc -127
    //   181: aload_1
    //   182: invokeinterface 131 4 0
    //   187: aconst_null
    //   188: areturn
    //   189: astore_1
    //   190: invokestatic 61	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   193: ldc 63
    //   195: ldc -123
    //   197: aload_1
    //   198: invokeinterface 131 4 0
    //   203: aconst_null
    //   204: areturn
    //   205: astore_1
    //   206: invokestatic 61	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   209: ldc 63
    //   211: ldc -121
    //   213: invokeinterface 71 3 0
    //   218: aconst_null
    //   219: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	220	0	this	AdvertisingInfoServiceStrategy
    //   45	108	1	localAdvertisingConnection	AdvertisingConnection
    //   173	9	1	localThrowable	Throwable
    //   189	9	1	localException1	Exception
    //   205	1	1	localNameNotFoundException	android.content.pm.PackageManager.NameNotFoundException
    //   55	47	2	localObject1	Object
    //   119	1	2	localObject2	Object
    //   123	34	2	localException2	Exception
    //   73	2	3	bool	boolean
    //   93	24	4	localAdvertisingInfo	AdvertisingInfo
    // Exception table:
    //   from	to	target	type
    //   78	108	119	finally
    //   124	137	119	finally
    //   78	108	123	java/lang/Exception
    //   63	74	173	finally
    //   108	116	173	finally
    //   137	145	173	finally
    //   148	158	173	finally
    //   158	170	173	finally
    //   23	37	189	java/lang/Exception
    //   23	37	205	android/content/pm/PackageManager$NameNotFoundException
  }
  
  private static final class AdvertisingConnection
    implements ServiceConnection
  {
    private static final int QUEUE_TIMEOUT_IN_MS = 200;
    private final LinkedBlockingQueue<IBinder> queue = new LinkedBlockingQueue(1);
    private boolean retrieved = false;
    
    private AdvertisingConnection() {}
    
    public IBinder getBinder()
    {
      if (this.retrieved) {
        Fabric.getLogger().e("Fabric", "getBinder already called");
      }
      this.retrieved = true;
      try
      {
        IBinder localIBinder = (IBinder)this.queue.poll(200L, TimeUnit.MILLISECONDS);
        return localIBinder;
      }
      catch (InterruptedException localInterruptedException) {}
      return null;
    }
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      try
      {
        this.queue.put(paramIBinder);
        return;
      }
      catch (InterruptedException paramComponentName)
      {
        for (;;) {}
      }
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      this.queue.clear();
    }
  }
  
  private static final class AdvertisingInterface
    implements IInterface
  {
    public static final String ADVERTISING_ID_SERVICE_INTERFACE_TOKEN = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";
    private static final int AD_TRANSACTION_CODE_ID = 1;
    private static final int AD_TRANSACTION_CODE_LIMIT_AD_TRACKING = 2;
    private static final int FLAGS_NONE = 0;
    private final IBinder binder;
    
    public AdvertisingInterface(IBinder paramIBinder)
    {
      this.binder = paramIBinder;
    }
    
    public IBinder asBinder()
    {
      return this.binder;
    }
    
    /* Error */
    public String getId()
      throws android.os.RemoteException
    {
      // Byte code:
      //   0: invokestatic 44	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   3: astore_1
      //   4: invokestatic 44	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   7: astore_2
      //   8: aload_1
      //   9: ldc 13
      //   11: invokevirtual 48	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
      //   14: aload_0
      //   15: getfield 29	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:binder	Landroid/os/IBinder;
      //   18: iconst_1
      //   19: aload_1
      //   20: aload_2
      //   21: iconst_0
      //   22: invokeinterface 54 5 0
      //   27: pop
      //   28: aload_2
      //   29: invokevirtual 57	android/os/Parcel:readException	()V
      //   32: aload_2
      //   33: invokevirtual 60	android/os/Parcel:readString	()Ljava/lang/String;
      //   36: astore_3
      //   37: aload_2
      //   38: invokevirtual 63	android/os/Parcel:recycle	()V
      //   41: aload_1
      //   42: invokevirtual 63	android/os/Parcel:recycle	()V
      //   45: goto +30 -> 75
      //   48: astore_3
      //   49: goto +28 -> 77
      //   52: astore_3
      //   53: invokestatic 69	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
      //   56: ldc 71
      //   58: ldc 73
      //   60: invokeinterface 79 3 0
      //   65: aload_2
      //   66: invokevirtual 63	android/os/Parcel:recycle	()V
      //   69: aload_1
      //   70: invokevirtual 63	android/os/Parcel:recycle	()V
      //   73: aconst_null
      //   74: astore_3
      //   75: aload_3
      //   76: areturn
      //   77: aload_2
      //   78: invokevirtual 63	android/os/Parcel:recycle	()V
      //   81: aload_1
      //   82: invokevirtual 63	android/os/Parcel:recycle	()V
      //   85: aload_3
      //   86: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	87	0	this	AdvertisingInterface
      //   3	79	1	localParcel1	android.os.Parcel
      //   7	71	2	localParcel2	android.os.Parcel
      //   36	1	3	str1	String
      //   48	1	3	localObject	Object
      //   52	1	3	localException	Exception
      //   74	12	3	str2	String
      // Exception table:
      //   from	to	target	type
      //   8	37	48	finally
      //   53	65	48	finally
      //   8	37	52	java/lang/Exception
    }
    
    /* Error */
    public boolean isLimitAdTrackingEnabled()
      throws android.os.RemoteException
    {
      // Byte code:
      //   0: invokestatic 44	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   3: astore_1
      //   4: invokestatic 44	android/os/Parcel:obtain	()Landroid/os/Parcel;
      //   7: astore_2
      //   8: iconst_0
      //   9: istore_3
      //   10: aload_1
      //   11: ldc 13
      //   13: invokevirtual 48	android/os/Parcel:writeInterfaceToken	(Ljava/lang/String;)V
      //   16: aload_1
      //   17: iconst_1
      //   18: invokevirtual 86	android/os/Parcel:writeInt	(I)V
      //   21: aload_0
      //   22: getfield 29	io/fabric/sdk/android/services/common/AdvertisingInfoServiceStrategy$AdvertisingInterface:binder	Landroid/os/IBinder;
      //   25: iconst_2
      //   26: aload_1
      //   27: aload_2
      //   28: iconst_0
      //   29: invokeinterface 54 5 0
      //   34: pop
      //   35: aload_2
      //   36: invokevirtual 57	android/os/Parcel:readException	()V
      //   39: aload_2
      //   40: invokevirtual 90	android/os/Parcel:readInt	()I
      //   43: istore 4
      //   45: iload 4
      //   47: ifeq +27 -> 74
      //   50: iconst_1
      //   51: istore_3
      //   52: goto +22 -> 74
      //   55: astore 5
      //   57: goto +27 -> 84
      //   60: astore 5
      //   62: invokestatic 69	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
      //   65: ldc 71
      //   67: ldc 92
      //   69: invokeinterface 79 3 0
      //   74: aload_2
      //   75: invokevirtual 63	android/os/Parcel:recycle	()V
      //   78: aload_1
      //   79: invokevirtual 63	android/os/Parcel:recycle	()V
      //   82: iload_3
      //   83: ireturn
      //   84: aload_2
      //   85: invokevirtual 63	android/os/Parcel:recycle	()V
      //   88: aload_1
      //   89: invokevirtual 63	android/os/Parcel:recycle	()V
      //   92: aload 5
      //   94: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	95	0	this	AdvertisingInterface
      //   3	86	1	localParcel1	android.os.Parcel
      //   7	78	2	localParcel2	android.os.Parcel
      //   9	74	3	bool	boolean
      //   43	3	4	i	int
      //   55	1	5	localObject	Object
      //   60	33	5	localException	Exception
      // Exception table:
      //   from	to	target	type
      //   10	45	55	finally
      //   62	74	55	finally
      //   10	45	60	java/lang/Exception
    }
  }
}
