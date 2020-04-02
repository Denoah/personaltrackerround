package com.askgps.personaltrackercore.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build.VERSION;
import androidx.core.app.NotificationCompat.BigTextStyle;
import androidx.core.app.NotificationCompat.Builder;
import androidx.core.app.NotificationCompat.Style;
import com.askgps.personaltrackercore.R.drawable;
import kotlin.Metadata;
import kotlin.TypeCastException;
import org.koin.core.Koin;
import org.koin.core.KoinComponent;
import org.koin.core.KoinComponent.DefaultImpls;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\002\n\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J6\020\t\032\004\030\0010\n2\b\020\013\032\004\030\0010\f2\b\020\r\032\004\030\0010\f2\b\020\016\032\004\030\0010\0172\006\020\020\032\0020\0212\006\020\022\032\0020\021J$\020\023\032\0020\0242\b\020\013\032\004\030\0010\f2\b\020\r\032\004\030\0010\f2\b\020\016\032\004\030\0010\017R\024\020\005\032\0020\006X?D?\006\b\n\000\032\004\b\007\020\bR\016\020\002\032\0020\003X?\004?\006\002\n\000?\006\025"}, d2={"Lcom/askgps/personaltrackercore/utils/NotificationHandler;", "Lorg/koin/core/KoinComponent;", "ctx", "Landroid/content/Context;", "(Landroid/content/Context;)V", "NOTIFY_ID", "", "getNOTIFY_ID", "()I", "getNotification", "Landroid/app/Notification;", "title", "", "message", "intent", "Landroid/app/PendingIntent;", "isSound", "", "isAutoCancel", "notifyTestMessage", "", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class NotificationHandler
  implements KoinComponent
{
  private final int NOTIFY_ID;
  private final Context ctx;
  
  public NotificationHandler(Context paramContext)
  {
    this.ctx = paramContext;
    this.NOTIFY_ID = 1;
  }
  
  public Koin getKoin()
  {
    return KoinComponent.DefaultImpls.getKoin(this);
  }
  
  public final int getNOTIFY_ID()
  {
    return this.NOTIFY_ID;
  }
  
  public final Notification getNotification(String paramString1, String paramString2, PendingIntent paramPendingIntent, boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject = this.ctx.getSystemService("notification");
    if (localObject != null)
    {
      NotificationManager localNotificationManager = (NotificationManager)localObject;
      if (!paramBoolean1) {
        localObject = "wl_channel_id_02";
      } else {
        localObject = "wl_channel_id_01";
      }
      if (Build.VERSION.SDK_INT >= 26)
      {
        NotificationChannel localNotificationChannel = new NotificationChannel((String)localObject, (CharSequence)"WL Server Notification", 4);
        if (!paramBoolean1) {
          localNotificationChannel = new NotificationChannel((String)localObject, (CharSequence)"WL Server Notification", 2);
        }
        localNotificationChannel.setDescription("Patient channel");
        localNotificationChannel.enableLights(true);
        localNotificationChannel.setLockscreenVisibility(1);
        localNotificationChannel.setLightColor(-65536);
        localNotificationManager.createNotificationChannel(localNotificationChannel);
      }
      paramString1 = new NotificationCompat.Builder(this.ctx, (String)localObject).setSmallIcon(R.drawable.ic_red_call).setLargeIcon(BitmapFactory.decodeResource(this.ctx.getResources(), R.drawable.ic_red_call)).setContentTitle((CharSequence)paramString1);
      paramString2 = (CharSequence)paramString2;
      paramString1 = paramString1.setContentText(paramString2).setAutoCancel(paramBoolean2).setOngoing(paramBoolean2 ^ true).setVisibility(1).setStyle((NotificationCompat.Style)new NotificationCompat.BigTextStyle().bigText(paramString2));
      if (paramPendingIntent != null) {
        paramString1.setContentIntent(paramPendingIntent);
      }
      if (paramBoolean1) {
        paramString1.setSound(RingtoneManager.getDefaultUri(2));
      }
      return paramString1.build();
    }
    throw new TypeCastException("null cannot be cast to non-null type android.app.NotificationManager");
  }
  
  public final void notifyTestMessage(String paramString1, String paramString2, PendingIntent paramPendingIntent)
  {
    paramString2 = getNotification(paramString1, paramString2, paramPendingIntent, false, true);
    paramString1 = this.ctx.getSystemService("notification");
    if (paramString1 != null)
    {
      ((NotificationManager)paramString1).notify(this.NOTIFY_ID, paramString2);
      return;
    }
    throw new TypeCastException("null cannot be cast to non-null type android.app.NotificationManager");
  }
}
