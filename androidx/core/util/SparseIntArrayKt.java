package androidx.core.util;

import android.util.SparseIntArray;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\0008\n\000\n\002\020\b\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\007\032\025\020\005\032\0020\006*\0020\0022\006\020\007\032\0020\001H?\n\032\025\020\b\032\0020\006*\0020\0022\006\020\007\032\0020\001H?\b\032\025\020\t\032\0020\006*\0020\0022\006\020\n\032\0020\001H?\b\032E\020\013\032\0020\f*\0020\00226\020\r\0322\022\023\022\0210\001?\006\f\b\017\022\b\b\020\022\004\b\b(\007\022\023\022\0210\001?\006\f\b\017\022\b\b\020\022\004\b\b(\n\022\004\022\0020\f0\016H?\b\032\035\020\021\032\0020\001*\0020\0022\006\020\007\032\0020\0012\006\020\022\032\0020\001H?\b\032#\020\023\032\0020\001*\0020\0022\006\020\007\032\0020\0012\f\020\022\032\b\022\004\022\0020\0010\024H?\b\032\r\020\025\032\0020\006*\0020\002H?\b\032\r\020\026\032\0020\006*\0020\002H?\b\032\n\020\027\032\0020\030*\0020\002\032\025\020\031\032\0020\002*\0020\0022\006\020\032\032\0020\002H?\002\032\022\020\033\032\0020\f*\0020\0022\006\020\032\032\0020\002\032\032\020\034\032\0020\006*\0020\0022\006\020\007\032\0020\0012\006\020\n\032\0020\001\032\035\020\035\032\0020\f*\0020\0022\006\020\007\032\0020\0012\006\020\n\032\0020\001H?\n\032\n\020\036\032\0020\030*\0020\002\"\026\020\000\032\0020\001*\0020\0028?\002?\006\006\032\004\b\003\020\004?\006\037"}, d2={"size", "", "Landroid/util/SparseIntArray;", "getSize", "(Landroid/util/SparseIntArray;)I", "contains", "", "key", "containsKey", "containsValue", "value", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "getOrElse", "Lkotlin/Function0;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", "set", "valueIterator", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class SparseIntArrayKt
{
  public static final boolean contains(SparseIntArray paramSparseIntArray, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$contains");
    boolean bool;
    if (paramSparseIntArray.indexOfKey(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean containsKey(SparseIntArray paramSparseIntArray, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$containsKey");
    boolean bool;
    if (paramSparseIntArray.indexOfKey(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean containsValue(SparseIntArray paramSparseIntArray, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$containsValue");
    boolean bool;
    if (paramSparseIntArray.indexOfValue(paramInt) != -1) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final void forEach(SparseIntArray paramSparseIntArray, Function2<? super Integer, ? super Integer, Unit> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$forEach");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "action");
    int i = paramSparseIntArray.size();
    for (int j = 0; j < i; j++) {
      paramFunction2.invoke(Integer.valueOf(paramSparseIntArray.keyAt(j)), Integer.valueOf(paramSparseIntArray.valueAt(j)));
    }
  }
  
  public static final int getOrDefault(SparseIntArray paramSparseIntArray, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$getOrDefault");
    return paramSparseIntArray.get(paramInt1, paramInt2);
  }
  
  public static final int getOrElse(SparseIntArray paramSparseIntArray, int paramInt, Function0<Integer> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$getOrElse");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "defaultValue");
    paramInt = paramSparseIntArray.indexOfKey(paramInt);
    if (paramInt != -1) {
      paramInt = paramSparseIntArray.valueAt(paramInt);
    } else {
      paramInt = ((Number)paramFunction0.invoke()).intValue();
    }
    return paramInt;
  }
  
  public static final int getSize(SparseIntArray paramSparseIntArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$size");
    return paramSparseIntArray.size();
  }
  
  public static final boolean isEmpty(SparseIntArray paramSparseIntArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$isEmpty");
    boolean bool;
    if (paramSparseIntArray.size() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isNotEmpty(SparseIntArray paramSparseIntArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$isNotEmpty");
    boolean bool;
    if (paramSparseIntArray.size() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final IntIterator keyIterator(SparseIntArray paramSparseIntArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$keyIterator");
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
        SparseIntArray localSparseIntArray = this.$this_keyIterator;
        int i = this.index;
        this.index = (i + 1);
        return localSparseIntArray.keyAt(i);
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
  
  public static final SparseIntArray plus(SparseIntArray paramSparseIntArray1, SparseIntArray paramSparseIntArray2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray2, "other");
    SparseIntArray localSparseIntArray = new SparseIntArray(paramSparseIntArray1.size() + paramSparseIntArray2.size());
    putAll(localSparseIntArray, paramSparseIntArray1);
    putAll(localSparseIntArray, paramSparseIntArray2);
    return localSparseIntArray;
  }
  
  public static final void putAll(SparseIntArray paramSparseIntArray1, SparseIntArray paramSparseIntArray2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray1, "$this$putAll");
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray2, "other");
    int i = paramSparseIntArray2.size();
    for (int j = 0; j < i; j++) {
      paramSparseIntArray1.put(paramSparseIntArray2.keyAt(j), paramSparseIntArray2.valueAt(j));
    }
  }
  
  public static final boolean remove(SparseIntArray paramSparseIntArray, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$remove");
    paramInt1 = paramSparseIntArray.indexOfKey(paramInt1);
    if ((paramInt1 != -1) && (paramInt2 == paramSparseIntArray.valueAt(paramInt1)))
    {
      paramSparseIntArray.removeAt(paramInt1);
      return true;
    }
    return false;
  }
  
  public static final void set(SparseIntArray paramSparseIntArray, int paramInt1, int paramInt2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$set");
    paramSparseIntArray.put(paramInt1, paramInt2);
  }
  
  public static final IntIterator valueIterator(SparseIntArray paramSparseIntArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseIntArray, "$this$valueIterator");
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
        if (this.index < this.$this_valueIterator.size()) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public int nextInt()
      {
        SparseIntArray localSparseIntArray = this.$this_valueIterator;
        int i = this.index;
        this.index = (i + 1);
        return localSparseIntArray.valueAt(i);
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
}
