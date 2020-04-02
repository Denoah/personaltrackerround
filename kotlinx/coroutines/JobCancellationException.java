package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\000\n\002\020\003\n\000\n\002\030\002\n\002\b\003\n\002\020\013\n\000\n\002\020\000\n\002\b\002\n\002\020\b\n\002\b\002\b\000\030\0002\0060\001j\002`\0022\b\022\004\022\0020\0000\003B\037\022\006\020\004\032\0020\005\022\b\020\006\032\004\030\0010\007\022\006\020\b\032\0020\t?\006\002\020\nJ\n\020\013\032\004\030\0010\000H\026J\023\020\f\032\0020\r2\b\020\016\032\004\030\0010\017H?\002J\b\020\020\032\0020\007H\026J\b\020\021\032\0020\022H\026J\b\020\023\032\0020\005H\026R\020\020\b\032\0020\t8\000X?\004?\006\002\n\000?\006\024"}, d2={"Lkotlinx/coroutines/JobCancellationException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "Lkotlinx/coroutines/CopyableThrowable;", "message", "", "cause", "", "job", "Lkotlinx/coroutines/Job;", "(Ljava/lang/String;Ljava/lang/Throwable;Lkotlinx/coroutines/Job;)V", "createCopy", "equals", "", "other", "", "fillInStackTrace", "hashCode", "", "toString", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class JobCancellationException
  extends CancellationException
  implements CopyableThrowable<JobCancellationException>
{
  public final Job job;
  
  public JobCancellationException(String paramString, Throwable paramThrowable, Job paramJob)
  {
    super(paramString);
    this.job = paramJob;
    if (paramThrowable != null) {
      initCause(paramThrowable);
    }
  }
  
  public JobCancellationException createCopy()
  {
    if (DebugKt.getDEBUG())
    {
      String str = getMessage();
      if (str == null) {
        Intrinsics.throwNpe();
      }
      return new JobCancellationException(str, (Throwable)this, this.job);
    }
    return null;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject != (JobCancellationException)this) {
      if ((paramObject instanceof JobCancellationException))
      {
        paramObject = (JobCancellationException)paramObject;
        if ((Intrinsics.areEqual(paramObject.getMessage(), getMessage())) && (Intrinsics.areEqual(paramObject.job, this.job)) && (Intrinsics.areEqual(paramObject.getCause(), getCause()))) {}
      }
      else
      {
        return false;
      }
    }
    boolean bool = true;
    return bool;
  }
  
  public Throwable fillInStackTrace()
  {
    if (DebugKt.getDEBUG())
    {
      Throwable localThrowable = super.fillInStackTrace();
      Intrinsics.checkExpressionValueIsNotNull(localThrowable, "super.fillInStackTrace()");
      return localThrowable;
    }
    return (Throwable)this;
  }
  
  public int hashCode()
  {
    Object localObject = getMessage();
    if (localObject == null) {
      Intrinsics.throwNpe();
    }
    int i = ((String)localObject).hashCode();
    int j = this.job.hashCode();
    localObject = getCause();
    int k;
    if (localObject != null) {
      k = ((Throwable)localObject).hashCode();
    } else {
      k = 0;
    }
    return (i * 31 + j) * 31 + k;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(super.toString());
    localStringBuilder.append("; job=");
    localStringBuilder.append(this.job);
    return localStringBuilder.toString();
  }
}
