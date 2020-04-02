package kotlin.coroutines;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\000\n\002\020\000\n\000\n\002\030\002\n\002\b\003\n\002\020\002\n\000\n\002\030\002\n\002\b\002\bg\030\000*\006\b\000\020\001 \0002\0020\002J\036\020\007\032\0020\b2\f\020\t\032\b\022\004\022\0028\0000\nH&?\001\000?\006\002\020\013R\022\020\003\032\0020\004X¦\004?\006\006\032\004\b\005\020\006?\002\004\n\002\b\031?\006\f"}, d2={"Lkotlin/coroutines/Continuation;", "T", "", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public abstract interface Continuation<T>
{
  public abstract CoroutineContext getContext();
  
  public abstract void resumeWith(Object paramObject);
}
