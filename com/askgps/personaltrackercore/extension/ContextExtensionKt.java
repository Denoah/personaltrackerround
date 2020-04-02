package com.askgps.personaltrackercore.extension;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.telephony.TelephonyManager;
import androidmads.library.qrgenearator.QRGEncoder;
import androidx.core.app.ActivityCompat;
import com.askgps.personaltrackercore.LogKt;
import com.askgps.personaltrackercore.R.dimen;
import com.askgps.personaltrackercore.utils.WifiUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\000\n\002\020\005\n\000\n\002\020\016\n\000\n\002\020\b\n\000\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\003\n\002\020\002\n\002\b\005\n\002\030\002\032\n\020\006\032\0020\007*\0020\b\032\022\020\t\032\0020\n*\0020\b2\006\020\013\032\0020\003\032\n\020\f\032\0020\001*\0020\b\032\016\020\r\032\004\030\0010\003*\0020\bH\007\032\f\020\016\032\0020\003*\0020\bH\007\032\016\020\017\032\004\030\0010\020*\0020\bH\007\032\f\020\021\032\004\030\0010\003*\0020\b\032\f\020\022\032\0020\003*\0020\bH\007\032\022\020\023\032\0020\024*\0020\b2\006\020\025\032\0020\003\032\022\020\026\032\0020\024*\0020\b2\006\020\027\032\0020\003\"\016\020\000\032\0020\001X?T?\006\002\n\000\"\016\020\002\032\0020\003X?T?\006\002\n\000\"\016\020\004\032\0020\005X?T?\006\002\n\000?\006\030?\006\n\020\031\032\0020\032X??\002"}, d2={"BATTERY_DEFAULT", "", "FAIL_IMEI", "", "MAX_DELAY", "", "createImageFile", "Ljava/io/File;", "Landroid/content/Context;", "generateQr", "Landroid/graphics/Bitmap;", "text", "getBatteryLevel", "getID", "getImei", "getLastLocation", "Landroid/location/Location;", "getMac", "getSimNumber", "makeCall", "", "number", "openUrl", "url", "personaltrackercore_release", "wifiUtils", "Lcom/askgps/personaltrackercore/utils/WifiUtils;"}, k=2, mv={1, 1, 16})
public final class ContextExtensionKt
{
  public static final byte BATTERY_DEFAULT = -1;
  public static final String FAIL_IMEI = "000000000000000";
  public static final int MAX_DELAY = 1000000;
  
  public static final File createImageFile(Context paramContext)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "$this$createImageFile");
    String str = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    Intrinsics.checkExpressionValueIsNotNull(str, "SimpleDateFormat(\"yyyyMMdd_HHmmss\").format(Date())");
    File localFile = paramContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    Intrinsics.checkExpressionValueIsNotNull(localFile, "this.getExternalFilesDir…nment.DIRECTORY_PICTURES)");
    paramContext = new StringBuilder();
    paramContext.append("photo_");
    paramContext.append(str);
    paramContext.append('_');
    paramContext = File.createTempFile(paramContext.toString(), ".jpg", localFile);
    Intrinsics.checkExpressionValueIsNotNull(paramContext, "File.createTempFile(\n   …Dir /* directory */\n    )");
    return paramContext;
  }
  
  public static final Bitmap generateQr(Context paramContext, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "$this$generateQr");
    Intrinsics.checkParameterIsNotNull(paramString, "text");
    paramContext = new QRGEncoder(paramString, null, "TEXT_TYPE", (int)paramContext.getResources().getDimension(R.dimen.qr_size)).encodeAsBitmap();
    Intrinsics.checkExpressionValueIsNotNull(paramContext, "encoder.encodeAsBitmap()");
    return paramContext;
  }
  
  public static final byte getBatteryLevel(Context paramContext)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "$this$getBatteryLevel");
    paramContext = paramContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    byte b1 = -1;
    byte b2 = b1;
    if (paramContext != null)
    {
      b1 = (byte)paramContext.getIntExtra("level", -1);
      b2 = b1;
    }
    return b2;
  }
  
  public static final String getID(Context paramContext)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "$this$getID");
    if (Build.VERSION.SDK_INT >= 29) {
      return getMac(paramContext);
    }
    return getImei(paramContext);
  }
  
  public static final String getImei(Context paramContext)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "$this$getImei");
    if (ActivityCompat.checkSelfPermission(paramContext, "android.permission.READ_PHONE_STATE") == -1)
    {
      LogKt.toLog$default("Request IMEI failed", null, null, null, 7, null);
      paramContext = "000000000000000";
    }
    else
    {
      paramContext = paramContext.getSystemService("phone");
      if (paramContext == null) {
        break label78;
      }
      paramContext = (TelephonyManager)paramContext;
      if (Build.VERSION.SDK_INT >= 26) {
        paramContext = paramContext.getImei();
      } else {
        paramContext = paramContext.getDeviceId();
      }
      Intrinsics.checkExpressionValueIsNotNull(paramContext, "if (Build.VERSION.SDK_IN…       telephone.deviceId");
    }
    return paramContext;
    label78:
    throw new TypeCastException("null cannot be cast to non-null type android.telephony.TelephonyManager");
  }
  
  public static final Location getLastLocation(Context paramContext)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "$this$getLastLocation");
    paramContext = paramContext.getSystemService("location");
    if (paramContext != null)
    {
      paramContext = (LocationManager)paramContext;
      return paramContext.getLastKnownLocation(paramContext.getBestProvider(new Criteria(), false));
    }
    throw new TypeCastException("null cannot be cast to non-null type android.location.LocationManager");
  }
  
  public static final String getMac(Context paramContext)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "$this$getMac");
    paramContext = LazyKt.lazy((Function0)new Lambda(paramContext)
    {
      public final WifiUtils invoke()
      {
        return new WifiUtils(this.$this_getMac);
      }
    });
    Object localObject = $$delegatedProperties;
    int i = 0;
    localObject = localObject[0];
    paramContext = ((WifiUtils)paramContext.getValue()).getMacAddr();
    LogKt.toLog$default(paramContext, "Device_WIFI_Mac= ", null, null, 6, null);
    if (paramContext != null)
    {
      paramContext = (CharSequence)paramContext;
      localObject = (Appendable)new StringBuilder();
      int j = paramContext.length();
      while (i < j)
      {
        char c = paramContext.charAt(i);
        if ((Character.valueOf(c).equals(Character.valueOf(':')) ^ true)) {
          ((Appendable)localObject).append(c);
        }
        i++;
      }
      paramContext = ((StringBuilder)localObject).toString();
      Intrinsics.checkExpressionValueIsNotNull(paramContext, "filterTo(StringBuilder(), predicate).toString()");
    }
    else
    {
      paramContext = null;
    }
    return paramContext;
  }
  
  public static final String getSimNumber(Context paramContext)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "$this$getSimNumber");
    if (ActivityCompat.checkSelfPermission(paramContext, "android.permission.READ_PHONE_STATE") == -1)
    {
      LogKt.toLog$default("Request sim failed", null, null, null, 7, null);
      paramContext = "000000000000000";
    }
    else
    {
      paramContext = paramContext.getSystemService("phone");
      if (paramContext == null) {
        break label63;
      }
      paramContext = ((TelephonyManager)paramContext).getLine1Number();
      Intrinsics.checkExpressionValueIsNotNull(paramContext, "telephone.line1Number");
    }
    return paramContext;
    label63:
    throw new TypeCastException("null cannot be cast to non-null type android.telephony.TelephonyManager");
  }
  
  public static final void makeCall(Context paramContext, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "$this$makeCall");
    Intrinsics.checkParameterIsNotNull(paramString, "number");
    Intent localIntent = new Intent("android.intent.action.CALL");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("tel:");
    localStringBuilder.append(paramString);
    localIntent.setData(Uri.parse(localStringBuilder.toString()));
    paramContext.startActivity(localIntent);
  }
  
  public static final void openUrl(Context paramContext, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramContext, "$this$openUrl");
    Intrinsics.checkParameterIsNotNull(paramString, "url");
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.setData(Uri.parse(paramString));
    paramContext.startActivity(localIntent);
  }
}
