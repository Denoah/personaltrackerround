package kotlin.coroutines;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref.IntRef;

@Metadata(bv={1, 0, 3}, d1={"\000L\n\002\030\002\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\002\n\002\020\013\n\002\b\004\n\002\020\000\n\002\b\004\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\003\n\002\020\016\n\002\b\003\b\001\030\0002\0020\0012\0060\002j\002`\003:\001!B\025\022\006\020\004\032\0020\001\022\006\020\005\032\0020\006?\006\002\020\007J\020\020\b\032\0020\t2\006\020\005\032\0020\006H\002J\020\020\n\032\0020\t2\006\020\013\032\0020\000H\002J\023\020\f\032\0020\t2\b\020\r\032\004\030\0010\016H?\002J5\020\017\032\002H\020\"\004\b\000\020\0202\006\020\021\032\002H\0202\030\020\022\032\024\022\004\022\002H\020\022\004\022\0020\006\022\004\022\002H\0200\023H\026?\006\002\020\024J(\020\025\032\004\030\001H\026\"\b\b\000\020\026*\0020\0062\f\020\027\032\b\022\004\022\002H\0260\030H?\002?\006\002\020\031J\b\020\032\032\0020\033H\026J\024\020\034\032\0020\0012\n\020\027\032\006\022\002\b\0030\030H\026J\b\020\035\032\0020\033H\002J\b\020\036\032\0020\037H\026J\b\020 \032\0020\016H\002R\016\020\005\032\0020\006X?\004?\006\002\n\000R\016\020\004\032\0020\001X?\004?\006\002\n\000?\006\""}, d2={"Lkotlin/coroutines/CombinedContext;", "Lkotlin/coroutines/CoroutineContext;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "left", "element", "Lkotlin/coroutines/CoroutineContext$Element;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/CoroutineContext$Element;)V", "contains", "", "containsAll", "context", "equals", "other", "", "fold", "R", "initial", "operation", "Lkotlin/Function2;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "get", "E", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "hashCode", "", "minusKey", "size", "toString", "", "writeReplace", "Serialized", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class CombinedContext
  implements CoroutineContext, Serializable
{
  private final CoroutineContext.Element element;
  private final CoroutineContext left;
  
  public CombinedContext(CoroutineContext paramCoroutineContext, CoroutineContext.Element paramElement)
  {
    this.left = paramCoroutineContext;
    this.element = paramElement;
  }
  
  private final boolean contains(CoroutineContext.Element paramElement)
  {
    return Intrinsics.areEqual(get(paramElement.getKey()), paramElement);
  }
  
  private final boolean containsAll(CombinedContext paramCombinedContext)
  {
    for (;;)
    {
      if (!contains(paramCombinedContext.element)) {
        return false;
      }
      paramCombinedContext = paramCombinedContext.left;
      if (!(paramCombinedContext instanceof CombinedContext)) {
        break;
      }
      paramCombinedContext = (CombinedContext)paramCombinedContext;
    }
    if (paramCombinedContext != null) {
      return contains((CoroutineContext.Element)paramCombinedContext);
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.CoroutineContext.Element");
  }
  
  private final int size()
  {
    Object localObject = (CombinedContext)this;
    for (int i = 2;; i++)
    {
      CoroutineContext localCoroutineContext = ((CombinedContext)localObject).left;
      localObject = localCoroutineContext;
      if (!(localCoroutineContext instanceof CombinedContext)) {
        localObject = null;
      }
      localObject = (CombinedContext)localObject;
      if (localObject == null) {
        break;
      }
    }
    return i;
  }
  
  private final Object writeReplace()
  {
    int i = size();
    CoroutineContext[] arrayOfCoroutineContext = new CoroutineContext[i];
    final Ref.IntRef localIntRef = new Ref.IntRef();
    int j = 0;
    localIntRef.element = 0;
    fold(Unit.INSTANCE, (Function2)new Lambda(arrayOfCoroutineContext)
    {
      public final void invoke(Unit paramAnonymousUnit, CoroutineContext.Element paramAnonymousElement)
      {
        Intrinsics.checkParameterIsNotNull(paramAnonymousUnit, "<anonymous parameter 0>");
        Intrinsics.checkParameterIsNotNull(paramAnonymousElement, "element");
        paramAnonymousUnit = this.$elements;
        Ref.IntRef localIntRef = localIntRef;
        int i = localIntRef.element;
        localIntRef.element = (i + 1);
        paramAnonymousUnit[i] = ((CoroutineContext)paramAnonymousElement);
      }
    });
    if (localIntRef.element == i) {
      j = 1;
    }
    if (j != 0) {
      return new Serialized(arrayOfCoroutineContext);
    }
    throw ((Throwable)new IllegalStateException("Check failed.".toString()));
  }
  
  public boolean equals(Object paramObject)
  {
    if ((CombinedContext)this != paramObject) {
      if ((paramObject instanceof CombinedContext))
      {
        paramObject = (CombinedContext)paramObject;
        if ((paramObject.size() == size()) && (paramObject.containsAll(this))) {}
      }
      else
      {
        return false;
      }
    }
    boolean bool = true;
    return bool;
  }
  
  public <R> R fold(R paramR, Function2<? super R, ? super CoroutineContext.Element, ? extends R> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramFunction2, "operation");
    return paramFunction2.invoke(this.left.fold(paramR, paramFunction2), this.element);
  }
  
  public <E extends CoroutineContext.Element> E get(CoroutineContext.Key<E> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    for (Object localObject = (CombinedContext)this;; localObject = (CombinedContext)localObject)
    {
      CoroutineContext.Element localElement = ((CombinedContext)localObject).element.get(paramKey);
      if (localElement != null) {
        return localElement;
      }
      localObject = ((CombinedContext)localObject).left;
      if (!(localObject instanceof CombinedContext)) {
        break;
      }
    }
    return ((CoroutineContext)localObject).get(paramKey);
  }
  
  public int hashCode()
  {
    return this.left.hashCode() + this.element.hashCode();
  }
  
  public CoroutineContext minusKey(CoroutineContext.Key<?> paramKey)
  {
    Intrinsics.checkParameterIsNotNull(paramKey, "key");
    if (this.element.get(paramKey) != null) {
      return this.left;
    }
    paramKey = this.left.minusKey(paramKey);
    if (paramKey == this.left) {
      paramKey = (CoroutineContext)this;
    } else if (paramKey == EmptyCoroutineContext.INSTANCE) {
      paramKey = (CoroutineContext)this.element;
    } else {
      paramKey = (CoroutineContext)new CombinedContext(paramKey, this.element);
    }
    return paramKey;
  }
  
  public CoroutineContext plus(CoroutineContext paramCoroutineContext)
  {
    Intrinsics.checkParameterIsNotNull(paramCoroutineContext, "context");
    return CoroutineContext.DefaultImpls.plus(this, paramCoroutineContext);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[");
    localStringBuilder.append((String)fold("", (Function2)toString.1.INSTANCE));
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\"\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\b\005\n\002\020\000\n\002\b\002\b\002\030\000 \f2\0060\001j\002`\002:\001\fB\023\022\f\020\003\032\b\022\004\022\0020\0050\004?\006\002\020\006J\b\020\n\032\0020\013H\002R\031\020\003\032\b\022\004\022\0020\0050\004?\006\n\n\002\020\t\032\004\b\007\020\b?\006\r"}, d2={"Lkotlin/coroutines/CombinedContext$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "elements", "", "Lkotlin/coroutines/CoroutineContext;", "([Lkotlin/coroutines/CoroutineContext;)V", "getElements", "()[Lkotlin/coroutines/CoroutineContext;", "[Lkotlin/coroutines/CoroutineContext;", "readResolve", "", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
  private static final class Serialized
    implements Serializable
  {
    public static final Companion Companion = new Companion(null);
    private static final long serialVersionUID = 0L;
    private final CoroutineContext[] elements;
    
    public Serialized(CoroutineContext[] paramArrayOfCoroutineContext)
    {
      this.elements = paramArrayOfCoroutineContext;
    }
    
    private final Object readResolve()
    {
      CoroutineContext[] arrayOfCoroutineContext = this.elements;
      Object localObject = EmptyCoroutineContext.INSTANCE;
      int i = arrayOfCoroutineContext.length;
      for (int j = 0; j < i; j++)
      {
        CoroutineContext localCoroutineContext = arrayOfCoroutineContext[j];
        localObject = ((CoroutineContext)localObject).plus(localCoroutineContext);
      }
      return localObject;
    }
    
    public final CoroutineContext[] getElements()
    {
      return this.elements;
    }
    
    @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\t\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002R\016\020\003\032\0020\004X?T?\006\002\n\000?\006\005"}, d2={"Lkotlin/coroutines/CombinedContext$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion
    {
      private Companion() {}
    }
  }
}
