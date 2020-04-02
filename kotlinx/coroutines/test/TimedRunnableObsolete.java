package kotlinx.coroutines.test;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\002\030\002\n\002\020\017\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\t\n\002\b\003\n\002\030\002\n\002\b\005\n\002\020\b\n\002\b\006\n\002\020\002\n\000\n\002\020\016\n\000\b\002\030\0002\b\022\004\022\0020\0000\0012\0060\002j\002`\0032\0020\004B%\022\n\020\005\032\0060\002j\002`\003\022\b\b\002\020\006\032\0020\007\022\b\b\002\020\b\032\0020\007?\006\002\020\tJ\021\020\026\032\0020\0212\006\020\027\032\0020\000H?\002J\b\020\005\032\0020\030H\026J\b\020\031\032\0020\032H\026R\016\020\006\032\0020\007X?\004?\006\002\n\000R \020\n\032\b\022\002\b\003\030\0010\013X?\016?\006\016\n\000\032\004\b\f\020\r\"\004\b\016\020\017R\032\020\020\032\0020\021X?\016?\006\016\n\000\032\004\b\022\020\023\"\004\b\024\020\025R\022\020\005\032\0060\002j\002`\003X?\004?\006\002\n\000R\020\020\b\032\0020\0078\000X?\004?\006\002\n\000?\006\033"}, d2={"Lkotlinx/coroutines/test/TimedRunnableObsolete;", "", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "run", "count", "", "time", "(Ljava/lang/Runnable;JJ)V", "heap", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "getHeap", "()Lkotlinx/coroutines/internal/ThreadSafeHeap;", "setHeap", "(Lkotlinx/coroutines/internal/ThreadSafeHeap;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "compareTo", "other", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
final class TimedRunnableObsolete
  implements Comparable<TimedRunnableObsolete>, Runnable, ThreadSafeHeapNode
{
  private final long count;
  private ThreadSafeHeap<?> heap;
  private int index;
  private final Runnable run;
  public final long time;
  
  public TimedRunnableObsolete(Runnable paramRunnable, long paramLong1, long paramLong2)
  {
    this.run = paramRunnable;
    this.count = paramLong1;
    this.time = paramLong2;
  }
  
  public int compareTo(TimedRunnableObsolete paramTimedRunnableObsolete)
  {
    Intrinsics.checkParameterIsNotNull(paramTimedRunnableObsolete, "other");
    long l1 = this.time;
    long l2 = paramTimedRunnableObsolete.time;
    long l3 = l1;
    long l4 = l2;
    if (l1 == l2)
    {
      l3 = this.count;
      l4 = paramTimedRunnableObsolete.count;
    }
    return l3 < l4;
  }
  
  public ThreadSafeHeap<?> getHeap()
  {
    return this.heap;
  }
  
  public int getIndex()
  {
    return this.index;
  }
  
  public void run()
  {
    this.run.run();
  }
  
  public void setHeap(ThreadSafeHeap<?> paramThreadSafeHeap)
  {
    this.heap = paramThreadSafeHeap;
  }
  
  public void setIndex(int paramInt)
  {
    this.index = paramInt;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("TimedRunnable(time=");
    localStringBuilder.append(this.time);
    localStringBuilder.append(", run=");
    localStringBuilder.append(this.run);
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
}
