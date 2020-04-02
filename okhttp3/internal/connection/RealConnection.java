package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.Socket;
import java.net.SocketException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import okhttp3.Address;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.CertificatePinner.Companion;
import okhttp3.CipherSuite;
import okhttp3.Connection;
import okhttp3.ConnectionSpec;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Handshake.Companion;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Protocol.Companion;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.Route;
import okhttp3.TlsVersion;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http1.Http1ExchangeCodec;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Http2Connection.Builder;
import okhttp3.internal.http2.Http2Connection.Companion;
import okhttp3.internal.http2.Http2Connection.Listener;
import okhttp3.internal.http2.Http2ExchangeCodec;
import okhttp3.internal.http2.Http2Stream;
import okhttp3.internal.http2.Settings;
import okhttp3.internal.http2.StreamResetException;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;
import okhttp3.internal.ws.RealWebSocket.Streams;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\000?\001\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\b\n\000\n\002\020!\n\002\030\002\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\t\n\002\b\005\n\002\020\013\n\002\b\007\n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\007\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\020 \n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\016\n\002\b\005\030\000 w2\0020\0012\0020\002:\001wB\025\022\006\020\003\032\0020\004\022\006\020\005\032\0020\006?\006\002\020\007J\006\0206\032\00207J>\0208\032\002072\006\0209\032\0020\t2\006\020:\032\0020\t2\006\020;\032\0020\t2\006\020<\032\0020\t2\006\020=\032\0020\0352\006\020>\032\0020?2\006\020@\032\0020AJ%\020B\032\002072\006\020C\032\0020D2\006\020E\032\0020\0062\006\020F\032\0020GH\000?\006\002\bHJ(\020I\032\002072\006\0209\032\0020\t2\006\020:\032\0020\t2\006\020>\032\0020?2\006\020@\032\0020AH\002J\020\020J\032\002072\006\020K\032\0020LH\002J0\020M\032\002072\006\0209\032\0020\t2\006\020:\032\0020\t2\006\020;\032\0020\t2\006\020>\032\0020?2\006\020@\032\0020AH\002J*\020N\032\004\030\0010O2\006\020:\032\0020\t2\006\020;\032\0020\t2\006\020P\032\0020O2\006\020Q\032\0020RH\002J\b\020S\032\0020OH\002J(\020T\032\002072\006\020K\032\0020L2\006\020<\032\0020\t2\006\020>\032\0020?2\006\020@\032\0020AH\002J\n\020\022\032\004\030\0010\023H\026J%\020U\032\0020\0352\006\020V\032\0020W2\016\020X\032\n\022\004\022\0020\006\030\0010YH\000?\006\002\bZJ\016\020[\032\0020\0352\006\020\\\032\0020\035J\035\020]\032\0020^2\006\020C\032\0020D2\006\020_\032\0020`H\000?\006\002\baJ\025\020b\032\0020c2\006\020d\032\0020eH\000?\006\002\bfJ\006\020\037\032\00207J\006\020 \032\00207J\030\020g\032\002072\006\020h\032\0020\0252\006\020i\032\0020jH\026J\020\020k\032\002072\006\020l\032\0020mH\026J\b\020$\032\0020%H\026J\b\020\005\032\0020\006H\026J\026\020n\032\0020\0352\f\020o\032\b\022\004\022\0020\0060YH\002J\b\0200\032\0020'H\026J\020\020p\032\002072\006\020<\032\0020\tH\002J\016\020q\032\0020\0352\006\020Q\032\0020RJ\b\020r\032\0020sH\026J\037\020t\032\002072\006\020>\032\0020\r2\b\020u\032\004\030\0010GH\000?\006\002\bvR\016\020\b\032\0020\tX?\016?\006\002\n\000R\035\020\n\032\016\022\n\022\b\022\004\022\0020\r0\f0\013?\006\b\n\000\032\004\b\016\020\017R\021\020\003\032\0020\004?\006\b\n\000\032\004\b\020\020\021R\020\020\022\032\004\030\0010\023X?\016?\006\002\n\000R\020\020\024\032\004\030\0010\025X?\016?\006\002\n\000R\032\020\026\032\0020\027X?\016?\006\016\n\000\032\004\b\030\020\031\"\004\b\032\020\033R\021\020\034\032\0020\0358F?\006\006\032\004\b\034\020\036R\016\020\037\032\0020\035X?\016?\006\002\n\000R\032\020 \032\0020\035X?\016?\006\016\n\000\032\004\b!\020\036\"\004\b\"\020#R\020\020$\032\004\030\0010%X?\016?\006\002\n\000R\020\020&\032\004\030\0010'X?\016?\006\002\n\000R\016\020(\032\0020\tX?\016?\006\002\n\000R\016\020\005\032\0020\006X?\004?\006\002\n\000R\032\020)\032\0020\tX?\016?\006\016\n\000\032\004\b*\020+\"\004\b,\020-R\020\020.\032\004\030\0010/X?\016?\006\002\n\000R\020\0200\032\004\030\0010'X?\016?\006\002\n\000R\020\0201\032\004\030\00102X?\016?\006\002\n\000R\032\0203\032\0020\tX?\016?\006\016\n\000\032\004\b4\020+\"\004\b5\020-?\006x"}, d2={"Lokhttp3/internal/connection/RealConnection;", "Lokhttp3/internal/http2/Http2Connection$Listener;", "Lokhttp3/Connection;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "route", "Lokhttp3/Route;", "(Lokhttp3/internal/connection/RealConnectionPool;Lokhttp3/Route;)V", "allocationLimit", "", "calls", "", "Ljava/lang/ref/Reference;", "Lokhttp3/internal/connection/RealCall;", "getCalls", "()Ljava/util/List;", "getConnectionPool", "()Lokhttp3/internal/connection/RealConnectionPool;", "handshake", "Lokhttp3/Handshake;", "http2Connection", "Lokhttp3/internal/http2/Http2Connection;", "idleAtNs", "", "getIdleAtNs$okhttp", "()J", "setIdleAtNs$okhttp", "(J)V", "isMultiplexed", "", "()Z", "noCoalescedConnections", "noNewExchanges", "getNoNewExchanges", "setNoNewExchanges", "(Z)V", "protocol", "Lokhttp3/Protocol;", "rawSocket", "Ljava/net/Socket;", "refusedStreamCount", "routeFailureCount", "getRouteFailureCount$okhttp", "()I", "setRouteFailureCount$okhttp", "(I)V", "sink", "Lokio/BufferedSink;", "socket", "source", "Lokio/BufferedSource;", "successCount", "getSuccessCount$okhttp", "setSuccessCount$okhttp", "cancel", "", "connect", "connectTimeout", "readTimeout", "writeTimeout", "pingIntervalMillis", "connectionRetryEnabled", "call", "Lokhttp3/Call;", "eventListener", "Lokhttp3/EventListener;", "connectFailed", "client", "Lokhttp3/OkHttpClient;", "failedRoute", "failure", "Ljava/io/IOException;", "connectFailed$okhttp", "connectSocket", "connectTls", "connectionSpecSelector", "Lokhttp3/internal/connection/ConnectionSpecSelector;", "connectTunnel", "createTunnel", "Lokhttp3/Request;", "tunnelRequest", "url", "Lokhttp3/HttpUrl;", "createTunnelRequest", "establishProtocol", "isEligible", "address", "Lokhttp3/Address;", "routes", "", "isEligible$okhttp", "isHealthy", "doExtensiveChecks", "newCodec", "Lokhttp3/internal/http/ExchangeCodec;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "newCodec$okhttp", "newWebSocketStreams", "Lokhttp3/internal/ws/RealWebSocket$Streams;", "exchange", "Lokhttp3/internal/connection/Exchange;", "newWebSocketStreams$okhttp", "onSettings", "connection", "settings", "Lokhttp3/internal/http2/Settings;", "onStream", "stream", "Lokhttp3/internal/http2/Http2Stream;", "routeMatchesAny", "candidates", "startHttp2", "supportsUrl", "toString", "", "trackFailure", "e", "trackFailure$okhttp", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class RealConnection
  extends Http2Connection.Listener
  implements Connection
{
  public static final Companion Companion = new Companion(null);
  public static final long IDLE_CONNECTION_HEALTHY_NS = 10000000000L;
  private static final int MAX_TUNNEL_ATTEMPTS = 21;
  private static final String NPE_THROW_WITH_NULL = "throw with null exception";
  private int allocationLimit;
  private final List<Reference<RealCall>> calls;
  private final RealConnectionPool connectionPool;
  private Handshake handshake;
  private Http2Connection http2Connection;
  private long idleAtNs;
  private boolean noCoalescedConnections;
  private boolean noNewExchanges;
  private Protocol protocol;
  private Socket rawSocket;
  private int refusedStreamCount;
  private final Route route;
  private int routeFailureCount;
  private BufferedSink sink;
  private Socket socket;
  private BufferedSource source;
  private int successCount;
  
  public RealConnection(RealConnectionPool paramRealConnectionPool, Route paramRoute)
  {
    this.connectionPool = paramRealConnectionPool;
    this.route = paramRoute;
    this.allocationLimit = 1;
    this.calls = ((List)new ArrayList());
    this.idleAtNs = Long.MAX_VALUE;
  }
  
  private final void connectSocket(int paramInt1, int paramInt2, Call paramCall, EventListener paramEventListener)
    throws IOException
  {
    Proxy localProxy = this.route.proxy();
    Object localObject1 = this.route.address();
    Object localObject2 = localProxy.type();
    if (localObject2 != null)
    {
      int i = RealConnection.WhenMappings.$EnumSwitchMapping$0[localObject2.ordinal()];
      if ((i == 1) || (i == 2)) {}
    }
    else
    {
      localObject2 = new Socket(localProxy);
      break label96;
    }
    localObject1 = ((Address)localObject1).socketFactory().createSocket();
    localObject2 = localObject1;
    if (localObject1 == null)
    {
      Intrinsics.throwNpe();
      localObject2 = localObject1;
    }
    label96:
    this.rawSocket = ((Socket)localObject2);
    paramEventListener.connectStart(paramCall, this.route.socketAddress(), localProxy);
    ((Socket)localObject2).setSoTimeout(paramInt2);
    try
    {
      Platform.Companion.get().connectSocket((Socket)localObject2, this.route.socketAddress(), paramInt1);
      try
      {
        this.source = Okio.buffer(Okio.source((Socket)localObject2));
        this.sink = Okio.buffer(Okio.sink((Socket)localObject2));
      }
      catch (NullPointerException paramCall)
      {
        if (Intrinsics.areEqual(paramCall.getMessage(), "throw with null exception")) {
          break label183;
        }
      }
      return;
    }
    catch (ConnectException paramCall)
    {
      label183:
      paramEventListener = new StringBuilder();
      paramEventListener.append("Failed to connect to ");
      paramEventListener.append(this.route.socketAddress());
      paramEventListener = new ConnectException(paramEventListener.toString());
      paramEventListener.initCause((Throwable)paramCall);
      throw ((Throwable)paramEventListener);
    }
    throw ((Throwable)new IOException((Throwable)paramCall));
  }
  
  private final void connectTls(ConnectionSpecSelector paramConnectionSpecSelector)
    throws IOException
  {
    Object localObject1 = this.route.address();
    Object localObject2 = ((Address)localObject1).sslSocketFactory();
    SSLPeerUnverifiedException localSSLPeerUnverifiedException = null;
    Object localObject3 = (SSLSocket)null;
    if (localObject2 == null) {}
    try
    {
      Intrinsics.throwNpe();
      localObject2 = ((SSLSocketFactory)localObject2).createSocket(this.rawSocket, ((Address)localObject1).url().host(), ((Address)localObject1).url().port(), true);
      if (localObject2 != null)
      {
        localObject2 = (SSLSocket)localObject2;
        try
        {
          localObject3 = paramConnectionSpecSelector.configureSecureSocket((SSLSocket)localObject2);
          if (((ConnectionSpec)localObject3).supportsTlsExtensions()) {
            Platform.Companion.get().configureTlsExtensions((SSLSocket)localObject2, ((Address)localObject1).url().host(), ((Address)localObject1).protocols());
          }
          ((SSLSocket)localObject2).startHandshake();
          paramConnectionSpecSelector = ((SSLSocket)localObject2).getSession();
          Object localObject4 = Handshake.Companion;
          Intrinsics.checkExpressionValueIsNotNull(paramConnectionSpecSelector, "sslSocketSession");
          localObject4 = ((Handshake.Companion)localObject4).get(paramConnectionSpecSelector);
          Object localObject5 = ((Address)localObject1).hostnameVerifier();
          if (localObject5 == null) {
            Intrinsics.throwNpe();
          }
          if (!((HostnameVerifier)localObject5).verify(((Address)localObject1).url().host(), paramConnectionSpecSelector))
          {
            paramConnectionSpecSelector = ((Handshake)localObject4).peerCertificates();
            if ((((Collection)paramConnectionSpecSelector).isEmpty() ^ true))
            {
              paramConnectionSpecSelector = paramConnectionSpecSelector.get(0);
              if (paramConnectionSpecSelector == null)
              {
                paramConnectionSpecSelector = new kotlin/TypeCastException;
                paramConnectionSpecSelector.<init>("null cannot be cast to non-null type java.security.cert.X509Certificate");
                throw paramConnectionSpecSelector;
              }
              localObject3 = (X509Certificate)paramConnectionSpecSelector;
              localSSLPeerUnverifiedException = new javax/net/ssl/SSLPeerUnverifiedException;
              paramConnectionSpecSelector = new java/lang/StringBuilder;
              paramConnectionSpecSelector.<init>();
              paramConnectionSpecSelector.append("\n              |Hostname ");
              paramConnectionSpecSelector.append(((Address)localObject1).url().host());
              paramConnectionSpecSelector.append(" not verified:\n              |    certificate: ");
              paramConnectionSpecSelector.append(CertificatePinner.Companion.pin((Certificate)localObject3));
              paramConnectionSpecSelector.append("\n              |    DN: ");
              localObject1 = ((X509Certificate)localObject3).getSubjectDN();
              Intrinsics.checkExpressionValueIsNotNull(localObject1, "cert.subjectDN");
              paramConnectionSpecSelector.append(((Principal)localObject1).getName());
              paramConnectionSpecSelector.append("\n              |    subjectAltNames: ");
              paramConnectionSpecSelector.append(OkHostnameVerifier.INSTANCE.allSubjectAltNames((X509Certificate)localObject3));
              paramConnectionSpecSelector.append("\n              ");
              localSSLPeerUnverifiedException.<init>(StringsKt.trimMargin$default(paramConnectionSpecSelector.toString(), null, 1, null));
              throw ((Throwable)localSSLPeerUnverifiedException);
            }
            localSSLPeerUnverifiedException = new javax/net/ssl/SSLPeerUnverifiedException;
            paramConnectionSpecSelector = new java/lang/StringBuilder;
            paramConnectionSpecSelector.<init>();
            paramConnectionSpecSelector.append("Hostname ");
            paramConnectionSpecSelector.append(((Address)localObject1).url().host());
            paramConnectionSpecSelector.append(" not verified (no certificates)");
            localSSLPeerUnverifiedException.<init>(paramConnectionSpecSelector.toString());
            throw ((Throwable)localSSLPeerUnverifiedException);
          }
          paramConnectionSpecSelector = ((Address)localObject1).certificatePinner();
          if (paramConnectionSpecSelector == null) {
            Intrinsics.throwNpe();
          }
          Handshake localHandshake = new okhttp3/Handshake;
          localObject5 = ((Handshake)localObject4).tlsVersion();
          CipherSuite localCipherSuite = ((Handshake)localObject4).cipherSuite();
          List localList = ((Handshake)localObject4).localCertificates();
          Lambda local1 = new okhttp3/internal/connection/RealConnection$connectTls$1;
          local1.<init>(paramConnectionSpecSelector, (Handshake)localObject4, (Address)localObject1);
          localHandshake.<init>((TlsVersion)localObject5, localCipherSuite, localList, (Function0)local1);
          this.handshake = localHandshake;
          localObject1 = ((Address)localObject1).url().host();
          localObject4 = new okhttp3/internal/connection/RealConnection$connectTls$2;
          ((connectTls.2)localObject4).<init>(this);
          paramConnectionSpecSelector.check$okhttp((String)localObject1, (Function0)localObject4);
          paramConnectionSpecSelector = localSSLPeerUnverifiedException;
          if (((ConnectionSpec)localObject3).supportsTlsExtensions()) {
            paramConnectionSpecSelector = Platform.Companion.get().getSelectedProtocol((SSLSocket)localObject2);
          }
          this.socket = ((Socket)localObject2);
          this.source = Okio.buffer(Okio.source((Socket)localObject2));
          this.sink = Okio.buffer(Okio.sink((Socket)localObject2));
          if (paramConnectionSpecSelector != null) {
            paramConnectionSpecSelector = Protocol.Companion.get(paramConnectionSpecSelector);
          } else {
            paramConnectionSpecSelector = Protocol.HTTP_1_1;
          }
          this.protocol = paramConnectionSpecSelector;
          if (localObject2 != null) {
            Platform.Companion.get().afterHandshake((SSLSocket)localObject2);
          }
          return;
        }
        finally
        {
          break label626;
        }
      }
      paramConnectionSpecSelector = new kotlin/TypeCastException;
      paramConnectionSpecSelector.<init>("null cannot be cast to non-null type javax.net.ssl.SSLSocket");
      throw paramConnectionSpecSelector;
    }
    finally
    {
      localObject2 = localObject3;
      label626:
      if (localObject2 != null) {
        Platform.Companion.get().afterHandshake((SSLSocket)localObject2);
      }
      if (localObject2 != null) {
        Util.closeQuietly((Socket)localObject2);
      }
    }
  }
  
  private final void connectTunnel(int paramInt1, int paramInt2, int paramInt3, Call paramCall, EventListener paramEventListener)
    throws IOException
  {
    Request localRequest = createTunnelRequest();
    HttpUrl localHttpUrl = localRequest.url();
    for (int i = 0; i < 21; i++)
    {
      connectSocket(paramInt1, paramInt2, paramCall, paramEventListener);
      localRequest = createTunnel(paramInt2, paramInt3, localRequest, localHttpUrl);
      if (localRequest == null) {
        break;
      }
      Socket localSocket = this.rawSocket;
      if (localSocket != null) {
        Util.closeQuietly(localSocket);
      }
      this.rawSocket = ((Socket)null);
      this.sink = ((BufferedSink)null);
      this.source = ((BufferedSource)null);
      paramEventListener.connectEnd(paramCall, this.route.socketAddress(), this.route.proxy(), null);
    }
  }
  
  private final Request createTunnel(int paramInt1, int paramInt2, Request paramRequest, HttpUrl paramHttpUrl)
    throws IOException
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("CONNECT ");
    ((StringBuilder)localObject1).append(Util.toHostHeader(paramHttpUrl, true));
    ((StringBuilder)localObject1).append(" HTTP/1.1");
    paramHttpUrl = ((StringBuilder)localObject1).toString();
    BufferedSource localBufferedSource;
    BufferedSink localBufferedSink;
    Object localObject2;
    for (;;)
    {
      localBufferedSource = this.source;
      if (localBufferedSource == null) {
        Intrinsics.throwNpe();
      }
      localBufferedSink = this.sink;
      if (localBufferedSink == null) {
        Intrinsics.throwNpe();
      }
      localObject1 = new Http1ExchangeCodec(null, this, localBufferedSource, localBufferedSink);
      localBufferedSource.timeout().timeout(paramInt1, TimeUnit.MILLISECONDS);
      localBufferedSink.timeout().timeout(paramInt2, TimeUnit.MILLISECONDS);
      ((Http1ExchangeCodec)localObject1).writeRequest(paramRequest.headers(), paramHttpUrl);
      ((Http1ExchangeCodec)localObject1).finishRequest();
      localObject2 = ((Http1ExchangeCodec)localObject1).readResponseHeaders(false);
      if (localObject2 == null) {
        Intrinsics.throwNpe();
      }
      localObject2 = ((Response.Builder)localObject2).request(paramRequest).build();
      ((Http1ExchangeCodec)localObject1).skipConnectBody((Response)localObject2);
      int i = ((Response)localObject2).code();
      if (i == 200) {
        break label301;
      }
      if (i != 407) {
        break label260;
      }
      paramRequest = this.route.address().proxyAuthenticator().authenticate(this.route, (Response)localObject2);
      if (paramRequest == null) {
        break;
      }
      if (StringsKt.equals("close", Response.header$default((Response)localObject2, "Connection", null, 2, null), true)) {
        return paramRequest;
      }
    }
    throw ((Throwable)new IOException("Failed to authenticate with proxy"));
    label260:
    paramRequest = new StringBuilder();
    paramRequest.append("Unexpected response code for CONNECT: ");
    paramRequest.append(((Response)localObject2).code());
    throw ((Throwable)new IOException(paramRequest.toString()));
    label301:
    if ((localBufferedSource.getBuffer().exhausted()) && (localBufferedSink.getBuffer().exhausted())) {
      return null;
    }
    throw ((Throwable)new IOException("TLS tunnel buffered too many bytes!"));
  }
  
  private final Request createTunnelRequest()
    throws IOException
  {
    Object localObject1 = new Request.Builder().url(this.route.address().url()).method("CONNECT", null).header("Host", Util.toHostHeader(this.route.address().url(), true)).header("Proxy-Connection", "Keep-Alive").header("User-Agent", "okhttp/4.4.0").build();
    Object localObject2 = new Response.Builder().request((Request)localObject1).protocol(Protocol.HTTP_1_1).code(407).message("Preemptive Authenticate").body(Util.EMPTY_RESPONSE).sentRequestAtMillis(-1L).receivedResponseAtMillis(-1L).header("Proxy-Authenticate", "OkHttp-Preemptive").build();
    localObject2 = this.route.address().proxyAuthenticator().authenticate(this.route, (Response)localObject2);
    if (localObject2 != null) {
      localObject1 = localObject2;
    }
    return localObject1;
  }
  
  private final void establishProtocol(ConnectionSpecSelector paramConnectionSpecSelector, int paramInt, Call paramCall, EventListener paramEventListener)
    throws IOException
  {
    if (this.route.address().sslSocketFactory() == null)
    {
      if (this.route.address().protocols().contains(Protocol.H2_PRIOR_KNOWLEDGE))
      {
        this.socket = this.rawSocket;
        this.protocol = Protocol.H2_PRIOR_KNOWLEDGE;
        startHttp2(paramInt);
        return;
      }
      this.socket = this.rawSocket;
      this.protocol = Protocol.HTTP_1_1;
      return;
    }
    paramEventListener.secureConnectStart(paramCall);
    connectTls(paramConnectionSpecSelector);
    paramEventListener.secureConnectEnd(paramCall, this.handshake);
    if (this.protocol == Protocol.HTTP_2) {
      startHttp2(paramInt);
    }
  }
  
  private final boolean routeMatchesAny(List<Route> paramList)
  {
    paramList = (Iterable)paramList;
    boolean bool1 = paramList instanceof Collection;
    boolean bool2 = true;
    if ((bool1) && (((Collection)paramList).isEmpty())) {}
    int i;
    do
    {
      while (!paramList.hasNext())
      {
        bool2 = false;
        break;
        paramList = paramList.iterator();
      }
      Route localRoute = (Route)paramList.next();
      if ((localRoute.proxy().type() == Proxy.Type.DIRECT) && (this.route.proxy().type() == Proxy.Type.DIRECT) && (Intrinsics.areEqual(this.route.socketAddress(), localRoute.socketAddress()))) {
        i = 1;
      } else {
        i = 0;
      }
    } while (i == 0);
    return bool2;
  }
  
  private final void startHttp2(int paramInt)
    throws IOException
  {
    Object localObject = this.socket;
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    BufferedSource localBufferedSource = this.source;
    if (localBufferedSource == null) {
      Intrinsics.throwNpe();
    }
    BufferedSink localBufferedSink = this.sink;
    if (localBufferedSink == null) {
      Intrinsics.throwNpe();
    }
    ((Socket)localObject).setSoTimeout(0);
    localObject = new Http2Connection.Builder(true, TaskRunner.INSTANCE).socket((Socket)localObject, this.route.address().url().host(), localBufferedSource, localBufferedSink).listener((Http2Connection.Listener)this).pingIntervalMillis(paramInt).build();
    this.http2Connection = ((Http2Connection)localObject);
    this.allocationLimit = Http2Connection.Companion.getDEFAULT_SETTINGS().getMaxConcurrentStreams();
    Http2Connection.start$default((Http2Connection)localObject, false, 1, null);
  }
  
  public final void cancel()
  {
    Socket localSocket = this.rawSocket;
    if (localSocket != null) {
      Util.closeQuietly(localSocket);
    }
  }
  
  /* Error */
  public final void connect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, Call paramCall, EventListener paramEventListener)
  {
    // Byte code:
    //   0: aload 6
    //   2: ldc_w 830
    //   5: invokestatic 171	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   8: aload 7
    //   10: ldc_w 831
    //   13: invokestatic 171	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   16: aload_0
    //   17: getfield 536	okhttp3/internal/connection/RealConnection:protocol	Lokhttp3/Protocol;
    //   20: ifnonnull +9 -> 29
    //   23: iconst_1
    //   24: istore 8
    //   26: goto +6 -> 32
    //   29: iconst_0
    //   30: istore 8
    //   32: iload 8
    //   34: ifeq +538 -> 572
    //   37: aconst_null
    //   38: checkcast 833	okhttp3/internal/connection/RouteException
    //   41: astore 9
    //   43: aload_0
    //   44: getfield 178	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   47: invokevirtual 219	okhttp3/Route:address	()Lokhttp3/Address;
    //   50: invokevirtual 836	okhttp3/Address:connectionSpecs	()Ljava/util/List;
    //   53: astore 10
    //   55: new 364	okhttp3/internal/connection/ConnectionSpecSelector
    //   58: dup
    //   59: aload 10
    //   61: invokespecial 839	okhttp3/internal/connection/ConnectionSpecSelector:<init>	(Ljava/util/List;)V
    //   64: astore 11
    //   66: aload_0
    //   67: getfield 178	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   70: invokevirtual 219	okhttp3/Route:address	()Lokhttp3/Address;
    //   73: invokevirtual 344	okhttp3/Address:sslSocketFactory	()Ljavax/net/ssl/SSLSocketFactory;
    //   76: ifnonnull +133 -> 209
    //   79: aload 10
    //   81: getstatic 843	okhttp3/ConnectionSpec:CLEARTEXT	Lokhttp3/ConnectionSpec;
    //   84: invokeinterface 746 2 0
    //   89: ifeq +96 -> 185
    //   92: aload_0
    //   93: getfield 178	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   96: invokevirtual 219	okhttp3/Route:address	()Lokhttp3/Address;
    //   99: invokevirtual 349	okhttp3/Address:url	()Lokhttp3/HttpUrl;
    //   102: invokevirtual 354	okhttp3/HttpUrl:host	()Ljava/lang/String;
    //   105: astore 10
    //   107: getstatic 276	okhttp3/internal/platform/Platform:Companion	Lokhttp3/internal/platform/Platform$Companion;
    //   110: invokevirtual 282	okhttp3/internal/platform/Platform$Companion:get	()Lokhttp3/internal/platform/Platform;
    //   113: aload 10
    //   115: invokevirtual 847	okhttp3/internal/platform/Platform:isCleartextTrafficPermitted	(Ljava/lang/String;)Z
    //   118: ifeq +6 -> 124
    //   121: goto +109 -> 230
    //   124: new 319	java/lang/StringBuilder
    //   127: dup
    //   128: invokespecial 320	java/lang/StringBuilder:<init>	()V
    //   131: astore 6
    //   133: aload 6
    //   135: ldc_w 849
    //   138: invokevirtual 326	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   141: pop
    //   142: aload 6
    //   144: aload 10
    //   146: invokevirtual 326	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: pop
    //   150: aload 6
    //   152: ldc_w 851
    //   155: invokevirtual 326	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: pop
    //   159: new 833	okhttp3/internal/connection/RouteException
    //   162: dup
    //   163: new 853	java/net/UnknownServiceException
    //   166: dup
    //   167: aload 6
    //   169: invokevirtual 331	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   172: invokespecial 854	java/net/UnknownServiceException:<init>	(Ljava/lang/String;)V
    //   175: checkcast 206	java/io/IOException
    //   178: invokespecial 857	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   181: checkcast 314	java/lang/Throwable
    //   184: athrow
    //   185: new 833	okhttp3/internal/connection/RouteException
    //   188: dup
    //   189: new 853	java/net/UnknownServiceException
    //   192: dup
    //   193: ldc_w 859
    //   196: invokespecial 854	java/net/UnknownServiceException:<init>	(Ljava/lang/String;)V
    //   199: checkcast 206	java/io/IOException
    //   202: invokespecial 857	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   205: checkcast 314	java/lang/Throwable
    //   208: athrow
    //   209: aload_0
    //   210: getfield 178	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   213: invokevirtual 219	okhttp3/Route:address	()Lokhttp3/Address;
    //   216: invokevirtual 376	okhttp3/Address:protocols	()Ljava/util/List;
    //   219: getstatic 742	okhttp3/Protocol:H2_PRIOR_KNOWLEDGE	Lokhttp3/Protocol;
    //   222: invokeinterface 746 2 0
    //   227: ifne +321 -> 548
    //   230: aload_0
    //   231: getfield 178	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   234: invokevirtual 862	okhttp3/Route:requiresTunnel	()Z
    //   237: ifeq +31 -> 268
    //   240: aload_0
    //   241: iload_1
    //   242: iload_2
    //   243: iload_3
    //   244: aload 6
    //   246: aload 7
    //   248: invokespecial 864	okhttp3/internal/connection/RealConnection:connectTunnel	(IIILokhttp3/Call;Lokhttp3/EventListener;)V
    //   251: aload_0
    //   252: getfield 258	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   255: astore 10
    //   257: aload 10
    //   259: ifnonnull +6 -> 265
    //   262: goto +53 -> 315
    //   265: goto +13 -> 278
    //   268: aload_0
    //   269: iload_1
    //   270: iload_2
    //   271: aload 6
    //   273: aload 7
    //   275: invokespecial 557	okhttp3/internal/connection/RealConnection:connectSocket	(IILokhttp3/Call;Lokhttp3/EventListener;)V
    //   278: aload_0
    //   279: aload 11
    //   281: iload 4
    //   283: aload 6
    //   285: aload 7
    //   287: invokespecial 866	okhttp3/internal/connection/RealConnection:establishProtocol	(Lokhttp3/internal/connection/ConnectionSpecSelector;ILokhttp3/Call;Lokhttp3/EventListener;)V
    //   290: aload 7
    //   292: aload 6
    //   294: aload_0
    //   295: getfield 178	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   298: invokevirtual 262	okhttp3/Route:socketAddress	()Ljava/net/InetSocketAddress;
    //   301: aload_0
    //   302: getfield 178	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   305: invokevirtual 216	okhttp3/Route:proxy	()Ljava/net/Proxy;
    //   308: aload_0
    //   309: getfield 536	okhttp3/internal/connection/RealConnection:protocol	Lokhttp3/Protocol;
    //   312: invokevirtual 568	okhttp3/EventListener:connectEnd	(Lokhttp3/Call;Ljava/net/InetSocketAddress;Ljava/net/Proxy;Lokhttp3/Protocol;)V
    //   315: aload_0
    //   316: getfield 178	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   319: invokevirtual 862	okhttp3/Route:requiresTunnel	()Z
    //   322: ifeq +37 -> 359
    //   325: aload_0
    //   326: getfield 258	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   329: ifnull +6 -> 335
    //   332: goto +27 -> 359
    //   335: new 833	okhttp3/internal/connection/RouteException
    //   338: dup
    //   339: new 868	java/net/ProtocolException
    //   342: dup
    //   343: ldc_w 870
    //   346: invokespecial 871	java/net/ProtocolException:<init>	(Ljava/lang/String;)V
    //   349: checkcast 206	java/io/IOException
    //   352: invokespecial 857	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   355: checkcast 314	java/lang/Throwable
    //   358: athrow
    //   359: aload_0
    //   360: invokestatic 876	java/lang/System:nanoTime	()J
    //   363: putfield 191	okhttp3/internal/connection/RealConnection:idleAtNs	J
    //   366: return
    //   367: astore 10
    //   369: goto +10 -> 379
    //   372: astore 10
    //   374: goto +5 -> 379
    //   377: astore 10
    //   379: aload_0
    //   380: getfield 199	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   383: astore 12
    //   385: aload 12
    //   387: ifnull +8 -> 395
    //   390: aload 12
    //   392: invokestatic 548	okhttp3/internal/Util:closeQuietly	(Ljava/net/Socket;)V
    //   395: aload_0
    //   396: getfield 258	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   399: astore 12
    //   401: aload 12
    //   403: ifnull +8 -> 411
    //   406: aload 12
    //   408: invokestatic 548	okhttp3/internal/Util:closeQuietly	(Ljava/net/Socket;)V
    //   411: aconst_null
    //   412: checkcast 238	java/net/Socket
    //   415: astore 12
    //   417: aload_0
    //   418: aload 12
    //   420: putfield 199	okhttp3/internal/connection/RealConnection:socket	Ljava/net/Socket;
    //   423: aload_0
    //   424: aload 12
    //   426: putfield 258	okhttp3/internal/connection/RealConnection:rawSocket	Ljava/net/Socket;
    //   429: aload_0
    //   430: aconst_null
    //   431: checkcast 564	okio/BufferedSource
    //   434: putfield 296	okhttp3/internal/connection/RealConnection:source	Lokio/BufferedSource;
    //   437: aload_0
    //   438: aconst_null
    //   439: checkcast 562	okio/BufferedSink
    //   442: putfield 304	okhttp3/internal/connection/RealConnection:sink	Lokio/BufferedSink;
    //   445: aload_0
    //   446: aconst_null
    //   447: checkcast 389	okhttp3/Handshake
    //   450: putfield 195	okhttp3/internal/connection/RealConnection:handshake	Lokhttp3/Handshake;
    //   453: aload_0
    //   454: aconst_null
    //   455: checkcast 523	okhttp3/Protocol
    //   458: putfield 536	okhttp3/internal/connection/RealConnection:protocol	Lokhttp3/Protocol;
    //   461: aload_0
    //   462: aconst_null
    //   463: checkcast 810	okhttp3/internal/http2/Http2Connection
    //   466: putfield 808	okhttp3/internal/connection/RealConnection:http2Connection	Lokhttp3/internal/http2/Http2Connection;
    //   469: aload_0
    //   470: iconst_1
    //   471: putfield 180	okhttp3/internal/connection/RealConnection:allocationLimit	I
    //   474: aload 7
    //   476: aload 6
    //   478: aload_0
    //   479: getfield 178	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   482: invokevirtual 262	okhttp3/Route:socketAddress	()Ljava/net/InetSocketAddress;
    //   485: aload_0
    //   486: getfield 178	okhttp3/internal/connection/RealConnection:route	Lokhttp3/Route;
    //   489: invokevirtual 216	okhttp3/Route:proxy	()Ljava/net/Proxy;
    //   492: aconst_null
    //   493: aload 10
    //   495: invokevirtual 879	okhttp3/EventListener:connectFailed	(Lokhttp3/Call;Ljava/net/InetSocketAddress;Ljava/net/Proxy;Lokhttp3/Protocol;Ljava/io/IOException;)V
    //   498: aload 9
    //   500: ifnonnull +17 -> 517
    //   503: new 833	okhttp3/internal/connection/RouteException
    //   506: dup
    //   507: aload 10
    //   509: invokespecial 857	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   512: astore 9
    //   514: goto +10 -> 524
    //   517: aload 9
    //   519: aload 10
    //   521: invokevirtual 882	okhttp3/internal/connection/RouteException:addConnectException	(Ljava/io/IOException;)V
    //   524: iload 5
    //   526: ifeq +16 -> 542
    //   529: aload 11
    //   531: aload 10
    //   533: invokevirtual 886	okhttp3/internal/connection/ConnectionSpecSelector:connectionFailed	(Ljava/io/IOException;)Z
    //   536: ifeq +6 -> 542
    //   539: goto -309 -> 230
    //   542: aload 9
    //   544: checkcast 314	java/lang/Throwable
    //   547: athrow
    //   548: new 833	okhttp3/internal/connection/RouteException
    //   551: dup
    //   552: new 853	java/net/UnknownServiceException
    //   555: dup
    //   556: ldc_w 888
    //   559: invokespecial 854	java/net/UnknownServiceException:<init>	(Ljava/lang/String;)V
    //   562: checkcast 206	java/io/IOException
    //   565: invokespecial 857	okhttp3/internal/connection/RouteException:<init>	(Ljava/io/IOException;)V
    //   568: checkcast 314	java/lang/Throwable
    //   571: athrow
    //   572: new 890	java/lang/IllegalStateException
    //   575: dup
    //   576: ldc_w 892
    //   579: invokevirtual 895	java/lang/Object:toString	()Ljava/lang/String;
    //   582: invokespecial 896	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   585: checkcast 314	java/lang/Throwable
    //   588: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	589	0	this	RealConnection
    //   0	589	1	paramInt1	int
    //   0	589	2	paramInt2	int
    //   0	589	3	paramInt3	int
    //   0	589	4	paramInt4	int
    //   0	589	5	paramBoolean	boolean
    //   0	589	6	paramCall	Call
    //   0	589	7	paramEventListener	EventListener
    //   24	9	8	i	int
    //   41	502	9	localRouteException	RouteException
    //   53	205	10	localObject	Object
    //   367	1	10	localIOException1	IOException
    //   372	1	10	localIOException2	IOException
    //   377	155	10	localIOException3	IOException
    //   64	466	11	localConnectionSpecSelector	ConnectionSpecSelector
    //   383	42	12	localSocket	Socket
    // Exception table:
    //   from	to	target	type
    //   278	315	367	java/io/IOException
    //   268	278	372	java/io/IOException
    //   230	257	377	java/io/IOException
  }
  
  public final void connectFailed$okhttp(OkHttpClient paramOkHttpClient, Route paramRoute, IOException paramIOException)
  {
    Intrinsics.checkParameterIsNotNull(paramOkHttpClient, "client");
    Intrinsics.checkParameterIsNotNull(paramRoute, "failedRoute");
    Intrinsics.checkParameterIsNotNull(paramIOException, "failure");
    if (paramRoute.proxy().type() != Proxy.Type.DIRECT)
    {
      Address localAddress = paramRoute.address();
      localAddress.proxySelector().connectFailed(localAddress.url().uri(), paramRoute.proxy().address(), paramIOException);
    }
    paramOkHttpClient.getRouteDatabase().failed(paramRoute);
  }
  
  public final List<Reference<RealCall>> getCalls()
  {
    return this.calls;
  }
  
  public final RealConnectionPool getConnectionPool()
  {
    return this.connectionPool;
  }
  
  public final long getIdleAtNs$okhttp()
  {
    return this.idleAtNs;
  }
  
  public final boolean getNoNewExchanges()
  {
    return this.noNewExchanges;
  }
  
  public final int getRouteFailureCount$okhttp()
  {
    return this.routeFailureCount;
  }
  
  public final int getSuccessCount$okhttp()
  {
    return this.successCount;
  }
  
  public Handshake handshake()
  {
    return this.handshake;
  }
  
  public final boolean isEligible$okhttp(Address paramAddress, List<Route> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramAddress, "address");
    if ((this.calls.size() < this.allocationLimit) && (!this.noNewExchanges))
    {
      if (!this.route.address().equalsNonHost$okhttp(paramAddress)) {
        return false;
      }
      if (Intrinsics.areEqual(paramAddress.url().host(), route().address().url().host())) {
        return true;
      }
      if (this.http2Connection == null) {
        return false;
      }
      if ((paramList != null) && (routeMatchesAny(paramList)))
      {
        if (paramAddress.hostnameVerifier() != OkHostnameVerifier.INSTANCE) {
          return false;
        }
        if (!supportsUrl(paramAddress.url())) {
          return false;
        }
      }
    }
    try
    {
      paramList = paramAddress.certificatePinner();
      if (paramList == null) {
        Intrinsics.throwNpe();
      }
      String str = paramAddress.url().host();
      paramAddress = handshake();
      if (paramAddress == null) {
        Intrinsics.throwNpe();
      }
      paramList.check(str, paramAddress.peerCertificates());
      return true;
    }
    catch (SSLPeerUnverifiedException paramAddress)
    {
      for (;;) {}
    }
    return false;
  }
  
  public final boolean isHealthy(boolean paramBoolean)
  {
    long l = System.nanoTime();
    Socket localSocket = this.socket;
    if (localSocket == null) {
      Intrinsics.throwNpe();
    }
    BufferedSource localBufferedSource = this.source;
    if (localBufferedSource == null) {
      Intrinsics.throwNpe();
    }
    if ((!localSocket.isClosed()) && (!localSocket.isInputShutdown()) && (!localSocket.isOutputShutdown()))
    {
      Http2Connection localHttp2Connection = this.http2Connection;
      if (localHttp2Connection != null) {
        return localHttp2Connection.isHealthy(l);
      }
      if ((l - this.idleAtNs >= 10000000000L) && (paramBoolean)) {
        return Util.isHealthy(localSocket, localBufferedSource);
      }
      return true;
    }
    return false;
  }
  
  public final boolean isMultiplexed()
  {
    boolean bool;
    if (this.http2Connection != null) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public final ExchangeCodec newCodec$okhttp(OkHttpClient paramOkHttpClient, RealInterceptorChain paramRealInterceptorChain)
    throws SocketException
  {
    Intrinsics.checkParameterIsNotNull(paramOkHttpClient, "client");
    Intrinsics.checkParameterIsNotNull(paramRealInterceptorChain, "chain");
    Socket localSocket = this.socket;
    if (localSocket == null) {
      Intrinsics.throwNpe();
    }
    BufferedSource localBufferedSource = this.source;
    if (localBufferedSource == null) {
      Intrinsics.throwNpe();
    }
    BufferedSink localBufferedSink = this.sink;
    if (localBufferedSink == null) {
      Intrinsics.throwNpe();
    }
    Http2Connection localHttp2Connection = this.http2Connection;
    if (localHttp2Connection != null)
    {
      paramOkHttpClient = (ExchangeCodec)new Http2ExchangeCodec(paramOkHttpClient, this, paramRealInterceptorChain, localHttp2Connection);
    }
    else
    {
      localSocket.setSoTimeout(paramRealInterceptorChain.readTimeoutMillis());
      localBufferedSource.timeout().timeout(paramRealInterceptorChain.getReadTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
      localBufferedSink.timeout().timeout(paramRealInterceptorChain.getWriteTimeoutMillis$okhttp(), TimeUnit.MILLISECONDS);
      paramOkHttpClient = (ExchangeCodec)new Http1ExchangeCodec(paramOkHttpClient, this, localBufferedSource, localBufferedSink);
    }
    return paramOkHttpClient;
  }
  
  public final RealWebSocket.Streams newWebSocketStreams$okhttp(Exchange paramExchange)
    throws SocketException
  {
    Intrinsics.checkParameterIsNotNull(paramExchange, "exchange");
    Socket localSocket = this.socket;
    if (localSocket == null) {
      Intrinsics.throwNpe();
    }
    final BufferedSource localBufferedSource = this.source;
    if (localBufferedSource == null) {
      Intrinsics.throwNpe();
    }
    final BufferedSink localBufferedSink = this.sink;
    if (localBufferedSink == null) {
      Intrinsics.throwNpe();
    }
    localSocket.setSoTimeout(0);
    noNewExchanges();
    (RealWebSocket.Streams)new RealWebSocket.Streams(paramExchange, localBufferedSource, localBufferedSink)
    {
      public void close()
      {
        this.$exchange.bodyComplete(-1L, true, true, null);
      }
    };
  }
  
  public final void noCoalescedConnections()
  {
    Object localObject1 = this.connectionPool;
    if ((Util.assertionsEnabled) && (Thread.holdsLock(localObject1)))
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("Thread ");
      Thread localThread = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localThread, "Thread.currentThread()");
      ((StringBuilder)???).append(localThread.getName());
      ((StringBuilder)???).append(" MUST NOT hold lock on ");
      ((StringBuilder)???).append(localObject1);
      throw ((Throwable)new AssertionError(((StringBuilder)???).toString()));
    }
    synchronized (this.connectionPool)
    {
      this.noCoalescedConnections = true;
      localObject1 = Unit.INSTANCE;
      return;
    }
  }
  
  public final void noNewExchanges()
  {
    ??? = this.connectionPool;
    Object localObject1;
    if ((Util.assertionsEnabled) && (Thread.holdsLock(???)))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Thread ");
      localObject1 = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localObject1, "Thread.currentThread()");
      localStringBuilder.append(((Thread)localObject1).getName());
      localStringBuilder.append(" MUST NOT hold lock on ");
      localStringBuilder.append(???);
      throw ((Throwable)new AssertionError(localStringBuilder.toString()));
    }
    synchronized (this.connectionPool)
    {
      this.noNewExchanges = true;
      localObject1 = Unit.INSTANCE;
      return;
    }
  }
  
  public void onSettings(Http2Connection arg1, Settings paramSettings)
  {
    Intrinsics.checkParameterIsNotNull(???, "connection");
    Intrinsics.checkParameterIsNotNull(paramSettings, "settings");
    synchronized (this.connectionPool)
    {
      this.allocationLimit = paramSettings.getMaxConcurrentStreams();
      paramSettings = Unit.INSTANCE;
      return;
    }
  }
  
  public void onStream(Http2Stream paramHttp2Stream)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramHttp2Stream, "stream");
    paramHttp2Stream.close(ErrorCode.REFUSED_STREAM, null);
  }
  
  public Protocol protocol()
  {
    Protocol localProtocol = this.protocol;
    if (localProtocol == null) {
      Intrinsics.throwNpe();
    }
    return localProtocol;
  }
  
  public Route route()
  {
    return this.route;
  }
  
  public final void setIdleAtNs$okhttp(long paramLong)
  {
    this.idleAtNs = paramLong;
  }
  
  public final void setNoNewExchanges(boolean paramBoolean)
  {
    this.noNewExchanges = paramBoolean;
  }
  
  public final void setRouteFailureCount$okhttp(int paramInt)
  {
    this.routeFailureCount = paramInt;
  }
  
  public final void setSuccessCount$okhttp(int paramInt)
  {
    this.successCount = paramInt;
  }
  
  public Socket socket()
  {
    Socket localSocket = this.socket;
    if (localSocket == null) {
      Intrinsics.throwNpe();
    }
    return localSocket;
  }
  
  public final boolean supportsUrl(HttpUrl paramHttpUrl)
  {
    Intrinsics.checkParameterIsNotNull(paramHttpUrl, "url");
    Object localObject1 = this.route.address().url();
    int i = paramHttpUrl.port();
    int j = ((HttpUrl)localObject1).port();
    boolean bool1 = false;
    if (i != j) {
      return false;
    }
    if (Intrinsics.areEqual(paramHttpUrl.host(), ((HttpUrl)localObject1).host())) {
      return true;
    }
    boolean bool2 = bool1;
    if (!this.noCoalescedConnections)
    {
      bool2 = bool1;
      if (this.handshake != null)
      {
        localObject1 = OkHostnameVerifier.INSTANCE;
        paramHttpUrl = paramHttpUrl.host();
        Object localObject2 = this.handshake;
        if (localObject2 == null) {
          Intrinsics.throwNpe();
        }
        localObject2 = ((Handshake)localObject2).peerCertificates().get(0);
        if (localObject2 != null)
        {
          bool2 = bool1;
          if (((OkHostnameVerifier)localObject1).verify(paramHttpUrl, (X509Certificate)localObject2)) {
            bool2 = true;
          }
        }
        else
        {
          throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
        }
      }
    }
    return bool2;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Connection{");
    localStringBuilder.append(this.route.address().url().host());
    localStringBuilder.append(':');
    localStringBuilder.append(this.route.address().url().port());
    localStringBuilder.append(',');
    localStringBuilder.append(" proxy=");
    localStringBuilder.append(this.route.proxy());
    localStringBuilder.append(" hostAddress=");
    localStringBuilder.append(this.route.socketAddress());
    localStringBuilder.append(" cipherSuite=");
    Object localObject = this.handshake;
    if (localObject != null)
    {
      localObject = ((Handshake)localObject).cipherSuite();
      if (localObject != null) {}
    }
    else
    {
      localObject = "none";
    }
    localStringBuilder.append(localObject);
    localStringBuilder.append(" protocol=");
    localStringBuilder.append(this.protocol);
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
  
  public final void trackFailure$okhttp(RealCall paramRealCall, IOException paramIOException)
  {
    Intrinsics.checkParameterIsNotNull(paramRealCall, "call");
    ??? = this.connectionPool;
    if ((Util.assertionsEnabled) && (Thread.holdsLock(???)))
    {
      paramIOException = new StringBuilder();
      paramIOException.append("Thread ");
      paramRealCall = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(paramRealCall, "Thread.currentThread()");
      paramIOException.append(paramRealCall.getName());
      paramIOException.append(" MUST NOT hold lock on ");
      paramIOException.append(???);
      throw ((Throwable)new AssertionError(paramIOException.toString()));
    }
    synchronized (this.connectionPool)
    {
      if ((paramIOException instanceof StreamResetException))
      {
        if (((StreamResetException)paramIOException).errorCode == ErrorCode.REFUSED_STREAM)
        {
          int i = this.refusedStreamCount + 1;
          this.refusedStreamCount = i;
          if (i > 1)
          {
            this.noNewExchanges = true;
            this.routeFailureCount += 1;
          }
        }
        else if ((((StreamResetException)paramIOException).errorCode != ErrorCode.CANCEL) || (!paramRealCall.isCanceled()))
        {
          this.noNewExchanges = true;
          this.routeFailureCount += 1;
        }
      }
      else if ((!isMultiplexed()) || ((paramIOException instanceof ConnectionShutdownException)))
      {
        this.noNewExchanges = true;
        if (this.successCount == 0)
        {
          if (paramIOException != null) {
            connectFailed$okhttp(paramRealCall.getClient(), this.route, paramIOException);
          }
          this.routeFailureCount += 1;
        }
      }
      paramRealCall = Unit.INSTANCE;
      return;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0008\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\n\002\020\b\n\000\n\002\020\016\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J&\020\t\032\0020\n2\006\020\013\032\0020\f2\006\020\r\032\0020\0162\006\020\017\032\0020\0202\006\020\021\032\0020\004R\016\020\003\032\0020\004X?T?\006\002\n\000R\016\020\005\032\0020\006X?T?\006\002\n\000R\016\020\007\032\0020\bX?T?\006\002\n\000?\006\022"}, d2={"Lokhttp3/internal/connection/RealConnection$Companion;", "", "()V", "IDLE_CONNECTION_HEALTHY_NS", "", "MAX_TUNNEL_ATTEMPTS", "", "NPE_THROW_WITH_NULL", "", "newTestConnection", "Lokhttp3/internal/connection/RealConnection;", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "route", "Lokhttp3/Route;", "socket", "Ljava/net/Socket;", "idleAtNanos", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final RealConnection newTestConnection(RealConnectionPool paramRealConnectionPool, Route paramRoute, Socket paramSocket, long paramLong)
    {
      Intrinsics.checkParameterIsNotNull(paramRealConnectionPool, "connectionPool");
      Intrinsics.checkParameterIsNotNull(paramRoute, "route");
      Intrinsics.checkParameterIsNotNull(paramSocket, "socket");
      paramRealConnectionPool = new RealConnection(paramRealConnectionPool, paramRoute);
      RealConnection.access$setSocket$p(paramRealConnectionPool, paramSocket);
      paramRealConnectionPool.setIdleAtNs$okhttp(paramLong);
      return paramRealConnectionPool;
    }
  }
}
