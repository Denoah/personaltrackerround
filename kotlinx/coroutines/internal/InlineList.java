package kotlinx.coroutines.internal;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;

@Metadata(bv={1, 0, 3}, d1={"\0002\n\002\030\002\n\000\n\002\020\000\n\002\b\004\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\005\n\002\020\016\n\000\b?@\030\000*\004\b\000\020\0012\0020\002B\026\022\n\b\002\020\003\032\004\030\0010\002?\001\000?\006\004\b\004\020\005J\023\020\006\032\0020\0072\b\020\b\032\004\030\0010\002H?\003J$\020\t\032\0020\n2\022\020\013\032\016\022\004\022\0028\000\022\004\022\0020\n0\fH?\b?\006\004\b\r\020\016J\t\020\017\032\0020\020H?\001J!\020\021\032\b\022\004\022\0028\0000\0002\006\020\022\032\0028\000H?\002?\001\000?\006\004\b\023\020\024J\t\020\025\032\0020\026H?\001R\020\020\003\032\004\030\0010\002X?\004?\006\002\n\000?\001\000?\002\004\n\002\b\031?\006\027"}, d2={"Lkotlinx/coroutines/internal/InlineList;", "E", "", "holder", "constructor-impl", "(Ljava/lang/Object;)Ljava/lang/Object;", "equals", "", "other", "forEachReversed", "", "action", "Lkotlin/Function1;", "forEachReversed-impl", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "hashCode", "", "plus", "element", "plus-impl", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "kotlinx-coroutines-core"}, k=1, mv={1, 1, 16})
public final class InlineList<E>
{
  private final Object holder;
  
  public static Object constructor-impl(Object paramObject)
  {
    return paramObject;
  }
  
  public static boolean equals-impl(Object paramObject1, Object paramObject2)
  {
    return ((paramObject2 instanceof InlineList)) && (Intrinsics.areEqual(paramObject1, ((InlineList)paramObject2).unbox-impl()));
  }
  
  public static final boolean equals-impl0(Object paramObject1, Object paramObject2)
  {
    return Intrinsics.areEqual(paramObject1, paramObject2);
  }
  
  public static final void forEachReversed-impl(Object paramObject, Function1<? super E, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    if (paramObject == null) {
      return;
    }
    if (!(paramObject instanceof ArrayList))
    {
      paramFunction1.invoke(paramObject);
    }
    else
    {
      if (paramObject == null) {
        break label68;
      }
      paramObject = (ArrayList)paramObject;
      for (int i = paramObject.size() - 1; i >= 0; i--) {
        paramFunction1.invoke(paramObject.get(i));
      }
    }
    return;
    label68:
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<E> /* = java.util.ArrayList<E> */");
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
  
  public static final Object plus-impl(Object paramObject, E paramE)
  {
    if ((DebugKt.getASSERTIONS_ENABLED()) && (!(paramE instanceof List ^ true))) {
      throw ((Throwable)new AssertionError());
    }
    if (paramObject == null)
    {
      paramObject = constructor-impl(paramE);
    }
    else if ((paramObject instanceof ArrayList))
    {
      if (paramObject != null)
      {
        ((ArrayList)paramObject).add(paramE);
        paramObject = constructor-impl(paramObject);
      }
      else
      {
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<E> /* = java.util.ArrayList<E> */");
      }
    }
    else
    {
      ArrayList localArrayList = new ArrayList(4);
      localArrayList.add(paramObject);
      localArrayList.add(paramE);
      paramObject = constructor-impl(localArrayList);
    }
    return paramObject;
  }
  
  public static String toString-impl(Object paramObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("InlineList(holder=");
    localStringBuilder.append(paramObject);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
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
}
