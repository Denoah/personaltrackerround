package androidx.core.util;

import android.util.SparseBooleanArray;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.BooleanIterator;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\000>\n\000\n\002\020\b\n\002\030\002\n\002\b\003\n\002\020\013\n\002\b\005\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\004\n\002\030\002\n\002\b\003\n\002\030\002\n\002\b\006\n\002\030\002\n\000\032\025\020\005\032\0020\006*\0020\0022\006\020\007\032\0020\001H?\n\032\025\020\b\032\0020\006*\0020\0022\006\020\007\032\0020\001H?\b\032\025\020\t\032\0020\006*\0020\0022\006\020\n\032\0020\006H?\b\032E\020\013\032\0020\f*\0020\00226\020\r\0322\022\023\022\0210\001?\006\f\b\017\022\b\b\020\022\004\b\b(\007\022\023\022\0210\006?\006\f\b\017\022\b\b\020\022\004\b\b(\n\022\004\022\0020\f0\016H?\b\032\035\020\021\032\0020\006*\0020\0022\006\020\007\032\0020\0012\006\020\022\032\0020\006H?\b\032#\020\023\032\0020\006*\0020\0022\006\020\007\032\0020\0012\f\020\022\032\b\022\004\022\0020\0060\024H?\b\032\r\020\025\032\0020\006*\0020\002H?\b\032\r\020\026\032\0020\006*\0020\002H?\b\032\n\020\027\032\0020\030*\0020\002\032\025\020\031\032\0020\002*\0020\0022\006\020\032\032\0020\002H?\002\032\022\020\033\032\0020\f*\0020\0022\006\020\032\032\0020\002\032\032\020\034\032\0020\006*\0020\0022\006\020\007\032\0020\0012\006\020\n\032\0020\006\032\035\020\035\032\0020\f*\0020\0022\006\020\007\032\0020\0012\006\020\n\032\0020\006H?\n\032\n\020\036\032\0020\037*\0020\002\"\026\020\000\032\0020\001*\0020\0028?\002?\006\006\032\004\b\003\020\004?\006 "}, d2={"size", "", "Landroid/util/SparseBooleanArray;", "getSize", "(Landroid/util/SparseBooleanArray;)I", "contains", "", "key", "containsKey", "containsValue", "value", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "getOrElse", "Lkotlin/Function0;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", "set", "valueIterator", "Lkotlin/collections/BooleanIterator;", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class SparseBooleanArrayKt
{
  public static final boolean contains(SparseBooleanArray paramSparseBooleanArray, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$contains");
    boolean bool;
    if (paramSparseBooleanArray.indexOfKey(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean containsKey(SparseBooleanArray paramSparseBooleanArray, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$containsKey");
    boolean bool;
    if (paramSparseBooleanArray.indexOfKey(paramInt) >= 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean containsValue(SparseBooleanArray paramSparseBooleanArray, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$containsValue");
    if (paramSparseBooleanArray.indexOfValue(paramBoolean) != -1) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    }
    return paramBoolean;
  }
  
  public static final void forEach(SparseBooleanArray paramSparseBooleanArray, Function2<? super Integer, ? super Boolean, Unit> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$forEach");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "action");
    int i = paramSparseBooleanArray.size();
    for (int j = 0; j < i; j++) {
      paramFunction2.invoke(Integer.valueOf(paramSparseBooleanArray.keyAt(j)), Boolean.valueOf(paramSparseBooleanArray.valueAt(j)));
    }
  }
  
  public static final boolean getOrDefault(SparseBooleanArray paramSparseBooleanArray, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$getOrDefault");
    return paramSparseBooleanArray.get(paramInt, paramBoolean);
  }
  
  public static final boolean getOrElse(SparseBooleanArray paramSparseBooleanArray, int paramInt, Function0<Boolean> paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$getOrElse");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "defaultValue");
    paramInt = paramSparseBooleanArray.indexOfKey(paramInt);
    boolean bool;
    if (paramInt != -1) {
      bool = paramSparseBooleanArray.valueAt(paramInt);
    } else {
      bool = ((Boolean)paramFunction0.invoke()).booleanValue();
    }
    return bool;
  }
  
  public static final int getSize(SparseBooleanArray paramSparseBooleanArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$size");
    return paramSparseBooleanArray.size();
  }
  
  public static final boolean isEmpty(SparseBooleanArray paramSparseBooleanArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$isEmpty");
    boolean bool;
    if (paramSparseBooleanArray.size() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isNotEmpty(SparseBooleanArray paramSparseBooleanArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$isNotEmpty");
    boolean bool;
    if (paramSparseBooleanArray.size() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final IntIterator keyIterator(SparseBooleanArray paramSparseBooleanArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$keyIterator");
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
        SparseBooleanArray localSparseBooleanArray = this.$this_keyIterator;
        int i = this.index;
        this.index = (i + 1);
        return localSparseBooleanArray.keyAt(i);
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
  
  public static final SparseBooleanArray plus(SparseBooleanArray paramSparseBooleanArray1, SparseBooleanArray paramSparseBooleanArray2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray2, "other");
    SparseBooleanArray localSparseBooleanArray = new SparseBooleanArray(paramSparseBooleanArray1.size() + paramSparseBooleanArray2.size());
    putAll(localSparseBooleanArray, paramSparseBooleanArray1);
    putAll(localSparseBooleanArray, paramSparseBooleanArray2);
    return localSparseBooleanArray;
  }
  
  public static final void putAll(SparseBooleanArray paramSparseBooleanArray1, SparseBooleanArray paramSparseBooleanArray2)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray1, "$this$putAll");
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray2, "other");
    int i = paramSparseBooleanArray2.size();
    for (int j = 0; j < i; j++) {
      paramSparseBooleanArray1.put(paramSparseBooleanArray2.keyAt(j), paramSparseBooleanArray2.valueAt(j));
    }
  }
  
  public static final boolean remove(SparseBooleanArray paramSparseBooleanArray, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$remove");
    int i = paramSparseBooleanArray.indexOfKey(paramInt);
    if ((i != -1) && (paramBoolean == paramSparseBooleanArray.valueAt(i)))
    {
      paramSparseBooleanArray.delete(paramInt);
      return true;
    }
    return false;
  }
  
  public static final void set(SparseBooleanArray paramSparseBooleanArray, int paramInt, boolean paramBoolean)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$set");
    paramSparseBooleanArray.put(paramInt, paramBoolean);
  }
  
  public static final BooleanIterator valueIterator(SparseBooleanArray paramSparseBooleanArray)
  {
    Intrinsics.checkParameterIsNotNull(paramSparseBooleanArray, "$this$valueIterator");
    (BooleanIterator)new BooleanIterator()
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
      
      public boolean nextBoolean()
      {
        SparseBooleanArray localSparseBooleanArray = this.$this_valueIterator;
        int i = this.index;
        this.index = (i + 1);
        return localSparseBooleanArray.valueAt(i);
      }
      
      public final void setIndex(int paramAnonymousInt)
      {
        this.index = paramAnonymousInt;
      }
    };
  }
}
