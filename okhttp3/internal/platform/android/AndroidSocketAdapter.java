package okhttp3.internal.platform.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.AndroidPlatform;
import okhttp3.internal.platform.AndroidPlatform.Companion;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.platform.Platform.Companion;

@Metadata(bv={1, 0, 3}, d1={"\000N\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020\002\n\002\b\002\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\002\b\002\b\026\030\000 \0352\0020\001:\001\035B\025\022\016\020\002\032\n\022\006\b\000\022\0020\0040\003?\006\002\020\005J(\020\f\032\0020\r2\006\020\016\032\0020\0042\b\020\017\032\004\030\0010\0202\f\020\021\032\b\022\004\022\0020\0230\022H\026J\022\020\024\032\004\030\0010\0202\006\020\016\032\0020\004H\026J\b\020\025\032\0020\026H\026J\020\020\027\032\0020\0262\006\020\016\032\0020\004H\026J\020\020\030\032\0020\0262\006\020\031\032\0020\032H\026J\022\020\033\032\004\030\0010\0342\006\020\031\032\0020\032H\026R\026\020\006\032\n \b*\004\030\0010\0070\007X?\004?\006\002\n\000R\026\020\t\032\n \b*\004\030\0010\0070\007X?\004?\006\002\n\000R\026\020\n\032\n \b*\004\030\0010\0070\007X?\004?\006\002\n\000R\016\020\013\032\0020\007X?\004?\006\002\n\000R\026\020\002\032\n\022\006\b\000\022\0020\0040\003X?\004?\006\002\n\000?\006\036"}, d2={"Lokhttp3/internal/platform/android/AndroidSocketAdapter;", "Lokhttp3/internal/platform/android/SocketAdapter;", "sslSocketClass", "Ljava/lang/Class;", "Ljavax/net/ssl/SSLSocket;", "(Ljava/lang/Class;)V", "getAlpnSelectedProtocol", "Ljava/lang/reflect/Method;", "kotlin.jvm.PlatformType", "setAlpnProtocols", "setHostname", "setUseSessionTickets", "configureTlsExtensions", "", "sslSocket", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "isSupported", "", "matchesSocket", "matchesSocketFactory", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "trustManager", "Ljavax/net/ssl/X509TrustManager;", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public class AndroidSocketAdapter
  implements SocketAdapter
{
  public static final Companion Companion = new Companion(null);
  private final Method getAlpnSelectedProtocol;
  private final Method setAlpnProtocols;
  private final Method setHostname;
  private final Method setUseSessionTickets;
  private final Class<? super SSLSocket> sslSocketClass;
  
  public AndroidSocketAdapter(Class<? super SSLSocket> paramClass)
  {
    this.sslSocketClass = paramClass;
    paramClass = paramClass.getDeclaredMethod("setUseSessionTickets", new Class[] { Boolean.TYPE });
    Intrinsics.checkExpressionValueIsNotNull(paramClass, "sslSocketClass.getDeclar…:class.javaPrimitiveType)");
    this.setUseSessionTickets = paramClass;
    this.setHostname = this.sslSocketClass.getMethod("setHostname", new Class[] { String.class });
    this.getAlpnSelectedProtocol = this.sslSocketClass.getMethod("getAlpnSelectedProtocol", new Class[0]);
    this.setAlpnProtocols = this.sslSocketClass.getMethod("setAlpnProtocols", new Class[] { [B.class });
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<? extends Protocol> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramSSLSocket, "sslSocket");
    Intrinsics.checkParameterIsNotNull(paramList, "protocols");
    if (matchesSocket(paramSSLSocket)) {
      try
      {
        this.setUseSessionTickets.invoke(paramSSLSocket, new Object[] { Boolean.valueOf(true) });
        if (paramString != null) {
          this.setHostname.invoke(paramSSLSocket, new Object[] { paramString });
        }
        this.setAlpnProtocols.invoke(paramSSLSocket, new Object[] { Platform.Companion.concatLengthPrefixed(paramList) });
      }
      catch (InvocationTargetException paramSSLSocket)
      {
        throw ((Throwable)new AssertionError(paramSSLSocket));
      }
      catch (IllegalAccessException paramSSLSocket)
      {
        throw ((Throwable)new AssertionError(paramSSLSocket));
      }
    }
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkParameterIsNotNull(paramSSLSocket, "sslSocket");
    boolean bool = matchesSocket(paramSSLSocket);
    Object localObject = null;
    if (!bool) {
      return null;
    }
    try
    {
      byte[] arrayOfByte = (byte[])this.getAlpnSelectedProtocol.invoke(paramSSLSocket, new Object[0]);
      paramSSLSocket = localObject;
      if (arrayOfByte == null) {
        break label112;
      }
      Charset localCharset = StandardCharsets.UTF_8;
      Intrinsics.checkExpressionValueIsNotNull(localCharset, "StandardCharsets.UTF_8");
      paramSSLSocket = new java/lang/String;
      paramSSLSocket.<init>(arrayOfByte, localCharset);
    }
    catch (InvocationTargetException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError(paramSSLSocket));
    }
    catch (IllegalAccessException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError(paramSSLSocket));
    }
    catch (NullPointerException paramSSLSocket)
    {
      if (!Intrinsics.areEqual(paramSSLSocket.getMessage(), "ssl == null")) {
        break label114;
      }
    }
    paramSSLSocket = localObject;
    label112:
    return paramSSLSocket;
    label114:
    throw ((Throwable)paramSSLSocket);
  }
  
  public boolean isSupported()
  {
    return AndroidPlatform.Companion.isSupported();
  }
  
  public boolean matchesSocket(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkParameterIsNotNull(paramSSLSocket, "sslSocket");
    return this.sslSocketClass.isInstance(paramSSLSocket);
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
  
  @Metadata(bv={1, 0, 3}, d1={"\000\030\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\016\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\020\020\003\032\004\030\0010\0042\006\020\005\032\0020\006?\006\007"}, d2={"Lokhttp3/internal/platform/android/AndroidSocketAdapter$Companion;", "", "()V", "buildIfSupported", "Lokhttp3/internal/platform/android/SocketAdapter;", "packageName", "", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final SocketAdapter buildIfSupported(String paramString)
    {
      Intrinsics.checkParameterIsNotNull(paramString, "packageName");
      try
      {
        Object localObject = new java/lang/StringBuilder;
        ((StringBuilder)localObject).<init>();
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(".OpenSSLSocketImpl");
        localObject = Class.forName(((StringBuilder)localObject).toString());
        if (localObject != null)
        {
          paramString = new okhttp3/internal/platform/android/AndroidSocketAdapter;
          paramString.<init>((Class)localObject);
        }
        else
        {
          paramString = new kotlin/TypeCastException;
          paramString.<init>("null cannot be cast to non-null type java.lang.Class<in javax.net.ssl.SSLSocket>");
          throw paramString;
        }
      }
      catch (Exception paramString)
      {
        UtilKt.androidLog(5, "unable to load android socket classes", (Throwable)paramString);
        paramString = null;
      }
      return (SocketAdapter)paramString;
    }
  }
}
