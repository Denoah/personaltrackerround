package io.fabric.sdk.android.services.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;

public class HttpRequest
{
  private static final String BOUNDARY = "00content0boundary00";
  public static final String CHARSET_UTF8 = "UTF-8";
  private static ConnectionFactory CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
  public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
  public static final String CONTENT_TYPE_JSON = "application/json";
  private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data; boundary=00content0boundary00";
  private static final String CRLF = "\r\n";
  private static final String[] EMPTY_STRINGS = new String[0];
  public static final String ENCODING_GZIP = "gzip";
  public static final String HEADER_ACCEPT = "Accept";
  public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
  public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
  public static final String HEADER_AUTHORIZATION = "Authorization";
  public static final String HEADER_CACHE_CONTROL = "Cache-Control";
  public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
  public static final String HEADER_CONTENT_LENGTH = "Content-Length";
  public static final String HEADER_CONTENT_TYPE = "Content-Type";
  public static final String HEADER_DATE = "Date";
  public static final String HEADER_ETAG = "ETag";
  public static final String HEADER_EXPIRES = "Expires";
  public static final String HEADER_IF_NONE_MATCH = "If-None-Match";
  public static final String HEADER_LAST_MODIFIED = "Last-Modified";
  public static final String HEADER_LOCATION = "Location";
  public static final String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";
  public static final String HEADER_REFERER = "Referer";
  public static final String HEADER_SERVER = "Server";
  public static final String HEADER_USER_AGENT = "User-Agent";
  public static final String METHOD_DELETE = "DELETE";
  public static final String METHOD_GET = "GET";
  public static final String METHOD_HEAD = "HEAD";
  public static final String METHOD_OPTIONS = "OPTIONS";
  public static final String METHOD_POST = "POST";
  public static final String METHOD_PUT = "PUT";
  public static final String METHOD_TRACE = "TRACE";
  public static final String PARAM_CHARSET = "charset";
  private int bufferSize = 8192;
  private HttpURLConnection connection = null;
  private boolean form;
  private String httpProxyHost;
  private int httpProxyPort;
  private boolean ignoreCloseExceptions = true;
  private boolean multipart;
  private RequestOutputStream output;
  private final String requestMethod;
  private boolean uncompress = false;
  public final URL url;
  
  public HttpRequest(CharSequence paramCharSequence, String paramString)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      URL localURL = new java/net/URL;
      localURL.<init>(paramCharSequence.toString());
      this.url = localURL;
      this.requestMethod = paramString;
      return;
    }
    catch (MalformedURLException paramCharSequence)
    {
      throw new HttpRequestException(paramCharSequence);
    }
  }
  
  public HttpRequest(URL paramURL, String paramString)
    throws HttpRequest.HttpRequestException
  {
    this.url = paramURL;
    this.requestMethod = paramString;
  }
  
  private static StringBuilder addParamPrefix(String paramString, StringBuilder paramStringBuilder)
  {
    int i = paramString.indexOf('?');
    int j = paramStringBuilder.length() - 1;
    if (i == -1) {
      paramStringBuilder.append('?');
    } else if ((i < j) && (paramString.charAt(j) != '&')) {
      paramStringBuilder.append('&');
    }
    return paramStringBuilder;
  }
  
  private static StringBuilder addPathSeparator(String paramString, StringBuilder paramStringBuilder)
  {
    if (paramString.indexOf(':') + 2 == paramString.lastIndexOf('/')) {
      paramStringBuilder.append('/');
    }
    return paramStringBuilder;
  }
  
  public static String append(CharSequence paramCharSequence, Map<?, ?> paramMap)
  {
    Object localObject = paramCharSequence.toString();
    paramCharSequence = (CharSequence)localObject;
    if (paramMap != null) {
      if (paramMap.isEmpty())
      {
        paramCharSequence = (CharSequence)localObject;
      }
      else
      {
        paramCharSequence = new StringBuilder((String)localObject);
        addPathSeparator((String)localObject, paramCharSequence);
        addParamPrefix((String)localObject, paramCharSequence);
        paramMap = paramMap.entrySet().iterator();
        localObject = (Map.Entry)paramMap.next();
        paramCharSequence.append(((Map.Entry)localObject).getKey().toString());
        paramCharSequence.append('=');
        localObject = ((Map.Entry)localObject).getValue();
        if (localObject != null) {
          paramCharSequence.append(localObject);
        }
        while (paramMap.hasNext())
        {
          paramCharSequence.append('&');
          localObject = (Map.Entry)paramMap.next();
          paramCharSequence.append(((Map.Entry)localObject).getKey().toString());
          paramCharSequence.append('=');
          localObject = ((Map.Entry)localObject).getValue();
          if (localObject != null) {
            paramCharSequence.append(localObject);
          }
        }
        paramCharSequence = paramCharSequence.toString();
      }
    }
    return paramCharSequence;
  }
  
  public static String append(CharSequence paramCharSequence, Object... paramVarArgs)
  {
    Object localObject = paramCharSequence.toString();
    if ((paramVarArgs != null) && (paramVarArgs.length != 0))
    {
      int i = paramVarArgs.length;
      int j = 2;
      if (i % 2 == 0)
      {
        paramCharSequence = new StringBuilder((String)localObject);
        addPathSeparator((String)localObject, paramCharSequence);
        addParamPrefix((String)localObject, paramCharSequence);
        paramCharSequence.append(paramVarArgs[0]);
        paramCharSequence.append('=');
        localObject = paramVarArgs[1];
        i = j;
        if (localObject != null) {
          paramCharSequence.append(localObject);
        }
        for (i = j; i < paramVarArgs.length; i += 2)
        {
          paramCharSequence.append('&');
          paramCharSequence.append(paramVarArgs[i]);
          paramCharSequence.append('=');
          localObject = paramVarArgs[(i + 1)];
          if (localObject != null) {
            paramCharSequence.append(localObject);
          }
        }
        return paramCharSequence.toString();
      }
      throw new IllegalArgumentException("Must specify an even number of parameter names/values");
    }
    return localObject;
  }
  
  private HttpURLConnection createConnection()
  {
    try
    {
      HttpURLConnection localHttpURLConnection;
      if (this.httpProxyHost != null) {
        localHttpURLConnection = CONNECTION_FACTORY.create(this.url, createProxy());
      } else {
        localHttpURLConnection = CONNECTION_FACTORY.create(this.url);
      }
      localHttpURLConnection.setRequestMethod(this.requestMethod);
      return localHttpURLConnection;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  private Proxy createProxy()
  {
    return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.httpProxyHost, this.httpProxyPort));
  }
  
  public static HttpRequest delete(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "DELETE");
  }
  
  public static HttpRequest delete(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = append(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = encode(paramMap);
    }
    return delete(paramCharSequence);
  }
  
  public static HttpRequest delete(CharSequence paramCharSequence, boolean paramBoolean, Object... paramVarArgs)
  {
    paramVarArgs = append(paramCharSequence, paramVarArgs);
    paramCharSequence = paramVarArgs;
    if (paramBoolean) {
      paramCharSequence = encode(paramVarArgs);
    }
    return delete(paramCharSequence);
  }
  
  public static HttpRequest delete(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "DELETE");
  }
  
  /* Error */
  public static String encode(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    // Byte code:
    //   0: new 191	java/net/URL
    //   3: dup
    //   4: aload_0
    //   5: invokeinterface 197 1 0
    //   10: invokespecial 200	java/net/URL:<init>	(Ljava/lang/String;)V
    //   13: astore_1
    //   14: aload_1
    //   15: invokevirtual 360	java/net/URL:getHost	()Ljava/lang/String;
    //   18: astore_2
    //   19: aload_1
    //   20: invokevirtual 363	java/net/URL:getPort	()I
    //   23: istore_3
    //   24: aload_2
    //   25: astore_0
    //   26: iload_3
    //   27: iconst_m1
    //   28: if_icmpeq +38 -> 66
    //   31: new 224	java/lang/StringBuilder
    //   34: dup
    //   35: invokespecial 364	java/lang/StringBuilder:<init>	()V
    //   38: astore_0
    //   39: aload_0
    //   40: aload_2
    //   41: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   44: pop
    //   45: aload_0
    //   46: bipush 58
    //   48: invokevirtual 232	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   51: pop
    //   52: aload_0
    //   53: iload_3
    //   54: invokestatic 369	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   57: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   60: pop
    //   61: aload_0
    //   62: invokevirtual 287	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   65: astore_0
    //   66: new 371	java/net/URI
    //   69: astore_2
    //   70: aload_2
    //   71: aload_1
    //   72: invokevirtual 374	java/net/URL:getProtocol	()Ljava/lang/String;
    //   75: aload_0
    //   76: aload_1
    //   77: invokevirtual 377	java/net/URL:getPath	()Ljava/lang/String;
    //   80: aload_1
    //   81: invokevirtual 380	java/net/URL:getQuery	()Ljava/lang/String;
    //   84: aload_1
    //   85: invokevirtual 383	java/net/URL:getRef	()Ljava/lang/String;
    //   88: invokespecial 386	java/net/URI:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   91: aload_2
    //   92: invokevirtual 389	java/net/URI:toASCIIString	()Ljava/lang/String;
    //   95: astore_2
    //   96: aload_2
    //   97: bipush 63
    //   99: invokevirtual 222	java/lang/String:indexOf	(I)I
    //   102: istore_3
    //   103: aload_2
    //   104: astore_0
    //   105: iload_3
    //   106: ifle +68 -> 174
    //   109: iinc 3 1
    //   112: aload_2
    //   113: astore_0
    //   114: iload_3
    //   115: aload_2
    //   116: invokevirtual 390	java/lang/String:length	()I
    //   119: if_icmpge +55 -> 174
    //   122: new 224	java/lang/StringBuilder
    //   125: astore_0
    //   126: aload_0
    //   127: invokespecial 364	java/lang/StringBuilder:<init>	()V
    //   130: aload_0
    //   131: aload_2
    //   132: iconst_0
    //   133: iload_3
    //   134: invokevirtual 394	java/lang/String:substring	(II)Ljava/lang/String;
    //   137: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   140: pop
    //   141: aload_0
    //   142: aload_2
    //   143: iload_3
    //   144: invokevirtual 396	java/lang/String:substring	(I)Ljava/lang/String;
    //   147: ldc_w 398
    //   150: ldc_w 400
    //   153: invokevirtual 404	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   156: ldc_w 406
    //   159: ldc_w 408
    //   162: invokevirtual 404	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   165: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   168: pop
    //   169: aload_0
    //   170: invokevirtual 287	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   173: astore_0
    //   174: aload_0
    //   175: areturn
    //   176: astore_2
    //   177: new 299	java/io/IOException
    //   180: dup
    //   181: ldc_w 410
    //   184: invokespecial 411	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   187: astore_0
    //   188: aload_0
    //   189: aload_2
    //   190: invokevirtual 415	java/io/IOException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   193: pop
    //   194: new 36	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   197: dup
    //   198: aload_0
    //   199: invokespecial 207	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   202: athrow
    //   203: astore_0
    //   204: new 36	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   207: dup
    //   208: aload_0
    //   209: invokespecial 207	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   212: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	213	0	paramCharSequence	CharSequence
    //   13	72	1	localURL	URL
    //   18	125	2	localObject	Object
    //   176	14	2	localURISyntaxException	java.net.URISyntaxException
    //   23	121	3	i	int
    // Exception table:
    //   from	to	target	type
    //   66	103	176	java/net/URISyntaxException
    //   114	174	176	java/net/URISyntaxException
    //   0	14	203	java/io/IOException
  }
  
  public static HttpRequest get(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "GET");
  }
  
  public static HttpRequest get(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = append(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = encode(paramMap);
    }
    return get(paramCharSequence);
  }
  
  public static HttpRequest get(CharSequence paramCharSequence, boolean paramBoolean, Object... paramVarArgs)
  {
    paramVarArgs = append(paramCharSequence, paramVarArgs);
    paramCharSequence = paramVarArgs;
    if (paramBoolean) {
      paramCharSequence = encode(paramVarArgs);
    }
    return get(paramCharSequence);
  }
  
  public static HttpRequest get(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "GET");
  }
  
  private static String getValidCharset(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      return paramString;
    }
    return "UTF-8";
  }
  
  public static HttpRequest head(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "HEAD");
  }
  
  public static HttpRequest head(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = append(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = encode(paramMap);
    }
    return head(paramCharSequence);
  }
  
  public static HttpRequest head(CharSequence paramCharSequence, boolean paramBoolean, Object... paramVarArgs)
  {
    paramVarArgs = append(paramCharSequence, paramVarArgs);
    paramCharSequence = paramVarArgs;
    if (paramBoolean) {
      paramCharSequence = encode(paramVarArgs);
    }
    return head(paramCharSequence);
  }
  
  public static HttpRequest head(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "HEAD");
  }
  
  public static void keepAlive(boolean paramBoolean)
  {
    setProperty("http.keepAlive", Boolean.toString(paramBoolean));
  }
  
  public static void nonProxyHosts(String... paramVarArgs)
  {
    if ((paramVarArgs != null) && (paramVarArgs.length > 0))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      int i = paramVarArgs.length - 1;
      for (int j = 0; j < i; j++)
      {
        localStringBuilder.append(paramVarArgs[j]);
        localStringBuilder.append('|');
      }
      localStringBuilder.append(paramVarArgs[i]);
      setProperty("http.nonProxyHosts", localStringBuilder.toString());
    }
    else
    {
      setProperty("http.nonProxyHosts", null);
    }
  }
  
  public static HttpRequest options(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "OPTIONS");
  }
  
  public static HttpRequest options(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "OPTIONS");
  }
  
  public static HttpRequest post(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "POST");
  }
  
  public static HttpRequest post(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = append(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = encode(paramMap);
    }
    return post(paramCharSequence);
  }
  
  public static HttpRequest post(CharSequence paramCharSequence, boolean paramBoolean, Object... paramVarArgs)
  {
    paramVarArgs = append(paramCharSequence, paramVarArgs);
    paramCharSequence = paramVarArgs;
    if (paramBoolean) {
      paramCharSequence = encode(paramVarArgs);
    }
    return post(paramCharSequence);
  }
  
  public static HttpRequest post(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "POST");
  }
  
  public static void proxyHost(String paramString)
  {
    setProperty("http.proxyHost", paramString);
    setProperty("https.proxyHost", paramString);
  }
  
  public static void proxyPort(int paramInt)
  {
    String str = Integer.toString(paramInt);
    setProperty("http.proxyPort", str);
    setProperty("https.proxyPort", str);
  }
  
  public static HttpRequest put(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "PUT");
  }
  
  public static HttpRequest put(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    paramMap = append(paramCharSequence, paramMap);
    paramCharSequence = paramMap;
    if (paramBoolean) {
      paramCharSequence = encode(paramMap);
    }
    return put(paramCharSequence);
  }
  
  public static HttpRequest put(CharSequence paramCharSequence, boolean paramBoolean, Object... paramVarArgs)
  {
    paramVarArgs = append(paramCharSequence, paramVarArgs);
    paramCharSequence = paramVarArgs;
    if (paramBoolean) {
      paramCharSequence = encode(paramVarArgs);
    }
    return put(paramCharSequence);
  }
  
  public static HttpRequest put(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "PUT");
  }
  
  public static void setConnectionFactory(ConnectionFactory paramConnectionFactory)
  {
    if (paramConnectionFactory == null) {
      CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
    } else {
      CONNECTION_FACTORY = paramConnectionFactory;
    }
  }
  
  private static String setProperty(String paramString1, final String paramString2)
  {
    if (paramString2 != null) {
      paramString1 = new PrivilegedAction()
      {
        public String run()
        {
          return System.setProperty(this.val$name, paramString2);
        }
      };
    } else {
      paramString1 = new PrivilegedAction()
      {
        public String run()
        {
          return System.clearProperty(this.val$name);
        }
      };
    }
    return (String)AccessController.doPrivileged(paramString1);
  }
  
  public static HttpRequest trace(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "TRACE");
  }
  
  public static HttpRequest trace(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "TRACE");
  }
  
  public HttpRequest accept(String paramString)
  {
    return header("Accept", paramString);
  }
  
  public HttpRequest acceptCharset(String paramString)
  {
    return header("Accept-Charset", paramString);
  }
  
  public HttpRequest acceptEncoding(String paramString)
  {
    return header("Accept-Encoding", paramString);
  }
  
  public HttpRequest acceptGzipEncoding()
  {
    return acceptEncoding("gzip");
  }
  
  public HttpRequest acceptJson()
  {
    return accept("application/json");
  }
  
  public HttpRequest authorization(String paramString)
  {
    return header("Authorization", paramString);
  }
  
  public boolean badRequest()
    throws HttpRequest.HttpRequestException
  {
    boolean bool;
    if (400 == code()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public HttpRequest basic(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append("Basic ");
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append(paramString1);
    localStringBuilder2.append(':');
    localStringBuilder2.append(paramString2);
    localStringBuilder1.append(Base64.encode(localStringBuilder2.toString()));
    return authorization(localStringBuilder1.toString());
  }
  
  public HttpRequest body(AtomicReference<String> paramAtomicReference)
    throws HttpRequest.HttpRequestException
  {
    paramAtomicReference.set(body());
    return this;
  }
  
  public HttpRequest body(AtomicReference<String> paramAtomicReference, String paramString)
    throws HttpRequest.HttpRequestException
  {
    paramAtomicReference.set(body(paramString));
    return this;
  }
  
  public String body()
    throws HttpRequest.HttpRequestException
  {
    return body(charset());
  }
  
  public String body(String paramString)
    throws HttpRequest.HttpRequestException
  {
    ByteArrayOutputStream localByteArrayOutputStream = byteStream();
    try
    {
      copy(buffer(), localByteArrayOutputStream);
      paramString = localByteArrayOutputStream.toString(getValidCharset(paramString));
      return paramString;
    }
    catch (IOException paramString)
    {
      throw new HttpRequestException(paramString);
    }
  }
  
  public BufferedInputStream buffer()
    throws HttpRequest.HttpRequestException
  {
    return new BufferedInputStream(stream(), this.bufferSize);
  }
  
  public int bufferSize()
  {
    return this.bufferSize;
  }
  
  public HttpRequest bufferSize(int paramInt)
  {
    if (paramInt >= 1)
    {
      this.bufferSize = paramInt;
      return this;
    }
    throw new IllegalArgumentException("Size must be greater than zero");
  }
  
  public BufferedReader bufferedReader()
    throws HttpRequest.HttpRequestException
  {
    return bufferedReader(charset());
  }
  
  public BufferedReader bufferedReader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    return new BufferedReader(reader(paramString), this.bufferSize);
  }
  
  protected ByteArrayOutputStream byteStream()
  {
    int i = contentLength();
    if (i > 0) {
      return new ByteArrayOutputStream(i);
    }
    return new ByteArrayOutputStream();
  }
  
  public byte[] bytes()
    throws HttpRequest.HttpRequestException
  {
    ByteArrayOutputStream localByteArrayOutputStream = byteStream();
    try
    {
      copy(buffer(), localByteArrayOutputStream);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public String cacheControl()
  {
    return header("Cache-Control");
  }
  
  public String charset()
  {
    return parameter("Content-Type", "charset");
  }
  
  public HttpRequest chunk(int paramInt)
  {
    getConnection().setChunkedStreamingMode(paramInt);
    return this;
  }
  
  protected HttpRequest closeOutput()
    throws IOException
  {
    RequestOutputStream localRequestOutputStream = this.output;
    if (localRequestOutputStream == null) {
      return this;
    }
    if (this.multipart) {
      localRequestOutputStream.write("\r\n--00content0boundary00--\r\n");
    }
    if (this.ignoreCloseExceptions) {}
    try
    {
      this.output.close();
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
    this.output.close();
    this.output = null;
    return this;
  }
  
  protected HttpRequest closeOutputQuietly()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      HttpRequest localHttpRequest = closeOutput();
      return localHttpRequest;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public int code()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      closeOutput();
      int i = getConnection().getResponseCode();
      return i;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public HttpRequest code(AtomicInteger paramAtomicInteger)
    throws HttpRequest.HttpRequestException
  {
    paramAtomicInteger.set(code());
    return this;
  }
  
  public HttpRequest connectTimeout(int paramInt)
  {
    getConnection().setConnectTimeout(paramInt);
    return this;
  }
  
  public String contentEncoding()
  {
    return header("Content-Encoding");
  }
  
  public int contentLength()
  {
    return intHeader("Content-Length");
  }
  
  public HttpRequest contentLength(int paramInt)
  {
    getConnection().setFixedLengthStreamingMode(paramInt);
    return this;
  }
  
  public HttpRequest contentLength(String paramString)
  {
    return contentLength(Integer.parseInt(paramString));
  }
  
  public HttpRequest contentType(String paramString)
  {
    return contentType(paramString, null);
  }
  
  public HttpRequest contentType(String paramString1, String paramString2)
  {
    if ((paramString2 != null) && (paramString2.length() > 0))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append("; charset=");
      localStringBuilder.append(paramString2);
      return header("Content-Type", localStringBuilder.toString());
    }
    return header("Content-Type", paramString1);
  }
  
  public String contentType()
  {
    return header("Content-Type");
  }
  
  protected HttpRequest copy(final InputStream paramInputStream, final OutputStream paramOutputStream)
    throws IOException
  {
    (HttpRequest)new CloseOperation(paramInputStream, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        byte[] arrayOfByte = new byte[HttpRequest.this.bufferSize];
        for (;;)
        {
          int i = paramInputStream.read(arrayOfByte);
          if (i == -1) {
            break;
          }
          paramOutputStream.write(arrayOfByte, 0, i);
        }
        return HttpRequest.this;
      }
    }.call();
  }
  
  protected HttpRequest copy(final Reader paramReader, final Writer paramWriter)
    throws IOException
  {
    (HttpRequest)new CloseOperation(paramReader, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        char[] arrayOfChar = new char[HttpRequest.this.bufferSize];
        for (;;)
        {
          int i = paramReader.read(arrayOfChar);
          if (i == -1) {
            break;
          }
          paramWriter.write(arrayOfChar, 0, i);
        }
        return HttpRequest.this;
      }
    }.call();
  }
  
  public boolean created()
    throws HttpRequest.HttpRequestException
  {
    boolean bool;
    if (201 == code()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public long date()
  {
    return dateHeader("Date");
  }
  
  public long dateHeader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    return dateHeader(paramString, -1L);
  }
  
  public long dateHeader(String paramString, long paramLong)
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderFieldDate(paramString, paramLong);
  }
  
  public HttpRequest disconnect()
  {
    getConnection().disconnect();
    return this;
  }
  
  public String eTag()
  {
    return header("ETag");
  }
  
  public long expires()
  {
    return dateHeader("Expires");
  }
  
  public HttpRequest followRedirects(boolean paramBoolean)
  {
    getConnection().setInstanceFollowRedirects(paramBoolean);
    return this;
  }
  
  public HttpRequest form(Object paramObject1, Object paramObject2)
    throws HttpRequest.HttpRequestException
  {
    return form(paramObject1, paramObject2, "UTF-8");
  }
  
  public HttpRequest form(Object paramObject1, Object paramObject2, String paramString)
    throws HttpRequest.HttpRequestException
  {
    boolean bool = this.form ^ true;
    if (bool)
    {
      contentType("application/x-www-form-urlencoded", paramString);
      this.form = true;
    }
    paramString = getValidCharset(paramString);
    try
    {
      openOutput();
      if (!bool) {
        this.output.write(38);
      }
      this.output.write(URLEncoder.encode(paramObject1.toString(), paramString));
      this.output.write(61);
      if (paramObject2 != null) {
        this.output.write(URLEncoder.encode(paramObject2.toString(), paramString));
      }
      return this;
    }
    catch (IOException paramObject1)
    {
      throw new HttpRequestException(paramObject1);
    }
  }
  
  public HttpRequest form(Map.Entry<?, ?> paramEntry)
    throws HttpRequest.HttpRequestException
  {
    return form(paramEntry, "UTF-8");
  }
  
  public HttpRequest form(Map.Entry<?, ?> paramEntry, String paramString)
    throws HttpRequest.HttpRequestException
  {
    return form(paramEntry.getKey(), paramEntry.getValue(), paramString);
  }
  
  public HttpRequest form(Map<?, ?> paramMap)
    throws HttpRequest.HttpRequestException
  {
    return form(paramMap, "UTF-8");
  }
  
  public HttpRequest form(Map<?, ?> paramMap, String paramString)
    throws HttpRequest.HttpRequestException
  {
    if (!paramMap.isEmpty())
    {
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext()) {
        form((Map.Entry)paramMap.next(), paramString);
      }
    }
    return this;
  }
  
  public HttpURLConnection getConnection()
  {
    if (this.connection == null) {
      this.connection = createConnection();
    }
    return this.connection;
  }
  
  protected String getParam(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString1.length() != 0))
    {
      int i = paramString1.length();
      int j = paramString1.indexOf(';') + 1;
      if ((j != 0) && (j != i))
      {
        int k = paramString1.indexOf(';', j);
        int m = j;
        int n = k;
        if (k == -1) {}
        for (m = j;; m = j)
        {
          n = i;
          do
          {
            if (m >= n) {
              break;
            }
            j = paramString1.indexOf('=', m);
            if ((j != -1) && (j < n) && (paramString2.equals(paramString1.substring(m, j).trim())))
            {
              String str = paramString1.substring(j + 1, n).trim();
              m = str.length();
              if (m != 0)
              {
                if ((m > 2) && ('"' == str.charAt(0)))
                {
                  n = m - 1;
                  if ('"' == str.charAt(n)) {
                    return str.substring(1, n);
                  }
                }
                return str;
              }
            }
            j = n + 1;
            k = paramString1.indexOf(';', j);
            m = j;
            n = k;
          } while (k != -1);
        }
      }
    }
    return null;
  }
  
  protected Map<String, String> getParams(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      int i = paramString.length();
      int j = paramString.indexOf(';') + 1;
      if ((j != 0) && (j != i))
      {
        int k = paramString.indexOf(';', j);
        int m = k;
        if (k == -1) {
          m = i;
        }
        LinkedHashMap localLinkedHashMap = new LinkedHashMap();
        while (j < m)
        {
          k = paramString.indexOf('=', j);
          if ((k != -1) && (k < m))
          {
            String str1 = paramString.substring(j, k).trim();
            if (str1.length() > 0)
            {
              String str2 = paramString.substring(k + 1, m).trim();
              j = str2.length();
              if (j != 0)
              {
                if ((j > 2) && ('"' == str2.charAt(0)))
                {
                  j--;
                  if ('"' == str2.charAt(j))
                  {
                    localLinkedHashMap.put(str1, str2.substring(1, j));
                    break label206;
                  }
                }
                localLinkedHashMap.put(str1, str2);
              }
            }
          }
          label206:
          k = m + 1;
          int n = paramString.indexOf(';', k);
          j = k;
          m = n;
          if (n == -1)
          {
            m = i;
            j = k;
          }
        }
        return localLinkedHashMap;
      }
      return Collections.emptyMap();
    }
    return Collections.emptyMap();
  }
  
  public HttpRequest header(String paramString, Number paramNumber)
  {
    if (paramNumber != null) {
      paramNumber = paramNumber.toString();
    } else {
      paramNumber = null;
    }
    return header(paramString, paramNumber);
  }
  
  public HttpRequest header(String paramString1, String paramString2)
  {
    getConnection().setRequestProperty(paramString1, paramString2);
    return this;
  }
  
  public HttpRequest header(Map.Entry<String, String> paramEntry)
  {
    return header((String)paramEntry.getKey(), (String)paramEntry.getValue());
  }
  
  public String header(String paramString)
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderField(paramString);
  }
  
  public HttpRequest headers(Map<String, String> paramMap)
  {
    if (!paramMap.isEmpty())
    {
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext()) {
        header((Map.Entry)paramMap.next());
      }
    }
    return this;
  }
  
  public Map<String, List<String>> headers()
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderFields();
  }
  
  public String[] headers(String paramString)
  {
    Map localMap = headers();
    if ((localMap != null) && (!localMap.isEmpty()))
    {
      paramString = (List)localMap.get(paramString);
      if ((paramString != null) && (!paramString.isEmpty())) {
        return (String[])paramString.toArray(new String[paramString.size()]);
      }
      return EMPTY_STRINGS;
    }
    return EMPTY_STRINGS;
  }
  
  public HttpRequest ifModifiedSince(long paramLong)
  {
    getConnection().setIfModifiedSince(paramLong);
    return this;
  }
  
  public HttpRequest ifNoneMatch(String paramString)
  {
    return header("If-None-Match", paramString);
  }
  
  public HttpRequest ignoreCloseExceptions(boolean paramBoolean)
  {
    this.ignoreCloseExceptions = paramBoolean;
    return this;
  }
  
  public boolean ignoreCloseExceptions()
  {
    return this.ignoreCloseExceptions;
  }
  
  public int intHeader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    return intHeader(paramString, -1);
  }
  
  public int intHeader(String paramString, int paramInt)
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderFieldInt(paramString, paramInt);
  }
  
  public boolean isBodyEmpty()
    throws HttpRequest.HttpRequestException
  {
    boolean bool;
    if (contentLength() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public long lastModified()
  {
    return dateHeader("Last-Modified");
  }
  
  public String location()
  {
    return header("Location");
  }
  
  public String message()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      closeOutput();
      String str = getConnection().getResponseMessage();
      return str;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public String method()
  {
    return getConnection().getRequestMethod();
  }
  
  public boolean notFound()
    throws HttpRequest.HttpRequestException
  {
    boolean bool;
    if (404 == code()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean notModified()
    throws HttpRequest.HttpRequestException
  {
    boolean bool;
    if (304 == code()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public boolean ok()
    throws HttpRequest.HttpRequestException
  {
    boolean bool;
    if (200 == code()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected HttpRequest openOutput()
    throws IOException
  {
    if (this.output != null) {
      return this;
    }
    getConnection().setDoOutput(true);
    String str = getParam(getConnection().getRequestProperty("Content-Type"), "charset");
    this.output = new RequestOutputStream(getConnection().getOutputStream(), str, this.bufferSize);
    return this;
  }
  
  public String parameter(String paramString1, String paramString2)
  {
    return getParam(header(paramString1), paramString2);
  }
  
  public Map<String, String> parameters(String paramString)
  {
    return getParams(header(paramString));
  }
  
  public HttpRequest part(String paramString, File paramFile)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString, null, paramFile);
  }
  
  public HttpRequest part(String paramString, InputStream paramInputStream)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString, null, null, paramInputStream);
  }
  
  public HttpRequest part(String paramString, Number paramNumber)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString, null, paramNumber);
  }
  
  public HttpRequest part(String paramString1, String paramString2)
  {
    return part(paramString1, null, paramString2);
  }
  
  public HttpRequest part(String paramString1, String paramString2, File paramFile)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString1, paramString2, null, paramFile);
  }
  
  public HttpRequest part(String paramString1, String paramString2, Number paramNumber)
    throws HttpRequest.HttpRequestException
  {
    if (paramNumber != null) {
      paramNumber = paramNumber.toString();
    } else {
      paramNumber = null;
    }
    return part(paramString1, paramString2, paramNumber);
  }
  
  public HttpRequest part(String paramString1, String paramString2, String paramString3)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString1, paramString2, null, paramString3);
  }
  
  /* Error */
  public HttpRequest part(String paramString1, String paramString2, String paramString3, File paramFile)
    throws HttpRequest.HttpRequestException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aconst_null
    //   4: astore 6
    //   6: aload 6
    //   8: astore 7
    //   10: new 531	java/io/BufferedInputStream
    //   13: astore 8
    //   15: aload 6
    //   17: astore 7
    //   19: new 820	java/io/FileInputStream
    //   22: astore 9
    //   24: aload 6
    //   26: astore 7
    //   28: aload 9
    //   30: aload 4
    //   32: invokespecial 823	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   35: aload 6
    //   37: astore 7
    //   39: aload 8
    //   41: aload 9
    //   43: invokespecial 826	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   46: aload_0
    //   47: aload_1
    //   48: aload_2
    //   49: aload_3
    //   50: aload 8
    //   52: invokevirtual 806	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/io/InputStream;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   55: astore_1
    //   56: aload 8
    //   58: invokevirtual 829	java/io/InputStream:close	()V
    //   61: aload_1
    //   62: areturn
    //   63: astore_1
    //   64: aload 8
    //   66: astore 7
    //   68: goto +38 -> 106
    //   71: astore_2
    //   72: aload 8
    //   74: astore_1
    //   75: goto +11 -> 86
    //   78: astore_1
    //   79: goto +27 -> 106
    //   82: astore_2
    //   83: aload 5
    //   85: astore_1
    //   86: aload_1
    //   87: astore 7
    //   89: new 36	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   92: astore_3
    //   93: aload_1
    //   94: astore 7
    //   96: aload_3
    //   97: aload_2
    //   98: invokespecial 207	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   101: aload_1
    //   102: astore 7
    //   104: aload_3
    //   105: athrow
    //   106: aload 7
    //   108: ifnull +8 -> 116
    //   111: aload 7
    //   113: invokevirtual 829	java/io/InputStream:close	()V
    //   116: aload_1
    //   117: athrow
    //   118: astore_2
    //   119: goto -58 -> 61
    //   122: astore_2
    //   123: goto -7 -> 116
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	126	0	this	HttpRequest
    //   0	126	1	paramString1	String
    //   0	126	2	paramString2	String
    //   0	126	3	paramString3	String
    //   0	126	4	paramFile	File
    //   1	83	5	localObject1	Object
    //   4	32	6	localObject2	Object
    //   8	104	7	localObject3	Object
    //   13	60	8	localBufferedInputStream	BufferedInputStream
    //   22	20	9	localFileInputStream	FileInputStream
    // Exception table:
    //   from	to	target	type
    //   46	56	63	finally
    //   46	56	71	java/io/IOException
    //   10	15	78	finally
    //   19	24	78	finally
    //   28	35	78	finally
    //   39	46	78	finally
    //   89	93	78	finally
    //   96	101	78	finally
    //   104	106	78	finally
    //   10	15	82	java/io/IOException
    //   19	24	82	java/io/IOException
    //   28	35	82	java/io/IOException
    //   39	46	82	java/io/IOException
    //   56	61	118	java/io/IOException
    //   111	116	122	java/io/IOException
  }
  
  public HttpRequest part(String paramString1, String paramString2, String paramString3, InputStream paramInputStream)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      startPart();
      writePartHeader(paramString1, paramString2, paramString3);
      copy(paramInputStream, this.output);
      return this;
    }
    catch (IOException paramString1)
    {
      throw new HttpRequestException(paramString1);
    }
  }
  
  public HttpRequest part(String paramString1, String paramString2, String paramString3, String paramString4)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      startPart();
      writePartHeader(paramString1, paramString2, paramString3);
      this.output.write(paramString4);
      return this;
    }
    catch (IOException paramString1)
    {
      throw new HttpRequestException(paramString1);
    }
  }
  
  public HttpRequest partHeader(String paramString1, String paramString2)
    throws HttpRequest.HttpRequestException
  {
    return send(paramString1).send(": ").send(paramString2).send("\r\n");
  }
  
  public HttpRequest proxyAuthorization(String paramString)
  {
    return header("Proxy-Authorization", paramString);
  }
  
  public HttpRequest proxyBasic(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append("Basic ");
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append(paramString1);
    localStringBuilder2.append(':');
    localStringBuilder2.append(paramString2);
    localStringBuilder1.append(Base64.encode(localStringBuilder2.toString()));
    return proxyAuthorization(localStringBuilder1.toString());
  }
  
  public HttpRequest readTimeout(int paramInt)
  {
    getConnection().setReadTimeout(paramInt);
    return this;
  }
  
  public InputStreamReader reader()
    throws HttpRequest.HttpRequestException
  {
    return reader(charset());
  }
  
  public InputStreamReader reader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      paramString = new InputStreamReader(stream(), getValidCharset(paramString));
      return paramString;
    }
    catch (UnsupportedEncodingException paramString)
    {
      throw new HttpRequestException(paramString);
    }
  }
  
  public HttpRequest receive(final File paramFile)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      FileOutputStream localFileOutputStream = new java/io/FileOutputStream;
      localFileOutputStream.<init>(paramFile);
      paramFile = new BufferedOutputStream(localFileOutputStream, this.bufferSize);
      (HttpRequest)new CloseOperation(paramFile, this.ignoreCloseExceptions)
      {
        protected HttpRequest run()
          throws HttpRequest.HttpRequestException, IOException
        {
          return HttpRequest.this.receive(paramFile);
        }
      }.call();
    }
    catch (FileNotFoundException paramFile)
    {
      throw new HttpRequestException(paramFile);
    }
  }
  
  public HttpRequest receive(OutputStream paramOutputStream)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      paramOutputStream = copy(buffer(), paramOutputStream);
      return paramOutputStream;
    }
    catch (IOException paramOutputStream)
    {
      throw new HttpRequestException(paramOutputStream);
    }
  }
  
  public HttpRequest receive(PrintStream paramPrintStream)
    throws HttpRequest.HttpRequestException
  {
    return receive(paramPrintStream);
  }
  
  public HttpRequest receive(final Writer paramWriter)
    throws HttpRequest.HttpRequestException
  {
    final BufferedReader localBufferedReader = bufferedReader();
    (HttpRequest)new CloseOperation(localBufferedReader, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        return HttpRequest.this.copy(localBufferedReader, paramWriter);
      }
    }.call();
  }
  
  public HttpRequest receive(final Appendable paramAppendable)
    throws HttpRequest.HttpRequestException
  {
    final BufferedReader localBufferedReader = bufferedReader();
    (HttpRequest)new CloseOperation(localBufferedReader, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        CharBuffer localCharBuffer = CharBuffer.allocate(HttpRequest.this.bufferSize);
        for (;;)
        {
          int i = localBufferedReader.read(localCharBuffer);
          if (i == -1) {
            break;
          }
          localCharBuffer.rewind();
          paramAppendable.append(localCharBuffer, 0, i);
          localCharBuffer.rewind();
        }
        return HttpRequest.this;
      }
    }.call();
  }
  
  public HttpRequest referer(String paramString)
  {
    return header("Referer", paramString);
  }
  
  public HttpRequest send(File paramFile)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      FileInputStream localFileInputStream = new java/io/FileInputStream;
      localFileInputStream.<init>(paramFile);
      paramFile = new BufferedInputStream(localFileInputStream);
      return send(paramFile);
    }
    catch (FileNotFoundException paramFile)
    {
      throw new HttpRequestException(paramFile);
    }
  }
  
  public HttpRequest send(InputStream paramInputStream)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      copy(paramInputStream, this.output);
      return this;
    }
    catch (IOException paramInputStream)
    {
      throw new HttpRequestException(paramInputStream);
    }
  }
  
  public HttpRequest send(final Reader paramReader)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      Object localObject = this.output;
      localObject = new OutputStreamWriter((OutputStream)localObject, ((RequestOutputStream)localObject).encoder.charset());
      (HttpRequest)new FlushOperation((Flushable)localObject)
      {
        protected HttpRequest run()
          throws IOException
        {
          return HttpRequest.this.copy(paramReader, this.val$writer);
        }
      }.call();
    }
    catch (IOException paramReader)
    {
      throw new HttpRequestException(paramReader);
    }
  }
  
  public HttpRequest send(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      this.output.write(paramCharSequence.toString());
      return this;
    }
    catch (IOException paramCharSequence)
    {
      throw new HttpRequestException(paramCharSequence);
    }
  }
  
  public HttpRequest send(byte[] paramArrayOfByte)
    throws HttpRequest.HttpRequestException
  {
    return send(new ByteArrayInputStream(paramArrayOfByte));
  }
  
  public String server()
  {
    return header("Server");
  }
  
  public boolean serverError()
    throws HttpRequest.HttpRequestException
  {
    boolean bool;
    if (500 == code()) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected HttpRequest startPart()
    throws IOException
  {
    if (!this.multipart)
    {
      this.multipart = true;
      contentType("multipart/form-data; boundary=00content0boundary00").openOutput();
      this.output.write("--00content0boundary00\r\n");
    }
    else
    {
      this.output.write("\r\n--00content0boundary00\r\n");
    }
    return this;
  }
  
  public InputStream stream()
    throws HttpRequest.HttpRequestException
  {
    if (code() < 400)
    {
      try
      {
        InputStream localInputStream1 = getConnection().getInputStream();
      }
      catch (IOException localIOException1)
      {
        throw new HttpRequestException(localIOException1);
      }
    }
    else
    {
      InputStream localInputStream2 = getConnection().getErrorStream();
      Object localObject = localInputStream2;
      if (localInputStream2 == null) {
        try
        {
          localObject = getConnection().getInputStream();
        }
        catch (IOException localIOException2)
        {
          throw new HttpRequestException(localIOException2);
        }
      }
    }
    if ((this.uncompress) && ("gzip".equals(contentEncoding()))) {
      try
      {
        GZIPInputStream localGZIPInputStream = new GZIPInputStream(localIOException2);
        return localGZIPInputStream;
      }
      catch (IOException localIOException3)
      {
        throw new HttpRequestException(localIOException3);
      }
    }
    return localIOException3;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(method());
    localStringBuilder.append(' ');
    localStringBuilder.append(url());
    return localStringBuilder.toString();
  }
  
  public HttpRequest trustAllCerts()
    throws HttpRequest.HttpRequestException
  {
    return this;
  }
  
  public HttpRequest trustAllHosts()
  {
    return this;
  }
  
  public HttpRequest uncompress(boolean paramBoolean)
  {
    this.uncompress = paramBoolean;
    return this;
  }
  
  public URL url()
  {
    return getConnection().getURL();
  }
  
  public HttpRequest useCaches(boolean paramBoolean)
  {
    getConnection().setUseCaches(paramBoolean);
    return this;
  }
  
  public HttpRequest useProxy(String paramString, int paramInt)
  {
    if (this.connection == null)
    {
      this.httpProxyHost = paramString;
      this.httpProxyPort = paramInt;
      return this;
    }
    throw new IllegalStateException("The connection has already been created. This method must be called before reading or writing to the request.");
  }
  
  public HttpRequest userAgent(String paramString)
  {
    return header("User-Agent", paramString);
  }
  
  protected HttpRequest writePartHeader(String paramString1, String paramString2)
    throws IOException
  {
    return writePartHeader(paramString1, paramString2, null);
  }
  
  protected HttpRequest writePartHeader(String paramString1, String paramString2, String paramString3)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("form-data; name=\"");
    localStringBuilder.append(paramString1);
    if (paramString2 != null)
    {
      localStringBuilder.append("\"; filename=\"");
      localStringBuilder.append(paramString2);
    }
    localStringBuilder.append('"');
    partHeader("Content-Disposition", localStringBuilder.toString());
    if (paramString3 != null) {
      partHeader("Content-Type", paramString3);
    }
    return send("\r\n");
  }
  
  public OutputStreamWriter writer()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(this.output, this.output.encoder.charset());
      return localOutputStreamWriter;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }
  
  public static class Base64
  {
    private static final byte EQUALS_SIGN = 61;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    private static final byte[] _STANDARD_ALPHABET = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
    
    private Base64() {}
    
    public static String encode(String paramString)
    {
      try
      {
        byte[] arrayOfByte = paramString.getBytes("US-ASCII");
        paramString = arrayOfByte;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        paramString = paramString.getBytes();
      }
      return encodeBytes(paramString);
    }
    
    private static byte[] encode3to4(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    {
      byte[] arrayOfByte = _STANDARD_ALPHABET;
      int i = 0;
      int j;
      if (paramInt2 > 0) {
        j = paramArrayOfByte1[paramInt1] << 24 >>> 8;
      } else {
        j = 0;
      }
      int k;
      if (paramInt2 > 1) {
        k = paramArrayOfByte1[(paramInt1 + 1)] << 24 >>> 16;
      } else {
        k = 0;
      }
      if (paramInt2 > 2) {
        i = paramArrayOfByte1[(paramInt1 + 2)] << 24 >>> 24;
      }
      paramInt1 = j | k | i;
      if (paramInt2 != 1)
      {
        if (paramInt2 != 2)
        {
          if (paramInt2 != 3) {
            return paramArrayOfByte2;
          }
          paramArrayOfByte2[paramInt3] = ((byte)arrayOfByte[(paramInt1 >>> 18)]);
          paramArrayOfByte2[(paramInt3 + 1)] = ((byte)arrayOfByte[(paramInt1 >>> 12 & 0x3F)]);
          paramArrayOfByte2[(paramInt3 + 2)] = ((byte)arrayOfByte[(paramInt1 >>> 6 & 0x3F)]);
          paramArrayOfByte2[(paramInt3 + 3)] = ((byte)arrayOfByte[(paramInt1 & 0x3F)]);
          return paramArrayOfByte2;
        }
        paramArrayOfByte2[paramInt3] = ((byte)arrayOfByte[(paramInt1 >>> 18)]);
        paramArrayOfByte2[(paramInt3 + 1)] = ((byte)arrayOfByte[(paramInt1 >>> 12 & 0x3F)]);
        paramArrayOfByte2[(paramInt3 + 2)] = ((byte)arrayOfByte[(paramInt1 >>> 6 & 0x3F)]);
        paramArrayOfByte2[(paramInt3 + 3)] = ((byte)61);
        return paramArrayOfByte2;
      }
      paramArrayOfByte2[paramInt3] = ((byte)arrayOfByte[(paramInt1 >>> 18)]);
      paramArrayOfByte2[(paramInt3 + 1)] = ((byte)arrayOfByte[(paramInt1 >>> 12 & 0x3F)]);
      paramArrayOfByte2[(paramInt3 + 2)] = ((byte)61);
      paramArrayOfByte2[(paramInt3 + 3)] = ((byte)61);
      return paramArrayOfByte2;
    }
    
    public static String encodeBytes(byte[] paramArrayOfByte)
    {
      return encodeBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
    }
    
    public static String encodeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      paramArrayOfByte = encodeBytesToBytes(paramArrayOfByte, paramInt1, paramInt2);
      try
      {
        String str = new String(paramArrayOfByte, "US-ASCII");
        return str;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
      return new String(paramArrayOfByte);
    }
    
    public static byte[] encodeBytesToBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      if (paramArrayOfByte != null)
      {
        if (paramInt1 >= 0)
        {
          if (paramInt2 >= 0)
          {
            if (paramInt1 + paramInt2 <= paramArrayOfByte.length)
            {
              int i = paramInt2 / 3;
              int j = 4;
              if (paramInt2 % 3 <= 0) {
                j = 0;
              }
              int k = i * 4 + j;
              byte[] arrayOfByte = new byte[k];
              i = 0;
              for (j = i; i < paramInt2 - 2; j += 4)
              {
                encode3to4(paramArrayOfByte, i + paramInt1, 3, arrayOfByte, j);
                i += 3;
              }
              int m = j;
              if (i < paramInt2)
              {
                encode3to4(paramArrayOfByte, paramInt1 + i, paramInt2 - i, arrayOfByte, j);
                m = j + 4;
              }
              if (m <= k - 1)
              {
                paramArrayOfByte = new byte[m];
                System.arraycopy(arrayOfByte, 0, paramArrayOfByte, 0, m);
                return paramArrayOfByte;
              }
              return arrayOfByte;
            }
            throw new IllegalArgumentException(String.format(Locale.ENGLISH, "Cannot have offset of %d and length of %d with array of length %d", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramArrayOfByte.length) }));
          }
          paramArrayOfByte = new StringBuilder();
          paramArrayOfByte.append("Cannot have length offset: ");
          paramArrayOfByte.append(paramInt2);
          throw new IllegalArgumentException(paramArrayOfByte.toString());
        }
        paramArrayOfByte = new StringBuilder();
        paramArrayOfByte.append("Cannot have negative offset: ");
        paramArrayOfByte.append(paramInt1);
        throw new IllegalArgumentException(paramArrayOfByte.toString());
      }
      throw new NullPointerException("Cannot serialize a null array.");
    }
  }
  
  protected static abstract class CloseOperation<V>
    extends HttpRequest.Operation<V>
  {
    private final Closeable closeable;
    private final boolean ignoreCloseExceptions;
    
    protected CloseOperation(Closeable paramCloseable, boolean paramBoolean)
    {
      this.closeable = paramCloseable;
      this.ignoreCloseExceptions = paramBoolean;
    }
    
    protected void done()
      throws IOException
    {
      Closeable localCloseable = this.closeable;
      if ((localCloseable instanceof Flushable)) {
        ((Flushable)localCloseable).flush();
      }
      if (this.ignoreCloseExceptions) {}
      try
      {
        this.closeable.close();
      }
      catch (IOException localIOException)
      {
        for (;;) {}
      }
      this.closeable.close();
    }
  }
  
  public static abstract interface ConnectionFactory
  {
    public static final ConnectionFactory DEFAULT = new ConnectionFactory()
    {
      public HttpURLConnection create(URL paramAnonymousURL)
        throws IOException
      {
        return (HttpURLConnection)paramAnonymousURL.openConnection();
      }
      
      public HttpURLConnection create(URL paramAnonymousURL, Proxy paramAnonymousProxy)
        throws IOException
      {
        return (HttpURLConnection)paramAnonymousURL.openConnection(paramAnonymousProxy);
      }
    };
    
    public abstract HttpURLConnection create(URL paramURL)
      throws IOException;
    
    public abstract HttpURLConnection create(URL paramURL, Proxy paramProxy)
      throws IOException;
  }
  
  protected static abstract class FlushOperation<V>
    extends HttpRequest.Operation<V>
  {
    private final Flushable flushable;
    
    protected FlushOperation(Flushable paramFlushable)
    {
      this.flushable = paramFlushable;
    }
    
    protected void done()
      throws IOException
    {
      this.flushable.flush();
    }
  }
  
  public static class HttpRequestException
    extends RuntimeException
  {
    private static final long serialVersionUID = -1170466989781746231L;
    
    protected HttpRequestException(IOException paramIOException)
    {
      super();
    }
    
    public IOException getCause()
    {
      return (IOException)super.getCause();
    }
  }
  
  protected static abstract class Operation<V>
    implements Callable<V>
  {
    protected Operation() {}
    
    /* Error */
    public V call()
      throws HttpRequest.HttpRequestException
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 24	io/fabric/sdk/android/services/network/HttpRequest$Operation:run	()Ljava/lang/Object;
      //   4: astore_1
      //   5: aload_0
      //   6: invokevirtual 27	io/fabric/sdk/android/services/network/HttpRequest$Operation:done	()V
      //   9: aload_1
      //   10: areturn
      //   11: astore_1
      //   12: new 19	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   15: dup
      //   16: aload_1
      //   17: invokespecial 30	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   20: athrow
      //   21: astore_1
      //   22: iconst_0
      //   23: istore_2
      //   24: goto +21 -> 45
      //   27: astore_3
      //   28: new 19	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   31: astore_1
      //   32: aload_1
      //   33: aload_3
      //   34: invokespecial 30	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   37: aload_1
      //   38: athrow
      //   39: astore_1
      //   40: aload_1
      //   41: athrow
      //   42: astore_1
      //   43: iconst_1
      //   44: istore_2
      //   45: aload_0
      //   46: invokevirtual 27	io/fabric/sdk/android/services/network/HttpRequest$Operation:done	()V
      //   49: goto +17 -> 66
      //   52: astore_3
      //   53: iload_2
      //   54: ifne +12 -> 66
      //   57: new 19	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   60: dup
      //   61: aload_3
      //   62: invokespecial 30	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   65: athrow
      //   66: aload_1
      //   67: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	68	0	this	Operation
      //   4	6	1	localObject1	Object
      //   11	6	1	localIOException1	IOException
      //   21	1	1	localObject2	Object
      //   31	7	1	localHttpRequestException1	HttpRequest.HttpRequestException
      //   39	2	1	localHttpRequestException2	HttpRequest.HttpRequestException
      //   42	25	1	localObject3	Object
      //   23	31	2	i	int
      //   27	7	3	localIOException2	IOException
      //   52	10	3	localIOException3	IOException
      // Exception table:
      //   from	to	target	type
      //   5	9	11	java/io/IOException
      //   0	5	21	finally
      //   0	5	27	java/io/IOException
      //   0	5	39	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   28	39	42	finally
      //   40	42	42	finally
      //   45	49	52	java/io/IOException
    }
    
    protected abstract void done()
      throws IOException;
    
    protected abstract V run()
      throws HttpRequest.HttpRequestException, IOException;
  }
  
  public static class RequestOutputStream
    extends BufferedOutputStream
  {
    private final CharsetEncoder encoder;
    
    public RequestOutputStream(OutputStream paramOutputStream, String paramString, int paramInt)
    {
      super(paramInt);
      this.encoder = Charset.forName(HttpRequest.getValidCharset(paramString)).newEncoder();
    }
    
    public RequestOutputStream write(String paramString)
      throws IOException
    {
      paramString = this.encoder.encode(CharBuffer.wrap(paramString));
      super.write(paramString.array(), 0, paramString.limit());
      return this;
    }
  }
}
