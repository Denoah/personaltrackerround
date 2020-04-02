package io.fabric.sdk.android.services.network;

public enum HttpMethod
{
  static
  {
    HttpMethod localHttpMethod = new HttpMethod("DELETE", 3);
    DELETE = localHttpMethod;
    $VALUES = new HttpMethod[] { GET, POST, PUT, localHttpMethod };
  }
  
  private HttpMethod() {}
}
