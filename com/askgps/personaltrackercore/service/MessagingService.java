package com.askgps.personaltrackercore.service;

import android.content.Intent;
import com.askgps.personaltrackercore.LogKt;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.Koin;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.qualifier.QualifierKt;
import org.koin.core.registry.ScopeRegistry;
import org.koin.core.scope.Scope;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\016\n\002\b\005\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020$\n\002\b\002\030\000 \0252\0020\001:\001\025B\005?\006\002\020\002J\b\020\003\032\0020\004H\026J\020\020\005\032\0020\0042\006\020\006\032\0020\007H\026J\020\020\b\032\0020\0042\006\020\t\032\0020\nH\026J\020\020\013\032\0020\0042\006\020\f\032\0020\nH\026J\034\020\r\032\0020\0042\006\020\016\032\0020\n2\n\020\017\032\0060\020j\002`\021H\026J\034\020\022\032\0020\0042\022\020\023\032\016\022\004\022\0020\n\022\004\022\0020\n0\024H\002?\006\026"}, d2={"Lcom/askgps/personaltrackercore/service/MessagingService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "onDeletedMessages", "", "onMessageReceived", "remoteMessage", "Lcom/google/firebase/messaging/RemoteMessage;", "onMessageSent", "msg", "", "onNewToken", "newToken", "onSendError", "error", "ex", "Ljava/lang/Exception;", "Lkotlin/Exception;", "parseData", "data", "", "Companion", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
public final class MessagingService
  extends FirebaseMessagingService
{
  public static final Companion Companion = new Companion(null);
  public static final String LOCATION_INTERVAL_KEY = "location_interval";
  public static final String OPEN_CAMERA_KEY = "open_camera";
  public static final String REMOVAL_FROM_HAND_INTERVAL_KEY = "removal_from_hand";
  public static final String SEND_TELEMETRY_INTERVAL_KEY = "send_telemetry_interval";
  public static final String SERVER_ADDRESS_KEY = "server_address";
  
  public MessagingService() {}
  
  private final void parseData(Map<String, String> paramMap)
  {
    Object localObject1 = (Qualifier)QualifierKt.named("MainActivityClass");
    Object localObject2 = (Function0)null;
    localObject1 = (Class)ComponentCallbackExtKt.getKoin(this).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(Class.class), (Qualifier)localObject1, (Function0)localObject2);
    localObject1 = new Intent(getApplicationContext(), (Class)localObject1);
    if ((String)paramMap.get("open_camera") != null) {
      ((Intent)localObject1).putExtra("open_camera", true);
    }
    localObject2 = (String)paramMap.get("server_address");
    if (localObject2 != null) {
      ((Intent)localObject1).putExtra("server_address", (String)localObject2);
    }
    localObject2 = (String)paramMap.get("send_telemetry_interval");
    if (localObject2 != null) {
      try
      {
        ((Intent)localObject1).putExtra("send_telemetry_interval", Long.parseLong((String)localObject2));
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        LogKt.toFile$default(localNumberFormatException1, "Send telemetry interval - string is not valid", null, null, 6, null);
      }
    }
    String str = (String)paramMap.get("location_interval");
    if (str != null) {
      try
      {
        ((Intent)localObject1).putExtra("location_interval", Long.parseLong(str));
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        LogKt.toFile$default(localNumberFormatException2, "Location interval - string is not valid", null, null, 6, null);
      }
    }
    paramMap = (String)paramMap.get("removal_from_hand");
    if (paramMap != null) {
      try
      {
        ((Intent)localObject1).putExtra("removal_from_hand", Long.parseLong(paramMap));
      }
      catch (NumberFormatException paramMap)
      {
        LogKt.toFile$default(paramMap, "Removal from hand interval - string is not valid", null, null, 6, null);
      }
    }
    ((Intent)localObject1).setFlags(872415232);
    startActivity((Intent)localObject1);
  }
  
  public void onDeletedMessages()
  {
    super.onDeletedMessages();
  }
  
  public void onMessageReceived(RemoteMessage paramRemoteMessage)
  {
    Intrinsics.checkParameterIsNotNull(paramRemoteMessage, "remoteMessage");
    super.onMessageReceived(paramRemoteMessage);
    LogKt.toFile$default(paramRemoteMessage.getData(), "Firebase message data", null, null, 6, null);
    paramRemoteMessage = paramRemoteMessage.getData();
    Intrinsics.checkExpressionValueIsNotNull(paramRemoteMessage, "remoteMessage.data");
    parseData(paramRemoteMessage);
  }
  
  public void onMessageSent(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "msg");
    super.onMessageSent(paramString);
    LogKt.toLog$default(paramString, "msg", null, null, 6, null);
  }
  
  public void onNewToken(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "newToken");
    super.onNewToken(paramString);
    LogKt.toLog$default(paramString, "newToken", null, null, 6, null);
  }
  
  public void onSendError(String paramString, Exception paramException)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "error");
    Intrinsics.checkParameterIsNotNull(paramException, "ex");
    super.onSendError(paramString, paramException);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\024\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\005\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\004X?T?\006\002\n\000R\016\020\006\032\0020\004X?T?\006\002\n\000R\016\020\007\032\0020\004X?T?\006\002\n\000R\016\020\b\032\0020\004X?T?\006\002\n\000?\006\t"}, d2={"Lcom/askgps/personaltrackercore/service/MessagingService$Companion;", "", "()V", "LOCATION_INTERVAL_KEY", "", "OPEN_CAMERA_KEY", "REMOVAL_FROM_HAND_INTERVAL_KEY", "SEND_TELEMETRY_INTERVAL_KEY", "SERVER_ADDRESS_KEY", "personaltrackercore_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
  }
}
