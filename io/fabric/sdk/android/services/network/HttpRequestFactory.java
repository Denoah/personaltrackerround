package io.fabric.sdk.android.services.network;

import java.util.Map;

public abstract interface HttpRequestFactory
{
  public abstract HttpRequest buildHttpRequest(HttpMethod paramHttpMethod, String paramString);
  
  public abstract HttpRequest buildHttpRequest(HttpMethod paramHttpMethod, String paramString, Map<String, String> paramMap);
  
  public abstract PinningInfoProvider getPinningInfoProvider();
  
  public abstract void setPinningInfoProvider(PinningInfoProvider paramPinningInfoProvider);
}
