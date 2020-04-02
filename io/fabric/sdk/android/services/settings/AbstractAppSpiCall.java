package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitInfo;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.Locale;

abstract class AbstractAppSpiCall
  extends AbstractSpiCall
  implements AppSpiCall
{
  public static final String APP_BUILD_VERSION_PARAM = "app[build_version]";
  public static final String APP_BUILT_SDK_VERSION_PARAM = "app[built_sdk_version]";
  public static final String APP_DISPLAY_VERSION_PARAM = "app[display_version]";
  public static final String APP_ICON_DATA_PARAM = "app[icon][data]";
  public static final String APP_ICON_HASH_PARAM = "app[icon][hash]";
  public static final String APP_ICON_HEIGHT_PARAM = "app[icon][height]";
  public static final String APP_ICON_PRERENDERED_PARAM = "app[icon][prerendered]";
  public static final String APP_ICON_WIDTH_PARAM = "app[icon][width]";
  public static final String APP_IDENTIFIER_PARAM = "app[identifier]";
  public static final String APP_INSTANCE_IDENTIFIER_PARAM = "app[instance_identifier]";
  public static final String APP_MIN_SDK_VERSION_PARAM = "app[minimum_sdk_version]";
  public static final String APP_NAME_PARAM = "app[name]";
  public static final String APP_SDK_MODULES_PARAM_BUILD_TYPE = "app[build][libraries][%s][type]";
  public static final String APP_SDK_MODULES_PARAM_PREFIX = "app[build][libraries][%s]";
  public static final String APP_SDK_MODULES_PARAM_VERSION = "app[build][libraries][%s][version]";
  public static final String APP_SOURCE_PARAM = "app[source]";
  static final String ICON_CONTENT_TYPE = "application/octet-stream";
  static final String ICON_FILE_NAME = "icon.png";
  
  public AbstractAppSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, HttpMethod paramHttpMethod)
  {
    super(paramKit, paramString1, paramString2, paramHttpRequestFactory, paramHttpMethod);
  }
  
  private HttpRequest applyHeadersTo(HttpRequest paramHttpRequest, AppRequestData paramAppRequestData)
  {
    return paramHttpRequest.header("X-CRASHLYTICS-API-KEY", paramAppRequestData.apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
  }
  
  /* Error */
  private HttpRequest applyMultipartDataTo(HttpRequest paramHttpRequest, AppRequestData paramAppRequestData)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 34
    //   3: aload_2
    //   4: getfield 103	io/fabric/sdk/android/services/settings/AppRequestData:appId	Ljava/lang/String;
    //   7: invokevirtual 106	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   10: ldc 43
    //   12: aload_2
    //   13: getfield 109	io/fabric/sdk/android/services/settings/AppRequestData:name	Ljava/lang/String;
    //   16: invokevirtual 106	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   19: ldc 16
    //   21: aload_2
    //   22: getfield 112	io/fabric/sdk/android/services/settings/AppRequestData:displayVersion	Ljava/lang/String;
    //   25: invokevirtual 106	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   28: ldc 10
    //   30: aload_2
    //   31: getfield 115	io/fabric/sdk/android/services/settings/AppRequestData:buildVersion	Ljava/lang/String;
    //   34: invokevirtual 106	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   37: ldc 55
    //   39: aload_2
    //   40: getfield 119	io/fabric/sdk/android/services/settings/AppRequestData:source	I
    //   43: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   46: invokevirtual 128	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/Number;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   49: ldc 40
    //   51: aload_2
    //   52: getfield 131	io/fabric/sdk/android/services/settings/AppRequestData:minSdkVersion	Ljava/lang/String;
    //   55: invokevirtual 106	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   58: ldc 13
    //   60: aload_2
    //   61: getfield 134	io/fabric/sdk/android/services/settings/AppRequestData:builtSdkVersion	Ljava/lang/String;
    //   64: invokevirtual 106	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   67: astore_3
    //   68: aload_2
    //   69: getfield 137	io/fabric/sdk/android/services/settings/AppRequestData:instanceIdentifier	Ljava/lang/String;
    //   72: invokestatic 143	io/fabric/sdk/android/services/common/CommonUtils:isNullOrEmpty	(Ljava/lang/String;)Z
    //   75: ifne +14 -> 89
    //   78: aload_3
    //   79: ldc 37
    //   81: aload_2
    //   82: getfield 137	io/fabric/sdk/android/services/settings/AppRequestData:instanceIdentifier	Ljava/lang/String;
    //   85: invokevirtual 106	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   88: pop
    //   89: aload_2
    //   90: getfield 147	io/fabric/sdk/android/services/settings/AppRequestData:icon	Lio/fabric/sdk/android/services/settings/IconRequest;
    //   93: ifnull +192 -> 285
    //   96: aconst_null
    //   97: astore_1
    //   98: aconst_null
    //   99: astore 4
    //   101: aload_0
    //   102: getfield 91	io/fabric/sdk/android/services/settings/AbstractAppSpiCall:kit	Lio/fabric/sdk/android/Kit;
    //   105: invokevirtual 151	io/fabric/sdk/android/Kit:getContext	()Landroid/content/Context;
    //   108: invokevirtual 157	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   111: aload_2
    //   112: getfield 147	io/fabric/sdk/android/services/settings/AppRequestData:icon	Lio/fabric/sdk/android/services/settings/IconRequest;
    //   115: getfield 162	io/fabric/sdk/android/services/settings/IconRequest:iconResourceId	I
    //   118: invokevirtual 168	android/content/res/Resources:openRawResource	(I)Ljava/io/InputStream;
    //   121: astore 5
    //   123: aload 5
    //   125: astore 4
    //   127: aload 5
    //   129: astore_1
    //   130: aload_3
    //   131: ldc 22
    //   133: aload_2
    //   134: getfield 147	io/fabric/sdk/android/services/settings/AppRequestData:icon	Lio/fabric/sdk/android/services/settings/IconRequest;
    //   137: getfield 171	io/fabric/sdk/android/services/settings/IconRequest:hash	Ljava/lang/String;
    //   140: invokevirtual 106	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   143: ldc 19
    //   145: ldc 61
    //   147: ldc 58
    //   149: aload 5
    //   151: invokevirtual 174	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/io/InputStream;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   154: ldc 31
    //   156: aload_2
    //   157: getfield 147	io/fabric/sdk/android/services/settings/AppRequestData:icon	Lio/fabric/sdk/android/services/settings/IconRequest;
    //   160: getfield 177	io/fabric/sdk/android/services/settings/IconRequest:width	I
    //   163: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   166: invokevirtual 128	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/Number;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   169: ldc 25
    //   171: aload_2
    //   172: getfield 147	io/fabric/sdk/android/services/settings/AppRequestData:icon	Lio/fabric/sdk/android/services/settings/IconRequest;
    //   175: getfield 180	io/fabric/sdk/android/services/settings/IconRequest:height	I
    //   178: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   181: invokevirtual 128	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/Number;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   184: pop
    //   185: aload 5
    //   187: astore_1
    //   188: goto +79 -> 267
    //   191: astore_1
    //   192: goto +84 -> 276
    //   195: astore 6
    //   197: aload_1
    //   198: astore 4
    //   200: invokestatic 186	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   203: astore 7
    //   205: aload_1
    //   206: astore 4
    //   208: new 188	java/lang/StringBuilder
    //   211: astore 5
    //   213: aload_1
    //   214: astore 4
    //   216: aload 5
    //   218: invokespecial 191	java/lang/StringBuilder:<init>	()V
    //   221: aload_1
    //   222: astore 4
    //   224: aload 5
    //   226: ldc -63
    //   228: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   231: pop
    //   232: aload_1
    //   233: astore 4
    //   235: aload 5
    //   237: aload_2
    //   238: getfield 147	io/fabric/sdk/android/services/settings/AppRequestData:icon	Lio/fabric/sdk/android/services/settings/IconRequest;
    //   241: getfield 162	io/fabric/sdk/android/services/settings/IconRequest:iconResourceId	I
    //   244: invokevirtual 200	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   247: pop
    //   248: aload_1
    //   249: astore 4
    //   251: aload 7
    //   253: ldc -54
    //   255: aload 5
    //   257: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   260: aload 6
    //   262: invokeinterface 211 4 0
    //   267: aload_1
    //   268: ldc -43
    //   270: invokestatic 217	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   273: goto +12 -> 285
    //   276: aload 4
    //   278: ldc -43
    //   280: invokestatic 217	io/fabric/sdk/android/services/common/CommonUtils:closeOrLog	(Ljava/io/Closeable;Ljava/lang/String;)V
    //   283: aload_1
    //   284: athrow
    //   285: aload_2
    //   286: getfield 221	io/fabric/sdk/android/services/settings/AppRequestData:sdkKits	Ljava/util/Collection;
    //   289: ifnull +63 -> 352
    //   292: aload_2
    //   293: getfield 221	io/fabric/sdk/android/services/settings/AppRequestData:sdkKits	Ljava/util/Collection;
    //   296: invokeinterface 227 1 0
    //   301: astore_2
    //   302: aload_2
    //   303: invokeinterface 233 1 0
    //   308: ifeq +44 -> 352
    //   311: aload_2
    //   312: invokeinterface 237 1 0
    //   317: checkcast 239	io/fabric/sdk/android/KitInfo
    //   320: astore_1
    //   321: aload_3
    //   322: aload_0
    //   323: aload_1
    //   324: invokevirtual 243	io/fabric/sdk/android/services/settings/AbstractAppSpiCall:getKitVersionKey	(Lio/fabric/sdk/android/KitInfo;)Ljava/lang/String;
    //   327: aload_1
    //   328: invokevirtual 244	io/fabric/sdk/android/KitInfo:getVersion	()Ljava/lang/String;
    //   331: invokevirtual 106	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   334: pop
    //   335: aload_3
    //   336: aload_0
    //   337: aload_1
    //   338: invokevirtual 247	io/fabric/sdk/android/services/settings/AbstractAppSpiCall:getKitBuildTypeKey	(Lio/fabric/sdk/android/KitInfo;)Ljava/lang/String;
    //   341: aload_1
    //   342: invokevirtual 250	io/fabric/sdk/android/KitInfo:getBuildType	()Ljava/lang/String;
    //   345: invokevirtual 106	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   348: pop
    //   349: goto -47 -> 302
    //   352: aload_3
    //   353: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	354	0	this	AbstractAppSpiCall
    //   0	354	1	paramHttpRequest	HttpRequest
    //   0	354	2	paramAppRequestData	AppRequestData
    //   67	286	3	localHttpRequest	HttpRequest
    //   99	178	4	localObject1	Object
    //   121	135	5	localObject2	Object
    //   195	66	6	localNotFoundException	android.content.res.Resources.NotFoundException
    //   203	49	7	localLogger	Logger
    // Exception table:
    //   from	to	target	type
    //   101	123	191	finally
    //   130	185	191	finally
    //   200	205	191	finally
    //   208	213	191	finally
    //   216	221	191	finally
    //   224	232	191	finally
    //   235	248	191	finally
    //   251	267	191	finally
    //   101	123	195	android/content/res/Resources$NotFoundException
    //   130	185	195	android/content/res/Resources$NotFoundException
  }
  
  String getKitBuildTypeKey(KitInfo paramKitInfo)
  {
    return String.format(Locale.US, "app[build][libraries][%s][type]", new Object[] { paramKitInfo.getIdentifier() });
  }
  
  String getKitVersionKey(KitInfo paramKitInfo)
  {
    return String.format(Locale.US, "app[build][libraries][%s][version]", new Object[] { paramKitInfo.getIdentifier() });
  }
  
  public boolean invoke(AppRequestData paramAppRequestData)
  {
    Object localObject1 = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), paramAppRequestData), paramAppRequestData);
    Object localObject2 = Fabric.getLogger();
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("Sending app info to ");
    ((StringBuilder)localObject3).append(getUrl());
    ((Logger)localObject2).d("Fabric", ((StringBuilder)localObject3).toString());
    if (paramAppRequestData.icon != null)
    {
      localObject3 = Fabric.getLogger();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("App icon hash is ");
      ((StringBuilder)localObject2).append(paramAppRequestData.icon.hash);
      ((Logger)localObject3).d("Fabric", ((StringBuilder)localObject2).toString());
      localObject3 = Fabric.getLogger();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("App icon size is ");
      ((StringBuilder)localObject2).append(paramAppRequestData.icon.width);
      ((StringBuilder)localObject2).append("x");
      ((StringBuilder)localObject2).append(paramAppRequestData.icon.height);
      ((Logger)localObject3).d("Fabric", ((StringBuilder)localObject2).toString());
    }
    int i = ((HttpRequest)localObject1).code();
    if ("POST".equals(((HttpRequest)localObject1).method())) {
      paramAppRequestData = "Create";
    } else {
      paramAppRequestData = "Update";
    }
    localObject2 = Fabric.getLogger();
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append(paramAppRequestData);
    ((StringBuilder)localObject3).append(" app request ID: ");
    ((StringBuilder)localObject3).append(((HttpRequest)localObject1).header("X-REQUEST-ID"));
    ((Logger)localObject2).d("Fabric", ((StringBuilder)localObject3).toString());
    paramAppRequestData = Fabric.getLogger();
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Result was ");
    ((StringBuilder)localObject1).append(i);
    paramAppRequestData.d("Fabric", ((StringBuilder)localObject1).toString());
    boolean bool;
    if (ResponseParser.parse(i) == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
}
