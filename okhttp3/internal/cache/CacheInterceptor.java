package okhttp3.internal.cache;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.Response.Builder;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\002\030\000 \0172\0020\001:\001\017B\017\022\b\020\002\032\004\030\0010\003?\006\002\020\004J\032\020\007\032\0020\b2\b\020\t\032\004\030\0010\n2\006\020\013\032\0020\bH\002J\020\020\f\032\0020\b2\006\020\r\032\0020\016H\026R\026\020\002\032\004\030\0010\003X?\004?\006\b\n\000\032\004\b\005\020\006?\006\020"}, d2={"Lokhttp3/internal/cache/CacheInterceptor;", "Lokhttp3/Interceptor;", "cache", "Lokhttp3/Cache;", "(Lokhttp3/Cache;)V", "getCache$okhttp", "()Lokhttp3/Cache;", "cacheWritingResponse", "Lokhttp3/Response;", "cacheRequest", "Lokhttp3/internal/cache/CacheRequest;", "response", "intercept", "chain", "Lokhttp3/Interceptor$Chain;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class CacheInterceptor
  implements Interceptor
{
  public static final Companion Companion = new Companion(null);
  private final Cache cache;
  
  public CacheInterceptor(Cache paramCache)
  {
    this.cache = paramCache;
  }
  
  private final Response cacheWritingResponse(final CacheRequest paramCacheRequest, Response paramResponse)
    throws IOException
  {
    if (paramCacheRequest == null) {
      return paramResponse;
    }
    Object localObject = paramCacheRequest.body();
    ResponseBody localResponseBody = paramResponse.body();
    if (localResponseBody == null) {
      Intrinsics.throwNpe();
    }
    localObject = new Source()
    {
      private boolean cacheRequestClosed;
      
      public void close()
        throws IOException
      {
        if ((!this.cacheRequestClosed) && (!Util.discard(this, 100, TimeUnit.MILLISECONDS)))
        {
          this.cacheRequestClosed = true;
          paramCacheRequest.abort();
        }
        this.$source.close();
      }
      
      public final boolean getCacheRequestClosed()
      {
        return this.cacheRequestClosed;
      }
      
      public long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousBuffer, "sink");
        try
        {
          paramAnonymousLong = this.$source.read(paramAnonymousBuffer, paramAnonymousLong);
          if (paramAnonymousLong == -1L)
          {
            if (!this.cacheRequestClosed)
            {
              this.cacheRequestClosed = true;
              this.$cacheBody.close();
            }
            return -1L;
          }
          paramAnonymousBuffer.copyTo(this.$cacheBody.getBuffer(), paramAnonymousBuffer.size() - paramAnonymousLong, paramAnonymousLong);
          this.$cacheBody.emitCompleteSegments();
          return paramAnonymousLong;
        }
        catch (IOException paramAnonymousBuffer)
        {
          if (!this.cacheRequestClosed)
          {
            this.cacheRequestClosed = true;
            paramCacheRequest.abort();
          }
          throw ((Throwable)paramAnonymousBuffer);
        }
      }
      
      public final void setCacheRequestClosed(boolean paramAnonymousBoolean)
      {
        this.cacheRequestClosed = paramAnonymousBoolean;
      }
      
      public Timeout timeout()
      {
        return this.$source.timeout();
      }
    };
    paramCacheRequest = Response.header$default(paramResponse, "Content-Type", null, 2, null);
    long l = paramResponse.body().contentLength();
    return paramResponse.newBuilder().body((ResponseBody)new RealResponseBody(paramCacheRequest, l, Okio.buffer((Source)localObject))).build();
  }
  
  public final Cache getCache$okhttp()
  {
    return this.cache;
  }
  
  /* Error */
  public Response intercept(okhttp3.Interceptor.Chain paramChain)
    throws IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 122
    //   3: invokestatic 126	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_0
    //   7: getfield 52	okhttp3/internal/cache/CacheInterceptor:cache	Lokhttp3/Cache;
    //   10: astore_2
    //   11: aload_2
    //   12: ifnull +17 -> 29
    //   15: aload_2
    //   16: aload_1
    //   17: invokeinterface 132 1 0
    //   22: invokevirtual 138	okhttp3/Cache:get$okhttp	(Lokhttp3/Request;)Lokhttp3/Response;
    //   25: astore_2
    //   26: goto +5 -> 31
    //   29: aconst_null
    //   30: astore_2
    //   31: new 140	okhttp3/internal/cache/CacheStrategy$Factory
    //   34: dup
    //   35: invokestatic 145	java/lang/System:currentTimeMillis	()J
    //   38: aload_1
    //   39: invokeinterface 132 1 0
    //   44: aload_2
    //   45: invokespecial 148	okhttp3/internal/cache/CacheStrategy$Factory:<init>	(JLokhttp3/Request;Lokhttp3/Response;)V
    //   48: invokevirtual 152	okhttp3/internal/cache/CacheStrategy$Factory:compute	()Lokhttp3/internal/cache/CacheStrategy;
    //   51: astore_3
    //   52: aload_3
    //   53: invokevirtual 157	okhttp3/internal/cache/CacheStrategy:getNetworkRequest	()Lokhttp3/Request;
    //   56: astore 4
    //   58: aload_3
    //   59: invokevirtual 160	okhttp3/internal/cache/CacheStrategy:getCacheResponse	()Lokhttp3/Response;
    //   62: astore 5
    //   64: aload_0
    //   65: getfield 52	okhttp3/internal/cache/CacheInterceptor:cache	Lokhttp3/Cache;
    //   68: astore 6
    //   70: aload 6
    //   72: ifnull +9 -> 81
    //   75: aload 6
    //   77: aload_3
    //   78: invokevirtual 164	okhttp3/Cache:trackResponse$okhttp	(Lokhttp3/internal/cache/CacheStrategy;)V
    //   81: aload_2
    //   82: ifnull +24 -> 106
    //   85: aload 5
    //   87: ifnonnull +19 -> 106
    //   90: aload_2
    //   91: invokevirtual 66	okhttp3/Response:body	()Lokhttp3/ResponseBody;
    //   94: astore_3
    //   95: aload_3
    //   96: ifnull +10 -> 106
    //   99: aload_3
    //   100: checkcast 166	java/io/Closeable
    //   103: invokestatic 172	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   106: aload 4
    //   108: ifnonnull +63 -> 171
    //   111: aload 5
    //   113: ifnonnull +58 -> 171
    //   116: new 112	okhttp3/Response$Builder
    //   119: dup
    //   120: invokespecial 173	okhttp3/Response$Builder:<init>	()V
    //   123: aload_1
    //   124: invokeinterface 132 1 0
    //   129: invokevirtual 176	okhttp3/Response$Builder:request	(Lokhttp3/Request;)Lokhttp3/Response$Builder;
    //   132: getstatic 182	okhttp3/Protocol:HTTP_1_1	Lokhttp3/Protocol;
    //   135: invokevirtual 186	okhttp3/Response$Builder:protocol	(Lokhttp3/Protocol;)Lokhttp3/Response$Builder;
    //   138: sipush 504
    //   141: invokevirtual 190	okhttp3/Response$Builder:code	(I)Lokhttp3/Response$Builder;
    //   144: ldc -64
    //   146: invokevirtual 196	okhttp3/Response$Builder:message	(Ljava/lang/String;)Lokhttp3/Response$Builder;
    //   149: getstatic 200	okhttp3/internal/Util:EMPTY_RESPONSE	Lokhttp3/ResponseBody;
    //   152: invokevirtual 115	okhttp3/Response$Builder:body	(Lokhttp3/ResponseBody;)Lokhttp3/Response$Builder;
    //   155: ldc2_w 201
    //   158: invokevirtual 206	okhttp3/Response$Builder:sentRequestAtMillis	(J)Lokhttp3/Response$Builder;
    //   161: invokestatic 145	java/lang/System:currentTimeMillis	()J
    //   164: invokevirtual 209	okhttp3/Response$Builder:receivedResponseAtMillis	(J)Lokhttp3/Response$Builder;
    //   167: invokevirtual 119	okhttp3/Response$Builder:build	()Lokhttp3/Response;
    //   170: areturn
    //   171: aload 4
    //   173: ifnonnull +31 -> 204
    //   176: aload 5
    //   178: ifnonnull +6 -> 184
    //   181: invokestatic 71	kotlin/jvm/internal/Intrinsics:throwNpe	()V
    //   184: aload 5
    //   186: invokevirtual 100	okhttp3/Response:newBuilder	()Lokhttp3/Response$Builder;
    //   189: getstatic 47	okhttp3/internal/cache/CacheInterceptor:Companion	Lokhttp3/internal/cache/CacheInterceptor$Companion;
    //   192: aload 5
    //   194: invokestatic 213	okhttp3/internal/cache/CacheInterceptor$Companion:access$stripBody	(Lokhttp3/internal/cache/CacheInterceptor$Companion;Lokhttp3/Response;)Lokhttp3/Response;
    //   197: invokevirtual 217	okhttp3/Response$Builder:cacheResponse	(Lokhttp3/Response;)Lokhttp3/Response$Builder;
    //   200: invokevirtual 119	okhttp3/Response$Builder:build	()Lokhttp3/Response;
    //   203: areturn
    //   204: aconst_null
    //   205: checkcast 63	okhttp3/Response
    //   208: astore_3
    //   209: aload_1
    //   210: aload 4
    //   212: invokeinterface 220 2 0
    //   217: astore_1
    //   218: aload_1
    //   219: ifnonnull +23 -> 242
    //   222: aload_2
    //   223: ifnull +19 -> 242
    //   226: aload_2
    //   227: invokevirtual 66	okhttp3/Response:body	()Lokhttp3/ResponseBody;
    //   230: astore_2
    //   231: aload_2
    //   232: ifnull +10 -> 242
    //   235: aload_2
    //   236: checkcast 166	java/io/Closeable
    //   239: invokestatic 172	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   242: aload 5
    //   244: ifnull +140 -> 384
    //   247: aload_1
    //   248: ifnull +119 -> 367
    //   251: aload_1
    //   252: invokevirtual 223	okhttp3/Response:code	()I
    //   255: sipush 304
    //   258: if_icmpne +109 -> 367
    //   261: aload 5
    //   263: invokevirtual 100	okhttp3/Response:newBuilder	()Lokhttp3/Response$Builder;
    //   266: getstatic 47	okhttp3/internal/cache/CacheInterceptor:Companion	Lokhttp3/internal/cache/CacheInterceptor$Companion;
    //   269: aload 5
    //   271: invokevirtual 227	okhttp3/Response:headers	()Lokhttp3/Headers;
    //   274: aload_1
    //   275: invokevirtual 227	okhttp3/Response:headers	()Lokhttp3/Headers;
    //   278: invokestatic 231	okhttp3/internal/cache/CacheInterceptor$Companion:access$combine	(Lokhttp3/internal/cache/CacheInterceptor$Companion;Lokhttp3/Headers;Lokhttp3/Headers;)Lokhttp3/Headers;
    //   281: invokevirtual 234	okhttp3/Response$Builder:headers	(Lokhttp3/Headers;)Lokhttp3/Response$Builder;
    //   284: aload_1
    //   285: invokevirtual 236	okhttp3/Response:sentRequestAtMillis	()J
    //   288: invokevirtual 206	okhttp3/Response$Builder:sentRequestAtMillis	(J)Lokhttp3/Response$Builder;
    //   291: aload_1
    //   292: invokevirtual 238	okhttp3/Response:receivedResponseAtMillis	()J
    //   295: invokevirtual 209	okhttp3/Response$Builder:receivedResponseAtMillis	(J)Lokhttp3/Response$Builder;
    //   298: getstatic 47	okhttp3/internal/cache/CacheInterceptor:Companion	Lokhttp3/internal/cache/CacheInterceptor$Companion;
    //   301: aload 5
    //   303: invokestatic 213	okhttp3/internal/cache/CacheInterceptor$Companion:access$stripBody	(Lokhttp3/internal/cache/CacheInterceptor$Companion;Lokhttp3/Response;)Lokhttp3/Response;
    //   306: invokevirtual 217	okhttp3/Response$Builder:cacheResponse	(Lokhttp3/Response;)Lokhttp3/Response$Builder;
    //   309: getstatic 47	okhttp3/internal/cache/CacheInterceptor:Companion	Lokhttp3/internal/cache/CacheInterceptor$Companion;
    //   312: aload_1
    //   313: invokestatic 213	okhttp3/internal/cache/CacheInterceptor$Companion:access$stripBody	(Lokhttp3/internal/cache/CacheInterceptor$Companion;Lokhttp3/Response;)Lokhttp3/Response;
    //   316: invokevirtual 241	okhttp3/Response$Builder:networkResponse	(Lokhttp3/Response;)Lokhttp3/Response$Builder;
    //   319: invokevirtual 119	okhttp3/Response$Builder:build	()Lokhttp3/Response;
    //   322: astore_2
    //   323: aload_1
    //   324: invokevirtual 66	okhttp3/Response:body	()Lokhttp3/ResponseBody;
    //   327: astore_1
    //   328: aload_1
    //   329: ifnonnull +6 -> 335
    //   332: invokestatic 71	kotlin/jvm/internal/Intrinsics:throwNpe	()V
    //   335: aload_1
    //   336: invokevirtual 244	okhttp3/ResponseBody:close	()V
    //   339: aload_0
    //   340: getfield 52	okhttp3/internal/cache/CacheInterceptor:cache	Lokhttp3/Cache;
    //   343: astore_1
    //   344: aload_1
    //   345: ifnonnull +6 -> 351
    //   348: invokestatic 71	kotlin/jvm/internal/Intrinsics:throwNpe	()V
    //   351: aload_1
    //   352: invokevirtual 247	okhttp3/Cache:trackConditionalCacheHit$okhttp	()V
    //   355: aload_0
    //   356: getfield 52	okhttp3/internal/cache/CacheInterceptor:cache	Lokhttp3/Cache;
    //   359: aload 5
    //   361: aload_2
    //   362: invokevirtual 251	okhttp3/Cache:update$okhttp	(Lokhttp3/Response;Lokhttp3/Response;)V
    //   365: aload_2
    //   366: areturn
    //   367: aload 5
    //   369: invokevirtual 66	okhttp3/Response:body	()Lokhttp3/ResponseBody;
    //   372: astore_2
    //   373: aload_2
    //   374: ifnull +10 -> 384
    //   377: aload_2
    //   378: checkcast 166	java/io/Closeable
    //   381: invokestatic 172	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   384: aload_1
    //   385: ifnonnull +6 -> 391
    //   388: invokestatic 71	kotlin/jvm/internal/Intrinsics:throwNpe	()V
    //   391: aload_1
    //   392: invokevirtual 100	okhttp3/Response:newBuilder	()Lokhttp3/Response$Builder;
    //   395: getstatic 47	okhttp3/internal/cache/CacheInterceptor:Companion	Lokhttp3/internal/cache/CacheInterceptor$Companion;
    //   398: aload 5
    //   400: invokestatic 213	okhttp3/internal/cache/CacheInterceptor$Companion:access$stripBody	(Lokhttp3/internal/cache/CacheInterceptor$Companion;Lokhttp3/Response;)Lokhttp3/Response;
    //   403: invokevirtual 217	okhttp3/Response$Builder:cacheResponse	(Lokhttp3/Response;)Lokhttp3/Response$Builder;
    //   406: getstatic 47	okhttp3/internal/cache/CacheInterceptor:Companion	Lokhttp3/internal/cache/CacheInterceptor$Companion;
    //   409: aload_1
    //   410: invokestatic 213	okhttp3/internal/cache/CacheInterceptor$Companion:access$stripBody	(Lokhttp3/internal/cache/CacheInterceptor$Companion;Lokhttp3/Response;)Lokhttp3/Response;
    //   413: invokevirtual 241	okhttp3/Response$Builder:networkResponse	(Lokhttp3/Response;)Lokhttp3/Response$Builder;
    //   416: invokevirtual 119	okhttp3/Response$Builder:build	()Lokhttp3/Response;
    //   419: astore_1
    //   420: aload_0
    //   421: getfield 52	okhttp3/internal/cache/CacheInterceptor:cache	Lokhttp3/Cache;
    //   424: ifnull +59 -> 483
    //   427: aload_1
    //   428: invokestatic 257	okhttp3/internal/http/HttpHeaders:promisesBody	(Lokhttp3/Response;)Z
    //   431: ifeq +29 -> 460
    //   434: getstatic 260	okhttp3/internal/cache/CacheStrategy:Companion	Lokhttp3/internal/cache/CacheStrategy$Companion;
    //   437: aload_1
    //   438: aload 4
    //   440: invokevirtual 266	okhttp3/internal/cache/CacheStrategy$Companion:isCacheable	(Lokhttp3/Response;Lokhttp3/Request;)Z
    //   443: ifeq +17 -> 460
    //   446: aload_0
    //   447: aload_0
    //   448: getfield 52	okhttp3/internal/cache/CacheInterceptor:cache	Lokhttp3/Cache;
    //   451: aload_1
    //   452: invokevirtual 270	okhttp3/Cache:put$okhttp	(Lokhttp3/Response;)Lokhttp3/internal/cache/CacheRequest;
    //   455: aload_1
    //   456: invokespecial 272	okhttp3/internal/cache/CacheInterceptor:cacheWritingResponse	(Lokhttp3/internal/cache/CacheRequest;Lokhttp3/Response;)Lokhttp3/Response;
    //   459: areturn
    //   460: getstatic 278	okhttp3/internal/http/HttpMethod:INSTANCE	Lokhttp3/internal/http/HttpMethod;
    //   463: aload 4
    //   465: invokevirtual 284	okhttp3/Request:method	()Ljava/lang/String;
    //   468: invokevirtual 288	okhttp3/internal/http/HttpMethod:invalidatesCache	(Ljava/lang/String;)Z
    //   471: ifeq +12 -> 483
    //   474: aload_0
    //   475: getfield 52	okhttp3/internal/cache/CacheInterceptor:cache	Lokhttp3/Cache;
    //   478: aload 4
    //   480: invokevirtual 292	okhttp3/Cache:remove$okhttp	(Lokhttp3/Request;)V
    //   483: aload_1
    //   484: areturn
    //   485: astore_1
    //   486: aload_2
    //   487: ifnull +19 -> 506
    //   490: aload_2
    //   491: invokevirtual 66	okhttp3/Response:body	()Lokhttp3/ResponseBody;
    //   494: astore_2
    //   495: aload_2
    //   496: ifnull +10 -> 506
    //   499: aload_2
    //   500: checkcast 166	java/io/Closeable
    //   503: invokestatic 172	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
    //   506: aload_1
    //   507: athrow
    //   508: astore_2
    //   509: goto -26 -> 483
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	512	0	this	CacheInterceptor
    //   0	512	1	paramChain	okhttp3.Interceptor.Chain
    //   10	490	2	localObject1	Object
    //   508	1	2	localIOException	IOException
    //   51	158	3	localObject2	Object
    //   56	423	4	localRequest	okhttp3.Request
    //   62	337	5	localResponse	Response
    //   68	8	6	localCache	Cache
    // Exception table:
    //   from	to	target	type
    //   209	218	485	finally
    //   474	483	508	java/io/IOException
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000*\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\030\020\003\032\0020\0042\006\020\005\032\0020\0042\006\020\006\032\0020\004H\002J\020\020\007\032\0020\b2\006\020\t\032\0020\nH\002J\020\020\013\032\0020\b2\006\020\t\032\0020\nH\002J\024\020\f\032\004\030\0010\r2\b\020\016\032\004\030\0010\rH\002?\006\017"}, d2={"Lokhttp3/internal/cache/CacheInterceptor$Companion;", "", "()V", "combine", "Lokhttp3/Headers;", "cachedHeaders", "networkHeaders", "isContentSpecificHeader", "", "fieldName", "", "isEndToEnd", "stripBody", "Lokhttp3/Response;", "response", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    private final Headers combine(Headers paramHeaders1, Headers paramHeaders2)
    {
      Headers.Builder localBuilder = new Headers.Builder();
      int i = paramHeaders1.size();
      int j = 0;
      String str2;
      for (int k = 0; k < i; k++)
      {
        String str1 = paramHeaders1.name(k);
        str2 = paramHeaders1.value(k);
        if ((!StringsKt.equals("Warning", str1, true)) || (!StringsKt.startsWith$default(str2, "1", false, 2, null)))
        {
          Companion localCompanion = (Companion)this;
          if ((localCompanion.isContentSpecificHeader(str1)) || (!localCompanion.isEndToEnd(str1)) || (paramHeaders2.get(str1) == null)) {
            localBuilder.addLenient$okhttp(str1, str2);
          }
        }
      }
      i = paramHeaders2.size();
      for (k = j; k < i; k++)
      {
        str2 = paramHeaders2.name(k);
        paramHeaders1 = (Companion)this;
        if ((!paramHeaders1.isContentSpecificHeader(str2)) && (paramHeaders1.isEndToEnd(str2))) {
          localBuilder.addLenient$okhttp(str2, paramHeaders2.value(k));
        }
      }
      return localBuilder.build();
    }
    
    private final boolean isContentSpecificHeader(String paramString)
    {
      boolean bool1 = true;
      boolean bool2 = bool1;
      if (!StringsKt.equals("Content-Length", paramString, true))
      {
        bool2 = bool1;
        if (!StringsKt.equals("Content-Encoding", paramString, true)) {
          if (StringsKt.equals("Content-Type", paramString, true)) {
            bool2 = bool1;
          } else {
            bool2 = false;
          }
        }
      }
      return bool2;
    }
    
    private final boolean isEndToEnd(String paramString)
    {
      boolean bool = true;
      if ((StringsKt.equals("Connection", paramString, true)) || (StringsKt.equals("Keep-Alive", paramString, true)) || (StringsKt.equals("Proxy-Authenticate", paramString, true)) || (StringsKt.equals("Proxy-Authorization", paramString, true)) || (StringsKt.equals("TE", paramString, true)) || (StringsKt.equals("Trailers", paramString, true)) || (StringsKt.equals("Transfer-Encoding", paramString, true)) || (StringsKt.equals("Upgrade", paramString, true))) {
        bool = false;
      }
      return bool;
    }
    
    private final Response stripBody(Response paramResponse)
    {
      ResponseBody localResponseBody;
      if (paramResponse != null) {
        localResponseBody = paramResponse.body();
      } else {
        localResponseBody = null;
      }
      Response localResponse = paramResponse;
      if (localResponseBody != null) {
        localResponse = paramResponse.newBuilder().body(null).build();
      }
      return localResponse;
    }
  }
}
