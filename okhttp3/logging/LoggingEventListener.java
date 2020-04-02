package okhttp3.logging;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.EventListener.Factory;
import okhttp3.Handshake;
import okhttp3.HttpUrl;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

@Metadata(bv={1, 0, 3}, d1={"\000|\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\b\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\030\0002\0020\001:\001>B\017\b\002\022\006\020\002\032\0020\003?\006\002\020\004J\020\020\007\032\0020\b2\006\020\t\032\0020\nH\026J\030\020\013\032\0020\b2\006\020\t\032\0020\n2\006\020\f\032\0020\rH\026J\020\020\016\032\0020\b2\006\020\t\032\0020\nH\026J\020\020\017\032\0020\b2\006\020\t\032\0020\nH\026J*\020\020\032\0020\b2\006\020\t\032\0020\n2\006\020\021\032\0020\0222\006\020\023\032\0020\0242\b\020\025\032\004\030\0010\026H\026J2\020\027\032\0020\b2\006\020\t\032\0020\n2\006\020\021\032\0020\0222\006\020\023\032\0020\0242\b\020\025\032\004\030\0010\0262\006\020\f\032\0020\rH\026J \020\030\032\0020\b2\006\020\t\032\0020\n2\006\020\021\032\0020\0222\006\020\023\032\0020\024H\026J\030\020\031\032\0020\b2\006\020\t\032\0020\n2\006\020\032\032\0020\033H\026J\030\020\034\032\0020\b2\006\020\t\032\0020\n2\006\020\032\032\0020\033H\026J&\020\035\032\0020\b2\006\020\t\032\0020\n2\006\020\036\032\0020\0372\f\020 \032\b\022\004\022\0020\"0!H\026J\030\020#\032\0020\b2\006\020\t\032\0020\n2\006\020\036\032\0020\037H\026J\020\020$\032\0020\b2\006\020%\032\0020\037H\002J&\020&\032\0020\b2\006\020\t\032\0020\n2\006\020'\032\0020(2\f\020)\032\b\022\004\022\0020\0240!H\026J\030\020*\032\0020\b2\006\020\t\032\0020\n2\006\020'\032\0020(H\026J\030\020+\032\0020\b2\006\020\t\032\0020\n2\006\020,\032\0020\006H\026J\020\020-\032\0020\b2\006\020\t\032\0020\nH\026J\030\020.\032\0020\b2\006\020\t\032\0020\n2\006\020\f\032\0020\rH\026J\030\020/\032\0020\b2\006\020\t\032\0020\n2\006\0200\032\00201H\026J\020\0202\032\0020\b2\006\020\t\032\0020\nH\026J\030\0203\032\0020\b2\006\020\t\032\0020\n2\006\020,\032\0020\006H\026J\020\0204\032\0020\b2\006\020\t\032\0020\nH\026J\030\0205\032\0020\b2\006\020\t\032\0020\n2\006\020\f\032\0020\rH\026J\030\0206\032\0020\b2\006\020\t\032\0020\n2\006\0207\032\00208H\026J\020\0209\032\0020\b2\006\020\t\032\0020\nH\026J\032\020:\032\0020\b2\006\020\t\032\0020\n2\b\020;\032\004\030\0010<H\026J\020\020=\032\0020\b2\006\020\t\032\0020\nH\026R\016\020\002\032\0020\003X?\004?\006\002\n\000R\016\020\005\032\0020\006X?\016?\006\002\n\000?\006?"}, d2={"Lokhttp3/logging/LoggingEventListener;", "Lokhttp3/EventListener;", "logger", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "(Lokhttp3/logging/HttpLoggingInterceptor$Logger;)V", "startNs", "", "callEnd", "", "call", "Lokhttp3/Call;", "callFailed", "ioe", "Ljava/io/IOException;", "callStart", "canceled", "connectEnd", "inetSocketAddress", "Ljava/net/InetSocketAddress;", "proxy", "Ljava/net/Proxy;", "protocol", "Lokhttp3/Protocol;", "connectFailed", "connectStart", "connectionAcquired", "connection", "Lokhttp3/Connection;", "connectionReleased", "dnsEnd", "domainName", "", "inetAddressList", "", "Ljava/net/InetAddress;", "dnsStart", "logWithTime", "message", "proxySelectEnd", "url", "Lokhttp3/HttpUrl;", "proxies", "proxySelectStart", "requestBodyEnd", "byteCount", "requestBodyStart", "requestFailed", "requestHeadersEnd", "request", "Lokhttp3/Request;", "requestHeadersStart", "responseBodyEnd", "responseBodyStart", "responseFailed", "responseHeadersEnd", "response", "Lokhttp3/Response;", "responseHeadersStart", "secureConnectEnd", "handshake", "Lokhttp3/Handshake;", "secureConnectStart", "Factory", "okhttp-logging-interceptor"}, k=1, mv={1, 1, 16})
public final class LoggingEventListener
  extends EventListener
{
  private final HttpLoggingInterceptor.Logger logger;
  private long startNs;
  
  private LoggingEventListener(HttpLoggingInterceptor.Logger paramLogger)
  {
    this.logger = paramLogger;
  }
  
  private final void logWithTime(String paramString)
  {
    long l = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this.startNs);
    HttpLoggingInterceptor.Logger localLogger = this.logger;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('[');
    localStringBuilder.append(l);
    localStringBuilder.append(" ms] ");
    localStringBuilder.append(paramString);
    localLogger.log(localStringBuilder.toString());
  }
  
  public void callEnd(Call paramCall)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    logWithTime("callEnd");
  }
  
  public void callFailed(Call paramCall, IOException paramIOException)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramIOException, "ioe");
    paramCall = new StringBuilder();
    paramCall.append("callFailed: ");
    paramCall.append(paramIOException);
    logWithTime(paramCall.toString());
  }
  
  public void callStart(Call paramCall)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    this.startNs = System.nanoTime();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("callStart: ");
    localStringBuilder.append(paramCall.request());
    logWithTime(localStringBuilder.toString());
  }
  
  public void canceled(Call paramCall)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    logWithTime("canceled");
  }
  
  public void connectEnd(Call paramCall, InetSocketAddress paramInetSocketAddress, Proxy paramProxy, Protocol paramProtocol)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramInetSocketAddress, "inetSocketAddress");
    Intrinsics.checkParameterIsNotNull(paramProxy, "proxy");
    paramCall = new StringBuilder();
    paramCall.append("connectEnd: ");
    paramCall.append(paramProtocol);
    logWithTime(paramCall.toString());
  }
  
  public void connectFailed(Call paramCall, InetSocketAddress paramInetSocketAddress, Proxy paramProxy, Protocol paramProtocol, IOException paramIOException)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramInetSocketAddress, "inetSocketAddress");
    Intrinsics.checkParameterIsNotNull(paramProxy, "proxy");
    Intrinsics.checkParameterIsNotNull(paramIOException, "ioe");
    paramCall = new StringBuilder();
    paramCall.append("connectFailed: ");
    paramCall.append(paramProtocol);
    paramCall.append(' ');
    paramCall.append(paramIOException);
    logWithTime(paramCall.toString());
  }
  
  public void connectStart(Call paramCall, InetSocketAddress paramInetSocketAddress, Proxy paramProxy)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramInetSocketAddress, "inetSocketAddress");
    Intrinsics.checkParameterIsNotNull(paramProxy, "proxy");
    paramCall = new StringBuilder();
    paramCall.append("connectStart: ");
    paramCall.append(paramInetSocketAddress);
    paramCall.append(' ');
    paramCall.append(paramProxy);
    logWithTime(paramCall.toString());
  }
  
  public void connectionAcquired(Call paramCall, Connection paramConnection)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramConnection, "connection");
    paramCall = new StringBuilder();
    paramCall.append("connectionAcquired: ");
    paramCall.append(paramConnection);
    logWithTime(paramCall.toString());
  }
  
  public void connectionReleased(Call paramCall, Connection paramConnection)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramConnection, "connection");
    logWithTime("connectionReleased");
  }
  
  public void dnsEnd(Call paramCall, String paramString, List<? extends InetAddress> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramString, "domainName");
    Intrinsics.checkParameterIsNotNull(paramList, "inetAddressList");
    paramCall = new StringBuilder();
    paramCall.append("dnsEnd: ");
    paramCall.append(paramList);
    logWithTime(paramCall.toString());
  }
  
  public void dnsStart(Call paramCall, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramString, "domainName");
    paramCall = new StringBuilder();
    paramCall.append("dnsStart: ");
    paramCall.append(paramString);
    logWithTime(paramCall.toString());
  }
  
  public void proxySelectEnd(Call paramCall, HttpUrl paramHttpUrl, List<? extends Proxy> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramHttpUrl, "url");
    Intrinsics.checkParameterIsNotNull(paramList, "proxies");
    paramCall = new StringBuilder();
    paramCall.append("proxySelectEnd: ");
    paramCall.append(paramList);
    logWithTime(paramCall.toString());
  }
  
  public void proxySelectStart(Call paramCall, HttpUrl paramHttpUrl)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramHttpUrl, "url");
    paramCall = new StringBuilder();
    paramCall.append("proxySelectStart: ");
    paramCall.append(paramHttpUrl);
    logWithTime(paramCall.toString());
  }
  
  public void requestBodyEnd(Call paramCall, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    paramCall = new StringBuilder();
    paramCall.append("requestBodyEnd: byteCount=");
    paramCall.append(paramLong);
    logWithTime(paramCall.toString());
  }
  
  public void requestBodyStart(Call paramCall)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    logWithTime("requestBodyStart");
  }
  
  public void requestFailed(Call paramCall, IOException paramIOException)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramIOException, "ioe");
    paramCall = new StringBuilder();
    paramCall.append("requestFailed: ");
    paramCall.append(paramIOException);
    logWithTime(paramCall.toString());
  }
  
  public void requestHeadersEnd(Call paramCall, Request paramRequest)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramRequest, "request");
    logWithTime("requestHeadersEnd");
  }
  
  public void requestHeadersStart(Call paramCall)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    logWithTime("requestHeadersStart");
  }
  
  public void responseBodyEnd(Call paramCall, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    paramCall = new StringBuilder();
    paramCall.append("responseBodyEnd: byteCount=");
    paramCall.append(paramLong);
    logWithTime(paramCall.toString());
  }
  
  public void responseBodyStart(Call paramCall)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    logWithTime("responseBodyStart");
  }
  
  public void responseFailed(Call paramCall, IOException paramIOException)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramIOException, "ioe");
    paramCall = new StringBuilder();
    paramCall.append("responseFailed: ");
    paramCall.append(paramIOException);
    logWithTime(paramCall.toString());
  }
  
  public void responseHeadersEnd(Call paramCall, Response paramResponse)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    Intrinsics.checkParameterIsNotNull(paramResponse, "response");
    paramCall = new StringBuilder();
    paramCall.append("responseHeadersEnd: ");
    paramCall.append(paramResponse);
    logWithTime(paramCall.toString());
  }
  
  public void responseHeadersStart(Call paramCall)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    logWithTime("responseHeadersStart");
  }
  
  public void secureConnectEnd(Call paramCall, Handshake paramHandshake)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    paramCall = new StringBuilder();
    paramCall.append("secureConnectEnd: ");
    paramCall.append(paramHandshake);
    logWithTime(paramCall.toString());
  }
  
  public void secureConnectStart(Call paramCall)
  {
    Intrinsics.checkParameterIsNotNull(paramCall, "call");
    logWithTime("secureConnectStart");
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\b\026\030\0002\0020\001B\021\b\007\022\b\b\002\020\002\032\0020\003?\006\002\020\004J\020\020\005\032\0020\0062\006\020\007\032\0020\bH\026R\016\020\002\032\0020\003X?\004?\006\002\n\000?\006\t"}, d2={"Lokhttp3/logging/LoggingEventListener$Factory;", "Lokhttp3/EventListener$Factory;", "logger", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "(Lokhttp3/logging/HttpLoggingInterceptor$Logger;)V", "create", "Lokhttp3/EventListener;", "call", "Lokhttp3/Call;", "okhttp-logging-interceptor"}, k=1, mv={1, 1, 16})
  public static class Factory
    implements EventListener.Factory
  {
    private final HttpLoggingInterceptor.Logger logger;
    
    public Factory()
    {
      this(null, 1, null);
    }
    
    public Factory(HttpLoggingInterceptor.Logger paramLogger)
    {
      this.logger = paramLogger;
    }
    
    public EventListener create(Call paramCall)
    {
      Intrinsics.checkParameterIsNotNull(paramCall, "call");
      return (EventListener)new LoggingEventListener(this.logger, null);
    }
  }
}
