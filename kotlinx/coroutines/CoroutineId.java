package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.CoroutineContext.Element;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\000:\n\002\030\002\n\002\030\002\n\002\020\016\n\002\030\002\n\000\n\002\020\t\n\002\b\006\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\000\n\002\020\002\n\000\n\002\030\002\n\002\b\005\b?\b\030\000 \0302\b\022\004\022\0020\0020\0012\0020\003:\001\030B\r\022\006\020\004\032\0020\005?\006\002\020\006J\t\020\t\032\0020\005H?\003J\023\020\n\032\0020\0002\b\b\002\020\004\032\0020\005H?\001J\023\020\013\032\0020\f2\b\020\r\032\004\030\0010\016H?\003J\t\020\017\032\0020\020H?\001J\030\020\021\032\0020\0222\006\020\023\032\0020\0242\006\020\025\032\0020\002H\026J\b\020\026\032\0020\002H\026J\020\020\027\032\0020\0022\006\020\023\032\0020\024H\026R\021\020\004\032\0020\005?\006\b\n\000\032\004\b\007\020\b?\006\031"}, d2={"Lkotlinx/coroutines/CoroutineId;", "Lkotlinx/coroutines/ThreadContextElement;", "", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "id", "", "(J)V", "getId", "()J", "component1", "copy", "equals", "", "other", "", "hashCode", "", "restoreThreadContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "oldState", "toString", "updateThreadContext", "Key", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class CoroutineId
  extends AbstractCoroutineContextElement
  implements ThreadContextElement<String>
{
  public static final Key Key = new Key(null);
  private final long id;
  
  public CoroutineId(long paramLong)
  {
    super((CoroutineContext.Key)Key);
    this.id = paramLong;
  }
  
  public final long component1()
  {
    return this.id;
  }
  
  public final CoroutineId copy(long paramLong)
  {
    return new CoroutineId(paramLong);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof CoroutineId))
      {
        paramObject = (CoroutineId)paramObject;
        if (this.id == paramObject.id) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public <R> R fold(R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
    return ThreadContextElement.DefaultImpls.fold(this, paramR, paramFunction2);
  }
  
  public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    return ThreadContextElement.DefaultImpls.get(this, paramKey);
  }
  
  public final long getId()
  {
    return this.id;
  }
  
  public int hashCode()
  {
    long l = this.id;
    return (int)(l ^ l >>> 32);
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key<?> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    return ThreadContextElement.DefaultImpls.minusKey(this, paramKey);
  }
  
  public CoroutineContext plus(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return ThreadContextElement.DefaultImpls.plus(this, paramCoroutineContext);
  }
  
  public void restoreThreadContext(CoroutineContext paramCoroutineContext, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    Intrinsics.checkParameterIsNotNull(paramString, "oldState");
    paramCoroutineContext = Thread.currentThread();
    Intrinsics.checkExpressionValueIsNotNull(paramCoroutineContext, "Thread.currentThread()");
    paramCoroutineContext.setName(paramString);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CoroutineId(");
    localStringBuilder.append(this.id);
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
  
  public String updateThreadContext(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    paramCoroutineContext = (CoroutineName)paramCoroutineContext.get((CoroutineContext.Key)CoroutineName.Key);
    if (paramCoroutineContext != null)
    {
      paramCoroutineContext = paramCoroutineContext.getName();
      if (paramCoroutineContext != null) {}
    }
    else
    {
      paramCoroutineContext = "coroutine";
    }
    Thread localThread = Thread.currentThread();
    Intrinsics.checkExpressionValueIsNotNull(localThread, "currentThread");
    String str1 = localThread.getName();
    Intrinsics.checkExpressionValueIsNotNull(str1, "oldName");
    int i = StringsKt.lastIndexOf$default((CharSequence)str1, " @", 0, false, 6, null);
    int j = i;
    if (i < 0) {
      j = str1.length();
    }
    StringBuilder localStringBuilder = new StringBuilder(paramCoroutineContext.length() + j + 10);
    String str2 = str1.substring(0, j);
    Intrinsics.checkExpressionValueIsNotNull(str2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
    localStringBuilder.append(str2);
    localStringBuilder.append(" @");
    localStringBuilder.append(paramCoroutineContext);
    localStringBuilder.append('#');
    localStringBuilder.append(this.id);
    paramCoroutineContext = localStringBuilder.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramCoroutineContext, "StringBuilder(capacity).…builderAction).toString()");
    localThread.setName(paramCoroutineContext);
    return str1;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b?\003\030\0002\b\022\004\022\0020\0020\001B\007\b\002?\006\002\020\003?\006\004"}, d2={"Lkotlinx/coroutines/CoroutineId$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlinx/coroutines/CoroutineId;", "()V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static final class Key
    implements CoroutineContext.Key<CoroutineId>
  {
    private Key() {}
  }
}
