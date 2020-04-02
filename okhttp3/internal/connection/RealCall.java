package okhttp3.internal.connection;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.Connection;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.EventListener;
import okhttp3.EventListener.Factory;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;
import okio.AsyncTimeout;

@Metadata(bv={1, 0, 3}, d1={"\000?\001\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\020\000\n\002\b\004\n\002\030\002\n\002\b\005\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\r\n\002\b\004\n\002\020\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\n\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\007*\001)\030\0002\0020\001:\002`aB\035\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007?\006\002\020\bJ\016\020,\032\0020-2\006\020\016\032\0020\017J\b\020.\032\0020-H\002J\b\020/\032\0020-H\026J\b\0200\032\0020\000H\026J\020\0201\032\002022\006\0203\032\00204H\002J\020\0205\032\0020-2\006\0206\032\00207H\026J\026\0208\032\0020-2\006\0209\032\0020\0052\006\020:\032\0020\007J\b\020;\032\0020<H\026J\025\020=\032\0020-2\006\020>\032\0020\007H\000?\006\002\b?J\r\020@\032\0020<H\000?\006\002\bAJ\025\020B\032\0020\0312\006\020C\032\0020DH\000?\006\002\bEJ\b\020F\032\0020\007H\026J\b\020G\032\0020\007H\026J)\020H\032\002HI\"\n\b\000\020I*\004\030\0010J2\006\020K\032\002HI2\006\020L\032\0020\007H\002?\006\002\020MJ;\020N\032\002HI\"\n\b\000\020I*\004\030\0010J2\006\020\030\032\0020\0312\006\020O\032\0020\0072\006\020P\032\0020\0072\006\020K\032\002HIH\000?\006\004\bQ\020RJ\031\020%\032\004\030\0010J2\b\020K\032\004\030\0010JH\000?\006\002\bSJ\r\020T\032\0020UH\000?\006\002\bVJ\017\020W\032\004\030\0010XH\000?\006\002\bYJ\b\0209\032\0020\005H\026J\006\020Z\032\0020\007J\b\020(\032\0020[H\026J\006\020+\032\0020-J!\020\\\032\002HI\"\n\b\000\020I*\004\030\0010J2\006\020]\032\002HIH\002?\006\002\020^J\b\020_\032\0020UH\002R\020\020\t\032\004\030\0010\nX?\016?\006\002\n\000R\016\020\013\032\0020\007X?\016?\006\002\n\000R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\f\020\rR\034\020\016\032\004\030\0010\017X?\016?\006\016\n\000\032\004\b\020\020\021\"\004\b\022\020\023R\016\020\024\032\0020\025X?\004?\006\002\n\000R\016\020\026\032\0020\027X?\004?\006\002\n\000R\020\020\030\032\004\030\0010\031X?\016?\006\002\n\000R\020\020\032\032\004\030\0010\033X?\016?\006\002\n\000R\016\020\034\032\0020\007X?\016?\006\002\n\000R\016\020\035\032\0020\007X?\016?\006\002\n\000R\016\020\036\032\0020\007X?\016?\006\002\n\000R\021\020\006\032\0020\007?\006\b\n\000\032\004\b\037\020 R\"\020\"\032\004\030\0010\0312\b\020!\032\004\030\0010\031@BX?\016?\006\b\n\000\032\004\b#\020$R\016\020%\032\0020\007X?\016?\006\002\n\000R\021\020\004\032\0020\005?\006\b\n\000\032\004\b&\020'R\020\020(\032\0020)X?\004?\006\004\n\002\020*R\016\020+\032\0020\007X?\016?\006\002\n\000?\006b"}, d2={"Lokhttp3/internal/connection/RealCall;", "Lokhttp3/Call;", "client", "Lokhttp3/OkHttpClient;", "originalRequest", "Lokhttp3/Request;", "forWebSocket", "", "(Lokhttp3/OkHttpClient;Lokhttp3/Request;Z)V", "callStackTrace", "", "canceled", "getClient", "()Lokhttp3/OkHttpClient;", "connection", "Lokhttp3/internal/connection/RealConnection;", "getConnection", "()Lokhttp3/internal/connection/RealConnection;", "setConnection", "(Lokhttp3/internal/connection/RealConnection;)V", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "eventListener", "Lokhttp3/EventListener;", "exchange", "Lokhttp3/internal/connection/Exchange;", "exchangeFinder", "Lokhttp3/internal/connection/ExchangeFinder;", "exchangeRequestDone", "exchangeResponseDone", "executed", "getForWebSocket", "()Z", "<set-?>", "interceptorScopedExchange", "getInterceptorScopedExchange$okhttp", "()Lokhttp3/internal/connection/Exchange;", "noMoreExchanges", "getOriginalRequest", "()Lokhttp3/Request;", "timeout", "okhttp3/internal/connection/RealCall$timeout$1", "Lokhttp3/internal/connection/RealCall$timeout$1;", "timeoutEarlyExit", "acquireConnectionNoEvents", "", "callStart", "cancel", "clone", "createAddress", "Lokhttp3/Address;", "url", "Lokhttp3/HttpUrl;", "enqueue", "responseCallback", "Lokhttp3/Callback;", "enterNetworkInterceptorExchange", "request", "newExchangeFinder", "execute", "Lokhttp3/Response;", "exitNetworkInterceptorExchange", "closeExchange", "exitNetworkInterceptorExchange$okhttp", "getResponseWithInterceptorChain", "getResponseWithInterceptorChain$okhttp", "initExchange", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "initExchange$okhttp", "isCanceled", "isExecuted", "maybeReleaseConnection", "E", "Ljava/io/IOException;", "e", "force", "(Ljava/io/IOException;Z)Ljava/io/IOException;", "messageDone", "requestDone", "responseDone", "messageDone$okhttp", "(Lokhttp3/internal/connection/Exchange;ZZLjava/io/IOException;)Ljava/io/IOException;", "noMoreExchanges$okhttp", "redactedUrl", "", "redactedUrl$okhttp", "releaseConnectionNoEvents", "Ljava/net/Socket;", "releaseConnectionNoEvents$okhttp", "retryAfterFailure", "Lokio/AsyncTimeout;", "timeoutExit", "cause", "(Ljava/io/IOException;)Ljava/io/IOException;", "toLoggableString", "AsyncCall", "CallReference", "okhttp"}, k=1, mv={1, 1, 16})
public final class RealCall
  implements Call
{
  private Object callStackTrace;
  private boolean canceled;
  private final OkHttpClient client;
  private RealConnection connection;
  private final RealConnectionPool connectionPool;
  private final EventListener eventListener;
  private Exchange exchange;
  private ExchangeFinder exchangeFinder;
  private boolean exchangeRequestDone;
  private boolean exchangeResponseDone;
  private boolean executed;
  private final boolean forWebSocket;
  private Exchange interceptorScopedExchange;
  private boolean noMoreExchanges;
  private final Request originalRequest;
  private final timeout.1 timeout;
  private boolean timeoutEarlyExit;
  
  public RealCall(OkHttpClient paramOkHttpClient, Request paramRequest, boolean paramBoolean)
  {
    this.client = paramOkHttpClient;
    this.originalRequest = paramRequest;
    this.forWebSocket = paramBoolean;
    this.connectionPool = paramOkHttpClient.connectionPool().getDelegate$okhttp();
    this.eventListener = this.client.eventListenerFactory().create((Call)this);
    paramOkHttpClient = new AsyncTimeout()
    {
      protected void timedOut()
      {
        this.this$0.cancel();
      }
    };
    paramOkHttpClient.timeout(this.client.callTimeoutMillis(), TimeUnit.MILLISECONDS);
    this.timeout = paramOkHttpClient;
  }
  
  private final void callStart()
  {
    this.callStackTrace = Platform.Companion.get().getStackTraceForCloseable("response.body().close()");
    this.eventListener.callStart((Call)this);
  }
  
  private final Address createAddress(HttpUrl paramHttpUrl)
  {
    SSLSocketFactory localSSLSocketFactory = (SSLSocketFactory)null;
    HostnameVerifier localHostnameVerifier = (HostnameVerifier)null;
    CertificatePinner localCertificatePinner = (CertificatePinner)null;
    if (paramHttpUrl.isHttps())
    {
      localSSLSocketFactory = this.client.sslSocketFactory();
      localHostnameVerifier = this.client.hostnameVerifier();
      localCertificatePinner = this.client.certificatePinner();
    }
    return new Address(paramHttpUrl.host(), paramHttpUrl.port(), this.client.dns(), this.client.socketFactory(), localSSLSocketFactory, localHostnameVerifier, localCertificatePinner, this.client.proxyAuthenticator(), this.client.proxy(), this.client.protocols(), this.client.connectionSpecs(), this.client.proxySelector());
  }
  
  private final <E extends IOException> E maybeReleaseConnection(E paramE, boolean paramBoolean)
  {
    Object localObject1 = new Ref.ObjectRef();
    Object localObject2 = this.connectionPool;
    int i = 0;
    if (paramBoolean) {
      try
      {
        if (this.exchange != null) {
          j = 0;
        }
      }
      finally
      {
        break label293;
      }
    }
    int j = 1;
    if (j != 0)
    {
      ((Ref.ObjectRef)localObject1).element = ((Connection)this.connection);
      if ((this.connection != null) && (this.exchange == null) && ((paramBoolean) || (this.noMoreExchanges))) {
        localObject3 = releaseConnectionNoEvents$okhttp();
      } else {
        localObject3 = null;
      }
      if (this.connection != null) {
        ((Ref.ObjectRef)localObject1).element = ((Connection)null);
      }
      if ((this.noMoreExchanges) && (this.exchange == null)) {
        j = 1;
      } else {
        j = 0;
      }
      Unit localUnit = Unit.INSTANCE;
      if (localObject3 != null) {
        Util.closeQuietly((Socket)localObject3);
      }
      if ((Connection)((Ref.ObjectRef)localObject1).element != null)
      {
        localObject2 = this.eventListener;
        localObject3 = (Call)this;
        localObject1 = (Connection)((Ref.ObjectRef)localObject1).element;
        if (localObject1 == null) {
          Intrinsics.throwNpe();
        }
        ((EventListener)localObject2).connectionReleased((Call)localObject3, (Connection)localObject1);
      }
      Object localObject3 = paramE;
      if (j != 0)
      {
        j = i;
        if (paramE != null) {
          j = 1;
        }
        localObject3 = timeoutExit(paramE);
        if (j != 0)
        {
          paramE = this.eventListener;
          localObject1 = (Call)this;
          if (localObject3 == null) {
            Intrinsics.throwNpe();
          }
          paramE.callFailed((Call)localObject1, (IOException)localObject3);
        }
        else
        {
          this.eventListener.callEnd((Call)this);
        }
      }
      return localObject3;
    }
    paramE = new java/lang/IllegalStateException;
    paramE.<init>("cannot release connection while it is in use".toString());
    throw ((Throwable)paramE);
    label293:
    throw paramE;
  }
  
  private final <E extends IOException> E timeoutExit(E paramE)
  {
    if (this.timeoutEarlyExit) {
      return paramE;
    }
    if (!this.timeout.exit()) {
      return paramE;
    }
    InterruptedIOException localInterruptedIOException = new InterruptedIOException("timeout");
    if (paramE != null) {
      localInterruptedIOException.initCause((Throwable)paramE);
    }
    return (IOException)localInterruptedIOException;
  }
  
  private final String toLoggableString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str;
    if (isCanceled()) {
      str = "canceled ";
    } else {
      str = "";
    }
    localStringBuilder.append(str);
    if (this.forWebSocket) {
      str = "web socket";
    } else {
      str = "call";
    }
    localStringBuilder.append(str);
    localStringBuilder.append(" to ");
    localStringBuilder.append(redactedUrl$okhttp());
    return localStringBuilder.toString();
  }
  
  public final void acquireConnectionNoEvents(RealConnection paramRealConnection)
  {
    Intrinsics.checkParameterIsNotNull(paramRealConnection, "connection");
    RealConnectionPool localRealConnectionPool = this.connectionPool;
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(localRealConnectionPool)))
    {
      paramRealConnection = new StringBuilder();
      paramRealConnection.append("Thread ");
      Thread localThread = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localThread, "Thread.currentThread()");
      paramRealConnection.append(localThread.getName());
      paramRealConnection.append(" MUST hold lock on ");
      paramRealConnection.append(localRealConnectionPool);
      throw ((Throwable)new AssertionError(paramRealConnection.toString()));
    }
    int i;
    if (this.connection == null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      this.connection = paramRealConnection;
      paramRealConnection.getCalls().add(new CallReference(this, this.callStackTrace));
      return;
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  public void cancel()
  {
    synchronized (this.connectionPool)
    {
      boolean bool = this.canceled;
      if (bool) {
        return;
      }
      this.canceled = true;
      Exchange localExchange = this.exchange;
      Object localObject1 = this.exchangeFinder;
      if (localObject1 != null)
      {
        localObject1 = ((ExchangeFinder)localObject1).connectingConnection();
        if (localObject1 != null) {}
      }
      else
      {
        localObject1 = this.connection;
      }
      Unit localUnit = Unit.INSTANCE;
      if (localExchange != null) {
        localExchange.cancel();
      } else if (localObject1 != null) {
        ((RealConnection)localObject1).cancel();
      }
      this.eventListener.canceled((Call)this);
      return;
    }
  }
  
  public RealCall clone()
  {
    return new RealCall(this.client, this.originalRequest, this.forWebSocket);
  }
  
  public void enqueue(Callback paramCallback)
  {
    Intrinsics.checkParameterIsNotNull(paramCallback, "responseCallback");
    try
    {
      if ((this.executed ^ true))
      {
        this.executed = true;
        Unit localUnit = Unit.INSTANCE;
        callStart();
        this.client.dispatcher().enqueue$okhttp(new AsyncCall(paramCallback));
        return;
      }
      paramCallback = new java/lang/IllegalStateException;
      paramCallback.<init>("Already Executed".toString());
      throw ((Throwable)paramCallback);
    }
    finally {}
  }
  
  public final void enterNetworkInterceptorExchange(Request paramRequest, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramRequest, "request");
    Exchange localExchange = this.interceptorScopedExchange;
    int i = 1;
    int j;
    if (localExchange == null) {
      j = 1;
    } else {
      j = 0;
    }
    if (j != 0)
    {
      if (this.exchange == null) {
        j = i;
      } else {
        j = 0;
      }
      if (j != 0)
      {
        if (paramBoolean) {
          this.exchangeFinder = new ExchangeFinder(this.connectionPool, createAddress(paramRequest.url()), this, this.eventListener);
        }
        return;
      }
      throw ((Throwable)new IllegalStateException("cannot make a new request because the previous response is still open: please call response.close()".toString()));
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  public Response execute()
  {
    try
    {
      if ((this.executed ^ true))
      {
        this.executed = true;
        Object localObject1 = Unit.INSTANCE;
        this.timeout.enter();
        callStart();
        try
        {
          this.client.dispatcher().executed$okhttp(this);
          localObject1 = getResponseWithInterceptorChain$okhttp();
          return localObject1;
        }
        finally
        {
          this.client.dispatcher().finished$okhttp(this);
        }
      }
      IllegalStateException localIllegalStateException = new java/lang/IllegalStateException;
      localIllegalStateException.<init>("Already Executed".toString());
      throw ((Throwable)localIllegalStateException);
    }
    finally {}
  }
  
  public final void exitNetworkInterceptorExchange$okhttp(boolean paramBoolean)
  {
    boolean bool = this.noMoreExchanges;
    int i = 1;
    if ((bool ^ true))
    {
      if (paramBoolean)
      {
        Exchange localExchange = this.exchange;
        if (localExchange != null) {
          localExchange.detachWithViolence();
        }
        if (this.exchange != null) {
          i = 0;
        }
        if (i == 0) {
          throw ((Throwable)new IllegalStateException("Check failed.".toString()));
        }
      }
      this.interceptorScopedExchange = ((Exchange)null);
      return;
    }
    throw ((Throwable)new IllegalStateException("released".toString()));
  }
  
  public final OkHttpClient getClient()
  {
    return this.client;
  }
  
  public final RealConnection getConnection()
  {
    return this.connection;
  }
  
  public final boolean getForWebSocket()
  {
    return this.forWebSocket;
  }
  
  public final Exchange getInterceptorScopedExchange$okhttp()
  {
    return this.interceptorScopedExchange;
  }
  
  public final Request getOriginalRequest()
  {
    return this.originalRequest;
  }
  
  /* Error */
  public final Response getResponseWithInterceptorChain$okhttp()
    throws IOException
  {
    // Byte code:
    //   0: new 501	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 502	java/util/ArrayList:<init>	()V
    //   7: checkcast 416	java/util/List
    //   10: astore_1
    //   11: aload_1
    //   12: checkcast 504	java/util/Collection
    //   15: astore_2
    //   16: aload_2
    //   17: aload_0
    //   18: getfield 134	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   21: invokevirtual 507	okhttp3/OkHttpClient:interceptors	()Ljava/util/List;
    //   24: checkcast 509	java/lang/Iterable
    //   27: invokestatic 515	kotlin/collections/CollectionsKt:addAll	(Ljava/util/Collection;Ljava/lang/Iterable;)Z
    //   30: pop
    //   31: aload_2
    //   32: new 517	okhttp3/internal/http/RetryAndFollowUpInterceptor
    //   35: dup
    //   36: aload_0
    //   37: getfield 134	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   40: invokespecial 520	okhttp3/internal/http/RetryAndFollowUpInterceptor:<init>	(Lokhttp3/OkHttpClient;)V
    //   43: invokeinterface 521 2 0
    //   48: pop
    //   49: aload_2
    //   50: new 523	okhttp3/internal/http/BridgeInterceptor
    //   53: dup
    //   54: aload_0
    //   55: getfield 134	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   58: invokevirtual 527	okhttp3/OkHttpClient:cookieJar	()Lokhttp3/CookieJar;
    //   61: invokespecial 530	okhttp3/internal/http/BridgeInterceptor:<init>	(Lokhttp3/CookieJar;)V
    //   64: invokeinterface 521 2 0
    //   69: pop
    //   70: aload_2
    //   71: new 532	okhttp3/internal/cache/CacheInterceptor
    //   74: dup
    //   75: aload_0
    //   76: getfield 134	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   79: invokevirtual 536	okhttp3/OkHttpClient:cache	()Lokhttp3/Cache;
    //   82: invokespecial 539	okhttp3/internal/cache/CacheInterceptor:<init>	(Lokhttp3/Cache;)V
    //   85: invokeinterface 521 2 0
    //   90: pop
    //   91: aload_2
    //   92: getstatic 544	okhttp3/internal/connection/ConnectInterceptor:INSTANCE	Lokhttp3/internal/connection/ConnectInterceptor;
    //   95: invokeinterface 521 2 0
    //   100: pop
    //   101: aload_0
    //   102: getfield 138	okhttp3/internal/connection/RealCall:forWebSocket	Z
    //   105: ifne +18 -> 123
    //   108: aload_2
    //   109: aload_0
    //   110: getfield 134	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   113: invokevirtual 547	okhttp3/OkHttpClient:networkInterceptors	()Ljava/util/List;
    //   116: checkcast 509	java/lang/Iterable
    //   119: invokestatic 515	kotlin/collections/CollectionsKt:addAll	(Ljava/util/Collection;Ljava/lang/Iterable;)Z
    //   122: pop
    //   123: aload_2
    //   124: new 549	okhttp3/internal/http/CallServerInterceptor
    //   127: dup
    //   128: aload_0
    //   129: getfield 138	okhttp3/internal/connection/RealCall:forWebSocket	Z
    //   132: invokespecial 551	okhttp3/internal/http/CallServerInterceptor:<init>	(Z)V
    //   135: invokeinterface 521 2 0
    //   140: pop
    //   141: new 553	okhttp3/internal/http/RealInterceptorChain
    //   144: dup
    //   145: aload_0
    //   146: aload_1
    //   147: iconst_0
    //   148: aconst_null
    //   149: aload_0
    //   150: getfield 136	okhttp3/internal/connection/RealCall:originalRequest	Lokhttp3/Request;
    //   153: aload_0
    //   154: getfield 134	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   157: invokevirtual 556	okhttp3/OkHttpClient:connectTimeoutMillis	()I
    //   160: aload_0
    //   161: getfield 134	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   164: invokevirtual 559	okhttp3/OkHttpClient:readTimeoutMillis	()I
    //   167: aload_0
    //   168: getfield 134	okhttp3/internal/connection/RealCall:client	Lokhttp3/OkHttpClient;
    //   171: invokevirtual 562	okhttp3/OkHttpClient:writeTimeoutMillis	()I
    //   174: invokespecial 565	okhttp3/internal/http/RealInterceptorChain:<init>	(Lokhttp3/internal/connection/RealCall;Ljava/util/List;ILokhttp3/internal/connection/Exchange;Lokhttp3/Request;III)V
    //   177: astore_1
    //   178: iconst_0
    //   179: istore_3
    //   180: iload_3
    //   181: istore 4
    //   183: aload_1
    //   184: aload_0
    //   185: getfield 136	okhttp3/internal/connection/RealCall:originalRequest	Lokhttp3/Request;
    //   188: invokevirtual 569	okhttp3/internal/http/RealInterceptorChain:proceed	(Lokhttp3/Request;)Lokhttp3/Response;
    //   191: astore_1
    //   192: iload_3
    //   193: istore 4
    //   195: aload_0
    //   196: invokevirtual 356	okhttp3/internal/connection/RealCall:isCanceled	()Z
    //   199: istore 5
    //   201: iload 5
    //   203: ifne +11 -> 214
    //   206: aload_0
    //   207: aconst_null
    //   208: invokevirtual 571	okhttp3/internal/connection/RealCall:noMoreExchanges$okhttp	(Ljava/io/IOException;)Ljava/io/IOException;
    //   211: pop
    //   212: aload_1
    //   213: areturn
    //   214: iload_3
    //   215: istore 4
    //   217: aload_1
    //   218: checkcast 573	java/io/Closeable
    //   221: invokestatic 576	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   224: iload_3
    //   225: istore 4
    //   227: new 350	java/io/IOException
    //   230: astore_1
    //   231: iload_3
    //   232: istore 4
    //   234: aload_1
    //   235: ldc_w 578
    //   238: invokespecial 579	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   241: iload_3
    //   242: istore 4
    //   244: aload_1
    //   245: checkcast 333	java/lang/Throwable
    //   248: athrow
    //   249: astore_1
    //   250: goto +49 -> 299
    //   253: astore_1
    //   254: iconst_1
    //   255: istore_3
    //   256: iload_3
    //   257: istore 4
    //   259: aload_0
    //   260: aload_1
    //   261: invokevirtual 571	okhttp3/internal/connection/RealCall:noMoreExchanges$okhttp	(Ljava/io/IOException;)Ljava/io/IOException;
    //   264: astore_1
    //   265: aload_1
    //   266: ifnonnull +25 -> 291
    //   269: iload_3
    //   270: istore 4
    //   272: new 581	kotlin/TypeCastException
    //   275: astore_1
    //   276: iload_3
    //   277: istore 4
    //   279: aload_1
    //   280: ldc_w 583
    //   283: invokespecial 584	kotlin/TypeCastException:<init>	(Ljava/lang/String;)V
    //   286: iload_3
    //   287: istore 4
    //   289: aload_1
    //   290: athrow
    //   291: iload_3
    //   292: istore 4
    //   294: aload_1
    //   295: checkcast 333	java/lang/Throwable
    //   298: athrow
    //   299: iload 4
    //   301: ifne +9 -> 310
    //   304: aload_0
    //   305: aconst_null
    //   306: invokevirtual 571	okhttp3/internal/connection/RealCall:noMoreExchanges$okhttp	(Ljava/io/IOException;)Ljava/io/IOException;
    //   309: pop
    //   310: aload_1
    //   311: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	312	0	this	RealCall
    //   10	235	1	localObject1	Object
    //   249	1	1	localObject2	Object
    //   253	8	1	localIOException	IOException
    //   264	47	1	localObject3	Object
    //   15	109	2	localCollection	java.util.Collection
    //   179	113	3	i	int
    //   181	119	4	j	int
    //   199	3	5	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   183	192	249	finally
    //   195	201	249	finally
    //   217	224	249	finally
    //   227	231	249	finally
    //   234	241	249	finally
    //   244	249	249	finally
    //   259	265	249	finally
    //   272	276	249	finally
    //   279	286	249	finally
    //   289	291	249	finally
    //   294	299	249	finally
    //   183	192	253	java/io/IOException
    //   195	201	253	java/io/IOException
    //   217	224	253	java/io/IOException
    //   227	231	253	java/io/IOException
    //   234	241	253	java/io/IOException
    //   244	249	253	java/io/IOException
  }
  
  public final Exchange initExchange$okhttp(RealInterceptorChain arg1)
  {
    Intrinsics.checkParameterIsNotNull(???, "chain");
    synchronized (this.connectionPool)
    {
      boolean bool = this.noMoreExchanges;
      int i = 1;
      if ((bool ^ true))
      {
        if (this.exchange != null) {
          i = 0;
        }
        if (i != 0)
        {
          Object localObject3 = Unit.INSTANCE;
          ??? = this.exchangeFinder;
          if (??? == null) {
            Intrinsics.throwNpe();
          }
          ??? = ((ExchangeFinder)???).find(this.client, ???);
          ??? = this.eventListener;
          localObject3 = this.exchangeFinder;
          if (localObject3 == null) {
            Intrinsics.throwNpe();
          }
          ??? = new Exchange(this, ???, (ExchangeFinder)localObject3, (ExchangeCodec)???);
          this.interceptorScopedExchange = ((Exchange)???);
          synchronized (this.connectionPool)
          {
            this.exchange = ((Exchange)???);
            this.exchangeRequestDone = false;
            this.exchangeResponseDone = false;
            return ???;
          }
        }
        ??? = new java/lang/IllegalStateException;
        ???.<init>("Check failed.".toString());
        throw ((Throwable)???);
      }
      ??? = new java/lang/IllegalStateException;
      ???.<init>("released".toString());
      throw ((Throwable)???);
    }
  }
  
  public boolean isCanceled()
  {
    synchronized (this.connectionPool)
    {
      boolean bool = this.canceled;
      return bool;
    }
  }
  
  public boolean isExecuted()
  {
    try
    {
      boolean bool = this.executed;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final <E extends IOException> E messageDone$okhttp(Exchange paramExchange, boolean paramBoolean1, boolean paramBoolean2, E paramE)
  {
    Intrinsics.checkParameterIsNotNull(paramExchange, "exchange");
    synchronized (this.connectionPool)
    {
      boolean bool1 = Intrinsics.areEqual(paramExchange, this.exchange);
      boolean bool2 = true;
      if ((bool1 ^ true)) {
        return paramE;
      }
      boolean bool3;
      if (paramBoolean1)
      {
        bool3 = this.exchangeRequestDone ^ true;
        this.exchangeRequestDone = true;
      }
      else
      {
        bool3 = false;
      }
      boolean bool4 = bool3;
      if (paramBoolean2)
      {
        if (!this.exchangeResponseDone) {
          bool3 = true;
        }
        this.exchangeResponseDone = true;
        bool4 = bool3;
      }
      if ((this.exchangeRequestDone) && (this.exchangeResponseDone) && (bool4))
      {
        paramExchange = this.exchange;
        if (paramExchange == null) {
          Intrinsics.throwNpe();
        }
        paramExchange = paramExchange.getConnection$okhttp();
        paramExchange.setSuccessCount$okhttp(paramExchange.getSuccessCount$okhttp() + 1);
        this.exchange = ((Exchange)null);
        bool3 = bool2;
      }
      else
      {
        bool3 = false;
      }
      paramExchange = Unit.INSTANCE;
      paramExchange = paramE;
      if (bool3) {
        paramExchange = maybeReleaseConnection(paramE, false);
      }
      return paramExchange;
    }
  }
  
  public final IOException noMoreExchanges$okhttp(IOException paramIOException)
  {
    synchronized (this.connectionPool)
    {
      this.noMoreExchanges = true;
      Unit localUnit = Unit.INSTANCE;
      return maybeReleaseConnection(paramIOException, false);
    }
  }
  
  public final String redactedUrl$okhttp()
  {
    return this.originalRequest.url().redact();
  }
  
  public final Socket releaseConnectionNoEvents$okhttp()
  {
    RealConnectionPool localRealConnectionPool = this.connectionPool;
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(localRealConnectionPool)))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Thread ");
      Thread localThread = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localThread, "Thread.currentThread()");
      ((StringBuilder)localObject).append(localThread.getName());
      ((StringBuilder)localObject).append(" MUST hold lock on ");
      ((StringBuilder)localObject).append(localRealConnectionPool);
      throw ((Throwable)new AssertionError(((StringBuilder)localObject).toString()));
    }
    Object localObject = this.connection;
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    localObject = ((RealConnection)localObject).getCalls().iterator();
    int i = 0;
    for (int j = 0; ((Iterator)localObject).hasNext(); j++) {
      if (Intrinsics.areEqual((RealCall)((Reference)((Iterator)localObject).next()).get(), (RealCall)this)) {
        break label160;
      }
    }
    j = -1;
    label160:
    if (j != -1) {
      i = 1;
    }
    if (i != 0)
    {
      localObject = this.connection;
      if (localObject == null) {
        Intrinsics.throwNpe();
      }
      ((RealConnection)localObject).getCalls().remove(j);
      this.connection = ((RealConnection)null);
      if (((RealConnection)localObject).getCalls().isEmpty())
      {
        ((RealConnection)localObject).setIdleAtNs$okhttp(System.nanoTime());
        if (this.connectionPool.connectionBecameIdle((RealConnection)localObject)) {
          return ((RealConnection)localObject).socket();
        }
      }
      return null;
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  public Request request()
  {
    return this.originalRequest;
  }
  
  public final boolean retryAfterFailure()
  {
    ExchangeFinder localExchangeFinder = this.exchangeFinder;
    if (localExchangeFinder == null) {
      Intrinsics.throwNpe();
    }
    return localExchangeFinder.retryAfterFailure();
  }
  
  public final void setConnection(RealConnection paramRealConnection)
  {
    this.connection = paramRealConnection;
  }
  
  public AsyncTimeout timeout()
  {
    return (AsyncTimeout)this.timeout;
  }
  
  public final void timeoutEarlyExit()
  {
    if ((this.timeoutEarlyExit ^ true))
    {
      this.timeoutEarlyExit = true;
      this.timeout.exit();
      return;
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000@\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\020\016\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\004\b?\004\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\016\020\026\032\0020\0272\006\020\030\032\0020\031J\022\020\032\032\0020\0272\n\020\033\032\0060\000R\0020\006J\b\020\034\032\0020\027H\026R\021\020\005\032\0020\0068F?\006\006\032\004\b\007\020\bR\036\020\013\032\0020\n2\006\020\t\032\0020\n@BX?\016?\006\b\n\000\032\004\b\f\020\rR\021\020\016\032\0020\0178F?\006\006\032\004\b\020\020\021R\021\020\022\032\0020\0238F?\006\006\032\004\b\024\020\025R\016\020\002\032\0020\003X?\004?\006\002\n\000?\006\035"}, d2={"Lokhttp3/internal/connection/RealCall$AsyncCall;", "Ljava/lang/Runnable;", "responseCallback", "Lokhttp3/Callback;", "(Lokhttp3/internal/connection/RealCall;Lokhttp3/Callback;)V", "call", "Lokhttp3/internal/connection/RealCall;", "getCall", "()Lokhttp3/internal/connection/RealCall;", "<set-?>", "Ljava/util/concurrent/atomic/AtomicInteger;", "callsPerHost", "getCallsPerHost", "()Ljava/util/concurrent/atomic/AtomicInteger;", "host", "", "getHost", "()Ljava/lang/String;", "request", "Lokhttp3/Request;", "getRequest", "()Lokhttp3/Request;", "executeOn", "", "executorService", "Ljava/util/concurrent/ExecutorService;", "reuseCallsPerHostFrom", "other", "run", "okhttp"}, k=1, mv={1, 1, 16})
  public final class AsyncCall
    implements Runnable
  {
    private volatile AtomicInteger callsPerHost;
    private final Callback responseCallback;
    
    public AsyncCall()
    {
      this.responseCallback = localObject;
      this.callsPerHost = new AtomicInteger(0);
    }
    
    /* Error */
    public final void executeOn(java.util.concurrent.ExecutorService paramExecutorService)
    {
      // Byte code:
      //   0: aload_1
      //   1: ldc 79
      //   3: invokestatic 58	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_0
      //   7: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   10: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   13: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   16: astore_2
      //   17: getstatic 95	okhttp3/internal/Util:assertionsEnabled	Z
      //   20: ifeq +75 -> 95
      //   23: aload_2
      //   24: invokestatic 101	java/lang/Thread:holdsLock	(Ljava/lang/Object;)Z
      //   27: ifne +6 -> 33
      //   30: goto +65 -> 95
      //   33: new 103	java/lang/StringBuilder
      //   36: dup
      //   37: invokespecial 104	java/lang/StringBuilder:<init>	()V
      //   40: astore_3
      //   41: aload_3
      //   42: ldc 106
      //   44: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   47: pop
      //   48: invokestatic 114	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   51: astore_1
      //   52: aload_1
      //   53: ldc 116
      //   55: invokestatic 119	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
      //   58: aload_3
      //   59: aload_1
      //   60: invokevirtual 122	java/lang/Thread:getName	()Ljava/lang/String;
      //   63: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   66: pop
      //   67: aload_3
      //   68: ldc 124
      //   70: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   73: pop
      //   74: aload_3
      //   75: aload_2
      //   76: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   79: pop
      //   80: new 129	java/lang/AssertionError
      //   83: dup
      //   84: aload_3
      //   85: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   88: invokespecial 135	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
      //   91: checkcast 137	java/lang/Throwable
      //   94: athrow
      //   95: aload_1
      //   96: aload_0
      //   97: checkcast 6	java/lang/Runnable
      //   100: invokeinterface 143 2 0
      //   105: goto +73 -> 178
      //   108: astore_1
      //   109: goto +70 -> 179
      //   112: astore_1
      //   113: new 145	java/io/InterruptedIOException
      //   116: astore_2
      //   117: aload_2
      //   118: ldc -109
      //   120: invokespecial 150	java/io/InterruptedIOException:<init>	(Ljava/lang/String;)V
      //   123: aload_2
      //   124: aload_1
      //   125: checkcast 137	java/lang/Throwable
      //   128: invokevirtual 154	java/io/InterruptedIOException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
      //   131: pop
      //   132: aload_0
      //   133: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   136: aload_2
      //   137: checkcast 156	java/io/IOException
      //   140: invokevirtual 160	okhttp3/internal/connection/RealCall:noMoreExchanges$okhttp	(Ljava/io/IOException;)Ljava/io/IOException;
      //   143: pop
      //   144: aload_0
      //   145: getfield 65	okhttp3/internal/connection/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   148: aload_0
      //   149: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   152: checkcast 162	okhttp3/Call
      //   155: aload_2
      //   156: checkcast 156	java/io/IOException
      //   159: invokeinterface 168 3 0
      //   164: aload_0
      //   165: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   168: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   171: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   174: aload_0
      //   175: invokevirtual 174	okhttp3/Dispatcher:finished$okhttp	(Lokhttp3/internal/connection/RealCall$AsyncCall;)V
      //   178: return
      //   179: aload_0
      //   180: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   183: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   186: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   189: aload_0
      //   190: invokevirtual 174	okhttp3/Dispatcher:finished$okhttp	(Lokhttp3/internal/connection/RealCall$AsyncCall;)V
      //   193: aload_1
      //   194: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	195	0	this	AsyncCall
      //   0	195	1	paramExecutorService	java.util.concurrent.ExecutorService
      //   16	140	2	localObject	Object
      //   40	45	3	localStringBuilder	StringBuilder
      // Exception table:
      //   from	to	target	type
      //   95	105	108	finally
      //   113	164	108	finally
      //   95	105	112	java/util/concurrent/RejectedExecutionException
    }
    
    public final RealCall getCall()
    {
      return RealCall.this;
    }
    
    public final AtomicInteger getCallsPerHost()
    {
      return this.callsPerHost;
    }
    
    public final String getHost()
    {
      return RealCall.this.getOriginalRequest().url().host();
    }
    
    public final Request getRequest()
    {
      return RealCall.this.getOriginalRequest();
    }
    
    public final void reuseCallsPerHostFrom(AsyncCall paramAsyncCall)
    {
      Intrinsics.checkParameterIsNotNull(paramAsyncCall, "other");
      this.callsPerHost = paramAsyncCall.callsPerHost;
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: new 103	java/lang/StringBuilder
      //   3: dup
      //   4: invokespecial 104	java/lang/StringBuilder:<init>	()V
      //   7: astore_1
      //   8: aload_1
      //   9: ldc -66
      //   11: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   14: pop
      //   15: aload_1
      //   16: aload_0
      //   17: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   20: invokevirtual 193	okhttp3/internal/connection/RealCall:redactedUrl$okhttp	()Ljava/lang/String;
      //   23: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   26: pop
      //   27: aload_1
      //   28: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   31: astore_1
      //   32: invokestatic 114	java/lang/Thread:currentThread	()Ljava/lang/Thread;
      //   35: astore_2
      //   36: aload_2
      //   37: ldc -62
      //   39: invokestatic 119	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
      //   42: aload_2
      //   43: invokevirtual 122	java/lang/Thread:getName	()Ljava/lang/String;
      //   46: astore_3
      //   47: aload_2
      //   48: aload_1
      //   49: invokevirtual 197	java/lang/Thread:setName	(Ljava/lang/String;)V
      //   52: aload_0
      //   53: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   56: invokestatic 201	okhttp3/internal/connection/RealCall:access$getTimeout$p	(Lokhttp3/internal/connection/RealCall;)Lokhttp3/internal/connection/RealCall$timeout$1;
      //   59: invokevirtual 206	okhttp3/internal/connection/RealCall$timeout$1:enter	()V
      //   62: aload_0
      //   63: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   66: invokevirtual 210	okhttp3/internal/connection/RealCall:getResponseWithInterceptorChain$okhttp	()Lokhttp3/Response;
      //   69: astore_1
      //   70: iconst_1
      //   71: istore 4
      //   73: iconst_1
      //   74: istore 5
      //   76: aload_0
      //   77: getfield 65	okhttp3/internal/connection/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   80: aload_0
      //   81: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   84: checkcast 162	okhttp3/Call
      //   87: aload_1
      //   88: invokeinterface 214 3 0
      //   93: aload_0
      //   94: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   97: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   100: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   103: astore_1
      //   104: aload_1
      //   105: aload_0
      //   106: invokevirtual 174	okhttp3/Dispatcher:finished$okhttp	(Lokhttp3/internal/connection/RealCall$AsyncCall;)V
      //   109: goto +198 -> 307
      //   112: astore_1
      //   113: goto +15 -> 128
      //   116: astore_1
      //   117: iload 4
      //   119: istore 5
      //   121: goto +93 -> 214
      //   124: astore_1
      //   125: iconst_0
      //   126: istore 5
      //   128: aload_0
      //   129: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   132: invokevirtual 217	okhttp3/internal/connection/RealCall:cancel	()V
      //   135: iload 5
      //   137: ifne +67 -> 204
      //   140: new 156	java/io/IOException
      //   143: astore 6
      //   145: new 103	java/lang/StringBuilder
      //   148: astore 7
      //   150: aload 7
      //   152: invokespecial 104	java/lang/StringBuilder:<init>	()V
      //   155: aload 7
      //   157: ldc -37
      //   159: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   162: pop
      //   163: aload 7
      //   165: aload_1
      //   166: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   169: pop
      //   170: aload 6
      //   172: aload 7
      //   174: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   177: invokespecial 220	java/io/IOException:<init>	(Ljava/lang/String;)V
      //   180: aload 6
      //   182: aload_1
      //   183: invokevirtual 224	java/io/IOException:addSuppressed	(Ljava/lang/Throwable;)V
      //   186: aload_0
      //   187: getfield 65	okhttp3/internal/connection/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   190: aload_0
      //   191: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   194: checkcast 162	okhttp3/Call
      //   197: aload 6
      //   199: invokeinterface 168 3 0
      //   204: aload_1
      //   205: athrow
      //   206: astore_1
      //   207: goto +106 -> 313
      //   210: astore_1
      //   211: iconst_0
      //   212: istore 5
      //   214: iload 5
      //   216: ifeq +60 -> 276
      //   219: getstatic 230	okhttp3/internal/platform/Platform:Companion	Lokhttp3/internal/platform/Platform$Companion;
      //   222: invokevirtual 236	okhttp3/internal/platform/Platform$Companion:get	()Lokhttp3/internal/platform/Platform;
      //   225: astore 7
      //   227: new 103	java/lang/StringBuilder
      //   230: astore 6
      //   232: aload 6
      //   234: invokespecial 104	java/lang/StringBuilder:<init>	()V
      //   237: aload 6
      //   239: ldc -18
      //   241: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   244: pop
      //   245: aload 6
      //   247: aload_0
      //   248: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   251: invokestatic 242	okhttp3/internal/connection/RealCall:access$toLoggableString	(Lokhttp3/internal/connection/RealCall;)Ljava/lang/String;
      //   254: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   257: pop
      //   258: aload 7
      //   260: aload 6
      //   262: invokevirtual 132	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   265: iconst_4
      //   266: aload_1
      //   267: checkcast 137	java/lang/Throwable
      //   270: invokevirtual 246	okhttp3/internal/platform/Platform:log	(Ljava/lang/String;ILjava/lang/Throwable;)V
      //   273: goto +20 -> 293
      //   276: aload_0
      //   277: getfield 65	okhttp3/internal/connection/RealCall$AsyncCall:responseCallback	Lokhttp3/Callback;
      //   280: aload_0
      //   281: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   284: checkcast 162	okhttp3/Call
      //   287: aload_1
      //   288: invokeinterface 168 3 0
      //   293: aload_0
      //   294: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   297: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   300: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   303: astore_1
      //   304: goto -200 -> 104
      //   307: aload_2
      //   308: aload_3
      //   309: invokevirtual 197	java/lang/Thread:setName	(Ljava/lang/String;)V
      //   312: return
      //   313: aload_0
      //   314: getfield 60	okhttp3/internal/connection/RealCall$AsyncCall:this$0	Lokhttp3/internal/connection/RealCall;
      //   317: invokevirtual 83	okhttp3/internal/connection/RealCall:getClient	()Lokhttp3/OkHttpClient;
      //   320: invokevirtual 89	okhttp3/OkHttpClient:dispatcher	()Lokhttp3/Dispatcher;
      //   323: aload_0
      //   324: invokevirtual 174	okhttp3/Dispatcher:finished$okhttp	(Lokhttp3/internal/connection/RealCall$AsyncCall;)V
      //   327: aload_1
      //   328: athrow
      //   329: astore_1
      //   330: aload_2
      //   331: aload_3
      //   332: invokevirtual 197	java/lang/Thread:setName	(Ljava/lang/String;)V
      //   335: aload_1
      //   336: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	337	0	this	AsyncCall
      //   7	98	1	localObject1	Object
      //   112	1	1	localObject2	Object
      //   116	1	1	localIOException1	IOException
      //   124	81	1	localObject3	Object
      //   206	1	1	localObject4	Object
      //   210	78	1	localIOException2	IOException
      //   303	25	1	localDispatcher	Dispatcher
      //   329	7	1	localObject5	Object
      //   35	296	2	localThread	Thread
      //   46	286	3	str	String
      //   71	47	4	i	int
      //   74	141	5	j	int
      //   143	118	6	localObject6	Object
      //   148	111	7	localObject7	Object
      // Exception table:
      //   from	to	target	type
      //   76	93	112	finally
      //   76	93	116	java/io/IOException
      //   62	70	124	finally
      //   128	135	206	finally
      //   140	204	206	finally
      //   204	206	206	finally
      //   219	273	206	finally
      //   276	293	206	finally
      //   62	70	210	java/io/IOException
      //   52	62	329	finally
      //   93	104	329	finally
      //   104	109	329	finally
      //   293	304	329	finally
      //   313	329	329	finally
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\000\n\002\b\004\b\000\030\0002\b\022\004\022\0020\0020\001B\027\022\006\020\003\032\0020\002\022\b\020\004\032\004\030\0010\005?\006\002\020\006R\023\020\004\032\004\030\0010\005?\006\b\n\000\032\004\b\007\020\b?\006\t"}, d2={"Lokhttp3/internal/connection/RealCall$CallReference;", "Ljava/lang/ref/WeakReference;", "Lokhttp3/internal/connection/RealCall;", "referent", "callStackTrace", "", "(Lokhttp3/internal/connection/RealCall;Ljava/lang/Object;)V", "getCallStackTrace", "()Ljava/lang/Object;", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class CallReference
    extends WeakReference<RealCall>
  {
    private final Object callStackTrace;
    
    public CallReference(RealCall paramRealCall, Object paramObject)
    {
      super();
      this.callStackTrace = paramObject;
    }
    
    public final Object getCallStackTrace()
    {
      return this.callStackTrace;
    }
  }
}
