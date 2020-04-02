package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequestFactory;

public class UpdateAppSpiCall
  extends AbstractAppSpiCall
{
  public UpdateAppSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory)
  {
    super(paramKit, paramString1, paramString2, paramHttpRequestFactory, HttpMethod.PUT);
  }
}
