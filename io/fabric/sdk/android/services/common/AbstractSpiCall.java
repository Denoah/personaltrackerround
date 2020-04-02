package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractSpiCall
{
  public static final String ACCEPT_JSON_VALUE = "application/json";
  public static final String ANDROID_CLIENT_TYPE = "android";
  public static final String CLS_ANDROID_SDK_DEVELOPER_TOKEN = "470fa2b4ae81cd56ecbcda9735803434cec591fa";
  public static final String CRASHLYTICS_USER_AGENT = "Crashlytics Android SDK/";
  public static final int DEFAULT_TIMEOUT = 10000;
  public static final String HEADER_ACCEPT = "Accept";
  public static final String HEADER_API_KEY = "X-CRASHLYTICS-API-KEY";
  public static final String HEADER_CLIENT_TYPE = "X-CRASHLYTICS-API-CLIENT-TYPE";
  public static final String HEADER_CLIENT_VERSION = "X-CRASHLYTICS-API-CLIENT-VERSION";
  public static final String HEADER_DEVELOPER_TOKEN = "X-CRASHLYTICS-DEVELOPER-TOKEN";
  public static final String HEADER_REQUEST_ID = "X-REQUEST-ID";
  public static final String HEADER_USER_AGENT = "User-Agent";
  private static final Pattern PROTOCOL_AND_HOST_PATTERN = Pattern.compile("http(s?)://[^\\/]+", 2);
  protected final Kit kit;
  private final HttpMethod method;
  private final String protocolAndHostOverride;
  private final HttpRequestFactory requestFactory;
  private final String url;
  
  public AbstractSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, HttpMethod paramHttpMethod)
  {
    if (paramString2 != null)
    {
      if (paramHttpRequestFactory != null)
      {
        this.kit = paramKit;
        this.protocolAndHostOverride = paramString1;
        this.url = overrideProtocolAndHost(paramString2);
        this.requestFactory = paramHttpRequestFactory;
        this.method = paramHttpMethod;
        return;
      }
      throw new IllegalArgumentException("requestFactory must not be null.");
    }
    throw new IllegalArgumentException("url must not be null.");
  }
  
  private String overrideProtocolAndHost(String paramString)
  {
    String str = paramString;
    if (!CommonUtils.isNullOrEmpty(this.protocolAndHostOverride)) {
      str = PROTOCOL_AND_HOST_PATTERN.matcher(paramString).replaceFirst(this.protocolAndHostOverride);
    }
    return str;
  }
  
  protected HttpRequest getHttpRequest()
  {
    return getHttpRequest(Collections.emptyMap());
  }
  
  protected HttpRequest getHttpRequest(Map<String, String> paramMap)
  {
    paramMap = this.requestFactory.buildHttpRequest(this.method, getUrl(), paramMap).useCaches(false).connectTimeout(10000);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Crashlytics Android SDK/");
    localStringBuilder.append(this.kit.getVersion());
    return paramMap.header("User-Agent", localStringBuilder.toString()).header("X-CRASHLYTICS-DEVELOPER-TOKEN", "470fa2b4ae81cd56ecbcda9735803434cec591fa");
  }
  
  protected String getUrl()
  {
    return this.url;
  }
}