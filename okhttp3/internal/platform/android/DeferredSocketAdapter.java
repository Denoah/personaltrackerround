package okhttp3.internal.platform.android;

import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Protocol;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;

@Metadata(bv={1, 0, 3}, d1={"\000>\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\003\n\002\020\013\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020 \n\002\030\002\n\002\b\007\n\002\030\002\n\000\n\002\030\002\n\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J(\020\b\032\0020\t2\006\020\n\032\0020\0132\b\020\f\032\004\030\0010\0032\f\020\r\032\b\022\004\022\0020\0170\016H\026J\022\020\020\032\004\030\0010\0012\006\020\021\032\0020\013H\002J\022\020\022\032\004\030\0010\0032\006\020\n\032\0020\013H\026J\b\020\023\032\0020\007H\026J\020\020\024\032\0020\0072\006\020\n\032\0020\013H\026J\020\020\025\032\0020\0072\006\020\026\032\0020\027H\026J\022\020\030\032\004\030\0010\0312\006\020\026\032\0020\027H\026R\020\020\005\032\004\030\0010\001X?\016?\006\002\n\000R\016\020\006\032\0020\007X?\016?\006\002\n\000R\016\020\002\032\0020\003X?\004?\006\002\n\000?\006\032"}, d2={"Lokhttp3/internal/platform/android/DeferredSocketAdapter;", "Lokhttp3/internal/platform/android/SocketAdapter;", "socketPackage", "", "(Ljava/lang/String;)V", "delegate", "initialized", "", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "protocols", "", "Lokhttp3/Protocol;", "getDelegate", "actualSSLSocketClass", "getSelectedProtocol", "isSupported", "matchesSocket", "matchesSocketFactory", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "okhttp"}, k=1, mv={1, 1, 16})
public final class DeferredSocketAdapter
  implements SocketAdapter
{
  private SocketAdapter delegate;
  private boolean initialized;
  private final String socketPackage;
  
  public DeferredSocketAdapter(String paramString)
  {
    this.socketPackage = paramString;
  }
  
  private final SocketAdapter getDelegate(SSLSocket paramSSLSocket)
  {
    try
    {
      boolean bool = this.initialized;
      if (!bool)
      {
        try
        {
          localObject1 = paramSSLSocket.getClass();
          do
          {
            localObject2 = ((Class)localObject1).getName();
            StringBuilder localStringBuilder = new java/lang/StringBuilder;
            localStringBuilder.<init>();
            localStringBuilder.append(this.socketPackage);
            localStringBuilder.append(".OpenSSLSocketImpl");
            if (!(Intrinsics.areEqual(localObject2, localStringBuilder.toString()) ^ true)) {
              break;
            }
            localObject1 = ((Class)localObject1).getSuperclass();
            Intrinsics.checkExpressionValueIsNotNull(localObject1, "possibleClass.superclass");
          } while (localObject1 != null);
          localObject1 = new java/lang/AssertionError;
          Object localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          ((StringBuilder)localObject2).append("No OpenSSLSocketImpl superclass of socket of type ");
          ((StringBuilder)localObject2).append(paramSSLSocket);
          ((AssertionError)localObject1).<init>(((StringBuilder)localObject2).toString());
          throw ((Throwable)localObject1);
          paramSSLSocket = new okhttp3/internal/platform/android/AndroidSocketAdapter;
          paramSSLSocket.<init>((Class)localObject1);
          this.delegate = ((SocketAdapter)paramSSLSocket);
        }
        catch (Exception localException)
        {
          Object localObject1 = Platform.Companion.get();
          paramSSLSocket = new java/lang/StringBuilder;
          paramSSLSocket.<init>();
          paramSSLSocket.append("Failed to initialize DeferredSocketAdapter ");
          paramSSLSocket.append(this.socketPackage);
          ((Platform)localObject1).log(paramSSLSocket.toString(), 5, (Throwable)localException);
        }
        this.initialized = true;
      }
      paramSSLSocket = this.delegate;
      return paramSSLSocket;
    }
    finally {}
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<? extends Protocol> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramSSLSocket, "sslSocket");
    Intrinsics.checkParameterIsNotNull(paramList, "protocols");
    SocketAdapter localSocketAdapter = getDelegate(paramSSLSocket);
    if (localSocketAdapter != null) {
      localSocketAdapter.configureTlsExtensions(paramSSLSocket, paramString, paramList);
    }
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkParameterIsNotNull(paramSSLSocket, "sslSocket");
    SocketAdapter localSocketAdapter = getDelegate(paramSSLSocket);
    if (localSocketAdapter != null) {
      paramSSLSocket = localSocketAdapter.getSelectedProtocol(paramSSLSocket);
    } else {
      paramSSLSocket = null;
    }
    return paramSSLSocket;
  }
  
  public boolean isSupported()
  {
    return true;
  }
  
  public boolean matchesSocket(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkParameterIsNotNull(paramSSLSocket, "sslSocket");
    paramSSLSocket = paramSSLSocket.getClass().getName();
    Intrinsics.checkExpressionValueIsNotNull(paramSSLSocket, "sslSocket.javaClass.name");
    return StringsKt.startsWith$default(paramSSLSocket, this.socketPackage, false, 2, null);
  }
  
  public boolean matchesSocketFactory(SSLSocketFactory paramSSLSocketFactory)
  {
    Intrinsics.checkParameterIsNotNull(paramSSLSocketFactory, "sslSocketFactory");
    return false;
  }
  
  public X509TrustManager trustManager(SSLSocketFactory paramSSLSocketFactory)
  {
    Intrinsics.checkParameterIsNotNull(paramSSLSocketFactory, "sslSocketFactory");
    return null;
  }
}
