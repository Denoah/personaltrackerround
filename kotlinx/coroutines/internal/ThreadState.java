package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\002\030\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\021\n\002\b\005\n\002\020\002\n\002\b\004\b\002\030\0002\0020\001B\025\022\006\020\002\032\0020\003\022\006\020\004\032\0020\005?\006\002\020\006J\020\020\r\032\0020\0162\b\020\017\032\004\030\0010\001J\006\020\020\032\0020\016J\b\020\021\032\004\030\0010\001R\030\020\007\032\n\022\006\022\004\030\0010\0010\bX?\016?\006\004\n\002\020\tR\021\020\002\032\0020\003?\006\b\n\000\032\004\b\n\020\013R\016\020\f\032\0020\005X?\016?\006\002\n\000?\006\022"}, d2={"Lkotlinx/coroutines/internal/ThreadState;", "", "context", "Lkotlin/coroutines/CoroutineContext;", "n", "", "(Lkotlin/coroutines/CoroutineContext;I)V", "a", "", "[Ljava/lang/Object;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "i", "append", "", "value", "start", "take", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class ThreadState
{
  private Object[] a;
  private final CoroutineContext context;
  private int i;
  
  public ThreadState(CoroutineContext paramCoroutineContext, int paramInt)
  {
    this.context = paramCoroutineContext;
    this.a = new Object[paramInt];
  }
  
  public final void append(Object paramObject)
  {
    Object[] arrayOfObject = this.a;
    int j = this.i;
    this.i = (j + 1);
    arrayOfObject[j] = paramObject;
  }
  
  public final CoroutineContext getContext()
  {
    return this.context;
  }
  
  public final void start()
  {
    this.i = 0;
  }
  
  public final Object take()
  {
    Object[] arrayOfObject = this.a;
    int j = this.i;
    this.i = (j + 1);
    return arrayOfObject[j];
  }
}
