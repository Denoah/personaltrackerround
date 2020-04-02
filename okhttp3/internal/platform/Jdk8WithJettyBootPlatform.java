package okhttp3.internal.platform;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\002\n\002\020\016\n\000\n\002\020 \n\002\030\002\n\002\b\004\030\000 \0262\0020\001:\002\025\026B5\022\006\020\002\032\0020\003\022\006\020\004\032\0020\003\022\006\020\005\032\0020\003\022\n\020\006\032\006\022\002\b\0030\007\022\n\020\b\032\006\022\002\b\0030\007?\006\002\020\tJ\020\020\n\032\0020\0132\006\020\f\032\0020\rH\026J(\020\016\032\0020\0132\006\020\f\032\0020\r2\b\020\017\032\004\030\0010\0202\f\020\021\032\b\022\004\022\0020\0230\022H\026J\022\020\024\032\004\030\0010\0202\006\020\f\032\0020\rH\026R\022\020\006\032\006\022\002\b\0030\007X?\004?\006\002\n\000R\016\020\004\032\0020\003X?\004?\006\002\n\000R\016\020\002\032\0020\003X?\004?\006\002\n\000R\016\020\005\032\0020\003X?\004?\006\002\n\000R\022\020\b\032\006\022\002\b\0030\007X?\004?\006\002\n\000?\006\027"}, d2={"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform;", "Lokhttp3/internal/platform/Platform;", "putMethod", "Ljava/lang/reflect/Method;", "getMethod", "removeMethod", "clientProviderClass", "Ljava/lang/Class;", "serverProviderClass", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/Class;Ljava/lang/Class;)V", "afterHandshake", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "configureTlsExtensions", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "AlpnProvider", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class Jdk8WithJettyBootPlatform
  extends Platform
{
  public static final Companion Companion = new Companion(null);
  private final Class<?> clientProviderClass;
  private final Method getMethod;
  private final Method putMethod;
  private final Method removeMethod;
  private final Class<?> serverProviderClass;
  
  public Jdk8WithJettyBootPlatform(Method paramMethod1, Method paramMethod2, Method paramMethod3, Class<?> paramClass1, Class<?> paramClass2)
  {
    this.putMethod = paramMethod1;
    this.getMethod = paramMethod2;
    this.removeMethod = paramMethod3;
    this.clientProviderClass = paramClass1;
    this.serverProviderClass = paramClass2;
  }
  
  public void afterHandshake(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkParameterIsNotNull(paramSSLSocket, "sslSocket");
    try
    {
      this.removeMethod.invoke(null, new Object[] { paramSSLSocket });
      return;
    }
    catch (InvocationTargetException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to remove ALPN", (Throwable)paramSSLSocket));
    }
    catch (IllegalAccessException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to remove ALPN", (Throwable)paramSSLSocket));
    }
  }
  
  public void configureTlsExtensions(SSLSocket paramSSLSocket, String paramString, List<? extends Protocol> paramList)
  {
    Intrinsics.checkParameterIsNotNull(paramSSLSocket, "sslSocket");
    Intrinsics.checkParameterIsNotNull(paramList, "protocols");
    List localList = Platform.Companion.alpnProtocolNames(paramList);
    try
    {
      paramString = Platform.class.getClassLoader();
      Class localClass = this.clientProviderClass;
      paramList = this.serverProviderClass;
      Object localObject = new okhttp3/internal/platform/Jdk8WithJettyBootPlatform$AlpnProvider;
      ((AlpnProvider)localObject).<init>(localList);
      localObject = (InvocationHandler)localObject;
      paramString = Proxy.newProxyInstance(paramString, new Class[] { localClass, paramList }, (InvocationHandler)localObject);
      this.putMethod.invoke(null, new Object[] { paramSSLSocket, paramString });
      return;
    }
    catch (IllegalAccessException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to set ALPN", (Throwable)paramSSLSocket));
    }
    catch (InvocationTargetException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to set ALPN", (Throwable)paramSSLSocket));
    }
  }
  
  public String getSelectedProtocol(SSLSocket paramSSLSocket)
  {
    Intrinsics.checkParameterIsNotNull(paramSSLSocket, "sslSocket");
    try
    {
      Method localMethod = this.getMethod;
      Object localObject = null;
      paramSSLSocket = Proxy.getInvocationHandler(localMethod.invoke(null, new Object[] { paramSSLSocket }));
      if (paramSSLSocket != null)
      {
        paramSSLSocket = (AlpnProvider)paramSSLSocket;
        if ((!paramSSLSocket.getUnsupported$okhttp()) && (paramSSLSocket.getSelected$okhttp() == null))
        {
          Platform.log$default(this, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", 0, null, 6, null);
          return null;
        }
        if (paramSSLSocket.getUnsupported$okhttp()) {
          paramSSLSocket = localObject;
        } else {
          paramSSLSocket = paramSSLSocket.getSelected$okhttp();
        }
        return paramSSLSocket;
      }
      paramSSLSocket = new kotlin/TypeCastException;
      paramSSLSocket.<init>("null cannot be cast to non-null type okhttp3.internal.platform.Jdk8WithJettyBootPlatform.AlpnProvider");
      throw paramSSLSocket;
    }
    catch (IllegalAccessException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to get ALPN selected protocol", (Throwable)paramSSLSocket));
    }
    catch (InvocationTargetException paramSSLSocket)
    {
      throw ((Throwable)new AssertionError("failed to get ALPN selected protocol", (Throwable)paramSSLSocket));
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\0004\n\002\030\002\n\002\030\002\n\000\n\002\020 \n\002\020\016\n\002\b\007\n\002\020\013\n\002\b\005\n\002\020\000\n\002\b\002\n\002\030\002\n\000\n\002\020\021\n\002\b\002\b\002\030\0002\0020\001B\025\b\000\022\f\020\002\032\b\022\004\022\0020\0040\003?\006\002\020\005J0\020\021\032\004\030\0010\0222\006\020\023\032\0020\0222\006\020\024\032\0020\0252\016\020\026\032\n\022\004\022\0020\022\030\0010\027H?\002?\006\002\020\030R\024\020\002\032\b\022\004\022\0020\0040\003X?\004?\006\002\n\000R\034\020\006\032\004\030\0010\004X?\016?\006\016\n\000\032\004\b\007\020\b\"\004\b\t\020\nR\032\020\013\032\0020\fX?\016?\006\016\n\000\032\004\b\r\020\016\"\004\b\017\020\020?\006\031"}, d2={"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform$AlpnProvider;", "Ljava/lang/reflect/InvocationHandler;", "protocols", "", "", "(Ljava/util/List;)V", "selected", "getSelected$okhttp", "()Ljava/lang/String;", "setSelected$okhttp", "(Ljava/lang/String;)V", "unsupported", "", "getUnsupported$okhttp", "()Z", "setUnsupported$okhttp", "(Z)V", "invoke", "", "proxy", "method", "Ljava/lang/reflect/Method;", "args", "", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", "okhttp"}, k=1, mv={1, 1, 16})
  private static final class AlpnProvider
    implements InvocationHandler
  {
    private final List<String> protocols;
    private String selected;
    private boolean unsupported;
    
    public AlpnProvider(List<String> paramList)
    {
      this.protocols = paramList;
    }
    
    public final String getSelected$okhttp()
    {
      return this.selected;
    }
    
    public final boolean getUnsupported$okhttp()
    {
      return this.unsupported;
    }
    
    public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
      throws Throwable
    {
      Intrinsics.checkParameterIsNotNull(paramObject, "proxy");
      Intrinsics.checkParameterIsNotNull(paramMethod, "method");
      if (paramArrayOfObject == null) {
        paramArrayOfObject = new Object[0];
      }
      paramObject = paramMethod.getName();
      Class localClass = paramMethod.getReturnType();
      if ((Intrinsics.areEqual(paramObject, "supports")) && (Intrinsics.areEqual(Boolean.TYPE, localClass))) {
        return Boolean.valueOf(true);
      }
      if ((Intrinsics.areEqual(paramObject, "unsupported")) && (Intrinsics.areEqual(Void.TYPE, localClass)))
      {
        this.unsupported = true;
        return null;
      }
      int i;
      if (Intrinsics.areEqual(paramObject, "protocols"))
      {
        if (paramArrayOfObject.length == 0) {
          i = 1;
        } else {
          i = 0;
        }
        if (i != 0) {
          return this.protocols;
        }
      }
      if (((Intrinsics.areEqual(paramObject, "selectProtocol")) || (Intrinsics.areEqual(paramObject, "select"))) && (Intrinsics.areEqual(String.class, localClass)) && (paramArrayOfObject.length == 1) && ((paramArrayOfObject[0] instanceof List)))
      {
        paramObject = paramArrayOfObject[0];
        if (paramObject != null)
        {
          paramObject = (List)paramObject;
          int j = paramObject.size();
          if (j >= 0)
          {
            for (i = 0;; i++)
            {
              paramMethod = paramObject.get(i);
              if (paramMethod == null) {
                break;
              }
              paramMethod = (String)paramMethod;
              if (this.protocols.contains(paramMethod))
              {
                this.selected = paramMethod;
                return paramMethod;
              }
              if (i == j) {
                break label253;
              }
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
          }
          label253:
          paramObject = (String)this.protocols.get(0);
          this.selected = paramObject;
          return paramObject;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<*>");
      }
      if (((Intrinsics.areEqual(paramObject, "protocolSelected")) || (Intrinsics.areEqual(paramObject, "selected"))) && (paramArrayOfObject.length == 1))
      {
        paramObject = paramArrayOfObject[0];
        if (paramObject != null)
        {
          this.selected = ((String)paramObject);
          return null;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
      }
      return paramMethod.invoke(this, Arrays.copyOf(paramArrayOfObject, paramArrayOfObject.length));
    }
    
    public final void setSelected$okhttp(String paramString)
    {
      this.selected = paramString;
    }
    
    public final void setUnsupported$okhttp(boolean paramBoolean)
    {
      this.unsupported = paramBoolean;
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\b\020\003\032\004\030\0010\004?\006\005"}, d2={"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform$Companion;", "", "()V", "buildIfSupported", "Lokhttp3/internal/platform/Platform;", "okhttp"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final Platform buildIfSupported()
    {
      Object localObject1 = System.getProperty("java.specification.version", "unknown");
      for (;;)
      {
        try
        {
          Intrinsics.checkExpressionValueIsNotNull(localObject1, "jvmVersion");
          int i = Integer.parseInt((String)localObject1);
          if (i >= 9) {
            return null;
          }
        }
        catch (NumberFormatException localNumberFormatException)
        {
          Object localObject2;
          Object localObject3;
          Object localObject4;
          Method localMethod1;
          Method localMethod2;
          continue;
        }
        try
        {
          localObject1 = Class.forName("org.eclipse.jetty.alpn.ALPN", true, null);
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          ((StringBuilder)localObject2).append("org.eclipse.jetty.alpn.ALPN");
          ((StringBuilder)localObject2).append("$Provider");
          localObject3 = Class.forName(((StringBuilder)localObject2).toString(), true, null);
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>();
          ((StringBuilder)localObject2).append("org.eclipse.jetty.alpn.ALPN");
          ((StringBuilder)localObject2).append("$ClientProvider");
          localObject2 = Class.forName(((StringBuilder)localObject2).toString(), true, null);
          localObject4 = new java/lang/StringBuilder;
          ((StringBuilder)localObject4).<init>();
          ((StringBuilder)localObject4).append("org.eclipse.jetty.alpn.ALPN");
          ((StringBuilder)localObject4).append("$ServerProvider");
          localObject4 = Class.forName(((StringBuilder)localObject4).toString(), true, null);
          localMethod1 = ((Class)localObject1).getMethod("put", new Class[] { SSLSocket.class, localObject3 });
          localObject3 = ((Class)localObject1).getMethod("get", new Class[] { SSLSocket.class });
          localMethod2 = ((Class)localObject1).getMethod("remove", new Class[] { SSLSocket.class });
          localObject1 = new okhttp3/internal/platform/Jdk8WithJettyBootPlatform;
          Intrinsics.checkExpressionValueIsNotNull(localMethod1, "putMethod");
          Intrinsics.checkExpressionValueIsNotNull(localObject3, "getMethod");
          Intrinsics.checkExpressionValueIsNotNull(localMethod2, "removeMethod");
          Intrinsics.checkExpressionValueIsNotNull(localObject2, "clientProviderClass");
          Intrinsics.checkExpressionValueIsNotNull(localObject4, "serverProviderClass");
          ((Jdk8WithJettyBootPlatform)localObject1).<init>(localMethod1, (Method)localObject3, localMethod2, (Class)localObject2, (Class)localObject4);
          localObject1 = (Platform)localObject1;
          return localObject1;
        }
        catch (ClassNotFoundException|NoSuchMethodException localClassNotFoundException)
        {
          return null;
        }
      }
    }
  }
}
