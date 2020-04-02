package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealConnection;
import okio.BufferedSink;
import okio.Okio;

@Metadata(bv={1, 0, 3}, d1={"\000\036\n\002\030\002\n\002\030\002\n\000\n\002\020\013\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\020\020\005\032\0020\0062\006\020\007\032\0020\bH\026R\016\020\002\032\0020\003X?\004?\006\002\n\000?\006\t"}, d2={"Lokhttp3/internal/http/CallServerInterceptor;", "Lokhttp3/Interceptor;", "forWebSocket", "", "(Z)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "okhttp"}, k=1, mv={1, 1, 16})
public final class CallServerInterceptor
  implements Interceptor
{
  private final boolean forWebSocket;
  
  public CallServerInterceptor(boolean paramBoolean)
  {
    this.forWebSocket = paramBoolean;
  }
  
  public Response intercept(Interceptor.Chain paramChain)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramChain, "chain");
    paramChain = (RealInterceptorChain)paramChain;
    Object localObject1 = paramChain.getExchange$okhttp();
    if (localObject1 == null) {
      Intrinsics.throwNpe();
    }
    Request localRequest = paramChain.getRequest$okhttp();
    RequestBody localRequestBody = localRequest.body();
    long l = System.currentTimeMillis();
    ((Exchange)localObject1).writeRequestHeaders(localRequest);
    Object localObject2 = null;
    paramChain = (Response.Builder)null;
    Object localObject3;
    if ((HttpMethod.permitsRequestBody(localRequest.method())) && (localRequestBody != null))
    {
      if (StringsKt.equals("100-continue", localRequest.header("Expect"), true))
      {
        ((Exchange)localObject1).flushRequest();
        paramChain = ((Exchange)localObject1).readResponseHeaders(true);
        ((Exchange)localObject1).responseHeadersStart();
        i = 0;
      }
      else
      {
        i = 1;
      }
      if (paramChain == null)
      {
        if (localRequestBody.isDuplex())
        {
          ((Exchange)localObject1).flushRequest();
          localRequestBody.writeTo(Okio.buffer(((Exchange)localObject1).createRequestBody(localRequest, true)));
          localObject3 = paramChain;
          j = i;
        }
        else
        {
          localObject3 = Okio.buffer(((Exchange)localObject1).createRequestBody(localRequest, false));
          localRequestBody.writeTo((BufferedSink)localObject3);
          ((BufferedSink)localObject3).close();
          localObject3 = paramChain;
          j = i;
        }
      }
      else
      {
        ((Exchange)localObject1).noRequestBody();
        localObject3 = paramChain;
        j = i;
        if (!((Exchange)localObject1).getConnection$okhttp().isMultiplexed())
        {
          ((Exchange)localObject1).noNewExchangesOnConnection();
          localObject3 = paramChain;
          j = i;
        }
      }
    }
    else
    {
      ((Exchange)localObject1).noRequestBody();
      j = 1;
      localObject3 = paramChain;
    }
    if ((localRequestBody == null) || (!localRequestBody.isDuplex())) {
      ((Exchange)localObject1).finishRequest();
    }
    paramChain = (Interceptor.Chain)localObject3;
    int i = j;
    if (localObject3 == null)
    {
      localObject3 = ((Exchange)localObject1).readResponseHeaders(false);
      if (localObject3 == null) {
        Intrinsics.throwNpe();
      }
      paramChain = (Interceptor.Chain)localObject3;
      i = j;
      if (j != 0)
      {
        ((Exchange)localObject1).responseHeadersStart();
        i = 0;
        paramChain = (Interceptor.Chain)localObject3;
      }
    }
    paramChain = paramChain.request(localRequest).handshake(((Exchange)localObject1).getConnection$okhttp().handshake()).sentRequestAtMillis(l).receivedResponseAtMillis(System.currentTimeMillis()).build();
    int k = paramChain.code();
    int j = k;
    if (k == 100)
    {
      paramChain = ((Exchange)localObject1).readResponseHeaders(false);
      if (paramChain == null) {
        Intrinsics.throwNpe();
      }
      if (i != 0) {
        ((Exchange)localObject1).responseHeadersStart();
      }
      paramChain = paramChain.request(localRequest).handshake(((Exchange)localObject1).getConnection$okhttp().handshake()).sentRequestAtMillis(l).receivedResponseAtMillis(System.currentTimeMillis()).build();
      j = paramChain.code();
    }
    ((Exchange)localObject1).responseHeadersEnd(paramChain);
    if ((this.forWebSocket) && (j == 101)) {
      paramChain = paramChain.newBuilder().body(Util.EMPTY_RESPONSE).build();
    } else {
      paramChain = paramChain.newBuilder().body(((Exchange)localObject1).openResponseBody(paramChain)).build();
    }
    if ((StringsKt.equals("close", paramChain.request().header("Connection"), true)) || (StringsKt.equals("close", Response.header$default(paramChain, "Connection", null, 2, null), true))) {
      ((Exchange)localObject1).noNewExchangesOnConnection();
    }
    if ((j == 204) || (j == 205))
    {
      localObject3 = paramChain.body();
      if (localObject3 != null) {
        l = ((ResponseBody)localObject3).contentLength();
      } else {
        l = -1L;
      }
      if (l > 0L)
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("HTTP ");
        ((StringBuilder)localObject3).append(j);
        ((StringBuilder)localObject3).append(" had non-zero Content-Length: ");
        localObject1 = paramChain.body();
        paramChain = localObject2;
        if (localObject1 != null) {
          paramChain = Long.valueOf(((ResponseBody)localObject1).contentLength());
        }
        ((StringBuilder)localObject3).append(paramChain);
        throw ((Throwable)new ProtocolException(((StringBuilder)localObject3).toString()));
      }
    }
    return paramChain;
  }
}
