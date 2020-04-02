package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\000L\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\003\n\002\020\b\n\002\b\003\n\002\020\013\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\b\005\n\002\020)\n\002\b\003\n\002\030\002\n\002\b\t\032\025\020\n\032\0020\013*\0020\0032\006\020\f\032\0020\002H?\n\0320\020\r\032\0020\016*\0020\0032!\020\017\032\035\022\023\022\0210\002?\006\f\b\021\022\b\b\022\022\004\b\b(\f\022\004\022\0020\0160\020H?\b\032E\020\023\032\0020\016*\0020\00326\020\017\0322\022\023\022\0210\007?\006\f\b\021\022\b\b\022\022\004\b\b(\025\022\023\022\0210\002?\006\f\b\021\022\b\b\022\022\004\b\b(\f\022\004\022\0020\0160\024H?\b\032\025\020\026\032\0020\002*\0020\0032\006\020\025\032\0020\007H?\002\032\r\020\027\032\0020\013*\0020\003H?\b\032\r\020\030\032\0020\013*\0020\003H?\b\032\023\020\031\032\b\022\004\022\0020\0020\032*\0020\003H?\002\032\025\020\033\032\0020\016*\0020\0032\006\020\f\032\0020\002H?\n\032\025\020\034\032\0020\016*\0020\0032\006\020\f\032\0020\002H?\n\032\027\020\035\032\0020\016*\0020\0362\b\b\001\020\006\032\0020\007H?\b\0325\020\037\032\0020\016*\0020\0362\b\b\003\020 \032\0020\0072\b\b\003\020!\032\0020\0072\b\b\003\020\"\032\0020\0072\b\b\003\020#\032\0020\007H?\b\0325\020$\032\0020\016*\0020\0362\b\b\003\020%\032\0020\0072\b\b\003\020!\032\0020\0072\b\b\003\020&\032\0020\0072\b\b\003\020#\032\0020\007H?\b\"\033\020\000\032\b\022\004\022\0020\0020\001*\0020\0038F?\006\006\032\004\b\004\020\005\"\026\020\006\032\0020\007*\0020\0038?\002?\006\006\032\004\b\b\020\t?\006'"}, d2={"children", "Lkotlin/sequences/Sequence;", "Landroid/view/View;", "Landroid/view/ViewGroup;", "getChildren", "(Landroid/view/ViewGroup;)Lkotlin/sequences/Sequence;", "size", "", "getSize", "(Landroid/view/ViewGroup;)I", "contains", "", "view", "forEach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "forEachIndexed", "Lkotlin/Function2;", "index", "get", "isEmpty", "isNotEmpty", "iterator", "", "minusAssign", "plusAssign", "setMargins", "Landroid/view/ViewGroup$MarginLayoutParams;", "updateMargins", "left", "top", "right", "bottom", "updateMarginsRelative", "start", "end", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class ViewGroupKt
{
  public static final boolean contains(ViewGroup paramViewGroup, View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "$this$contains");
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    boolean bool;
    if (paramViewGroup.indexOfChild(paramView) != -1) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final void forEach(ViewGroup paramViewGroup, Function1<? super View, Unit> paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "$this$forEach");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "action");
    int i = paramViewGroup.getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = paramViewGroup.getChildAt(j);
      Intrinsics.checkExpressionValueIsNotNull(localView, "getChildAt(index)");
      paramFunction1.invoke(localView);
    }
  }
  
  public static final void forEachIndexed(ViewGroup paramViewGroup, Function2<? super Integer, ? super View, Unit> paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "$this$forEachIndexed");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "action");
    int i = paramViewGroup.getChildCount();
    for (int j = 0; j < i; j++)
    {
      View localView = paramViewGroup.getChildAt(j);
      Intrinsics.checkExpressionValueIsNotNull(localView, "getChildAt(index)");
      paramFunction2.invoke(Integer.valueOf(j), localView);
    }
  }
  
  public static final View get(ViewGroup paramViewGroup, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "$this$get");
    Object localObject = paramViewGroup.getChildAt(paramInt);
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("Index: ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append(", Size: ");
    ((StringBuilder)localObject).append(paramViewGroup.getChildCount());
    throw ((Throwable)new IndexOutOfBoundsException(((StringBuilder)localObject).toString()));
  }
  
  public static final Sequence<View> getChildren(ViewGroup paramViewGroup)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "$this$children");
    (Sequence)new Sequence()
    {
      public Iterator<View> iterator()
      {
        return ViewGroupKt.iterator(this.$this_children);
      }
    };
  }
  
  public static final int getSize(ViewGroup paramViewGroup)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "$this$size");
    return paramViewGroup.getChildCount();
  }
  
  public static final boolean isEmpty(ViewGroup paramViewGroup)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "$this$isEmpty");
    boolean bool;
    if (paramViewGroup.getChildCount() == 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final boolean isNotEmpty(ViewGroup paramViewGroup)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "$this$isNotEmpty");
    boolean bool;
    if (paramViewGroup.getChildCount() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    return bool;
  }
  
  public static final Iterator<View> iterator(ViewGroup paramViewGroup)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "$this$iterator");
    (Iterator)new Iterator()
    {
      private int index;
      
      public boolean hasNext()
      {
        boolean bool;
        if (this.index < this.$this_iterator.getChildCount()) {
          bool = true;
        } else {
          bool = false;
        }
        return bool;
      }
      
      public View next()
      {
        Object localObject = this.$this_iterator;
        int i = this.index;
        this.index = (i + 1);
        localObject = ((ViewGroup)localObject).getChildAt(i);
        if (localObject != null) {
          return localObject;
        }
        throw ((Throwable)new IndexOutOfBoundsException());
      }
      
      public void remove()
      {
        ViewGroup localViewGroup = this.$this_iterator;
        int i = this.index - 1;
        this.index = i;
        localViewGroup.removeViewAt(i);
      }
    };
  }
  
  public static final void minusAssign(ViewGroup paramViewGroup, View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "$this$minusAssign");
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    paramViewGroup.removeView(paramView);
  }
  
  public static final void plusAssign(ViewGroup paramViewGroup, View paramView)
  {
    Intrinsics.checkParameterIsNotNull(paramViewGroup, "$this$plusAssign");
    Intrinsics.checkParameterIsNotNull(paramView, "view");
    paramViewGroup.addView(paramView);
  }
  
  public static final void setMargins(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramMarginLayoutParams, "$this$setMargins");
    paramMarginLayoutParams.setMargins(paramInt, paramInt, paramInt, paramInt);
  }
  
  public static final void updateMargins(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Intrinsics.checkParameterIsNotNull(paramMarginLayoutParams, "$this$updateMargins");
    paramMarginLayoutParams.setMargins(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static final void updateMarginsRelative(ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Intrinsics.checkParameterIsNotNull(paramMarginLayoutParams, "$this$updateMarginsRelative");
    paramMarginLayoutParams.setMarginStart(paramInt1);
    paramMarginLayoutParams.topMargin = paramInt2;
    paramMarginLayoutParams.setMarginEnd(paramInt3);
    paramMarginLayoutParams.bottomMargin = paramInt4;
  }
}
