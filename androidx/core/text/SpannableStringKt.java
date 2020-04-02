package androidx.core.text;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(bv={1, 0, 3}, d1={"\000(\n\000\n\002\020\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\002\n\002\020\000\n\000\n\002\030\002\n\000\n\002\020\r\n\000\032\r\020\000\032\0020\001*\0020\002H?\b\032%\020\003\032\0020\001*\0020\0022\006\020\004\032\0020\0052\006\020\006\032\0020\0052\006\020\007\032\0020\bH?\n\032\035\020\003\032\0020\001*\0020\0022\006\020\t\032\0020\n2\006\020\007\032\0020\bH?\n\032\r\020\013\032\0020\002*\0020\fH?\b?\006\r"}, d2={"clearSpans", "", "Landroid/text/Spannable;", "set", "start", "", "end", "span", "", "range", "Lkotlin/ranges/IntRange;", "toSpannable", "", "core-ktx_release"}, k=2, mv={1, 1, 16})
public final class SpannableStringKt
{
  public static final void clearSpans(Spannable paramSpannable)
  {
    Intrinsics.checkParameterIsNotNull(paramSpannable, "$this$clearSpans");
    Object localObject = (Spanned)paramSpannable;
    int i = ((Spanned)localObject).length();
    int j = 0;
    localObject = ((Spanned)localObject).getSpans(0, i, Object.class);
    Intrinsics.checkExpressionValueIsNotNull(localObject, "getSpans(start, end, T::class.java)");
    i = localObject.length;
    while (j < i)
    {
      paramSpannable.removeSpan(localObject[j]);
      j++;
    }
  }
  
  public static final void set(Spannable paramSpannable, int paramInt1, int paramInt2, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSpannable, "$this$set");
    Intrinsics.checkParameterIsNotNull(paramObject, "span");
    paramSpannable.setSpan(paramObject, paramInt1, paramInt2, 17);
  }
  
  public static final void set(Spannable paramSpannable, IntRange paramIntRange, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramSpannable, "$this$set");
    Intrinsics.checkParameterIsNotNull(paramIntRange, "range");
    Intrinsics.checkParameterIsNotNull(paramObject, "span");
    paramSpannable.setSpan(paramObject, paramIntRange.getStart().intValue(), paramIntRange.getEndInclusive().intValue(), 17);
  }
  
  public static final Spannable toSpannable(CharSequence paramCharSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramCharSequence, "$this$toSpannable");
    paramCharSequence = SpannableString.valueOf(paramCharSequence);
    Intrinsics.checkExpressionValueIsNotNull(paramCharSequence, "SpannableString.valueOf(this)");
    return (Spannable)paramCharSequence;
  }
}
