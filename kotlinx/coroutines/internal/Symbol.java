package kotlinx.coroutines.internal;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\000\n\002\020\016\n\002\b\t\b\000\030\0002\0020\001B\r\022\006\020\002\032\0020\003?\006\002\020\004J\b\020\007\032\0020\003H\026J\036\020\b\032\002H\t\"\004\b\000\020\t2\b\020\n\032\004\030\0010\001H?\b?\006\002\020\013R\021\020\002\032\0020\003?\006\b\n\000\032\004\b\005\020\006?\006\f"}, d2={"Lkotlinx/coroutines/internal/Symbol;", "", "symbol", "", "(Ljava/lang/String;)V", "getSymbol", "()Ljava/lang/String;", "toString", "unbox", "T", "value", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class Symbol
{
  private final String symbol;
  
  public Symbol(String paramString)
  {
    this.symbol = paramString;
  }
  
  public final String getSymbol()
  {
    return this.symbol;
  }
  
  public String toString()
  {
    return this.symbol;
  }
  
  public final <T> T unbox(Object paramObject)
  {
    Object localObject = paramObject;
    if (paramObject == (Symbol)this) {
      localObject = null;
    }
    return localObject;
  }
}
