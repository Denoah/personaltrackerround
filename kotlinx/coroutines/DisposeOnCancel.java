package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000$\n\002\030\002\n\002\030\002\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\020\003\n\000\n\002\020\016\n\000\b\002\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\023\020\005\032\0020\0062\b\020\007\032\004\030\0010\bH?\002J\b\020\t\032\0020\nH\026R\016\020\002\032\0020\003X?\004?\006\002\n\000?\006\013"}, d2={"Lkotlinx/coroutines/DisposeOnCancel;", "Lkotlinx/coroutines/CancelHandler;", "handle", "Lkotlinx/coroutines/DisposableHandle;", "(Lkotlinx/coroutines/DisposableHandle;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class DisposeOnCancel
  extends CancelHandler
{
  private final DisposableHandle handle;
  
  public DisposeOnCancel(DisposableHandle paramDisposableHandle)
  {
    this.handle = paramDisposableHandle;
  }
  
  public void invoke(Throwable paramThrowable)
  {
    this.handle.dispose();
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("DisposeOnCancel[");
    localStringBuilder.append(this.handle);
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }
}
