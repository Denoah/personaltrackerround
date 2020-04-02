package okhttp3.internal.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref.ObjectRef;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Route;
import okhttp3.internal.Util;
import okhttp3.internal.http.ExchangeCodec;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.StreamResetException;

@Metadata(bv={1, 0, 3}, d1={"\000l\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\004\n\002\030\002\n\000\n\002\020\b\n\000\n\002\030\002\n\002\b\003\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\b\006\n\002\020\013\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\000\030\0002\0020\001B%\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005\022\006\020\006\032\0020\007\022\006\020\b\032\0020\t?\006\002\020\nJ\b\020\r\032\004\030\0010\016J\026\020\031\032\0020\0322\006\020\033\032\0020\0342\006\020\035\032\0020\036J0\020\037\032\0020\0162\006\020 \032\0020\0202\006\020!\032\0020\0202\006\020\"\032\0020\0202\006\020#\032\0020\0202\006\020$\032\0020%H\002J8\020&\032\0020\0162\006\020 \032\0020\0202\006\020!\032\0020\0202\006\020\"\032\0020\0202\006\020#\032\0020\0202\006\020$\032\0020%2\006\020'\032\0020%H\002J\006\020(\032\0020%J\b\020)\032\0020%H\002J\016\020*\032\0020+2\006\020,\032\0020-R\024\020\004\032\0020\005X?\004?\006\b\n\000\032\004\b\013\020\fR\016\020\006\032\0020\007X?\004?\006\002\n\000R\020\020\r\032\004\030\0010\016X?\016?\006\002\n\000R\016\020\002\032\0020\003X?\004?\006\002\n\000R\016\020\017\032\0020\020X?\016?\006\002\n\000R\016\020\b\032\0020\tX?\004?\006\002\n\000R\020\020\021\032\004\030\0010\022X?\016?\006\002\n\000R\016\020\023\032\0020\020X?\016?\006\002\n\000R\016\020\024\032\0020\020X?\016?\006\002\n\000R\020\020\025\032\004\030\0010\026X?\016?\006\002\n\000R\020\020\027\032\004\030\0010\030X?\016?\006\002\n\000?\006."}, d2={"Lokhttp3/internal/connection/ExchangeFinder;", "", "connectionPool", "Lokhttp3/internal/connection/RealConnectionPool;", "address", "Lokhttp3/Address;", "call", "Lokhttp3/internal/connection/RealCall;", "eventListener", "Lokhttp3/EventListener;", "(Lokhttp3/internal/connection/RealConnectionPool;Lokhttp3/Address;Lokhttp3/internal/connection/RealCall;Lokhttp3/EventListener;)V", "getAddress$okhttp", "()Lokhttp3/Address;", "connectingConnection", "Lokhttp3/internal/connection/RealConnection;", "connectionShutdownCount", "", "nextRouteToTry", "Lokhttp3/Route;", "otherFailureCount", "refusedStreamCount", "routeSelection", "Lokhttp3/internal/connection/RouteSelector$Selection;", "routeSelector", "Lokhttp3/internal/connection/RouteSelector;", "find", "Lokhttp3/internal/http/ExchangeCodec;", "client", "Lokhttp3/OkHttpClient;", "chain", "Lokhttp3/internal/http/RealInterceptorChain;", "findConnection", "connectTimeout", "readTimeout", "writeTimeout", "pingIntervalMillis", "connectionRetryEnabled", "", "findHealthyConnection", "doExtensiveHealthChecks", "retryAfterFailure", "retryCurrentRoute", "trackFailure", "", "e", "Ljava/io/IOException;", "okhttp"}, k=1, mv={1, 1, 16})
public final class ExchangeFinder
{
  private final Address address;
  private final RealCall call;
  private RealConnection connectingConnection;
  private final RealConnectionPool connectionPool;
  private int connectionShutdownCount;
  private final EventListener eventListener;
  private Route nextRouteToTry;
  private int otherFailureCount;
  private int refusedStreamCount;
  private RouteSelector.Selection routeSelection;
  private RouteSelector routeSelector;
  
  public ExchangeFinder(RealConnectionPool paramRealConnectionPool, Address paramAddress, RealCall paramRealCall, EventListener paramEventListener)
  {
    this.connectionPool = paramRealConnectionPool;
    this.address = paramAddress;
    this.call = paramRealCall;
    this.eventListener = paramEventListener;
  }
  
  private final RealConnection findConnection(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
    throws IOException
  {
    Object localObject1 = (RealConnection)null;
    Object localObject2 = (Route)null;
    ??? = new Ref.ObjectRef();
    synchronized (this.connectionPool)
    {
      if (!this.call.isCanceled())
      {
        ((Ref.ObjectRef)???).element = this.call.getConnection();
        if (this.call.getConnection() != null)
        {
          localObject5 = this.call.getConnection();
          if (localObject5 == null) {
            Intrinsics.throwNpe();
          }
          if (!((RealConnection)localObject5).getNoNewExchanges())
          {
            localObject5 = this.call.getConnection();
            if (localObject5 == null) {
              Intrinsics.throwNpe();
            }
            if (((RealConnection)localObject5).supportsUrl(this.address.url())) {}
          }
          else
          {
            localObject9 = this.call.releaseConnectionNoEvents$okhttp();
            break label134;
          }
        }
        Object localObject9 = null;
        label134:
        if (this.call.getConnection() != null)
        {
          localObject1 = this.call.getConnection();
          ((Ref.ObjectRef)???).element = ((RealConnection)null);
        }
        Object localObject5 = localObject2;
        if (localObject1 == null)
        {
          this.refusedStreamCount = 0;
          this.connectionShutdownCount = 0;
          this.otherFailureCount = 0;
          if (this.connectionPool.callAcquirePooledConnection(this.address, this.call, null, false))
          {
            localObject1 = this.call.getConnection();
            i = 1;
            localObject5 = localObject2;
            break label253;
          }
          localObject5 = localObject2;
          if (this.nextRouteToTry != null)
          {
            localObject5 = this.nextRouteToTry;
            this.nextRouteToTry = ((Route)null);
          }
        }
        int i = 0;
        label253:
        localObject2 = Unit.INSTANCE;
        if (localObject9 != null) {
          Util.closeQuietly((Socket)localObject9);
        }
        if ((RealConnection)((Ref.ObjectRef)???).element != null)
        {
          localObject2 = this.eventListener;
          localObject9 = (Call)this.call;
          ??? = (RealConnection)((Ref.ObjectRef)???).element;
          if (??? == null) {
            Intrinsics.throwNpe();
          }
          ((EventListener)localObject2).connectionReleased((Call)localObject9, (Connection)???);
        }
        if (i != 0)
        {
          localObject9 = this.eventListener;
          localObject2 = (Call)this.call;
          if (localObject1 == null) {
            Intrinsics.throwNpe();
          }
          ((EventListener)localObject9).connectionAcquired((Call)localObject2, (Connection)localObject1);
        }
        if (localObject1 != null)
        {
          if (localObject1 == null) {
            Intrinsics.throwNpe();
          }
          return localObject1;
        }
        if (localObject5 == null)
        {
          localObject2 = this.routeSelection;
          if (localObject2 != null)
          {
            if (localObject2 == null) {
              Intrinsics.throwNpe();
            }
            if (((RouteSelector.Selection)localObject2).hasNext()) {}
          }
          else
          {
            localObject9 = this.routeSelector;
            localObject2 = localObject9;
            if (localObject9 == null)
            {
              localObject2 = new RouteSelector(this.address, this.call.getClient().getRouteDatabase(), (Call)this.call, this.eventListener);
              this.routeSelector = ((RouteSelector)localObject2);
            }
            this.routeSelection = ((RouteSelector)localObject2).next();
            j = 1;
            break label488;
          }
        }
        int j = 0;
        label488:
        localObject9 = (List)null;
        synchronized (this.connectionPool)
        {
          if (!this.call.isCanceled())
          {
            localObject2 = localObject1;
            int k = i;
            if (j != 0)
            {
              localObject2 = this.routeSelection;
              if (localObject2 == null) {
                Intrinsics.throwNpe();
              }
              ??? = ((RouteSelector.Selection)localObject2).getRoutes();
              localObject2 = localObject1;
              localObject9 = ???;
              k = i;
              if (this.connectionPool.callAcquirePooledConnection(this.address, this.call, (List)???, false))
              {
                localObject2 = this.call.getConnection();
                k = 1;
                localObject9 = ???;
              }
            }
            localObject1 = localObject2;
            localObject2 = localObject5;
            if (k == 0)
            {
              localObject1 = localObject5;
              if (localObject5 == null)
              {
                localObject5 = this.routeSelection;
                if (localObject5 == null) {
                  Intrinsics.throwNpe();
                }
                localObject1 = ((RouteSelector.Selection)localObject5).next();
              }
              localObject5 = new okhttp3/internal/connection/RealConnection;
              localObject2 = this.connectionPool;
              if (localObject1 == null) {
                Intrinsics.throwNpe();
              }
              ((RealConnection)localObject5).<init>((RealConnectionPool)localObject2, (Route)localObject1);
              this.connectingConnection = ((RealConnection)localObject5);
              localObject2 = localObject1;
              localObject1 = localObject5;
            }
            localObject5 = Unit.INSTANCE;
            if (k != 0)
            {
              localObject5 = this.eventListener;
              localObject2 = (Call)this.call;
              if (localObject1 == null) {
                Intrinsics.throwNpe();
              }
              ((EventListener)localObject5).connectionAcquired((Call)localObject2, (Connection)localObject1);
              if (localObject1 == null) {
                Intrinsics.throwNpe();
              }
              return localObject1;
            }
            if (localObject1 == null) {
              Intrinsics.throwNpe();
            }
            ((RealConnection)localObject1).connect(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean, (Call)this.call, this.eventListener);
            localObject5 = this.call.getClient().getRouteDatabase();
            if (localObject1 == null) {
              Intrinsics.throwNpe();
            }
            ((RouteDatabase)localObject5).connected(((RealConnection)localObject1).route());
            localObject5 = (Socket)null;
            synchronized (this.connectionPool)
            {
              this.connectingConnection = ((RealConnection)null);
              if (this.connectionPool.callAcquirePooledConnection(this.address, this.call, (List)localObject9, true))
              {
                if (localObject1 == null) {
                  Intrinsics.throwNpe();
                }
                ((RealConnection)localObject1).setNoNewExchanges(true);
                if (localObject1 == null) {
                  Intrinsics.throwNpe();
                }
                localObject5 = ((RealConnection)localObject1).socket();
                localObject1 = this.call.getConnection();
                this.nextRouteToTry = ((Route)localObject2);
              }
              else
              {
                localObject2 = this.connectionPool;
                if (localObject1 == null) {
                  Intrinsics.throwNpe();
                }
                ((RealConnectionPool)localObject2).put((RealConnection)localObject1);
                localObject2 = this.call;
                if (localObject1 == null) {
                  Intrinsics.throwNpe();
                }
                ((RealCall)localObject2).acquireConnectionNoEvents((RealConnection)localObject1);
              }
              localObject2 = Unit.INSTANCE;
              if (localObject5 != null) {
                Util.closeQuietly((Socket)localObject5);
              }
              localObject5 = this.eventListener;
              localObject2 = (Call)this.call;
              if (localObject1 == null) {
                Intrinsics.throwNpe();
              }
              ((EventListener)localObject5).connectionAcquired((Call)localObject2, (Connection)localObject1);
              if (localObject1 == null) {
                Intrinsics.throwNpe();
              }
              return localObject1;
            }
          }
          IOException localIOException1 = new java/io/IOException;
          localIOException1.<init>("Canceled");
          throw ((Throwable)localIOException1);
        }
      }
      IOException localIOException2 = new java/io/IOException;
      localIOException2.<init>("Canceled");
      throw ((Throwable)localIOException2);
    }
  }
  
  private final RealConnection findHealthyConnection(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    RealConnection localRealConnection;
    for (;;)
    {
      localRealConnection = findConnection(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean1);
      if (localRealConnection.isHealthy(paramBoolean2)) {
        break;
      }
      localRealConnection.noNewExchanges();
    }
    return localRealConnection;
  }
  
  private final boolean retryCurrentRoute()
  {
    int i = this.refusedStreamCount;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i <= 1)
    {
      bool2 = bool1;
      if (this.connectionShutdownCount <= 1) {
        if (this.otherFailureCount > 0)
        {
          bool2 = bool1;
        }
        else
        {
          RealConnection localRealConnection = this.call.getConnection();
          bool2 = bool1;
          if (localRealConnection != null)
          {
            bool2 = bool1;
            if (localRealConnection.getRouteFailureCount$okhttp() == 0)
            {
              bool2 = bool1;
              if (Util.canReuseConnectionFor(localRealConnection.route().address().url(), this.address.url())) {
                bool2 = true;
              }
            }
          }
        }
      }
    }
    return bool2;
  }
  
  public final RealConnection connectingConnection()
  {
    RealConnectionPool localRealConnectionPool = this.connectionPool;
    if ((Util.assertionsEnabled) && (!Thread.holdsLock(localRealConnectionPool)))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Thread ");
      Thread localThread = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(localThread, "Thread.currentThread()");
      localStringBuilder.append(localThread.getName());
      localStringBuilder.append(" MUST hold lock on ");
      localStringBuilder.append(localRealConnectionPool);
      throw ((Throwable)new AssertionError(localStringBuilder.toString()));
    }
    return this.connectingConnection;
  }
  
  public final ExchangeCodec find(OkHttpClient paramOkHttpClient, RealInterceptorChain paramRealInterceptorChain)
  {
    Intrinsics.checkParameterIsNotNull(paramOkHttpClient, "client");
    Intrinsics.checkParameterIsNotNull(paramRealInterceptorChain, "chain");
    try
    {
      paramOkHttpClient = findHealthyConnection(paramRealInterceptorChain.getConnectTimeoutMillis$okhttp(), paramRealInterceptorChain.getReadTimeoutMillis$okhttp(), paramRealInterceptorChain.getWriteTimeoutMillis$okhttp(), paramOkHttpClient.pingIntervalMillis(), paramOkHttpClient.retryOnConnectionFailure(), Intrinsics.areEqual(paramRealInterceptorChain.getRequest$okhttp().method(), "GET") ^ true).newCodec$okhttp(paramOkHttpClient, paramRealInterceptorChain);
      return paramOkHttpClient;
    }
    catch (IOException paramOkHttpClient)
    {
      trackFailure(paramOkHttpClient);
      throw ((Throwable)new RouteException(paramOkHttpClient));
    }
    catch (RouteException paramOkHttpClient)
    {
      trackFailure(paramOkHttpClient.getLastConnectException());
      throw ((Throwable)paramOkHttpClient);
    }
  }
  
  public final Address getAddress$okhttp()
  {
    return this.address;
  }
  
  public final boolean retryAfterFailure()
  {
    synchronized (this.connectionPool)
    {
      if ((this.refusedStreamCount == 0) && (this.connectionShutdownCount == 0))
      {
        int i = this.otherFailureCount;
        if (i == 0) {
          return false;
        }
      }
      Object localObject1 = this.nextRouteToTry;
      if (localObject1 != null) {
        return true;
      }
      if (retryCurrentRoute())
      {
        localObject1 = this.call.getConnection();
        if (localObject1 == null) {
          Intrinsics.throwNpe();
        }
        this.nextRouteToTry = ((RealConnection)localObject1).route();
        return true;
      }
      localObject1 = this.routeSelection;
      boolean bool;
      if (localObject1 != null)
      {
        bool = ((RouteSelector.Selection)localObject1).hasNext();
        if (bool == true) {
          return true;
        }
      }
      localObject1 = this.routeSelector;
      if (localObject1 != null)
      {
        bool = ((RouteSelector)localObject1).hasNext();
        return bool;
      }
      return true;
    }
  }
  
  public final void trackFailure(IOException paramIOException)
  {
    Intrinsics.checkParameterIsNotNull(paramIOException, "e");
    ??? = this.connectionPool;
    if ((Util.assertionsEnabled) && (Thread.holdsLock(???)))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Thread ");
      paramIOException = Thread.currentThread();
      Intrinsics.checkExpressionValueIsNotNull(paramIOException, "Thread.currentThread()");
      localStringBuilder.append(paramIOException.getName());
      localStringBuilder.append(" MUST NOT hold lock on ");
      localStringBuilder.append(???);
      throw ((Throwable)new AssertionError(localStringBuilder.toString()));
    }
    synchronized (this.connectionPool)
    {
      this.nextRouteToTry = ((Route)null);
      if (((paramIOException instanceof StreamResetException)) && (((StreamResetException)paramIOException).errorCode == ErrorCode.REFUSED_STREAM)) {
        this.refusedStreamCount += 1;
      } else if ((paramIOException instanceof ConnectionShutdownException)) {
        this.connectionShutdownCount += 1;
      } else {
        this.otherFailureCount += 1;
      }
      return;
    }
  }
}
