package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000,\n\002\030\002\n\000\n\002\020\000\n\002\b\004\n\002\020\003\n\002\b\005\n\002\020\013\n\002\b\013\n\002\020\b\n\000\n\002\020\016\n\002\b\005\b?@\030\000 \037*\006\b\000\020\001 \0012\0020\002:\002\036\037B\026\b\000\022\b\020\003\032\004\030\0010\002?\001\000?\006\004\b\004\020\005J\023\020\026\032\0020\r2\b\020\027\032\004\030\0010\002H?\003J\t\020\030\032\0020\031H?\001J\017\020\032\032\0020\033H\026?\006\004\b\034\020\035R\031\020\006\032\004\030\0010\0078F?\006\f\022\004\b\b\020\t\032\004\b\n\020\013R\020\020\003\032\004\030\0010\002X?\004?\006\002\n\000R\021\020\f\032\0020\r8F?\006\006\032\004\b\016\020\017R\027\020\020\032\0028\0008F?\006\f\022\004\b\021\020\t\032\004\b\022\020\005R\031\020\023\032\004\030\0018\0008F?\006\f\022\004\b\024\020\t\032\004\b\025\020\005?\001\000?\002\004\n\002\b\031?\006 "}, d2={"Lkotlinx/coroutines/channels/ValueOrClosed;", "T", "", "holder", "constructor-impl", "(Ljava/lang/Object;)Ljava/lang/Object;", "closeCause", "", "closeCause$annotations", "()V", "getCloseCause-impl", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "isClosed", "", "isClosed-impl", "(Ljava/lang/Object;)Z", "value", "value$annotations", "getValue-impl", "valueOrNull", "valueOrNull$annotations", "getValueOrNull-impl", "equals", "other", "hashCode", "", "toString", "", "toString-impl", "(Ljava/lang/Object;)Ljava/lang/String;", "Closed", "Companion", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class ValueOrClosed<T>
{
  public static final Companion Companion = new Companion(null);
  private final Object holder;
  
  public static Object constructor-impl(Object paramObject)
  {
    return paramObject;
  }
  
  public static boolean equals-impl(Object paramObject1, Object paramObject2)
  {
    return ((paramObject2 instanceof ValueOrClosed)) && (Intrinsics.areEqual(paramObject1, ((ValueOrClosed)paramObject2).unbox-impl()));
  }
  
  public static final boolean equals-impl0(Object paramObject1, Object paramObject2)
  {
    return Intrinsics.areEqual(paramObject1, paramObject2);
  }
  
  public static final Throwable getCloseCause-impl(Object paramObject)
  {
    if ((paramObject instanceof Closed)) {
      return ((Closed)paramObject).cause;
    }
    throw ((Throwable)new IllegalStateException("Channel was not closed".toString()));
  }
  
  public static final T getValue-impl(Object paramObject)
  {
    if (!(paramObject instanceof Closed)) {
      return paramObject;
    }
    throw ((Throwable)new IllegalStateException("Channel was closed".toString()));
  }
  
  public static final T getValueOrNull-impl(Object paramObject)
  {
    Object localObject = paramObject;
    if ((paramObject instanceof Closed)) {
      localObject = null;
    }
    return localObject;
  }
  
  public static int hashCode-impl(Object paramObject)
  {
    int i;
    if (paramObject != null) {
      i = paramObject.hashCode();
    } else {
      i = 0;
    }
    return i;
  }
  
  public static final boolean isClosed-impl(Object paramObject)
  {
    return paramObject instanceof Closed;
  }
  
  public static String toString-impl(Object paramObject)
  {
    if ((paramObject instanceof Closed))
    {
      paramObject = paramObject.toString();
    }
    else
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Value(");
      localStringBuilder.append(paramObject);
      localStringBuilder.append(')');
      paramObject = localStringBuilder.toString();
    }
    return paramObject;
  }
  
  public boolean equals(Object paramObject)
  {
    return equals-impl(this.holder, paramObject);
  }
  
  public int hashCode()
  {
    return hashCode-impl(this.holder);
  }
  
  public String toString()
  {
    return toString-impl(this.holder);
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000&\n\002\030\002\n\002\020\000\n\000\n\002\020\003\n\002\b\002\n\002\020\013\n\002\b\002\n\002\020\b\n\000\n\002\020\016\n\000\b\000\030\0002\0020\001B\017\022\b\020\002\032\004\030\0010\003?\006\002\020\004J\023\020\005\032\0020\0062\b\020\007\032\004\030\0010\001H?\002J\b\020\b\032\0020\tH\026J\b\020\n\032\0020\013H\026R\022\020\002\032\004\030\0010\0038\006X?\004?\006\002\n\000?\006\f"}, d2={"Lkotlinx/coroutines/channels/ValueOrClosed$Closed;", "", "cause", "", "(Ljava/lang/Throwable;)V", "equals", "", "other", "hashCode", "", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static final class Closed
  {
    public final Throwable cause;
    
    public Closed(Throwable paramThrowable)
    {
      this.cause = paramThrowable;
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool;
      if (((paramObject instanceof Closed)) && (Intrinsics.areEqual(this.cause, ((Closed)paramObject).cause))) {
        bool = true;
      } else {
        bool = false;
      }
      return bool;
    }
    
    public int hashCode()
    {
      Throwable localThrowable = this.cause;
      int i;
      if (localThrowable != null) {
        i = localThrowable.hashCode();
      } else {
        i = 0;
      }
      return i;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Closed(");
      localStringBuilder.append(this.cause);
      localStringBuilder.append(')');
      return localStringBuilder.toString();
    }
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\003\n\002\b\006\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J)\020\003\032\b\022\004\022\002H\0050\004\"\004\b\001\020\0052\b\020\006\032\004\030\0010\007H?\b?\001\000?\006\004\b\b\020\tJ'\020\n\032\b\022\004\022\002H\0050\004\"\004\b\001\020\0052\006\020\n\032\002H\005H?\b?\001\000?\006\004\b\013\020\f?\002\004\n\002\b\031?\006\r"}, d2={"Lkotlinx/coroutines/channels/ValueOrClosed$Companion;", "", "()V", "closed", "Lkotlinx/coroutines/channels/ValueOrClosed;", "E", "cause", "", "closed$kotlinx_coroutines_core", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "value", "value$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final <E> Object closed$kotlinx_coroutines_core(Throwable paramThrowable)
    {
      return ValueOrClosed.constructor-impl(new ValueOrClosed.Closed(paramThrowable));
    }
    
    public final <E> Object value$kotlinx_coroutines_core(E paramE)
    {
      return ValueOrClosed.constructor-impl(paramE);
    }
  }
}
