package androidx.core.util;

import android.util.SparseLongArray;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.collections.LongIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000D\n\000\n\002\020\b\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\004\n\002\020\t\n\000\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\006\n\002\030\002\n\000\032\025\020\005\032\0020\006*\0020\0022\006\020\007\032\0020\001H?\n\032\025\020\b\032\0020\006*\0020\0022\006\020\007\032\0020\001H?\b\032\025\020\t\032\0020\006*\0020\0022\006\020\n\032\0020\013H?\b\032E\020\f\032\0020\r*\0020\00226\020\016\0322\022\023\022\0210\001?\006\f\b\020\022\b\b\021\022\004\b\b(\007\022\023\022\0210\013?\006\f\b\020\022\b\b\021\022\004\b\b(\n\022\004\022\0020\r0\017H?\b\032\035\020\022\032\0020\013*\0020\0022\006\020\007\032\0020\0012\006\020\023\032\0020\013H?\b\032#\020\024\032\0020\013*\0020\0022\006\020\007\032\0020\0012\f\020\023\032\b\022\004\022\0020\0130\025H?\b\032\r\020\026\032\0020\006*\0020\002H?\b\032\r\020\027\032\0020\006*\0020\002H?\b\032\f\020\030\032\0020\031*\0020\002H\007\032\025\020\032\032\0020\002*\0020\0022\006\020\033\032\0020\002H?\002\032\024\020\034\032\0020\r*\0020\0022\006\020\033\032\0020\002H\007\032\034\020\035\032\0020\006*\0020\0022\006\020\007\032\0020\0012\006\020\n\032\0020\013H\007\032\035\020\036\032\0020\r*\0020\0022\006\020\007\032\0020\0012\006\020\n\032\0020\013H?\n\032\f\020\037\032\0020 *\0020\002H\007\"\026\020\000\032\0020\001*\0020\0028?\002?\006\006\032\004\b\003\020\004?\006!"}, d2={"size", "", "Landroid/util/SparseLongArray;", "getSize", "(Landroid/util/SparseLongArray;)I", "contains", "", "key", "containsKey", "containsValue", "value", "", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "getOrElse", "Lkotlin/Function0;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", "set", "valueIterator", "Lkotlin/collections/LongIterator;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class SparseLongArrayKt
{
  public static final boolean contains(SparseLongArray paramSparseLongArray, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$contains");
    boolean bool;
    if (paramSparseLongArray.indexOfKey(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean containsKey(SparseLongArray paramSparseLongArray, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$containsKey");
    boolean bool;
    if (paramSparseLongArray.indexOfKey(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean containsValue(SparseLongArray paramSparseLongArray, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$containsValue");
    boolean bool;
    if (paramSparseLongArray.indexOfValue(paramLong) != -1) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final void forEach(SparseLongArray paramSparseLongArray, Function2<? super Integer, ? super Long, Unit> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$forEach");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "action");
    int i = paramSparseLongArray.size();
    for (int j = 0; j < i; j++) {
      paramFunction2.invoke(Integer.valueOf(paramSparseLongArray.keyAt(j)), Long.valueOf(paramSparseLongArray.valueAt(j)));
    }
  }
  
  public static final long getOrDefault(SparseLongArray paramSparseLongArray, int paramInt, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$getOrDefault");
    return paramSparseLongArray.get(paramInt, paramLong);
  }
  
  public static final long getOrElse(SparseLongArray paramSparseLongArray, int paramInt, Function0<Long> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$getOrElse");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "defaultValue");
    paramInt = paramSparseLongArray.indexOfKey(paramInt);
    long l;
    if (paramInt != -1) {
      l = paramSparseLongArray.valueAt(paramInt);
    } else {
      l = ((Number)paramFunction0.invoke()).longValue();
    }
    return l;
  }
  
  public static final int getSize(SparseLongArray paramSparseLongArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$size");
    return paramSparseLongArray.size();
  }
  
  public static final boolean isEmpty(SparseLongArray paramSparseLongArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$isEmpty");
    boolean bool;
    if (paramSparseLongArray.size() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isNotEmpty(SparseLongArray paramSparseLongArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$isNotEmpty");
    boolean bool;
    if (paramSparseLongArray.size() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final IntIterator keyIterator(SparseLongArray paramSparseLongArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$keyIterator");
    (IntIterator)new IntIterator()
    {
      private int index;
      
      public final int getIndex()
      {
        return this.index;
      }
      
      public boolean hasNext()
      {
        boolean bool;
        if (this.index < this.$this_keyIterator.size()) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public int nextInt()
      {
        SparseLongArray localSparseLongArray = this.$this_keyIterator;
        int i = this.index;
        this.index = (i + 1);
        return localSparseLongArray.keyAt(i);
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
  
  public static final SparseLongArray plus(SparseLongArray paramSparseLongArray1, SparseLongArray paramSparseLongArray2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray2, "other");
    SparseLongArray localSparseLongArray = new SparseLongArray(paramSparseLongArray1.size() + paramSparseLongArray2.size());
    putAll(localSparseLongArray, paramSparseLongArray1);
    putAll(localSparseLongArray, paramSparseLongArray2);
    return localSparseLongArray;
  }
  
  public static final void putAll(SparseLongArray paramSparseLongArray1, SparseLongArray paramSparseLongArray2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray1, "$this$putAll");
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray2, "other");
    int i = paramSparseLongArray2.size();
    for (int j = 0; j < i; j++) {
      paramSparseLongArray1.put(paramSparseLongArray2.keyAt(j), paramSparseLongArray2.valueAt(j));
    }
  }
  
  public static final boolean remove(SparseLongArray paramSparseLongArray, int paramInt, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$remove");
    paramInt = paramSparseLongArray.indexOfKey(paramInt);
    if ((paramInt != -1) && (paramLong == paramSparseLongArray.valueAt(paramInt)))
    {
      paramSparseLongArray.removeAt(paramInt);
      return true;
    }
    return false;
  }
  
  public static final void set(SparseLongArray paramSparseLongArray, int paramInt, long paramLong)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$set");
    paramSparseLongArray.put(paramInt, paramLong);
  }
  
  public static final LongIterator valueIterator(SparseLongArray paramSparseLongArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseLongArray, "$this$valueIterator");
    (LongIterator)new LongIterator()
    {
      private int index;
      
      public final int getIndex()
      {
        return this.index;
      }
      
      public boolean hasNext()
      {
        boolean bool;
        if (this.index < this.$this_valueIterator.size()) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public long nextLong()
      {
        SparseLongArray localSparseLongArray = this.$this_valueIterator;
        int i = this.index;
        this.index = (i + 1);
        return localSparseLongArray.valueAt(i);
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
}
