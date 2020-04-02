package io.fabric.sdk.android.services.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import javax.crypto.Cipher;

public class CommonUtils
{
  static final int BYTES_IN_A_GIGABYTE = 1073741824;
  static final int BYTES_IN_A_KILOBYTE = 1024;
  static final int BYTES_IN_A_MEGABYTE = 1048576;
  private static final String CLS_SHARED_PREFERENCES_NAME = "com.crashlytics.prefs";
  static final boolean CLS_TRACE_DEFAULT = false;
  static final String CLS_TRACE_PREFERENCE_NAME = "com.crashlytics.Trace";
  static final String CRASHLYTICS_BUILD_ID = "com.crashlytics.android.build_id";
  public static final int DEVICE_STATE_BETAOS = 8;
  public static final int DEVICE_STATE_COMPROMISEDLIBRARIES = 32;
  public static final int DEVICE_STATE_DEBUGGERATTACHED = 4;
  public static final int DEVICE_STATE_ISSIMULATOR = 1;
  public static final int DEVICE_STATE_JAILBROKEN = 2;
  public static final int DEVICE_STATE_VENDORINTERNAL = 16;
  static final String FABRIC_BUILD_ID = "io.fabric.android.build_id";
  public static final Comparator<File> FILE_MODIFIED_COMPARATOR = new Comparator()
  {
    public int compare(File paramAnonymousFile1, File paramAnonymousFile2)
    {
      return (int)(paramAnonymousFile1.lastModified() - paramAnonymousFile2.lastModified());
    }
  };
  public static final String GOOGLE_SDK = "google_sdk";
  private static final char[] HEX_VALUES = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  private static final String LOG_PRIORITY_NAME_ASSERT = "A";
  private static final String LOG_PRIORITY_NAME_DEBUG = "D";
  private static final String LOG_PRIORITY_NAME_ERROR = "E";
  private static final String LOG_PRIORITY_NAME_INFO = "I";
  private static final String LOG_PRIORITY_NAME_UNKNOWN = "?";
  private static final String LOG_PRIORITY_NAME_VERBOSE = "V";
  private static final String LOG_PRIORITY_NAME_WARN = "W";
  public static final String SDK = "sdk";
  public static final String SHA1_INSTANCE = "SHA-1";
  public static final String SHA256_INSTANCE = "SHA-256";
  private static final long UNCALCULATED_TOTAL_RAM = -1L;
  static final String UNITY_EDITOR_VERSION = "com.google.firebase.crashlytics.unity_version";
  private static Boolean clsTrace;
  private static long totalRamInBytes = -1L;
  
  public CommonUtils() {}
  
  public static long calculateFreeRamInBytes(Context paramContext)
  {
    ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
    ((ActivityManager)paramContext.getSystemService("activity")).getMemoryInfo(localMemoryInfo);
    return localMemoryInfo.availMem;
  }
  
  public static long calculateUsedDiskSpaceInBytes(String paramString)
  {
    paramString = new StatFs(paramString);
    long l = paramString.getBlockSize();
    return paramString.getBlockCount() * l - l * paramString.getAvailableBlocks();
  }
  
  public static boolean canTryConnection(Context paramContext)
  {
    boolean bool1 = checkPermission(paramContext, "android.permission.ACCESS_NETWORK_STATE");
    boolean bool2 = true;
    boolean bool3 = bool2;
    if (bool1)
    {
      paramContext = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if ((paramContext != null) && (paramContext.isConnectedOrConnecting())) {
        bool3 = bool2;
      } else {
        bool3 = false;
      }
    }
    return bool3;
  }
  
  public static boolean checkPermission(Context paramContext, String paramString)
  {
    boolean bool;
    if (paramContext.checkCallingOrSelfPermission(paramString) == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static void closeOrLog(Closeable paramCloseable, String paramString)
  {
    if (paramCloseable != null) {
      try
      {
        paramCloseable.close();
      }
      catch (IOException paramCloseable)
      {
        Fabric.getLogger().e("Fabric", paramString, paramCloseable);
      }
    }
  }
  
  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      try
      {
        paramCloseable.close();
      }
      catch (RuntimeException paramCloseable)
      {
        throw paramCloseable;
      }
      return;
    }
    catch (Exception paramCloseable)
    {
      for (;;) {}
    }
  }
  
  static long convertMemInfoToBytes(String paramString1, String paramString2, int paramInt)
  {
    return Long.parseLong(paramString1.split(paramString2)[0].trim()) * paramInt;
  }
  
  public static void copyStream(InputStream paramInputStream, OutputStream paramOutputStream, byte[] paramArrayOfByte)
    throws IOException
  {
    for (;;)
    {
      int i = paramInputStream.read(paramArrayOfByte);
      if (i == -1) {
        break;
      }
      paramOutputStream.write(paramArrayOfByte, 0, i);
    }
  }
  
  @Deprecated
  public static Cipher createCipher(int paramInt, String paramString)
    throws InvalidKeyException
  {
    throw new InvalidKeyException("This method is deprecated");
  }
  
  public static String createInstanceIdFrom(String... paramVarArgs)
  {
    Object localObject1 = null;
    Object localObject2 = localObject1;
    if (paramVarArgs != null) {
      if (paramVarArgs.length == 0)
      {
        localObject2 = localObject1;
      }
      else
      {
        localObject2 = new ArrayList();
        int i = paramVarArgs.length;
        for (int j = 0; j < i; j++)
        {
          String str = paramVarArgs[j];
          if (str != null) {
            ((List)localObject2).add(str.replace("-", "").toLowerCase(Locale.US));
          }
        }
        Collections.sort((List)localObject2);
        paramVarArgs = new StringBuilder();
        localObject2 = ((List)localObject2).iterator();
        while (((Iterator)localObject2).hasNext()) {
          paramVarArgs.append((String)((Iterator)localObject2).next());
        }
        paramVarArgs = paramVarArgs.toString();
        localObject2 = localObject1;
        if (paramVarArgs.length() > 0) {
          localObject2 = sha1(paramVarArgs);
        }
      }
    }
    return localObject2;
  }
  
  public static byte[] dehexify(String paramString)
  {
    int i = paramString.length();
    byte[] arrayOfByte = new byte[i / 2];
    for (int j = 0; j < i; j += 2) {
      arrayOfByte[(j / 2)] = ((byte)(byte)((Character.digit(paramString.charAt(j), 16) << 4) + Character.digit(paramString.charAt(j + 1), 16)));
    }
    return arrayOfByte;
  }
  
  /* Error */
  public static String extractFieldFromSystemFile(File paramFile, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 339	java/io/File:exists	()Z
    //   4: istore_2
    //   5: aconst_null
    //   6: astore_3
    //   7: aconst_null
    //   8: astore 4
    //   10: aconst_null
    //   11: astore 5
    //   13: iload_2
    //   14: ifeq +217 -> 231
    //   17: new 341	java/io/BufferedReader
    //   20: astore 6
    //   22: new 343	java/io/FileReader
    //   25: astore 4
    //   27: aload 4
    //   29: aload_0
    //   30: invokespecial 346	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   33: aload 6
    //   35: aload 4
    //   37: sipush 1024
    //   40: invokespecial 349	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   43: aload 6
    //   45: astore 4
    //   47: aload 6
    //   49: invokevirtual 352	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   52: astore 7
    //   54: aload 6
    //   56: astore_3
    //   57: aload 5
    //   59: astore 4
    //   61: aload 7
    //   63: ifnull +52 -> 115
    //   66: aload 6
    //   68: astore 4
    //   70: ldc_w 354
    //   73: invokestatic 360	java/util/regex/Pattern:compile	(Ljava/lang/String;)Ljava/util/regex/Pattern;
    //   76: aload 7
    //   78: iconst_2
    //   79: invokevirtual 363	java/util/regex/Pattern:split	(Ljava/lang/CharSequence;I)[Ljava/lang/String;
    //   82: astore_3
    //   83: aload 6
    //   85: astore 4
    //   87: aload_3
    //   88: arraylength
    //   89: iconst_1
    //   90: if_icmple -47 -> 43
    //   93: aload 6
    //   95: astore 4
    //   97: aload_3
    //   98: iconst_0
    //   99: aaload
    //   100: aload_1
    //   101: invokevirtual 366	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   104: ifeq -61 -> 43
    //   107: aload_3
    //   108: iconst_1
    //   109: aaload
    //   110: astore 4
    //   112: aload 6
    //   114: astore_3
    //   115: aload_3
    //   116: ldc_w 368
    //   119: invokestatic 370	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   122: goto +109 -> 231
    //   125: astore 4
    //   127: aload 6
    //   129: astore_1
    //   130: aload 4
    //   132: astore 6
    //   134: goto +13 -> 147
    //   137: astore_1
    //   138: aload_3
    //   139: astore_0
    //   140: goto +82 -> 222
    //   143: astore 6
    //   145: aconst_null
    //   146: astore_1
    //   147: aload_1
    //   148: astore 4
    //   150: invokestatic 200	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   153: astore 7
    //   155: aload_1
    //   156: astore 4
    //   158: new 292	java/lang/StringBuilder
    //   161: astore_3
    //   162: aload_1
    //   163: astore 4
    //   165: aload_3
    //   166: invokespecial 293	java/lang/StringBuilder:<init>	()V
    //   169: aload_1
    //   170: astore 4
    //   172: aload_3
    //   173: ldc_w 372
    //   176: invokevirtual 310	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   179: pop
    //   180: aload_1
    //   181: astore 4
    //   183: aload_3
    //   184: aload_0
    //   185: invokevirtual 375	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   188: pop
    //   189: aload_1
    //   190: astore 4
    //   192: aload 7
    //   194: ldc -54
    //   196: aload_3
    //   197: invokevirtual 313	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   200: aload 6
    //   202: invokeinterface 208 4 0
    //   207: aload_1
    //   208: astore_3
    //   209: aload 5
    //   211: astore 4
    //   213: goto -98 -> 115
    //   216: astore_0
    //   217: aload_0
    //   218: astore_1
    //   219: aload 4
    //   221: astore_0
    //   222: aload_0
    //   223: ldc_w 368
    //   226: invokestatic 370	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   229: aload_1
    //   230: athrow
    //   231: aload 4
    //   233: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	234	0	paramFile	File
    //   0	234	1	paramString	String
    //   4	10	2	bool	boolean
    //   6	203	3	localObject1	Object
    //   8	103	4	localObject2	Object
    //   125	6	4	localException1	Exception
    //   148	84	4	localObject3	Object
    //   11	199	5	localObject4	Object
    //   20	113	6	localObject5	Object
    //   143	58	6	localException2	Exception
    //   52	141	7	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   47	54	125	java/lang/Exception
    //   70	83	125	java/lang/Exception
    //   87	93	125	java/lang/Exception
    //   97	107	125	java/lang/Exception
    //   17	43	137	finally
    //   17	43	143	java/lang/Exception
    //   47	54	216	finally
    //   70	83	216	finally
    //   87	93	216	finally
    //   97	107	216	finally
    //   150	155	216	finally
    //   158	162	216	finally
    //   165	169	216	finally
    //   172	180	216	finally
    //   183	189	216	finally
    //   192	207	216	finally
  }
  
  public static void finishAffinity(Activity paramActivity, int paramInt)
  {
    if (paramActivity == null) {
      return;
    }
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramActivity.finishAffinity();
    }
    else
    {
      paramActivity.setResult(paramInt);
      paramActivity.finish();
    }
  }
  
  public static void finishAffinity(Context paramContext, int paramInt)
  {
    if ((paramContext instanceof Activity)) {
      finishAffinity((Activity)paramContext, paramInt);
    }
  }
  
  public static void flushOrLog(Flushable paramFlushable, String paramString)
  {
    if (paramFlushable != null) {
      try
      {
        paramFlushable.flush();
      }
      catch (IOException paramFlushable)
      {
        Fabric.getLogger().e("Fabric", paramString, paramFlushable);
      }
    }
  }
  
  /* Error */
  public static String getAppIconHashOrNull(Context paramContext)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: invokevirtual 409	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   6: aload_0
    //   7: invokestatic 413	io/fabric/sdk/android/services/common/CommonUtils:getAppIconResourceId	(Landroid/content/Context;)I
    //   10: invokevirtual 419	android/content/res/Resources:openRawResource	(I)Ljava/io/InputStream;
    //   13: astore_2
    //   14: aload_2
    //   15: astore_0
    //   16: aload_2
    //   17: invokestatic 422	io/fabric/sdk/android/services/common/CommonUtils:sha1	(Ljava/io/InputStream;)Ljava/lang/String;
    //   20: astore_3
    //   21: aload_2
    //   22: astore_0
    //   23: aload_3
    //   24: invokestatic 426	io/fabric/sdk/android/services/common/CommonUtils:isNullOrEmpty	(Ljava/lang/String;)Z
    //   27: istore 4
    //   29: iload 4
    //   31: ifeq +8 -> 39
    //   34: aload_1
    //   35: astore_0
    //   36: goto +5 -> 41
    //   39: aload_3
    //   40: astore_0
    //   41: aload_2
    //   42: ldc_w 428
    //   45: invokestatic 370	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   48: aload_0
    //   49: areturn
    //   50: astore_1
    //   51: goto +12 -> 63
    //   54: astore_0
    //   55: aconst_null
    //   56: astore_1
    //   57: goto +75 -> 132
    //   60: astore_1
    //   61: aconst_null
    //   62: astore_2
    //   63: aload_2
    //   64: astore_0
    //   65: invokestatic 200	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   68: astore 5
    //   70: aload_2
    //   71: astore_0
    //   72: new 292	java/lang/StringBuilder
    //   75: astore_3
    //   76: aload_2
    //   77: astore_0
    //   78: aload_3
    //   79: invokespecial 293	java/lang/StringBuilder:<init>	()V
    //   82: aload_2
    //   83: astore_0
    //   84: aload_3
    //   85: ldc_w 430
    //   88: invokevirtual 310	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: pop
    //   92: aload_2
    //   93: astore_0
    //   94: aload_3
    //   95: aload_1
    //   96: invokevirtual 433	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   99: invokevirtual 310	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: aload_2
    //   104: astore_0
    //   105: aload 5
    //   107: ldc -54
    //   109: aload_3
    //   110: invokevirtual 313	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   113: invokeinterface 437 3 0
    //   118: aload_2
    //   119: ldc_w 428
    //   122: invokestatic 370	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   125: aconst_null
    //   126: areturn
    //   127: astore_2
    //   128: aload_0
    //   129: astore_1
    //   130: aload_2
    //   131: astore_0
    //   132: aload_1
    //   133: ldc_w 428
    //   136: invokestatic 370	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   139: aload_0
    //   140: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	141	0	paramContext	Context
    //   1	34	1	localObject1	Object
    //   50	1	1	localException1	Exception
    //   56	1	1	localObject2	Object
    //   60	36	1	localException2	Exception
    //   129	4	1	localContext	Context
    //   13	106	2	localInputStream	InputStream
    //   127	4	2	localObject3	Object
    //   20	90	3	localObject4	Object
    //   27	3	4	bool	boolean
    //   68	38	5	localLogger	Logger
    // Exception table:
    //   from	to	target	type
    //   16	21	50	java/lang/Exception
    //   23	29	50	java/lang/Exception
    //   2	14	54	finally
    //   2	14	60	java/lang/Exception
    //   16	21	127	finally
    //   23	29	127	finally
    //   65	70	127	finally
    //   72	76	127	finally
    //   78	82	127	finally
    //   84	92	127	finally
    //   94	103	127	finally
    //   105	118	127	finally
  }
  
  public static int getAppIconResourceId(Context paramContext)
  {
    return paramContext.getApplicationContext().getApplicationInfo().icon;
  }
  
  public static ActivityManager.RunningAppProcessInfo getAppProcessInfo(String paramString, Context paramContext)
  {
    paramContext = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
    if (paramContext != null)
    {
      Iterator localIterator = paramContext.iterator();
      while (localIterator.hasNext())
      {
        paramContext = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        if (paramContext.processName.equals(paramString)) {
          return paramContext;
        }
      }
    }
    paramString = null;
    return paramString;
  }
  
  public static Float getBatteryLevel(Context paramContext)
  {
    paramContext = paramContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    if (paramContext == null) {
      return null;
    }
    int i = paramContext.getIntExtra("level", -1);
    int j = paramContext.getIntExtra("scale", -1);
    return Float.valueOf(i / j);
  }
  
  public static int getBatteryVelocity(Context paramContext, boolean paramBoolean)
  {
    paramContext = getBatteryLevel(paramContext);
    if ((paramBoolean) && (paramContext != null))
    {
      if (paramContext.floatValue() >= 99.0D) {
        return 3;
      }
      if (paramContext.floatValue() < 99.0D) {
        return 2;
      }
      return 0;
    }
    return 1;
  }
  
  public static boolean getBooleanResourceValue(Context paramContext, String paramString, boolean paramBoolean)
  {
    if (paramContext != null)
    {
      Resources localResources = paramContext.getResources();
      if (localResources != null)
      {
        int i = getResourcesIdentifier(paramContext, paramString, "bool");
        if (i > 0) {
          return localResources.getBoolean(i);
        }
        i = getResourcesIdentifier(paramContext, paramString, "string");
        if (i > 0) {
          return Boolean.parseBoolean(paramContext.getString(i));
        }
      }
    }
    return paramBoolean;
  }
  
  public static int getCpuArchitectureInt()
  {
    return Architecture.getValue().ordinal();
  }
  
  public static int getDeviceState(Context paramContext)
  {
    if (isEmulator(paramContext)) {
      i = 1;
    } else {
      i = 0;
    }
    int j = i;
    if (isRooted(paramContext)) {
      j = i | 0x2;
    }
    int i = j;
    if (isDebuggerAttached()) {
      i = j | 0x4;
    }
    return i;
  }
  
  public static boolean getProximitySensorEnabled(Context paramContext)
  {
    boolean bool1 = isEmulator(paramContext);
    boolean bool2 = false;
    if (bool1) {
      return false;
    }
    if (((SensorManager)paramContext.getSystemService("sensor")).getDefaultSensor(8) != null) {
      bool2 = true;
    }
    return bool2;
  }
  
  public static String getResourcePackageName(Context paramContext)
  {
    int i = paramContext.getApplicationContext().getApplicationInfo().icon;
    if (i > 0) {
      try
      {
        String str = paramContext.getResources().getResourcePackageName(i);
        paramContext = str;
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        paramContext = paramContext.getPackageName();
      }
    } else {
      paramContext = paramContext.getPackageName();
    }
    return paramContext;
  }
  
  public static int getResourcesIdentifier(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getResources().getIdentifier(paramString1, paramString2, getResourcePackageName(paramContext));
  }
  
  public static SharedPreferences getSharedPrefs(Context paramContext)
  {
    return paramContext.getSharedPreferences("com.crashlytics.prefs", 0);
  }
  
  public static String getStringsFileValue(Context paramContext, String paramString)
  {
    int i = getResourcesIdentifier(paramContext, paramString, "string");
    if (i > 0) {
      return paramContext.getString(i);
    }
    return "";
  }
  
  public static long getTotalRamInBytes()
  {
    try
    {
      if (totalRamInBytes == -1L)
      {
        long l1 = 0L;
        Object localObject1 = new java/io/File;
        ((File)localObject1).<init>("/proc/meminfo");
        localObject1 = extractFieldFromSystemFile((File)localObject1, "MemTotal");
        l2 = l1;
        if (!TextUtils.isEmpty((CharSequence)localObject1))
        {
          localObject1 = ((String)localObject1).toUpperCase(Locale.US);
          try
          {
            if (((String)localObject1).endsWith("KB"))
            {
              l2 = convertMemInfoToBytes((String)localObject1, "KB", 1024);
            }
            else if (((String)localObject1).endsWith("MB"))
            {
              l2 = convertMemInfoToBytes((String)localObject1, "MB", 1048576);
            }
            else if (((String)localObject1).endsWith("GB"))
            {
              l2 = convertMemInfoToBytes((String)localObject1, "GB", 1073741824);
            }
            else
            {
              localObject3 = Fabric.getLogger();
              StringBuilder localStringBuilder = new java/lang/StringBuilder;
              localStringBuilder.<init>();
              localStringBuilder.append("Unexpected meminfo format while computing RAM: ");
              localStringBuilder.append((String)localObject1);
              ((Logger)localObject3).d("Fabric", localStringBuilder.toString());
              l2 = l1;
            }
          }
          catch (NumberFormatException localNumberFormatException)
          {
            Logger localLogger = Fabric.getLogger();
            Object localObject3 = new java/lang/StringBuilder;
            ((StringBuilder)localObject3).<init>();
            ((StringBuilder)localObject3).append("Unexpected meminfo format while computing RAM: ");
            ((StringBuilder)localObject3).append((String)localObject1);
            localLogger.e("Fabric", ((StringBuilder)localObject3).toString(), localNumberFormatException);
            l2 = l1;
          }
        }
        totalRamInBytes = l2;
      }
      long l2 = totalRamInBytes;
      return l2;
    }
    finally {}
  }
  
  private static String hash(InputStream paramInputStream, String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance(paramString);
      paramString = new byte['?'];
      for (;;)
      {
        int i = paramInputStream.read(paramString);
        if (i == -1) {
          break;
        }
        localMessageDigest.update(paramString, 0, i);
      }
      paramInputStream = hexify(localMessageDigest.digest());
      return paramInputStream;
    }
    catch (Exception paramInputStream)
    {
      Fabric.getLogger().e("Fabric", "Could not calculate hash for app icon.", paramInputStream);
    }
    return "";
  }
  
  private static String hash(String paramString1, String paramString2)
  {
    return hash(paramString1.getBytes(), paramString2);
  }
  
  private static String hash(byte[] paramArrayOfByte, String paramString)
  {
    try
    {
      localObject = MessageDigest.getInstance(paramString);
      ((MessageDigest)localObject).update(paramArrayOfByte);
      return hexify(((MessageDigest)localObject).digest());
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      paramArrayOfByte = Fabric.getLogger();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Could not create hashing algorithm: ");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(", returning empty string.");
      paramArrayOfByte.e("Fabric", ((StringBuilder)localObject).toString(), localNoSuchAlgorithmException);
    }
    return "";
  }
  
  public static String hexify(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = new char[paramArrayOfByte.length * 2];
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      int j = paramArrayOfByte[i] & 0xFF;
      int k = i * 2;
      char[] arrayOfChar2 = HEX_VALUES;
      arrayOfChar1[k] = ((char)arrayOfChar2[(j >>> 4)]);
      arrayOfChar1[(k + 1)] = ((char)arrayOfChar2[(j & 0xF)]);
    }
    return new String(arrayOfChar1);
  }
  
  public static void hideKeyboard(Context paramContext, View paramView)
  {
    paramContext = (InputMethodManager)paramContext.getSystemService("input_method");
    if (paramContext != null) {
      paramContext.hideSoftInputFromWindow(paramView.getWindowToken(), 0);
    }
  }
  
  public static boolean isAppDebuggable(Context paramContext)
  {
    boolean bool;
    if ((paramContext.getApplicationInfo().flags & 0x2) != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isClsTrace(Context paramContext)
  {
    if (clsTrace == null) {
      clsTrace = Boolean.valueOf(getBooleanResourceValue(paramContext, "com.crashlytics.Trace", false));
    }
    return clsTrace.booleanValue();
  }
  
  public static boolean isDebuggerAttached()
  {
    boolean bool;
    if ((!Debug.isDebuggerConnected()) && (!Debug.waitingForDebugger())) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isEmulator(Context paramContext)
  {
    paramContext = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    boolean bool;
    if ((!"sdk".equals(Build.PRODUCT)) && (!"google_sdk".equals(Build.PRODUCT)) && (paramContext != null)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  @Deprecated
  public static boolean isLoggingEnabled(Context paramContext)
  {
    return false;
  }
  
  public static boolean isNullOrEmpty(String paramString)
  {
    boolean bool;
    if ((paramString != null) && (paramString.length() != 0)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isRooted(Context paramContext)
  {
    boolean bool = isEmulator(paramContext);
    paramContext = Build.TAGS;
    if ((!bool) && (paramContext != null) && (paramContext.contains("test-keys"))) {
      return true;
    }
    if (new File("/system/app/Superuser.apk").exists()) {
      return true;
    }
    paramContext = new File("/system/xbin/su");
    return (!bool) && (paramContext.exists());
  }
  
  public static void logControlled(Context paramContext, int paramInt, String paramString1, String paramString2)
  {
    if (isClsTrace(paramContext)) {
      Fabric.getLogger().log(paramInt, "Fabric", paramString2);
    }
  }
  
  public static void logControlled(Context paramContext, String paramString)
  {
    if (isClsTrace(paramContext)) {
      Fabric.getLogger().d("Fabric", paramString);
    }
  }
  
  public static void logControlledError(Context paramContext, String paramString, Throwable paramThrowable)
  {
    if (isClsTrace(paramContext)) {
      Fabric.getLogger().e("Fabric", paramString);
    }
  }
  
  public static void logOrThrowIllegalArgumentException(String paramString1, String paramString2)
  {
    if (!Fabric.isDebuggable())
    {
      Fabric.getLogger().w(paramString1, paramString2);
      return;
    }
    throw new IllegalArgumentException(paramString2);
  }
  
  public static void logOrThrowIllegalStateException(String paramString1, String paramString2)
  {
    if (!Fabric.isDebuggable())
    {
      Fabric.getLogger().w(paramString1, paramString2);
      return;
    }
    throw new IllegalStateException(paramString2);
  }
  
  public static String logPriorityToString(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "?";
    case 7: 
      return "A";
    case 6: 
      return "E";
    case 5: 
      return "W";
    case 4: 
      return "I";
    case 3: 
      return "D";
    }
    return "V";
  }
  
  public static void openKeyboard(Context paramContext, View paramView)
  {
    paramContext = (InputMethodManager)paramContext.getSystemService("input_method");
    if (paramContext != null) {
      paramContext.showSoftInputFromInputMethod(paramView.getWindowToken(), 0);
    }
  }
  
  public static String padWithZerosToMaxIntWidth(int paramInt)
  {
    if (paramInt >= 0) {
      return String.format(Locale.US, "%1$10s", new Object[] { Integer.valueOf(paramInt) }).replace(' ', '0');
    }
    throw new IllegalArgumentException("value must be zero or greater");
  }
  
  public static String resolveBuildId(Context paramContext)
  {
    int i = getResourcesIdentifier(paramContext, "io.fabric.android.build_id", "string");
    int j = i;
    if (i == 0) {
      j = getResourcesIdentifier(paramContext, "com.crashlytics.android.build_id", "string");
    }
    if (j != 0)
    {
      paramContext = paramContext.getResources().getString(j);
      Logger localLogger = Fabric.getLogger();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Build ID is: ");
      localStringBuilder.append(paramContext);
      localLogger.d("Fabric", localStringBuilder.toString());
    }
    else
    {
      paramContext = null;
    }
    return paramContext;
  }
  
  public static String resolveUnityEditorVersion(Context paramContext)
  {
    int i = getResourcesIdentifier(paramContext, "com.google.firebase.crashlytics.unity_version", "string");
    if (i != 0)
    {
      paramContext = paramContext.getResources().getString(i);
      Logger localLogger = Fabric.getLogger();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unity Editor version is: ");
      localStringBuilder.append(paramContext);
      localLogger.d("Fabric", localStringBuilder.toString());
    }
    else
    {
      paramContext = null;
    }
    return paramContext;
  }
  
  public static String sha1(InputStream paramInputStream)
  {
    return hash(paramInputStream, "SHA-1");
  }
  
  public static String sha1(String paramString)
  {
    return hash(paramString, "SHA-1");
  }
  
  public static String sha256(String paramString)
  {
    return hash(paramString, "SHA-256");
  }
  
  public static String streamToString(InputStream paramInputStream)
    throws IOException
  {
    paramInputStream = new Scanner(paramInputStream).useDelimiter("\\A");
    if (paramInputStream.hasNext()) {
      paramInputStream = paramInputStream.next();
    } else {
      paramInputStream = "";
    }
    return paramInputStream;
  }
  
  public static boolean stringsEqualIncludingNull(String paramString1, String paramString2)
  {
    if (paramString1 == paramString2) {
      return true;
    }
    if (paramString1 != null) {
      return paramString1.equals(paramString2);
    }
    return false;
  }
  
  static enum Architecture
  {
    private static final Map<String, Architecture> matcher;
    
    static
    {
      ARM_UNKNOWN = new Architecture("ARM_UNKNOWN", 2);
      PPC = new Architecture("PPC", 3);
      PPC64 = new Architecture("PPC64", 4);
      ARMV6 = new Architecture("ARMV6", 5);
      ARMV7 = new Architecture("ARMV7", 6);
      UNKNOWN = new Architecture("UNKNOWN", 7);
      ARMV7S = new Architecture("ARMV7S", 8);
      Object localObject = new Architecture("ARM64", 9);
      ARM64 = (Architecture)localObject;
      $VALUES = new Architecture[] { X86_32, X86_64, ARM_UNKNOWN, PPC, PPC64, ARMV6, ARMV7, UNKNOWN, ARMV7S, localObject };
      localObject = new HashMap(4);
      matcher = (Map)localObject;
      ((Map)localObject).put("armeabi-v7a", ARMV7);
      matcher.put("armeabi", ARMV6);
      matcher.put("arm64-v8a", ARM64);
      matcher.put("x86", X86_32);
    }
    
    private Architecture() {}
    
    static Architecture getValue()
    {
      Object localObject = Build.CPU_ABI;
      if (TextUtils.isEmpty((CharSequence)localObject))
      {
        Fabric.getLogger().d("Fabric", "Architecture#getValue()::Build.CPU_ABI returned null or empty");
        return UNKNOWN;
      }
      localObject = ((String)localObject).toLowerCase(Locale.US);
      Architecture localArchitecture = (Architecture)matcher.get(localObject);
      localObject = localArchitecture;
      if (localArchitecture == null) {
        localObject = UNKNOWN;
      }
      return localObject;
    }
  }
}
