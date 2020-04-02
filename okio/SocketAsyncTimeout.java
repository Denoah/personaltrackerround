package okio;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\022\020\b\032\0020\t2\b\020\n\032\004\030\0010\tH\024J\b\020\013\032\0020\fH\024R\026\020\005\032\n \007*\004\030\0010\0060\006X?\004?\006\002\n\000R\016\020\002\032\0020\003X?\004?\006\002\n\000?\006\r"}, d2={"Lokio/SocketAsyncTimeout;", "Lokio/AsyncTimeout;", "socket", "Ljava/net/Socket;", "(Ljava/net/Socket;)V", "logger", "Ljava/util/logging/Logger;", "kotlin.jvm.PlatformType", "newTimeoutException", "Ljava/io/IOException;", "cause", "timedOut", "", "okio"}, k=1, mv={1, 1, 16})
final class SocketAsyncTimeout
  extends AsyncTimeout
{
  private final Logger logger;
  private final Socket socket;
  
  public SocketAsyncTimeout(Socket paramSocket)
  {
    this.socket = paramSocket;
    this.logger = Logger.getLogger("okio.Okio");
  }
  
  protected IOException newTimeoutException(IOException paramIOException)
  {
    SocketTimeoutException localSocketTimeoutException = new SocketTimeoutException("timeout");
    if (paramIOException != null) {
      localSocketTimeoutException.initCause((Throwable)paramIOException);
    }
    return (IOException)localSocketTimeoutException;
  }
  
  protected void timedOut()
  {
    try
    {
      this.socket.close();
    }
    catch (AssertionError localAssertionError)
    {
      if (Okio.isAndroidGetsocknameError(localAssertionError))
      {
        localObject1 = this.logger;
        localLevel = Level.WARNING;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Failed to close timed out socket ");
        ((StringBuilder)localObject2).append(this.socket);
        ((Logger)localObject1).log(localLevel, ((StringBuilder)localObject2).toString(), (Throwable)localAssertionError);
      }
      else
      {
        throw ((Throwable)localAssertionError);
      }
    }
    catch (Exception localException)
    {
      Object localObject2 = this.logger;
      Level localLevel = Level.WARNING;
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Failed to close timed out socket ");
      ((StringBuilder)localObject1).append(this.socket);
      ((Logger)localObject2).log(localLevel, ((StringBuilder)localObject1).toString(), (Throwable)localException);
    }
  }
}
