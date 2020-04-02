package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext.Key;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\030\002\n\000\n\002\020\016\n\002\b\006\n\002\020\013\n\000\n\002\020\000\n\000\n\002\020\b\n\002\b\003\b?\b\030\000 \0202\0020\001:\001\020B\r\022\006\020\002\032\0020\003?\006\002\020\004J\t\020\007\032\0020\003H?\003J\023\020\b\032\0020\0002\b\b\002\020\002\032\0020\003H?\001J\023\020\t\032\0020\n2\b\020\013\032\004\030\0010\fH?\003J\t\020\r\032\0020\016H?\001J\b\020\017\032\0020\003H\026R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\005\020\006?\006\021"}, d2={"Lkotlinx/coroutines/CoroutineName;", "Lkotlin/coroutines/AbstractCoroutineContextElement;", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "Key", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class CoroutineName
  extends AbstractCoroutineContextElement
{
  public static final Key Key = new Key(null);
  private final String name;
  
  public CoroutineName(String paramString)
  {
    super((CoroutineContext.Key)Key);
    this.name = paramString;
  }
  
  public final String component1()
  {
    return this.name;
  }
  
  public final CoroutineName copy(String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramString, "name");
    return new CoroutineName(paramString);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this != paramObject) {
      if ((paramObject instanceof CoroutineName))
      {
        paramObject = (CoroutineName)paramObject;
        if (Intrinsics.areEqual(this.name, paramObject.name)) {}
      }
      else
      {
        return false;
      }
    }
    return true;
  }
  
  public final String getName()
  {
    return this.name;
  }
  
  public int hashCode()
  {
    String str = this.name;
    int i;
    if (str != null) {
      i = str.hashCode();
    } else {
      i = 0;
    }
    return i;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CoroutineName(");
    localStringBuilder.append(this.name);
    localStringBuilder.append(')');
    return localStringBuilder.toString();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\020\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\b?\003\030\0002\b\022\004\022\0020\0020\001B\007\b\002?\006\002\020\003?\006\004"}, d2={"Lkotlinx/coroutines/CoroutineName$Key;", "Lkotlin/coroutines/CoroutineContext$Key;", "Lkotlinx/coroutines/CoroutineName;", "()V", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static final class Key
    implements CoroutineContext.Key<CoroutineName>
  {
    private Key() {}
  }
}
