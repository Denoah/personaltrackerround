package okhttp3.internal.http2;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.LongRef;
import kotlin.jvm.internal.Ref.ObjectRef;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okhttp3.internal.concurrent.Task;
import okhttp3.internal.concurrent.TaskQueue;
import okhttp3.internal.concurrent.TaskRunner;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\000?\001\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\002\n\002\020\013\n\002\b\003\n\002\020\016\n\002\b\003\n\002\020#\n\002\020\b\n\002\b\f\n\002\030\002\n\002\b\006\n\002\030\002\n\002\b\007\n\002\030\002\n\000\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\020%\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\013\n\002\020 \n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\035\n\002\030\002\n\002\b\024\030\000 ?\0012\0020\001:\b?\001?\001?\001?\001B\017\b\000\022\006\020\002\032\0020\003?\006\002\020\004J\006\020P\032\0020QJ\b\020R\032\0020QH\026J'\020R\032\0020Q2\006\020S\032\0020T2\006\020U\032\0020T2\b\020V\032\004\030\0010WH\000?\006\002\bXJ\022\020Y\032\0020Q2\b\020Z\032\004\030\0010WH\002J\006\020[\032\0020QJ\020\020\\\032\004\030\0010B2\006\020]\032\0020\022J\016\020^\032\0020\t2\006\020_\032\0020\006J&\020`\032\0020B2\006\020a\032\0020\0222\f\020b\032\b\022\004\022\0020d0c2\006\020e\032\0020\tH\002J\034\020`\032\0020B2\f\020b\032\b\022\004\022\0020d0c2\006\020e\032\0020\tJ\006\020f\032\0020\022J-\020g\032\0020Q2\006\020h\032\0020\0222\006\020i\032\0020j2\006\020k\032\0020\0222\006\020l\032\0020\tH\000?\006\002\bmJ+\020n\032\0020Q2\006\020h\032\0020\0222\f\020b\032\b\022\004\022\0020d0c2\006\020l\032\0020\tH\000?\006\002\boJ#\020p\032\0020Q2\006\020h\032\0020\0222\f\020b\032\b\022\004\022\0020d0cH\000?\006\002\bqJ\035\020r\032\0020Q2\006\020h\032\0020\0222\006\020s\032\0020TH\000?\006\002\btJ$\020u\032\0020B2\006\020a\032\0020\0222\f\020b\032\b\022\004\022\0020d0c2\006\020e\032\0020\tJ\025\020v\032\0020\t2\006\020h\032\0020\022H\000?\006\002\bwJ\027\020x\032\004\030\0010B2\006\020h\032\0020\022H\000?\006\002\byJ\r\020z\032\0020QH\000?\006\002\b{J\016\020|\032\0020Q2\006\020}\032\0020&J\016\020~\032\0020Q2\006\020\032\0020TJ\024\020?\001\032\0020Q2\t\b\002\020?\001\032\0020\tH\007J\030\020?\001\032\0020Q2\007\020?\001\032\0020\006H\000?\006\003\b?\001J,\020?\001\032\0020Q2\006\020h\032\0020\0222\007\020?\001\032\0020\t2\n\020?\001\032\005\030\0010?\0012\006\020k\032\0020\006J/\020?\001\032\0020Q2\006\020h\032\0020\0222\007\020?\001\032\0020\t2\r\020?\001\032\b\022\004\022\0020d0cH\000?\006\003\b?\001J\007\020?\001\032\0020QJ\"\020?\001\032\0020Q2\007\020?\001\032\0020\t2\007\020?\001\032\0020\0222\007\020?\001\032\0020\022J\007\020?\001\032\0020QJ\037\020?\001\032\0020Q2\006\020h\032\0020\0222\006\020\032\0020TH\000?\006\003\b?\001J\037\020?\001\032\0020Q2\006\020h\032\0020\0222\006\020s\032\0020TH\000?\006\003\b?\001J \020?\001\032\0020Q2\006\020h\032\0020\0222\007\020?\001\032\0020\006H\000?\006\003\b?\001R\016\020\005\032\0020\006X?\016?\006\002\n\000R\016\020\007\032\0020\006X?\016?\006\002\n\000R\024\020\b\032\0020\tX?\004?\006\b\n\000\032\004\b\n\020\013R\024\020\f\032\0020\rX?\004?\006\b\n\000\032\004\b\016\020\017R\024\020\020\032\b\022\004\022\0020\0220\021X?\004?\006\002\n\000R\016\020\023\032\0020\006X?\016?\006\002\n\000R\016\020\024\032\0020\006X?\016?\006\002\n\000R\016\020\025\032\0020\006X?\016?\006\002\n\000R\016\020\026\032\0020\006X?\016?\006\002\n\000R\016\020\027\032\0020\006X?\016?\006\002\n\000R\016\020\030\032\0020\tX?\016?\006\002\n\000R\032\020\031\032\0020\022X?\016?\006\016\n\000\032\004\b\032\020\033\"\004\b\034\020\035R\024\020\036\032\0020\037X?\004?\006\b\n\000\032\004\b \020!R\032\020\"\032\0020\022X?\016?\006\016\n\000\032\004\b#\020\033\"\004\b$\020\035R\021\020%\032\0020&?\006\b\n\000\032\004\b'\020(R\032\020)\032\0020&X?\016?\006\016\n\000\032\004\b*\020(\"\004\b+\020,R\016\020-\032\0020.X?\004?\006\002\n\000R\016\020/\032\00200X?\004?\006\002\n\000R\036\0202\032\0020\0062\006\0201\032\0020\006@BX?\016?\006\b\n\000\032\004\b3\0204R\036\0205\032\0020\0062\006\0201\032\0020\006@BX?\016?\006\b\n\000\032\004\b6\0204R\025\0207\032\00608R\0020\000?\006\b\n\000\032\004\b9\020:R\016\020;\032\00200X?\004?\006\002\n\000R\024\020<\032\0020=X?\004?\006\b\n\000\032\004\b>\020?R \020@\032\016\022\004\022\0020\022\022\004\022\0020B0AX?\004?\006\b\n\000\032\004\bC\020DR\016\020E\032\0020FX?\004?\006\002\n\000R\036\020G\032\0020\0062\006\0201\032\0020\006@BX?\016?\006\b\n\000\032\004\bH\0204R\036\020I\032\0020\0062\006\0201\032\0020\006@BX?\016?\006\b\n\000\032\004\bJ\0204R\021\020K\032\0020L?\006\b\n\000\032\004\bM\020NR\016\020O\032\00200X?\004?\006\002\n\000?\006?\001"}, d2={"Lokhttp3/internal/http2/Http2Connection;", "Ljava/io/Closeable;", "builder", "Lokhttp3/internal/http2/Http2Connection$Builder;", "(Lokhttp3/internal/http2/Http2Connection$Builder;)V", "awaitPingsSent", "", "awaitPongsReceived", "client", "", "getClient$okhttp", "()Z", "connectionName", "", "getConnectionName$okhttp", "()Ljava/lang/String;", "currentPushRequests", "", "", "degradedPingsSent", "degradedPongDeadlineNs", "degradedPongsReceived", "intervalPingsSent", "intervalPongsReceived", "isShutdown", "lastGoodStreamId", "getLastGoodStreamId$okhttp", "()I", "setLastGoodStreamId$okhttp", "(I)V", "listener", "Lokhttp3/internal/http2/Http2Connection$Listener;", "getListener$okhttp", "()Lokhttp3/internal/http2/Http2Connection$Listener;", "nextStreamId", "getNextStreamId$okhttp", "setNextStreamId$okhttp", "okHttpSettings", "Lokhttp3/internal/http2/Settings;", "getOkHttpSettings", "()Lokhttp3/internal/http2/Settings;", "peerSettings", "getPeerSettings", "setPeerSettings", "(Lokhttp3/internal/http2/Settings;)V", "pushObserver", "Lokhttp3/internal/http2/PushObserver;", "pushQueue", "Lokhttp3/internal/concurrent/TaskQueue;", "<set-?>", "readBytesAcknowledged", "getReadBytesAcknowledged", "()J", "readBytesTotal", "getReadBytesTotal", "readerRunnable", "Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "getReaderRunnable", "()Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "settingsListenerQueue", "socket", "Ljava/net/Socket;", "getSocket$okhttp", "()Ljava/net/Socket;", "streams", "", "Lokhttp3/internal/http2/Http2Stream;", "getStreams$okhttp", "()Ljava/util/Map;", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "writeBytesMaximum", "getWriteBytesMaximum", "writeBytesTotal", "getWriteBytesTotal", "writer", "Lokhttp3/internal/http2/Http2Writer;", "getWriter", "()Lokhttp3/internal/http2/Http2Writer;", "writerQueue", "awaitPong", "", "close", "connectionCode", "Lokhttp3/internal/http2/ErrorCode;", "streamCode", "cause", "Ljava/io/IOException;", "close$okhttp", "failConnection", "e", "flush", "getStream", "id", "isHealthy", "nowNs", "newStream", "associatedStreamId", "requestHeaders", "", "Lokhttp3/internal/http2/Header;", "out", "openStreamCount", "pushDataLater", "streamId", "source", "Lokio/BufferedSource;", "byteCount", "inFinished", "pushDataLater$okhttp", "pushHeadersLater", "pushHeadersLater$okhttp", "pushRequestLater", "pushRequestLater$okhttp", "pushResetLater", "errorCode", "pushResetLater$okhttp", "pushStream", "pushedStream", "pushedStream$okhttp", "removeStream", "removeStream$okhttp", "sendDegradedPingLater", "sendDegradedPingLater$okhttp", "setSettings", "settings", "shutdown", "statusCode", "start", "sendConnectionPreface", "updateConnectionFlowControl", "read", "updateConnectionFlowControl$okhttp", "writeData", "outFinished", "buffer", "Lokio/Buffer;", "writeHeaders", "alternating", "writeHeaders$okhttp", "writePing", "reply", "payload1", "payload2", "writePingAndAwaitPong", "writeSynReset", "writeSynReset$okhttp", "writeSynResetLater", "writeSynResetLater$okhttp", "writeWindowUpdateLater", "unacknowledgedBytesRead", "writeWindowUpdateLater$okhttp", "Builder", "Companion", "Listener", "ReaderRunnable", "okhttp"}, k=1, mv={1, 1, 16})
public final class Http2Connection
  implements Closeable
{
  public static final int AWAIT_PING = 3;
  public static final Companion Companion = new Companion(null);
  private static final Settings DEFAULT_SETTINGS;
  public static final int DEGRADED_PING = 2;
  public static final int DEGRADED_PONG_TIMEOUT_NS = 1000000000;
  public static final int INTERVAL_PING = 1;
  public static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
  private long awaitPingsSent;
  private long awaitPongsReceived;
  private final boolean client;
  private final String connectionName;
  private final Set<Integer> currentPushRequests;
  private long degradedPingsSent;
  private long degradedPongDeadlineNs;
  private long degradedPongsReceived;
  private long intervalPingsSent;
  private long intervalPongsReceived;
  private boolean isShutdown;
  private int lastGoodStreamId;
  private final Listener listener;
  private int nextStreamId;
  private final Settings okHttpSettings;
  private Settings peerSettings;
  private final PushObserver pushObserver;
  private final TaskQueue pushQueue;
  private long readBytesAcknowledged;
  private long readBytesTotal;
  private final ReaderRunnable readerRunnable;
  private final TaskQueue settingsListenerQueue;
  private final Socket socket;
  private final Map<Integer, Http2Stream> streams;
  private final TaskRunner taskRunner;
  private long writeBytesMaximum;
  private long writeBytesTotal;
  private final Http2Writer writer;
  private final TaskQueue writerQueue;
  
  static
  {
    Settings localSettings = new Settings();
    localSettings.set(7, 65535);
    localSettings.set(5, 16384);
    DEFAULT_SETTINGS = localSettings;
  }
  
  public Http2Connection(Builder paramBuilder)
  {
    this.client = paramBuilder.getClient$okhttp();
    this.listener = paramBuilder.getListener$okhttp();
    this.streams = ((Map)new LinkedHashMap());
    this.connectionName = paramBuilder.getConnectionName$okhttp();
    int i;
    if (paramBuilder.getClient$okhttp()) {
      i = 3;
    } else {
      i = 2;
    }
    this.nextStreamId = i;
    Object localObject = paramBuilder.getTaskRunner$okhttp();
    this.taskRunner = ((TaskRunner)localObject);
    this.writerQueue = ((TaskRunner)localObject).newQueue();
    this.pushQueue = this.taskRunner.newQueue();
    this.settingsListenerQueue = this.taskRunner.newQueue();
    this.pushObserver = paramBuilder.getPushObserver$okhttp();
    localObject = new Settings();
    if (paramBuilder.getClient$okhttp()) {
      ((Settings)localObject).set(7, 16777216);
    }
    this.okHttpSettings = ((Settings)localObject);
    localObject = DEFAULT_SETTINGS;
    this.peerSettings = ((Settings)localObject);
    this.writeBytesMaximum = ((Settings)localObject).getInitialWindowSize();
    this.socket = paramBuilder.getSocket$okhttp();
    this.writer = new Http2Writer(paramBuilder.getSink$okhttp(), this.client);
    this.readerRunnable = new ReaderRunnable(new Http2Reader(paramBuilder.getSource$okhttp(), this.client));
    this.currentPushRequests = ((Set)new LinkedHashSet());
    if (paramBuilder.getPingIntervalMillis$okhttp() != 0)
    {
      final long l = TimeUnit.MILLISECONDS.toNanos(paramBuilder.getPingIntervalMillis$okhttp());
      paramBuilder = this.writerQueue;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(this.connectionName);
      ((StringBuilder)localObject).append(" ping");
      localObject = ((StringBuilder)localObject).toString();
      paramBuilder.schedule((Task)new Task((String)localObject, (String)localObject, this, l)
      {
        public long runOnce()
        {
          synchronized (jdField_this)
          {
            int i;
            if (Http2Connection.access$getIntervalPongsReceived$p(jdField_this) < Http2Connection.access$getIntervalPingsSent$p(jdField_this))
            {
              i = 1;
            }
            else
            {
              Http2Connection localHttp2Connection2 = jdField_this;
              Http2Connection.access$setIntervalPingsSent$p(localHttp2Connection2, Http2Connection.access$getIntervalPingsSent$p(localHttp2Connection2) + 1L);
              i = 0;
            }
            long l;
            if (i != 0)
            {
              Http2Connection.access$failConnection(jdField_this, null);
              l = -1L;
            }
            else
            {
              jdField_this.writePing(false, 1, 0);
              l = l;
            }
            return l;
          }
        }
      }, l);
    }
  }
  
  private final void failConnection(IOException paramIOException)
  {
    close$okhttp(ErrorCode.PROTOCOL_ERROR, ErrorCode.PROTOCOL_ERROR, paramIOException);
  }
  
  private final Http2Stream newStream(int paramInt, List<Header> paramList, boolean paramBoolean)
    throws IOException
  {
    boolean bool = paramBoolean ^ true;
    synchronized (this.writer)
    {
      try
      {
        if (this.nextStreamId > 1073741823) {
          shutdown(ErrorCode.REFUSED_STREAM);
        }
        if (!this.isShutdown)
        {
          int i = this.nextStreamId;
          this.nextStreamId += 2;
          Http2Stream localHttp2Stream = new okhttp3/internal/http2/Http2Stream;
          localHttp2Stream.<init>(i, this, bool, false, null);
          int j;
          if ((paramBoolean) && (this.writeBytesTotal < this.writeBytesMaximum) && (localHttp2Stream.getWriteBytesTotal() < localHttp2Stream.getWriteBytesMaximum())) {
            j = 0;
          } else {
            j = 1;
          }
          if (localHttp2Stream.isOpen()) {
            this.streams.put(Integer.valueOf(i), localHttp2Stream);
          }
          Unit localUnit = Unit.INSTANCE;
          if (paramInt == 0)
          {
            this.writer.headers(bool, i, paramList);
          }
          else
          {
            if (!(true ^ this.client)) {
              break label208;
            }
            this.writer.pushPromise(paramInt, i, paramList);
          }
          paramList = Unit.INSTANCE;
          if (j != 0) {
            this.writer.flush();
          }
          return localHttp2Stream;
          label208:
          paramList = new java/lang/IllegalArgumentException;
          paramList.<init>("client streams shouldn't have associated stream IDs".toString());
          throw ((Throwable)paramList);
        }
        paramList = new okhttp3/internal/http2/ConnectionShutdownException;
        paramList.<init>();
        throw ((Throwable)paramList);
      }
      finally {}
    }
  }
  
  public final void awaitPong()
    throws InterruptedException
  {
    try
    {
      while (this.awaitPongsReceived < this.awaitPingsSent) {
        ((Object)this).wait();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void close()
  {
    close$okhttp(ErrorCode.NO_ERROR, ErrorCode.CANCEL, null);
  }
  
  /* Error */
  public final void close$okhttp(ErrorCode paramErrorCode1, ErrorCode paramErrorCode2, IOException paramIOException)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 509
    //   4: invokestatic 248	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_2
    //   8: ldc_w 510
    //   11: invokestatic 248	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   14: getstatic 515	okhttp3/internal/Util:assertionsEnabled	Z
    //   17: ifeq +78 -> 95
    //   20: aload_0
    //   21: invokestatic 521	java/lang/Thread:holdsLock	(Ljava/lang/Object;)Z
    //   24: ifne +6 -> 30
    //   27: goto +68 -> 95
    //   30: new 354	java/lang/StringBuilder
    //   33: dup
    //   34: invokespecial 355	java/lang/StringBuilder:<init>	()V
    //   37: astore_2
    //   38: aload_2
    //   39: ldc_w 523
    //   42: invokevirtual 359	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: invokestatic 527	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   49: astore_1
    //   50: aload_1
    //   51: ldc_w 529
    //   54: invokestatic 532	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   57: aload_2
    //   58: aload_1
    //   59: invokevirtual 535	java/lang/Thread:getName	()Ljava/lang/String;
    //   62: invokevirtual 359	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   65: pop
    //   66: aload_2
    //   67: ldc_w 537
    //   70: invokevirtual 359	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   73: pop
    //   74: aload_2
    //   75: aload_0
    //   76: invokevirtual 540	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   79: pop
    //   80: new 542	java/lang/AssertionError
    //   83: dup
    //   84: aload_2
    //   85: invokevirtual 364	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   88: invokespecial 545	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   91: checkcast 484	java/lang/Throwable
    //   94: athrow
    //   95: aload_0
    //   96: aload_1
    //   97: invokevirtual 434	okhttp3/internal/http2/Http2Connection:shutdown	(Lokhttp3/internal/http2/ErrorCode;)V
    //   100: aconst_null
    //   101: checkcast 547	[Lokhttp3/internal/http2/Http2Stream;
    //   104: astore_1
    //   105: aload_0
    //   106: monitorenter
    //   107: aload_0
    //   108: getfield 264	okhttp3/internal/http2/Http2Connection:streams	Ljava/util/Map;
    //   111: invokeinterface 550 1 0
    //   116: istore 4
    //   118: iconst_0
    //   119: istore 5
    //   121: iload 4
    //   123: iconst_1
    //   124: ixor
    //   125: ifeq +56 -> 181
    //   128: aload_0
    //   129: getfield 264	okhttp3/internal/http2/Http2Connection:streams	Ljava/util/Map;
    //   132: invokeinterface 554 1 0
    //   137: iconst_0
    //   138: anewarray 436	okhttp3/internal/http2/Http2Stream
    //   141: invokeinterface 560 2 0
    //   146: astore_1
    //   147: aload_1
    //   148: ifnull +20 -> 168
    //   151: aload_1
    //   152: checkcast 547	[Lokhttp3/internal/http2/Http2Stream;
    //   155: astore_1
    //   156: aload_0
    //   157: getfield 264	okhttp3/internal/http2/Http2Connection:streams	Ljava/util/Map;
    //   160: invokeinterface 563 1 0
    //   165: goto +16 -> 181
    //   168: new 565	kotlin/TypeCastException
    //   171: astore_1
    //   172: aload_1
    //   173: ldc_w 567
    //   176: invokespecial 568	kotlin/TypeCastException:<init>	(Ljava/lang/String;)V
    //   179: aload_1
    //   180: athrow
    //   181: getstatic 464	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   184: astore 6
    //   186: aload_0
    //   187: monitorexit
    //   188: aload_1
    //   189: ifnull +33 -> 222
    //   192: aload_1
    //   193: arraylength
    //   194: istore 7
    //   196: iload 5
    //   198: iload 7
    //   200: if_icmpge +22 -> 222
    //   203: aload_1
    //   204: iload 5
    //   206: aaload
    //   207: astore 6
    //   209: aload 6
    //   211: aload_2
    //   212: aload_3
    //   213: invokevirtual 571	okhttp3/internal/http2/Http2Stream:close	(Lokhttp3/internal/http2/ErrorCode;Ljava/io/IOException;)V
    //   216: iinc 5 1
    //   219: goto -23 -> 196
    //   222: aload_0
    //   223: getfield 318	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   226: invokevirtual 573	okhttp3/internal/http2/Http2Writer:close	()V
    //   229: aload_0
    //   230: getfield 307	okhttp3/internal/http2/Http2Connection:socket	Ljava/net/Socket;
    //   233: invokevirtual 576	java/net/Socket:close	()V
    //   236: aload_0
    //   237: getfield 284	okhttp3/internal/http2/Http2Connection:writerQueue	Lokhttp3/internal/concurrent/TaskQueue;
    //   240: invokevirtual 578	okhttp3/internal/concurrent/TaskQueue:shutdown	()V
    //   243: aload_0
    //   244: getfield 286	okhttp3/internal/http2/Http2Connection:pushQueue	Lokhttp3/internal/concurrent/TaskQueue;
    //   247: invokevirtual 578	okhttp3/internal/concurrent/TaskQueue:shutdown	()V
    //   250: aload_0
    //   251: getfield 288	okhttp3/internal/http2/Http2Connection:settingsListenerQueue	Lokhttp3/internal/concurrent/TaskQueue;
    //   254: invokevirtual 578	okhttp3/internal/concurrent/TaskQueue:shutdown	()V
    //   257: return
    //   258: astore_1
    //   259: aload_0
    //   260: monitorexit
    //   261: aload_1
    //   262: athrow
    //   263: astore_1
    //   264: goto -164 -> 100
    //   267: astore 6
    //   269: goto -53 -> 216
    //   272: astore_1
    //   273: goto -44 -> 229
    //   276: astore_1
    //   277: goto -41 -> 236
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	280	0	this	Http2Connection
    //   0	280	1	paramErrorCode1	ErrorCode
    //   0	280	2	paramErrorCode2	ErrorCode
    //   0	280	3	paramIOException	IOException
    //   116	9	4	bool	boolean
    //   119	98	5	i	int
    //   184	26	6	localUnit	Unit
    //   267	1	6	localIOException	IOException
    //   194	7	7	j	int
    // Exception table:
    //   from	to	target	type
    //   107	118	258	finally
    //   128	147	258	finally
    //   151	165	258	finally
    //   168	181	258	finally
    //   181	186	258	finally
    //   95	100	263	java/io/IOException
    //   209	216	267	java/io/IOException
    //   222	229	272	java/io/IOException
    //   229	236	276	java/io/IOException
  }
  
  public final void flush()
    throws IOException
  {
    this.writer.flush();
  }
  
  public final boolean getClient$okhttp()
  {
    return this.client;
  }
  
  public final String getConnectionName$okhttp()
  {
    return this.connectionName;
  }
  
  public final int getLastGoodStreamId$okhttp()
  {
    return this.lastGoodStreamId;
  }
  
  public final Listener getListener$okhttp()
  {
    return this.listener;
  }
  
  public final int getNextStreamId$okhttp()
  {
    return this.nextStreamId;
  }
  
  public final Settings getOkHttpSettings()
  {
    return this.okHttpSettings;
  }
  
  public final Settings getPeerSettings()
  {
    return this.peerSettings;
  }
  
  public final long getReadBytesAcknowledged()
  {
    return this.readBytesAcknowledged;
  }
  
  public final long getReadBytesTotal()
  {
    return this.readBytesTotal;
  }
  
  public final ReaderRunnable getReaderRunnable()
  {
    return this.readerRunnable;
  }
  
  public final Socket getSocket$okhttp()
  {
    return this.socket;
  }
  
  public final Http2Stream getStream(int paramInt)
  {
    try
    {
      Http2Stream localHttp2Stream = (Http2Stream)this.streams.get(Integer.valueOf(paramInt));
      return localHttp2Stream;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final Map<Integer, Http2Stream> getStreams$okhttp()
  {
    return this.streams;
  }
  
  public final long getWriteBytesMaximum()
  {
    return this.writeBytesMaximum;
  }
  
  public final long getWriteBytesTotal()
  {
    return this.writeBytesTotal;
  }
  
  public final Http2Writer getWriter()
  {
    return this.writer;
  }
  
  public final boolean isHealthy(long paramLong)
  {
    try
    {
      boolean bool = this.isShutdown;
      if (bool) {
        return false;
      }
      if (this.degradedPongsReceived < this.degradedPingsSent)
      {
        long l = this.degradedPongDeadlineNs;
        if (paramLong >= l) {
          return false;
        }
      }
      return true;
    }
    finally {}
  }
  
  public final Http2Stream newStream(List<Header> paramList, boolean paramBoolean)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramList, "requestHeaders");
    return newStream(0, paramList, paramBoolean);
  }
  
  public final int openStreamCount()
  {
    try
    {
      int i = this.streams.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void pushDataLater$okhttp(final int paramInt1, BufferedSource paramBufferedSource, final int paramInt2, final boolean paramBoolean)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramBufferedSource, "source");
    final Buffer localBuffer = new Buffer();
    long l = paramInt2;
    paramBufferedSource.require(l);
    paramBufferedSource.read(localBuffer, l);
    paramBufferedSource = this.pushQueue;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.connectionName);
    ((StringBuilder)localObject).append('[');
    ((StringBuilder)localObject).append(paramInt1);
    ((StringBuilder)localObject).append("] onData");
    localObject = ((StringBuilder)localObject).toString();
    paramBufferedSource.schedule((Task)new Task((String)localObject, true)
    {
      public long runOnce()
      {
        try
        {
          boolean bool = Http2Connection.access$getPushObserver$p(jdField_this).onData(paramInt1, (BufferedSource)localBuffer, paramInt2, paramBoolean);
          if (bool) {
            jdField_this.getWriter().rstStream(paramInt1, ErrorCode.CANCEL);
          }
          if ((bool) || (paramBoolean)) {
            synchronized (jdField_this)
            {
              Http2Connection.access$getCurrentPushRequests$p(jdField_this).remove(Integer.valueOf(paramInt1));
            }
          }
        }
        catch (IOException localIOException)
        {
          for (;;) {}
        }
        return -1L;
      }
    }, 0L);
  }
  
  public final void pushHeadersLater$okhttp(final int paramInt, final List<Header> paramList, final boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "requestHeaders");
    TaskQueue localTaskQueue = this.pushQueue;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.connectionName);
    ((StringBuilder)localObject).append('[');
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append("] onHeaders");
    localObject = ((StringBuilder)localObject).toString();
    localTaskQueue.schedule((Task)new Task((String)localObject, true)
    {
      public long runOnce()
      {
        boolean bool = Http2Connection.access$getPushObserver$p(jdField_this).onHeaders(paramInt, paramList, paramBoolean);
        if (bool) {}
        try
        {
          jdField_this.getWriter().rstStream(paramInt, ErrorCode.CANCEL);
          if ((bool) || (paramBoolean)) {
            synchronized (jdField_this)
            {
              Http2Connection.access$getCurrentPushRequests$p(jdField_this).remove(Integer.valueOf(paramInt));
            }
          }
        }
        catch (IOException localIOException)
        {
          for (;;) {}
        }
        return -1L;
      }
    }, 0L);
  }
  
  public final void pushRequestLater$okhttp(final int paramInt, final List<Header> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramList, "requestHeaders");
    try
    {
      if (this.currentPushRequests.contains(Integer.valueOf(paramInt)))
      {
        writeSynResetLater$okhttp(paramInt, ErrorCode.PROTOCOL_ERROR);
        return;
      }
      this.currentPushRequests.add(Integer.valueOf(paramInt));
      TaskQueue localTaskQueue = this.pushQueue;
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(this.connectionName);
      ((StringBuilder)localObject).append('[');
      ((StringBuilder)localObject).append(paramInt);
      ((StringBuilder)localObject).append("] onRequest");
      localObject = ((StringBuilder)localObject).toString();
      localTaskQueue.schedule((Task)new Task((String)localObject, true)
      {
        public long runOnce()
        {
          if (Http2Connection.access$getPushObserver$p(jdField_this).onRequest(paramInt, paramList)) {}
          try
          {
            jdField_this.getWriter().rstStream(paramInt, ErrorCode.CANCEL);
            synchronized (jdField_this)
            {
              Http2Connection.access$getCurrentPushRequests$p(jdField_this).remove(Integer.valueOf(paramInt));
            }
          }
          catch (IOException localIOException)
          {
            for (;;) {}
          }
          return -1L;
        }
      }, 0L);
      return;
    }
    finally {}
  }
  
  public final void pushResetLater$okhttp(final int paramInt, final ErrorCode paramErrorCode)
  {
    Intrinsics.checkParameterIsNotNull(paramErrorCode, "errorCode");
    TaskQueue localTaskQueue = this.pushQueue;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.connectionName);
    ((StringBuilder)localObject).append('[');
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append("] onReset");
    localObject = ((StringBuilder)localObject).toString();
    localTaskQueue.schedule((Task)new Task((String)localObject, true)
    {
      public long runOnce()
      {
        Http2Connection.access$getPushObserver$p(jdField_this).onReset(paramInt, paramErrorCode);
        synchronized (jdField_this)
        {
          Http2Connection.access$getCurrentPushRequests$p(jdField_this).remove(Integer.valueOf(paramInt));
          return -1L;
        }
      }
    }, 0L);
  }
  
  public final Http2Stream pushStream(int paramInt, List<Header> paramList, boolean paramBoolean)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramList, "requestHeaders");
    if ((this.client ^ true)) {
      return newStream(paramInt, paramList, paramBoolean);
    }
    throw ((Throwable)new IllegalStateException("Client cannot push requests.".toString()));
  }
  
  public final boolean pushedStream$okhttp(int paramInt)
  {
    boolean bool = true;
    if ((paramInt == 0) || ((paramInt & 0x1) != 0)) {
      bool = false;
    }
    return bool;
  }
  
  public final Http2Stream removeStream$okhttp(int paramInt)
  {
    try
    {
      Http2Stream localHttp2Stream = (Http2Stream)this.streams.remove(Integer.valueOf(paramInt));
      ((Object)this).notifyAll();
      return localHttp2Stream;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public final void sendDegradedPingLater$okhttp()
  {
    try
    {
      long l1 = this.degradedPongsReceived;
      long l2 = this.degradedPingsSent;
      if (l1 < l2) {
        return;
      }
      this.degradedPingsSent += 1L;
      this.degradedPongDeadlineNs = (System.nanoTime() + 1000000000);
      Object localObject1 = Unit.INSTANCE;
      localObject1 = this.writerQueue;
      Object localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append(this.connectionName);
      ((StringBuilder)localObject3).append(" ping");
      localObject3 = ((StringBuilder)localObject3).toString();
      ((TaskQueue)localObject1).schedule((Task)new Task((String)localObject3, true)
      {
        public long runOnce()
        {
          jdField_this.writePing(false, 2, 0);
          return -1L;
        }
      }, 0L);
      return;
    }
    finally {}
  }
  
  public final void setLastGoodStreamId$okhttp(int paramInt)
  {
    this.lastGoodStreamId = paramInt;
  }
  
  public final void setNextStreamId$okhttp(int paramInt)
  {
    this.nextStreamId = paramInt;
  }
  
  public final void setPeerSettings(Settings paramSettings)
  {
    Intrinsics.checkParameterIsNotNull(paramSettings, "<set-?>");
    this.peerSettings = paramSettings;
  }
  
  public final void setSettings(Settings paramSettings)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramSettings, "settings");
    synchronized (this.writer)
    {
      try
      {
        if (!this.isShutdown)
        {
          this.okHttpSettings.merge(paramSettings);
          Unit localUnit = Unit.INSTANCE;
          this.writer.settings(paramSettings);
          paramSettings = Unit.INSTANCE;
          return;
        }
        paramSettings = new okhttp3/internal/http2/ConnectionShutdownException;
        paramSettings.<init>();
        throw ((Throwable)paramSettings);
      }
      finally {}
    }
  }
  
  public final void shutdown(ErrorCode paramErrorCode)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramErrorCode, "statusCode");
    synchronized (this.writer)
    {
      try
      {
        boolean bool = this.isShutdown;
        if (bool) {
          return;
        }
        this.isShutdown = true;
        int i = this.lastGoodStreamId;
        Unit localUnit = Unit.INSTANCE;
        this.writer.goAway(i, paramErrorCode, Util.EMPTY_BYTE_ARRAY);
        paramErrorCode = Unit.INSTANCE;
        return;
      }
      finally {}
    }
  }
  
  public final void start()
    throws IOException
  {
    start$default(this, false, 1, null);
  }
  
  public final void start(boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean)
    {
      this.writer.connectionPreface();
      this.writer.settings(this.okHttpSettings);
      int i = this.okHttpSettings.getInitialWindowSize();
      if (i != 65535) {
        this.writer.windowUpdate(0, i - 65535);
      }
    }
    new Thread((Runnable)this.readerRunnable, this.connectionName).start();
  }
  
  public final void updateConnectionFlowControl$okhttp(long paramLong)
  {
    try
    {
      paramLong = this.readBytesTotal + paramLong;
      this.readBytesTotal = paramLong;
      paramLong -= this.readBytesAcknowledged;
      if (paramLong >= this.okHttpSettings.getInitialWindowSize() / 2)
      {
        writeWindowUpdateLater$okhttp(0, paramLong);
        this.readBytesAcknowledged += paramLong;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public final void writeData(int paramInt, boolean paramBoolean, Buffer paramBuffer, long paramLong)
    throws IOException
  {
    // Byte code:
    //   0: lload 4
    //   2: lstore 6
    //   4: lload 4
    //   6: lconst_0
    //   7: lcmp
    //   8: ifne +15 -> 23
    //   11: aload_0
    //   12: getfield 318	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   15: iload_2
    //   16: iload_1
    //   17: aload_3
    //   18: iconst_0
    //   19: invokevirtual 716	okhttp3/internal/http2/Http2Writer:data	(ZILokio/Buffer;I)V
    //   22: return
    //   23: lload 6
    //   25: lconst_0
    //   26: lcmp
    //   27: ifle +212 -> 239
    //   30: new 718	kotlin/jvm/internal/Ref$IntRef
    //   33: dup
    //   34: invokespecial 719	kotlin/jvm/internal/Ref$IntRef:<init>	()V
    //   37: astore 8
    //   39: aload_0
    //   40: monitorenter
    //   41: aload_0
    //   42: getfield 441	okhttp3/internal/http2/Http2Connection:writeBytesTotal	J
    //   45: aload_0
    //   46: getfield 303	okhttp3/internal/http2/Http2Connection:writeBytesMaximum	J
    //   49: lcmp
    //   50: iflt +45 -> 95
    //   53: aload_0
    //   54: getfield 264	okhttp3/internal/http2/Http2Connection:streams	Ljava/util/Map;
    //   57: iload_1
    //   58: invokestatic 454	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   61: invokeinterface 722 2 0
    //   66: ifeq +13 -> 79
    //   69: aload_0
    //   70: checkcast 4	java/lang/Object
    //   73: invokevirtual 502	java/lang/Object:wait	()V
    //   76: goto -35 -> 41
    //   79: new 427	java/io/IOException
    //   82: astore_3
    //   83: aload_3
    //   84: ldc_w 724
    //   87: invokespecial 725	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   90: aload_3
    //   91: checkcast 484	java/lang/Throwable
    //   94: athrow
    //   95: aload 8
    //   97: lload 6
    //   99: aload_0
    //   100: getfield 303	okhttp3/internal/http2/Http2Connection:writeBytesMaximum	J
    //   103: aload_0
    //   104: getfield 441	okhttp3/internal/http2/Http2Connection:writeBytesTotal	J
    //   107: lsub
    //   108: invokestatic 731	java/lang/Math:min	(JJ)J
    //   111: l2i
    //   112: putfield 734	kotlin/jvm/internal/Ref$IntRef:element	I
    //   115: aload 8
    //   117: aload 8
    //   119: getfield 734	kotlin/jvm/internal/Ref$IntRef:element	I
    //   122: aload_0
    //   123: getfield 318	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   126: invokevirtual 737	okhttp3/internal/http2/Http2Writer:maxDataLength	()I
    //   129: invokestatic 740	java/lang/Math:min	(II)I
    //   132: putfield 734	kotlin/jvm/internal/Ref$IntRef:element	I
    //   135: aload_0
    //   136: aload_0
    //   137: getfield 441	okhttp3/internal/http2/Http2Connection:writeBytesTotal	J
    //   140: aload 8
    //   142: getfield 734	kotlin/jvm/internal/Ref$IntRef:element	I
    //   145: i2l
    //   146: ladd
    //   147: putfield 441	okhttp3/internal/http2/Http2Connection:writeBytesTotal	J
    //   150: getstatic 464	kotlin/Unit:INSTANCE	Lkotlin/Unit;
    //   153: astore 9
    //   155: aload_0
    //   156: monitorexit
    //   157: lload 6
    //   159: aload 8
    //   161: getfield 734	kotlin/jvm/internal/Ref$IntRef:element	I
    //   164: i2l
    //   165: lsub
    //   166: lstore 6
    //   168: aload_0
    //   169: getfield 318	okhttp3/internal/http2/Http2Connection:writer	Lokhttp3/internal/http2/Http2Writer;
    //   172: astore 9
    //   174: iload_2
    //   175: ifeq +16 -> 191
    //   178: lload 6
    //   180: lconst_0
    //   181: lcmp
    //   182: ifne +9 -> 191
    //   185: iconst_1
    //   186: istore 10
    //   188: goto +6 -> 194
    //   191: iconst_0
    //   192: istore 10
    //   194: aload 9
    //   196: iload 10
    //   198: iload_1
    //   199: aload_3
    //   200: aload 8
    //   202: getfield 734	kotlin/jvm/internal/Ref$IntRef:element	I
    //   205: invokevirtual 716	okhttp3/internal/http2/Http2Writer:data	(ZILokio/Buffer;I)V
    //   208: goto -185 -> 23
    //   211: astore_3
    //   212: goto +23 -> 235
    //   215: astore_3
    //   216: invokestatic 527	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   219: invokevirtual 743	java/lang/Thread:interrupt	()V
    //   222: new 745	java/io/InterruptedIOException
    //   225: astore_3
    //   226: aload_3
    //   227: invokespecial 746	java/io/InterruptedIOException:<init>	()V
    //   230: aload_3
    //   231: checkcast 484	java/lang/Throwable
    //   234: athrow
    //   235: aload_0
    //   236: monitorexit
    //   237: aload_3
    //   238: athrow
    //   239: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	240	0	this	Http2Connection
    //   0	240	1	paramInt	int
    //   0	240	2	paramBoolean	boolean
    //   0	240	3	paramBuffer	Buffer
    //   0	240	4	paramLong	long
    //   2	177	6	l	long
    //   37	164	8	localIntRef	kotlin.jvm.internal.Ref.IntRef
    //   153	42	9	localObject	Object
    //   186	11	10	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   41	76	211	finally
    //   79	95	211	finally
    //   95	155	211	finally
    //   216	235	211	finally
    //   41	76	215	java/lang/InterruptedException
    //   79	95	215	java/lang/InterruptedException
  }
  
  public final void writeHeaders$okhttp(int paramInt, boolean paramBoolean, List<Header> paramList)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramList, "alternating");
    this.writer.headers(paramBoolean, paramInt, paramList);
  }
  
  public final void writePing()
    throws InterruptedException
  {
    try
    {
      this.awaitPingsSent += 1L;
      writePing(false, 3, 1330343787);
      return;
    }
    finally {}
  }
  
  public final void writePing(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    try
    {
      this.writer.ping(paramBoolean, paramInt1, paramInt2);
    }
    catch (IOException localIOException)
    {
      failConnection(localIOException);
    }
  }
  
  public final void writePingAndAwaitPong()
    throws InterruptedException
  {
    writePing();
    awaitPong();
  }
  
  public final void writeSynReset$okhttp(int paramInt, ErrorCode paramErrorCode)
    throws IOException
  {
    Intrinsics.checkParameterIsNotNull(paramErrorCode, "statusCode");
    this.writer.rstStream(paramInt, paramErrorCode);
  }
  
  public final void writeSynResetLater$okhttp(final int paramInt, final ErrorCode paramErrorCode)
  {
    Intrinsics.checkParameterIsNotNull(paramErrorCode, "errorCode");
    TaskQueue localTaskQueue = this.writerQueue;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.connectionName);
    ((StringBuilder)localObject).append('[');
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append("] writeSynReset");
    localObject = ((StringBuilder)localObject).toString();
    localTaskQueue.schedule((Task)new Task((String)localObject, true)
    {
      public long runOnce()
      {
        try
        {
          jdField_this.writeSynReset$okhttp(paramInt, paramErrorCode);
        }
        catch (IOException localIOException)
        {
          Http2Connection.access$failConnection(jdField_this, localIOException);
        }
        return -1L;
      }
    }, 0L);
  }
  
  public final void writeWindowUpdateLater$okhttp(final int paramInt, final long paramLong)
  {
    TaskQueue localTaskQueue = this.writerQueue;
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.connectionName);
    ((StringBuilder)localObject).append('[');
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append("] windowUpdate");
    localObject = ((StringBuilder)localObject).toString();
    localTaskQueue.schedule((Task)new Task((String)localObject, true)
    {
      public long runOnce()
      {
        try
        {
          jdField_this.getWriter().windowUpdate(paramInt, paramLong);
        }
        catch (IOException localIOException)
        {
          Http2Connection.access$failConnection(jdField_this, localIOException);
        }
        return -1L;
      }
    }, 0L);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000X\n\002\030\002\n\002\020\000\n\000\n\002\020\013\n\000\n\002\030\002\n\002\b\006\n\002\020\016\n\002\b\005\n\002\030\002\n\002\b\005\n\002\020\b\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\005\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006J\006\0207\032\00208J\016\020\021\032\0020\0002\006\020\021\032\0020\022J\016\020\027\032\0020\0002\006\020\027\032\0020\030J\016\020\035\032\0020\0002\006\020\035\032\0020\036J.\020)\032\0020\0002\006\020)\032\0020*2\b\b\002\0209\032\0020\f2\b\b\002\020/\032\002002\b\b\002\020#\032\0020$H\007R\032\020\002\032\0020\003X?\016?\006\016\n\000\032\004\b\007\020\b\"\004\b\t\020\nR\032\020\013\032\0020\fX?.?\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020R\032\020\021\032\0020\022X?\016?\006\016\n\000\032\004\b\023\020\024\"\004\b\025\020\026R\032\020\027\032\0020\030X?\016?\006\016\n\000\032\004\b\031\020\032\"\004\b\033\020\034R\032\020\035\032\0020\036X?\016?\006\016\n\000\032\004\b\037\020 \"\004\b!\020\"R\032\020#\032\0020$X?.?\006\016\n\000\032\004\b%\020&\"\004\b'\020(R\032\020)\032\0020*X?.?\006\016\n\000\032\004\b+\020,\"\004\b-\020.R\032\020/\032\00200X?.?\006\016\n\000\032\004\b1\0202\"\004\b3\0204R\024\020\004\032\0020\005X?\004?\006\b\n\000\032\004\b5\0206?\006:"}, d2={"Lokhttp3/internal/http2/Http2Connection$Builder;", "", "client", "", "taskRunner", "Lokhttp3/internal/concurrent/TaskRunner;", "(ZLokhttp3/internal/concurrent/TaskRunner;)V", "getClient$okhttp", "()Z", "setClient$okhttp", "(Z)V", "connectionName", "", "getConnectionName$okhttp", "()Ljava/lang/String;", "setConnectionName$okhttp", "(Ljava/lang/String;)V", "listener", "Lokhttp3/internal/http2/Http2Connection$Listener;", "getListener$okhttp", "()Lokhttp3/internal/http2/Http2Connection$Listener;", "setListener$okhttp", "(Lokhttp3/internal/http2/Http2Connection$Listener;)V", "pingIntervalMillis", "", "getPingIntervalMillis$okhttp", "()I", "setPingIntervalMillis$okhttp", "(I)V", "pushObserver", "Lokhttp3/internal/http2/PushObserver;", "getPushObserver$okhttp", "()Lokhttp3/internal/http2/PushObserver;", "setPushObserver$okhttp", "(Lokhttp3/internal/http2/PushObserver;)V", "sink", "Lokio/BufferedSink;", "getSink$okhttp", "()Lokio/BufferedSink;", "setSink$okhttp", "(Lokio/BufferedSink;)V", "socket", "Ljava/net/Socket;", "getSocket$okhttp", "()Ljava/net/Socket;", "setSocket$okhttp", "(Ljava/net/Socket;)V", "source", "Lokio/BufferedSource;", "getSource$okhttp", "()Lokio/BufferedSource;", "setSource$okhttp", "(Lokio/BufferedSource;)V", "getTaskRunner$okhttp", "()Lokhttp3/internal/concurrent/TaskRunner;", "build", "Lokhttp3/internal/http2/Http2Connection;", "peerName", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Builder
  {
    private boolean client;
    public String connectionName;
    private Http2Connection.Listener listener;
    private int pingIntervalMillis;
    private PushObserver pushObserver;
    public BufferedSink sink;
    public Socket socket;
    public BufferedSource source;
    private final TaskRunner taskRunner;
    
    public Builder(boolean paramBoolean, TaskRunner paramTaskRunner)
    {
      this.client = paramBoolean;
      this.taskRunner = paramTaskRunner;
      this.listener = Http2Connection.Listener.REFUSE_INCOMING_STREAMS;
      this.pushObserver = PushObserver.CANCEL;
    }
    
    public final Http2Connection build()
    {
      return new Http2Connection(this);
    }
    
    public final boolean getClient$okhttp()
    {
      return this.client;
    }
    
    public final String getConnectionName$okhttp()
    {
      String str = this.connectionName;
      if (str == null) {
        Intrinsics.throwUninitializedPropertyAccessException("connectionName");
      }
      return str;
    }
    
    public final Http2Connection.Listener getListener$okhttp()
    {
      return this.listener;
    }
    
    public final int getPingIntervalMillis$okhttp()
    {
      return this.pingIntervalMillis;
    }
    
    public final PushObserver getPushObserver$okhttp()
    {
      return this.pushObserver;
    }
    
    public final BufferedSink getSink$okhttp()
    {
      BufferedSink localBufferedSink = this.sink;
      if (localBufferedSink == null) {
        Intrinsics.throwUninitializedPropertyAccessException("sink");
      }
      return localBufferedSink;
    }
    
    public final Socket getSocket$okhttp()
    {
      Socket localSocket = this.socket;
      if (localSocket == null) {
        Intrinsics.throwUninitializedPropertyAccessException("socket");
      }
      return localSocket;
    }
    
    public final BufferedSource getSource$okhttp()
    {
      BufferedSource localBufferedSource = this.source;
      if (localBufferedSource == null) {
        Intrinsics.throwUninitializedPropertyAccessException("source");
      }
      return localBufferedSource;
    }
    
    public final TaskRunner getTaskRunner$okhttp()
    {
      return this.taskRunner;
    }
    
    public final Builder listener(Http2Connection.Listener paramListener)
    {
      Intrinsics.checkParameterIsNotNull(paramListener, "listener");
      Builder localBuilder = (Builder)this;
      localBuilder.listener = paramListener;
      return localBuilder;
    }
    
    public final Builder pingIntervalMillis(int paramInt)
    {
      Builder localBuilder = (Builder)this;
      localBuilder.pingIntervalMillis = paramInt;
      return localBuilder;
    }
    
    public final Builder pushObserver(PushObserver paramPushObserver)
    {
      Intrinsics.checkParameterIsNotNull(paramPushObserver, "pushObserver");
      Builder localBuilder = (Builder)this;
      localBuilder.pushObserver = paramPushObserver;
      return localBuilder;
    }
    
    public final void setClient$okhttp(boolean paramBoolean)
    {
      this.client = paramBoolean;
    }
    
    public final void setConnectionName$okhttp(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "<set-?>");
      this.connectionName = paramString;
    }
    
    public final void setListener$okhttp(Http2Connection.Listener paramListener)
    {
      Intrinsics.checkParameterIsNotNull(paramListener, "<set-?>");
      this.listener = paramListener;
    }
    
    public final void setPingIntervalMillis$okhttp(int paramInt)
    {
      this.pingIntervalMillis = paramInt;
    }
    
    public final void setPushObserver$okhttp(PushObserver paramPushObserver)
    {
      Intrinsics.checkParameterIsNotNull(paramPushObserver, "<set-?>");
      this.pushObserver = paramPushObserver;
    }
    
    public final void setSink$okhttp(BufferedSink paramBufferedSink)
    {
      Intrinsics.checkParameterIsNotNull(paramBufferedSink, "<set-?>");
      this.sink = paramBufferedSink;
    }
    
    public final void setSocket$okhttp(Socket paramSocket)
    {
      Intrinsics.checkParameterIsNotNull(paramSocket, "<set-?>");
      this.socket = paramSocket;
    }
    
    public final void setSource$okhttp(BufferedSource paramBufferedSource)
    {
      Intrinsics.checkParameterIsNotNull(paramBufferedSource, "<set-?>");
      this.source = paramBufferedSource;
    }
    
    public final Builder socket(Socket paramSocket)
      throws IOException
    {
      return socket$default(this, paramSocket, null, null, null, 14, null);
    }
    
    public final Builder socket(Socket paramSocket, String paramString)
      throws IOException
    {
      return socket$default(this, paramSocket, paramString, null, null, 12, null);
    }
    
    public final Builder socket(Socket paramSocket, String paramString, BufferedSource paramBufferedSource)
      throws IOException
    {
      return socket$default(this, paramSocket, paramString, paramBufferedSource, null, 8, null);
    }
    
    public final Builder socket(Socket paramSocket, String paramString, BufferedSource paramBufferedSource, BufferedSink paramBufferedSink)
      throws IOException
    {
      Intrinsics.checkParameterIsNotNull(paramSocket, "socket");
      Intrinsics.checkParameterIsNotNull(paramString, "peerName");
      Intrinsics.checkParameterIsNotNull(paramBufferedSource, "source");
      Intrinsics.checkParameterIsNotNull(paramBufferedSink, "sink");
      Builder localBuilder = (Builder)this;
      localBuilder.socket = paramSocket;
      if (localBuilder.client)
      {
        paramSocket = new StringBuilder();
        paramSocket.append(Util.okHttpName);
        paramSocket.append(' ');
        paramSocket.append(paramString);
        paramSocket = paramSocket.toString();
      }
      else
      {
        paramSocket = new StringBuilder();
        paramSocket.append("MockWebServer ");
        paramSocket.append(paramString);
        paramSocket = paramSocket.toString();
      }
      localBuilder.connectionName = paramSocket;
      localBuilder.source = paramBufferedSource;
      localBuilder.sink = paramBufferedSink;
      return localBuilder;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\b\n\000\n\002\030\002\n\002\b\007\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000R\021\020\005\032\0020\006?\006\b\n\000\032\004\b\007\020\bR\016\020\t\032\0020\004X?T?\006\002\n\000R\016\020\n\032\0020\004X?T?\006\002\n\000R\016\020\013\032\0020\004X?T?\006\002\n\000R\016\020\f\032\0020\004X?T?\006\002\n\000?\006\r"}, d2={"Lokhttp3/internal/http2/Http2Connection$Companion;", "", "()V", "AWAIT_PING", "", "DEFAULT_SETTINGS", "Lokhttp3/internal/http2/Settings;", "getDEFAULT_SETTINGS", "()Lokhttp3/internal/http2/Settings;", "DEGRADED_PING", "DEGRADED_PONG_TIMEOUT_NS", "INTERVAL_PING", "OKHTTP_CLIENT_WINDOW_SIZE", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final Settings getDEFAULT_SETTINGS()
    {
      return Http2Connection.access$getDEFAULT_SETTINGS$cp();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\b&\030\000 \f2\0020\001:\001\fB\005?\006\002\020\002J\030\020\003\032\0020\0042\006\020\005\032\0020\0062\006\020\007\032\0020\bH\026J\020\020\t\032\0020\0042\006\020\n\032\0020\013H&?\006\r"}, d2={"Lokhttp3/internal/http2/Http2Connection$Listener;", "", "()V", "onSettings", "", "connection", "Lokhttp3/internal/http2/Http2Connection;", "settings", "Lokhttp3/internal/http2/Settings;", "onStream", "stream", "Lokhttp3/internal/http2/Http2Stream;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
  public static abstract class Listener
  {
    public static final Companion Companion = new Companion(null);
    public static final Listener REFUSE_INCOMING_STREAMS = (Listener)new Http2Connection.Listener.Companion.REFUSE_INCOMING_STREAMS.1();
    
    public Listener() {}
    
    public void onSettings(Http2Connection paramHttp2Connection, Settings paramSettings)
    {
      Intrinsics.checkParameterIsNotNull(paramHttp2Connection, "connection");
      Intrinsics.checkParameterIsNotNull(paramSettings, "settings");
    }
    
    public abstract void onStream(Http2Stream paramHttp2Stream)
      throws IOException;
    
    @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\020\020\003\032\0020\0048\006X?\004?\006\002\n\000?\006\005"}, d2={"Lokhttp3/internal/http2/Http2Connection$Listener$Companion;", "", "()V", "REFUSE_INCOMING_STREAMS", "Lokhttp3/internal/http2/Http2Connection$Listener;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion
    {
      private Companion() {}
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000d\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\020\002\n\002\b\002\n\002\020\b\n\000\n\002\020\016\n\000\n\002\030\002\n\002\b\003\n\002\020\t\n\002\b\002\n\002\020\013\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\004\n\002\020 \n\002\030\002\n\002\b\020\b?\004\030\0002\0020\0012\0020\002B\017\b\000\022\006\020\003\032\0020\004?\006\002\020\005J\b\020\b\032\0020\tH\026J8\020\n\032\0020\t2\006\020\013\032\0020\f2\006\020\r\032\0020\0162\006\020\017\032\0020\0202\006\020\021\032\0020\0162\006\020\022\032\0020\f2\006\020\023\032\0020\024H\026J\026\020\025\032\0020\t2\006\020\026\032\0020\0272\006\020\030\032\0020\031J(\020\032\032\0020\t2\006\020\033\032\0020\0272\006\020\013\032\0020\f2\006\020\034\032\0020\0352\006\020\036\032\0020\fH\026J \020\037\032\0020\t2\006\020 \032\0020\f2\006\020!\032\0020\"2\006\020#\032\0020\020H\026J.\020$\032\0020\t2\006\020\033\032\0020\0272\006\020\013\032\0020\f2\006\020%\032\0020\f2\f\020&\032\b\022\004\022\0020(0'H\026J \020)\032\0020\t2\006\020*\032\0020\0272\006\020+\032\0020\f2\006\020,\032\0020\fH\026J(\020-\032\0020\t2\006\020\013\032\0020\f2\006\020.\032\0020\f2\006\020/\032\0020\f2\006\0200\032\0020\027H\026J&\0201\032\0020\t2\006\020\013\032\0020\f2\006\0202\032\0020\f2\f\0203\032\b\022\004\022\0020(0'H\026J\030\0204\032\0020\t2\006\020\013\032\0020\f2\006\020!\032\0020\"H\026J\b\0205\032\0020\tH\026J\030\020\030\032\0020\t2\006\020\026\032\0020\0272\006\020\030\032\0020\031H\026J\030\0206\032\0020\t2\006\020\013\032\0020\f2\006\0207\032\0020\024H\026R\024\020\003\032\0020\004X?\004?\006\b\n\000\032\004\b\006\020\007?\0068"}, d2={"Lokhttp3/internal/http2/Http2Connection$ReaderRunnable;", "Ljava/lang/Runnable;", "Lokhttp3/internal/http2/Http2Reader$Handler;", "reader", "Lokhttp3/internal/http2/Http2Reader;", "(Lokhttp3/internal/http2/Http2Connection;Lokhttp3/internal/http2/Http2Reader;)V", "getReader$okhttp", "()Lokhttp3/internal/http2/Http2Reader;", "ackSettings", "", "alternateService", "streamId", "", "origin", "", "protocol", "Lokio/ByteString;", "host", "port", "maxAge", "", "applyAndAckSettings", "clearPrevious", "", "settings", "Lokhttp3/internal/http2/Settings;", "data", "inFinished", "source", "Lokio/BufferedSource;", "length", "goAway", "lastGoodStreamId", "errorCode", "Lokhttp3/internal/http2/ErrorCode;", "debugData", "headers", "associatedStreamId", "headerBlock", "", "Lokhttp3/internal/http2/Header;", "ping", "ack", "payload1", "payload2", "priority", "streamDependency", "weight", "exclusive", "pushPromise", "promisedStreamId", "requestHeaders", "rstStream", "run", "windowUpdate", "windowSizeIncrement", "okhttp"}, k=1, mv={1, 1, 16})
  public final class ReaderRunnable
    implements Runnable, Http2Reader.Handler
  {
    private final Http2Reader reader;
    
    public ReaderRunnable()
    {
      this.reader = localObject;
    }
    
    public void ackSettings() {}
    
    public void alternateService(int paramInt1, String paramString1, ByteString paramByteString, String paramString2, int paramInt2, long paramLong)
    {
      Intrinsics.checkParameterIsNotNull(paramString1, "origin");
      Intrinsics.checkParameterIsNotNull(paramByteString, "protocol");
      Intrinsics.checkParameterIsNotNull(paramString2, "host");
    }
    
    public final void applyAndAckSettings(boolean paramBoolean, Settings arg2)
    {
      Intrinsics.checkParameterIsNotNull(???, "settings");
      Ref.LongRef localLongRef = new Ref.LongRef();
      Ref.ObjectRef localObjectRef1 = new Ref.ObjectRef();
      Ref.ObjectRef localObjectRef2 = new Ref.ObjectRef();
      synchronized (Http2Connection.this.getWriter())
      {
        synchronized (Http2Connection.this)
        {
          Object localObject2 = Http2Connection.this.getPeerSettings();
          if (paramBoolean)
          {
            localObjectRef2.element = ???;
          }
          else
          {
            localObject4 = new okhttp3/internal/http2/Settings;
            ((Settings)localObject4).<init>();
            ((Settings)localObject4).merge((Settings)localObject2);
            ((Settings)localObject4).merge(???);
            localObjectRef2.element = localObject4;
          }
          localLongRef.element = (((Settings)localObjectRef2.element).getInitialWindowSize() - ((Settings)localObject2).getInitialWindowSize());
          if ((localLongRef.element != 0L) && (!Http2Connection.this.getStreams$okhttp().isEmpty()))
          {
            localObject2 = Http2Connection.this.getStreams$okhttp().values().toArray(new Http2Stream[0]);
            if (localObject2 != null)
            {
              localObject2 = (Http2Stream[])localObject2;
            }
            else
            {
              ??? = new kotlin/TypeCastException;
              ???.<init>("null cannot be cast to non-null type kotlin.Array<T>");
              throw ???;
            }
          }
          else
          {
            localObject2 = null;
          }
          localObjectRef1.element = localObject2;
          Http2Connection.this.setPeerSettings((Settings)localObjectRef2.element);
          localObject2 = Http2Connection.access$getSettingsListenerQueue$p(Http2Connection.this);
          Object localObject4 = new java/lang/StringBuilder;
          ((StringBuilder)localObject4).<init>();
          ((StringBuilder)localObject4).append(Http2Connection.this.getConnectionName$okhttp());
          ((StringBuilder)localObject4).append(" onSettings");
          String str = ((StringBuilder)localObject4).toString();
          localObject4 = new okhttp3/internal/http2/Http2Connection$ReaderRunnable$applyAndAckSettings$$inlined$synchronized$lambda$1;
          try
          {
            ((applyAndAckSettings..inlined.synchronized.lambda.1)localObject4).<init>(str, true, str, true, this, paramBoolean, localObjectRef2, ???, localLongRef, localObjectRef1);
            ((TaskQueue)localObject2).schedule((Task)localObject4, 0L);
            ??? = Unit.INSTANCE;
            try
            {
              Http2Connection.this.getWriter().applyAndAckSettings((Settings)localObjectRef2.element);
            }
            catch (IOException ???)
            {
              Http2Connection.access$failConnection(Http2Connection.this, ???);
            }
            ??? = Unit.INSTANCE;
            int i;
            int j;
            if ((Http2Stream[])localObjectRef1.element != null)
            {
              localObject2 = (Http2Stream[])localObjectRef1.element;
              if (localObject2 == null) {
                Intrinsics.throwNpe();
              }
              i = localObject2.length;
              j = 0;
            }
            for (;;)
            {
              if (j < i) {}
              synchronized (localObject2[j])
              {
                ???.addBytesToWriteWindow(localLongRef.element);
                ??? = Unit.INSTANCE;
                j++;
              }
            }
            ??? = finally;
          }
          finally {}
        }
        throw ???;
      }
    }
    
    public void data(boolean paramBoolean, int paramInt1, BufferedSource paramBufferedSource, int paramInt2)
      throws IOException
    {
      Intrinsics.checkParameterIsNotNull(paramBufferedSource, "source");
      if (Http2Connection.this.pushedStream$okhttp(paramInt1))
      {
        Http2Connection.this.pushDataLater$okhttp(paramInt1, paramBufferedSource, paramInt2, paramBoolean);
        return;
      }
      Object localObject = Http2Connection.this.getStream(paramInt1);
      if (localObject == null)
      {
        Http2Connection.this.writeSynResetLater$okhttp(paramInt1, ErrorCode.PROTOCOL_ERROR);
        localObject = Http2Connection.this;
        long l = paramInt2;
        ((Http2Connection)localObject).updateConnectionFlowControl$okhttp(l);
        paramBufferedSource.skip(l);
        return;
      }
      ((Http2Stream)localObject).receiveData(paramBufferedSource, paramInt2);
      if (paramBoolean) {
        ((Http2Stream)localObject).receiveHeaders(Util.EMPTY_HEADERS, true);
      }
    }
    
    public final Http2Reader getReader$okhttp()
    {
      return this.reader;
    }
    
    public void goAway(int paramInt, ErrorCode arg2, ByteString paramByteString)
    {
      Intrinsics.checkParameterIsNotNull(???, "errorCode");
      Intrinsics.checkParameterIsNotNull(paramByteString, "debugData");
      paramByteString.size();
      synchronized (Http2Connection.this)
      {
        paramByteString = Http2Connection.this.getStreams$okhttp().values();
        int i = 0;
        paramByteString = paramByteString.toArray(new Http2Stream[0]);
        if (paramByteString != null)
        {
          paramByteString = (Http2Stream[])paramByteString;
          Http2Connection.access$setShutdown$p(Http2Connection.this, true);
          Unit localUnit = Unit.INSTANCE;
          int j = paramByteString.length;
          while (i < j)
          {
            ??? = paramByteString[i];
            if ((???.getId() > paramInt) && (???.isLocallyInitiated()))
            {
              ???.receiveRstStream(ErrorCode.REFUSED_STREAM);
              Http2Connection.this.removeStream$okhttp(???.getId());
            }
            i++;
          }
          return;
        }
        paramByteString = new kotlin/TypeCastException;
        paramByteString.<init>("null cannot be cast to non-null type kotlin.Array<T>");
        throw paramByteString;
      }
    }
    
    public void headers(boolean paramBoolean, int paramInt1, int paramInt2, List<Header> paramList)
    {
      Intrinsics.checkParameterIsNotNull(paramList, "headerBlock");
      if (Http2Connection.this.pushedStream$okhttp(paramInt1))
      {
        Http2Connection.this.pushHeadersLater$okhttp(paramInt1, paramList, paramBoolean);
        return;
      }
      synchronized (Http2Connection.this)
      {
        Http2Stream localHttp2Stream = Http2Connection.this.getStream(paramInt1);
        if (localHttp2Stream == null)
        {
          boolean bool = Http2Connection.access$isShutdown$p(Http2Connection.this);
          if (bool) {
            return;
          }
          paramInt2 = Http2Connection.this.getLastGoodStreamId$okhttp();
          if (paramInt1 <= paramInt2) {
            return;
          }
          paramInt2 = Http2Connection.this.getNextStreamId$okhttp();
          if (paramInt1 % 2 == paramInt2 % 2) {
            return;
          }
          Object localObject1 = Util.toHeaders(paramList);
          localObject2 = new okhttp3/internal/http2/Http2Stream;
          ((Http2Stream)localObject2).<init>(paramInt1, Http2Connection.this, false, paramBoolean, (Headers)localObject1);
          Http2Connection.this.setLastGoodStreamId$okhttp(paramInt1);
          Http2Connection.this.getStreams$okhttp().put(Integer.valueOf(paramInt1), localObject2);
          localObject1 = Http2Connection.access$getTaskRunner$p(Http2Connection.this).newQueue();
          Object localObject3 = new java/lang/StringBuilder;
          ((StringBuilder)localObject3).<init>();
          ((StringBuilder)localObject3).append(Http2Connection.this.getConnectionName$okhttp());
          ((StringBuilder)localObject3).append('[');
          ((StringBuilder)localObject3).append(paramInt1);
          ((StringBuilder)localObject3).append("] onStream");
          String str = ((StringBuilder)localObject3).toString();
          localObject3 = new okhttp3/internal/http2/Http2Connection$ReaderRunnable$headers$$inlined$synchronized$lambda$1;
          ((headers..inlined.synchronized.lambda.1)localObject3).<init>(str, true, str, true, (Http2Stream)localObject2, this, localHttp2Stream, paramInt1, paramList, paramBoolean);
          ((TaskQueue)localObject1).schedule((Task)localObject3, 0L);
          return;
        }
        Object localObject2 = Unit.INSTANCE;
        localHttp2Stream.receiveHeaders(Util.toHeaders(paramList), paramBoolean);
        return;
      }
    }
    
    public void ping(boolean paramBoolean, final int paramInt1, final int paramInt2)
    {
      if (paramBoolean)
      {
        localObject1 = Http2Connection.this;
        if ((paramInt1 == 1) || ((paramInt1 == 2) || (paramInt1 != 3))) {}
        label139:
        try
        {
          for (;;)
          {
            localObject2 = Unit.INSTANCE;
            break label139;
            localObject2 = Http2Connection.this;
            Http2Connection.access$setAwaitPongsReceived$p((Http2Connection)localObject2, Http2Connection.access$getAwaitPongsReceived$p((Http2Connection)localObject2) + 1L);
            localObject2 = Http2Connection.this;
            if (localObject2 == null) {
              break;
            }
            ((Object)localObject2).notifyAll();
          }
          Object localObject2 = new kotlin/TypeCastException;
          ((TypeCastException)localObject2).<init>("null cannot be cast to non-null type java.lang.Object");
          throw ((Throwable)localObject2);
          localObject2 = Http2Connection.this;
          long l = Http2Connection.access$getDegradedPongsReceived$p((Http2Connection)localObject2);
          Http2Connection.access$setDegradedPongsReceived$p((Http2Connection)localObject2, 1L + l);
          break label139;
          localObject2 = Http2Connection.this;
          l = Http2Connection.access$getIntervalPongsReceived$p((Http2Connection)localObject2);
          Http2Connection.access$setIntervalPongsReceived$p((Http2Connection)localObject2, 1L + l);
        }
        finally {}
      }
      Object localObject1 = Http2Connection.access$getWriterQueue$p(Http2Connection.this);
      Object localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append(Http2Connection.this.getConnectionName$okhttp());
      ((StringBuilder)localObject4).append(" ping");
      localObject4 = ((StringBuilder)localObject4).toString();
      ((TaskQueue)localObject1).schedule((Task)new Task((String)localObject4, true)
      {
        public long runOnce()
        {
          jdField_this.this$0.writePing(true, paramInt1, paramInt2);
          return -1L;
        }
      }, 0L);
    }
    
    public void priority(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {}
    
    public void pushPromise(int paramInt1, int paramInt2, List<Header> paramList)
    {
      Intrinsics.checkParameterIsNotNull(paramList, "requestHeaders");
      Http2Connection.this.pushRequestLater$okhttp(paramInt2, paramList);
    }
    
    public void rstStream(int paramInt, ErrorCode paramErrorCode)
    {
      Intrinsics.checkParameterIsNotNull(paramErrorCode, "errorCode");
      if (Http2Connection.this.pushedStream$okhttp(paramInt))
      {
        Http2Connection.this.pushResetLater$okhttp(paramInt, paramErrorCode);
        return;
      }
      Http2Stream localHttp2Stream = Http2Connection.this.removeStream$okhttp(paramInt);
      if (localHttp2Stream != null) {
        localHttp2Stream.receiveRstStream(paramErrorCode);
      }
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: getstatic 409	okhttp3/internal/http2/ErrorCode:INTERNAL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   3: astore_1
      //   4: getstatic 409	okhttp3/internal/http2/ErrorCode:INTERNAL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   7: astore_2
      //   8: aconst_null
      //   9: checkcast 109	java/io/IOException
      //   12: astore_3
      //   13: aload_1
      //   14: astore 4
      //   16: aload_3
      //   17: astore 5
      //   19: aload_1
      //   20: astore 6
      //   22: aload_0
      //   23: getfield 99	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   26: aload_0
      //   27: checkcast 8	okhttp3/internal/http2/Http2Reader$Handler
      //   30: invokevirtual 415	okhttp3/internal/http2/Http2Reader:readConnectionPreface	(Lokhttp3/internal/http2/Http2Reader$Handler;)V
      //   33: aload_1
      //   34: astore 4
      //   36: aload_3
      //   37: astore 5
      //   39: aload_1
      //   40: astore 6
      //   42: aload_0
      //   43: getfield 99	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   46: iconst_0
      //   47: aload_0
      //   48: checkcast 8	okhttp3/internal/http2/Http2Reader$Handler
      //   51: invokevirtual 419	okhttp3/internal/http2/Http2Reader:nextFrame	(ZLokhttp3/internal/http2/Http2Reader$Handler;)Z
      //   54: ifeq +6 -> 60
      //   57: goto -24 -> 33
      //   60: aload_1
      //   61: astore 4
      //   63: aload_3
      //   64: astore 5
      //   66: aload_1
      //   67: astore 6
      //   69: getstatic 422	okhttp3/internal/http2/ErrorCode:NO_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   72: astore_1
      //   73: aload_1
      //   74: astore 4
      //   76: aload_3
      //   77: astore 5
      //   79: aload_1
      //   80: astore 6
      //   82: getstatic 425	okhttp3/internal/http2/ErrorCode:CANCEL	Lokhttp3/internal/http2/ErrorCode;
      //   85: astore 7
      //   87: aload_1
      //   88: astore 6
      //   90: aload 7
      //   92: astore 4
      //   94: aload_3
      //   95: astore_1
      //   96: goto +35 -> 131
      //   99: astore 6
      //   101: goto +53 -> 154
      //   104: astore_1
      //   105: aload 6
      //   107: astore 4
      //   109: aload_1
      //   110: astore 5
      //   112: getstatic 247	okhttp3/internal/http2/ErrorCode:PROTOCOL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   115: astore 6
      //   117: aload 6
      //   119: astore 4
      //   121: aload_1
      //   122: astore 5
      //   124: getstatic 247	okhttp3/internal/http2/ErrorCode:PROTOCOL_ERROR	Lokhttp3/internal/http2/ErrorCode;
      //   127: astore_3
      //   128: aload_3
      //   129: astore 4
      //   131: aload_0
      //   132: getfield 94	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   135: aload 6
      //   137: aload 4
      //   139: aload_1
      //   140: invokevirtual 429	okhttp3/internal/http2/Http2Connection:close$okhttp	(Lokhttp3/internal/http2/ErrorCode;Lokhttp3/internal/http2/ErrorCode;Ljava/io/IOException;)V
      //   143: aload_0
      //   144: getfield 99	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   147: checkcast 431	java/io/Closeable
      //   150: invokestatic 435	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   153: return
      //   154: aload_0
      //   155: getfield 94	okhttp3/internal/http2/Http2Connection$ReaderRunnable:this$0	Lokhttp3/internal/http2/Http2Connection;
      //   158: aload 4
      //   160: aload_2
      //   161: aload 5
      //   163: invokevirtual 429	okhttp3/internal/http2/Http2Connection:close$okhttp	(Lokhttp3/internal/http2/ErrorCode;Lokhttp3/internal/http2/ErrorCode;Ljava/io/IOException;)V
      //   166: aload_0
      //   167: getfield 99	okhttp3/internal/http2/Http2Connection$ReaderRunnable:reader	Lokhttp3/internal/http2/Http2Reader;
      //   170: checkcast 431	java/io/Closeable
      //   173: invokestatic 435	okhttp3/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   176: aload 6
      //   178: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	179	0	this	ReaderRunnable
      //   3	93	1	localObject1	Object
      //   104	36	1	localIOException	IOException
      //   7	154	2	localErrorCode1	ErrorCode
      //   12	117	3	localObject2	Object
      //   14	145	4	localObject3	Object
      //   17	145	5	localObject4	Object
      //   20	69	6	localObject5	Object
      //   99	7	6	localObject6	Object
      //   115	62	6	localErrorCode2	ErrorCode
      //   85	6	7	localErrorCode3	ErrorCode
      // Exception table:
      //   from	to	target	type
      //   22	33	99	finally
      //   42	57	99	finally
      //   69	73	99	finally
      //   82	87	99	finally
      //   112	117	99	finally
      //   124	128	99	finally
      //   22	33	104	java/io/IOException
      //   42	57	104	java/io/IOException
      //   69	73	104	java/io/IOException
      //   82	87	104	java/io/IOException
    }
    
    public void settings(final boolean paramBoolean, final Settings paramSettings)
    {
      Intrinsics.checkParameterIsNotNull(paramSettings, "settings");
      TaskQueue localTaskQueue = Http2Connection.access$getWriterQueue$p(Http2Connection.this);
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(Http2Connection.this.getConnectionName$okhttp());
      ((StringBuilder)localObject).append(" applyAndAckSettings");
      localObject = ((StringBuilder)localObject).toString();
      localTaskQueue.schedule((Task)new Task((String)localObject, true)
      {
        public long runOnce()
        {
          jdField_this.applyAndAckSettings(paramBoolean, paramSettings);
          return -1L;
        }
      }, 0L);
    }
    
    public void windowUpdate(int paramInt, long paramLong)
    {
      if (paramInt == 0) {
        synchronized (Http2Connection.this)
        {
          Object localObject2 = Http2Connection.this;
          Http2Connection.access$setWriteBytesMaximum$p((Http2Connection)localObject2, ((Http2Connection)localObject2).getWriteBytesMaximum() + paramLong);
          localObject2 = Http2Connection.this;
          if (localObject2 != null)
          {
            ((Object)localObject2).notifyAll();
            localObject2 = Unit.INSTANCE;
            return;
          }
          localObject2 = new kotlin/TypeCastException;
          ((TypeCastException)localObject2).<init>("null cannot be cast to non-null type java.lang.Object");
          throw ((Throwable)localObject2);
        }
      }
      ??? = Http2Connection.this.getStream(paramInt);
      if (??? != null) {
        try
        {
          ((Http2Stream)???).addBytesToWriteWindow(paramLong);
          Unit localUnit = Unit.INSTANCE;
        }
        finally
        {
          localObject4 = finally;
          throw localObject4;
        }
      }
    }
  }
}
