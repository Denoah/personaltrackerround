package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\000\n\002\020\003\n\002\020\000\n\002\b\003\bg\030\000*\022\b\000\020\001*\0020\002*\b\022\004\022\002H\0010\0002\0020\003J\017\020\004\032\004\030\0018\000H&?\006\002\020\005?\006\006"}, d2={"Lkotlinx/coroutines/CopyableThrowable;", "T", "", "", "createCopy", "()Ljava/lang/Throwable;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public abstract interface CopyableThrowable<T extends Throwable,  extends CopyableThrowable<T>>
{
  public abstract T createCopy();
}
