package okhttp3.logging;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;
import okhttp3.Headers;
import okhttp3.Interceptor;

@Metadata(bv={1, 0, 3}, d1={"\000L\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\"\n\002\020\016\n\000\n\002\030\002\n\002\b\005\n\002\020\013\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\020\b\n\002\b\006\030\0002\0020\001:\002\036\037B\021\b\007\022\b\b\002\020\002\032\0020\003?\006\002\020\004J\020\020\016\032\0020\0172\006\020\020\032\0020\021H\002J\r\020\013\032\0020\tH\007?\006\002\b\022J\020\020\023\032\0020\0242\006\020\025\032\0020\026H\026J\030\020\027\032\0020\0302\006\020\020\032\0020\0212\006\020\031\032\0020\032H\002J\016\020\033\032\0020\0302\006\020\034\032\0020\007J\016\020\035\032\0020\0002\006\020\n\032\0020\tR\024\020\005\032\b\022\004\022\0020\0070\006X?\016?\006\002\n\000R$\020\n\032\0020\t2\006\020\b\032\0020\t@GX?\016?\006\016\n\000\032\004\b\013\020\f\"\004\b\n\020\rR\016\020\002\032\0020\003X?\004?\006\002\n\000?\002\007\n\005\b?F0\001?\006 "}, d2={"Lokhttp3/logging/HttpLoggingInterceptor;", "Lokhttp3/Interceptor;", "logger", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "(Lokhttp3/logging/HttpLoggingInterceptor$Logger;)V", "headersToRedact", "", "", "<set-?>", "Lokhttp3/logging/HttpLoggingInterceptor$Level;", "level", "getLevel", "()Lokhttp3/logging/HttpLoggingInterceptor$Level;", "(Lokhttp3/logging/HttpLoggingInterceptor$Level;)V", "bodyHasUnknownEncoding", "", "headers", "Lokhttp3/Headers;", "-deprecated_level", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "logHeader", "", "i", "", "redactHeader", "name", "setLevel", "Level", "Logger", "okhttp-logging-interceptor"}, k=1, mv={1, 1, 16})
public final class HttpLoggingInterceptor
  implements Interceptor
{
  private volatile Set<String> headersToRedact;
  private volatile Level level;
  private final Logger logger;
  
  public HttpLoggingInterceptor()
  {
    this(null, 1, null);
  }
  
  public HttpLoggingInterceptor(Logger paramLogger)
  {
    this.logger = paramLogger;
    this.headersToRedact = SetsKt.emptySet();
    this.level = Level.NONE;
  }
  
  private final boolean bodyHasUnknownEncoding(Headers paramHeaders)
  {
    paramHeaders = paramHeaders.get("Content-Encoding");
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramHeaders != null)
    {
      bool2 = bool1;
      if (!StringsKt.equals(paramHeaders, "identity", true))
      {
        bool2 = bool1;
        if (!StringsKt.equals(paramHeaders, "gzip", true)) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  private final void logHeader(Headers paramHeaders, int paramInt)
  {
    String str;
    if (this.headersToRedact.contains(paramHeaders.name(paramInt))) {
      str = "??";
    } else {
      str = paramHeaders.value(paramInt);
    }
    Logger localLogger = this.logger;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramHeaders.name(paramInt));
    localStringBuilder.append(": ");
    localStringBuilder.append(str);
    localLogger.log(localStringBuilder.toString());
  }
  
  @Deprecated(level=DeprecationLevel.ERROR, message="moved to var", replaceWith=@ReplaceWith(expression="level", imports={}))
  public final Level -deprecated_level()
  {
    return this.level;
  }
  
  public final Level getLevel()
  {
    return this.level;
  }
  
  /* Error */
  public okhttp3.Response intercept(okhttp3.Interceptor.Chain paramChain)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc -99
    //   3: invokestatic 68	kotlin/jvm/internal/Intrinsics:checkParameterIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_0
    //   7: getfield 85	okhttp3/logging/HttpLoggingInterceptor:level	Lokhttp3/logging/HttpLoggingInterceptor$Level;
    //   10: astore_2
    //   11: aload_1
    //   12: invokeinterface 163 1 0
    //   17: astore_3
    //   18: aload_2
    //   19: getstatic 83	okhttp3/logging/HttpLoggingInterceptor$Level:NONE	Lokhttp3/logging/HttpLoggingInterceptor$Level;
    //   22: if_acmpne +11 -> 33
    //   25: aload_1
    //   26: aload_3
    //   27: invokeinterface 167 2 0
    //   32: areturn
    //   33: aload_2
    //   34: getstatic 170	okhttp3/logging/HttpLoggingInterceptor$Level:BODY	Lokhttp3/logging/HttpLoggingInterceptor$Level;
    //   37: if_acmpne +9 -> 46
    //   40: iconst_1
    //   41: istore 4
    //   43: goto +6 -> 49
    //   46: iconst_0
    //   47: istore 4
    //   49: iload 4
    //   51: ifne +19 -> 70
    //   54: aload_2
    //   55: getstatic 173	okhttp3/logging/HttpLoggingInterceptor$Level:HEADERS	Lokhttp3/logging/HttpLoggingInterceptor$Level;
    //   58: if_acmpne +6 -> 64
    //   61: goto +9 -> 70
    //   64: iconst_0
    //   65: istore 5
    //   67: goto +6 -> 73
    //   70: iconst_1
    //   71: istore 5
    //   73: aload_3
    //   74: invokevirtual 179	okhttp3/Request:body	()Lokhttp3/RequestBody;
    //   77: astore 6
    //   79: aload_1
    //   80: invokeinterface 183 1 0
    //   85: astore_2
    //   86: new 126	java/lang/StringBuilder
    //   89: dup
    //   90: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   93: astore 7
    //   95: aload 7
    //   97: ldc -71
    //   99: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: aload 7
    //   105: aload_3
    //   106: invokevirtual 188	okhttp3/Request:method	()Ljava/lang/String;
    //   109: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: pop
    //   113: aload 7
    //   115: bipush 32
    //   117: invokevirtual 191	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   120: pop
    //   121: aload 7
    //   123: aload_3
    //   124: invokevirtual 195	okhttp3/Request:url	()Lokhttp3/HttpUrl;
    //   127: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   130: pop
    //   131: aload_2
    //   132: ifnull +41 -> 173
    //   135: new 126	java/lang/StringBuilder
    //   138: dup
    //   139: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   142: astore 8
    //   144: aload 8
    //   146: ldc -56
    //   148: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: pop
    //   152: aload 8
    //   154: aload_2
    //   155: invokeinterface 206 1 0
    //   160: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   163: pop
    //   164: aload 8
    //   166: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   169: astore_2
    //   170: goto +6 -> 176
    //   173: ldc -49
    //   175: astore_2
    //   176: aload 7
    //   178: aload_2
    //   179: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   182: pop
    //   183: aload 7
    //   185: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   188: astore 7
    //   190: aload 7
    //   192: astore_2
    //   193: iload 5
    //   195: ifne +55 -> 250
    //   198: aload 7
    //   200: astore_2
    //   201: aload 6
    //   203: ifnull +47 -> 250
    //   206: new 126	java/lang/StringBuilder
    //   209: dup
    //   210: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   213: astore_2
    //   214: aload_2
    //   215: aload 7
    //   217: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: pop
    //   221: aload_2
    //   222: ldc -47
    //   224: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: pop
    //   228: aload_2
    //   229: aload 6
    //   231: invokevirtual 215	okhttp3/RequestBody:contentLength	()J
    //   234: invokevirtual 218	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   237: pop
    //   238: aload_2
    //   239: ldc -36
    //   241: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: pop
    //   245: aload_2
    //   246: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   249: astore_2
    //   250: aload_0
    //   251: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   254: aload_2
    //   255: invokeinterface 141 2 0
    //   260: iload 5
    //   262: ifeq +653 -> 915
    //   265: aload_3
    //   266: invokevirtual 223	okhttp3/Request:headers	()Lokhttp3/Headers;
    //   269: astore_2
    //   270: aload 6
    //   272: ifnull +134 -> 406
    //   275: aload 6
    //   277: invokevirtual 227	okhttp3/RequestBody:contentType	()Lokhttp3/MediaType;
    //   280: astore 9
    //   282: aload 9
    //   284: ifnull +55 -> 339
    //   287: aload_2
    //   288: ldc -27
    //   290: invokevirtual 99	okhttp3/Headers:get	(Ljava/lang/String;)Ljava/lang/String;
    //   293: ifnonnull +46 -> 339
    //   296: aload_0
    //   297: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   300: astore 8
    //   302: new 126	java/lang/StringBuilder
    //   305: dup
    //   306: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   309: astore 7
    //   311: aload 7
    //   313: ldc -25
    //   315: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: pop
    //   319: aload 7
    //   321: aload 9
    //   323: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   326: pop
    //   327: aload 8
    //   329: aload 7
    //   331: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   334: invokeinterface 141 2 0
    //   339: aload 6
    //   341: invokevirtual 215	okhttp3/RequestBody:contentLength	()J
    //   344: ldc2_w 232
    //   347: lcmp
    //   348: ifeq +58 -> 406
    //   351: aload_2
    //   352: ldc -21
    //   354: invokevirtual 99	okhttp3/Headers:get	(Ljava/lang/String;)Ljava/lang/String;
    //   357: ifnonnull +49 -> 406
    //   360: aload_0
    //   361: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   364: astore 8
    //   366: new 126	java/lang/StringBuilder
    //   369: dup
    //   370: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   373: astore 7
    //   375: aload 7
    //   377: ldc -19
    //   379: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   382: pop
    //   383: aload 7
    //   385: aload 6
    //   387: invokevirtual 215	okhttp3/RequestBody:contentLength	()J
    //   390: invokevirtual 218	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   393: pop
    //   394: aload 8
    //   396: aload 7
    //   398: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   401: invokeinterface 141 2 0
    //   406: aload_2
    //   407: invokevirtual 241	okhttp3/Headers:size	()I
    //   410: istore 10
    //   412: iconst_0
    //   413: istore 11
    //   415: iload 11
    //   417: iload 10
    //   419: if_icmpge +16 -> 435
    //   422: aload_0
    //   423: aload_2
    //   424: iload 11
    //   426: invokespecial 243	okhttp3/logging/HttpLoggingInterceptor:logHeader	(Lokhttp3/Headers;I)V
    //   429: iinc 11 1
    //   432: goto -17 -> 415
    //   435: iload 4
    //   437: ifeq +435 -> 872
    //   440: aload 6
    //   442: ifnonnull +6 -> 448
    //   445: goto +427 -> 872
    //   448: aload_0
    //   449: aload_3
    //   450: invokevirtual 223	okhttp3/Request:headers	()Lokhttp3/Headers;
    //   453: invokespecial 245	okhttp3/logging/HttpLoggingInterceptor:bodyHasUnknownEncoding	(Lokhttp3/Headers;)Z
    //   456: ifeq +54 -> 510
    //   459: aload_0
    //   460: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   463: astore 7
    //   465: new 126	java/lang/StringBuilder
    //   468: dup
    //   469: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   472: astore_2
    //   473: aload_2
    //   474: ldc -9
    //   476: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   479: pop
    //   480: aload_2
    //   481: aload_3
    //   482: invokevirtual 188	okhttp3/Request:method	()Ljava/lang/String;
    //   485: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   488: pop
    //   489: aload_2
    //   490: ldc -7
    //   492: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   495: pop
    //   496: aload 7
    //   498: aload_2
    //   499: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   502: invokeinterface 141 2 0
    //   507: goto +408 -> 915
    //   510: aload 6
    //   512: invokevirtual 253	okhttp3/RequestBody:isDuplex	()Z
    //   515: ifeq +57 -> 572
    //   518: aload_0
    //   519: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   522: astore_2
    //   523: new 126	java/lang/StringBuilder
    //   526: dup
    //   527: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   530: astore 7
    //   532: aload 7
    //   534: ldc -9
    //   536: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   539: pop
    //   540: aload 7
    //   542: aload_3
    //   543: invokevirtual 188	okhttp3/Request:method	()Ljava/lang/String;
    //   546: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   549: pop
    //   550: aload 7
    //   552: ldc -1
    //   554: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   557: pop
    //   558: aload_2
    //   559: aload 7
    //   561: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   564: invokeinterface 141 2 0
    //   569: goto +346 -> 915
    //   572: aload 6
    //   574: invokevirtual 258	okhttp3/RequestBody:isOneShot	()Z
    //   577: ifeq +58 -> 635
    //   580: aload_0
    //   581: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   584: astore_2
    //   585: new 126	java/lang/StringBuilder
    //   588: dup
    //   589: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   592: astore 7
    //   594: aload 7
    //   596: ldc -9
    //   598: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   601: pop
    //   602: aload 7
    //   604: aload_3
    //   605: invokevirtual 188	okhttp3/Request:method	()Ljava/lang/String;
    //   608: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   611: pop
    //   612: aload 7
    //   614: ldc_w 260
    //   617: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   620: pop
    //   621: aload_2
    //   622: aload 7
    //   624: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   627: invokeinterface 141 2 0
    //   632: goto +283 -> 915
    //   635: new 262	okio/Buffer
    //   638: dup
    //   639: invokespecial 263	okio/Buffer:<init>	()V
    //   642: astore 7
    //   644: aload 6
    //   646: aload 7
    //   648: checkcast 265	okio/BufferedSink
    //   651: invokevirtual 269	okhttp3/RequestBody:writeTo	(Lokio/BufferedSink;)V
    //   654: aload 6
    //   656: invokevirtual 227	okhttp3/RequestBody:contentType	()Lokhttp3/MediaType;
    //   659: astore_2
    //   660: aload_2
    //   661: ifnull +18 -> 679
    //   664: aload_2
    //   665: getstatic 275	java/nio/charset/StandardCharsets:UTF_8	Ljava/nio/charset/Charset;
    //   668: invokevirtual 281	okhttp3/MediaType:charset	(Ljava/nio/charset/Charset;)Ljava/nio/charset/Charset;
    //   671: astore_2
    //   672: aload_2
    //   673: ifnull +6 -> 679
    //   676: goto +14 -> 690
    //   679: getstatic 275	java/nio/charset/StandardCharsets:UTF_8	Ljava/nio/charset/Charset;
    //   682: astore_2
    //   683: aload_2
    //   684: ldc_w 282
    //   687: invokestatic 285	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   690: aload_0
    //   691: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   694: ldc -49
    //   696: invokeinterface 141 2 0
    //   701: aload 7
    //   703: invokestatic 291	okhttp3/logging/Utf8Kt:isProbablyUtf8	(Lokio/Buffer;)Z
    //   706: ifeq +91 -> 797
    //   709: aload_0
    //   710: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   713: aload 7
    //   715: aload_2
    //   716: invokevirtual 295	okio/Buffer:readString	(Ljava/nio/charset/Charset;)Ljava/lang/String;
    //   719: invokeinterface 141 2 0
    //   724: aload_0
    //   725: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   728: astore_2
    //   729: new 126	java/lang/StringBuilder
    //   732: dup
    //   733: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   736: astore 7
    //   738: aload 7
    //   740: ldc -9
    //   742: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   745: pop
    //   746: aload 7
    //   748: aload_3
    //   749: invokevirtual 188	okhttp3/Request:method	()Ljava/lang/String;
    //   752: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   755: pop
    //   756: aload 7
    //   758: ldc -47
    //   760: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   763: pop
    //   764: aload 7
    //   766: aload 6
    //   768: invokevirtual 215	okhttp3/RequestBody:contentLength	()J
    //   771: invokevirtual 218	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   774: pop
    //   775: aload 7
    //   777: ldc -36
    //   779: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   782: pop
    //   783: aload_2
    //   784: aload 7
    //   786: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   789: invokeinterface 141 2 0
    //   794: goto +121 -> 915
    //   797: aload_0
    //   798: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   801: astore_2
    //   802: new 126	java/lang/StringBuilder
    //   805: dup
    //   806: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   809: astore 7
    //   811: aload 7
    //   813: ldc -9
    //   815: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   818: pop
    //   819: aload 7
    //   821: aload_3
    //   822: invokevirtual 188	okhttp3/Request:method	()Ljava/lang/String;
    //   825: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   828: pop
    //   829: aload 7
    //   831: ldc_w 297
    //   834: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   837: pop
    //   838: aload 7
    //   840: aload 6
    //   842: invokevirtual 215	okhttp3/RequestBody:contentLength	()J
    //   845: invokevirtual 218	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   848: pop
    //   849: aload 7
    //   851: ldc_w 299
    //   854: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   857: pop
    //   858: aload_2
    //   859: aload 7
    //   861: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   864: invokeinterface 141 2 0
    //   869: goto +46 -> 915
    //   872: aload_0
    //   873: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   876: astore_2
    //   877: new 126	java/lang/StringBuilder
    //   880: dup
    //   881: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   884: astore 7
    //   886: aload 7
    //   888: ldc -9
    //   890: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   893: pop
    //   894: aload 7
    //   896: aload_3
    //   897: invokevirtual 188	okhttp3/Request:method	()Ljava/lang/String;
    //   900: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   903: pop
    //   904: aload_2
    //   905: aload 7
    //   907: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   910: invokeinterface 141 2 0
    //   915: invokestatic 304	java/lang/System:nanoTime	()J
    //   918: lstore 12
    //   920: aload_1
    //   921: aload_3
    //   922: invokeinterface 167 2 0
    //   927: astore_3
    //   928: getstatic 310	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
    //   931: invokestatic 304	java/lang/System:nanoTime	()J
    //   934: lload 12
    //   936: lsub
    //   937: invokevirtual 314	java/util/concurrent/TimeUnit:toMillis	(J)J
    //   940: lstore 12
    //   942: aload_3
    //   943: invokevirtual 319	okhttp3/Response:body	()Lokhttp3/ResponseBody;
    //   946: astore 6
    //   948: aload 6
    //   950: ifnonnull +6 -> 956
    //   953: invokestatic 322	kotlin/jvm/internal/Intrinsics:throwNpe	()V
    //   956: aload 6
    //   958: invokevirtual 325	okhttp3/ResponseBody:contentLength	()J
    //   961: lstore 14
    //   963: lload 14
    //   965: ldc2_w 232
    //   968: lcmp
    //   969: ifeq +34 -> 1003
    //   972: new 126	java/lang/StringBuilder
    //   975: dup
    //   976: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   979: astore_1
    //   980: aload_1
    //   981: lload 14
    //   983: invokevirtual 218	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   986: pop
    //   987: aload_1
    //   988: ldc_w 327
    //   991: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   994: pop
    //   995: aload_1
    //   996: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   999: astore_1
    //   1000: goto +7 -> 1007
    //   1003: ldc_w 329
    //   1006: astore_1
    //   1007: aload_0
    //   1008: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   1011: astore 8
    //   1013: new 126	java/lang/StringBuilder
    //   1016: dup
    //   1017: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   1020: astore 7
    //   1022: aload 7
    //   1024: ldc_w 331
    //   1027: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1030: pop
    //   1031: aload 7
    //   1033: aload_3
    //   1034: invokevirtual 334	okhttp3/Response:code	()I
    //   1037: invokevirtual 337	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1040: pop
    //   1041: aload_3
    //   1042: invokevirtual 339	okhttp3/Response:message	()Ljava/lang/String;
    //   1045: checkcast 341	java/lang/CharSequence
    //   1048: invokeinterface 344 1 0
    //   1053: ifne +9 -> 1062
    //   1056: iconst_1
    //   1057: istore 11
    //   1059: goto +6 -> 1065
    //   1062: iconst_0
    //   1063: istore 11
    //   1065: iload 11
    //   1067: ifeq +9 -> 1076
    //   1070: ldc -49
    //   1072: astore_2
    //   1073: goto +39 -> 1112
    //   1076: aload_3
    //   1077: invokevirtual 339	okhttp3/Response:message	()Ljava/lang/String;
    //   1080: astore 9
    //   1082: new 126	java/lang/StringBuilder
    //   1085: dup
    //   1086: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   1089: astore_2
    //   1090: aload_2
    //   1091: bipush 32
    //   1093: invokestatic 350	java/lang/String:valueOf	(C)Ljava/lang/String;
    //   1096: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1099: pop
    //   1100: aload_2
    //   1101: aload 9
    //   1103: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1106: pop
    //   1107: aload_2
    //   1108: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1111: astore_2
    //   1112: aload 7
    //   1114: aload_2
    //   1115: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1118: pop
    //   1119: aload 7
    //   1121: bipush 32
    //   1123: invokevirtual 191	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   1126: pop
    //   1127: aload 7
    //   1129: aload_3
    //   1130: invokevirtual 351	okhttp3/Response:request	()Lokhttp3/Request;
    //   1133: invokevirtual 195	okhttp3/Request:url	()Lokhttp3/HttpUrl;
    //   1136: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1139: pop
    //   1140: aload 7
    //   1142: ldc -47
    //   1144: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1147: pop
    //   1148: aload 7
    //   1150: lload 12
    //   1152: invokevirtual 218	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1155: pop
    //   1156: aload 7
    //   1158: ldc_w 353
    //   1161: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1164: pop
    //   1165: iload 5
    //   1167: ifne +41 -> 1208
    //   1170: new 126	java/lang/StringBuilder
    //   1173: dup
    //   1174: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   1177: astore_2
    //   1178: aload_2
    //   1179: ldc_w 355
    //   1182: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1185: pop
    //   1186: aload_2
    //   1187: aload_1
    //   1188: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1191: pop
    //   1192: aload_2
    //   1193: ldc_w 357
    //   1196: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1199: pop
    //   1200: aload_2
    //   1201: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1204: astore_1
    //   1205: goto +6 -> 1211
    //   1208: ldc -49
    //   1210: astore_1
    //   1211: aload 7
    //   1213: aload_1
    //   1214: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1217: pop
    //   1218: aload 7
    //   1220: bipush 41
    //   1222: invokevirtual 191	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   1225: pop
    //   1226: aload 8
    //   1228: aload 7
    //   1230: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1233: invokeinterface 141 2 0
    //   1238: iload 5
    //   1240: ifeq +502 -> 1742
    //   1243: aload_3
    //   1244: invokevirtual 358	okhttp3/Response:headers	()Lokhttp3/Headers;
    //   1247: astore 8
    //   1249: aload 8
    //   1251: invokevirtual 241	okhttp3/Headers:size	()I
    //   1254: istore 11
    //   1256: iconst_0
    //   1257: istore 5
    //   1259: iload 5
    //   1261: iload 11
    //   1263: if_icmpge +17 -> 1280
    //   1266: aload_0
    //   1267: aload 8
    //   1269: iload 5
    //   1271: invokespecial 243	okhttp3/logging/HttpLoggingInterceptor:logHeader	(Lokhttp3/Headers;I)V
    //   1274: iinc 5 1
    //   1277: goto -18 -> 1259
    //   1280: iload 4
    //   1282: ifeq +448 -> 1730
    //   1285: aload_3
    //   1286: invokestatic 364	okhttp3/internal/http/HttpHeaders:promisesBody	(Lokhttp3/Response;)Z
    //   1289: ifne +6 -> 1295
    //   1292: goto +438 -> 1730
    //   1295: aload_0
    //   1296: aload_3
    //   1297: invokevirtual 358	okhttp3/Response:headers	()Lokhttp3/Headers;
    //   1300: invokespecial 245	okhttp3/logging/HttpLoggingInterceptor:bodyHasUnknownEncoding	(Lokhttp3/Headers;)Z
    //   1303: ifeq +18 -> 1321
    //   1306: aload_0
    //   1307: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   1310: ldc_w 366
    //   1313: invokeinterface 141 2 0
    //   1318: goto +424 -> 1742
    //   1321: aload 6
    //   1323: invokevirtual 370	okhttp3/ResponseBody:source	()Lokio/BufferedSource;
    //   1326: astore_1
    //   1327: aload_1
    //   1328: ldc2_w 371
    //   1331: invokeinterface 377 3 0
    //   1336: pop
    //   1337: aload_1
    //   1338: invokeinterface 381 1 0
    //   1343: astore 7
    //   1345: aconst_null
    //   1346: checkcast 383	java/lang/Long
    //   1349: astore_2
    //   1350: aload 7
    //   1352: astore_1
    //   1353: ldc 109
    //   1355: aload 8
    //   1357: ldc 93
    //   1359: invokevirtual 99	okhttp3/Headers:get	(Ljava/lang/String;)Ljava/lang/String;
    //   1362: iconst_1
    //   1363: invokestatic 107	kotlin/text/StringsKt:equals	(Ljava/lang/String;Ljava/lang/String;Z)Z
    //   1366: ifeq +85 -> 1451
    //   1369: aload 7
    //   1371: invokevirtual 385	okio/Buffer:size	()J
    //   1374: invokestatic 388	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   1377: astore_2
    //   1378: new 390	okio/GzipSource
    //   1381: dup
    //   1382: aload 7
    //   1384: invokevirtual 393	okio/Buffer:clone	()Lokio/Buffer;
    //   1387: checkcast 395	okio/Source
    //   1390: invokespecial 398	okio/GzipSource:<init>	(Lokio/Source;)V
    //   1393: checkcast 400	java/io/Closeable
    //   1396: astore 7
    //   1398: aconst_null
    //   1399: checkcast 402	java/lang/Throwable
    //   1402: astore 8
    //   1404: aload 7
    //   1406: checkcast 390	okio/GzipSource
    //   1409: astore 9
    //   1411: new 262	okio/Buffer
    //   1414: astore_1
    //   1415: aload_1
    //   1416: invokespecial 263	okio/Buffer:<init>	()V
    //   1419: aload_1
    //   1420: aload 9
    //   1422: checkcast 395	okio/Source
    //   1425: invokevirtual 406	okio/Buffer:writeAll	(Lokio/Source;)J
    //   1428: pop2
    //   1429: aload 7
    //   1431: aload 8
    //   1433: invokestatic 412	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   1436: goto +15 -> 1451
    //   1439: astore_1
    //   1440: aload_1
    //   1441: athrow
    //   1442: astore_2
    //   1443: aload 7
    //   1445: aload_1
    //   1446: invokestatic 412	kotlin/io/CloseableKt:closeFinally	(Ljava/io/Closeable;Ljava/lang/Throwable;)V
    //   1449: aload_2
    //   1450: athrow
    //   1451: aload 6
    //   1453: invokevirtual 413	okhttp3/ResponseBody:contentType	()Lokhttp3/MediaType;
    //   1456: astore 7
    //   1458: aload 7
    //   1460: ifnull +21 -> 1481
    //   1463: aload 7
    //   1465: getstatic 275	java/nio/charset/StandardCharsets:UTF_8	Ljava/nio/charset/Charset;
    //   1468: invokevirtual 281	okhttp3/MediaType:charset	(Ljava/nio/charset/Charset;)Ljava/nio/charset/Charset;
    //   1471: astore 7
    //   1473: aload 7
    //   1475: ifnull +6 -> 1481
    //   1478: goto +16 -> 1494
    //   1481: getstatic 275	java/nio/charset/StandardCharsets:UTF_8	Ljava/nio/charset/Charset;
    //   1484: astore 7
    //   1486: aload 7
    //   1488: ldc_w 282
    //   1491: invokestatic 285	kotlin/jvm/internal/Intrinsics:checkExpressionValueIsNotNull	(Ljava/lang/Object;Ljava/lang/String;)V
    //   1494: aload_1
    //   1495: invokestatic 291	okhttp3/logging/Utf8Kt:isProbablyUtf8	(Lokio/Buffer;)Z
    //   1498: ifne +66 -> 1564
    //   1501: aload_0
    //   1502: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   1505: ldc -49
    //   1507: invokeinterface 141 2 0
    //   1512: aload_0
    //   1513: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   1516: astore 7
    //   1518: new 126	java/lang/StringBuilder
    //   1521: dup
    //   1522: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   1525: astore_2
    //   1526: aload_2
    //   1527: ldc_w 415
    //   1530: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1533: pop
    //   1534: aload_2
    //   1535: aload_1
    //   1536: invokevirtual 385	okio/Buffer:size	()J
    //   1539: invokevirtual 218	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1542: pop
    //   1543: aload_2
    //   1544: ldc_w 299
    //   1547: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1550: pop
    //   1551: aload 7
    //   1553: aload_2
    //   1554: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1557: invokeinterface 141 2 0
    //   1562: aload_3
    //   1563: areturn
    //   1564: lload 14
    //   1566: lconst_0
    //   1567: lcmp
    //   1568: ifeq +32 -> 1600
    //   1571: aload_0
    //   1572: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   1575: ldc -49
    //   1577: invokeinterface 141 2 0
    //   1582: aload_0
    //   1583: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   1586: aload_1
    //   1587: invokevirtual 393	okio/Buffer:clone	()Lokio/Buffer;
    //   1590: aload 7
    //   1592: invokevirtual 295	okio/Buffer:readString	(Ljava/nio/charset/Charset;)Ljava/lang/String;
    //   1595: invokeinterface 141 2 0
    //   1600: aload_2
    //   1601: ifnull +77 -> 1678
    //   1604: aload_0
    //   1605: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   1608: astore 7
    //   1610: new 126	java/lang/StringBuilder
    //   1613: dup
    //   1614: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   1617: astore 6
    //   1619: aload 6
    //   1621: ldc_w 417
    //   1624: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1627: pop
    //   1628: aload 6
    //   1630: aload_1
    //   1631: invokevirtual 385	okio/Buffer:size	()J
    //   1634: invokevirtual 218	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1637: pop
    //   1638: aload 6
    //   1640: ldc_w 419
    //   1643: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1646: pop
    //   1647: aload 6
    //   1649: aload_2
    //   1650: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1653: pop
    //   1654: aload 6
    //   1656: ldc_w 421
    //   1659: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1662: pop
    //   1663: aload 7
    //   1665: aload 6
    //   1667: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1670: invokeinterface 141 2 0
    //   1675: goto +67 -> 1742
    //   1678: aload_0
    //   1679: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   1682: astore 7
    //   1684: new 126	java/lang/StringBuilder
    //   1687: dup
    //   1688: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   1691: astore_2
    //   1692: aload_2
    //   1693: ldc_w 417
    //   1696: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1699: pop
    //   1700: aload_2
    //   1701: aload_1
    //   1702: invokevirtual 385	okio/Buffer:size	()J
    //   1705: invokevirtual 218	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1708: pop
    //   1709: aload_2
    //   1710: ldc -36
    //   1712: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1715: pop
    //   1716: aload 7
    //   1718: aload_2
    //   1719: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1722: invokeinterface 141 2 0
    //   1727: goto +15 -> 1742
    //   1730: aload_0
    //   1731: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   1734: ldc_w 423
    //   1737: invokeinterface 141 2 0
    //   1742: aload_3
    //   1743: areturn
    //   1744: astore_2
    //   1745: aload_0
    //   1746: getfield 72	okhttp3/logging/HttpLoggingInterceptor:logger	Lokhttp3/logging/HttpLoggingInterceptor$Logger;
    //   1749: astore 7
    //   1751: new 126	java/lang/StringBuilder
    //   1754: dup
    //   1755: invokespecial 127	java/lang/StringBuilder:<init>	()V
    //   1758: astore_1
    //   1759: aload_1
    //   1760: ldc_w 425
    //   1763: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1766: pop
    //   1767: aload_1
    //   1768: aload_2
    //   1769: invokevirtual 198	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1772: pop
    //   1773: aload 7
    //   1775: aload_1
    //   1776: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1779: invokeinterface 141 2 0
    //   1784: aload_2
    //   1785: checkcast 402	java/lang/Throwable
    //   1788: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1789	0	this	HttpLoggingInterceptor
    //   0	1789	1	paramChain	okhttp3.Interceptor.Chain
    //   10	1368	2	localObject1	Object
    //   1442	8	2	localObject2	Object
    //   1525	194	2	localStringBuilder	StringBuilder
    //   1744	41	2	localException	Exception
    //   17	1726	3	localObject3	Object
    //   41	1240	4	i	int
    //   65	1210	5	j	int
    //   77	1589	6	localObject4	Object
    //   93	1681	7	localObject5	Object
    //   142	1290	8	localObject6	Object
    //   280	1141	9	localObject7	Object
    //   410	10	10	k	int
    //   413	851	11	m	int
    //   918	233	12	l1	long
    //   961	604	14	l2	long
    // Exception table:
    //   from	to	target	type
    //   1404	1429	1439	finally
    //   1440	1442	1442	finally
    //   920	928	1744	java/lang/Exception
  }
  
  public final void level(Level paramLevel)
  {
    Intrinsics.checkParameterIsNotNull(paramLevel, "<set-?>");
    this.level = paramLevel;
  }
  
  public final void redactHeader(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    TreeSet localTreeSet = new TreeSet(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
    Collection localCollection = (Collection)localTreeSet;
    CollectionsKt.addAll(localCollection, (Iterable)this.headersToRedact);
    localCollection.add(paramString);
    this.headersToRedact = ((Set)localTreeSet);
  }
  
  public final HttpLoggingInterceptor setLevel(Level paramLevel)
  {
    Intrinsics.checkParameterIsNotNull(paramLevel, "level");
    HttpLoggingInterceptor localHttpLoggingInterceptor = (HttpLoggingInterceptor)this;
    localHttpLoggingInterceptor.level = paramLevel;
    return localHttpLoggingInterceptor;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\f\n\002\030\002\n\002\020\020\n\002\b\006\b?\001\030\0002\b\022\004\022\0020\0000\001B\007\b\002?\006\002\020\002j\002\b\003j\002\b\004j\002\b\005j\002\b\006?\006\007"}, d2={"Lokhttp3/logging/HttpLoggingInterceptor$Level;", "", "(Ljava/lang/String;I)V", "NONE", "BASIC", "HEADERS", "BODY", "okhttp-logging-interceptor"}, k=1, mv={1, 1, 16})
  public static enum Level
  {
    static
    {
      Level localLevel1 = new Level("NONE", 0);
      NONE = localLevel1;
      Level localLevel2 = new Level("BASIC", 1);
      BASIC = localLevel2;
      Level localLevel3 = new Level("HEADERS", 2);
      HEADERS = localLevel3;
      Level localLevel4 = new Level("BODY", 3);
      BODY = localLevel4;
      $VALUES = new Level[] { localLevel1, localLevel2, localLevel3, localLevel4 };
    }
    
    private Level() {}
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\020\016\n\002\b\002\bf\030\000 \0062\0020\001:\001\006J\020\020\002\032\0020\0032\006\020\004\032\0020\005H&?\002\007\n\005\b?F0\001?\006\007"}, d2={"Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "", "log", "", "message", "", "Companion", "okhttp-logging-interceptor"}, k=1, mv={1, 1, 16})
  public static abstract interface Logger
  {
    public static final Companion Companion = new Companion(null);
    public static final Logger DEFAULT = (Logger)new HttpLoggingInterceptor.Logger.Companion.DEFAULT.1();
    
    public abstract void log(String paramString);
    
    @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\026\020\003\032\0020\0048\006X?\004?\001\000?\006\002\n\000?\006\001?\002\007\n\005\b?F0\001?\006\005"}, d2={"Lokhttp3/logging/HttpLoggingInterceptor$Logger$Companion;", "", "()V", "DEFAULT", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "okhttp-logging-interceptor"}, k=1, mv={1, 1, 16})
    public static final class Companion
    {
      private Companion() {}
    }
  }
}
