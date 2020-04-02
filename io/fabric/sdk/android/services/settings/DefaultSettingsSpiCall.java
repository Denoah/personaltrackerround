package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class DefaultSettingsSpiCall
  extends AbstractSpiCall
  implements SettingsSpiCall
{
  static final String BUILD_VERSION_PARAM = "build_version";
  static final String DISPLAY_VERSION_PARAM = "display_version";
  static final String HEADER_DEVICE_MODEL = "X-CRASHLYTICS-DEVICE-MODEL";
  static final String HEADER_INSTALLATION_ID = "X-CRASHLYTICS-INSTALLATION-ID";
  static final String HEADER_OS_BUILD_VERSION = "X-CRASHLYTICS-OS-BUILD-VERSION";
  static final String HEADER_OS_DISPLAY_VERSION = "X-CRASHLYTICS-OS-DISPLAY-VERSION";
  static final String ICON_HASH = "icon_hash";
  static final String INSTANCE_PARAM = "instance";
  static final String SOURCE_PARAM = "source";
  
  public DefaultSettingsSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory)
  {
    this(paramKit, paramString1, paramString2, paramHttpRequestFactory, HttpMethod.GET);
  }
  
  DefaultSettingsSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, HttpMethod paramHttpMethod)
  {
    super(paramKit, paramString1, paramString2, paramHttpRequestFactory, paramHttpMethod);
  }
  
  private HttpRequest applyHeadersTo(HttpRequest paramHttpRequest, SettingsRequest paramSettingsRequest)
  {
    applyNonNullHeader(paramHttpRequest, "X-CRASHLYTICS-API-KEY", paramSettingsRequest.apiKey);
    applyNonNullHeader(paramHttpRequest, "X-CRASHLYTICS-API-CLIENT-TYPE", "android");
    applyNonNullHeader(paramHttpRequest, "X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
    applyNonNullHeader(paramHttpRequest, "Accept", "application/json");
    applyNonNullHeader(paramHttpRequest, "X-CRASHLYTICS-DEVICE-MODEL", paramSettingsRequest.deviceModel);
    applyNonNullHeader(paramHttpRequest, "X-CRASHLYTICS-OS-BUILD-VERSION", paramSettingsRequest.osBuildVersion);
    applyNonNullHeader(paramHttpRequest, "X-CRASHLYTICS-OS-DISPLAY-VERSION", paramSettingsRequest.osDisplayVersion);
    applyNonNullHeader(paramHttpRequest, "X-CRASHLYTICS-INSTALLATION-ID", paramSettingsRequest.installationId);
    return paramHttpRequest;
  }
  
  private void applyNonNullHeader(HttpRequest paramHttpRequest, String paramString1, String paramString2)
  {
    if (paramString2 != null) {
      paramHttpRequest.header(paramString1, paramString2);
    }
  }
  
  private JSONObject getJsonObjectFrom(String paramString)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      return localJSONObject;
    }
    catch (Exception localException)
    {
      Object localObject = Fabric.getLogger();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Failed to parse settings JSON from ");
      localStringBuilder.append(getUrl());
      ((Logger)localObject).d("Fabric", localStringBuilder.toString(), localException);
      Logger localLogger = Fabric.getLogger();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Settings response ");
      ((StringBuilder)localObject).append(paramString);
      localLogger.d("Fabric", ((StringBuilder)localObject).toString());
    }
    return null;
  }
  
  private Map<String, String> getQueryParamsFor(SettingsRequest paramSettingsRequest)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("build_version", paramSettingsRequest.buildVersion);
    localHashMap.put("display_version", paramSettingsRequest.displayVersion);
    localHashMap.put("source", Integer.toString(paramSettingsRequest.source));
    if (paramSettingsRequest.iconHash != null) {
      localHashMap.put("icon_hash", paramSettingsRequest.iconHash);
    }
    paramSettingsRequest = paramSettingsRequest.instanceId;
    if (!CommonUtils.isNullOrEmpty(paramSettingsRequest)) {
      localHashMap.put("instance", paramSettingsRequest);
    }
    return localHashMap;
  }
  
  JSONObject handleResponse(HttpRequest paramHttpRequest)
  {
    int i = paramHttpRequest.code();
    Logger localLogger = Fabric.getLogger();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Settings result was: ");
    ((StringBuilder)localObject).append(i);
    localLogger.d("Fabric", ((StringBuilder)localObject).toString());
    if (requestWasSuccessful(i))
    {
      paramHttpRequest = getJsonObjectFrom(paramHttpRequest.body());
    }
    else
    {
      localObject = Fabric.getLogger();
      paramHttpRequest = new StringBuilder();
      paramHttpRequest.append("Failed to retrieve settings from ");
      paramHttpRequest.append(getUrl());
      ((Logger)localObject).e("Fabric", paramHttpRequest.toString());
      paramHttpRequest = null;
    }
    return paramHttpRequest;
  }
  
  /* Error */
  public JSONObject invoke(SettingsRequest paramSettingsRequest)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: aconst_null
    //   5: astore 4
    //   7: aload_0
    //   8: aload_1
    //   9: invokespecial 213	io/fabric/sdk/android/services/settings/DefaultSettingsSpiCall:getQueryParamsFor	(Lio/fabric/sdk/android/services/settings/SettingsRequest;)Ljava/util/Map;
    //   12: astore 5
    //   14: aload_0
    //   15: aload 5
    //   17: invokevirtual 217	io/fabric/sdk/android/services/settings/DefaultSettingsSpiCall:getHttpRequest	(Ljava/util/Map;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   20: astore 6
    //   22: aload 6
    //   24: astore_3
    //   25: aload 6
    //   27: astore 7
    //   29: aload_0
    //   30: aload 6
    //   32: aload_1
    //   33: invokespecial 219	io/fabric/sdk/android/services/settings/DefaultSettingsSpiCall:applyHeadersTo	(Lio/fabric/sdk/android/services/network/HttpRequest;Lio/fabric/sdk/android/services/settings/SettingsRequest;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   36: astore_1
    //   37: aload_1
    //   38: astore_3
    //   39: aload_1
    //   40: astore 7
    //   42: invokestatic 113	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   45: astore 8
    //   47: aload_1
    //   48: astore_3
    //   49: aload_1
    //   50: astore 7
    //   52: new 115	java/lang/StringBuilder
    //   55: astore 6
    //   57: aload_1
    //   58: astore_3
    //   59: aload_1
    //   60: astore 7
    //   62: aload 6
    //   64: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   67: aload_1
    //   68: astore_3
    //   69: aload_1
    //   70: astore 7
    //   72: aload 6
    //   74: ldc -35
    //   76: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: pop
    //   80: aload_1
    //   81: astore_3
    //   82: aload_1
    //   83: astore 7
    //   85: aload 6
    //   87: aload_0
    //   88: invokevirtual 127	io/fabric/sdk/android/services/settings/DefaultSettingsSpiCall:getUrl	()Ljava/lang/String;
    //   91: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: pop
    //   95: aload_1
    //   96: astore_3
    //   97: aload_1
    //   98: astore 7
    //   100: aload 8
    //   102: ldc -127
    //   104: aload 6
    //   106: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   109: invokeinterface 143 3 0
    //   114: aload_1
    //   115: astore_3
    //   116: aload_1
    //   117: astore 7
    //   119: invokestatic 113	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   122: astore 6
    //   124: aload_1
    //   125: astore_3
    //   126: aload_1
    //   127: astore 7
    //   129: new 115	java/lang/StringBuilder
    //   132: astore 8
    //   134: aload_1
    //   135: astore_3
    //   136: aload_1
    //   137: astore 7
    //   139: aload 8
    //   141: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   144: aload_1
    //   145: astore_3
    //   146: aload_1
    //   147: astore 7
    //   149: aload 8
    //   151: ldc -33
    //   153: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   156: pop
    //   157: aload_1
    //   158: astore_3
    //   159: aload_1
    //   160: astore 7
    //   162: aload 8
    //   164: aload 5
    //   166: invokevirtual 226	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   169: pop
    //   170: aload_1
    //   171: astore_3
    //   172: aload_1
    //   173: astore 7
    //   175: aload 6
    //   177: ldc -127
    //   179: aload 8
    //   181: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   184: invokeinterface 143 3 0
    //   189: aload_1
    //   190: astore_3
    //   191: aload_1
    //   192: astore 7
    //   194: aload_0
    //   195: aload_1
    //   196: invokevirtual 228	io/fabric/sdk/android/services/settings/DefaultSettingsSpiCall:handleResponse	(Lio/fabric/sdk/android/services/network/HttpRequest;)Lorg/json/JSONObject;
    //   199: astore 6
    //   201: aload 6
    //   203: astore 7
    //   205: aload_1
    //   206: ifnull +123 -> 329
    //   209: invokestatic 113	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   212: astore_3
    //   213: new 115	java/lang/StringBuilder
    //   216: dup
    //   217: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   220: astore 4
    //   222: aload 6
    //   224: astore 7
    //   226: aload 4
    //   228: ldc -26
    //   230: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   233: pop
    //   234: aload 4
    //   236: aload_1
    //   237: ldc -24
    //   239: invokevirtual 235	io/fabric/sdk/android/services/network/HttpRequest:header	(Ljava/lang/String;)Ljava/lang/String;
    //   242: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   245: pop
    //   246: aload_3
    //   247: ldc -127
    //   249: aload 4
    //   251: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   254: invokeinterface 143 3 0
    //   259: goto +70 -> 329
    //   262: astore 7
    //   264: aload_3
    //   265: astore_1
    //   266: aload 7
    //   268: astore_3
    //   269: goto +13 -> 282
    //   272: astore 7
    //   274: aload_3
    //   275: astore_1
    //   276: goto +63 -> 339
    //   279: astore_3
    //   280: aconst_null
    //   281: astore_1
    //   282: aload_1
    //   283: astore 7
    //   285: invokestatic 113	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   288: ldc -127
    //   290: ldc -19
    //   292: aload_3
    //   293: invokeinterface 239 4 0
    //   298: aload_2
    //   299: astore 7
    //   301: aload_1
    //   302: ifnull +27 -> 329
    //   305: invokestatic 113	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   308: astore_3
    //   309: new 115	java/lang/StringBuilder
    //   312: dup
    //   313: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   316: astore 6
    //   318: aload 4
    //   320: astore 7
    //   322: aload 6
    //   324: astore 4
    //   326: goto -100 -> 226
    //   329: aload 7
    //   331: areturn
    //   332: astore_3
    //   333: aload 7
    //   335: astore_1
    //   336: aload_3
    //   337: astore 7
    //   339: aload_1
    //   340: ifnull +49 -> 389
    //   343: invokestatic 113	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   346: astore_3
    //   347: new 115	java/lang/StringBuilder
    //   350: dup
    //   351: invokespecial 118	java/lang/StringBuilder:<init>	()V
    //   354: astore 6
    //   356: aload 6
    //   358: ldc -26
    //   360: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   363: pop
    //   364: aload 6
    //   366: aload_1
    //   367: ldc -24
    //   369: invokevirtual 235	io/fabric/sdk/android/services/network/HttpRequest:header	(Ljava/lang/String;)Ljava/lang/String;
    //   372: invokevirtual 124	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   375: pop
    //   376: aload_3
    //   377: ldc -127
    //   379: aload 6
    //   381: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   384: invokeinterface 143 3 0
    //   389: aload 7
    //   391: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	392	0	this	DefaultSettingsSpiCall
    //   0	392	1	paramSettingsRequest	SettingsRequest
    //   1	298	2	localObject1	Object
    //   3	272	3	localObject2	Object
    //   279	14	3	localHttpRequestException1	io.fabric.sdk.android.services.network.HttpRequest.HttpRequestException
    //   308	1	3	localLogger1	Logger
    //   332	5	3	localObject3	Object
    //   346	31	3	localLogger2	Logger
    //   5	320	4	localObject4	Object
    //   12	153	5	localMap	Map
    //   20	360	6	localObject5	Object
    //   27	198	7	localObject6	Object
    //   262	5	7	localHttpRequestException2	io.fabric.sdk.android.services.network.HttpRequest.HttpRequestException
    //   272	1	7	localObject7	Object
    //   283	107	7	localObject8	Object
    //   45	135	8	localObject9	Object
    // Exception table:
    //   from	to	target	type
    //   29	37	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   42	47	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   52	57	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   62	67	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   72	80	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   85	95	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   100	114	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   119	124	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   129	134	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   139	144	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   149	157	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   162	170	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   175	189	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   194	201	262	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   7	22	272	finally
    //   7	22	279	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   29	37	332	finally
    //   42	47	332	finally
    //   52	57	332	finally
    //   62	67	332	finally
    //   72	80	332	finally
    //   85	95	332	finally
    //   100	114	332	finally
    //   119	124	332	finally
    //   129	134	332	finally
    //   139	144	332	finally
    //   149	157	332	finally
    //   162	170	332	finally
    //   175	189	332	finally
    //   194	201	332	finally
    //   285	298	332	finally
  }
  
  boolean requestWasSuccessful(int paramInt)
  {
    boolean bool;
    if ((paramInt != 200) && (paramInt != 201) && (paramInt != 202) && (paramInt != 203)) {
      bool = false;
    } else {
      bool = true;
    }
    return bool;
  }
}
